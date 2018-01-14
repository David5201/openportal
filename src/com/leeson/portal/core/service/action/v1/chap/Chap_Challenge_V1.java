/*    */ package com.leeson.portal.core.service.action.v1.chap;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.service.utils.PortalUtil;
/*    */ import java.io.IOException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class Chap_Challenge_V1
/*    */ {
/* 22 */   private static Config config = Config.getInstance();
/* 23 */   private static Logger log = Logger.getLogger(Chap_Challenge_V1.class);
/*    */ 
/*    */   public static byte[] Action(String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP)
/*    */   {
/* 27 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*    */ 
/* 29 */     DatagramSocket dataSocket = null;
/*    */ 
/* 31 */     byte[] ErrorInfo = new byte[2];
/*    */ 
/* 33 */     byte[] Req_Challenge = new byte[16];
/*    */ 
/* 35 */     Req_Challenge[0] = 1;
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
/* 51 */     if (basConfig.getIsdebug().equals("1")) {
/* 52 */       log.info("REQ Challenge" + PortalUtil.Getbyte2HexString(Req_Challenge));
/*    */     }
/*    */     try
/*    */     {
/* 56 */       dataSocket = new DatagramSocket();
/* 57 */       DatagramPacket requestPacket = new DatagramPacket(Req_Challenge, 
/* 58 */         16, InetAddress.getByName(Bas_IP), bas_PORT);
/* 59 */       dataSocket.send(requestPacket);
/* 60 */       byte[] ACK_Challenge = new byte[34];
/* 61 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Challenge, 34);
/* 62 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/* 63 */       dataSocket.receive(receivePacket);
/* 64 */       if (basConfig.getIsdebug().equals("1")) {
/* 65 */         log.info("ACK Challenge" + 
/* 66 */           PortalUtil.Getbyte2HexString(ACK_Challenge));
/*    */       }
/*    */ 
/* 69 */       ErrorInfo[0] = ACK_Challenge[6];
/* 70 */       ErrorInfo[1] = ACK_Challenge[7];
/*    */ 
/* 72 */       if ((ACK_Challenge[14] & 0xFF) == 0) {
/* 73 */         if (basConfig.getIsdebug().equals("1")) {
/* 74 */           log.info("请求Challenge成功,准备发送REQ Auth认证请求!!!");
/*    */         }
/*    */ 
/* 77 */         return ACK_Challenge;
/* 78 */       }if ((ACK_Challenge[14] & 0xFF) == 1) {
/* 79 */         if (basConfig.getIsdebug().equals("1")) {
/* 80 */           log.info("发情Challenge请求被拒绝!!!");
/*    */         }
/*    */ 
/* 84 */         return ErrorInfo;
/* 85 */       }if ((ACK_Challenge[14] & 0xFF) == 2) {
/* 86 */         if (basConfig.getIsdebug().equals("1")) {
/* 87 */           log.info("发送Challenge请求已建立!!!");
/*    */         }
/*    */ 
/* 91 */         return ErrorInfo;
/* 92 */       }if ((ACK_Challenge[14] & 0xFF) == 3) {
/* 93 */         if (basConfig.getIsdebug().equals("1")) {
/* 94 */           log.info("系统繁忙，请稍后再试!!!");
/*    */         }
/*    */ 
/* 98 */         return ErrorInfo;
/* 99 */       }if ((ACK_Challenge[14] & 0xFF) == 4) {
/* 100 */         if (basConfig.getIsdebug().equals("1")) {
/* 101 */           log.info("发送Challenge请求失败!!!");
/*    */         }
/*    */ 
/* 105 */         return ErrorInfo;
/*    */       }
/* 107 */       if (basConfig.getIsdebug().equals("1")) {
/* 108 */         log.info("发送Challenge请求发生未知错误!!!");
/*    */       }
/*    */ 
/* 112 */       return ErrorInfo;
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/*    */       byte[] arrayOfByte1;
/* 115 */       if (basConfig.getIsdebug().equals("1")) {
/* 116 */         log.info("发送Challenge请求无响应!!!");
/*    */       }
/*    */ 
/* 120 */       return ErrorInfo;
/*    */     } finally {
/* 122 */       dataSocket.close();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.chap.Chap_Challenge_V1
 * JD-Core Version:    0.6.2
 */