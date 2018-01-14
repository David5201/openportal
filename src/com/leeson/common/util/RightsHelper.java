/*    */ package com.leeson.common.util;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ public class RightsHelper
/*    */ {
/*    */   public static BigInteger sumRights(int[] rights)
/*    */   {
/* 16 */     BigInteger num = new BigInteger("0");
/* 17 */     for (int i = 0; i < rights.length; i++) {
/* 18 */       num = num.setBit(rights[i]);
/*    */     }
/* 20 */     return num;
/*    */   }
/*    */ 
/*    */   public static BigInteger sumRights(String[] rights)
/*    */   {
/* 28 */     BigInteger num = new BigInteger("0");
/* 29 */     for (int i = 0; i < rights.length; i++) {
/* 30 */       num = num.setBit(Integer.parseInt(rights[i]));
/*    */     }
/* 32 */     return num;
/*    */   }
/*    */ 
/*    */   public static boolean testRights(BigInteger sum, int targetRights)
/*    */   {
/* 42 */     return sum.testBit(targetRights);
/*    */   }
/*    */ 
/*    */   public static boolean testRights(String sum, int targetRights)
/*    */   {
/* 52 */     if (Tools.isEmpty(sum))
/* 53 */       return false;
/* 54 */     return testRights(new BigInteger(sum), targetRights);
/*    */   }
/*    */ 
/*    */   public static boolean testRights(String sum, String targetRights)
/*    */   {
/* 64 */     if (Tools.isEmpty(sum))
/* 65 */       return false;
/* 66 */     return testRights(new BigInteger(sum), targetRights);
/*    */   }
/*    */ 
/*    */   public static boolean testRights(BigInteger sum, String targetRights)
/*    */   {
/* 76 */     return testRights(sum, Integer.parseInt(targetRights));
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.RightsHelper
 * JD-Core Version:    0.6.2
 */