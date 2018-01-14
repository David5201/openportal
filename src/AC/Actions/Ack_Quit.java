/*    */ package AC.Actions;
/*    */ 
/*    */ import AC.Utils.WR;
/*    */ import java.io.PrintStream;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ 
/*    */ public class Ack_Quit
/*    */ {
/*    */   public static void Quit(String ip, int port, byte[] Req_Data)
/*    */   {
/* 11 */     System.out.println("收到" + ip + ":" + port + ":" + "发来的下线请求报文：" + 
/* 12 */       WR.Getbyte2HexString(Req_Data));
/*    */ 
/* 15 */     byte[] Ack_Data_Quit = null;
/* 16 */     if ((Req_Data[0] & 0xFF) == 1) {
/* 17 */       Ack_Data_Quit = new byte[16];
/*    */     }
/* 19 */     if ((Req_Data[0] & 0xFF) == 2) {
/* 20 */       Ack_Data_Quit = new byte[32];
/*    */     }
/*    */ 
/* 23 */     for (int i = 0; i < Ack_Data_Quit.length; i++) {
/* 24 */       Ack_Data_Quit[i] = Req_Data[i];
/*    */     }
/* 26 */     short typet = 6;
/* 27 */     Ack_Data_Quit[1] = ((byte)typet);
/*    */ 
/* 29 */     short ErrCodet = 0;
/* 30 */     Ack_Data_Quit[14] = ((byte)ErrCodet);
/*    */ 
/* 36 */     System.out.println("准备发送下线回复报文给：" + ip + ":" + port + ":" + 
/* 37 */       WR.Getbyte2HexString(Ack_Data_Quit));
/*    */ 
/* 39 */     DatagramSocket scoket_Quit_ACK = null;
/*    */     try {
/* 41 */       scoket_Quit_ACK = new DatagramSocket();
/* 42 */       DatagramPacket data = new DatagramPacket(Ack_Data_Quit, 
/* 43 */         Ack_Data_Quit.length, InetAddress.getByName(ip), port);
/* 44 */       scoket_Quit_ACK.send(data);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 48 */       e.printStackTrace();
/*    */     } finally {
/* 50 */       scoket_Quit_ACK.close();
/*    */     }
/* 52 */     WR.space();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_Quit
 * JD-Core Version:    0.6.2
 */