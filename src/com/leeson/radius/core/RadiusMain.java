/*    */ package com.leeson.radius.core;
/*    */ 
/*    */ public class RadiusMain
/*    */ {
/*  6 */   private static RadiusServer radiusServer = null;
/*  7 */   private static Thread radiusThread = null;
/*    */ 
/* 10 */   private static RadiusAccountServer radiusAccountServer = null;
/* 11 */   private static Thread radiusAccountThread = null;
/*    */ 
/*    */   public static void radiusServer()
/*    */   {
/* 15 */     new RadiusMain().start();
/*    */   }
/*    */ 
/*    */   public Integer start()
/*    */   {
/*    */     try
/*    */     {
/* 26 */       radiusServer = new RadiusServer();
/* 27 */       radiusThread = new Thread(radiusServer);
/* 28 */       radiusThread.start();
/*    */ 
/* 30 */       radiusAccountServer = new RadiusAccountServer();
/* 31 */       radiusAccountThread = new Thread(radiusAccountServer);
/* 32 */       radiusAccountThread.start();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 36 */       Tool.writeErrorLog("Error", e);
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   public static int stop(int exitCode)
/*    */   {
/*    */     try
/*    */     {
/* 45 */       radiusServer.stop();
/* 46 */       radiusAccountServer.stop();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 51 */       Tool.writeErrorLog("Error", e);
/*    */     }
/* 53 */     return exitCode;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusMain
 * JD-Core Version:    0.6.2
 */