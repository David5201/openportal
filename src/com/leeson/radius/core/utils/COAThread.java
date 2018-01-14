/*    */ package com.leeson.radius.core.utils;
/*    */ 
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ public class COAThread
/*    */ {
/* 11 */   private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 
/* 12 */     2147483647, 3L, TimeUnit.SECONDS, 
/* 13 */     new LinkedBlockingQueue());
/*    */ 
/*    */   public static void COA_Account_Cost(String[] radiusOnlineInfo, String info)
/*    */   {
/* 17 */     MyTask myTask = new MyTask(radiusOnlineInfo, info);
/* 18 */     executor.execute(myTask);
/*    */   }
/*    */ 
/*    */   public static void offThread() {
/* 22 */     executor.shutdown();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.COAThread
 * JD-Core Version:    0.6.2
 */