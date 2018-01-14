/*    */ package com.wxpay.utils.http;
/*    */ 
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
/* 43 */     return this.responseHeaders;
/*    */   }
/*    */ 
/*    */   public void setResponseHeaders(Header[] responseHeaders) {
/* 47 */     this.responseHeaders = responseHeaders;
/*    */   }
/*    */ 
/*    */   public byte[] getByteResult() {
/* 51 */     if (this.byteResult != null) {
/* 52 */       return this.byteResult;
/*    */     }
/* 54 */     if (this.stringResult != null) {
/* 55 */       return this.stringResult.getBytes();
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */   public void setByteResult(byte[] byteResult) {
/* 61 */     this.byteResult = byteResult;
/*    */   }
/*    */ 
/*    */   public String getStringResult() throws UnsupportedEncodingException {
/* 65 */     if (this.stringResult != null) {
/* 66 */       return this.stringResult;
/*    */     }
/* 68 */     if (this.byteResult != null) {
/* 69 */       return new String(this.byteResult, "utf-8");
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */   public void setStringResult(String stringResult) {
/* 75 */     this.stringResult = stringResult;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.http.HttpResponse
 * JD-Core Version:    0.6.2
 */