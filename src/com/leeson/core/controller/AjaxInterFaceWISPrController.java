package com.leeson.core.controller;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portalonlinelimit;
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
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortalonlinelimitService;
import com.leeson.core.service.PortalsmsapiService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.service.PortalweixinwifiService;
import com.leeson.core.utils.Kick;
import com.leeson.core.utils.TwoDimensionCode.makeImg;
import com.leeson.portal.core.model.GuestAuthMap;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.model.RosAuthMap;
import com.leeson.portal.core.model.UserLimitMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ajaxInterFaceWISPrController")
public class AjaxInterFaceWISPrController
{

  @Autowired
  private PortalaccountService accountService;

  @Autowired
  private PortalweixinwifiService weixinwifiService;

  @Autowired
  private PortalaccountmacsService macsService;

  @Autowired
  private PortalsmsapiService portalsmsapiService;

  @Autowired
  private PortalbasauthService portalbasauthService;

  @Autowired
  private PortalonlinelimitService onlinelimitService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalwebService webService;
  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

  private static Logger logger = Logger.getLogger(AjaxInterFaceWISPrController.class);
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  @ResponseBody
  @RequestMapping({"/ajax_WISPr_guestHeart"})
  public Map<String, String> ajax_WISPr_guestHeart(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession();
    String ikmac = (String)session.getAttribute("ikmac");
    String ikweb = (String)session.getAttribute("ikweb");
    String authUrl = ikweb;
    Map map = new HashMap();

    String accountInfo = null;
    accountInfo = 
      (String)GuestAuthMap.getInstance().getGuestAuthMap()
      .get(ip + ":" + basip);
    if (stringUtils.isNotBlank(accountInfo)) {
      String username = accountInfo;

      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 7) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrl = ba.getUrl();
            if ((stringUtils.isBlank(authUser)) || 
              (stringUtils.isBlank(authPwd))) {
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();
            }
            if (!stringUtils.isBlank(authUrl)) break;
            authUrl = ikweb;

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrl)) {
        authUrl = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }
      session.setAttribute("ikweb", authUrl);

      String[] rosInfo = new String[5];
      rosInfo[0] = "7";
      rosInfo[1] = "8";
      rosInfo[2] = username;
      rosInfo[3] = authUser;
      rosInfo[4] = authPwd;
      RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);

      session.setAttribute("authUser", authUser);
      session.setAttribute("authPwd", authPwd);
      session.setAttribute("username", username);
      session.setAttribute("ip", ip);
      session.setAttribute("basip", basip);

      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", username);
      map.put("msg", "你已经通过认证！！");

      GuestAuthMap.getInstance().getGuestAuthMap()
        .remove(ip + ":" + basip);
      return map;
    }
    map.put("ret", "1");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_WISPr_guest"})
  public Map<String, String> ajax_WISPr_guest(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();

    String ikmac = (String)session.getAttribute("ikmac");
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
    catch (Exception localException)
    {
    }

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        else if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
        else if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isBlank(ip)) {
      ip = (String)session.getAttribute("ip");
    }
    if (stringUtils.isBlank(ip)) {
      ip = cip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    if (stringUtils.isBlank(basip)) {
      basip = (String)session.getAttribute("basip");
    }
    if (stringUtils.isBlank(basip)) {
      basip = cbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    if ((!basConfig.getBas().equals("5")) && 
      (!basConfig.getBas().equals("6")) && 
      (!basConfig.getBas().equals("7")) && 
      (!basConfig
      .getBas().equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }
    if (stringUtils.isBlank(ikmac)) {
      map.put("ret", "10");
      map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
      return map;
    }
    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      map.put("ret", "2");
      map.put("i", ip);
      map.put("u", "访客认证");
      map.put("msg", "你已经通过认证,请不要重复刷新！！");
      return map;
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
      String basePath = request.getScheme() + "://" + 
        request.getServerName() + ":" + request.getServerPort() + 
        request.getContextPath() + "/";
      String guestAuthPath = basePath + "ajax_WISPr_guestScan.action?ip=" + 
        ip + "&basip=" + basip + "&mac=" + ikmac;

      String logoPath = request.getServletContext().getRealPath("/");
      String outPath = logoPath + "ExcelOut/";
      File dir = new File(outPath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      Date now = new Date();
      String nowString = ThreadLocalDateUtil.format(now);
      outPath = outPath + ip + nowString + ".jpg";
      logoPath = logoPath + "distogoS.png";
      int result = makeImg.make(guestAuthPath, logoPath, outPath);
      if (result == 1) {
        String imgUrl = basePath + "ExcelOut/" + ip + nowString + 
          ".jpg";
        map.put("ret", "0");
        map.put("url", imgUrl);
        map.put("ip", ip);
        map.put("basip", basip);
        map.put("mac", ikmac);
        map.put("msg", "请让员工扫描此二维码，授权上网！！");
        return map;
      }
      map.put("ret", "1");
      map.put("msg", "生成二维码失败！！");
      return map;
    }

    map.put("ret", "3");
    map.put("i", ip);
    map.put("u", "访客认证");
    map.put("msg", "系统不是该验证模式，请联系管理员！！");
    return map;
  }

  @RequestMapping({"/ajax_WISPr_guestScan"})
  public String ajax_WISPr_guestScan(String ip, String basip, String mac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
      model.addAttribute("msg", "参数获取错误！！");
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
      return "guest/result";
    }
    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      model.addAttribute("msg", "该用户已经认证！！");
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
      return "guest/result";
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
      model.addAttribute("msg", "请员工账号授权！！");
      return "guest/authAPI";
    }
    model.addAttribute("msg", "系统不是该验证模式，请联系管理员！！");
    model.addAttribute("ip", ip);
    model.addAttribute("basip", basip);
    model.addAttribute("mac", mac);
    return "guest/result";
  }

  @RequestMapping({"/ajax_WISPr_guestAuth"})
  public String ajax_WISPr_guestAuth(String usr, String pwd, String ip, String basip, String mac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
      model.addAttribute("msg", "参数获取错误！！");
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
      return "guest/result";
    }
    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      model.addAttribute("msg", "该用户已经认证！！");
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
      return "guest/result";
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
      String accountIP = GetNgnixRemotIP.getRemoteAddrIp(request);
      String[] accountInfo = null;

      accountInfo = (String[])onlineMap.getOnlineUserMap().get(
        accountIP + ":" + basip);

      Iterator iterator = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
      while (iterator.hasNext()) {
        Object o = iterator.next();
        String t = o.toString();
        if (t.contains(accountIP + ":")) {
          accountInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
          break;
        }
      }

      if (accountInfo != null) {
        String username = accountInfo[0];

        if (accountInfo.length < 6) {
          model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
          model.addAttribute("ip", ip);
          model.addAttribute("basip", basip);
          model.addAttribute("mac", mac);
          return "guest/result";
        }
        try {
          String state = accountInfo[5];
          if (!"ok".equals(state)) {
            model.addAttribute("msg", 
              "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
            model.addAttribute("ip", ip);
            model.addAttribute("basip", basip);
            model.addAttribute("mac", mac);
            return "guest/result";
          }
        } catch (Exception e) {
          model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
          model.addAttribute("ip", ip);
          model.addAttribute("basip", basip);
          model.addAttribute("mac", mac);
          return "guest/result";
        }

        if (1 == CheckMacTimeLimitMethod(mac, Long.valueOf(8L))) {
          model.addAttribute("msg", "该设备当日时长已用完！！");
          model.addAttribute("ip", ip);
          model.addAttribute("basip", basip);
          model.addAttribute("mac", mac);
        }

        username = username + "授权";
        GuestAuthMap.getInstance().getGuestAuthMap()
          .put(ip + ":" + basip, username);

        model.addAttribute("ip", ip);
        model.addAttribute("basip", basip);
        model.addAttribute("mac", mac);
        model.addAttribute("msg", "认证成功，访客可以上网了！！");
        return "guest/result";
      }

      model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
      return "guest/result";
    }

    model.addAttribute("msg", "系统不是该验证模式，请联系管理员！！");
    model.addAttribute("ip", ip);
    model.addAttribute("basip", basip);
    model.addAttribute("mac", mac);
    return "guest/result";
  }

  @ResponseBody
  @RequestMapping({"/ajax_WISPr_sms"})
  public Map<String, String> ajax_WISPr_sms(String ip, String phone, String yzm, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();
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

    String ikmac = (String)session.getAttribute("ikmac");
    String ikweb = (String)session.getAttribute("ikweb");
    String authUrl = ikweb;

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        else if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
        else if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isBlank(ip)) {
      ip = (String)session.getAttribute("ip");
    }
    if (stringUtils.isBlank(ip)) {
      ip = cip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    if (stringUtils.isBlank(basip)) {
      basip = (String)session.getAttribute("basip");
    }
    if (stringUtils.isBlank(basip)) {
      basip = cbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    if ((!basConfig.getBas().equals("5")) && 
      (!basConfig.getBas().equals("6")) && 
      (!basConfig.getBas().equals("7")) && 
      (!basConfig
      .getBas().equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }
    if (stringUtils.isBlank(ikmac)) {
      map.put("ret", "10");
      map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
      return map;
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", phone);
      map.put("msg", "你已经通过认证,请不要重复刷新！！");
      return map;
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("4"))
    {
      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 4) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrl = ba.getUrl();
            if ((stringUtils.isBlank(authUser)) || 
              (stringUtils.isBlank(authPwd))) {
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();
            }
            if (!stringUtils.isBlank(authUrl)) break;
            authUrl = ikweb;

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrl)) {
        authUrl = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }

      if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(1L))) {
        map.put("ret", "2");
        map.put("i", ip);
        map.put("u", phone);
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      Boolean info = Boolean.valueOf(true);

      if (info.booleanValue()) {
        String[] rosInfo = new String[5];
        rosInfo[0] = "4";
        rosInfo[1] = "1";
        rosInfo[2] = phone;
        rosInfo[3] = authUser;
        rosInfo[4] = authPwd;
        RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
        session.setAttribute("authUser", authUser);
        session.setAttribute("authPwd", authPwd);
        session.setAttribute("username", phone);

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);

        String more = smsapi.getMore();
        if ("0".equals(more)) {
          PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        }
        session.setAttribute("ikweb", authUrl);

        map.put("ret", "0");
        map.put("i", ip);
        map.put("u", phone);
        map.put("msg", "认证成功！");
        return map;
      }
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", phone);
      map.put("msg", "网络暂时不可用，请联系管理员！！");
      return map;
    }

    map.put("ret", "3");
    map.put("i", ip);
    map.put("u", phone);
    map.put("msg", "系统不是该验证模式，请联系管理员！！");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_WISPr_gzh"})
  public Map<String, String> ajax_WISPr_gzh(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();
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
    catch (Exception localException)
    {
    }
    String ikmac = (String)session.getAttribute("ikmac");
    String ssid = (String)session.getAttribute("ssid");
    String ikweb = (String)session.getAttribute("ikweb");
    String authUrlWeb = ikweb;

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        else if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
        else if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isBlank(ip)) {
      ip = (String)session.getAttribute("ip");
    }
    if (stringUtils.isBlank(ip)) {
      ip = cip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    if (stringUtils.isBlank(basip)) {
      basip = (String)session.getAttribute("basip");
    }
    if (stringUtils.isBlank(basip)) {
      basip = cbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    if ((!basConfig.getBas().equals("5")) && 
      (!basConfig.getBas().equals("6")) && 
      (!basConfig.getBas().equals("7")) && 
      (!basConfig
      .getBas().equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }
    if (stringUtils.isBlank(ikmac)) {
      map.put("ret", "10");
      map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
      return map;
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
      logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
        ip + " mac=" + ikmac);
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      map.put("ret", "2");
      map.put("msg", "你已经通过验证,或者下线后重试！！");
      return map;
    }

    String userAgent = request.getHeader("user-agent");

    boolean isComputer = false;
    if (stringUtils.isNotBlank(userAgent)) {
      if (userAgent.contains("Windows"))
        isComputer = true;
      else if (userAgent.contains("Macintosh")) {
        isComputer = true;
      }
      else if ((userAgent.contains("Android")) || 
        (userAgent.contains("iPhone")) || 
        (userAgent.contains("iPod")) || 
        (userAgent.contains("iPad"))) {
        isComputer = false;
      }

    }

    if (isComputer) {
      map.put("ret", "1");
      map.put("msg", "该认证模式仅支持手机使用！！");
      return map;
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("6"))
    {
      if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(7L))) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", "公众号认证-临时放行");
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 6) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrlWeb = ba.getUrl();
            if ((stringUtils.isBlank(authUser)) || 
              (stringUtils.isBlank(authPwd))) {
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();
            }
            if (!stringUtils.isBlank(authUrlWeb)) break;
            authUrlWeb = ikweb;

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrlWeb)) {
        authUrlWeb = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }

      Boolean info = Boolean.valueOf(true);

      if (info.booleanValue())
      {
        String[] rosInfo = new String[5];
        rosInfo[0] = "6";
        rosInfo[1] = "7";
        rosInfo[2] = "公众号认证-临时放行";
        rosInfo[3] = authUser;
        rosInfo[4] = authPwd;
        RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
        session.setAttribute("authUser", authUser);
        session.setAttribute("authPwd", authPwd);

        session.setAttribute("username", "公众号认证-临时放行");

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikweb", authUrlWeb);

        Long time = Long.valueOf(new Date().getTime());

        Portalweixinwifi wifi = null;
        if (stringUtils.isNotBlank(ssid)) {
          PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
          wxq.setSsid(ssid);
          wxq.setSsidLike(false);
          wxq.setBasip(basip);
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
          wxq.setBasip(basip);
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
        if (!Do()) {
          wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
        }

        if (wifi != null) {
          String appId = wifi.getAppId();
          String extend = ip + ":" + basip;
          String timestamp = String.valueOf(time);
          String shop_id = wifi.getShopId();

          String authUrl = request.getScheme() + "://" + 
            request.getServerName() + ":" + 
            request.getServerPort() + request.getContextPath() + "/gzhAuth.action";
          ssid = wifi.getSsid();
          String secretKey = wifi.getSecretKey();
          String mac = "";
          String bssid = "";

          String code = appId + extend + timestamp + shop_id + 
            authUrl + mac + ssid + bssid + secretKey;
          String sign = DigestUtils.md5Hex(code);

          if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug()
            .equals("1")) {
            logger.info("finally ssid=" + ssid + " basip=" + basip + 
              " ip=" + ip + " code=" + code + " sign" + 
              sign);
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

          session.setAttribute("appId", appId);
          session.setAttribute("extend", extend);
          session.setAttribute("timestamp", timestamp);
          session.setAttribute("sign", sign);
          session.setAttribute("shop_id", shop_id);
          session.setAttribute("authUrl", authUrl);
          session.setAttribute("mac", mac);
          session.setAttribute("ssid", ssid);
          session.setAttribute("bssid", bssid);
          return map;
        }
        map.put("ret", "1");
        map.put("msg", "微信认证参数未配置，请联系管理员！！");
        return map;
      }

      map.put("ret", "1");
      map.put("msg", "网络暂时不可用，请联系管理员！！");
      return map;
    }

    map.put("ret", "3");
    map.put("msg", "系统不是公众号认证模式！！！！");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_WISPr_weixin"})
  public Map<String, String> ajax_WISPr_weixin(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();
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
    catch (Exception localException)
    {
    }
    String ikmac = (String)session.getAttribute("ikmac");
    String ssid = (String)session.getAttribute("ssid");
    String ikweb = (String)session.getAttribute("ikweb");
    String authUrlWeb = ikweb;

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        else if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
        else if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isBlank(ip)) {
      ip = (String)session.getAttribute("ip");
    }
    if (stringUtils.isBlank(ip)) {
      ip = cip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    if (stringUtils.isBlank(basip)) {
      basip = (String)session.getAttribute("basip");
    }
    if (stringUtils.isBlank(basip)) {
      basip = cbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    if ((!basConfig.getBas().equals("5")) && 
      (!basConfig.getBas().equals("6")) && 
      (!basConfig.getBas().equals("7")) && 
      (!basConfig
      .getBas().equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }
    if (stringUtils.isBlank(ikmac)) {
      map.put("ret", "10");
      map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
      return map;
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
      logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
        ip + " mac=" + ikmac);
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      map.put("ret", "2");
      map.put("msg", "你已经通过验证,或者下线后重试！！");
      return map;
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("5"))
    {
      if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(6L))) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", "微信验证");
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 5) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrlWeb = ba.getUrl();
            if ((stringUtils.isBlank(authUser)) || 
              (stringUtils.isBlank(authPwd))) {
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();
            }
            if (!stringUtils.isBlank(authUrlWeb)) break;
            authUrlWeb = ikweb;

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrlWeb)) {
        authUrlWeb = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }

      Boolean info = Boolean.valueOf(true);

      if (info.booleanValue())
      {
        String[] rosInfo = new String[5];
        rosInfo[0] = "5";
        rosInfo[1] = "6";
        rosInfo[2] = "微信验证-临时放行";
        rosInfo[3] = authUser;
        rosInfo[4] = authPwd;
        RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
        session.setAttribute("authUser", authUser);
        session.setAttribute("authPwd", authPwd);

        session.setAttribute("username", "微信验证-临时放行");

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikweb", authUrlWeb);

        Long time = Long.valueOf(new Date().getTime());

        Portalweixinwifi wifi = null;
        if (stringUtils.isNotBlank(ssid)) {
          PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
          wxq.setSsid(ssid);
          wxq.setSsidLike(false);
          wxq.setBasip(basip);
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
          wxq.setBasip(basip);
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
        if (!Do()) {
          wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
        }

        if (wifi != null) {
          String appId = wifi.getAppId();
          String extend = ip + ":" + basip;
          String timestamp = String.valueOf(time);
          String shop_id = wifi.getShopId();

          String authUrl = request.getScheme() + "://" + 
            request.getServerName() + ":" + 
            request.getServerPort() + request.getContextPath() + "/weixinAuth.action";

          String userAgent = request.getHeader("user-agent");

          boolean isComputer = false;
          if (stringUtils.isNotBlank(userAgent)) {
            if (userAgent.contains("Windows"))
              isComputer = true;
            else if (userAgent.contains("Macintosh")) {
              isComputer = true;
            }
            else if ((userAgent.contains("Android")) || 
              (userAgent.contains("iPhone")) || 
              (userAgent.contains("iPod")) || 
              (userAgent.contains("iPad"))) {
              isComputer = false;
            }

          }

          if (isComputer)
          {
            authUrl = request.getScheme() + "://" + 
              request.getServerName() + ":" + 
              request.getServerPort() + 
              request.getContextPath() + 
              "/weixinPCAuth.action";
          }

          ssid = wifi.getSsid();
          String secretKey = wifi.getSecretKey();
          String mac = "";
          String bssid = "";

          String code = appId + extend + timestamp + shop_id + 
            authUrl + mac + ssid + bssid + secretKey;
          String sign = DigestUtils.md5Hex(code);

          if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug()
            .equals("1")) {
            logger.info("finally ssid=" + ssid + " basip=" + basip + 
              " ip=" + ip + " code=" + code + " sign" + 
              sign);
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

          session.setAttribute("appId", appId);
          session.setAttribute("extend", extend);
          session.setAttribute("timestamp", timestamp);
          session.setAttribute("sign", sign);
          session.setAttribute("shop_id", shop_id);
          session.setAttribute("authUrl", authUrl);
          session.setAttribute("mac", mac);
          session.setAttribute("ssid", ssid);
          session.setAttribute("bssid", bssid);
          return map;
        }
        map.put("ret", "1");
        map.put("msg", "微信认证参数未配置，请联系管理员！！");
        return map;
      }

      map.put("ret", "1");
      map.put("msg", "网络暂时不可用，请联系管理员！！");
      return map;
    }

    map.put("ret", "3");
    map.put("msg", "系统不是微信验证模式！！！！");
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
  @RequestMapping({"/ajax_WISPr_LoginOut"})
  public Map<String, String> ajax_WISPr_LoginOut(String ip, String basip, HttpServletRequest request, HttpServletResponse response) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();
    String ikmac = (String)session.getAttribute("ikmac");

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        else if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
        else if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isBlank(ip)) {
      ip = (String)session.getAttribute("ip");
    }
    if (stringUtils.isBlank(ip)) {
      ip = cip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    if (stringUtils.isBlank(basip)) {
      basip = (String)session.getAttribute("basip");
    }
    if (stringUtils.isBlank(basip)) {
      basip = cbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    if ((!basConfig.getBas().equals("5")) && 
      (!basConfig.getBas().equals("6")) && 
      (!basConfig.getBas().equals("7")) && 
      (!basConfig
      .getBas().equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }
    if (stringUtils.isBlank(ikmac)) {
      map.put("ret", "10");
      map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
      return map;
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (!isLogin) {
      map.put("ret", "0");
      map.put("msg", "你已经离线！");
      session.removeAttribute("username");
      session.removeAttribute("password");

      Cookie cookieIP = new Cookie("ip", null);
      cookieIP.setMaxAge(-1);
      response.addCookie(cookieIP);
      Cookie cookieBasIp = new Cookie("basip", null);
      cookieBasIp.setMaxAge(-1);
      response.addCookie(cookieBasIp);
      Cookie cookiePwd = new Cookie("password", null);
      cookiePwd.setMaxAge(-1);
      response.addCookie(cookiePwd);

      return map;
    }

    Kick.offLine(ip + ":" + basip, ikmac, "");

    map.put("ret", "0");
    map.put("msg", "下线成功！");
    session.removeAttribute("username");
    session.removeAttribute("password");

    Cookie cookieIP = new Cookie("ip", null);
    cookieIP.setMaxAge(-1);
    response.addCookie(cookieIP);
    Cookie cookieBasIp = new Cookie("basip", null);
    cookieBasIp.setMaxAge(-1);
    response.addCookie(cookieBasIp);
    Cookie cookiePwd = new Cookie("password", null);
    cookiePwd.setMaxAge(-1);
    response.addCookie(cookiePwd);

    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_WISPr_Login"})
  public Map<String, String> ajax_WISPr_Login(String usr, String pwd, String ip, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();
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
    catch (Exception localException)
    {
    }
    String ikmac = (String)session.getAttribute("ikmac");
    String ikweb = (String)session.getAttribute("ikweb");
    String authUrl = ikweb;

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        else if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
        else if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isBlank(ip)) {
      ip = (String)session.getAttribute("ip");
    }
    if (stringUtils.isBlank(ip)) {
      ip = cip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    if (stringUtils.isBlank(basip)) {
      basip = (String)session.getAttribute("basip");
    }
    if (stringUtils.isBlank(basip)) {
      basip = cbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    if ((!basConfig.getBas().equals("5")) && 
      (!basConfig.getBas().equals("6")) && 
      (!basConfig.getBas().equals("7")) && 
      (!basConfig
      .getBas().equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型不匹配！！");
      return map;
    }
    if (stringUtils.isBlank(ikmac)) {
      map.put("ret", "10");
      map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
      return map;
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      String[] userinfo = (String[])onlineMap.getOnlineUserMap().get(
        ip + ":" + basip);
      usr = userinfo[0];
      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", usr);
      map.put("msg", "你已经通过认证，请不要重复刷新 ！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("0")) && 
      (stringUtils.isBlank(usr)) && (stringUtils.isBlank(pwd)))
    {
      if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(2L))) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", "一键认证");
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 0) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrl = ba.getUrl();
            if ((stringUtils.isBlank(authUser)) || 
              (stringUtils.isBlank(authPwd))) {
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();
            }
            if (!stringUtils.isBlank(authUrl)) break;
            authUrl = ikweb;

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrl)) {
        authUrl = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }

      Boolean info = Boolean.valueOf(true);

      if (info.booleanValue())
      {
        String[] rosInfo = new String[5];
        rosInfo[0] = "0";
        rosInfo[1] = "2";
        rosInfo[2] = "一键认证";
        rosInfo[3] = authUser;
        rosInfo[4] = authPwd;
        RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);

        session.setAttribute("username", "一键认证");
        session.setAttribute("authUser", authUser);
        session.setAttribute("authPwd", authPwd);
        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);

        map.put("ret", "0");
        map.put("i", ip);
        map.put("u", "一键认证");
        map.put("msg", "认证成功,你可以上网了！！");
        return map;
      }
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", "一键认证");
      map.put("msg", "认证失败，请联系管理员！！");
      return map;
    }

    if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", "");
      map.put("msg", "用户名和密码不能为空！！");
      return map;
    }

    String username = usr;
    String password = pwd;
    if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
      logger.info("Request Auth User: " + username + " IP: " + ip);
    }

    Boolean info = Boolean.valueOf(false);

    String userState = "";
    Long userId = Long.valueOf(0L);
    Date userDate = new Date();
    Long userTime = Long.valueOf(0L);
    Long octets = Long.valueOf(0L);
    PortalbasauthQuery bsq;
    String[] rosInfo;
    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("1"))
    {
      if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", username);
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      Map resultCheck = checkLocalAccount(username, 
        password);
      int state = ((Integer)resultCheck.get("result")).intValue();

      if (state == 0) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", username);
        map.put("msg", "用户名密码错误,或账户已过期！！");
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
        return map;
      }

      if (userState.equals("3")) {
        Date now = new Date();
        if (userDate.getTime() - now.getTime() <= 3600000L) {
          map.put("msg", "账户余额告警，请计时充值！！");
        }
      }
      if ((userState.equals("2")) && 
        (userTime.longValue() <= 3600000L)) {
        map.put("msg", "账户余额告警，请计时充值！！");
      }

      if ((userState.equals("4")) && 
        (octets.longValue() <= 104857600L)) {
        map.put("msg", "账户余额告警，请计时充值！！");
      }

      Portalaccount acc = this.accountService.getPortalaccountByKey(userId);
      String[] loginInfo;
      if (("1".equals(((Portalbas)config.getConfigMap().get(basip)).getIsPortalCheck())) && 
        (acc != null)) {
        Integer limitCount = acc.getMaclimitcount();
        if ((limitCount != null) && (limitCount.intValue() > 0)) {
          int count = 0;
          Iterator iterator = OnlineMap.getInstance()
            .getOnlineUserMap().keySet()
            .iterator();
          while (iterator.hasNext()) {
            Object o = iterator.next();
            String t = o.toString();
            loginInfo = 
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

      bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 1) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrl = ba.getUrl();
            if ((stringUtils.isBlank(authUser)) || 
              (stringUtils.isBlank(authPwd))) {
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();
            }
            if (!stringUtils.isBlank(authUrl)) break;
            authUrl = ikweb;

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrl)) {
        authUrl = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }
      if (!"1".equals(((Portalbas)config.getConfigMap().get(basip)).getIsPortalCheck())) {
        authUser = username;
        authPwd = password;
      }

      info = Boolean.valueOf(true);

      if (info.booleanValue())
      {
        int limitMac = acc.getMaclimit().intValue();
        int limitCount = acc.getMaclimitcount().intValue();
        int auto = acc.getAutologin().intValue();

        String userMac = ikmac;

        if (limitMac == 1) {
          PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
          mq.setAccountId(userId);
          List<Portalaccountmacs> accountmacs = this.macsService
            .getPortalaccountmacsList(mq);
          if ((accountmacs.size() < 1) || (limitCount == 0) || 
            (accountmacs.size() < limitCount))
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
                Portalaccountmacs mac = new Portalaccountmacs();
                mac.setAccountId(userId);
                mac.setMac(userMac);
                this.macsService.addPortalaccountmacs(mac);
              }
            }
          }
          else
          {
            Boolean isHave = Boolean.valueOf(false);
            for (Portalaccountmacs amacs : accountmacs)
            {
              if (amacs.getMac().toLowerCase()
                .equals(userMac)) {
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
              return map;
            }
          }
        }

        if ((auto == 1) && 
          (!stringUtils.isBlank(userMac)) && 
          (!"error"
          .equals(userMac))) {
          PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
          mq.setAccountId(userId);
          List<Portalaccountmacs> accountmacs = this.macsService
            .getPortalaccountmacsList(mq);
          if ((limitCount == 0) || 
            (accountmacs.size() < limitCount)) {
            boolean macNotHave = true;
            for (Portalaccountmacs mac : accountmacs) {
              if (userMac.equals(mac.getMac())) {
                macNotHave = false;
              }
            }
            if (macNotHave) {
              Portalaccountmacs mac = new Portalaccountmacs();
              mac.setAccountId(userId);
              mac.setMac(userMac);
              this.macsService.addPortalaccountmacs(mac);
            }

          }

        }

      }

      if (info.booleanValue()) {
        rosInfo = new String[5];
        rosInfo[0] = "1";
        rosInfo[1] = "3";
        rosInfo[2] = username;
        rosInfo[3] = authUser;
        rosInfo[4] = authPwd;
        RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);

        session.setAttribute("authUser", authUser);
        session.setAttribute("authPwd", authPwd);
      }

    }
    else
    {
      if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", username);
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 2) {
            authUrl = ba.getUrl();
            if (!stringUtils.isBlank(authUrl)) break;
            authUrl = ikweb;

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrl)) {
        authUrl = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
      }

      info = Boolean.valueOf(true);
      if (info.booleanValue()) {
        String userMac = ikmac;
        rosInfo = new String[5];
        rosInfo[0] = "2";
        rosInfo[1] = "4";
        rosInfo[2] = username;
        rosInfo[3] = username;
        rosInfo[4] = password;
        RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
        session.setAttribute("authUser", username);
        session.setAttribute("authPwd", password);

        if (stringUtils.isNotBlank(userMac))
        {
          boolean macHave = false;
          List<Portalaccountmacs> macs = this.macsService
            .getPortalaccountmacsList(new PortalaccountmacsQuery());
          for (Portalaccountmacs mac : macs) {
            if (mac.getMac().equals(userMac)) {
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

    }

    if (info.booleanValue())
    {
      session.setAttribute("password", password);
      Cookie cookiePwd = new Cookie("password", password);
      cookiePwd.setMaxAge(86400);
      response.addCookie(cookiePwd);

      if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
        Cookie cookieMac = new Cookie("mac", ikmac);
        cookieMac.setMaxAge(86400);
        response.addCookie(cookieMac);
        session.setAttribute("ikmac", ikmac);
      }

      session.setAttribute("username", username);
      session.setAttribute("ip", ip);
      session.setAttribute("basip", basip);
      session.setAttribute("ikweb", authUrl);

      Cookie cookieIp = new Cookie("ip", ip);
      cookieIp.setMaxAge(86400);
      response.addCookie(cookieIp);

      Cookie cookieBasIp = new Cookie("basip", basip);
      cookieBasIp.setMaxAge(86400);
      response.addCookie(cookieBasIp);

      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", username);
      return map;
    }

    map.put("ret", "1");
    map.put("i", ip);
    map.put("u", username);
    map.put("msg", "认证失败！");
    return map;
  }

  private int CheckMacTimeLimitMethod(String param, Long id)
  {
    if (Do()) {
      Portalonlinelimit onlinelimit = this.onlinelimitService
        .getPortalonlinelimitByKey(id);
      if (onlinelimit.getState().intValue() == 1) {
        if (onlinelimit.getType().intValue() == 0) {
          if ((stringUtils.isNotBlank(param)) && 
            (!"error".equals(param))) {
            String[] TimeInfo = 
              (String[])MacLimitMap.getInstance()
              .getMacLimitMap().get(param);
            if (TimeInfo != null) {
              Long timepermit = onlinelimit.getTime();
              if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
                return 1;
            }
          }
          else {
            return 2;
          }
        }
        else if (stringUtils.isNotBlank(param)) {
          String[] TimeInfo = 
            (String[])UserLimitMap.getInstance()
            .getUserLimitMap().get(param);
          if (TimeInfo != null) {
            Long timepermit = onlinelimit.getTime();
            if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
              return 1;
          }
        }
        else {
          return 2;
        }
      }

    }

    return 0;
  }

  public static boolean Do() {
    Long isThis = Long.valueOf(new Date().getTime());
    boolean Do = false;
    if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
      Do = true;
    }
    return Do;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxInterFaceWISPrController
 * JD-Core Version:    0.6.2
 */