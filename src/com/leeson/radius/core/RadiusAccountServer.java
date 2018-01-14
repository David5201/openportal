/*     */ package com.leeson.radius.core;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Config;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import com.leeson.radius.core.model.RadiusNasMap;
/*     */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*     */ import com.leeson.radius.core.utils.COAThread;
/*     */ import com.leeson.radius.core.utils.DoRecord;
/*     */ import java.io.PrintStream;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class RadiusAccountServer
/*     */   implements Runnable
/*     */ {
/*  26 */   private int listenPort = 1813;
/*     */ 
/*  33 */   private boolean isRun = false;
/*     */ 
/*  35 */   private DatagramSocket server = null;
/*     */   private PortalaccountService portalaccountService;
/*     */   private ConfigService configService;
/*     */ 
/*     */   public RadiusAccountServer()
/*     */   {
/*  42 */     this.portalaccountService = ((PortalaccountService)
/*  43 */       SpringContextHelper.getBean("portalaccountServiceImpl"));
/*  44 */     this.configService = ((ConfigService)
/*  45 */       SpringContextHelper.getBean("configServiceImpl"));
/*     */   }
/*     */ 
/*     */   public void finalize() {
/*  49 */     stop();
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try {
/*  55 */       if (readConfig())
/*     */       {
/*  57 */         if (startRadius()) {
/*  58 */           this.isRun = true;
/*  59 */           while (this.isRun)
/*     */             try {
/*  61 */               byte[] buf = new byte[4096];
/*  62 */               DatagramPacket in = new DatagramPacket(buf, 4096);
/*  63 */               this.server.receive(in);
/*  64 */               new ReceiveAccountThread(this, in);
/*     */             } catch (Exception e1) {
/*  66 */               Tool.writeErrorLog("Accounting Error", e1);
/*     */             }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  72 */       Tool.writeErrorLog("Accounting Error", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean readConfig()
/*     */   {
/*  78 */     boolean ret = true;
/*  79 */     return ret;
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/*  84 */     this.isRun = false;
/*     */     try {
/*  86 */       if (this.server != null) {
/*  87 */         this.server.close();
/*  88 */         this.server = null;
/*     */       }
/*     */     } catch (Exception e) {
/*  91 */       Tool.writeErrorLog("Accounting Error", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean startRadius()
/*     */   {
/* 105 */     boolean ret = false;
/*     */     try {
/* 107 */       Integer port = this.configService.getConfigByKey(Long.valueOf(1L)).getAcctPort();
/* 108 */       if (port != null)
/* 109 */         this.listenPort = port.intValue();
/*     */       else {
/* 111 */         this.listenPort = 1813;
/*     */       }
/* 113 */       if (this.listenPort > 0) {
/* 114 */         this.server = new DatagramSocket(this.listenPort);
/* 115 */         ret = true;
/*     */       }
/*     */     } catch (Exception e) {
/* 118 */       Tool.writeErrorLog("Accounting Error", e);
/*     */     }
/* 120 */     Tool.writeLog("Radius Accounting Service Start", ret ? " Success" : 
/* 121 */       " fail");
/* 122 */     return ret;
/*     */   }
/*     */ 
/*     */   public void receive(DatagramPacket in)
/*     */   {
/*     */     try {
/* 128 */       String ip = in.getAddress().getHostAddress();
/* 129 */       int port = in.getPort();
/*     */ 
/* 132 */       byte[] inData = in.getData();
/*     */ 
/* 134 */       int code = inData[0];
/*     */ 
/* 136 */       byte[] id = new byte[1];
/* 137 */       id[0] = inData[1];
/* 138 */       int identifier = Tool.ByteToInt(id);
/*     */ 
/* 142 */       int length = in.getLength();
/*     */ 
/* 147 */       String authenticator = Tool.ByteToHex(inData, 4, 20);
/*     */ 
/* 149 */       String attributes = Tool.ByteToHex(inData, 20, length);
/* 150 */       Tool.writeLog("Accounting Receive ", "ip=" + ip + ",port=" + port + 
/* 151 */         ",code=" + code + ",identifier=" + identifier + 
/* 152 */         ",length=" + length + ",authenticator=" + authenticator + 
/* 153 */         ",attributes=" + attributes);
/* 154 */       String[][] attributesList = null;
/* 155 */       if ((attributes != null) && (attributes.length() > 0)) {
/* 156 */         attributesList = Tool.getAttributes(attributes);
/*     */       }
/* 158 */       byte[] outData = optionData(code, ip, port, identifier, 
/* 159 */         authenticator, attributesList);
/* 160 */       if (outData != null)
/*     */       {
/* 163 */         int lengthTo = outData.length;
/*     */ 
/* 165 */         String attributesTo = Tool.ByteToHex(outData, 20, lengthTo);
/*     */ 
/* 167 */         String[][] attributesListTo = null;
/* 168 */         if ((attributesTo != null) && (attributesTo.length() > 0)) {
/* 169 */           attributesListTo = Tool.getAttributes(attributesTo);
/*     */         }
/* 171 */         if ((attributesListTo != null) && (attributesListTo.length > 0)) {
/* 172 */           for (int i = 0; i < attributesListTo.length; i++) {
/*     */             try {
/* 174 */               int type = Integer.parseInt(attributesListTo[i][0], 
/* 175 */                 16);
/* 176 */               Tool.getAttributeValue(ip, type, 
/* 177 */                 attributesListTo[i][1]).trim();
/*     */             } catch (Exception e) {
/* 179 */               Tool.writeErrorLog("Accounting Error", e);
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 185 */         DatagramPacket out = new DatagramPacket(outData, 
/* 186 */           outData.length, in.getSocketAddress());
/* 187 */         this.server.send(out);
/* 188 */         Tool.writeLog(ip, "Accounting Send OK !!");
/*     */       }
/*     */     } catch (Exception e) {
/* 191 */       Tool.writeErrorLog("Accounting Error", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] optionData(int code, String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*     */   {
/* 197 */     byte[] ret = null;
/*     */     try {
/* 199 */       switch (code)
/*     */       {
/*     */       case 4:
/* 202 */         Tool.writeLog(ip, "Accounting>>Accounting-Request");
/* 203 */         ret = accountingRequest(ip, port, identifier, authenticator, 
/* 204 */           attributesList);
/*     */ 
/* 206 */         break;
/*     */       default:
/* 208 */         Tool.writeLog(ip, "Accounting code ERROR (" + code + ")");
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 213 */       Tool.writeErrorLog("Accounting Error", e);
/*     */     }
/* 215 */     return ret;
/*     */   }
/*     */ 
/*     */   private byte[] accountingRequest(String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*     */   {
/* 221 */     byte[] ret = null;
/*     */     try {
/* 223 */       String inS = "0";
/* 224 */       String outS = "0";
/* 225 */       String name = null;
/* 226 */       String userIP = null;
/* 227 */       String nasIP = null;
/* 228 */       String AcctSessionId = null;
/* 229 */       String CallingStationId = null;
/* 230 */       String AcctSessionTime = null;
/* 231 */       String AcctType = null;
/* 232 */       String NasIdentifier = "";
/* 233 */       for (int i = 0; i < attributesList.length; i++) {
/*     */         try {
/* 235 */           int type = Integer.parseInt(attributesList[i][0], 16);
/* 236 */           String value = Tool.getAttributeValue(ip, type, 
/* 237 */             attributesList[i][1]).trim();
/* 238 */           switch (type)
/*     */           {
/*     */           case 1:
/* 241 */             name = value;
/*     */ 
/* 243 */             break;
/*     */           case 4:
/* 246 */             nasIP = value;
/*     */ 
/* 248 */             break;
/*     */           case 5:
/* 253 */             break;
/*     */           case 8:
/* 256 */             userIP = value;
/*     */ 
/* 258 */             break;
/*     */           case 31:
/* 261 */             CallingStationId = value;
/*     */ 
/* 263 */             break;
/*     */           case 32:
/* 266 */             NasIdentifier = value;
/*     */ 
/* 268 */             break;
/*     */           case 40:
/* 271 */             AcctType = value;
/*     */ 
/* 273 */             break;
/*     */           case 42:
/* 276 */             inS = value;
/*     */ 
/* 278 */             break;
/*     */           case 43:
/* 281 */             outS = value;
/*     */ 
/* 283 */             break;
/*     */           case 44:
/* 286 */             AcctSessionId = value;
/*     */ 
/* 288 */             break;
/*     */           case 46:
/* 291 */             AcctSessionTime = value;
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (Exception e1)
/*     */         {
/* 299 */           Tool.writeErrorLog("Accounting Error", e1);
/*     */         }
/*     */       }
/*     */ 
/* 303 */       if (stringUtils.isNotBlank(CallingStationId)) {
/* 304 */         CallingStationId = PortalUtil.MacFormat(CallingStationId);
/*     */       }
/*     */       try
/*     */       {
/* 308 */         if (userIP != null) {
/* 309 */           String[] accountInfo = null;
/* 310 */           Iterator iterator = OnlineMap.getInstance()
/* 311 */             .getOnlineUserMap().keySet()
/* 312 */             .iterator();
/* 313 */           while (iterator.hasNext()) {
/* 314 */             Object o = iterator.next();
/* 315 */             String t = o.toString();
/* 316 */             if (t.contains(userIP + ":")) {
/* 317 */               accountInfo = 
/* 318 */                 (String[])OnlineMap.getInstance()
/* 318 */                 .getOnlineUserMap().get(t);
/* 319 */               accountInfo[7] = inS;
/* 320 */               accountInfo[8] = outS;
/* 321 */               OnlineMap.getInstance().getOnlineUserMap()
/* 322 */                 .put(t, accountInfo);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 328 */         Tool.writeErrorLog("Accounting Error", e);
/*     */       }
/*     */ 
/* 331 */       String attributes = "";
/* 332 */       int code = 5;
/*     */ 
/* 334 */       String client = "";
/* 335 */       String clientType = "";
/* 336 */       String coa = "3799";
/* 337 */       String nasname = NasIdentifier;
/* 338 */       String[] clients = null;
/* 339 */       if (stringUtils.isNotBlank(nasIP)) {
/* 340 */         clients = (String[])RadiusNasMap.getInstance().getNasMap().get(nasIP);
/*     */       }
/* 342 */       if ((clients != null) && (clients.length > 0)) {
/* 343 */         client = clients[0];
/* 344 */         clientType = clients[1];
/* 345 */         coa = clients[8];
/* 346 */         nasname = clients[7];
/*     */       }
/* 348 */       else if ((stringUtils.isNotBlank(ip)) && 
/* 349 */         (!ip.equals(nasIP))) {
/* 350 */         Iterator iterator = RadiusNasMap.getInstance().getNasMap().keySet().iterator();
/* 351 */         while (iterator.hasNext()) {
/* 352 */           Object o = iterator.next();
/* 353 */           String t = o.toString();
/* 354 */           if (ip.equals(DomainToIP(t))) {
/* 355 */             clients = (String[])RadiusNasMap.getInstance().getNasMap().get(t);
/* 356 */             break;
/*     */           }
/*     */         }
/* 359 */         if ((clients != null) && (clients.length > 0)) {
/* 360 */           client = clients[0];
/* 361 */           clientType = clients[1];
/* 362 */           coa = clients[8];
/* 363 */           nasname = clients[7];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 368 */       if ((stringUtils.isBlank(client)) && 
/* 369 */         (NasIdentifier != null) && (NasIdentifier != "")) {
/* 370 */         Iterator iterator = RadiusNasMap.getInstance().getNasMap().keySet()
/* 371 */           .iterator();
/* 372 */         while (iterator.hasNext()) {
/* 373 */           Object o = iterator.next();
/* 374 */           String t = o.toString();
/* 375 */           String[] temp = (String[])RadiusNasMap.getInstance().getNasMap().get(t);
/* 376 */           if ((temp.length > 0) && (NasIdentifier.equals(temp[7]))) {
/* 377 */             clients = temp;
/* 378 */             break;
/*     */           }
/*     */         }
/* 381 */         if ((clients != null) && (clients.length > 0)) {
/* 382 */           client = clients[0];
/* 383 */           clientType = clients[1];
/* 384 */           coa = clients[8];
/* 385 */           nasname = clients[7];
/*     */         }
/*     */       }
/*     */ 
/* 389 */       if (stringUtils.isBlank(client)) {
/* 390 */         client = Tool.ByteToHex("OpenPortal".getBytes());
/*     */       }
/* 392 */       if (stringUtils.isBlank(clientType)) {
/* 393 */         clientType = "0";
/*     */       }
/*     */ 
/* 396 */       if (client != null) {
/* 397 */         String sharedSecret = client;
/* 398 */         if (sharedSecret != null) {
/* 399 */           Tool.writeLog(ip, "Accounting-Request Print Finish !!");
/* 400 */           ret = Tool.getOutData("accountingResponse", sharedSecret, 
/* 401 */             ip, port, code, identifier, authenticator, 
/* 402 */             attributes);
/*     */ 
/* 404 */           Date nowDate = new Date();
/* 405 */           String nowS = ThreadLocalDateUtil.format(nowDate);
/*     */ 
/* 407 */           if ((stringUtils.isNotBlank(AcctSessionId)) && 
/* 408 */             (stringUtils.isNotBlank(AcctType))) {
/* 409 */             PortalaccountQuery aq = new PortalaccountQuery();
/* 410 */             aq.setLoginName(name);
/* 411 */             aq.setLoginNameLike(false);
/* 412 */             List users = this.portalaccountService
/* 413 */               .getPortalaccountList(aq);
/* 414 */             String userState = "0";
/* 415 */             Date userDate = new Date();
/* 416 */             long userTime = 0L;
/* 417 */             long now = userDate.getTime();
/* 418 */             int sessionTime = 0;
/* 419 */             long octets = 0L;
/* 420 */             if ((users != null) && (users.size() > 0)) {
/* 421 */               Portalaccount user = (Portalaccount)users.get(0);
/* 422 */               userState = user.getState();
/* 423 */               userDate = user.getDate();
/* 424 */               userTime = user.getTime().longValue();
/* 425 */               if ("0".equals(userState)) {
/* 426 */                 sessionTime = 0;
/*     */               }
/*     */ 
/* 432 */               if ("1".equals(userState)) {
/* 433 */                 sessionTime = (int)(userTime / 1000L);
/* 434 */                 if (sessionTime <= 0) {
/* 435 */                   sessionTime = 86400;
/*     */                 }
/*     */               }
/* 438 */               if ("3".equals(userState)) {
/* 439 */                 sessionTime = (int)((userDate.getTime() - now) / 1000L);
/* 440 */                 if (sessionTime <= 0) {
/* 441 */                   user.setState("2");
/* 442 */                   this.portalaccountService
/* 443 */                     .updatePortalaccountByKey(user);
/* 444 */                   userState = "2";
/*     */                 }
/*     */               }
/* 447 */               if ("2".equals(userState)) {
/* 448 */                 sessionTime = (int)(userTime / 1000L);
/* 449 */                 if (sessionTime <= 0) {
/* 450 */                   user.setState("4");
/* 451 */                   this.portalaccountService
/* 452 */                     .updatePortalaccountByKey(user);
/* 453 */                   userState = "4";
/*     */                 }
/*     */               }
/* 456 */               if ("4".equals(userState)) {
/* 457 */                 sessionTime = 86400;
/* 458 */                 octets = user.getOctets().longValue();
/* 459 */                 if (octets <= 0L) {
/* 460 */                   user.setState("0");
/* 461 */                   this.portalaccountService
/* 462 */                     .updatePortalaccountByKey(user);
/* 463 */                   sessionTime = 0;
/* 464 */                   octets = 0L;
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 469 */               if ("Start(1)".equals(AcctType)) {
/* 470 */                 if (stringUtils.isBlank(nasIP)) {
/* 471 */                   nasIP = ip;
/*     */                 }
/* 473 */                 String[] radiusOnlineInfo = new String[18];
/* 474 */                 radiusOnlineInfo[0] = nasIP;
/* 475 */                 radiusOnlineInfo[1] = ip;
/* 476 */                 radiusOnlineInfo[2] = userIP;
/* 477 */                 radiusOnlineInfo[3] = CallingStationId;
/* 478 */                 radiusOnlineInfo[4] = name;
/* 479 */                 radiusOnlineInfo[5] = sharedSecret;
/* 480 */                 radiusOnlineInfo[6] = 
/* 481 */                   String.valueOf(sessionTime);
/* 482 */                 radiusOnlineInfo[7] = String.valueOf(octets);
/* 483 */                 radiusOnlineInfo[8] = clientType;
/* 484 */                 radiusOnlineInfo[9] = nowS;
/* 485 */                 radiusOnlineInfo[10] = "0";
/* 486 */                 radiusOnlineInfo[11] = "0";
/* 487 */                 radiusOnlineInfo[12] = "0";
/* 488 */                 radiusOnlineInfo[13] = AcctSessionId;
/* 489 */                 radiusOnlineInfo[14] = nowS;
/* 490 */                 radiusOnlineInfo[15] = userState;
/* 491 */                 radiusOnlineInfo[16] = nasname;
/* 492 */                 radiusOnlineInfo[17] = coa;
/* 493 */                 RadiusOnlineMap.getInstance()
/* 494 */                   .getRadiusOnlineMap()
/* 495 */                   .put(AcctSessionId, radiusOnlineInfo);
/*     */               }
/*     */ 
/* 500 */               if (("Interim-Update(3)".equals(AcctType)) || ("Stop(2)".equals(AcctType))) {
/* 501 */                 String[] radiusOnlineInfo = 
/* 503 */                   (String[])RadiusOnlineMap.getInstance().getRadiusOnlineMap()
/* 503 */                   .get(AcctSessionId);
/* 504 */                 if ((radiusOnlineInfo != null) && 
/* 505 */                   (radiusOnlineInfo.length > 0)) {
/* 506 */                   if (stringUtils.isNotBlank(AcctSessionTime)) {
/* 507 */                     radiusOnlineInfo[10] = AcctSessionTime;
/*     */                   }
/* 509 */                   if (stringUtils.isNotBlank(inS)) {
/* 510 */                     radiusOnlineInfo[11] = inS;
/*     */                   }
/* 512 */                   if (stringUtils.isNotBlank(outS)) {
/* 513 */                     radiusOnlineInfo[12] = outS;
/*     */                   }
/* 515 */                   radiusOnlineInfo[14] = nowS;
/* 516 */                   radiusOnlineInfo[1] = ip;
/* 517 */                   radiusOnlineInfo[16] = nasname;
/* 518 */                   radiusOnlineInfo[17] = clients[8];
/*     */ 
/* 521 */                   RadiusOnlineMap.getInstance()
/* 522 */                     .getRadiusOnlineMap()
/* 523 */                     .put(AcctSessionId, 
/* 524 */                     radiusOnlineInfo);
/*     */ 
/* 526 */                   if ("Interim-Update(3)".equals(AcctType))
/*     */                   {
/* 529 */                     if ("4".equals(userState)) {
/*     */                       try {
/* 531 */                         long in = Long.valueOf(inS).longValue();
/* 532 */                         long out = Long.valueOf(outS).longValue();
/* 533 */                         long all = in + out;
/* 534 */                         if (all > octets)
/* 535 */                           COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Octets Over COA");
/*     */                       }
/*     */                       catch (Exception localException1)
/*     */                       {
/*     */                       }
/*     */                     }
/* 541 */                     if ("2".equals(userState)) {
/*     */                       try {
/* 543 */                         long costTime = Long.valueOf(AcctSessionTime).longValue() * 1000L;
/* 544 */                         if (userTime <= costTime)
/* 545 */                           COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Time Over COA");
/*     */                       }
/*     */                       catch (Exception localException2)
/*     */                       {
/*     */                       }
/*     */                     }
/* 551 */                     if ("3".equals(userState)) {
/*     */                       try {
/* 553 */                         if (userDate.getTime() - now <= 0L) {
/* 554 */                           COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Date Over COA");
/*     */                         }
/*     */                       }
/*     */                       catch (Exception localException3)
/*     */                       {
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 566 */               if ("Stop(2)".equals(AcctType))
/*     */               {
/* 568 */                 DoRecord.coreMethod(AcctSessionId, 
/* 569 */                   "Radius Account Stop");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 577 */       Tool.writeErrorLog("Accounting Error", e);
/*     */     }
/* 579 */     return ret;
/*     */   }
/*     */ 
/*     */   private String DomainToIP(String domain) {
/* 583 */     if (this.configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
/* 584 */       return domain;
/*     */     }
/* 586 */     String ip = "";
/*     */     try {
/* 588 */       ip = InetAddress.getByName(domain).toString().split("/")[1];
/*     */     } catch (UnknownHostException e) {
/* 590 */       Tool.writeErrorLog("Radius DomainToIP ERROR INFO ", e);
/*     */     }
/* 592 */     System.out.println("Domain:" + domain + " IP:" + ip);
/* 593 */     return ip;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusAccountServer
 * JD-Core Version:    0.6.2
 */