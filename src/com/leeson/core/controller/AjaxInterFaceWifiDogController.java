package com.leeson.core.controller;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalsmsapiQuery;
import com.leeson.core.query.PortalweixinwifiQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortalsmsapiService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.service.PortalweixinwifiService;
import com.leeson.core.utils.WifidogKick;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.model.WeixinMap;
import com.leeson.portal.core.model.WeixinSMSMap;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxInterFaceWifiDogController
{

  @Autowired
  private PortalaccountService accountService;

  @Autowired
  private PortalweixinwifiService weixinwifiService;

  @Autowired
  private PortalaccountmacsService macsService;

  @Autowired
  private PortalbasService basService;

  @Autowired
  private PortalsmsapiService portalsmsapiService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalwebService webService;

  @Autowired
  private PortalbasauthService portalbasauthService;
  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

  private static Logger logger = Logger.getLogger(AjaxInterFaceWifiDogController.class);

  @ResponseBody
  @RequestMapping({"/ajax_wifidog_sms"})
  public Map<String, String> sms(String phone, String yzm, HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    Map map = new HashMap();

    if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
      map.put("ret", "2");
      map.put("msg", "请输入手机号和验证码！！");
      return map;
    }
    if (phone.length() != 11) {
      map.put("ret", "2");
      map.put("msg", "手机号码不正确！");
      return map;
    }
    try {
      Long.parseLong(phone);
    } catch (Exception e) {
      map.put("ret", "2");
      map.put("msg", "手机号码不正确！");
      return map;
    }
    String code = "";
    PortalsmsapiQuery query = new PortalsmsapiQuery();
    query.setState("1");
    List smsList = this.portalsmsapiService
      .getPortalsmsapiList(query);
    Portalsmsapi smsapi;
    if (smsList.size() > 0)
      smsapi = (Portalsmsapi)smsList.get(0);
    else {
      smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
    }
    Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
    Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
    try {
      Date saveDate = (Date)objs[1];
      Long nowTime = Long.valueOf(new Date().getTime());
      if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        map.put("ret", "2");
        map.put("msg", "验证码已过期，请重新获取验证码！");
        return map;
      }
      code = (String)objs[0];
    } catch (Exception e) {
      map.put("ret", "2");
      map.put("msg", "手机号或验证码不正确！");
      return map;
    }

    if (!yzm.equals(code)) {
      map.put("ret", "2");
      map.put("msg", "验证码不正确！");
      return map;
    }

    String more = smsapi.getMore();
    if ("0".equals(more)) {
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
    }

    HttpSession session = request.getSession();

    String gw_id = (String)session.getAttribute("gw_id");
    String gw_address = (String)session.getAttribute("gw_address");
    String gw_port = (String)session.getAttribute("gw_port");
    String mac = (String)session.getAttribute("mac");
    String ip = GetNgnixRemotIP.getRemoteAddrIp(request);

    String url = (String)session.getAttribute("url");
    String basName;
    if (stringUtils.isNotBlank(gw_id))
      try
      {
        Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
        while (iteratorConfig.hasNext()) {
          Object o = iteratorConfig.next();
          String t = o.toString();
          Portalbas bas = (Portalbas)config.getConfigMap().get(t);
          basName = bas.getBasname();
          if ((stringUtils.isNotBlank(basName)) && 
            (basName.equals(gw_id))) {
            basConfig = bas;
            break;
          }
        }
      }
      catch (Exception e) {
        basConfig = (Portalbas)config.getConfigMap().get("");
      }
    try
    {
      String web = (String)session.getAttribute("web");
      if (stringUtils.isNotBlank(web)) {
        while (web.endsWith("/")) {
          web = web.substring(0, web.length() - 1);
        }
        Long webID = Long.valueOf(web);
        if (webID.longValue() != 0L) {
          Portalweb portalweb = this.webService.getPortalwebByKey(webID);
          if (portalweb != null) {
            portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
            this.webService.updatePortalwebByKey(portalweb);
          }
        } else {
          com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1) {
    }
    String goUrl = url;
    PortalbasauthQuery bsq = new PortalbasauthQuery();
    bsq.setBasip(basConfig.getBasIp());
    List<Portalbasauth> basauths = this.portalbasauthService
      .getPortalbasauthList(bsq);
    if (basauths.size() > 0) {
      for (Portalbasauth ba : basauths) {
        if (ba.getType().intValue() == 4) {
          goUrl = ba.getUrl();
          if (!stringUtils.isBlank(goUrl)) break;
          goUrl = url;

          break;
        }
      }
    }
    if (stringUtils.isBlank(goUrl)) {
      goUrl = 
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[0];
    }
    url = goUrl;
    session.setAttribute("url", url);

    String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);

    if (basConfig.getAuthInterface().contains("4"))
    {
      session.setAttribute("username", phone);
      session.setAttribute("ip", ip);
      session.setAttribute("token", token);

      String[] loginInfo = new String[10];
      loginInfo[0] = phone;
      Date now = new Date();
      loginInfo[3] = ThreadLocalDateUtil.format(now);
      loginInfo[4] = ip;
      loginInfo[5] = mac;
      loginInfo[6] = gw_id;
      loginInfo[7] = gw_address;
      loginInfo[8] = "0";
      loginInfo[9] = "0";

      WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
        .put(token, loginInfo);

      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", phone);
      map.put("msg", "认证成功！");
      map.put("l", url);
      map.put("gw_address", gw_address);
      map.put("gw_port", gw_port);
      map.put("token", token);
      return map;
    }

    map.put("ret", "3");
    map.put("i", "");
    map.put("u", phone);
    map.put("msg", "系统不是该验证模式，请联系管理员！！");
    if (stringUtils.isBlank(url)) {
      url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
    }
    map.put("l", url);
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_wifidog_weixinsms"})
  public Map<String, String> weixinsms(String pwd, HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    Map map = new HashMap();
    HttpSession session = request.getSession();
    String gw_id = (String)session.getAttribute("gw_id");
    String gw_address = (String)session.getAttribute("gw_address");
    String gw_port = (String)session.getAttribute("gw_port");
    String mac = (String)session.getAttribute("mac");
    String url = (String)session.getAttribute("url");

    String ip = GetNgnixRemotIP.getRemoteAddrIp(request);

    String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);

    if (stringUtils.isBlank(pwd)) {
      map.put("ret", "2");
      map.put("i", ip);
      map.put("u", "微信获取密码认证");
      map.put("msg", "上网密码不正确！！");
      if (stringUtils.isBlank(url)) {
        url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
      }
      map.put("l", url);
      return map;
    }
    if (!pwd.equals(WeixinSMSMap.getInstance().getPwd())) {
      map.put("ret", "2");
      map.put("i", ip);
      map.put("u", "微信获取密码认证");
      map.put("msg", "上网密码不正确！！");
      if (stringUtils.isBlank(url)) {
        url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
      }
      map.put("l", url);
      return map;
    }

    if (basConfig.getAuthInterface().contains("6"))
    {
      session.setAttribute("username", "微信获取密码认证");
      session.setAttribute("ip", ip);
      session.setAttribute("token", token);

      String[] loginInfo = new String[10];
      loginInfo[0] = "微信获取密码认证";
      Date now = new Date();
      loginInfo[3] = ThreadLocalDateUtil.format(now);
      loginInfo[4] = ip;
      loginInfo[5] = mac;
      loginInfo[6] = gw_id;
      loginInfo[7] = gw_address;
      loginInfo[8] = "0";
      loginInfo[9] = "0";

      WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
        .put(token, loginInfo);

      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", "微信获取密码认证");
      map.put("msg", "认证成功！");
      if (stringUtils.isBlank(url)) {
        url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
      }
      map.put("l", url);

      map.put("gw_address", gw_address);
      map.put("gw_port", gw_port);
      map.put("token", token);
      return map;
    }

    map.put("ret", "3");
    map.put("i", ip);
    map.put("u", "微信获取密码认证");
    map.put("msg", "系统不是该验证模式，请联系管理员！！");
    if (stringUtils.isBlank(url)) {
      url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
    }
    map.put("l", url);
    return map;
  }

  @ResponseBody
  @RequestMapping({"/wifidog_check_weixin"})
  public Map<String, String> wifidog_check_weixin(HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession();
    String token = (String)session.getAttribute("token");

    Map map = new HashMap();
    if (stringUtils.isBlank(token)) {
      map.put("ret", "1");
      return map;
    }
    try {
      String[] loginInfo = (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().get(token);
      Date wxt = 
        (Date)WeixinMap.getInstance().getWeixinipmap()
        .get("wifidog" + token);
      if ((loginInfo != null) && (wxt == null)) {
        map.put("ret", "0");
        return map;
      }
    }
    catch (Exception localException) {
      map.put("ret", "1");
    }return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_wifidog_weixin"})
  public Map<String, String> weixin(HttpServletRequest request, HttpServletResponse response) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    Map map = new HashMap();
    HttpSession session = request.getSession();
    String gw_id = (String)session.getAttribute("gw_id");
    String gw_address = (String)session.getAttribute("gw_address");
    String gw_port = (String)session.getAttribute("gw_port");
    String mac = (String)session.getAttribute("mac");
    String ssid = gw_id;
    String ip = GetNgnixRemotIP.getRemoteAddrIp(request);

    String url = (String)session.getAttribute("url");
    String basName;
    if (stringUtils.isNotBlank(gw_id))
      try
      {
        Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
        while (iteratorConfig.hasNext()) {
          Object o = iteratorConfig.next();
          String t = o.toString();
          Portalbas bas = (Portalbas)config.getConfigMap().get(t);
          basName = bas.getBasname();
          if ((stringUtils.isNotBlank(basName)) && 
            (basName.equals(gw_id))) {
            basConfig = bas;
            break;
          }
        }
      }
      catch (Exception e) {
        basConfig = (Portalbas)config.getConfigMap().get("");
      }
    try
    {
      String web = (String)session.getAttribute("web");
      if (stringUtils.isNotBlank(web)) {
        while (web.endsWith("/")) {
          web = web.substring(0, web.length() - 1);
        }
        Long webID = Long.valueOf(web);
        if (webID.longValue() != 0L) {
          Portalweb portalweb = this.webService.getPortalwebByKey(webID);
          if (portalweb != null) {
            portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
            this.webService.updatePortalwebByKey(portalweb);
          }
        } else {
          com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1) {
    }
    String goUrl = url;
    PortalbasauthQuery bsq = new PortalbasauthQuery();
    bsq.setBasip(basConfig.getBasIp());
    List<Portalbasauth> basauths = this.portalbasauthService
      .getPortalbasauthList(bsq);
    if (basauths.size() > 0) {
      for (Portalbasauth ba : basauths) {
        if (ba.getType().intValue() == 5) {
          goUrl = ba.getUrl();
          if (!stringUtils.isBlank(goUrl)) break;
          goUrl = url;

          break;
        }
      }
    }
    if (stringUtils.isBlank(goUrl)) {
      goUrl = 
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[0];
    }
    url = goUrl;
    session.setAttribute("url", url);

    String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);

    if (basConfig.getAuthInterface().contains("5"))
    {
      session.setAttribute("username", "微信验证");
      session.setAttribute("ip", ip);
      session.setAttribute("token", token);

      String[] loginInfo = new String[11];
      loginInfo[0] = "微信验证";
      Date now = new Date();
      loginInfo[3] = ThreadLocalDateUtil.format(now);
      loginInfo[4] = ip;
      loginInfo[5] = mac;
      loginInfo[6] = gw_id;
      loginInfo[7] = gw_address;
      loginInfo[8] = "0";
      loginInfo[9] = "0";
      loginInfo[10] = "weixin";

      WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
        .put(token, loginInfo);
      WeixinMap.getInstance().getWeixinipmap()
        .put("wifidog" + token.trim(), now);

      Long time = Long.valueOf(now.getTime());

      Portalweixinwifi wifi = null;
      if (stringUtils.isNotBlank(ssid)) {
        PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
        wxq.setSsid(ssid);
        wxq.setSsidLike(false);
        wxq.setBasip(gw_address);
        wxq.setBasipLike(false);
        List wxs = this.weixinwifiService
          .getPortalweixinwifiList(wxq);
        if (wxs.size() > 0) {
          wifi = (Portalweixinwifi)wxs.get(0);
        } else {
          wxq = new PortalweixinwifiQuery();
          wxq.setSsid(ssid);
          wxq.setSsidLike(false);
          wxs = this.weixinwifiService.getPortalweixinwifiList(wxq);
          if (wxs.size() > 0) {
            wifi = (Portalweixinwifi)wxs.get(0);
          }
        }
      }
      if (wifi == null) {
        PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
        wxq.setBasip(gw_address);
        wxq.setBasipLike(false);
        List wxs = this.weixinwifiService
          .getPortalweixinwifiList(wxq);
        if (wxs.size() > 0) {
          wifi = (Portalweixinwifi)wxs.get(0);
        }
      }
      if (wifi == null) {
        wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
      }

      if (!AjaxInterFaceController.Do()) {
        wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
      }

      if (wifi != null) {
        String appId = wifi.getAppId();
        String extend = "wifidog" + token.trim();
        String timestamp = String.valueOf(time);
        String shop_id = wifi.getShopId();

        String authUrl = request.getScheme() + "://" + 
          request.getServerName() + ":" + 
          request.getServerPort() + request.getContextPath() + "/weixinAuth.action";
        ssid = wifi.getSsid();
        String secretKey = wifi.getSecretKey();
        mac = "";
        String bssid = "";

        String code = appId + extend + timestamp + shop_id + authUrl + 
          mac + ssid + bssid + secretKey;
        String sign = DigestUtils.md5Hex(code);

        if (basConfig.getIsdebug().equals("1")) {
          logger.info("finally ssid=" + ssid + " token=" + token + 
            " ip=" + ip + " code=" + code + " sign" + sign);
        }

        map.put("ret", "0");
        map.put("appId", appId);
        map.put("extend", extend);
        map.put("timestamp", timestamp);
        map.put("sign", sign);
        map.put("shop_id", shop_id);
        map.put("authUrl", authUrl);
        map.put("mac", mac);
        map.put("ssid", ssid);
        map.put("bssid", bssid);

        map.put("gw_address", gw_address);
        map.put("gw_port", gw_port);
        map.put("token", token);

        session.setAttribute("appId", appId);
        session.setAttribute("extend", extend);
        session.setAttribute("timestamp", timestamp);
        session.setAttribute("sign", sign);
        session.setAttribute("shop_id", shop_id);
        session.setAttribute("authUrl", authUrl);
        session.setAttribute("mac", mac);
        session.setAttribute("ssid", ssid);
        session.setAttribute("bssid", bssid);

        session.setAttribute("token", token);

        return map;
      }
      map.put("ret", "1");
      return map;
    }

    map.put("ret", "3");
    return map;
  }

  private Map<String, Object> checkLocalAccount(String username, String password)
  {
    PortalaccountQuery accountqQuery = new PortalaccountQuery();
    accountqQuery.setLoginNameLike(false);
    accountqQuery.setPasswordLike(false);
    accountqQuery.setLoginName(username);
    accountqQuery.setPassword(password);
    List accounts = this.accountService
      .getPortalaccountList(accountqQuery);

    Map map = new HashMap();
    if (accounts.size() != 0) {
      Portalaccount account = (Portalaccount)accounts.get(0);
      if (password.equals(account.getPassword())) {
        map.put("id", account.getId());
        map.put("state", account.getState());
        map.put("date", account.getDate());
        map.put("time", account.getTime());
        if (account.getOctets() == null)
          map.put("octets", Long.valueOf(0L));
        else {
          map.put("octets", account.getOctets());
        }
        map.put("result", Integer.valueOf(1));
        return map;
      }
    }
    map.put("result", Integer.valueOf(0));
    return map;
  }

  private boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets)
  {
    Portalaccount account = this.accountService.getPortalaccountByKey(userId);

    if (userState.equals("0")) {
      return false;
    }

    if (userState.equals("1")) {
      return true;
    }

    if (userState.equals("3")) {
      Date now = new Date();
      if (userDate.getTime() > now.getTime()) {
        return true;
      }
      account.setState("2");
      this.accountService.updatePortalaccountByKey(account);
      userState = "2";
    }

    if (userState.equals("2")) {
      if (userTime.longValue() > 0L) {
        return true;
      }
      account.setState("4");
      this.accountService.updatePortalaccountByKey(account);
      userState = "4";
    }

    if (userState.equals("4")) {
      if (octets.longValue() > 0L) {
        return true;
      }
      account.setState("0");
      this.accountService.updatePortalaccountByKey(account);
      return false;
    }

    return false;
  }

  @ResponseBody
  @RequestMapping({"/ajax_wifidog_LoginOut"})
  public Map<String, String> LoginOut(HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();

    HttpSession session = request.getSession();
    String token = (String)session.getAttribute("token");

    if (stringUtils.isNotBlank(token)) {
      String[] loginInfo = (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().get(token);

      WifidogKick.offLine(token);
    }

    map.put("ret", "0");
    map.put("msg", "下线成功！");
    session.removeAttribute("username");
    session.removeAttribute("password");
    session.removeAttribute("ip");
    session.removeAttribute("token");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_wifidog_Login"})
  public Map<String, String> Login(String usr, String pwd, HttpServletRequest request, HttpServletResponse response) throws IOException {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    Map map = new HashMap();
    HttpSession session = request.getSession();
    String gw_id = (String)session.getAttribute("gw_id");
    String gw_address = (String)session.getAttribute("gw_address");
    String gw_port = (String)session.getAttribute("gw_port");
    String mac = (String)session.getAttribute("mac");

    String ip = GetNgnixRemotIP.getRemoteAddrIp(request);

    String url = (String)session.getAttribute("url");

    if (stringUtils.isNotBlank(gw_id))
      try
      {
        Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
        while (iteratorConfig.hasNext()) {
          Object o = iteratorConfig.next();
          String t = o.toString();
          Portalbas bas = (Portalbas)config.getConfigMap().get(t);
          String basName = bas.getBasname();
          if ((stringUtils.isNotBlank(basName)) && 
            (basName.equals(gw_id))) {
            basConfig = bas;
            break;
          }
        }
      }
      catch (Exception e) {
        basConfig = (Portalbas)config.getConfigMap().get("");
      }
    try
    {
      String web = (String)session.getAttribute("web");
      if (stringUtils.isNotBlank(web)) {
        while (web.endsWith("/")) {
          web = web.substring(0, web.length() - 1);
        }
        Long webID = Long.valueOf(web);
        if (webID.longValue() != 0L) {
          Portalweb portalweb = this.webService.getPortalwebByKey(webID);
          if (portalweb != null) {
            portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
            this.webService.updatePortalwebByKey(portalweb);
          }
        } else {
          com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1)
    {
    }
    String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);

    if ((basConfig.getAuthInterface().contains("0")) && 
      (stringUtils.isBlank(usr)) && (stringUtils.isBlank(pwd)))
    {
      session.setAttribute("username", "一键认证");
      session.setAttribute("ip", ip);
      session.setAttribute("token", token);

      String[] loginInfo = new String[10];
      loginInfo[0] = "一键认证";
      Date now = new Date();
      loginInfo[3] = ThreadLocalDateUtil.format(now);
      loginInfo[4] = ip;
      loginInfo[5] = mac;
      loginInfo[6] = gw_id;
      loginInfo[7] = gw_address;
      loginInfo[8] = "0";
      loginInfo[9] = "0";

      WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
        .put(token, loginInfo);

      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", "一键认证");
      map.put("msg", "认证成功！");
      String goUrl = url;
      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basConfig.getBasIp());
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 0) {
            goUrl = ba.getUrl();
            if (!stringUtils.isBlank(goUrl)) break;
            goUrl = url;

            break;
          }
        }
      }
      if (stringUtils.isBlank(goUrl)) {
        goUrl = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }
      url = goUrl;
      session.setAttribute("url", url);
      map.put("l", url);

      map.put("gw_address", gw_address);
      map.put("gw_port", gw_port);
      map.put("token", token);
      return map;
    }

    if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", "");
      map.put("msg", "用户名和密码不能为空！！");
      if (stringUtils.isBlank(url)) {
        url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
      }
      map.put("l", url);
      return map;
    }

    String username = usr;
    String password = pwd;
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("请求认证    用户：" + username + " IP地址:" + ip);
    }

    String userState = "";
    Long userId = Long.valueOf(0L);
    Date userDate = new Date();
    Long userTime = Long.valueOf(0L);
    Long octets = Long.valueOf(0L);
    String userMac;
    if (basConfig.getAuthInterface().contains("1"))
    {
      Map resultCheck = checkLocalAccount(username, 
        password);
      int state = ((Integer)resultCheck.get("result")).intValue();

      if (state == 0) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", username);
        map.put("msg", "用户名密码错误,或账户已过期！！");
        if (stringUtils.isBlank(url)) {
          url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
        }
        map.put("l", url);
        return map;
      }

      userState = (String)resultCheck.get("state");
      userId = (Long)resultCheck.get("id");
      userDate = (Date)resultCheck.get("date");
      userTime = (Long)resultCheck.get("time");
      octets = (Long)resultCheck.get("octets");

      if (!checkTimeOut(userState, userId, userDate, userTime, octets)) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", username);
        map.put("msg", "账户已过期，请及时联系管理员充值！！");
        if (stringUtils.isBlank(url)) {
          url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
        }
        map.put("l", url);
        return map;
      }

      Portalaccount acc = this.accountService.getPortalaccountByKey(userId);
      if (acc != null) {
        Integer limitCount = acc.getMaclimitcount();
        if ((limitCount != null) && (limitCount.intValue() > 0)) {
          int count = 0;
          Iterator iterator = OnlineMap.getInstance()
            .getOnlineUserMap().keySet()
            .iterator();
          while (iterator.hasNext()) {
            Object o = iterator.next();
            String t = o.toString();
            String[] loginInfo = 
              (String[])OnlineMap.getInstance()
              .getOnlineUserMap().get(t);
            String haveUsername = loginInfo[0];
            if (username.equals(haveUsername)) {
              count++;
            }
          }
          if (count >= limitCount.intValue()) {
            map.put("ret", "1");
            map.put("i", ip);
            map.put("u", username);
            map.put("msg", "该账户同时在线数已超出限制，请等待使用该账户的其他设备下线！！");
            return map;
          }
        }

      }

      int limitMac = acc.getMaclimit().intValue();
      int limitCount = acc.getMaclimitcount().intValue();
      int auto = acc.getAutologin().intValue();

      userMac = mac;

      if (limitMac == 1) {
        if (stringUtils.isBlank(userMac))
        {
          map.put("ret", "1");
          map.put("i", ip);
          map.put("u", username);
          map.put("msg", "协议不支持MAC绑定，请联系管理员修改账户属性！！");
          if (stringUtils.isBlank(url)) {
            url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
          }
          map.put("l", url);
          return map;
        }

        PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
        mq.setAccountId(userId);
        List<Portalaccountmacs> accountmacs = this.macsService
          .getPortalaccountmacsList(mq);
        if ((accountmacs.size() < 1) || (limitCount == 0) || (accountmacs.size() < limitCount))
        {
          if ((stringUtils.isNotBlank(userMac)) && 
            (!"error".equals(userMac))) {
            Boolean isHave = Boolean.valueOf(false);
            for (Portalaccountmacs amacs : accountmacs)
            {
              if (amacs.getMac().toLowerCase()
                .equals(userMac)) {
                isHave = Boolean.valueOf(true);
                break;
              }
            }
            if (!isHave.booleanValue()) {
              Portalaccountmacs macs = new Portalaccountmacs();
              macs.setAccountId(userId);
              macs.setMac(userMac);
              this.macsService.addPortalaccountmacs(macs);
            }
          }
        }
        else
        {
          Boolean isHave = Boolean.valueOf(false);
          for (Portalaccountmacs amacs : accountmacs) {
            if (amacs.getMac().toLowerCase().equals(userMac)) {
              isHave = Boolean.valueOf(true);
              break;
            }
          }
          if (!isHave.booleanValue())
          {
            map.put("ret", "1");
            map.put("i", ip);
            map.put("u", username);
            map.put("msg", "此账号没有绑定该设备，请联系管理员！！");
            if (stringUtils.isBlank(url)) {
              url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
            }
            map.put("l", url);
            return map;
          }
        }
      }

      if ((auto == 1) && 
        (stringUtils.isNotBlank(userMac))) {
        PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
        mq.setAccountId(userId);
        List<Portalaccountmacs> accountmacs = this.macsService
          .getPortalaccountmacsList(mq);
        if ((limitCount == 0) || (accountmacs.size() < limitCount)) {
          boolean macNotHave = true;
          for (Portalaccountmacs macs : accountmacs) {
            if (userMac.equals(macs.getMac())) {
              macNotHave = false;
            }
          }
          if (macNotHave) {
            Portalaccountmacs macs = new Portalaccountmacs();
            macs.setAccountId(userId);
            macs.setMac(userMac);
            this.macsService.addPortalaccountmacs(macs);
          }

        }

      }

    }
    else
    {
       userMac = mac;

      if (stringUtils.isNotBlank(userMac))
      {
        boolean macHave = false;
        List<Portalaccountmacs> accountmacs = this.macsService
          .getPortalaccountmacsList(new PortalaccountmacsQuery());
        for (Portalaccountmacs macs : accountmacs) {
          if (macs.getMac().equals(userMac)) {
            macHave = true;
            break;
          }
        }
        if (!macHave) {
          List accs = this.accountService.getPortalaccountList(new PortalaccountQuery());
          if ((accs.size() > 0) && 
            (((Portalaccount)accs.get(0)).getAutologin().intValue() == 1)) {
            Portalaccountmacs accMac = new Portalaccountmacs();
            accMac.setAccountId(((Portalaccount)accs.get(0)).getId());
            accMac.setMac(userMac);
            this.macsService.addPortalaccountmacs(accMac);
          }

        }

      }

    }

    if (basConfig.getAuthInterface()
      .contains("1")) {
      session.setAttribute("password", password);
    }

    if (stringUtils.isNotBlank(mac)) {
      String[] userinfo = new String[2];
      userinfo[0] = username;
      userinfo[1] = password;
      AutoLoginMap.getInstance().getAutoLoginMap()
        .put(mac, userinfo);
    }

    session.setAttribute("username", username);
    session.setAttribute("ip", ip);
    session.setAttribute("token", token);

    String[] loginInfo = new String[10];
    loginInfo[0] = username;
    loginInfo[1] = String.valueOf(userId);
    Date now = new Date();
    loginInfo[3] = ThreadLocalDateUtil.format(now);
    loginInfo[4] = ip;
    loginInfo[5] = mac;
    loginInfo[6] = gw_id;
    loginInfo[7] = gw_address;
    loginInfo[8] = "0";
    loginInfo[9] = "0";

    WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
      .put(token, loginInfo);

    map.put("ret", "0");
    map.put("i", ip);
    map.put("u", username);
    map.put("msg", "认证成功！");
    String goUrl = url;
    PortalbasauthQuery bsq = new PortalbasauthQuery();
    bsq.setBasip(basConfig.getBasIp());
    List<Portalbasauth> basauths = this.portalbasauthService
      .getPortalbasauthList(bsq);
    if (basauths.size() > 0) {
      for (Portalbasauth ba : basauths) {
        if (ba.getType().intValue() == 1) {
          goUrl = ba.getUrl();
          if (!stringUtils.isBlank(goUrl)) break;
          goUrl = url;

          break;
        }
      }
    }
    if (stringUtils.isBlank(goUrl)) {
      goUrl = 
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[0];
    }
    url = goUrl;
    session.setAttribute("url", url);

    map.put("gw_address", gw_address);
    map.put("gw_port", gw_port);
    map.put("token", token);
    return map;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxInterFaceWifiDogController
 * JD-Core Version:    0.6.2
 */