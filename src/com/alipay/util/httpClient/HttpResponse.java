/*    */ package com.alipay.util.httpClient;
/*    */ 
/*    */ import com.alipay.config.AlipayConfig;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.apache.commons.httpclient.Header;
/*    */ 
/*    */ public class HttpResponse
/*    */ {
/*    */   private Header[] responseHeaders;
/*    */   private String stringResult;
/*    */   private byte[] byteResult;
/*    */ 
/*    */   public Header[] getResponseHeaders()
/*    */   {
/* 37 */     return this.responseHeaders;
/*    */   }
/*    */ 
/*    */   public void setResponseHeaders(Header[] responseHeaders) {
/* 41 */     this.responseHeaders = responseHeaders;
/*    */   }
/*    */ 
/*    */   public byte[] getByteResult() {
/* 45 */     if (this.byteResult != null) {
/* 46 */       return this.byteResult;
/*    */     }
/* 48 */     if (this.stringResult != null) {
/* 49 */       return this.stringResult.getBytes();
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public void setByteResult(byte[] byteResult) {
/* 55 */     this.byteResult = byteResult;
/*    */   }
/*    */ 
/*    */   public String getStringResult() throws UnsupportedEncodingException {
/* 59 */     if (this.stringResult != null) {
/* 60 */       return this.stringResult;
/*    */     }
/* 62 */     if (this.byteResult != null) {
/* 63 */       return new String(this.byteResult, AlipayConfig.input_charset);
/*    */     }
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */   public void setStringResult(String stringResult) {
/* 69 */     this.stringResult = stringResult;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.httpClient.HttpResponse
 * JD-Core Version:    0.6.2
 */