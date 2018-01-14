/*      */ package com.leeson.core.controller;
/*      */ 
/*      */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalaccountmacs;
/*      */ import com.leeson.core.bean.Portalbas;
/*      */ import com.leeson.core.bean.Portalbasauth;
/*      */ import com.leeson.core.bean.Portalonlinelimit;
/*      */ import com.leeson.core.bean.Portalsmsapi;
/*      */ import com.leeson.core.bean.Portalweb;
/*      */ import com.leeson.core.bean.Portalweixinwifi;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalaccountmacsQuery;
/*      */ import com.leeson.core.query.PortalbasauthQuery;
/*      */ import com.leeson.core.query.PortalsmsapiQuery;
/*      */ import com.leeson.core.query.PortalweixinwifiQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalaccountmacsService;
/*      */ import com.leeson.core.service.PortalbasauthService;
/*      */ import com.leeson.core.service.PortalonlinelimitService;
/*      */ import com.leeson.core.service.PortalsmsapiService;
/*      */ import com.leeson.core.service.PortalwebService;
/*      */ import com.leeson.core.service.PortalweixinwifiService;
/*      */ import com.leeson.core.utils.Kick;
/*      */ import com.leeson.core.utils.TwoDimensionCode.makeImg;
/*      */ import com.leeson.portal.core.model.GuestAuthMap;
/*      */ import com.leeson.portal.core.model.MacLimitMap;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.portal.core.model.PhoneCodeMap;
/*      */ import com.leeson.portal.core.model.RosAuthMap;
/*      */ import com.leeson.portal.core.model.UserLimitMap;
/*      */ import com.leeson.portal.core.model.isDo;
/*      */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*      */ import java.io.File;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.codec.digest.DigestUtils;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Controller;
/*      */ import org.springframework.ui.ModelMap;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.ResponseBody;
/*      */ 
/*      */ @Controller
/*      */ public class AjaxInterFaceWISPrController
/*      */ {
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountService accountService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalweixinwifiService weixinwifiService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountmacsService macsService;
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
/*      */   private ConfigService configService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalwebService webService;
/*   90 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*      */ 
/*   92 */   private static Logger logger = Logger.getLogger(AjaxInterFaceWISPrController.class);
/*   93 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WISPr_guestHeart"})
/*      */   public Map<String, String> ajax_WISPr_guestHeart(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  101 */     HttpSession session = request.getSession();
/*  102 */     String ikmac = (String)session.getAttribute("ikmac");
/*  103 */     String ikweb = (String)session.getAttribute("ikweb");
/*  104 */     String authUrl = ikweb;
/*  105 */     Map map = new HashMap();
/*      */ 
/*  107 */     String accountInfo = null;
/*  108 */     accountInfo = 
/*  109 */       (String)GuestAuthMap.getInstance().getGuestAuthMap()
/*  109 */       .get(ip + ":" + basip);
/*  110 */     if (stringUtils.isNotBlank(accountInfo)) {
/*  111 */       String username = accountInfo;
/*      */ 
/*  113 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  114 */       bsq.setBasip(basip);
/*  115 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/*  116 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/*  117 */       List<Portalbasauth> basauths = this.portalbasauthService
/*  118 */         .getPortalbasauthList(bsq);
/*  119 */       if (basauths.size() > 0) {
/*  120 */         for (Portalbasauth ba : basauths) {
/*  121 */           if (ba.getType().intValue() == 7) {
/*  122 */             authUser = ba.getUsername();
/*  123 */             authPwd = ba.getPassword();
/*  124 */             authUrl = ba.getUrl();
/*  125 */             if ((stringUtils.isBlank(authUser)) || 
/*  126 */               (stringUtils.isBlank(authPwd))) {
/*  127 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/*  128 */                 .getBasUser();
/*  129 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/*  130 */                 .getBasPwd();
/*      */             }
/*  132 */             if (!stringUtils.isBlank(authUrl)) break;
/*  133 */             authUrl = ikweb;
/*      */ 
/*  135 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  139 */       if (stringUtils.isBlank(authUrl)) {
/*  140 */         authUrl = 
/*  141 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  141 */           .get("core"))[0];
/*      */       }
/*  143 */       session.setAttribute("ikweb", authUrl);
/*      */ 
/*  145 */       String[] rosInfo = new String[5];
/*  146 */       rosInfo[0] = "7";
/*  147 */       rosInfo[1] = "8";
/*  148 */       rosInfo[2] = username;
/*  149 */       rosInfo[3] = authUser;
/*  150 */       rosInfo[4] = authPwd;
/*  151 */       RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
/*      */ 
/*  153 */       session.setAttribute("authUser", authUser);
/*  154 */       session.setAttribute("authPwd", authPwd);
/*  155 */       session.setAttribute("username", username);
/*  156 */       session.setAttribute("ip", ip);
/*  157 */       session.setAttribute("basip", basip);
/*      */ 
/*  159 */       map.put("ret", "0");
/*  160 */       map.put("i", ip);
/*  161 */       map.put("u", username);
/*  162 */       map.put("msg", "你已经通过认证！！");
/*      */ 
/*  164 */       GuestAuthMap.getInstance().getGuestAuthMap()
/*  165 */         .remove(ip + ":" + basip);
/*  166 */       return map;
/*      */     }
/*  168 */     map.put("ret", "1");
/*  169 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WISPr_guest"})
/*      */   public Map<String, String> ajax_WISPr_guest(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  178 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  179 */     Map map = new HashMap();
/*      */ 
/*  181 */     HttpSession session = request.getSession();
/*      */ 
/*  183 */     String ikmac = (String)session.getAttribute("ikmac");
/*      */     try
/*      */     {
/*  186 */       String web = (String)session.getAttribute("web");
/*  187 */       if (stringUtils.isNotBlank(web)) {
/*  188 */         while (web.endsWith("/")) {
/*  189 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  191 */         Long webID = Long.valueOf(web);
/*  192 */         if (webID.longValue() != 0L) {
/*  193 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  194 */           if (portalweb != null) {
/*  195 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  196 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  199 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  200 */           if (config != null) {
/*  201 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  202 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  206 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  207 */         if (config != null) {
/*  208 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  209 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/*  217 */     Cookie[] cookies = request.getCookies();
/*  218 */     String cip = "";
/*  219 */     String cbasip = "";
/*  220 */     String cmac = "";
/*  221 */     if (cookies != null) {
/*  222 */       for (int i = 0; i < cookies.length; i++) {
/*  223 */         if (cookies[i].getName().equals("ip"))
/*  224 */           cip = cookies[i].getValue();
/*  225 */         else if (cookies[i].getName().equals("basip"))
/*  226 */           cbasip = cookies[i].getValue();
/*  227 */         else if (cookies[i].getName().equals("mac")) {
/*  228 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/*  232 */     if (stringUtils.isBlank(ikmac)) {
/*  233 */       ikmac = cmac;
/*      */     }
/*      */ 
/*  236 */     if (stringUtils.isBlank(ip)) {
/*  237 */       ip = (String)session.getAttribute("ip");
/*      */     }
/*  239 */     if (stringUtils.isBlank(ip)) {
/*  240 */       ip = cip;
/*      */     }
/*  242 */     if (stringUtils.isBlank(ip)) {
/*  243 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/*  246 */     if (stringUtils.isBlank(basip)) {
/*  247 */       basip = (String)session.getAttribute("basip");
/*      */     }
/*  249 */     if (stringUtils.isBlank(basip)) {
/*  250 */       basip = cbasip;
/*      */     }
/*  252 */     if (stringUtils.isBlank(basip)) {
/*  253 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/*  256 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/*  257 */     if ((basConfigFind != null) && 
/*  258 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/*  259 */       basConfig = basConfigFind;
/*      */     }
/*      */ 
/*  263 */     if ((!basConfig.getBas().equals("5")) && 
/*  264 */       (!basConfig.getBas().equals("6")) && 
/*  265 */       (!basConfig.getBas().equals("7")) && 
/*  266 */       (!basConfig
/*  266 */       .getBas().equals("8"))) {
/*  267 */       map.put("ret", "1");
/*  268 */       map.put("msg", "该设备类型错误！！");
/*  269 */       return map;
/*      */     }
/*  271 */     if (stringUtils.isBlank(ikmac)) {
/*  272 */       map.put("ret", "10");
/*  273 */       map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/*  274 */       return map;
/*      */     }
/*  276 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  277 */       ip + ":" + basip);
/*  278 */     if (isLogin) {
/*  279 */       map.put("ret", "2");
/*  280 */       map.put("i", ip);
/*  281 */       map.put("u", "访客认证");
/*  282 */       map.put("msg", "你已经通过认证,请不要重复刷新！！");
/*  283 */       return map;
/*      */     }
/*      */ 
/*  286 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
/*  287 */       String basePath = request.getScheme() + "://" + 
/*  288 */         request.getServerName() + ":" + request.getServerPort() + 
/*  289 */         request.getContextPath() + "/";
/*  290 */       String guestAuthPath = basePath + "ajax_WISPr_guestScan.action?ip=" + 
/*  291 */         ip + "&basip=" + basip + "&mac=" + ikmac;
/*      */ 
/*  293 */       String logoPath = request.getServletContext().getRealPath("/");
/*  294 */       String outPath = logoPath + "ExcelOut/";
/*  295 */       File dir = new File(outPath);
/*  296 */       if (!dir.exists()) {
/*  297 */         dir.mkdirs();
/*      */       }
/*  299 */       Date now = new Date();
/*  300 */       String nowString = ThreadLocalDateUtil.format(now);
/*  301 */       outPath = outPath + ip + nowString + ".jpg";
/*  302 */       logoPath = logoPath + "dist/css/img/logoS.png";
/*  303 */       int result = makeImg.make(guestAuthPath, logoPath, outPath);
/*  304 */       if (result == 1) {
/*  305 */         String imgUrl = basePath + "ExcelOut/" + ip + nowString + 
/*  306 */           ".jpg";
/*  307 */         map.put("ret", "0");
/*  308 */         map.put("url", imgUrl);
/*  309 */         map.put("ip", ip);
/*  310 */         map.put("basip", basip);
/*  311 */         map.put("mac", ikmac);
/*  312 */         map.put("msg", "请让员工扫描此二维码，授权上网！！");
/*  313 */         return map;
/*      */       }
/*  315 */       map.put("ret", "1");
/*  316 */       map.put("msg", "生成二维码失败！！");
/*  317 */       return map;
/*      */     }
/*      */ 
/*  320 */     map.put("ret", "3");
/*  321 */     map.put("i", ip);
/*  322 */     map.put("u", "访客认证");
/*  323 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/*  324 */     return map;
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/ajax_WISPr_guestScan"})
/*      */   public String ajax_WISPr_guestScan(String ip, String basip, String mac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  333 */     if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
/*  334 */       model.addAttribute("msg", "参数获取错误！！");
/*  335 */       model.addAttribute("ip", ip);
/*  336 */       model.addAttribute("basip", basip);
/*  337 */       model.addAttribute("mac", mac);
/*  338 */       return "guest/result";
/*      */     }
/*  340 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  341 */       ip + ":" + basip);
/*  342 */     if (isLogin) {
/*  343 */       model.addAttribute("msg", "该用户已经认证！！");
/*  344 */       model.addAttribute("ip", ip);
/*  345 */       model.addAttribute("basip", basip);
/*  346 */       model.addAttribute("mac", mac);
/*  347 */       return "guest/result";
/*      */     }
/*      */ 
/*  350 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
/*  351 */       model.addAttribute("ip", ip);
/*  352 */       model.addAttribute("basip", basip);
/*  353 */       model.addAttribute("mac", mac);
/*  354 */       model.addAttribute("msg", "请员工账号授权！！");
/*  355 */       return "guest/authAPI";
/*      */     }
/*  357 */     model.addAttribute("msg", "系统不是该验证模式，请联系管理员！！");
/*  358 */     model.addAttribute("ip", ip);
/*  359 */     model.addAttribute("basip", basip);
/*  360 */     model.addAttribute("mac", mac);
/*  361 */     return "guest/result";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/ajax_WISPr_guestAuth"})
/*      */   public String ajax_WISPr_guestAuth(String usr, String pwd, String ip, String basip, String mac, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  371 */     if ((stringUtils.isBlank(ip)) || (stringUtils.isBlank(basip))) {
/*  372 */       model.addAttribute("msg", "参数获取错误！！");
/*  373 */       model.addAttribute("ip", ip);
/*  374 */       model.addAttribute("basip", basip);
/*  375 */       model.addAttribute("mac", mac);
/*  376 */       return "guest/result";
/*      */     }
/*  378 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  379 */       ip + ":" + basip);
/*  380 */     if (isLogin) {
/*  381 */       model.addAttribute("msg", "该用户已经认证！！");
/*  382 */       model.addAttribute("ip", ip);
/*  383 */       model.addAttribute("basip", basip);
/*  384 */       model.addAttribute("mac", mac);
/*  385 */       return "guest/result";
/*      */     }
/*      */ 
/*  388 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("7")) {
/*  389 */       String accountIP = GetNgnixRemotIP.getRemoteAddrIp(request);
/*  390 */       String[] accountInfo = null;
/*      */ 
/*  392 */       accountInfo = (String[])onlineMap.getOnlineUserMap().get(
/*  393 */         accountIP + ":" + basip);
/*      */ 
/*  395 */       Iterator iterator = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/*  396 */       while (iterator.hasNext()) {
/*  397 */         Object o = iterator.next();
/*  398 */         String t = o.toString();
/*  399 */         if (t.contains(accountIP + ":")) {
/*  400 */           accountInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
/*  401 */           break;
/*      */         }
/*      */       }
/*      */ 
/*  405 */       if (accountInfo != null) {
/*  406 */         String username = accountInfo[0];
/*      */ 
/*  408 */         if (accountInfo.length < 6) {
/*  409 */           model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
/*  410 */           model.addAttribute("ip", ip);
/*  411 */           model.addAttribute("basip", basip);
/*  412 */           model.addAttribute("mac", mac);
/*  413 */           return "guest/result";
/*      */         }
/*      */         try {
/*  416 */           String state = accountInfo[5];
/*  417 */           if (!"ok".equals(state)) {
/*  418 */             model.addAttribute("msg", 
/*  419 */               "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
/*  420 */             model.addAttribute("ip", ip);
/*  421 */             model.addAttribute("basip", basip);
/*  422 */             model.addAttribute("mac", mac);
/*  423 */             return "guest/result";
/*      */           }
/*      */         } catch (Exception e) {
/*  426 */           model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
/*  427 */           model.addAttribute("ip", ip);
/*  428 */           model.addAttribute("basip", basip);
/*  429 */           model.addAttribute("mac", mac);
/*  430 */           return "guest/result";
/*      */         }
/*      */ 
/*  433 */         if (1 == CheckMacTimeLimitMethod(mac, Long.valueOf(8L))) {
/*  434 */           model.addAttribute("msg", "该设备当日时长已用完！！");
/*  435 */           model.addAttribute("ip", ip);
/*  436 */           model.addAttribute("basip", basip);
/*  437 */           model.addAttribute("mac", mac);
/*      */         }
/*      */ 
/*  440 */         username = username + "授权";
/*  441 */         GuestAuthMap.getInstance().getGuestAuthMap()
/*  442 */           .put(ip + ":" + basip, username);
/*      */ 
/*  444 */         model.addAttribute("ip", ip);
/*  445 */         model.addAttribute("basip", basip);
/*  446 */         model.addAttribute("mac", mac);
/*  447 */         model.addAttribute("msg", "认证成功，访客可以上网了！！");
/*  448 */         return "guest/result";
/*      */       }
/*      */ 
/*  451 */       model.addAttribute("msg", "你没有权限进行授权，请用接入账号进行认证再扫码授权！！");
/*  452 */       model.addAttribute("ip", ip);
/*  453 */       model.addAttribute("basip", basip);
/*  454 */       model.addAttribute("mac", mac);
/*  455 */       return "guest/result";
/*      */     }
/*      */ 
/*  458 */     model.addAttribute("msg", "系统不是该验证模式，请联系管理员！！");
/*  459 */     model.addAttribute("ip", ip);
/*  460 */     model.addAttribute("basip", basip);
/*  461 */     model.addAttribute("mac", mac);
/*  462 */     return "guest/result";
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WISPr_sms"})
/*      */   public Map<String, String> ajax_WISPr_sms(String ip, String phone, String yzm, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  473 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  474 */     Map map = new HashMap();
/*      */ 
/*  476 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/*  479 */       String web = (String)session.getAttribute("web");
/*  480 */       if (stringUtils.isNotBlank(web)) {
/*  481 */         while (web.endsWith("/")) {
/*  482 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  484 */         Long webID = Long.valueOf(web);
/*  485 */         if (webID.longValue() != 0L) {
/*  486 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  487 */           if (portalweb != null) {
/*  488 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  489 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  492 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  493 */           if (config != null) {
/*  494 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  495 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  499 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  500 */         if (config != null) {
/*  501 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  502 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/*  509 */     if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
/*  510 */       map.put("ret", "2");
/*  511 */       map.put("msg", "请输入手机号和验证码！！");
/*  512 */       return map;
/*      */     }
/*  514 */     if (phone.length() != 11) {
/*  515 */       map.put("ret", "2");
/*  516 */       map.put("msg", "手机号码不正确！");
/*  517 */       return map;
/*      */     }
/*      */     try {
/*  520 */       Long.parseLong(phone);
/*      */     } catch (Exception e) {
/*  522 */       map.put("ret", "2");
/*  523 */       map.put("msg", "手机号码不正确！");
/*  524 */       return map;
/*      */     }
/*  526 */     String code = "";
/*  527 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/*  528 */     query.setState("1");
/*  529 */     List smsList = this.portalsmsapiService
/*  530 */       .getPortalsmsapiList(query);
/*      */     Portalsmsapi smsapi;
/*  532 */     if (smsList.size() > 0)
/*  533 */       smsapi = (Portalsmsapi)smsList.get(0);
/*      */     else {
/*  535 */       smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*      */     }
/*  537 */     Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
/*  538 */     Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
/*      */     try {
/*  540 */       Date saveDate = (Date)objs[1];
/*  541 */       Long nowTime = Long.valueOf(new Date().getTime());
/*  542 */       if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
/*  543 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  544 */         map.put("ret", "2");
/*  545 */         map.put("msg", "验证码已过期，请重新获取验证码！");
/*  546 */         return map;
/*      */       }
/*  548 */       code = (String)objs[0];
/*      */     } catch (Exception e) {
/*  550 */       map.put("ret", "2");
/*  551 */       map.put("msg", "手机号或验证码不正确！");
/*  552 */       return map;
/*      */     }
/*      */ 
/*  555 */     if (!yzm.equals(code)) {
/*  556 */       map.put("ret", "2");
/*  557 */       map.put("msg", "验证码不正确！");
/*  558 */       return map;
/*      */     }
/*      */ 
/*  561 */     String ikmac = (String)session.getAttribute("ikmac");
/*  562 */     String ikweb = (String)session.getAttribute("ikweb");
/*  563 */     String authUrl = ikweb;
/*      */ 
/*  566 */     Cookie[] cookies = request.getCookies();
/*  567 */     String cip = "";
/*  568 */     String cbasip = "";
/*  569 */     String cmac = "";
/*  570 */     if (cookies != null) {
/*  571 */       for (int i = 0; i < cookies.length; i++) {
/*  572 */         if (cookies[i].getName().equals("ip"))
/*  573 */           cip = cookies[i].getValue();
/*  574 */         else if (cookies[i].getName().equals("basip"))
/*  575 */           cbasip = cookies[i].getValue();
/*  576 */         else if (cookies[i].getName().equals("mac")) {
/*  577 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/*  581 */     if (stringUtils.isBlank(ikmac)) {
/*  582 */       ikmac = cmac;
/*      */     }
/*      */ 
/*  585 */     if (stringUtils.isBlank(ip)) {
/*  586 */       ip = (String)session.getAttribute("ip");
/*      */     }
/*  588 */     if (stringUtils.isBlank(ip)) {
/*  589 */       ip = cip;
/*      */     }
/*  591 */     if (stringUtils.isBlank(ip)) {
/*  592 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/*  595 */     if (stringUtils.isBlank(basip)) {
/*  596 */       basip = (String)session.getAttribute("basip");
/*      */     }
/*  598 */     if (stringUtils.isBlank(basip)) {
/*  599 */       basip = cbasip;
/*      */     }
/*  601 */     if (stringUtils.isBlank(basip)) {
/*  602 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/*  605 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/*  606 */     if ((basConfigFind != null) && 
/*  607 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/*  608 */       basConfig = basConfigFind;
/*      */     }
/*      */ 
/*  612 */     if ((!basConfig.getBas().equals("5")) && 
/*  613 */       (!basConfig.getBas().equals("6")) && 
/*  614 */       (!basConfig.getBas().equals("7")) && 
/*  615 */       (!basConfig
/*  615 */       .getBas().equals("8"))) {
/*  616 */       map.put("ret", "1");
/*  617 */       map.put("msg", "该设备类型错误！！");
/*  618 */       return map;
/*      */     }
/*  620 */     if (stringUtils.isBlank(ikmac)) {
/*  621 */       map.put("ret", "10");
/*  622 */       map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/*  623 */       return map;
/*      */     }
/*      */ 
/*  626 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  627 */       ip + ":" + basip);
/*  628 */     if (isLogin) {
/*  629 */       map.put("ret", "0");
/*  630 */       map.put("i", ip);
/*  631 */       map.put("u", phone);
/*  632 */       map.put("msg", "你已经通过认证,请不要重复刷新！！");
/*  633 */       return map;
/*      */     }
/*      */ 
/*  636 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("4"))
/*      */     {
/*  638 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  639 */       bsq.setBasip(basip);
/*  640 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/*  641 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/*  642 */       List<Portalbasauth> basauths = this.portalbasauthService
/*  643 */         .getPortalbasauthList(bsq);
/*  644 */       if (basauths.size() > 0) {
/*  645 */         for (Portalbasauth ba : basauths) {
/*  646 */           if (ba.getType().intValue() == 4) {
/*  647 */             authUser = ba.getUsername();
/*  648 */             authPwd = ba.getPassword();
/*  649 */             authUrl = ba.getUrl();
/*  650 */             if ((stringUtils.isBlank(authUser)) || 
/*  651 */               (stringUtils.isBlank(authPwd))) {
/*  652 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/*  653 */                 .getBasUser();
/*  654 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/*  655 */                 .getBasPwd();
/*      */             }
/*  657 */             if (!stringUtils.isBlank(authUrl)) break;
/*  658 */             authUrl = ikweb;
/*      */ 
/*  660 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  664 */       if (stringUtils.isBlank(authUrl)) {
/*  665 */         authUrl = 
/*  666 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  666 */           .get("core"))[0];
/*      */       }
/*      */ 
/*  670 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(1L))) {
/*  671 */         map.put("ret", "2");
/*  672 */         map.put("i", ip);
/*  673 */         map.put("u", phone);
/*  674 */         map.put("msg", "该设备当日时长已用完！！");
/*  675 */         return map;
/*      */       }
/*      */ 
/*  678 */       Boolean info = Boolean.valueOf(true);
/*      */ 
/*  680 */       if (info.booleanValue()) {
/*  681 */         String[] rosInfo = new String[5];
/*  682 */         rosInfo[0] = "4";
/*  683 */         rosInfo[1] = "1";
/*  684 */         rosInfo[2] = phone;
/*  685 */         rosInfo[3] = authUser;
/*  686 */         rosInfo[4] = authPwd;
/*  687 */         RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
/*  688 */         session.setAttribute("authUser", authUser);
/*  689 */         session.setAttribute("authPwd", authPwd);
/*  690 */         session.setAttribute("username", phone);
/*      */ 
/*  692 */         session.setAttribute("ip", ip);
/*  693 */         session.setAttribute("basip", basip);
/*      */ 
/*  695 */         String more = smsapi.getMore();
/*  696 */         if ("0".equals(more)) {
/*  697 */           PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*      */         }
/*  699 */         session.setAttribute("ikweb", authUrl);
/*      */ 
/*  701 */         map.put("ret", "0");
/*  702 */         map.put("i", ip);
/*  703 */         map.put("u", phone);
/*  704 */         map.put("msg", "认证成功！");
/*  705 */         return map;
/*      */       }
/*  707 */       map.put("ret", "1");
/*  708 */       map.put("i", ip);
/*  709 */       map.put("u", phone);
/*  710 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/*  711 */       return map;
/*      */     }
/*      */ 
/*  715 */     map.put("ret", "3");
/*  716 */     map.put("i", ip);
/*  717 */     map.put("u", phone);
/*  718 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/*  719 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WISPr_gzh"})
/*      */   public Map<String, String> ajax_WISPr_gzh(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  729 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  730 */     Map map = new HashMap();
/*      */ 
/*  732 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/*  735 */       String web = (String)session.getAttribute("web");
/*  736 */       if (stringUtils.isNotBlank(web)) {
/*  737 */         while (web.endsWith("/")) {
/*  738 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  740 */         Long webID = Long.valueOf(web);
/*  741 */         if (webID.longValue() != 0L) {
/*  742 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  743 */           if (portalweb != null) {
/*  744 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  745 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  748 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  749 */           if (config != null) {
/*  750 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  751 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  755 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  756 */         if (config != null) {
/*  757 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  758 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*  765 */     String ikmac = (String)session.getAttribute("ikmac");
/*  766 */     String ssid = (String)session.getAttribute("ssid");
/*  767 */     String ikweb = (String)session.getAttribute("ikweb");
/*  768 */     String authUrlWeb = ikweb;
/*      */ 
/*  771 */     Cookie[] cookies = request.getCookies();
/*  772 */     String cip = "";
/*  773 */     String cbasip = "";
/*  774 */     String cmac = "";
/*  775 */     if (cookies != null) {
/*  776 */       for (int i = 0; i < cookies.length; i++) {
/*  777 */         if (cookies[i].getName().equals("ip"))
/*  778 */           cip = cookies[i].getValue();
/*  779 */         else if (cookies[i].getName().equals("basip"))
/*  780 */           cbasip = cookies[i].getValue();
/*  781 */         else if (cookies[i].getName().equals("mac")) {
/*  782 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/*  786 */     if (stringUtils.isBlank(ikmac)) {
/*  787 */       ikmac = cmac;
/*      */     }
/*      */ 
/*  790 */     if (stringUtils.isBlank(ip)) {
/*  791 */       ip = (String)session.getAttribute("ip");
/*      */     }
/*  793 */     if (stringUtils.isBlank(ip)) {
/*  794 */       ip = cip;
/*      */     }
/*  796 */     if (stringUtils.isBlank(ip)) {
/*  797 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/*  800 */     if (stringUtils.isBlank(basip)) {
/*  801 */       basip = (String)session.getAttribute("basip");
/*      */     }
/*  803 */     if (stringUtils.isBlank(basip)) {
/*  804 */       basip = cbasip;
/*      */     }
/*  806 */     if (stringUtils.isBlank(basip)) {
/*  807 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/*  810 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/*  811 */     if ((basConfigFind != null) && 
/*  812 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/*  813 */       basConfig = basConfigFind;
/*      */     }
/*      */ 
/*  816 */     if ((!basConfig.getBas().equals("5")) && 
/*  817 */       (!basConfig.getBas().equals("6")) && 
/*  818 */       (!basConfig.getBas().equals("7")) && 
/*  819 */       (!basConfig
/*  819 */       .getBas().equals("8"))) {
/*  820 */       map.put("ret", "1");
/*  821 */       map.put("msg", "该设备类型错误！！");
/*  822 */       return map;
/*      */     }
/*  824 */     if (stringUtils.isBlank(ikmac)) {
/*  825 */       map.put("ret", "10");
/*  826 */       map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/*  827 */       return map;
/*      */     }
/*      */ 
/*  830 */     if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
/*  831 */       logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
/*  832 */         ip + " mac=" + ikmac);
/*      */     }
/*      */ 
/*  835 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  836 */       ip + ":" + basip);
/*  837 */     if (isLogin) {
/*  838 */       map.put("ret", "2");
/*  839 */       map.put("msg", "你已经通过验证,或者下线后重试！！");
/*  840 */       return map;
/*      */     }
/*      */ 
/*  843 */     String userAgent = request.getHeader("user-agent");
/*      */ 
/*  845 */     boolean isComputer = false;
/*  846 */     if (stringUtils.isNotBlank(userAgent)) {
/*  847 */       if (userAgent.contains("Windows"))
/*  848 */         isComputer = true;
/*  849 */       else if (userAgent.contains("Macintosh")) {
/*  850 */         isComputer = true;
/*      */       }
/*  852 */       else if ((userAgent.contains("Android")) || 
/*  853 */         (userAgent.contains("iPhone")) || 
/*  854 */         (userAgent.contains("iPod")) || 
/*  855 */         (userAgent.contains("iPad"))) {
/*  856 */         isComputer = false;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  861 */     if (isComputer) {
/*  862 */       map.put("ret", "1");
/*  863 */       map.put("msg", "该认证模式仅支持手机使用！！");
/*  864 */       return map;
/*      */     }
/*      */ 
/*  867 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("6"))
/*      */     {
/*  869 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(7L))) {
/*  870 */         map.put("ret", "1");
/*  871 */         map.put("i", ip);
/*  872 */         map.put("u", "公众号认证-临时放行");
/*  873 */         map.put("msg", "该设备当日时长已用完！！");
/*  874 */         return map;
/*      */       }
/*      */ 
/*  877 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  878 */       bsq.setBasip(basip);
/*  879 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/*  880 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/*  881 */       List<Portalbasauth> basauths = this.portalbasauthService
/*  882 */         .getPortalbasauthList(bsq);
/*  883 */       if (basauths.size() > 0) {
/*  884 */         for (Portalbasauth ba : basauths) {
/*  885 */           if (ba.getType().intValue() == 6) {
/*  886 */             authUser = ba.getUsername();
/*  887 */             authPwd = ba.getPassword();
/*  888 */             authUrlWeb = ba.getUrl();
/*  889 */             if ((stringUtils.isBlank(authUser)) || 
/*  890 */               (stringUtils.isBlank(authPwd))) {
/*  891 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/*  892 */                 .getBasUser();
/*  893 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/*  894 */                 .getBasPwd();
/*      */             }
/*  896 */             if (!stringUtils.isBlank(authUrlWeb)) break;
/*  897 */             authUrlWeb = ikweb;
/*      */ 
/*  899 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  903 */       if (stringUtils.isBlank(authUrlWeb)) {
/*  904 */         authUrlWeb = 
/*  905 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  905 */           .get("core"))[0];
/*      */       }
/*      */ 
/*  908 */       Boolean info = Boolean.valueOf(true);
/*      */ 
/*  910 */       if (info.booleanValue())
/*      */       {
/*  912 */         String[] rosInfo = new String[5];
/*  913 */         rosInfo[0] = "6";
/*  914 */         rosInfo[1] = "7";
/*  915 */         rosInfo[2] = "公众号认证-临时放行";
/*  916 */         rosInfo[3] = authUser;
/*  917 */         rosInfo[4] = authPwd;
/*  918 */         RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
/*  919 */         session.setAttribute("authUser", authUser);
/*  920 */         session.setAttribute("authPwd", authPwd);
/*      */ 
/*  922 */         session.setAttribute("username", "公众号认证-临时放行");
/*      */ 
/*  924 */         session.setAttribute("ip", ip);
/*  925 */         session.setAttribute("basip", basip);
/*  926 */         session.setAttribute("ikweb", authUrlWeb);
/*      */ 
/*  936 */         Long time = Long.valueOf(new Date().getTime());
/*      */ 
/*  938 */         Portalweixinwifi wifi = null;
/*  939 */         if (stringUtils.isNotBlank(ssid)) {
/*  940 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/*  941 */           wxq.setSsid(ssid);
/*  942 */           wxq.setSsidLike(false);
/*  943 */           wxq.setBasip(basip);
/*  944 */           wxq.setBasipLike(false);
/*  945 */           List wxs = this.weixinwifiService
/*  946 */             .getPortalweixinwifiList(wxq);
/*  947 */           if (wxs.size() > 0) {
/*  948 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           } else {
/*  950 */             wxq = new PortalweixinwifiQuery();
/*  951 */             wxq.setSsid(ssid);
/*  952 */             wxq.setSsidLike(false);
/*  953 */             wxs = this.weixinwifiService.getPortalweixinwifiList(wxq);
/*  954 */             if (wxs.size() > 0) {
/*  955 */               wifi = (Portalweixinwifi)wxs.get(0);
/*      */             }
/*      */           }
/*      */         }
/*  959 */         if (wifi == null) {
/*  960 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/*  961 */           wxq.setBasip(basip);
/*  962 */           wxq.setBasipLike(false);
/*  963 */           List wxs = this.weixinwifiService
/*  964 */             .getPortalweixinwifiList(wxq);
/*  965 */           if (wxs.size() > 0) {
/*  966 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           }
/*      */         }
/*  969 */         if (wifi == null) {
/*  970 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/*  972 */         if (!Do()) {
/*  973 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/*      */ 
/*  976 */         if (wifi != null) {
/*  977 */           String appId = wifi.getAppId();
/*  978 */           String extend = ip + ":" + basip;
/*  979 */           String timestamp = String.valueOf(time);
/*  980 */           String shop_id = wifi.getShopId();
/*      */ 
/*  982 */           String authUrl = request.getScheme() + "://" + 
/*  983 */             request.getServerName() + ":" + 
/*  984 */             request.getServerPort() + request.getContextPath() + "/gzhAuth.action";
/*  985 */           ssid = wifi.getSsid();
/*  986 */           String secretKey = wifi.getSecretKey();
/*  987 */           String mac = "";
/*  988 */           String bssid = "";
/*      */ 
/*  990 */           String code = appId + extend + timestamp + shop_id + 
/*  991 */             authUrl + mac + ssid + bssid + secretKey;
/*  992 */           String sign = DigestUtils.md5Hex(code);
/*      */ 
/*  995 */           if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug()
/*  995 */             .equals("1")) {
/*  996 */             logger.info("finally ssid=" + ssid + " basip=" + basip + 
/*  997 */               " ip=" + ip + " code=" + code + " sign" + 
/*  998 */               sign);
/*      */           }
/*      */ 
/* 1001 */           map.put("ret", "0");
/* 1002 */           map.put("appId", appId);
/* 1003 */           map.put("extend", extend);
/* 1004 */           map.put("timestamp", timestamp);
/* 1005 */           map.put("sign", sign);
/* 1006 */           map.put("shop_id", shop_id);
/* 1007 */           map.put("authUrl", authUrl);
/* 1008 */           map.put("mac", mac);
/* 1009 */           map.put("ssid", ssid);
/* 1010 */           map.put("bssid", bssid);
/*      */ 
/* 1012 */           session.setAttribute("appId", appId);
/* 1013 */           session.setAttribute("extend", extend);
/* 1014 */           session.setAttribute("timestamp", timestamp);
/* 1015 */           session.setAttribute("sign", sign);
/* 1016 */           session.setAttribute("shop_id", shop_id);
/* 1017 */           session.setAttribute("authUrl", authUrl);
/* 1018 */           session.setAttribute("mac", mac);
/* 1019 */           session.setAttribute("ssid", ssid);
/* 1020 */           session.setAttribute("bssid", bssid);
/* 1021 */           return map;
/*      */         }
/* 1023 */         map.put("ret", "1");
/* 1024 */         map.put("msg", "微信认证参数未配置，请联系管理员！！");
/* 1025 */         return map;
/*      */       }
/*      */ 
/* 1029 */       map.put("ret", "1");
/* 1030 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/* 1031 */       return map;
/*      */     }
/*      */ 
/* 1034 */     map.put("ret", "3");
/* 1035 */     map.put("msg", "系统不是公众号认证模式！！！！");
/* 1036 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WISPr_weixin"})
/*      */   public Map<String, String> ajax_WISPr_weixin(String ip, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1045 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 1046 */     Map map = new HashMap();
/*      */ 
/* 1048 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/* 1051 */       String web = (String)session.getAttribute("web");
/* 1052 */       if (stringUtils.isNotBlank(web)) {
/* 1053 */         while (web.endsWith("/")) {
/* 1054 */           web = web.substring(0, web.length() - 1);
/*      */         }
/* 1056 */         Long webID = Long.valueOf(web);
/* 1057 */         if (webID.longValue() != 0L) {
/* 1058 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/* 1059 */           if (portalweb != null) {
/* 1060 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/* 1061 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/* 1064 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/* 1065 */           if (config != null) {
/* 1066 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1067 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/* 1071 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/* 1072 */         if (config != null) {
/* 1073 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1074 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/* 1081 */     String ikmac = (String)session.getAttribute("ikmac");
/* 1082 */     String ssid = (String)session.getAttribute("ssid");
/* 1083 */     String ikweb = (String)session.getAttribute("ikweb");
/* 1084 */     String authUrlWeb = ikweb;
/*      */ 
/* 1087 */     Cookie[] cookies = request.getCookies();
/* 1088 */     String cip = "";
/* 1089 */     String cbasip = "";
/* 1090 */     String cmac = "";
/* 1091 */     if (cookies != null) {
/* 1092 */       for (int i = 0; i < cookies.length; i++) {
/* 1093 */         if (cookies[i].getName().equals("ip"))
/* 1094 */           cip = cookies[i].getValue();
/* 1095 */         else if (cookies[i].getName().equals("basip"))
/* 1096 */           cbasip = cookies[i].getValue();
/* 1097 */         else if (cookies[i].getName().equals("mac")) {
/* 1098 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 1102 */     if (stringUtils.isBlank(ikmac)) {
/* 1103 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 1106 */     if (stringUtils.isBlank(ip)) {
/* 1107 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 1109 */     if (stringUtils.isBlank(ip)) {
/* 1110 */       ip = cip;
/*      */     }
/* 1112 */     if (stringUtils.isBlank(ip)) {
/* 1113 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 1116 */     if (stringUtils.isBlank(basip)) {
/* 1117 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 1119 */     if (stringUtils.isBlank(basip)) {
/* 1120 */       basip = cbasip;
/*      */     }
/* 1122 */     if (stringUtils.isBlank(basip)) {
/* 1123 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 1126 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 1127 */     if ((basConfigFind != null) && 
/* 1128 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/* 1129 */       basConfig = basConfigFind;
/*      */     }
/*      */ 
/* 1132 */     if ((!basConfig.getBas().equals("5")) && 
/* 1133 */       (!basConfig.getBas().equals("6")) && 
/* 1134 */       (!basConfig.getBas().equals("7")) && 
/* 1135 */       (!basConfig
/* 1135 */       .getBas().equals("8"))) {
/* 1136 */       map.put("ret", "1");
/* 1137 */       map.put("msg", "该设备类型错误！！");
/* 1138 */       return map;
/*      */     }
/* 1140 */     if (stringUtils.isBlank(ikmac)) {
/* 1141 */       map.put("ret", "10");
/* 1142 */       map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 1143 */       return map;
/*      */     }
/*      */ 
/* 1146 */     if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
/* 1147 */       logger.info("session ssid=" + ssid + " basip=" + basip + " ip=" + 
/* 1148 */         ip + " mac=" + ikmac);
/*      */     }
/*      */ 
/* 1151 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 1152 */       ip + ":" + basip);
/* 1153 */     if (isLogin) {
/* 1154 */       map.put("ret", "2");
/* 1155 */       map.put("msg", "你已经通过验证,或者下线后重试！！");
/* 1156 */       return map;
/*      */     }
/*      */ 
/* 1159 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("5"))
/*      */     {
/* 1161 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(6L))) {
/* 1162 */         map.put("ret", "1");
/* 1163 */         map.put("i", ip);
/* 1164 */         map.put("u", "微信验证");
/* 1165 */         map.put("msg", "该设备当日时长已用完！！");
/* 1166 */         return map;
/*      */       }
/*      */ 
/* 1169 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1170 */       bsq.setBasip(basip);
/* 1171 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 1172 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 1173 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 1174 */         .getPortalbasauthList(bsq);
/* 1175 */       if (basauths.size() > 0) {
/* 1176 */         for (Portalbasauth ba : basauths) {
/* 1177 */           if (ba.getType().intValue() == 5) {
/* 1178 */             authUser = ba.getUsername();
/* 1179 */             authPwd = ba.getPassword();
/* 1180 */             authUrlWeb = ba.getUrl();
/* 1181 */             if ((stringUtils.isBlank(authUser)) || 
/* 1182 */               (stringUtils.isBlank(authPwd))) {
/* 1183 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 1184 */                 .getBasUser();
/* 1185 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 1186 */                 .getBasPwd();
/*      */             }
/* 1188 */             if (!stringUtils.isBlank(authUrlWeb)) break;
/* 1189 */             authUrlWeb = ikweb;
/*      */ 
/* 1191 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1195 */       if (stringUtils.isBlank(authUrlWeb)) {
/* 1196 */         authUrlWeb = 
/* 1197 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1197 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 1200 */       Boolean info = Boolean.valueOf(true);
/*      */ 
/* 1202 */       if (info.booleanValue())
/*      */       {
/* 1204 */         String[] rosInfo = new String[5];
/* 1205 */         rosInfo[0] = "5";
/* 1206 */         rosInfo[1] = "6";
/* 1207 */         rosInfo[2] = "微信验证-临时放行";
/* 1208 */         rosInfo[3] = authUser;
/* 1209 */         rosInfo[4] = authPwd;
/* 1210 */         RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
/* 1211 */         session.setAttribute("authUser", authUser);
/* 1212 */         session.setAttribute("authPwd", authPwd);
/*      */ 
/* 1214 */         session.setAttribute("username", "微信验证-临时放行");
/*      */ 
/* 1216 */         session.setAttribute("ip", ip);
/* 1217 */         session.setAttribute("basip", basip);
/* 1218 */         session.setAttribute("ikweb", authUrlWeb);
/*      */ 
/* 1228 */         Long time = Long.valueOf(new Date().getTime());
/*      */ 
/* 1230 */         Portalweixinwifi wifi = null;
/* 1231 */         if (stringUtils.isNotBlank(ssid)) {
/* 1232 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/* 1233 */           wxq.setSsid(ssid);
/* 1234 */           wxq.setSsidLike(false);
/* 1235 */           wxq.setBasip(basip);
/* 1236 */           wxq.setBasipLike(false);
/* 1237 */           List wxs = this.weixinwifiService
/* 1238 */             .getPortalweixinwifiList(wxq);
/* 1239 */           if (wxs.size() > 0) {
/* 1240 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           } else {
/* 1242 */             wxq = new PortalweixinwifiQuery();
/* 1243 */             wxq.setSsid(ssid);
/* 1244 */             wxq.setSsidLike(false);
/* 1245 */             wxs = this.weixinwifiService.getPortalweixinwifiList(wxq);
/* 1246 */             if (wxs.size() > 0) {
/* 1247 */               wifi = (Portalweixinwifi)wxs.get(0);
/*      */             }
/*      */           }
/*      */         }
/* 1251 */         if (wifi == null) {
/* 1252 */           PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/* 1253 */           wxq.setBasip(basip);
/* 1254 */           wxq.setBasipLike(false);
/* 1255 */           List wxs = this.weixinwifiService
/* 1256 */             .getPortalweixinwifiList(wxq);
/* 1257 */           if (wxs.size() > 0) {
/* 1258 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           }
/*      */         }
/* 1261 */         if (wifi == null) {
/* 1262 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/* 1264 */         if (!Do()) {
/* 1265 */           wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */         }
/*      */ 
/* 1268 */         if (wifi != null) {
/* 1269 */           String appId = wifi.getAppId();
/* 1270 */           String extend = ip + ":" + basip;
/* 1271 */           String timestamp = String.valueOf(time);
/* 1272 */           String shop_id = wifi.getShopId();
/*      */ 
/* 1274 */           String authUrl = request.getScheme() + "://" + 
/* 1275 */             request.getServerName() + ":" + 
/* 1276 */             request.getServerPort() + request.getContextPath() + "/weixinAuth.action";
/*      */ 
/* 1278 */           String userAgent = request.getHeader("user-agent");
/*      */ 
/* 1280 */           boolean isComputer = false;
/* 1281 */           if (stringUtils.isNotBlank(userAgent)) {
/* 1282 */             if (userAgent.contains("Windows"))
/* 1283 */               isComputer = true;
/* 1284 */             else if (userAgent.contains("Macintosh")) {
/* 1285 */               isComputer = true;
/*      */             }
/* 1287 */             else if ((userAgent.contains("Android")) || 
/* 1288 */               (userAgent.contains("iPhone")) || 
/* 1289 */               (userAgent.contains("iPod")) || 
/* 1290 */               (userAgent.contains("iPad"))) {
/* 1291 */               isComputer = false;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1296 */           if (isComputer)
/*      */           {
/* 1298 */             authUrl = request.getScheme() + "://" + 
/* 1299 */               request.getServerName() + ":" + 
/* 1300 */               request.getServerPort() + 
/* 1301 */               request.getContextPath() + 
/* 1302 */               "/weixinPCAuth.action";
/*      */           }
/*      */ 
/* 1305 */           ssid = wifi.getSsid();
/* 1306 */           String secretKey = wifi.getSecretKey();
/* 1307 */           String mac = "";
/* 1308 */           String bssid = "";
/*      */ 
/* 1310 */           String code = appId + extend + timestamp + shop_id + 
/* 1311 */             authUrl + mac + ssid + bssid + secretKey;
/* 1312 */           String sign = DigestUtils.md5Hex(code);
/*      */ 
/* 1315 */           if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug()
/* 1315 */             .equals("1")) {
/* 1316 */             logger.info("finally ssid=" + ssid + " basip=" + basip + 
/* 1317 */               " ip=" + ip + " code=" + code + " sign" + 
/* 1318 */               sign);
/*      */           }
/*      */ 
/* 1321 */           map.put("ret", "0");
/* 1322 */           map.put("appId", appId);
/* 1323 */           map.put("extend", extend);
/* 1324 */           map.put("timestamp", timestamp);
/* 1325 */           map.put("sign", sign);
/* 1326 */           map.put("shop_id", shop_id);
/* 1327 */           map.put("authUrl", authUrl);
/* 1328 */           map.put("mac", mac);
/* 1329 */           map.put("ssid", ssid);
/* 1330 */           map.put("bssid", bssid);
/*      */ 
/* 1332 */           session.setAttribute("appId", appId);
/* 1333 */           session.setAttribute("extend", extend);
/* 1334 */           session.setAttribute("timestamp", timestamp);
/* 1335 */           session.setAttribute("sign", sign);
/* 1336 */           session.setAttribute("shop_id", shop_id);
/* 1337 */           session.setAttribute("authUrl", authUrl);
/* 1338 */           session.setAttribute("mac", mac);
/* 1339 */           session.setAttribute("ssid", ssid);
/* 1340 */           session.setAttribute("bssid", bssid);
/* 1341 */           return map;
/*      */         }
/* 1343 */         map.put("ret", "1");
/* 1344 */         map.put("msg", "微信认证参数未配置，请联系管理员！！");
/* 1345 */         return map;
/*      */       }
/*      */ 
/* 1349 */       map.put("ret", "1");
/* 1350 */       map.put("msg", "网络暂时不可用，请联系管理员！！");
/* 1351 */       return map;
/*      */     }
/*      */ 
/* 1354 */     map.put("ret", "3");
/* 1355 */     map.put("msg", "系统不是微信验证模式！！！！");
/* 1356 */     return map;
/*      */   }
/*      */ 
/*      */   private Map<String, Object> checkLocalAccount(String username, String password)
/*      */   {
/* 1366 */     PortalaccountQuery accountqQuery = new PortalaccountQuery();
/* 1367 */     accountqQuery.setLoginNameLike(false);
/* 1368 */     accountqQuery.setPasswordLike(false);
/* 1369 */     accountqQuery.setLoginName(username);
/* 1370 */     accountqQuery.setPassword(password);
/* 1371 */     List accounts = this.accountService
/* 1372 */       .getPortalaccountList(accountqQuery);
/*      */ 
/* 1374 */     Map map = new HashMap();
/* 1375 */     if (accounts.size() != 0) {
/* 1376 */       Portalaccount account = (Portalaccount)accounts.get(0);
/* 1377 */       if (password.equals(account.getPassword())) {
/* 1378 */         map.put("id", account.getId());
/* 1379 */         map.put("state", account.getState());
/* 1380 */         map.put("date", account.getDate());
/* 1381 */         map.put("time", account.getTime());
/* 1382 */         if (account.getOctets() == null)
/* 1383 */           map.put("octets", Long.valueOf(0L));
/*      */         else {
/* 1385 */           map.put("octets", account.getOctets());
/*      */         }
/* 1387 */         map.put("result", Integer.valueOf(1));
/* 1388 */         return map;
/*      */       }
/*      */     }
/* 1391 */     map.put("result", Integer.valueOf(0));
/* 1392 */     return map;
/*      */   }
/*      */ 
/*      */   private boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets)
/*      */   {
/* 1405 */     Portalaccount account = this.accountService.getPortalaccountByKey(userId);
/*      */ 
/* 1407 */     if (userState.equals("0")) {
/* 1408 */       return false;
/*      */     }
/*      */ 
/* 1411 */     if (userState.equals("1")) {
/* 1412 */       return true;
/*      */     }
/*      */ 
/* 1415 */     if (userState.equals("3")) {
/* 1416 */       Date now = new Date();
/* 1417 */       if (userDate.getTime() > now.getTime()) {
/* 1418 */         return true;
/*      */       }
/* 1420 */       account.setState("2");
/* 1421 */       this.accountService.updatePortalaccountByKey(account);
/* 1422 */       userState = "2";
/*      */     }
/*      */ 
/* 1426 */     if (userState.equals("2")) {
/* 1427 */       if (userTime.longValue() > 0L) {
/* 1428 */         return true;
/*      */       }
/* 1430 */       account.setState("4");
/* 1431 */       this.accountService.updatePortalaccountByKey(account);
/* 1432 */       userState = "4";
/*      */     }
/*      */ 
/* 1436 */     if (userState.equals("4")) {
/* 1437 */       if (octets.longValue() > 0L) {
/* 1438 */         return true;
/*      */       }
/* 1440 */       account.setState("0");
/* 1441 */       this.accountService.updatePortalaccountByKey(account);
/* 1442 */       return false;
/*      */     }
/*      */ 
/* 1445 */     return false;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WISPr_LoginOut"})
/*      */   public Map<String, String> ajax_WISPr_LoginOut(String ip, String basip, HttpServletRequest request, HttpServletResponse response) {
/* 1452 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 1453 */     Map map = new HashMap();
/*      */ 
/* 1455 */     HttpSession session = request.getSession();
/* 1456 */     String ikmac = (String)session.getAttribute("ikmac");
/*      */ 
/* 1458 */     Cookie[] cookies = request.getCookies();
/* 1459 */     String cip = "";
/* 1460 */     String cbasip = "";
/* 1461 */     String cmac = "";
/* 1462 */     if (cookies != null) {
/* 1463 */       for (int i = 0; i < cookies.length; i++) {
/* 1464 */         if (cookies[i].getName().equals("ip"))
/* 1465 */           cip = cookies[i].getValue();
/* 1466 */         else if (cookies[i].getName().equals("basip"))
/* 1467 */           cbasip = cookies[i].getValue();
/* 1468 */         else if (cookies[i].getName().equals("mac")) {
/* 1469 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 1473 */     if (stringUtils.isBlank(ikmac)) {
/* 1474 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 1477 */     if (stringUtils.isBlank(ip)) {
/* 1478 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 1480 */     if (stringUtils.isBlank(ip)) {
/* 1481 */       ip = cip;
/*      */     }
/* 1483 */     if (stringUtils.isBlank(ip)) {
/* 1484 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 1487 */     if (stringUtils.isBlank(basip)) {
/* 1488 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 1490 */     if (stringUtils.isBlank(basip)) {
/* 1491 */       basip = cbasip;
/*      */     }
/* 1493 */     if (stringUtils.isBlank(basip)) {
/* 1494 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 1497 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 1498 */     if ((basConfigFind != null) && 
/* 1499 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/* 1500 */       basConfig = basConfigFind;
/*      */     }
/*      */ 
/* 1503 */     if ((!basConfig.getBas().equals("5")) && 
/* 1504 */       (!basConfig.getBas().equals("6")) && 
/* 1505 */       (!basConfig.getBas().equals("7")) && 
/* 1506 */       (!basConfig
/* 1506 */       .getBas().equals("8"))) {
/* 1507 */       map.put("ret", "1");
/* 1508 */       map.put("msg", "该设备类型错误！！");
/* 1509 */       return map;
/*      */     }
/* 1511 */     if (stringUtils.isBlank(ikmac)) {
/* 1512 */       map.put("ret", "10");
/* 1513 */       map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 1514 */       return map;
/*      */     }
/*      */ 
/* 1517 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 1518 */       ip + ":" + basip);
/* 1519 */     if (!isLogin) {
/* 1520 */       map.put("ret", "0");
/* 1521 */       map.put("msg", "你已经离线！");
/* 1522 */       session.removeAttribute("username");
/* 1523 */       session.removeAttribute("password");
/*      */ 
/* 1527 */       Cookie cookieIP = new Cookie("ip", null);
/* 1528 */       cookieIP.setMaxAge(-1);
/* 1529 */       response.addCookie(cookieIP);
/* 1530 */       Cookie cookieBasIp = new Cookie("basip", null);
/* 1531 */       cookieBasIp.setMaxAge(-1);
/* 1532 */       response.addCookie(cookieBasIp);
/* 1533 */       Cookie cookiePwd = new Cookie("password", null);
/* 1534 */       cookiePwd.setMaxAge(-1);
/* 1535 */       response.addCookie(cookiePwd);
/*      */ 
/* 1537 */       return map;
/*      */     }
/*      */ 
/* 1540 */     Kick.offLine(ip + ":" + basip, ikmac, "");
/*      */ 
/* 1543 */     map.put("ret", "0");
/* 1544 */     map.put("msg", "下线成功！");
/* 1545 */     session.removeAttribute("username");
/* 1546 */     session.removeAttribute("password");
/*      */ 
/* 1550 */     Cookie cookieIP = new Cookie("ip", null);
/* 1551 */     cookieIP.setMaxAge(-1);
/* 1552 */     response.addCookie(cookieIP);
/* 1553 */     Cookie cookieBasIp = new Cookie("basip", null);
/* 1554 */     cookieBasIp.setMaxAge(-1);
/* 1555 */     response.addCookie(cookieBasIp);
/* 1556 */     Cookie cookiePwd = new Cookie("password", null);
/* 1557 */     cookiePwd.setMaxAge(-1);
/* 1558 */     response.addCookie(cookiePwd);
/*      */ 
/* 1566 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_WISPr_Login"})
/*      */   public Map<String, String> ajax_WISPr_Login(String usr, String pwd, String ip, String basip, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1574 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 1575 */     Map map = new HashMap();
/*      */ 
/* 1577 */     HttpSession session = request.getSession();
/*      */     try
/*      */     {
/* 1580 */       String web = (String)session.getAttribute("web");
/* 1581 */       if (stringUtils.isNotBlank(web)) {
/* 1582 */         while (web.endsWith("/")) {
/* 1583 */           web = web.substring(0, web.length() - 1);
/*      */         }
/* 1585 */         Long webID = Long.valueOf(web);
/* 1586 */         if (webID.longValue() != 0L) {
/* 1587 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/* 1588 */           if (portalweb != null) {
/* 1589 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/* 1590 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/* 1593 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/* 1594 */           if (config != null) {
/* 1595 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1596 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/* 1600 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/* 1601 */         if (config != null) {
/* 1602 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/* 1603 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/* 1610 */     String ikmac = (String)session.getAttribute("ikmac");
/* 1611 */     String ikweb = (String)session.getAttribute("ikweb");
/* 1612 */     String authUrl = ikweb;
/*      */ 
/* 1615 */     Cookie[] cookies = request.getCookies();
/* 1616 */     String cip = "";
/* 1617 */     String cbasip = "";
/* 1618 */     String cmac = "";
/* 1619 */     if (cookies != null) {
/* 1620 */       for (int i = 0; i < cookies.length; i++) {
/* 1621 */         if (cookies[i].getName().equals("ip"))
/* 1622 */           cip = cookies[i].getValue();
/* 1623 */         else if (cookies[i].getName().equals("basip"))
/* 1624 */           cbasip = cookies[i].getValue();
/* 1625 */         else if (cookies[i].getName().equals("mac")) {
/* 1626 */           cmac = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/* 1630 */     if (stringUtils.isBlank(ikmac)) {
/* 1631 */       ikmac = cmac;
/*      */     }
/*      */ 
/* 1634 */     if (stringUtils.isBlank(ip)) {
/* 1635 */       ip = (String)session.getAttribute("ip");
/*      */     }
/* 1637 */     if (stringUtils.isBlank(ip)) {
/* 1638 */       ip = cip;
/*      */     }
/* 1640 */     if (stringUtils.isBlank(ip)) {
/* 1641 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 1644 */     if (stringUtils.isBlank(basip)) {
/* 1645 */       basip = (String)session.getAttribute("basip");
/*      */     }
/* 1647 */     if (stringUtils.isBlank(basip)) {
/* 1648 */       basip = cbasip;
/*      */     }
/* 1650 */     if (stringUtils.isBlank(basip)) {
/* 1651 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 1654 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 1655 */     if ((basConfigFind != null) && 
/* 1656 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/* 1657 */       basConfig = basConfigFind;
/*      */     }
/*      */ 
/* 1660 */     if ((!basConfig.getBas().equals("5")) && 
/* 1661 */       (!basConfig.getBas().equals("6")) && 
/* 1662 */       (!basConfig.getBas().equals("7")) && 
/* 1663 */       (!basConfig
/* 1663 */       .getBas().equals("8"))) {
/* 1664 */       map.put("ret", "1");
/* 1665 */       map.put("msg", "该设备类型不匹配！！");
/* 1666 */       return map;
/*      */     }
/* 1668 */     if (stringUtils.isBlank(ikmac)) {
/* 1669 */       map.put("ret", "10");
/* 1670 */       map.put("msg", "获取MAC地址失败，请关闭浏览器重试！！");
/* 1671 */       return map;
/*      */     }
/*      */ 
/* 1674 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 1675 */       ip + ":" + basip);
/* 1676 */     if (isLogin) {
/* 1677 */       String[] userinfo = (String[])onlineMap.getOnlineUserMap().get(
/* 1678 */         ip + ":" + basip);
/* 1679 */       usr = userinfo[0];
/* 1680 */       map.put("ret", "0");
/* 1681 */       map.put("i", ip);
/* 1682 */       map.put("u", usr);
/* 1683 */       map.put("msg", "你已经通过认证，请不要重复刷新 ！");
/* 1684 */       return map;
/*      */     }
/*      */ 
/* 1688 */     if ((((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("0")) && 
/* 1689 */       (stringUtils.isBlank(usr)) && (stringUtils.isBlank(pwd)))
/*      */     {
/* 1691 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(2L))) {
/* 1692 */         map.put("ret", "1");
/* 1693 */         map.put("i", ip);
/* 1694 */         map.put("u", "一键认证");
/* 1695 */         map.put("msg", "该设备当日时长已用完！！");
/* 1696 */         return map;
/*      */       }
/*      */ 
/* 1699 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1700 */       bsq.setBasip(basip);
/* 1701 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 1702 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 1703 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 1704 */         .getPortalbasauthList(bsq);
/* 1705 */       if (basauths.size() > 0) {
/* 1706 */         for (Portalbasauth ba : basauths) {
/* 1707 */           if (ba.getType().intValue() == 0) {
/* 1708 */             authUser = ba.getUsername();
/* 1709 */             authPwd = ba.getPassword();
/* 1710 */             authUrl = ba.getUrl();
/* 1711 */             if ((stringUtils.isBlank(authUser)) || 
/* 1712 */               (stringUtils.isBlank(authPwd))) {
/* 1713 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 1714 */                 .getBasUser();
/* 1715 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 1716 */                 .getBasPwd();
/*      */             }
/* 1718 */             if (!stringUtils.isBlank(authUrl)) break;
/* 1719 */             authUrl = ikweb;
/*      */ 
/* 1721 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1725 */       if (stringUtils.isBlank(authUrl)) {
/* 1726 */         authUrl = 
/* 1727 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1727 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 1730 */       Boolean info = Boolean.valueOf(true);
/*      */ 
/* 1732 */       if (info.booleanValue())
/*      */       {
/* 1735 */         String[] rosInfo = new String[5];
/* 1736 */         rosInfo[0] = "0";
/* 1737 */         rosInfo[1] = "2";
/* 1738 */         rosInfo[2] = "一键认证";
/* 1739 */         rosInfo[3] = authUser;
/* 1740 */         rosInfo[4] = authPwd;
/* 1741 */         RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
/*      */ 
/* 1743 */         session.setAttribute("username", "一键认证");
/* 1744 */         session.setAttribute("authUser", authUser);
/* 1745 */         session.setAttribute("authPwd", authPwd);
/* 1746 */         session.setAttribute("ip", ip);
/* 1747 */         session.setAttribute("basip", basip);
/*      */ 
/* 1757 */         map.put("ret", "0");
/* 1758 */         map.put("i", ip);
/* 1759 */         map.put("u", "一键认证");
/* 1760 */         map.put("msg", "认证成功,你可以上网了！！");
/* 1761 */         return map;
/*      */       }
/* 1763 */       map.put("ret", "1");
/* 1764 */       map.put("i", ip);
/* 1765 */       map.put("u", "一键认证");
/* 1766 */       map.put("msg", "认证失败，请联系管理员！！");
/* 1767 */       return map;
/*      */     }
/*      */ 
/* 1772 */     if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
/* 1773 */       map.put("ret", "1");
/* 1774 */       map.put("i", ip);
/* 1775 */       map.put("u", "");
/* 1776 */       map.put("msg", "用户名和密码不能为空！！");
/* 1777 */       return map;
/*      */     }
/*      */ 
/* 1780 */     String username = usr;
/* 1781 */     String password = pwd;
/* 1782 */     if (((Portalbas)config.getConfigMap().get(basip)).getIsdebug().equals("1")) {
/* 1783 */       logger.info("Request Auth User: " + username + " IP: " + ip);
/*      */     }
/*      */ 
/* 1789 */     Boolean info = Boolean.valueOf(false);
/*      */ 
/* 1791 */     String userState = "";
/* 1792 */     Long userId = Long.valueOf(0L);
/* 1793 */     Date userDate = new Date();
/* 1794 */     Long userTime = Long.valueOf(0L);
/* 1795 */     Long octets = Long.valueOf(0L);
/*      */     PortalbasauthQuery bsq;
/*      */     String[] rosInfo;
/* 1798 */     if (((Portalbas)config.getConfigMap().get(basip)).getAuthInterface().contains("1"))
/*      */     {
/* 1800 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) {
/* 1801 */         map.put("ret", "1");
/* 1802 */         map.put("i", ip);
/* 1803 */         map.put("u", username);
/* 1804 */         map.put("msg", "该设备当日时长已用完！！");
/* 1805 */         return map;
/*      */       }
/*      */ 
/* 1808 */       Map resultCheck = checkLocalAccount(username, 
/* 1809 */         password);
/* 1810 */       int state = ((Integer)resultCheck.get("result")).intValue();
/*      */ 
/* 1812 */       if (state == 0) {
/* 1813 */         map.put("ret", "1");
/* 1814 */         map.put("i", ip);
/* 1815 */         map.put("u", username);
/* 1816 */         map.put("msg", "用户名密码错误,或账户已过期！！");
/* 1817 */         return map;
/*      */       }
/*      */ 
/* 1820 */       userState = (String)resultCheck.get("state");
/* 1821 */       userId = (Long)resultCheck.get("id");
/* 1822 */       userDate = (Date)resultCheck.get("date");
/* 1823 */       userTime = (Long)resultCheck.get("time");
/* 1824 */       octets = (Long)resultCheck.get("octets");
/*      */ 
/* 1826 */       if (!checkTimeOut(userState, userId, userDate, userTime, octets)) {
/* 1827 */         map.put("ret", "1");
/* 1828 */         map.put("i", ip);
/* 1829 */         map.put("u", username);
/* 1830 */         map.put("msg", "账户已过期，请及时联系管理员充值！！");
/* 1831 */         return map;
/*      */       }
/*      */ 
/* 1834 */       if (userState.equals("3")) {
/* 1835 */         Date now = new Date();
/* 1836 */         if (userDate.getTime() - now.getTime() <= 3600000L) {
/* 1837 */           map.put("msg", "账户余额告警，请计时充值！！");
/*      */         }
/*      */       }
/* 1840 */       if ((userState.equals("2")) && 
/* 1841 */         (userTime.longValue() <= 3600000L)) {
/* 1842 */         map.put("msg", "账户余额告警，请计时充值！！");
/*      */       }
/*      */ 
/* 1845 */       if ((userState.equals("4")) && 
/* 1846 */         (octets.longValue() <= 104857600L)) {
/* 1847 */         map.put("msg", "账户余额告警，请计时充值！！");
/*      */       }
/*      */ 
/* 1851 */       Portalaccount acc = this.accountService.getPortalaccountByKey(userId);
/*      */       String[] loginInfo;
/* 1852 */       if (("1".equals(((Portalbas)config.getConfigMap().get(basip)).getIsPortalCheck())) && 
/* 1853 */         (acc != null)) {
/* 1854 */         Integer limitCount = acc.getMaclimitcount();
/* 1855 */         if ((limitCount != null) && (limitCount.intValue() > 0)) {
/* 1856 */           int count = 0;
/* 1857 */           Iterator iterator = OnlineMap.getInstance()
/* 1858 */             .getOnlineUserMap().keySet()
/* 1859 */             .iterator();
/* 1860 */           while (iterator.hasNext()) {
/* 1861 */             Object o = iterator.next();
/* 1862 */             String t = o.toString();
/* 1863 */             loginInfo = 
/* 1864 */               (String[])OnlineMap.getInstance()
/* 1864 */               .getOnlineUserMap().get(t);
/* 1865 */             String haveUsername = loginInfo[0];
/* 1866 */             if (username.equals(haveUsername)) {
/* 1867 */               count++;
/*      */             }
/*      */           }
/* 1870 */           if (count >= limitCount.intValue()) {
/* 1871 */             map.put("ret", "1");
/* 1872 */             map.put("i", ip);
/* 1873 */             map.put("u", username);
/* 1874 */             map.put("msg", "该账户同时在线数已超出限制，请等待使用该账户的其他设备下线！！");
/* 1875 */             return map;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1881 */       bsq = new PortalbasauthQuery();
/* 1882 */       bsq.setBasip(basip);
/* 1883 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 1884 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 1885 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 1886 */         .getPortalbasauthList(bsq);
/* 1887 */       if (basauths.size() > 0) {
/* 1888 */         for (Portalbasauth ba : basauths) {
/* 1889 */           if (ba.getType().intValue() == 1) {
/* 1890 */             authUser = ba.getUsername();
/* 1891 */             authPwd = ba.getPassword();
/* 1892 */             authUrl = ba.getUrl();
/* 1893 */             if ((stringUtils.isBlank(authUser)) || 
/* 1894 */               (stringUtils.isBlank(authPwd))) {
/* 1895 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 1896 */                 .getBasUser();
/* 1897 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 1898 */                 .getBasPwd();
/*      */             }
/* 1900 */             if (!stringUtils.isBlank(authUrl)) break;
/* 1901 */             authUrl = ikweb;
/*      */ 
/* 1903 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1907 */       if (stringUtils.isBlank(authUrl)) {
/* 1908 */         authUrl = 
/* 1909 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1909 */           .get("core"))[0];
/*      */       }
/* 1911 */       if (!"1".equals(((Portalbas)config.getConfigMap().get(basip)).getIsPortalCheck())) {
/* 1912 */         authUser = username;
/* 1913 */         authPwd = password;
/*      */       }
/*      */ 
/* 1916 */       info = Boolean.valueOf(true);
/*      */ 
/* 1919 */       if (info.booleanValue())
/*      */       {
/* 1927 */         int limitMac = acc.getMaclimit().intValue();
/* 1928 */         int limitCount = acc.getMaclimitcount().intValue();
/* 1929 */         int auto = acc.getAutologin().intValue();
/*      */ 
/* 1931 */         String userMac = ikmac;
/*      */ 
/* 1933 */         if (limitMac == 1) {
/* 1934 */           PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/* 1935 */           mq.setAccountId(userId);
/* 1936 */           List<Portalaccountmacs> accountmacs = this.macsService
/* 1937 */             .getPortalaccountmacsList(mq);
/* 1938 */           if ((accountmacs.size() < 1) || (limitCount == 0) || 
/* 1939 */             (accountmacs.size() < limitCount))
/*      */           {
/* 1941 */             if ((stringUtils.isNotBlank(userMac)) && 
/* 1942 */               (!"error".equals(userMac))) {
/* 1943 */               Boolean isHave = Boolean.valueOf(false);
/* 1944 */               for (Portalaccountmacs amacs : accountmacs)
/*      */               {
/* 1946 */                 if (amacs.getMac().toLowerCase()
/* 1946 */                   .equals(userMac)) {
/* 1947 */                   isHave = Boolean.valueOf(true);
/* 1948 */                   break;
/*      */                 }
/*      */               }
/* 1951 */               if (!isHave.booleanValue()) {
/* 1952 */                 Portalaccountmacs mac = new Portalaccountmacs();
/* 1953 */                 mac.setAccountId(userId);
/* 1954 */                 mac.setMac(userMac);
/* 1955 */                 this.macsService.addPortalaccountmacs(mac);
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 1961 */             Boolean isHave = Boolean.valueOf(false);
/* 1962 */             for (Portalaccountmacs amacs : accountmacs)
/*      */             {
/* 1964 */               if (amacs.getMac().toLowerCase()
/* 1964 */                 .equals(userMac)) {
/* 1965 */                 isHave = Boolean.valueOf(true);
/* 1966 */                 break;
/*      */               }
/*      */             }
/* 1969 */             if (!isHave.booleanValue())
/*      */             {
/* 1971 */               map.put("ret", "1");
/* 1972 */               map.put("i", ip);
/* 1973 */               map.put("u", username);
/* 1974 */               map.put("msg", "此账号没有绑定该设备，请联系管理员！！");
/* 1975 */               return map;
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 1980 */         if ((auto == 1) && 
/* 1981 */           (!stringUtils.isBlank(userMac)) && 
/* 1982 */           (!"error"
/* 1982 */           .equals(userMac))) {
/* 1983 */           PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/* 1984 */           mq.setAccountId(userId);
/* 1985 */           List<Portalaccountmacs> accountmacs = this.macsService
/* 1986 */             .getPortalaccountmacsList(mq);
/* 1987 */           if ((limitCount == 0) || 
/* 1988 */             (accountmacs.size() < limitCount)) {
/* 1989 */             boolean macNotHave = true;
/* 1990 */             for (Portalaccountmacs mac : accountmacs) {
/* 1991 */               if (userMac.equals(mac.getMac())) {
/* 1992 */                 macNotHave = false;
/*      */               }
/*      */             }
/* 1995 */             if (macNotHave) {
/* 1996 */               Portalaccountmacs mac = new Portalaccountmacs();
/* 1997 */               mac.setAccountId(userId);
/* 1998 */               mac.setMac(userMac);
/* 1999 */               this.macsService.addPortalaccountmacs(mac);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2010 */       if (info.booleanValue()) {
/* 2011 */         rosInfo = new String[5];
/* 2012 */         rosInfo[0] = "1";
/* 2013 */         rosInfo[1] = "3";
/* 2014 */         rosInfo[2] = username;
/* 2015 */         rosInfo[3] = authUser;
/* 2016 */         rosInfo[4] = authPwd;
/* 2017 */         RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
/*      */ 
/* 2019 */         session.setAttribute("authUser", authUser);
/* 2020 */         session.setAttribute("authPwd", authPwd);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2029 */       if (1 == CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))) {
/* 2030 */         map.put("ret", "1");
/* 2031 */         map.put("i", ip);
/* 2032 */         map.put("u", username);
/* 2033 */         map.put("msg", "该设备当日时长已用完！！");
/* 2034 */         return map;
/*      */       }
/*      */ 
/* 2037 */       bsq = new PortalbasauthQuery();
/* 2038 */       bsq.setBasip(basip);
/* 2039 */       List<Portalbasauth> basauths = this.portalbasauthService
/* 2040 */         .getPortalbasauthList(bsq);
/* 2041 */       if (basauths.size() > 0) {
/* 2042 */         for (Portalbasauth ba : basauths) {
/* 2043 */           if (ba.getType().intValue() == 2) {
/* 2044 */             authUrl = ba.getUrl();
/* 2045 */             if (!stringUtils.isBlank(authUrl)) break;
/* 2046 */             authUrl = ikweb;
/*      */ 
/* 2048 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 2052 */       if (stringUtils.isBlank(authUrl)) {
/* 2053 */         authUrl = 
/* 2054 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 2054 */           .get("core"))[0];
/*      */       }
/*      */ 
/* 2057 */       info = Boolean.valueOf(true);
/* 2058 */       if (info.booleanValue()) {
/* 2059 */         String userMac = ikmac;
/* 2060 */         rosInfo = new String[5];
/* 2061 */         rosInfo[0] = "2";
/* 2062 */         rosInfo[1] = "4";
/* 2063 */         rosInfo[2] = username;
/* 2064 */         rosInfo[3] = username;
/* 2065 */         rosInfo[4] = password;
/* 2066 */         RosAuthMap.getInstance().getRosAuthMap().put(ikmac, rosInfo);
/* 2067 */         session.setAttribute("authUser", username);
/* 2068 */         session.setAttribute("authPwd", password);
/*      */ 
/* 2072 */         if (stringUtils.isNotBlank(userMac))
/*      */         {
/* 2074 */           boolean macHave = false;
/* 2075 */           List<Portalaccountmacs> macs = this.macsService
/* 2076 */             .getPortalaccountmacsList(new PortalaccountmacsQuery());
/* 2077 */           for (Portalaccountmacs mac : macs) {
/* 2078 */             if (mac.getMac().equals(userMac)) {
/* 2079 */               macHave = true;
/* 2080 */               break;
/*      */             }
/*      */           }
/* 2083 */           if (!macHave) {
/* 2084 */             List accs = this.accountService.getPortalaccountList(new PortalaccountQuery());
/* 2085 */             if ((accs.size() > 0) && 
/* 2086 */               (((Portalaccount)accs.get(0)).getAutologin().intValue() == 1)) {
/* 2087 */               Portalaccountmacs accMac = new Portalaccountmacs();
/* 2088 */               accMac.setAccountId(((Portalaccount)accs.get(0)).getId());
/* 2089 */               accMac.setMac(userMac);
/* 2090 */               this.macsService.addPortalaccountmacs(accMac);
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
/* 2101 */     if (info.booleanValue())
/*      */     {
/* 2104 */       session.setAttribute("password", password);
/* 2105 */       Cookie cookiePwd = new Cookie("password", password);
/* 2106 */       cookiePwd.setMaxAge(86400);
/* 2107 */       response.addCookie(cookiePwd);
/*      */ 
/* 2110 */       if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
/* 2111 */         Cookie cookieMac = new Cookie("mac", ikmac);
/* 2112 */         cookieMac.setMaxAge(86400);
/* 2113 */         response.addCookie(cookieMac);
/* 2114 */         session.setAttribute("ikmac", ikmac);
/*      */       }
/*      */ 
/* 2117 */       session.setAttribute("username", username);
/* 2118 */       session.setAttribute("ip", ip);
/* 2119 */       session.setAttribute("basip", basip);
/* 2120 */       session.setAttribute("ikweb", authUrl);
/*      */ 
/* 2122 */       Cookie cookieIp = new Cookie("ip", ip);
/* 2123 */       cookieIp.setMaxAge(86400);
/* 2124 */       response.addCookie(cookieIp);
/*      */ 
/* 2126 */       Cookie cookieBasIp = new Cookie("basip", basip);
/* 2127 */       cookieBasIp.setMaxAge(86400);
/* 2128 */       response.addCookie(cookieBasIp);
/*      */ 
/* 2130 */       map.put("ret", "0");
/* 2131 */       map.put("i", ip);
/* 2132 */       map.put("u", username);
/* 2133 */       return map;
/*      */     }
/*      */ 
/* 2136 */     map.put("ret", "1");
/* 2137 */     map.put("i", ip);
/* 2138 */     map.put("u", username);
/* 2139 */     map.put("msg", "认证失败！");
/* 2140 */     return map;
/*      */   }
/*      */ 
/*      */   private int CheckMacTimeLimitMethod(String param, Long id)
/*      */   {
/* 2152 */     if (Do()) {
/* 2153 */       Portalonlinelimit onlinelimit = this.onlinelimitService
/* 2154 */         .getPortalonlinelimitByKey(id);
/* 2155 */       if (onlinelimit.getState().intValue() == 1) {
/* 2156 */         if (onlinelimit.getType().intValue() == 0) {
/* 2157 */           if ((stringUtils.isNotBlank(param)) && 
/* 2158 */             (!"error".equals(param))) {
/* 2159 */             String[] TimeInfo = 
/* 2160 */               (String[])MacLimitMap.getInstance()
/* 2160 */               .getMacLimitMap().get(param);
/* 2161 */             if (TimeInfo != null) {
/* 2162 */               Long timepermit = onlinelimit.getTime();
/* 2163 */               if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 2164 */                 return 1;
/*      */             }
/*      */           }
/*      */           else {
/* 2168 */             return 2;
/*      */           }
/*      */         }
/* 2171 */         else if (stringUtils.isNotBlank(param)) {
/* 2172 */           String[] TimeInfo = 
/* 2173 */             (String[])UserLimitMap.getInstance()
/* 2173 */             .getUserLimitMap().get(param);
/* 2174 */           if (TimeInfo != null) {
/* 2175 */             Long timepermit = onlinelimit.getTime();
/* 2176 */             if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 2177 */               return 1;
/*      */           }
/*      */         }
/*      */         else {
/* 2181 */           return 2;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2187 */     return 0;
/*      */   }
/*      */ 
/*      */   public static boolean Do() {
/* 2191 */     Long isThis = Long.valueOf(new Date().getTime());
/* 2192 */     boolean Do = false;
/* 2193 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 2194 */       Do = true;
/*      */     }
/* 2196 */     return Do;
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxInterFaceWISPrController
 * JD-Core Version:    0.6.2
 */