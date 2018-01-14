/*    */ package com.leeson.portal.core.service.h3c;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.net.InetAddress;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class CommonFunctions
/*    */ {
/*    */   public static BigInteger ipv4ToBytes(String ipv4)
/*    */   {
/* 14 */     byte[] ret = new byte[5];
/* 15 */     ret[0] = 0;
/*    */ 
/* 17 */     int position1 = ipv4.indexOf(".");
/* 18 */     int position2 = ipv4.indexOf(".", position1 + 1);
/* 19 */     int position3 = ipv4.indexOf(".", position2 + 1);
/*    */ 
/* 21 */     ret[1] = ((byte)Integer.parseInt(ipv4.substring(0, position1)));
/* 22 */     ret[2] = ((byte)Integer.parseInt(ipv4.substring(position1 + 1, 
/* 23 */       position2)));
/* 24 */     ret[3] = ((byte)Integer.parseInt(ipv4.substring(position2 + 1, 
/* 25 */       position3)));
/* 26 */     ret[4] = ((byte)Integer.parseInt(ipv4.substring(position3 + 1)));
/* 27 */     BigInteger ipBig = new BigInteger(ret);
/* 28 */     return ipBig;
/*    */   }
/*    */ 
/*    */   public static byte[] convertIntToByteArray(int paramInt)
/*    */   {
/* 33 */     byte[] arrayOfByte = new byte[4];
/* 34 */     int i = 0;
/* 35 */     for (int j = 0; j < 4; j++)
/*    */     {
/* 37 */       i = 4 - j - 1;
/* 38 */       arrayOfByte[i] = ((byte)(paramInt >> j * 8 & 0xFF));
/*    */     }
/* 40 */     return arrayOfByte;
/*    */   }
/*    */ 
/*    */   public static byte[] convertBigIntegerTo16Bytes(BigInteger paramBigInteger)
/*    */   {
/* 45 */     byte[] arrayOfByte1 = new byte[16];
/* 46 */     Arrays.fill(arrayOfByte1, (byte)0);
/* 47 */     byte[] arrayOfByte2 = paramBigInteger.toByteArray();
/* 48 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 16 - arrayOfByte2.length, arrayOfByte2.length);
/* 49 */     return arrayOfByte1;
/*    */   }
/*    */ 
/*    */   public static InetAddress convertIntToInetAddress(int paramInt)
/*    */   {
/*    */     try
/*    */     {
/* 56 */       return InetAddress.getByAddress(convertIntToByteArray(paramInt));
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 61 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.h3c.CommonFunctions
 * JD-Core Version:    0.6.2
 */