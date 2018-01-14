/*     */ package com.alipay.util.httpClient;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpConnectionManager;
/*     */ import org.apache.commons.httpclient.HttpException;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ import org.apache.commons.httpclient.methods.GetMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.methods.multipart.FilePart;
/*     */ import org.apache.commons.httpclient.methods.multipart.FilePartSource;
/*     */ import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
/*     */ import org.apache.commons.httpclient.methods.multipart.Part;
/*     */ import org.apache.commons.httpclient.methods.multipart.StringPart;
/*     */ import org.apache.commons.httpclient.params.HttpClientParams;
/*     */ import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/*     */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*     */ import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
/*     */ 
/*     */ public class HttpProtocolHandler
/*     */ {
/*  38 */   private static String DEFAULT_CHARSET = "GBK";
/*     */ 
/*  41 */   private int defaultConnectionTimeout = 8000;
/*     */ 
/*  44 */   private int defaultSoTimeout = 30000;
/*     */ 
/*  47 */   private int defaultIdleConnTimeout = 60000;
/*     */ 
/*  49 */   private int defaultMaxConnPerHost = 30;
/*     */ 
/*  51 */   private int defaultMaxTotalConn = 80;
/*     */   private static final long defaultHttpConnectionManagerTimeout = 3000L;
/*     */   private HttpConnectionManager connectionManager;
/*  61 */   private static HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();
/*     */ 
/*     */   public static HttpProtocolHandler getInstance()
/*     */   {
/*  69 */     return httpProtocolHandler;
/*     */   }
/*     */ 
/*     */   private HttpProtocolHandler()
/*     */   {
/*  77 */     this.connectionManager = new MultiThreadedHttpConnectionManager();
/*  78 */     this.connectionManager.getParams().setDefaultMaxConnectionsPerHost(this.defaultMaxConnPerHost);
/*  79 */     this.connectionManager.getParams().setMaxTotalConnections(this.defaultMaxTotalConn);
/*     */ 
/*  81 */     IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
/*  82 */     ict.addConnectionManager(this.connectionManager);
/*  83 */     ict.setConnectionTimeout(this.defaultIdleConnTimeout);
/*     */ 
/*  85 */     ict.start();
/*     */   }
/*     */ 
/*     */   public HttpResponse execute(HttpRequest request, String strParaFileName, String strFilePath)
/*     */     throws HttpException, IOException
/*     */   {
/*  98 */     HttpClient httpclient = new HttpClient(this.connectionManager);
/*     */ 
/* 101 */     int connectionTimeout = this.defaultConnectionTimeout;
/* 102 */     if (request.getConnectionTimeout() > 0) {
/* 103 */       connectionTimeout = request.getConnectionTimeout();
/*     */     }
/* 105 */     httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
/*     */ 
/* 108 */     int soTimeout = this.defaultSoTimeout;
/* 109 */     if (request.getTimeout() > 0) {
/* 110 */       soTimeout = request.getTimeout();
/*     */     }
/* 112 */     httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
/*     */ 
/* 115 */     httpclient.getParams().setConnectionManagerTimeout(3000L);
/*     */ 
/* 117 */     String charset = request.getCharset();
/* 118 */     charset = charset == null ? DEFAULT_CHARSET : charset;
/* 119 */     HttpMethod method = null;
/*     */ 
/* 122 */     if (request.getMethod().equals("GET")) {
/* 123 */       method = new GetMethod(request.getUrl());
/* 124 */       method.getParams().setCredentialCharset(charset);
/*     */ 
/* 127 */       method.setQueryString(request.getQueryString());
/* 128 */     } else if ((strParaFileName.equals("")) && (strFilePath.equals("")))
/*     */     {
/* 130 */       method = new PostMethod(request.getUrl());
/* 131 */       ((PostMethod)method).addParameters(request.getParameters());
/* 132 */       method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
/*     */     }
/*     */     else
/*     */     {
/* 136 */       method = new PostMethod(request.getUrl());
/* 137 */       List parts = new ArrayList();
/* 138 */       for (int i = 0; i < request.getParameters().length; i++) {
/* 139 */         parts.add(new StringPart(request.getParameters()[i].getName(), request.getParameters()[i].getValue(), charset));
/*     */       }
/*     */ 
/* 142 */       parts.add(new FilePart(strParaFileName, new FilePartSource(new File(strFilePath))));
/*     */ 
/* 145 */       ((PostMethod)method).setRequestEntity(new MultipartRequestEntity((Part[])parts.toArray(new Part[0]), new HttpMethodParams()));
/*     */     }
/*     */ 
/* 149 */     method.addRequestHeader("User-Agent", "Mozilla/4.0");
/* 150 */     HttpResponse response = new HttpResponse();
/*     */     try
/*     */     {
/* 153 */       httpclient.executeMethod(method);
/* 154 */       if (request.getResultType().equals(HttpResultType.STRING))
/* 155 */         response.setStringResult(method.getResponseBodyAsString());
/* 156 */       else if (request.getResultType().equals(HttpResultType.BYTES)) {
/* 157 */         response.setByteResult(method.getResponseBody());
/*     */       }
/* 159 */       response.setResponseHeaders(method.getResponseHeaders());
/*     */     }
/*     */     catch (UnknownHostException ex) {
/* 162 */       return null;
/*     */     }
/*     */     catch (IOException ex) {
/* 165 */       return null;
/*     */     }
/*     */     catch (Exception ex) {
/* 168 */       return null;
/*     */     } finally {
/* 170 */       method.releaseConnection(); } method.releaseConnection();
/*     */ 
/* 172 */     return response;
/*     */   }
/*     */ 
/*     */   protected String toString(NameValuePair[] nameValues)
/*     */   {
/* 182 */     if ((nameValues == null) || (nameValues.length == 0)) {
/* 183 */       return "null";
/*     */     }
/*     */ 
/* 186 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/* 188 */     for (int i = 0; i < nameValues.length; i++) {
/* 189 */       NameValuePair nameValue = nameValues[i];
/*     */ 
/* 191 */       if (i == 0)
/* 192 */         buffer.append(nameValue.getName() + "=" + nameValue.getValue());
/*     */       else {
/* 194 */         buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
/*     */       }
/*     */     }
/*     */ 
/* 198 */     return buffer.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.httpClient.HttpProtocolHandler
 * JD-Core Version:    0.6.2
 */