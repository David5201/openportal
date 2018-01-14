/*      */ package com.leeson.portal.core.controller.WISPr.utils;
/*      */ 
/*      */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalaccountmacs;
/*      */ import com.leeson.core.bean.Portalap;
/*      */ import com.leeson.core.bean.Portalautologin;
/*      */ import com.leeson.core.bean.Portalbas;
/*      */ import com.leeson.core.bean.Portalbasauth;
/*      */ import com.leeson.core.bean.Portalip;
/*      */ import com.leeson.core.bean.Portallinkrecord;
/*      */ import com.leeson.core.bean.Portalonlinelimit;
/*      */ import com.leeson.core.bean.Portalssid;
/*      */ import com.leeson.core.bean.Portaltimeweb;
/*      */ import com.leeson.core.bean.Portalweb;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalaccountmacsQuery;
/*      */ import com.leeson.core.query.PortalapQuery;
/*      */ import com.leeson.core.query.PortalbasauthQuery;
/*      */ import com.leeson.core.query.PortalipQuery;
/*      */ import com.leeson.core.query.PortalssidQuery;
/*      */ import com.leeson.core.query.PortaltimewebQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalaccountmacsService;
/*      */ import com.leeson.core.service.PortalapService;
/*      */ import com.leeson.core.service.PortalautologinService;
/*      */ import com.leeson.core.service.PortalbasauthService;
/*      */ import com.leeson.core.service.PortalipService;
/*      */ import com.leeson.core.service.PortallinkrecordService;
/*      */ import com.leeson.core.service.PortalonlinelimitService;
/*      */ import com.leeson.core.service.PortalssidService;
/*      */ import com.leeson.core.service.PortaltimewebService;
/*      */ import com.leeson.core.service.PortalwebService;
/*      */ import com.leeson.core.utils.IPv4Util;
/*      */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*      */ import com.leeson.portal.core.model.AutoLoginMap;
/*      */ import com.leeson.portal.core.model.MacLimitMap;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.portal.core.model.RosAuthMap;
/*      */ import com.leeson.portal.core.model.UserLimitMap;
/*      */ import com.leeson.portal.core.model.ipMap;
/*      */ import com.leeson.portal.core.model.isDo;
/*      */ import com.leeson.portal.core.utils.SpringContextHelper;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class Tools
/*      */ {
/*   62 */   private static Logger logger = Logger.getLogger(Tools.class);
/*      */ 
/*   64 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*      */ 
/*   66 */   private static PortalaccountService accountService = (PortalaccountService)
/*   67 */     SpringContextHelper.getBean("portalaccountServiceImpl");
/*      */ 
/*   68 */   private static PortalautologinService autologinService = (PortalautologinService)
/*   69 */     SpringContextHelper.getBean("portalautologinServiceImpl");
/*      */ 
/*   70 */   private static PortalapService apService = (PortalapService)
/*   71 */     SpringContextHelper.getBean("portalapServiceImpl");
/*      */ 
/*   72 */   private static PortalbasauthService basauthService = (PortalbasauthService)
/*   73 */     SpringContextHelper.getBean("portalbasauthServiceImpl");
/*      */ 
/*   74 */   private static PortalonlinelimitService onlinelimitService = (PortalonlinelimitService)
/*   75 */     SpringContextHelper.getBean("portalonlinelimitServiceImpl");
/*      */ 
/*   76 */   private static PortalaccountmacsService macsService = (PortalaccountmacsService)
/*   77 */     SpringContextHelper.getBean("portalaccountmacsServiceImpl");
/*      */ 
/*   78 */   private static PortalssidService ssidService = (PortalssidService)
/*   79 */     SpringContextHelper.getBean("portalssidServiceImpl");
/*      */ 
/*   80 */   private static PortalwebService webService = (PortalwebService)
/*   81 */     SpringContextHelper.getBean("portalwebServiceImpl");
/*      */ 
/*   82 */   private static PortallinkrecordService linkRecordService = (PortallinkrecordService)
/*   83 */     SpringContextHelper.getBean("portallinkrecordServiceImpl");
/*      */ 
/*   84 */   private static PortalipService ipService = (PortalipService)
/*   85 */     SpringContextHelper.getBean("portalipServiceImpl");
/*      */ 
/*   86 */   private static ConfigService configService = (ConfigService)
/*   87 */     SpringContextHelper.getBean("configServiceImpl");
/*      */ 
/*   88 */   private static PortaltimewebService portaltimewebService = (PortaltimewebService)
/*   89 */     SpringContextHelper.getBean("portaltimewebServiceImpl");
/*      */ 
/*      */   public static int CheckMacTimeLimitMethod(String param, Long id)
/*      */   {
/*   99 */     Portalonlinelimit onlinelimit = onlinelimitService
/*  100 */       .getPortalonlinelimitByKey(id);
/*  101 */     if (onlinelimit.getState().intValue() == 1) {
/*  102 */       if (onlinelimit.getType().intValue() == 0) {
/*  103 */         if ((stringUtils.isNotBlank(param)) && (!"error".equals(param))) {
/*  104 */           String[] TimeInfo = 
/*  105 */             (String[])MacLimitMap.getInstance()
/*  105 */             .getMacLimitMap().get(param);
/*  106 */           if (TimeInfo != null) {
/*  107 */             Long timepermit = onlinelimit.getTime();
/*  108 */             if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/*  109 */               return 1;
/*      */           }
/*      */         }
/*      */         else {
/*  113 */           return 2;
/*      */         }
/*      */       }
/*  116 */       else if (stringUtils.isNotBlank(param)) {
/*  117 */         String[] TimeInfo = 
/*  118 */           (String[])UserLimitMap.getInstance()
/*  118 */           .getUserLimitMap().get(param);
/*  119 */         if (TimeInfo != null) {
/*  120 */           Long timepermit = onlinelimit.getTime();
/*  121 */           if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/*  122 */             return 1;
/*      */         }
/*      */       }
/*      */       else {
/*  126 */         return 2;
/*      */       }
/*      */     }
/*      */ 
/*  130 */     return 0;
/*      */   }
/*      */ 
/*      */   public static boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets)
/*      */   {
/*  142 */     Portalaccount account = accountService.getPortalaccountByKey(userId);
/*      */ 
/*  144 */     if (userState.equals("0")) {
/*  145 */       return false;
/*      */     }
/*      */ 
/*  148 */     if (userState.equals("1")) {
/*  149 */       return true;
/*      */     }
/*      */ 
/*  152 */     if (userState.equals("3")) {
/*  153 */       Date now = new Date();
/*  154 */       if (userDate.getTime() > now.getTime()) {
/*  155 */         return true;
/*      */       }
/*  157 */       account.setState("2");
/*  158 */       accountService.updatePortalaccountByKey(account);
/*  159 */       userState = "2";
/*      */     }
/*      */ 
/*  163 */     if (userState.equals("2")) {
/*  164 */       if (userTime.longValue() > 0L) {
/*  165 */         return true;
/*      */       }
/*  167 */       account.setState("4");
/*  168 */       accountService.updatePortalaccountByKey(account);
/*  169 */       userState = "4";
/*      */     }
/*      */ 
/*  173 */     if (userState.equals("4")) {
/*  174 */       if (octets.longValue() > 0L) {
/*  175 */         return true;
/*      */       }
/*  177 */       account.setState("0");
/*  178 */       accountService.updatePortalaccountByKey(account);
/*  179 */       return false;
/*      */     }
/*      */ 
/*  182 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean Do() {
/*  186 */     Long isThis = Long.valueOf(new Date().getTime());
/*  187 */     boolean Do = false;
/*  188 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/*  189 */       Do = true;
/*      */     }
/*  191 */     return Do;
/*      */   }
/*      */ 
/*      */   public static boolean autoLogin(String basip, String ip, String ikmac, Portalbas basConfig, HttpSession session)
/*      */   {
/*      */     try {
/*  197 */       if (Do())
/*      */       {
/*      */         String authUser;
/*      */         Long userId;
/*  198 */         if (stringUtils.isNotBlank(ikmac)) {
/*  199 */           String[] macTimeInfo = 
/*  200 */             (String[])AutoLoginMacMap.getInstance()
/*  200 */             .getAutoLoginMacMap().get(ikmac);
/*  201 */           if (macTimeInfo != null) {
/*  202 */             String[] userinfo = 
/*  203 */               (String[])AutoLoginMap.getInstance()
/*  203 */               .getAutoLoginMap().get(ikmac);
/*      */             try {
/*  205 */               Long id = Long.valueOf(macTimeInfo[2]);
/*  206 */               if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
/*  207 */                 authUser = userinfo[0];
/*  208 */                 String authPwd = userinfo[1];
/*  209 */                 String username = userinfo[2];
/*  210 */                 Portalautologin autologin = autologinService
/*  211 */                   .getPortalautologinByKey(id);
/*  212 */                 if ((autologin != null) && 
/*  213 */                   (autologin.getState().intValue() == 1)) {
/*  214 */                   Long timepermit = autologin.getTime();
/*  215 */                   String loginTimeString = macTimeInfo[0];
/*  216 */                   Long oldcostTime = 
/*  217 */                     Long.valueOf(macTimeInfo[1]);
/*  218 */                   Long costTime = oldcostTime;
/*      */ 
/*  220 */                   if (stringUtils.isNotBlank(loginTimeString)) {
/*  221 */                     Date loginTime = 
/*  222 */                       ThreadLocalDateUtil.parse(loginTimeString);
/*  223 */                     String nowString = 
/*  224 */                       ThreadLocalDateUtil.format(new Date());
/*  225 */                     Date nowTime = 
/*  226 */                       ThreadLocalDateUtil.parse(nowString);
/*  227 */                     costTime = Long.valueOf(nowTime.getTime() - 
/*  228 */                       loginTime.getTime() + 
/*  229 */                       oldcostTime.longValue());
/*      */                   }
/*  231 */                   if ((costTime.longValue() < timepermit.longValue()) || 
/*  232 */                     (timepermit.longValue() <= 0L)) {
/*  233 */                     userId = null;
/*  234 */                     String userState = null;
/*  235 */                     String password = authPwd;
/*  236 */                     boolean CheckAccount = false;
/*  237 */                     if (3L == id.longValue())
/*      */                     {
/*  239 */                       PortalaccountQuery accq = new PortalaccountQuery();
/*  240 */                       accq.setLoginNameLike(false);
/*  241 */                       accq.setLoginName(username);
/*      */ 
/*  243 */                       if (!"1".equals(basConfig
/*  243 */                         .getIsPortalCheck())) {
/*  244 */                         accq.setPasswordLike(false);
/*  245 */                         accq.setPassword(password);
/*      */                       }
/*  247 */                       List accs = accountService
/*  248 */                         .getPortalaccountList(accq);
/*  249 */                       if ((accs != null) && 
/*  250 */                         (accs.size() == 1)) {
/*  251 */                         Portalaccount acc = 
/*  252 */                           (Portalaccount)accs
/*  252 */                           .get(0);
/*  253 */                         if (acc != null) {
/*  254 */                           userId = acc.getId();
/*  255 */                           Date userDate = acc
/*  256 */                             .getDate();
/*  257 */                           Long userTime = acc
/*  258 */                             .getTime();
/*  259 */                           Long octets = acc
/*  260 */                             .getOctets();
/*  261 */                           if (octets == null) {
/*  262 */                             octets = Long.valueOf(0L);
/*      */                           }
/*  264 */                           userState = acc
/*  265 */                             .getState();
/*  266 */                           password = acc
/*  267 */                             .getPassword();
/*      */ 
/*  274 */                           if (checkTimeOut(
/*  270 */                             userState, 
/*  271 */                             userId, 
/*  272 */                             userDate, 
/*  273 */                             userTime, 
/*  274 */                             octets)) {
/*  275 */                             CheckAccount = true;
/*      */                           }
/*      */ 
/*  280 */                           if (!"1"
/*  279 */                             .equals(basConfig
/*  280 */                             .getIsPortalCheck()))
/*      */                           {
/*  282 */                             if (!password
/*  282 */                               .equals(authPwd)) {
/*  283 */                               CheckAccount = false;
/*      */                             }
/*      */ 
/*      */                           }
/*      */ 
/*  288 */                           if (CheckAccount)
/*      */                           {
/*  290 */                             if ("1".equals(basConfig
/*  290 */                               .getIsPortalCheck())) {
/*  291 */                               Integer limitCount = acc
/*  292 */                                 .getMaclimitcount();
/*  293 */                               if ((limitCount != null) && 
/*  294 */                                 (limitCount.intValue() > 0)) {
/*  295 */                                 int count = 0;
/*  296 */                                 Iterator iterator = 
/*  297 */                                   OnlineMap.getInstance()
/*  298 */                                   .getOnlineUserMap()
/*  299 */                                   .keySet()
/*  300 */                                   .iterator();
/*      */ 
/*  302 */                                 while (iterator
/*  302 */                                   .hasNext()) {
/*  303 */                                   Object o = iterator
/*  304 */                                     .next();
/*  305 */                                   String t = o
/*  306 */                                     .toString();
/*  307 */                                   String[] loginInfo = 
/*  310 */                                     (String[])OnlineMap.getInstance()
/*  309 */                                     .getOnlineUserMap()
/*  310 */                                     .get(t);
/*  311 */                                   String haveUsername = loginInfo[0];
/*      */ 
/*  313 */                                   if (username
/*  313 */                                     .equals(haveUsername)) {
/*  314 */                                     count++;
/*      */                                   }
/*      */                                 }
/*  317 */                                 if (count >= limitCount.intValue())
/*  318 */                                   CheckAccount = false;
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     else
/*      */                     {
/*  327 */                       CheckAccount = true;
/*      */                     }
/*      */ 
/*  330 */                     if (CheckAccount) {
/*  331 */                       String tid = "0";
/*  332 */                       if (1L == id.longValue())
/*  333 */                         tid = "4";
/*  334 */                       else if (2L == id.longValue())
/*  335 */                         tid = "0";
/*  336 */                       else if (3L == id.longValue())
/*  337 */                         tid = "1";
/*  338 */                       else if (4L == id.longValue())
/*  339 */                         tid = "2";
/*  340 */                       else if (5L == id.longValue())
/*  341 */                         tid = "3";
/*  342 */                       else if (6L == id.longValue())
/*  343 */                         tid = "5";
/*  344 */                       else if (7L == id.longValue())
/*  345 */                         tid = "6";
/*  346 */                       else if (8L == id.longValue()) {
/*  347 */                         tid = "7";
/*      */                       }
/*      */ 
/*  350 */                       String[] rosInfo = new String[6];
/*  351 */                       rosInfo[0] = tid;
/*  352 */                       rosInfo[1] = macTimeInfo[2];
/*  353 */                       rosInfo[2] = username;
/*  354 */                       rosInfo[3] = authUser;
/*  355 */                       rosInfo[4] = authPwd;
/*  356 */                       rosInfo[5] = "auto";
/*  357 */                       RosAuthMap.getInstance()
/*  358 */                         .getRosAuthMap()
/*  359 */                         .put(ikmac, rosInfo);
/*      */ 
/*  361 */                       session.setAttribute(
/*  362 */                         "username", username);
/*  363 */                       session.setAttribute(
/*  364 */                         "password", password);
/*  365 */                       session.setAttribute(
/*  366 */                         "authUser", authUser);
/*  367 */                       session.setAttribute("authPwd", 
/*  368 */                         authPwd);
/*  369 */                       session.setAttribute("ip", ip);
/*  370 */                       session.setAttribute("basip", 
/*  371 */                         basip);
/*  372 */                       return true;
/*      */                     }
/*  374 */                     AutoLoginMacMap.getInstance()
/*  375 */                       .getAutoLoginMacMap()
/*  376 */                       .remove(ikmac);
/*  377 */                     AutoLoginMap.getInstance()
/*  378 */                       .getAutoLoginMap()
/*  379 */                       .remove(ikmac);
/*      */                   }
/*      */                   else
/*      */                   {
/*  383 */                     AutoLoginMacMap.getInstance()
/*  384 */                       .getAutoLoginMacMap()
/*  385 */                       .remove(ikmac);
/*  386 */                     AutoLoginMap.getInstance()
/*  387 */                       .getAutoLoginMap()
/*  388 */                       .remove(ikmac);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception localException1)
/*      */             {
/*      */             }
/*      */           }
/*      */         }
/*  398 */         if ((1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) && 
/*  399 */           (1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))))
/*      */         {
/*  401 */           if (!ipMap.getInstance().getIpmap().containsKey(ip)) {
/*  402 */             ipMap.getInstance().getIpmap().put(ip, Integer.valueOf(1));
/*      */ 
/*  404 */             String userMac = ikmac;
/*      */ 
/*  406 */             if (stringUtils.isNotBlank(userMac))
/*      */             {
/*  408 */               List<Portalaccountmacs> macs = macsService
/*  409 */                 .getPortalaccountmacsList(new PortalaccountmacsQuery());
/*  410 */               for (Portalaccountmacs mac : macs) {
/*  411 */                 if (mac.getMac().equals(userMac)) {
/*  412 */                   Portalaccount acc = accountService
/*  413 */                     .getPortalaccountByKey(mac
/*  414 */                     .getAccountId());
/*  415 */                   int state = acc.getAutologin().intValue();
/*  416 */                   if (state != 1) {
/*      */                     break;
/*      */                   }
/*  419 */                   if (basConfig.getAuthInterface()
/*  419 */                     .contains("1"))
/*      */                   {
/*  421 */                     PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  422 */                     bsq.setBasip(basip);
/*  423 */                     authUser = basConfig
/*  424 */                       .getBasUser();
/*  425 */                     String authPwd = basConfig
/*  426 */                       .getBasPwd();
/*  427 */                     List<Portalbasauth> basauths = basauthService
/*  428 */                       .getPortalbasauthList(bsq);
/*  429 */                     if (basauths.size() > 0) {
/*  430 */                       for (Portalbasauth ba : basauths) {
/*  431 */                         if (ba.getType().intValue() == 1) {
/*  432 */                           authUser = ba
/*  433 */                             .getUsername();
/*  434 */                           authPwd = ba
/*  435 */                             .getPassword();
/*      */ 
/*  437 */                           if ((!stringUtils.isBlank(authUser)) && 
/*  439 */                             (!stringUtils.isBlank(authPwd))) break;
/*  440 */                           authUser = basConfig
/*  441 */                             .getBasUser();
/*  442 */                           authPwd = basConfig
/*  443 */                             .getBasPwd();
/*      */ 
/*  445 */                           break;
/*      */                         }
/*      */                       }
/*      */                     }
/*  449 */                     if (acc == null) break;
/*  450 */                     userId = acc.getId();
/*  451 */                     Date userDate = acc.getDate();
/*  452 */                     Long userTime = acc.getTime();
/*  453 */                     Long octets = acc.getOctets();
/*  454 */                     if (octets == null) {
/*  455 */                       octets = Long.valueOf(0L);
/*      */                     }
/*  457 */                     String username = acc
/*  458 */                       .getLoginName();
/*  459 */                     String userState = acc
/*  460 */                       .getState();
/*  461 */                     String password = acc
/*  462 */                       .getPassword();
/*      */ 
/*  465 */                     if (!"1".equals(basConfig
/*  465 */                       .getIsPortalCheck())) {
/*  466 */                       authUser = username;
/*  467 */                       authPwd = password;
/*      */                     }
/*      */ 
/*  472 */                     if (!checkTimeOut(userState, 
/*  471 */                       userId, userDate, 
/*  472 */                       userTime, octets)) {
/*      */                       break;
/*      */                     }
/*  475 */                     boolean onlimitCan = true;
/*      */ 
/*  477 */                     if ("1".equals(basConfig
/*  477 */                       .getIsPortalCheck())) {
/*  478 */                       Integer limitCount = acc
/*  479 */                         .getMaclimitcount();
/*  480 */                       if ((limitCount != null) && 
/*  481 */                         (limitCount.intValue() > 0)) {
/*  482 */                         int count = 0;
/*  483 */                         Iterator iterator = 
/*  484 */                           OnlineMap.getInstance()
/*  485 */                           .getOnlineUserMap()
/*  486 */                           .keySet()
/*  487 */                           .iterator();
/*      */ 
/*  489 */                         while (iterator
/*  489 */                           .hasNext()) {
/*  490 */                           Object o = iterator
/*  491 */                             .next();
/*  492 */                           String t = o
/*  493 */                             .toString();
/*  494 */                           String[] loginInfo = 
/*  497 */                             (String[])OnlineMap.getInstance()
/*  496 */                             .getOnlineUserMap()
/*  497 */                             .get(t);
/*  498 */                           String haveUsername = loginInfo[0];
/*      */ 
/*  500 */                           if (username
/*  500 */                             .equals(haveUsername)) {
/*  501 */                             count++;
/*      */                           }
/*      */                         }
/*  504 */                         if (count >= limitCount.intValue()) {
/*  505 */                           onlimitCan = false;
/*      */                         }
/*      */ 
/*      */                       }
/*      */ 
/*      */                     }
/*      */ 
/*  512 */                     if (!onlimitCan) break;
/*  513 */                     String[] rosInfo = new String[6];
/*  514 */                     rosInfo[0] = "1";
/*  515 */                     rosInfo[1] = "3";
/*  516 */                     rosInfo[2] = username;
/*  517 */                     rosInfo[3] = authUser;
/*  518 */                     rosInfo[4] = authPwd;
/*  519 */                     rosInfo[5] = "auto";
/*      */ 
/*  521 */                     RosAuthMap.getInstance()
/*  522 */                       .getRosAuthMap()
/*  523 */                       .put(ikmac, 
/*  524 */                       rosInfo);
/*      */ 
/*  526 */                     session.setAttribute(
/*  527 */                       "username", 
/*  528 */                       username);
/*  529 */                     session.setAttribute(
/*  530 */                       "password", 
/*  531 */                       password);
/*  532 */                     session.setAttribute(
/*  533 */                       "authUser", 
/*  534 */                       authUser);
/*  535 */                     session.setAttribute(
/*  536 */                       "authPwd", 
/*  537 */                       authPwd);
/*  538 */                     session.setAttribute(
/*  539 */                       "ip", ip);
/*  540 */                     session.setAttribute(
/*  541 */                       "basip", basip);
/*      */ 
/*  543 */                     ipMap.getInstance()
/*  544 */                       .getIpmap()
/*  545 */                       .remove(ip);
/*  546 */                     return true;
/*      */                   }
/*      */ 
/*  552 */                   String[] userinfo = null;
/*  553 */                   userinfo = 
/*  556 */                     (String[])AutoLoginMap.getInstance()
/*  555 */                     .getAutoLoginMap()
/*  556 */                     .get(userMac);
/*  557 */                   String username = "";
/*  558 */                   String password = "";
/*  559 */                   String phone = "";
/*  560 */                   if ((userinfo != null) && 
/*  561 */                     (userinfo.length == 2)) {
/*  562 */                     username = userinfo[0];
/*  563 */                     password = userinfo[1];
/*      */                   }
/*  565 */                   if ((userinfo != null) && 
/*  566 */                     (userinfo.length == 3)) {
/*  567 */                     username = userinfo[0];
/*  568 */                     password = userinfo[1];
/*  569 */                     phone = userinfo[2];
/*      */                   }
/*      */ 
/*  574 */                   String[] rosInfo = new String[6];
/*  575 */                   rosInfo[0] = "2";
/*  576 */                   rosInfo[1] = "4";
/*  577 */                   rosInfo[2] = phone;
/*  578 */                   rosInfo[3] = username;
/*  579 */                   rosInfo[4] = password;
/*  580 */                   rosInfo[5] = "auto";
/*  581 */                   RosAuthMap.getInstance()
/*  582 */                     .getRosAuthMap()
/*  583 */                     .put(ikmac, rosInfo);
/*      */ 
/*  585 */                   session.setAttribute(
/*  586 */                     "username", phone);
/*  587 */                   session.setAttribute(
/*  588 */                     "password", password);
/*  589 */                   session.setAttribute(
/*  590 */                     "authUser", username);
/*  591 */                   session.setAttribute("authPwd", 
/*  592 */                     password);
/*  593 */                   session.setAttribute("ip", ip);
/*  594 */                   session.setAttribute("basip", 
/*  595 */                     basip);
/*      */ 
/*  597 */                   ipMap.getInstance().getIpmap()
/*  598 */                     .remove(ip);
/*  599 */                   return true;
/*      */                 }
/*      */ 
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
/*      */     catch (Exception e)
/*      */     {
/*  615 */       logger.error("==============ERROR Start=============");
/*  616 */       logger.error(e);
/*  617 */       logger.error("ERROR INFO ", e);
/*  618 */       logger.error("==============ERROR End=============");
/*      */     }
/*  620 */     ipMap.getInstance().getIpmap().remove(ip);
/*  621 */     return false;
/*      */   }
/*      */ 
/*      */   public static String getWebMod(String ssid, String apmac, String ip, Long basConfigWeb)
/*      */   {
/*  627 */     String webMod = "";
/*  628 */     Long webID = Long.valueOf(0L);
/*  629 */     if (Do()) {
/*      */       try {
/*      */         PortaltimewebQuery timewebq;
/*      */         try {
/*  633 */           Date date = new Date();
/*  634 */           int weekDay = date.getDay();
/*  635 */           if (weekDay == 0) {
/*  636 */             weekDay = 7;
/*      */           }
/*  638 */           int dateDay = date.getDate();
/*  639 */           int monthDay = date.getMonth() + 1;
/*      */ 
/*  641 */           timewebq = new PortaltimewebQuery();
/*  642 */           timewebq.orderbyPos(true);
/*  643 */           timewebq.orderbyId(true);
/*  644 */           List<Portaltimeweb> timewebs = portaltimewebService
/*  645 */             .getPortaltimewebList(timewebq);
/*  646 */           if ((timewebs != null) && (timewebs.size() > 0))
/*  647 */             for (Portaltimeweb timeweb : timewebs) {
/*  648 */               Date setday = timeweb.getViewdate();
/*  649 */               int weekday = timeweb.getViewweekday().intValue();
/*  650 */               int dateday = timeweb.getViewdateday().intValue();
/*  651 */               int monthday = timeweb.getViewmonth().intValue();
/*  652 */               if ((setday != null) || (weekday != 0) || (dateday != 0) || 
/*  653 */                 (monthday != 0))
/*      */               {
/*  656 */                 if (setday != null) {
/*  657 */                   long time = date.getTime();
/*  658 */                   long endTime = setday.getTime() + 86400000L;
/*  659 */                   if ((setday.getTime() <= time) && (time <= endTime)) {
/*  660 */                     Portalweb web = webService
/*  661 */                       .getPortalwebByKey(timeweb.getWeb());
/*  662 */                     if (web != null) {
/*  663 */                       webMod = String.valueOf(web.getId()) + 
/*  664 */                         "/";
/*  665 */                       webID = web.getId();
/*  666 */                       break;
/*      */                     }
/*      */                   }
/*      */                 } else {
/*  670 */                   boolean weekState = false;
/*  671 */                   boolean dayState = false;
/*  672 */                   boolean monthState = false;
/*  673 */                   if (weekday == 0) {
/*  674 */                     weekState = true;
/*      */                   }
/*  676 */                   else if (weekDay == weekday) {
/*  677 */                     weekState = true;
/*      */                   }
/*      */ 
/*  680 */                   if (dateday == 0) {
/*  681 */                     dayState = true;
/*      */                   }
/*  683 */                   else if (dateDay == dateday) {
/*  684 */                     dayState = true;
/*      */                   }
/*      */ 
/*  687 */                   if (monthday == 0) {
/*  688 */                     monthState = true;
/*      */                   }
/*  690 */                   else if (monthDay == monthday) {
/*  691 */                     monthState = true;
/*      */                   }
/*      */ 
/*  694 */                   if ((weekState) && (dayState) && (monthState)) {
/*  695 */                     Portalweb web = webService
/*  696 */                       .getPortalwebByKey(timeweb.getWeb());
/*  697 */                     if (web != null) {
/*  698 */                       webMod = String.valueOf(web.getId()) + 
/*  699 */                         "/";
/*  700 */                       webID = web.getId();
/*  701 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */         }
/*      */         catch (Exception localException1) {
/*      */         }
/*  710 */         if ((stringUtils.isBlank(webMod)) && 
/*  711 */           (stringUtils.isNotBlank(ssid))) {
/*  712 */           PortalssidQuery ssidq = new PortalssidQuery();
/*  713 */           ssidq.setSsid(ssid);
/*  714 */           ssidq.setSsidLike(false);
/*  715 */           List ssids = ssidService
/*  716 */             .getPortalssidList(ssidq);
/*  717 */           if ((ssids != null) && (ssids.size() > 0)) {
/*  718 */             Portalssid sside = (Portalssid)ssids.get(0);
/*  719 */             Portalweb web = webService.getPortalwebByKey(sside
/*  720 */               .getWeb());
/*  721 */             if (web != null) {
/*  722 */               webMod = String.valueOf(web.getId()) + "/";
/*  723 */               webID = web.getId();
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  728 */         if ((stringUtils.isBlank(webMod)) && 
/*  729 */           (stringUtils.isNotBlank(apmac))) {
/*  730 */           PortalapQuery apq = new PortalapQuery();
/*  731 */           apq.setMac(apmac);
/*  732 */           apq.setMacLike(false);
/*  733 */           List aps = apService.getPortalapList(apq);
/*  734 */           if ((aps != null) && (aps.size() > 0)) {
/*  735 */             Portalap ap = (Portalap)aps.get(0);
/*  736 */             Portalweb web = webService.getPortalwebByKey(ap
/*  737 */               .getWeb());
/*  738 */             if (web != null) {
/*  739 */               webMod = String.valueOf(web.getId()) + "/";
/*  740 */               webID = web.getId();
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  745 */         if (stringUtils.isBlank(webMod)) {
/*  746 */           Portalweb web = webService.getPortalwebByKey(basConfigWeb);
/*  747 */           if (web != null) {
/*  748 */             webMod = String.valueOf(web.getId()) + "/";
/*  749 */             webID = web.getId();
/*      */           }
/*      */         }
/*  752 */         if (stringUtils.isBlank(webMod)) {
/*  753 */           long ipL = IPv4Util.ipToLong(ip);
/*  754 */           List<Portalip> iplList = ipService
/*  755 */             .getPortalipList(new PortalipQuery());
/*  756 */           for (Portalip portalip : iplList) {
/*  757 */             long startH = IPv4Util.ipToLong(portalip.getStart());
/*  758 */             long endH = IPv4Util.ipToLong(portalip.getEnd());
/*  759 */             if ((ipL >= startH) && (ipL <= endH)) {
/*  760 */               Portalweb web = webService
/*  761 */                 .getPortalwebByKey(portalip.getWeb());
/*  762 */               if (web == null) break;
/*  763 */               webMod = String.valueOf(web.getId()) + "/";
/*  764 */               webID = web.getId();
/*      */ 
/*  766 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/*  771 */         webMod = "";
/*  772 */         webID = Long.valueOf(0L);
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  777 */       if (webID.longValue() != 0L) {
/*  778 */         Portalweb portalweb = webService.getPortalwebByKey(webID);
/*  779 */         if (portalweb != null) {
/*  780 */           portalweb.setCountShow(Long.valueOf(portalweb.getCountShow().longValue() + 1L));
/*  781 */           webService.updatePortalwebByKey(portalweb);
/*      */         }
/*      */       } else {
/*  784 */         com.leeson.core.bean.Config config = configService
/*  785 */           .getConfigByKey(Long.valueOf(1L));
/*  786 */         if (config != null) {
/*  787 */           config.setCountShow(Long.valueOf(config.getCountShow().longValue() + 1L));
/*  788 */           configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException2) {
/*      */     }
/*  794 */     return webMod;
/*      */   }
/*      */ 
/*      */   public static Long getAccountid(String username)
/*      */   {
/*  803 */     PortalaccountQuery q = new PortalaccountQuery();
/*  804 */     q.setLoginName(username);
/*  805 */     q.setLoginNameLike(false);
/*  806 */     List accs = accountService.getPortalaccountList(q);
/*  807 */     Portalaccount acc = null;
/*  808 */     if (accs.size() > 0) {
/*  809 */       acc = (Portalaccount)accs.get(0);
/*      */     }
/*  811 */     if (acc != null) {
/*  812 */       return acc.getId();
/*      */     }
/*  814 */     return null;
/*      */   }
/*      */ 
/*      */   public static Long doLinkRecord(Long userId, String ip, String basip, String mac)
/*      */   {
/*  827 */     Portalbas basconfig = (Portalbas)config.getConfigMap().get(basip);
/*  828 */     if ((basconfig != null) && 
/*  829 */       ("1".equals(basconfig.getIsPortalCheck()))) {
/*  830 */       Portalaccount acc = accountService
/*  831 */         .getPortalaccountByKey(userId);
/*  832 */       if (acc != null) {
/*  833 */         String username = acc.getLoginName();
/*  834 */         String userState = acc.getState();
/*  835 */         Portallinkrecord linkRecord = new Portallinkrecord();
/*  836 */         linkRecord.setStartDate(new Date());
/*  837 */         linkRecord.setIp(ip);
/*  838 */         linkRecord.setBasip(basip);
/*  839 */         linkRecord.setMac(mac);
/*  840 */         linkRecord.setIns(Long.valueOf(0L));
/*  841 */         linkRecord.setOuts(Long.valueOf(0L));
/*  842 */         linkRecord.setOctets(Long.valueOf(0L));
/*  843 */         linkRecord.setLoginName(username);
/*  844 */         linkRecord.setState(userState);
/*  845 */         linkRecord.setUid(userId);
/*  846 */         linkRecordService.addPortallinkrecord(linkRecord);
/*  847 */         return linkRecord.getId();
/*      */       }
/*      */     }
/*      */ 
/*  851 */     return null;
/*      */   }
/*      */ 
/*      */   public static void GetMacTimeLimitMethod(String[] loginInfo, String mac, HttpSession session)
/*      */   {
/*  862 */     String username = loginInfo[0];
/*  863 */     String uid = loginInfo[1];
/*  864 */     String rid = loginInfo[2];
/*  865 */     String loginTimeString = loginInfo[3];
/*  866 */     if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
/*  867 */       mac = loginInfo[4];
/*      */     }
/*  869 */     Date nowTime = new Date();
/*  870 */     Date loginTime = nowTime;
/*      */     try {
/*  872 */       loginTime = ThreadLocalDateUtil.parse(loginTimeString);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/*  878 */     int costTime = (int)((nowTime.getTime() - loginTime.getTime()) / 60000L);
/*      */ 
/*  880 */     int haveTime = getHaveTime();
/*  881 */     if ((!stringUtils.isBlank(uid)) && (!stringUtils.isBlank(rid))) {
/*  882 */       haveTime = (int)(getTime(username).longValue() / 60000L);
/*      */     }
/*  884 */     int toHaveTime = haveTime;
/*      */ 
/*  886 */     Long oldcostTime = Long.valueOf(0L);
/*  887 */     Boolean notLimit = Boolean.valueOf(true);
/*  888 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/*  889 */       String[] macTimeInfo = 
/*  890 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/*  890 */         .get(mac);
/*  891 */       if (macTimeInfo != null) {
/*  892 */         Long id = Long.valueOf(macTimeInfo[2]);
/*  893 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*  894 */         Portalonlinelimit onlinelimit = onlinelimitService
/*  895 */           .getPortalonlinelimitByKey(id);
/*  896 */         if (onlinelimit != null) {
/*  897 */           Long timepermit = onlinelimit.getTime();
/*  898 */           loginTimeString = macTimeInfo[0];
/*  899 */           if (onlinelimit.getState().intValue() == 1)
/*      */           {
/*  901 */             if (stringUtils.isNotBlank(loginTimeString))
/*      */               try {
/*  903 */                 loginTime = 
/*  904 */                   ThreadLocalDateUtil.parse(loginTimeString);
/*      */               }
/*      */               catch (Exception localException1)
/*      */               {
/*      */               }
/*  909 */             costTime = (int)((nowTime.getTime() - 
/*  910 */               loginTime.getTime() + oldcostTime.longValue()) / 60000L);
/*  911 */             haveTime = (int)(timepermit.longValue() / 60000L);
/*  912 */             notLimit = Boolean.valueOf(false);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  918 */     int overTime = costTime;
/*  919 */     if (!notLimit.booleanValue()) {
/*  920 */       haveTime -= overTime;
/*      */     }
/*  922 */     if (haveTime > toHaveTime) {
/*  923 */       haveTime = toHaveTime;
/*      */     }
/*  925 */     if (haveTime < 0) {
/*  926 */       haveTime = 0;
/*      */     }
/*  928 */     if (notLimit.booleanValue()) {
/*  929 */       overTime = costTime + (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/*      */ 
/*  932 */     String haveTimeString = String.valueOf(haveTime);
/*  933 */     String overTimeString = String.valueOf(overTime);
/*  934 */     session.setAttribute("haveTime", haveTimeString);
/*  935 */     session.setAttribute("overTime", overTimeString);
/*      */   }
/*      */ 
/*      */   public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, String authUser, String authPwd, String phone, HttpServletResponse response)
/*      */   {
/*  946 */     Portalonlinelimit onlinelimit = onlinelimitService
/*  947 */       .getPortalonlinelimitByKey(id);
/*  948 */     Long timepermit = onlinelimit.getTime();
/*  949 */     Long costTime = Long.valueOf(0L);
/*  950 */     int haveTime = getHaveTime();
/*  951 */     int toHaveTime = haveTime;
/*  952 */     Long oldcostTime = Long.valueOf(0L);
/*  953 */     Boolean notLimit = Boolean.valueOf(true);
/*  954 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/*  955 */       String[] macTimeInfo = 
/*  956 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/*  956 */         .get(mac);
/*  957 */       if (macTimeInfo != null) {
/*  958 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*      */       }
/*  960 */       if (onlinelimit.getState().intValue() == 1) {
/*  961 */         Date now = new Date();
/*  962 */         String nowString = ThreadLocalDateUtil.format(now);
/*  963 */         if (macTimeInfo == null) {
/*  964 */           macTimeInfo = new String[3];
/*  965 */           macTimeInfo[1] = "0";
/*      */         }
/*  967 */         macTimeInfo[0] = nowString;
/*  968 */         macTimeInfo[2] = String.valueOf(id);
/*  969 */         MacLimitMap.getInstance().getMacLimitMap()
/*  970 */           .put(mac, macTimeInfo);
/*  971 */         costTime = oldcostTime;
/*  972 */         haveTime = (int)(timepermit.longValue() / 60000L);
/*  973 */         notLimit = Boolean.valueOf(false);
/*      */ 
/*  975 */         if (id.longValue() == 1L) {
/*  976 */           String[] TimeInfo = 
/*  977 */             (String[])UserLimitMap.getInstance()
/*  977 */             .getUserLimitMap().get(phone);
/*  978 */           if (TimeInfo == null) {
/*  979 */             TimeInfo = new String[3];
/*  980 */             TimeInfo[1] = "0";
/*      */           }
/*  982 */           TimeInfo[0] = nowString;
/*  983 */           TimeInfo[2] = String.valueOf(id);
/*  984 */           UserLimitMap.getInstance().getUserLimitMap()
/*  985 */             .put(phone, TimeInfo);
/*  986 */           if (onlinelimit.getType().intValue() == 1) {
/*  987 */             costTime = Long.valueOf(TimeInfo[1]);
/*  988 */             haveTime = (int)(timepermit.longValue() / 60000L);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  993 */       Cookie cookieMac = new Cookie("mac", mac);
/*  994 */       cookieMac.setMaxAge(86400);
/*  995 */       response.addCookie(cookieMac);
/*  996 */       session.setAttribute("ikmac", mac);
/*      */     }
/*  998 */     int overTime = (int)(costTime.longValue() / 60000L);
/*  999 */     haveTime -= overTime;
/* 1000 */     if (haveTime > toHaveTime) {
/* 1001 */       haveTime = toHaveTime;
/*      */     }
/* 1003 */     if (haveTime < 0) {
/* 1004 */       haveTime = 0;
/*      */     }
/* 1006 */     if (notLimit.booleanValue()) {
/* 1007 */       overTime += (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/* 1009 */     String haveTimeString = String.valueOf(haveTime);
/* 1010 */     String overTimeString = String.valueOf(overTime);
/* 1011 */     session.setAttribute("haveTime", haveTimeString);
/* 1012 */     session.setAttribute("overTime", overTimeString);
/*      */ 
/* 1014 */     Boolean isAuto = Boolean.valueOf(false);
/* 1015 */     if (isAuto.booleanValue())
/*      */     {
/* 1017 */       if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 1018 */         String[] userinfo = new String[3];
/* 1019 */         userinfo[0] = authUser;
/* 1020 */         userinfo[1] = authPwd;
/* 1021 */         userinfo[2] = phone;
/* 1022 */         AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response)
/*      */   {
/* 1034 */     Portalonlinelimit onlinelimit = onlinelimitService
/* 1035 */       .getPortalonlinelimitByKey(id);
/* 1036 */     Long timepermit = onlinelimit.getTime();
/* 1037 */     Long costTime = Long.valueOf(0L);
/* 1038 */     int haveTime = getHaveTime();
/* 1039 */     int toHaveTime = haveTime;
/* 1040 */     Long oldcostTime = Long.valueOf(0L);
/* 1041 */     Boolean notLimit = Boolean.valueOf(true);
/* 1042 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 1043 */       String[] macTimeInfo = 
/* 1044 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 1044 */         .get(mac);
/* 1045 */       if (macTimeInfo != null) {
/* 1046 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*      */       }
/* 1048 */       if ((onlinelimit.getState().intValue() == 1) && 
/* 1049 */         (onlinelimit.getType().intValue() == 0)) {
/* 1050 */         Date now = new Date();
/* 1051 */         String nowString = ThreadLocalDateUtil.format(now);
/* 1052 */         if (macTimeInfo == null) {
/* 1053 */           macTimeInfo = new String[3];
/* 1054 */           macTimeInfo[1] = "0";
/*      */         }
/* 1056 */         macTimeInfo[0] = nowString;
/* 1057 */         macTimeInfo[2] = String.valueOf(id);
/* 1058 */         MacLimitMap.getInstance().getMacLimitMap()
/* 1059 */           .put(mac, macTimeInfo);
/* 1060 */         costTime = oldcostTime;
/* 1061 */         haveTime = (int)(timepermit.longValue() / 60000L);
/* 1062 */         notLimit = Boolean.valueOf(false);
/*      */       }
/*      */ 
/* 1066 */       Cookie cookieMac = new Cookie("mac", mac);
/* 1067 */       cookieMac.setMaxAge(86400);
/* 1068 */       response.addCookie(cookieMac);
/* 1069 */       session.setAttribute("ikmac", mac);
/*      */     }
/* 1071 */     int overTime = (int)(costTime.longValue() / 60000L);
/* 1072 */     haveTime -= overTime;
/* 1073 */     if (haveTime > toHaveTime) {
/* 1074 */       haveTime = toHaveTime;
/*      */     }
/* 1076 */     if (haveTime < 0) {
/* 1077 */       haveTime = 0;
/*      */     }
/* 1079 */     if (notLimit.booleanValue()) {
/* 1080 */       overTime += (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/* 1082 */     String haveTimeString = String.valueOf(haveTime);
/* 1083 */     String overTimeString = String.valueOf(overTime);
/* 1084 */     session.setAttribute("haveTime", haveTimeString);
/* 1085 */     session.setAttribute("overTime", overTimeString);
/*      */   }
/*      */ 
/*      */   public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response, String username)
/*      */   {
/* 1096 */     Portalonlinelimit onlinelimit = onlinelimitService
/* 1097 */       .getPortalonlinelimitByKey(id);
/* 1098 */     Long timepermit = onlinelimit.getTime();
/* 1099 */     Long costTime = Long.valueOf(0L);
/* 1100 */     int haveTime = (int)(getTime(username).longValue() / 60000L);
/* 1101 */     int toHaveTime = haveTime;
/* 1102 */     Long oldcostTime = Long.valueOf(0L);
/* 1103 */     Boolean notLimit = Boolean.valueOf(true);
/* 1104 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 1105 */       String[] macTimeInfo = 
/* 1106 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 1106 */         .get(mac);
/* 1107 */       if (macTimeInfo != null) {
/* 1108 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*      */       }
/* 1110 */       if ((onlinelimit.getState().intValue() == 1) && 
/* 1111 */         (onlinelimit.getType().intValue() == 0)) {
/* 1112 */         Date now = new Date();
/* 1113 */         String nowString = ThreadLocalDateUtil.format(now);
/* 1114 */         if (macTimeInfo == null) {
/* 1115 */           macTimeInfo = new String[3];
/* 1116 */           macTimeInfo[1] = "0";
/*      */         }
/* 1118 */         macTimeInfo[0] = nowString;
/* 1119 */         macTimeInfo[2] = String.valueOf(id);
/* 1120 */         MacLimitMap.getInstance().getMacLimitMap()
/* 1121 */           .put(mac, macTimeInfo);
/* 1122 */         costTime = oldcostTime;
/* 1123 */         haveTime = (int)(timepermit.longValue() / 60000L);
/* 1124 */         notLimit = Boolean.valueOf(false);
/*      */       }
/*      */ 
/* 1128 */       Cookie cookieMac = new Cookie("mac", mac);
/* 1129 */       cookieMac.setMaxAge(86400);
/* 1130 */       response.addCookie(cookieMac);
/* 1131 */       session.setAttribute("ikmac", mac);
/*      */     }
/* 1133 */     int overTime = (int)(costTime.longValue() / 60000L);
/* 1134 */     haveTime -= overTime;
/* 1135 */     if (haveTime > toHaveTime) {
/* 1136 */       haveTime = toHaveTime;
/*      */     }
/* 1138 */     if (haveTime < 0) {
/* 1139 */       haveTime = 0;
/*      */     }
/* 1141 */     if (notLimit.booleanValue()) {
/* 1142 */       overTime += (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/* 1144 */     String haveTimeString = String.valueOf(haveTime);
/* 1145 */     String overTimeString = String.valueOf(overTime);
/* 1146 */     session.setAttribute("haveTime", haveTimeString);
/* 1147 */     session.setAttribute("overTime", overTimeString);
/*      */   }
/*      */ 
/*      */   public static Long getTime(String username)
/*      */   {
/* 1157 */     PortalaccountQuery query = new PortalaccountQuery();
/* 1158 */     query.setLoginName(username);
/* 1159 */     query.setLoginNameLike(false);
/* 1160 */     Portalaccount a = (Portalaccount)accountService.getPortalaccountList(query).get(0);
/* 1161 */     String userState = a.getState();
/* 1162 */     Date userDate = a.getDate();
/* 1163 */     Long userTime = a.getTime();
/*      */ 
/* 1165 */     if (userState.equals(String.valueOf(0))) {
/* 1166 */       return Long.valueOf(0L);
/*      */     }
/*      */ 
/* 1169 */     if (userState.equals(String.valueOf(1))) {
/* 1170 */       return Long.valueOf(86400000L);
/*      */     }
/*      */ 
/* 1173 */     if (userState.equals(String.valueOf(3))) {
/* 1174 */       Date now = new Date();
/* 1175 */       if (userDate.getTime() > now.getTime()) {
/* 1176 */         return Long.valueOf(userDate.getTime() - now.getTime());
/*      */       }
/* 1178 */       return Long.valueOf(0L);
/*      */     }
/*      */ 
/* 1182 */     if (userState.equals("2")) {
/* 1183 */       if (userTime.longValue() > 0L) {
/* 1184 */         return userTime;
/*      */       }
/* 1186 */       return Long.valueOf(0L);
/*      */     }
/*      */ 
/* 1189 */     return Long.valueOf(0L);
/*      */   }
/*      */ 
/*      */   public static int getHaveTime()
/*      */   {
/*      */     try
/*      */     {
/* 1199 */       Date nowdate = new Date();
/* 1200 */       Calendar calendar = new GregorianCalendar();
/* 1201 */       calendar.setTime(nowdate);
/* 1202 */       calendar.add(5, 1);
/* 1203 */       Date tdate = calendar.getTime();
/* 1204 */       SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/* 1205 */       String tsString = fs.format(tdate);
/* 1206 */       Date t = fs.parse(tsString);
/* 1207 */       return (int)((t.getTime() - nowdate.getTime()) / 60000L); } catch (Exception e) {
/*      */     }
/* 1209 */     return 1440;
/*      */   }
/*      */ 
/*      */   public static void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
/*      */   {
/* 1220 */     Portalautologin autologin = autologinService
/* 1221 */       .getPortalautologinByKey(id);
/* 1222 */     if ((autologin != null) && (autologin.getState().intValue() == 1) && 
/* 1223 */       (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 1224 */       String[] macTimeInfo = 
/* 1225 */         (String[])AutoLoginMacMap.getInstance()
/* 1225 */         .getAutoLoginMacMap().get(mac);
/* 1226 */       Date now = new Date();
/* 1227 */       String nowString = ThreadLocalDateUtil.format(now);
/* 1228 */       if (macTimeInfo == null) {
/* 1229 */         macTimeInfo = new String[3];
/* 1230 */         macTimeInfo[1] = "0";
/*      */       }
/* 1232 */       macTimeInfo[0] = nowString;
/* 1233 */       macTimeInfo[2] = String.valueOf(id);
/* 1234 */       AutoLoginMacMap.getInstance().getAutoLoginMacMap()
/* 1235 */         .put(mac, macTimeInfo);
/*      */ 
/* 1237 */       String[] userinfo = new String[3];
/* 1238 */       userinfo[0] = authUser;
/* 1239 */       userinfo[1] = authPwd;
/* 1240 */       userinfo[2] = username;
/* 1241 */       AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.utils.Tools
 * JD-Core Version:    0.6.2
 */