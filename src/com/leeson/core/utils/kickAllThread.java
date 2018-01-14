/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ public class kickAllThread
/*    */ {
/*  9 */   private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 
/* 10 */     2147483647, 3L, TimeUnit.SECONDS, 
/* 11 */     new LinkedBlockingQueue());
/*    */ 
/*    */   public static void kickAll(String host)
/*    */   {
/* 15 */     MyTask myTask = new MyTask(host);
/* 16 */     executor.execute(myTask);
/*    */   }
/*    */ 
/*    */   public static void offThread() {
/* 20 */     executor.shutdown();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.kickAllThread
 * JD-Core Version:    0.6.2
 */