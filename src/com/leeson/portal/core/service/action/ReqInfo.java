/*     */ package com.leeson.portal.core.service.action;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.service.utils.Authenticator;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ReqInfo
/*     */ {
/*  23 */   private static Logger log = Logger.getLogger(ReqInfo.class);
/*     */ 
/*  25 */   private static Config config = Config.getInstance();
/*     */ 
/*     */   public static byte[] reqInfo(String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP, String sharedSecret, int portalVer)
/*     */   {
/*  42 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  43 */     DatagramSocket dataSocket = null;
/*  44 */     byte[] ErrorInfo = new byte[1];
/*  45 */     byte[] BBuff = new byte[16];
/*  46 */     byte[] Attrs = new byte[4];
/*     */ 
/*  48 */     if (portalVer == 1) {
/*  49 */       byte[] Req_Info = new byte[20];
/*  50 */       Req_Info[0] = 1;
/*  51 */     } else if (portalVer == 2) {
/*  52 */       byte[] Req_Info = new byte[36];
/*  53 */       Req_Info[0] = 2;
/*     */     } else {
/*  55 */       if (basConfig.getIsdebug().equals("1")) {
/*  56 */         log.info("不支持该版本!!!");
/*     */       }
/*     */ 
/*  59 */       ErrorInfo[0] = 12;
/*  60 */       return ErrorInfo;
/*     */     }
/*     */     byte[] Req_Info = new byte[15];
/*  63 */     Req_Info[1] = 9;
/*  64 */     Req_Info[2] = 0;
/*  65 */     Req_Info[3] = 0;
/*  66 */     Req_Info[4] = SerialNo[0];
/*  67 */     Req_Info[5] = SerialNo[1];
/*  68 */     Req_Info[6] = 0;
/*  69 */     Req_Info[7] = 0;
/*  70 */     Req_Info[8] = UserIP[0];
/*  71 */     Req_Info[9] = UserIP[1];
/*  72 */     Req_Info[10] = UserIP[2];
/*  73 */     Req_Info[11] = UserIP[3];
/*  74 */     Req_Info[12] = 0;
/*  75 */     Req_Info[13] = 0;
/*  76 */     Req_Info[14] = 0;
/*  77 */     Req_Info[15] = 1;
/*  78 */     for (int i = 0; i < 16; i++) {
/*  79 */       BBuff[i] = Req_Info[i];
/*     */     }
/*  81 */     Attrs[0] = 8;
/*  82 */     Attrs[1] = 4;
/*  83 */     Attrs[2] = 0;
/*  84 */     Attrs[3] = 0;
/*  85 */     Req_Info[16] = 8;
/*  86 */     Req_Info[17] = 4;
/*  87 */     Req_Info[18] = 0;
/*  88 */     Req_Info[19] = 0;
/*  89 */     if (portalVer == 2) {
/*  90 */       byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, sharedSecret);
/*  91 */       for (int i = 0; i < 16; i++) {
/*  92 */         Req_Info[(16 + i)] = Authen[i];
/*     */       }
/*  94 */       Req_Info[32] = 8;
/*  95 */       Req_Info[33] = 4;
/*  96 */       Req_Info[34] = 0;
/*  97 */       Req_Info[35] = 0;
/*     */     }
/*     */ 
/* 100 */     if (basConfig.getIsdebug().equals("1")) {
/* 101 */       log.info("REQ Info" + PortalUtil.Getbyte2HexString(Req_Info));
/*     */     }
/*     */     try
/*     */     {
/* 105 */       dataSocket = new DatagramSocket();
/* 106 */       DatagramPacket requestPacket = new DatagramPacket(Req_Info, 
/* 107 */         Req_Info.length, InetAddress.getByName(Bas_IP), bas_PORT);
/* 108 */       dataSocket.send(requestPacket);
/* 109 */       byte[] ACK_Info_Data = new byte[50];
/* 110 */       DatagramPacket receivePacket = new DatagramPacket(
/* 111 */         ACK_Info_Data, 50);
/* 112 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/* 113 */       dataSocket.receive(receivePacket);
/* 114 */       if (basConfig.getIsdebug().equals("1")) {
/* 115 */         log.info("ACK Info" + 
/* 116 */           PortalUtil.Getbyte2HexString(ACK_Info_Data));
/*     */       }
/*     */ 
/* 119 */       if ((ACK_Info_Data[14] & 0xFF) == 0) {
/* 120 */         if (basConfig.getIsdebug().equals("1")) {
/* 121 */           log.info("建立INFO会话成功,准备发送REQ Challenge!!!");
/*     */         }
/*     */ 
/* 124 */         return ACK_Info_Data;
/* 125 */       }if ((ACK_Info_Data[14] & 0xFF) == 1) {
/* 126 */         if (basConfig.getIsdebug().equals("1")) {
/* 127 */           log.info("建立INFO会话失败,不支持信息查询功能或者处理失败!!!");
/*     */         }
/*     */ 
/* 130 */         ErrorInfo[0] = 11;
/* 131 */         return ErrorInfo;
/* 132 */       }if ((ACK_Info_Data[14] & 0xFF) == 2) {
/* 133 */         if (basConfig.getIsdebug().equals("1")) {
/* 134 */           log.info("建立INFO会话失败,消息处理失败，由于某种不可知原因，使处理失败，例如询问消息格式错误等!!!");
/*     */         }
/*     */ 
/* 137 */         ErrorInfo[0] = 12;
/* 138 */         return ErrorInfo;
/*     */       }
/* 140 */       if (basConfig.getIsdebug().equals("1")) {
/* 141 */         log.info("建立INFO会话失败,出现未知错误!!!");
/*     */       }
/*     */ 
/* 144 */       ErrorInfo[0] = 13;
/* 145 */       return ErrorInfo;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       byte[] arrayOfByte1;
/* 148 */       if (basConfig.getIsdebug().equals("1")) {
/* 149 */         log.info("建立INFO会话，请求无响应!!!");
/*     */       }
/*     */ 
/* 152 */       ErrorInfo[0] = 1;
/* 153 */       return ErrorInfo;
/*     */     } finally {
/* 155 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.ReqInfo
 * JD-Core Version:    0.6.2
 */