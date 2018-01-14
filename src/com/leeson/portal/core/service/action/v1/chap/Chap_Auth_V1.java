/*     */ package com.leeson.portal.core.service.action.v1.chap;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.service.utils.ChapPassword;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Chap_Auth_V1
/*     */ {
/*  24 */   private static Config config = Config.getInstance();
/*  25 */   private static Logger log = Logger.getLogger(Chap_Auth_V1.class);
/*     */ 
/*     */   public static byte[] auth(String Bas_IP, int bas_PORT, int timeout_Sec, String in_username, String in_password, byte[] SerialNo, byte[] UserIP, byte[] ReqID, byte[] Challenge)
/*     */   {
/*  31 */     byte[] ChapPass = new byte[16];
/*  32 */     byte[] Username = in_username.getBytes();
/*  33 */     byte[] password = in_password.getBytes();
/*     */     try {
/*  35 */       ChapPass = ChapPassword.MK_ChapPwd(ReqID, Challenge, password);
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*  38 */       e.printStackTrace();
/*     */     }
/*  40 */     return Req_Auth(Username, ChapPass, SerialNo, UserIP, 
/*  41 */       ReqID, timeout_Sec, Bas_IP, bas_PORT);
/*     */   }
/*     */ 
/*     */   public static byte[] Req_Auth(byte[] Username, byte[] ChapPass, byte[] SerialNo, byte[] UserIP, byte[] ReqID, int timeout_Sec, String Bas_IP, int bas_PORT)
/*     */   {
/*  48 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/*  50 */     DatagramSocket dataSocket = null;
/*     */ 
/*  52 */     byte[] ErrorInfo = new byte[1];
/*  53 */     byte[] Req_Auth = new byte[20 + Username.length + ChapPass.length];
/*  54 */     Req_Auth[0] = 1;
/*  55 */     Req_Auth[1] = 3;
/*  56 */     Req_Auth[2] = 0;
/*  57 */     Req_Auth[3] = 0;
/*  58 */     Req_Auth[4] = SerialNo[0];
/*  59 */     Req_Auth[5] = SerialNo[1];
/*  60 */     Req_Auth[6] = ReqID[0];
/*  61 */     Req_Auth[7] = ReqID[1];
/*  62 */     Req_Auth[8] = UserIP[0];
/*  63 */     Req_Auth[9] = UserIP[1];
/*  64 */     Req_Auth[10] = UserIP[2];
/*  65 */     Req_Auth[11] = UserIP[3];
/*  66 */     Req_Auth[12] = 0;
/*  67 */     Req_Auth[13] = 0;
/*  68 */     Req_Auth[14] = 0;
/*  69 */     Req_Auth[15] = 2;
/*  70 */     Req_Auth[16] = 1;
/*  71 */     Req_Auth[17] = ((byte)(Username.length + 2));
/*  72 */     for (int i = 0; i < Username.length; i++) {
/*  73 */       Req_Auth[(18 + i)] = Username[i];
/*     */     }
/*  75 */     Req_Auth[(18 + Username.length)] = 4;
/*  76 */     Req_Auth[(19 + Username.length)] = ((byte)(ChapPass.length + 2));
/*  77 */     for (int i = 0; i < ChapPass.length; i++) {
/*  78 */       Req_Auth[(20 + Username.length + i)] = ChapPass[i];
/*     */     }
/*  80 */     if (basConfig.getIsdebug().equals("1")) {
/*  81 */       log.info("REQ Auth" + PortalUtil.Getbyte2HexString(Req_Auth));
/*     */     }
/*     */     try
/*     */     {
/*  85 */       dataSocket = new DatagramSocket();
/*  86 */       DatagramPacket requestPacket = new DatagramPacket(Req_Auth, 
/*  87 */         Req_Auth.length, InetAddress.getByName(Bas_IP), bas_PORT);
/*  88 */       dataSocket.send(requestPacket);
/*  89 */       byte[] ACK_Auth_Data = new byte[16];
/*  90 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Auth_Data, 
/*  91 */         ACK_Auth_Data.length);
/*  92 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/*  93 */       dataSocket.receive(receivePacket);
/*  94 */       if (basConfig.getIsdebug().equals("1")) {
/*  95 */         log.info("ACK Auth" + PortalUtil.Getbyte2HexString(ACK_Auth_Data));
/*     */       }
/*     */ 
/*  98 */       if (((ACK_Auth_Data[14] & 0xFF) == 0) || ((ACK_Auth_Data[14] & 0xFF) == 2)) {
/*  99 */         if (basConfig.getIsdebug().equals("1")) {
/* 100 */           log.info("认证成功,准备发送AFF_ACK_AUTH!!!");
/*     */         }
/*     */ 
/* 103 */         return AFF_Ack_Auth(SerialNo, UserIP, ReqID, 
/* 104 */           Bas_IP, bas_PORT);
/* 105 */       }if ((ACK_Auth_Data[14] & 0xFF) == 1) {
/* 106 */         if (basConfig.getIsdebug().equals("1")) {
/* 107 */           log.info("发送认证请求被拒绝!!!");
/*     */         }
/*     */ 
/* 110 */         ErrorInfo[0] = 21;
/* 111 */         return ErrorInfo;
/* 112 */       }if ((ACK_Auth_Data[14] & 0xFF) == 2) {
/* 113 */         if (basConfig.getIsdebug().equals("1")) {
/* 114 */           log.info("发送认证请求连接已建立!!!");
/*     */         }
/*     */ 
/* 117 */         ErrorInfo[0] = 22;
/* 118 */         return ErrorInfo;
/* 119 */       }if ((ACK_Auth_Data[14] & 0xFF) == 3) {
/* 120 */         if (basConfig.getIsdebug().equals("1")) {
/* 121 */           log.info("系统繁忙,请稍后再试!!!");
/*     */         }
/*     */ 
/* 124 */         ErrorInfo[0] = 23;
/* 125 */         return ErrorInfo;
/* 126 */       }if ((ACK_Auth_Data[14] & 0xFF) == 4) {
/* 127 */         if (basConfig.getIsdebug().equals("1")) {
/* 128 */           log.info("发送认证请求失败!!!");
/*     */         }
/*     */ 
/* 131 */         ErrorInfo[0] = 24;
/* 132 */         return ErrorInfo;
/*     */       }
/* 134 */       if (basConfig.getIsdebug().equals("1")) {
/* 135 */         log.info("发送认证请求出现未知错误!!!");
/*     */       }
/*     */ 
/* 138 */       ErrorInfo[0] = 2;
/* 139 */       return ErrorInfo;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       byte[] arrayOfByte1;
/* 142 */       if (basConfig.getIsdebug().equals("1")) {
/* 143 */         log.info("发送认证请求无响应!!!");
/*     */       }
/*     */ 
/* 146 */       ErrorInfo[0] = 2;
/* 147 */       return ErrorInfo;
/*     */     } finally {
/* 149 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] AFF_Ack_Auth(byte[] SerialNo, byte[] UserIP, byte[] ReqID, String Bas_IP, int bas_PORT)
/*     */   {
/* 161 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 163 */     DatagramSocket dataSocket = null;
/*     */ 
/* 165 */     byte[] ErrorInfo = new byte[1];
/* 166 */     byte[] AFF_Ack_Auth = new byte[16];
/*     */ 
/* 168 */     AFF_Ack_Auth[0] = 1;
/* 169 */     AFF_Ack_Auth[1] = 7;
/* 170 */     AFF_Ack_Auth[2] = 0;
/* 171 */     AFF_Ack_Auth[3] = 0;
/* 172 */     AFF_Ack_Auth[4] = SerialNo[0];
/* 173 */     AFF_Ack_Auth[5] = SerialNo[1];
/* 174 */     AFF_Ack_Auth[6] = ReqID[0];
/* 175 */     AFF_Ack_Auth[7] = ReqID[1];
/* 176 */     AFF_Ack_Auth[8] = UserIP[0];
/* 177 */     AFF_Ack_Auth[9] = UserIP[1];
/* 178 */     AFF_Ack_Auth[10] = UserIP[2];
/* 179 */     AFF_Ack_Auth[11] = UserIP[3];
/* 180 */     AFF_Ack_Auth[12] = 0;
/* 181 */     AFF_Ack_Auth[13] = 0;
/* 182 */     AFF_Ack_Auth[14] = 0;
/* 183 */     AFF_Ack_Auth[15] = 0;
/* 184 */     if (basConfig.getIsdebug().equals("1")) {
/* 185 */       log.info("AFF_Ack_Auth" + PortalUtil.Getbyte2HexString(AFF_Ack_Auth));
/*     */     }
/*     */     try
/*     */     {
/* 189 */       dataSocket = new DatagramSocket();
/* 190 */       DatagramPacket requestPacket = new DatagramPacket(AFF_Ack_Auth, 16, 
/* 191 */         InetAddress.getByName(Bas_IP), bas_PORT);
/* 192 */       dataSocket.send(requestPacket);
/* 193 */       if (basConfig.getIsdebug().equals("1"))
/* 194 */         log.info("发送AFF_Ack_Auth认证成功响应报文回复成功!!!");
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 198 */       if (basConfig.getIsdebug().equals("1"))
/* 199 */         log.info("发送AFF_Ack_Auth认证成功响应报文回复失败!!!");
/*     */     }
/*     */     finally
/*     */     {
/* 203 */       dataSocket.close();
/*     */     }
/* 205 */     ErrorInfo[0] = 20;
/* 206 */     return ErrorInfo;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.chap.Chap_Auth_V1
 * JD-Core Version:    0.6.2
 */