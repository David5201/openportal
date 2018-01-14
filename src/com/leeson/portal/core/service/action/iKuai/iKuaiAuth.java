/*     */ package com.leeson.portal.core.service.action.iKuai;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.service.action.v1.pap.PAP_Quit_V1;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class iKuaiAuth
/*     */ {
/*  51 */   private static Config config = Config.getInstance();
/*  52 */   private static Logger log = Logger.getLogger(iKuaiAuth.class);
/*     */ 
/*     */   public static boolean auth(String Bas_IP, int bas_PORT, int timeout_Sec, String in_username, String in_password, byte[] SerialNo, byte[] UserIP, String userMac)
/*     */   {
/*  58 */     byte[] Username = in_username.getBytes();
/*  59 */     byte[] password = in_password.getBytes();
/*  60 */     byte[] mac = userMac.getBytes();
/*  61 */     byte[] authbuff = new byte[4 + Username.length + password.length + 2 + mac.length];
/*  62 */     authbuff[0] = 1;
/*  63 */     authbuff[1] = ((byte)(Username.length + 2));
/*  64 */     for (int i = 0; i < Username.length; i++) {
/*  65 */       authbuff[(2 + i)] = Username[i];
/*     */     }
/*  67 */     authbuff[(2 + Username.length)] = 2;
/*  68 */     authbuff[(3 + Username.length)] = ((byte)(password.length + 2));
/*  69 */     for (int i = 0; i < password.length; i++) {
/*  70 */       authbuff[(4 + Username.length + i)] = password[i];
/*     */     }
/*  72 */     authbuff[(4 + Username.length + password.length)] = 11;
/*  73 */     authbuff[(5 + Username.length + password.length)] = ((byte)(mac.length + 2));
/*  74 */     for (int i = 0; i < mac.length; i++) {
/*  75 */       authbuff[(6 + Username.length + password.length + i)] = mac[i];
/*     */     }
/*  77 */     return Req_Auth(Username, password, SerialNo, UserIP, 
/*  78 */       authbuff, timeout_Sec, Bas_IP, bas_PORT, mac);
/*     */   }
/*     */ 
/*     */   public static boolean Req_Auth(byte[] Username, byte[] password, byte[] SerialNo, byte[] UserIP, byte[] authbuff, int timeout_Sec, String Bas_IP, int bas_PORT, byte[] mac)
/*     */   {
/*  85 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  86 */     DatagramSocket dataSocket = null;
/*     */ 
/*  88 */     byte[] Req_Auth = new byte[20 + Username.length + password.length + 2 + mac.length];
/*  89 */     Req_Auth[0] = 1;
/*  90 */     Req_Auth[1] = 3;
/*  91 */     Req_Auth[2] = 1;
/*  92 */     Req_Auth[3] = 0;
/*  93 */     Req_Auth[4] = SerialNo[0];
/*  94 */     Req_Auth[5] = SerialNo[1];
/*  95 */     Req_Auth[6] = 0;
/*  96 */     Req_Auth[7] = 0;
/*  97 */     Req_Auth[8] = UserIP[0];
/*  98 */     Req_Auth[9] = UserIP[1];
/*  99 */     Req_Auth[10] = UserIP[2];
/* 100 */     Req_Auth[11] = UserIP[3];
/* 101 */     Req_Auth[12] = 0;
/* 102 */     Req_Auth[13] = 0;
/* 103 */     Req_Auth[14] = 0;
/* 104 */     Req_Auth[15] = 3;
/*     */ 
/* 106 */     for (int i = 0; i < authbuff.length; i++) {
/* 107 */       Req_Auth[(16 + i)] = authbuff[i];
/*     */     }
/* 109 */     if (basConfig.getIsdebug().equals("1")) {
/* 110 */       log.info("REQ Auth" + PortalUtil.Getbyte2HexString(Req_Auth));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 115 */       dataSocket = new DatagramSocket();
/*     */ 
/* 117 */       DatagramPacket requestPacket = new DatagramPacket(Req_Auth, 
/* 118 */         Req_Auth.length, InetAddress.getByName(Bas_IP), bas_PORT);
/* 119 */       dataSocket.send(requestPacket);
/*     */ 
/* 121 */       byte[] ACK_Auth_Data_Temp = new byte[100];
/* 122 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Auth_Data_Temp, 100);
/*     */ 
/* 124 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/* 125 */       dataSocket.receive(receivePacket);
/*     */ 
/* 127 */       byte[] ACK_Auth_Data = new byte[receivePacket.getLength()];
/* 128 */       for (int l = 0; l < ACK_Auth_Data.length; l++) {
/* 129 */         ACK_Auth_Data[l] = receivePacket.getData()[l];
/*     */       }
/*     */ 
/* 132 */       if (basConfig.getIsdebug().equals("1")) {
/* 133 */         log.info("ACK Auth" + PortalUtil.Getbyte2HexString(ACK_Auth_Data));
/*     */       }
/*     */ 
/* 136 */       if (((ACK_Auth_Data[14] & 0xFF) == 0) || 
/* 137 */         ((ACK_Auth_Data[14] & 0xFF) == 2))
/*     */       {
/* 139 */         if (basConfig.getIsdebug().equals("1")) {
/* 140 */           log.info("认证成功,准备发送AFF_ACK_AUTH!!!");
/*     */         }
/* 142 */         return AFF_Ack_Auth(SerialNo, UserIP, Bas_IP, 
/* 143 */           bas_PORT);
/*     */       }
/* 145 */       if ((ACK_Auth_Data[14] & 0xFF) == 1)
/*     */       {
/* 147 */         if (basConfig.getIsdebug().equals("1")) {
/* 148 */           log.info("发送认证请求被拒绝!!!");
/*     */         }
/*     */ 
/*     */       }
/* 153 */       else if ((ACK_Auth_Data[14] & 0xFF) == 3)
/*     */       {
/* 155 */         if (basConfig.getIsdebug().equals("1")) {
/* 156 */           log.info("系统繁忙，请稍后再试!!!");
/*     */         }
/*     */       }
/* 159 */       else if ((ACK_Auth_Data[14] & 0xFF) == 4)
/*     */       {
/* 161 */         if (basConfig.getIsdebug().equals("1")) {
/* 162 */           log.info("发送认证请求失败!!!");
/*     */         }
/*     */ 
/*     */       }
/* 167 */       else if (basConfig.getIsdebug().equals("1")) {
/* 168 */         log.info("发送认证请求出现未知错误!!!");
/*     */       }
/*     */ 
/* 172 */       return false;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 176 */       if (basConfig.getIsdebug().equals("1")) {
/* 177 */         log.info("发送认证请求无响应!!!");
/*     */       }
/*     */ 
/* 180 */       PAP_Quit_V1.quit(2, Bas_IP, bas_PORT, timeout_Sec, SerialNo, UserIP);
/* 181 */       return false;
/*     */     } finally {
/* 183 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean AFF_Ack_Auth(byte[] SerialNo, byte[] UserIP, String Bas_IP, int bas_PORT)
/*     */   {
/* 190 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 192 */     DatagramSocket dataSocket = null;
/*     */ 
/* 194 */     byte[] AFF_Ack_Auth_Data = new byte[16];
/*     */ 
/* 196 */     AFF_Ack_Auth_Data[0] = 1;
/* 197 */     AFF_Ack_Auth_Data[1] = 7;
/* 198 */     AFF_Ack_Auth_Data[2] = 1;
/* 199 */     AFF_Ack_Auth_Data[3] = 0;
/* 200 */     AFF_Ack_Auth_Data[4] = SerialNo[0];
/* 201 */     AFF_Ack_Auth_Data[5] = SerialNo[1];
/* 202 */     AFF_Ack_Auth_Data[6] = 0;
/* 203 */     AFF_Ack_Auth_Data[7] = 0;
/* 204 */     AFF_Ack_Auth_Data[8] = UserIP[0];
/* 205 */     AFF_Ack_Auth_Data[9] = UserIP[1];
/* 206 */     AFF_Ack_Auth_Data[10] = UserIP[2];
/* 207 */     AFF_Ack_Auth_Data[11] = UserIP[3];
/* 208 */     AFF_Ack_Auth_Data[12] = 0;
/* 209 */     AFF_Ack_Auth_Data[13] = 0;
/* 210 */     AFF_Ack_Auth_Data[14] = 0;
/* 211 */     AFF_Ack_Auth_Data[15] = 0;
/*     */ 
/* 213 */     if (basConfig.getIsdebug().equals("1")) {
/* 214 */       log.info("AFF_Ack_Auth" + 
/* 215 */         PortalUtil.Getbyte2HexString(AFF_Ack_Auth_Data));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 220 */       dataSocket = new DatagramSocket();
/*     */ 
/* 222 */       DatagramPacket requestPacket = new DatagramPacket(
/* 223 */         AFF_Ack_Auth_Data, 16, InetAddress.getByName(Bas_IP), 
/* 224 */         bas_PORT);
/* 225 */       dataSocket.send(requestPacket);
/*     */ 
/* 227 */       if (basConfig.getIsdebug().equals("1")) {
/* 228 */         log.info("发送AFF_Ack_Auth认证成功回复报文成功!!!");
/*     */       }
/*     */ 
/* 231 */       return true;
/*     */     }
/*     */     catch (IOException e) {
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
 * Qualified Name:     com.leeson.portal.core.service.action.iKuai.iKuaiAuth
 * JD-Core Version:    0.6.2
 */