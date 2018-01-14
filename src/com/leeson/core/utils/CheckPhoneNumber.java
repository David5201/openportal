/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class CheckPhoneNumber
/*     */ {
/*  19 */   public static String yd = "^1(3[4-9]|5[01789]|8[78])\\d{8}$";
/*     */ 
/*  21 */   public static String lt = "^1(3[0-2]|5[256]|8[56])\\d{8}$";
/*     */ 
/*  23 */   public static String dx = "^(18[09]|1[35]3)\\d{8}$";
/*     */ 
/*     */   public static boolean isPhoneNumberDX(String number)
/*     */   {
/*  32 */     Pattern p = Pattern.compile(dx);
/*  33 */     Matcher m = p.matcher(number);
/*  34 */     return m.matches();
/*     */   }
/*     */ 
/*     */   public static boolean isPhoneNumberAllDX(String[] list)
/*     */   {
/*  44 */     Pattern p = Pattern.compile(dx);
/*  45 */     String[] arrayOfString = list; int j = list.length; for (int i = 0; i < j; i++) { String string = arrayOfString[i];
/*  46 */       if (string.length() != 11)
/*  47 */         return false;
/*  48 */       Matcher m = p.matcher(string);
/*  49 */       if (!m.matches()) {
/*  50 */         return false;
/*     */       }
/*     */     }
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean isPhoneNumberYD(String number)
/*     */   {
/*  63 */     Pattern p = Pattern.compile(yd);
/*  64 */     Matcher m = p.matcher(number);
/*  65 */     return m.matches();
/*     */   }
/*     */ 
/*     */   public static boolean isPhoneNumberAllYD(String[] list)
/*     */   {
/*  75 */     Pattern p = Pattern.compile(yd);
/*  76 */     String[] arrayOfString = list; int j = list.length; for (int i = 0; i < j; i++) { String string = arrayOfString[i];
/*  77 */       if (string.length() != 11)
/*  78 */         return false;
/*  79 */       Matcher m = p.matcher(string);
/*  80 */       if (!m.matches()) {
/*  81 */         return false;
/*     */       }
/*     */     }
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean isPhoneNumberLT(String number)
/*     */   {
/*  94 */     Pattern p = Pattern.compile(lt);
/*  95 */     Matcher m = p.matcher(number);
/*  96 */     return m.matches();
/*     */   }
/*     */ 
/*     */   public static boolean isPhoneNumberAllLT(String[] list)
/*     */   {
/* 106 */     Pattern p = Pattern.compile(lt);
/* 107 */     String[] arrayOfString = list; int j = list.length; for (int i = 0; i < j; i++) { String string = arrayOfString[i];
/* 108 */       if (string.length() != 11)
/* 109 */         return false;
/* 110 */       Matcher m = p.matcher(string);
/* 111 */       if (!m.matches()) {
/* 112 */         return false;
/*     */       }
/*     */     }
/* 115 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.CheckPhoneNumber
 * JD-Core Version:    0.6.2
 */