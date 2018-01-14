/*     */ package AC.Actions;
/*     */ 
/*     */ import AC.Utils.WR;
/*     */ import java.io.PrintStream;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class Ack_Auth
/*     */ {
/*     */   public static void Auth(String ip, int port, byte[] Req_Data)
/*     */   {
/*  14 */     System.out.println("收到" + ip + ":" + port + ":" + "发来的认证请求报文：" + 
/*  15 */       WR.Getbyte2HexString(Req_Data));
/*     */ 
/*  17 */     byte[] Ack_Data_Auth = null;
/*  18 */     int pos = 0;
/*  19 */     if ((Req_Data[0] & 0xFF) == 1) {
/*  20 */       Ack_Data_Auth = new byte[30];
/*  21 */       pos = 16;
/*     */     }
/*  23 */     if ((Req_Data[0] & 0xFF) == 2) {
/*  24 */       Ack_Data_Auth = new byte[46];
/*  25 */       pos = 32;
/*     */     }
/*     */ 
/*  28 */     int Len = 0;
/*  29 */     if (Req_Data.length >= Ack_Data_Auth.length)
/*  30 */       Len = Ack_Data_Auth.length;
/*     */     else {
/*  32 */       Len = Req_Data.length;
/*     */     }
/*  34 */     for (int i = 0; i < Len; i++) {
/*  35 */       Ack_Data_Auth[i] = Req_Data[i];
/*     */     }
/*     */ 
/*  38 */     short typet = 4;
/*  39 */     Ack_Data_Auth[1] = ((byte)typet);
/*     */ 
/*  41 */     short ErrCodet = 0;
/*  42 */     Ack_Data_Auth[14] = ((byte)ErrCodet);
/*     */ 
/*  52 */     short atrnumt = 2;
/*  53 */     Ack_Data_Auth[15] = ((byte)atrnumt);
/*     */ 
/*  55 */     String Bas_IP = "127.0.0.1";
/*  56 */     byte[] BasIP = new byte[4];
/*  57 */     String[] basips = Bas_IP.split("[.]");
/*     */ 
/*  59 */     for (int i = 0; i < 4; i++) {
/*  60 */       int m = NumberUtils.toInt(basips[i]);
/*  61 */       byte b = (byte)m;
/*  62 */       BasIP[i] = b;
/*     */     }
/*  64 */     byte[] mac = new byte[6];
/*     */ 
/*  71 */     int a = (int)(255.0D * Math.random());
/*  72 */     int b = (int)(255.0D * Math.random());
/*  73 */     int c = (int)(255.0D * Math.random());
/*  74 */     int d = (int)(255.0D * Math.random());
/*  75 */     int e = (int)(255.0D * Math.random());
/*  76 */     int f = (int)(255.0D * Math.random());
/*  77 */     mac[0] = ((byte)a);
/*  78 */     mac[1] = ((byte)b);
/*  79 */     mac[2] = ((byte)c);
/*  80 */     mac[3] = ((byte)d);
/*  81 */     mac[4] = ((byte)e);
/*  82 */     mac[5] = ((byte)f);
/*  83 */     Ack_Data_Auth[pos] = 10;
/*  84 */     Ack_Data_Auth[(pos + 1)] = 6;
/*  85 */     Ack_Data_Auth[(pos + 2)] = BasIP[0];
/*  86 */     Ack_Data_Auth[(pos + 3)] = BasIP[1];
/*  87 */     Ack_Data_Auth[(pos + 4)] = BasIP[2];
/*  88 */     Ack_Data_Auth[(pos + 5)] = BasIP[3];
/*  89 */     Ack_Data_Auth[(pos + 6)] = 11;
/*  90 */     Ack_Data_Auth[(pos + 7)] = 8;
/*  91 */     Ack_Data_Auth[(pos + 8)] = mac[0];
/*  92 */     Ack_Data_Auth[(pos + 9)] = mac[1];
/*  93 */     Ack_Data_Auth[(pos + 10)] = mac[2];
/*  94 */     Ack_Data_Auth[(pos + 11)] = mac[3];
/*  95 */     Ack_Data_Auth[(pos + 12)] = mac[4];
/*  96 */     Ack_Data_Auth[(pos + 13)] = mac[5];
/*     */ 
/*  98 */     System.out.println("准备发送认证回复报文给：" + ip + ":" + port + ":" + 
/*  99 */       WR.Getbyte2HexString(Ack_Data_Auth));
/*     */ 
/* 101 */     DatagramSocket scoket_Auth_ACK = null;
/*     */     try {
/* 103 */       scoket_Auth_ACK = new DatagramSocket();
/* 104 */       DatagramPacket data = new DatagramPacket(Ack_Data_Auth, 
/* 105 */         Ack_Data_Auth.length, InetAddress.getByName(ip), port);
/* 106 */       scoket_Auth_ACK.send(data);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 110 */       ex.printStackTrace();
/*     */     } finally {
/* 112 */       scoket_Auth_ACK.close();
/*     */     }
/* 114 */     WR.space();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_Auth
 * JD-Core Version:    0.6.2
 */