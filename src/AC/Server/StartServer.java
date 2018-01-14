/*    */ package AC.Server;
/*    */ 
/*    */ public class StartServer
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*  8 */     new Thread()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try {
/* 13 */           ACServer.openServer();
/*    */         }
/*    */         catch (Exception e)
/*    */         {
/* 17 */           e.printStackTrace();
/*    */         }
/*    */       }
/*    */     }
/* 21 */     .start();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Server.StartServer
 * JD-Core Version:    0.6.2
 */