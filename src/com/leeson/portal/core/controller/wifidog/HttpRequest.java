/*     */ package com.leeson.portal.core.controller.wifidog;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ 
/*     */ public class HttpRequest
/*     */ {
/*     */   public static void sendGet(String url, String param)
/*     */   {
/*  23 */     String result = "";
/*  24 */     BufferedReader in = null;
/*     */     try {
/*  26 */       String urlNameString = url + "?" + param;
/*  27 */       URL realUrl = new URL(urlNameString);
/*     */ 
/*  29 */       URLConnection connection = realUrl.openConnection();
/*     */ 
/*  31 */       connection.setRequestProperty("accept", "*/*");
/*  32 */       connection.setRequestProperty("connection", "Keep-Alive");
/*  33 */       connection.setRequestProperty("user-agent", 
/*  34 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/*  36 */       connection.connect();
/*     */ 
/*  44 */       in = new BufferedReader(new InputStreamReader(
/*  45 */         connection.getInputStream()));
/*     */       String line;
/*  47 */       while ((line = in.readLine()) != null)
/*     */       {
/*  48 */         result = result + line;
/*     */       }
/*  50 */       System.out.println(result);
/*     */     } catch (Exception e) {
/*  52 */       System.out.println("发送GET请求出现异常！" + e);
/*     */       try
/*     */       {
/*  58 */         if (in != null)
/*  59 */           in.close();
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  58 */         if (in != null)
/*  59 */           in.close();
/*     */       }
/*     */       catch (Exception localException2)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String sendPost(String url, String param)
/*     */   {
/*  77 */     PrintWriter out = null;
/*  78 */     BufferedReader in = null;
/*  79 */     String result = "";
/*     */     try {
/*  81 */       URL realUrl = new URL(url);
/*     */ 
/*  83 */       URLConnection conn = realUrl.openConnection();
/*     */ 
/*  85 */       conn.setRequestProperty("accept", "*/*");
/*  86 */       conn.setRequestProperty("connection", "Keep-Alive");
/*  87 */       conn.setRequestProperty("user-agent", 
/*  88 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/*  90 */       conn.setDoOutput(true);
/*  91 */       conn.setDoInput(true);
/*     */ 
/*  93 */       out = new PrintWriter(conn.getOutputStream());
/*     */ 
/*  95 */       out.print(param);
/*     */ 
/*  97 */       out.flush();
/*     */ 
/*  99 */       in = new BufferedReader(
/* 100 */         new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/* 102 */       while ((line = in.readLine()) != null)
/*     */       {
/* 103 */         result = result + line;
/*     */       }
/* 105 */       System.out.println(result);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       try
/*     */       {
/* 113 */         if (out != null) {
/* 114 */           out.close();
/*     */         }
/* 116 */         if (in != null)
/* 117 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 113 */         if (out != null) {
/* 114 */           out.close();
/*     */         }
/* 116 */         if (in != null) {
/* 117 */           in.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/*     */       }
/*     */     }
/* 124 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.HttpRequest
 * JD-Core Version:    0.6.2
 */