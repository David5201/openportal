/*      */ package com.leeson.core.controller;
/*      */ 
/*      */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.api.AccountAPIRequest;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalaccountgroup;
/*      */ import com.leeson.core.bean.Portalaccountmacs;
/*      */ import com.leeson.core.bean.Portalap;
/*      */ import com.leeson.core.bean.Portalautologin;
/*      */ import com.leeson.core.bean.Portalbas;
/*      */ import com.leeson.core.bean.Portalbasauth;
/*      */ import com.leeson.core.bean.Portallinkrecord;
/*      */ import com.leeson.core.bean.Portallogrecord;
/*      */ import com.leeson.core.bean.Portalonlinelimit;
/*      */ import com.leeson.core.bean.Portalsmsapi;
/*      */ import com.leeson.core.bean.Portalssid;
/*      */ import com.leeson.core.bean.Portalweb;
/*      */ import com.leeson.core.bean.Portalweixinwifi;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalaccountmacsQuery;
/*      */ import com.leeson.core.query.PortalapQuery;
/*      */ import com.leeson.core.query.PortalbasQuery;
/*      */ import com.leeson.core.query.PortalbasauthQuery;
/*      */ import com.leeson.core.query.PortalphonesQuery;
/*      */ import com.leeson.core.query.PortalsmsapiQuery;
/*      */ import com.leeson.core.query.PortalssidQuery;
/*      */ import com.leeson.core.query.PortalweixinwifiQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalaccountgroupService;
/*      */ import com.leeson.core.service.PortalaccountmacsService;
/*      */ import com.leeson.core.service.PortalapService;
/*      */ import com.leeson.core.service.PortalautologinService;
/*      */ import com.leeson.core.service.PortalbasService;
/*      */ import com.leeson.core.service.PortalbasauthService;
/*      */ import com.leeson.core.service.PortallinkrecordService;
/*      */ import com.leeson.core.service.PortallogrecordService;
/*      */ import com.leeson.core.service.PortalonlinelimitService;
/*      */ import com.leeson.core.service.PortalphonesService;
/*      */ import com.leeson.core.service.PortalsmsapiService;
/*      */ import com.leeson.core.service.PortalssidService;
/*      */ import com.leeson.core.service.PortalwebService;
/*      */ import com.leeson.core.service.PortalweixinwifiService;
/*      */ import com.leeson.core.utils.Kick;
/*      */ import com.leeson.core.utils.MyUtils;
/*      */ import com.leeson.core.utils.SangforRequest;
/*      */ import com.leeson.core.utils.TwoDimensionCode.makeImg;
/*      */ import com.leeson.portal.core.controller.IndexAction;
/*      */ import com.leeson.portal.core.model.AccountAPIMap;
/*      */ import com.leeson.portal.core.model.AppTokenMap;
/*      */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*      */ import com.leeson.portal.core.model.AutoLoginMap;
/*      */ import com.leeson.portal.core.model.MacLimitMap;
/*      */ import com.leeson.portal.core.model.MagicMap;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.portal.core.model.OpenIdMap;
/*      */ import com.leeson.portal.core.model.PhoneCodeMap;
/*      */ import com.leeson.portal.core.model.UserLimitMap;
/*      */ import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
/*      */ import com.leeson.portal.core.model.WeixinMap;
/*      */ import com.leeson.portal.core.model.WeixinOpenIDMap;
/*      */ import com.leeson.portal.core.model.iKuaiMacIpMap;
/*      */ import com.leeson.portal.core.model.ipMacMap;
/*      */ import com.leeson.portal.core.model.isDo;
/*      */ import com.leeson.portal.core.service.InterfaceControl;
/*      */ import com.leeson.portal.core.service.action.unifi.UniFiMethod;
/*      */ import com.leeson.portal.core.service.utils.PortalUtil;
/*      */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLDecoder;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import javax.servlet.RequestDispatcher;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.codec.digest.DigestUtils;
/*      */ import org.apache.commons.lang.StringEscapeUtils;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Controller;
/*      */ import org.springframework.ui.ModelMap;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.RequestParam;
/*      */ import org.springframework.web.bind.annotation.ResponseBody;
/*      */ 
/*      */ @Controller
/*      */ public class AjaxInterFaceController
/*      */ {
/*      */ 
/*      */   @Autowired
/*      */   private PortallogrecordService logRecordService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountService accountService;
/*      */ 
/*      */   @Autowired
/*      */   private PortallinkrecordService linkRecordService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalweixinwifiService weixinwifiService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountmacsService macsService;
/*      */ 
/*      */   @Autowired
/*      */   PortalbasService basService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalsmsapiService portalsmsapiService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalbasauthService portalbasauthService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalonlinelimitService onlinelimitService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalautologinService autologinService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalapService apService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalssidService ssidService;
/*      */ 
/*      */   @Autowired
/*      */   private ConfigService configService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalwebService webService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountgroupService portalaccountgroupService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalphonesService portalphonesService;
/*  163 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*      */ 
/*  165 */   private static Logger logger = Logger.getLogger(AjaxInterFaceController.class);
/*  166 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_onlineInfo"})
/*      */   public Map<String, String[]> onlineInfo(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  174 */     return onlineMap.getOnlineUserMap();
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/online"})
/*      */   public Map<String, Long> online(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  183 */     HashMap kickMap = new HashMap();
/*  184 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet()
/*  185 */       .iterator();
/*  186 */     while (iterator.hasNext())
/*      */       try {
/*  188 */         Object o = iterator.next();
/*  189 */         String host = o.toString();
/*  190 */         String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
/*  191 */         String[] ips = host.split(":");
/*  192 */         String uip = ips[0];
/*  193 */         String time = loginInfo[3];
/*  194 */         Date loginTime = ThreadLocalDateUtil.parse(time);
/*  195 */         String nowString = ThreadLocalDateUtil.format(new Date());
/*  196 */         Date nowTime = ThreadLocalDateUtil.parse(nowString);
/*  197 */         Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/*  198 */         costTime = Long.valueOf(costTime.longValue() / 1000L);
/*      */ 
/*  200 */         kickMap.put(uip, costTime);
/*      */       }
/*      */       catch (Exception localException) {
/*      */       }
/*  204 */     return kickMap;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WeixinOpenIdInfo"})
/*      */   public Map<String, String> weixinOpenIdInfo(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  213 */     return WeixinOpenIDMap.getInstance().getWeixinOpenIDMap();
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajaxUI_Login"})
/*      */   public Map<String, String> coreLogin(String usr, String pwd, String ip, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  223 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */ 
/*  225 */     Map map = new HashMap();
/*  226 */     if (stringUtils.isBlank(ip))
/*      */     {
/*  228 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*  230 */     if (stringUtils.isBlank(basip)) {
/*  231 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/*  234 */     if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
/*  235 */       map.put("ret", "1");
/*  236 */       map.put("i", ip);
/*  237 */       map.put("u", usr);
/*  238 */       map.put("msg", "用户名和密码不能为空！！");
/*  239 */       return map;
/*      */     }
/*      */ 
/*  242 */     Boolean info = InterfaceControl.Method("PORTAL_LOGIN", usr, pwd, ip, 
/*  243 */       basip, "");
/*      */ 
/*  245 */     if (info.booleanValue()) {
/*  246 */       map.put("ret", "0");
/*  247 */       map.put("i", ip);
/*  248 */       map.put("u", usr);
/*  249 */       map.put("msg", "认证成功！");
/*      */     }
/*      */     else {
/*  252 */       map.put("ret", "1");
/*  253 */       map.put("i", ip);
/*  254 */       map.put("u", usr);
/*  255 */       map.put("msg", "认证失败！或者你已经在线！");
/*      */     }
/*  257 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajaxUI_LoginOut"})
/*      */   public Map<String, String> coreLoginOut(String ip, String basip, HttpServletRequest request, HttpServletResponse response) {
/*  264 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  265 */     String username = "test";
/*  266 */     String password = "test";
/*      */ 
/*  268 */     if (stringUtils.isBlank(ip))
/*      */     {
/*  270 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*  272 */     if (stringUtils.isBlank(basip)) {
/*  273 */       basip = basConfig.getBasIp();
/*      */     }
/*  275 */     Boolean info = InterfaceControl.Method("PORTAL_LOGINOUT", username, 
/*  276 */       password, ip, basip, "");
/*      */ 
/*  278 */     Map map = new HashMap();
/*      */ 
/*  280 */     if (info.booleanValue()) {
/*  281 */       map.put("ret", "0");
/*  282 */       map.put("msg", "下线成功！");
/*      */     }
/*      */     else {
/*  285 */       map.put("ret", "1");
/*  286 */       map.put("msg", "下线失败！请稍后再试！或者你已经离线！");
/*      */     }
/*  288 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_guestHeart"})
/*      */   public Map<String, String> ajax_guestHeart(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  298 */     HttpSession session = request.getSession();
/*  299 */     String ikweb = (String)session.getAttribute("ikweb");
/*  300 */     String authUrl = ikweb;
/*  301 */     Map map = new HashMap();
/*      */ 
/*  303 */     String[] accountInfo = null;
/*  304 */     accountInfo = (String[])onlineMap.getOnlineUserMap().get(ip + ":" + basip);
/*  305 */     if (accountInfo != null) {
/*  306 */       String ssid = (String)session.getAttribute("ssid");
/*  307 */       String apmac = (String)session.getAttribute("apmac");
/*  308 */       String agent = (String)session.getAttribute("agent");
/*  309 */       accountInfo[9] = ip;
/*  310 */       accountInfo[10] = basip;
/*  311 */       accountInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/*  312 */       accountInfo[12] = ssid;
/*  313 */       accountInfo[13] = apmac;
/*  314 */       accountInfo[14] = "no";
/*  315 */       accountInfo[15] = agent;
/*  316 */       onlineMap.getOnlineUserMap().put(ip + ":" + basip, accountInfo);
/*      */ 
/*  318 */       String mac = accountInfo[4];
/*      */ 
/*  320 */       UpdateMacTimeLimitMethod(mac, Long.valueOf(8L), session, response);
/*      */ 
/*  322 */       String username = accountInfo[0];
/*  323 */       session.setAttribute("username", username);
/*  324 */       session.setAttribute("ip", ip);
/*  325 */       session.setAttribute("basip", basip);
/*  326 */       session.setAttribute("ikmac", mac);
/*      */ 
/*  328 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  329 */       bsq.setBasip(basip);
/*  330 */       List<Portalbasauth> basauths = this.portalbasauthService
/*  331 */         .getPortalbasauthList(bsq);
/*  332 */       if (basauths.size() > 0) {
/*  333 */         for (Portalbasauth ba : basauths) {
/*  334 */           if (ba.getType().intValue() == 7) {
/*  335 */             authUrl = ba.getUrl();
/*  336 */             if (!stringUtils.isBlank(authUrl)) break;
/*  337 */             authUrl = ikweb;
/*      */ 
/*  339 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  343 */       if (stringUtils.isBlank(authUrl)) {
/*  344 */         authUrl = 
/*  345 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  345 */           .get("core"))[0];
/*      */       }
/*      */ 
/*  348 */       session.setAttribute("ikweb", authUrl);
/*      */ 
/*  350 */       if (stringUtils.isNotBlank(ssid)) {
/*      */         try {
/*  352 */           PortalssidQuery apq = new PortalssidQuery();
/*  353 */           apq.setSsid(ssid);
/*  354 */           apq.setSsidLike(false);
/*  355 */           List aps = this.ssidService.getPortalssidList(apq);
/*  356 */           if ((aps != null) && (aps.size() > 0)) {
/*  357 */             Portalssid ap = (Portalssid)aps.get(0);
/*  358 */             ap.setBasip(basip);
/*  359 */             long count = ap.getCount().longValue() + 1L;
/*  360 */             ap.setCount(Long.valueOf(count));
/*  361 */             this.ssidService.updatePortalssidByKey(ap);
/*      */           } else {
/*  363 */             Portalssid ap = new Portalssid();
/*  364 */             ap.setSsid(ssid);
/*  365 */             ap.setBasip(basip);
/*  366 */             ap.setCount(Long.valueOf(1L));
/*  367 */             this.ssidService.addPortalssid(ap);
/*      */           }
/*      */         } catch (Exception e) {
/*  370 */           logger.error("==============ERROR Start=============");
/*  371 */           logger.error(e);
/*  372 */           logger.error("ERROR INFO ", e);
/*  373 */           logger.error("==============ERROR End=============");
/*      */         }
/*      */       }
/*      */ 
/*  377 */       if (stringUtils.isNotBlank(apmac)) {
/*      */         try {
/*  379 */           PortalapQuery apq = new PortalapQuery();
/*  380 */           apq.setMac(apmac);
/*  381 */           apq.setMacLike(false);
/*  382 */           List aps = this.apService.getPortalapList(apq);
/*  383 */           if ((aps != null) && (aps.size() > 0)) {
/*  384 */             Portalap ap = (Portalap)aps.get(0);
/*  385 */             ap.setBasip(basip);
/*  386 */             long count = ap.getCount().longValue() + 1L;
/*  387 */             ap.setCount(Long.valueOf(count));
/*  388 */             this.apService.updatePortalapByKey(ap);
/*      */           } else {
/*  390 */             Portalap ap = new Portalap();
/*  391 */             ap.setMac(apmac);
/*  392 */             ap.setBasip(basip);
/*  393 */             ap.setCount(Long.valueOf(1L));
/*  394 */             this.apService.addPortalap(ap);
/*      */           }
/*      */         } catch (Exception e) {
/*  397 */           logger.error("==============ERROR Start=============");
/*  398 */           logger.error(e);
/*  399 */           logger.error("ERROR INFO ", e);
/*  400 */           logger.error("==============ERROR End=============");
/*      */         }
/*      */       }
/*      */ 
/*  404 */       SangforLogin(ip, username, mac, apmac, basip);
/*      */ 
/*  406 */       map.put("ret", "0");
/*  407 */       map.put("i", ip);
/*  408 */       map.put("u", username);
/*  409 */       map.put("w", authUrl);
/*  410 */       map.put("msg", "你已经通过认证！！");
/*  411 */       return map;
/*      */     }
/*  413 */     map.put("ret", "1");
/*  414 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_guest"})
/*      */   public Map<String, String> guest(String ip, String basip, String umac, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  423 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  424 */     Map map = new HashMap();
/*      */ 
/*  426 */     HttpSession session = request.getSession();
/*      */ 
/*  428 */     String ikmac = umac;
/*  429 */     if (stringUtils.isBlank(ikmac)) {
/*  430 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/*  432 */     String site = (String)session.getAttribute("site");
/*      */     try
/*      */     {
/*  435 */       String web = (String)session.getAttribute("web");
/*  436 */       if (stringUtils.isNotBlank(web)) {
/*  437 */         while (web.endsWith("/")) {
/*  438 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  440 */         Long webID = Long.valueOf(web);
/*  441 */         if (webID.longValue() != 0L) {
/*  442 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  443 */           if (portalweb != null) {
/*  444 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  445 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  448 */           com.leeson.core.bean.Config config = this.configService
/*  449 */             .getConfigByKey(Long.valueOf(1L));
/*  450 */           if (config != null) {
/*  451 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  452 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  456 */         com.leeson.core.bean.Config config = this.configService
/*  457 */           .getConfigByKey(Long.valueOf(1L));
/*  458 */         if (config != null) {
/*  459 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  460 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/*  468 */     Cookie[] cookies = request.getCookies();
/*  469 */     String cip = "";
/*  470 */     String cbasip = "";
/*  471 */     String cmac = "";
/*  472 */     if (cookies != null) {
/*  473 */       for (int i = 0; i < cookies.length; i++) {
/*  474 */         if (cookies[i].getName().equals("ip"))
/*  475 */           cip = cookies[i].getValue();
/*  476 */         else if (cookies[i].getName().equals("basip"))
/*  477 */           cbasip = cookies[i].getValue();
/*  478 */         else if (cookies[i].getName().equals("mac")) {
/*  479 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/*  483 */     if (stringUtils.isBlank(ikmac)) {
/*  484 */       ikmac = cmac;
/*      */     }
/*      */ 
/*  487 */     if (stringUtils.isBlank(ip)) {
/*  488 */       ip = (String)session.getAttribute("ip");
/*      */     }
/*  490 */     if (stringUtils.isBlank(ip)) {
/*  491 */       ip = cip;
/*      */     }
/*  493 */     if (stringUtils.isBlank(ip))
/*      */     {
/*  495 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/*  498 */     if (stringUtils.isBlank(basip)) {
/*  499 */       basip = (String)session.getAttribute("basip");
/*      */     }
/*  501 */     if (stringUtils.isBlank(basip)) {
/*  502 */       basip = cbasip;
/*      */     }
/*  504 */     if (stringUtils.isBlank(basip)) {
/*  505 */       basip = basConfig.getBasIp();
/*      */     }
/*  507 */     if (config.getConfigMap().get(basip) == null) {
/*  508 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/*  511 */     String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
/*  512 */     if (checkResult != "") {
/*  513 */       map.put("ret", "1");
/*  514 */       map.put("msg", checkResult);
/*  515 */       return map;
/*      */     }
/*      */ 
/*  519 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/*  521 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/*  521 */       .equals("4")))
/*      */     {
/*  523 */       if (stringUtils.isBlank(ip))
/*      */       {
/*  525 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/*  527 */       if (stringUtils.isBlank(ikmac)) {
/*  528 */         map.put("ret", "10");
/*  529 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/*  530 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  535 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/*  536 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/*  537 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/*  538 */       ip = ikmac;
/*  539 */       if (stringUtils.isBlank(ip))
/*      */       {
/*  541 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/*  543 */       if (stringUtils.isBlank(ikmac)) {
/*  544 */         map.put("ret", "10");
/*  545 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/*  546 */         return map;
/*      */       }
/*  548 */       if (stringUtils.isBlank(site)) {
/*  549 */         map.put("ret", "10");
/*  550 */         map.put("msg", "获取Site信息失败，请关闭浏览器重试！！");
/*  551 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  556 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  557 */       ip + ":" + basip);
/*  558 */     if (isLogin) {
/*  559 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/*  560 */         map.put("ret", "119");
/*  561 */         map.put("i", ip);
/*  562 */         map.put("u", "访客认证");
/*  563 */         map.put("msg", "你已经通过认证,请不要重复刷新！！");
/*  564 */         return map;
/*      */       }
/*  566 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/*  570 */     if (onlineMap.getOnlineUserMap().size() >= 
/*  570 */       Integer.valueOf(
/*  571 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  571 */       .get("core"))[1]).intValue())
/*      */     {
/*  572 */       map.put("ret", "110");
/*  573 */       map.put("msg", "网络暂时不可用，请稍后再试！！");
/*  574 */       return map;
/*      */     }
/*      */ 
/*  577 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/*  579 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/*  579 */       .equals("6")) || 
/*  581 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/*  581 */       .equals("7")) || 
/*  583 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/*  583 */       .equals("8"))) {
/*  584 */       map.put("ret", "1");
/*  585 */       map.put("msg", "该设备类型错误！！");
/*  586 */       return map;
/*      */     }
/*      */ 
/*  589 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
/*  590 */       String basePath = request.getScheme() + "://" + 
/*  591 */         request.getServerName() + ":" + request.getServerPort() + 
/*  592 */         request.getContextPath() + "/";
/*  593 */       String guestAuthPath = basePath + "ajax_guestScan.action?ip=" + ip + 
/*  594 */         "&basip=" + basip + "&mac=" + ikmac + "&site=" + site;
/*      */ 
/*  596 */       String logoPath = request.getServletContext().getRealPath("/");
/*  597 */       String outPath = logoPath + "ExcelOut/";
/*  598 */       File dir = new File(outPath);
/*  599 */       if (!dir.exists()) {
/*  600 */         dir.mkdirs();
/*      */       }
/*  602 */       Date now = new Date();
/*  603 */       String nowString = ThreadLocalDateUtil.format(now);
/*  604 */       String temp = UUID.randomUUID().toString().replace("-", "");
/*  605 */       outPath = outPath + temp + nowString + ".jpg";
/*  606 */       logoPath = logoPath + "dist/css/img/logoS.png";
/*  607 */       int result = makeImg.make(guestAuthPath, logoPath, outPath);
/*  608 */       if (result == 1) {
/*  609 */         String imgUrl = basePath + "ExcelOut/" + temp + nowString + 
/*  610 */           ".jpg";
/*  611 */         map.put("ret", "0");
/*  612 */         map.put("url", imgUrl);
/*  613 */         map.put("ip", ip);
/*  614 */         map.put("basip", basip);
/*  615 */         map.put("mac", ikmac);
/*  616 */         map.put("site", site);
/*  617 */         map.put("msg", "请让员工扫描此二维码，授权上网！！");
/*  618 */         return map;
/*      */       }
/*  620 */       map.put("ret", "1");
/*  621 */       map.put("msg", "生成二维码失败！！");
/*  622 */       return map;
/*      */     }
/*      */ 
/*  625 */     map.put("ret", "3");
/*  626 */     map.put("i", ip);
/*  627 */     map.put("u", "访客认证");
/*  628 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/*  629 */     return map;
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/ajax_guestScan"})
/*      */   public String guestScan(String ip, String basip, String mac, String site, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  638 */     if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
/*  639 */       model.addAttribute("msg", "参数获取错误！！");
/*  640 */       model.addAttribute("ip", ip);
/*  641 */       model.addAttribute("basip", basip);
/*  642 */       model.addAttribute("mac", mac);
/*  643 */       return "guest/result";
/*      */     }
/*  645 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  646 */       ip + ":" + basip);
/*  647 */     if (isLogin) {
/*  648 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/*  649 */         model.addAttribute("msg", "该用户已经认证！！");
/*  650 */         model.addAttribute("ip", ip);
/*  651 */         model.addAttribute("basip", basip);
/*  652 */         model.addAttribute("mac", mac);
/*  653 */         return "guest/result";
/*      */       }
/*  655 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/*  659 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
/*  660 */       model.addAttribute("ip", ip);
/*  661 */       model.addAttribute("basip", basip);
/*  662 */       model.addAttribute("mac", mac);
/*  663 */       model.addAttribute("site", site);
/*  664 */       model.addAttribute("msg", "请员工账号授权！！");
/*  665 */       return "guest/auth";
/*      */     }
/*  667 */     model.addAttribute("msg", "系统不是该验证模式，请联系管理员！！");
/*  668 */     model.addAttribute("ip", ip);
/*  669 */     model.addAttribute("basip", basip);
/*  670 */     model.addAttribute("mac", mac);
/*  671 */     return "guest/result";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/ajax_guestAuth"})
/*      */   public String guestAuth(String usr, String pwd, String ip, String basip, String mac, String site, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  681 */     if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
/*  682 */       model.addAttribute("msg", "参数获取错误！！");
/*  683 */       model.addAttribute("ip", ip);
/*  684 */       model.addAttribute("basip", basip);
/*  685 */       model.addAttribute("mac", mac);
/*  686 */       return "guest/result";
/*      */     }
/*  688 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  689 */       ip + ":" + basip);
/*  690 */     if (isLogin) {
/*  691 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/*  692 */         model.addAttribute("msg", "该用户已经认证！！");
/*  693 */         model.addAttribute("ip", ip);
/*  694 */         model.addAttribute("basip", basip);
/*  695 */         model.addAttribute("mac", mac);
/*  696 */         return "guest/result";
/*      */       }
/*  698 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/*  703 */     if (onlineMap.getOnlineUserMap().size() >= 
/*  703 */       Integer.valueOf(
/*  704 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  704 */       .get("core"))[1]).intValue())
/*      */     {
/*  705 */       model.addAttribute("msg", "认证失败！！");
/*  706 */       model.addAttribute("ip", ip);
/*  707 */       model.addAttribute("basip", basip);
/*  708 */       model.addAttribute("mac", mac);
/*      */     }
/*      */ 
/*  711 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7"))
/*      */     {
/*  713 */       String accountIP = GetNgnixRemotIP.getRemoteAddrIp(request);
/*  714 */       String[] accountInfo = null;
/*      */ 
/*  716 */       accountInfo = (String[])onlineMap.getOnlineUserMap().get(
/*  717 */         accountIP + ":" + basip);
/*      */ 
/*  719 */       Iterator iterator = OnlineMap.getInstance()
/*  720 */         .getOnlineUserMap().keySet().iterator();
/*  721 */       while (iterator.hasNext()) {
/*  722 */         Object o = iterator.next();
/*  723 */         String t = o.toString();
/*  724 */         if (t.contains(accountIP + ":")) {
/*  725 */           accountInfo = 
/*  726 */             (String[])OnlineMap.getInstance().getOnlineUserMap()
/*  726 */             .get(t);
/*  727 */           break;
/*      */         }
/*      */       }
/*      */ 
/*  731 */       if (accountInfo != null) {
/*  732 */         String username = accountInfo[0];
/*      */         try
/*      */         {
/*  735 */           String state = accountInfo[5];
/*  736 */           if (!"ok".equals(state)) {
/*  737 */             model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
/*  738 */             model.addAttribute("ip", ip);
/*  739 */             model.addAttribute("basip", basip);
/*  740 */             model.addAttribute("mac", mac);
/*  741 */             return "guest/result";
/*      */           }
/*      */         } catch (Exception e) {
/*  744 */           model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
/*  745 */           model.addAttribute("ip", ip);
/*  746 */           model.addAttribute("basip", basip);
/*  747 */           model.addAttribute("mac", mac);
/*  748 */           return "guest/result";
/*      */         }
/*      */ 
/*  751 */         if (1 == CheckMacTimeLimitMethod(mac, Long.valueOf(8L))) {
/*  752 */           model.addAttribute("msg", "该设备当日时长已用完！！");
/*  753 */           model.addAttribute("ip", ip);
/*  754 */           model.addAttribute("basip", basip);
/*  755 */           model.addAttribute("mac", mac);
/*      */         }
/*      */ 
/*  758 */         PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  759 */         bsq.setBasip(basip);
/*  760 */         String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/*  761 */         String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/*  762 */         List<Portalbasauth> basauths = this.portalbasauthService
/*  763 */           .getPortalbasauthList(bsq);
/*  764 */         if (basauths.size() > 0) {
/*  765 */           for (Portalbasauth ba : basauths) {
/*  766 */             if (ba.getType().intValue() == 7) {
/*  767 */               authUser = ba.getUsername();
/*  768 */               authPwd = ba.getPassword();
/*  769 */               if ((!stringUtils.isBlank(authUser)) && 
/*  770 */                 (!stringUtils.isBlank(authPwd))) break;
/*  771 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/*  772 */                 .getBasUser();
/*  773 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/*  774 */                 .getBasPwd();
/*      */ 
/*  776 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  781 */         Boolean info = Boolean.valueOf(false);
/*      */ 
/*  784 */         if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/*  784 */           .equals("3")) {
/*  785 */           if ((stringUtils.isBlank(site)) || (stringUtils.isBlank(mac))) {
/*  786 */             model.addAttribute("msg", "获取参数信息失败！！");
/*  787 */             model.addAttribute("ip", ip);
/*  788 */             model.addAttribute("basip", basip);
/*  789 */             model.addAttribute("mac", mac);
/*  790 */             return "guest/result";
/*      */           }
/*  792 */           PortalaccountQuery aq = new PortalaccountQuery();
/*  793 */           aq.setLoginName(authUser);
/*  794 */           aq.setLoginNameLike(false);
/*  795 */           List accs = this.accountService
/*  796 */             .getPortalaccountList(aq);
/*  797 */           int accTime = 1440;
/*  798 */           if (accs.size() == 1) {
/*  799 */             long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/*  800 */             if (accTimeLong > 0L) {
/*  801 */               accTime = (int)accTimeLong;
/*      */             }
/*      */           }
/*  804 */           info = Boolean.valueOf(UniFiMethod.sendAuthorization(mac, accTime, site, 
/*  805 */             basip));
/*      */         } else {
/*  807 */           info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/*  808 */             authPwd, ip, basip, mac);
/*      */         }
/*      */ 
/*  811 */         if (info.booleanValue()) {
/*  812 */           String accountAPI_Url = 
/*  813 */             (String)AccountAPIMap.getInstance()
/*  813 */             .getAccountAPIMap().get("publicurl");
/*  814 */           String accountAPI_State = 
/*  815 */             (String)AccountAPIMap.getInstance()
/*  815 */             .getAccountAPIMap().get("publicstate");
/*  816 */           if ((stringUtils.isNotBlank(accountAPI_Url)) && 
/*  817 */             (stringUtils.isNotBlank(accountAPI_State)) && 
/*  818 */             ("1".equals(accountAPI_State))) {
/*  819 */             HttpSession session = request.getSession();
/*  820 */             String agent = (String)session.getAttribute("agent");
/*  821 */             AccountAPIRequest.guestSend(accountAPI_Url, username, 
/*  822 */               ip, mac, agent);
/*      */           }
/*      */ 
/*  825 */           username = username + "授权";
/*      */ 
/*  827 */           if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
/*  828 */             mac = 
/*  829 */               (String)ipMacMap.getInstance().getIpMacMap()
/*  829 */               .get(ip + ":" + basip);
/*      */           }
/*      */ 
/*  833 */           AutoLoginPutMethod(mac, Long.valueOf(8L), authUser, authPwd, username + 
/*  834 */             "(无感知)");
/*      */ 
/*  836 */           String[] loginInfo = new String[16];
/*      */ 
/*  838 */           loginInfo[0] = username;
/*  839 */           Date now = new Date();
/*  840 */           loginInfo[3] = ThreadLocalDateUtil.format(now);
/*  841 */           loginInfo[4] = mac;
/*  842 */           loginInfo[6] = "7";
/*  843 */           loginInfo[7] = "0";
/*  844 */           loginInfo[8] = "0";
/*      */ 
/*  846 */           loginInfo[9] = ip;
/*  847 */           loginInfo[10] = basip;
/*  848 */           loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip))
/*  849 */             .getBasname();
/*  850 */           loginInfo[12] = "";
/*  851 */           loginInfo[13] = "";
/*  852 */           loginInfo[14] = "no";
/*  853 */           loginInfo[15] = "";
/*      */ 
/*  855 */           onlineMap.getOnlineUserMap().put(ip + ":" + basip, 
/*  856 */             loginInfo);
/*  857 */           Portallogrecord logRecord = new Portallogrecord();
/*  858 */           logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + 
/*  859 */             mac + " 用户: " + username + ",登录成功!");
/*  860 */           logRecord.setRecDate(now);
/*  861 */           this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/*  863 */           model.addAttribute("ip", ip);
/*  864 */           model.addAttribute("basip", basip);
/*  865 */           model.addAttribute("mac", mac);
/*  866 */           model.addAttribute("msg", "认证成功，访客可以上网了！！");
/*  867 */           return "guest/result";
/*      */         }
/*      */ 
/*  870 */         model.addAttribute("msg", "网络暂时不可用，请稍后再试或者联系管理员！！");
/*  871 */         model.addAttribute("ip", ip);
/*  872 */         model.addAttribute("basip", basip);
/*  873 */         model.addAttribute("mac", mac);
/*  874 */         return "guest/result";
/*      */       }
/*      */ 
/*  877 */       model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
/*  878 */       model.addAttribute("ip", ip);
/*  879 */       model.addAttribute("basip", basip);
/*  880 */       model.addAttribute("mac", mac);
/*  881 */       return "guest/result";
/*      */     }
/*      */ 
/*  884 */     model.addAttribute("msg", "系统不是该验证模式，请联系管理员！！");
/*  885 */     model.addAttribute("ip", ip);
/*  886 */     model.addAttribute("basip", basip);
/*  887 */     model.addAttribute("mac", mac);
/*  888 */     return "guest/result";
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_sms"})
/*      */   public Map<String, String> sms(String ip, String phone, String yzm, String name, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  899 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  900 */     Map map = new HashMap();
/*      */ 
/*  902 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/*  905 */       String web = (String)session.getAttribute("web");
/*  906 */       if (stringUtils.isNotBlank(web)) {
/*  907 */         while (web.endsWith("/")) {
/*  908 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  910 */         Long webID = Long.valueOf(web);
/*  911 */         if (webID.longValue() != 0L) {
/*  912 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  913 */           if (portalweb != null) {
/*  914 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  915 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  918 */           com.leeson.core.bean.Config config = this.configService
/*  919 */             .getConfigByKey(Long.valueOf(1L));
/*  920 */           if (config != null) {
/*  921 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  922 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  926 */         com.leeson.core.bean.Config config = this.configService
/*  927 */           .getConfigByKey(Long.valueOf(1L));
/*  928 */         if (config != null) {
/*  929 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  930 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/*  937 */     if (stringUtils.isBlank(name)) {
/*  938 */       name = "";
/*      */     } else {
/*  940 */       name = URLDecoder.decode(name.trim());
/*  941 */       name = StringEscapeUtils.unescapeHtml(name);
/*      */     }
/*      */ 
/*  944 */     if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
/*  945 */       map.put("ret", "2");
/*  946 */       map.put("msg", "请输入手机号和验证码！！");
/*  947 */       return map;
/*      */     }
/*  949 */     if (phone.length() != 11) {
/*  950 */       map.put("ret", "2");
/*  951 */       map.put("msg", "手机号码不正确！");
/*  952 */       return map;
/*      */     }
/*      */     try {
/*  955 */       Long.parseLong(phone);
/*      */     } catch (Exception e) {
/*  957 */       map.put("ret", "2");
/*  958 */       map.put("msg", "手机号码不正确！");
/*  959 */       return map;
/*      */     }
/*      */ 
/*  962 */     if (1 == this.configService.getConfigByKey(Long.valueOf(1L)).getSmsAuthList().intValue()) {
/*  963 */       PortalphonesQuery phonesQuery = new PortalphonesQuery();
/*  964 */       phonesQuery.setPhone(phone);
/*  965 */       phonesQuery.setPhoneLike(false);
/*  966 */       int isCanAuth = this.portalphonesService.getPortalphonesCount(phonesQuery).intValue();
/*  967 */       if (isCanAuth <= 0) {
/*  968 */         map.put("ret", "1");
/*  969 */         map.put("msg", "当前手机号未授权，请联系管理员！");
/*  970 */         return map;
/*      */       }
/*      */     }
/*      */ 
/*  974 */     String code = "";
/*  975 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/*  976 */     query.setState("1");
/*  977 */     List smsList = this.portalsmsapiService
/*  978 */       .getPortalsmsapiList(query);
/*      */     Portalsmsapi smsapi;
/*  980 */     if (smsList.size() > 0)
/*  981 */       smsapi = (Portalsmsapi)smsList.get(0);
/*      */     else {
/*  983 */       smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*      */     }
/*  985 */     Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
/*  986 */     Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
/*      */     try {
/*  988 */       Date saveDate = (Date)objs[1];
/*  989 */       Long nowTime = Long.valueOf(new Date().getTime());
/*  990 */       if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
/*  991 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  992 */         map.put("ret", "2");
/*  993 */         map.put("msg", "验证码已过期，请重新获取验证码！");
/*  994 */         return map;
/*      */       }
/*  996 */       code = (String)objs[0];
/*      */     } catch (Exception e) {
/*  998 */       map.put("ret", "2");
/*  999 */       map.put("msg", "手机号或验证码不正确！");
/* 1000 */       return map;
/*      */     }
/*      */ 
/* 1003 */     if (!yzm.equals(code)) {
/* 1004 */       map.put("ret", "2");
/* 1005 */       map.put("msg", "验证码不正确！");
/* 1006 */       return map;
/*      */     }
/*      */ 
/* 1009 */     String ikmac = umac;
/* 1010 */     if (stringUtils.isBlank(ikmac)) {
/* 1011 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/*      */ 
/* 1014 */     String ikweb = (String)session.getAttribute("ikweb");
/* 1015 */     String authUrl = ikweb;
/*      */ 
/* 1018 */     Cookie[] cookies = request.getCookies();
/* 1019 */     String cip = "";
/* 1020 */     String cbasip = "";
/* 1021 */     String cmac = "";
/* 1022 */     if (cookies != null) {
/* 1023 */       for (int i = 0; i < cookies.length; i++) {
/* 1024 */         if (cookies[i].getName().equals("ip"))
/* 1025 */           cip = cookies[i].getValue();
/* 1026 */         else if (cookies[i].getName().equals("basip"))
/* 1027 */           cbasip = cookies[i].getValue();
/* 1028 */         else if (cookies[i].getName().equals("mac")) {
/* 1029 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 1033 */     if (stringUtils.isBlank(ikmac)) {
/* 1034 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 1037 */     if (stringUtils.isBlank(ip)) {
/* 1038 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 1040 */     if (stringUtils.isBlank(ip)) {
/* 1041 */       ip = cip;
/*      */     }
/* 1043 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 1045 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 1048 */     if (stringUtils.isBlank(basip)) {
/* 1049 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 1051 */     if (stringUtils.isBlank(basip)) {
/* 1052 */       basip = cbasip;
/*      */     }
/* 1054 */     if (stringUtils.isBlank(basip)) {
/* 1055 */       basip = basConfig.getBasIp();
/*      */     }
/* 1057 */     if (config.getConfigMap().get(basip) == null) {
/* 1058 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 1061 */     String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
/* 1062 */     if (checkResult != "") {
/* 1063 */       map.put("ret", "1");
/* 1064 */       map.put("msg", checkResult);
/* 1065 */       return map;
/*      */     }
/*      */ 
/* 1069 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/* 1071 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1071 */       .equals("4")))
/*      */     {
/* 1073 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 1075 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 1077 */       if (stringUtils.isBlank(ikmac)) {
/* 1078 */         map.put("ret", "10");
/* 1079 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 1080 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1085 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 1086 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/* 1087 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/* 1088 */       ip = ikmac;
/* 1089 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 1091 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 1093 */       if (stringUtils.isBlank(ikmac)) {
/* 1094 */         map.put("ret", "10");
/* 1095 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 1096 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1101 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 1102 */       ip + ":" + basip);
/* 1103 */     if (isLogin) {
/* 1104 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/* 1105 */         map.put("ret", "119");
/* 1106 */         map.put("i", ip);
/* 1107 */         map.put("u", name + phone);
/* 1108 */         map.put("msg", "你已经通过认证,请不要重复刷新！！");
/* 1109 */         return map;
/*      */       }
/* 1111 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/* 1115 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 1115 */       Integer.valueOf(
/* 1116 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1116 */       .get("core"))[1]).intValue())
/*      */     {
/* 1117 */       map.put("ret", "110");
/* 1118 */       map.put("msg", "网络暂时不可用，请稍后再试！！");
/* 1119 */       return map;
/*      */     }
/*      */ 
/* 1122 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/* 1124 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1124 */       .equals("6")) || 
/* 1126 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1126 */       .equals("7")) || 
/* 1128 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1128 */       .equals("8"))) {
/* 1129 */       map.put("ret", "1");
/* 1130 */       map.put("msg", "该设备类型错误！！");
/* 1131 */       return map;
/*      */     }
/*      */ 
/* 1134 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("4"))
/*      */     {
/* 1136 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1137 */       bsq.setBasip(basip);
/* 1138 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 1139 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 1140 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 1141 */         .getPortalbasauthList(bsq);
/* 1142 */       if (basauths.size() > 0) {
/* 1143 */         for (Portalbasauth ba : basauths) {
/* 1144 */           if (ba.getType().intValue() == 4) {
/* 1145 */             authUser = ba.getUsername();
/* 1146 */             authPwd = ba.getPassword();
/* 1147 */             authUrl = ba.getUrl();
/* 1148 */             if ((stringUtils.isBlank(authUser)) || 
/* 1149 */               (stringUtils.isBlank(authPwd))) {
/* 1150 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 1151 */                 .getBasUser();
/* 1152 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 1153 */                 .getBasPwd();
/*      */             }
/* 1155 */             if (!stringUtils.isBlank(authUrl)) break;
/* 1156 */             authUrl = ikweb;
/*      */ 
/* 1158 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1162 */       if (stringUtils.isBlank(authUrl)) {
/* 1163 */         authUrl = 
/* 1164 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1164 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 1168 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(1L))) {
/* 1169 */         map.put("ret", "2");
/* 1170 */         map.put("i", ip);
/* 1171 */         map.put("u", name + phone);
/* 1172 */         map.put("msg", "该设备当日时长已用完！！");
/* 1173 */         return map;
/*      */       }
/*      */ 
/* 1176 */       Boolean info = Boolean.valueOf(false);
/*      */ 
/* 1179 */       if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1179 */         .equals("3")) {
/* 1180 */         String site = (String)session.getAttribute("site");
/* 1181 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 1182 */         aq.setLoginName(authUser);
/* 1183 */         aq.setLoginNameLike(false);
/* 1184 */         List accs = this.accountService
/* 1185 */           .getPortalaccountList(aq);
/* 1186 */         int accTime = 1440;
/* 1187 */         if (accs.size() == 1) {
/* 1188 */           long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/* 1189 */           if (accTimeLong > 0L) {
/* 1190 */             accTime = (int)accTimeLong;
/*      */           }
/*      */         }
/* 1193 */         info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
/* 1194 */           basip));
/*      */       } else {
/* 1196 */         info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 1197 */           authPwd, ip, basip, ikmac);
/*      */       }
/*      */ 
/* 1200 */       if (info.booleanValue())
/*      */       {
/* 1202 */         if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
/* 1203 */           ikmac = 
/* 1204 */             (String)ipMacMap.getInstance().getIpMacMap()
/* 1204 */             .get(ip + ":" + basip);
/*      */         }
/* 1206 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(1L), session, authUser, authPwd, 
/* 1207 */           name + phone, response);
/*      */ 
/* 1209 */         AutoLoginPutMethod(ikmac, Long.valueOf(1L), authUser, authPwd, name + phone + 
/* 1210 */           "(无感知)");
/*      */ 
/* 1212 */         session.setAttribute("username", name + phone);
/*      */ 
/* 1214 */         session.setAttribute("ip", ip);
/* 1215 */         session.setAttribute("basip", basip);
/* 1216 */         session.setAttribute("ikmac", ikmac);
/*      */ 
/* 1226 */         if (stringUtils.isEmpty(ssid)) {
/* 1227 */           ssid = (String)session.getAttribute("ssid");
/*      */         }
/* 1229 */         if (stringUtils.isEmpty(apmac)) {
/* 1230 */           apmac = (String)session.getAttribute("apmac");
/*      */         }
/* 1232 */         String[] loginInfo = new String[16];
/* 1233 */         loginInfo[0] = (name + phone);
/* 1234 */         Date now = new Date();
/* 1235 */         loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 1236 */         loginInfo[4] = ikmac;
/* 1237 */         loginInfo[6] = "4";
/* 1238 */         loginInfo[7] = "0";
/* 1239 */         loginInfo[8] = "0";
/*      */ 
/* 1241 */         loginInfo[9] = ip;
/* 1242 */         loginInfo[10] = basip;
/* 1243 */         loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 1244 */         loginInfo[12] = ssid;
/* 1245 */         loginInfo[13] = apmac;
/* 1246 */         loginInfo[14] = "no";
/* 1247 */         loginInfo[15] = getAgent(request);
/*      */ 
/* 1249 */         onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/* 1250 */         Portallogrecord logRecord = new Portallogrecord();
/* 1251 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 1252 */           " 用户: " + name + phone + ",登录成功!");
/* 1253 */         logRecord.setRecDate(now);
/* 1254 */         this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 1256 */         String more = smsapi.getMore();
/* 1257 */         if ("0".equals(more)) {
/* 1258 */           PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*      */         }
/* 1260 */         session.setAttribute("ikweb", authUrl);
/*      */ 
/* 1262 */         if (stringUtils.isNotBlank(ssid)) {
/*      */           try {
/* 1264 */             PortalssidQuery apq = new PortalssidQuery();
/* 1265 */             apq.setSsid(ssid);
/* 1266 */             apq.setSsidLike(false);
/* 1267 */             List aps = this.ssidService
/* 1268 */               .getPortalssidList(apq);
/* 1269 */             if ((aps != null) && (aps.size() > 0)) {
/* 1270 */               Portalssid ap = (Portalssid)aps.get(0);
/* 1271 */               ap.setBasip(basip);
/* 1272 */               long count = ap.getCount().longValue() + 1L;
/* 1273 */               ap.setCount(Long.valueOf(count));
/* 1274 */               this.ssidService.updatePortalssidByKey(ap);
/*      */             } else {
/* 1276 */               Portalssid ap = new Portalssid();
/* 1277 */               ap.setSsid(ssid);
/* 1278 */               ap.setBasip(basip);
/* 1279 */               ap.setCount(Long.valueOf(1L));
/* 1280 */               this.ssidService.addPortalssid(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 1283 */             logger.error("==============ERROR Start=============");
/* 1284 */             logger.error(e);
/* 1285 */             logger.error("ERROR INFO ", e);
/* 1286 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/* 1289 */         if (stringUtils.isNotBlank(apmac)) {
/*      */           try {
/* 1291 */             PortalapQuery apq = new PortalapQuery();
/* 1292 */             apq.setMac(apmac);
/* 1293 */             apq.setMacLike(false);
/* 1294 */             List aps = this.apService.getPortalapList(apq);
/* 1295 */             if ((aps != null) && (aps.size() > 0)) {
/* 1296 */               Portalap ap = (Portalap)aps.get(0);
/* 1297 */               ap.setBasip(basip);
/* 1298 */               long count = ap.getCount().longValue() + 1L;
/* 1299 */               ap.setCount(Long.valueOf(count));
/* 1300 */               this.apService.updatePortalapByKey(ap);
/*      */             } else {
/* 1302 */               Portalap ap = new Portalap();
/* 1303 */               ap.setMac(apmac);
/* 1304 */               ap.setBasip(basip);
/* 1305 */               ap.setCount(Long.valueOf(1L));
/* 1306 */               this.apService.addPortalap(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 1309 */             logger.error("==============ERROR Start=============");
/* 1310 */             logger.error(e);
/* 1311 */             logger.error("ERROR INFO ", e);
/* 1312 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/*      */ 
/* 1316 */         SangforLogin(ip, name + phone, ikmac, apmac, basip);
/*      */ 
/* 1318 */         map.put("ret", "0");
/* 1319 */         map.put("i", ip);
/* 1320 */         map.put("u", name + phone);
/* 1321 */         map.put("w", authUrl);
/* 1322 */         map.put("msg", "认证成功！");
/* 1323 */         return map;
/*      */       }
/* 1325 */       map.put("ret", "1");
/* 1326 */       map.put("i", ip);
/* 1327 */       map.put("u", name + phone);
/* 1328 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/* 1329 */       return map;
/*      */     }
/*      */ 
/* 1333 */     map.put("ret", "3");
/* 1334 */     map.put("i", ip);
/* 1335 */     map.put("u", name + phone);
/* 1336 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/* 1337 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/check_weixin"})
/*      */   public Map<String, String> check_weixin(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1348 */     HttpSession session = request.getSession();
/* 1349 */     String ip = (String)session.getAttribute("ip");
/* 1350 */     String basip = (String)session.getAttribute("basip");
/*      */ 
/* 1352 */     Map map = new HashMap();
/* 1353 */     if ((stringUtils.isBlank(basip)) || (stringUtils.isBlank(ip))) {
/* 1354 */       map.put("ret", "1");
/* 1355 */       return map;
/*      */     }
/*      */     try {
/* 1358 */       String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
/* 1359 */         ip + ":" + basip);
/* 1360 */       Date wxt = 
/* 1361 */         (Date)WeixinMap.getInstance().getWeixinipmap()
/* 1361 */         .get(ip + ":" + basip);
/* 1362 */       if ((loginInfo != null) && (wxt == null)) {
/* 1363 */         session.setAttribute("username", "微信认证");
/* 1364 */         session.setAttribute("ip", loginInfo[9]);
/* 1365 */         session.setAttribute("basip", loginInfo[10]);
/* 1366 */         session.setAttribute("ikmac", loginInfo[4]);
/* 1367 */         map.put("ret", "0");
/* 1368 */         return map;
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {
/* 1372 */       map.put("ret", "1");
/* 1373 */     }return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_gzh"})
/*      */   public Map<String, String> gzh(String ip, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1381 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 1382 */     Map map = new HashMap();
/*      */ 
/* 1384 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/* 1387 */       String web = (String)session.getAttribute("web");
/* 1388 */       if (stringUtils.isNotBlank(web)) {
/* 1389 */         while (web.endsWith("/")) {
/* 1390 */           web = web.substring(0, web.length() - 1);
/*      */         }
/* 1392 */         Long webID = Long.valueOf(web);
/* 1393 */         if (webID.longValue() != 0L) {
/* 1394 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/* 1395 */           if (portalweb != null) {
/* 1396 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/* 1397 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/* 1400 */           com.leeson.core.bean.Config config = this.configService
/* 1401 */             .getConfigByKey(Long.valueOf(1L));
/* 1402 */           if (config != null) {
/* 1403 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1404 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/* 1408 */         com.leeson.core.bean.Config config = this.configService
/* 1409 */           .getConfigByKey(Long.valueOf(1L));
/* 1410 */         if (config != null) {
/* 1411 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1412 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/* 1419 */     String ikmac = umac;
/* 1420 */     if (stringUtils.isBlank(ikmac)) {
/* 1421 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/* 1423 */     if (stringUtils.isEmpty(ssid)) {
/* 1424 */       ssid = (String)session.getAttribute("ssid");
/*      */     }
/* 1426 */     if (stringUtils.isEmpty(apmac)) {
/* 1427 */       apmac = (String)session.getAttribute("apmac");
/*      */     }
/* 1429 */     String ikweb = (String)session.getAttribute("ikweb");
/* 1430 */     String authUrlWeb = ikweb;
/*      */ 
/* 1433 */     Cookie[] cookies = request.getCookies();
/* 1434 */     String cip = "";
/* 1435 */     String cbasip = "";
/* 1436 */     String cmac = "";
/* 1437 */     if (cookies != null) {
/* 1438 */       for (int i = 0; i < cookies.length; i++) {
/* 1439 */         if (cookies[i].getName().equals("ip"))
/* 1440 */           cip = cookies[i].getValue();
/* 1441 */         else if (cookies[i].getName().equals("basip"))
/* 1442 */           cbasip = cookies[i].getValue();
/* 1443 */         else if (cookies[i].getName().equals("mac")) {
/* 1444 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 1448 */     if (stringUtils.isBlank(ikmac)) {
/* 1449 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 1452 */     if (stringUtils.isBlank(ip)) {
/* 1453 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 1455 */     if (stringUtils.isBlank(ip)) {
/* 1456 */       ip = cip;
/*      */     }
/* 1458 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 1460 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 1463 */     if (stringUtils.isBlank(basip)) {
/* 1464 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 1466 */     if (stringUtils.isBlank(basip)) {
/* 1467 */       basip = cbasip;
/*      */     }
/* 1469 */     if (stringUtils.isBlank(basip)) {
/* 1470 */       basip = basConfig.getBasIp();
/*      */     }
/* 1472 */     if (config.getConfigMap().get(basip) == null) {
/* 1473 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 1476 */     String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
/* 1477 */     if (checkResult != "") {
/* 1478 */       map.put("ret", "1");
/* 1479 */       map.put("msg", checkResult);
/* 1480 */       return map;
/*      */     }
/*      */ 
/* 1484 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/* 1486 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1486 */       .equals("4")))
/*      */     {
/* 1488 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 1490 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 1492 */       if (stringUtils.isBlank(ikmac)) {
/* 1493 */         map.put("ret", "10");
/* 1494 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 1495 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1500 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 1501 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/* 1502 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/* 1503 */       ip = ikmac;
/* 1504 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 1506 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 1508 */       if (stringUtils.isBlank(ikmac)) {
/* 1509 */         map.put("ret", "10");
/* 1510 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 1511 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1516 */     if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
/* 1517 */       logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
/* 1518 */         ip + " mac=" + ikmac);
/*      */     }
/*      */ 
/* 1521 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 1522 */       ip + ":" + basip);
/* 1523 */     if (isLogin) {
/* 1524 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/* 1525 */         map.put("ret", "119");
/* 1526 */         map.put("msg", "你已经通过验证,或者下线后重试！！");
/* 1527 */         return map;
/*      */       }
/* 1529 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/* 1533 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 1533 */       Integer.valueOf(
/* 1534 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1534 */       .get("core"))[1]).intValue())
/*      */     {
/* 1535 */       map.put("ret", "110");
/* 1536 */       map.put("msg", "网络暂时不可用，请稍后再试！！");
/* 1537 */       return map;
/*      */     }
/*      */ 
/* 1540 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/* 1542 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1542 */       .equals("6")) || 
/* 1544 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1544 */       .equals("7")) || 
/* 1546 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1546 */       .equals("8"))) {
/* 1547 */       map.put("ret", "1");
/* 1548 */       map.put("msg", "该设备类型错误！！");
/* 1549 */       return map;
/*      */     }
/*      */ 
/* 1552 */     String userAgent = request.getHeader("user-agent");
/*      */ 
/* 1554 */     boolean isComputer = false;
/* 1555 */     if (stringUtils.isNotBlank(userAgent)) {
/* 1556 */       if (userAgent.contains("Windows"))
/* 1557 */         isComputer = true;
/* 1558 */       else if (userAgent.contains("Macintosh")) {
/* 1559 */         isComputer = true;
/*      */       }
/* 1561 */       else if ((userAgent.contains("Android")) || 
/* 1562 */         (userAgent.contains("iPhone")) || 
/* 1563 */         (userAgent.contains("iPod")) || 
/* 1564 */         (userAgent.contains("iPad"))) {
/* 1565 */         isComputer = false;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1570 */     if (isComputer) {
/* 1571 */       map.put("ret", "1");
/* 1572 */       map.put("msg", "该认证模式仅支持手机使用！！");
/* 1573 */       return map;
/*      */     }
/*      */ 
/* 1576 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("6"))
/*      */     {
/* 1578 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(7L))) {
/* 1579 */         map.put("ret", "1");
/* 1580 */         map.put("i", ip);
/* 1581 */         map.put("u", "公众号认证");
/* 1582 */         map.put("msg", "该设备当日时长已用完！！");
/* 1583 */         return map;
/*      */       }
/*      */ 
/* 1586 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1587 */       bsq.setBasip(basip);
/* 1588 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 1589 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 1590 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 1591 */         .getPortalbasauthList(bsq);
/* 1592 */       if (basauths.size() > 0) {
/* 1593 */         for (Portalbasauth ba : basauths) {
/* 1594 */           if (ba.getType().intValue() == 6) {
/* 1595 */             authUser = ba.getUsername();
/* 1596 */             authPwd = ba.getPassword();
/* 1597 */             authUrlWeb = ba.getUrl();
/* 1598 */             if ((stringUtils.isBlank(authUser)) || 
/* 1599 */               (stringUtils.isBlank(authPwd))) {
/* 1600 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 1601 */                 .getBasUser();
/* 1602 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 1603 */                 .getBasPwd();
/*      */             }
/* 1605 */             if (!stringUtils.isBlank(authUrlWeb)) break;
/* 1606 */             authUrlWeb = ikweb;
/*      */ 
/* 1608 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1612 */       if (stringUtils.isBlank(authUrlWeb)) {
/* 1613 */         authUrlWeb = 
/* 1614 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1614 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 1617 */       Boolean info = Boolean.valueOf(false);
/*      */ 
/* 1620 */       if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1620 */         .equals("3")) {
/* 1621 */         String site = (String)session.getAttribute("site");
/* 1622 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 1623 */         aq.setLoginName(authUser);
/* 1624 */         aq.setLoginNameLike(false);
/* 1625 */         List accs = this.accountService
/* 1626 */           .getPortalaccountList(aq);
/* 1627 */         int accTime = 1440;
/* 1628 */         if (accs.size() == 1) {
/* 1629 */           long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/* 1630 */           if (accTimeLong > 0L) {
/* 1631 */             accTime = (int)accTimeLong;
/*      */           }
/*      */         }
/* 1634 */         info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
/* 1635 */           basip));
/*      */       } else {
/* 1637 */         info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 1638 */           authPwd, ip, basip, ikmac);
/*      */       }
/*      */ 
/* 1641 */       if (info.booleanValue())
/*      */       {
/* 1643 */         if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
/* 1644 */           ikmac = 
/* 1645 */             (String)ipMacMap.getInstance().getIpMacMap()
/* 1645 */             .get(ip + ":" + basip);
/*      */         }
/* 1647 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(7L), request.getSession(), 
/* 1648 */           authUser, authPwd, "公众号认证", response);
/*      */ 
/* 1650 */         session.setAttribute("username", "公众号认证");
/*      */ 
/* 1652 */         session.setAttribute("ip", ip);
/* 1653 */         session.setAttribute("basip", basip);
/* 1654 */         session.setAttribute("ikweb", authUrlWeb);
/* 1655 */         session.setAttribute("ikmac", ikmac);
/*      */ 
/* 1665 */         String[] loginInfo = new String[16];
/* 1666 */         loginInfo[0] = "公众号认证-临时放行";
/* 1667 */         Date now = new Date();
/* 1668 */         loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 1669 */         loginInfo[4] = ikmac;
/* 1670 */         loginInfo[6] = "6";
/* 1671 */         loginInfo[7] = "0";
/* 1672 */         loginInfo[8] = "0";
/*      */ 
/* 1674 */         loginInfo[9] = ip;
/* 1675 */         loginInfo[10] = basip;
/* 1676 */         loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 1677 */         loginInfo[12] = ssid;
/* 1678 */         loginInfo[13] = apmac;
/* 1679 */         loginInfo[14] = "no";
/* 1680 */         loginInfo[15] = getAgent(request);
/*      */ 
/* 1682 */         onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/* 1683 */         WeixinMap.getInstance().getWeixinipmap()
/* 1684 */           .put(ip + ":" + basip, now);
/*      */ 
/* 1686 */         Portallogrecord logRecord = new Portallogrecord();
/*      */ 
/* 1688 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 1689 */           " 用户: 【公众号认证-临时放行】,登录成功!");
/* 1690 */         logRecord.setRecDate(now);
/* 1691 */         this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 1693 */         Long time = Long.valueOf(new Date().getTime());
/*      */ 
/* 1695 */         Portalweixinwifi wifi = null;
/* 1696 */         if (stringUtils.isNotBlank(ssid)) {
/* 1697 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/* 1698 */           wxq.setSsid(ssid);
/* 1699 */           wxq.setSsidLike(false);
/* 1700 */           wxq.setBasip(basip);
/* 1701 */           wxq.setBasipLike(false);
/* 1702 */           List wxs = this.weixinwifiService
/* 1703 */             .getPortalweixinwifiList(wxq);
/* 1704 */           if (wxs.size() > 0) {
/* 1705 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           } else {
/* 1707 */             wxq = new PortalweixinwifiQuery();
/* 1708 */             wxq.setSsid(ssid);
/* 1709 */             wxq.setSsidLike(false);
/* 1710 */             wxs = this.weixinwifiService.getPortalweixinwifiList(wxq);
/* 1711 */             if (wxs.size() > 0) {
/* 1712 */               wifi = (Portalweixinwifi)wxs.get(0);
/*      */             }
/*      */           }
/*      */         }
/* 1716 */         if (wifi == null) {
/* 1717 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/* 1718 */           wxq.setBasip(basip);
/* 1719 */           wxq.setBasipLike(false);
/* 1720 */           List wxs = this.weixinwifiService
/* 1721 */             .getPortalweixinwifiList(wxq);
/* 1722 */           if (wxs.size() > 0) {
/* 1723 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           }
/*      */         }
/* 1726 */         if (wifi == null) {
/* 1727 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/* 1729 */         if (!Do()) {
/* 1730 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/*      */ 
/* 1733 */         if (wifi != null) {
/* 1734 */           String[] magicInfo = new String[3];
/* 1735 */           magicInfo[0] = (ip + ":" + basip);
/* 1736 */           magicInfo[1] = "";
/* 1737 */           magicInfo[2] = ikmac;
/* 1738 */           MagicMap.getInstance().getMagicMap()
/* 1739 */             .put(ip + ":" + basip, magicInfo);
/*      */ 
/* 1741 */           String appId = wifi.getAppId();
/* 1742 */           String extend = ip + ":" + basip;
/* 1743 */           String timestamp = String.valueOf(time);
/* 1744 */           String shop_id = wifi.getShopId();
/*      */ 
/* 1748 */           String authUrl = request.getScheme() + "://" + 
/* 1749 */             request.getServerName() + ":" + 
/* 1750 */             request.getServerPort() + 
/* 1751 */             request.getContextPath() + "/gzhAuth.action";
/* 1752 */           ssid = wifi.getSsid();
/* 1753 */           String secretKey = wifi.getSecretKey();
/* 1754 */           String mac = "";
/* 1755 */           String bssid = "";
/*      */ 
/* 1757 */           String code = appId + extend + timestamp + shop_id + 
/* 1758 */             authUrl + mac + ssid + bssid + secretKey;
/* 1759 */           String sign = DigestUtils.md5Hex(code);
/*      */ 
/* 1762 */           if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug()
/* 1762 */             .equals("1")) {
/* 1763 */             logger.info("finally ssid=" + ssid + " basip=" + basip + 
/* 1764 */               " ip=" + ip + " code=" + code + " sign" + 
/* 1765 */               sign);
/*      */           }
/*      */ 
/* 1768 */           if (stringUtils.isNotBlank(ssid)) {
/*      */             try {
/* 1770 */               PortalssidQuery apq = new PortalssidQuery();
/* 1771 */               apq.setSsid(ssid);
/* 1772 */               apq.setSsidLike(false);
/* 1773 */               List aps = this.ssidService
/* 1774 */                 .getPortalssidList(apq);
/* 1775 */               if ((aps != null) && (aps.size() > 0)) {
/* 1776 */                 Portalssid ap = (Portalssid)aps.get(0);
/* 1777 */                 ap.setBasip(basip);
/* 1778 */                 long count = ap.getCount().longValue() + 1L;
/* 1779 */                 ap.setCount(Long.valueOf(count));
/* 1780 */                 this.ssidService.updatePortalssidByKey(ap);
/*      */               } else {
/* 1782 */                 Portalssid ap = new Portalssid();
/* 1783 */                 ap.setSsid(ssid);
/* 1784 */                 ap.setBasip(basip);
/* 1785 */                 ap.setCount(Long.valueOf(1L));
/* 1786 */                 this.ssidService.addPortalssid(ap);
/*      */               }
/*      */             } catch (Exception e) {
/* 1789 */               logger.error("==============ERROR Start=============");
/* 1790 */               logger.error(e);
/* 1791 */               logger.error("ERROR INFO ", e);
/* 1792 */               logger.error("==============ERROR End=============");
/*      */             }
/*      */           }
/* 1795 */           if (stringUtils.isNotBlank(apmac)) {
/*      */             try {
/* 1797 */               PortalapQuery apq = new PortalapQuery();
/* 1798 */               apq.setMac(apmac);
/* 1799 */               apq.setMacLike(false);
/* 1800 */               List aps = this.apService.getPortalapList(apq);
/* 1801 */               if ((aps != null) && (aps.size() > 0)) {
/* 1802 */                 Portalap ap = (Portalap)aps.get(0);
/* 1803 */                 ap.setBasip(basip);
/* 1804 */                 long count = ap.getCount().longValue() + 1L;
/* 1805 */                 ap.setCount(Long.valueOf(count));
/* 1806 */                 this.apService.updatePortalapByKey(ap);
/*      */               } else {
/* 1808 */                 Portalap ap = new Portalap();
/* 1809 */                 ap.setMac(apmac);
/* 1810 */                 ap.setBasip(basip);
/* 1811 */                 ap.setCount(Long.valueOf(1L));
/* 1812 */                 this.apService.addPortalap(ap);
/*      */               }
/*      */             } catch (Exception e) {
/* 1815 */               logger.error("==============ERROR Start=============");
/* 1816 */               logger.error(e);
/* 1817 */               logger.error("ERROR INFO ", e);
/* 1818 */               logger.error("==============ERROR End=============");
/*      */             }
/*      */           }
/*      */ 
/* 1822 */           map.put("ret", "0");
/* 1823 */           map.put("appId", appId);
/* 1824 */           map.put("extend", extend);
/* 1825 */           map.put("timestamp", timestamp);
/* 1826 */           map.put("sign", sign);
/* 1827 */           map.put("shop_id", shop_id);
/* 1828 */           map.put("authUrl", authUrl);
/* 1829 */           map.put("mac", mac);
/* 1830 */           map.put("ssid", ssid);
/* 1831 */           map.put("bssid", bssid);
/* 1832 */           return map;
/*      */         }
/* 1834 */         map.put("ret", "1");
/* 1835 */         map.put("msg", "公众号认证参数未配置，请联系管理员！！");
/* 1836 */         return map;
/*      */       }
/*      */ 
/* 1840 */       map.put("ret", "1");
/* 1841 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/* 1842 */       return map;
/*      */     }
/*      */ 
/* 1845 */     map.put("ret", "3");
/* 1846 */     map.put("msg", "系统不是公众号认证模式！！！！");
/* 1847 */     return map;
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/gzhAuth.action"})
/*      */   public void gzhAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
/*      */     throws IOException
/*      */   {
/* 1858 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 1859 */     if (basConfig.getIsdebug().equals("1")) {
/* 1860 */       logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
/* 1861 */         " openId=" + openId + " tid=" + tid);
/*      */     }
/*      */ 
/* 1864 */     String[] magicInfo = (String[])MagicMap.getInstance().getMagicMap().get(extend);
/* 1865 */     if ((magicInfo != null) && (magicInfo.length >= 3)) {
/* 1866 */       OpenIdMap.getInstance().getOpenIdMap().put(openId, magicInfo);
/* 1867 */       MagicMap.getInstance().getMagicMap().remove(extend);
/*      */ 
/* 1869 */       String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
/* 1870 */       if ((userInfo != null) && (userInfo.length > 0)) {
/* 1871 */         String username = openId;
/* 1872 */         userInfo[0] = username;
/* 1873 */         onlineMap.getOnlineUserMap().put(extend, userInfo);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1878 */     String respMessage = "HTTP/1.0 200 OK\r\n";
/* 1879 */     PrintWriter out = response.getWriter();
/* 1880 */     out.print(respMessage);
/* 1881 */     out.close();
/* 1882 */     WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_weixin"})
/*      */   public Map<String, String> weixin(String ip, String basip, String umac, String ssid, String apmac, String htmlUrl, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1890 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 1891 */     Map map = new HashMap();
/*      */ 
/* 1893 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/* 1896 */       String web = (String)session.getAttribute("web");
/* 1897 */       if (stringUtils.isNotBlank(web)) {
/* 1898 */         while (web.endsWith("/")) {
/* 1899 */           web = web.substring(0, web.length() - 1);
/*      */         }
/* 1901 */         Long webID = Long.valueOf(web);
/* 1902 */         if (webID.longValue() != 0L) {
/* 1903 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/* 1904 */           if (portalweb != null) {
/* 1905 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/* 1906 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/* 1909 */           com.leeson.core.bean.Config config = this.configService
/* 1910 */             .getConfigByKey(Long.valueOf(1L));
/* 1911 */           if (config != null) {
/* 1912 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1913 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/* 1917 */         com.leeson.core.bean.Config config = this.configService
/* 1918 */           .getConfigByKey(Long.valueOf(1L));
/* 1919 */         if (config != null) {
/* 1920 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1921 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/* 1928 */     String ikmac = umac;
/* 1929 */     if (stringUtils.isBlank(ikmac)) {
/* 1930 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/* 1932 */     if (stringUtils.isEmpty(ssid)) {
/* 1933 */       ssid = (String)session.getAttribute("ssid");
/*      */     }
/* 1935 */     if (stringUtils.isEmpty(apmac)) {
/* 1936 */       apmac = (String)session.getAttribute("apmac");
/*      */     }
/* 1938 */     String ikweb = (String)session.getAttribute("ikweb");
/* 1939 */     String authUrlWeb = ikweb;
/*      */ 
/* 1942 */     Cookie[] cookies = request.getCookies();
/* 1943 */     String cip = "";
/* 1944 */     String cbasip = "";
/* 1945 */     String cmac = "";
/* 1946 */     if (cookies != null) {
/* 1947 */       for (int i = 0; i < cookies.length; i++) {
/* 1948 */         if (cookies[i].getName().equals("ip"))
/* 1949 */           cip = cookies[i].getValue();
/* 1950 */         else if (cookies[i].getName().equals("basip"))
/* 1951 */           cbasip = cookies[i].getValue();
/* 1952 */         else if (cookies[i].getName().equals("mac")) {
/* 1953 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 1957 */     if (stringUtils.isBlank(ikmac)) {
/* 1958 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 1961 */     if (stringUtils.isBlank(ip)) {
/* 1962 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 1964 */     if (stringUtils.isBlank(ip)) {
/* 1965 */       ip = cip;
/*      */     }
/* 1967 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 1969 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 1972 */     if (stringUtils.isBlank(basip)) {
/* 1973 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 1975 */     if (stringUtils.isBlank(basip)) {
/* 1976 */       basip = cbasip;
/*      */     }
/* 1978 */     if (stringUtils.isBlank(basip)) {
/* 1979 */       basip = basConfig.getBasIp();
/*      */     }
/* 1981 */     if (config.getConfigMap().get(basip) == null) {
/* 1982 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 1985 */     String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
/* 1986 */     if (checkResult != "") {
/* 1987 */       map.put("ret", "1");
/* 1988 */       map.put("msg", checkResult);
/* 1989 */       return map;
/*      */     }
/*      */ 
/* 1993 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/* 1995 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 1995 */       .equals("4")))
/*      */     {
/* 1997 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 1999 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 2001 */       if (stringUtils.isBlank(ikmac)) {
/* 2002 */         map.put("ret", "10");
/* 2003 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 2004 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2009 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 2010 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/* 2011 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/* 2012 */       ip = ikmac;
/* 2013 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 2015 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 2017 */       if (stringUtils.isBlank(ikmac)) {
/* 2018 */         map.put("ret", "10");
/* 2019 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 2020 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2025 */     if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
/* 2026 */       logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
/* 2027 */         ip + " mac=" + ikmac);
/*      */     }
/*      */ 
/* 2030 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 2031 */       ip + ":" + basip);
/* 2032 */     if (isLogin) {
/* 2033 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/* 2034 */         map.put("ret", "119");
/* 2035 */         map.put("msg", "你已经通过验证,或者下线后重试！！");
/* 2036 */         return map;
/*      */       }
/* 2038 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/* 2042 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 2042 */       Integer.valueOf(
/* 2043 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 2043 */       .get("core"))[1]).intValue())
/*      */     {
/* 2044 */       map.put("ret", "110");
/* 2045 */       map.put("msg", "网络暂时不可用，请稍后再试！！");
/* 2046 */       return map;
/*      */     }
/*      */ 
/* 2049 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/* 2051 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 2051 */       .equals("6")) || 
/* 2053 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 2053 */       .equals("7")) || 
/* 2055 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 2055 */       .equals("8"))) {
/* 2056 */       map.put("ret", "1");
/* 2057 */       map.put("msg", "该设备类型错误！！");
/* 2058 */       return map;
/*      */     }
/*      */ 
/* 2061 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("5"))
/*      */     {
/* 2063 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(6L))) {
/* 2064 */         map.put("ret", "1");
/* 2065 */         map.put("i", ip);
/* 2066 */         map.put("u", "微信验证");
/* 2067 */         map.put("msg", "该设备当日时长已用完！！");
/* 2068 */         return map;
/*      */       }
/*      */ 
/* 2071 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 2072 */       bsq.setBasip(basip);
/* 2073 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 2074 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 2075 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 2076 */         .getPortalbasauthList(bsq);
/* 2077 */       if (basauths.size() > 0) {
/* 2078 */         for (Portalbasauth ba : basauths) {
/* 2079 */           if (ba.getType().intValue() == 5) {
/* 2080 */             authUser = ba.getUsername();
/* 2081 */             authPwd = ba.getPassword();
/* 2082 */             authUrlWeb = ba.getUrl();
/* 2083 */             if ((stringUtils.isBlank(authUser)) || 
/* 2084 */               (stringUtils.isBlank(authPwd))) {
/* 2085 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 2086 */                 .getBasUser();
/* 2087 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 2088 */                 .getBasPwd();
/*      */             }
/* 2090 */             if (!stringUtils.isBlank(authUrlWeb)) break;
/* 2091 */             authUrlWeb = ikweb;
/*      */ 
/* 2093 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 2097 */       if (stringUtils.isBlank(authUrlWeb)) {
/* 2098 */         authUrlWeb = 
/* 2099 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 2099 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 2102 */       Boolean info = Boolean.valueOf(false);
/*      */ 
/* 2105 */       if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 2105 */         .equals("3")) {
/* 2106 */         String site = (String)session.getAttribute("site");
/* 2107 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 2108 */         aq.setLoginName(authUser);
/* 2109 */         aq.setLoginNameLike(false);
/* 2110 */         List accs = this.accountService
/* 2111 */           .getPortalaccountList(aq);
/* 2112 */         int accTime = 1440;
/* 2113 */         if (accs.size() == 1) {
/* 2114 */           long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/* 2115 */           if (accTimeLong > 0L) {
/* 2116 */             accTime = (int)accTimeLong;
/*      */           }
/*      */         }
/* 2119 */         info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
/* 2120 */           basip));
/*      */       } else {
/* 2122 */         info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 2123 */           authPwd, ip, basip, ikmac);
/*      */       }
/*      */ 
/* 2126 */       if (info.booleanValue())
/*      */       {
/* 2128 */         if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
/* 2129 */           ikmac = 
/* 2130 */             (String)ipMacMap.getInstance().getIpMacMap()
/* 2130 */             .get(ip + ":" + basip);
/*      */         }
/* 2132 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(6L), request.getSession(), 
/* 2133 */           authUser, authPwd, "微信验证", response);
/*      */ 
/* 2135 */         session.setAttribute("username", "微信验证");
/*      */ 
/* 2137 */         session.setAttribute("ip", ip);
/* 2138 */         session.setAttribute("basip", basip);
/* 2139 */         session.setAttribute("ikweb", authUrlWeb);
/* 2140 */         session.setAttribute("ikmac", ikmac);
/*      */ 
/* 2150 */         String[] loginInfo = new String[16];
/* 2151 */         loginInfo[0] = "微信验证-临时放行";
/* 2152 */         Date now = new Date();
/* 2153 */         loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 2154 */         loginInfo[4] = ikmac;
/* 2155 */         loginInfo[6] = "5";
/* 2156 */         loginInfo[7] = "0";
/* 2157 */         loginInfo[8] = "0";
/*      */ 
/* 2159 */         loginInfo[9] = ip;
/* 2160 */         loginInfo[10] = basip;
/* 2161 */         loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 2162 */         loginInfo[12] = ssid;
/* 2163 */         loginInfo[13] = apmac;
/* 2164 */         loginInfo[14] = "no";
/* 2165 */         loginInfo[15] = getAgent(request);
/*      */ 
/* 2167 */         onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/* 2168 */         WeixinMap.getInstance().getWeixinipmap()
/* 2169 */           .put(ip + ":" + basip, now);
/*      */ 
/* 2171 */         Portallogrecord logRecord = new Portallogrecord();
/*      */ 
/* 2173 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 2174 */           " 用户: 【微信验证-临时放行】,登录成功!");
/* 2175 */         logRecord.setRecDate(now);
/* 2176 */         this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 2178 */         Long time = Long.valueOf(new Date().getTime());
/*      */ 
/* 2180 */         Portalweixinwifi wifi = null;
/* 2181 */         if (stringUtils.isNotBlank(ssid)) {
/* 2182 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/* 2183 */           wxq.setSsid(ssid);
/* 2184 */           wxq.setSsidLike(false);
/* 2185 */           wxq.setBasip(basip);
/* 2186 */           wxq.setBasipLike(false);
/* 2187 */           List wxs = this.weixinwifiService
/* 2188 */             .getPortalweixinwifiList(wxq);
/* 2189 */           if (wxs.size() > 0) {
/* 2190 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           } else {
/* 2192 */             wxq = new PortalweixinwifiQuery();
/* 2193 */             wxq.setSsid(ssid);
/* 2194 */             wxq.setSsidLike(false);
/* 2195 */             wxs = this.weixinwifiService.getPortalweixinwifiList(wxq);
/* 2196 */             if (wxs.size() > 0) {
/* 2197 */               wifi = (Portalweixinwifi)wxs.get(0);
/*      */             }
/*      */           }
/*      */         }
/* 2201 */         if (wifi == null) {
/* 2202 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/* 2203 */           wxq.setBasip(basip);
/* 2204 */           wxq.setBasipLike(false);
/* 2205 */           List wxs = this.weixinwifiService
/* 2206 */             .getPortalweixinwifiList(wxq);
/* 2207 */           if (wxs.size() > 0) {
/* 2208 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           }
/*      */         }
/* 2211 */         if (wifi == null) {
/* 2212 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/* 2214 */         if (!Do()) {
/* 2215 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/*      */ 
/* 2218 */         if (wifi != null) {
/* 2219 */           String appId = wifi.getAppId();
/* 2220 */           String extend = ip + ":" + basip;
/* 2221 */           String timestamp = String.valueOf(time);
/* 2222 */           String shop_id = wifi.getShopId();
/*      */ 
/* 2226 */           String authUrl = request.getScheme() + "://" + 
/* 2227 */             request.getServerName() + ":" + 
/* 2228 */             request.getServerPort() + 
/* 2229 */             request.getContextPath() + "/weixinAuth.action";
/*      */ 
/* 2231 */           String userAgent = request.getHeader("user-agent");
/*      */ 
/* 2233 */           boolean isComputer = false;
/* 2234 */           if (stringUtils.isNotBlank(userAgent)) {
/* 2235 */             if (userAgent.contains("Windows"))
/* 2236 */               isComputer = true;
/* 2237 */             else if (userAgent.contains("Macintosh")) {
/* 2238 */               isComputer = true;
/*      */             }
/* 2240 */             else if ((userAgent.contains("Android")) || 
/* 2241 */               (userAgent.contains("iPhone")) || 
/* 2242 */               (userAgent.contains("iPod")) || 
/* 2243 */               (userAgent.contains("iPad"))) {
/* 2244 */               isComputer = false;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 2249 */           if (isComputer) {
/* 2250 */             if (stringUtils.isNotBlank(htmlUrl))
/* 2251 */               authUrl = htmlUrl + "weixinPCAuth.html";
/*      */             else {
/* 2253 */               authUrl = request.getScheme() + "://" + 
/* 2254 */                 request.getServerName() + ":" + 
/* 2255 */                 request.getServerPort() + 
/* 2256 */                 request.getContextPath() + 
/* 2257 */                 "/weixinPCAuth.action";
/*      */             }
/*      */           }
/*      */ 
/* 2261 */           ssid = wifi.getSsid();
/* 2262 */           String secretKey = wifi.getSecretKey();
/* 2263 */           String mac = "";
/* 2264 */           String bssid = "";
/*      */ 
/* 2266 */           String code = appId + extend + timestamp + shop_id + 
/* 2267 */             authUrl + mac + ssid + bssid + secretKey;
/* 2268 */           String sign = DigestUtils.md5Hex(code);
/*      */ 
/* 2271 */           if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug()
/* 2271 */             .equals("1")) {
/* 2272 */             logger.info("finally ssid=" + ssid + " basip=" + basip + 
/* 2273 */               " ip=" + ip + " code=" + code + " sign" + 
/* 2274 */               sign);
/*      */           }
/*      */ 
/* 2277 */           if (stringUtils.isNotBlank(ssid)) {
/*      */             try {
/* 2279 */               PortalssidQuery apq = new PortalssidQuery();
/* 2280 */               apq.setSsid(ssid);
/* 2281 */               apq.setSsidLike(false);
/* 2282 */               List aps = this.ssidService
/* 2283 */                 .getPortalssidList(apq);
/* 2284 */               if ((aps != null) && (aps.size() > 0)) {
/* 2285 */                 Portalssid ap = (Portalssid)aps.get(0);
/* 2286 */                 ap.setBasip(basip);
/* 2287 */                 long count = ap.getCount().longValue() + 1L;
/* 2288 */                 ap.setCount(Long.valueOf(count));
/* 2289 */                 this.ssidService.updatePortalssidByKey(ap);
/*      */               } else {
/* 2291 */                 Portalssid ap = new Portalssid();
/* 2292 */                 ap.setSsid(ssid);
/* 2293 */                 ap.setBasip(basip);
/* 2294 */                 ap.setCount(Long.valueOf(1L));
/* 2295 */                 this.ssidService.addPortalssid(ap);
/*      */               }
/*      */             } catch (Exception e) {
/* 2298 */               logger.error("==============ERROR Start=============");
/* 2299 */               logger.error(e);
/* 2300 */               logger.error("ERROR INFO ", e);
/* 2301 */               logger.error("==============ERROR End=============");
/*      */             }
/*      */           }
/* 2304 */           if (stringUtils.isNotBlank(apmac)) {
/*      */             try {
/* 2306 */               PortalapQuery apq = new PortalapQuery();
/* 2307 */               apq.setMac(apmac);
/* 2308 */               apq.setMacLike(false);
/* 2309 */               List aps = this.apService.getPortalapList(apq);
/* 2310 */               if ((aps != null) && (aps.size() > 0)) {
/* 2311 */                 Portalap ap = (Portalap)aps.get(0);
/* 2312 */                 ap.setBasip(basip);
/* 2313 */                 long count = ap.getCount().longValue() + 1L;
/* 2314 */                 ap.setCount(Long.valueOf(count));
/* 2315 */                 this.apService.updatePortalapByKey(ap);
/*      */               } else {
/* 2317 */                 Portalap ap = new Portalap();
/* 2318 */                 ap.setMac(apmac);
/* 2319 */                 ap.setBasip(basip);
/* 2320 */                 ap.setCount(Long.valueOf(1L));
/* 2321 */                 this.apService.addPortalap(ap);
/*      */               }
/*      */             } catch (Exception e) {
/* 2324 */               logger.error("==============ERROR Start=============");
/* 2325 */               logger.error(e);
/* 2326 */               logger.error("ERROR INFO ", e);
/* 2327 */               logger.error("==============ERROR End=============");
/*      */             }
/*      */           }
/*      */ 
/* 2331 */           map.put("ret", "0");
/* 2332 */           map.put("appId", appId);
/* 2333 */           map.put("extend", extend);
/* 2334 */           map.put("timestamp", timestamp);
/* 2335 */           map.put("sign", sign);
/* 2336 */           map.put("shop_id", shop_id);
/* 2337 */           map.put("authUrl", authUrl);
/* 2338 */           map.put("mac", mac);
/* 2339 */           map.put("ssid", ssid);
/* 2340 */           map.put("bssid", bssid);
/* 2341 */           return map;
/*      */         }
/* 2343 */         map.put("ret", "1");
/* 2344 */         map.put("msg", "微信认证参数未配置，请联系管理员！！");
/* 2345 */         return map;
/*      */       }
/*      */ 
/* 2349 */       map.put("ret", "1");
/* 2350 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/* 2351 */       return map;
/*      */     }
/*      */ 
/* 2354 */     map.put("ret", "3");
/* 2355 */     map.put("msg", "系统不是微信验证模式！！！！");
/* 2356 */     return map;
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/weixinAuth.action"})
/*      */   public void weixinAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
/*      */     throws IOException
/*      */   {
/* 2367 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 2368 */     if (basConfig.getIsdebug().equals("1")) {
/* 2369 */       logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
/* 2370 */         " openId=" + openId + " tid=" + tid);
/*      */     }
/*      */ 
/* 2373 */     WeixinMap.getInstance().getWeixinipmap().remove(extend);
/* 2374 */     WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap()
/* 2375 */       .remove(extend);
/* 2376 */     if (basConfig.getIsdebug().equals("1")) {
/* 2377 */       logger.info("IP:" + extend + 
/* 2378 */         "Weixin Auth Success , Remove WeixinTempMap !!");
/*      */     }
/* 2380 */     String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
/* 2381 */     if ((userInfo != null) && (userInfo.length > 0)) {
/* 2382 */       userInfo[0] = openId;
/* 2383 */       onlineMap.getOnlineUserMap().put(extend, userInfo);
/* 2384 */       String mac = userInfo[4];
/* 2385 */       String basip = "";
/* 2386 */       String ip = "";
/*      */       try {
/* 2388 */         int i = extend.lastIndexOf(":");
/* 2389 */         basip = extend.substring(i + 1);
/* 2390 */         ip = extend.substring(0, i);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/* 2398 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 2399 */       bsq.setBasip(basip);
/* 2400 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 2401 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 2402 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 2403 */         .getPortalbasauthList(bsq);
/* 2404 */       if (basauths.size() > 0) {
/* 2405 */         for (Portalbasauth ba : basauths) {
/* 2406 */           if (ba.getType().intValue() == 5) {
/* 2407 */             authUser = ba.getUsername();
/* 2408 */             authPwd = ba.getPassword();
/* 2409 */             if ((!stringUtils.isBlank(authUser)) && 
/* 2410 */               (!stringUtils.isBlank(authPwd))) break;
/* 2411 */             authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 2412 */               .getBasUser();
/* 2413 */             authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 2414 */               .getBasPwd();
/*      */ 
/* 2416 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2421 */       if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 2422 */         PortalbasauthQuery baq = new PortalbasauthQuery();
/* 2423 */         baq.setBasid(Long.valueOf(0L));
/* 2424 */         basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 2425 */         if (basauths.size() > 0) {
/* 2426 */           for (Portalbasauth ba : basauths) {
/* 2427 */             if (ba.getType().intValue() == 5) {
/* 2428 */               authUser = ba.getUsername();
/* 2429 */               authPwd = ba.getPassword();
/* 2430 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2435 */       if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 2436 */         authUser = basConfig.getBasUser();
/* 2437 */         authPwd = basConfig.getBasPwd();
/*      */       }
/* 2439 */       AutoLoginPutMethod(mac, Long.valueOf(6L), authUser, authPwd, userInfo[0]);
/*      */ 
/* 2441 */       SangforLogin(ip, userInfo[0], mac, "", basip);
/*      */     }
/*      */ 
/* 2444 */     String respMessage = "HTTP/1.0 200 OK\r\n";
/* 2445 */     PrintWriter out = response.getWriter();
/* 2446 */     out.print(respMessage);
/* 2447 */     out.close();
/* 2448 */     WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/weixinHtmlPCAuth.action"})
/*      */   public Map<String, String> weixinHtmlPCAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
/*      */     throws IOException
/*      */   {
/* 2459 */     extend = URLDecoder.decode(extend);
/* 2460 */     Map map = new HashMap();
/* 2461 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 2462 */     if (basConfig.getIsdebug().equals("1")) {
/* 2463 */       logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
/* 2464 */         " openId=" + openId + " tid=" + tid);
/*      */     }
/*      */ 
/* 2467 */     WeixinMap.getInstance().getWeixinipmap().remove(extend);
/* 2468 */     WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap()
/* 2469 */       .remove(extend);
/* 2470 */     if (basConfig.getIsdebug().equals("1")) {
/* 2471 */       logger.info("IP:" + extend + 
/* 2472 */         "Weixin Auth Success , Remove WeixinTempMap !!");
/*      */     }
/* 2474 */     HttpSession session = request.getSession();
/* 2475 */     String ikweb = (String)session.getAttribute("ikweb");
/* 2476 */     String username = openId;
/* 2477 */     String basip = "";
/* 2478 */     String authUrlWeb = "";
/* 2479 */     String ip = "";
/*      */     try {
/* 2481 */       int i = extend.lastIndexOf(":");
/* 2482 */       basip = extend.substring(i + 1);
/* 2483 */       ip = extend.substring(0, i);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/* 2491 */     PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 2492 */     bsq.setBasip(basip);
/* 2493 */     String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 2494 */     String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 2495 */     List<Portalbasauth> basauths = this.portalbasauthService
/* 2496 */       .getPortalbasauthList(bsq);
/* 2497 */     if (basauths.size() > 0) {
/* 2498 */       for (Portalbasauth ba : basauths) {
/* 2499 */         if (ba.getType().intValue() == 5) {
/* 2500 */           authUser = ba.getUsername();
/* 2501 */           authPwd = ba.getPassword();
/* 2502 */           authUrlWeb = ba.getUrl();
/* 2503 */           if ((stringUtils.isBlank(authUser)) || 
/* 2504 */             (stringUtils.isBlank(authPwd))) {
/* 2505 */             authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 2506 */               .getBasUser();
/* 2507 */             authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/*      */           }
/* 2509 */           if (!stringUtils.isBlank(authUrlWeb)) break;
/* 2510 */           authUrlWeb = ikweb;
/*      */ 
/* 2512 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2517 */     if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 2518 */       PortalbasauthQuery baq = new PortalbasauthQuery();
/* 2519 */       baq.setBasid(Long.valueOf(0L));
/* 2520 */       basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 2521 */       if (basauths.size() > 0) {
/* 2522 */         for (Portalbasauth ba : basauths) {
/* 2523 */           if (ba.getType().intValue() == 5) {
/* 2524 */             authUser = ba.getUsername();
/* 2525 */             authPwd = ba.getPassword();
/* 2526 */             authUrlWeb = ba.getUrl();
/* 2527 */             if (!stringUtils.isBlank(authUrlWeb)) break;
/* 2528 */             authUrlWeb = ikweb;
/*      */ 
/* 2530 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2535 */     if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 2536 */       authUser = basConfig.getBasUser();
/* 2537 */       authPwd = basConfig.getBasPwd();
/*      */     }
/*      */ 
/* 2540 */     if (stringUtils.isBlank(authUrlWeb)) {
/* 2541 */       authUrlWeb = 
/* 2542 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 2542 */         .get("core"))[0];
/*      */     }
/* 2544 */     ikweb = authUrlWeb;
/*      */ 
/* 2546 */     String mac = "";
/* 2547 */     String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
/* 2548 */     if ((userInfo != null) && (userInfo.length > 0)) {
/* 2549 */       userInfo[0] = username;
/*      */ 
/* 2554 */       onlineMap.getOnlineUserMap().put(extend, userInfo);
/* 2555 */       mac = userInfo[4];
/* 2556 */       AutoLoginPutMethod(mac, Long.valueOf(6L), authUser, authPwd, userInfo[0]);
/*      */ 
/* 2558 */       String apmac = (String)session.getAttribute("apmac");
/* 2559 */       SangforLogin(ip, userInfo[0], mac, apmac, basip);
/*      */     }
/* 2561 */     WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
/* 2562 */     String webMod = (String)session.getAttribute("web");
/* 2563 */     session.setAttribute("ip", ip);
/* 2564 */     session.setAttribute("basip", basip);
/* 2565 */     session.setAttribute("ikweb", ikweb);
/* 2566 */     session.setAttribute("username", "微信认证");
/*      */ 
/* 2568 */     if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
/* 2569 */       mac = (String)ipMacMap.getInstance().getIpMacMap().get(ip + ":" + basip);
/*      */     }
/* 2571 */     if (stringUtils.isNotBlank(mac)) {
/* 2572 */       session.setAttribute("ikmac", mac);
/*      */     }
/*      */ 
/* 2575 */     if (stringUtils.isEmpty(webMod)) {
/* 2576 */       webMod = "";
/*      */     }
/* 2578 */     session.setAttribute("web", webMod);
/*      */ 
/* 2580 */     map.put("ret", "0");
/* 2581 */     map.put("i", ip);
/* 2582 */     map.put("u", "微信认证");
/* 2583 */     map.put("w", ikweb);
/* 2584 */     map.put("msg", "认证成功！");
/* 2585 */     return map;
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/weixinPCAuth.action"})
/*      */   public void weixinPCAuth(HttpServletResponse response, HttpServletRequest request, @RequestParam("extend") String extend, @RequestParam("openId") String openId, @RequestParam("tid") String tid)
/*      */     throws IOException
/*      */   {
/* 2594 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 2595 */     if (basConfig.getIsdebug().equals("1")) {
/* 2596 */       logger.info("WeiXin Server Send Auth Msg !! extend=" + extend + 
/* 2597 */         " openId=" + openId + " tid=" + tid);
/*      */     }
/*      */ 
/* 2600 */     WeixinMap.getInstance().getWeixinipmap().remove(extend);
/* 2601 */     WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap()
/* 2602 */       .remove(extend);
/* 2603 */     if (basConfig.getIsdebug().equals("1")) {
/* 2604 */       logger.info("IP:" + extend + 
/* 2605 */         "Weixin Auth Success , Remove WeixinTempMap !!");
/*      */     }
/* 2607 */     HttpSession session = request.getSession();
/* 2608 */     String ikweb = (String)session.getAttribute("ikweb");
/* 2609 */     String username = openId;
/* 2610 */     String basip = "";
/* 2611 */     String authUrlWeb = "";
/* 2612 */     String ip = "";
/*      */     try {
/* 2614 */       int i = extend.lastIndexOf(":");
/* 2615 */       basip = extend.substring(i + 1);
/* 2616 */       ip = extend.substring(0, i);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/* 2624 */     PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 2625 */     bsq.setBasip(basip);
/* 2626 */     String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 2627 */     String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 2628 */     List<Portalbasauth> basauths = this.portalbasauthService
/* 2629 */       .getPortalbasauthList(bsq);
/* 2630 */     if (basauths.size() > 0) {
/* 2631 */       for (Portalbasauth ba : basauths) {
/* 2632 */         if (ba.getType().intValue() == 5) {
/* 2633 */           authUser = ba.getUsername();
/* 2634 */           authPwd = ba.getPassword();
/* 2635 */           authUrlWeb = ba.getUrl();
/* 2636 */           if ((stringUtils.isBlank(authUser)) || 
/* 2637 */             (stringUtils.isBlank(authPwd))) {
/* 2638 */             authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 2639 */               .getBasUser();
/* 2640 */             authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/*      */           }
/* 2642 */           if (!stringUtils.isBlank(authUrlWeb)) break;
/* 2643 */           authUrlWeb = ikweb;
/*      */ 
/* 2645 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2650 */     if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 2651 */       PortalbasauthQuery baq = new PortalbasauthQuery();
/* 2652 */       baq.setBasid(Long.valueOf(0L));
/* 2653 */       basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 2654 */       if (basauths.size() > 0) {
/* 2655 */         for (Portalbasauth ba : basauths) {
/* 2656 */           if (ba.getType().intValue() == 5) {
/* 2657 */             authUser = ba.getUsername();
/* 2658 */             authPwd = ba.getPassword();
/* 2659 */             authUrlWeb = ba.getUrl();
/* 2660 */             if (!stringUtils.isBlank(authUrlWeb)) break;
/* 2661 */             authUrlWeb = ikweb;
/*      */ 
/* 2663 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2668 */     if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 2669 */       authUser = basConfig.getBasUser();
/* 2670 */       authPwd = basConfig.getBasPwd();
/*      */     }
/*      */ 
/* 2673 */     if (stringUtils.isBlank(authUrlWeb)) {
/* 2674 */       authUrlWeb = 
/* 2675 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 2675 */         .get("core"))[0];
/*      */     }
/* 2677 */     ikweb = authUrlWeb;
/*      */ 
/* 2679 */     String mac = "";
/* 2680 */     String[] userInfo = (String[])onlineMap.getOnlineUserMap().get(extend);
/* 2681 */     if ((userInfo != null) && (userInfo.length > 0)) {
/* 2682 */       userInfo[0] = username;
/*      */ 
/* 2687 */       onlineMap.getOnlineUserMap().put(extend, userInfo);
/* 2688 */       mac = userInfo[4];
/* 2689 */       AutoLoginPutMethod(mac, Long.valueOf(6L), authUser, authPwd, userInfo[0]);
/*      */ 
/* 2691 */       String apmac = (String)session.getAttribute("apmac");
/* 2692 */       SangforLogin(ip, userInfo[0], mac, apmac, basip);
/*      */     }
/* 2694 */     WeixinOpenIDMap.getInstance().getWeixinOpenIDMap().put(extend, openId);
/* 2695 */     String webMod = (String)session.getAttribute("web");
/* 2696 */     session.setAttribute("ip", ip);
/* 2697 */     session.setAttribute("basip", basip);
/* 2698 */     session.setAttribute("ikweb", ikweb);
/* 2699 */     session.setAttribute("username", "微信认证");
/*      */ 
/* 2701 */     if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
/* 2702 */       mac = (String)ipMacMap.getInstance().getIpMacMap().get(ip + ":" + basip);
/*      */     }
/* 2704 */     if (stringUtils.isNotBlank(mac)) {
/* 2705 */       session.setAttribute("ikmac", mac);
/*      */     }
/*      */ 
/* 2708 */     if (stringUtils.isEmpty(webMod)) {
/* 2709 */       webMod = "";
/*      */     }
/* 2711 */     session.setAttribute("web", webMod);
/*      */     try
/*      */     {
/* 2714 */       String api = (String)session.getAttribute("api");
/* 2715 */       if ((api != null) && (!"default".equals(api)))
/* 2716 */         request.getRequestDispatcher("/" + webMod + "APIok.jsp")
/* 2717 */           .forward(request, response);
/*      */       else
/* 2719 */         request.getRequestDispatcher("/" + webMod + "ok.jsp").forward(
/* 2720 */           request, response);
/*      */     }
/*      */     catch (ServletException localServletException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_token"})
/*      */   public Map<String, String> token(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2736 */     Map map = new HashMap();
/* 2737 */     String token = MyUtils.creatToken();
/* 2738 */     AppTokenMap.getInstance().getTokenMap().put(token, token);
/*      */ 
/* 2740 */     map.put("token", token);
/* 2741 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_info"})
/*      */   public Map<String, String> info(HttpServletResponse response, HttpServletRequest request) throws IOException
/*      */   {
/* 2749 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 2750 */     String filePath = request.getServletContext().getRealPath("/") + 
/* 2751 */       "/info.txt";
/* 2752 */     String msg = "";
/*      */     try {
/* 2754 */       String encoding = "utf-8";
/* 2755 */       File file = new File(filePath);
/* 2756 */       if ((file.isFile()) && (file.exists())) {
/* 2757 */         InputStreamReader read = new InputStreamReader(
/* 2758 */           new FileInputStream(file), encoding);
/* 2759 */         BufferedReader bufferedReader = new BufferedReader(read);
/* 2760 */         String lineTxt = null;
/*      */ 
/* 2762 */         while ((lineTxt = bufferedReader.readLine()) != null) {
/* 2763 */           msg = msg + lineTxt;
/*      */         }
/* 2765 */         read.close();
/*      */       }
/* 2767 */       else if (basConfig.getIsdebug().equals("1")) {
/* 2768 */         logger.info("版本信息文件info.txt不存在!");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2772 */       if (basConfig.getIsdebug().equals("1")) {
/* 2773 */         logger.info("读取版本信息文件info.txt出错!");
/*      */       }
/*      */     }
/* 2776 */     Map map = new HashMap();
/* 2777 */     map.put("msg", msg);
/* 2778 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_app"})
/*      */   public Map<String, String> app(String basip, String ip, String sign, String token, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2787 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */ 
/* 2789 */     String host = ip;
/* 2790 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 2792 */       host = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 2795 */     String remoteIP = GetNgnixRemotIP.getRemoteAddrIp(request);
/* 2796 */     if ("1".equals(basConfig.getIsdebug())) {
/* 2797 */       logger.info("================ip: " + ip + " basip: " + basip + 
/* 2798 */         " remoteIP: " + remoteIP + " token: " + token + " sign: " + 
/* 2799 */         sign + "==============================");
/*      */     }
/*      */ 
/* 2802 */     String bip = basip;
/* 2803 */     if (stringUtils.isBlank(basip)) {
/* 2804 */       bip = remoteIP;
/*      */     }
/*      */ 
/* 2807 */     Portalbas basconfig = (Portalbas)config.getConfigMap().get(bip);
/* 2808 */     if (basconfig == null) {
/* 2809 */       bip = remoteIP;
/*      */     }
/* 2811 */     basconfig = (Portalbas)config.getConfigMap().get(bip);
/* 2812 */     if (basconfig == null) {
/* 2813 */       bip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 2817 */     Map map = new HashMap();
/*      */ 
/* 2819 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 2820 */       host + ":" + bip);
/* 2821 */     if (isLogin) {
/* 2822 */       if (((Portalbas)config.getConfigMap().get(bip)).getIsNtf().intValue() == 1) {
/* 2823 */         AppTokenMap.getInstance().getTokenMap().remove(token);
/* 2824 */         map.put("ret", "0");
/* 2825 */         return map;
/*      */       }
/* 2827 */       Kick.kickUserSyn(host + ":" + bip);
/*      */     }
/*      */ 
/* 2831 */     if ((((Portalbas)config.getConfigMap().get(bip)).getBas().equals("5")) || 
/* 2833 */       (((Portalbas)config.getConfigMap().get(bip)).getBas()
/* 2833 */       .equals("6")) || 
/* 2835 */       (((Portalbas)config.getConfigMap().get(bip)).getBas()
/* 2835 */       .equals("7")) || 
/* 2837 */       (((Portalbas)config.getConfigMap().get(bip)).getBas()
/* 2837 */       .equals("8"))) {
/* 2838 */       map.put("ret", "1");
/* 2839 */       map.put("msg", "该设备类型错误！！");
/* 2840 */       return map;
/*      */     }
/*      */ 
/* 2843 */     String trueToken = (String)AppTokenMap.getInstance().getTokenMap().get(token);
/*      */ 
/* 2846 */     if (!token.equals(trueToken)) {
/* 2847 */       AppTokenMap.getInstance().getTokenMap().remove(token);
/* 2848 */       map.put("ret", "1");
/* 2849 */       return map;
/*      */     }
/*      */ 
/* 2852 */     String code = basip + ip + token;
/*      */ 
/* 2854 */     String trueSign = DigestUtils.md5Hex(code);
/*      */ 
/* 2856 */     if (!trueSign.equals(sign)) {
/* 2857 */       AppTokenMap.getInstance().getTokenMap().remove(token);
/* 2858 */       map.put("ret", "1");
/* 2859 */       return map;
/*      */     }
/*      */ 
/* 2862 */     if (basConfig.getAuthInterface().contains("3"))
/*      */     {
/* 2864 */       if (1 == CheckMacTimeLimitMethod("", Long.valueOf(5L))) {
/* 2865 */         map.put("ret", "1");
/* 2866 */         return map;
/*      */       }
/*      */ 
/* 2870 */       if (onlineMap.getOnlineUserMap().size() >= 
/* 2870 */         Integer.valueOf(
/* 2871 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 2871 */         .get("core"))[1]).intValue())
/*      */       {
/* 2872 */         map.put("ret", "1");
/* 2873 */         return map;
/*      */       }
/*      */ 
/* 2876 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 2877 */       bsq.setBasip(bip);
/* 2878 */       String authUser = ((Portalbas)config.getConfigMap().get(bip)).getBasUser();
/* 2879 */       String authPwd = ((Portalbas)config.getConfigMap().get(bip)).getBasPwd();
/* 2880 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 2881 */         .getPortalbasauthList(bsq);
/* 2882 */       if (basauths.size() > 0) {
/* 2883 */         for (Portalbasauth ba : basauths) {
/* 2884 */           if (ba.getType().intValue() == 3) {
/* 2885 */             authUser = ba.getUsername();
/* 2886 */             authPwd = ba.getPassword();
/* 2887 */             if ((!stringUtils.isBlank(authUser)) && 
/* 2888 */               (!stringUtils.isBlank(authPwd))) break;
/* 2889 */             authUser = ((Portalbas)config.getConfigMap().get(bip))
/* 2890 */               .getBasUser();
/* 2891 */             authPwd = ((Portalbas)config.getConfigMap().get(bip))
/* 2892 */               .getBasPwd();
/*      */ 
/* 2894 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2899 */       Boolean info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 2900 */         authPwd, host, bip, "");
/* 2901 */       if (info.booleanValue()) {
/* 2902 */         String ikmac = "";
/* 2903 */         if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
/* 2904 */           ikmac = 
/* 2905 */             (String)ipMacMap.getInstance().getIpMacMap()
/* 2905 */             .get(host + ":" + bip);
/*      */         }
/* 2907 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(5L), request.getSession(), 
/* 2908 */           authUser, authPwd, "APP认证", response);
/*      */ 
/* 2910 */         AutoLoginPutMethod(ikmac, Long.valueOf(5L), authUser, authPwd, "APP认证(无感知)");
/*      */ 
/* 2912 */         HttpSession session = request.getSession();
/* 2913 */         session.setAttribute("username", "APP认证");
/*      */ 
/* 2915 */         session.setAttribute("ip", host);
/* 2916 */         session.setAttribute("basip", bip);
/*      */ 
/* 2926 */         String[] loginInfo = new String[16];
/* 2927 */         loginInfo[0] = "APP认证";
/* 2928 */         Date now = new Date();
/* 2929 */         loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 2930 */         loginInfo[4] = ikmac;
/* 2931 */         loginInfo[6] = "3";
/* 2932 */         loginInfo[7] = "0";
/* 2933 */         loginInfo[8] = "0";
/*      */ 
/* 2935 */         loginInfo[9] = host;
/* 2936 */         loginInfo[10] = bip;
/* 2937 */         loginInfo[11] = ((Portalbas)config.getConfigMap().get(bip)).getBasname();
/* 2938 */         loginInfo[12] = "";
/* 2939 */         loginInfo[13] = "";
/* 2940 */         loginInfo[14] = "no";
/* 2941 */         loginInfo[15] = "Android";
/*      */ 
/* 2943 */         onlineMap.getOnlineUserMap().put(host + ":" + bip, loginInfo);
/* 2944 */         AppTokenMap.getInstance().getTokenMap().remove(token);
/*      */ 
/* 2946 */         Portallogrecord logRecord = new Portallogrecord();
/*      */ 
/* 2948 */         logRecord.setInfo("IP: " + host + ":" + bip + " mac: " + ikmac + 
/* 2949 */           " 用户: 【APP认证】,登录成功!");
/* 2950 */         logRecord.setRecDate(now);
/* 2951 */         this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 2953 */         SangforLogin(host, "APP认证", ikmac, "", basip);
/*      */ 
/* 2955 */         map.put("ret", "0");
/* 2956 */         return map;
/*      */       }
/*      */ 
/* 2959 */       AppTokenMap.getInstance().getTokenMap().remove(token);
/* 2960 */       map.put("ret", "1");
/* 2961 */       return map;
/*      */     }
/*      */ 
/* 2964 */     AppTokenMap.getInstance().getTokenMap().remove(token);
/* 2965 */     map.put("ret", "2");
/* 2966 */     return map;
/*      */   }
/*      */ 
/*      */   private Map<String, Object> checkLocalAccount(String username, String password)
/*      */   {
/* 2976 */     PortalaccountQuery accountqQuery = new PortalaccountQuery();
/* 2977 */     accountqQuery.setLoginNameLike(false);
/* 2978 */     accountqQuery.setPasswordLike(false);
/* 2979 */     accountqQuery.setLoginName(username);
/* 2980 */     accountqQuery.setPassword(password);
/* 2981 */     List accounts = this.accountService
/* 2982 */       .getPortalaccountList(accountqQuery);
/*      */ 
/* 2984 */     Map map = new HashMap();
/* 2985 */     if (accounts.size() != 0) {
/* 2986 */       Portalaccount account = (Portalaccount)accounts.get(0);
/* 2987 */       if (password.equals(account.getPassword())) {
/* 2988 */         map.put("id", account.getId());
/* 2989 */         map.put("state", account.getState());
/* 2990 */         map.put("date", account.getDate());
/* 2991 */         map.put("time", account.getTime());
/* 2992 */         if (account.getOctets() == null)
/* 2993 */           map.put("octets", Long.valueOf(0L));
/*      */         else {
/* 2995 */           map.put("octets", account.getOctets());
/*      */         }
/* 2997 */         map.put("result", Integer.valueOf(1));
/* 2998 */         return map;
/*      */       }
/*      */     }
/* 3001 */     map.put("result", Integer.valueOf(0));
/* 3002 */     return map;
/*      */   }
/*      */ 
/*      */   private boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets)
/*      */   {
/* 3015 */     Portalaccount account = this.accountService.getPortalaccountByKey(userId);
/*      */ 
/* 3017 */     if (userState.equals("0")) {
/* 3018 */       return false;
/*      */     }
/*      */ 
/* 3021 */     if (userState.equals("1")) {
/* 3022 */       return true;
/*      */     }
/*      */ 
/* 3025 */     if (userState.equals("3")) {
/* 3026 */       Date now = new Date();
/* 3027 */       if (userDate.getTime() > now.getTime()) {
/* 3028 */         return true;
/*      */       }
/* 3030 */       account.setState("2");
/* 3031 */       this.accountService.updatePortalaccountByKey(account);
/* 3032 */       userState = "2";
/*      */     }
/*      */ 
/* 3036 */     if (userState.equals("2")) {
/* 3037 */       if (userTime.longValue() > 0L) {
/* 3038 */         return true;
/*      */       }
/* 3040 */       account.setState("4");
/* 3041 */       this.accountService.updatePortalaccountByKey(account);
/* 3042 */       userState = "4";
/*      */     }
/*      */ 
/* 3046 */     if (userState.equals("4")) {
/* 3047 */       if (octets.longValue() > 0L) {
/* 3048 */         return true;
/*      */       }
/* 3050 */       account.setState("0");
/* 3051 */       this.accountService.updatePortalaccountByKey(account);
/* 3052 */       return false;
/*      */     }
/*      */ 
/* 3055 */     return false;
/*      */   }
/*      */ 
/*      */   private Long doLinkRecord(String username, String ip, String basip, String userState, Long userId, String mac)
/*      */   {
/* 3067 */     Portalbas basconfig = (Portalbas)config.getConfigMap().get(basip);
/* 3068 */     if ((basconfig != null) && 
/* 3069 */       ("1".equals(basconfig.getIsPortalCheck()))) {
/* 3070 */       Portallinkrecord linkRecord = new Portallinkrecord();
/* 3071 */       linkRecord.setStartDate(new Date());
/* 3072 */       linkRecord.setIp(ip);
/* 3073 */       linkRecord.setBasip(basip);
/* 3074 */       linkRecord.setMac(mac);
/* 3075 */       linkRecord.setIns(Long.valueOf(0L));
/* 3076 */       linkRecord.setOuts(Long.valueOf(0L));
/* 3077 */       linkRecord.setOctets(Long.valueOf(0L));
/* 3078 */       linkRecord.setLoginName(username);
/* 3079 */       linkRecord.setState(userState);
/* 3080 */       linkRecord.setUid(userId);
/* 3081 */       this.linkRecordService.addPortallinkrecord(linkRecord);
/* 3082 */       return linkRecord.getId();
/*      */     }
/*      */ 
/* 3085 */     return null;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_LoginOut"})
/*      */   public Map<String, String> LoginOut(String ip, String basip, String umac, HttpServletRequest request, HttpServletResponse response) {
/* 3092 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 3093 */     Map map = new HashMap();
/*      */ 
/* 3095 */     HttpSession session = request.getSession();
/* 3096 */     String ikmac = umac;
/* 3097 */     if (stringUtils.isBlank(ikmac)) {
/* 3098 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/*      */ 
/* 3101 */     Cookie[] cookies = request.getCookies();
/* 3102 */     String cip = "";
/* 3103 */     String cbasip = "";
/* 3104 */     String cmac = "";
/* 3105 */     if (cookies != null) {
/* 3106 */       for (int i = 0; i < cookies.length; i++) {
/* 3107 */         if (cookies[i].getName().equals("ip"))
/* 3108 */           cip = cookies[i].getValue();
/* 3109 */         else if (cookies[i].getName().equals("basip"))
/* 3110 */           cbasip = cookies[i].getValue();
/* 3111 */         else if (cookies[i].getName().equals("mac")) {
/* 3112 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 3116 */     if (stringUtils.isBlank(ikmac)) {
/* 3117 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 3120 */     if (stringUtils.isBlank(ip)) {
/* 3121 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 3123 */     if (stringUtils.isBlank(ip)) {
/* 3124 */       ip = cip;
/*      */     }
/* 3126 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 3128 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 3131 */     if (stringUtils.isBlank(basip)) {
/* 3132 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 3134 */     if (stringUtils.isBlank(basip)) {
/* 3135 */       basip = cbasip;
/*      */     }
/* 3137 */     if (stringUtils.isBlank(basip)) {
/* 3138 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 3142 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/* 3144 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3144 */       .equals("4")))
/*      */     {
/* 3146 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 3148 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 3150 */       if (stringUtils.isBlank(ikmac)) {
/* 3151 */         map.put("ret", "10");
/* 3152 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 3153 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3158 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 3159 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/* 3160 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/* 3161 */       ip = ikmac;
/* 3162 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 3164 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 3166 */       if (stringUtils.isBlank(ikmac)) {
/* 3167 */         map.put("ret", "10");
/* 3168 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 3169 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3174 */     if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
/* 3175 */       map.put("ret", "1");
/* 3176 */       map.put("msg", "参数获取错误！！");
/* 3177 */       return map;
/*      */     }
/*      */ 
/* 3180 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 3181 */       ip + ":" + basip);
/* 3182 */     if (!isLogin) {
/* 3183 */       map.put("ret", "0");
/* 3184 */       map.put("msg", "你已经离线！");
/* 3185 */       session.removeAttribute("username");
/* 3186 */       session.removeAttribute("password");
/* 3187 */       session.removeAttribute("ikweb");
/*      */ 
/* 3191 */       Cookie cookieIP = new Cookie("ip", null);
/* 3192 */       cookieIP.setMaxAge(-1);
/* 3193 */       response.addCookie(cookieIP);
/* 3194 */       Cookie cookieBasIp = new Cookie("basip", null);
/* 3195 */       cookieBasIp.setMaxAge(-1);
/* 3196 */       response.addCookie(cookieBasIp);
/* 3197 */       Cookie cookiePwd = new Cookie("password", null);
/* 3198 */       cookiePwd.setMaxAge(-1);
/* 3199 */       response.addCookie(cookiePwd);
/*      */ 
/* 3207 */       return map;
/*      */     }
/*      */ 
/* 3210 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/* 3212 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3212 */       .equals("6")) || 
/* 3214 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3214 */       .equals("7")) || 
/* 3216 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3216 */       .equals("8"))) {
/* 3217 */       map.put("ret", "1");
/* 3218 */       map.put("msg", "该设备类型错误！！");
/* 3219 */       return map;
/*      */     }
/*      */ 
/* 3222 */     String site = "";
/* 3223 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 3224 */       site = (String)session.getAttribute("site");
/*      */     }
/*      */ 
/* 3227 */     Kick.offLine(ip + ":" + basip, ikmac, site);
/*      */ 
/* 3229 */     map.put("ret", "0");
/* 3230 */     map.put("msg", "下线成功！");
/* 3231 */     session.removeAttribute("username");
/* 3232 */     session.removeAttribute("password");
/*      */ 
/* 3236 */     Cookie cookieIP = new Cookie("ip", null);
/* 3237 */     cookieIP.setMaxAge(-1);
/* 3238 */     response.addCookie(cookieIP);
/* 3239 */     Cookie cookieBasIp = new Cookie("basip", null);
/* 3240 */     cookieBasIp.setMaxAge(-1);
/* 3241 */     response.addCookie(cookieBasIp);
/* 3242 */     Cookie cookiePwd = new Cookie("password", null);
/* 3243 */     cookiePwd.setMaxAge(-1);
/* 3244 */     response.addCookie(cookiePwd);
/*      */ 
/* 3252 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_Login"})
/*      */   public Map<String, String> Login(String usr, String pwd, String ip, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3260 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 3261 */     Map map = new HashMap();
/*      */ 
/* 3263 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/* 3266 */       String web = (String)session.getAttribute("web");
/* 3267 */       if (stringUtils.isNotBlank(web)) {
/* 3268 */         while (web.endsWith("/")) {
/* 3269 */           web = web.substring(0, web.length() - 1);
/*      */         }
/* 3271 */         Long webID = Long.valueOf(web);
/* 3272 */         if (webID.longValue() != 0L) {
/* 3273 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/* 3274 */           if (portalweb != null) {
/* 3275 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/* 3276 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/* 3279 */           com.leeson.core.bean.Config config = this.configService
/* 3280 */             .getConfigByKey(Long.valueOf(1L));
/* 3281 */           if (config != null) {
/* 3282 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 3283 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/* 3287 */         com.leeson.core.bean.Config config = this.configService
/* 3288 */           .getConfigByKey(Long.valueOf(1L));
/* 3289 */         if (config != null) {
/* 3290 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 3291 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/* 3298 */     String ikmac = umac;
/* 3299 */     if (stringUtils.isBlank(ikmac)) {
/* 3300 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/* 3302 */     String ikweb = (String)session.getAttribute("ikweb");
/* 3303 */     String authUrl = ikweb;
/*      */ 
/* 3306 */     Cookie[] cookies = request.getCookies();
/* 3307 */     String cip = "";
/* 3308 */     String cbasip = "";
/* 3309 */     String cmac = "";
/* 3310 */     if (cookies != null) {
/* 3311 */       for (int i = 0; i < cookies.length; i++) {
/* 3312 */         if (cookies[i].getName().equals("ip"))
/* 3313 */           cip = cookies[i].getValue();
/* 3314 */         else if (cookies[i].getName().equals("basip"))
/* 3315 */           cbasip = cookies[i].getValue();
/* 3316 */         else if (cookies[i].getName().equals("mac")) {
/* 3317 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 3321 */     if (stringUtils.isBlank(ikmac)) {
/* 3322 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 3325 */     if (stringUtils.isBlank(ip)) {
/* 3326 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 3328 */     if (stringUtils.isBlank(ip)) {
/* 3329 */       ip = cip;
/*      */     }
/* 3331 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 3333 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 3336 */     if (stringUtils.isBlank(basip)) {
/* 3337 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 3339 */     if (stringUtils.isBlank(basip)) {
/* 3340 */       basip = cbasip;
/*      */     }
/* 3342 */     if (stringUtils.isBlank(basip)) {
/* 3343 */       basip = basConfig.getBasIp();
/*      */     }
/* 3345 */     if (config.getConfigMap().get(basip) == null) {
/* 3346 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 3349 */     String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
/* 3350 */     if (checkResult != "") {
/* 3351 */       map.put("ret", "1");
/* 3352 */       map.put("msg", checkResult);
/* 3353 */       return map;
/*      */     }
/*      */ 
/* 3357 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/* 3359 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3359 */       .equals("4")))
/*      */     {
/* 3361 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 3363 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 3365 */       if (stringUtils.isBlank(ikmac)) {
/* 3366 */         map.put("ret", "10");
/* 3367 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 3368 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3373 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 3374 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/* 3375 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/* 3376 */       ip = ikmac;
/* 3377 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 3379 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 3381 */       if (stringUtils.isBlank(ikmac)) {
/* 3382 */         map.put("ret", "10");
/* 3383 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 3384 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3389 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 3390 */       ip + ":" + basip);
/* 3391 */     if (isLogin) {
/* 3392 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/* 3393 */         String[] userinfo = (String[])onlineMap.getOnlineUserMap().get(
/* 3394 */           ip + ":" + basip);
/* 3395 */         usr = userinfo[0];
/* 3396 */         map.put("ret", "119");
/* 3397 */         map.put("i", ip);
/* 3398 */         map.put("u", usr);
/* 3399 */         map.put("msg", "你已经通过认证，请不要重复刷新 ！");
/* 3400 */         return map;
/*      */       }
/* 3402 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/* 3406 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 3406 */       Integer.valueOf(
/* 3407 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 3407 */       .get("core"))[1]).intValue())
/*      */     {
/* 3408 */       map.put("ret", "110");
/* 3409 */       map.put("msg", "网络暂时不可用，请稍后再试！！");
/* 3410 */       return map;
/*      */     }
/*      */ 
/* 3413 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/* 3415 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3415 */       .equals("6")) || 
/* 3417 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3417 */       .equals("7")) || 
/* 3419 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3419 */       .equals("8"))) {
/* 3420 */       map.put("ret", "1");
/* 3421 */       map.put("msg", "该设备类型错误！！");
/* 3422 */       return map;
/*      */     }
/*      */ 
/* 3426 */     if ((((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("0")) && 
/* 3427 */       (stringUtils.isBlank(usr)) && (stringUtils.isBlank(pwd)))
/*      */     {
/* 3429 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(2L))) {
/* 3430 */         map.put("ret", "1");
/* 3431 */         map.put("i", ip);
/* 3432 */         map.put("u", "一键认证");
/* 3433 */         map.put("msg", "该设备当日时长已用完！！");
/* 3434 */         return map;
/*      */       }
/*      */ 
/* 3437 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 3438 */       bsq.setBasip(basip);
/* 3439 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 3440 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 3441 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 3442 */         .getPortalbasauthList(bsq);
/* 3443 */       if (basauths.size() > 0) {
/* 3444 */         for (Portalbasauth ba : basauths) {
/* 3445 */           if (ba.getType().intValue() == 0) {
/* 3446 */             authUser = ba.getUsername();
/* 3447 */             authPwd = ba.getPassword();
/* 3448 */             authUrl = ba.getUrl();
/* 3449 */             if ((stringUtils.isBlank(authUser)) || 
/* 3450 */               (stringUtils.isBlank(authPwd))) {
/* 3451 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 3452 */                 .getBasUser();
/* 3453 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 3454 */                 .getBasPwd();
/*      */             }
/* 3456 */             if (!stringUtils.isBlank(authUrl)) break;
/* 3457 */             authUrl = ikweb;
/*      */ 
/* 3459 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 3463 */       if (stringUtils.isBlank(authUrl)) {
/* 3464 */         authUrl = 
/* 3465 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 3465 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 3468 */       Boolean info = Boolean.valueOf(false);
/*      */ 
/* 3471 */       if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3471 */         .equals("3")) {
/* 3472 */         String site = (String)session.getAttribute("site");
/* 3473 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 3474 */         aq.setLoginName(authUser);
/* 3475 */         aq.setLoginNameLike(false);
/* 3476 */         List accs = this.accountService
/* 3477 */           .getPortalaccountList(aq);
/* 3478 */         int accTime = 1440;
/* 3479 */         if (accs.size() == 1) {
/* 3480 */           long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/* 3481 */           if (accTimeLong > 0L) {
/* 3482 */             accTime = (int)accTimeLong;
/*      */           }
/*      */         }
/* 3485 */         info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
/* 3486 */           basip));
/*      */       } else {
/* 3488 */         info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 3489 */           authPwd, ip, basip, ikmac);
/*      */       }
/*      */ 
/* 3492 */       if (info.booleanValue())
/*      */       {
/* 3494 */         if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
/* 3495 */           ikmac = 
/* 3496 */             (String)ipMacMap.getInstance().getIpMacMap()
/* 3496 */             .get(ip + ":" + basip);
/*      */         }
/* 3498 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(2L), request.getSession(), 
/* 3499 */           authUser, authPwd, "", response);
/*      */ 
/* 3501 */         AutoLoginPutMethod(ikmac, Long.valueOf(2L), authUser, authPwd, "一键认证(无感知)");
/*      */ 
/* 3503 */         session.setAttribute("username", "一键认证");
/*      */ 
/* 3505 */         session.setAttribute("ip", ip);
/* 3506 */         session.setAttribute("basip", basip);
/* 3507 */         session.setAttribute("ikweb", authUrl);
/* 3508 */         session.setAttribute("ikmac", ikmac);
/*      */ 
/* 3518 */         if (stringUtils.isEmpty(ssid)) {
/* 3519 */           ssid = (String)session.getAttribute("ssid");
/*      */         }
/* 3521 */         if (stringUtils.isEmpty(apmac)) {
/* 3522 */           apmac = (String)session.getAttribute("apmac");
/*      */         }
/* 3524 */         String[] loginInfo = new String[16];
/* 3525 */         loginInfo[0] = "一键认证";
/* 3526 */         Date now = new Date();
/* 3527 */         loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 3528 */         loginInfo[4] = ikmac;
/* 3529 */         loginInfo[6] = "0";
/* 3530 */         loginInfo[7] = "0";
/* 3531 */         loginInfo[8] = "0";
/*      */ 
/* 3533 */         loginInfo[9] = ip;
/* 3534 */         loginInfo[10] = basip;
/* 3535 */         loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 3536 */         loginInfo[12] = ssid;
/* 3537 */         loginInfo[13] = apmac;
/* 3538 */         loginInfo[14] = "no";
/* 3539 */         loginInfo[15] = getAgent(request);
/*      */ 
/* 3541 */         onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/* 3542 */         Portallogrecord logRecord = new Portallogrecord();
/*      */ 
/* 3544 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 3545 */           " 用户: 【一键认证】,登录成功!");
/* 3546 */         logRecord.setRecDate(now);
/* 3547 */         this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 3549 */         if (stringUtils.isNotBlank(ssid)) {
/*      */           try {
/* 3551 */             PortalssidQuery apq = new PortalssidQuery();
/* 3552 */             apq.setSsid(ssid);
/* 3553 */             apq.setSsidLike(false);
/* 3554 */             List aps = this.ssidService
/* 3555 */               .getPortalssidList(apq);
/* 3556 */             if ((aps != null) && (aps.size() > 0)) {
/* 3557 */               Portalssid ap = (Portalssid)aps.get(0);
/* 3558 */               ap.setBasip(basip);
/* 3559 */               long count = ap.getCount().longValue() + 1L;
/* 3560 */               ap.setCount(Long.valueOf(count));
/* 3561 */               this.ssidService.updatePortalssidByKey(ap);
/*      */             } else {
/* 3563 */               Portalssid ap = new Portalssid();
/* 3564 */               ap.setSsid(ssid);
/* 3565 */               ap.setBasip(basip);
/* 3566 */               ap.setCount(Long.valueOf(1L));
/* 3567 */               this.ssidService.addPortalssid(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 3570 */             logger.error("==============ERROR Start=============");
/* 3571 */             logger.error(e);
/* 3572 */             logger.error("ERROR INFO ", e);
/* 3573 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/* 3576 */         if (stringUtils.isNotBlank(apmac)) {
/*      */           try {
/* 3578 */             PortalapQuery apq = new PortalapQuery();
/* 3579 */             apq.setMac(apmac);
/* 3580 */             apq.setMacLike(false);
/* 3581 */             List aps = this.apService.getPortalapList(apq);
/* 3582 */             if ((aps != null) && (aps.size() > 0)) {
/* 3583 */               Portalap ap = (Portalap)aps.get(0);
/* 3584 */               ap.setBasip(basip);
/* 3585 */               long count = ap.getCount().longValue() + 1L;
/* 3586 */               ap.setCount(Long.valueOf(count));
/* 3587 */               this.apService.updatePortalapByKey(ap);
/*      */             } else {
/* 3589 */               Portalap ap = new Portalap();
/* 3590 */               ap.setMac(apmac);
/* 3591 */               ap.setBasip(basip);
/* 3592 */               ap.setCount(Long.valueOf(1L));
/* 3593 */               this.apService.addPortalap(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 3596 */             logger.error("==============ERROR Start=============");
/* 3597 */             logger.error(e);
/* 3598 */             logger.error("ERROR INFO ", e);
/* 3599 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/*      */ 
/* 3603 */         SangforLogin(ip, "一键认证", ikmac, apmac, basip);
/*      */ 
/* 3605 */         map.put("ret", "0");
/* 3606 */         map.put("i", ip);
/* 3607 */         map.put("u", "一键认证");
/* 3608 */         map.put("w", authUrl);
/* 3609 */         map.put("msg", "认证成功,你可以上网了！！");
/* 3610 */         return map;
/*      */       }
/* 3612 */       map.put("ret", "1");
/* 3613 */       map.put("i", ip);
/* 3614 */       map.put("u", "一键认证");
/* 3615 */       map.put("msg", "认证失败，请联系管理员！！");
/* 3616 */       return map;
/*      */     }
/*      */ 
/* 3621 */     if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
/* 3622 */       map.put("ret", "1");
/* 3623 */       map.put("i", ip);
/* 3624 */       map.put("u", "");
/* 3625 */       map.put("msg", "用户名和密码不能为空！！");
/* 3626 */       return map;
/*      */     }
/*      */ 
/* 3629 */     String username = usr;
/* 3630 */     String password = pwd;
/* 3631 */     if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
/* 3632 */       logger.info("Request Auth User: " + username + " IP: " + ip);
/*      */     }
/*      */ 
/* 3638 */     Boolean info = Boolean.valueOf(false);
/*      */ 
/* 3640 */     String userState = "";
/* 3641 */     Long userId = Long.valueOf(0L);
/* 3642 */     Date userDate = new Date();
/* 3643 */     Long userTime = Long.valueOf(0L);
/* 3644 */     Long octets = Long.valueOf(0L);
/* 3645 */     Long recordId = null;
/*      */ 
/* 3647 */     boolean isRadius = false;
/*      */     String authPwd;
/*      */     int accTime;
/* 3650 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("1"))
/*      */     {
/* 3652 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) {
/* 3653 */         map.put("ret", "1");
/* 3654 */         map.put("i", ip);
/* 3655 */         map.put("u", username);
/* 3656 */         map.put("msg", "该设备当日时长已用完！！");
/* 3657 */         return map;
/*      */       }
/*      */ 
/* 3660 */       Map resultCheck = checkLocalAccount(username, 
/* 3661 */         password);
/* 3662 */       int state = ((Integer)resultCheck.get("result")).intValue();
/*      */ 
/* 3664 */       if (state == 0) {
/* 3665 */         map.put("ret", "1");
/* 3666 */         map.put("i", ip);
/* 3667 */         map.put("u", username);
/* 3668 */         map.put("msg", "用户名密码错误,或账户已过期！！");
/* 3669 */         return map;
/*      */       }
/*      */ 
/* 3672 */       userState = (String)resultCheck.get("state");
/* 3673 */       userId = (Long)resultCheck.get("id");
/* 3674 */       userDate = (Date)resultCheck.get("date");
/* 3675 */       userTime = (Long)resultCheck.get("time");
/* 3676 */       octets = (Long)resultCheck.get("octets");
/*      */ 
/* 3678 */       if (!checkTimeOut(userState, userId, userDate, userTime, octets)) {
/* 3679 */         map.put("ret", "1");
/* 3680 */         map.put("i", ip);
/* 3681 */         map.put("u", username);
/* 3682 */         map.put("msg", "账户已过期，请及时联系管理员充值！！");
/* 3683 */         return map;
/*      */       }
/*      */ 
/* 3686 */       if (userState.equals("3")) {
/* 3687 */         Date now = new Date();
/* 3688 */         if (userDate.getTime() - now.getTime() <= 3600000L) {
/* 3689 */           map.put("msg", "账户余额告警，请计时充值！！");
/*      */         }
/*      */       }
/* 3692 */       if ((userState.equals("2")) && 
/* 3693 */         (userTime.longValue() <= 3600000L)) {
/* 3694 */         map.put("msg", "账户余额告警，请计时充值！！");
/*      */       }
/*      */ 
/* 3697 */       if ((userState.equals("4")) && 
/* 3698 */         (octets.longValue() <= 104857600L)) {
/* 3699 */         map.put("msg", "账户余额告警，请计时充值！！");
/*      */       }
/*      */ 
/* 3703 */       Portalaccount acc = this.accountService.getPortalaccountByKey(userId);
/*      */       String[] loginInfo;
/* 3705 */       if (("1".equals(((Portalbas)config.getConfigMap().get(basip)).getIsPortalCheck())) && 
/* 3706 */         (acc != null)) {
/* 3707 */         Integer limitCount = acc.getMaclimitcount();
/* 3708 */         if ((limitCount != null) && (limitCount.intValue() > 0)) {
/* 3709 */           int count = 0;
/* 3710 */           Iterator iterator = OnlineMap.getInstance()
/* 3711 */             .getOnlineUserMap().keySet().iterator();
/* 3712 */           while (iterator.hasNext()) {
/* 3713 */             Object o = iterator.next();
/* 3714 */             String t = o.toString();
/* 3715 */             loginInfo = 
/* 3716 */               (String[])OnlineMap.getInstance()
/* 3716 */               .getOnlineUserMap().get(t);
/* 3717 */             String haveUsername = loginInfo[0];
/* 3718 */             if (username.equals(haveUsername)) {
/* 3719 */               count++;
/*      */             }
/*      */           }
/* 3722 */           if (count >= limitCount.intValue()) {
/* 3723 */             map.put("ret", "1");
/* 3724 */             map.put("i", ip);
/* 3725 */             map.put("u", username);
/* 3726 */             map.put("msg", "该账户同时在线数已超出限制，请等待使用该账户的其他设备下线！！");
/* 3727 */             return map;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3733 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 3734 */       bsq.setBasip(basip);
/* 3735 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 3736 */       authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 3737 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 3738 */         .getPortalbasauthList(bsq);
/* 3739 */       if (basauths.size() > 0) {
/* 3740 */         for (Portalbasauth ba : basauths) {
/* 3741 */           if (ba.getType().intValue() == 1) {
/* 3742 */             authUser = ba.getUsername();
/* 3743 */             authPwd = ba.getPassword();
/* 3744 */             authUrl = ba.getUrl();
/* 3745 */             if ((stringUtils.isBlank(authUser)) || 
/* 3746 */               (stringUtils.isBlank(authPwd))) {
/* 3747 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 3748 */                 .getBasUser();
/* 3749 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 3750 */                 .getBasPwd();
/*      */             }
/* 3752 */             if (!stringUtils.isBlank(authUrl)) break;
/* 3753 */             authUrl = ikweb;
/*      */ 
/* 3755 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 3759 */       if (stringUtils.isBlank(authUrl)) {
/* 3760 */         authUrl = 
/* 3761 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 3761 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 3764 */       if (!"1".equals(((Portalbas)config.getConfigMap().get(basip))
/* 3764 */         .getIsPortalCheck())) {
/* 3765 */         authUser = username;
/* 3766 */         authPwd = password;
/*      */       }
/*      */ 
/* 3770 */       if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3770 */         .equals("1")) || 
/* 3772 */         (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3772 */         .equals("0")))
/* 3773 */         info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 3774 */           authPwd, ip, basip, ikmac);
/*      */       else {
/* 3776 */         info = Boolean.valueOf(true);
/*      */       }
/*      */ 
/* 3780 */       if (info.booleanValue())
/*      */       {
/* 3788 */         int limitMac = acc.getMaclimit().intValue();
/* 3789 */         int limitCount = acc.getMaclimitcount().intValue();
/* 3790 */         int auto = acc.getAutologin().intValue();
/*      */ 
/* 3792 */         String userMac = ikmac;
/*      */ 
/* 3795 */         if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3795 */           .equals("2")) || 
/* 3797 */           (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3797 */           .equals("4")) || 
/* 3799 */           (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3799 */           .equals("3"))) {
/* 3800 */           userMac = ikmac;
/*      */         } else {
/* 3802 */           userMac = 
/* 3803 */             (String)ipMacMap.getInstance().getIpMacMap()
/* 3803 */             .get(ip + ":" + basip);
/* 3804 */           if ((stringUtils.isBlank(userMac)) || 
/* 3805 */             ("error".equals(userMac)))
/* 3806 */             userMac = ikmac;
/*      */           else {
/* 3808 */             ikmac = userMac;
/*      */           }
/*      */         }
/*      */ 
/* 3812 */         if (limitMac == 1) {
/* 3813 */           if ((stringUtils.isBlank(userMac)) || 
/* 3814 */             ("error".equals(userMac)))
/*      */           {
/* 3817 */             if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3817 */               .equals("1")) || 
/* 3820 */               (((Portalbas)config.getConfigMap().get(basip))
/* 3819 */               .getBas()
/* 3820 */               .equals("0"))) {
/* 3821 */               InterfaceControl.Method("PORTAL_LOGINOUT", 
/* 3822 */                 username, "password", ip, basip, "");
/*      */             }
/* 3824 */             map.put("ret", "1");
/* 3825 */             map.put("i", ip);
/* 3826 */             map.put("u", username);
/* 3827 */             map.put("msg", "协议不支持MAC绑定，请联系管理员修改账户属性！！");
/* 3828 */             return map;
/*      */           }
/*      */ 
/* 3831 */           PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/* 3832 */           mq.setAccountId(userId);
/* 3833 */           List<Portalaccountmacs> accountmacs = this.macsService
/* 3834 */             .getPortalaccountmacsList(mq);
/* 3835 */           if ((accountmacs.size() < 1) || (limitCount == 0) || 
/* 3836 */             (accountmacs.size() < limitCount))
/*      */           {
/* 3838 */             if ((stringUtils.isNotBlank(userMac)) && 
/* 3839 */               (!"error".equals(userMac))) {
/* 3840 */               Boolean isHave = Boolean.valueOf(false);
/* 3841 */               for (Portalaccountmacs amacs : accountmacs)
/*      */               {
/* 3843 */                 if (amacs.getMac().toLowerCase()
/* 3843 */                   .equals(userMac)) {
/* 3844 */                   isHave = Boolean.valueOf(true);
/* 3845 */                   break;
/*      */                 }
/*      */               }
/* 3848 */               if (!isHave.booleanValue()) {
/* 3849 */                 Portalaccountmacs mac = new Portalaccountmacs();
/* 3850 */                 mac.setAccountId(userId);
/* 3851 */                 mac.setMac(userMac);
/* 3852 */                 this.macsService.addPortalaccountmacs(mac);
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3858 */             Boolean isHave = Boolean.valueOf(false);
/* 3859 */             for (Portalaccountmacs amacs : accountmacs)
/*      */             {
/* 3861 */               if (amacs.getMac().toLowerCase()
/* 3861 */                 .equals(userMac)) {
/* 3862 */                 isHave = Boolean.valueOf(true);
/* 3863 */                 break;
/*      */               }
/*      */             }
/* 3866 */             if (!isHave.booleanValue())
/*      */             {
/* 3869 */               if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3869 */                 .equals("1")) || 
/* 3872 */                 (((Portalbas)config.getConfigMap().get(basip))
/* 3871 */                 .getBas()
/* 3872 */                 .equals("0")))
/*      */               {
/* 3874 */                 InterfaceControl.Method("PORTAL_LOGINOUT", 
/* 3875 */                   username, "password", ip, 
/* 3876 */                   basip, "");
/*      */               }
/*      */ 
/* 3879 */               map.put("ret", "1");
/* 3880 */               map.put("i", ip);
/* 3881 */               map.put("u", username);
/* 3882 */               map.put("msg", "此账号没有绑定该设备，请联系管理员！！");
/* 3883 */               return map;
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 3888 */         if ((auto == 1) && 
/* 3889 */           (!stringUtils.isBlank(userMac)) && 
/* 3890 */           (!"error"
/* 3890 */           .equals(userMac))) {
/* 3891 */           PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/* 3892 */           mq.setAccountId(userId);
/* 3893 */           List<Portalaccountmacs> accountmacs = this.macsService
/* 3894 */             .getPortalaccountmacsList(mq);
/* 3895 */           if ((limitCount == 0) || 
/* 3896 */             (accountmacs.size() < limitCount)) {
/* 3897 */             boolean macNotHave = true;
/* 3898 */             for (Portalaccountmacs mac : accountmacs) {
/* 3899 */               if (userMac.equals(mac.getMac())) {
/* 3900 */                 macNotHave = false;
/*      */               }
/*      */             }
/* 3903 */             if (macNotHave) {
/* 3904 */               Portalaccountmacs mac = new Portalaccountmacs();
/* 3905 */               mac.setAccountId(userId);
/* 3906 */               mac.setMac(userMac);
/* 3907 */               this.macsService.addPortalaccountmacs(mac);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 3917 */         if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3917 */           .equals("2")) || 
/* 3919 */           (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3919 */           .equals("4"))) {
/* 3920 */           info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 3921 */             authPwd, ip, basip, ikmac);
/*      */         }
/*      */ 
/* 3924 */         if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 3924 */           .equals("3")) {
/* 3925 */           String site = (String)session.getAttribute("site");
/* 3926 */           accTime = 1440;
/* 3927 */           if (userState.equals("3")) {
/* 3928 */             Date now = new Date();
/* 3929 */             accTime = (int)((userDate.getTime() - now.getTime()) / 60000L);
/*      */           }
/* 3931 */           if (userState.equals("2")) {
/* 3932 */             accTime = (int)(userTime.longValue() / 60000L);
/*      */           }
/*      */ 
/* 3935 */           info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
/* 3936 */             basip));
/*      */         }
/*      */       }
/*      */ 
/* 3940 */       if (info.booleanValue()) {
/* 3941 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(3L), request.getSession(), 
/* 3942 */           response, username);
/* 3943 */         AutoLoginPutMethod(ikmac, Long.valueOf(3L), authUser, authPwd, username);
/* 3944 */         recordId = doLinkRecord(username, ip, basip, userState, userId, 
/* 3945 */           ikmac);
/* 3946 */         if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
/* 3947 */           String[] userinfo = new String[3];
/* 3948 */           userinfo[0] = authUser;
/* 3949 */           userinfo[1] = authPwd;
/* 3950 */           userinfo[2] = username;
/* 3951 */           AutoLoginMap.getInstance().getAutoLoginMap()
/* 3952 */             .put(ikmac, userinfo);
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 3958 */       isRadius = true;
/* 3959 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))) {
/* 3960 */         map.put("ret", "1");
/* 3961 */         map.put("i", ip);
/* 3962 */         map.put("u", username);
/* 3963 */         map.put("msg", "该设备当日时长已用完！！");
/* 3964 */         return map;
/*      */       }
/*      */ 
/* 3968 */       String accountAPI_Url = 
/* 3969 */         (String)AccountAPIMap.getInstance()
/* 3969 */         .getAccountAPIMap().get("url");
/* 3970 */       String accountAPI_State = 
/* 3971 */         (String)AccountAPIMap.getInstance()
/* 3971 */         .getAccountAPIMap().get("state");
/* 3972 */       if ((stringUtils.isNotBlank(accountAPI_Url)) && 
/* 3973 */         (stringUtils.isNotBlank(accountAPI_State)) && 
/* 3974 */         ("1".equals(accountAPI_State))) {
/* 3975 */         String agent = (String)session.getAttribute("agent");
/* 3976 */         boolean apiResult = AccountAPIRequest.send(accountAPI_Url, 
/* 3977 */           username, password, ip, ikmac, agent);
/* 3978 */         if (!apiResult) {
/* 3979 */           map.put("ret", "1");
/* 3980 */           map.put("i", ip);
/* 3981 */           map.put("u", username);
/* 3982 */           map.put("msg", "用户名或密码错误！");
/* 3983 */           return map;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3988 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 3989 */       bsq.setBasip(basip);
/* 3990 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 3991 */         .getPortalbasauthList(bsq);
/* 3992 */       if (basauths.size() > 0) {
/* 3993 */         for (Portalbasauth ba : basauths) {
/* 3994 */           if (ba.getType().intValue() == 2) {
/* 3995 */             authUrl = ba.getUrl();
/* 3996 */             if (!stringUtils.isBlank(authUrl)) break;
/* 3997 */             authUrl = ikweb;
/*      */ 
/* 3999 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 4003 */       if (stringUtils.isBlank(authUrl)) {
/* 4004 */         authUrl = 
/* 4005 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 4005 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 4009 */       if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 4009 */         .equals("3")) {
/* 4010 */         String site = (String)session.getAttribute("site");
/* 4011 */         info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, 1440, site, 
/* 4012 */           basip));
/*      */       } else {
/* 4014 */         info = InterfaceControl.Method("PORTAL_LOGIN", username, 
/* 4015 */           password, ip, basip, ikmac);
/*      */       }
/* 4017 */       if (info.booleanValue()) {
/* 4018 */         String userMac = ikmac;
/*      */ 
/* 4020 */         if ((((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 4020 */           .equals("2")) || 
/* 4022 */           (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 4022 */           .equals("4")) || 
/* 4024 */           (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 4024 */           .equals("3"))) {
/* 4025 */           userMac = ikmac;
/*      */         } else {
/* 4027 */           userMac = 
/* 4028 */             (String)ipMacMap.getInstance().getIpMacMap()
/* 4028 */             .get(ip + ":" + basip);
/* 4029 */           if ((stringUtils.isBlank(userMac)) || 
/* 4030 */             ("error".equals(userMac)))
/* 4031 */             userMac = ikmac;
/*      */           else {
/* 4033 */             ikmac = userMac;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 4039 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(4L), request.getSession(), 
/* 4040 */           response, username);
/* 4041 */         AutoLoginPutMethod(ikmac, Long.valueOf(4L), username, password, username + 
/* 4042 */           "(无感知)");
/*      */ 
/* 4044 */         if (stringUtils.isNotBlank(userMac))
/*      */         {
/* 4046 */           boolean macHave = false;
/* 4047 */           List<Portalaccountmacs> macs = this.macsService
/* 4048 */             .getPortalaccountmacsList(new PortalaccountmacsQuery());
/* 4049 */           for (Portalaccountmacs mac : macs) {
/* 4050 */             if (mac.getMac().equals(userMac)) {
/* 4051 */               macHave = true;
/* 4052 */               break;
/*      */             }
/*      */           }
/* 4055 */           if (!macHave) {
/* 4056 */             List accs = this.accountService
/* 4057 */               .getPortalaccountList(new PortalaccountQuery());
/* 4058 */             if ((accs.size() > 0) && 
/* 4059 */               (((Portalaccount)accs.get(0)).getAutologin().intValue() == 1)) {
/* 4060 */               Portalaccountmacs accMac = new Portalaccountmacs();
/* 4061 */               accMac.setAccountId(((Portalaccount)accs.get(0)).getId());
/* 4062 */               accMac.setMac(userMac);
/* 4063 */               this.macsService.addPortalaccountmacs(accMac);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4074 */     if (info.booleanValue())
/*      */     {
/* 4079 */       session.setAttribute("password", password);
/* 4080 */       Cookie cookiePwd = new Cookie("password", password);
/* 4081 */       cookiePwd.setMaxAge(86400);
/* 4082 */       response.addCookie(cookiePwd);
/*      */ 
/* 4085 */       if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac)))
/*      */       {
/* 4088 */         if (!((Portalbas)config.getConfigMap().get(basip)).getAuthInterface()
/* 4088 */           .contains("1")) {
/* 4089 */           String[] userinfo = new String[3];
/* 4090 */           userinfo[0] = username;
/* 4091 */           userinfo[1] = password;
/* 4092 */           userinfo[2] = (username + "(无感知)");
/* 4093 */           AutoLoginMap.getInstance().getAutoLoginMap()
/* 4094 */             .put(ikmac, userinfo);
/*      */         }
/*      */ 
/* 4097 */         Cookie cookieMac = new Cookie("mac", ikmac);
/* 4098 */         cookieMac.setMaxAge(86400);
/* 4099 */         response.addCookie(cookieMac);
/* 4100 */         session.setAttribute("ikmac", ikmac);
/*      */       }
/*      */ 
/* 4103 */       session.setAttribute("username", username);
/* 4104 */       session.setAttribute("ip", ip);
/* 4105 */       session.setAttribute("basip", basip);
/* 4106 */       session.setAttribute("ikweb", authUrl);
/*      */ 
/* 4108 */       Cookie cookieIp = new Cookie("ip", ip);
/* 4109 */       cookieIp.setMaxAge(86400);
/* 4110 */       response.addCookie(cookieIp);
/*      */ 
/* 4112 */       Cookie cookieBasIp = new Cookie("basip", basip);
/* 4113 */       cookieBasIp.setMaxAge(86400);
/* 4114 */       response.addCookie(cookieBasIp);
/*      */ 
/* 4117 */       if (stringUtils.isEmpty(ssid)) {
/* 4118 */         ssid = (String)session.getAttribute("ssid");
/*      */       }
/* 4120 */       if (stringUtils.isEmpty(apmac)) {
/* 4121 */         apmac = (String)session.getAttribute("apmac");
/*      */       }
/* 4123 */       String[] loginInfo = new String[16];
/* 4124 */       loginInfo[0] = username;
/* 4125 */       loginInfo[1] = String.valueOf(userId);
/* 4126 */       loginInfo[2] = String.valueOf(recordId);
/*      */ 
/* 4128 */       Date now = new Date();
/* 4129 */       loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 4130 */       loginInfo[4] = ikmac;
/* 4131 */       loginInfo[5] = "ok";
/* 4132 */       if (isRadius)
/* 4133 */         loginInfo[6] = "2";
/*      */       else {
/* 4135 */         loginInfo[6] = "1";
/*      */       }
/* 4137 */       loginInfo[7] = "0";
/* 4138 */       loginInfo[8] = "0";
/*      */ 
/* 4140 */       loginInfo[9] = ip;
/* 4141 */       loginInfo[10] = basip;
/* 4142 */       loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 4143 */       loginInfo[12] = ssid;
/* 4144 */       loginInfo[13] = apmac;
/* 4145 */       loginInfo[14] = "no";
/* 4146 */       loginInfo[15] = getAgent(request);
/*      */ 
/* 4148 */       onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/*      */ 
/* 4151 */       Portallogrecord logRecord = new Portallogrecord();
/*      */ 
/* 4154 */       if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface()
/* 4154 */         .contains("1"))
/* 4155 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 4156 */           " 系统用户: " + username + ",登录成功!");
/*      */       else {
/* 4158 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 4159 */           " Radius用户: " + username + ",登录成功!");
/*      */       }
/*      */ 
/* 4162 */       logRecord.setRecDate(now);
/*      */ 
/* 4164 */       this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 4166 */       if (stringUtils.isNotBlank(ssid)) {
/*      */         try {
/* 4168 */           PortalssidQuery apq = new PortalssidQuery();
/* 4169 */           apq.setSsid(ssid);
/* 4170 */           apq.setSsidLike(false);
/* 4171 */           List aps = this.ssidService.getPortalssidList(apq);
/* 4172 */           if ((aps != null) && (aps.size() > 0)) {
/* 4173 */             Portalssid ap = (Portalssid)aps.get(0);
/* 4174 */             ap.setBasip(basip);
/* 4175 */             long count = ap.getCount().longValue() + 1L;
/* 4176 */             ap.setCount(Long.valueOf(count));
/* 4177 */             this.ssidService.updatePortalssidByKey(ap);
/*      */           } else {
/* 4179 */             Portalssid ap = new Portalssid();
/* 4180 */             ap.setSsid(ssid);
/* 4181 */             ap.setBasip(basip);
/* 4182 */             ap.setCount(Long.valueOf(1L));
/* 4183 */             this.ssidService.addPortalssid(ap);
/*      */           }
/*      */         } catch (Exception e) {
/* 4186 */           logger.error("==============ERROR Start=============");
/* 4187 */           logger.error(e);
/* 4188 */           logger.error("ERROR INFO ", e);
/* 4189 */           logger.error("==============ERROR End=============");
/*      */         }
/*      */       }
/* 4192 */       if (stringUtils.isNotBlank(apmac)) {
/*      */         try {
/* 4194 */           PortalapQuery apq = new PortalapQuery();
/* 4195 */           apq.setMac(apmac);
/* 4196 */           apq.setMacLike(false);
/* 4197 */           List aps = this.apService.getPortalapList(apq);
/* 4198 */           if ((aps != null) && (aps.size() > 0)) {
/* 4199 */             Portalap ap = (Portalap)aps.get(0);
/* 4200 */             ap.setBasip(basip);
/* 4201 */             long count = ap.getCount().longValue() + 1L;
/* 4202 */             ap.setCount(Long.valueOf(count));
/* 4203 */             this.apService.updatePortalapByKey(ap);
/*      */           } else {
/* 4205 */             Portalap ap = new Portalap();
/* 4206 */             ap.setMac(apmac);
/* 4207 */             ap.setBasip(basip);
/* 4208 */             ap.setCount(Long.valueOf(1L));
/* 4209 */             this.apService.addPortalap(ap);
/*      */           }
/*      */         } catch (Exception e) {
/* 4212 */           logger.error("==============ERROR Start=============");
/* 4213 */           logger.error(e);
/* 4214 */           logger.error("ERROR INFO ", e);
/* 4215 */           logger.error("==============ERROR End=============");
/*      */         }
/*      */       }
/*      */ 
/* 4219 */       SangforLogin(ip, username, ikmac, apmac, basip);
/*      */ 
/* 4221 */       map.put("ret", "0");
/* 4222 */       map.put("i", ip);
/* 4223 */       map.put("u", username);
/* 4224 */       map.put("w", authUrl);
/* 4225 */       return map;
/*      */     }
/*      */ 
/* 4228 */     map.put("ret", "1");
/* 4229 */     map.put("i", ip);
/* 4230 */     map.put("u", username);
/* 4231 */     map.put("msg", "认证失败！");
/* 4232 */     return map;
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/changeip.action"})
/*      */   public void changeip(HttpServletResponse response, HttpServletRequest request)
/*      */     throws IOException
/*      */   {
/* 4243 */     String ikServer = GetNgnixRemotIP.getRemoteAddrIp(request);
/* 4244 */     String gwid = request.getParameter("gwid");
/* 4245 */     String nasname = request.getParameter("nasname");
/* 4246 */     String old_ip = request.getParameter("user_ip");
/* 4247 */     String new_ip = request.getParameter("new_ip");
/* 4248 */     String mac = request.getParameter("mac");
/* 4249 */     String basip = request.getParameter("basip");
/*      */ 
/* 4251 */     Portalbas basConfigFind = null;
/* 4252 */     if (stringUtils.isNotBlank(basip)) {
/* 4253 */       basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/*      */     }
/* 4255 */     if (stringUtils.isNotBlank(nasname)) {
/* 4256 */       PortalbasQuery bq = new PortalbasQuery();
/* 4257 */       bq.setBasname(nasname);
/* 4258 */       bq.setBasnameLike(false);
/* 4259 */       List bs = this.basService.getPortalbasList(bq);
/* 4260 */       if (bs.size() > 0) {
/* 4261 */         basConfigFind = (Portalbas)bs.get(0);
/* 4262 */         basip = basConfigFind.getBasIp();
/*      */       }
/*      */     }
/* 4265 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 4266 */     if (basConfigFind != null) {
/* 4267 */       basConfig = basConfigFind;
/*      */     }
/*      */ 
/* 4270 */     if (basConfig.getIsdebug().equals("1")) {
/* 4271 */       logger.info("BAS服务器" + ikServer + "发来用户IP切换消息: basip=" + basip + 
/* 4272 */         " gwid=" + gwid + " nasname=" + nasname + " mac=" + mac + 
/* 4273 */         " old_ip=" + old_ip + " new_ip=" + new_ip);
/*      */     }
/*      */ 
/* 4276 */     if ((stringUtils.isBlank(basip)) && 
/* 4277 */       (stringUtils.isNotBlank(nasname))) {
/* 4278 */       PortalbasQuery bq = new PortalbasQuery();
/* 4279 */       bq.setBasname(nasname);
/* 4280 */       bq.setBasnameLike(false);
/* 4281 */       List bs = this.basService.getPortalbasList(bq);
/* 4282 */       if (bs.size() > 0) {
/* 4283 */         basip = ((Portalbas)bs.get(0)).getBasIp();
/*      */       }
/*      */     }
/*      */ 
/* 4287 */     if (stringUtils.isBlank(basip)) {
/* 4288 */       if ("0".equals(basConfig.getIsOut())) {
/* 4289 */         basip = basConfig.getBasIp();
/*      */       }
/*      */     }
/*      */     else {
/* 4293 */       basip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 4296 */     if ((stringUtils.isNotBlank(old_ip)) && (stringUtils.isNotBlank(new_ip)) && 
/* 4297 */       (stringUtils.isNotBlank(mac))) {
/* 4298 */       mac = PortalUtil.MacFormat(mac);
/* 4299 */       iKuaiMacIpMap.getInstance().getMacIpMap().put(mac, new_ip);
/*      */ 
/* 4301 */       if (onlineMap.getOnlineUserMap().containsKey(old_ip + ":" + basip)) {
/* 4302 */         String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
/* 4303 */           old_ip + ":" + basip);
/* 4304 */         onlineMap.getOnlineUserMap().remove(old_ip + ":" + basip);
/* 4305 */         onlineMap.getOnlineUserMap().put(new_ip + ":" + basip, 
/* 4306 */           loginInfo);
/* 4307 */         if (basConfig.getIsdebug().equals("1")) {
/* 4308 */           logger.info("爱快漫游IP切换成功!! 移除失效列表【" + old_ip + ":" + basip + 
/* 4309 */             "】 启用新列表【" + new_ip + ":" + basip + "】");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4314 */     String respMessage = "HTTP/1.0 200 OK\r\n";
/* 4315 */     PrintWriter out = response.getWriter();
/* 4316 */     out.print(respMessage);
/* 4317 */     out.close();
/*      */   }
/*      */ 
/*      */   private int CheckMacTimeLimitMethod(String param, Long id)
/*      */   {
/* 4328 */     if (Do()) {
/* 4329 */       Portalonlinelimit onlinelimit = this.onlinelimitService
/* 4330 */         .getPortalonlinelimitByKey(id);
/* 4331 */       if (onlinelimit.getState().intValue() == 1) {
/* 4332 */         if (onlinelimit.getType().intValue() == 0) {
/* 4333 */           if ((stringUtils.isNotBlank(param)) && 
/* 4334 */             (!"error".equals(param))) {
/* 4335 */             String[] TimeInfo = 
/* 4336 */               (String[])MacLimitMap.getInstance()
/* 4336 */               .getMacLimitMap().get(param);
/* 4337 */             if (TimeInfo != null) {
/* 4338 */               Long timepermit = onlinelimit.getTime();
/* 4339 */               if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 4340 */                 return 1;
/*      */             }
/*      */           }
/*      */           else {
/* 4344 */             return 2;
/*      */           }
/*      */         }
/* 4347 */         else if (stringUtils.isNotBlank(param)) {
/* 4348 */           String[] TimeInfo = 
/* 4349 */             (String[])UserLimitMap.getInstance()
/* 4349 */             .getUserLimitMap().get(param);
/* 4350 */           if (TimeInfo != null) {
/* 4351 */             Long timepermit = onlinelimit.getTime();
/* 4352 */             if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 4353 */               return 1;
/*      */           }
/*      */         }
/*      */         else {
/* 4357 */           return 2;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4363 */     return 0;
/*      */   }
/*      */ 
/*      */   private void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, String authUser, String authPwd, String phone, HttpServletResponse response)
/*      */   {
/* 4374 */     Portalonlinelimit onlinelimit = this.onlinelimitService
/* 4375 */       .getPortalonlinelimitByKey(id);
/* 4376 */     Long timepermit = onlinelimit.getTime();
/* 4377 */     Long costTime = Long.valueOf(0L);
/* 4378 */     int haveTime = getHaveTime();
/* 4379 */     int toHaveTime = haveTime;
/* 4380 */     Long oldcostTime = Long.valueOf(0L);
/* 4381 */     Boolean notLimit = Boolean.valueOf(true);
/* 4382 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 4383 */       String[] macTimeInfo = 
/* 4384 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 4384 */         .get(mac);
/* 4385 */       if (macTimeInfo != null) {
/* 4386 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*      */       }
/* 4388 */       if (onlinelimit.getState().intValue() == 1) {
/* 4389 */         Date now = new Date();
/* 4390 */         String nowString = ThreadLocalDateUtil.format(now);
/* 4391 */         if (macTimeInfo == null) {
/* 4392 */           macTimeInfo = new String[3];
/* 4393 */           macTimeInfo[1] = "0";
/*      */         }
/* 4395 */         macTimeInfo[0] = nowString;
/* 4396 */         macTimeInfo[2] = String.valueOf(id);
/* 4397 */         MacLimitMap.getInstance().getMacLimitMap()
/* 4398 */           .put(mac, macTimeInfo);
/* 4399 */         costTime = oldcostTime;
/* 4400 */         haveTime = (int)(timepermit.longValue() / 60000L);
/* 4401 */         notLimit = Boolean.valueOf(false);
/*      */ 
/* 4403 */         if (id.longValue() == 1L) {
/* 4404 */           String[] TimeInfo = 
/* 4405 */             (String[])UserLimitMap.getInstance()
/* 4405 */             .getUserLimitMap().get(phone);
/* 4406 */           if (TimeInfo == null) {
/* 4407 */             TimeInfo = new String[3];
/* 4408 */             TimeInfo[1] = "0";
/*      */           }
/* 4410 */           TimeInfo[0] = nowString;
/* 4411 */           TimeInfo[2] = String.valueOf(id);
/* 4412 */           UserLimitMap.getInstance().getUserLimitMap()
/* 4413 */             .put(phone, TimeInfo);
/* 4414 */           if (onlinelimit.getType().intValue() == 1) {
/* 4415 */             costTime = Long.valueOf(TimeInfo[1]);
/* 4416 */             haveTime = (int)(timepermit.longValue() / 60000L);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 4421 */       Cookie cookieMac = new Cookie("mac", mac);
/* 4422 */       cookieMac.setMaxAge(86400);
/* 4423 */       response.addCookie(cookieMac);
/* 4424 */       session.setAttribute("ikmac", mac);
/*      */     }
/* 4426 */     int overTime = (int)(costTime.longValue() / 60000L);
/* 4427 */     haveTime -= overTime;
/* 4428 */     if (haveTime > toHaveTime) {
/* 4429 */       haveTime = toHaveTime;
/*      */     }
/* 4431 */     if (haveTime < 0) {
/* 4432 */       haveTime = 0;
/*      */     }
/* 4434 */     if (notLimit.booleanValue()) {
/* 4435 */       overTime += (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/* 4437 */     String haveTimeString = String.valueOf(haveTime);
/* 4438 */     String overTimeString = String.valueOf(overTime);
/* 4439 */     session.setAttribute("haveTime", haveTimeString);
/* 4440 */     session.setAttribute("overTime", overTimeString);
/*      */ 
/* 4442 */     Boolean isAuto = Boolean.valueOf(false);
/* 4443 */     if (isAuto.booleanValue())
/*      */     {
/* 4445 */       if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 4446 */         String[] userinfo = new String[3];
/* 4447 */         userinfo[0] = authUser;
/* 4448 */         userinfo[1] = authPwd;
/* 4449 */         userinfo[2] = phone;
/* 4450 */         AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response)
/*      */   {
/* 4462 */     Portalonlinelimit onlinelimit = this.onlinelimitService
/* 4463 */       .getPortalonlinelimitByKey(id);
/* 4464 */     Long timepermit = onlinelimit.getTime();
/* 4465 */     Long costTime = Long.valueOf(0L);
/* 4466 */     int haveTime = getHaveTime();
/* 4467 */     int toHaveTime = haveTime;
/* 4468 */     Long oldcostTime = Long.valueOf(0L);
/* 4469 */     Boolean notLimit = Boolean.valueOf(true);
/* 4470 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 4471 */       String[] macTimeInfo = 
/* 4472 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 4472 */         .get(mac);
/* 4473 */       if (macTimeInfo != null) {
/* 4474 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*      */       }
/* 4476 */       if ((onlinelimit.getState().intValue() == 1) && 
/* 4477 */         (onlinelimit.getType().intValue() == 0)) {
/* 4478 */         Date now = new Date();
/* 4479 */         String nowString = ThreadLocalDateUtil.format(now);
/* 4480 */         if (macTimeInfo == null) {
/* 4481 */           macTimeInfo = new String[3];
/* 4482 */           macTimeInfo[1] = "0";
/*      */         }
/* 4484 */         macTimeInfo[0] = nowString;
/* 4485 */         macTimeInfo[2] = String.valueOf(id);
/* 4486 */         MacLimitMap.getInstance().getMacLimitMap()
/* 4487 */           .put(mac, macTimeInfo);
/* 4488 */         costTime = oldcostTime;
/* 4489 */         haveTime = (int)(timepermit.longValue() / 60000L);
/* 4490 */         notLimit = Boolean.valueOf(false);
/*      */       }
/*      */ 
/* 4494 */       Cookie cookieMac = new Cookie("mac", mac);
/* 4495 */       cookieMac.setMaxAge(86400);
/* 4496 */       response.addCookie(cookieMac);
/* 4497 */       session.setAttribute("ikmac", mac);
/*      */     }
/* 4499 */     int overTime = (int)(costTime.longValue() / 60000L);
/* 4500 */     haveTime -= overTime;
/* 4501 */     if (haveTime > toHaveTime) {
/* 4502 */       haveTime = toHaveTime;
/*      */     }
/* 4504 */     if (haveTime < 0) {
/* 4505 */       haveTime = 0;
/*      */     }
/* 4507 */     if (notLimit.booleanValue()) {
/* 4508 */       overTime += (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/* 4510 */     String haveTimeString = String.valueOf(haveTime);
/* 4511 */     String overTimeString = String.valueOf(overTime);
/* 4512 */     session.setAttribute("haveTime", haveTimeString);
/* 4513 */     session.setAttribute("overTime", overTimeString);
/*      */   }
/*      */ 
/*      */   private void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response, String username)
/*      */   {
/* 4524 */     Portalonlinelimit onlinelimit = this.onlinelimitService
/* 4525 */       .getPortalonlinelimitByKey(id);
/* 4526 */     Long timepermit = onlinelimit.getTime();
/* 4527 */     Long costTime = Long.valueOf(0L);
/* 4528 */     int haveTime = (int)(getTime(username).longValue() / 60000L);
/* 4529 */     int toHaveTime = haveTime;
/* 4530 */     Long oldcostTime = Long.valueOf(0L);
/* 4531 */     Boolean notLimit = Boolean.valueOf(true);
/* 4532 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 4533 */       String[] macTimeInfo = 
/* 4534 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 4534 */         .get(mac);
/* 4535 */       if (macTimeInfo != null) {
/* 4536 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*      */       }
/* 4538 */       if ((onlinelimit.getState().intValue() == 1) && 
/* 4539 */         (onlinelimit.getType().intValue() == 0)) {
/* 4540 */         Date now = new Date();
/* 4541 */         String nowString = ThreadLocalDateUtil.format(now);
/* 4542 */         if (macTimeInfo == null) {
/* 4543 */           macTimeInfo = new String[3];
/* 4544 */           macTimeInfo[1] = "0";
/*      */         }
/* 4546 */         macTimeInfo[0] = nowString;
/* 4547 */         macTimeInfo[2] = String.valueOf(id);
/* 4548 */         MacLimitMap.getInstance().getMacLimitMap()
/* 4549 */           .put(mac, macTimeInfo);
/* 4550 */         costTime = oldcostTime;
/* 4551 */         haveTime = (int)(timepermit.longValue() / 60000L);
/* 4552 */         notLimit = Boolean.valueOf(false);
/*      */       }
/*      */ 
/* 4556 */       Cookie cookieMac = new Cookie("mac", mac);
/* 4557 */       cookieMac.setMaxAge(86400);
/* 4558 */       response.addCookie(cookieMac);
/* 4559 */       session.setAttribute("ikmac", mac);
/*      */     }
/* 4561 */     int overTime = (int)(costTime.longValue() / 60000L);
/* 4562 */     haveTime -= overTime;
/* 4563 */     if (haveTime > toHaveTime) {
/* 4564 */       haveTime = toHaveTime;
/*      */     }
/* 4566 */     if (haveTime < 0) {
/* 4567 */       haveTime = 0;
/*      */     }
/* 4569 */     if (notLimit.booleanValue()) {
/* 4570 */       overTime += (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/* 4572 */     String haveTimeString = String.valueOf(haveTime);
/* 4573 */     String overTimeString = String.valueOf(overTime);
/* 4574 */     session.setAttribute("haveTime", haveTimeString);
/* 4575 */     session.setAttribute("overTime", overTimeString);
/*      */   }
/*      */ 
/*      */   private Long getTime(String username)
/*      */   {
/* 4585 */     PortalaccountQuery query = new PortalaccountQuery();
/* 4586 */     query.setLoginName(username);
/* 4587 */     query.setLoginNameLike(false);
/* 4588 */     List as = this.accountService.getPortalaccountList(query);
/* 4589 */     if ((as != null) && (as.size() > 0)) {
/* 4590 */       Portalaccount a = (Portalaccount)as.get(0);
/* 4591 */       String userState = a.getState();
/* 4592 */       Date userDate = a.getDate();
/* 4593 */       Long userTime = a.getTime();
/*      */ 
/* 4595 */       if (userState.equals("0")) {
/* 4596 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 4599 */       if (userState.equals("1")) {
/* 4600 */         if (userTime.longValue() > 0L) {
/* 4601 */           return userTime;
/*      */         }
/* 4603 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 4607 */       if (userState.equals("3")) {
/* 4608 */         Date now = new Date();
/* 4609 */         if (userDate.getTime() > now.getTime()) {
/* 4610 */           return Long.valueOf(userDate.getTime() - now.getTime());
/*      */         }
/* 4612 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 4616 */       if (userState.equals("2")) {
/* 4617 */         if (userTime.longValue() > 0L) {
/* 4618 */           return userTime;
/*      */         }
/* 4620 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 4624 */       return Long.valueOf(0L);
/*      */     }
/* 4626 */     return Long.valueOf(getHaveTime() * 60000L);
/*      */   }
/*      */ 
/*      */   private static int getHaveTime()
/*      */   {
/*      */     try
/*      */     {
/* 4637 */       Date nowdate = new Date();
/* 4638 */       Calendar calendar = new GregorianCalendar();
/* 4639 */       calendar.setTime(nowdate);
/* 4640 */       calendar.add(5, 1);
/* 4641 */       Date tdate = calendar.getTime();
/* 4642 */       SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/* 4643 */       String tsString = fs.format(tdate);
/* 4644 */       Date t = fs.parse(tsString);
/* 4645 */       return (int)((t.getTime() - nowdate.getTime()) / 60000L); } catch (Exception e) {
/*      */     }
/* 4647 */     return 1440;
/*      */   }
/*      */ 
/*      */   private void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
/*      */   {
/* 4658 */     Portalautologin autologin = this.autologinService
/* 4659 */       .getPortalautologinByKey(id);
/* 4660 */     if ((autologin != null) && (autologin.getState().intValue() == 1) && 
/* 4661 */       (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 4662 */       String[] macTimeInfo = 
/* 4663 */         (String[])AutoLoginMacMap.getInstance()
/* 4663 */         .getAutoLoginMacMap().get(mac);
/* 4664 */       Date now = new Date();
/* 4665 */       String nowString = ThreadLocalDateUtil.format(now);
/* 4666 */       if (macTimeInfo == null) {
/* 4667 */         macTimeInfo = new String[3];
/* 4668 */         macTimeInfo[1] = "0";
/*      */       }
/* 4670 */       macTimeInfo[0] = nowString;
/* 4671 */       macTimeInfo[2] = String.valueOf(id);
/* 4672 */       AutoLoginMacMap.getInstance().getAutoLoginMacMap()
/* 4673 */         .put(mac, macTimeInfo);
/*      */ 
/* 4675 */       String[] userinfo = new String[3];
/* 4676 */       userinfo[0] = authUser;
/* 4677 */       userinfo[1] = authPwd;
/* 4678 */       userinfo[2] = username;
/* 4679 */       AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean Do() {
/* 4684 */     Long isThis = Long.valueOf(new Date().getTime());
/* 4685 */     boolean Do = false;
/* 4686 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 4687 */       Do = true;
/*      */     }
/* 4689 */     return Do;
/*      */   }
/*      */ 
/*      */   public static void SangforLogin(String userIP, String userName, String mac, String apmac, String basip)
/*      */   {
/* 4694 */     SangforRequest.login(userIP, userName, mac, apmac, basip);
/*      */   }
/*      */ 
/*      */   public static void SangforLogout(String userIP, String userName) {
/* 4698 */     SangforRequest.logout(userIP, userName);
/*      */   }
/*      */ 
/*      */   public static String getAgent(HttpServletRequest request) {
/* 4702 */     String userAgent = request.getHeader("user-agent");
/* 4703 */     String agent = "";
/* 4704 */     if (stringUtils.isNotBlank(userAgent)) {
/* 4705 */       if (userAgent.contains("Windows"))
/* 4706 */         agent = "Windows";
/* 4707 */       else if (userAgent.contains("Macintosh")) {
/* 4708 */         agent = "MacOS";
/*      */       }
/* 4710 */       else if (userAgent.contains("Linux"))
/* 4711 */         agent = "Android";
/* 4712 */       else if (userAgent.contains("Android"))
/* 4713 */         agent = "Android";
/* 4714 */       else if (userAgent.contains("iPhone"))
/* 4715 */         agent = "IOS";
/* 4716 */       else if (userAgent.contains("iPad"))
/* 4717 */         agent = "IOS";
/* 4718 */       else if (userAgent.contains("iPod")) {
/* 4719 */         agent = "IOS";
/*      */       }
/*      */     }
/*      */ 
/* 4723 */     return agent;
/*      */   }
/*      */ 
/*      */   public static String check(Portalbas basConfig, HttpServletRequest request)
/*      */   {
/* 4729 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 4729 */       Integer.valueOf(
/* 4730 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 4730 */       .get("core"))[1]).intValue())
/*      */     {
/* 4731 */       return "已超过允许最大用户数限制！！";
/*      */     }
/* 4733 */     String userAgent = request.getHeader("user-agent");
/* 4734 */     boolean isComputer = false;
/* 4735 */     String agent = "";
/* 4736 */     if (stringUtils.isNotBlank(userAgent)) {
/* 4737 */       if (userAgent.contains("Windows")) {
/* 4738 */         isComputer = true;
/* 4739 */         agent = "Windows";
/* 4740 */       } else if (userAgent.contains("Macintosh")) {
/* 4741 */         isComputer = true;
/* 4742 */         agent = "MacOS";
/*      */       }
/* 4744 */       else if (userAgent.contains("Linux")) {
/* 4745 */         isComputer = false;
/* 4746 */         agent = "Android";
/* 4747 */       } else if (userAgent.contains("Android")) {
/* 4748 */         isComputer = false;
/* 4749 */         agent = "Android";
/* 4750 */       } else if (userAgent.contains("iPhone")) {
/* 4751 */         isComputer = false;
/* 4752 */         agent = "IOS";
/* 4753 */       } else if (userAgent.contains("iPad")) {
/* 4754 */         isComputer = false;
/* 4755 */         agent = "IOS";
/* 4756 */       } else if (userAgent.contains("iPod")) {
/* 4757 */         isComputer = false;
/* 4758 */         agent = "IOS";
/*      */       }
/*      */     }
/*      */ 
/* 4762 */     if ((!"1".equals(basConfig.getIsComputer())) && 
/* 4763 */       (isComputer)) {
/* 4764 */       return "当前系统设置不允许电脑认证！！";
/*      */     }
/*      */ 
/* 4767 */     return "";
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_autoAuth"})
/*      */   public Map<String, String> autoAuth(String ip, String basip, String umac, String ssid, String apmac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 4775 */     Map map = new HashMap();
/* 4776 */     if (Do())
/*      */     {
/* 4778 */       if (onlineMap.getOnlineUserMap().size() < 
/* 4778 */         Integer.valueOf(
/* 4779 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 4779 */         .get("core"))[1]).intValue())
/*      */       {
/* 4780 */         HttpSession session = request.getSession();
/* 4781 */         Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 4782 */         String ikweb = 
/* 4783 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 4783 */           .get("core"))[0];
/* 4784 */         String site = (String)session.getAttribute("site");
/* 4785 */         if (stringUtils.isBlank(site)) {
/* 4786 */           site = "default";
/*      */         }
/* 4788 */         String ikmac = umac;
/* 4789 */         ikmac = PortalUtil.MacFormat(ikmac);
/* 4790 */         if (stringUtils.isBlank(ikmac)) {
/* 4791 */           ikmac = (String)session.getAttribute("ikmac");
/*      */         }
/*      */ 
/* 4794 */         Cookie[] cookies = request.getCookies();
/* 4795 */         String cmac = "";
/* 4796 */         if (cookies != null) {
/* 4797 */           for (int i = 0; i < cookies.length; i++) {
/* 4798 */             if (cookies[i].getName().equals("mac")) {
/* 4799 */               cmac = cookies[i].getValue();
/*      */             }
/*      */           }
/*      */         }
/* 4803 */         if (stringUtils.isBlank(ikmac)) {
/* 4804 */           ikmac = cmac;
/*      */         }
/*      */ 
/* 4807 */         if (stringUtils.isBlank(ip)) {
/* 4808 */           ip = (String)session.getAttribute("ip");
/*      */         }
/* 4810 */         if (stringUtils.isBlank(ip)) {
/* 4811 */           ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/*      */ 
/* 4814 */         if (stringUtils.isBlank(basip)) {
/* 4815 */           basip = (String)session.getAttribute("basip");
/*      */         }
/* 4817 */         if (stringUtils.isNotEmpty(basip)) {
/* 4818 */           basConfig = (Portalbas)config.getConfigMap().get(basip);
/*      */         }
/* 4820 */         if (basConfig == null) {
/* 4821 */           basConfig = (Portalbas)config.getConfigMap().get("");
/*      */         }
/* 4823 */         basip = basConfig.getBasIp();
/*      */         String authPwd;
/*      */         String password;
/* 4826 */         if (stringUtils.isNotBlank(ikmac)) {
/* 4827 */           String[] macTimeInfo = 
/* 4828 */             (String[])AutoLoginMacMap.getInstance()
/* 4828 */             .getAutoLoginMacMap().get(ikmac);
/* 4829 */           String[] userinfo = 
/* 4830 */             (String[])AutoLoginMap.getInstance()
/* 4830 */             .getAutoLoginMap().get(ikmac);
/* 4831 */           if ((macTimeInfo != null) && (userinfo != null)) {
/*      */             try {
/* 4833 */               Long id = Long.valueOf(macTimeInfo[2]);
/* 4834 */               if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
/* 4835 */                 String authUser = userinfo[0];
/* 4836 */                 authPwd = userinfo[1];
/* 4837 */                 String username = userinfo[2];
/* 4838 */                 Portalautologin autologin = this.autologinService
/* 4839 */                   .getPortalautologinByKey(id);
/* 4840 */                 if ((autologin != null) && 
/* 4841 */                   (autologin.getState().intValue() == 1)) {
/* 4842 */                   Long timepermit = autologin.getTime();
/* 4843 */                   String loginTimeString = macTimeInfo[0];
/* 4844 */                   Long oldcostTime = 
/* 4845 */                     Long.valueOf(macTimeInfo[1]);
/* 4846 */                   Long costTime = oldcostTime;
/*      */ 
/* 4848 */                   if (stringUtils.isNotBlank(loginTimeString)) {
/* 4849 */                     Date loginTime = 
/* 4850 */                       ThreadLocalDateUtil.parse(loginTimeString);
/* 4851 */                     String nowString = 
/* 4852 */                       ThreadLocalDateUtil.format(new Date());
/* 4853 */                     Date nowTime = 
/* 4854 */                       ThreadLocalDateUtil.parse(nowString);
/* 4855 */                     costTime = Long.valueOf(nowTime.getTime() - 
/* 4856 */                       loginTime.getTime() + 
/* 4857 */                       oldcostTime.longValue());
/*      */                   }
/* 4859 */                   if ((costTime.longValue() < timepermit.longValue()) || 
/* 4860 */                     (timepermit.longValue() <= 0L)) {
/* 4861 */                     Long userId = Long.valueOf(0L);
/* 4862 */                     String userState = null;
/* 4863 */                     password = authPwd;
/* 4864 */                     boolean CheckAccount = false;
/*      */                     Object o;
/* 4865 */                     if (3L == id.longValue())
/*      */                     {
/* 4867 */                       PortalaccountQuery accq = new PortalaccountQuery();
/* 4868 */                       accq.setLoginNameLike(false);
/* 4869 */                       accq.setLoginName(username);
/*      */ 
/* 4871 */                       if (!"1".equals(basConfig
/* 4871 */                         .getIsPortalCheck())) {
/* 4872 */                         accq.setPasswordLike(false);
/* 4873 */                         accq.setPassword(password);
/*      */                       }
/* 4875 */                       List accs = this.accountService
/* 4876 */                         .getPortalaccountList(accq);
/* 4877 */                       if ((accs != null) && 
/* 4878 */                         (accs.size() == 1)) {
/* 4879 */                         Portalaccount acc = 
/* 4880 */                           (Portalaccount)accs
/* 4880 */                           .get(0);
/* 4881 */                         if (acc != null) {
/* 4882 */                           userId = acc.getId();
/* 4883 */                           Date userDate = acc
/* 4884 */                             .getDate();
/* 4885 */                           Long userTime = acc
/* 4886 */                             .getTime();
/* 4887 */                           Long octets = acc
/* 4888 */                             .getOctets();
/* 4889 */                           if (octets == null) {
/* 4890 */                             octets = Long.valueOf(0L);
/*      */                           }
/* 4892 */                           userState = acc
/* 4893 */                             .getState();
/* 4894 */                           password = acc
/* 4895 */                             .getPassword();
/*      */ 
/* 4902 */                           if (checkTimeOut(
/* 4898 */                             userState, 
/* 4899 */                             userId, 
/* 4900 */                             userDate, 
/* 4901 */                             userTime, 
/* 4902 */                             octets)) {
/* 4903 */                             CheckAccount = true;
/*      */                           }
/*      */ 
/* 4908 */                           if (!"1"
/* 4907 */                             .equals(basConfig
/* 4908 */                             .getIsPortalCheck()))
/*      */                           {
/* 4910 */                             if (!password
/* 4910 */                               .equals(authPwd)) {
/* 4911 */                               CheckAccount = false;
/*      */                             }
/*      */ 
/*      */                           }
/*      */ 
/* 4916 */                           if (CheckAccount)
/*      */                           {
/* 4918 */                             if ("1".equals(basConfig
/* 4918 */                               .getIsPortalCheck())) {
/* 4919 */                               Integer limitCount = acc
/* 4920 */                                 .getMaclimitcount();
/* 4921 */                               if ((limitCount != null) && 
/* 4922 */                                 (limitCount.intValue() > 0)) {
/* 4923 */                                 int count = 0;
/* 4924 */                                 Iterator iterator = 
/* 4925 */                                   OnlineMap.getInstance()
/* 4926 */                                   .getOnlineUserMap()
/* 4927 */                                   .keySet()
/* 4928 */                                   .iterator();
/*      */ 
/* 4930 */                                 while (iterator
/* 4930 */                                   .hasNext()) {
/* 4931 */                                   o = iterator
/* 4932 */                                     .next();
/* 4933 */                                   String t = o
/* 4934 */                                     .toString();
/* 4935 */                                   String[] loginInfo = 
/* 4938 */                                     (String[])OnlineMap.getInstance()
/* 4937 */                                     .getOnlineUserMap()
/* 4938 */                                     .get(t);
/* 4939 */                                   String haveUsername = loginInfo[0];
/*      */ 
/* 4941 */                                   if (username
/* 4941 */                                     .equals(haveUsername)) {
/* 4942 */                                     count++;
/*      */                                   }
/*      */                                 }
/* 4945 */                                 if (count >= limitCount.intValue()) {
/* 4946 */                                   CheckAccount = false;
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     else
/*      */                     {
/* 4956 */                       CheckAccount = true;
/*      */                     }
/* 4958 */                     if (CheckAccount) {
/* 4959 */                       Boolean info = Boolean.valueOf(false);
/*      */ 
/* 4964 */                       if (((Portalbas)config
/* 4961 */                         .getConfigMap()
/* 4962 */                         .get(basip))
/* 4963 */                         .getBas()
/* 4964 */                         .equals("3")) {
/* 4965 */                         PortalaccountQuery aq = new PortalaccountQuery();
/* 4966 */                         aq.setLoginName(authUser);
/* 4967 */                         aq.setLoginNameLike(false);
/* 4968 */                         List accs = this.accountService
/* 4969 */                           .getPortalaccountList(aq);
/* 4970 */                         int accTime = 1440;
/* 4971 */                         if (accs.size() == 1) {
/* 4972 */                           long accTimeLong = 
/* 4973 */                             ((Portalaccount)accs
/* 4973 */                             .get(0))
/* 4974 */                             .getTime().longValue() / 60000L;
/* 4975 */                           if (accTimeLong > 0L) {
/* 4976 */                             accTime = (int)accTimeLong;
/*      */                           }
/*      */                         }
/* 4979 */                         info = 
/* 4980 */                           Boolean.valueOf(UniFiMethod.sendAuthorization(
/* 4981 */                           ikmac, 
/* 4982 */                           accTime, 
/* 4983 */                           site, basip));
/*      */                       } else {
/* 4985 */                         info = 
/* 4986 */                           InterfaceControl.Method("PORTAL_LOGIN", 
/* 4987 */                           authUser, 
/* 4988 */                           authPwd, 
/* 4989 */                           ip, basip, 
/* 4990 */                           ikmac);
/*      */                       }
/* 4992 */                       if (info.booleanValue())
/*      */                       {
/* 4995 */                         if (stringUtils.isNotBlank(ssid)) {
/*      */                           try {
/* 4997 */                             PortalssidQuery apq = new PortalssidQuery();
/* 4998 */                             apq.setSsid(ssid);
/* 4999 */                             apq.setSsidLike(false);
/* 5000 */                             List aps = this.ssidService
/* 5001 */                               .getPortalssidList(apq);
/* 5002 */                             if ((aps != null) && 
/* 5003 */                               (aps.size() > 0)) {
/* 5004 */                               Portalssid ap = 
/* 5005 */                                 (Portalssid)aps
/* 5005 */                                 .get(0);
/* 5006 */                               ap.setBasip(basip);
/* 5007 */                               long count = ap
/* 5008 */                                 .getCount().longValue() + 1L;
/* 5009 */                               ap.setCount(Long.valueOf(count));
/* 5010 */                               this.ssidService
/* 5011 */                                 .updatePortalssidByKey(ap);
/*      */                             } else {
/* 5013 */                               Portalssid ap = new Portalssid();
/* 5014 */                               ap.setSsid(ssid);
/* 5015 */                               ap.setBasip(basip);
/* 5016 */                               ap.setCount(Long.valueOf(1L));
/* 5017 */                               this.ssidService
/* 5018 */                                 .addPortalssid(ap);
/*      */                             }
/*      */                           } catch (Exception e) {
/* 5021 */                             logger.error("==============ERROR Start=============");
/* 5022 */                             logger.error(e);
/* 5023 */                             logger.error(
/* 5024 */                               "ERROR INFO ", 
/* 5025 */                               e);
/* 5026 */                             logger.error("==============ERROR End=============");
/*      */                           }
/*      */ 
/*      */                         }
/*      */ 
/* 5031 */                         if (stringUtils.isNotBlank(apmac)) {
/*      */                           try {
/* 5033 */                             PortalapQuery apq = new PortalapQuery();
/* 5034 */                             apq.setMac(apmac);
/* 5035 */                             apq.setMacLike(false);
/* 5036 */                             List aps = this.apService
/* 5037 */                               .getPortalapList(apq);
/* 5038 */                             if ((aps != null) && 
/* 5039 */                               (aps.size() > 0)) {
/* 5040 */                               Portalap ap = 
/* 5041 */                                 (Portalap)aps
/* 5041 */                                 .get(0);
/* 5042 */                               ap.setBasip(basip);
/* 5043 */                               long count = ap
/* 5044 */                                 .getCount().longValue() + 1L;
/* 5045 */                               ap.setCount(Long.valueOf(count));
/* 5046 */                               this.apService
/* 5047 */                                 .updatePortalapByKey(ap);
/*      */                             } else {
/* 5049 */                               Portalap ap = new Portalap();
/* 5050 */                               ap.setMac(apmac);
/* 5051 */                               ap.setBasip(basip);
/* 5052 */                               ap.setCount(Long.valueOf(1L));
/* 5053 */                               this.apService
/* 5054 */                                 .addPortalap(ap);
/*      */                             }
/*      */                           } catch (Exception e) {
/* 5057 */                             logger.error("==============ERROR Start=============");
/* 5058 */                             logger.error(e);
/* 5059 */                             logger.error(
/* 5060 */                               "ERROR INFO ", 
/* 5061 */                               e);
/* 5062 */                             logger.error("==============ERROR End=============");
/*      */                           }
/*      */ 
/*      */                         }
/*      */ 
/* 5067 */                         if ((stringUtils.isBlank(ikmac)) || 
/* 5069 */                           ("error"
/* 5069 */                           .equals(ikmac))) {
/* 5070 */                           ikmac = 
/* 5073 */                             (String)ipMacMap.getInstance()
/* 5072 */                             .getIpMacMap()
/* 5073 */                             .get(ip + ":" + 
/* 5074 */                             basip);
/*      */                         }
/*      */ 
/* 5077 */                         IndexAction.UpdateMacTimeLimitMethod(
/* 5078 */                           ikmac, id, 
/* 5079 */                           session, 
/* 5080 */                           username);
/*      */ 
/* 5082 */                         IndexAction.AutoLoginPutMethod(
/* 5083 */                           ikmac, id);
/*      */ 
/* 5085 */                         session.setAttribute(
/* 5086 */                           "username", 
/* 5087 */                           username);
/* 5088 */                         session.setAttribute(
/* 5089 */                           "password", 
/* 5090 */                           password);
/* 5091 */                         session.setAttribute("ip", 
/* 5092 */                           ip);
/* 5093 */                         session.setAttribute(
/* 5094 */                           "basip", basip);
/* 5095 */                         session.setAttribute(
/* 5096 */                           "ikmac", ikmac);
/*      */ 
/* 5098 */                         String[] loginInfo = new String[16];
/* 5099 */                         loginInfo[0] = username;
/* 5100 */                         Date now = new Date();
/* 5101 */                         loginInfo[3] = 
/* 5102 */                           ThreadLocalDateUtil.format(now);
/* 5103 */                         loginInfo[4] = ikmac;
/* 5104 */                         if ((3L == id.longValue()) || (4L == id.longValue())) {
/* 5105 */                           loginInfo[5] = "ok";
/*      */                         }
/* 5107 */                         if (3L == id.longValue())
/*      */                         {
/* 5109 */                           if ((stringUtils.isNotBlank(userState)) && 
/* 5110 */                             (userId != null)) {
/* 5111 */                             Long recordId = doLinkRecord(
/* 5112 */                               username, ip, 
/* 5113 */                               basip, 
/* 5114 */                               userState, 
/* 5115 */                               userId, ikmac);
/* 5116 */                             loginInfo[1] = 
/* 5117 */                               String.valueOf(userId);
/* 5118 */                             loginInfo[2] = 
/* 5119 */                               String.valueOf(recordId);
/* 5120 */                             session.setAttribute(
/* 5121 */                               "password", 
/* 5122 */                               password);
/*      */                           }
/*      */                         }
/* 5125 */                         String tid = "0";
/* 5126 */                         if (1L == id.longValue())
/* 5127 */                           tid = "4";
/* 5128 */                         else if (2L == id.longValue())
/* 5129 */                           tid = "0";
/* 5130 */                         else if (3L == id.longValue())
/* 5131 */                           tid = "1";
/* 5132 */                         else if (4L == id.longValue())
/* 5133 */                           tid = "2";
/* 5134 */                         else if (5L == id.longValue())
/* 5135 */                           tid = "3";
/* 5136 */                         else if (6L == id.longValue())
/* 5137 */                           tid = "5";
/* 5138 */                         else if (7L == id.longValue())
/* 5139 */                           tid = "6";
/* 5140 */                         else if (8L == id.longValue()) {
/* 5141 */                           tid = "7";
/*      */                         }
/* 5143 */                         loginInfo[6] = tid;
/* 5144 */                         loginInfo[7] = "0";
/* 5145 */                         loginInfo[8] = "0";
/*      */ 
/* 5147 */                         loginInfo[9] = ip;
/* 5148 */                         loginInfo[10] = basip;
/* 5149 */                         loginInfo[11] = 
/* 5151 */                           ((Portalbas)config
/* 5150 */                           .getConfigMap()
/* 5151 */                           .get(basip))
/* 5152 */                           .getBasname();
/* 5153 */                         loginInfo[12] = ssid;
/* 5154 */                         loginInfo[13] = apmac;
/* 5155 */                         loginInfo[14] = "yes";
/* 5156 */                         loginInfo[15] = getAgent(request);
/*      */ 
/* 5159 */                         onlineMap
/* 5160 */                           .getOnlineUserMap()
/* 5161 */                           .put(ip + ":" + 
/* 5162 */                           basip, 
/* 5163 */                           loginInfo);
/* 5164 */                         Portallogrecord logRecord = new Portallogrecord();
/*      */ 
/* 5166 */                         logRecord.setInfo("IP: " + 
/* 5167 */                           ip + ":" + basip + 
/* 5168 */                           " mac: " + ikmac + 
/* 5169 */                           " 用户: " + 
/* 5170 */                           username + 
/* 5171 */                           " ,无感知登录成功!");
/* 5172 */                         logRecord.setRecDate(now);
/* 5173 */                         this.logRecordService
/* 5174 */                           .addPortallogrecord(logRecord);
/*      */ 
/* 5176 */                         String authUrl = ikweb;
/* 5177 */                         PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 5178 */                         bsq.setBasip(basip);
/* 5179 */                         List<Portalbasauth> basauths = this.portalbasauthService
/* 5180 */                           .getPortalbasauthList(bsq);
/* 5181 */                         if (basauths.size() > 0) {
/* 5182 */                           for (Portalbasauth ba : basauths) {
/* 5183 */                             if (ba.getType() == 
/* 5184 */                               Integer.valueOf(tid)) {
/* 5185 */                               authUrl = ba
/* 5186 */                                 .getUrl();
/*      */ 
/* 5188 */                               if (!stringUtils.isBlank(authUrl)) break;
/* 5189 */                               authUrl = ikweb;
/*      */ 
/* 5191 */                               break;
/*      */                             }
/*      */                           }
/*      */ 
/*      */                         }
/*      */ 
/* 5197 */                         SangforLogin(ip, 
/* 5198 */                           username, 
/* 5199 */                           ikmac, 
/* 5200 */                           apmac, 
/* 5201 */                           basip);
/* 5202 */                         map.put("ret", "0");
/* 5203 */                         map.put("i", ip);
/* 5204 */                         map.put("u", username);
/* 5205 */                         map.put("w", authUrl);
/* 5206 */                         return map;
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception e) {
/* 5214 */               logger.error("==============ERROR Start=============");
/* 5215 */               logger.error(e);
/* 5216 */               logger.error("ERROR INFO ", e);
/* 5217 */               logger.error("==============ERROR End=============");
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 5223 */         if ((1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) && 
/* 5224 */           (1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))))
/*      */         {
/* 5226 */           Boolean info = Boolean.valueOf(false);
/*      */ 
/* 5228 */           String userMac = ikmac;
/*      */ 
/* 5230 */           if (stringUtils.isNotBlank(userMac))
/*      */           {
/* 5232 */             List<Portalaccountmacs> macs = this.macsService
/* 5233 */               .getPortalaccountmacsList(new PortalaccountmacsQuery());
/* 5234 */             label4452: for (Portalaccountmacs mac : macs) {
/* 5235 */               if (mac.getMac().equals(userMac)) {
/* 5236 */                 Portalaccount acc = this.accountService
/* 5237 */                   .getPortalaccountByKey(mac
/* 5238 */                   .getAccountId());
/* 5239 */                 if (acc == null) break;
/* 5240 */                 int state = acc.getAutologin().intValue();
/* 5241 */                 if (state != 1)
/*      */                   break;
/*      */                 List<Portalbasauth> basauths;
/* 5243 */                 if (basConfig.getAuthInterface()
/* 5243 */                   .contains("1"))
/*      */                 {
/* 5245 */                   PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 5246 */                   bsq.setBasip(basip);
/* 5247 */                   String authUser = basConfig
/* 5248 */                     .getBasUser();
/* 5249 */                   authPwd = basConfig
/* 5250 */                     .getBasPwd();
/* 5251 */                   String authUrl = ikweb;
/* 5252 */                   basauths = this.portalbasauthService
/* 5253 */                     .getPortalbasauthList(bsq);
/* 5254 */                   if (basauths.size() > 0) {
/* 5255 */                     for (Portalbasauth ba : basauths) {
/* 5256 */                       if (ba.getType().intValue() == 1) {
/* 5257 */                         authUser = ba
/* 5258 */                           .getUsername();
/* 5259 */                         authPwd = ba
/* 5260 */                           .getPassword();
/*      */ 
/* 5262 */                         if (stringUtils.isBlank(ikweb)) {
/* 5263 */                           authUrl = ba
/* 5264 */                             .getUrl();
/*      */                         }
/*      */ 
/* 5267 */                         if ((stringUtils.isBlank(authUser)) || 
/* 5269 */                           (stringUtils.isBlank(authPwd))) {
/* 5270 */                           authUser = basConfig
/* 5271 */                             .getBasUser();
/* 5272 */                           authPwd = basConfig
/* 5273 */                             .getBasPwd();
/*      */                         }
/*      */ 
/* 5276 */                         if (!stringUtils.isBlank(authUrl)) break;
/* 5277 */                         authUrl = ikweb;
/*      */ 
/* 5279 */                         break;
/*      */                       }
/*      */                     }
/*      */                   }
/*      */ 
/* 5284 */                   if (acc == null) break;
/* 5285 */                   Long userId = acc.getId();
/* 5286 */                   Date userDate = acc.getDate();
/* 5287 */                   Long userTime = acc.getTime();
/* 5288 */                   Long octets = acc.getOctets();
/* 5289 */                   if (octets == null) {
/* 5290 */                     octets = Long.valueOf(0L);
/*      */                   }
/* 5292 */                   String username = acc
/* 5293 */                     .getLoginName();
/* 5294 */                   String userState = acc
/* 5295 */                     .getState();
/* 5296 */                    password = acc
/* 5297 */                     .getPassword();
/*      */ 
/* 5300 */                   if (!"1".equals(basConfig
/* 5300 */                     .getIsPortalCheck())) {
/* 5301 */                     authUser = username;
/* 5302 */                     authPwd = password;
/*      */                   }
/*      */ 
/* 5307 */                   if (!checkTimeOut(userState, 
/* 5306 */                     userId, userDate, 
/* 5307 */                     userTime, octets)) {
/*      */                     break;
/*      */                   }
/* 5310 */                   boolean checkOnlineLimit = true;
/*      */ 
/* 5312 */                   if ("1".equals(basConfig
/* 5312 */                     .getIsPortalCheck())) {
/* 5313 */                     Integer limitCount = acc
/* 5314 */                       .getMaclimitcount();
/* 5315 */                     if ((limitCount != null) && 
/* 5316 */                       (limitCount.intValue() > 0)) {
/* 5317 */                       int countOnline = 0;
/* 5318 */                       Iterator iterator = 
/* 5319 */                         OnlineMap.getInstance()
/* 5320 */                         .getOnlineUserMap()
/* 5321 */                         .keySet()
/* 5322 */                         .iterator();
/*      */ 
/* 5324 */                       while (iterator
/* 5324 */                         .hasNext()) {
/* 5325 */                         Object o = iterator
/* 5326 */                           .next();
/* 5327 */                         String t = o
/* 5328 */                           .toString();
/* 5329 */                         String[] loginInfo = 
/* 5332 */                           (String[])OnlineMap.getInstance()
/* 5331 */                           .getOnlineUserMap()
/* 5332 */                           .get(t);
/* 5333 */                         String haveUsername = loginInfo[0];
/*      */ 
/* 5335 */                         if (username
/* 5335 */                           .equals(haveUsername)) {
/* 5336 */                           countOnline++;
/*      */                         }
/*      */                       }
/* 5339 */                       if (countOnline >= limitCount.intValue()) {
/* 5340 */                         checkOnlineLimit = false;
/*      */                       }
/*      */                     }
/*      */                   }
/*      */ 
/* 5345 */                   if (!checkOnlineLimit) {
/*      */                     break;
/*      */                   }
/* 5348 */                   if ((basConfig
/* 5347 */                     .getBas()
/* 5348 */                     .equals("1")) || 
/* 5351 */                     (basConfig
/* 5350 */                     .getBas()
/* 5351 */                     .equals("0"))) {
/* 5352 */                     info = 
/* 5353 */                       InterfaceControl.Method("PORTAL_LOGIN", 
/* 5354 */                       authUser, 
/* 5355 */                       authPwd, 
/* 5356 */                       ip, 
/* 5357 */                       basip, 
/* 5358 */                       ikmac);
/*      */                   }
/*      */ 
/* 5362 */                   if ((basConfig
/* 5361 */                     .getBas()
/* 5362 */                     .equals("2")) || 
/* 5365 */                     (basConfig
/* 5364 */                     .getBas()
/* 5365 */                     .equals("4"))) {
/* 5366 */                     info = 
/* 5367 */                       InterfaceControl.Method("PORTAL_LOGIN", 
/* 5368 */                       authUser, 
/* 5369 */                       authPwd, 
/* 5370 */                       ip, 
/* 5371 */                       basip, 
/* 5372 */                       ikmac);
/*      */                   }
/*      */ 
/* 5376 */                   if (basConfig
/* 5375 */                     .getBas()
/* 5376 */                     .equals("3")) {
/* 5377 */                     PortalaccountQuery aq = new PortalaccountQuery();
/* 5378 */                     aq.setLoginName(authUser);
/* 5379 */                     aq.setLoginNameLike(false);
/* 5380 */                     List accs = this.accountService
/* 5381 */                       .getPortalaccountList(aq);
/* 5382 */                     int accTime = 1440;
/* 5383 */                     if (accs.size() == 1) {
/* 5384 */                       long accTimeLong = 
/* 5385 */                         ((Portalaccount)accs
/* 5385 */                         .get(0))
/* 5386 */                         .getTime().longValue() / 60000L;
/* 5387 */                       if (accTimeLong > 0L) {
/* 5388 */                         accTime = (int)accTimeLong;
/*      */                       }
/*      */                     }
/* 5391 */                     info = 
/* 5392 */                       Boolean.valueOf(UniFiMethod.sendAuthorization(
/* 5393 */                       ikmac, 
/* 5394 */                       accTime, 
/* 5395 */                       site, 
/* 5396 */                       basip));
/*      */                   }
/* 5398 */                   if (!info.booleanValue())
/*      */                     break;
/* 5400 */                   Long recordId = doLinkRecord(
/* 5401 */                     username, 
/* 5402 */                     ip, basip, 
/* 5403 */                     userState, 
/* 5404 */                     userId, 
/* 5405 */                     ikmac);
/*      */ 
/* 5407 */                   session.setAttribute(
/* 5408 */                     "password", 
/* 5409 */                     password);
/* 5410 */                   Cookie cookiePwd = new Cookie(
/* 5411 */                     "password", 
/* 5412 */                     password);
/* 5413 */                   cookiePwd
/* 5414 */                     .setMaxAge(86400);
/* 5415 */                   response.addCookie(cookiePwd);
/* 5416 */                   session.setAttribute(
/* 5417 */                     "username", 
/* 5418 */                     username);
/* 5419 */                   session.setAttribute(
/* 5420 */                     "ip", ip);
/* 5421 */                   session.setAttribute(
/* 5422 */                     "basip", 
/* 5423 */                     basip);
/* 5424 */                   session.setAttribute(
/* 5425 */                     "ikmac", 
/* 5426 */                     ikmac);
/*      */ 
/* 5428 */                   Cookie cookieIp = new Cookie(
/* 5429 */                     "ip", ip);
/* 5430 */                   cookieIp.setMaxAge(86400);
/* 5431 */                   response.addCookie(cookieIp);
/*      */ 
/* 5433 */                   Cookie cookieBasIp = new Cookie(
/* 5434 */                     "basip", 
/* 5435 */                     basip);
/* 5436 */                   cookieBasIp
/* 5437 */                     .setMaxAge(86400);
/* 5438 */                   response.addCookie(cookieBasIp);
/*      */ 
/* 5441 */                   String[] loginInfo = new String[16];
/* 5442 */                   loginInfo[0] = username;
/* 5443 */                   loginInfo[1] = 
/* 5444 */                     String.valueOf(userId);
/* 5445 */                   loginInfo[2] = 
/* 5446 */                     String.valueOf(recordId);
/*      */ 
/* 5448 */                   Date now = new Date();
/* 5449 */                   loginInfo[3] = 
/* 5450 */                     ThreadLocalDateUtil.format(now);
/* 5451 */                   loginInfo[4] = ikmac;
/* 5452 */                   loginInfo[5] = "ok";
/* 5453 */                   loginInfo[6] = "1";
/* 5454 */                   loginInfo[7] = "0";
/* 5455 */                   loginInfo[8] = "0";
/*      */ 
/* 5457 */                   loginInfo[9] = ip;
/* 5458 */                   loginInfo[10] = basip;
/* 5459 */                   loginInfo[11] = 
/* 5461 */                     ((Portalbas)config
/* 5460 */                     .getConfigMap()
/* 5461 */                     .get(basip))
/* 5462 */                     .getBasname();
/* 5463 */                   loginInfo[12] = ssid;
/* 5464 */                   loginInfo[13] = apmac;
/* 5465 */                   loginInfo[14] = "yes";
/* 5466 */                   loginInfo[15] = getAgent(request);
/*      */ 
/* 5469 */                   onlineMap
/* 5470 */                     .getOnlineUserMap()
/* 5471 */                     .put(ip + 
/* 5472 */                     ":" + 
/* 5473 */                     basip, 
/* 5474 */                     loginInfo);
/*      */ 
/* 5477 */                   Portallogrecord logRecord = new Portallogrecord();
/* 5478 */                   logRecord
/* 5479 */                     .setInfo("IP: " + 
/* 5480 */                     ip + 
/* 5481 */                     ":" + 
/* 5482 */                     basip + 
/* 5483 */                     " mac: " + 
/* 5484 */                     ikmac + 
/* 5485 */                     " 系统用户: " + 
/* 5486 */                     username + 
/* 5487 */                     ",无感知登录成功!");
/* 5488 */                   logRecord
/* 5489 */                     .setRecDate(now);
/* 5490 */                   this.logRecordService
/* 5491 */                     .addPortallogrecord(logRecord);
/*      */ 
/* 5494 */                   IndexAction.UpdateMacTimeLimitMethod(
/* 5495 */                     ikmac, 
/* 5496 */                     Long.valueOf(3L), 
/* 5497 */                     request.getSession(), 
/* 5498 */                     username);
/*      */ 
/* 5501 */                   if (stringUtils.isNotBlank(ssid)) {
/*      */                     try {
/* 5503 */                       PortalssidQuery apq = new PortalssidQuery();
/* 5504 */                       apq.setSsid(ssid);
/* 5505 */                       apq.setSsidLike(false);
/* 5506 */                       List aps = this.ssidService
/* 5507 */                         .getPortalssidList(apq);
/* 5508 */                       if ((aps != null) && 
/* 5509 */                         (aps.size() > 0)) {
/* 5510 */                         Portalssid ap = 
/* 5511 */                           (Portalssid)aps
/* 5511 */                           .get(0);
/* 5512 */                         ap.setBasip(basip);
/* 5513 */                         long count = ap
/* 5514 */                           .getCount().longValue() + 1L;
/* 5515 */                         ap.setCount(Long.valueOf(count));
/* 5516 */                         this.ssidService
/* 5517 */                           .updatePortalssidByKey(ap);
/*      */                       } else {
/* 5519 */                         Portalssid ap = new Portalssid();
/* 5520 */                         ap.setSsid(ssid);
/* 5521 */                         ap.setBasip(basip);
/* 5522 */                         ap.setCount(Long.valueOf(1L));
/* 5523 */                         this.ssidService
/* 5524 */                           .addPortalssid(ap);
/*      */                       }
/*      */                     } catch (Exception e) {
/* 5527 */                       logger.error("==============ERROR Start=============");
/* 5528 */                       logger.error(e);
/* 5529 */                       logger.error(
/* 5530 */                         "ERROR INFO ", 
/* 5531 */                         e);
/* 5532 */                       logger.error("==============ERROR End=============");
/*      */                     }
/*      */ 
/*      */                   }
/*      */ 
/* 5537 */                   if (stringUtils.isNotBlank(apmac)) {
/*      */                     try {
/* 5539 */                       PortalapQuery apq = new PortalapQuery();
/* 5540 */                       apq.setMac(apmac);
/* 5541 */                       apq.setMacLike(false);
/* 5542 */                       List aps = this.apService
/* 5543 */                         .getPortalapList(apq);
/* 5544 */                       if ((aps != null) && 
/* 5545 */                         (aps.size() > 0)) {
/* 5546 */                         Portalap ap = 
/* 5547 */                           (Portalap)aps
/* 5547 */                           .get(0);
/* 5548 */                         ap.setBasip(basip);
/* 5549 */                         long count = ap
/* 5550 */                           .getCount().longValue() + 1L;
/* 5551 */                         ap.setCount(Long.valueOf(count));
/* 5552 */                         this.apService
/* 5553 */                           .updatePortalapByKey(ap);
/*      */                       } else {
/* 5555 */                         Portalap ap = new Portalap();
/* 5556 */                         ap.setMac(apmac);
/* 5557 */                         ap.setBasip(basip);
/* 5558 */                         ap.setCount(Long.valueOf(1L));
/* 5559 */                         this.apService
/* 5560 */                           .addPortalap(ap);
/*      */                       }
/*      */                     } catch (Exception e) {
/* 5563 */                       logger.error("==============ERROR Start=============");
/* 5564 */                       logger.error(e);
/* 5565 */                       logger.error(
/* 5566 */                         "ERROR INFO ", 
/* 5567 */                         e);
/* 5568 */                       logger.error("==============ERROR End=============");
/*      */                     }
/*      */ 
/*      */                   }
/*      */ 
/* 5573 */                   SangforLogin(
/* 5574 */                     ip, 
/* 5575 */                     username, 
/* 5576 */                     ikmac, 
/* 5577 */                     apmac, 
/* 5578 */                     basip);
/*      */ 
/* 5580 */                   map.put("ret", "0");
/* 5581 */                   map.put("i", ip);
/* 5582 */                   map.put("u", 
/* 5583 */                     username);
/* 5584 */                   map.put("w", 
/* 5585 */                     authUrl);
/* 5586 */                   return map;
/*      */                 }
/*      */ 
/* 5595 */                 PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 5596 */                 bsq.setBasip(basip);
/* 5597 */                 String authUrl = ikweb;
/* 5598 */                  basauths = this.portalbasauthService
/* 5599 */                   .getPortalbasauthList(bsq);
/* 5600 */                 if (basauths.size() > 0) {
/* 5601 */                   for (Portalbasauth ba : basauths) {
/* 5602 */                     if (ba.getType().intValue() == 2)
/*      */                     {
/* 5604 */                       if (stringUtils.isBlank(ikweb)) {
/* 5605 */                         authUrl = ba
/* 5606 */                           .getUrl();
/*      */                       }
/*      */ 
/* 5609 */                       if (!stringUtils.isBlank(authUrl)) break;
/* 5610 */                       authUrl = ikweb;
/*      */ 
/* 5612 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */ 
/* 5617 */                 String[] userinfo = null;
/* 5618 */                 if ((!stringUtils.isBlank(userMac)) && 
/* 5619 */                   (!"error"
/* 5619 */                   .equals(userMac))) {
/* 5620 */                   userinfo = 
/* 5623 */                     (String[])AutoLoginMap.getInstance()
/* 5622 */                     .getAutoLoginMap()
/* 5623 */                     .get(userMac);
/*      */                 }
/* 5625 */                 String username = "";
/* 5626 */                 password = "";
/* 5627 */                 String phone = "";
/* 5628 */                 if ((userinfo != null) && 
/* 5629 */                   (userinfo.length == 2)) {
/* 5630 */                   username = userinfo[0];
/* 5631 */                   password = userinfo[1];
/*      */                 }
/* 5633 */                 if ((userinfo != null) && 
/* 5634 */                   (userinfo.length == 3)) {
/* 5635 */                   username = userinfo[0];
/* 5636 */                   password = userinfo[1];
/* 5637 */                   phone = userinfo[2];
/*      */                 }
/*      */ 
/* 5640 */                 if (stringUtils.isNotBlank(username))
/*      */                 {
/* 5642 */                   if (stringUtils.isNotBlank(password))
/*      */                   {
/* 5644 */                     boolean CheckAccount = true;
/*      */ 
/* 5646 */                     if (!CheckAccount) {
/*      */                       break label4452;
/*      */                     }
/* 5649 */                     if ((basConfig
/* 5648 */                       .getBas()
/* 5649 */                       .equals("1")) || 
/* 5652 */                       (basConfig
/* 5651 */                       .getBas()
/* 5652 */                       .equals("0"))) {
/* 5653 */                       info = 
/* 5654 */                         InterfaceControl.Method("PORTAL_LOGIN", 
/* 5655 */                         username, 
/* 5656 */                         password, 
/* 5657 */                         ip, 
/* 5658 */                         basip, 
/* 5659 */                         ikmac);
/*      */                     }
/*      */ 
/* 5663 */                     if ((basConfig
/* 5662 */                       .getBas()
/* 5663 */                       .equals("2")) || 
/* 5666 */                       (basConfig
/* 5665 */                       .getBas()
/* 5666 */                       .equals("4"))) {
/* 5667 */                       info = 
/* 5668 */                         InterfaceControl.Method("PORTAL_LOGIN", 
/* 5669 */                         username, 
/* 5670 */                         password, 
/* 5671 */                         ip, 
/* 5672 */                         basip, 
/* 5673 */                         ikmac);
/*      */                     }
/*      */ 
/* 5677 */                     if (!basConfig
/* 5676 */                       .getBas()
/* 5677 */                       .equals("3")) break label4452;
/* 5678 */                     info = 
/* 5679 */                       Boolean.valueOf(UniFiMethod.sendAuthorization(
/* 5680 */                       ikmac, 
/* 5681 */                       1440, 
/* 5682 */                       site, 
/* 5683 */                       basip));
/*      */ 
/* 5687 */                     break label4452; } 
/* 5688 */                 }info = Boolean.valueOf(false);
/*      */ 
/* 5691 */                 if (!info.booleanValue()) {
/*      */                   break;
/*      */                 }
/* 5694 */                 if (stringUtils.isNotBlank(phone)) {
/* 5695 */                   username = phone;
/*      */                 }
/*      */ 
/* 5698 */                 session.setAttribute(
/* 5699 */                   "username", username);
/* 5700 */                 session.setAttribute(
/* 5701 */                   "password", password);
/* 5702 */                 session.setAttribute("ip", ip);
/* 5703 */                 session.setAttribute("basip", 
/* 5704 */                   basip);
/* 5705 */                 session.setAttribute("ikmac", 
/* 5706 */                   ikmac);
/*      */ 
/* 5708 */                 Cookie cookieIp = new Cookie(
/* 5709 */                   "ip", ip);
/* 5710 */                 cookieIp.setMaxAge(86400);
/* 5711 */                 response.addCookie(cookieIp);
/*      */ 
/* 5713 */                 Cookie cookieBasIp = new Cookie(
/* 5714 */                   "basip", basip);
/* 5715 */                 cookieBasIp
/* 5716 */                   .setMaxAge(86400);
/* 5717 */                 response.addCookie(cookieBasIp);
/*      */ 
/* 5720 */                 String[] loginInfo = new String[16];
/* 5721 */                 loginInfo[0] = username;
/*      */ 
/* 5723 */                 Date now = new Date();
/* 5724 */                 loginInfo[3] = 
/* 5725 */                   ThreadLocalDateUtil.format(now);
/* 5726 */                 loginInfo[4] = ikmac;
/* 5727 */                 loginInfo[5] = "ok";
/* 5728 */                 loginInfo[6] = "2";
/* 5729 */                 loginInfo[7] = "0";
/* 5730 */                 loginInfo[8] = "0";
/*      */ 
/* 5732 */                 loginInfo[9] = ip;
/* 5733 */                 loginInfo[10] = basip;
/* 5734 */                 loginInfo[11] = 
/* 5736 */                   ((Portalbas)config
/* 5735 */                   .getConfigMap()
/* 5736 */                   .get(basip))
/* 5737 */                   .getBasname();
/* 5738 */                 loginInfo[12] = ssid;
/* 5739 */                 loginInfo[13] = apmac;
/* 5740 */                 loginInfo[14] = "yes";
/* 5741 */                 loginInfo[15] = getAgent(request);
/*      */ 
/* 5744 */                 onlineMap.getOnlineUserMap()
/* 5745 */                   .put(ip + ":" + basip, 
/* 5746 */                   loginInfo);
/*      */ 
/* 5749 */                 Portallogrecord logRecord = new Portallogrecord();
/* 5750 */                 logRecord.setInfo("IP: " + ip + 
/* 5751 */                   ":" + basip + 
/* 5752 */                   " mac: " + ikmac + 
/* 5753 */                   " Radius用户: " + 
/* 5754 */                   username + 
/* 5755 */                   ",无感知登录成功!");
/* 5756 */                 logRecord.setRecDate(now);
/* 5757 */                 this.logRecordService
/* 5758 */                   .addPortallogrecord(logRecord);
/*      */ 
/* 5761 */                 IndexAction.UpdateMacTimeLimitMethod(
/* 5762 */                   ikmac, 
/* 5763 */                   Long.valueOf(4L), 
/* 5764 */                   request.getSession(), 
/* 5765 */                   username);
/*      */ 
/* 5768 */                 if (stringUtils.isNotBlank(ssid)) {
/*      */                   try {
/* 5770 */                     PortalssidQuery apq = new PortalssidQuery();
/* 5771 */                     apq.setSsid(ssid);
/* 5772 */                     apq.setSsidLike(false);
/* 5773 */                     List aps = this.ssidService
/* 5774 */                       .getPortalssidList(apq);
/* 5775 */                     if ((aps != null) && 
/* 5776 */                       (aps.size() > 0)) {
/* 5777 */                       Portalssid ap = 
/* 5778 */                         (Portalssid)aps
/* 5778 */                         .get(0);
/* 5779 */                       ap.setBasip(basip);
/* 5780 */                       long count = ap
/* 5781 */                         .getCount().longValue() + 1L;
/* 5782 */                       ap.setCount(Long.valueOf(count));
/* 5783 */                       this.ssidService
/* 5784 */                         .updatePortalssidByKey(ap);
/*      */                     } else {
/* 5786 */                       Portalssid ap = new Portalssid();
/* 5787 */                       ap.setSsid(ssid);
/* 5788 */                       ap.setBasip(basip);
/* 5789 */                       ap.setCount(Long.valueOf(1L));
/* 5790 */                       this.ssidService
/* 5791 */                         .addPortalssid(ap);
/*      */                     }
/*      */                   } catch (Exception e) {
/* 5794 */                     logger.error("==============ERROR Start=============");
/* 5795 */                     logger.error(e);
/* 5796 */                     logger.error(
/* 5797 */                       "ERROR INFO ", 
/* 5798 */                       e);
/* 5799 */                     logger.error("==============ERROR End=============");
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/* 5804 */                 if (stringUtils.isNotBlank(apmac)) {
/*      */                   try {
/* 5806 */                     PortalapQuery apq = new PortalapQuery();
/* 5807 */                     apq.setMac(apmac);
/* 5808 */                     apq.setMacLike(false);
/* 5809 */                     List aps = this.apService
/* 5810 */                       .getPortalapList(apq);
/* 5811 */                     if ((aps != null) && 
/* 5812 */                       (aps.size() > 0)) {
/* 5813 */                       Portalap ap = 
/* 5814 */                         (Portalap)aps
/* 5814 */                         .get(0);
/* 5815 */                       ap.setBasip(basip);
/* 5816 */                       long count = ap
/* 5817 */                         .getCount().longValue() + 1L;
/* 5818 */                       ap.setCount(Long.valueOf(count));
/* 5819 */                       this.apService
/* 5820 */                         .updatePortalapByKey(ap);
/*      */                     } else {
/* 5822 */                       Portalap ap = new Portalap();
/* 5823 */                       ap.setMac(apmac);
/* 5824 */                       ap.setBasip(basip);
/* 5825 */                       ap.setCount(Long.valueOf(1L));
/* 5826 */                       this.apService
/* 5827 */                         .addPortalap(ap);
/*      */                     }
/*      */                   } catch (Exception e) {
/* 5830 */                     logger.error("==============ERROR Start=============");
/* 5831 */                     logger.error(e);
/* 5832 */                     logger.error(
/* 5833 */                       "ERROR INFO ", 
/* 5834 */                       e);
/* 5835 */                     logger.error("==============ERROR End=============");
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/* 5840 */                 SangforLogin(ip, 
/* 5841 */                   username, 
/* 5842 */                   ikmac, apmac, 
/* 5843 */                   basip);
/*      */ 
/* 5845 */                 map.put("ret", "0");
/* 5846 */                 map.put("i", ip);
/* 5847 */                 map.put("u", username);
/* 5848 */                 map.put("w", authUrl);
/* 5849 */                 return map;
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 5863 */     map.put("ret", "1");
/* 5864 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_smsAuth"})
/*      */   public Map<String, String> smsAuth(String ip, String phone, String yzm, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 5875 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 5876 */     Map map = new HashMap();
/*      */ 
/* 5878 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/* 5881 */       String web = (String)session.getAttribute("web");
/* 5882 */       if (stringUtils.isNotBlank(web)) {
/* 5883 */         while (web.endsWith("/")) {
/* 5884 */           web = web.substring(0, web.length() - 1);
/*      */         }
/* 5886 */         Long webID = Long.valueOf(web);
/* 5887 */         if (webID.longValue() != 0L) {
/* 5888 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/* 5889 */           if (portalweb != null) {
/* 5890 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/* 5891 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/* 5894 */           com.leeson.core.bean.Config config = this.configService
/* 5895 */             .getConfigByKey(Long.valueOf(1L));
/* 5896 */           if (config != null) {
/* 5897 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 5898 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/* 5902 */         com.leeson.core.bean.Config config = this.configService
/* 5903 */           .getConfigByKey(Long.valueOf(1L));
/* 5904 */         if (config != null) {
/* 5905 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 5906 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/* 5913 */     if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
/* 5914 */       map.put("ret", "2");
/* 5915 */       map.put("msg", "请输入手机号和验证码！！");
/* 5916 */       return map;
/*      */     }
/* 5918 */     if (phone.length() != 11) {
/* 5919 */       map.put("ret", "2");
/* 5920 */       map.put("msg", "手机号码不正确！");
/* 5921 */       return map;
/*      */     }
/*      */     try {
/* 5924 */       Long.parseLong(phone);
/*      */     } catch (Exception e) {
/* 5926 */       map.put("ret", "2");
/* 5927 */       map.put("msg", "手机号码不正确！");
/* 5928 */       return map;
/*      */     }
/* 5930 */     String code = "";
/* 5931 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/* 5932 */     query.setState("1");
/* 5933 */     List smsList = this.portalsmsapiService
/* 5934 */       .getPortalsmsapiList(query);
/*      */     Portalsmsapi smsapi;
/* 5936 */     if (smsList.size() > 0)
/* 5937 */       smsapi = (Portalsmsapi)smsList.get(0);
/*      */     else {
/* 5939 */       smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*      */     }
/* 5941 */     Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
/* 5942 */     Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
/*      */     try {
/* 5944 */       Date saveDate = (Date)objs[1];
/* 5945 */       Long nowTime = Long.valueOf(new Date().getTime());
/* 5946 */       if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
/* 5947 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/* 5948 */         map.put("ret", "2");
/* 5949 */         map.put("msg", "验证码已过期，请重新获取验证码！");
/* 5950 */         return map;
/*      */       }
/* 5952 */       code = (String)objs[0];
/*      */     } catch (Exception e) {
/* 5954 */       map.put("ret", "2");
/* 5955 */       map.put("msg", "手机号或验证码不正确！");
/* 5956 */       return map;
/*      */     }
/*      */ 
/* 5959 */     if (!yzm.equals(code)) {
/* 5960 */       map.put("ret", "2");
/* 5961 */       map.put("msg", "验证码不正确！");
/* 5962 */       return map;
/*      */     }
/*      */ 
/* 5965 */     String ikmac = umac;
/* 5966 */     ikmac = PortalUtil.MacFormat(ikmac);
/* 5967 */     if (stringUtils.isBlank(ikmac)) {
/* 5968 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/*      */ 
/* 5971 */     String ikweb = (String)session.getAttribute("ikweb");
/* 5972 */     String authUrl = ikweb;
/*      */ 
/* 5975 */     Cookie[] cookies = request.getCookies();
/* 5976 */     String cip = "";
/* 5977 */     String cbasip = "";
/* 5978 */     String cmac = "";
/* 5979 */     if (cookies != null) {
/* 5980 */       for (int i = 0; i < cookies.length; i++) {
/* 5981 */         if (cookies[i].getName().equals("ip"))
/* 5982 */           cip = cookies[i].getValue();
/* 5983 */         else if (cookies[i].getName().equals("basip"))
/* 5984 */           cbasip = cookies[i].getValue();
/* 5985 */         else if (cookies[i].getName().equals("mac")) {
/* 5986 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 5990 */     if (stringUtils.isBlank(ikmac)) {
/* 5991 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 5994 */     if (stringUtils.isBlank(ip)) {
/* 5995 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 5997 */     if (stringUtils.isBlank(ip)) {
/* 5998 */       ip = cip;
/*      */     }
/* 6000 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 6002 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 6005 */     if (stringUtils.isBlank(basip)) {
/* 6006 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 6008 */     if (stringUtils.isBlank(basip)) {
/* 6009 */       basip = cbasip;
/*      */     }
/* 6011 */     if (stringUtils.isBlank(basip)) {
/* 6012 */       basip = basConfig.getBasIp();
/*      */     }
/* 6014 */     if (config.getConfigMap().get(basip) == null) {
/* 6015 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 6018 */     String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
/* 6019 */     if (checkResult != "") {
/* 6020 */       map.put("ret", "1");
/* 6021 */       map.put("msg", checkResult);
/* 6022 */       return map;
/*      */     }
/*      */ 
/* 6026 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/* 6028 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6028 */       .equals("4")))
/*      */     {
/* 6030 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 6032 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 6034 */       if (stringUtils.isBlank(ikmac)) {
/* 6035 */         map.put("ret", "10");
/* 6036 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 6037 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 6042 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 6043 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/* 6044 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/* 6045 */       ip = ikmac;
/* 6046 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 6048 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 6050 */       if (stringUtils.isBlank(ikmac)) {
/* 6051 */         map.put("ret", "10");
/* 6052 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 6053 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 6058 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 6059 */       ip + ":" + basip);
/* 6060 */     if (isLogin) {
/* 6061 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/* 6062 */         map.put("ret", "119");
/* 6063 */         map.put("i", ip);
/* 6064 */         map.put("u", phone);
/* 6065 */         map.put("msg", "你已经通过认证,请不要重复刷新！！");
/* 6066 */         return map;
/*      */       }
/* 6068 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/* 6072 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 6072 */       Integer.valueOf(
/* 6073 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 6073 */       .get("core"))[1]).intValue())
/*      */     {
/* 6074 */       map.put("ret", "110");
/* 6075 */       map.put("msg", "网络暂时不可用，请稍后再试！！");
/* 6076 */       return map;
/*      */     }
/*      */ 
/* 6079 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/* 6081 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6081 */       .equals("6")) || 
/* 6083 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6083 */       .equals("7")) || 
/* 6085 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6085 */       .equals("8"))) {
/* 6086 */       map.put("ret", "1");
/* 6087 */       map.put("msg", "该设备类型错误！！");
/* 6088 */       return map;
/*      */     }
/*      */ 
/* 6091 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("4"))
/*      */     {
/* 6093 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 6094 */       bsq.setBasip(basip);
/* 6095 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 6096 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 6097 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 6098 */         .getPortalbasauthList(bsq);
/* 6099 */       if (basauths.size() > 0) {
/* 6100 */         for (Portalbasauth ba : basauths) {
/* 6101 */           if (ba.getType().intValue() == 4) {
/* 6102 */             authUser = ba.getUsername();
/* 6103 */             authPwd = ba.getPassword();
/* 6104 */             authUrl = ba.getUrl();
/* 6105 */             if ((stringUtils.isBlank(authUser)) || 
/* 6106 */               (stringUtils.isBlank(authPwd))) {
/* 6107 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 6108 */                 .getBasUser();
/* 6109 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 6110 */                 .getBasPwd();
/*      */             }
/* 6112 */             if (!stringUtils.isBlank(authUrl)) break;
/* 6113 */             authUrl = ikweb;
/*      */ 
/* 6115 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 6119 */       if (stringUtils.isBlank(authUrl)) {
/* 6120 */         authUrl = 
/* 6121 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 6121 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 6125 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(1L))) {
/* 6126 */         map.put("ret", "2");
/* 6127 */         map.put("i", ip);
/* 6128 */         map.put("u", phone);
/* 6129 */         map.put("msg", "该设备当日时长已用完！！");
/* 6130 */         return map;
/*      */       }
/*      */ 
/* 6134 */       PortalaccountQuery accq = new PortalaccountQuery();
/* 6135 */       accq.setLoginName(phone);
/* 6136 */       accq.setLoginNameLike(false);
/* 6137 */       List accqs = this.accountService
/* 6138 */         .getPortalaccountList(accq);
/*      */       Portalaccount smsAcc;
/* 6139 */       if (accqs.size() > 0) {
/* 6140 */         Portalaccount acc = (Portalaccount)accqs.get(0);
/* 6141 */         acc.setPassword(yzm);
/* 6142 */         this.accountService.updatePortalaccountByKey(acc);
/* 6143 */         smsAcc = acc;
/*      */       } else {
/* 6145 */         Portalaccountgroup ag = this.portalaccountgroupService
/* 6146 */           .getPortalaccountgroupByKey(Long.valueOf(1L));
/* 6147 */         Portalaccount e = new Portalaccount();
/* 6148 */         e.setLoginName(phone);
/* 6149 */         e.setPassword(yzm);
/* 6150 */         e.setPhoneNumber(phone);
/* 6151 */         e.setName(phone);
/*      */ 
/* 6153 */         e.setState(ag.getState());
/* 6154 */         e.setMaclimitcount(ag.getMaclimitcount());
/* 6155 */         e.setMaclimit(ag.getMaclimit());
/* 6156 */         e.setAutologin(ag.getAutologin());
/* 6157 */         e.setSpeed(ag.getSpeed());
/* 6158 */         e.setDate(ag.getDate());
/* 6159 */         e.setTime(ag.getTime());
/* 6160 */         e.setOctets(ag.getOctets());
/* 6161 */         this.accountService.addPortalaccount(e);
/* 6162 */         smsAcc = e;
/*      */       }
/* 6164 */       authUser = smsAcc.getLoginName();
/* 6165 */       authPwd = smsAcc.getPassword();
/*      */ 
/* 6167 */       Boolean info = Boolean.valueOf(false);
/*      */ 
/* 6170 */       if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6170 */         .equals("3")) {
/* 6171 */         String site = (String)session.getAttribute("site");
/* 6172 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 6173 */         aq.setLoginName(authUser);
/* 6174 */         aq.setLoginNameLike(false);
/* 6175 */         List accs = this.accountService
/* 6176 */           .getPortalaccountList(aq);
/* 6177 */         int accTime = 1440;
/* 6178 */         if (accs.size() == 1) {
/* 6179 */           long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/* 6180 */           if (accTimeLong > 0L) {
/* 6181 */             accTime = (int)accTimeLong;
/*      */           }
/*      */         }
/* 6184 */         info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
/* 6185 */           basip));
/*      */       } else {
/* 6187 */         info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 6188 */           authPwd, ip, basip, ikmac);
/*      */       }
/*      */ 
/* 6191 */       if (info.booleanValue()) {
/* 6192 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(1L), session, authUser, authPwd, 
/* 6193 */           phone, response);
/*      */ 
/* 6195 */         AutoLoginPutMethod(ikmac, Long.valueOf(1L), authUser, authPwd, phone + 
/* 6196 */           "(无感知)");
/*      */ 
/* 6198 */         if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
/* 6199 */           PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/* 6200 */           mq.setAccountId(smsAcc.getId());
/* 6201 */           mq.setMac(ikmac.toLowerCase());
/* 6202 */           mq.setMacLike(false);
/* 6203 */           List accountmacs = this.macsService
/* 6204 */             .getPortalaccountmacsList(mq);
/* 6205 */           if (accountmacs.size() <= 0) {
/* 6206 */             Portalaccountmacs mac = new Portalaccountmacs();
/* 6207 */             mac.setAccountId(smsAcc.getId());
/* 6208 */             mac.setMac(ikmac.toLowerCase());
/* 6209 */             this.macsService.addPortalaccountmacs(mac);
/*      */           }
/*      */         }
/*      */ 
/* 6213 */         session.setAttribute("username", phone);
/*      */ 
/* 6215 */         session.setAttribute("ip", ip);
/* 6216 */         session.setAttribute("basip", basip);
/* 6217 */         session.setAttribute("ikmac", ikmac);
/*      */ 
/* 6227 */         if (stringUtils.isEmpty(ssid)) {
/* 6228 */           ssid = (String)session.getAttribute("ssid");
/*      */         }
/* 6230 */         if (stringUtils.isEmpty(apmac)) {
/* 6231 */           apmac = (String)session.getAttribute("apmac");
/*      */         }
/* 6233 */         String[] loginInfo = new String[16];
/* 6234 */         loginInfo[0] = phone;
/* 6235 */         loginInfo[1] = String.valueOf(smsAcc.getId());
/* 6236 */         Date now = new Date();
/* 6237 */         loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 6238 */         loginInfo[4] = ikmac;
/* 6239 */         loginInfo[5] = "ok";
/* 6240 */         loginInfo[6] = "1";
/*      */ 
/* 6242 */         loginInfo[7] = "0";
/* 6243 */         loginInfo[8] = "0";
/*      */ 
/* 6245 */         loginInfo[9] = ip;
/* 6246 */         loginInfo[10] = basip;
/* 6247 */         loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 6248 */         loginInfo[12] = ssid;
/* 6249 */         loginInfo[13] = apmac;
/* 6250 */         loginInfo[14] = "no";
/* 6251 */         loginInfo[15] = getAgent(request);
/*      */ 
/* 6253 */         onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/* 6254 */         Portallogrecord logRecord = new Portallogrecord();
/* 6255 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 6256 */           " 用户: " + phone + ",登录成功!");
/* 6257 */         logRecord.setRecDate(now);
/* 6258 */         this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 6260 */         String more = smsapi.getMore();
/* 6261 */         if ("0".equals(more)) {
/* 6262 */           PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*      */         }
/* 6264 */         session.setAttribute("ikweb", authUrl);
/*      */ 
/* 6266 */         if (stringUtils.isNotBlank(ssid)) {
/*      */           try {
/* 6268 */             PortalssidQuery apq = new PortalssidQuery();
/* 6269 */             apq.setSsid(ssid);
/* 6270 */             apq.setSsidLike(false);
/* 6271 */             List aps = this.ssidService
/* 6272 */               .getPortalssidList(apq);
/* 6273 */             if ((aps != null) && (aps.size() > 0)) {
/* 6274 */               Portalssid ap = (Portalssid)aps.get(0);
/* 6275 */               ap.setBasip(basip);
/* 6276 */               long count = ap.getCount().longValue() + 1L;
/* 6277 */               ap.setCount(Long.valueOf(count));
/* 6278 */               this.ssidService.updatePortalssidByKey(ap);
/*      */             } else {
/* 6280 */               Portalssid ap = new Portalssid();
/* 6281 */               ap.setSsid(ssid);
/* 6282 */               ap.setBasip(basip);
/* 6283 */               ap.setCount(Long.valueOf(1L));
/* 6284 */               this.ssidService.addPortalssid(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 6287 */             logger.error("==============ERROR Start=============");
/* 6288 */             logger.error(e);
/* 6289 */             logger.error("ERROR INFO ", e);
/* 6290 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/* 6293 */         if (stringUtils.isNotBlank(apmac)) {
/*      */           try {
/* 6295 */             PortalapQuery apq = new PortalapQuery();
/* 6296 */             apq.setMac(apmac);
/* 6297 */             apq.setMacLike(false);
/* 6298 */             List aps = this.apService.getPortalapList(apq);
/* 6299 */             if ((aps != null) && (aps.size() > 0)) {
/* 6300 */               Portalap ap = (Portalap)aps.get(0);
/* 6301 */               ap.setBasip(basip);
/* 6302 */               long count = ap.getCount().longValue() + 1L;
/* 6303 */               ap.setCount(Long.valueOf(count));
/* 6304 */               this.apService.updatePortalapByKey(ap);
/*      */             } else {
/* 6306 */               Portalap ap = new Portalap();
/* 6307 */               ap.setMac(apmac);
/* 6308 */               ap.setBasip(basip);
/* 6309 */               ap.setCount(Long.valueOf(1L));
/* 6310 */               this.apService.addPortalap(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 6313 */             logger.error("==============ERROR Start=============");
/* 6314 */             logger.error(e);
/* 6315 */             logger.error("ERROR INFO ", e);
/* 6316 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/*      */ 
/* 6320 */         SangforLogin(ip, phone, ikmac, apmac, basip);
/*      */ 
/* 6322 */         map.put("ret", "0");
/* 6323 */         map.put("i", ip);
/* 6324 */         map.put("u", phone);
/* 6325 */         map.put("w", authUrl);
/* 6326 */         map.put("msg", "认证成功！");
/* 6327 */         return map;
/*      */       }
/* 6329 */       map.put("ret", "1");
/* 6330 */       map.put("i", ip);
/* 6331 */       map.put("u", phone);
/* 6332 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/* 6333 */       return map;
/*      */     }
/*      */ 
/* 6337 */     map.put("ret", "3");
/* 6338 */     map.put("i", ip);
/* 6339 */     map.put("u", phone);
/* 6340 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/* 6341 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_autoAuthSms"})
/*      */   public Map<String, String> autoAuthSms(String ip, String basip, String umac, String ssid, String apmac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 6350 */     Map map = new HashMap();
/* 6351 */     if (Do())
/*      */     {
/* 6353 */       if (onlineMap.getOnlineUserMap().size() < 
/* 6353 */         Integer.valueOf(
/* 6354 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 6354 */         .get("core"))[1]).intValue())
/*      */       {
/* 6355 */         HttpSession session = request.getSession();
/* 6356 */         String ikmac = umac;
/* 6357 */         ikmac = PortalUtil.MacFormat(ikmac);
/* 6358 */         if (stringUtils.isBlank(ikmac)) {
/* 6359 */           ikmac = (String)session.getAttribute("ikmac");
/*      */         }
/*      */ 
/* 6362 */         Cookie[] cookies = request.getCookies();
/* 6363 */         String cmac = "";
/* 6364 */         if (cookies != null) {
/* 6365 */           for (int i = 0; i < cookies.length; i++) {
/* 6366 */             if (cookies[i].getName().equals("mac")) {
/* 6367 */               cmac = cookies[i].getValue();
/*      */             }
/*      */           }
/*      */         }
/* 6371 */         if (stringUtils.isBlank(ikmac)) {
/* 6372 */           ikmac = cmac;
/*      */         }
/*      */ 
/* 6375 */         if (stringUtils.isNotBlank(ikmac)) {
/* 6376 */           PortalaccountmacsQuery amq = new PortalaccountmacsQuery();
/* 6377 */           amq.setMac(ikmac);
/* 6378 */           amq.setMacLike(false);
/* 6379 */           amq.orderbyId(false);
/* 6380 */           List macs = this.macsService
/* 6381 */             .getPortalaccountmacsList(amq);
/* 6382 */           if (macs.size() > 0) {
/* 6383 */             Portalaccount acc = this.accountService
/* 6384 */               .getPortalaccountByKey(((Portalaccountmacs)macs.get(0))
/* 6385 */               .getAccountId());
/* 6386 */             if (acc != null) {
/* 6387 */               int state = acc.getAutologin().intValue();
/* 6388 */               if (state == 1) {
/* 6389 */                 map.put("ret", "0");
/* 6390 */                 map.put("u", acc.getLoginName());
/* 6391 */                 map.put("p", acc.getPassword());
/* 6392 */                 return map;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 6399 */     map.put("ret", "1");
/* 6400 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_smsOnekeyAuth"})
/*      */   public Map<String, String> smsOnekeyAuth(String usr, String pwd, String ip, String basip, String umac, String ssid, String apmac, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 6410 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 6411 */     Map map = new HashMap();
/*      */ 
/* 6413 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/* 6416 */       String web = (String)session.getAttribute("web");
/* 6417 */       if (stringUtils.isNotBlank(web)) {
/* 6418 */         while (web.endsWith("/")) {
/* 6419 */           web = web.substring(0, web.length() - 1);
/*      */         }
/* 6421 */         Long webID = Long.valueOf(web);
/* 6422 */         if (webID.longValue() != 0L) {
/* 6423 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/* 6424 */           if (portalweb != null) {
/* 6425 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/* 6426 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/* 6429 */           com.leeson.core.bean.Config config = this.configService
/* 6430 */             .getConfigByKey(Long.valueOf(1L));
/* 6431 */           if (config != null) {
/* 6432 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 6433 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/* 6437 */         com.leeson.core.bean.Config config = this.configService
/* 6438 */           .getConfigByKey(Long.valueOf(1L));
/* 6439 */         if (config != null) {
/* 6440 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 6441 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/* 6448 */     if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
/* 6449 */       map.put("ret", "2");
/* 6450 */       map.put("msg", "参数获取失败！！");
/* 6451 */       return map;
/*      */     }
/*      */ 
/* 6455 */     String ikmac = umac;
/* 6456 */     ikmac = PortalUtil.MacFormat(ikmac);
/* 6457 */     if (stringUtils.isBlank(ikmac)) {
/* 6458 */       ikmac = (String)session.getAttribute("ikmac");
/*      */     }
/*      */ 
/* 6461 */     String ikweb = (String)session.getAttribute("ikweb");
/* 6462 */     String authUrl = ikweb;
/*      */ 
/* 6465 */     Cookie[] cookies = request.getCookies();
/* 6466 */     String cip = "";
/* 6467 */     String cbasip = "";
/* 6468 */     String cmac = "";
/* 6469 */     if (cookies != null) {
/* 6470 */       for (int i = 0; i < cookies.length; i++) {
/* 6471 */         if (cookies[i].getName().equals("ip"))
/* 6472 */           cip = cookies[i].getValue();
/* 6473 */         else if (cookies[i].getName().equals("basip"))
/* 6474 */           cbasip = cookies[i].getValue();
/* 6475 */         else if (cookies[i].getName().equals("mac")) {
/* 6476 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 6480 */     if (stringUtils.isBlank(ikmac)) {
/* 6481 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 6484 */     if (stringUtils.isBlank(ip)) {
/* 6485 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 6487 */     if (stringUtils.isBlank(ip)) {
/* 6488 */       ip = cip;
/*      */     }
/* 6490 */     if (stringUtils.isBlank(ip))
/*      */     {
/* 6492 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 6495 */     if (stringUtils.isBlank(basip)) {
/* 6496 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 6498 */     if (stringUtils.isBlank(basip)) {
/* 6499 */       basip = cbasip;
/*      */     }
/* 6501 */     if (stringUtils.isBlank(basip)) {
/* 6502 */       basip = basConfig.getBasIp();
/*      */     }
/* 6504 */     if (config.getConfigMap().get(basip) == null) {
/* 6505 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 6508 */     String checkResult = check((Portalbas)config.getConfigMap().get(basip), request);
/* 6509 */     if (checkResult != "") {
/* 6510 */       map.put("ret", "1");
/* 6511 */       map.put("msg", checkResult);
/* 6512 */       return map;
/*      */     }
/*      */ 
/* 6516 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("2")) || 
/* 6518 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6518 */       .equals("4")))
/*      */     {
/* 6520 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 6522 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 6524 */       if (stringUtils.isBlank(ikmac)) {
/* 6525 */         map.put("ret", "10");
/* 6526 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 6527 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 6532 */     if (((Portalbas)config.getConfigMap().get(basip)).getBas().equals("3")) {
/* 6533 */       basip = ((Portalbas)config.getConfigMap().get(basip)).getBasIp();
/* 6534 */       ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/* 6535 */       ip = ikmac;
/* 6536 */       if (stringUtils.isBlank(ip))
/*      */       {
/* 6538 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/* 6540 */       if (stringUtils.isBlank(ikmac)) {
/* 6541 */         map.put("ret", "10");
/* 6542 */         map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 6543 */         return map;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 6548 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 6549 */       ip + ":" + basip);
/* 6550 */     if (isLogin) {
/* 6551 */       if (((Portalbas)config.getConfigMap().get(basip)).getIsNtf().intValue() == 1) {
/* 6552 */         map.put("ret", "119");
/* 6553 */         map.put("i", ip);
/* 6554 */         map.put("u", usr);
/* 6555 */         map.put("msg", "你已经通过认证,请不要重复刷新！！");
/* 6556 */         return map;
/*      */       }
/* 6558 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/* 6562 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 6562 */       Integer.valueOf(
/* 6563 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 6563 */       .get("core"))[1]).intValue())
/*      */     {
/* 6564 */       map.put("ret", "110");
/* 6565 */       map.put("msg", "网络暂时不可用，请稍后再试！！");
/* 6566 */       return map;
/*      */     }
/*      */ 
/* 6569 */     if ((((Portalbas)config.getConfigMap().get(basip)).getBas().equals("5")) || 
/* 6571 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6571 */       .equals("6")) || 
/* 6573 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6573 */       .equals("7")) || 
/* 6575 */       (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6575 */       .equals("8"))) {
/* 6576 */       map.put("ret", "1");
/* 6577 */       map.put("msg", "该设备类型错误！！");
/* 6578 */       return map;
/*      */     }
/*      */ 
/* 6581 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("4"))
/*      */     {
/* 6583 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 6584 */       bsq.setBasip(basip);
/* 6585 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 6586 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 6587 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 6588 */         .getPortalbasauthList(bsq);
/* 6589 */       if (basauths.size() > 0) {
/* 6590 */         for (Portalbasauth ba : basauths) {
/* 6591 */           if (ba.getType().intValue() == 4) {
/* 6592 */             authUser = ba.getUsername();
/* 6593 */             authPwd = ba.getPassword();
/* 6594 */             authUrl = ba.getUrl();
/* 6595 */             if ((stringUtils.isBlank(authUser)) || 
/* 6596 */               (stringUtils.isBlank(authPwd))) {
/* 6597 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 6598 */                 .getBasUser();
/* 6599 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 6600 */                 .getBasPwd();
/*      */             }
/* 6602 */             if (!stringUtils.isBlank(authUrl)) break;
/* 6603 */             authUrl = ikweb;
/*      */ 
/* 6605 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 6609 */       if (stringUtils.isBlank(authUrl)) {
/* 6610 */         authUrl = 
/* 6611 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 6611 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 6615 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(1L))) {
/* 6616 */         map.put("ret", "2");
/* 6617 */         map.put("i", ip);
/* 6618 */         map.put("u", usr);
/* 6619 */         map.put("msg", "该设备当日时长已用完！！");
/* 6620 */         return map;
/*      */       }
/*      */ 
/* 6624 */       PortalaccountQuery accq = new PortalaccountQuery();
/* 6625 */       accq.setLoginName(usr);
/* 6626 */       accq.setLoginNameLike(false);
/* 6627 */       List accqs = this.accountService
/* 6628 */         .getPortalaccountList(accq);
/*      */       Portalaccount smsAcc;
/* 6629 */       if (accqs.size() > 0) {
/* 6630 */         Portalaccount acc = (Portalaccount)accqs.get(0);
/* 6631 */         smsAcc = acc;
/*      */       } else {
/* 6633 */         map.put("ret", "2");
/* 6634 */         map.put("i", ip);
/* 6635 */         map.put("u", usr);
/* 6636 */         map.put("msg", "用户不存在！！");
/* 6637 */         return map;
/*      */       }
/* 6639 */       authUser = smsAcc.getLoginName();
/* 6640 */       authPwd = smsAcc.getPassword();
/*      */ 
/* 6642 */       Boolean info = Boolean.valueOf(false);
/*      */ 
/* 6645 */       if (((Portalbas)config.getConfigMap().get(basip)).getBas()
/* 6645 */         .equals("3")) {
/* 6646 */         String site = (String)session.getAttribute("site");
/* 6647 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 6648 */         aq.setLoginName(authUser);
/* 6649 */         aq.setLoginNameLike(false);
/* 6650 */         List accs = this.accountService
/* 6651 */           .getPortalaccountList(aq);
/* 6652 */         int accTime = 1440;
/* 6653 */         if (accs.size() == 1) {
/* 6654 */           long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/* 6655 */           if (accTimeLong > 0L) {
/* 6656 */             accTime = (int)accTimeLong;
/*      */           }
/*      */         }
/* 6659 */         info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, 
/* 6660 */           basip));
/*      */       } else {
/* 6662 */         info = InterfaceControl.Method("PORTAL_LOGIN", authUser, 
/* 6663 */           authPwd, ip, basip, ikmac);
/*      */       }
/*      */ 
/* 6666 */       if (info.booleanValue()) {
/* 6667 */         UpdateMacTimeLimitMethod(ikmac, Long.valueOf(1L), session, authUser, authPwd, 
/* 6668 */           usr, response);
/*      */ 
/* 6670 */         AutoLoginPutMethod(ikmac, Long.valueOf(1L), authUser, authPwd, usr + 
/* 6671 */           "(无感知)");
/*      */ 
/* 6674 */         session.setAttribute("username", usr);
/*      */ 
/* 6676 */         session.setAttribute("ip", ip);
/* 6677 */         session.setAttribute("basip", basip);
/* 6678 */         session.setAttribute("ikmac", ikmac);
/*      */ 
/* 6688 */         if (stringUtils.isEmpty(ssid)) {
/* 6689 */           ssid = (String)session.getAttribute("ssid");
/*      */         }
/* 6691 */         if (stringUtils.isEmpty(apmac)) {
/* 6692 */           apmac = (String)session.getAttribute("apmac");
/*      */         }
/* 6694 */         String[] loginInfo = new String[16];
/* 6695 */         loginInfo[0] = usr;
/* 6696 */         loginInfo[1] = String.valueOf(smsAcc.getId());
/* 6697 */         Date now = new Date();
/* 6698 */         loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 6699 */         loginInfo[4] = ikmac;
/* 6700 */         loginInfo[5] = "ok";
/* 6701 */         loginInfo[6] = "1";
/*      */ 
/* 6703 */         loginInfo[7] = "0";
/* 6704 */         loginInfo[8] = "0";
/*      */ 
/* 6706 */         loginInfo[9] = ip;
/* 6707 */         loginInfo[10] = basip;
/* 6708 */         loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 6709 */         loginInfo[12] = ssid;
/* 6710 */         loginInfo[13] = apmac;
/* 6711 */         loginInfo[14] = "no";
/* 6712 */         loginInfo[15] = getAgent(request);
/*      */ 
/* 6714 */         onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/* 6715 */         Portallogrecord logRecord = new Portallogrecord();
/* 6716 */         logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 6717 */           " 用户: " + usr + ",登录成功!");
/* 6718 */         logRecord.setRecDate(now);
/* 6719 */         this.logRecordService.addPortallogrecord(logRecord);
/*      */ 
/* 6721 */         session.setAttribute("ikweb", authUrl);
/*      */ 
/* 6723 */         if (stringUtils.isNotBlank(ssid)) {
/*      */           try {
/* 6725 */             PortalssidQuery apq = new PortalssidQuery();
/* 6726 */             apq.setSsid(ssid);
/* 6727 */             apq.setSsidLike(false);
/* 6728 */             List aps = this.ssidService
/* 6729 */               .getPortalssidList(apq);
/* 6730 */             if ((aps != null) && (aps.size() > 0)) {
/* 6731 */               Portalssid ap = (Portalssid)aps.get(0);
/* 6732 */               ap.setBasip(basip);
/* 6733 */               long count = ap.getCount().longValue() + 1L;
/* 6734 */               ap.setCount(Long.valueOf(count));
/* 6735 */               this.ssidService.updatePortalssidByKey(ap);
/*      */             } else {
/* 6737 */               Portalssid ap = new Portalssid();
/* 6738 */               ap.setSsid(ssid);
/* 6739 */               ap.setBasip(basip);
/* 6740 */               ap.setCount(Long.valueOf(1L));
/* 6741 */               this.ssidService.addPortalssid(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 6744 */             logger.error("==============ERROR Start=============");
/* 6745 */             logger.error(e);
/* 6746 */             logger.error("ERROR INFO ", e);
/* 6747 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/* 6750 */         if (stringUtils.isNotBlank(apmac)) {
/*      */           try {
/* 6752 */             PortalapQuery apq = new PortalapQuery();
/* 6753 */             apq.setMac(apmac);
/* 6754 */             apq.setMacLike(false);
/* 6755 */             List aps = this.apService.getPortalapList(apq);
/* 6756 */             if ((aps != null) && (aps.size() > 0)) {
/* 6757 */               Portalap ap = (Portalap)aps.get(0);
/* 6758 */               ap.setBasip(basip);
/* 6759 */               long count = ap.getCount().longValue() + 1L;
/* 6760 */               ap.setCount(Long.valueOf(count));
/* 6761 */               this.apService.updatePortalapByKey(ap);
/*      */             } else {
/* 6763 */               Portalap ap = new Portalap();
/* 6764 */               ap.setMac(apmac);
/* 6765 */               ap.setBasip(basip);
/* 6766 */               ap.setCount(Long.valueOf(1L));
/* 6767 */               this.apService.addPortalap(ap);
/*      */             }
/*      */           } catch (Exception e) {
/* 6770 */             logger.error("==============ERROR Start=============");
/* 6771 */             logger.error(e);
/* 6772 */             logger.error("ERROR INFO ", e);
/* 6773 */             logger.error("==============ERROR End=============");
/*      */           }
/*      */         }
/*      */ 
/* 6777 */         SangforLogin(ip, usr, ikmac, apmac, basip);
/*      */ 
/* 6779 */         map.put("ret", "0");
/* 6780 */         map.put("i", ip);
/* 6781 */         map.put("u", usr);
/* 6782 */         map.put("w", authUrl);
/* 6783 */         map.put("msg", "认证成功！");
/* 6784 */         return map;
/*      */       }
/* 6786 */       map.put("ret", "1");
/* 6787 */       map.put("i", ip);
/* 6788 */       map.put("u", usr);
/* 6789 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/* 6790 */       return map;
/*      */     }
/*      */ 
/* 6794 */     map.put("ret", "3");
/* 6795 */     map.put("i", ip);
/* 6796 */     map.put("u", usr);
/* 6797 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/* 6798 */     return map;
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxInterFaceController
 * JD-Core Version:    0.6.2
 */