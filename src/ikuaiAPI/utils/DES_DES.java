/*    */ package ikuaiAPI.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.security.SecureRandom;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ import sun.misc.BASE64Decoder;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class DES_DES
/*    */ {
/*    */   private static final String DES = "DES";
/*    */ 
/*    */   public static String encrypt(String data, String key)
/*    */     throws Exception
/*    */   {
/* 18 */     byte[] bt = encrypt(data.getBytes(), key.getBytes());
/* 19 */     String strs = new BASE64Encoder().encode(bt);
/* 20 */     return strs;
/*    */   }
/*    */ 
/*    */   public static String decrypt(String data, String key) throws IOException, Exception
/*    */   {
/* 25 */     if (data == null)
/* 26 */       return null;
/* 27 */     BASE64Decoder decoder = new BASE64Decoder();
/* 28 */     byte[] buf = decoder.decodeBuffer(data);
/* 29 */     byte[] bt = decrypt(buf, key.getBytes());
/* 30 */     return new String(bt);
/*    */   }
/*    */ 
/*    */   private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
/* 34 */     SecureRandom sr = new SecureRandom();
/* 35 */     DESKeySpec dks = new DESKeySpec(key);
/* 36 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 37 */     SecretKey securekey = keyFactory.generateSecret(dks);
/* 38 */     Cipher cipher = Cipher.getInstance("DES");
/* 39 */     cipher.init(1, securekey, sr);
/* 40 */     return cipher.doFinal(data);
/*    */   }
/*    */ 
/*    */   private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
/* 44 */     SecureRandom sr = new SecureRandom();
/* 45 */     DESKeySpec dks = new DESKeySpec(key);
/* 46 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 47 */     SecretKey securekey = keyFactory.generateSecret(dks);
/* 48 */     Cipher cipher = Cipher.getInstance("DES");
/* 49 */     cipher.init(2, securekey, sr);
/* 50 */     return cipher.doFinal(data);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     ikuaiAPI.utils.DES_DES
 * JD-Core Version:    0.6.2
 */