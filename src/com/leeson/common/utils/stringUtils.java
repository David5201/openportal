/*    */ package com.leeson.common.utils;
/*    */ 
/*    */ public class stringUtils
/*    */ {
/*    */   public static boolean isEmpty(String str)
/*    */   {
/* 15 */     if ((str == null) || (str.length() == 0)) {
/* 16 */       return true;
/*    */     }
/* 18 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean isNotEmpty(String str) {
/* 22 */     if ((str == null) || (str.length() == 0)) {
/* 23 */       return false;
/*    */     }
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public static boolean isBlank(String str)
/*    */   {
/* 35 */     if ((str == null) || (str.length() == 0) || ("".equals(str.trim())) || ("null".equals(str)) || ("null".equals(str.trim()))) {
/* 36 */       return true;
/*    */     }
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean isNotBlank(String str) {
/* 42 */     if ((str == null) || (str.length() == 0) || ("".equals(str.trim())) || ("null".equals(str)) || ("null".equals(str.trim()))) {
/* 43 */       return false;
/*    */     }
/* 45 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.stringUtils
 * JD-Core Version:    0.6.2
 */