/*     */ package com.alipay.util;
/*     */ 
/*     */ import com.alipay.config.AlipayConfig;
/*     */ import com.alipay.sign.MD5;
/*     */ import com.leeson.core.bean.Payapi;
/*     */ import com.leeson.core.service.PayapiService;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AlipayNotify
/*     */ {
/*  29 */   private static PayapiService payapiService = (PayapiService)
/*  30 */     SpringContextHelper.getBean("payapiServiceImpl");
/*     */   private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
/*     */ 
/*     */   public static boolean verify(Map<String, String> params)
/*     */   {
/*  46 */     String responseTxt = "false";
/*  47 */     if (params.get("notify_id") != null) {
/*  48 */       String notify_id = (String)params.get("notify_id");
/*  49 */       responseTxt = verifyResponse(notify_id);
/*     */     }
/*  51 */     String sign = "";
/*  52 */     if (params.get("sign") != null) sign = (String)params.get("sign");
/*  53 */     boolean isSign = getSignVeryfy(params, sign);
/*     */ 
/*  59 */     if ((isSign) && (responseTxt.equals("true"))) {
/*  60 */       return true;
/*     */     }
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean getSignVeryfy(Map<String, String> Params, String sign)
/*     */   {
/*  74 */     Map sParaNew = AlipayCore.paraFilter(Params);
/*     */ 
/*  76 */     String preSignStr = AlipayCore.createLinkString(sParaNew);
/*     */ 
/*  78 */     boolean isSign = false;
/*  79 */     if (AlipayConfig.sign_type.equals("MD5")) {
/*  80 */       isSign = MD5.verify(preSignStr, sign, payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayKey(), AlipayConfig.input_charset);
/*     */     }
/*  82 */     return isSign;
/*     */   }
/*     */ 
/*     */   private static String verifyResponse(String notify_id)
/*     */   {
/*  97 */     String partner = payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayPartner();
/*  98 */     String veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;
/*     */ 
/* 100 */     return checkUrl(veryfy_url);
/*     */   }
/*     */ 
/*     */   private static String checkUrl(String urlvalue)
/*     */   {
/* 113 */     String inputLine = "";
/*     */     try
/*     */     {
/* 116 */       URL url = new URL(urlvalue);
/* 117 */       HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
/* 118 */       BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
/* 119 */         .getInputStream()));
/* 120 */       inputLine = in.readLine().toString();
/*     */     } catch (Exception e) {
/* 122 */       e.printStackTrace();
/* 123 */       inputLine = "";
/*     */     }
/*     */ 
/* 126 */     return inputLine;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.AlipayNotify
 * JD-Core Version:    0.6.2
 */