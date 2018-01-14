/*    */ package com.wxpay.utils;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ public class MD5Util
/*    */ {
/* 43 */   private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", 
/* 44 */     "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
/*    */ 
/*    */   private static String byteArrayToHexString(byte[] b)
/*    */   {
/* 11 */     StringBuffer resultSb = new StringBuffer();
/* 12 */     for (int i = 0; i < b.length; i++) {
/* 13 */       resultSb.append(byteToHexString(b[i]));
/*    */     }
/* 15 */     return resultSb.toString();
/*    */   }
/*    */ 
/*    */   private static String byteToHexString(byte b) {
/* 19 */     int n = b;
/* 20 */     if (n < 0)
/* 21 */       n += 256;
/* 22 */     int d1 = n / 16;
/* 23 */     int d2 = n % 16;
/* 24 */     return hexDigits[d1] + hexDigits[d2];
/*    */   }
/*    */ 
/*    */   public static String MD5Encode(String origin, String charsetname) {
/* 28 */     String resultString = null;
/*    */     try {
/* 30 */       resultString = new String(origin);
/* 31 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 32 */       if ((charsetname == null) || ("".equals(charsetname)))
/* 33 */         resultString = byteArrayToHexString(md.digest(resultString
/* 34 */           .getBytes()));
/*    */       else
/* 36 */         resultString = byteArrayToHexString(md.digest(resultString
/* 37 */           .getBytes(charsetname)));
/*    */     } catch (Exception localException) {
/*    */     }
/* 40 */     return resultString;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.MD5Util
 * JD-Core Version:    0.6.2
 */