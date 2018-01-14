/*      */ package com.leeson.radius.core;
/*      */ 
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Config;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalspeed;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalspeedService;
/*      */ import com.leeson.portal.core.model.isDo;
/*      */ import com.leeson.portal.core.service.utils.ChapPassword;
/*      */ import com.leeson.portal.core.service.utils.PortalUtil;
/*      */ import com.leeson.portal.core.utils.SpringContextHelper;
/*      */ import com.leeson.radius.core.model.RadiusNasMap;
/*      */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*      */ import com.leeson.radius.core.utils.COAThread;
/*      */ import com.leeson.radius.core.utils.MacLimit;
/*      */ import com.leeson.radius.core.utils.OnlineLimit;
/*      */ import java.io.PrintStream;
/*      */ import java.net.DatagramPacket;
/*      */ import java.net.DatagramSocket;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ public class RadiusServer
/*      */   implements Runnable
/*      */ {
/*   30 */   private int listenPort = 1812;
/*      */ 
/*   34 */   private boolean isRun = false;
/*      */ 
/*   36 */   private DatagramSocket server = null;
/*      */   private PortalaccountService portalaccountService;
/*      */   private PortalspeedService speedService;
/*      */   private ConfigService configService;
/*      */ 
/*      */   public RadiusServer()
/*      */   {
/*   45 */     this.portalaccountService = ((PortalaccountService)
/*   46 */       SpringContextHelper.getBean("portalaccountServiceImpl"));
/*   47 */     this.speedService = ((PortalspeedService)
/*   48 */       SpringContextHelper.getBean("portalspeedServiceImpl"));
/*   49 */     this.configService = ((ConfigService)
/*   50 */       SpringContextHelper.getBean("configServiceImpl"));
/*      */   }
/*      */ 
/*      */   public void finalize() {
/*   54 */     stop();
/*      */   }
/*      */ 
/*      */   public void run()
/*      */   {
/*      */     try {
/*   60 */       if (readConfig())
/*      */       {
/*   62 */         if (startRadius()) {
/*   63 */           this.isRun = true;
/*   64 */           while (this.isRun)
/*      */             try {
/*   66 */               byte[] buf = new byte[4096];
/*   67 */               DatagramPacket in = new DatagramPacket(buf, 4096);
/*   68 */               this.server.receive(in);
/*   69 */               new ReceiveThread(this, in);
/*      */             } catch (Exception e1) {
/*   71 */               Tool.writeErrorLog("Error", e1);
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*   77 */       Tool.writeErrorLog("Error", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean readConfig()
/*      */   {
/*   83 */     boolean ret = true;
/*   84 */     return ret;
/*      */   }
/*      */ 
/*      */   public void stop()
/*      */   {
/*   89 */     this.isRun = false;
/*      */     try {
/*   91 */       if (this.server != null) {
/*   92 */         this.server.close();
/*   93 */         this.server = null;
/*      */       }
/*      */     } catch (Exception e) {
/*   96 */       Tool.writeErrorLog("Error", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean startRadius()
/*      */   {
/*  102 */     boolean ret = false;
/*      */     try {
/*  104 */       Integer port = this.configService.getConfigByKey(Long.valueOf(1L)).getAuthPort();
/*  105 */       if (port != null)
/*  106 */         this.listenPort = port.intValue();
/*      */       else {
/*  108 */         this.listenPort = 1812;
/*      */       }
/*  110 */       if (this.listenPort > 0) {
/*  111 */         this.server = new DatagramSocket(this.listenPort);
/*  112 */         ret = true;
/*      */       }
/*      */     } catch (Exception e) {
/*  115 */       Tool.writeErrorLog("error", e);
/*      */     }
/*  117 */     Tool.writeLog("Radius Service Start", ret ? " Success" : " fail");
/*  118 */     return ret;
/*      */   }
/*      */ 
/*      */   public void receive(DatagramPacket in)
/*      */   {
/*      */     try {
/*  124 */       String ip = in.getAddress().getHostAddress();
/*  125 */       int port = in.getPort();
/*      */ 
/*  128 */       byte[] inData = in.getData();
/*      */ 
/*  130 */       int code = inData[0];
/*      */ 
/*  132 */       byte[] id = new byte[1];
/*  133 */       id[0] = inData[1];
/*  134 */       int identifier = Tool.ByteToInt(id);
/*      */ 
/*  138 */       int length = in.getLength();
/*      */ 
/*  143 */       String authenticator = Tool.ByteToHex(inData, 4, 20);
/*      */ 
/*  145 */       String attributes = Tool.ByteToHex(inData, 20, length);
/*  146 */       Tool.writeLog("Receive ", "ip=" + ip + ",port=" + port + ",code=" + 
/*  147 */         code + ",identifier=" + identifier + ",length=" + length + 
/*  148 */         ",authenticator=" + authenticator + ",attributes=" + 
/*  149 */         attributes);
/*  150 */       String[][] attributesList = null;
/*  151 */       if ((attributes != null) && (attributes.length() > 0)) {
/*  152 */         attributesList = Tool.getAttributes(attributes);
/*      */       }
/*  154 */       byte[] outData = optionData(code, ip, port, identifier, 
/*  155 */         authenticator, attributesList);
/*  156 */       if (outData != null)
/*      */       {
/*  159 */         int lengthTo = outData.length;
/*      */ 
/*  161 */         String attributesTo = Tool.ByteToHex(outData, 20, lengthTo);
/*      */ 
/*  163 */         String[][] attributesListTo = null;
/*  164 */         if ((attributesTo != null) && (attributesTo.length() > 0)) {
/*  165 */           attributesListTo = Tool.getAttributes(attributesTo);
/*      */         }
/*  167 */         if ((attributesListTo != null) && (attributesListTo.length > 0)) {
/*  168 */           for (int i = 0; i < attributesListTo.length; i++) {
/*      */             try {
/*  170 */               int type = Integer.parseInt(attributesListTo[i][0], 
/*  171 */                 16);
/*  172 */               Tool.getAttributeValue(ip, type, 
/*  173 */                 attributesListTo[i][1]).trim();
/*      */             } catch (Exception e) {
/*  175 */               Tool.writeErrorLog("Error", e);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  181 */         DatagramPacket out = new DatagramPacket(outData, 
/*  182 */           outData.length, in.getSocketAddress());
/*  183 */         this.server.send(out);
/*  184 */         Tool.writeLog(ip, "Send OK !!");
/*      */       }
/*      */     } catch (Exception e) {
/*  187 */       Tool.writeErrorLog("error", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private byte[] optionData(int code, String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*      */   {
/*  193 */     byte[] ret = null;
/*      */     try {
/*  195 */       switch (code)
/*      */       {
/*      */       case 1:
/*  198 */         Tool.writeLog(ip, ">>Access-Request");
/*  199 */         ret = accessRequest(ip, port, identifier, authenticator, 
/*  200 */           attributesList);
/*      */ 
/*  202 */         break;
/*      */       case 11:
/*  205 */         Tool.writeLog(ip, ">>Access-Challenge");
/*  206 */         ret = accessChallenge(ip, port, identifier, authenticator, 
/*  207 */           attributesList);
/*      */ 
/*  209 */         break;
/*      */       case 12:
/*  212 */         Tool.writeLog(ip, ">>Status-Server");
/*  213 */         ret = statusServer(ip, port, identifier, authenticator, 
/*  214 */           attributesList);
/*      */ 
/*  216 */         break;
/*      */       case 13:
/*  219 */         Tool.writeLog(ip, ">>Status-client");
/*  220 */         ret = statusClient(ip, port, identifier, authenticator, 
/*  221 */           attributesList);
/*      */ 
/*  223 */         break;
/*      */       case 255:
/*  226 */         Tool.writeLog(ip, ">>Reserved");
/*  227 */         ret = reserved(ip, port, identifier, authenticator, 
/*  228 */           attributesList);
/*      */ 
/*  230 */         break;
/*      */       default:
/*  232 */         Tool.writeLog(ip, "code ERROR (" + code + ")");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  237 */       Tool.writeErrorLog("Error", e);
/*      */     }
/*  239 */     return ret;
/*      */   }
/*      */ 
/*      */   private byte[] accessRequest(String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*      */   {
/*  245 */     byte[] ret = null;
/*      */     try {
/*  247 */       String name = null;
/*  248 */       String password = null;
/*  249 */       String userIP = null;
/*  250 */       String Challenge = null;
/*  251 */       String nasIP = null;
/*  252 */       String AcctSessionId = null;
/*  253 */       String CallingStationId = null;
/*  254 */       String NasIdentifier = "";
/*  255 */       int chap = 0;
/*  256 */       for (int i = 0; i < attributesList.length; i++) {
/*      */         try {
/*  258 */           int type = Integer.parseInt(attributesList[i][0], 16);
/*  259 */           String value = Tool.getAttributeValue(ip, type, 
/*  260 */             attributesList[i][1]).trim();
/*  261 */           switch (type)
/*      */           {
/*      */           case 1:
/*  264 */             name = value;
/*      */ 
/*  266 */             break;
/*      */           case 2:
/*  269 */             password = value;
/*      */ 
/*  271 */             break;
/*      */           case 3:
/*  274 */             password = value;
/*  275 */             chap = 1;
/*      */ 
/*  277 */             break;
/*      */           case 4:
/*  280 */             nasIP = value;
/*      */ 
/*  282 */             break;
/*      */           case 5:
/*  287 */             break;
/*      */           case 8:
/*  290 */             userIP = value;
/*      */ 
/*  292 */             break;
/*      */           case 31:
/*  295 */             CallingStationId = value;
/*      */ 
/*  297 */             break;
/*      */           case 32:
/*  300 */             NasIdentifier = value;
/*      */ 
/*  302 */             break;
/*      */           case 44:
/*  305 */             AcctSessionId = value;
/*      */ 
/*  307 */             break;
/*      */           case 60:
/*  310 */             Challenge = value;
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (Exception e1)
/*      */         {
/*  318 */           Tool.writeErrorLog("Error", e1);
/*      */         }
/*      */       }
/*  321 */       if (stringUtils.isNotBlank(CallingStationId)) {
/*  322 */         CallingStationId = PortalUtil.MacFormat(CallingStationId);
/*      */       }
/*  324 */       if ((name != null) && (password != null) && (ip != null)) {
/*  325 */         String username = name;
/*  326 */         String client = "";
/*  327 */         String clientType = "";
/*  328 */         String acctInterimInterval = "";
/*  329 */         String idleTimeout = "";
/*  330 */         String autoKick = "";
/*  331 */         String[] clients = null;
/*  332 */         if (stringUtils.isNotBlank(nasIP)) {
/*  333 */           clients = (String[])RadiusNasMap.getInstance().getNasMap().get(nasIP);
/*      */         }
/*  335 */         if ((clients != null) && (clients.length > 0)) {
/*  336 */           client = clients[0];
/*  337 */           clientType = clients[1];
/*  338 */           acctInterimInterval = clients[3];
/*  339 */           idleTimeout = clients[5];
/*  340 */           autoKick = clients[6];
/*      */         }
/*  342 */         else if ((stringUtils.isNotBlank(ip)) && 
/*  343 */           (!ip.equals(nasIP))) {
/*  344 */           Iterator iterator = RadiusNasMap.getInstance().getNasMap().keySet()
/*  345 */             .iterator();
/*  346 */           while (iterator.hasNext()) {
/*  347 */             Object o = iterator.next();
/*  348 */             String t = o.toString();
/*  349 */             if (ip.equals(DomainToIP(t))) {
/*  350 */               clients = (String[])RadiusNasMap.getInstance().getNasMap().get(t);
/*  351 */               break;
/*      */             }
/*      */           }
/*  354 */           if ((clients != null) && (clients.length > 0)) {
/*  355 */             client = clients[0];
/*  356 */             clientType = clients[1];
/*  357 */             acctInterimInterval = clients[3];
/*  358 */             idleTimeout = clients[5];
/*  359 */             autoKick = clients[6];
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  364 */         if ((stringUtils.isBlank(client)) && 
/*  365 */           (NasIdentifier != null) && (NasIdentifier != "")) {
/*  366 */           Iterator iterator = RadiusNasMap.getInstance().getNasMap().keySet()
/*  367 */             .iterator();
/*  368 */           while (iterator.hasNext()) {
/*  369 */             Object o = iterator.next();
/*  370 */             String t = o.toString();
/*  371 */             String[] temp = (String[])RadiusNasMap.getInstance().getNasMap().get(t);
/*  372 */             if ((temp.length > 0) && (NasIdentifier.equals(temp[7]))) {
/*  373 */               clients = temp;
/*  374 */               break;
/*      */             }
/*      */           }
/*  377 */           if ((clients != null) && (clients.length > 0)) {
/*  378 */             client = clients[0];
/*  379 */             clientType = clients[1];
/*  380 */             acctInterimInterval = clients[3];
/*  381 */             idleTimeout = clients[5];
/*  382 */             autoKick = clients[6];
/*      */           }
/*      */         }
/*      */ 
/*  386 */         if (stringUtils.isBlank(client)) {
/*  387 */           client = Tool.ByteToHex("OpenPortal".getBytes());
/*      */         }
/*  389 */         if (stringUtils.isBlank(clientType)) {
/*  390 */           clientType = "0";
/*      */         }
/*  392 */         if (stringUtils.isBlank(acctInterimInterval)) {
/*  393 */           acctInterimInterval = "300";
/*      */         }
/*  395 */         if (stringUtils.isBlank(idleTimeout)) {
/*  396 */           idleTimeout = "0";
/*      */         }
/*  398 */         if (stringUtils.isBlank(autoKick)) {
/*  399 */           autoKick = "1";
/*      */         }
/*      */ 
/*  402 */         if (stringUtils.isNotBlank(client)) {
/*  403 */           String sharedSecret = client;
/*  404 */           if (stringUtils.isNotBlank(sharedSecret)) {
/*  405 */             if (chap == 0) {
/*  406 */               password = Tool.decodeMD5(sharedSecret, 
/*  407 */                 authenticator, password);
/*      */             }
/*  409 */             Tool.writeLog("Access-Request", "ip=" + ip + ",port=" + 
/*  410 */               port + ",name=" + name + ",password=" + 
/*  411 */               password);
/*  412 */             String result = null;
/*      */ 
/*  414 */             PortalaccountQuery aq = new PortalaccountQuery();
/*  415 */             aq.setLoginName(name);
/*  416 */             aq.setLoginNameLike(false);
/*  417 */             List users = this.portalaccountService
/*  418 */               .getPortalaccountList(aq);
/*  419 */             Long id = Long.valueOf(0L);
/*  420 */             String pwd = "";
/*  421 */             Long sid = Long.valueOf(0L);
/*      */ 
/*  423 */             String userState = "0";
/*  424 */             Date userDate = new Date();
/*  425 */             long userTime = 0L;
/*  426 */             long now = userDate.getTime();
/*  427 */             int sessionTime = 0;
/*  428 */             if ((users != null) && (users.size() > 0)) {
/*  429 */               Portalaccount user = (Portalaccount)users.get(0);
/*  430 */               pwd = user.getPassword();
/*  431 */               id = user.getId();
/*  432 */               sid = user.getSpeed();
/*  433 */               userState = user.getState();
/*  434 */               userDate = user.getDate();
/*  435 */               userTime = user.getTime().longValue();
/*  436 */               if ("0".equals(userState)) {
/*  437 */                 sessionTime = 0;
/*  438 */                 result = "-1";
/*      */               }
/*      */ 
/*  444 */               if ("1".equals(userState)) {
/*  445 */                 sessionTime = (int)(userTime / 1000L);
/*  446 */                 if (sessionTime <= 0) {
/*  447 */                   sessionTime = 86400;
/*      */                 }
/*      */               }
/*  450 */               if ("3".equals(userState)) {
/*  451 */                 sessionTime = (int)((userDate.getTime() - now) / 1000L);
/*  452 */                 if (sessionTime <= 0) {
/*  453 */                   user.setState("2");
/*  454 */                   this.portalaccountService
/*  455 */                     .updatePortalaccountByKey(user);
/*  456 */                   userState = "2";
/*      */                 }
/*      */               }
/*  459 */               if ("2".equals(userState)) {
/*  460 */                 sessionTime = (int)(userTime / 1000L);
/*  461 */                 if (sessionTime <= 0) {
/*  462 */                   user.setState("4");
/*  463 */                   this.portalaccountService
/*  464 */                     .updatePortalaccountByKey(user);
/*  465 */                   userState = "4";
/*      */                 }
/*      */               }
/*  468 */               if ("4".equals(userState)) {
/*  469 */                 sessionTime = 86400;
/*  470 */                 long octets = user.getOctets().longValue();
/*  471 */                 if (octets <= 0L) {
/*  472 */                   user.setState("0");
/*  473 */                   this.portalaccountService
/*  474 */                     .updatePortalaccountByKey(user);
/*  475 */                   sessionTime = 0;
/*  476 */                   octets = 0L;
/*  477 */                   result = "-1";
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*  483 */               if (!OnlineLimit.macLimit(username, 
/*  483 */                 CallingStationId, user.getMaclimitcount().intValue())) {
/*  484 */                 if ("1".equals(autoKick)) {
/*  485 */                   Iterator iterator = RadiusOnlineMap.getInstance()
/*  486 */                     .getRadiusOnlineMap().keySet().iterator();
/*  487 */                   while (iterator.hasNext()) {
/*  488 */                     Object o = iterator.next();
/*  489 */                     String t = o.toString();
/*  490 */                     String[] loginInfo = 
/*  491 */                       (String[])RadiusOnlineMap.getInstance()
/*  491 */                       .getRadiusOnlineMap().get(t);
/*  492 */                     String haveUsername = loginInfo[4];
/*  493 */                     if (username.equals(haveUsername)) {
/*  494 */                       COAThread.COA_Account_Cost(loginInfo, "Radius AutoKick COA");
/*  495 */                       break;
/*      */                     }
/*      */                   }
/*      */                 } else {
/*  499 */                   sessionTime = 0;
/*  500 */                   result = "-1";
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*  507 */               if (!MacLimit.macLimit(id, username, 
/*  506 */                 CallingStationId, user.getMaclimit().intValue(), 
/*  507 */                 user.getMaclimitcount().intValue())) {
/*  508 */                 sessionTime = 0;
/*  509 */                 result = "-1";
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/*  514 */               sessionTime = 0;
/*  515 */               result = "-1";
/*      */             }
/*      */ 
/*  518 */             if (chap == 0) {
/*  519 */               Tool.writeLog(ip, "Database PWD=" + pwd + 
/*  520 */                 ":::: Req PWD=" + password);
/*  521 */               if ((password.equals(pwd)) && 
/*  522 */                 (result == null))
/*  523 */                 result = "0";
/*      */             }
/*      */             else
/*      */             {
/*  527 */               byte[] pwdByte = pwd.getBytes();
/*  528 */               byte[] ChallengeByte = Tool.HexToByte(Challenge);
/*  529 */               byte[] chapidByte = new byte[2];
/*  530 */               chapidByte[1] = ((byte)Integer.parseInt(
/*  531 */                 password.substring(0, 2), 16));
/*      */ 
/*  533 */               String chap_password = Tool.ByteToHex(
/*  534 */                 ChapPassword.MK_ChapPwd(chapidByte, ChallengeByte, 
/*  535 */                 pwdByte));
/*  536 */               password = password.substring(2);
/*      */ 
/*  538 */               Tool.writeLog(ip, "Database ChapPWD=" + 
/*  539 */                 chap_password + ":::: Req ChapPWD=" + 
/*  540 */                 password);
/*      */ 
/*  542 */               if ((password.equals(chap_password)) && 
/*  543 */                 (result == null)) {
/*  544 */                 result = "0";
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  562 */             String attributes = null;
/*  563 */             int code = 0;
/*  564 */             if (result != null) {
/*  565 */               if (result.equals("0"))
/*      */               {
/*  568 */                 if (RadiusOnlineMap.getInstance()
/*  567 */                   .getRadiusOnlineMap().size() < 
/*  568 */                   Integer.valueOf(
/*  569 */                   ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
/*  569 */                   .getCoreConfigMap().get("core"))[1]).intValue())
/*      */                 {
/*  570 */                   name = "Access-Accept";
/*  571 */                   attributes = "";
/*  572 */                   if ((userIP != null) && (!"".equals(userIP))) {
/*  573 */                     attributes = attributes + 
/*  574 */                       Tool.getAttributeIP(userIP);
/*      */                   }
/*  576 */                   if (stringUtils.isNotBlank(AcctSessionId)) {
/*  577 */                     attributes = attributes + 
/*  578 */                       Tool.getAttributeString(44, 
/*  579 */                       AcctSessionId);
/*      */                   }
/*  581 */                   attributes = attributes + 
/*  582 */                     Tool.getAttributeString(18, 
/*  583 */                     "success!");
/*  584 */                   attributes = attributes + 
/*  585 */                     Tool.getAttributeInt(27, 
/*  586 */                     sessionTime);
/*      */ 
/*  588 */                   Integer idletime = null;
/*  589 */                   if (stringUtils.isNotBlank(idleTimeout))
/*      */                     try {
/*  591 */                       idletime = 
/*  592 */                         Integer.valueOf(idleTimeout);
/*      */                     }
/*      */                     catch (Exception localException1) {
/*      */                     }
/*  596 */                   Integer acctInterval = null;
/*      */ 
/*  598 */                   if (stringUtils.isNotBlank(acctInterimInterval))
/*      */                     try {
/*  600 */                       acctInterval = 
/*  601 */                         Integer.valueOf(acctInterimInterval);
/*      */                     }
/*      */                     catch (Exception localException2)
/*      */                     {
/*      */                     }
/*  606 */                   if ((idletime != null) && (idletime.intValue() != 0)) {
/*  607 */                     attributes = attributes + 
/*  608 */                       Tool.getAttributeInt(28, 
/*  609 */                       idletime.intValue());
/*      */                   }
/*  611 */                   if ((acctInterval != null) && 
/*  612 */                     (acctInterval.intValue() != 0)) {
/*  613 */                     attributes = attributes + 
/*  614 */                       Tool.getAttributeInt(85, 
/*  615 */                       acctInterval.intValue());
/*      */                   }
/*      */ 
/*  618 */                   if (Do()) {
/*      */                     try
/*      */                     {
/*  621 */                       if (stringUtils.isNotBlank(clientType)) {
/*  622 */                         Portalspeed speed = this.speedService
/*  623 */                           .getPortalspeedByKey(sid);
/*  624 */                         if (speed != null) {
/*  625 */                           if ("6".equals(clientType))
/*      */                           {
/*  627 */                             attributes = attributes + 
/*  628 */                               Tool.getAttributeVendor(
/*  629 */                               26, 
/*  630 */                               3902);
/*  631 */                             attributes = attributes + 
/*  632 */                               Tool.getAttributeSpeed(
/*  633 */                               83, 
/*  635 */                               (int)Math.floor(speed
/*  636 */                               .getDown().longValue()));
/*      */ 
/*  638 */                             attributes = attributes + 
/*  639 */                               Tool.getAttributeVendor(
/*  640 */                               26, 
/*  641 */                               3902);
/*  642 */                             attributes = attributes + 
/*  643 */                               Tool.getAttributeSpeed(
/*  644 */                               89, 
/*  646 */                               (int)Math.floor(speed
/*  647 */                               .getUp().longValue()));
/*      */                           }
/*      */ 
/*  650 */                           if ("4".equals(clientType))
/*      */                           {
/*  652 */                             int down = 
/*  653 */                               (int)Math.floor(speed
/*  654 */                               .getDown().longValue());
/*  655 */                             int up = 
/*  656 */                               (int)Math.floor(speed
/*  657 */                               .getUp().longValue());
/*  658 */                             String s = 
/*  659 */                               String.valueOf(up) + 
/*  660 */                               "k/" + 
/*  661 */                               String.valueOf(down) + 
/*  662 */                               "k";
/*  663 */                             int valueLen = 
/*  664 */                               Tool.getAttributeStringLen(s);
/*  665 */                             attributes = attributes + 
/*  666 */                               Tool.getAttributeVendor(
/*  667 */                               26, 
/*  668 */                               14988, 
/*  669 */                               valueLen);
/*  670 */                             attributes = attributes + 
/*  671 */                               Tool.getAttributeString(
/*  672 */                               8, s);
/*      */                           }
/*      */ 
/*  675 */                           if ("5".equals(clientType))
/*      */                           {
/*  677 */                             attributes = attributes + 
/*  678 */                               Tool.getAttributeVendor(
/*  679 */                               26, 
/*  680 */                               10055);
/*  681 */                             attributes = attributes + 
/*  682 */                               Tool.getAttributeSpeed(
/*  683 */                               2, 
/*  685 */                               (int)Math.floor(speed
/*  686 */                               .getDown().longValue() * 0.125D));
/*      */ 
/*  688 */                             attributes = attributes + 
/*  689 */                               Tool.getAttributeVendor(
/*  690 */                               26, 
/*  691 */                               10055);
/*  692 */                             attributes = attributes + 
/*  693 */                               Tool.getAttributeSpeed(
/*  694 */                               1, 
/*  696 */                               (int)Math.floor(speed
/*  697 */                               .getUp().longValue() * 0.125D));
/*      */                           }
/*      */ 
/*  700 */                           if ("7".equals(clientType))
/*      */                           {
/*  702 */                             attributes = attributes + 
/*  703 */                               Tool.getAttributeVendor(
/*  704 */                               26, 
/*  705 */                               10055);
/*  706 */                             attributes = attributes + 
/*  707 */                               Tool.getAttributeSpeed(
/*  708 */                               2, 
/*  710 */                               (int)Math.floor(speed
/*  711 */                               .getDown().longValue() * 0.125D));
/*      */ 
/*  713 */                             attributes = attributes + 
/*  714 */                               Tool.getAttributeVendor(
/*  715 */                               26, 
/*  716 */                               10055);
/*  717 */                             attributes = attributes + 
/*  718 */                               Tool.getAttributeSpeed(
/*  719 */                               1, 
/*  721 */                               (int)Math.floor(speed
/*  722 */                               .getUp().longValue() * 0.125D));
/*      */                           }
/*      */ 
/*  725 */                           if ("1".equals(clientType))
/*      */                           {
/*  727 */                             attributes = attributes + 
/*  728 */                               Tool.getAttributeVendor(
/*  729 */                               26, 
/*  730 */                               2011);
/*  731 */                             attributes = attributes + 
/*  732 */                               Tool.getAttributeSpeed(
/*  733 */                               5, 
/*  735 */                               (int)Math.floor(speed
/*  736 */                               .getDown().longValue() * 1024L));
/*      */ 
/*  738 */                             attributes = attributes + 
/*  739 */                               Tool.getAttributeVendor(
/*  740 */                               26, 
/*  741 */                               2011);
/*  742 */                             attributes = attributes + 
/*  743 */                               Tool.getAttributeSpeed(
/*  744 */                               6, 
/*  746 */                               (int)Math.floor(speed
/*  747 */                               .getMdown().longValue() * 1024L));
/*      */ 
/*  749 */                             attributes = attributes + 
/*  750 */                               Tool.getAttributeVendor(
/*  751 */                               26, 
/*  752 */                               2011);
/*  753 */                             attributes = attributes + 
/*  754 */                               Tool.getAttributeSpeed(
/*  755 */                               2, 
/*  757 */                               (int)Math.floor(speed
/*  758 */                               .getUp().longValue() * 1024L));
/*      */ 
/*  760 */                             attributes = attributes + 
/*  761 */                               Tool.getAttributeVendor(
/*  762 */                               26, 
/*  763 */                               2011);
/*  764 */                             attributes = attributes + 
/*  765 */                               Tool.getAttributeSpeed(
/*  766 */                               3, 
/*  768 */                               (int)Math.floor(speed
/*  769 */                               .getMup().longValue() * 1024L));
/*      */                           }
/*      */ 
/*  772 */                           if ("2".equals(clientType))
/*      */                           {
/*  774 */                             attributes = attributes + 
/*  775 */                               Tool.getAttributeVendor(
/*  776 */                               26, 
/*  777 */                               25506);
/*  778 */                             attributes = attributes + 
/*  779 */                               Tool.getAttributeSpeed(
/*  780 */                               5, 
/*  782 */                               (int)Math.floor(speed
/*  783 */                               .getDown().longValue() * 1024L));
/*      */ 
/*  785 */                             attributes = attributes + 
/*  786 */                               Tool.getAttributeVendor(
/*  787 */                               26, 
/*  788 */                               25506);
/*  789 */                             attributes = attributes + 
/*  790 */                               Tool.getAttributeSpeed(
/*  791 */                               4, 
/*  793 */                               (int)Math.floor(speed
/*  794 */                               .getMdown().longValue() * 1024L));
/*      */ 
/*  796 */                             attributes = attributes + 
/*  797 */                               Tool.getAttributeVendor(
/*  798 */                               26, 
/*  799 */                               25506);
/*  800 */                             attributes = attributes + 
/*  801 */                               Tool.getAttributeSpeed(
/*  802 */                               2, 
/*  804 */                               (int)Math.floor(speed
/*  805 */                               .getUp().longValue() * 1024L));
/*      */ 
/*  807 */                             attributes = attributes + 
/*  808 */                               Tool.getAttributeVendor(
/*  809 */                               26, 
/*  810 */                               25506);
/*  811 */                             attributes = attributes + 
/*  812 */                               Tool.getAttributeSpeed(
/*  813 */                               1, 
/*  815 */                               (int)Math.floor(speed
/*  816 */                               .getMup().longValue() * 1024L));
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e) {
/*  822 */                       Tool.writeErrorLog("Error", e);
/*      */                     }
/*      */ 
/*      */                   }
/*      */ 
/*  865 */                   code = 2;
/*  866 */                   Tool.writeLog(ip, "Account Check OK !!");
/*      */                 }
/*      */                 else {
/*  869 */                   name = "Access-Reject";
/*  870 */                   attributes = Tool.getAttributeString(18, 
/*  871 */                     "System Max Online Limit!");
/*  872 */                   code = 3;
/*  873 */                   Tool.writeLog(ip, "Account is Out of Time !!");
/*      */                 }
/*      */               } else {
/*  876 */                 name = "Access-Reject";
/*  877 */                 attributes = Tool.getAttributeString(18, 
/*  878 */                   "Account is Out of Time or Mac Limit!");
/*  879 */                 code = 3;
/*  880 */                 Tool.writeLog(ip, "Account is Out of Time !!");
/*      */               }
/*      */             } else {
/*  883 */               name = "Access-Reject";
/*  884 */               attributes = Tool.getAttributeString(18, 
/*  885 */                 "Account Check Fail!");
/*  886 */               code = 3;
/*  887 */               Tool.writeLog(ip, "Account Check Fail !!");
/*      */             }
/*  889 */             Tool.writeLog(ip, "Access-Request Print Finish !!");
/*  890 */             ret = Tool.getOutData(name, sharedSecret, ip, port, 
/*  891 */               code, identifier, authenticator, attributes);
/*      */           } else {
/*  893 */             Tool.writeLog(ip, "ShareKey is NULL !!");
/*      */           }
/*      */         } else {
/*  896 */           Tool.writeLog(ip, "Get ShareKey Fail !!");
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/*  900 */       Tool.writeErrorLog("error", e);
/*      */     }
/*  902 */     return ret;
/*      */   }
/*      */ 
/*      */   private byte[] accessChallenge(String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*      */   {
/*  910 */     byte[] ret = null;
/*      */     try {
/*  912 */       for (int i = 0; i < attributesList.length; i++)
/*      */         try {
/*  914 */           int type = Integer.parseInt(attributesList[i][0], 16);
/*  915 */           Tool.getAttributeValue(ip, type, attributesList[i][1])
/*  916 */             .trim();
/*      */         } catch (Exception e1) {
/*  918 */           Tool.writeErrorLog("Error", e1);
/*      */         }
/*      */     }
/*      */     catch (Exception e) {
/*  922 */       Tool.writeErrorLog("Error", e);
/*      */     }
/*  924 */     return ret;
/*      */   }
/*      */ 
/*      */   private byte[] statusServer(String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*      */   {
/*  930 */     byte[] ret = null;
/*      */     try {
/*  932 */       for (int i = 0; i < attributesList.length; i++)
/*      */         try {
/*  934 */           int type = Integer.parseInt(attributesList[i][0], 16);
/*  935 */           Tool.getAttributeValue(ip, type, attributesList[i][1])
/*  936 */             .trim();
/*      */         } catch (Exception e1) {
/*  938 */           Tool.writeErrorLog("Error", e1);
/*      */         }
/*      */     }
/*      */     catch (Exception e) {
/*  942 */       Tool.writeErrorLog("Error", e);
/*      */     }
/*  944 */     return ret;
/*      */   }
/*      */ 
/*      */   private byte[] statusClient(String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*      */   {
/*  950 */     byte[] ret = null;
/*      */     try {
/*  952 */       for (int i = 0; i < attributesList.length; i++)
/*      */         try {
/*  954 */           int type = Integer.parseInt(attributesList[i][0], 16);
/*  955 */           Tool.getAttributeValue(ip, type, attributesList[i][1])
/*  956 */             .trim();
/*      */         } catch (Exception e1) {
/*  958 */           Tool.writeErrorLog("Error", e1);
/*      */         }
/*      */     }
/*      */     catch (Exception e) {
/*  962 */       Tool.writeErrorLog("Error", e);
/*      */     }
/*  964 */     return ret;
/*      */   }
/*      */ 
/*      */   private byte[] reserved(String ip, int port, int identifier, String authenticator, String[][] attributesList)
/*      */   {
/*  970 */     byte[] ret = null;
/*      */     try {
/*  972 */       for (int i = 0; i < attributesList.length; i++)
/*      */         try {
/*  974 */           int type = Integer.parseInt(attributesList[i][0], 16);
/*  975 */           Tool.getAttributeValue(ip, type, attributesList[i][1])
/*  976 */             .trim();
/*      */         } catch (Exception e1) {
/*  978 */           Tool.writeErrorLog("Error", e1);
/*      */         }
/*      */     }
/*      */     catch (Exception e) {
/*  982 */       Tool.writeErrorLog("Error", e);
/*      */     }
/*  984 */     return ret;
/*      */   }
/*      */ 
/*      */   public static boolean Do() {
/*  988 */     Long isThis = Long.valueOf(new Date().getTime());
/*  989 */     boolean Do = false;
/*  990 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/*  991 */       Do = true;
/*      */     }
/*  993 */     return Do;
/*      */   }
/*      */ 
/*      */   private String DomainToIP(String domain) {
/*  997 */     if (this.configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
/*  998 */       return domain;
/*      */     }
/* 1000 */     String ip = "";
/*      */     try {
/* 1002 */       ip = InetAddress.getByName(domain).toString().split("/")[1];
/*      */     } catch (UnknownHostException e) {
/* 1004 */       Tool.writeErrorLog("Radius DomainToIP ERROR INFO ", e);
/*      */     }
/* 1006 */     System.out.println("Domain:" + domain + " IP:" + ip);
/* 1007 */     return ip;
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusServer
 * JD-Core Version:    0.6.2
 */