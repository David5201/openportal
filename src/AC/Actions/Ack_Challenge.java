/*     */ package AC.Actions;
/*     */ 
/*     */ import AC.Utils.Make_Challenge;
/*     */ import AC.Utils.WR;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ 
/*     */ public class Ack_Challenge
/*     */ {
/*     */   public static void Challenge(String ip, int port, byte[] Req_Data)
/*     */   {
/*  14 */     System.out.println("收到" + ip + ":" + port + ":" + "发来的Challenge请求报文：" + 
/*  15 */       WR.Getbyte2HexString(Req_Data));
/*     */ 
/*  19 */     byte[] ReqID = new byte[2];
/*  20 */     int ReqID_int = (int)(1.0D + Math.random() * 32767.0D);
/*  21 */     for (int i = 0; i < 2; i++) {
/*  22 */       int offset = (ReqID.length - 1 - i) * 8;
/*  23 */       ReqID[i] = ((byte)(ReqID_int >>> offset & 0xFF));
/*     */     }
/*  25 */     System.out.println("生成的ReqID内容：" + ReqID_int);
/*  26 */     System.out
/*  27 */       .println("验证ReqID内容：" + ((ReqID[0] << 8) + (ReqID[1] & 0xFF)));
/*     */ 
/*  29 */     int pos = 16;
/*  30 */     byte[] Ack_Data_challenge = null;
/*  31 */     if ((Req_Data[0] & 0xFF) == 1) {
/*  32 */       pos = 16;
/*  33 */       Ack_Data_challenge = new byte[34];
/*     */     }
/*  35 */     if ((Req_Data[0] & 0xFF) == 2) {
/*  36 */       pos = 32;
/*  37 */       Ack_Data_challenge = new byte[50];
/*     */     }
/*     */ 
/*  40 */     for (int i = 0; i < pos; i++) {
/*  41 */       Ack_Data_challenge[i] = Req_Data[i];
/*     */     }
/*     */ 
/*  44 */     short typet = 2;
/*  45 */     Ack_Data_challenge[1] = ((byte)typet);
/*     */ 
/*  47 */     short ErrCodet = 0;
/*  48 */     Ack_Data_challenge[14] = ((byte)ErrCodet);
/*     */ 
/*  58 */     Ack_Data_challenge[6] = ReqID[0];
/*  59 */     Ack_Data_challenge[7] = ReqID[1];
/*     */ 
/*  61 */     short atrnumt = 1;
/*  62 */     Ack_Data_challenge[15] = ((byte)atrnumt);
/*  63 */     short atrtypt = 3;
/*     */ 
/*  65 */     Ack_Data_challenge[pos] = ((byte)atrtypt);
/*  66 */     int atrleg = 18;
/*  67 */     Ack_Data_challenge[(pos + 1)] = ((byte)(atrleg & 0xFF));
/*     */ 
/*  70 */     byte[] Challenge = new byte[16];
/*     */ 
/*  72 */     String challengeStr = Make_Challenge.getChallenge();
/*     */     try {
/*  74 */       Challenge = challengeStr.getBytes("US-ASCII");
/*     */     }
/*     */     catch (UnsupportedEncodingException e1) {
/*  77 */       e1.printStackTrace();
/*     */     }
/*  79 */     System.out.println("生成的challenge内容：" + challengeStr);
/*  80 */     System.out.println("验证challenge内容：" + new String(Challenge));
/*     */ 
/*  82 */     for (int i = 0; i < Challenge.length; i++) {
/*  83 */       Ack_Data_challenge[(pos + 2 + i)] = Challenge[i];
/*     */     }
/*     */ 
/*  86 */     System.out.println("准备发送Challenge回复报文给：" + ip + ":" + port + ":" + 
/*  87 */       WR.Getbyte2HexString(Ack_Data_challenge));
/*     */ 
/*  89 */     DatagramSocket scoket_Challenge_Ack = null;
/*     */     try {
/*  91 */       scoket_Challenge_Ack = new DatagramSocket();
/*  92 */       DatagramPacket data = new DatagramPacket(Ack_Data_challenge, 
/*  93 */         Ack_Data_challenge.length, InetAddress.getByName(ip), port);
/*  94 */       scoket_Challenge_Ack.send(data);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  98 */       e.printStackTrace();
/*     */     } finally {
/* 100 */       scoket_Challenge_Ack.close();
/*     */     }
/* 102 */     WR.space();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_Challenge
 * JD-Core Version:    0.6.2
 */