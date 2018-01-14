/*     */ package com.alipay.util.httpClient;
/*     */ 
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ 
/*     */ public class HttpRequest
/*     */ {
/*     */   public static final String METHOD_GET = "GET";
/*     */   public static final String METHOD_POST = "POST";
/*  27 */   private String url = null;
/*     */ 
/*  32 */   private String method = "POST";
/*     */ 
/*  34 */   private int timeout = 0;
/*     */ 
/*  36 */   private int connectionTimeout = 0;
/*     */ 
/*  41 */   private NameValuePair[] parameters = null;
/*     */ 
/*  46 */   private String queryString = null;
/*     */ 
/*  51 */   private String charset = "GBK";
/*     */   private String clientIp;
/*  61 */   private HttpResultType resultType = HttpResultType.BYTES;
/*     */ 
/*     */   public HttpRequest(HttpResultType resultType)
/*     */   {
/*  65 */     this.resultType = resultType;
/*     */   }
/*     */ 
/*     */   public String getClientIp()
/*     */   {
/*  72 */     return this.clientIp;
/*     */   }
/*     */ 
/*     */   public void setClientIp(String clientIp)
/*     */   {
/*  79 */     this.clientIp = clientIp;
/*     */   }
/*     */ 
/*     */   public NameValuePair[] getParameters() {
/*  83 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   public void setParameters(NameValuePair[] parameters) {
/*  87 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */   public String getQueryString() {
/*  91 */     return this.queryString;
/*     */   }
/*     */ 
/*     */   public void setQueryString(String queryString) {
/*  95 */     this.queryString = queryString;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  99 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/* 103 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getMethod() {
/* 107 */     return this.method;
/*     */   }
/*     */ 
/*     */   public void setMethod(String method) {
/* 111 */     this.method = method;
/*     */   }
/*     */ 
/*     */   public int getConnectionTimeout() {
/* 115 */     return this.connectionTimeout;
/*     */   }
/*     */ 
/*     */   public void setConnectionTimeout(int connectionTimeout) {
/* 119 */     this.connectionTimeout = connectionTimeout;
/*     */   }
/*     */ 
/*     */   public int getTimeout() {
/* 123 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTimeout(int timeout) {
/* 127 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public String getCharset()
/*     */   {
/* 134 */     return this.charset;
/*     */   }
/*     */ 
/*     */   public void setCharset(String charset)
/*     */   {
/* 141 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */   public HttpResultType getResultType() {
/* 145 */     return this.resultType;
/*     */   }
/*     */ 
/*     */   public void setResultType(HttpResultType resultType) {
/* 149 */     this.resultType = resultType;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.httpClient.HttpRequest
 * JD-Core Version:    0.6.2
 */