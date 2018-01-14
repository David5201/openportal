/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URLEncoder;
/*    */ import java.security.SecureRandom;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ import sun.misc.BASE64Decoder;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class DesUtil
/*    */ {
/*    */   private static final String DES = "DES";
/*    */   private static final String CDES = "DES";
/*    */   public static final String DES_KEY = "wp63vxd9";
/*    */ 
/*    */   public static String encrypt(String data, String key)
/*    */     throws Exception
/*    */   {
/* 30 */     byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes());
/* 31 */     String strs = new BASE64Encoder().encode(bt);
/* 32 */     return strs;
/*    */   }
/*    */ 
/*    */   public static String decrypt(String data, String key)
/*    */     throws IOException, Exception
/*    */   {
/* 44 */     if (data == null)
/* 45 */       return null;
/* 46 */     BASE64Decoder decoder = new BASE64Decoder();
/* 47 */     byte[] buf = decoder.decodeBuffer(data);
/* 48 */     byte[] bt = decrypt(buf, key.getBytes());
/* 49 */     return new String(bt, "utf-8");
/*    */   }
/*    */ 
/*    */   private static byte[] encrypt(byte[] data, byte[] key)
/*    */     throws Exception
/*    */   {
/* 61 */     SecureRandom sr = new SecureRandom();
/*    */ 
/* 63 */     DESKeySpec dks = new DESKeySpec(key);
/*    */ 
/* 65 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 66 */     SecretKey securekey = keyFactory.generateSecret(dks);
/*    */ 
/* 68 */     Cipher cipher = Cipher.getInstance("DES");
/*    */ 
/* 70 */     cipher.init(1, securekey, sr);
/* 71 */     return cipher.doFinal(data);
/*    */   }
/*    */ 
/*    */   private static byte[] decrypt(byte[] data, byte[] key)
/*    */     throws Exception
/*    */   {
/* 83 */     SecureRandom sr = new SecureRandom();
/*    */ 
/* 85 */     DESKeySpec dks = new DESKeySpec(key);
/*    */ 
/* 87 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 88 */     SecretKey securekey = keyFactory.generateSecret(dks);
/*    */ 
/* 90 */     Cipher cipher = Cipher.getInstance("DES");
/*    */ 
/* 92 */     cipher.init(2, securekey, sr);
/* 93 */     return cipher.doFinal(data);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) throws Exception {
/* 97 */     String str = encrypt("15850503315", "wifi_njqxq");
/* 98 */     System.out.println(str);
/* 99 */     System.out.println(URLEncoder.encode(str, "utf-8"));
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.DesUtil
 * JD-Core Version:    0.6.2
 */