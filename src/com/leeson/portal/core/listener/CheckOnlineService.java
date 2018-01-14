/*     */ package com.leeson.portal.core.listener;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Historyonline;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalbasauth;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.bean.Portalweixinwifi;
/*     */ import com.leeson.core.query.HistoryonlineQuery;
/*     */ import com.leeson.core.query.PortalbasauthQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.HistoryonlineService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalbasauthService;
/*     */ import com.leeson.core.service.PortallinkrecordService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.core.service.PortalweixinwifiService;
/*     */ import com.leeson.core.utils.CheckAutoLoginUtils;
/*     */ import com.leeson.core.utils.CheckTimeUtils;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.core.utils.WifidogKick;
/*     */ import com.leeson.portal.core.model.LateAuthMap;
/*     */ import com.leeson.portal.core.model.MagicMap;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.OpenIdMap;
/*     */ import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
/*     */ import com.leeson.portal.core.model.WeixinMap;
/*     */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*     */ import com.leeson.portal.core.model.iKuaiMacIpMap;
/*     */ import com.leeson.portal.core.model.isDo;
/*     */ import com.leeson.portal.core.utils.HttpRequest;
/*     */ import com.leeson.portal.core.utils.KickAll;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import com.leeson.radius.core.RadiusCOA;
/*     */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CheckOnlineService
/*     */   implements ServletContextListener
/*     */ {
/*  67 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*     */ 
/*  69 */   private static Logger log = Logger.getLogger(CheckOnlineService.class);
/*     */   private PortallinkrecordService linkRecordService;
/*     */   private PortalaccountService accountService;
/*     */   private PortallogrecordService logRecordService;
/*     */   private PortalweixinwifiService weixinwifiService;
/*     */   private PortalbasauthService basauthService;
/*     */   private HistoryonlineService historyonlineService;
/*  77 */   private Long weixinTime = Long.valueOf(10L);
/*  78 */   private Long checkTime = Long.valueOf(10L);
/*     */   private String path;
/*     */   private static ConfigService configService;
/*  82 */   private static Timer timerRecordOnlineCount = new Timer();
/*  83 */   private static Timer timerweixinCheck = new Timer();
/*  84 */   private static Timer timerstartCheck = new Timer();
/*     */ 
/*     */   public void contextDestroyed(ServletContextEvent servletContextEvent)
/*     */   {
/*  88 */     timerRecordOnlineCount.cancel();
/*  89 */     timerweixinCheck.cancel();
/*  90 */     timerstartCheck.cancel();
/*     */ 
/*  92 */     configService = (ConfigService)
/*  93 */       SpringContextHelper.getBean("configServiceImpl");
/*  94 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  95 */     if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue()) {
/*  96 */       if (basConfig.getIsdebug().equals("1")) {
/*  97 */         log.info("CheckOnlineService Will Stop , Ready Offline All User...");
/*     */       }
/*  99 */       this.linkRecordService = ((PortallinkrecordService)
/* 100 */         SpringContextHelper.getBean("portallinkrecordServiceImpl"));
/* 101 */       this.accountService = ((PortalaccountService)
/* 102 */         SpringContextHelper.getBean("portalaccountServiceImpl"));
/* 103 */       this.logRecordService = ((PortallogrecordService)
/* 104 */         SpringContextHelper.getBean("portallogrecordServiceImpl"));
/*     */ 
/* 106 */       Iterator iterator = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/* 107 */       while (iterator.hasNext()) {
/* 108 */         Object o = iterator.next();
/* 109 */         String t = o.toString();
/*     */ 
/* 112 */         int i = t.lastIndexOf(":");
/* 113 */         String ubip = t.substring(i + 1);
/*     */ 
/* 115 */         String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
/* 116 */         Kick.doLinkAll(loginInfo, "服务器关闭");
/* 117 */         String username = loginInfo[0];
/* 118 */         String mac = loginInfo[4];
/* 119 */         String type = loginInfo[6];
/* 120 */         Portalbas basconfig = (Portalbas)config.getConfigMap().get(ubip);
/* 121 */         if ((basconfig != null) && 
/* 122 */           ("1".equals(type)) && 
/* 123 */           ("1".equals(basconfig.getIsPortalCheck()))) {
/* 124 */           doLinkRecord(loginInfo, "服务器关闭");
/*     */         }
/*     */         try
/*     */         {
/* 128 */           String time = loginInfo[3];
/* 129 */           Date loginTime = ThreadLocalDateUtil.parse(time);
/* 130 */           String nowString = ThreadLocalDateUtil.format(new Date());
/* 131 */           Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 132 */           Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 133 */           costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/*     */           try
/*     */           {
/* 136 */             doLogRecord("IP: " + t + " mac: " + mac + " 用户: " + 
/* 137 */               username + " 上线时间: " + time + " 在线时长: " + 
/* 138 */               costTime + "分钟,服务器关闭！");
/*     */           }
/*     */           catch (Exception localException)
/*     */           {
/*     */           }
/* 143 */           if (basConfig.getIsdebug().equals("1")) {
/* 144 */             log.info("IP: " + t + " mac: " + mac + " user: " + 
/* 145 */               username + 
/* 146 */               " , Portal Kick By Server Shutdown!");
/*     */           }
/*     */         }
/*     */         catch (Exception localException1)
/*     */         {
/*     */         }
/* 152 */         CheckTimeUtils.recordTime(loginInfo);
/* 153 */         CheckAutoLoginUtils.recordTime(loginInfo);
/*     */ 
/* 156 */         KickAll.kickUser(t);
/* 157 */         if (stringUtils.isNotBlank(mac))
/* 158 */           LateAuthMap.getInstance().getLateAuthMap().remove(mac);
/*     */       }
/*     */     }
/*     */     else {
/* 162 */       WeixinMapToDisk(servletContextEvent);
/*     */     }
/* 164 */     OnlineUserMapToDisk(servletContextEvent);
/*     */ 
/* 167 */     ikuaiMacIpMapToDisk(servletContextEvent);
/*     */ 
/* 170 */     if (basConfig.getIsdebug().equals("1")) {
/* 171 */       log.info("CheckOnlineService Stoped!!");
/*     */     }
/* 173 */     com.leeson.core.utils.kickAllThread.offThread();
/* 174 */     com.leeson.portal.core.utils.kickAllThread.offThread();
/*     */   }
/*     */ 
/*     */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*     */   {
/* 179 */     configService = (ConfigService)
/* 180 */       SpringContextHelper.getBean("configServiceImpl");
/* 181 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 182 */     this.linkRecordService = ((PortallinkrecordService)
/* 183 */       SpringContextHelper.getBean("portallinkrecordServiceImpl"));
/* 184 */     this.accountService = ((PortalaccountService)
/* 185 */       SpringContextHelper.getBean("portalaccountServiceImpl"));
/* 186 */     this.logRecordService = ((PortallogrecordService)
/* 187 */       SpringContextHelper.getBean("portallogrecordServiceImpl"));
/* 188 */     this.weixinwifiService = ((PortalweixinwifiService)
/* 189 */       SpringContextHelper.getBean("portalweixinwifiServiceImpl"));
/* 190 */     this.weixinTime = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L))
/* 191 */       .getOutTime();
/* 192 */     this.basauthService = ((PortalbasauthService)
/* 193 */       SpringContextHelper.getBean("portalbasauthServiceImpl"));
/* 194 */     this.historyonlineService = ((HistoryonlineService)
/* 195 */       SpringContextHelper.getBean("historyonlineServiceImpl"));
/* 196 */     this.checkTime = configService.getConfigByKey(Long.valueOf(1L)).getCheckTime();
/*     */ 
/* 198 */     if (1 != configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue()) {
/* 199 */       OnlineUserMapFromDisk(servletContextEvent);
/* 200 */       WeixinMapFromDisk(servletContextEvent);
/*     */     }
/*     */ 
/* 204 */     ikuaiMacIpMapFromDisk(servletContextEvent);
/*     */ 
/* 207 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 212 */           CheckOnlineService.this.startCheck();
/*     */         }
/*     */         catch (Exception e) {
/* 215 */           CheckOnlineService.log.error("CheckOnlineService Start ERROR!!");
/* 216 */           CheckOnlineService.log.error("==============ERROR Start=============");
/* 217 */           CheckOnlineService.log.error(e);
/* 218 */           CheckOnlineService.log.error("ERROR INFO ", e);
/* 219 */           CheckOnlineService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/* 223 */     .start();
/*     */ 
/* 225 */     if (basConfig.getIsdebug().equals("1")) {
/* 226 */       log.info("CheckOnlineService Start Success!! 20s Later Start , Every " + 
/* 227 */         this.checkTime + "s Check Once !!");
/*     */     }
/*     */ 
/* 230 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 235 */           CheckOnlineService.this.weixinCheck();
/*     */         }
/*     */         catch (Exception e) {
/* 238 */           CheckOnlineService.log.error("CheckWeiXinAuthTempService Start ERROR!!");
/* 239 */           CheckOnlineService.log.error("==============ERROR Start=============");
/* 240 */           CheckOnlineService.log.error(e);
/* 241 */           CheckOnlineService.log.error("ERROR INFO ", e);
/* 242 */           CheckOnlineService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/* 246 */     .start();
/*     */ 
/* 248 */     if (basConfig.getIsdebug().equals("1"))
/*     */     {
/* 250 */       log.info("CheckWeiXinAuthTempService Start Success!! 20s Later Start , Every " + 
/* 251 */         this.weixinTime + "s Check Once !!");
/*     */     }
/*     */ 
/* 254 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 259 */           CheckOnlineService.this.RecordOnlineCount();
/* 260 */           CheckOnlineService.log.info("Start Record Online Count Service !!");
/*     */         } catch (Exception e) {
/* 262 */           CheckOnlineService.log.error("Do Record Online Count Service Start ERROR!!");
/* 263 */           CheckOnlineService.log.error("==============ERROR Start=============");
/* 264 */           CheckOnlineService.log.error(e);
/* 265 */           CheckOnlineService.log.error("ERROR INFO ", e);
/* 266 */           CheckOnlineService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/* 270 */     .start();
/*     */ 
/* 272 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 277 */           HttpRequest.send();
/*     */         }
/*     */         catch (Exception e) {
/* 280 */           CheckOnlineService.log.error("==============ERROR Start=============");
/* 281 */           CheckOnlineService.log.error(e);
/* 282 */           CheckOnlineService.log.error("ERROR INFO ", e);
/* 283 */           CheckOnlineService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/* 287 */     .start();
/*     */ 
/* 289 */     if (basConfig.getIsdebug().equals("1")) {
/* 290 */       log.info("Check Server Info !!");
/*     */     }
/*     */ 
/* 293 */     this.path = servletContextEvent.getServletContext().getRealPath("/");
/* 294 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 299 */           CheckOnlineService.this.deleteFiles(CheckOnlineService.this.path);
/*     */         }
/*     */         catch (Exception e) {
/* 302 */           CheckOnlineService.log.error("==============ERROR Start=============");
/* 303 */           CheckOnlineService.log.error(e);
/* 304 */           CheckOnlineService.log.error("ERROR INFO ", e);
/* 305 */           CheckOnlineService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/* 309 */     .start();
/*     */   }
/*     */ 
/*     */   private void startCheck()
/*     */   {
/* 314 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/* 317 */         Portalbas basConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get("");
/* 318 */         if (basConfig.getIsdebug().equals("1")) {
/* 319 */           CheckOnlineService.log.info("Start Check Portal Online User List , Check Have Time !!");
/*     */         }
/*     */ 
/* 322 */         Iterator iteratorWifidog = WifiDogOnlineMap.getInstance()
/* 323 */           .getWifiDogOnlineMap()
/* 324 */           .keySet().iterator();
/* 325 */         while (iteratorWifidog.hasNext()) {
/* 326 */           Object o = iteratorWifidog.next();
/* 327 */           String t = o.toString();
/* 328 */           String[] loginInfo = 
/* 329 */             (String[])WifiDogOnlineMap.getInstance()
/* 329 */             .getWifiDogOnlineMap().get(t);
/* 330 */           String username = loginInfo[0];
/* 331 */           if (basConfig.getIsdebug().equals("1")) {
/* 332 */             CheckOnlineService.log.info("Check Online User , host: " + loginInfo[4] + 
/* 333 */               " user: " + username);
/*     */           }
/* 335 */           new CheckOnlineWifidog(t, username, loginInfo).start();
/*     */         }
/*     */ 
/* 338 */         if (CheckOnlineService.Do()) {
/*     */           try
/*     */           {
/* 341 */             Iterator iteratorOnlineUserSessionTimeCheck = 
/* 342 */               OnlineMap.getInstance().getOnlineUserMap()
/* 343 */               .keySet().iterator();
/* 344 */             while (iteratorOnlineUserSessionTimeCheck.hasNext()) {
/* 345 */               Object oOnlineUserSessionTimeCheck = iteratorOnlineUserSessionTimeCheck
/* 346 */                 .next();
/* 347 */               String tOnlineUserSessionTimeCheck = oOnlineUserSessionTimeCheck
/* 348 */                 .toString();
/*     */ 
/* 352 */               int i = tOnlineUserSessionTimeCheck.lastIndexOf(":");
/* 353 */               String basip = tOnlineUserSessionTimeCheck.substring(i + 1);
/*     */ 
/* 355 */               String[] loginInfo = 
/* 357 */                 (String[])OnlineMap.getInstance().getOnlineUserMap()
/* 357 */                 .get(tOnlineUserSessionTimeCheck);
/* 358 */               if ((loginInfo != null) && (loginInfo.length > 0)) {
/* 359 */                 Portalbas bas = 
/* 360 */                   (Portalbas)CheckOnlineService.config.getConfigMap()
/* 360 */                   .get(basip);
/* 361 */                 if (bas != null) {
/* 362 */                   String type = loginInfo[6];
/* 363 */                   PortalbasauthQuery q = new PortalbasauthQuery();
/* 364 */                   q.setBasip(basip);
/* 365 */                   q.setBasipLike(false);
/* 366 */                   q.setType(Integer.valueOf(type));
/* 367 */                   List basauths = CheckOnlineService.this.basauthService
/* 368 */                     .getPortalbasauthList(q);
/* 369 */                   if ((basauths != null) && (basauths.size() > 0)) {
/* 370 */                     String username = loginInfo[0];
/* 371 */                     if (basConfig.getIsdebug().equals("1")) {
/* 372 */                       CheckOnlineService.log.info("SessionTime Check  host: " + 
/* 373 */                         tOnlineUserSessionTimeCheck + 
/* 374 */                         " LoginName: " + username);
/*     */                     }
/* 376 */                     Portalbasauth basauth = (Portalbasauth)basauths.get(0);
/* 377 */                     Long sessionTime = basauth
/* 378 */                       .getSessiontime();
/* 379 */                     if ((sessionTime != null) && 
/* 380 */                       (sessionTime.longValue() > 0L)) {
/* 381 */                       String time = loginInfo[3];
/* 382 */                       Date loginTime = ThreadLocalDateUtil.parse(time);
/* 383 */                       String nowString = 
/* 384 */                         ThreadLocalDateUtil.format(new Date());
/* 385 */                       Date nowTime = 
/* 386 */                         ThreadLocalDateUtil.parse(nowString);
/* 387 */                       Long costTime = Long.valueOf(nowTime.getTime() - 
/* 388 */                         loginTime.getTime());
/* 389 */                       if (costTime.longValue() >= sessionTime.longValue()) {
/* 390 */                         Kick.kickUserSessionTimeOut(tOnlineUserSessionTimeCheck);
/*     */                       }
/*     */ 
/*     */                     }
/*     */ 
/*     */                   }
/*     */ 
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 429 */             CheckOnlineService.log.error("==============SessionTimeCheck ERROR Start=============");
/* 430 */             CheckOnlineService.log.error(e);
/* 431 */             CheckOnlineService.log.error("ERROR INFO ", e);
/* 432 */             CheckOnlineService.log.error("==============SessionTimeCheck ERROR End=============");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 438 */         Iterator iterator = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/* 439 */         while (iterator.hasNext()) {
/* 440 */           Object o = iterator.next();
/* 441 */           String t = o.toString();
/* 442 */           String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
/* 443 */           String username = loginInfo[0];
/*     */ 
/* 446 */           int i = t.lastIndexOf(":");
/* 447 */           String basip = t.substring(i + 1);
/*     */ 
/* 449 */           Portalbas userConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get(basip);
/* 450 */           if ((userConfig != null) && ("1".equals(loginInfo[6])) && ("1".equals(userConfig.getIsPortalCheck()))) {
/* 451 */             if (basConfig.getIsdebug().equals("1")) {
/* 452 */               CheckOnlineService.log.info("Check Online User , host: " + t + 
/* 453 */                 " user: " + username);
/*     */             }
/* 455 */             new CheckOnline(t, username, loginInfo).start();
/*     */           }
/*     */         }
/*     */       }
/*     */     };
/* 462 */     long delay = 20000L;
/* 463 */     long intevalPeriod = this.checkTime.longValue() * 1000L;
/*     */ 
/* 465 */     timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
/*     */   }
/*     */ 
/*     */   private void weixinCheck() {
/* 469 */     TimerTask taskweixin = new TimerTask()
/*     */     {
/*     */       public void run() {
/* 472 */         Portalbas basConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get("");
/* 473 */         if (basConfig.getIsdebug().equals("1")) {
/* 474 */           CheckOnlineService.log.info("Start Check WeiXin Auth Temp List!!");
/*     */         }
/*     */ 
/* 478 */         Iterator it = WeixinMap.getInstance().getWeixinipmap().keySet().iterator();
/* 479 */         Long nowTime = Long.valueOf(new Date().getTime());
/* 480 */         while (it.hasNext()) {
/* 481 */           Object o = it.next();
/* 482 */           String host = o.toString();
/* 483 */           if (basConfig.getIsdebug().equals("1")) {
/* 484 */             CheckOnlineService.log.info("Check WeiXin Auth Temp , host: " + host);
/*     */           }
/* 486 */           Long startTime = Long.valueOf(((Date)WeixinMap.getInstance().getWeixinipmap().get(host)).getTime());
/*     */ 
/* 488 */           Long outTime = Long.valueOf(CheckOnlineService.this.weixinTime.longValue() * 1000L);
/* 489 */           if (nowTime.longValue() - startTime.longValue() >= outTime.longValue()) {
/* 490 */             if (host.startsWith("wifidog")) {
/* 491 */               WeixinMap.getInstance().getWeixinipmap()
/* 492 */                 .remove(host);
/* 493 */               host = host.replace("wifidog", "");
/* 494 */               WifidogKick.WeixinOffLine(host);
/*     */             } else {
/* 496 */               Kick.WeixinOffLine(host);
/* 497 */               WeixinMap.getInstance().getWeixinipmap()
/* 498 */                 .remove(host);
/* 499 */               MagicMap.getInstance().getMagicMap().remove(host);
/*     */               try
/*     */               {
/* 503 */                 Iterator itWisprMap = WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().keySet()
/* 504 */                   .iterator();
/* 505 */                 while (itWisprMap.hasNext()) {
/* 506 */                   Object oWispr = itWisprMap.next();
/* 507 */                   String hostWispr = oWispr.toString();
/* 508 */                   if (host.equals(hostWispr)) {
/* 509 */                     WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(hostWispr);
/*     */ 
/* 512 */                     int i = host.lastIndexOf(":");
/* 513 */                     String uip = host.substring(0, i);
/*     */ 
/* 515 */                     Iterator iterator = RadiusOnlineMap.getInstance()
/* 516 */                       .getRadiusOnlineMap().keySet().iterator();
/* 517 */                     while (iterator.hasNext()) {
/* 518 */                       Object oRadius = iterator.next();
/* 519 */                       String tRadius = oRadius.toString();
/* 520 */                       String[] radiusOnlineInfo = 
/* 521 */                         (String[])RadiusOnlineMap.getInstance()
/* 521 */                         .getRadiusOnlineMap().get(tRadius);
/* 522 */                       if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 523 */                         (uip.equals(radiusOnlineInfo[2]))) {
/* 524 */                         RadiusCOA.req_COA(radiusOnlineInfo, "Radius Weixin Kick COA");
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */ 
/*     */               }
/*     */               catch (Exception localException)
/*     */               {
/*     */               }
/*     */ 
/* 535 */               Iterator itopenidMap = OpenIdMap.getInstance()
/* 536 */                 .getOpenIdMap().keySet()
/* 537 */                 .iterator();
/* 538 */               while (itopenidMap.hasNext()) {
/* 539 */                 Object oopenidMap = itopenidMap.next();
/* 540 */                 String openid = oopenidMap.toString();
/* 541 */                 String[] hosts = 
/* 542 */                   (String[])OpenIdMap.getInstance()
/* 542 */                   .getOpenIdMap().get(openid);
/* 543 */                 if ((hosts != null) && (hosts.length > 0)) {
/* 544 */                   String hostopenid = hosts[0];
/* 545 */                   if (host.equals(hostopenid)) {
/* 546 */                     OpenIdMap.getInstance().getOpenIdMap()
/* 547 */                       .remove(openid);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 552 */             if (basConfig.getIsdebug().equals("1"))
/* 553 */               CheckOnlineService.log.info(host + " Can Not Finish WinXin Auth!!");
/*     */           }
/*     */         }
/*     */       }
/*     */     };
/* 564 */     long delay = 20000L;
/* 565 */     long intevalPeriod = this.weixinTime.longValue() * 500L;
/*     */ 
/* 567 */     timerweixinCheck.scheduleAtFixedRate(taskweixin, delay, intevalPeriod);
/*     */   }
/*     */ 
/*     */   private void doLinkRecord(String[] loginInfo, String info)
/*     */   {
/*     */     try
/*     */     {
/* 579 */       String uid = loginInfo[1];
/* 580 */       String rid = loginInfo[2];
/* 581 */       if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
/* 582 */         return;
/*     */       }
/* 584 */       Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
/* 585 */       Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
/* 586 */       if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || 
/* 587 */         (recordId.longValue() == 0L)) {
/* 588 */         return;
/*     */       }
/* 590 */       Portallinkrecord linkRecord = this.linkRecordService
/* 591 */         .getPortallinkrecordByKey(recordId);
/* 592 */       Portalaccount account = this.accountService
/* 593 */         .getPortalaccountByKey(userId);
/* 594 */       String state = account.getState();
/*     */ 
/* 596 */       long in = Long.valueOf(loginInfo[7]).longValue();
/* 597 */       long out = Long.valueOf(loginInfo[8]).longValue();
/* 598 */       long octets = in + out;
/* 599 */       linkRecord.setIns(Long.valueOf(in));
/* 600 */       linkRecord.setOuts(Long.valueOf(out));
/* 601 */       linkRecord.setOctets(Long.valueOf(octets));
/* 602 */       Date now = new Date();
/* 603 */       linkRecord.setEndDate(now);
/* 604 */       Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
/* 605 */       linkRecord.setTime(costTime);
/* 606 */       linkRecord.setState(state);
/* 607 */       linkRecord.setEx1("Portal");
/* 608 */       linkRecord.setEx2(info);
/* 609 */       this.linkRecordService.updatePortallinkrecordByKey(linkRecord);
/*     */ 
/* 611 */       if (!state.equals("1")) {
/* 612 */         Long haveTime = account.getTime();
/* 613 */         Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
/* 614 */         if ((state.equals("3")) && 
/* 615 */           (account.getDate().getTime() <= now.getTime())) {
/* 616 */           account.setState("2");
/* 617 */           this.accountService.updatePortalaccountByKey(account);
/*     */         }
/*     */ 
/* 620 */         if (state.equals("2")) {
/* 621 */           if (newHaveTime.longValue() <= 0L) {
/* 622 */             account.setState("4");
/*     */           }
/* 624 */           account.setTime(newHaveTime);
/* 625 */           this.accountService.updatePortalaccountByKey(account);
/*     */         }
/* 627 */         if ((state.equals("4")) || (state.equals("0"))) {
/* 628 */           long haveOctets = account.getOctets().longValue() - octets;
/* 629 */           if (haveOctets <= 0L) {
/* 630 */             account.setState("0");
/*     */           }
/* 632 */           account.setOctets(Long.valueOf(haveOctets));
/* 633 */           this.accountService.updatePortalaccountByKey(account);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doLogRecord(String info) {
/*     */     try {
/* 644 */       Portallogrecord logRecord = new Portallogrecord();
/* 645 */       Date nowDate = new Date();
/* 646 */       logRecord.setInfo(info);
/* 647 */       logRecord.setRecDate(nowDate);
/* 648 */       this.logRecordService.addPortallogrecord(logRecord);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void ikuaiMacIpMapToDisk(ServletContextEvent servletContextEvent) {
/* 656 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 657 */     ObjectOutputStream os = null;
/*     */     try {
/* 659 */       os = new ObjectOutputStream(new FileOutputStream(
/* 660 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 661 */         "/ikuaiMacIpMap.dat"));
/*     */ 
/* 665 */       os.writeObject(iKuaiMacIpMap.getInstance().getMacIpMap());
/*     */ 
/* 667 */       if (basConfig.getIsdebug().equals("1"))
/* 668 */         log.info("ikuaiMacIpMapToDisk!!");
/*     */     }
/*     */     catch (Exception e) {
/* 671 */       log.error("==============ERROR Start=============");
/* 672 */       log.error(e);
/* 673 */       log.error("ERROR INFO ", e);
/* 674 */       log.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 677 */         if (os != null)
/* 678 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 681 */         log.error("==============ERROR Start=============");
/* 682 */         log.error(e1);
/* 683 */         log.error("ERROR INFO ", e1);
/* 684 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 677 */         if (os != null)
/* 678 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 681 */         log.error("==============ERROR Start=============");
/* 682 */         log.error(e);
/* 683 */         log.error("ERROR INFO ", e);
/* 684 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void ikuaiMacIpMapFromDisk(ServletContextEvent servletContextEvent) {
/* 690 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 691 */     ObjectInputStream is = null;
/* 692 */     File parent = new File(servletContextEvent.getServletContext()
/* 693 */       .getRealPath("/") + "/ikuaiMacIpMap.dat");
/*     */ 
/* 695 */     label331: 
/*     */     try { if (parent.exists()) {
/* 696 */         is = new ObjectInputStream(new FileInputStream(
/* 697 */           servletContextEvent.getServletContext()
/* 698 */           .getRealPath("/") + "/ikuaiMacIpMap.dat"));
/* 699 */         HashMap ikuaiMacIpMap = (HashMap)is
/* 700 */           .readObject();
/*     */ 
/* 705 */         iKuaiMacIpMap.getInstance().setMacIpMap(ikuaiMacIpMap);
/*     */ 
/* 707 */         if (basConfig.getIsdebug().equals("1")) {
/* 708 */           log.info("ikuaiMacIpMapFromDisk!!");
/*     */ 
/* 711 */           break label331;
/*     */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 713 */         log.info("ikuaiMacIpMap File Not Exists!!");
/*     */       }
/*     */     } catch (Exception e)
/*     */     {
/* 717 */       log.error("==============ERROR Start=============");
/* 718 */       log.error(e);
/* 719 */       log.error("ERROR INFO ", e);
/* 720 */       log.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 723 */         if (is != null) {
/* 724 */           is.close();
/*     */         }
/* 726 */         parent = null;
/*     */       } catch (IOException e1) {
/* 728 */         log.error("==============ERROR Start=============");
/* 729 */         log.error(e1);
/* 730 */         log.error("ERROR INFO ", e1);
/* 731 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 723 */         if (is != null) {
/* 724 */           is.close();
/*     */         }
/* 726 */         parent = null;
/*     */       } catch (IOException e) {
/* 728 */         log.error("==============ERROR Start=============");
/* 729 */         log.error(e);
/* 730 */         log.error("ERROR INFO ", e);
/* 731 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void OnlineUserMapToDisk(ServletContextEvent servletContextEvent) {
/* 737 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 738 */     ObjectOutputStream os = null;
/*     */     try {
/* 740 */       os = new ObjectOutputStream(new FileOutputStream(
/* 741 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 742 */         "/OnlineUserMap.dat"));
/* 743 */       if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue())
/* 744 */         os.writeObject(new ConcurrentHashMap());
/*     */       else {
/* 746 */         os.writeObject(OnlineMap.getInstance().getOnlineUserMap());
/*     */       }
/* 748 */       if (basConfig.getIsdebug().equals("1"))
/* 749 */         log.info("OnlineUserMapToDisk!!");
/*     */     }
/*     */     catch (Exception e) {
/* 752 */       log.error("==============ERROR Start=============");
/* 753 */       log.error(e);
/* 754 */       log.error("ERROR INFO ", e);
/* 755 */       log.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 758 */         if (os != null)
/* 759 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 762 */         log.error("==============ERROR Start=============");
/* 763 */         log.error(e1);
/* 764 */         log.error("ERROR INFO ", e1);
/* 765 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 758 */         if (os != null)
/* 759 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 762 */         log.error("==============ERROR Start=============");
/* 763 */         log.error(e);
/* 764 */         log.error("ERROR INFO ", e);
/* 765 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void OnlineUserMapFromDisk(ServletContextEvent servletContextEvent) {
/* 771 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 772 */     ObjectInputStream is = null;
/* 773 */     File parent = new File(servletContextEvent.getServletContext()
/* 774 */       .getRealPath("/") + "/OnlineUserMap.dat");
/*     */ 
/* 776 */     label331: 
/*     */     try { if (parent.exists()) {
/* 777 */         is = new ObjectInputStream(new FileInputStream(
/* 778 */           servletContextEvent.getServletContext()
/* 779 */           .getRealPath("/") + "/OnlineUserMap.dat"));
/* 780 */         Map OnlineUserMap = (ConcurrentHashMap)is
/* 781 */           .readObject();
/* 782 */         OnlineMap.getInstance().setOnlineUserMap(OnlineUserMap);
/* 783 */         if (basConfig.getIsdebug().equals("1")) {
/* 784 */           log.info("OnlineUserMapFromDisk!!");
/*     */ 
/* 787 */           break label331;
/*     */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 789 */         log.info("OnlineUserMap File Not Exists!!");
/*     */       }
/*     */     } catch (Exception e)
/*     */     {
/* 793 */       log.error("==============ERROR Start=============");
/* 794 */       log.error(e);
/* 795 */       log.error("ERROR INFO ", e);
/* 796 */       log.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 799 */         if (is != null) {
/* 800 */           is.close();
/*     */         }
/* 802 */         parent = null;
/*     */       } catch (IOException e1) {
/* 804 */         log.error("==============ERROR Start=============");
/* 805 */         log.error(e1);
/* 806 */         log.error("ERROR INFO ", e1);
/* 807 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 799 */         if (is != null) {
/* 800 */           is.close();
/*     */         }
/* 802 */         parent = null;
/*     */       } catch (IOException e) {
/* 804 */         log.error("==============ERROR Start=============");
/* 805 */         log.error(e);
/* 806 */         log.error("ERROR INFO ", e);
/* 807 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void WeixinMapToDisk(ServletContextEvent servletContextEvent) {
/* 813 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 814 */     ObjectOutputStream os = null;
/*     */     try {
/* 816 */       os = new ObjectOutputStream(new FileOutputStream(
/* 817 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 818 */         "/WeixinMap.dat"));
/* 819 */       if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue())
/* 820 */         os.writeObject(new ConcurrentHashMap());
/*     */       else {
/* 822 */         os.writeObject(WeixinMap.getInstance().getWeixinipmap());
/*     */       }
/* 824 */       if (basConfig.getIsdebug().equals("1"))
/* 825 */         log.info("WeixinMapToDisk!!");
/*     */     }
/*     */     catch (Exception e) {
/* 828 */       log.error("==============ERROR Start=============");
/* 829 */       log.error(e);
/* 830 */       log.error("ERROR INFO ", e);
/* 831 */       log.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 834 */         if (os != null)
/* 835 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 838 */         log.error("==============ERROR Start=============");
/* 839 */         log.error(e1);
/* 840 */         log.error("ERROR INFO ", e1);
/* 841 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 834 */         if (os != null)
/* 835 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 838 */         log.error("==============ERROR Start=============");
/* 839 */         log.error(e);
/* 840 */         log.error("ERROR INFO ", e);
/* 841 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void WeixinMapFromDisk(ServletContextEvent servletContextEvent) {
/* 847 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 848 */     ObjectInputStream is = null;
/* 849 */     File parent = new File(servletContextEvent.getServletContext()
/* 850 */       .getRealPath("/") + "/WeixinMap.dat");
/*     */ 
/* 852 */     label331: 
/*     */     try { if (parent.exists()) {
/* 853 */         is = new ObjectInputStream(new FileInputStream(
/* 854 */           servletContextEvent.getServletContext()
/* 855 */           .getRealPath("/") + "/WeixinMap.dat"));
/* 856 */         Map weixinipmap = (ConcurrentHashMap)is
/* 857 */           .readObject();
/* 858 */         WeixinMap.getInstance().setWeixinipmap(weixinipmap);
/* 859 */         if (basConfig.getIsdebug().equals("1")) {
/* 860 */           log.info("WeixinMapFromDisk!!");
/*     */ 
/* 863 */           break label331;
/*     */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 865 */         log.info("WeixinMap File Not Exists!!");
/*     */       }
/*     */     } catch (Exception e)
/*     */     {
/* 869 */       log.error("==============ERROR Start=============");
/* 870 */       log.error(e);
/* 871 */       log.error("ERROR INFO ", e);
/* 872 */       log.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 875 */         if (is != null) {
/* 876 */           is.close();
/*     */         }
/* 878 */         parent = null;
/*     */       } catch (IOException e1) {
/* 880 */         log.error("==============ERROR Start=============");
/* 881 */         log.error(e1);
/* 882 */         log.error("ERROR INFO ", e1);
/* 883 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 875 */         if (is != null) {
/* 876 */           is.close();
/*     */         }
/* 878 */         parent = null;
/*     */       } catch (IOException e) {
/* 880 */         log.error("==============ERROR Start=============");
/* 881 */         log.error(e);
/* 882 */         log.error("ERROR INFO ", e);
/* 883 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deleteFiles(String path) {
/* 889 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 890 */     File file = new File(path + "/OnlineUserMap.dat");
/* 891 */     if (file.exists()) {
/* 892 */       file.delete();
/* 893 */       if (basConfig.getIsdebug().equals("1")) {
/* 894 */         log.info("Server Start Format OnlineUserMap File!!");
/*     */       }
/*     */     }
/* 897 */     file = null;
/* 898 */     file = new File(path + "/WeixinMap.dat");
/* 899 */     if (file.exists()) {
/* 900 */       file.delete();
/* 901 */       if (basConfig.getIsdebug().equals("1")) {
/* 902 */         log.info("Server Start Format WeixinMap File!!");
/*     */       }
/*     */     }
/* 905 */     file = null;
/*     */   }
/*     */ 
/*     */   public static boolean Do() {
/* 909 */     Long isThis = Long.valueOf(new Date().getTime());
/* 910 */     boolean Do = false;
/* 911 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 912 */       Do = true;
/*     */     }
/* 914 */     return Do;
/*     */   }
/*     */ 
/*     */   private void RecordOnlineCount() {
/* 918 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/*     */         try {
/* 922 */           Portalbas basConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get("");
/* 923 */           if (basConfig.getIsdebug().equals("1")) {
/* 924 */             CheckOnlineService.log.info("Do Record Online Count Service !!");
/*     */           }
/* 926 */           long counts = OnlineMap.getInstance().getOnlineUserMap().size();
/* 927 */           Date now = new Date();
/* 928 */           int year = now.getYear() + 1900;
/* 929 */           int month = now.getMonth() + 1;
/* 930 */           int day = now.getDate();
/* 931 */           int week = now.getDay();
/* 932 */           if (week == 0) {
/* 933 */             week = 7;
/*     */           }
/* 935 */           int hour = now.getHours();
/*     */ 
/* 937 */           if (basConfig.getIsdebug().equals("1")) {
/* 938 */             CheckOnlineService.log.info("Online Count=" + counts + " " + year + "-" + month + "-" + day + " week=" + week + " HH=" + hour);
/*     */           }
/*     */ 
/* 941 */           HistoryonlineQuery hq = new HistoryonlineQuery();
/* 942 */           hq.setRecDay(Integer.valueOf(day));
/* 943 */           hq.setRecMonth(Integer.valueOf(month));
/* 944 */           hq.setRecWeek(Integer.valueOf(week));
/* 945 */           hq.setRecYear(Integer.valueOf(year));
/* 946 */           hq.setRecTime(Integer.valueOf(hour));
/* 947 */           List hs = CheckOnlineService.this.historyonlineService.getHistoryonlineList(hq);
/* 948 */           if (hs.size() > 0) {
/* 949 */             Historyonline oh = (Historyonline)hs.get(0);
/* 950 */             long oC = oh.getCounts().longValue();
/* 951 */             if (counts > oC) {
/* 952 */               oh.setCounts(Long.valueOf(counts));
/* 953 */               oh.setRecDate(now);
/* 954 */               CheckOnlineService.this.historyonlineService.updateHistoryonlineByKey(oh);
/*     */             }
/*     */           } else {
/* 957 */             Historyonline r = new Historyonline();
/* 958 */             r.setCounts(Long.valueOf(counts));
/* 959 */             r.setRecDate(now);
/* 960 */             r.setRecDay(Integer.valueOf(day));
/* 961 */             r.setRecMonth(Integer.valueOf(month));
/* 962 */             r.setRecWeek(Integer.valueOf(week));
/* 963 */             r.setRecYear(Integer.valueOf(year));
/* 964 */             r.setRecTime(Integer.valueOf(hour));
/* 965 */             CheckOnlineService.this.historyonlineService.addHistoryonline(r);
/*     */           }
/*     */         } catch (Exception e) {
/* 968 */           CheckOnlineService.log.error("==============ERROR Start=============");
/* 969 */           CheckOnlineService.log.error(e);
/* 970 */           CheckOnlineService.log.error("ERROR INFO ", e);
/* 971 */           CheckOnlineService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     };
/* 976 */     long delay = 60000L;
/* 977 */     long intevalPeriod = 60000L;
/*     */ 
/* 979 */     timerRecordOnlineCount.scheduleAtFixedRate(task, delay, intevalPeriod);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.CheckOnlineService
 * JD-Core Version:    0.6.2
 */