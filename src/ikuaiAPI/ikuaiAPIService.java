/*    */ package ikuaiAPI;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ 
/*    */ public class ikuaiAPIService
/*    */   implements ServletContextListener
/*    */ {
/*    */   public void contextDestroyed(ServletContextEvent arg0)
/*    */   {
/* 18 */     System.out.println("IkuaiAPI Service Stop !!");
/*    */   }
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*    */   {
/* 24 */     new Thread()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try {
/* 29 */           RoamAutoAuth.openServer();
/*    */         }
/*    */         catch (Exception e) {
/* 32 */           System.out.println("IkuaiAPI Service Start Error : " + e);
/*    */         }
/*    */       }
/*    */     }
/* 36 */     .start();
/*    */ 
/* 38 */     System.out.println("IkuaiAPI Service Start !!");
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     ikuaiAPI.ikuaiAPIService
 * JD-Core Version:    0.6.2
 */