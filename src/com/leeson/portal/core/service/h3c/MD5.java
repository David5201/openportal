/*    */ package com.leeson.portal.core.service.h3c;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ 
/*    */ public class MD5
/*    */ {
/*    */   private byte[] md5Buffer;
/*    */ 
/*    */   public void update(byte[] paramArrayOfByte)
/*    */   {
/* 12 */     int i = this.md5Buffer == null ? 0 : this.md5Buffer.length;
/* 13 */     int j = paramArrayOfByte == null ? 0 : paramArrayOfByte.length;
/* 14 */     byte[] arrayOfByte = new byte[i + j];
/* 15 */     if (i != 0)
/* 16 */       System.arraycopy(this.md5Buffer, 0, arrayOfByte, 0, i);
/* 17 */     if (j != 0)
/* 18 */       System.arraycopy(paramArrayOfByte, 0, arrayOfByte, i, j);
/* 19 */     this.md5Buffer = arrayOfByte;
/*    */   }
/*    */ 
/*    */   public void update(byte paramByte)
/*    */   {
/* 24 */     int i = this.md5Buffer == null ? 0 : this.md5Buffer.length;
/* 25 */     byte[] arrayOfByte = new byte[i + 1];
/* 26 */     if (i != 0)
/* 27 */       System.arraycopy(this.md5Buffer, 0, arrayOfByte, 0, i);
/* 28 */     arrayOfByte[i] = paramByte;
/* 29 */     this.md5Buffer = arrayOfByte;
/*    */   }
/*    */ 
/*    */   public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*    */   {
/* 34 */     if (paramInt1 > paramInt2)
/* 35 */       return;
/* 36 */     int i = this.md5Buffer == null ? 0 : this.md5Buffer.length;
/* 37 */     int j = paramArrayOfByte == null ? 0 : paramInt2 - paramInt1;
/* 38 */     byte[] arrayOfByte = new byte[i + j];
/* 39 */     if (i != 0)
/* 40 */       System.arraycopy(this.md5Buffer, 0, arrayOfByte, 0, i);
/* 41 */     if (j != 0)
/* 42 */       System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, i, j);
/* 43 */     this.md5Buffer = arrayOfByte;
/*    */   }
/*    */ 
/*    */   public byte[] finalFunc()
/*    */   {
/* 48 */     MessageDigest localMessageDigest = null;
/* 49 */     if (this.md5Buffer == null)
/* 50 */       return null;
/*    */     try
/*    */     {
/* 53 */       localMessageDigest = MessageDigest.getInstance("MD5");
/*    */     }
/*    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/*    */     {
/*    */     }
/* 58 */     if (localMessageDigest != null)
/*    */     {
/* 60 */       localMessageDigest.update(this.md5Buffer);
/* 61 */       return localMessageDigest.digest();
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.h3c.MD5
 * JD-Core Version:    0.6.2
 */