/*     */ package com.leeson.portal.core.service;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.query.PortalbasQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalbasService;
/*     */ import com.leeson.portal.core.service.action.ReqInfo;
/*     */ import com.leeson.portal.core.service.action.iKuai.iKuaiAuth;
/*     */ import com.leeson.portal.core.service.action.v1.chap.Chap_Auth_V1;
/*     */ import com.leeson.portal.core.service.action.v1.chap.Chap_Challenge_V1;
/*     */ import com.leeson.portal.core.service.action.v1.chap.Chap_Quit_V1;
/*     */ import com.leeson.portal.core.service.action.v1.pap.PAP_Auth_V1;
/*     */ import com.leeson.portal.core.service.action.v1.pap.PAP_Quit_V1;
/*     */ import com.leeson.portal.core.service.action.v2.chap.Chap_Auth_V2;
/*     */ import com.leeson.portal.core.service.action.v2.chap.Chap_Challenge_V2;
/*     */ import com.leeson.portal.core.service.action.v2.chap.Chap_Quit_V2;
/*     */ import com.leeson.portal.core.service.action.v2.pap.PAP_Auth_V2;
/*     */ import com.leeson.portal.core.service.action.v2.pap.PAP_Quit_V2;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.PrintStream;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ 
/*     */ public class InterfaceControl
/*     */ {
/*  41 */   private static Logger log = Logger.getLogger(InterfaceControl.class);
/*     */ 
/*  43 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*     */ 
/*  49 */   private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
/*  50 */   private static PortalbasService basService = (PortalbasService)ac.getBean("portalbasServiceImpl");
/*  51 */   private static ConfigService configService = (ConfigService)ac.getBean("configServiceImpl");
/*     */ 
/*     */   public static Boolean Method(String Action, String userName, String passWord, String ip, String bip, String mac)
/*     */   {
/* 171 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 172 */     String basIp = basConfig.getBasIp();
/* 173 */     String bas = basConfig.getBas();
/* 174 */     int basPort = Integer.parseInt(basConfig.getBasPort());
/* 175 */     String sharedSecret = basConfig.getSharedSecret();
/* 176 */     String authType = basConfig.getAuthType();
/* 177 */     int timeoutSec = Integer.parseInt(basConfig.getTimeoutSec());
/* 178 */     int portalVer = Integer.parseInt(basConfig.getPortalVer());
/*     */ 
/* 181 */     if (stringUtils.isNotBlank(bip)) {
/* 182 */       PortalbasQuery bq = new PortalbasQuery();
/* 183 */       bq.setBasIp(bip);
/* 184 */       bq.setBasIpLike(false);
/* 185 */       List basList = basService.getPortalbasList(bq);
/* 186 */       if (basList.size() > 0) {
/* 187 */         Portalbas basconfig = (Portalbas)basList.get(0);
/*     */ 
/* 189 */         bas = basconfig.getBas();
/* 190 */         basPort = Integer.parseInt(basconfig.getBasPort());
/* 191 */         sharedSecret = basconfig.getSharedSecret();
/* 192 */         authType = basconfig.getAuthType();
/* 193 */         timeoutSec = Integer.parseInt(basconfig.getTimeoutSec());
/* 194 */         portalVer = Integer.parseInt(basconfig.getPortalVer());
/* 195 */         basIp = bip;
/*     */       }
/*     */     }
/*     */ 
/* 199 */     String basipT = DomainToIP(basIp);
/* 200 */     if (stringUtils.isBlank(basipT)) {
/* 201 */       return Boolean.valueOf(false);
/*     */     }
/* 203 */     basIp = basipT;
/*     */ 
/* 207 */     short SerialNo_Short = (short)(int)(1.0D + Math.random() * 32767.0D);
/*     */ 
/* 213 */     byte[] SerialNo = PortalUtil.SerialNo(SerialNo_Short);
/* 214 */     byte[] UserIP = new byte[4];
/* 215 */     String[] ips = ip.split("[.]");
/*     */ 
/* 217 */     for (int i = 0; i < 4; i++) {
/* 218 */       int m = NumberUtils.toInt(ips[i]);
/* 219 */       byte b = (byte)m;
/* 220 */       UserIP[i] = b;
/*     */     }
/*     */ 
/* 223 */     if ((bas.equals("2")) || (bas.equals("4"))) {
/* 224 */       if (basConfig.getIsdebug().equals("1")) {
/* 225 */         log.info("【iKuai对接   准备portal协议报文发送】  ip: " + ip + " mac: " + mac + " user: " + userName + " basip: " + basIp);
/*     */       }
/*     */ 
/* 228 */       if (Action.equals("PORTAL_LOGIN")) {
/* 229 */         if (stringUtils.isBlank(mac)) {
/* 230 */           if (basConfig.getIsdebug().equals("1")) {
/* 231 */             log.info("【iKuai对接   获取MAC地址参数 错误!!!】");
/*     */           }
/* 233 */           return Boolean.valueOf(false);
/*     */         }
/* 235 */         return Boolean.valueOf(iKuaiAuth.auth(basIp, basPort, timeoutSec, userName, passWord, SerialNo, UserIP, mac));
/*     */       }
/*     */ 
/* 238 */       return Boolean.valueOf(PAP_Quit_V1.quit(0, basIp, basPort, timeoutSec, 
/* 239 */         SerialNo, UserIP));
/*     */     }
/*     */ 
/* 246 */     if (basConfig.getIsdebug().equals("1")) {
/* 247 */       log.info("【准备portal协议报文发送】  ip: " + ip + " user: " + userName + " basip: " + basIp);
/*     */     }
/*     */ 
/* 250 */     if ((authType.equals("0")) && (portalVer == 1)) {
/* 251 */       if (basConfig.getIsdebug().equals("1")) {
/* 252 */         log.info("使用Portal V1协议，PAP认证方式！！");
/*     */       }
/*     */ 
/* 255 */       if (Action.equals("PORTAL_LOGIN")) {
/* 256 */         if (bas.equals("1")) {
/* 257 */           if (basConfig.getIsdebug().equals("1")) {
/* 258 */             log.info("H3C设备！！");
/*     */           }
/*     */ 
/* 262 */           byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 1);
/*     */ 
/* 264 */           if (Ack_info.length == 1) {
/* 265 */             return Boolean.valueOf(false);
/*     */           }
/*     */         }
/* 268 */         return Boolean.valueOf(PAP_Auth_V1.auth(basIp, basPort, timeoutSec, userName, 
/* 269 */           passWord, SerialNo, UserIP));
/*     */       }
/*     */ 
/* 272 */       return Boolean.valueOf(PAP_Quit_V1.quit(0, basIp, basPort, timeoutSec, 
/* 273 */         SerialNo, UserIP));
/*     */     }
/* 275 */     if ((authType.equals("0")) && (portalVer == 2))
/*     */     {
/* 277 */       if (basConfig.getIsdebug().equals("1")) {
/* 278 */         log.info("使用Portal V2协议，PAP认证方式！！");
/*     */       }
/*     */ 
/* 281 */       if (Action.equals("PORTAL_LOGIN")) {
/* 282 */         if (bas.equals("1")) {
/* 283 */           if (basConfig.getIsdebug().equals("1")) {
/* 284 */             log.info("H3C设备！！");
/*     */           }
/*     */ 
/* 288 */           byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 2);
/*     */ 
/* 290 */           if (Ack_info.length == 1) {
/* 291 */             return Boolean.valueOf(false);
/*     */           }
/*     */         }
/* 294 */         return Boolean.valueOf(PAP_Auth_V2.auth(basIp, basPort, timeoutSec, userName, 
/* 295 */           passWord, SerialNo, UserIP, sharedSecret));
/*     */       }
/*     */ 
/* 298 */       return Boolean.valueOf(PAP_Quit_V2.quit(0, basIp, basPort, timeoutSec, 
/* 299 */         SerialNo, UserIP, sharedSecret));
/*     */     }
/*     */ 
/* 304 */     if ((authType.equals("1")) && (portalVer == 2))
/*     */     {
/* 307 */       return Boolean.valueOf(Portal_V2(Action, userName, passWord, basIp, basPort, 
/* 308 */         timeoutSec, SerialNo, UserIP, sharedSecret, SerialNo_Short, ip, bas));
/*     */     }
/*     */ 
/* 311 */     if ((authType.equals("1")) && (portalVer == 1))
/*     */     {
/* 313 */       return Boolean.valueOf(Portal_V1(Action, userName, passWord, basIp, basPort, 
/* 314 */         timeoutSec, SerialNo, UserIP, ip, bas, sharedSecret));
/*     */     }
/*     */ 
/* 318 */     if (basConfig.getIsdebug().equals("1")) {
/* 319 */       log.info("参数错误,认证方式或版本号错误!!!");
/*     */     }
/*     */ 
/* 322 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */   private static boolean Portal_V2(String Action, String userName, String passWord, String basIp, int basPort, int timeoutSec, byte[] SerialNo, byte[] UserIP, String sharedSecret, short SerialNo_Short, String ip, String bas)
/*     */   {
/* 330 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 331 */     if (basConfig.getIsdebug().equals("1")) {
/* 332 */       log.info("使用Portal V2协议，Chap认证方式！！");
/*     */     }
/*     */ 
/* 335 */     byte[] ReqID = new byte[2];
/* 336 */     if (Action.equals("PORTAL_LOGIN"))
/*     */     {
/* 338 */       if (bas.equals("1")) {
/* 339 */         if (basConfig.getIsdebug().equals("1")) {
/* 340 */           log.info("H3C设备！！");
/*     */         }
/*     */ 
/* 345 */         byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 2);
/*     */ 
/* 347 */         if (Ack_info.length == 1) {
/* 348 */           return false;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 355 */       byte[] Challenge = new byte[16];
/*     */ 
/* 358 */       byte[] Ack_Challenge_V2 = Chap_Challenge_V2.challenge(basIp, 
/* 359 */         basPort, timeoutSec, SerialNo, UserIP, sharedSecret);
/*     */ 
/* 361 */       if (Ack_Challenge_V2.length == 2) {
/* 362 */         ReqID = Ack_Challenge_V2;
/* 363 */         Chap_Quit_V2.quit(1, basIp, basPort, timeoutSec, SerialNo, 
/* 364 */           UserIP, ReqID, sharedSecret);
/* 365 */         return false;
/*     */       }
/* 367 */       ReqID[0] = Ack_Challenge_V2[6];
/* 368 */       ReqID[1] = Ack_Challenge_V2[7];
/* 369 */       for (int i = 0; i < 16; i++) {
/* 370 */         Challenge[i] = Ack_Challenge_V2[(34 + i)];
/*     */       }
/* 372 */       if (basConfig.getIsdebug().equals("1")) {
/* 373 */         log.info("获得Challenge：" + 
/* 374 */           PortalUtil.Getbyte2HexString(Challenge));
/*     */       }
/*     */ 
/* 378 */       byte[] Ack_Auth_V2 = Chap_Auth_V2.auth(basIp, basPort, 
/* 379 */         timeoutSec, userName, passWord, SerialNo, UserIP, 
/* 380 */         ReqID, Challenge, sharedSecret);
/* 381 */       if (((Ack_Auth_V2[0] & 0xFF) != 20) && 
/* 382 */         ((Ack_Auth_V2[0] & 0xFF) != 22)) {
/* 383 */         Chap_Quit_V2.quit(2, basIp, basPort, timeoutSec, SerialNo, 
/* 384 */           UserIP, ReqID, sharedSecret);
/* 385 */         return false;
/*     */       }
/* 387 */       return true;
/*     */     }
/*     */ 
/* 392 */     return Chap_Quit_V2.quit(0, basIp, basPort, timeoutSec, SerialNo, 
/* 393 */       UserIP, ReqID, sharedSecret);
/*     */   }
/*     */ 
/*     */   private static boolean Portal_V1(String Action, String userName, String passWord, String basIp, int basPort, int timeoutSec, byte[] SerialNo, byte[] UserIP, String ip, String bas, String sharedSecret)
/*     */   {
/* 400 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 401 */     if (basConfig.getIsdebug().equals("1")) {
/* 402 */       log.info("使用Portal V1协议，Chap认证方式！！");
/*     */     }
/*     */ 
/* 405 */     byte[] ReqID = new byte[2];
/* 406 */     if (Action.equals("PORTAL_LOGIN"))
/*     */     {
/* 408 */       if (bas.equals("1")) {
/* 409 */         if (basConfig.getIsdebug().equals("1")) {
/* 410 */           log.info("H3C设备！！");
/*     */         }
/*     */ 
/* 414 */         byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 1);
/*     */ 
/* 416 */         if (Ack_info.length == 1) {
/* 417 */           return false;
/*     */         }
/*     */       }
/*     */ 
/* 421 */       byte[] Challenge = new byte[16];
/*     */ 
/* 423 */       byte[] Ack_Challenge_V1 = Chap_Challenge_V1.Action(basIp, basPort, 
/* 424 */         timeoutSec, SerialNo, UserIP);
/*     */ 
/* 426 */       if (Ack_Challenge_V1.length == 2) {
/* 427 */         ReqID = Ack_Challenge_V1;
/* 428 */         Chap_Quit_V1.quit(1, basIp, basPort, timeoutSec, SerialNo, 
/* 429 */           UserIP, ReqID);
/* 430 */         return false;
/*     */       }
/* 432 */       ReqID[0] = Ack_Challenge_V1[6];
/* 433 */       ReqID[1] = Ack_Challenge_V1[7];
/* 434 */       for (int i = 0; i < 16; i++) {
/* 435 */         Challenge[i] = Ack_Challenge_V1[(18 + i)];
/*     */       }
/* 437 */       if (basConfig.getIsdebug().equals("1")) {
/* 438 */         log.info("获得Challenge：" + 
/* 439 */           PortalUtil.Getbyte2HexString(Challenge));
/*     */       }
/*     */ 
/* 443 */       byte[] Ack_Auth_V1 = Chap_Auth_V1.auth(basIp, basPort, 
/* 444 */         timeoutSec, userName, passWord, SerialNo, UserIP, 
/* 445 */         ReqID, Challenge);
/* 446 */       if (((Ack_Auth_V1[0] & 0xFF) != 20) && 
/* 447 */         ((Ack_Auth_V1[0] & 0xFF) != 22)) {
/* 448 */         Chap_Quit_V1.quit(2, basIp, basPort, timeoutSec, SerialNo, 
/* 449 */           UserIP, ReqID);
/* 450 */         return false;
/*     */       }
/* 452 */       return true;
/*     */     }
/*     */ 
/* 457 */     return Chap_Quit_V1.quit(0, basIp, basPort, timeoutSec, SerialNo, 
/* 458 */       UserIP, ReqID);
/*     */   }
/*     */ 
/*     */   private static String DomainToIP(String domain)
/*     */   {
/* 508 */     if (configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
/* 509 */       return domain;
/*     */     }
/* 511 */     String ip = "";
/*     */     try {
/* 513 */       ip = java.net.InetAddress.getByName(domain).toString().split("/")[1];
/*     */     } catch (UnknownHostException e) {
/* 515 */       log.error("DomainToIP ERROR!!");
/* 516 */       log.error("==============ERROR Start=============");
/* 517 */       log.error(e);
/* 518 */       log.error("ERROR INFO ", e);
/* 519 */       log.error("==============ERROR End=============");
/*     */     }
/* 521 */     System.out.println("Domain:" + domain + " IP:" + ip);
/* 522 */     return ip;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.InterfaceControl
 * JD-Core Version:    0.6.2
 */