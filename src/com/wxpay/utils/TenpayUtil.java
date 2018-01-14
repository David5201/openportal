/*     */ package com.wxpay.utils;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class TenpayUtil
/*     */ {
/*     */   private static Object Server;
/*     */   private static String QRfromGoogle;
/*     */ 
/*     */   public static String toString(Object obj)
/*     */   {
/*  25 */     if (obj == null) {
/*  26 */       return "";
/*     */     }
/*  28 */     return obj.toString();
/*     */   }
/*     */ 
/*     */   public static int toInt(Object obj)
/*     */   {
/*  39 */     int a = 0;
/*     */     try {
/*  41 */       if (obj != null)
/*  42 */         a = Integer.parseInt(obj.toString());
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*  46 */     return a;
/*     */   }
/*     */ 
/*     */   public static String getCurrTime()
/*     */   {
/*  54 */     Date now = new Date();
/*  55 */     SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
/*  56 */     String s = outFormat.format(now);
/*  57 */     return s;
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date date)
/*     */   {
/*  66 */     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
/*  67 */     String strDate = formatter.format(date);
/*  68 */     return strDate;
/*     */   }
/*     */ 
/*     */   public static int buildRandom(int length)
/*     */   {
/*  79 */     int num = 1;
/*  80 */     double random = Math.random();
/*  81 */     if (random < 0.1D) {
/*  82 */       random += 0.1D;
/*     */     }
/*  84 */     for (int i = 0; i < length; i++) {
/*  85 */       num *= 10;
/*     */     }
/*  87 */     return (int)(random * num);
/*     */   }
/*     */ 
/*     */   public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 100 */     if ((request == null) || (response == null)) {
/* 101 */       return "gbk";
/*     */     }
/*     */ 
/* 104 */     String enc = request.getCharacterEncoding();
/* 105 */     if ((enc == null) || ("".equals(enc))) {
/* 106 */       enc = response.getCharacterEncoding();
/*     */     }
/*     */ 
/* 109 */     if ((enc == null) || ("".equals(enc))) {
/* 110 */       enc = "gbk";
/*     */     }
/*     */ 
/* 113 */     return enc;
/*     */   }
/*     */ 
/*     */   public static String URLencode(String content)
/*     */   {
/* 120 */     String URLencode = replace(Server.equals(content), "+", "%20");
/*     */ 
/* 122 */     return URLencode;
/*     */   }
/*     */ 
/*     */   private static String replace(boolean equals, String string, String string2) {
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   public static long getUnixTime(Date date)
/*     */   {
/* 135 */     if (date == null) {
/* 136 */       return 0L;
/*     */     }
/*     */ 
/* 139 */     return date.getTime() / 1000L;
/*     */   }
/*     */ 
/*     */   public static String QRfromGoogle(String chl)
/*     */   {
/* 144 */     int widhtHeight = 300;
/* 145 */     String EC_level = "L";
/* 146 */     int margin = 0;
/*     */ 
/* 148 */     chl = URLencode(chl);
/*     */ 
/* 150 */     String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight + "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;
/*     */ 
/* 152 */     return QRfromGoogle;
/*     */   }
/*     */ 
/*     */   public static String date2String(Date date, String formatType)
/*     */   {
/* 162 */     SimpleDateFormat sdf = new SimpleDateFormat(formatType);
/* 163 */     return sdf.format(date);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.TenpayUtil
 * JD-Core Version:    0.6.2
 */