/*    */ package com.alipay.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class UtilDate
/*    */ {
/*    */   public static final String dtLong = "yyyyMMddHHmmss";
/*    */   public static final String simple = "yyyy-MM-dd HH:mm:ss";
/*    */   public static final String dtShort = "yyyyMMdd";
/*    */ 
/*    */   public static String getOrderNum()
/*    */   {
/* 37 */     Date date = new Date();
/* 38 */     DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/* 39 */     return df.format(date);
/*    */   }
/*    */ 
/*    */   public static String getDateFormatter()
/*    */   {
/* 47 */     Date date = new Date();
/* 48 */     DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 49 */     return df.format(date);
/*    */   }
/*    */ 
/*    */   public static String getDate()
/*    */   {
/* 57 */     Date date = new Date();
/* 58 */     DateFormat df = new SimpleDateFormat("yyyyMMdd");
/* 59 */     return df.format(date);
/*    */   }
/*    */ 
/*    */   public static String getThree()
/*    */   {
/* 67 */     Random rad = new Random();
/* 68 */     return rad.nextInt(1000)+"";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.UtilDate
 * JD-Core Version:    0.6.2
 */