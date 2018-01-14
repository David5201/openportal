/*    */ package com.leeson.portal.core.service.action.v2.chap;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.service.utils.Authenticator;
/*    */ import com.leeson.portal.core.service.utils.PortalUtil;
/*    */ import java.io.IOException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class Chap_Challenge_V2
/*    */ {
/* 24 */   private static Logger log = Logger.getLogger(Chap_Challenge_V2.class);
/* 25 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public static byte[] challenge(String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP, String sharedSecret)
/*    */   {
/* 29 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 30 */     DatagramSocket dataSocket = null;
/* 31 */     byte[] ErrorInfo = new byte[2];
/* 32 */     byte[] Req_Challenge = new byte[32];
/* 33 */     byte[] BBuff = new byte[16];
/* 34 */     byte[] Attrs = new byte[0];
/* 35 */     Req_Challenge[0] = 2;
/* 36 */     Req_Challenge[1] = 1;
/* 37 */     Req_Challenge[2] = 0;
/* 38 */     Req_Challenge[3] = 0;
/* 39 */     Req_Challenge[4] = SerialNo[0];
/* 40 */     Req_Challenge[5] = SerialNo[1];
/* 41 */     Req_Challenge[6] = 0;
/* 42 */     Req_Challenge[7] = 0;
/* 43 */     Req_Challenge[8] = UserIP[0];
/* 44 */     Req_Challenge[9] = UserIP[1];
/* 45 */     Req_Challenge[10] = UserIP[2];
/* 46 */     Req_Challenge[11] = UserIP[3];
/* 47 */     Req_Challenge[12] = 0;
/* 48 */     Req_Challenge[13] = 0;
/* 49 */     Req_Challenge[14] = 0;
/* 50 */     Req_Challenge[15] = 0;
/* 51 */     for (int i = 0; i < 16; i++) {
/* 52 */       BBuff[i] = Req_Challenge[i];
/*    */     }
/* 54 */     byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, sharedSecret);
/* 55 */     for (int i = 0; i < 16; i++) {
/* 56 */       Req_Challenge[(16 + i)] = Authen[i];
/*    */     }
/* 58 */     if (basConfig.getIsdebug().equals("1")) {
/* 59 */       log.info("REQ Challenge" + PortalUtil.Getbyte2HexString(Req_Challenge));
/*    */     }
/*    */     try
/*    */     {
/* 63 */       dataSocket = new DatagramSocket();
/* 64 */       DatagramPacket requestPacket = new DatagramPacket(Req_Challenge, 
/* 65 */         32, InetAddress.getByName(Bas_IP), bas_PORT);
/* 66 */       dataSocket.send(requestPacket);
/* 67 */       byte[] ACK_Challenge_Data = new byte[50];
/* 68 */       DatagramPacket receivePacket = new DatagramPacket(
/* 69 */         ACK_Challenge_Data, 50);
/* 70 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/* 71 */       dataSocket.receive(receivePacket);
/* 72 */       if (basConfig.getIsdebug().equals("1")) {
/* 73 */         log.info("ACK Challenge" + 
/* 74 */           PortalUtil.Getbyte2HexString(ACK_Challenge_Data));
/*    */       }
/*    */ 
/* 77 */       ErrorInfo[0] = ACK_Challenge_Data[6];
/* 78 */       ErrorInfo[1] = ACK_Challenge_Data[7];
/*    */ 
/* 80 */       if ((ACK_Challenge_Data[14] & 0xFF) == 0) {
/* 81 */         if (basConfig.getIsdebug().equals("1")) {
/* 82 */           log.info("发送Challenge请求成功,准备发送REQ Auth!!!");
/*    */         }
/*    */ 
/* 85 */         return ACK_Challenge_Data;
/* 86 */       }if ((ACK_Challenge_Data[14] & 0xFF) == 1) {
/* 87 */         if (basConfig.getIsdebug().equals("1")) {
/* 88 */           log.info("发送Challenge请求被拒绝!!!");
/*    */         }
/*    */ 
/* 92 */         return ErrorInfo;
/* 93 */       }if ((ACK_Challenge_Data[14] & 0xFF) == 2) {
/* 94 */         if (basConfig.getIsdebug().equals("1")) {
/* 95 */           log.info("发送Challenge连接已建立!!!");
/*    */         }
/*    */ 
/* 99 */         return ErrorInfo;
/* 100 */       }if ((ACK_Challenge_Data[14] & 0xFF) == 3) {
/* 101 */         if (basConfig.getIsdebug().equals("1")) {
/* 102 */           log.info("系统繁忙，请稍后再试!!!");
/*    */         }
/*    */ 
/* 106 */         return ErrorInfo;
/* 107 */       }if ((ACK_Challenge_Data[14] & 0xFF) == 4) {
/* 108 */         if (basConfig.getIsdebug().equals("1")) {
/* 109 */           log.info("发送Challenge请求出现未知错误!!!");
/*    */         }
/*    */ 
/* 113 */         return ErrorInfo;
/*    */       }
/* 115 */       if (basConfig.getIsdebug().equals("1")) {
/* 116 */         log.info("发送Challenge请求出现未知错误!!!");
/*    */       }
/*    */ 
/* 120 */       return ErrorInfo;
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/*    */       byte[] arrayOfByte1;
/* 123 */       if (basConfig.getIsdebug().equals("1")) {
/* 124 */         log.info("发送Challenge请求无响应!!!");
/*    */       }
/*    */ 
/* 128 */       return ErrorInfo;
/*    */     } finally {
/* 130 */       dataSocket.close();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.chap.Chap_Challenge_V2
 * JD-Core Version:    0.6.2
 */