/*     */ package com.leeson.portal.core.service.action.v2;
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
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PublicV2
/*     */ {
/*  18 */   private static Config config = Config.getInstance();
/*  19 */   private static Logger log = Logger.getLogger(PublicV2.class);
/*     */ 
/*     */   public static boolean choose(int type, byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT, String sharedSecret)
/*     */   {
/*  23 */     if (type == 0) {
/*  24 */       return offline(Req_Quit, timeout_Sec, Bas_IP, bas_PORT, 
/*  25 */         sharedSecret);
/*     */     }
/*  27 */     return timeoutAffirm(Req_Quit, Bas_IP, bas_PORT, 
/*  28 */       sharedSecret);
/*     */   }
/*     */ 
/*     */   public static boolean offline(byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT, String sharedSecret)
/*     */   {
/*  40 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/*  42 */     DatagramSocket dataSocket = null;
/*  43 */     byte[] ACK_Data = new byte[100];
/*  44 */     byte[] BBuff = new byte[16];
/*  45 */     Req_Quit[15] = 1;
/*  46 */     for (int i = 0; i < 16; i++) {
/*  47 */       BBuff[i] = Req_Quit[i];
/*     */     }
/*     */ 
/*  50 */     byte[] Attrs = new byte[6];
/*  51 */     byte[] BasIP = new byte[4];
/*  52 */     String[] basips = Bas_IP.split("[.]");
/*     */ 
/*  54 */     for (int i = 0; i < 4; i++) {
/*  55 */       int m = NumberUtils.toInt(basips[i]);
/*  56 */       byte b = (byte)m;
/*  57 */       BasIP[i] = b;
/*     */     }
/*  59 */     Attrs[0] = 10;
/*  60 */     Attrs[1] = 6;
/*  61 */     Attrs[2] = BasIP[0];
/*  62 */     Attrs[3] = BasIP[1];
/*  63 */     Attrs[4] = BasIP[2];
/*  64 */     Attrs[5] = BasIP[3];
/*     */ 
/*  66 */     byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, sharedSecret);
/*     */ 
/*  68 */     for (int i = 0; i < 16; i++) {
/*  69 */       Req_Quit[(16 + i)] = Authen[i];
/*     */     }
/*     */ 
/*  72 */     byte[] Req_Quit_F = new byte[Req_Quit.length + 6];
/*  73 */     for (int i = 0; i < Req_Quit.length; i++) {
/*  74 */       Req_Quit_F[i] = Req_Quit[i];
/*     */     }
/*  76 */     for (int i = 0; i < 6; i++) {
/*  77 */       Req_Quit_F[(32 + i)] = Attrs[i];
/*     */     }
/*     */ 
/*  80 */     if (basConfig.getIsdebug().equals("1")) {
/*  81 */       log.info("REQ Quit" + PortalUtil.Getbyte2HexString(Req_Quit_F));
/*     */     }
/*     */     try
/*     */     {
/*  85 */       dataSocket = new DatagramSocket();
/*  86 */       DatagramPacket requestPacket = new DatagramPacket(Req_Quit_F, 38, 
/*  87 */         InetAddress.getByName(Bas_IP), bas_PORT);
/*  88 */       dataSocket.send(requestPacket);
/*  89 */       DatagramPacket receivePacket = new DatagramPacket(ACK_Data, 100);
/*  90 */       dataSocket.setSoTimeout(timeout_Sec * 1000);
/*  91 */       dataSocket.receive(receivePacket);
/*  92 */       ACK_Data = new byte[receivePacket.getLength()];
/*  93 */       for (int i = 0; i < ACK_Data.length; i++) {
/*  94 */         ACK_Data[i] = receivePacket.getData()[i];
/*     */       }
/*  96 */       if (basConfig.getIsdebug().equals("1"))
/*  97 */         log.info("ACK Quit" + PortalUtil.Getbyte2HexString(ACK_Data));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 101 */       if (basConfig.getIsdebug().equals("1")) {
/* 102 */         log.info("发送下线请求无响应!!!");
/*     */       }
/*     */ 
/* 105 */       return false;
/*     */     } finally {
/* 107 */       dataSocket.close();
/*     */     }
/* 109 */     if ((ACK_Data[14] & 0xFF) == 1) {
/* 110 */       if (basConfig.getIsdebug().equals("1")) {
/* 111 */         log.info("发送下线请求被拒绝!!!");
/*     */       }
/*     */ 
/* 114 */       return false;
/* 115 */     }if ((ACK_Data[14] & 0xFF) == 2) {
/* 116 */       if (basConfig.getIsdebug().equals("1")) {
/* 117 */         log.info("发送下线请求出现错误!!!");
/*     */       }
/*     */ 
/* 120 */       return false;
/*     */     }
/* 122 */     if (basConfig.getIsdebug().equals("1")) {
/* 123 */       log.info("请求下线成功！！");
/*     */     }
/*     */ 
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean timeoutAffirm(byte[] Req_Quit, String Bas_IP, int bas_PORT, String sharedSecret)
/*     */   {
/* 138 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 139 */     DatagramSocket dataSocket = null;
/* 140 */     byte[] BBuff = new byte[16];
/* 141 */     Req_Quit[14] = 1;
/* 142 */     Req_Quit[15] = 1;
/* 143 */     for (int i = 0; i < 16; i++) {
/* 144 */       BBuff[i] = Req_Quit[i];
/*     */     }
/*     */ 
/* 147 */     byte[] Attrs = new byte[6];
/* 148 */     byte[] BasIP = new byte[4];
/* 149 */     String[] basips = Bas_IP.split("[.]");
/*     */ 
/* 151 */     for (int i = 0; i < 4; i++) {
/* 152 */       int m = NumberUtils.toInt(basips[i]);
/* 153 */       byte b = (byte)m;
/* 154 */       BasIP[i] = b;
/*     */     }
/* 156 */     Attrs[0] = 10;
/* 157 */     Attrs[1] = 6;
/* 158 */     Attrs[2] = BasIP[0];
/* 159 */     Attrs[3] = BasIP[1];
/* 160 */     Attrs[4] = BasIP[2];
/* 161 */     Attrs[5] = BasIP[3];
/*     */ 
/* 163 */     byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, 
/* 164 */       sharedSecret);
/* 165 */     for (int i = 0; i < 16; i++) {
/* 166 */       Req_Quit[(16 + i)] = Authen[i];
/*     */     }
/*     */ 
/* 169 */     byte[] Req_Quit_F = new byte[Req_Quit.length + 6];
/* 170 */     for (int i = 0; i < Req_Quit.length; i++) {
/* 171 */       Req_Quit_F[i] = Req_Quit[i];
/*     */     }
/* 173 */     for (int i = 0; i < 6; i++) {
/* 174 */       Req_Quit_F[(32 + i)] = Attrs[i];
/*     */     }
/*     */     try
/*     */     {
/* 178 */       dataSocket = new DatagramSocket();
/* 179 */       DatagramPacket requestPacket = new DatagramPacket(Req_Quit_F, 38, 
/* 180 */         InetAddress.getByName(Bas_IP), bas_PORT);
/* 181 */       dataSocket.send(requestPacket);
/* 182 */       if (basConfig.getIsdebug().equals("1")) {
/* 183 */         log.info("发送超时回复报文成功:" + PortalUtil.Getbyte2HexString(Req_Quit));
/*     */       }
/*     */ 
/* 186 */       return true;
/*     */     } catch (IOException e) {
/* 188 */       if (basConfig.getIsdebug().equals("1")) {
/* 189 */         log.info("请求超时回复报文发生未知错误!");
/*     */       }
/*     */ 
/* 192 */       return false;
/*     */     } finally {
/* 194 */       dataSocket.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.PublicV2
 * JD-Core Version:    0.6.2
 */