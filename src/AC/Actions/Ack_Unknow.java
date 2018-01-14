/*    */ package AC.Actions;
/*    */ 
/*    */ import AC.Utils.WR;
/*    */ import java.io.PrintStream;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ 
/*    */ public class Ack_Unknow
/*    */ {
/*    */   public static void Ack(String ip, int port, byte[] Req_Data)
/*    */   {
/* 11 */     System.out.println("收到" + ip + ":" + port + ":" + "发来的报文：" + 
/* 12 */       WR.Getbyte2HexString(Req_Data));
/*    */ 
/* 15 */     byte[] Ack_Data_Quit = new byte[6];
/*    */ 
/* 18 */     for (int i = 0; i < Ack_Data_Quit.length; i++) {
/* 19 */       Ack_Data_Quit[i] = 1;
/*    */     }
/*    */ 
/* 22 */     System.out.println("准备回复报文给：" + ip + ":" + port + ":" + 
/* 23 */       WR.Getbyte2HexString(Ack_Data_Quit));
/*    */ 
/* 25 */     DatagramSocket scoket_Unknow_ACK = null;
/*    */     try {
/* 27 */       scoket_Unknow_ACK = new DatagramSocket();
/* 28 */       DatagramPacket data = new DatagramPacket(Ack_Data_Quit, 
/* 29 */         Ack_Data_Quit.length, InetAddress.getByName(ip), port);
/* 30 */       scoket_Unknow_ACK.send(data);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 34 */       e.printStackTrace();
/*    */     } finally {
/* 36 */       scoket_Unknow_ACK.close();
/*    */     }
/* 38 */     WR.space();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_Unknow
 * JD-Core Version:    0.6.2
 */