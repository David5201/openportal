/*    */ package com.leeson.common.utils;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ThreadLocalDateUtil
/*    */ {
/*    */   private static final String date_format = "yyyy-MM-dd-HH-mm-ss";
/*  9 */   private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal();
/*    */ 
/*    */   public static DateFormat getDateFormat()
/*    */   {
/* 13 */     DateFormat df = (DateFormat)threadLocal.get();
/* 14 */     if (df == null) {
/* 15 */       df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 16 */       threadLocal.set(df);
/*    */     }
/*    */ 
/* 19 */     return df;
/*    */   }
/*    */ 
/*    */   public static String format(Date date) {
/* 23 */     return getDateFormat().format(date);
/*    */   }
/*    */ 
/*    */   public static Date parse(String strDate) throws ParseException {
/* 27 */     return getDateFormat().parse(strDate);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.ThreadLocalDateUtil
 * JD-Core Version:    0.6.2
 */