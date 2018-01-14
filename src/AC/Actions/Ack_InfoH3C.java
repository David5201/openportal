/*    */ package AC.Actions;
/*    */ 
/*    */ import AC.Utils.WR;
/*    */ import java.io.PrintStream;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ 
/*    */ public class Ack_InfoH3C
/*    */ {
/*    */   public static void Info(String ip, int port, byte[] Req_Data)
/*    */   {
/* 12 */     System.out.println("收到" + ip + ":" + port + ":" + "发来的Info会话请求报文：" + 
/* 13 */       WR.Getbyte2HexString(Req_Data));
/*    */ 
/* 15 */     byte[] Ack_Data_Info = null;
/* 16 */     if ((Req_Data[0] & 0xFF) == 1) {
/* 17 */       Ack_Data_Info = new byte[16];
/*    */     }
/* 19 */     if ((Req_Data[0] & 0xFF) == 2) {
/* 20 */       Ack_Data_Info = new byte[32];
/*    */     }
/*    */ 
/* 23 */     for (int i = 0; i < Ack_Data_Info.length; i++) {
/* 24 */       Ack_Data_Info[i] = Req_Data[i];
/*    */     }
/*    */ 
/* 27 */     short typet = 10;
/* 28 */     Ack_Data_Info[1] = ((byte)typet);
/*    */ 
/* 30 */     short ErrCodet = 0;
/* 31 */     Ack_Data_Info[14] = ((byte)ErrCodet);
/*    */ 
/* 33 */     short atrnumt = 0;
/* 34 */     Ack_Data_Info[15] = ((byte)atrnumt);
/*    */ 
/* 36 */     System.out.println("准备发送Info回复报文给：" + ip + ":" + port + ":" + 
/* 37 */       WR.Getbyte2HexString(Ack_Data_Info));
/*    */ 
/* 39 */     DatagramSocket scoket_Auth_ACK = null;
/*    */     try {
/* 41 */       scoket_Auth_ACK = new DatagramSocket();
/* 42 */       DatagramPacket data = new DatagramPacket(Ack_Data_Info, 
/* 43 */         Ack_Data_Info.length, InetAddress.getByName(ip), port);
/* 44 */       scoket_Auth_ACK.send(data);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 48 */       e.printStackTrace();
/*    */     } finally {
/* 50 */       scoket_Auth_ACK.close();
/*    */     }
/* 52 */     WR.space();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_InfoH3C
 * JD-Core Version:    0.6.2
 */