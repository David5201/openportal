/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.bean.Portallinkrecordall;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.controller.AjaxInterFaceController;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortallinkrecordService;
/*     */ import com.leeson.core.service.PortallinkrecordallService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.LateAuthMap;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.UniFiMacSiteMap;
/*     */ import com.leeson.portal.core.model.ipMap;
/*     */ import com.leeson.portal.core.service.InterfaceControl;
/*     */ import com.leeson.portal.core.service.action.unifi.UniFiMethod;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ 
/*     */ public class Kick
/*     */ {
/*  32 */   private static Logger log = Logger.getLogger(Kick.class);
/*  33 */   private static Config configDefault = Config.getInstance();
/*  34 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*     */ 
/*  36 */   private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
/*  37 */   private static PortallinkrecordallService linkAllService = (PortallinkrecordallService)ac
/*  38 */     .getBean("portallinkrecordallServiceImpl");
/*     */ 
/*  40 */   private static PortallinkrecordService portallinkrecordService = (PortallinkrecordService)ac
/*  41 */     .getBean("portallinkrecordServiceImpl");
/*     */ 
/*  42 */   private static PortalaccountService portalaccountService = (PortalaccountService)ac
/*  43 */     .getBean("portalaccountServiceImpl");
/*     */ 
/*  44 */   private static PortallogrecordService portallogrecordService = (PortallogrecordService)ac
/*  45 */     .getBean("portallogrecordServiceImpl");
/*     */ 
/*     */   public static void deleteOnline(String ip)
/*     */   {
/*  52 */     int i = ip.lastIndexOf(":");
/*  53 */     String ubip = ip.substring(i + 1);
/*  54 */     String uip = ip.substring(0, i);
/*     */ 
/*  56 */     String[] loginInfo = null;
/*  57 */     loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
/*  58 */     if (loginInfo != null) {
/*  59 */       doLinkAll(loginInfo, "管理员清除");
/*  60 */       String username = loginInfo[0];
/*  61 */       String mac = loginInfo[4];
/*  62 */       String type = loginInfo[6];
/*  63 */       Portalbas config = (Portalbas)configDefault.getConfigMap().get(ubip);
/*  64 */       if ((config != null) && 
/*  65 */         ("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
/*  66 */         doLinkRecord(loginInfo, "管理员清除");
/*     */       }
/*     */       try
/*     */       {
/*  70 */         String time = loginInfo[3];
/*  71 */         Date loginTime = ThreadLocalDateUtil.parse(time);
/*  72 */         String nowString = ThreadLocalDateUtil.format(new Date());
/*  73 */         Date nowTime = ThreadLocalDateUtil.parse(nowString);
/*  74 */         Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/*  75 */         costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/*  76 */         doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + 
/*  77 */           " 上线时间: " + time + " 在线时长: " + costTime + "分钟,管理员清除！");
/*     */ 
/*  79 */         if (((Portalbas)configDefault.getConfigMap().get("")).getIsdebug()
/*  79 */           .equals("1"))
/*  80 */           log.info("IP: " + ip + " mac: " + mac + " user: " + 
/*  81 */             username + " , Del Online By Admin!");
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*  86 */       AjaxInterFaceController.SangforLogout(uip, username);
/*  87 */       CheckTimeUtils.recordTime(loginInfo);
/*  88 */       CheckAutoLoginUtils.recordTime(loginInfo);
/*     */     }
/*     */ 
/*  93 */     onlineMap.getOnlineUserMap().remove(ip);
/*  94 */     ipMap.getInstance().getIpmap().remove(uip);
/*     */   }
/*     */ 
/*     */   public static void kickUserWISPrSyn(String ip, String mac, String site)
/*     */   {
/* 103 */     int i = ip.lastIndexOf(":");
/* 104 */     String ubip = ip.substring(i + 1);
/* 105 */     String uip = ip.substring(0, i);
/*     */ 
/* 107 */     String[] loginInfo = null;
/* 108 */     loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
/* 109 */     if (loginInfo != null) {
/* 110 */       doLinkAll(loginInfo, "WISPr同步");
/* 111 */       String username = loginInfo[0];
/* 112 */       mac = loginInfo[4];
/* 113 */       String type = loginInfo[6];
/* 114 */       Portalbas config = (Portalbas)configDefault.getConfigMap().get(ubip);
/* 115 */       if ((config != null) && 
/* 116 */         ("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
/* 117 */         doLinkRecord(loginInfo, "WISPr同步");
/*     */       }
/*     */       try
/*     */       {
/* 121 */         String time = loginInfo[3];
/* 122 */         Date loginTime = ThreadLocalDateUtil.parse(time);
/* 123 */         String nowString = ThreadLocalDateUtil.format(new Date());
/* 124 */         Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 125 */         Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 126 */         costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/* 127 */         doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + 
/* 128 */           " 上线时间: " + time + " 在线时长: " + costTime + 
/* 129 */           "分钟,WISPr同步!");
/*     */ 
/* 131 */         if (((Portalbas)configDefault.getConfigMap().get("")).getIsdebug()
/* 131 */           .equals("1"))
/* 132 */           log.info("IP: " + ip + " mac: " + mac + " user: " + 
/* 133 */             username + " , OffLine By WISPrSyn!");
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/* 138 */       AjaxInterFaceController.SangforLogout(uip, username);
/* 139 */       CheckTimeUtils.recordTime(loginInfo);
/* 140 */       CheckAutoLoginUtils.recordTime(loginInfo);
/*     */     }
/*     */ 
/* 145 */     onlineMap.getOnlineUserMap().remove(ip);
/* 146 */     ipMap.getInstance().getIpmap().remove(uip);
/*     */   }
/*     */ 
/*     */   public static void kickUserSyn(String ip)
/*     */   {
/* 155 */     int i = ip.lastIndexOf(":");
/* 156 */     String ubip = ip.substring(i + 1);
/* 157 */     String uip = ip.substring(0, i);
/* 158 */     String mac = "";
/* 159 */     String[] loginInfo = null;
/* 160 */     loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
/* 161 */     if (loginInfo != null) {
/* 162 */       doLinkAll(loginInfo, "在线同步");
/* 163 */       String username = loginInfo[0];
/* 164 */       mac = loginInfo[4];
/* 165 */       String type = loginInfo[6];
/* 166 */       Portalbas config = (Portalbas)configDefault.getConfigMap().get(ubip);
/* 167 */       if ((config != null) && 
/* 168 */         ("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
/* 169 */         doLinkRecord(loginInfo, "在线同步");
/*     */       }
/*     */       try
/*     */       {
/* 173 */         String time = loginInfo[3];
/* 174 */         Date loginTime = ThreadLocalDateUtil.parse(time);
/* 175 */         String nowString = ThreadLocalDateUtil.format(new Date());
/* 176 */         Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 177 */         Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 178 */         costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/* 179 */         doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + 
/* 180 */           " 上线时间: " + time + " 在线时长: " + costTime + "分钟,在线同步!");
/*     */ 
/* 182 */         if (((Portalbas)configDefault.getConfigMap().get("")).getIsdebug()
/* 182 */           .equals("1"))
/* 183 */           log.info("IP: " + ip + " mac: " + mac + " user: " + 
/* 184 */             username + " , OffLine By IkuaiSyn!");
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/* 189 */       AjaxInterFaceController.SangforLogout(uip, username);
/* 190 */       CheckTimeUtils.recordTime(loginInfo);
/* 191 */       CheckAutoLoginUtils.recordTime(loginInfo);
/*     */     }
/*     */ 
/* 196 */     onlineMap.getOnlineUserMap().remove(ip);
/* 197 */     ipMap.getInstance().getIpmap().remove(uip);
/*     */   }
/*     */ 
/*     */   public static void kickUserDeleteUser(String ip)
/*     */   {
/* 202 */     coreMethod(ip, "删除用户", "Portal Kick By Delete User");
/*     */   }
/*     */ 
/*     */   public static void kickUserUpdate(String ip)
/*     */   {
/* 207 */     coreMethod(ip, "服务器更新", "Portal Kick By Server Update");
/*     */   }
/*     */ 
/*     */   public static void kickUserUnDingYue(String ip)
/*     */   {
/* 212 */     coreMethod(ip, "取消关注", "Portal Kick By UnDingYue WeiXin");
/*     */   }
/*     */ 
/*     */   public static void kickUserOneDayLimit(String ip)
/*     */   {
/* 217 */     coreMethod(ip, "每日时长", "Portal Kick By One Day Online Limit");
/*     */   }
/*     */ 
/*     */   public static void kickUserAutoLogin(String ip)
/*     */   {
/* 222 */     coreMethod(ip, "无感知时长", "Portal Kick By AutoLogin Limit");
/*     */   }
/*     */ 
/*     */   public static void kickUserLaterAuth(String ip)
/*     */   {
/* 227 */     coreMethod(ip, "延迟认证", "Portal Kick By LaterAuth Limit");
/*     */   }
/*     */ 
/*     */   public static void kickUserSessionTimeOut(String ip)
/*     */   {
/* 232 */     coreMethod(ip, "SessionTimeOut", "Portal Kick By SessionTimeOut");
/*     */   }
/*     */ 
/*     */   public static void kickUserByCustomer(String ip)
/*     */   {
/* 237 */     coreMethod(ip, "用户中心操作", "Portal Kick By Customer");
/*     */   }
/*     */ 
/*     */   public static void kickUserByAdmin(String ip)
/*     */   {
/* 242 */     coreMethod(ip, "管理员操作", "Portal Kick By Admin");
/*     */   }
/*     */ 
/*     */   public static void kickUserAllAPI(String ip)
/*     */   {
/* 247 */     coreMethod(ip, "API操作", "Portal Kick By API");
/*     */   }
/*     */ 
/*     */   public static void kickUserIPAPI(String ip)
/*     */   {
/* 252 */     coreMethod(ip, "API操作", "Portal Kick By API");
/*     */   }
/*     */ 
/*     */   public static void offLine(String ip, String mac, String site)
/*     */   {
/* 257 */     coreMethod(ip, "主动下线", "OffLine By Self");
/*     */   }
/*     */ 
/*     */   public static void WeixinOffLine(String ip)
/*     */   {
/* 262 */     coreMethod(ip, "微信临时放行", "Portal Kick By UnFinish WinXin Auth");
/*     */   }
/*     */ 
/*     */   private static void doLinkRecord(String[] loginInfo, String info)
/*     */   {
/*     */     try
/*     */     {
/* 274 */       String state = null;
/* 275 */       String uid = loginInfo[1];
/* 276 */       String rid = loginInfo[2];
/* 277 */       if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
/* 278 */         return;
/*     */       }
/* 280 */       Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
/* 281 */       Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
/* 282 */       if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || 
/* 283 */         (recordId.longValue() == 0L)) {
/* 284 */         return;
/*     */       }
/* 286 */       Portallinkrecord linkRecord = portallinkrecordService
/* 287 */         .getPortallinkrecordByKey(recordId);
/* 288 */       Portalaccount account = portalaccountService
/* 289 */         .getPortalaccountByKey(userId);
/* 290 */       if (account != null) {
/* 291 */         state = account.getState();
/*     */       }
/* 293 */       long in = Long.valueOf(loginInfo[7]).longValue();
/* 294 */       long out = Long.valueOf(loginInfo[8]).longValue();
/* 295 */       long octets = in + out;
/* 296 */       linkRecord.setIns(Long.valueOf(in));
/* 297 */       linkRecord.setOuts(Long.valueOf(out));
/* 298 */       linkRecord.setOctets(Long.valueOf(octets));
/* 299 */       Date now = new Date();
/* 300 */       linkRecord.setEndDate(now);
/* 301 */       Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
/* 302 */       linkRecord.setTime(costTime);
/* 303 */       linkRecord.setState(state);
/* 304 */       linkRecord.setEx1("Portal");
/* 305 */       linkRecord.setEx2(info);
/* 306 */       portallinkrecordService.updatePortallinkrecordByKey(linkRecord);
/* 307 */       if (!state.equals("1")) {
/* 308 */         Long haveTime = account.getTime();
/* 309 */         Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
/* 310 */         if ((state.equals("3")) && 
/* 311 */           (account.getDate().getTime() <= now.getTime())) {
/* 312 */           account.setState("2");
/* 313 */           portalaccountService.updatePortalaccountByKey(account);
/*     */         }
/*     */ 
/* 316 */         if (state.equals("2")) {
/* 317 */           if (newHaveTime.longValue() <= 0L) {
/* 318 */             account.setState("4");
/*     */           }
/* 320 */           account.setTime(newHaveTime);
/* 321 */           portalaccountService.updatePortalaccountByKey(account);
/*     */         }
/* 323 */         if ((state.equals("4")) || (state.equals("0"))) {
/* 324 */           long haveOctets = account.getOctets().longValue() - octets;
/* 325 */           if (haveOctets <= 0L) {
/* 326 */             account.setState("0");
/*     */           }
/* 328 */           account.setOctets(Long.valueOf(haveOctets));
/* 329 */           portalaccountService.updatePortalaccountByKey(account);
/*     */         }
/*     */       }
/*     */     } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void doLogRecord(String info) {
/*     */     try {
/* 338 */       Portallogrecord logRecord = new Portallogrecord();
/* 339 */       Date nowDate = new Date();
/* 340 */       logRecord.setInfo(info);
/* 341 */       logRecord.setRecDate(nowDate);
/* 342 */       portallogrecordService.addPortallogrecord(logRecord);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void coreMethod(String ip, String msg, String logInfo)
/*     */   {
/* 353 */     int i = ip.lastIndexOf(":");
/* 354 */     String ubip = ip.substring(i + 1);
/* 355 */     String uip = ip.substring(0, i);
/*     */ 
/* 357 */     Portalbas config = (Portalbas)configDefault.getConfigMap().get(ubip);
/* 358 */     if (config == null) {
/* 359 */       String[] loginInfo = null;
/* 360 */       loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
/* 361 */       if (loginInfo != null) {
/* 362 */         doLinkAll(loginInfo, msg);
/* 363 */         String username = loginInfo[0];
/* 364 */         String mac = loginInfo[4];
/*     */         try {
/* 366 */           String time = loginInfo[3];
/* 367 */           Date loginTime = ThreadLocalDateUtil.parse(time);
/* 368 */           String nowString = ThreadLocalDateUtil.format(new Date());
/* 369 */           Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 370 */           Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 371 */           costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/*     */ 
/* 373 */           doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + 
/* 374 */             username + " 上线时间: " + time + " 在线时长: " + 
/* 375 */             costTime + "分钟," + msg + "！");
/*     */ 
/* 378 */           if (((Portalbas)configDefault.getConfigMap().get("")).getIsdebug()
/* 378 */             .equals("1"))
/* 379 */             log.info("IP: " + ip + " mac: " + mac + " user: " + 
/* 380 */               username + " , " + logInfo + "!");
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/* 384 */         CheckTimeUtils.recordTime(loginInfo);
/* 385 */         CheckAutoLoginUtils.recordTime(loginInfo);
/* 386 */         AjaxInterFaceController.SangforLogout(uip, username);
/*     */       }
/*     */ 
/* 391 */       onlineMap.getOnlineUserMap().remove(ip);
/* 392 */       return;
/*     */     }
/*     */ 
/* 398 */     String[] loginInfo = null;
/* 399 */     loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
/* 400 */     if (loginInfo != null) {
/* 401 */       doLinkAll(loginInfo, msg);
/* 402 */       String username = loginInfo[0];
/* 403 */       String mac = loginInfo[4];
/* 404 */       String type = loginInfo[6];
/* 405 */       if (("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
/* 406 */         doLinkRecord(loginInfo, msg);
/*     */       }
/* 408 */       Boolean info = Boolean.valueOf(false);
/* 409 */       if (config.getBas().equals("3")) {
/* 410 */         if (stringUtils.isNotBlank(mac)) {
/* 411 */           String site = 
/* 412 */             (String)UniFiMacSiteMap.getInstance().getMacSiteMap()
/* 412 */             .get(mac);
/* 413 */           info = Boolean.valueOf(UniFiMethod.sendUnauthorization(mac, site, ubip));
/*     */         }
/* 415 */       } else if ((!config.getBas().equals("5")) && 
/* 416 */         (!config.getBas().equals("6")) && 
/* 417 */         (!config.getBas().equals("7")) && 
/* 418 */         (!config
/* 418 */         .getBas().equals("8")))
/* 419 */         info = InterfaceControl.Method("PORTAL_LOGINOUT", username, 
/* 420 */           "password", uip, ubip, mac);
/*     */       else
/* 422 */         info = Boolean.valueOf(true);
/*     */       try
/*     */       {
/* 425 */         String time = loginInfo[3];
/* 426 */         Date loginTime = ThreadLocalDateUtil.parse(time);
/* 427 */         String nowString = ThreadLocalDateUtil.format(new Date());
/* 428 */         Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 429 */         Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 430 */         costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/*     */ 
/* 432 */         if (info.booleanValue()) {
/* 433 */           doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + 
/* 434 */             username + " 上线时间: " + time + " 在线时长: " + 
/* 435 */             costTime + "分钟," + msg + "！");
/*     */ 
/* 437 */           if (((Portalbas)configDefault.getConfigMap().get("")).getIsdebug()
/* 437 */             .equals("1"))
/* 438 */             log.info("IP: " + ip + " mac: " + mac + " user: " + 
/* 439 */               username + " , " + logInfo + "!");
/*     */         }
/*     */         else {
/* 442 */           doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + 
/* 443 */             username + " 上线时间: " + time + " 在线时长: " + 
/* 444 */             costTime + "分钟," + msg + "???");
/*     */ 
/* 446 */           if (((Portalbas)configDefault.getConfigMap().get("")).getIsdebug()
/* 446 */             .equals("1"))
/* 447 */             log.info("IP: " + ip + " mac: " + mac + " user: " + 
/* 448 */               username + " , " + logInfo + "???");
/*     */         }
/*     */       }
/*     */       catch (Exception localException1) {
/*     */       }
/* 453 */       CheckTimeUtils.recordTime(loginInfo);
/* 454 */       CheckAutoLoginUtils.recordTime(loginInfo);
/* 455 */       AjaxInterFaceController.SangforLogout(uip, username);
/* 456 */       if ((stringUtils.isNotBlank(mac)) && (!"8".equals(type))) {
/* 457 */         LateAuthMap.getInstance().getLateAuthMap().remove(mac);
/*     */       }
/*     */     }
/* 460 */     onlineMap.getOnlineUserMap().remove(ip);
/* 461 */     ipMap.getInstance().getIpmap().remove(uip);
/*     */   }
/*     */ 
/*     */   public static void doLinkAll(String[] loginInfo, String info) {
/*     */     try {
/* 466 */       if (loginInfo != null) {
/* 467 */         String time = loginInfo[3];
/* 468 */         Date loginTime = new Date();
/* 469 */         String nowString = ThreadLocalDateUtil.format(new Date());
/* 470 */         Date nowTime = new Date();
/*     */         try {
/* 472 */           loginTime = ThreadLocalDateUtil.parse(time);
/* 473 */           nowTime = ThreadLocalDateUtil.parse(nowString);
/*     */         } catch (Exception localException1) {
/*     */         }
/* 476 */         Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 477 */         String state = null;
/*     */         try {
/* 479 */           String uid = loginInfo[1];
/* 480 */           if (stringUtils.isNotBlank(uid)) {
/* 481 */             Long userId = Long.valueOf(Long.parseLong(uid));
/* 482 */             if ((userId != null) && (userId.longValue() != 0L)) {
/* 483 */               Portalaccount account = portalaccountService
/* 484 */                 .getPortalaccountByKey(userId);
/* 485 */               if (account != null)
/* 486 */                 state = account.getState();
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception localException2) {
/*     */         }
/* 492 */         String type = loginInfo[6];
/* 493 */         if ("0".equals(type))
/* 494 */           type = "一键认证";
/* 495 */         else if ("1".equals(type))
/* 496 */           type = "本地用户";
/* 497 */         else if ("2".equals(type))
/* 498 */           type = "Radius";
/* 499 */         else if ("3".equals(type))
/* 500 */           type = "APP认证";
/* 501 */         else if ("4".equals(type))
/* 502 */           type = "短信认证";
/* 503 */         else if ("5".equals(type))
/* 504 */           type = "微信认证";
/* 505 */         else if ("6".equals(type))
/* 506 */           type = "公众号认证";
/* 507 */         else if ("7".equals(type))
/* 508 */           type = "访客认证";
/* 509 */         else if ("8".equals(type)) {
/* 510 */           type = "延迟认证";
/*     */         }
/* 512 */         String basname = loginInfo[11];
/* 513 */         String ssid = loginInfo[12];
/* 514 */         String apmac = loginInfo[13];
/* 515 */         String agent = loginInfo[15];
/* 516 */         if (stringUtils.isBlank(basname)) {
/* 517 */           basname = "base";
/*     */         }
/* 519 */         if (stringUtils.isBlank(ssid)) {
/* 520 */           ssid = "unknow";
/*     */         }
/* 522 */         if (stringUtils.isBlank(apmac)) {
/* 523 */           apmac = "unknow";
/*     */         }
/* 525 */         if (stringUtils.isBlank(agent)) {
/* 526 */           agent = "unknow";
/*     */         }
/* 528 */         if (stringUtils.isBlank(state)) {
/* 529 */           state = "-1";
/*     */         }
/* 531 */         Portallinkrecordall linkAll = new Portallinkrecordall();
/*     */ 
/* 546 */         long in = 0L;
/* 547 */         long out = 0L;
/*     */         try {
/* 549 */           in = Long.valueOf(Long.valueOf(loginInfo[7]).longValue()).longValue();
/* 550 */           out = Long.valueOf(Long.valueOf(loginInfo[8]).longValue()).longValue();
/*     */         } catch (Exception localException3) {
/*     */         }
/* 553 */         long octets = in + out;
/*     */ 
/* 555 */         linkAll.setAgent(agent);
/* 556 */         linkAll.setApmac(apmac);
/* 557 */         linkAll.setAuto(loginInfo[14]);
/* 558 */         linkAll.setBasip(loginInfo[10]);
/* 559 */         linkAll.setBasname(basname);
/* 560 */         linkAll.setEndDate(new Date());
/* 561 */         linkAll.setIns(Long.valueOf(in));
/* 562 */         linkAll.setIp(loginInfo[9]);
/* 563 */         linkAll.setLoginName(loginInfo[0]);
/* 564 */         linkAll.setMac(loginInfo[4]);
/* 565 */         linkAll.setOuts(Long.valueOf(out));
/* 566 */         linkAll.setSsid(ssid);
/* 567 */         linkAll.setStartDate(loginTime);
/* 568 */         linkAll.setState(state);
/* 569 */         linkAll.setTime(costTime);
/* 570 */         linkAll.setMethodtype(type);
/* 571 */         linkAll.setOctets(Long.valueOf(octets));
/* 572 */         linkAll.setEx1(info);
/* 573 */         linkAllService.addPortallinkrecordall(linkAll);
/*     */       }
/*     */     } catch (Exception e) {
/* 576 */       log.error("==============ERROR Start=============");
/* 577 */       log.error(e);
/* 578 */       log.error("ERROR INFO ", e);
/* 579 */       log.error("==============ERROR End=============");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.Kick
 * JD-Core Version:    0.6.2
 */