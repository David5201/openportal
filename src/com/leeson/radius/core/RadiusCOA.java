/*    */ package com.leeson.radius.core;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.service.utils.PortalUtil;
/*    */ import com.leeson.radius.core.utils.DoRecord;
/*    */ import java.io.IOException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class RadiusCOA
/*    */ {
/* 17 */   private static Logger log = Logger.getLogger(RadiusCOA.class);
/* 18 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public static boolean req_COA(String[] radiusOnlineInfo, String info) {
/* 21 */     DatagramSocket dataSocket = null;
/* 22 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*    */     try {
/* 24 */       int timeoutSec = Integer.parseInt(basConfig.getTimeoutSec());
/*    */ 
/* 26 */       if ((radiusOnlineInfo == null) || (radiusOnlineInfo.length <= 0)) {
/* 27 */         if (basConfig.getIsdebug().equals("1"))
/* 28 */           log.info("RadiusOnlineInfo Data is not Haveing !!");
/*    */         String acctSessionId;
/* 30 */         return false;
/*    */       }
/*    */ 
/* 33 */       int port = 3799;
/* 34 */       String portString = radiusOnlineInfo[17];
/*    */       try {
/* 36 */         Integer portT = Integer.valueOf(portString);
/* 37 */         if (portT != null) {
/* 38 */           port = portT.intValue();
/*    */         }
/*    */       }
/*    */       catch (Exception localException1)
/*    */       {
/*    */       }
/* 44 */       String name = "Disconnect-Request";
/*    */ 
/* 46 */       String ip = radiusOnlineInfo[1];
/* 47 */       String userIP = radiusOnlineInfo[2];
/* 48 */       String userName = radiusOnlineInfo[4];
/* 49 */       String sharedSecret = radiusOnlineInfo[5];
/* 50 */       String clientType = radiusOnlineInfo[8];
/* 51 */       String acctSessionId = radiusOnlineInfo[13];
/* 52 */       String nasip = radiusOnlineInfo[0];
/* 53 */       byte[] data = null;
/*    */ 
/* 55 */       if (("5".equals(clientType)) || ("7".equals(clientType))) {
/* 56 */         data = new byte[364];
/* 57 */         data[0] = 0;
/* 58 */         data[1] = 1;
/* 59 */         data[2] = 0;
/* 60 */         data[3] = 1;
/*    */ 
/* 62 */         byte[] sharedSecretByte = Tool.HexToByte(sharedSecret);
/* 63 */         byte[] sharedSecretByteInit = new byte[36];
/* 64 */         sharedSecretByteInit[0] = 0;
/* 65 */         sharedSecretByteInit[1] = 17;
/* 66 */         sharedSecretByteInit[2] = 0;
/* 67 */         sharedSecretByteInit[3] = ((byte)sharedSecretByte.length);
/* 68 */         for (int i = 0; i < sharedSecretByte.length; i++) {
/* 69 */           sharedSecretByteInit[(4 + i)] = sharedSecretByte[i];
/*    */         }
/* 71 */         for (int i = 0; i < sharedSecretByteInit.length; i++) {
/* 72 */           data[(4 + i)] = sharedSecretByteInit[i];
/*    */         }
/*    */ 
/* 75 */         byte[] usernameByte = userName.getBytes();
/* 76 */         byte[] usernameByteInit = new byte[36];
/* 77 */         usernameByteInit[0] = 0;
/* 78 */         usernameByteInit[1] = 20;
/* 79 */         usernameByteInit[2] = 0;
/* 80 */         usernameByteInit[3] = ((byte)usernameByte.length);
/* 81 */         for (int i = 0; i < usernameByte.length; i++) {
/* 82 */           usernameByteInit[(4 + i)] = usernameByte[i];
/*    */         }
/* 84 */         for (int i = 0; i < usernameByteInit.length; i++)
/* 85 */           data[(40 + i)] = usernameByteInit[i];
/*    */       }
/*    */       else
/*    */       {
/* 89 */         int identifier = (int)(255.0D * Math.random());
/* 90 */         if (identifier < 1) {
/* 91 */           identifier = 1;
/*    */         }
/* 93 */         if (identifier > 255) {
/* 94 */           identifier = 255;
/*    */         }
/*    */ 
/* 97 */         String authenticator = "00000000000000000000000000000000";
/*    */ 
/* 99 */         String attributes = Tool.getAttributeString(44, acctSessionId);
/*    */ 
/* 101 */         if (stringUtils.isNotBlank(userName)) {
/* 102 */           attributes = attributes + Tool.getAttributeString(1, userName);
/*    */         }
/*    */ 
/* 105 */         if (stringUtils.isNotBlank(userIP)) {
/* 106 */           attributes = attributes + Tool.getAttributeIP(userIP);
/*    */         }
/*    */ 
/* 109 */         if (stringUtils.isNotBlank(nasip)) {
/* 110 */           attributes = attributes + Tool.getAttributeNasIP(nasip);
/*    */         }
/* 112 */         data = Tool.getOutData(name, sharedSecret, ip, port, 
/* 113 */           40, identifier, authenticator, attributes);
/*    */       }
/*    */ 
/* 118 */       if (basConfig.getIsdebug().equals("1")) {
/* 119 */         log.info("REQ CoA port " + port + " : " + PortalUtil.Getbyte2HexString(data));
/*    */       }
/*    */ 
/* 122 */       dataSocket = new DatagramSocket();
/* 123 */       DatagramPacket requestPacket = new DatagramPacket(data, 
/* 124 */         data.length, InetAddress.getByName(ip), port);
/* 125 */       dataSocket.send(requestPacket);
/* 126 */       if (!"5".equals(clientType)) {
/* 127 */         byte[] ACK_Data = new byte[100];
/* 128 */         DatagramPacket receivePacket = new DatagramPacket(ACK_Data, 100);
/* 129 */         dataSocket.setSoTimeout(timeoutSec * 1000);
/* 130 */         dataSocket.receive(receivePacket);
/* 131 */         ACK_Data = new byte[receivePacket.getLength()];
/* 132 */         for (int i = 0; i < ACK_Data.length; i++) {
/* 133 */           ACK_Data[i] = receivePacket.getData()[i];
/*    */         }
/*    */ 
/* 136 */         if (basConfig.getIsdebug().equals("1")) {
/* 137 */           log.info("ACK CoA: " + PortalUtil.Getbyte2HexString(ACK_Data));
/*    */         }
/*    */ 
/* 140 */         if ((ACK_Data[0] & 0xFF) == 41) {
/* 141 */           if (basConfig.getIsdebug().equals("1"))
/* 142 */             log.info("CoA Offline Success !!");
/* 144 */           return true;
/*    */         }
/* 146 */         if (basConfig.getIsdebug().equals("1"))
/* 147 */           log.info("CoA Offline Fail !!");
/* 149 */         return false;
/*    */       }
/* 152 */       return true;
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 156 */       if (basConfig.getIsdebug().equals("1"))
/* 157 */         Tool.writeErrorLog("CoA Error", e);
/*    */       String acctSessionId;
/* 159 */       return false;
/*    */     } finally {
/* 161 */       if (dataSocket != null) {
/* 162 */         dataSocket.close();
/*    */       }
/*    */       try
/*    */       {
/* 166 */         Thread.sleep(3000L);
/*    */       }
/*    */       catch (InterruptedException localInterruptedException5) {
/*    */       }
/*    */       try {
/* 171 */         if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0)) {
/* 172 */           String acctSessionId = radiusOnlineInfo[13];
/* 173 */           DoRecord.coreMethod(acctSessionId, info);
/*    */         }
/*    */       }
/*    */       catch (Exception localException6)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusCOA
 * JD-Core Version:    0.6.2
 */