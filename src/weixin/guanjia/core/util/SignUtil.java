/*    */ package weixin.guanjia.core.util;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class SignUtil
/*    */ {
/*    */   public static boolean checkSignature(String token, String signature, String timestamp, String nonce)
/*    */   {
/* 24 */     String[] arr = { token, timestamp, nonce };
/*    */ 
/* 26 */     Arrays.sort(arr);
/* 27 */     StringBuilder content = new StringBuilder();
/* 28 */     for (int i = 0; i < arr.length; i++) {
/* 29 */       content.append(arr[i]);
/*    */     }
/* 31 */     MessageDigest md = null;
/* 32 */     String tmpStr = null;
/*    */     try
/*    */     {
/* 35 */       md = MessageDigest.getInstance("SHA-1");
/*    */ 
/* 37 */       byte[] digest = md.digest(content.toString().getBytes());
/* 38 */       tmpStr = byteToStr(digest);
/*    */     } catch (NoSuchAlgorithmException e) {
/* 40 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 43 */     content = null;
/*    */ 
/* 45 */     return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
/*    */   }
/*    */ 
/*    */   private static String byteToStr(byte[] byteArray)
/*    */   {
/* 55 */     String strDigest = "";
/* 56 */     for (int i = 0; i < byteArray.length; i++) {
/* 57 */       strDigest = strDigest + byteToHexStr(byteArray[i]);
/*    */     }
/* 59 */     return strDigest;
/*    */   }
/*    */ 
/*    */   private static String byteToHexStr(byte mByte)
/*    */   {
/* 70 */     char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/* 71 */     char[] tempArr = new char[2];
/* 72 */     tempArr[0] = Digit[(mByte >>> 4 & 0xF)];
/* 73 */     tempArr[1] = Digit[(mByte & 0xF)];
/*    */ 
/* 75 */     String s = new String(tempArr);
/* 76 */     return s;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.util.SignUtil
 * JD-Core Version:    0.6.2
 */