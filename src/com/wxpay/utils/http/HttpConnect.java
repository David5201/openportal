/*    */ package com.wxpay.utils.http;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.httpclient.HttpClient;
/*    */ import org.apache.commons.httpclient.HttpConnectionManager;
/*    */ import org.apache.commons.httpclient.HttpException;
/*    */ import org.apache.commons.httpclient.HttpMethod;
/*    */ import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
/*    */ import org.apache.commons.httpclient.methods.GetMethod;
/*    */ import org.apache.commons.httpclient.params.HttpClientParams;
/*    */ import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/*    */ 
/*    */ public class HttpConnect
/*    */ {
/* 17 */   private static HttpConnect httpConnect = new HttpConnect();
/*    */ 
/* 26 */   MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
/*    */ 
/*    */   public static HttpConnect getInstance()
/*    */   {
/* 24 */     return httpConnect;
/*    */   }
/*    */ 
/*    */   public HttpResponse doGetStr(String url)
/*    */   {
/* 30 */     String CONTENT_CHARSET = "GBK";
/* 31 */     long time1 = System.currentTimeMillis();
/* 32 */     HttpClient client = new HttpClient(this.connectionManager);
/* 33 */     client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
/* 34 */     client.getHttpConnectionManager().getParams().setSoTimeout(55000);
/* 35 */     client.getParams().setParameter("http.protocol.content-charset", CONTENT_CHARSET);
/* 36 */     HttpMethod method = new GetMethod(url);
/* 37 */     HttpResponse response = new HttpResponse();
/*    */     try {
/* 39 */       client.executeMethod(method);
/* 40 */       System.out.println("调接口返回的时间:" + (System.currentTimeMillis() - time1));
/* 41 */       response.setStringResult(method.getResponseBodyAsString());
/*    */     } catch (HttpException e) {
/* 43 */       method.releaseConnection();
/* 44 */       return null;
/*    */     } catch (IOException e) {
/* 46 */       method.releaseConnection();
/* 47 */       return null;
/*    */     } finally {
/* 49 */       method.releaseConnection(); } method.releaseConnection();
/*    */ 
/* 51 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.http.HttpConnect
 * JD-Core Version:    0.6.2
 */