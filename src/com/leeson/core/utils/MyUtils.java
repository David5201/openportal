/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ 
/*     */ public class MyUtils
/*     */ {
/*     */   public static boolean checkUserName(String userName)
/*     */   {
/*  17 */     String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5])+";
/*  18 */     Pattern p = Pattern.compile(regex);
/*  19 */     Matcher m = p.matcher(userName);
/*  20 */     return m.matches();
/*     */   }
/*     */ 
/*     */   public static boolean checkInput(String input)
/*     */   {
/*  25 */     input = URLDecoder.decode(input);
/*  26 */     input = StringEscapeUtils.unescapeHtml(input);
/*  27 */     String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5]|[\\s\t\n])+";
/*  28 */     Pattern p = Pattern.compile(regex);
/*  29 */     Matcher m = p.matcher(input);
/*  30 */     return m.matches();
/*     */   }
/*     */ 
/*     */   public static String creatMac()
/*     */   {
/*  35 */     byte[] SerialNo = new byte[2];
/*  36 */     short SerialNo_int = (short)(int)(1.0D + Math.random() * 32767.0D);
/*  37 */     for (int i = 0; i < 2; i++) {
/*  38 */       int offset = (SerialNo.length - 1 - i) * 8;
/*  39 */       SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
/*     */     }
/*     */ 
/*  42 */     StringBuilder sb = new StringBuilder();
/*  43 */     for (int i = 0; i < SerialNo.length; i++) {
/*  44 */       String hex = Integer.toHexString(SerialNo[i] & 0xFF);
/*  45 */       if (hex.length() == 1) {
/*  46 */         hex = '0' + hex;
/*     */       }
/*  48 */       sb.append(":" + hex);
/*     */     }
/*  50 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String creatToken() {
/*  54 */     byte[] SerialNo = new byte[2];
/*  55 */     short SerialNo_int = (short)(int)(1.0D + Math.random() * 32767.0D);
/*  56 */     for (int i = 0; i < 2; i++) {
/*  57 */       int offset = (SerialNo.length - 1 - i) * 8;
/*  58 */       SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
/*     */     }
/*     */ 
/*  61 */     StringBuilder sb = new StringBuilder();
/*  62 */     for (int i = 0; i < SerialNo.length; i++) {
/*  63 */       String hex = Integer.toHexString(SerialNo[i] & 0xFF);
/*  64 */       if (hex.length() == 1) {
/*  65 */         hex = '0' + hex;
/*     */       }
/*  67 */       sb.append(hex);
/*     */     }
/*  69 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static String callCmd(String[] cmd)
/*     */   {
/*  74 */     String result = "";
/*  75 */     String line = "";
/*     */     try {
/*  77 */       Process proc = Runtime.getRuntime().exec(cmd);
/*  78 */       InputStreamReader is = new InputStreamReader(proc.getInputStream());
/*  79 */       BufferedReader br = new BufferedReader(is);
/*  80 */       while ((line = br.readLine()) != null)
/*  81 */         result = result + line;
/*     */     }
/*     */     catch (Exception e) {
/*  84 */       System.out.println("这不是Linux系统！！");
/*  85 */       return "";
/*     */     }
/*  87 */     return result;
/*     */   }
/*     */ 
/*     */   private static String callCmd(String[] cmd, String[] another)
/*     */   {
/* 104 */     String result = "";
/* 105 */     String line = "";
/*     */     try {
/* 107 */       Runtime rt = Runtime.getRuntime();
/* 108 */       Process proc = rt.exec(cmd);
/* 109 */       proc.waitFor();
/* 110 */       proc = rt.exec(another);
/* 111 */       InputStreamReader is = new InputStreamReader(proc.getInputStream());
/* 112 */       BufferedReader br = new BufferedReader(is);
/* 113 */       while ((line = br.readLine()) != null)
/* 114 */         result = result + line;
/*     */     }
/*     */     catch (Exception e) {
/* 117 */       System.out.println("这不是windows系统！！");
/* 118 */       return "";
/*     */     }
/* 120 */     return result;
/*     */   }
/*     */ 
/*     */   private static String filterMacAddress(String ip, String sourceString, String macSeparator)
/*     */   {
/* 141 */     String result = "";
/* 142 */     String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
/* 143 */     Pattern pattern = Pattern.compile(regExp);
/* 144 */     Matcher matcher = pattern.matcher(sourceString);
/* 145 */     while (matcher.find()) {
/* 146 */       result = matcher.group(1);
/* 147 */       if (sourceString.lastIndexOf(ip) <= sourceString.indexOf(matcher.group(1))) {
/*     */         break;
/*     */       }
/*     */     }
/* 151 */     return result;
/*     */   }
/*     */ 
/*     */   private static String getMacInWindows(String ip)
/*     */   {
/* 168 */     String result = "";
/* 169 */     String[] cmd = { "cmd", "/c", "ping " + ip };
/* 170 */     String[] another = { "cmd", "/c", "arp -a " + ip };
/* 171 */     String cmdResult = callCmd(cmd, another);
/* 172 */     result = filterMacAddress(ip, cmdResult, "-");
/* 173 */     return result;
/*     */   }
/*     */ 
/*     */   private static String getMacInLinux(String ip)
/*     */   {
/* 183 */     String result = "";
/* 184 */     String[] cmd = { "/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a" };
/* 185 */     String cmdResult = callCmd(cmd);
/* 186 */     result = filterMacAddress(ip, cmdResult, ":");
/* 187 */     return result;
/*     */   }
/*     */ 
/*     */   private static String getMacAddress(String ip)
/*     */   {
/* 196 */     String macAddress = getMacInWindows(ip);
/* 197 */     if ((macAddress != null) && (!"".equals(macAddress))) {
/* 198 */       macAddress = macAddress.trim();
/*     */     }
/* 200 */     if ((macAddress == null) || ("".equals(macAddress))) {
/* 201 */       macAddress = getMacInLinux(ip);
/* 202 */       if ((macAddress != null) && (!"".equals(macAddress))) {
/* 203 */         macAddress = macAddress.trim();
/*     */       }
/*     */     }
/* 206 */     return macAddress;
/*     */   }
/*     */ 
/*     */   public static String getMac(String ip)
/*     */   {
/* 211 */     String mac = getMacAddress(ip);
/* 212 */     if ((mac == null) || (mac.equals(""))) {
/* 213 */       System.out.println("mac 地址获取失败！！！");
/*     */ 
/* 215 */       mac = "aa:aa:aa:aa:aa:aa";
/* 216 */       System.out.println("随机创建mac: " + mac);
/*     */     }
/* 218 */     mac = mac.replace("-", ":");
/* 219 */     System.out.println("ip: " + ip + " Mac: " + mac);
/* 220 */     return mac;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.MyUtils
 * JD-Core Version:    0.6.2
 */