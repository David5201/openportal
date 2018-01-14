/*     */ package com.leeson.portal.core.service.action.v1.pap;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PAP_Auth_V1
/*     */ {
/*  22 */   private static Config config = Config.getInstance();
/*  23 */   private static Logger log = Logger.getLogger(PAP_Auth_V1.class);
/*     */ 
/*     */   public static boolean auth(String Bas_IP, int bas_PORT, int timeout_Sec, String in_username, String in_password, byte[] SerialNo, byte[] UserIP)
/*     */   {
/*  28 */     return Req_Auth(in_username.getBytes(), 
/*  29 */       in_password.getBytes(), SerialNo, UserIP, timeout_Sec, Bas_IP, 
/*  30 */       bas_PORT);
/*     */   }
/*     */ 
/*     */   public static boolean Req_Auth(byte[] Username, byte[] password, byte[] SerialNo, byte[] UserIP, int timeout_Sec, String Bas_IP, int bas_PORT)
/*     */   {
/*  37 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  38 */     DatagramSocket dataSocket = null;
/*  39 */     byte[] Req_Auth = new byte[20 + Username.length + password.length];
/*  40 */     Req_Auth[0] = 1;
/*  41 */     Req_Auth[1] = 3;
/*  42 */     Req_Auth[2] = 1;
/*  43 */     Req_Auth[3] = 0;
/*  44 */     Req_Auth[4] = SerialNo[0];
/*  45 */     Req_Auth[5] = SerialNo[1];
/*  46 */     Req_Auth[6] = 0;
/*  47 */     Req_Auth[7] = 0;
/*  48 */     Req_Auth[8] = UserIP[0];
/*  49 */     Req_Auth[9] = UserIP[1];
/*  50 */     Req_Auth[10] = UserIP[2];
/*  51 */     Req_Auth[11] = UserIP[3];
/*  52 */     Req_Auth[12] = 0;
/*  53 */     Req_Auth[13] = 0;
/*  54 */     Req_Auth[14] = 0;
/*  55 */     Req_Auth[15] = 2;
/*  56 */     Req_Auth[16] = 1;
/*  57 */     Req_Auth[17] = ((byte)(Username.length + 2));
/*  58 */     for (int i = 0; i < Username.length; i++) {
/*  59 */       Req_Auth[(18 + i)] = Username[i];
/*     */     }
/*  61 */     Req_Auth[(18 + Username.length)] = 2;
/*  62 */     Req_Auth[(19 + Username.length)] = ((byte)(password.length + 2));
/*  63 */     for (int i = 0; i < password.length; i++) {
/*  64 */       Req_Auth[(20 + Username.length + i)] = password[i];
/*     */     }
/*  66 */     if (basConfig.getIsdebug().equals("1")) {
/*  67 */       log.info("REQ Auth" + PortalUtil.Getbyte2HexString(Req_Auth));
/*     */     }
/*     */     try
/*     */     {
/*  71 */       dataSocket = new DatagramSocket();
/*  72 */       DatagramPacket requestPacket = new DatagramPacket(Req_Auth, 
/*  73 */         Req_Auth.length, InetAddress.getByName(Bas_IP), bas_PORT);
/*  74 */       dataSocket.send(requestPacket);
/*  75 */       byte[] ACK_Data = new byte[100];
/*  76 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Data, 
/*  77 */         100);
/*  78 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/*  79 */       dataSocket.receive(receivePacket);
/*  80 */       ACK_Data = new byte[receivePacket.getLength()];
/*  81 */       for (int i = 0; i < ACK_Data.length; i++) {
/*  82 */         ACK_Data[i] = receivePacket.getData()[i];
/*     */       }
/*     */ 
/*  85 */       if (basConfig.getIsdebug().equals("1")) {
/*  86 */         log.info("ACK Auth" + PortalUtil.Getbyte2HexString(ACK_Data));
/*     */       }
/*     */ 
/*  89 */       if (((ACK_Data[14] & 0xFF) == 0) || 
/*  90 */         ((ACK_Data[14] & 0xFF) == 2)) {
/*  91 */         if (basConfig.getIsdebug().equals("1")) {
/*  92 */           log.info("认证成功,准备发送AFF_ACK_AUTH!!!");
/*     */         }
/*     */ 
/*  95 */         return AFF_Ack_Auth(SerialNo, UserIP, Bas_IP, 
/*  96 */           bas_PORT);
/*     */       }
/*  98 */       if ((ACK_Data[14] & 0xFF) == 1) {
/*  99 */         if (basConfig.getIsdebug().equals("1")) {
/* 100 */           log.info("发送认证请求被拒绝!!!");
/*     */         }
/*     */ 
/*     */       }
/* 109 */       else if ((ACK_Data[14] & 0xFF) == 3) {
/* 110 */         if (basConfig.getIsdebug().equals("1")) {
/* 111 */           log.info("系统繁忙，请稍后再试!!!");
/*     */         }
/*     */       }
/* 114 */       else if ((ACK_Data[14] & 0xFF) == 4) {
/* 115 */         if (basConfig.getIsdebug().equals("1")) {
/* 116 */           log.info("发送认证请求失败!!!");
/*     */         }
/*     */ 
/*     */       }
/* 120 */       else if (basConfig.getIsdebug().equals("1")) {
/* 121 */         log.info("发送认证请求出现未知错误!!!");
/*     */       }
/*     */ 
/* 125 */       return false;
/*     */     }
/*     */     catch (IOException e) {
/* 128 */       if (basConfig.getIsdebug().equals("1")) {
/* 129 */         log.info("发送认证请求无响应!!!");
/*     */       }
/*     */ 
/* 133 */       PAP_Quit_V1.quit(2, Bas_IP, bas_PORT, timeout_Sec, SerialNo, UserIP);
/* 134 */       return false;
/*     */     } finally {
/* 136 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean AFF_Ack_Auth(byte[] SerialNo, byte[] UserIP, String Bas_IP, int bas_PORT)
/*     */   {
/* 143 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 144 */     DatagramSocket dataSocket = null;
/* 145 */     byte[] AFF_Ack_Auth = new byte[16];
/* 146 */     AFF_Ack_Auth[0] = 1;
/* 147 */     AFF_Ack_Auth[1] = 7;
/* 148 */     AFF_Ack_Auth[2] = 1;
/* 149 */     AFF_Ack_Auth[3] = 0;
/* 150 */     AFF_Ack_Auth[4] = SerialNo[0];
/* 151 */     AFF_Ack_Auth[5] = SerialNo[1];
/* 152 */     AFF_Ack_Auth[6] = 0;
/* 153 */     AFF_Ack_Auth[7] = 0;
/* 154 */     AFF_Ack_Auth[8] = UserIP[0];
/* 155 */     AFF_Ack_Auth[9] = UserIP[1];
/* 156 */     AFF_Ack_Auth[10] = UserIP[2];
/* 157 */     AFF_Ack_Auth[11] = UserIP[3];
/* 158 */     AFF_Ack_Auth[12] = 0;
/* 159 */     AFF_Ack_Auth[13] = 0;
/* 160 */     AFF_Ack_Auth[14] = 0;
/* 161 */     AFF_Ack_Auth[15] = 0;
/* 162 */     if (basConfig.getIsdebug().equals("1")) {
/* 163 */       log.info("AFF_Ack_Auth" + PortalUtil.Getbyte2HexString(AFF_Ack_Auth));
/*     */     }
/*     */     try
/*     */     {
/* 167 */       dataSocket = new DatagramSocket();
/* 168 */       DatagramPacket requestPacket = new DatagramPacket(AFF_Ack_Auth, 
/* 169 */         AFF_Ack_Auth.length, InetAddress.getByName(Bas_IP), 
/* 170 */         bas_PORT);
/* 171 */       dataSocket.send(requestPacket);
/* 172 */       if (basConfig.getIsdebug().equals("1")) {
/* 173 */         log.info("发送AFF_Ack_Auth认证成功确认报文成功！！");
/*     */       }
/*     */ 
/* 176 */       return true;
/*     */     } catch (IOException e) {
/* 178 */       if (basConfig.getIsdebug().equals("1")) {
/* 179 */         log.info("发送AFF_Ack_Auth认证成功确认报文出错！！");
/*     */       }
/*     */ 
/* 182 */       return false;
/*     */     } finally {
/* 184 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.pap.PAP_Auth_V1
 * JD-Core Version:    0.6.2
 */