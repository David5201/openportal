/*    */ package com.wxpay.utils.http;
/*    */ 
/*    */ import org.apache.http.client.HttpClient;
/*    */ import org.apache.http.client.methods.HttpGet;
/*    */ import org.apache.http.client.methods.HttpPost;
/*    */ import org.apache.http.conn.ClientConnectionManager;
/*    */ import org.apache.http.conn.scheme.Scheme;
/*    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*    */ import org.apache.http.impl.client.DefaultHttpClient;
/*    */ 
/*    */ public class HttpClientConnectionManager
/*    */ {
/*    */   public static HttpClient getSSLInstance(HttpClient httpClient)
/*    */   {
/* 25 */     ClientConnectionManager ccm = httpClient.getConnectionManager();
/* 26 */     SchemeRegistry sr = ccm.getSchemeRegistry();
/* 27 */     sr.register(new Scheme("https", MySSLSocketFactory.getInstance(), 443));
/* 28 */     httpClient = new DefaultHttpClient(ccm, httpClient.getParams());
/* 29 */     return httpClient;
/*    */   }
/*    */ 
/*    */   public static HttpPost getPostMethod(String url)
/*    */   {
/* 39 */     HttpPost pmethod = new HttpPost(url);
/* 40 */     pmethod.addHeader("Connection", "keep-alive");
/* 41 */     pmethod.addHeader("Accept", "*/*");
/* 42 */     pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
/* 43 */     pmethod.addHeader("Host", "api.mch.weixin.qq.com");
/* 44 */     pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
/* 45 */     pmethod.addHeader("Cache-Control", "max-age=0");
/* 46 */     pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
/* 47 */     return pmethod;
/*    */   }
/*    */ 
/*    */   public static HttpGet getGetMethod(String url)
/*    */   {
/* 56 */     HttpGet pmethod = new HttpGet(url);
/*    */ 
/* 58 */     pmethod.addHeader("Connection", "keep-alive");
/* 59 */     pmethod.addHeader("Cache-Control", "max-age=0");
/* 60 */     pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
/* 61 */     pmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
/* 62 */     return pmethod;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.http.HttpClientConnectionManager
 * JD-Core Version:    0.6.2
 */