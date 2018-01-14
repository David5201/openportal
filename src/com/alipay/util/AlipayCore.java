/*     */ package com.alipay.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.commons.httpclient.methods.multipart.FilePartSource;
/*     */ import org.apache.commons.httpclient.methods.multipart.PartSource;
/*     */ 
/*     */ public class AlipayCore
/*     */ {
/*     */   public static Map<String, String> paraFilter(Map<String, String> sArray)
/*     */   {
/*  38 */     Map result = new HashMap();
/*     */ 
/*  40 */     if ((sArray == null) || (sArray.size() <= 0)) {
/*  41 */       return result;
/*     */     }
/*     */ 
/*  44 */     for (String key : sArray.keySet()) {
/*  45 */       String value = (String)sArray.get(key);
/*  46 */       if ((value != null) && (!value.equals("")) && (!key.equalsIgnoreCase("sign")) && 
/*  47 */         (!key.equalsIgnoreCase("sign_type")))
/*     */       {
/*  50 */         result.put(key, value);
/*     */       }
/*     */     }
/*  53 */     return result;
/*     */   }
/*     */ 
/*     */   public static String createLinkString(Map<String, String> params)
/*     */   {
/*  63 */     List keys = new ArrayList(params.keySet());
/*  64 */     Collections.sort(keys);
/*     */ 
/*  66 */     String prestr = "";
/*     */ 
/*  68 */     for (int i = 0; i < keys.size(); i++) {
/*  69 */       String key = (String)keys.get(i);
/*  70 */       String value = (String)params.get(key);
/*     */ 
/*  72 */       if (i == keys.size() - 1)
/*  73 */         prestr = prestr + key + "=" + value;
/*     */       else {
/*  75 */         prestr = prestr + key + "=" + value + "&";
/*     */       }
/*     */     }
/*     */ 
/*  79 */     return prestr;
/*     */   }
/*     */ 
/*     */   public static void logResult(HttpServletRequest request, String sWord)
/*     */   {
/*  87 */     FileWriter writer = null;
/*     */     try {
/*  89 */       writer = new FileWriter(request.getServletContext().getRealPath("/") + "alipay_log_" + System.currentTimeMillis() + ".txt");
/*  90 */       writer.write(sWord);
/*     */     } catch (Exception e) {
/*  92 */       e.printStackTrace();
/*     */ 
/*  94 */       if (writer != null)
/*     */         try {
/*  96 */           writer.close();
/*     */         } catch (IOException e1) {
/*  98 */           e1.printStackTrace();
/*     */         }
/*     */     }
/*     */     finally
/*     */     {
/*  94 */       if (writer != null)
/*     */         try {
/*  96 */           writer.close();
/*     */         } catch (IOException e) {
/*  98 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getAbstract(String strFilePath, String file_digest_type)
/*     */     throws IOException
/*     */   {
/* 111 */     PartSource file = new FilePartSource(new File(strFilePath));
/* 112 */     if (file_digest_type.equals("MD5")) {
/* 113 */       return DigestUtils.md5Hex(file.createInputStream());
/*     */     }
/* 115 */     if (file_digest_type.equals("SHA")) {
/* 116 */       return DigestUtils.sha256Hex(file.createInputStream());
/*     */     }
/*     */ 
/* 119 */     return "";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.AlipayCore
 * JD-Core Version:    0.6.2
 */