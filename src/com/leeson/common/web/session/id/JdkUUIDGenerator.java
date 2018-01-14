/*    */ package com.leeson.common.web.session.id;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.UUID;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class JdkUUIDGenerator
/*    */   implements SessionIdGenerator
/*    */ {
/*    */   public String get()
/*    */   {
/*  9 */     return StringUtils.remove(UUID.randomUUID().toString(), '-');
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 13 */     UUID.randomUUID();
/* 14 */     long time = System.currentTimeMillis();
/* 15 */     int count = 1;
/* 16 */     for (int i = 0; i < 100000; i++) {
/* 17 */       UUID uuid = UUID.randomUUID();
/* 18 */       System.out.println(count++ + ":" + uuid.toString());
/*    */     }
/* 20 */     time = System.currentTimeMillis() - time;
/* 21 */     System.out.println(time);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.id.JdkUUIDGenerator
 * JD-Core Version:    0.6.2
 */