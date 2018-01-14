/*     */ package com.leeson.portal.core.utils;
/*     */ 
/*     */ public class Tools
/*     */ {
/*     */   public static String IntToHex(int n)
/*     */   {
/*   6 */     char[] ch = new char[20];
/*   7 */     int nIndex = 0;
/*     */     while (true) {
/*   9 */       int m = n / 16;
/*  10 */       int k = n % 16;
/*  11 */       if (k == 15)
/*  12 */         ch[nIndex] = 'F';
/*  13 */       else if (k == 14)
/*  14 */         ch[nIndex] = 'E';
/*  15 */       else if (k == 13)
/*  16 */         ch[nIndex] = 'D';
/*  17 */       else if (k == 12)
/*  18 */         ch[nIndex] = 'C';
/*  19 */       else if (k == 11)
/*  20 */         ch[nIndex] = 'B';
/*  21 */       else if (k == 10)
/*  22 */         ch[nIndex] = 'A';
/*     */       else
/*  24 */         ch[nIndex] = ((char)(48 + k));
/*  25 */       nIndex++;
/*  26 */       if (m == 0)
/*     */         break;
/*  28 */       n = m;
/*     */     }
/*  30 */     StringBuffer sb = new StringBuffer();
/*  31 */     sb.append(ch, 0, nIndex);
/*  32 */     sb.reverse();
/*  33 */     String strHex = new String("0x");
/*  34 */     strHex = strHex + sb.toString();
/*  35 */     return strHex;
/*     */   }
/*     */ 
/*     */   public static int HexToInt(String strHex)
/*     */   {
/*  40 */     int nResult = 0;
/*  41 */     if (!IsHex(strHex))
/*  42 */       return nResult;
/*  43 */     String str = strHex.toUpperCase();
/*  44 */     if ((str.length() > 2) && 
/*  45 */       (str.charAt(0) == '0') && (str.charAt(1) == 'X')) {
/*  46 */       str = str.substring(2);
/*     */     }
/*     */ 
/*  49 */     int nLen = str.length();
/*  50 */     for (int i = 0; i < nLen; i++) {
/*  51 */       char ch = str.charAt(nLen - i - 1);
/*     */       try {
/*  53 */         nResult += GetHex(ch) * GetPower(16, i);
/*     */       }
/*     */       catch (Exception e) {
/*  56 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  59 */     return nResult;
/*     */   }
/*     */ 
/*     */   public static int GetHex(char ch) throws Exception
/*     */   {
/*  64 */     if ((ch >= '0') && (ch <= '9'))
/*  65 */       return ch - '0';
/*  66 */     if ((ch >= 'a') && (ch <= 'f'))
/*  67 */       return ch - 'a' + 10;
/*  68 */     if ((ch >= 'A') && (ch <= 'F'))
/*  69 */       return ch - 'A' + 10;
/*  70 */     throw new Exception("error param");
/*     */   }
/*     */ 
/*     */   public static int GetPower(int nValue, int nCount) throws Exception
/*     */   {
/*  75 */     if (nCount < 0)
/*  76 */       throw new Exception("nCount can't small than 1!");
/*  77 */     if (nCount == 0)
/*  78 */       return 1;
/*  79 */     int nSum = 1;
/*  80 */     for (int i = 0; i < nCount; i++) {
/*  81 */       nSum *= nValue;
/*     */     }
/*  83 */     return nSum;
/*     */   }
/*     */ 
/*     */   public static boolean IsHex(String strHex)
/*     */   {
/*  88 */     int i = 0;
/*  89 */     if ((strHex.length() > 2) && 
/*  90 */       (strHex.charAt(0) == '0') && (
/*  91 */       (strHex.charAt(1) == 'X') || (strHex.charAt(1) == 'x')));
/*  92 */     for (i = 2; 
/*  95 */       i < strHex.length(); i++) {
/*  96 */       char ch = strHex.charAt(i);
/*  97 */       if (((ch < '0') || (ch > '9')) && ((ch < 'A') || (ch > 'F')) && (
/*  98 */         (ch < 'a') || (ch > 'f')))
/*     */       {
/* 100 */         return false;
/*     */       }
/*     */     }
/* 102 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.Tools
 * JD-Core Version:    0.6.2
 */