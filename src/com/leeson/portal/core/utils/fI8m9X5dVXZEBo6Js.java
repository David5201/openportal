/*     */ package com.leeson.portal.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.NetworkInterface;
/*     */ import java.util.Enumeration;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ 
/*     */ public class fI8m9X5dVXZEBo6Js
/*     */ {
/*     */   private static String cHZVOWoHedyv()
/*     */   {
/*  17 */     StringBuilder M2MzQ0NTM3NDEzNjM4MzQzN = new StringBuilder();
/*     */     try {
/*  19 */       Enumeration el = 
/*  20 */         NetworkInterface.getNetworkInterfaces();
/*  21 */       while (el.hasMoreElements()) {
/*  22 */         byte[] mac = ((NetworkInterface)el.nextElement()).getHardwareAddress();
/*  23 */         if (mac != null)
/*     */         {
/*  25 */           StringBuilder builder = new StringBuilder();
/*  26 */           for (int i = 0; i < mac.length; i++) {
/*  27 */             builder.append(hexByte(mac[i]));
/*     */           }
/*  29 */           M2MzQ0NTM3NDEzNjM4MzQzN.append(builder);
/*     */         }
/*     */       }
/*     */     } catch (Exception localException) {
/*     */     }
/*  34 */     String result = M2MzQ0NTM3NDEzNjM4MzQzN.toString();
/*  35 */     result = result.toUpperCase();
/*  36 */     result = result.replace("00000000000000E0", "");
/*  37 */     return "G" + result;
/*     */   }
/*     */ 
/*     */   private static String hexByte(byte b) {
/*  41 */     String s = "000000" + Integer.toHexString(b);
/*  42 */     return s.substring(s.length() - 2);
/*     */   }
/*     */ 
/*     */   private static String XTRuxvYEzmYNhYQMW() {
/*  46 */     String result = "";
/*     */     try {
/*  48 */       File file = File.createTempFile("tmp", ".vbs");
/*  49 */       file.deleteOnExit();
/*  50 */       FileWriter fw = new FileWriter(file);
/*  51 */       String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";
/*     */ 
/*  59 */       fw.write(vbs);
/*  60 */       fw.close();
/*  61 */       Process p = Runtime.getRuntime().exec(
/*  62 */         "cscript //NoLogo " + file.getPath());
/*  63 */       BufferedReader input = new BufferedReader(new InputStreamReader(
/*  64 */         p.getInputStream()));
/*     */       String line;
/*  66 */       while ((line = input.readLine()) != null)
/*     */       {
/*  67 */         result = result + line;
/*     */       }
/*  69 */       input.close();
/*  70 */       file.delete();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*  74 */     if ((result.trim().length() < 1) || (result == null)) {
/*  75 */       result = "E";
/*     */     }
/*  77 */     return result.trim();
/*     */   }
/*     */ 
/*     */   private static String zITsztQAqoO4Ikggk() {
/*  81 */     String result = "";
/*     */     try {
/*  83 */       Process p = Runtime.getRuntime().exec("dmidecode -t processor | grep ID");
/*  84 */       BufferedReader input = new BufferedReader(new InputStreamReader(
/*  85 */         p.getInputStream()));
/*     */       String line;
/*  87 */       while ((line = input.readLine()) != null)
/*     */       {
/*  88 */         result = result + line;
/*     */       }
/*  90 */       input.close();
/*     */ 
/*  92 */       if ((result.trim().length() < 1) || (result == null) || (!result.contains("ID:"))) {
/*  93 */         return "E";
/*     */       }
/*  95 */       String[] infos = result.split("ID:");
/*  96 */       result = infos[1];
/*  97 */       result = result.trim();
/*  98 */       result = result.replace(" ", "");
/*  99 */       if (result.length() >= 16) {
/* 100 */         result = result.substring(0, 16);
/*     */       }
/*     */ 
/* 103 */       return result.trim();
/*     */     } catch (Exception e) {
/*     */     }
/* 106 */     return "E";
/*     */   }
/*     */ 
/*     */   private static String S3VX4Nrex1t5VqAQ2()
/*     */   {
/* 112 */     String RTc5NEI1RTU5NTg2MjYyNj = System.getProperty("os.name");
/* 113 */     if (RTc5NEI1RTU5NTg2MjYyNj.startsWith("W"))
/* 114 */       return "WG" + XTRuxvYEzmYNhYQMW();
/* 115 */     if (RTc5NEI1RTU5NTg2MjYyNj.startsWith("L")) {
/* 116 */       return "LG" + zITsztQAqoO4Ikggk();
/*     */     }
/* 118 */     return "EG";
/*     */   }
/*     */ 
/*     */   public static String pV3Y5xivmveI277H6QS87V()
/*     */   {
/*     */     try {
/* 124 */       StringBuilder RTRCOEFERTU5QkJERTZCMkIzRTU4Qz = new StringBuilder();
/* 125 */       RTRCOEFERTU5QkJERTZCMkIzRTU4Qz.append(S3VX4Nrex1t5VqAQ2());
/* 126 */       RTRCOEFERTU5QkJERTZCMkIzRTU4Qz.append(cHZVOWoHedyv());
/* 127 */       String k8KVEI3aa5bvOQ6W = RTRCOEFERTU5QkJERTZCMkIzRTU4Qz.toString();
/* 128 */       String KMyPAGX5Qnk8R7 = AaHpl8Ha9bNPen9OLddV.encryptBASE64(k8KVEI3aa5bvOQ6W);
/*     */ 
/* 130 */       StringBuilder c5NEI1RTU5NTg = new StringBuilder();
/* 131 */       c5NEI1RTU5NTg.append(DigestUtils.md5Hex(k8KVEI3aa5bvOQ6W).toUpperCase());
/* 132 */       c5NEI1RTU5NTg.append(AaHpl8Ha9bNPen9OLddV.encode(KMyPAGX5Qnk8R7));
/* 133 */       c5NEI1RTU5NTg.append(DigestUtils.md5Hex(KMyPAGX5Qnk8R7).toUpperCase());
/*     */ 
/* 135 */       String zUzNDM2NDMzNDM2MzQ0NT = "";
/* 136 */       if (stringUtils.isBlank(KMyPAGX5Qnk8R7)) {
/* 137 */         zUzNDM2NDMzNDM2MzQ0NT = "E";
/*     */       }
/* 139 */       return c5NEI1RTU5NTg.toString();
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 143 */     return "E";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.fI8m9X5dVXZEBo6Js
 * JD-Core Version:    0.6.2
 */