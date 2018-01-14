/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.portal.core.model.PhoneCodeMap;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SMSVirtualRequest
/*    */ {
/*    */   public static boolean send(String phone)
/*    */   {
/*    */     try
/*    */     {
/* 11 */       String yzm = 
/* 12 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/* 13 */       Object[] objs = new Object[2];
/* 14 */       objs[0] = yzm;
/* 15 */       objs[1] = new Date();
/* 16 */       PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/* 17 */       return true;
/*    */     } catch (Exception e) {
/* 19 */       System.out.println("error" + e);
/* 20 */     }return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSVirtualRequest
 * JD-Core Version:    0.6.2
 */