/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sf.json.JSONObject;
/*     */ 
/*     */ public class UpdateOnlineRequest
/*     */ {
/*     */   public static String[] send(String versionNO)
/*     */   {
/*  25 */     String result = "";
/*  26 */     BufferedReader in = null;
/*     */     try {
/*  28 */       String urlNameString = "http://license.openportal.com.cn/get_ajax_version.action?v=" + versionNO;
/*  29 */       URL realUrl = new URL(urlNameString);
/*     */ 
/*  31 */       URLConnection connection = realUrl.openConnection();
/*     */ 
/*  33 */       connection.setRequestProperty("accept", "*/*");
/*  34 */       connection.setRequestProperty("connection", "Keep-Alive");
/*  35 */       connection.setRequestProperty("user-agent", 
/*  36 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/*  38 */       connection.connect();
/*     */ 
/*  40 */       in = new BufferedReader(new InputStreamReader(
/*  41 */         connection.getInputStream()));
/*     */       String line;
/*  43 */       while ((line = in.readLine()) != null)
/*     */       {
/*  44 */         result = result + line;
/*     */       }
/*  46 */       System.out.println("Update_API Result= " + result);
/*  47 */       JSONObject jsonObj = JSONObject.fromObject(result);
/*  48 */       String version = jsonObj.getString("v");
/*  49 */       String versionInfo = jsonObj.getString("info");
/*  50 */       String[] v = new String[2];
/*  51 */       v[0] = version;
/*  52 */       v[1] = versionInfo;
/*  53 */       return v;
/*     */     } catch (Exception e) {
/*  55 */       System.out.println("发送GET请求出现异常！" + e);
/*  56 */       return null;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/*  61 */         if (in != null)
/*  62 */           in.close();
/*     */       }
/*     */       catch (Exception e2) {
/*  65 */         System.out.println("关闭资源出现异常！" + e2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean updateService(String v, String localFile, HttpServletRequest request)
/*     */   {
/*  81 */     String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/update/UpdateOnline";
/*  82 */     String param = "v=" + v + "&path=" + localFile;
/*  83 */     PrintWriter out = null;
/*  84 */     BufferedReader in = null;
/*  85 */     String result = "";
/*  86 */     boolean back = false;
/*     */     try {
/*  88 */       URL realUrl = new URL(url);
/*     */ 
/*  90 */       URLConnection conn = realUrl.openConnection();
/*     */ 
/*  92 */       conn.setRequestProperty("accept", "*/*");
/*  93 */       conn.setRequestProperty("connection", "Keep-Alive");
/*  94 */       conn.setRequestProperty("user-agent", 
/*  95 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/*  97 */       conn.setDoOutput(true);
/*  98 */       conn.setDoInput(true);
/*     */ 
/* 100 */       out = new PrintWriter(conn.getOutputStream());
/*     */ 
/* 102 */       out.print(param);
/*     */ 
/* 104 */       out.flush();
/*     */ 
/* 106 */       in = new BufferedReader(
/* 107 */         new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/* 109 */       while ((line = in.readLine()) != null)
/*     */       {
/* 110 */         result = result + line;
/*     */       }
/* 112 */       if (result.contains("update=ok")) {
/* 113 */         back = true;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       try
/*     */       {
/* 120 */         if (out != null) {
/* 121 */           out.close();
/*     */         }
/* 123 */         if (in != null)
/* 124 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 120 */         if (out != null) {
/* 121 */           out.close();
/*     */         }
/* 123 */         if (in != null)
/* 124 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/*     */       }
/*     */     }
/* 130 */     return back;
/*     */   }
/*     */ 
/*     */   public static boolean updateService(String localFile, HttpServletRequest request)
/*     */   {
/* 144 */     String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/update/Update";
/* 145 */     String param = "path=" + localFile;
/* 146 */     PrintWriter out = null;
/* 147 */     BufferedReader in = null;
/* 148 */     String result = "";
/* 149 */     boolean back = false;
/*     */     try {
/* 151 */       URL realUrl = new URL(url);
/*     */ 
/* 153 */       URLConnection conn = realUrl.openConnection();
/*     */ 
/* 155 */       conn.setRequestProperty("accept", "*/*");
/* 156 */       conn.setRequestProperty("connection", "Keep-Alive");
/* 157 */       conn.setRequestProperty("user-agent", 
/* 158 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/* 160 */       conn.setDoOutput(true);
/* 161 */       conn.setDoInput(true);
/*     */ 
/* 163 */       out = new PrintWriter(conn.getOutputStream());
/*     */ 
/* 165 */       out.print(param);
/*     */ 
/* 167 */       out.flush();
/*     */ 
/* 169 */       in = new BufferedReader(
/* 170 */         new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/* 172 */       while ((line = in.readLine()) != null)
/*     */       {
/* 173 */         result = result + line;
/*     */       }
/* 175 */       if (result.contains("update=ok")) {
/* 176 */         back = true;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       try
/*     */       {
/* 183 */         if (out != null) {
/* 184 */           out.close();
/*     */         }
/* 186 */         if (in != null)
/* 187 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 183 */         if (out != null) {
/* 184 */           out.close();
/*     */         }
/* 186 */         if (in != null)
/* 187 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/*     */       }
/*     */     }
/* 193 */     return back;
/*     */   }
/*     */ 
/*     */   public static boolean restartService(String localFile, HttpServletRequest request)
/*     */   {
/* 207 */     String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/update/Restart";
/* 208 */     String param = "path=" + localFile;
/* 209 */     PrintWriter out = null;
/* 210 */     BufferedReader in = null;
/* 211 */     String result = "";
/* 212 */     boolean back = false;
/*     */     try {
/* 214 */       URL realUrl = new URL(url);
/*     */ 
/* 216 */       URLConnection conn = realUrl.openConnection();
/*     */ 
/* 218 */       conn.setRequestProperty("accept", "*/*");
/* 219 */       conn.setRequestProperty("connection", "Keep-Alive");
/* 220 */       conn.setRequestProperty("user-agent", 
/* 221 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/* 223 */       conn.setDoOutput(true);
/* 224 */       conn.setDoInput(true);
/*     */ 
/* 226 */       out = new PrintWriter(conn.getOutputStream());
/*     */ 
/* 228 */       out.print(param);
/*     */ 
/* 230 */       out.flush();
/*     */ 
/* 232 */       in = new BufferedReader(
/* 233 */         new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/* 235 */       while ((line = in.readLine()) != null)
/*     */       {
/* 236 */         result = result + line;
/*     */       }
/* 238 */       if (result.contains("restart=ok")) {
/* 239 */         back = true;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       try
/*     */       {
/* 246 */         if (out != null) {
/* 247 */           out.close();
/*     */         }
/* 249 */         if (in != null)
/* 250 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 246 */         if (out != null) {
/* 247 */           out.close();
/*     */         }
/* 249 */         if (in != null)
/* 250 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/*     */       }
/*     */     }
/* 256 */     return back;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 260 */     send("0");
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.UpdateOnlineRequest
 * JD-Core Version:    0.6.2
 */