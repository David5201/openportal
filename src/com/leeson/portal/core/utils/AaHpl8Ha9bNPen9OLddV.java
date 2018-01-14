/*     */ package com.leeson.portal.core.utils;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import sun.misc.BASE64Decoder;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class AaHpl8Ha9bNPen9OLddV
/*     */ {
/*  63 */   private static String hexString = "0123456789ABCDEF";
/*     */ 
/*     */   public static String toHexString(String s)
/*     */   {
/*  13 */     String str = "";
/*  14 */     for (int i = 0; i < s.length(); i++) {
/*  15 */       int ch = s.charAt(i);
/*  16 */       String s4 = Integer.toHexString(ch);
/*  17 */       str = str + s4;
/*     */     }
/*  19 */     return str;
/*     */   }
/*     */ 
/*     */   public static String toStringHex1(String s)
/*     */   {
/*  24 */     byte[] baKeyword = new byte[s.length() / 2];
/*  25 */     for (int i = 0; i < baKeyword.length; i++)
/*     */       try {
/*  27 */         baKeyword[i] = ((byte)(0xFF & Integer.parseInt(
/*  28 */           s.substring(i * 2, i * 2 + 2), 16)));
/*     */       } catch (Exception e) {
/*  30 */         e.printStackTrace();
/*     */       }
/*     */     try
/*     */     {
/*  34 */       s = new String(baKeyword, "utf-8");
/*     */     } catch (Exception e1) {
/*  36 */       e1.printStackTrace();
/*     */     }
/*  38 */     return s;
/*     */   }
/*     */ 
/*     */   public static String toStringHex2(String s)
/*     */   {
/*  43 */     byte[] baKeyword = new byte[s.length() / 2];
/*  44 */     for (int i = 0; i < baKeyword.length; i++)
/*     */       try {
/*  46 */         baKeyword[i] = ((byte)(0xFF & Integer.parseInt(
/*  47 */           s.substring(i * 2, i * 2 + 2), 16)));
/*     */       } catch (Exception e) {
/*  49 */         e.printStackTrace();
/*     */       }
/*     */     try
/*     */     {
/*  53 */       s = new String(baKeyword, "utf-8");
/*     */     } catch (Exception e1) {
/*  55 */       e1.printStackTrace();
/*     */     }
/*  57 */     return s;
/*     */   }
/*     */ 
/*     */   public static String encode(String str)
/*     */   {
/*  70 */     byte[] bytes = null;
/*     */     try {
/*  72 */       bytes = str.getBytes("utf-8");
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/*     */     }
/*  75 */     StringBuilder sb = new StringBuilder(bytes.length * 2);
/*     */ 
/*  77 */     for (int i = 0; i < bytes.length; i++) {
/*  78 */       sb.append(hexString.charAt((bytes[i] & 0xF0) >> 4));
/*  79 */       sb.append(hexString.charAt((bytes[i] & 0xF) >> 0));
/*     */     }
/*  81 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String decode(String bytes)
/*     */   {
/*  88 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(
/*  89 */       bytes.length() / 2);
/*     */ 
/*  91 */     for (int i = 0; i < bytes.length(); i += 2)
/*  92 */       baos.write(hexString.indexOf(bytes.charAt(i)) << 4 | hexString
/*  93 */         .indexOf(bytes.charAt(i + 1)));
/*  94 */     String result = "";
/*     */     try {
/*  96 */       result = new String(baos.toByteArray(), "utf-8");
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/*     */     }
/*  99 */     return result;
/*     */   }
/*     */ 
/*     */   public static void printHexString(String hint, byte[] b)
/*     */   {
/* 115 */     System.out.print(hint);
/* 116 */     for (int i = 0; i < b.length; i++) {
/* 117 */       String hex = Integer.toHexString(b[i] & 0xFF);
/* 118 */       if (hex.length() == 1) {
/* 119 */         hex = '0' + hex;
/*     */       }
/* 121 */       System.out.print(hex.toUpperCase() + " ");
/*     */     }
/* 123 */     System.out.println("");
/*     */   }
/*     */ 
/*     */   public static String Bytes2HexString(byte[] b)
/*     */   {
/* 132 */     String ret = "";
/* 133 */     for (int i = 0; i < b.length; i++) {
/* 134 */       String hex = Integer.toHexString(b[i] & 0xFF);
/* 135 */       if (hex.length() == 1) {
/* 136 */         hex = '0' + hex;
/*     */       }
/* 138 */       ret = ret + hex.toUpperCase();
/*     */     }
/* 140 */     return ret;
/*     */   }
/*     */ 
/*     */   public static byte uniteBytes(byte src0, byte src1)
/*     */   {
/* 153 */     byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
/* 154 */       .byteValue();
/* 155 */     _b0 = (byte)(_b0 << 4);
/* 156 */     byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
/* 157 */       .byteValue();
/* 158 */     byte ret = (byte)(_b0 ^ _b1);
/* 159 */     return ret;
/*     */   }
/*     */ 
/*     */   public static byte[] HexString2Bytes(String src)
/*     */   {
/* 171 */     byte[] ret = new byte[8];
/* 172 */     byte[] tmp = src.getBytes();
/* 173 */     for (int i = 0; i < 8; i++) {
/* 174 */       ret[i] = uniteBytes(tmp[(i * 2)], tmp[(i * 2 + 1)]);
/*     */     }
/* 176 */     return ret;
/*     */   }
/*     */ 
/*     */   public static String decryptBASE64(String key)
/*     */   {
/* 187 */     String result = "";
/*     */     try {
/* 189 */       result = new String(new BASE64Decoder().decodeBuffer(key));
/*     */     } catch (Exception localException) {
/*     */     }
/* 192 */     return result;
/*     */   }
/*     */ 
/*     */   public static String encryptBASE64(String key)
/*     */   {
/* 203 */     String result = "";
/*     */     try {
/* 205 */       result = new BASE64Encoder().encodeBuffer(key.getBytes());
/*     */     } catch (Exception localException) {
/*     */     }
/* 208 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.AaHpl8Ha9bNPen9OLddV
 * JD-Core Version:    0.6.2
 */