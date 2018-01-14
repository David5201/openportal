/*     */ package com.wxpay.utils.http;
/*     */ 
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ 
/*     */ public class HttpRequest
/*     */ {
/*     */   public static final String METHOD_GET = "GET";
/*     */   public static final String METHOD_POST = "POST";
/*  29 */   private String url = null;
/*     */ 
/*  34 */   private String method = "POST";
/*     */ 
/*  36 */   private int timeout = 0;
/*     */ 
/*  38 */   private int connectionTimeout = 0;
/*     */ 
/*  43 */   private NameValuePair[] parameters = null;
/*     */ 
/*  48 */   private String queryString = null;
/*     */ 
/*  53 */   private String charset = "GBK";
/*     */   private String clientIp;
/*  63 */   private HttpResultType resultType = HttpResultType.BYTES;
/*     */ 
/*     */   public HttpRequest(HttpResultType resultType)
/*     */   {
/*  67 */     this.resultType = resultType;
/*     */   }
/*     */ 
/*     */   public String getClientIp()
/*     */   {
/*  74 */     return this.clientIp;
/*     */   }
/*     */ 
/*     */   public void setClientIp(String clientIp)
/*     */   {
/*  81 */     this.clientIp = clientIp;
/*     */   }
/*     */ 
/*     */   public NameValuePair[] getParameters() {
/*  85 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   public void setParameters(NameValuePair[] parameters) {
/*  89 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */   public String getQueryString() {
/*  93 */     return this.queryString;
/*     */   }
/*     */ 
/*     */   public void setQueryString(String queryString) {
/*  97 */     this.queryString = queryString;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/* 101 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/* 105 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getMethod() {
/* 109 */     return this.method;
/*     */   }
/*     */ 
/*     */   public void setMethod(String method) {
/* 113 */     this.method = method;
/*     */   }
/*     */ 
/*     */   public int getConnectionTimeout() {
/* 117 */     return this.connectionTimeout;
/*     */   }
/*     */ 
/*     */   public void setConnectionTimeout(int connectionTimeout) {
/* 121 */     this.connectionTimeout = connectionTimeout;
/*     */   }
/*     */ 
/*     */   public int getTimeout() {
/* 125 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTimeout(int timeout) {
/* 129 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public String getCharset()
/*     */   {
/* 136 */     return this.charset;
/*     */   }
/*     */ 
/*     */   public void setCharset(String charset)
/*     */   {
/* 143 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */   public HttpResultType getResultType() {
/* 147 */     return this.resultType;
/*     */   }
/*     */ 
/*     */   public void setResultType(HttpResultType resultType) {
/* 151 */     this.resultType = resultType;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.http.HttpRequest
 * JD-Core Version:    0.6.2
 */