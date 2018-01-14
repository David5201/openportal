/*    */ package com.leeson.common.util;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Tools
/*    */ {
/*    */   public static boolean notEmpty(String s)
/*    */   {
/* 14 */     return (s != null) && (!"".equals(s)) && (!"null".equals(s));
/*    */   }
/*    */ 
/*    */   public static boolean isEmpty(String s)
/*    */   {
/* 23 */     return (s == null) || ("".equals(s)) || ("null".equals(s));
/*    */   }
/*    */ 
/*    */   public static String[] str2StrArray(String str, String splitRegex)
/*    */   {
/* 33 */     if (isEmpty(str)) {
/* 34 */       return null;
/*    */     }
/* 36 */     return str.split(splitRegex);
/*    */   }
/*    */ 
/*    */   public static String[] str2StrArray(String str)
/*    */   {
/* 45 */     return str2StrArray(str, ",\\s*");
/*    */   }
/*    */ 
/*    */   public static String date2Str(Date date)
/*    */   {
/* 54 */     return date2Str(date, "yyyy-MM-dd HH:mm:ss");
/*    */   }
/*    */ 
/*    */   public static Date str2Date(String date)
/*    */   {
/* 63 */     if (notEmpty(date)) {
/* 64 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*    */       try {
/* 66 */         return sdf.parse(date);
/*    */       }
/*    */       catch (ParseException e) {
/* 69 */         e.printStackTrace();
/*    */ 
/* 71 */         return new Date();
/*    */       }
/*    */     }
/* 73 */     return null;
/*    */   }
/*    */ 
/*    */   public static String date2Str(Date date, String format)
/*    */   {
/* 84 */     if (date != null) {
/* 85 */       SimpleDateFormat sdf = new SimpleDateFormat(format);
/* 86 */       return sdf.format(date);
/*    */     }
/* 88 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.Tools
 * JD-Core Version:    0.6.2
 */