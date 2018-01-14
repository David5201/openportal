/*    */ package com.wxpay.utils.http;
/*    */ 
/*    */ import java.security.KeyManagementException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.TrustManager;
/*    */ import org.apache.http.conn.ssl.SSLSocketFactory;
/*    */ 
/*    */ public class MySSLSocketFactory extends SSLSocketFactory
/*    */ {
/* 20 */   private static MySSLSocketFactory mySSLSocketFactory = null;
/*    */ 
/*    */   private static SSLContext createSContext()
/*    */   {
/* 25 */     SSLContext sslcontext = null;
/*    */     try {
/* 27 */       sslcontext = SSLContext.getInstance("SSL");
/*    */     } catch (NoSuchAlgorithmException e) {
/* 29 */       e.printStackTrace();
/*    */     }
/*    */     try {
/* 32 */       sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, null);
/*    */     } catch (KeyManagementException e) {
/* 34 */       e.printStackTrace();
/* 35 */       return null;
/*    */     }
/* 37 */     return sslcontext;
/*    */   }
/*    */ 
/*    */   private MySSLSocketFactory(SSLContext sslContext) {
/* 41 */     super(sslContext);
/* 42 */     setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
/*    */   }
/*    */ 
/*    */   public static MySSLSocketFactory getInstance() {
/* 46 */     if (mySSLSocketFactory != null) {
/* 47 */       return mySSLSocketFactory;
/*    */     }
/* 49 */     return MySSLSocketFactory.mySSLSocketFactory = new MySSLSocketFactory(createSContext());
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.http.MySSLSocketFactory
 * JD-Core Version:    0.6.2
 */