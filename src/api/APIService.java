/*    */ package api;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ 
/*    */ public class APIService
/*    */   implements ServletContextListener
/*    */ {
/*    */   private String path;
/*    */ 
/*    */   public void contextDestroyed(ServletContextEvent arg0)
/*    */   {
/*    */     try
/*    */     {
/* 16 */       ServiceSocket.closeServer();
/*    */     }
/*    */     catch (Exception localException) {
/*    */     }
/* 20 */     System.out.println("API Service Stop !!");
/*    */   }
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*    */   {
/* 25 */     this.path = servletContextEvent.getServletContext().getRealPath("/");
/*    */ 
/* 27 */     new Thread()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try {
/* 32 */           ServiceSocket.openServer(APIService.this.path);
/*    */         }
/*    */         catch (Exception e) {
/* 35 */           e.printStackTrace();
/*    */         }
/*    */       }
/*    */     }
/* 39 */     .start();
/*    */ 
/* 41 */     System.out.println("API Service Start !!");
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     api.APIService
 * JD-Core Version:    0.6.2
 */