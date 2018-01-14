/*    */ package com.leeson.portal.core.service.utils;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ 
/*    */ public class PortalUtil
/*    */ {
/*    */   public static String Getbyte2HexString(byte[] b)
/*    */   {
/* 20 */     StringBuilder sb = new StringBuilder();
/* 21 */     for (int i = 0; i < b.length; i++) {
/* 22 */       String hex = Integer.toHexString(b[i] & 0xFF);
/* 23 */       if (hex.length() == 1) {
/* 24 */         hex = '0' + hex;
/*    */       }
/* 26 */       sb.append(hex);
/*    */     }
/* 28 */     return "[" + sb.toString() + "]";
/*    */   }
/*    */ 
/*    */   public static String Getbyte2MacString(byte[] b) {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append("");
/* 34 */     for (int i = 0; i < b.length; i++) {
/* 35 */       String hex = Integer.toHexString(b[i] & 0xFF);
/* 36 */       if (hex.length() == 1) {
/* 37 */         hex = '0' + hex;
/*    */       }
/* 39 */       sb.append(hex);
/* 40 */       if (i < b.length - 1) {
/* 41 */         sb.append(":");
/*    */       }
/*    */     }
/* 44 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static String MacFormat(String ikmac) {
/* 48 */     if (stringUtils.isNotBlank(ikmac)) {
/* 49 */       ikmac = ikmac.replace("-", "");
/* 50 */       ikmac = ikmac.replace(":", "");
/* 51 */       ikmac = ikmac.trim().toLowerCase();
/* 52 */       StringBuilder sb = new StringBuilder();
/* 53 */       sb.append("");
/* 54 */       int count = ikmac.length();
/* 55 */       for (int i = 1; i <= count; i++) {
/* 56 */         sb.append(ikmac.substring(i - 1, i));
/* 57 */         if ((i < count) && (i % 2 == 0)) {
/* 58 */           sb.append(":");
/*    */         }
/*    */       }
/* 61 */       return sb.toString();
/*    */     }
/* 63 */     return "";
/*    */   }
/*    */ 
/*    */   public static String MacFormat1(String ikmac)
/*    */   {
/* 68 */     if (stringUtils.isNotBlank(ikmac)) {
/* 69 */       ikmac = ikmac.replace("-", "");
/* 70 */       ikmac = ikmac.replace(":", "");
/* 71 */       ikmac = ikmac.trim().toLowerCase();
/* 72 */       return ikmac;
/*    */     }
/* 74 */     return "";
/*    */   }
/*    */ 
/*    */   public static byte[] SerialNo()
/*    */   {
/* 79 */     byte[] SerialNo = new byte[2];
/* 80 */     short SerialNo_int = (short)(int)(1.0D + Math.random() * 32767.0D);
/* 81 */     for (int i = 0; i < 2; i++) {
/* 82 */       int offset = (SerialNo.length - 1 - i) * 8;
/* 83 */       SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
/*    */     }
/* 85 */     return SerialNo;
/*    */   }
/*    */ 
/*    */   public static byte[] SerialNo(short SerialNo_int) {
/* 89 */     byte[] SerialNo = new byte[2];
/* 90 */     for (int i = 0; i < 2; i++) {
/* 91 */       int offset = (SerialNo.length - 1 - i) * 8;
/* 92 */       SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
/*    */     }
/* 94 */     return SerialNo;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.utils.PortalUtil
 * JD-Core Version:    0.6.2
 */