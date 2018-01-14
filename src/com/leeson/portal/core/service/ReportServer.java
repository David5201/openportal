/*     */ package com.leeson.portal.core.service;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.controller.AjaxInterFaceController;
/*     */ import com.leeson.core.query.PortalbasQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalbasService;
/*     */ import com.leeson.core.service.PortallinkrecordService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.core.utils.CheckAutoLoginUtils;
/*     */ import com.leeson.core.utils.CheckTimeUtils;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.iKuaiMacIpMap;
/*     */ import com.leeson.portal.core.service.utils.ACKAuthenticator;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.PrintStream;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ 
/*     */ public class ReportServer extends Thread
/*     */ {
/*  73 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*  74 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*  75 */   private static Logger log = Logger.getLogger(ReportServer.class);
/*  76 */   private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
/*  77 */   private static PortalbasService basService = (PortalbasService)ac.getBean("portalbasServiceImpl");
/*  78 */   private static ConfigService configService = (ConfigService)
/*  79 */     SpringContextHelper.getBean("configServiceImpl");
/*     */ 
/*  81 */   private static Boolean isRun = Boolean.valueOf(true);
/*  82 */   private static DatagramSocket socket = null;
/*     */ 
/*  87 */   DatagramPacket data = null;
/*     */ 
/*     */   public ReportServer(DatagramPacket data)
/*     */   {
/*  91 */     this.data = data;
/*     */   }
/*     */ 
/*     */   public void run() {
/*  95 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/*  97 */     byte[] Req_Data_Base = new byte[this.data.getLength()];
/*  98 */     for (int l = 0; l < Req_Data_Base.length; l++) {
/*  99 */       Req_Data_Base[l] = this.data.getData()[l];
/*     */     }
/*     */ 
/* 103 */     String ip = this.data.getAddress().getHostAddress();
/* 104 */     int port = this.data.getPort();
/*     */ 
/* 106 */     String domain = ip;
/*     */     try {
/* 108 */       Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
/* 109 */       while (iteratorConfig.hasNext()) {
/* 110 */         Object o = iteratorConfig.next();
/* 111 */         String t = o.toString();
/* 112 */         String basip = ((Portalbas)config.getConfigMap().get(t)).getBasIp();
/* 113 */         String basipT = DomainToIP(basip);
/* 114 */         if (ip.equals(basipT)) {
/* 115 */           domain = basip;
/* 116 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/* 123 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 124 */       log.info("Receive BAS Domain:" + domain + " IP:" + ip + ":" + port + ":" + "Packet Size:" + this.data.getLength() + " Packet Text: " + 
/* 125 */         PortalUtil.Getbyte2HexString(Req_Data_Base));
/*     */     }
/*     */ 
/* 129 */     byte[] Ver = new byte[1];
/* 130 */     byte[] Type = new byte[1];
/* 131 */     byte[] Mod = new byte[1];
/* 132 */     byte[] Rsvd = new byte[1];
/* 133 */     byte[] SerialNo = new byte[2];
/* 134 */     byte[] ReqID = new byte[2];
/* 135 */     byte[] UserIP = new byte[4];
/* 136 */     byte[] UserPort = new byte[2];
/* 137 */     byte[] ErrCode = new byte[1];
/* 138 */     byte[] AttrNum = new byte[1];
/*     */ 
/* 141 */     Ver[0] = Req_Data_Base[0];
/* 142 */     Type[0] = Req_Data_Base[1];
/* 143 */     Mod[0] = Req_Data_Base[2];
/* 144 */     Rsvd[0] = Req_Data_Base[3];
/* 145 */     SerialNo[0] = Req_Data_Base[4];
/* 146 */     SerialNo[1] = Req_Data_Base[5];
/* 147 */     ReqID[0] = Req_Data_Base[6];
/* 148 */     ReqID[1] = Req_Data_Base[7];
/* 149 */     UserIP[0] = Req_Data_Base[8];
/* 150 */     UserIP[1] = Req_Data_Base[9];
/* 151 */     UserIP[2] = Req_Data_Base[10];
/* 152 */     UserIP[3] = Req_Data_Base[11];
/* 153 */     UserPort[0] = Req_Data_Base[12];
/* 154 */     UserPort[1] = Req_Data_Base[13];
/* 155 */     ErrCode[0] = Req_Data_Base[14];
/* 156 */     AttrNum[0] = Req_Data_Base[15];
/*     */ 
/* 161 */     if ((Type[0] & 0xFF) == 8)
/*     */     {
/* 163 */       String sharedSecret = basConfig.getSharedSecret();
/* 164 */       int basPort = Integer.parseInt(basConfig.getBasPort());
/*     */ 
/* 166 */       if (stringUtils.isNotBlank(ip)) {
/* 167 */         PortalbasQuery bq = new PortalbasQuery();
/* 168 */         bq.setBasIp(domain);
/* 169 */         bq.setBasIpLike(false);
/* 170 */         List basList = basService.getPortalbasList(bq);
/* 171 */         if (basList.size() > 0) {
/* 172 */           basConfig = (Portalbas)basList.get(0);
/* 173 */           basPort = Integer.parseInt(basConfig.getBasPort());
/* 174 */           sharedSecret = basConfig.getSharedSecret();
/*     */         }
/*     */       }
/*     */ 
/* 178 */       byte[] Ack_Data_Quit = null;
/* 179 */       if ((Ver[0] & 0xFF) == 1) {
/* 180 */         Ack_Data_Quit = new byte[16];
/*     */       }
/* 182 */       if ((Ver[0] & 0xFF) == 2) {
/* 183 */         Ack_Data_Quit = new byte[32];
/*     */       }
/* 185 */       for (int i = 0; i < 16; i++) {
/* 186 */         Ack_Data_Quit[i] = Req_Data_Base[i];
/*     */       }
/* 188 */       short typet = 14;
/* 189 */       Ack_Data_Quit[1] = ((byte)typet);
/* 190 */       Ack_Data_Quit[15] = 0;
/*     */ 
/* 192 */       if ((Ver[0] & 0xFF) == 2) {
/* 193 */         byte[] Attrs = new byte[0];
/* 194 */         byte[] BBuff = new byte[16];
/* 195 */         byte[] reqAuthen = new byte[16];
/* 196 */         for (int i = 0; i < 16; i++) {
/* 197 */           BBuff[i] = Ack_Data_Quit[i];
/*     */         }
/* 199 */         if (Req_Data_Base.length >= 32) {
/* 200 */           for (int i = 0; i < 16; i++) {
/* 201 */             reqAuthen[i] = Req_Data_Base[(16 + i)];
/*     */           }
/* 203 */           if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 204 */             log.info("Get reqAuthen " + PortalUtil.Getbyte2HexString(reqAuthen));
/*     */           }
/*     */         }
/* 207 */         byte[] Authen = ACKAuthenticator.MK_Authen(BBuff, Attrs, sharedSecret, reqAuthen);
/* 208 */         for (int i = 0; i < 16; i++) {
/* 209 */           Ack_Data_Quit[(16 + i)] = Authen[i];
/*     */         }
/*     */       }
/*     */ 
/* 213 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 214 */         log.info("Read Send NTF_LogOut Resp Packet to: " + ip + ":" + basPort + ":" + 
/* 215 */           PortalUtil.Getbyte2HexString(Ack_Data_Quit));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 221 */         DatagramPacket data = new DatagramPacket(Ack_Data_Quit, 
/* 222 */           Ack_Data_Quit.length, InetAddress.getByName(ip), basPort);
/* 223 */         socket.send(data);
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/*     */       }
/*     */ 
/* 232 */       if ((basConfig.getBas()
/* 232 */         .equals("2")) || 
/* 233 */         (basConfig.getBas()
/* 233 */         .equals("4")))
/*     */       {
/* 235 */         ikuaiOffline(Req_Data_Base, domain);
/*     */       }
/*     */       else
/*     */       {
/* 239 */         String userIP = bytesToIp(UserIP);
/*     */ 
/* 241 */         if (onlineMap.getOnlineUserMap().containsKey(userIP + ":" + domain)) {
/* 242 */           String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
/* 243 */             userIP + ":" + domain);
/* 244 */           Kick.doLinkAll(loginInfo, "NTF报文");
/* 245 */           String username = loginInfo[0];
/* 246 */           String mac = loginInfo[4];
/*     */ 
/* 248 */           if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 249 */             log.info("NTF_LogOut Request Want Logout User: " + userIP + ":" + domain + " mac: " + mac);
/*     */           }
/*     */ 
/* 252 */           CheckTimeUtils.recordTime(loginInfo);
/* 253 */           CheckAutoLoginUtils.recordTime(loginInfo);
/*     */           try
/*     */           {
/* 256 */             doLinkRecord(loginInfo, "NTF报文");
/* 257 */             String time = loginInfo[3];
/* 258 */             Date loginTime = ThreadLocalDateUtil.parse(time);
/* 259 */             String nowString = ThreadLocalDateUtil.format(new Date());
/* 260 */             Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 261 */             Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 262 */             costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/* 263 */             doLogRecord("IP: " + userIP + ":" + domain + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: " + costTime + 
/* 264 */               "分钟,NTF报文！");
/*     */           }
/*     */           catch (Exception localException2)
/*     */           {
/*     */           }
/* 269 */           AjaxInterFaceController.SangforLogout(userIP, username);
/*     */ 
/* 271 */           onlineMap.getOnlineUserMap().remove(userIP + ":" + domain);
/*     */ 
/* 276 */           if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/* 277 */             log.info("IP: " + userIP + ":" + domain + " mac: " + mac + " username: " + username + ", Logout By Ntf_Logout !!");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void ikuaiOffline(byte[] data, String ip)
/*     */   {
/* 312 */     String mac = getMac(data);
/* 313 */     String userIP = "";
/*     */ 
/* 315 */     if (stringUtils.isNotBlank(mac)) {
/* 316 */       userIP = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(mac);
/*     */     }
/* 318 */     if (stringUtils.isNotBlank(userIP))
/*     */     {
/* 320 */       if (onlineMap.getOnlineUserMap().containsKey(userIP + ":" + ip)) {
/* 321 */         String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
/* 322 */           userIP + ":" + ip);
/* 323 */         Kick.doLinkAll(loginInfo, "NTF报文");
/* 324 */         String username = loginInfo[0];
/*     */ 
/* 326 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 327 */           log.info("IKuai NTF_LogOut Request Want Logout User: " + userIP + ":" + ip + " mac: " + mac);
/*     */         }
/*     */ 
/* 330 */         CheckTimeUtils.recordTime(loginInfo);
/* 331 */         CheckAutoLoginUtils.recordTime(loginInfo);
/*     */         try
/*     */         {
/* 334 */           doLinkRecord(loginInfo, "NTF报文");
/* 335 */           String time = loginInfo[3];
/* 336 */           Date loginTime = ThreadLocalDateUtil.parse(time);
/* 337 */           String nowString = ThreadLocalDateUtil.format(new Date());
/* 338 */           Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 339 */           Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 340 */           costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/* 341 */           doLogRecord("IP: " + userIP + ":" + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: " + costTime + 
/* 342 */             "分钟,NTF报文！");
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/* 346 */         AjaxInterFaceController.SangforLogout(userIP, username);
/*     */ 
/* 348 */         onlineMap.getOnlineUserMap().remove(userIP + ":" + ip);
/*     */ 
/* 353 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/* 354 */           log.info("IP: " + userIP + ":" + ip + " mac: " + mac + " username: " + username + ", Logout By Ntf_Logout !!");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getMac(byte[] data)
/*     */   {
/* 362 */     String mac = "";
/* 363 */     if ((data[15] & 0xFF) != 0) {
/* 364 */       int pos = 0;
/* 365 */       if ((data[0] & 0xFF) == 1) {
/* 366 */         pos = 16;
/*     */       }
/* 368 */       if ((data[0] & 0xFF) == 2) {
/* 369 */         pos = 32;
/*     */       }
/* 371 */       int AN = data[15] & 0xFF;
/*     */ 
/* 373 */       int num = 1;
/* 374 */       while (num <= AN) {
/* 375 */         if (pos >= data.length)
/*     */         {
/*     */           break;
/*     */         }
/* 379 */         int type = data[pos] & 0xFF;
/* 380 */         pos++;
/*     */ 
/* 382 */         if (pos >= data.length)
/*     */         {
/*     */           break;
/*     */         }
/* 386 */         int len = (data[pos] & 0xFF) - 2;
/* 387 */         pos++;
/*     */ 
/* 389 */         byte[] buf = new byte[len];
/* 390 */         for (int l = 0; l < buf.length; l++) {
/* 391 */           if (pos + l >= data.length) {
/*     */             break;
/*     */           }
/* 394 */           buf[l] = data[(pos + l)];
/*     */         }
/* 396 */         pos += len;
/* 397 */         if (type == 11) {
/* 398 */           mac = new String(buf);
/* 399 */           break;
/*     */         }
/* 401 */         num++;
/*     */       }
/*     */     }
/*     */ 
/* 405 */     return mac;
/*     */   }
/*     */ 
/*     */   private static String bytesToIp(byte[] bytes)
/*     */   {
			return (bytes[0] & 0xFF) + '.' 
					+ (bytes[1] & 0xFF) + '.' 
						+ (bytes[2] & 0xFF) + '.'
							+ (bytes[3] & 0xFF)+"";
/*     */   }
/*     */ 
/*     */   private void doLinkRecord(String[] loginInfo, String info)
/*     */   {
/*     */     try
/*     */     {
/* 476 */       String type = loginInfo[6];
/* 477 */       String ubip = loginInfo[10];
/* 478 */       Portalbas basconfig = (Portalbas)config.getConfigMap().get(ubip);
/* 479 */       if ((basconfig != null) && 
/* 480 */         ("1".equals(type)) && ("1".equals(basconfig.getIsPortalCheck()))) {
/* 481 */         String uid = loginInfo[1];
/* 482 */         String rid = loginInfo[2];
/* 483 */         if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
/* 484 */           return;
/*     */         }
/* 486 */         Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
/* 487 */         Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
/* 488 */         if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || (recordId.longValue() == 0L)) {
/* 489 */           return;
/*     */         }
/* 491 */         PortallinkrecordService linkRecordService = (PortallinkrecordService)ac
/* 492 */           .getBean("portallinkrecordServiceImpl");
/* 493 */         PortalaccountService accountService = (PortalaccountService)ac
/* 494 */           .getBean("portalaccountServiceImpl");
/* 495 */         Portallinkrecord linkRecord = linkRecordService
/* 496 */           .getPortallinkrecordByKey(recordId);
/* 497 */         Portalaccount account = accountService.getPortalaccountByKey(userId);
/* 498 */         String state = account.getState();
/*     */ 
/* 500 */         long in = Long.valueOf(loginInfo[7]).longValue();
/* 501 */         long out = Long.valueOf(loginInfo[8]).longValue();
/* 502 */         long octets = in + out;
/* 503 */         linkRecord.setIns(Long.valueOf(in));
/* 504 */         linkRecord.setOuts(Long.valueOf(out));
/* 505 */         linkRecord.setOctets(Long.valueOf(octets));
/* 506 */         Date now = new Date();
/* 507 */         linkRecord.setEndDate(now);
/* 508 */         Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
/* 509 */         linkRecord.setTime(costTime);
/* 510 */         linkRecord.setState(state);
/* 511 */         linkRecord.setEx1("Portal");
/* 512 */         linkRecord.setEx2(info);
/* 513 */         linkRecordService.updatePortallinkrecordByKey(linkRecord);
/*     */ 
/* 515 */         if (!state.equals("1")) {
/* 516 */           Long haveTime = account.getTime();
/* 517 */           Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
/* 518 */           if ((state.equals("3")) && 
/* 519 */             (account.getDate().getTime() <= now.getTime())) {
/* 520 */             account.setState("2");
/* 521 */             accountService.updatePortalaccountByKey(account);
/*     */           }
/*     */ 
/* 524 */           if (state.equals("2")) {
/* 525 */             if (newHaveTime.longValue() <= 0L) {
/* 526 */               account.setState("4");
/*     */             }
/* 528 */             account.setTime(newHaveTime);
/* 529 */             accountService.updatePortalaccountByKey(account);
/*     */           }
/* 531 */           if ((state.equals("4")) || (state.equals("0"))) {
/* 532 */             long haveOctets = account.getOctets().longValue() - octets;
/* 533 */             if (haveOctets <= 0L) {
/* 534 */               account.setState("0");
/*     */             }
/* 536 */             account.setOctets(Long.valueOf(haveOctets));
/* 537 */             accountService.updatePortalaccountByKey(account);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doLogRecord(String info)
/*     */   {
/*     */     try
/*     */     {
/* 551 */       Portallogrecord logRecord = new Portallogrecord();
/* 552 */       Date nowDate = new Date();
/* 553 */       logRecord.setInfo(info);
/* 554 */       logRecord.setRecDate(nowDate);
/* 555 */       PortallogrecordService logRecordService = (PortallogrecordService)ac
/* 556 */         .getBean("portallogrecordServiceImpl");
/* 557 */       logRecordService.addPortallogrecord(logRecord);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String DomainToIP(String domain) {
/* 565 */     if (configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
/* 566 */       return domain;
/*     */     }
/* 568 */     String ip = "";
/*     */     try {
/* 570 */       ip = InetAddress.getByName(domain).toString().split("/")[1];
/*     */     } catch (UnknownHostException e) {
/* 572 */       log.error("DomainToIP ERROR!!");
/* 573 */       log.error("==============ERROR Start=============");
/* 574 */       log.error(e);
/* 575 */       log.error("ERROR INFO ", e);
/* 576 */       log.error("==============ERROR End=============");
/*     */     }
/* 578 */     System.out.println("Domain:" + domain + " IP:" + ip);
/* 579 */     return ip;
/*     */   }
/*     */ 
/*     */   public static void openServer() {
/*     */     try {
/* 584 */       socket = new DatagramSocket(configService.getConfigByKey(Long.valueOf(1L)).getPortalPort().intValue());
/* 585 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 586 */         log.info("PortalServer Service Start Success , Listen Portal UDP Default 50100 !!");
/*     */       }
/*     */ 
/* 589 */       while (isRun.booleanValue()) {
/* 590 */         byte[] b = new byte[100];
/* 591 */         DatagramPacket data = new DatagramPacket(b, b.length);
/* 592 */         socket.receive(data);
/* 593 */         new ReportServer(data).start();
/*     */       }
/*     */     } catch (Exception e) {
/* 596 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 597 */         log.error("==============ERROR Start=============");
/* 598 */         log.error(e);
/* 599 */         log.error("ERROR INFO ", e);
/* 600 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeServer()
/*     */   {
/*     */     try
/*     */     {
/* 609 */       isRun = Boolean.valueOf(false);
/* 610 */       if (socket != null) {
/* 611 */         socket.close();
/* 612 */         socket = null;
/*     */       }
/*     */     } catch (Exception e) {
/* 615 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 616 */         log.error("==============ERROR Start=============");
/* 617 */         log.error(e);
/* 618 */         log.error("ERROR INFO ", e);
/* 619 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.ReportServer
 * JD-Core Version:    0.6.2
 */