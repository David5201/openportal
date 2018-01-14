/*     */ package com.leeson.portal.core.service.action.v2.chap;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.service.action.GetMac;
/*     */ import com.leeson.portal.core.service.utils.Authenticator;
/*     */ import com.leeson.portal.core.service.utils.ChapPassword;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Chap_Auth_V2
/*     */ {
/*  28 */   private static Logger log = Logger.getLogger(Chap_Auth_V2.class);
/*  29 */   private static Config config = Config.getInstance();
/*     */ 
/*     */   public static byte[] auth(String Bas_IP, int bas_PORT, int timeout_Sec, String in_username, String in_password, byte[] SerialNo, byte[] UserIP, byte[] ReqID, byte[] Challenge, String sharedSecret)
/*     */   {
/*  35 */     byte[] ChapPass = new byte[16];
/*  36 */     byte[] Username = in_username.getBytes();
/*  37 */     byte[] password = in_password.getBytes();
/*     */     try {
/*  39 */       ChapPass = ChapPassword.MK_ChapPwd(ReqID, Challenge, password);
/*     */     } catch (UnsupportedEncodingException e) {
/*  41 */       e.printStackTrace();
/*     */     }
/*  43 */     return Req_Auth(Username, ChapPass, SerialNo, UserIP, 
/*  44 */       ReqID, timeout_Sec, Bas_IP, bas_PORT, sharedSecret);
/*     */   }
/*     */ 
/*     */   public static byte[] Req_Auth(byte[] Username, byte[] ChapPass, byte[] SerialNo, byte[] UserIP, byte[] ReqID, int timeout_Sec, String Bas_IP, int bas_PORT, String sharedSecret)
/*     */   {
/*  51 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/*  53 */     DatagramSocket dataSocket = null;
/*     */ 
/*  55 */     byte[] ErrorInfo = new byte[1];
/*     */ 
/*  57 */     byte[] authbuff = new byte[4 + Username.length + ChapPass.length + 6];
/*  58 */     authbuff[0] = 1;
/*  59 */     authbuff[1] = ((byte)(Username.length + 2));
/*  60 */     for (int i = 0; i < Username.length; i++) {
/*  61 */       authbuff[(2 + i)] = Username[i];
/*     */     }
/*  63 */     authbuff[(2 + Username.length)] = 4;
/*  64 */     authbuff[(3 + Username.length)] = ((byte)(ChapPass.length + 2));
/*  65 */     for (int i = 0; i < ChapPass.length; i++) {
/*  66 */       authbuff[(4 + Username.length + i)] = ChapPass[i];
/*     */     }
/*     */ 
/*  69 */     byte[] BasIP = new byte[4];
/*  70 */     String[] basips = Bas_IP.split("[.]");
/*     */ 
/*  72 */     for (int i = 0; i < 4; i++) {
/*  73 */       int m = NumberUtils.toInt(basips[i]);
/*  74 */       byte b = (byte)m;
/*  75 */       BasIP[i] = b;
/*     */     }
/*  77 */     authbuff[(4 + Username.length + ChapPass.length)] = 10;
/*  78 */     authbuff[(4 + Username.length + ChapPass.length + 1)] = 6;
/*  79 */     authbuff[(4 + Username.length + ChapPass.length + 2)] = BasIP[0];
/*  80 */     authbuff[(4 + Username.length + ChapPass.length + 3)] = BasIP[1];
/*  81 */     authbuff[(4 + Username.length + ChapPass.length + 4)] = BasIP[2];
/*  82 */     authbuff[(4 + Username.length + ChapPass.length + 5)] = BasIP[3];
/*     */ 
/*  85 */     byte[] Req_Auth = new byte[36 + Username.length + ChapPass.length + 6];
/*  86 */     Req_Auth[0] = 2;
/*  87 */     Req_Auth[1] = 3;
/*  88 */     Req_Auth[2] = 0;
/*  89 */     Req_Auth[3] = 0;
/*  90 */     Req_Auth[4] = SerialNo[0];
/*  91 */     Req_Auth[5] = SerialNo[1];
/*  92 */     Req_Auth[6] = ReqID[0];
/*  93 */     Req_Auth[7] = ReqID[1];
/*  94 */     Req_Auth[8] = UserIP[0];
/*  95 */     Req_Auth[9] = UserIP[1];
/*  96 */     Req_Auth[10] = UserIP[2];
/*  97 */     Req_Auth[11] = UserIP[3];
/*  98 */     Req_Auth[12] = 0;
/*  99 */     Req_Auth[13] = 0;
/* 100 */     Req_Auth[14] = 0;
/* 101 */     Req_Auth[15] = 3;
/* 102 */     byte[] BBuff = new byte[16];
/* 103 */     for (int i = 0; i < 16; i++) {
/* 104 */       BBuff[i] = Req_Auth[i];
/*     */     }
/* 106 */     byte[] Authen = Authenticator.MK_Authen(BBuff, authbuff, sharedSecret);
/* 107 */     for (int i = 0; i < 16; i++) {
/* 108 */       Req_Auth[(16 + i)] = Authen[i];
/*     */     }
/* 110 */     for (int i = 0; i < authbuff.length; i++) {
/* 111 */       Req_Auth[(32 + i)] = authbuff[i];
/*     */     }
/* 113 */     if (basConfig.getIsdebug().equals("1")) {
/* 114 */       log.info("REQ Auth" + PortalUtil.Getbyte2HexString(Req_Auth));
/*     */     }
/*     */     try
/*     */     {
/* 118 */       dataSocket = new DatagramSocket();
/* 119 */       DatagramPacket requestPacket = new DatagramPacket(Req_Auth, 
/* 120 */         Req_Auth.length, InetAddress.getByName(Bas_IP), bas_PORT);
/* 121 */       dataSocket.send(requestPacket);
/* 122 */       byte[] ACK_Auth_Data = new byte[100];
/* 123 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Auth_Data, ACK_Auth_Data.length);
/* 124 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/* 125 */       dataSocket.receive(receivePacket);
/* 126 */       ACK_Auth_Data = new byte[receivePacket.getLength()];
/* 127 */       for (int i = 0; i < ACK_Auth_Data.length; i++) {
/* 128 */         ACK_Auth_Data[i] = receivePacket.getData()[i];
/*     */       }
/* 130 */       if (basConfig.getIsdebug().equals("1")) {
/* 131 */         log.info("ACK Auth" + PortalUtil.Getbyte2HexString(ACK_Auth_Data));
/*     */       }
/*     */ 
/* 134 */       if (((ACK_Auth_Data[14] & 0xFF) == 0) || ((ACK_Auth_Data[14] & 0xFF) == 2))
/*     */       {
/* 137 */         GetMac.getMac(ACK_Auth_Data, Bas_IP, UserIP);
/*     */ 
/* 141 */         if (basConfig.getIsdebug().equals("1")) {
/* 142 */           log.info("认证成功！！准备发送AFF_ACK_AUTH!!!");
/*     */         }
/* 144 */         return AFF_Ack_Auth(SerialNo, UserIP, ReqID, 
/* 145 */           Bas_IP, bas_PORT, sharedSecret, BBuff);
/*     */       }
/* 147 */       if ((ACK_Auth_Data[14] & 0xFF) == 1) {
/* 148 */         if (basConfig.getIsdebug().equals("1")) {
/* 149 */           log.info("发送认证请求被拒绝!!!");
/*     */         }
/*     */ 
/* 152 */         ErrorInfo[0] = 21;
/* 153 */         return ErrorInfo;
/* 154 */       }if ((ACK_Auth_Data[14] & 0xFF) == 2) {
/* 155 */         if (basConfig.getIsdebug().equals("1")) {
/* 156 */           log.info("发送用户认证请求连接已建立!!!");
/*     */         }
/*     */ 
/* 159 */         ErrorInfo[0] = 22;
/* 160 */         return ErrorInfo;
/* 161 */       }if ((ACK_Auth_Data[14] & 0xFF) == 3) {
/* 162 */         if (basConfig.getIsdebug().equals("1")) {
/* 163 */           log.info("系统繁忙，请稍后再试!!!");
/*     */         }
/*     */ 
/* 166 */         ErrorInfo[0] = 23;
/* 167 */         return ErrorInfo;
/* 168 */       }if ((ACK_Auth_Data[14] & 0xFF) == 4) {
/* 169 */         if (basConfig.getIsdebug().equals("1")) {
/* 170 */           log.info("发送认证请求失败!!!");
/*     */         }
/*     */ 
/* 173 */         ErrorInfo[0] = 24;
/* 174 */         return ErrorInfo;
/*     */       }
/* 176 */       if (basConfig.getIsdebug().equals("1")) {
/* 177 */         log.info("发送认证请求出现未知错误!!!");
/*     */       }
/*     */ 
/* 180 */       ErrorInfo[0] = 2;
/* 181 */       return ErrorInfo;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       byte[] arrayOfByte1;
/* 184 */       if (basConfig.getIsdebug().equals("1")) {
/* 185 */         log.info("发送认证请求无响应!!!");
/*     */       }
/*     */ 
/* 188 */       ErrorInfo[0] = 2;
/* 189 */       return ErrorInfo;
/*     */     } finally {
/* 191 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] AFF_Ack_Auth(byte[] SerialNo, byte[] UserIP, byte[] ReqID, String Bas_IP, int bas_PORT, String sharedSecret, byte[] BBuff)
/*     */   {
/* 199 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 201 */     DatagramSocket dataSocket = null;
/*     */ 
/* 203 */     byte[] ErrorInfo = new byte[1];
/*     */ 
/* 205 */     byte[] AFF_Ack_Auth_Data = new byte[38];
/*     */ 
/* 207 */     AFF_Ack_Auth_Data[0] = 2;
/* 208 */     AFF_Ack_Auth_Data[1] = 7;
/* 209 */     AFF_Ack_Auth_Data[2] = 0;
/* 210 */     AFF_Ack_Auth_Data[3] = 0;
/* 211 */     AFF_Ack_Auth_Data[4] = SerialNo[0];
/* 212 */     AFF_Ack_Auth_Data[5] = SerialNo[1];
/* 213 */     AFF_Ack_Auth_Data[6] = ReqID[0];
/* 214 */     AFF_Ack_Auth_Data[7] = ReqID[1];
/* 215 */     AFF_Ack_Auth_Data[8] = UserIP[0];
/* 216 */     AFF_Ack_Auth_Data[9] = UserIP[1];
/* 217 */     AFF_Ack_Auth_Data[10] = UserIP[2];
/* 218 */     AFF_Ack_Auth_Data[11] = UserIP[3];
/* 219 */     AFF_Ack_Auth_Data[12] = 0;
/* 220 */     AFF_Ack_Auth_Data[13] = 0;
/* 221 */     AFF_Ack_Auth_Data[14] = 0;
/* 222 */     AFF_Ack_Auth_Data[15] = 1;
/*     */ 
/* 224 */     for (int i = 0; i < 16; i++) {
/* 225 */       BBuff[i] = AFF_Ack_Auth_Data[i];
/*     */     }
/* 227 */     byte[] Attrs = new byte[6];
/*     */ 
/* 229 */     byte[] BasIP = new byte[4];
/* 230 */     String[] basips = Bas_IP.split("[.]");
/*     */ 
/* 232 */     for (int i = 0; i < 4; i++) {
/* 233 */       int m = NumberUtils.toInt(basips[i]);
/* 234 */       byte b = (byte)m;
/* 235 */       BasIP[i] = b;
/*     */     }
/* 237 */     Attrs[0] = 10;
/* 238 */     Attrs[1] = 6;
/* 239 */     Attrs[2] = BasIP[0];
/* 240 */     Attrs[3] = BasIP[1];
/* 241 */     Attrs[4] = BasIP[2];
/* 242 */     Attrs[5] = BasIP[3];
/*     */ 
/* 244 */     byte[] BAuthen = Authenticator.MK_Authen(BBuff, Attrs, sharedSecret);
/* 245 */     for (int i = 0; i < 16; i++) {
/* 246 */       AFF_Ack_Auth_Data[(16 + i)] = BAuthen[i];
/*     */     }
/* 248 */     for (int i = 0; i < 6; i++) {
/* 249 */       AFF_Ack_Auth_Data[(32 + i)] = Attrs[i];
/*     */     }
/* 251 */     if (basConfig.getIsdebug().equals("1")) {
/* 252 */       log.info("AFF_Ack_Auth" + 
/* 253 */         PortalUtil.Getbyte2HexString(AFF_Ack_Auth_Data));
/*     */     }
/*     */     try
/*     */     {
/* 257 */       dataSocket = new DatagramSocket();
/*     */ 
/* 259 */       DatagramPacket requestPacket = new DatagramPacket(
/* 260 */         AFF_Ack_Auth_Data, 38, InetAddress.getByName(Bas_IP), 
/* 261 */         bas_PORT);
/* 262 */       dataSocket.send(requestPacket);
/* 263 */       if (basConfig.getIsdebug().equals("1"))
/* 264 */         log.info("发送AFF_Ack_Auth认证成功回复报文成功！！");
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 268 */       if (basConfig.getIsdebug().equals("1"))
/* 269 */         log.info("发送AFF_Ack_Auth认证成功回复出错！！");
/*     */     }
/*     */     finally
/*     */     {
/* 273 */       dataSocket.close();
/*     */     }
/* 275 */     ErrorInfo[0] = 20;
/* 276 */     return ErrorInfo;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.chap.Chap_Auth_V2
 * JD-Core Version:    0.6.2
 */