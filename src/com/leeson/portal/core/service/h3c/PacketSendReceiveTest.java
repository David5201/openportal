/*    */ package com.leeson.portal.core.service.h3c;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.service.utils.PortalUtil;
/*    */ import java.io.IOException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PacketSendReceiveTest
/*    */ {
/* 17 */   private static Logger log = Logger.getLogger(PacketSendReceiveTest.class);
/*    */ 
/* 19 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public static byte[] reqInfo(short SerialNo, String UserIP)
/*    */   {
/* 29 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 30 */     String basIp = basConfig.getBasIp();
/* 31 */     String bas = basConfig.getBas();
/* 32 */     int basPort = Integer.parseInt(basConfig.getBasPort());
/* 33 */     String sharedSecret = basConfig.getSharedSecret();
/* 34 */     String authType = basConfig.getAuthType();
/* 35 */     int timeoutSec = Integer.parseInt(basConfig.getTimeoutSec());
/* 36 */     int portalVer = Integer.parseInt(basConfig.getPortalVer());
/*    */ 
/* 39 */     PacketBuilder localPacketBuilder = new PacketBuilder();
/* 40 */     localPacketBuilder.setHead((byte)80, (byte)portalVer);
/* 41 */     localPacketBuilder.setHead((byte)81, (byte)9);
/* 42 */     localPacketBuilder.setHead((byte)87, CommonFunctions.ipv4ToBytes(UserIP));
/* 43 */     localPacketBuilder.setHead((byte)84, SerialNo);
/* 44 */     localPacketBuilder.setAttribute((byte)8, new byte[2]);
/* 45 */     localPacketBuilder.setShareSecret(sharedSecret.getBytes());
/*    */     try
/*    */     {
/* 48 */       localPacketBuilder.setDestineAddress(InetAddress.getByName(basIp));
/* 49 */       localPacketBuilder.setDestinePort(basPort);
/*    */     }
/*    */     catch (UnknownHostException localUnknownHostException)
/*    */     {
/* 53 */       localUnknownHostException.printStackTrace();
/*    */     }
/* 55 */     DatagramSocket dataSocket = null;
/*    */     try {
/* 57 */       dataSocket = new DatagramSocket();
/* 58 */       dataSocket.send(localPacketBuilder.composeWebPacket());
/* 59 */       byte[] ACK_Data_Temp = new byte[100];
/* 60 */       DatagramPacket receivePacket = new DatagramPacket(
/* 61 */         ACK_Data_Temp, 100);
/* 62 */       dataSocket.setSoTimeout(timeoutSec * 1000);
/* 63 */       dataSocket.receive(receivePacket);
/* 64 */       byte[] ACK_Data = new byte[receivePacket.getLength()];
/* 65 */       for (int i = 0; i < ACK_Data.length; i++) {
/* 66 */         ACK_Data[i] = receivePacket.getData()[i];
/*    */       }
/* 68 */       if (basConfig.getIsdebug().equals("1")) {
/* 69 */         log.info("ACK" + 
/* 70 */           PortalUtil.Getbyte2HexString(ACK_Data));
/*    */       }
/*    */ 
/* 73 */       return ACK_Data;
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/*    */       byte[] arrayOfByte1;
/* 75 */       if (basConfig.getIsdebug().equals("1")) {
/* 76 */         log.info("建立INFO会话，请求无响应!!!");
/*    */       }
/* 78 */       byte[] ErrorInfo = new byte[1];
/* 79 */       ErrorInfo[0] = 1;
/* 80 */       return ErrorInfo;
/*    */     } finally {
/* 82 */       dataSocket.close();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.h3c.PacketSendReceiveTest
 * JD-Core Version:    0.6.2
 */