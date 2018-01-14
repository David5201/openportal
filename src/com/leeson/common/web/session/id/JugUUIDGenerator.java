/*    */ package com.leeson.common.web.session.id;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.safehaus.uuid.UUID;
/*    */ import org.safehaus.uuid.UUIDGenerator;
/*    */ 
/*    */ public class JugUUIDGenerator
/*    */   implements SessionIdGenerator
/*    */ {
/*    */   public String get()
/*    */   {
/* 12 */     UUID uuid = UUIDGenerator.getInstance().generateRandomBasedUUID();
/* 13 */     return StringUtils.remove(uuid.toString(), '-');
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 17 */     UUIDGenerator.getInstance().generateRandomBasedUUID();
/* 18 */     long time = System.currentTimeMillis();
/* 19 */     int count = 1;
/* 20 */     for (int i = 0; i < 100000; i++) {
/* 21 */       UUID uuid = UUIDGenerator.getInstance().generateRandomBasedUUID();
/* 22 */       System.out.println(count++ + ":" + uuid.toString());
/*    */     }
/* 24 */     time = System.currentTimeMillis() - time;
/* 25 */     System.out.println(time);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.id.JugUUIDGenerator
 * JD-Core Version:    0.6.2
 */