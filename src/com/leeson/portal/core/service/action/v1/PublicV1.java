/*    */ package com.leeson.portal.core.service.action.v1;
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
/*    */ public class PublicV1
/*    */ {
/* 16 */   private static Config config = Config.getInstance();
/* 17 */   private static Logger log = Logger.getLogger(PublicV1.class);
/*    */ 
/*    */   public static boolean choose(int type, byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT)
/*    */   {
/* 21 */     if (type == 0) {
/* 22 */       return offline(Req_Quit, timeout_Sec, Bas_IP, bas_PORT);
/*    */     }
/* 24 */     return timeoutAffirm(type, Req_Quit, Bas_IP, bas_PORT);
/*    */   }
/*    */ 
/*    */   public static boolean offline(byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT)
/*    */   {
/* 36 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 37 */     if (basConfig.getIsdebug().equals("1")) {
/* 38 */       log.info("REQ Quit" + PortalUtil.Getbyte2HexString(Req_Quit));
/*    */     }
/*    */ 
/* 41 */     DatagramSocket dataSocket = null;
/* 42 */     byte[] ACK_Data = new byte[16];
/*    */     try {
/* 44 */       dataSocket = new DatagramSocket();
/* 45 */       DatagramPacket requestPacket = new DatagramPacket(Req_Quit, 16, 
/* 46 */         InetAddress.getByName(Bas_IP), bas_PORT);
/* 47 */       dataSocket.send(requestPacket);
/* 48 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Data, 16);
/* 49 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/* 50 */       dataSocket.receive(receivePacket);
/* 51 */       if (basConfig.getIsdebug().equals("1"))
/* 52 */         log.info("ACK Quit" + PortalUtil.Getbyte2HexString(ACK_Data));
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 56 */       if (basConfig.getIsdebug().equals("1")) {
/* 57 */         log.info("发送下线请求无响应!!!");
/*    */       }
/*    */ 
/* 60 */       return false;
/*    */     } finally {
/* 62 */       dataSocket.close();
/*    */     }
/* 64 */     if ((ACK_Data[14] & 0xFF) == 1) {
/* 65 */       if (basConfig.getIsdebug().equals("1")) {
/* 66 */         log.info("发送下线请求被拒绝!!!");
/*    */       }
/*    */ 
/* 69 */       return false;
/* 70 */     }if ((ACK_Data[14] & 0xFF) == 2) {
/* 71 */       if (basConfig.getIsdebug().equals("1")) {
/* 72 */         log.info("发送下线请求出现错误!!!");
/*    */       }
/*    */ 
/* 75 */       return false;
/*    */     }
/* 77 */     if (basConfig.getIsdebug().equals("1")) {
/* 78 */       log.info("请求下线成功！！！");
/*    */     }
/*    */ 
/* 81 */     return true;
/*    */   }
/*    */ 
/*    */   public static boolean timeoutAffirm(int type, byte[] Req_Quit, String Bas_IP, int bas_PORT)
/*    */   {
/* 93 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 94 */     DatagramSocket dataSocket = null;
/* 95 */     Req_Quit[14] = 1;
/*    */     try {
/* 97 */       dataSocket = new DatagramSocket();
/* 98 */       DatagramPacket requestPacket = new DatagramPacket(Req_Quit, 16, 
/* 99 */         InetAddress.getByName(Bas_IP), bas_PORT);
/* 100 */       dataSocket.send(requestPacket);
/* 101 */       if (basConfig.getIsdebug().equals("1")) {
/* 102 */         log.info("发送超时回复报文成功: " + PortalUtil.Getbyte2HexString(Req_Quit));
/*    */       }
/*    */ 
/* 105 */       return true;
/*    */     } catch (IOException e) {
/* 107 */       if (basConfig.getIsdebug().equals("1")) {
/* 108 */         log.info("发送超时回复报文出现未知错误！！！");
/*    */       }
/*    */ 
/* 111 */       return false;
/*    */     } finally {
/* 113 */       dataSocket.close();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.PublicV1
 * JD-Core Version:    0.6.2
 */