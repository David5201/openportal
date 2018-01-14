/*    */ package com.wxpay.utils;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.util.Iterator;
import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Random;
/*    */ import java.util.Set;
/*    */ import java.util.SortedMap;
/*    */ 
/*    */ public class Sha1Util
/*    */ {
/*    */   public static String getNonceStr()
/*    */   {
/* 24 */     Random random = new Random();
/* 25 */     return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
/*    */   }
/*    */   public static String getTimeStamp() {
/* 28 */     return String.valueOf(System.currentTimeMillis() / 1000L);
/*    */   }
/*    */ 
/*    */   public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception
/*    */   {
/* 33 */     StringBuffer sb = new StringBuffer();
/* 34 */     Set es = signParams.entrySet();
/* 35 */     Iterator it = es.iterator();
/* 36 */     while (it.hasNext()) {
/* 37 */       Map.Entry entry = (Map.Entry)it.next();
/* 38 */       String k = (String)entry.getKey();
/* 39 */       String v = (String)entry.getValue();
/* 40 */       sb.append(k + "=" + v + "&");
/*    */     }
/*    */ 
/* 43 */     String params = sb.substring(0, sb.lastIndexOf("&"));
/*    */ 
/* 46 */     return getSha1(params);
/*    */   }
/*    */ 
/*    */   public static String getSha1(String str) {
/* 50 */     if ((str == null) || (str.length() == 0)) {
/* 51 */       return null;
/*    */     }
/* 53 */     char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
/* 54 */       'a', 'b', 'c', 'd', 'e', 'f' };
/*    */     try
/*    */     {
/* 57 */       MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
/* 58 */       mdTemp.update(str.getBytes("GBK"));
/*    */ 
/* 60 */       byte[] md = mdTemp.digest();
/* 61 */       int j = md.length;
/* 62 */       char[] buf = new char[j * 2];
/* 63 */       int k = 0;
/* 64 */       for (int i = 0; i < j; i++) {
/* 65 */         byte byte0 = md[i];
/* 66 */         buf[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
/* 67 */         buf[(k++)] = hexDigits[(byte0 & 0xF)];
/*    */       }
/* 69 */       return new String(buf); } catch (Exception e) {
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.Sha1Util
 * JD-Core Version:    0.6.2
 */