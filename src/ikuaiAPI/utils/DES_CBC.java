/*    */ package ikuaiAPI.utils;
/*    */ 
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ 
/*    */ public class DES_CBC
/*    */ {
/*    */   public static byte[] encrypt(String message, String key, String ivStr)
/*    */     throws Exception
/*    */   {
/* 14 */     Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
/* 15 */     DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
/* 16 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 17 */     SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
/* 18 */     IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
/* 19 */     cipher.init(1, secretKey, iv);
/* 20 */     return cipher.doFinal(message.getBytes());
/*    */   }
/*    */ 
/*    */   public static String decrypt(byte[] message, String key, String ivStr) throws Exception
/*    */   {
/* 25 */     Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
/* 26 */     DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
/* 27 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 28 */     SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
/* 29 */     IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
/* 30 */     cipher.init(2, secretKey, iv);
/* 31 */     return new String(cipher.doFinal(message));
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     ikuaiAPI.utils.DES_CBC
 * JD-Core Version:    0.6.2
 */