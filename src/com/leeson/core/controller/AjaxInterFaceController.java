package com.leeson.core.controller;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.api.AccountAPIRequest;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.bean.Portalap;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.query.PortalapQuery;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalphonesQuery;
import com.leeson.core.query.PortalsmsapiQuery;
import com.leeson.core.query.PortalssidQuery;
import com.leeson.core.query.PortalweixinwifiQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountgroupService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalapService;
import com.leeson.core.service.PortalautologinService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.service.PortalonlinelimitService;
import com.leeson.core.service.PortalphonesService;
import com.leeson.core.service.PortalsmsapiService;
import com.leeson.core.service.PortalssidService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.service.PortalweixinwifiService;
import com.leeson.core.utils.Kick;
import com.leeson.core.utils.MyUtils;
import com.leeson.core.utils.SangforRequest;
import com.leeson.core.utils.TwoDimensionCode.makeImg;
import com.leeson.portal.core.controller.IndexAction;
import com.leeson.portal.core.model.AccountAPIMap;
import com.leeson.portal.core.model.AppTokenMap;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.MagicMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.OpenIdMap;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.model.UserLimitMap;
import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
import com.leeson.portal.core.model.WeixinMap;
import com.leeson.portal.core.model.WeixinOpenIDMap;
import com.leeson.portal.core.model.iKuaiMacIpMap;
import com.leeson.portal.core.model.ipMacMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.service.InterfaceControl;
import com.leeson.portal.core.service.action.unifi.UniFiMethod;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxInterFaceController
{

  @Autowired
  private PortallogrecordService logRecordService;

  @Autowired
  private PortalaccountService accountService;

  @Autowired
  private PortallinkrecordService linkRecordService;

  @Autowired
  private PortalweixinwifiService weixinwifiService;

  @Autowired
  private PortalaccountmacsService macsService;

  @Autowired
  PortalbasService basService;

  @Autowired
  private PortalsmsapiService portalsmsapiService;

  @Autowired
  private PortalbasauthService portalbasauthService;

  @Autowired
  private PortalonlinelimitService onlinelimitService;

  @Autowired
  private PortalautologinService autologinService;

  @Autowired
  private PortalapService apService;

  @Autowired
  private PortalssidService ssidService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalwebService webService;

  @Autowired
  private PortalaccountgroupService portalaccountgroupService;

  @Autowired
  private PortalphonesService portalphonesService;
  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

  private static Logger logger = Logger.getLogger(AjaxInterFaceController.class);
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  @ResponseBody
  @RequestMapping({"/ajax_onlineInfo"})
  public Map<String, String[]> onlineInfo(HttpServletRequest request, HttpServletResponse response)
  {
    return onlineMap.getOnlineUserMap();
  }

  @ResponseBody
  @RequestMapping({"/online"})
  public Map<String, Long> online(HttpServletRequest request, HttpServletResponse response)
  {
    HashMap kickMap = new HashMap();
    Iterator iterator = onlineMap.getOnlineUserMap().keySet()
      .iterator();
    while (iterator.hasNext())
      try {
        Object o = iterator.next();
        String host = o.toString();
        String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
        String[] ips = host.split(":");
        String uip = ips[0];
        String time = loginInfo[3];
        Date loginTime = ThreadLocalDateUtil.parse(time);
        String nowString = ThreadLocalDateUtil.format(new Date());
        Date nowTime = ThreadLocalDateUtil.parse(nowString);
        Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
        costTime = Long.valueOf(costTime.longValue() / 1000L);

        kickMap.put(uip, costTime);
      }
      catch (Exception localException) {
      }
    return kickMap;
  }

  @ResponseBody
  @RequestMapping({"/ajax_WeixinOpenIdInfo"})
  public Map<String, String> weixinOpenIdInfo(HttpServletRequest request, HttpServletResponse response)
  {
    return WeixinOpenIDMap.getInstance().getWeixinOpenIDMap();
  }

  @ResponseBody
  @RequestMapping({"/ajaxUI_Login"})
  public Map<String, String> coreLogin(String usr, String pwd, String ip, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    Map map = new HashMap();
    if (stringUtils.isBlank(ip))
    {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", usr);
      map.put("msg", "用户名和密码不能为空！！");
      return map;
    }

    Boolean info = InterfaceControl.Method("PORTAL_LOGIN", usr, pwd, ip, 
      basip, "");

    if (info.booleanValue()) {
      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", usr);
      map.put("msg", "认证成功！");
    }
    else {
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", usr);
      map.put("msg", "认证失败！或者你已经在线！");
    }
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajaxUI_LoginOut"})
  public Map<String, String> coreLoginOut(String ip, String basip, HttpServletRequest request, HttpServletResponse response) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String username = "test";
    String password = "test";

    if (stringUtils.isBlank(ip))
    {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }
    Boolean info = InterfaceControl.Method("PORTAL_LOGINOUT", username, 
      password, ip, basip, "");

    Map map = new HashMap();

    if (info.booleanValue()) {
      map.put("ret", "0");
      map.put("msg", "下线成功！");
    }
    else {
      map.put("ret", "1");
      map.put("msg", "下线失败！请稍后再试！或者你已经离线！");
    }
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_guestHeart"})
  public Map<String, String> ajax_guestHeart(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession();
    String ikweb = (String)session.getAttribute("ikweb");
    String authUrl = ikweb;
    Map map = new HashMap();

    String[] accountInfo = null;
    accountInfo = (String[])onlineMap.getOnlineUserMap().get(ip + ":" + basip);
    if (accountInfo != null) {
      String ssid = (String)session.getAttribute("ssid");
      String apmac = (String)session.getAttribute("apmac");
      String agent = (String)session.getAttribute("agent");
      accountInfo[9] = ip;
      accountInfo[10] = basip;
      accountInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
      accountInfo[12] = ssid;
      accountInfo[13] = apmac;
      accountInfo[14] = "no";
      accountInfo[15] = agent;
      onlineMap.getOnlineUserMap().put(ip + ":" + basip, accountInfo);

      String mac = accountInfo[4];

      UpdateMacTimeLimitMethod(mac, Long.valueOf(8L), session, response);

      String username = accountInfo[0];
      session.setAttribute("username", username);
      session.setAttribute("ip", ip);
      session.setAttribute("basip", basip);
      session.setAttribute("ikmac", mac);

      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 7) {
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

      session.setAttribute("ikweb", authUrl);

      if (stringUtils.isNotBlank(ssid)) {
        try {
          PortalssidQuery apq = new PortalssidQuery();
          apq.setSsid(ssid);
          apq.setSsidLike(false);
          List aps = this.ssidService.getPortalssidList(apq);
          if ((aps != null) && (aps.size() > 0)) {
            Portalssid ap = (Portalssid)aps.get(0);
            ap.setBasip(basip);
            long count = ap.getCount().longValue() + 1L;
            ap.setCount(Long.valueOf(count));
            this.ssidService.updatePortalssidByKey(ap);
          } else {
            Portalssid ap = new Portalssid();
            ap.setSsid(ssid);
            ap.setBasip(basip);
            ap.setCount(Long.valueOf(1L));
            this.ssidService.addPortalssid(ap);
          }
        } catch (Exception e) {
          logger.error("==============ERROR Start=============");
          logger.error(e);
          logger.error("ERROR INFO ", e);
          logger.error("==============ERROR End=============");
        }
      }

      if (stringUtils.isNotBlank(apmac)) {
        try {
          PortalapQuery apq = new PortalapQuery();
          apq.setMac(apmac);
          apq.setMacLike(false);
          List aps = this.apService.getPortalapList(apq);
          if ((aps != null) && (aps.size() > 0)) {
            Portalap ap = (Portalap)aps.get(0);
            ap.setBasip(basip);
            long count = ap.getCount().longValue() + 1L;
            ap.setCount(Long.valueOf(count));
            this.apService.updatePortalapByKey(ap);
          } else {
            Portalap ap = new Portalap();
            ap.setMac(apmac);
            ap.setBasip(basip);
            ap.setCount(Long.valueOf(1L));
            this.apService.addPortalap(ap);
          }
        } catch (Exception e) {
          logger.error("==============ERROR Start=============");
          logger.error(e);
          logger.error("ERROR INFO ", e);
          logger.error("==============ERROR End=============");
        }
      }

      SangforLogin(ip, username, mac, apmac, basip);

      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", username);
      map.put("w", authUrl);
      map.put("msg", "你已经通过认证！！");
      return map;
    }
    map.put("ret", "1");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_guest"})
  public Map<String, String> guest(String ip, String basip, String umac, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();

    String ikmac = umac;
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }
    String site = (String)session.getAttribute("site");
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
          com.leeson.core.bean.Config config = this.configService
            .getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService
          .getConfigByKey(Long.valueOf(1L));
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
    if (stringUtils.isBlank(ip))
    {
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
    if (config.getConfigMap().get(basip) == null) {
      basip = basConfig.getBasIp();
    }

    String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
    if (checkResult != "") {
      map.put("ret", "1");
      map.put("msg", checkResult);
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }
      if (stringUtils.isBlank(site)) {
        map.put("ret", "10");
        map.put("msg", "获取Site信息失败，请关闭浏览器重试！！");
        return map;
      }

    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        map.put("ret", "119");
        map.put("i", ip);
        map.put("u", "访客认证");
        map.put("msg", "你已经通过认证,请不要重复刷新！！");
        return map;
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      map.put("ret", "110");
      map.put("msg", "网络暂时不可用，请稍后再试！！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
      String basePath = request.getScheme() + "://" + 
        request.getServerName() + ":" + request.getServerPort() + 
        request.getContextPath() + "/";
      String guestAuthPath = basePath + "ajax_guestScan.action?ip=" + ip + 
        "&basip=" + basip + "&mac=" + ikmac + "&site=" + site;

      String logoPath = request.getServletContext().getRealPath("/");
      String outPath = logoPath + "ExcelOut/";
      File dir = new File(outPath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      Date now = new Date();
      String nowString = ThreadLocalDateUtil.format(now);
      String temp = UUID.randomUUID().toString().replace("-", "");
      outPath = outPath + temp + nowString + ".jpg";
      logoPath = logoPath + "distogoS.png";
      int result = makeImg.make(guestAuthPath, logoPath, outPath);
      if (result == 1) {
        String imgUrl = basePath + "ExcelOut/" + temp + nowString + 
          ".jpg";
        map.put("ret", "0");
        map.put("url", imgUrl);
        map.put("ip", ip);
        map.put("basip", basip);
        map.put("mac", ikmac);
        map.put("site", site);
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

  @RequestMapping({"/ajax_guestScan"})
  public String guestScan(String ip, String basip, String mac, String site, ModelMap model, HttpServletRequest request, HttpServletResponse response)
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
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        model.addAttribute("msg", "该用户已经认证！！");
        model.addAttribute("ip", ip);
        model.addAttribute("basip", basip);
        model.addAttribute("mac", mac);
        return "guest/result";
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
      model.addAttribute("site", site);
      model.addAttribute("msg", "请员工账号授权！！");
      return "guest/auth";
    }
    model.addAttribute("msg", "系统不是该验证模式，请联系管理员！！");
    model.addAttribute("ip", ip);
    model.addAttribute("basip", basip);
    model.addAttribute("mac", mac);
    return "guest/result";
  }

  @RequestMapping({"/ajax_guestAuth"})
  public String guestAuth(String usr, String pwd, String ip, String basip, String mac, String site, ModelMap model, HttpServletRequest request, HttpServletResponse response)
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
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        model.addAttribute("msg", "该用户已经认证！！");
        model.addAttribute("ip", ip);
        model.addAttribute("basip", basip);
        model.addAttribute("mac", mac);
        return "guest/result";
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      model.addAttribute("msg", "认证失败！！");
      model.addAttribute("ip", ip);
      model.addAttribute("basip", basip);
      model.addAttribute("mac", mac);
    }

    if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7"))
    {
      String accountIP = GetNgnixRemotIP.getRemoteAddrIp(request);
      String[] accountInfo = null;

      accountInfo = (String[])onlineMap.getOnlineUserMap().get(
        accountIP + ":" + basip);

      Iterator iterator = OnlineMap.getInstance()
        .getOnlineUserMap().keySet().iterator();
      while (iterator.hasNext()) {
        Object o = iterator.next();
        String t = o.toString();
        if (t.contains(accountIP + ":")) {
          accountInfo = 
            (String[])OnlineMap.getInstance().getOnlineUserMap()
            .get(t);
          break;
        }
      }

      if (accountInfo != null) {
        String username = accountInfo[0];
        try
        {
          String state = accountInfo[5];
          if (!"ok".equals(state)) {
            model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
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
              if ((!stringUtils.isBlank(authUser)) && 
                (!stringUtils.isBlank(authPwd))) break;
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();

              break;
            }
          }
        }

        Boolean info = Boolean.valueOf(false);

        if (((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("3")) {
          if ((stringUtils.isBlank(site)) || (stringUtils.isBlank(mac))) {
            model.addAttribute("msg", "获取参数信息失败！！");
            model.addAttribute("ip", ip);
            model.addAttribute("basip", basip);
            model.addAttribute("mac", mac);
            return "guest/result";
          }
          PortalaccountQuery aq = new PortalaccountQuery();
          aq.setLoginName(authUser);
          aq.setLoginNameLike(false);
          List accs = this.accountService
            .getPortalaccountList(aq);
          int accTime = 1440;
          if (accs.size() == 1) {
            long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
            if (accTimeLong > 0L) {
              accTime = (int)accTimeLong;
            }
          }
          info = Boolean.valueOf(UniFiMethod.sendAuthorization(mac, accTime, site, 
            basip));
        } else {
          info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
            authPwd, ip, basip, mac);
        }

        if (info.booleanValue()) {
          String accountAPI_Url = 
            (String)AccountAPIMap.getInstance()
            .getAccountAPIMap().get("publicurl");
          String accountAPI_State = 
            (String)AccountAPIMap.getInstance()
            .getAccountAPIMap().get("publicstate");
          if ((stringUtils.isNotBlank(accountAPI_Url)) && 
            (stringUtils.isNotBlank(accountAPI_State)) && 
            ("1".equals(accountAPI_State))) {
            HttpSession session = request.getSession();
            String agent = (String)session.getAttribute("agent");
            AccountAPIRequest.guestSend(accountAPI_Url, username, 
              ip, mac, agent);
          }

          username = username + "授权";

          if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
            mac = 
              (String)ipMacMap.getInstance().getIpMacMap()
              .get(ip + ":" + basip);
          }

          AutoLoginPutMethod(mac, Long.valueOf(8L), authUser, authPwd, username + 
            "(无感知)");

          String[] loginInfo = new String[16];

          loginInfo[0] = username;
          Date now = new Date();
          loginInfo[3] = ThreadLocalDateUtil.format(now);
          loginInfo[4] = mac;
          loginInfo[6] = "7";
          loginInfo[7] = "0";
          loginInfo[8] = "0";

          loginInfo[9] = ip;
          loginInfo[10] = basip;
          loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip))
            .getBasname();
          loginInfo[12] = "";
          loginInfo[13] = "";
          loginInfo[14] = "no";
          loginInfo[15] = "";

          onlineMap.getOnlineUserMap().put(ip + ":" + basip, 
            loginInfo);
          Portallogrecord logRecord = new Portallogrecord();
          logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + 
            mac + " 用户: " + username + ",登录成功!");
          logRecord.setRecDate(now);
          this.logRecordService.addPortallogrecord(logRecord);

          model.addAttribute("ip", ip);
          model.addAttribute("basip", basip);
          model.addAttribute("mac", mac);
          model.addAttribute("msg", "认证成功，访客可以上网了！！");
          return "guest/result";
        }

        model.addAttribute("msg", "网络暂时不可用，请稍后再试或者联系管理员！！");
        model.addAttribute("ip", ip);
        model.addAttribute("basip", basip);
        model.addAttribute("mac", mac);
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
  @RequestMapping({"/ajax_sms"})
  public Map<String, String> sms(String ip, String phone, String yzm, String name, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
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
          com.leeson.core.bean.Config config = this.configService
            .getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService
          .getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1)
    {
    }
    if (stringUtils.isBlank(name)) {
      name = "";
    } else {
      name = URLDecoder.decode(name.trim());
      name = StringEscapeUtils.unescapeHtml(name);
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

    if (1 == this.configService.getConfigByKey(Long.valueOf(1L)).getSmsAuthList().intValue()) {
      PortalphonesQuery phonesQuery = new PortalphonesQuery();
      phonesQuery.setPhone(phone);
      phonesQuery.setPhoneLike(false);
      int isCanAuth = this.portalphonesService.getPortalphonesCount(phonesQuery).intValue();
      if (isCanAuth <= 0) {
        map.put("ret", "1");
        map.put("msg", "当前手机号未授权，请联系管理员！");
        return map;
      }
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

    String ikmac = umac;
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }

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
    if (stringUtils.isBlank(ip))
    {
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
    if (config.getConfigMap().get(basip) == null) {
      basip = basConfig.getBasIp();
    }

    String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
    if (checkResult != "") {
      map.put("ret", "1");
      map.put("msg", checkResult);
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        map.put("ret", "119");
        map.put("i", ip);
        map.put("u", name + phone);
        map.put("msg", "你已经通过认证,请不要重复刷新！！");
        return map;
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      map.put("ret", "110");
      map.put("msg", "网络暂时不可用，请稍后再试！！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
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
        map.put("u", name + phone);
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      Boolean info = Boolean.valueOf(false);

      if (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("3")) {
        String site = (String)session.getAttribute("site");
        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginName(authUser);
        aq.setLoginNameLike(false);
        List accs = this.accountService
          .getPortalaccountList(aq);
        int accTime = 1440;
        if (accs.size() == 1) {
          long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
          if (accTimeLong > 0L) {
            accTime = (int)accTimeLong;
          }
        }
        info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
          basip));
      } else {
        info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
          authPwd, ip, basip, ikmac);
      }

      if (info.booleanValue())
      {
        if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
          ikmac = 
            (String)ipMacMap.getInstance().getIpMacMap()
            .get(ip + ":" + basip);
        }
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(1L), session, authUser, authPwd, 
          name + phone, response);

        AutoLoginPutMethod(ikmac, Long.valueOf(1L), authUser, authPwd, name + phone + 
          "(无感知)");

        session.setAttribute("username", name + phone);

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikmac", ikmac);

        if (stringUtils.isEmpty(ssid)) {
          ssid = (String)session.getAttribute("ssid");
        }
        if (stringUtils.isEmpty(apmac)) {
          apmac = (String)session.getAttribute("apmac");
        }
        String[] loginInfo = new String[16];
        loginInfo[0] = (name + phone);
        Date now = new Date();
        loginInfo[3] = ThreadLocalDateUtil.format(now);
        loginInfo[4] = ikmac;
        loginInfo[6] = "4";
        loginInfo[7] = "0";
        loginInfo[8] = "0";

        loginInfo[9] = ip;
        loginInfo[10] = basip;
        loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
        loginInfo[12] = ssid;
        loginInfo[13] = apmac;
        loginInfo[14] = "no";
        loginInfo[15] = getAgent(request);

        onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
        Portallogrecord logRecord = new Portallogrecord();
        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " 用户: " + name + phone + ",登录成功!");
        logRecord.setRecDate(now);
        this.logRecordService.addPortallogrecord(logRecord);

        String more = smsapi.getMore();
        if ("0".equals(more)) {
          PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        }
        session.setAttribute("ikweb", authUrl);

        if (stringUtils.isNotBlank(ssid)) {
          try {
            PortalssidQuery apq = new PortalssidQuery();
            apq.setSsid(ssid);
            apq.setSsidLike(false);
            List aps = this.ssidService
              .getPortalssidList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalssid ap = (Portalssid)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.ssidService.updatePortalssidByKey(ap);
            } else {
              Portalssid ap = new Portalssid();
              ap.setSsid(ssid);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.ssidService.addPortalssid(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }
        if (stringUtils.isNotBlank(apmac)) {
          try {
            PortalapQuery apq = new PortalapQuery();
            apq.setMac(apmac);
            apq.setMacLike(false);
            List aps = this.apService.getPortalapList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalap ap = (Portalap)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.apService.updatePortalapByKey(ap);
            } else {
              Portalap ap = new Portalap();
              ap.setMac(apmac);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.apService.addPortalap(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }

        SangforLogin(ip, name + phone, ikmac, apmac, basip);

        map.put("ret", "0");
        map.put("i", ip);
        map.put("u", name + phone);
        map.put("w", authUrl);
        map.put("msg", "认证成功！");
        return map;
      }
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", name + phone);
      map.put("msg", "网络暂时不可用，请联系管理员！！");
      return map;
    }

    map.put("ret", "3");
    map.put("i", ip);
    map.put("u", name + phone);
    map.put("msg", "系统不是该验证模式，请联系管理员！！");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/check_weixin"})
  public Map<String, String> check_weixin(HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession();
    String ip = (String)session.getAttribute("ip");
    String basip = (String)session.getAttribute("basip");

    Map map = new HashMap();
    if ((stringUtils.isBlank(basip)) || (stringUtils.isBlank(ip))) {
      map.put("ret", "1");
      return map;
    }
    try {
      String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
        ip + ":" + basip);
      Date wxt = 
        (Date)WeixinMap.getInstance().getWeixinipmap()
        .get(ip + ":" + basip);
      if ((loginInfo != null) && (wxt == null)) {
        session.setAttribute("username", "微信认证");
        session.setAttribute("ip", loginInfo[9]);
        session.setAttribute("basip", loginInfo[10]);
        session.setAttribute("ikmac", loginInfo[4]);
        map.put("ret", "0");
        return map;
      }
    }
    catch (Exception localException) {
      map.put("ret", "1");
    }return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_gzh"})
  public Map<String, String> gzh(String ip, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
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
          com.leeson.core.bean.Config config = this.configService
            .getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService
          .getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1)
    {
    }
    String ikmac = umac;
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }
    if (stringUtils.isEmpty(ssid)) {
      ssid = (String)session.getAttribute("ssid");
    }
    if (stringUtils.isEmpty(apmac)) {
      apmac = (String)session.getAttribute("apmac");
    }
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
    if (stringUtils.isBlank(ip))
    {
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
    if (config.getConfigMap().get(basip) == null) {
      basip = basConfig.getBasIp();
    }

    String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
    if (checkResult != "") {
      map.put("ret", "1");
      map.put("msg", checkResult);
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
      logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
        ip + " mac=" + ikmac);
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        map.put("ret", "119");
        map.put("msg", "你已经通过验证,或者下线后重试！！");
        return map;
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      map.put("ret", "110");
      map.put("msg", "网络暂时不可用，请稍后再试！！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
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
        map.put("u", "公众号认证");
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

      Boolean info = Boolean.valueOf(false);

      if (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("3")) {
        String site = (String)session.getAttribute("site");
        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginName(authUser);
        aq.setLoginNameLike(false);
        List accs = this.accountService
          .getPortalaccountList(aq);
        int accTime = 1440;
        if (accs.size() == 1) {
          long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
          if (accTimeLong > 0L) {
            accTime = (int)accTimeLong;
          }
        }
        info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
          basip));
      } else {
        info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
          authPwd, ip, basip, ikmac);
      }

      if (info.booleanValue())
      {
        if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
          ikmac = 
            (String)ipMacMap.getInstance().getIpMacMap()
            .get(ip + ":" + basip);
        }
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(7L), request.getSession(), 
          authUser, authPwd, "公众号认证", response);

        session.setAttribute("username", "公众号认证");

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikweb", authUrlWeb);
        session.setAttribute("ikmac", ikmac);

        String[] loginInfo = new String[16];
        loginInfo[0] = "公众号认证-临时放行";
        Date now = new Date();
        loginInfo[3] = ThreadLocalDateUtil.format(now);
        loginInfo[4] = ikmac;
        loginInfo[6] = "6";
        loginInfo[7] = "0";
        loginInfo[8] = "0";

        loginInfo[9] = ip;
        loginInfo[10] = basip;
        loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
        loginInfo[12] = ssid;
        loginInfo[13] = apmac;
        loginInfo[14] = "no";
        loginInfo[15] = getAgent(request);

        onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
        WeixinMap.getInstance().getWeixinipmap()
          .put(ip + ":" + basip, now);

        Portallogrecord logRecord = new Portallogrecord();

        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " 用户: 【公众号认证-临时放行】,登录成功!");
        logRecord.setRecDate(now);
        this.logRecordService.addPortallogrecord(logRecord);

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
          String[] magicInfo = new String[3];
          magicInfo[0] = (ip + ":" + basip);
          magicInfo[1] = "";
          magicInfo[2] = ikmac;
          MagicMap.getInstance().getMagicMap()
            .put(ip + ":" + basip, magicInfo);

          String appId = wifi.getAppId();
          String extend = ip + ":" + basip;
          String timestamp = String.valueOf(time);
          String shop_id = wifi.getShopId();

          String authUrl = request.getScheme() + "://" + 
            request.getServerName() + ":" + 
            request.getServerPort() + 
            request.getContextPath() + "/gzhAuth.action";
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

          if (stringUtils.isNotBlank(ssid)) {
            try {
              PortalssidQuery apq = new PortalssidQuery();
              apq.setSsid(ssid);
              apq.setSsidLike(false);
              List aps = this.ssidService
                .getPortalssidList(apq);
              if ((aps != null) && (aps.size() > 0)) {
                Portalssid ap = (Portalssid)aps.get(0);
                ap.setBasip(basip);
                long count = ap.getCount().longValue() + 1L;
                ap.setCount(Long.valueOf(count));
                this.ssidService.updatePortalssidByKey(ap);
              } else {
                Portalssid ap = new Portalssid();
                ap.setSsid(ssid);
                ap.setBasip(basip);
                ap.setCount(Long.valueOf(1L));
                this.ssidService.addPortalssid(ap);
              }
            } catch (Exception e) {
              logger.error("==============ERROR Start=============");
              logger.error(e);
              logger.error("ERROR INFO ", e);
              logger.error("==============ERROR End=============");
            }
          }
          if (stringUtils.isNotBlank(apmac)) {
            try {
              PortalapQuery apq = new PortalapQuery();
              apq.setMac(apmac);
              apq.setMacLike(false);
              List aps = this.apService.getPortalapList(apq);
              if ((aps != null) && (aps.size() > 0)) {
                Portalap ap = (Portalap)aps.get(0);
                ap.setBasip(basip);
                long count = ap.getCount().longValue() + 1L;
                ap.setCount(Long.valueOf(count));
                this.apService.updatePortalapByKey(ap);
              } else {
                Portalap ap = new Portalap();
                ap.setMac(apmac);
                ap.setBasip(basip);
                ap.setCount(Long.valueOf(1L));
                this.apService.addPortalap(ap);
              }
            } catch (Exception e) {
              logger.error("==============ERROR Start=============");
              logger.error(e);
              logger.error("ERROR INFO ", e);
              logger.error("==============ERROR End=============");
            }
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
          return map;
        }
        map.put("ret", "1");
        map.put("msg", "公众号认证参数未配置，请联系管理员！！");
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

  @RequestMapping({"/gzhAuth.action"})
  public void gzhAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
    throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
        " openId=" + openId + " tid=" + tid);
    }

    String[] magicInfo = (String[])MagicMap.getInstance().getMagicMap().get(extend);
    if ((magicInfo != null) && (magicInfo.length >= 3)) {
      OpenIdMap.getInstance().getOpenIdMap().put(openId, magicInfo);
      MagicMap.getInstance().getMagicMap().remove(extend);

      String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
      if ((userInfo != null) && (userInfo.length > 0)) {
        String username = openId;
        userInfo[0] = username;
        onlineMap.getOnlineUserMap().put(extend, userInfo);
      }

    }

    String respMessage = "HTTP/1.0 200 OK\r\n";
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
    WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
  }

  @ResponseBody
  @RequestMapping({"/ajax_weixin"})
  public Map<String, String> weixin(String ip, String basip, String umac, String ssid, String apmac, String htmlUrl, HttpServletRequest request, HttpServletResponse response)
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
          com.leeson.core.bean.Config config = this.configService
            .getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService
          .getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1)
    {
    }
    String ikmac = umac;
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }
    if (stringUtils.isEmpty(ssid)) {
      ssid = (String)session.getAttribute("ssid");
    }
    if (stringUtils.isEmpty(apmac)) {
      apmac = (String)session.getAttribute("apmac");
    }
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
    if (stringUtils.isBlank(ip))
    {
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
    if (config.getConfigMap().get(basip) == null) {
      basip = basConfig.getBasIp();
    }

    String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
    if (checkResult != "") {
      map.put("ret", "1");
      map.put("msg", checkResult);
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
      logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
        ip + " mac=" + ikmac);
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        map.put("ret", "119");
        map.put("msg", "你已经通过验证,或者下线后重试！！");
        return map;
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      map.put("ret", "110");
      map.put("msg", "网络暂时不可用，请稍后再试！！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
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

      Boolean info = Boolean.valueOf(false);

      if (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("3")) {
        String site = (String)session.getAttribute("site");
        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginName(authUser);
        aq.setLoginNameLike(false);
        List accs = this.accountService
          .getPortalaccountList(aq);
        int accTime = 1440;
        if (accs.size() == 1) {
          long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
          if (accTimeLong > 0L) {
            accTime = (int)accTimeLong;
          }
        }
        info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
          basip));
      } else {
        info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
          authPwd, ip, basip, ikmac);
      }

      if (info.booleanValue())
      {
        if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
          ikmac = 
            (String)ipMacMap.getInstance().getIpMacMap()
            .get(ip + ":" + basip);
        }
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(6L), request.getSession(), 
          authUser, authPwd, "微信验证", response);

        session.setAttribute("username", "微信验证");

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikweb", authUrlWeb);
        session.setAttribute("ikmac", ikmac);

        String[] loginInfo = new String[16];
        loginInfo[0] = "微信验证-临时放行";
        Date now = new Date();
        loginInfo[3] = ThreadLocalDateUtil.format(now);
        loginInfo[4] = ikmac;
        loginInfo[6] = "5";
        loginInfo[7] = "0";
        loginInfo[8] = "0";

        loginInfo[9] = ip;
        loginInfo[10] = basip;
        loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
        loginInfo[12] = ssid;
        loginInfo[13] = apmac;
        loginInfo[14] = "no";
        loginInfo[15] = getAgent(request);

        onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
        WeixinMap.getInstance().getWeixinipmap()
          .put(ip + ":" + basip, now);

        Portallogrecord logRecord = new Portallogrecord();

        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " 用户: 【微信验证-临时放行】,登录成功!");
        logRecord.setRecDate(now);
        this.logRecordService.addPortallogrecord(logRecord);

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
            request.getServerPort() + 
            request.getContextPath() + "/weixinAuth.action";

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
            if (stringUtils.isNotBlank(htmlUrl))
              authUrl = htmlUrl + "weixinPCAuth.html";
            else {
              authUrl = request.getScheme() + "://" + 
                request.getServerName() + ":" + 
                request.getServerPort() + 
                request.getContextPath() + 
                "/weixinPCAuth.action";
            }
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

          if (stringUtils.isNotBlank(ssid)) {
            try {
              PortalssidQuery apq = new PortalssidQuery();
              apq.setSsid(ssid);
              apq.setSsidLike(false);
              List aps = this.ssidService
                .getPortalssidList(apq);
              if ((aps != null) && (aps.size() > 0)) {
                Portalssid ap = (Portalssid)aps.get(0);
                ap.setBasip(basip);
                long count = ap.getCount().longValue() + 1L;
                ap.setCount(Long.valueOf(count));
                this.ssidService.updatePortalssidByKey(ap);
              } else {
                Portalssid ap = new Portalssid();
                ap.setSsid(ssid);
                ap.setBasip(basip);
                ap.setCount(Long.valueOf(1L));
                this.ssidService.addPortalssid(ap);
              }
            } catch (Exception e) {
              logger.error("==============ERROR Start=============");
              logger.error(e);
              logger.error("ERROR INFO ", e);
              logger.error("==============ERROR End=============");
            }
          }
          if (stringUtils.isNotBlank(apmac)) {
            try {
              PortalapQuery apq = new PortalapQuery();
              apq.setMac(apmac);
              apq.setMacLike(false);
              List aps = this.apService.getPortalapList(apq);
              if ((aps != null) && (aps.size() > 0)) {
                Portalap ap = (Portalap)aps.get(0);
                ap.setBasip(basip);
                long count = ap.getCount().longValue() + 1L;
                ap.setCount(Long.valueOf(count));
                this.apService.updatePortalapByKey(ap);
              } else {
                Portalap ap = new Portalap();
                ap.setMac(apmac);
                ap.setBasip(basip);
                ap.setCount(Long.valueOf(1L));
                this.apService.addPortalap(ap);
              }
            } catch (Exception e) {
              logger.error("==============ERROR Start=============");
              logger.error(e);
              logger.error("ERROR INFO ", e);
              logger.error("==============ERROR End=============");
            }
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

  @RequestMapping({"/weixinAuth.action"})
  public void weixinAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
    throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
        " openId=" + openId + " tid=" + tid);
    }

    WeixinMap.getInstance().getWeixinipmap().remove(extend);
    WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap()
      .remove(extend);
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("IP:" + extend + 
        "Weixin Auth Success , Remove WeixinTempMap !!");
    }
    String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
    if ((userInfo != null) && (userInfo.length > 0)) {
      userInfo[0] = openId;
      onlineMap.getOnlineUserMap().put(extend, userInfo);
      String mac = userInfo[4];
      String basip = "";
      String ip = "";
      try {
        int i = extend.lastIndexOf(":");
        basip = extend.substring(i + 1);
        ip = extend.substring(0, i);
      }
      catch (Exception localException)
      {
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
            if ((!stringUtils.isBlank(authUser)) && 
              (!stringUtils.isBlank(authPwd))) break;
            authUser = ((Portalbas)config.getConfigMap().get(basip))
              .getBasUser();
            authPwd = ((Portalbas)config.getConfigMap().get(basip))
              .getBasPwd();

            break;
          }
        }
      }

      if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
        PortalbasauthQuery baq = new PortalbasauthQuery();
        baq.setBasid(Long.valueOf(0L));
        basauths = this.portalbasauthService.getPortalbasauthList(baq);
        if (basauths.size() > 0) {
          for (Portalbasauth ba : basauths) {
            if (ba.getType().intValue() == 5) {
              authUser = ba.getUsername();
              authPwd = ba.getPassword();
              break;
            }
          }
        }
      }
      if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
        authUser = basConfig.getBasUser();
        authPwd = basConfig.getBasPwd();
      }
      AutoLoginPutMethod(mac, Long.valueOf(6L), authUser, authPwd, userInfo[0]);

      SangforLogin(ip, userInfo[0], mac, "", basip);
    }

    String respMessage = "HTTP/1.0 200 OK\r\n";
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
    WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
  }

  @ResponseBody
  @RequestMapping({"/weixinHtmlPCAuth.action"})
  public Map<String, String> weixinHtmlPCAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
    throws IOException
  {
    extend = URLDecoder.decode(extend);
    Map map = new HashMap();
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
        " openId=" + openId + " tid=" + tid);
    }

    WeixinMap.getInstance().getWeixinipmap().remove(extend);
    WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap()
      .remove(extend);
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("IP:" + extend + 
        "Weixin Auth Success , Remove WeixinTempMap !!");
    }
    HttpSession session = request.getSession();
    String ikweb = (String)session.getAttribute("ikweb");
    String username = openId;
    String basip = "";
    String authUrlWeb = "";
    String ip = "";
    try {
      int i = extend.lastIndexOf(":");
      basip = extend.substring(i + 1);
      ip = extend.substring(0, i);
    }
    catch (Exception localException)
    {
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
            authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
          }
          if (!stringUtils.isBlank(authUrlWeb)) break;
          authUrlWeb = ikweb;

          break;
        }
      }
    }

    if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
      PortalbasauthQuery baq = new PortalbasauthQuery();
      baq.setBasid(Long.valueOf(0L));
      basauths = this.portalbasauthService.getPortalbasauthList(baq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 5) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrlWeb = ba.getUrl();
            if (!stringUtils.isBlank(authUrlWeb)) break;
            authUrlWeb = ikweb;

            break;
          }
        }
      }
    }
    if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
      authUser = basConfig.getBasUser();
      authPwd = basConfig.getBasPwd();
    }

    if (stringUtils.isBlank(authUrlWeb)) {
      authUrlWeb = 
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[0];
    }
    ikweb = authUrlWeb;

    String mac = "";
    String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
    if ((userInfo != null) && (userInfo.length > 0)) {
      userInfo[0] = username;

      onlineMap.getOnlineUserMap().put(extend, userInfo);
      mac = userInfo[4];
      AutoLoginPutMethod(mac, Long.valueOf(6L), authUser, authPwd, userInfo[0]);

      String apmac = (String)session.getAttribute("apmac");
      SangforLogin(ip, userInfo[0], mac, apmac, basip);
    }
    WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
    String webMod = (String)session.getAttribute("web");
    session.setAttribute("ip", ip);
    session.setAttribute("basip", basip);
    session.setAttribute("ikweb", ikweb);
    session.setAttribute("username", "微信认证");

    if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
      mac = (String)ipMacMap.getInstance().getIpMacMap().get(ip + ":" + basip);
    }
    if (stringUtils.isNotBlank(mac)) {
      session.setAttribute("ikmac", mac);
    }

    if (stringUtils.isEmpty(webMod)) {
      webMod = "";
    }
    session.setAttribute("web", webMod);

    map.put("ret", "0");
    map.put("i", ip);
    map.put("u", "微信认证");
    map.put("w", ikweb);
    map.put("msg", "认证成功！");
    return map;
  }

  @RequestMapping({"/weixinPCAuth.action"})
  public void weixinPCAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
    throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
        " openId=" + openId + " tid=" + tid);
    }

    WeixinMap.getInstance().getWeixinipmap().remove(extend);
    WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap()
      .remove(extend);
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("IP:" + extend + 
        "Weixin Auth Success , Remove WeixinTempMap !!");
    }
    HttpSession session = request.getSession();
    String ikweb = (String)session.getAttribute("ikweb");
    String username = openId;
    String basip = "";
    String authUrlWeb = "";
    String ip = "";
    try {
      int i = extend.lastIndexOf(":");
      basip = extend.substring(i + 1);
      ip = extend.substring(0, i);
    }
    catch (Exception localException)
    {
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
            authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
          }
          if (!stringUtils.isBlank(authUrlWeb)) break;
          authUrlWeb = ikweb;

          break;
        }
      }
    }

    if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
      PortalbasauthQuery baq = new PortalbasauthQuery();
      baq.setBasid(Long.valueOf(0L));
      basauths = this.portalbasauthService.getPortalbasauthList(baq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 5) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrlWeb = ba.getUrl();
            if (!stringUtils.isBlank(authUrlWeb)) break;
            authUrlWeb = ikweb;

            break;
          }
        }
      }
    }
    if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
      authUser = basConfig.getBasUser();
      authPwd = basConfig.getBasPwd();
    }

    if (stringUtils.isBlank(authUrlWeb)) {
      authUrlWeb = 
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[0];
    }
    ikweb = authUrlWeb;

    String mac = "";
    String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
    if ((userInfo != null) && (userInfo.length > 0)) {
      userInfo[0] = username;

      onlineMap.getOnlineUserMap().put(extend, userInfo);
      mac = userInfo[4];
      AutoLoginPutMethod(mac, Long.valueOf(6L), authUser, authPwd, userInfo[0]);

      String apmac = (String)session.getAttribute("apmac");
      SangforLogin(ip, userInfo[0], mac, apmac, basip);
    }
    WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
    String webMod = (String)session.getAttribute("web");
    session.setAttribute("ip", ip);
    session.setAttribute("basip", basip);
    session.setAttribute("ikweb", ikweb);
    session.setAttribute("username", "微信认证");

    if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
      mac = (String)ipMacMap.getInstance().getIpMacMap().get(ip + ":" + basip);
    }
    if (stringUtils.isNotBlank(mac)) {
      session.setAttribute("ikmac", mac);
    }

    if (stringUtils.isEmpty(webMod)) {
      webMod = "";
    }
    session.setAttribute("web", webMod);
    try
    {
      String api = (String)session.getAttribute("api");
      if ((api != null) && (!"default".equals(api)))
        request.getRequestDispatcher("/" + webMod + "APIok.jsp")
          .forward(request, response);
      else
        request.getRequestDispatcher("/" + webMod + "ok.jsp").forward(
          request, response);
    }
    catch (ServletException localServletException)
    {
    }
  }

  @ResponseBody
  @RequestMapping({"/ajax_token"})
  public Map<String, String> token(HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();
    String token = MyUtils.creatToken();
    AppTokenMap.getInstance().getTokenMap().put(token, token);

    map.put("token", token);
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_info"})
  public Map<String, String> info(HttpServletResponse response, HttpServletRequest request) throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String filePath = request.getServletContext().getRealPath("/") + 
      "/info.txt";
    String msg = "";
    try {
      String encoding = "utf-8";
      File file = new File(filePath);
      if ((file.isFile()) && (file.exists())) {
        InputStreamReader read = new InputStreamReader(
          new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;

        while ((lineTxt = bufferedReader.readLine()) != null) {
          msg = msg + lineTxt;
        }
        read.close();
      }
      else if (basConfig.getIsdebug().equals("1")) {
        logger.info("版本信息文件info.txt不存在!");
      }
    }
    catch (Exception e) {
      if (basConfig.getIsdebug().equals("1")) {
        logger.info("读取版本信息文件info.txt出错!");
      }
    }
    Map map = new HashMap();
    map.put("msg", msg);
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_app"})
  public Map<String, String> app(String basip, String ip, String sign, String token, HttpServletRequest request, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    String host = ip;
    if (stringUtils.isBlank(ip))
    {
      host = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    String remoteIP = GetNgnixRemotIP.getRemoteAddrIp(request);
    if ("1".equals(basConfig.getIsdebug())) {
      logger.info("================ip: " + ip + " basip: " + basip + 
        " remoteIP: " + remoteIP + " token: " + token + " sign: " + 
        sign + "==============================");
    }

    String bip = basip;
    if (stringUtils.isBlank(basip)) {
      bip = remoteIP;
    }

    Portalbas basconfig = (Portalbas)config.getConfigMap().get(bip);
    if (basconfig == null) {
      bip = remoteIP;
    }
    basconfig = (Portalbas)config.getConfigMap().get(bip);
    if (basconfig == null) {
      bip = basConfig.getBasIp();
    }

    Map map = new HashMap();

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      host + ":" + bip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(bip)).getIsNtf().intValue() == 1) {
        AppTokenMap.getInstance().getTokenMap().remove(token);
        map.put("ret", "0");
        return map;
      }
      Kick.kickUserSyn(host + ":" + bip);
    }

    if ((((Portalbas)config.getConfigMap().get(bip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(bip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(bip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(bip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }

    String trueToken = (String)AppTokenMap.getInstance().getTokenMap().get(token);

    if (!token.equals(trueToken)) {
      AppTokenMap.getInstance().getTokenMap().remove(token);
      map.put("ret", "1");
      return map;
    }

    String code = basip + ip + token;

    String trueSign = DigestUtils.md5Hex(code);

    if (!trueSign.equals(sign)) {
      AppTokenMap.getInstance().getTokenMap().remove(token);
      map.put("ret", "1");
      return map;
    }

    if (basConfig.getAuthInterface().contains("3"))
    {
      if (1 == CheckMacTimeLimitMethod("", Long.valueOf(5L))) {
        map.put("ret", "1");
        return map;
      }

      if (onlineMap.getOnlineUserMap().size() >= 
        Integer.valueOf(
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[1]).intValue())
      {
        map.put("ret", "1");
        return map;
      }

      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(bip);
      String authUser = ((Portalbas)config.getConfigMap().get(bip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(bip)).getBasPwd();
      List<Portalbasauth> basauths = this.portalbasauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 3) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            if ((!stringUtils.isBlank(authUser)) && 
              (!stringUtils.isBlank(authPwd))) break;
            authUser = ((Portalbas)config.getConfigMap().get(bip))
              .getBasUser();
            authPwd = ((Portalbas)config.getConfigMap().get(bip))
              .getBasPwd();

            break;
          }
        }
      }

      Boolean info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
        authPwd, host, bip, "");
      if (info.booleanValue()) {
        String ikmac = "";
        if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
          ikmac = 
            (String)ipMacMap.getInstance().getIpMacMap()
            .get(host + ":" + bip);
        }
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(5L), request.getSession(), 
          authUser, authPwd, "APP认证", response);

        AutoLoginPutMethod(ikmac, Long.valueOf(5L), authUser, authPwd, "APP认证(无感知)");

        HttpSession session = request.getSession();
        session.setAttribute("username", "APP认证");

        session.setAttribute("ip", host);
        session.setAttribute("basip", bip);

        String[] loginInfo = new String[16];
        loginInfo[0] = "APP认证";
        Date now = new Date();
        loginInfo[3] = ThreadLocalDateUtil.format(now);
        loginInfo[4] = ikmac;
        loginInfo[6] = "3";
        loginInfo[7] = "0";
        loginInfo[8] = "0";

        loginInfo[9] = host;
        loginInfo[10] = bip;
        loginInfo[11] = ((Portalbas)config.getConfigMap().get(bip)).getBasname();
        loginInfo[12] = "";
        loginInfo[13] = "";
        loginInfo[14] = "no";
        loginInfo[15] = "Android";

        onlineMap.getOnlineUserMap().put(host + ":" + bip, loginInfo);
        AppTokenMap.getInstance().getTokenMap().remove(token);

        Portallogrecord logRecord = new Portallogrecord();

        logRecord.setInfo("IP: " + host + ":" + bip + " mac: " + ikmac + 
          " 用户: 【APP认证】,登录成功!");
        logRecord.setRecDate(now);
        this.logRecordService.addPortallogrecord(logRecord);

        SangforLogin(host, "APP认证", ikmac, "", basip);

        map.put("ret", "0");
        return map;
      }

      AppTokenMap.getInstance().getTokenMap().remove(token);
      map.put("ret", "1");
      return map;
    }

    AppTokenMap.getInstance().getTokenMap().remove(token);
    map.put("ret", "2");
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

  private Long doLinkRecord(String username, String ip, String basip, String userState, Long userId, String mac)
  {
    Portalbas basconfig = (Portalbas)config.getConfigMap().get(basip);
    if ((basconfig != null) && 
      ("1".equals(basconfig.getIsPortalCheck()))) {
      Portallinkrecord linkRecord = new Portallinkrecord();
      linkRecord.setStartDate(new Date());
      linkRecord.setIp(ip);
      linkRecord.setBasip(basip);
      linkRecord.setMac(mac);
      linkRecord.setIns(Long.valueOf(0L));
      linkRecord.setOuts(Long.valueOf(0L));
      linkRecord.setOctets(Long.valueOf(0L));
      linkRecord.setLoginName(username);
      linkRecord.setState(userState);
      linkRecord.setUid(userId);
      this.linkRecordService.addPortallinkrecord(linkRecord);
      return linkRecord.getId();
    }

    return null;
  }

  @ResponseBody
  @RequestMapping({"/ajax_LoginOut"})
  public Map<String, String> LoginOut(String ip, String basip, String umac, HttpServletRequest request, HttpServletResponse response) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Map map = new HashMap();

    HttpSession session = request.getSession();
    String ikmac = umac;
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
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
    if (stringUtils.isBlank(ip))
    {
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

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
      map.put("ret", "1");
      map.put("msg", "参数获取错误！！");
      return map;
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (!isLogin) {
      map.put("ret", "0");
      map.put("msg", "你已经离线！");
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ikweb");

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

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
      return map;
    }

    String site = "";
    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      site = (String)session.getAttribute("site");
    }

    Kick.offLine(ip + ":" + basip, ikmac, site);

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
  @RequestMapping({"/ajax_Login"})
  public Map<String, String> Login(String usr, String pwd, String ip, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
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
          com.leeson.core.bean.Config config = this.configService
            .getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService
          .getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1)
    {
    }
    String ikmac = umac;
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }
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
    if (stringUtils.isBlank(ip))
    {
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
    if (config.getConfigMap().get(basip) == null) {
      basip = basConfig.getBasIp();
    }

    String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
    if (checkResult != "") {
      map.put("ret", "1");
      map.put("msg", checkResult);
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        String[] userinfo = (String[])onlineMap.getOnlineUserMap().get(
          ip + ":" + basip);
        usr = userinfo[0];
        map.put("ret", "119");
        map.put("i", ip);
        map.put("u", usr);
        map.put("msg", "你已经通过认证，请不要重复刷新 ！");
        return map;
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      map.put("ret", "110");
      map.put("msg", "网络暂时不可用，请稍后再试！！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
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

      Boolean info = Boolean.valueOf(false);

      if (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("3")) {
        String site = (String)session.getAttribute("site");
        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginName(authUser);
        aq.setLoginNameLike(false);
        List accs = this.accountService
          .getPortalaccountList(aq);
        int accTime = 1440;
        if (accs.size() == 1) {
          long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
          if (accTimeLong > 0L) {
            accTime = (int)accTimeLong;
          }
        }
        info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
          basip));
      } else {
        info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
          authPwd, ip, basip, ikmac);
      }

      if (info.booleanValue())
      {
        if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
          ikmac = 
            (String)ipMacMap.getInstance().getIpMacMap()
            .get(ip + ":" + basip);
        }
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(2L), request.getSession(), 
          authUser, authPwd, "", response);

        AutoLoginPutMethod(ikmac, Long.valueOf(2L), authUser, authPwd, "一键认证(无感知)");

        session.setAttribute("username", "一键认证");

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikweb", authUrl);
        session.setAttribute("ikmac", ikmac);

        if (stringUtils.isEmpty(ssid)) {
          ssid = (String)session.getAttribute("ssid");
        }
        if (stringUtils.isEmpty(apmac)) {
          apmac = (String)session.getAttribute("apmac");
        }
        String[] loginInfo = new String[16];
        loginInfo[0] = "一键认证";
        Date now = new Date();
        loginInfo[3] = ThreadLocalDateUtil.format(now);
        loginInfo[4] = ikmac;
        loginInfo[6] = "0";
        loginInfo[7] = "0";
        loginInfo[8] = "0";

        loginInfo[9] = ip;
        loginInfo[10] = basip;
        loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
        loginInfo[12] = ssid;
        loginInfo[13] = apmac;
        loginInfo[14] = "no";
        loginInfo[15] = getAgent(request);

        onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
        Portallogrecord logRecord = new Portallogrecord();

        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " 用户: 【一键认证】,登录成功!");
        logRecord.setRecDate(now);
        this.logRecordService.addPortallogrecord(logRecord);

        if (stringUtils.isNotBlank(ssid)) {
          try {
            PortalssidQuery apq = new PortalssidQuery();
            apq.setSsid(ssid);
            apq.setSsidLike(false);
            List aps = this.ssidService
              .getPortalssidList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalssid ap = (Portalssid)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.ssidService.updatePortalssidByKey(ap);
            } else {
              Portalssid ap = new Portalssid();
              ap.setSsid(ssid);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.ssidService.addPortalssid(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }
        if (stringUtils.isNotBlank(apmac)) {
          try {
            PortalapQuery apq = new PortalapQuery();
            apq.setMac(apmac);
            apq.setMacLike(false);
            List aps = this.apService.getPortalapList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalap ap = (Portalap)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.apService.updatePortalapByKey(ap);
            } else {
              Portalap ap = new Portalap();
              ap.setMac(apmac);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.apService.addPortalap(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }

        SangforLogin(ip, "一键认证", ikmac, apmac, basip);

        map.put("ret", "0");
        map.put("i", ip);
        map.put("u", "一键认证");
        map.put("w", authUrl);
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
    Long recordId = null;

    boolean isRadius = false;
    String authPwd;
    int accTime;
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
            .getOnlineUserMap().keySet().iterator();
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

      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
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

      if (!"1".equals(((Portalbas)config.getConfigMap().get(basip))
        .getIsPortalCheck())) {
        authUser = username;
        authPwd = password;
      }

      if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("1")) || 
        (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("0")))
        info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
          authPwd, ip, basip, ikmac);
      else {
        info = Boolean.valueOf(true);
      }

      if (info.booleanValue())
      {
        int limitMac = acc.getMaclimit().intValue();
        int limitCount = acc.getMaclimitcount().intValue();
        int auto = acc.getAutologin().intValue();

        String userMac = ikmac;

        if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("2")) || 
          (((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("4")) || 
          (((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("3"))) {
          userMac = ikmac;
        } else {
          userMac = 
            (String)ipMacMap.getInstance().getIpMacMap()
            .get(ip + ":" + basip);
          if ((stringUtils.isBlank(userMac)) || 
            ("error".equals(userMac)))
            userMac = ikmac;
          else {
            ikmac = userMac;
          }
        }

        if (limitMac == 1) {
          if ((stringUtils.isBlank(userMac)) || 
            ("error".equals(userMac)))
          {
            if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
              .equals("1")) || 
              (((Portalbas)config.getConfigMap().get(basip))
              .getBas()
              .equals("0"))) {
              InterfaceControl.Method("PORTAL_LOGINOUT", 
                username, "password", ip, basip, "");
            }
            map.put("ret", "1");
            map.put("i", ip);
            map.put("u", username);
            map.put("msg", "协议不支持MAC绑定，请联系管理员修改账户属性！！");
            return map;
          }

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
              if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
                .equals("1")) || 
                (((Portalbas)config.getConfigMap().get(basip))
                .getBas()
                .equals("0")))
              {
                InterfaceControl.Method("PORTAL_LOGINOUT", 
                  username, "password", ip, 
                  basip, "");
              }

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

        if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("2")) || 
          (((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("4"))) {
          info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
            authPwd, ip, basip, ikmac);
        }

        if (((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("3")) {
          String site = (String)session.getAttribute("site");
          accTime = 1440;
          if (userState.equals("3")) {
            Date now = new Date();
            accTime = (int)((userDate.getTime() - now.getTime()) / 60000L);
          }
          if (userState.equals("2")) {
            accTime = (int)(userTime.longValue() / 60000L);
          }

          info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
            basip));
        }
      }

      if (info.booleanValue()) {
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(3L), request.getSession(), 
          response, username);
        AutoLoginPutMethod(ikmac, Long.valueOf(3L), authUser, authPwd, username);
        recordId = doLinkRecord(username, ip, basip, userState, userId, 
          ikmac);
        if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
          String[] userinfo = new String[3];
          userinfo[0] = authUser;
          userinfo[1] = authPwd;
          userinfo[2] = username;
          AutoLoginMap.getInstance().getAutoLoginMap()
            .put(ikmac, userinfo);
        }
      }
    }
    else
    {
      isRadius = true;
      if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))) {
        map.put("ret", "1");
        map.put("i", ip);
        map.put("u", username);
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      String accountAPI_Url = 
        (String)AccountAPIMap.getInstance()
        .getAccountAPIMap().get("url");
      String accountAPI_State = 
        (String)AccountAPIMap.getInstance()
        .getAccountAPIMap().get("state");
      if ((stringUtils.isNotBlank(accountAPI_Url)) && 
        (stringUtils.isNotBlank(accountAPI_State)) && 
        ("1".equals(accountAPI_State))) {
        String agent = (String)session.getAttribute("agent");
        boolean apiResult = AccountAPIRequest.send(accountAPI_Url, 
          username, password, ip, ikmac, agent);
        if (!apiResult) {
          map.put("ret", "1");
          map.put("i", ip);
          map.put("u", username);
          map.put("msg", "用户名或密码错误！");
          return map;
        }

      }

      PortalbasauthQuery bsq = new PortalbasauthQuery();
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

      if (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("3")) {
        String site = (String)session.getAttribute("site");
        info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, 1440, site, 
          basip));
      } else {
        info = InterfaceControl.Method("PORTAL_LOGIN", username, 
          password, ip, basip, ikmac);
      }
      if (info.booleanValue()) {
        String userMac = ikmac;

        if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("2")) || 
          (((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("4")) || 
          (((Portalbas)config.getConfigMap().get(basip)).getBas()
          .equals("3"))) {
          userMac = ikmac;
        } else {
          userMac = 
            (String)ipMacMap.getInstance().getIpMacMap()
            .get(ip + ":" + basip);
          if ((stringUtils.isBlank(userMac)) || 
            ("error".equals(userMac)))
            userMac = ikmac;
          else {
            ikmac = userMac;
          }

        }

        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(4L), request.getSession(), 
          response, username);
        AutoLoginPutMethod(ikmac, Long.valueOf(4L), username, password, username + 
          "(无感知)");

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
            List accs = this.accountService
              .getPortalaccountList(new PortalaccountQuery());
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

      if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac)))
      {
        if (!((Portalbas)config.getConfigMap().get(basip)).getAuthInterface()
          .contains("1")) {
          String[] userinfo = new String[3];
          userinfo[0] = username;
          userinfo[1] = password;
          userinfo[2] = (username + "(无感知)");
          AutoLoginMap.getInstance().getAutoLoginMap()
            .put(ikmac, userinfo);
        }

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

      if (stringUtils.isEmpty(ssid)) {
        ssid = (String)session.getAttribute("ssid");
      }
      if (stringUtils.isEmpty(apmac)) {
        apmac = (String)session.getAttribute("apmac");
      }
      String[] loginInfo = new String[16];
      loginInfo[0] = username;
      loginInfo[1] = String.valueOf(userId);
      loginInfo[2] = String.valueOf(recordId);

      Date now = new Date();
      loginInfo[3] = ThreadLocalDateUtil.format(now);
      loginInfo[4] = ikmac;
      loginInfo[5] = "ok";
      if (isRadius)
        loginInfo[6] = "2";
      else {
        loginInfo[6] = "1";
      }
      loginInfo[7] = "0";
      loginInfo[8] = "0";

      loginInfo[9] = ip;
      loginInfo[10] = basip;
      loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
      loginInfo[12] = ssid;
      loginInfo[13] = apmac;
      loginInfo[14] = "no";
      loginInfo[15] = getAgent(request);

      onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);

      Portallogrecord logRecord = new Portallogrecord();

      if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface()
        .contains("1"))
        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " 系统用户: " + username + ",登录成功!");
      else {
        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " Radius用户: " + username + ",登录成功!");
      }

      logRecord.setRecDate(now);

      this.logRecordService.addPortallogrecord(logRecord);

      if (stringUtils.isNotBlank(ssid)) {
        try {
          PortalssidQuery apq = new PortalssidQuery();
          apq.setSsid(ssid);
          apq.setSsidLike(false);
          List aps = this.ssidService.getPortalssidList(apq);
          if ((aps != null) && (aps.size() > 0)) {
            Portalssid ap = (Portalssid)aps.get(0);
            ap.setBasip(basip);
            long count = ap.getCount().longValue() + 1L;
            ap.setCount(Long.valueOf(count));
            this.ssidService.updatePortalssidByKey(ap);
          } else {
            Portalssid ap = new Portalssid();
            ap.setSsid(ssid);
            ap.setBasip(basip);
            ap.setCount(Long.valueOf(1L));
            this.ssidService.addPortalssid(ap);
          }
        } catch (Exception e) {
          logger.error("==============ERROR Start=============");
          logger.error(e);
          logger.error("ERROR INFO ", e);
          logger.error("==============ERROR End=============");
        }
      }
      if (stringUtils.isNotBlank(apmac)) {
        try {
          PortalapQuery apq = new PortalapQuery();
          apq.setMac(apmac);
          apq.setMacLike(false);
          List aps = this.apService.getPortalapList(apq);
          if ((aps != null) && (aps.size() > 0)) {
            Portalap ap = (Portalap)aps.get(0);
            ap.setBasip(basip);
            long count = ap.getCount().longValue() + 1L;
            ap.setCount(Long.valueOf(count));
            this.apService.updatePortalapByKey(ap);
          } else {
            Portalap ap = new Portalap();
            ap.setMac(apmac);
            ap.setBasip(basip);
            ap.setCount(Long.valueOf(1L));
            this.apService.addPortalap(ap);
          }
        } catch (Exception e) {
          logger.error("==============ERROR Start=============");
          logger.error(e);
          logger.error("ERROR INFO ", e);
          logger.error("==============ERROR End=============");
        }
      }

      SangforLogin(ip, username, ikmac, apmac, basip);

      map.put("ret", "0");
      map.put("i", ip);
      map.put("u", username);
      map.put("w", authUrl);
      return map;
    }

    map.put("ret", "1");
    map.put("i", ip);
    map.put("u", username);
    map.put("msg", "认证失败！");
    return map;
  }

  @RequestMapping({"/changeip.action"})
  public void changeip(HttpServletResponse response, HttpServletRequest request)
    throws IOException
  {
    String ikServer = GetNgnixRemotIP.getRemoteAddrIp(request);
    String gwid = request.getParameter("gwid");
    String nasname = request.getParameter("nasname");
    String old_ip = request.getParameter("user_ip");
    String new_ip = request.getParameter("new_ip");
    String mac = request.getParameter("mac");
    String basip = request.getParameter("basip");

    Portalbas basConfigFind = null;
    if (stringUtils.isNotBlank(basip)) {
      basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    }
    if (stringUtils.isNotBlank(nasname)) {
      PortalbasQuery bq = new PortalbasQuery();
      bq.setBasname(nasname);
      bq.setBasnameLike(false);
      List bs = this.basService.getPortalbasList(bq);
      if (bs.size() > 0) {
        basConfigFind = (Portalbas)bs.get(0);
        basip = basConfigFind.getBasIp();
      }
    }
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfigFind != null) {
      basConfig = basConfigFind;
    }

    if (basConfig.getIsdebug().equals("1")) {
      logger.info("BAS服务器" + ikServer + "发来用户IP切换消息: basip=" + basip + 
        " gwid=" + gwid + " nasname=" + nasname + " mac=" + mac + 
        " old_ip=" + old_ip + " new_ip=" + new_ip);
    }

    if ((stringUtils.isBlank(basip)) && 
      (stringUtils.isNotBlank(nasname))) {
      PortalbasQuery bq = new PortalbasQuery();
      bq.setBasname(nasname);
      bq.setBasnameLike(false);
      List bs = this.basService.getPortalbasList(bq);
      if (bs.size() > 0) {
        basip = ((Portalbas)bs.get(0)).getBasIp();
      }
    }

    if (stringUtils.isBlank(basip)) {
      if ("0".equals(basConfig.getIsOut())) {
        basip = basConfig.getBasIp();
      }
    }
    else {
      basip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }

    if ((stringUtils.isNotBlank(old_ip)) && (stringUtils.isNotBlank(new_ip)) && 
      (stringUtils.isNotBlank(mac))) {
      mac = PortalUtil.MacFormat(mac);
      iKuaiMacIpMap.getInstance().getMacIpMap().put(mac, new_ip);

      if (onlineMap.getOnlineUserMap().containsKey(old_ip + ":" + basip)) {
        String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
          old_ip + ":" + basip);
        onlineMap.getOnlineUserMap().remove(old_ip + ":" + basip);
        onlineMap.getOnlineUserMap().put(new_ip + ":" + basip, 
          loginInfo);
        if (basConfig.getIsdebug().equals("1")) {
          logger.info("爱快漫游IP切换成功!! 移除失效列表【" + old_ip + ":" + basip + 
            "】 启用新列表【" + new_ip + ":" + basip + "】");
        }
      }
    }

    String respMessage = "HTTP/1.0 200 OK\r\n";
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
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

  private void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, String authUser, String authPwd, String phone, HttpServletResponse response)
  {
    Portalonlinelimit onlinelimit = this.onlinelimitService
      .getPortalonlinelimitByKey(id);
    Long timepermit = onlinelimit.getTime();
    Long costTime = Long.valueOf(0L);
    int haveTime = getHaveTime();
    int toHaveTime = haveTime;
    Long oldcostTime = Long.valueOf(0L);
    Boolean notLimit = Boolean.valueOf(true);
    if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])MacLimitMap.getInstance().getMacLimitMap()
        .get(mac);
      if (macTimeInfo != null) {
        oldcostTime = Long.valueOf(macTimeInfo[1]);
      }
      if (onlinelimit.getState().intValue() == 1) {
        Date now = new Date();
        String nowString = ThreadLocalDateUtil.format(now);
        if (macTimeInfo == null) {
          macTimeInfo = new String[3];
          macTimeInfo[1] = "0";
        }
        macTimeInfo[0] = nowString;
        macTimeInfo[2] = String.valueOf(id);
        MacLimitMap.getInstance().getMacLimitMap()
          .put(mac, macTimeInfo);
        costTime = oldcostTime;
        haveTime = (int)(timepermit.longValue() / 60000L);
        notLimit = Boolean.valueOf(false);

        if (id.longValue() == 1L) {
          String[] TimeInfo = 
            (String[])UserLimitMap.getInstance()
            .getUserLimitMap().get(phone);
          if (TimeInfo == null) {
            TimeInfo = new String[3];
            TimeInfo[1] = "0";
          }
          TimeInfo[0] = nowString;
          TimeInfo[2] = String.valueOf(id);
          UserLimitMap.getInstance().getUserLimitMap()
            .put(phone, TimeInfo);
          if (onlinelimit.getType().intValue() == 1) {
            costTime = Long.valueOf(TimeInfo[1]);
            haveTime = (int)(timepermit.longValue() / 60000L);
          }
        }
      }

      Cookie cookieMac = new Cookie("mac", mac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", mac);
    }
    int overTime = (int)(costTime.longValue() / 60000L);
    haveTime -= overTime;
    if (haveTime > toHaveTime) {
      haveTime = toHaveTime;
    }
    if (haveTime < 0) {
      haveTime = 0;
    }
    if (notLimit.booleanValue()) {
      overTime += (int)(oldcostTime.longValue() / 60000L);
    }
    String haveTimeString = String.valueOf(haveTime);
    String overTimeString = String.valueOf(overTime);
    session.setAttribute("haveTime", haveTimeString);
    session.setAttribute("overTime", overTimeString);

    Boolean isAuto = Boolean.valueOf(false);
    if (isAuto.booleanValue())
    {
      if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
        String[] userinfo = new String[3];
        userinfo[0] = authUser;
        userinfo[1] = authPwd;
        userinfo[2] = phone;
        AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
      }
    }
  }

  private void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response)
  {
    Portalonlinelimit onlinelimit = this.onlinelimitService
      .getPortalonlinelimitByKey(id);
    Long timepermit = onlinelimit.getTime();
    Long costTime = Long.valueOf(0L);
    int haveTime = getHaveTime();
    int toHaveTime = haveTime;
    Long oldcostTime = Long.valueOf(0L);
    Boolean notLimit = Boolean.valueOf(true);
    if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])MacLimitMap.getInstance().getMacLimitMap()
        .get(mac);
      if (macTimeInfo != null) {
        oldcostTime = Long.valueOf(macTimeInfo[1]);
      }
      if ((onlinelimit.getState().intValue() == 1) && 
        (onlinelimit.getType().intValue() == 0)) {
        Date now = new Date();
        String nowString = ThreadLocalDateUtil.format(now);
        if (macTimeInfo == null) {
          macTimeInfo = new String[3];
          macTimeInfo[1] = "0";
        }
        macTimeInfo[0] = nowString;
        macTimeInfo[2] = String.valueOf(id);
        MacLimitMap.getInstance().getMacLimitMap()
          .put(mac, macTimeInfo);
        costTime = oldcostTime;
        haveTime = (int)(timepermit.longValue() / 60000L);
        notLimit = Boolean.valueOf(false);
      }

      Cookie cookieMac = new Cookie("mac", mac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", mac);
    }
    int overTime = (int)(costTime.longValue() / 60000L);
    haveTime -= overTime;
    if (haveTime > toHaveTime) {
      haveTime = toHaveTime;
    }
    if (haveTime < 0) {
      haveTime = 0;
    }
    if (notLimit.booleanValue()) {
      overTime += (int)(oldcostTime.longValue() / 60000L);
    }
    String haveTimeString = String.valueOf(haveTime);
    String overTimeString = String.valueOf(overTime);
    session.setAttribute("haveTime", haveTimeString);
    session.setAttribute("overTime", overTimeString);
  }

  private void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response, String username)
  {
    Portalonlinelimit onlinelimit = this.onlinelimitService
      .getPortalonlinelimitByKey(id);
    Long timepermit = onlinelimit.getTime();
    Long costTime = Long.valueOf(0L);
    int haveTime = (int)(getTime(username).longValue() / 60000L);
    int toHaveTime = haveTime;
    Long oldcostTime = Long.valueOf(0L);
    Boolean notLimit = Boolean.valueOf(true);
    if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])MacLimitMap.getInstance().getMacLimitMap()
        .get(mac);
      if (macTimeInfo != null) {
        oldcostTime = Long.valueOf(macTimeInfo[1]);
      }
      if ((onlinelimit.getState().intValue() == 1) && 
        (onlinelimit.getType().intValue() == 0)) {
        Date now = new Date();
        String nowString = ThreadLocalDateUtil.format(now);
        if (macTimeInfo == null) {
          macTimeInfo = new String[3];
          macTimeInfo[1] = "0";
        }
        macTimeInfo[0] = nowString;
        macTimeInfo[2] = String.valueOf(id);
        MacLimitMap.getInstance().getMacLimitMap()
          .put(mac, macTimeInfo);
        costTime = oldcostTime;
        haveTime = (int)(timepermit.longValue() / 60000L);
        notLimit = Boolean.valueOf(false);
      }

      Cookie cookieMac = new Cookie("mac", mac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", mac);
    }
    int overTime = (int)(costTime.longValue() / 60000L);
    haveTime -= overTime;
    if (haveTime > toHaveTime) {
      haveTime = toHaveTime;
    }
    if (haveTime < 0) {
      haveTime = 0;
    }
    if (notLimit.booleanValue()) {
      overTime += (int)(oldcostTime.longValue() / 60000L);
    }
    String haveTimeString = String.valueOf(haveTime);
    String overTimeString = String.valueOf(overTime);
    session.setAttribute("haveTime", haveTimeString);
    session.setAttribute("overTime", overTimeString);
  }

  private Long getTime(String username)
  {
    PortalaccountQuery query = new PortalaccountQuery();
    query.setLoginName(username);
    query.setLoginNameLike(false);
    List as = this.accountService.getPortalaccountList(query);
    if ((as != null) && (as.size() > 0)) {
      Portalaccount a = (Portalaccount)as.get(0);
      String userState = a.getState();
      Date userDate = a.getDate();
      Long userTime = a.getTime();

      if (userState.equals("0")) {
        return Long.valueOf(0L);
      }

      if (userState.equals("1")) {
        if (userTime.longValue() > 0L) {
          return userTime;
        }
        return Long.valueOf(0L);
      }

      if (userState.equals("3")) {
        Date now = new Date();
        if (userDate.getTime() > now.getTime()) {
          return Long.valueOf(userDate.getTime() - now.getTime());
        }
        return Long.valueOf(0L);
      }

      if (userState.equals("2")) {
        if (userTime.longValue() > 0L) {
          return userTime;
        }
        return Long.valueOf(0L);
      }

      return Long.valueOf(0L);
    }
    return Long.valueOf(getHaveTime() * 60000L);
  }

  private static int getHaveTime()
  {
    try
    {
      Date nowdate = new Date();
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(nowdate);
      calendar.add(5, 1);
      Date tdate = calendar.getTime();
      SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
      String tsString = fs.format(tdate);
      Date t = fs.parse(tsString);
      return (int)((t.getTime() - nowdate.getTime()) / 60000L); } catch (Exception e) {
    }
    return 1440;
  }

  private void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
  {
    Portalautologin autologin = this.autologinService
      .getPortalautologinByKey(id);
    if ((autologin != null) && (autologin.getState().intValue() == 1) && 
      (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])AutoLoginMacMap.getInstance()
        .getAutoLoginMacMap().get(mac);
      Date now = new Date();
      String nowString = ThreadLocalDateUtil.format(now);
      if (macTimeInfo == null) {
        macTimeInfo = new String[3];
        macTimeInfo[1] = "0";
      }
      macTimeInfo[0] = nowString;
      macTimeInfo[2] = String.valueOf(id);
      AutoLoginMacMap.getInstance().getAutoLoginMacMap()
        .put(mac, macTimeInfo);

      String[] userinfo = new String[3];
      userinfo[0] = authUser;
      userinfo[1] = authPwd;
      userinfo[2] = username;
      AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
    }
  }

  public static boolean Do() {
    Long isThis = Long.valueOf(new Date().getTime());
    boolean Do = false;
    if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
      Do = true;
    }
    return Do;
  }

  public static void SangforLogin(String userIP, String userName, String mac, String apmac, String basip)
  {
    SangforRequest.login(userIP, userName, mac, apmac, basip);
  }

  public static void SangforLogout(String userIP, String userName) {
    SangforRequest.logout(userIP, userName);
  }

  public static String getAgent(HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");
    String agent = "";
    if (stringUtils.isNotBlank(userAgent)) {
      if (userAgent.contains("Windows"))
        agent = "Windows";
      else if (userAgent.contains("Macintosh")) {
        agent = "MacOS";
      }
      else if (userAgent.contains("Linux"))
        agent = "Android";
      else if (userAgent.contains("Android"))
        agent = "Android";
      else if (userAgent.contains("iPhone"))
        agent = "IOS";
      else if (userAgent.contains("iPad"))
        agent = "IOS";
      else if (userAgent.contains("iPod")) {
        agent = "IOS";
      }
    }

    return agent;
  }

  public static String check(Portalbas basConfig, HttpServletRequest request)
  {
    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      return "已超过允许最大用户数限制！！";
    }
    String userAgent = request.getHeader("user-agent");
    boolean isComputer = false;
    String agent = "";
    if (stringUtils.isNotBlank(userAgent)) {
      if (userAgent.contains("Windows")) {
        isComputer = true;
        agent = "Windows";
      } else if (userAgent.contains("Macintosh")) {
        isComputer = true;
        agent = "MacOS";
      }
      else if (userAgent.contains("Linux")) {
        isComputer = false;
        agent = "Android";
      } else if (userAgent.contains("Android")) {
        isComputer = false;
        agent = "Android";
      } else if (userAgent.contains("iPhone")) {
        isComputer = false;
        agent = "IOS";
      } else if (userAgent.contains("iPad")) {
        isComputer = false;
        agent = "IOS";
      } else if (userAgent.contains("iPod")) {
        isComputer = false;
        agent = "IOS";
      }
    }

    if ((!"1".equals(basConfig.getIsComputer())) && 
      (isComputer)) {
      return "当前系统设置不允许电脑认证！！";
    }

    return "";
  }

  @ResponseBody
  @RequestMapping({"/ajax_autoAuth"})
  public Map<String, String> autoAuth(String ip, String basip, String umac, String ssid, String apmac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();
    if (Do())
    {
      if (onlineMap.getOnlineUserMap().size() < 
        Integer.valueOf(
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[1]).intValue())
      {
        HttpSession session = request.getSession();
        Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
        String ikweb = 
          ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core"))[0];
        String site = (String)session.getAttribute("site");
        if (stringUtils.isBlank(site)) {
          site = "default";
        }
        String ikmac = umac;
        ikmac = PortalUtil.MacFormat(ikmac);
        if (stringUtils.isBlank(ikmac)) {
          ikmac = (String)session.getAttribute("ikmac");
        }

        Cookie[] cookies = request.getCookies();
        String cmac = "";
        if (cookies != null) {
          for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("mac")) {
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
          ip = GetNgnixRemotIP.getRemoteAddrIp(request);
        }

        if (stringUtils.isBlank(basip)) {
          basip = (String)session.getAttribute("basip");
        }
        if (stringUtils.isNotEmpty(basip)) {
          basConfig = (Portalbas)config.getConfigMap().get(basip);
        }
        if (basConfig == null) {
          basConfig = (Portalbas)config.getConfigMap().get("");
        }
        basip = basConfig.getBasIp();
        String authPwd;
        String password;
        if (stringUtils.isNotBlank(ikmac)) {
          String[] macTimeInfo = 
            (String[])AutoLoginMacMap.getInstance()
            .getAutoLoginMacMap().get(ikmac);
          String[] userinfo = 
            (String[])AutoLoginMap.getInstance()
            .getAutoLoginMap().get(ikmac);
          if ((macTimeInfo != null) && (userinfo != null)) {
            try {
              Long id = Long.valueOf(macTimeInfo[2]);
              if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
                String authUser = userinfo[0];
                authPwd = userinfo[1];
                String username = userinfo[2];
                Portalautologin autologin = this.autologinService
                  .getPortalautologinByKey(id);
                if ((autologin != null) && 
                  (autologin.getState().intValue() == 1)) {
                  Long timepermit = autologin.getTime();
                  String loginTimeString = macTimeInfo[0];
                  Long oldcostTime = 
                    Long.valueOf(macTimeInfo[1]);
                  Long costTime = oldcostTime;

                  if (stringUtils.isNotBlank(loginTimeString)) {
                    Date loginTime = 
                      ThreadLocalDateUtil.parse(loginTimeString);
                    String nowString = 
                      ThreadLocalDateUtil.format(new Date());
                    Date nowTime = 
                      ThreadLocalDateUtil.parse(nowString);
                    costTime = Long.valueOf(nowTime.getTime() - 
                      loginTime.getTime() + 
                      oldcostTime.longValue());
                  }
                  if ((costTime.longValue() < timepermit.longValue()) || 
                    (timepermit.longValue() <= 0L)) {
                    Long userId = Long.valueOf(0L);
                    String userState = null;
                    password = authPwd;
                    boolean CheckAccount = false;
                    Object o;
                    if (3L == id.longValue())
                    {
                      PortalaccountQuery accq = new PortalaccountQuery();
                      accq.setLoginNameLike(false);
                      accq.setLoginName(username);

                      if (!"1".equals(basConfig
                        .getIsPortalCheck())) {
                        accq.setPasswordLike(false);
                        accq.setPassword(password);
                      }
                      List accs = this.accountService
                        .getPortalaccountList(accq);
                      if ((accs != null) && 
                        (accs.size() == 1)) {
                        Portalaccount acc = 
                          (Portalaccount)accs
                          .get(0);
                        if (acc != null) {
                          userId = acc.getId();
                          Date userDate = acc
                            .getDate();
                          Long userTime = acc
                            .getTime();
                          Long octets = acc
                            .getOctets();
                          if (octets == null) {
                            octets = Long.valueOf(0L);
                          }
                          userState = acc
                            .getState();
                          password = acc
                            .getPassword();

                          if (checkTimeOut(
                            userState, 
                            userId, 
                            userDate, 
                            userTime, 
                            octets)) {
                            CheckAccount = true;
                          }

                          if (!"1"
                            .equals(basConfig
                            .getIsPortalCheck()))
                          {
                            if (!password
                              .equals(authPwd)) {
                              CheckAccount = false;
                            }

                          }

                          if (CheckAccount)
                          {
                            if ("1".equals(basConfig
                              .getIsPortalCheck())) {
                              Integer limitCount = acc
                                .getMaclimitcount();
                              if ((limitCount != null) && 
                                (limitCount.intValue() > 0)) {
                                int count = 0;
                                Iterator iterator = 
                                  OnlineMap.getInstance()
                                  .getOnlineUserMap()
                                  .keySet()
                                  .iterator();

                                while (iterator
                                  .hasNext()) {
                                  o = iterator
                                    .next();
                                  String t = o
                                    .toString();
                                  String[] loginInfo = 
                                    (String[])OnlineMap.getInstance()
                                    .getOnlineUserMap()
                                    .get(t);
                                  String haveUsername = loginInfo[0];

                                  if (username
                                    .equals(haveUsername)) {
                                    count++;
                                  }
                                }
                                if (count >= limitCount.intValue()) {
                                  CheckAccount = false;
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                    else
                    {
                      CheckAccount = true;
                    }
                    if (CheckAccount) {
                      Boolean info = Boolean.valueOf(false);

                      if (((Portalbas)config
                        .getConfigMap()
                        .get(basip))
                        .getBas()
                        .equals("3")) {
                        PortalaccountQuery aq = new PortalaccountQuery();
                        aq.setLoginName(authUser);
                        aq.setLoginNameLike(false);
                        List accs = this.accountService
                          .getPortalaccountList(aq);
                        int accTime = 1440;
                        if (accs.size() == 1) {
                          long accTimeLong = 
                            ((Portalaccount)accs
                            .get(0))
                            .getTime().longValue() / 60000L;
                          if (accTimeLong > 0L) {
                            accTime = (int)accTimeLong;
                          }
                        }
                        info = 
                          Boolean.valueOf(UniFiMethod.sendAuthorization(
                          ikmac, 
                          accTime, 
                          site, basip));
                      } else {
                        info = 
                          InterfaceControl.Method("PORTAL_LOGIN", 
                          authUser, 
                          authPwd, 
                          ip, basip, 
                          ikmac);
                      }
                      if (info.booleanValue())
                      {
                        if (stringUtils.isNotBlank(ssid)) {
                          try {
                            PortalssidQuery apq = new PortalssidQuery();
                            apq.setSsid(ssid);
                            apq.setSsidLike(false);
                            List aps = this.ssidService
                              .getPortalssidList(apq);
                            if ((aps != null) && 
                              (aps.size() > 0)) {
                              Portalssid ap = 
                                (Portalssid)aps
                                .get(0);
                              ap.setBasip(basip);
                              long count = ap
                                .getCount().longValue() + 1L;
                              ap.setCount(Long.valueOf(count));
                              this.ssidService
                                .updatePortalssidByKey(ap);
                            } else {
                              Portalssid ap = new Portalssid();
                              ap.setSsid(ssid);
                              ap.setBasip(basip);
                              ap.setCount(Long.valueOf(1L));
                              this.ssidService
                                .addPortalssid(ap);
                            }
                          } catch (Exception e) {
                            logger.error("==============ERROR Start=============");
                            logger.error(e);
                            logger.error(
                              "ERROR INFO ", 
                              e);
                            logger.error("==============ERROR End=============");
                          }

                        }

                        if (stringUtils.isNotBlank(apmac)) {
                          try {
                            PortalapQuery apq = new PortalapQuery();
                            apq.setMac(apmac);
                            apq.setMacLike(false);
                            List aps = this.apService
                              .getPortalapList(apq);
                            if ((aps != null) && 
                              (aps.size() > 0)) {
                              Portalap ap = 
                                (Portalap)aps
                                .get(0);
                              ap.setBasip(basip);
                              long count = ap
                                .getCount().longValue() + 1L;
                              ap.setCount(Long.valueOf(count));
                              this.apService
                                .updatePortalapByKey(ap);
                            } else {
                              Portalap ap = new Portalap();
                              ap.setMac(apmac);
                              ap.setBasip(basip);
                              ap.setCount(Long.valueOf(1L));
                              this.apService
                                .addPortalap(ap);
                            }
                          } catch (Exception e) {
                            logger.error("==============ERROR Start=============");
                            logger.error(e);
                            logger.error(
                              "ERROR INFO ", 
                              e);
                            logger.error("==============ERROR End=============");
                          }

                        }

                        if ((stringUtils.isBlank(ikmac)) || 
                          ("error"
                          .equals(ikmac))) {
                          ikmac = 
                            (String)ipMacMap.getInstance()
                            .getIpMacMap()
                            .get(ip + ":" + 
                            basip);
                        }

                        IndexAction.UpdateMacTimeLimitMethod(
                          ikmac, id, 
                          session, 
                          username);

                        IndexAction.AutoLoginPutMethod(
                          ikmac, id);

                        session.setAttribute(
                          "username", 
                          username);
                        session.setAttribute(
                          "password", 
                          password);
                        session.setAttribute("ip", 
                          ip);
                        session.setAttribute(
                          "basip", basip);
                        session.setAttribute(
                          "ikmac", ikmac);

                        String[] loginInfo = new String[16];
                        loginInfo[0] = username;
                        Date now = new Date();
                        loginInfo[3] = 
                          ThreadLocalDateUtil.format(now);
                        loginInfo[4] = ikmac;
                        if ((3L == id.longValue()) || (4L == id.longValue())) {
                          loginInfo[5] = "ok";
                        }
                        if (3L == id.longValue())
                        {
                          if ((stringUtils.isNotBlank(userState)) && 
                            (userId != null)) {
                            Long recordId = doLinkRecord(
                              username, ip, 
                              basip, 
                              userState, 
                              userId, ikmac);
                            loginInfo[1] = 
                              String.valueOf(userId);
                            loginInfo[2] = 
                              String.valueOf(recordId);
                            session.setAttribute(
                              "password", 
                              password);
                          }
                        }
                        String tid = "0";
                        if (1L == id.longValue())
                          tid = "4";
                        else if (2L == id.longValue())
                          tid = "0";
                        else if (3L == id.longValue())
                          tid = "1";
                        else if (4L == id.longValue())
                          tid = "2";
                        else if (5L == id.longValue())
                          tid = "3";
                        else if (6L == id.longValue())
                          tid = "5";
                        else if (7L == id.longValue())
                          tid = "6";
                        else if (8L == id.longValue()) {
                          tid = "7";
                        }
                        loginInfo[6] = tid;
                        loginInfo[7] = "0";
                        loginInfo[8] = "0";

                        loginInfo[9] = ip;
                        loginInfo[10] = basip;
                        loginInfo[11] = 
                          ((Portalbas)config
                          .getConfigMap()
                          .get(basip))
                          .getBasname();
                        loginInfo[12] = ssid;
                        loginInfo[13] = apmac;
                        loginInfo[14] = "yes";
                        loginInfo[15] = getAgent(request);

                        onlineMap
                          .getOnlineUserMap()
                          .put(ip + ":" + 
                          basip, 
                          loginInfo);
                        Portallogrecord logRecord = new Portallogrecord();

                        logRecord.setInfo("IP: " + 
                          ip + ":" + basip + 
                          " mac: " + ikmac + 
                          " 用户: " + 
                          username + 
                          " ,无感知登录成功!");
                        logRecord.setRecDate(now);
                        this.logRecordService
                          .addPortallogrecord(logRecord);

                        String authUrl = ikweb;
                        PortalbasauthQuery bsq = new PortalbasauthQuery();
                        bsq.setBasip(basip);
                        List<Portalbasauth> basauths = this.portalbasauthService
                          .getPortalbasauthList(bsq);
                        if (basauths.size() > 0) {
                          for (Portalbasauth ba : basauths) {
                            if (ba.getType() == 
                              Integer.valueOf(tid)) {
                              authUrl = ba
                                .getUrl();

                              if (!stringUtils.isBlank(authUrl)) break;
                              authUrl = ikweb;

                              break;
                            }
                          }

                        }

                        SangforLogin(ip, 
                          username, 
                          ikmac, 
                          apmac, 
                          basip);
                        map.put("ret", "0");
                        map.put("i", ip);
                        map.put("u", username);
                        map.put("w", authUrl);
                        return map;
                      }
                    }
                  }
                }
              }
            }
            catch (Exception e) {
              logger.error("==============ERROR Start=============");
              logger.error(e);
              logger.error("ERROR INFO ", e);
              logger.error("==============ERROR End=============");
            }
          }

        }

        if ((1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) && 
          (1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))))
        {
          Boolean info = Boolean.valueOf(false);

          String userMac = ikmac;

          if (stringUtils.isNotBlank(userMac))
          {
            List<Portalaccountmacs> macs = this.macsService
              .getPortalaccountmacsList(new PortalaccountmacsQuery());
            label4452: for (Portalaccountmacs mac : macs) {
              if (mac.getMac().equals(userMac)) {
                Portalaccount acc = this.accountService
                  .getPortalaccountByKey(mac
                  .getAccountId());
                if (acc == null) break;
                int state = acc.getAutologin().intValue();
                if (state != 1)
                  break;
                List<Portalbasauth> basauths;
                if (basConfig.getAuthInterface()
                  .contains("1"))
                {
                  PortalbasauthQuery bsq = new PortalbasauthQuery();
                  bsq.setBasip(basip);
                  String authUser = basConfig
                    .getBasUser();
                  authPwd = basConfig
                    .getBasPwd();
                  String authUrl = ikweb;
                  basauths = this.portalbasauthService
                    .getPortalbasauthList(bsq);
                  if (basauths.size() > 0) {
                    for (Portalbasauth ba : basauths) {
                      if (ba.getType().intValue() == 1) {
                        authUser = ba
                          .getUsername();
                        authPwd = ba
                          .getPassword();

                        if (stringUtils.isBlank(ikweb)) {
                          authUrl = ba
                            .getUrl();
                        }

                        if ((stringUtils.isBlank(authUser)) || 
                          (stringUtils.isBlank(authPwd))) {
                          authUser = basConfig
                            .getBasUser();
                          authPwd = basConfig
                            .getBasPwd();
                        }

                        if (!stringUtils.isBlank(authUrl)) break;
                        authUrl = ikweb;

                        break;
                      }
                    }
                  }

                  if (acc == null) break;
                  Long userId = acc.getId();
                  Date userDate = acc.getDate();
                  Long userTime = acc.getTime();
                  Long octets = acc.getOctets();
                  if (octets == null) {
                    octets = Long.valueOf(0L);
                  }
                  String username = acc
                    .getLoginName();
                  String userState = acc
                    .getState();
                   password = acc
                    .getPassword();

                  if (!"1".equals(basConfig
                    .getIsPortalCheck())) {
                    authUser = username;
                    authPwd = password;
                  }

                  if (!checkTimeOut(userState, 
                    userId, userDate, 
                    userTime, octets)) {
                    break;
                  }
                  boolean checkOnlineLimit = true;

                  if ("1".equals(basConfig
                    .getIsPortalCheck())) {
                    Integer limitCount = acc
                      .getMaclimitcount();
                    if ((limitCount != null) && 
                      (limitCount.intValue() > 0)) {
                      int countOnline = 0;
                      Iterator iterator = 
                        OnlineMap.getInstance()
                        .getOnlineUserMap()
                        .keySet()
                        .iterator();

                      while (iterator
                        .hasNext()) {
                        Object o = iterator
                          .next();
                        String t = o
                          .toString();
                        String[] loginInfo = 
                          (String[])OnlineMap.getInstance()
                          .getOnlineUserMap()
                          .get(t);
                        String haveUsername = loginInfo[0];

                        if (username
                          .equals(haveUsername)) {
                          countOnline++;
                        }
                      }
                      if (countOnline >= limitCount.intValue()) {
                        checkOnlineLimit = false;
                      }
                    }
                  }

                  if (!checkOnlineLimit) {
                    break;
                  }
                  if ((basConfig
                    .getBas()
                    .equals("1")) || 
                    (basConfig
                    .getBas()
                    .equals("0"))) {
                    info = 
                      InterfaceControl.Method("PORTAL_LOGIN", 
                      authUser, 
                      authPwd, 
                      ip, 
                      basip, 
                      ikmac);
                  }

                  if ((basConfig
                    .getBas()
                    .equals("2")) || 
                    (basConfig
                    .getBas()
                    .equals("4"))) {
                    info = 
                      InterfaceControl.Method("PORTAL_LOGIN", 
                      authUser, 
                      authPwd, 
                      ip, 
                      basip, 
                      ikmac);
                  }

                  if (basConfig
                    .getBas()
                    .equals("3")) {
                    PortalaccountQuery aq = new PortalaccountQuery();
                    aq.setLoginName(authUser);
                    aq.setLoginNameLike(false);
                    List accs = this.accountService
                      .getPortalaccountList(aq);
                    int accTime = 1440;
                    if (accs.size() == 1) {
                      long accTimeLong = 
                        ((Portalaccount)accs
                        .get(0))
                        .getTime().longValue() / 60000L;
                      if (accTimeLong > 0L) {
                        accTime = (int)accTimeLong;
                      }
                    }
                    info = 
                      Boolean.valueOf(UniFiMethod.sendAuthorization(
                      ikmac, 
                      accTime, 
                      site, 
                      basip));
                  }
                  if (!info.booleanValue())
                    break;
                  Long recordId = doLinkRecord(
                    username, 
                    ip, basip, 
                    userState, 
                    userId, 
                    ikmac);

                  session.setAttribute(
                    "password", 
                    password);
                  Cookie cookiePwd = new Cookie(
                    "password", 
                    password);
                  cookiePwd
                    .setMaxAge(86400);
                  response.addCookie(cookiePwd);
                  session.setAttribute(
                    "username", 
                    username);
                  session.setAttribute(
                    "ip", ip);
                  session.setAttribute(
                    "basip", 
                    basip);
                  session.setAttribute(
                    "ikmac", 
                    ikmac);

                  Cookie cookieIp = new Cookie(
                    "ip", ip);
                  cookieIp.setMaxAge(86400);
                  response.addCookie(cookieIp);

                  Cookie cookieBasIp = new Cookie(
                    "basip", 
                    basip);
                  cookieBasIp
                    .setMaxAge(86400);
                  response.addCookie(cookieBasIp);

                  String[] loginInfo = new String[16];
                  loginInfo[0] = username;
                  loginInfo[1] = 
                    String.valueOf(userId);
                  loginInfo[2] = 
                    String.valueOf(recordId);

                  Date now = new Date();
                  loginInfo[3] = 
                    ThreadLocalDateUtil.format(now);
                  loginInfo[4] = ikmac;
                  loginInfo[5] = "ok";
                  loginInfo[6] = "1";
                  loginInfo[7] = "0";
                  loginInfo[8] = "0";

                  loginInfo[9] = ip;
                  loginInfo[10] = basip;
                  loginInfo[11] = 
                    ((Portalbas)config
                    .getConfigMap()
                    .get(basip))
                    .getBasname();
                  loginInfo[12] = ssid;
                  loginInfo[13] = apmac;
                  loginInfo[14] = "yes";
                  loginInfo[15] = getAgent(request);

                  onlineMap
                    .getOnlineUserMap()
                    .put(ip + 
                    ":" + 
                    basip, 
                    loginInfo);

                  Portallogrecord logRecord = new Portallogrecord();
                  logRecord
                    .setInfo("IP: " + 
                    ip + 
                    ":" + 
                    basip + 
                    " mac: " + 
                    ikmac + 
                    " 系统用户: " + 
                    username + 
                    ",无感知登录成功!");
                  logRecord
                    .setRecDate(now);
                  this.logRecordService
                    .addPortallogrecord(logRecord);

                  IndexAction.UpdateMacTimeLimitMethod(
                    ikmac, 
                    Long.valueOf(3L), 
                    request.getSession(), 
                    username);

                  if (stringUtils.isNotBlank(ssid)) {
                    try {
                      PortalssidQuery apq = new PortalssidQuery();
                      apq.setSsid(ssid);
                      apq.setSsidLike(false);
                      List aps = this.ssidService
                        .getPortalssidList(apq);
                      if ((aps != null) && 
                        (aps.size() > 0)) {
                        Portalssid ap = 
                          (Portalssid)aps
                          .get(0);
                        ap.setBasip(basip);
                        long count = ap
                          .getCount().longValue() + 1L;
                        ap.setCount(Long.valueOf(count));
                        this.ssidService
                          .updatePortalssidByKey(ap);
                      } else {
                        Portalssid ap = new Portalssid();
                        ap.setSsid(ssid);
                        ap.setBasip(basip);
                        ap.setCount(Long.valueOf(1L));
                        this.ssidService
                          .addPortalssid(ap);
                      }
                    } catch (Exception e) {
                      logger.error("==============ERROR Start=============");
                      logger.error(e);
                      logger.error(
                        "ERROR INFO ", 
                        e);
                      logger.error("==============ERROR End=============");
                    }

                  }

                  if (stringUtils.isNotBlank(apmac)) {
                    try {
                      PortalapQuery apq = new PortalapQuery();
                      apq.setMac(apmac);
                      apq.setMacLike(false);
                      List aps = this.apService
                        .getPortalapList(apq);
                      if ((aps != null) && 
                        (aps.size() > 0)) {
                        Portalap ap = 
                          (Portalap)aps
                          .get(0);
                        ap.setBasip(basip);
                        long count = ap
                          .getCount().longValue() + 1L;
                        ap.setCount(Long.valueOf(count));
                        this.apService
                          .updatePortalapByKey(ap);
                      } else {
                        Portalap ap = new Portalap();
                        ap.setMac(apmac);
                        ap.setBasip(basip);
                        ap.setCount(Long.valueOf(1L));
                        this.apService
                          .addPortalap(ap);
                      }
                    } catch (Exception e) {
                      logger.error("==============ERROR Start=============");
                      logger.error(e);
                      logger.error(
                        "ERROR INFO ", 
                        e);
                      logger.error("==============ERROR End=============");
                    }

                  }

                  SangforLogin(
                    ip, 
                    username, 
                    ikmac, 
                    apmac, 
                    basip);

                  map.put("ret", "0");
                  map.put("i", ip);
                  map.put("u", 
                    username);
                  map.put("w", 
                    authUrl);
                  return map;
                }

                PortalbasauthQuery bsq = new PortalbasauthQuery();
                bsq.setBasip(basip);
                String authUrl = ikweb;
                 basauths = this.portalbasauthService
                  .getPortalbasauthList(bsq);
                if (basauths.size() > 0) {
                  for (Portalbasauth ba : basauths) {
                    if (ba.getType().intValue() == 2)
                    {
                      if (stringUtils.isBlank(ikweb)) {
                        authUrl = ba
                          .getUrl();
                      }

                      if (!stringUtils.isBlank(authUrl)) break;
                      authUrl = ikweb;

                      break;
                    }
                  }
                }

                String[] userinfo = null;
                if ((!stringUtils.isBlank(userMac)) && 
                  (!"error"
                  .equals(userMac))) {
                  userinfo = 
                    (String[])AutoLoginMap.getInstance()
                    .getAutoLoginMap()
                    .get(userMac);
                }
                String username = "";
                password = "";
                String phone = "";
                if ((userinfo != null) && 
                  (userinfo.length == 2)) {
                  username = userinfo[0];
                  password = userinfo[1];
                }
                if ((userinfo != null) && 
                  (userinfo.length == 3)) {
                  username = userinfo[0];
                  password = userinfo[1];
                  phone = userinfo[2];
                }

                if (stringUtils.isNotBlank(username))
                {
                  if (stringUtils.isNotBlank(password))
                  {
                    boolean CheckAccount = true;

                    if (!CheckAccount) {
                      break label4452;
                    }
                    if ((basConfig
                      .getBas()
                      .equals("1")) || 
                      (basConfig
                      .getBas()
                      .equals("0"))) {
                      info = 
                        InterfaceControl.Method("PORTAL_LOGIN", 
                        username, 
                        password, 
                        ip, 
                        basip, 
                        ikmac);
                    }

                    if ((basConfig
                      .getBas()
                      .equals("2")) || 
                      (basConfig
                      .getBas()
                      .equals("4"))) {
                      info = 
                        InterfaceControl.Method("PORTAL_LOGIN", 
                        username, 
                        password, 
                        ip, 
                        basip, 
                        ikmac);
                    }

                    if (!basConfig
                      .getBas()
                      .equals("3")) break label4452;
                    info = 
                      Boolean.valueOf(UniFiMethod.sendAuthorization(
                      ikmac, 
                      1440, 
                      site, 
                      basip));

                    break label4452; } 
                }info = Boolean.valueOf(false);

                if (!info.booleanValue()) {
                  break;
                }
                if (stringUtils.isNotBlank(phone)) {
                  username = phone;
                }

                session.setAttribute(
                  "username", username);
                session.setAttribute(
                  "password", password);
                session.setAttribute("ip", ip);
                session.setAttribute("basip", 
                  basip);
                session.setAttribute("ikmac", 
                  ikmac);

                Cookie cookieIp = new Cookie(
                  "ip", ip);
                cookieIp.setMaxAge(86400);
                response.addCookie(cookieIp);

                Cookie cookieBasIp = new Cookie(
                  "basip", basip);
                cookieBasIp
                  .setMaxAge(86400);
                response.addCookie(cookieBasIp);

                String[] loginInfo = new String[16];
                loginInfo[0] = username;

                Date now = new Date();
                loginInfo[3] = 
                  ThreadLocalDateUtil.format(now);
                loginInfo[4] = ikmac;
                loginInfo[5] = "ok";
                loginInfo[6] = "2";
                loginInfo[7] = "0";
                loginInfo[8] = "0";

                loginInfo[9] = ip;
                loginInfo[10] = basip;
                loginInfo[11] = 
                  ((Portalbas)config
                  .getConfigMap()
                  .get(basip))
                  .getBasname();
                loginInfo[12] = ssid;
                loginInfo[13] = apmac;
                loginInfo[14] = "yes";
                loginInfo[15] = getAgent(request);

                onlineMap.getOnlineUserMap()
                  .put(ip + ":" + basip, 
                  loginInfo);

                Portallogrecord logRecord = new Portallogrecord();
                logRecord.setInfo("IP: " + ip + 
                  ":" + basip + 
                  " mac: " + ikmac + 
                  " Radius用户: " + 
                  username + 
                  ",无感知登录成功!");
                logRecord.setRecDate(now);
                this.logRecordService
                  .addPortallogrecord(logRecord);

                IndexAction.UpdateMacTimeLimitMethod(
                  ikmac, 
                  Long.valueOf(4L), 
                  request.getSession(), 
                  username);

                if (stringUtils.isNotBlank(ssid)) {
                  try {
                    PortalssidQuery apq = new PortalssidQuery();
                    apq.setSsid(ssid);
                    apq.setSsidLike(false);
                    List aps = this.ssidService
                      .getPortalssidList(apq);
                    if ((aps != null) && 
                      (aps.size() > 0)) {
                      Portalssid ap = 
                        (Portalssid)aps
                        .get(0);
                      ap.setBasip(basip);
                      long count = ap
                        .getCount().longValue() + 1L;
                      ap.setCount(Long.valueOf(count));
                      this.ssidService
                        .updatePortalssidByKey(ap);
                    } else {
                      Portalssid ap = new Portalssid();
                      ap.setSsid(ssid);
                      ap.setBasip(basip);
                      ap.setCount(Long.valueOf(1L));
                      this.ssidService
                        .addPortalssid(ap);
                    }
                  } catch (Exception e) {
                    logger.error("==============ERROR Start=============");
                    logger.error(e);
                    logger.error(
                      "ERROR INFO ", 
                      e);
                    logger.error("==============ERROR End=============");
                  }

                }

                if (stringUtils.isNotBlank(apmac)) {
                  try {
                    PortalapQuery apq = new PortalapQuery();
                    apq.setMac(apmac);
                    apq.setMacLike(false);
                    List aps = this.apService
                      .getPortalapList(apq);
                    if ((aps != null) && 
                      (aps.size() > 0)) {
                      Portalap ap = 
                        (Portalap)aps
                        .get(0);
                      ap.setBasip(basip);
                      long count = ap
                        .getCount().longValue() + 1L;
                      ap.setCount(Long.valueOf(count));
                      this.apService
                        .updatePortalapByKey(ap);
                    } else {
                      Portalap ap = new Portalap();
                      ap.setMac(apmac);
                      ap.setBasip(basip);
                      ap.setCount(Long.valueOf(1L));
                      this.apService
                        .addPortalap(ap);
                    }
                  } catch (Exception e) {
                    logger.error("==============ERROR Start=============");
                    logger.error(e);
                    logger.error(
                      "ERROR INFO ", 
                      e);
                    logger.error("==============ERROR End=============");
                  }

                }

                SangforLogin(ip, 
                  username, 
                  ikmac, apmac, 
                  basip);

                map.put("ret", "0");
                map.put("i", ip);
                map.put("u", username);
                map.put("w", authUrl);
                return map;
              }

            }

          }

        }

      }

    }

    map.put("ret", "1");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_smsAuth"})
  public Map<String, String> smsAuth(String ip, String phone, String yzm, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
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
          com.leeson.core.bean.Config config = this.configService
            .getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService
          .getConfigByKey(Long.valueOf(1L));
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

    String ikmac = umac;
    ikmac = PortalUtil.MacFormat(ikmac);
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }

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
    if (stringUtils.isBlank(ip))
    {
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
    if (config.getConfigMap().get(basip) == null) {
      basip = basConfig.getBasIp();
    }

    String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
    if (checkResult != "") {
      map.put("ret", "1");
      map.put("msg", checkResult);
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        map.put("ret", "119");
        map.put("i", ip);
        map.put("u", phone);
        map.put("msg", "你已经通过认证,请不要重复刷新！！");
        return map;
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      map.put("ret", "110");
      map.put("msg", "网络暂时不可用，请稍后再试！！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
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

      PortalaccountQuery accq = new PortalaccountQuery();
      accq.setLoginName(phone);
      accq.setLoginNameLike(false);
      List accqs = this.accountService
        .getPortalaccountList(accq);
      Portalaccount smsAcc;
      if (accqs.size() > 0) {
        Portalaccount acc = (Portalaccount)accqs.get(0);
        acc.setPassword(yzm);
        this.accountService.updatePortalaccountByKey(acc);
        smsAcc = acc;
      } else {
        Portalaccountgroup ag = this.portalaccountgroupService
          .getPortalaccountgroupByKey(Long.valueOf(1L));
        Portalaccount e = new Portalaccount();
        e.setLoginName(phone);
        e.setPassword(yzm);
        e.setPhoneNumber(phone);
        e.setName(phone);

        e.setState(ag.getState());
        e.setMaclimitcount(ag.getMaclimitcount());
        e.setMaclimit(ag.getMaclimit());
        e.setAutologin(ag.getAutologin());
        e.setSpeed(ag.getSpeed());
        e.setDate(ag.getDate());
        e.setTime(ag.getTime());
        e.setOctets(ag.getOctets());
        this.accountService.addPortalaccount(e);
        smsAcc = e;
      }
      authUser = smsAcc.getLoginName();
      authPwd = smsAcc.getPassword();

      Boolean info = Boolean.valueOf(false);

      if (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("3")) {
        String site = (String)session.getAttribute("site");
        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginName(authUser);
        aq.setLoginNameLike(false);
        List accs = this.accountService
          .getPortalaccountList(aq);
        int accTime = 1440;
        if (accs.size() == 1) {
          long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
          if (accTimeLong > 0L) {
            accTime = (int)accTimeLong;
          }
        }
        info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
          basip));
      } else {
        info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
          authPwd, ip, basip, ikmac);
      }

      if (info.booleanValue()) {
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(1L), session, authUser, authPwd, 
          phone, response);

        AutoLoginPutMethod(ikmac, Long.valueOf(1L), authUser, authPwd, phone + 
          "(无感知)");

        if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
          PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
          mq.setAccountId(smsAcc.getId());
          mq.setMac(ikmac.toLowerCase());
          mq.setMacLike(false);
          List accountmacs = this.macsService
            .getPortalaccountmacsList(mq);
          if (accountmacs.size() <= 0) {
            Portalaccountmacs mac = new Portalaccountmacs();
            mac.setAccountId(smsAcc.getId());
            mac.setMac(ikmac.toLowerCase());
            this.macsService.addPortalaccountmacs(mac);
          }
        }

        session.setAttribute("username", phone);

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikmac", ikmac);

        if (stringUtils.isEmpty(ssid)) {
          ssid = (String)session.getAttribute("ssid");
        }
        if (stringUtils.isEmpty(apmac)) {
          apmac = (String)session.getAttribute("apmac");
        }
        String[] loginInfo = new String[16];
        loginInfo[0] = phone;
        loginInfo[1] = String.valueOf(smsAcc.getId());
        Date now = new Date();
        loginInfo[3] = ThreadLocalDateUtil.format(now);
        loginInfo[4] = ikmac;
        loginInfo[5] = "ok";
        loginInfo[6] = "1";

        loginInfo[7] = "0";
        loginInfo[8] = "0";

        loginInfo[9] = ip;
        loginInfo[10] = basip;
        loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
        loginInfo[12] = ssid;
        loginInfo[13] = apmac;
        loginInfo[14] = "no";
        loginInfo[15] = getAgent(request);

        onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
        Portallogrecord logRecord = new Portallogrecord();
        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " 用户: " + phone + ",登录成功!");
        logRecord.setRecDate(now);
        this.logRecordService.addPortallogrecord(logRecord);

        String more = smsapi.getMore();
        if ("0".equals(more)) {
          PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        }
        session.setAttribute("ikweb", authUrl);

        if (stringUtils.isNotBlank(ssid)) {
          try {
            PortalssidQuery apq = new PortalssidQuery();
            apq.setSsid(ssid);
            apq.setSsidLike(false);
            List aps = this.ssidService
              .getPortalssidList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalssid ap = (Portalssid)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.ssidService.updatePortalssidByKey(ap);
            } else {
              Portalssid ap = new Portalssid();
              ap.setSsid(ssid);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.ssidService.addPortalssid(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }
        if (stringUtils.isNotBlank(apmac)) {
          try {
            PortalapQuery apq = new PortalapQuery();
            apq.setMac(apmac);
            apq.setMacLike(false);
            List aps = this.apService.getPortalapList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalap ap = (Portalap)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.apService.updatePortalapByKey(ap);
            } else {
              Portalap ap = new Portalap();
              ap.setMac(apmac);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.apService.addPortalap(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }

        SangforLogin(ip, phone, ikmac, apmac, basip);

        map.put("ret", "0");
        map.put("i", ip);
        map.put("u", phone);
        map.put("w", authUrl);
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
  @RequestMapping({"/ajax_autoAuthSms"})
  public Map<String, String> autoAuthSms(String ip, String basip, String umac, String ssid, String apmac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();
    if (Do())
    {
      if (onlineMap.getOnlineUserMap().size() < 
        Integer.valueOf(
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[1]).intValue())
      {
        HttpSession session = request.getSession();
        String ikmac = umac;
        ikmac = PortalUtil.MacFormat(ikmac);
        if (stringUtils.isBlank(ikmac)) {
          ikmac = (String)session.getAttribute("ikmac");
        }

        Cookie[] cookies = request.getCookies();
        String cmac = "";
        if (cookies != null) {
          for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("mac")) {
              cmac = cookies[i].getValue();
            }
          }
        }
        if (stringUtils.isBlank(ikmac)) {
          ikmac = cmac;
        }

        if (stringUtils.isNotBlank(ikmac)) {
          PortalaccountmacsQuery amq = new PortalaccountmacsQuery();
          amq.setMac(ikmac);
          amq.setMacLike(false);
          amq.orderbyId(false);
          List macs = this.macsService
            .getPortalaccountmacsList(amq);
          if (macs.size() > 0) {
            Portalaccount acc = this.accountService
              .getPortalaccountByKey(((Portalaccountmacs)macs.get(0))
              .getAccountId());
            if (acc != null) {
              int state = acc.getAutologin().intValue();
              if (state == 1) {
                map.put("ret", "0");
                map.put("u", acc.getLoginName());
                map.put("p", acc.getPassword());
                return map;
              }
            }
          }
        }
      }
    }
    map.put("ret", "1");
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_smsOnekeyAuth"})
  public Map<String, String> smsOnekeyAuth(String usr, String pwd, String ip, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
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
          com.leeson.core.bean.Config config = this.configService
            .getConfigByKey(Long.valueOf(1L));
          if (config != null) {
            config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
            this.configService.updateConfigByKey(config);
          }
        }
      } else {
        com.leeson.core.bean.Config config = this.configService
          .getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
          this.configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException1)
    {
    }
    if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
      map.put("ret", "2");
      map.put("msg", "参数获取失败！！");
      return map;
    }

    String ikmac = umac;
    ikmac = PortalUtil.MacFormat(ikmac);
    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }

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
    if (stringUtils.isBlank(ip))
    {
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
    if (config.getConfigMap().get(basip) == null) {
      basip = basConfig.getBasIp();
    }

    String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
    if (checkResult != "") {
      map.put("ret", "1");
      map.put("msg", checkResult);
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("4")))
    {
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
      basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
      ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
      ip = ikmac;
      if (stringUtils.isBlank(ip))
      {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      }
      if (stringUtils.isBlank(ikmac)) {
        map.put("ret", "10");
        map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
        return map;
      }

    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
        map.put("ret", "119");
        map.put("i", ip);
        map.put("u", usr);
        map.put("msg", "你已经通过认证,请不要重复刷新！！");
        return map;
      }
      Kick.kickUserSyn(ip + ":" + basip);
    }

    if (onlineMap.getOnlineUserMap().size() >= 
      Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
      .get("core"))[1]).intValue())
    {
      map.put("ret", "110");
      map.put("msg", "网络暂时不可用，请稍后再试！！");
      return map;
    }

    if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("6")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("7")) || 
      (((Portalbas)config.getConfigMap().get(basip)).getBas()
      .equals("8"))) {
      map.put("ret", "1");
      map.put("msg", "该设备类型错误！！");
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
        map.put("u", usr);
        map.put("msg", "该设备当日时长已用完！！");
        return map;
      }

      PortalaccountQuery accq = new PortalaccountQuery();
      accq.setLoginName(usr);
      accq.setLoginNameLike(false);
      List accqs = this.accountService
        .getPortalaccountList(accq);
      Portalaccount smsAcc;
      if (accqs.size() > 0) {
        Portalaccount acc = (Portalaccount)accqs.get(0);
        smsAcc = acc;
      } else {
        map.put("ret", "2");
        map.put("i", ip);
        map.put("u", usr);
        map.put("msg", "用户不存在！！");
        return map;
      }
      authUser = smsAcc.getLoginName();
      authPwd = smsAcc.getPassword();

      Boolean info = Boolean.valueOf(false);

      if (((Portalbas)config.getConfigMap().get(basip)).getBas()
        .equals("3")) {
        String site = (String)session.getAttribute("site");
        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginName(authUser);
        aq.setLoginNameLike(false);
        List accs = this.accountService
          .getPortalaccountList(aq);
        int accTime = 1440;
        if (accs.size() == 1) {
          long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
          if (accTimeLong > 0L) {
            accTime = (int)accTimeLong;
          }
        }
        info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
          basip));
      } else {
        info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
          authPwd, ip, basip, ikmac);
      }

      if (info.booleanValue()) {
        UpdateMacTimeLimitMethod(ikmac, Long.valueOf(1L), session, authUser, authPwd, 
          usr, response);

        AutoLoginPutMethod(ikmac, Long.valueOf(1L), authUser, authPwd, usr + 
          "(无感知)");

        session.setAttribute("username", usr);

        session.setAttribute("ip", ip);
        session.setAttribute("basip", basip);
        session.setAttribute("ikmac", ikmac);

        if (stringUtils.isEmpty(ssid)) {
          ssid = (String)session.getAttribute("ssid");
        }
        if (stringUtils.isEmpty(apmac)) {
          apmac = (String)session.getAttribute("apmac");
        }
        String[] loginInfo = new String[16];
        loginInfo[0] = usr;
        loginInfo[1] = String.valueOf(smsAcc.getId());
        Date now = new Date();
        loginInfo[3] = ThreadLocalDateUtil.format(now);
        loginInfo[4] = ikmac;
        loginInfo[5] = "ok";
        loginInfo[6] = "1";

        loginInfo[7] = "0";
        loginInfo[8] = "0";

        loginInfo[9] = ip;
        loginInfo[10] = basip;
        loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
        loginInfo[12] = ssid;
        loginInfo[13] = apmac;
        loginInfo[14] = "no";
        loginInfo[15] = getAgent(request);

        onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
        Portallogrecord logRecord = new Portallogrecord();
        logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
          " 用户: " + usr + ",登录成功!");
        logRecord.setRecDate(now);
        this.logRecordService.addPortallogrecord(logRecord);

        session.setAttribute("ikweb", authUrl);

        if (stringUtils.isNotBlank(ssid)) {
          try {
            PortalssidQuery apq = new PortalssidQuery();
            apq.setSsid(ssid);
            apq.setSsidLike(false);
            List aps = this.ssidService
              .getPortalssidList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalssid ap = (Portalssid)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.ssidService.updatePortalssidByKey(ap);
            } else {
              Portalssid ap = new Portalssid();
              ap.setSsid(ssid);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.ssidService.addPortalssid(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }
        if (stringUtils.isNotBlank(apmac)) {
          try {
            PortalapQuery apq = new PortalapQuery();
            apq.setMac(apmac);
            apq.setMacLike(false);
            List aps = this.apService.getPortalapList(apq);
            if ((aps != null) && (aps.size() > 0)) {
              Portalap ap = (Portalap)aps.get(0);
              ap.setBasip(basip);
              long count = ap.getCount().longValue() + 1L;
              ap.setCount(Long.valueOf(count));
              this.apService.updatePortalapByKey(ap);
            } else {
              Portalap ap = new Portalap();
              ap.setMac(apmac);
              ap.setBasip(basip);
              ap.setCount(Long.valueOf(1L));
              this.apService.addPortalap(ap);
            }
          } catch (Exception e) {
            logger.error("==============ERROR Start=============");
            logger.error(e);
            logger.error("ERROR INFO ", e);
            logger.error("==============ERROR End=============");
          }
        }

        SangforLogin(ip, usr, ikmac, apmac, basip);

        map.put("ret", "0");
        map.put("i", ip);
        map.put("u", usr);
        map.put("w", authUrl);
        map.put("msg", "认证成功！");
        return map;
      }
      map.put("ret", "1");
      map.put("i", ip);
      map.put("u", usr);
      map.put("msg", "网络暂时不可用，请联系管理员！！");
      return map;
    }

    map.put("ret", "3");
    map.put("i", ip);
    map.put("u", usr);
    map.put("msg", "系统不是该验证模式，请联系管理员！！");
    return map;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxInterFaceController
 * JD-Core Version:    0.6.2
 */