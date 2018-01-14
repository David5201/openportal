/*     */ package com.leeson.portal.core.service.action.v2.pap;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.service.action.GetMac;
/*     */ import com.leeson.portal.core.service.utils.Authenticator;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PAP_Auth_V2
/*     */ {
/*  25 */   private static Config config = Config.getInstance();
/*  26 */   private static Logger log = Logger.getLogger(PAP_Auth_V2.class);
/*     */ 
/*     */   public static boolean auth(String Bas_IP, int bas_PORT, int timeout_Sec, String in_username, String in_password, byte[] SerialNo, byte[] UserIP, String sharedSecret)
/*     */   {
/*  32 */     byte[] Username = in_username.getBytes();
/*  33 */     byte[] password = in_password.getBytes();
/*  34 */     byte[] authbuff = new byte[4 + Username.length + password.length + 6];
/*  35 */     authbuff[0] = 1;
/*  36 */     authbuff[1] = ((byte)(Username.length + 2));
/*  37 */     for (int i = 0; i < Username.length; i++) {
/*  38 */       authbuff[(2 + i)] = Username[i];
/*     */     }
/*  40 */     authbuff[(2 + Username.length)] = 2;
/*  41 */     authbuff[(3 + Username.length)] = ((byte)(password.length + 2));
/*  42 */     for (int i = 0; i < password.length; i++) {
/*  43 */       authbuff[(4 + Username.length + i)] = password[i];
/*     */     }
/*     */ 
/*  46 */     byte[] BasIP = new byte[4];
/*  47 */     String[] basips = Bas_IP.split("[.]");
/*     */ 
/*  49 */     for (int i = 0; i < 4; i++) {
/*  50 */       int m = NumberUtils.toInt(basips[i]);
/*  51 */       byte b = (byte)m;
/*  52 */       BasIP[i] = b;
/*     */     }
/*  54 */     authbuff[(4 + Username.length + password.length)] = 10;
/*  55 */     authbuff[(4 + Username.length + password.length + 1)] = 6;
/*  56 */     authbuff[(4 + Username.length + password.length + 2)] = BasIP[0];
/*  57 */     authbuff[(4 + Username.length + password.length + 3)] = BasIP[1];
/*  58 */     authbuff[(4 + Username.length + password.length + 4)] = BasIP[2];
/*  59 */     authbuff[(4 + Username.length + password.length + 5)] = BasIP[3];
/*     */ 
/*  61 */     return Req_Auth(Username, password, SerialNo, UserIP, 
/*  62 */       authbuff, timeout_Sec, Bas_IP, bas_PORT, sharedSecret);
/*     */   }
/*     */ 
/*     */   public static boolean Req_Auth(byte[] Username, byte[] password, byte[] SerialNo, byte[] UserIP, byte[] authbuff, int timeout_Sec, String Bas_IP, int bas_PORT, String sharedSecret)
/*     */   {
/*  69 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  70 */     DatagramSocket dataSocket = null;
/*     */ 
/*  72 */     byte[] Req_Auth = new byte[36 + Username.length + password.length + 6];
/*  73 */     Req_Auth[0] = 2;
/*  74 */     Req_Auth[1] = 3;
/*  75 */     Req_Auth[2] = 1;
/*  76 */     Req_Auth[3] = 0;
/*  77 */     Req_Auth[4] = SerialNo[0];
/*  78 */     Req_Auth[5] = SerialNo[1];
/*  79 */     Req_Auth[6] = 0;
/*  80 */     Req_Auth[7] = 0;
/*  81 */     Req_Auth[8] = UserIP[0];
/*  82 */     Req_Auth[9] = UserIP[1];
/*  83 */     Req_Auth[10] = UserIP[2];
/*  84 */     Req_Auth[11] = UserIP[3];
/*  85 */     Req_Auth[12] = 0;
/*  86 */     Req_Auth[13] = 0;
/*  87 */     Req_Auth[14] = 0;
/*  88 */     Req_Auth[15] = 3;
/*  89 */     byte[] BBuff = new byte[16];
/*  90 */     for (int i = 0; i < 16; i++) {
/*  91 */       BBuff[i] = Req_Auth[i];
/*     */     }
/*  93 */     byte[] Authen = Authenticator.MK_Authen(BBuff, authbuff, sharedSecret);
/*  94 */     for (int i = 0; i < 16; i++) {
/*  95 */       Req_Auth[(16 + i)] = Authen[i];
/*     */     }
/*  97 */     for (int i = 0; i < authbuff.length; i++) {
/*  98 */       Req_Auth[(32 + i)] = authbuff[i];
/*     */     }
/* 100 */     if (basConfig.getIsdebug().equals("1")) {
/* 101 */       log.info("REQ Auth" + PortalUtil.Getbyte2HexString(Req_Auth));
/*     */     }
/*     */     try
/*     */     {
/* 105 */       dataSocket = new DatagramSocket();
/*     */ 
/* 107 */       DatagramPacket requestPacket = new DatagramPacket(Req_Auth, 
/* 108 */         Req_Auth.length, InetAddress.getByName(Bas_IP), bas_PORT);
/* 109 */       dataSocket.send(requestPacket);
/*     */ 
/* 111 */       byte[] ACK_Auth_Data = new byte[100];
/* 112 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Auth_Data, 100);
/*     */ 
/* 114 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/* 115 */       dataSocket.receive(receivePacket);
/* 116 */       ACK_Auth_Data = new byte[receivePacket.getLength()];
/* 117 */       for (int i = 0; i < ACK_Auth_Data.length; i++) {
/* 118 */         ACK_Auth_Data[i] = receivePacket.getData()[i];
/*     */       }
/* 120 */       if (basConfig.getIsdebug().equals("1")) {
/* 121 */         log.info("ACK Auth" + PortalUtil.Getbyte2HexString(ACK_Auth_Data));
/*     */       }
/*     */ 
/* 124 */       if (((ACK_Auth_Data[14] & 0xFF) == 0) || 
/* 125 */         ((ACK_Auth_Data[14] & 0xFF) == 2))
/*     */       {
/* 128 */         GetMac.getMac(ACK_Auth_Data, Bas_IP, UserIP);
/*     */ 
/* 132 */         if (basConfig.getIsdebug().equals("1")) {
/* 133 */           log.info("认证成功,准备发送AFF_ACK_AUTH!!!");
/*     */         }
/*     */ 
/* 136 */         return AFF_Ack_Auth(SerialNo, UserIP, Bas_IP, 
/* 137 */           bas_PORT, sharedSecret);
/*     */       }
/* 139 */       if ((ACK_Auth_Data[14] & 0xFF) == 1) {
/* 140 */         if (basConfig.getIsdebug().equals("1")) {
/* 141 */           log.info("发送认证请求被拒绝!!!");
/*     */         }
/*     */ 
/*     */       }
/* 151 */       else if ((ACK_Auth_Data[14] & 0xFF) == 3) {
/* 152 */         if (basConfig.getIsdebug().equals("1")) {
/* 153 */           log.info("系统繁忙，请稍后再试!!!");
/*     */         }
/*     */       }
/* 156 */       else if ((ACK_Auth_Data[14] & 0xFF) == 4) {
/* 157 */         if (basConfig.getIsdebug().equals("1")) {
/* 158 */           log.info("发送认证请求失败!!!");
/*     */         }
/*     */ 
/*     */       }
/* 162 */       else if (basConfig.getIsdebug().equals("1")) {
/* 163 */         log.info("发送认证请求出现未知错误!!!");
/*     */       }
/*     */ 
/* 167 */       return false;
/*     */     }
/*     */     catch (IOException e) {
/* 170 */       if (basConfig.getIsdebug().equals("1")) {
/* 171 */         log.info("发送认证请求无响应!!!");
/*     */       }
/*     */ 
/* 174 */       PAP_Quit_V2.quit(2, Bas_IP, bas_PORT, timeout_Sec, SerialNo, 
/* 175 */         UserIP, sharedSecret);
/* 176 */       return false;
/*     */     } finally {
/* 178 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean AFF_Ack_Auth(byte[] SerialNo, byte[] UserIP, String Bas_IP, int bas_PORT, String sharedSecret)
/*     */   {
/* 185 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 187 */     DatagramSocket dataSocket = null;
/*     */ 
/* 189 */     byte[] AFF_Ack_Auth_Data = new byte[32];
/*     */ 
/* 191 */     AFF_Ack_Auth_Data[0] = 2;
/* 192 */     AFF_Ack_Auth_Data[1] = 7;
/* 193 */     AFF_Ack_Auth_Data[2] = 1;
/* 194 */     AFF_Ack_Auth_Data[3] = 0;
/* 195 */     AFF_Ack_Auth_Data[4] = SerialNo[0];
/* 196 */     AFF_Ack_Auth_Data[5] = SerialNo[1];
/* 197 */     AFF_Ack_Auth_Data[6] = 0;
/* 198 */     AFF_Ack_Auth_Data[7] = 0;
/* 199 */     AFF_Ack_Auth_Data[8] = UserIP[0];
/* 200 */     AFF_Ack_Auth_Data[9] = UserIP[1];
/* 201 */     AFF_Ack_Auth_Data[10] = UserIP[2];
/* 202 */     AFF_Ack_Auth_Data[11] = UserIP[3];
/* 203 */     AFF_Ack_Auth_Data[12] = 0;
/* 204 */     AFF_Ack_Auth_Data[13] = 0;
/* 205 */     AFF_Ack_Auth_Data[14] = 0;
/* 206 */     AFF_Ack_Auth_Data[15] = 0;
/* 207 */     byte[] BBBuff = new byte[16];
/* 208 */     for (int i = 0; i < BBBuff.length; i++) {
/* 209 */       BBBuff[i] = AFF_Ack_Auth_Data[i];
/*     */     }
/* 211 */     byte[] Attrs = new byte[0];
/* 212 */     byte[] BAuthen = Authenticator.MK_Authen(BBBuff, Attrs, sharedSecret);
/* 213 */     for (int i = 0; i < 16; i++) {
/* 214 */       AFF_Ack_Auth_Data[(16 + i)] = BAuthen[i];
/*     */     }
/* 216 */     if (basConfig.getIsdebug().equals("1")) {
/* 217 */       log.info("AFF_Ack_Auth" + 
/* 218 */         PortalUtil.Getbyte2HexString(AFF_Ack_Auth_Data));
/*     */     }
/*     */     try
/*     */     {
/* 222 */       dataSocket = new DatagramSocket();
/*     */ 
/* 224 */       DatagramPacket requestPacket = new DatagramPacket(
/* 225 */         AFF_Ack_Auth_Data, 32, InetAddress.getByName(Bas_IP), 
/* 226 */         bas_PORT);
/* 227 */       dataSocket.send(requestPacket);
/* 228 */       if (basConfig.getIsdebug().equals("1")) {
/* 229 */         log.info("发送AFF_Ack_Auth认证成功回复报文成功!!!");
/*     */       }
/*     */ 
/* 232 */       return true;
/*     */     } catch (IOException e) {
/* 234 */       if (basConfig.getIsdebug().equals("1")) {
/* 235 */         log.info("发送AFF_Ack_Auth认证成功回复报文出错!!!");
/*     */       }
/*     */ 
/* 238 */       return false;
/*     */     } finally {
/* 240 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.pap.PAP_Auth_V2
 * JD-Core Version:    0.6.2
 */