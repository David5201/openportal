/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URL;
/*    */ import java.security.SecureRandom;
/*    */ import javax.net.ssl.HttpsURLConnection;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.SSLSocketFactory;
/*    */ import javax.net.ssl.TrustManager;
/*    */ import weixin.guanjia.core.util.MyX509TrustManager;
/*    */ 
/*    */ public class HttpsUtils
/*    */ {
/*    */   public static String httpsRequest(String requestUrl, String requestMethod, String outputStr)
/*    */   {
/* 19 */     StringBuffer buffer = new StringBuffer();
/*    */     try
/*    */     {
/* 22 */       TrustManager[] tm = { new MyX509TrustManager() };
/* 23 */       SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
/* 24 */       sslContext.init(null, tm, new SecureRandom());
/*    */ 
/* 26 */       SSLSocketFactory ssf = sslContext.getSocketFactory();
/*    */ 
/* 28 */       URL url = new URL(requestUrl);
/* 29 */       HttpsURLConnection httpUrlConn = (HttpsURLConnection)url
/* 30 */         .openConnection();
/* 31 */       httpUrlConn.setSSLSocketFactory(ssf);
/*    */ 
/* 33 */       httpUrlConn.setDoOutput(true);
/* 34 */       httpUrlConn.setDoInput(true);
/* 35 */       httpUrlConn.setUseCaches(false);
/*    */ 
/* 37 */       httpUrlConn.setRequestMethod(requestMethod);
/*    */ 
/* 39 */       if ("GET".equalsIgnoreCase(requestMethod)) {
/* 40 */         httpUrlConn.connect();
/*    */       }
/*    */ 
/* 43 */       if (outputStr != null) {
/* 44 */         OutputStream outputStream = httpUrlConn.getOutputStream();
/*    */ 
/* 46 */         outputStream.write(outputStr.getBytes("UTF-8"));
/* 47 */         outputStream.close();
/*    */       }
/*    */ 
/* 51 */       InputStream inputStream = httpUrlConn.getInputStream();
/* 52 */       InputStreamReader inputStreamReader = new InputStreamReader(
/* 53 */         inputStream, "utf-8");
/* 54 */       BufferedReader bufferedReader = new BufferedReader(
/* 55 */         inputStreamReader);
/*    */ 
/* 57 */       String str = null;
/* 58 */       while ((str = bufferedReader.readLine()) != null) {
/* 59 */         buffer.append(str);
/*    */       }
/* 61 */       bufferedReader.close();
/* 62 */       inputStreamReader.close();
/*    */ 
/* 64 */       inputStream.close();
/* 65 */       inputStream = null;
/* 66 */       httpUrlConn.disconnect();
/*    */     } catch (Exception e) {
/* 68 */       String error = "Error " + e.getMessage();
/* 69 */       System.out.println(error);
/* 70 */       return error;
/*    */     }
/* 72 */     return buffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.HttpsUtils
 * JD-Core Version:    0.6.2
 */