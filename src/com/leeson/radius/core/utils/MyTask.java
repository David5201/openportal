/*    */ package com.leeson.radius.core.utils;
/*    */ 
/*    */ import com.leeson.radius.core.RadiusCOA;
/*    */ 
/*    */ class MyTask
/*    */   implements Runnable
/*    */ {
/*    */   private String[] radiusOnlineInfo;
/*    */   private String info;
/*    */ 
/*    */   public MyTask(String[] radiusOnlineInfo, String info)
/*    */   {
/* 32 */     this.radiusOnlineInfo = radiusOnlineInfo;
/* 33 */     this.info = info;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 38 */     RadiusCOA.req_COA(this.radiusOnlineInfo, this.info);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.MyTask
 * JD-Core Version:    0.6.2
 */