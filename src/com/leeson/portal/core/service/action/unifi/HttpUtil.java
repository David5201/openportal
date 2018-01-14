/*     */ package com.leeson.portal.core.service.action.unifi;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import org.apache.http.Consts;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.CookieStore;
/*     */ import org.apache.http.client.ResponseHandler;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.config.RequestConfig.Builder;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.protocol.HttpClientContext;
/*     */ import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
/*     */ import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.BasicCookieStore;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.impl.client.HttpClients;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.ssl.SSLContextBuilder;
/*     */ import org.apache.http.ssl.SSLContexts;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class HttpUtil
/*     */ {
/*  45 */   private static Logger log = Logger.getLogger(HttpUtil.class);
/*  46 */   private static Config config = Config.getInstance();
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/*  49 */     login("", 1, "default", "192.168.0.1");
/*  50 */     loginOut("", "default", "192.168.0.1");
/*     */   }
/*     */ 
/*     */   public static boolean login(String mac, int minutes, String site, String basip) {
/*  54 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get(basip);
/*  55 */     CloseableHttpClient httpclient = null;
/*     */     try {
/*  57 */       CookieStore cookieStore = new BasicCookieStore();
/*     */ 
/*  59 */       HttpClientContext context = HttpClientContext.create();
/*  60 */       context.setCookieStore(cookieStore);
/*     */ 
/*  62 */       RequestConfig globalConfig = RequestConfig.custom()
/*  63 */         .setCookieSpec("compatibility").build();
/*     */ 
/*  65 */       SSLContext sslcontext = SSLContexts.custom()
/*  66 */         .loadTrustMaterial(null, new TrustSelfSignedStrategy())
/*  67 */         .build();
/*     */ 
/*  69 */       SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
/*  70 */         sslcontext, new String[] { "TLSv1" }, null, 
/*  71 */         SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
/*     */ 
/*  73 */       httpclient = HttpClients.custom()
/*  74 */         .setDefaultRequestConfig(globalConfig)
/*  75 */         .setDefaultCookieStore(cookieStore)
/*  76 */         .setSSLSocketFactory(sslsf).build();
/*     */ 
/*  79 */       HttpHost httpHost = new HttpHost(basConfig.getBasIp(), 8443, "https");
/*  80 */       HttpPost httppost = new HttpPost("/api/login");
/*  81 */       String data = "{'login':'login','username':'" + basConfig.getBasUser() + "','password':'" + basConfig.getBasPwd() + "'}";
/*  82 */       StringEntity stringEntity = new StringEntity(data, "UTF-8");
/*  83 */       stringEntity.setContentEncoding("UTF-8");
/*  84 */       stringEntity.setContentType("application/json");
/*  85 */       httppost.setEntity(stringEntity);
/*     */ 
/*  87 */       ResponseHandler responseHandler = new ResponseHandler()
/*     */       {
/*     */         public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
/*  90 */           int status = response.getStatusLine().getStatusCode();
/*     */ 
/*  92 */           if ((status >= 200) && (status < 303)) {
/*  93 */             HttpEntity entity = response.getEntity();
/*  94 */             return entity != null ? EntityUtils.toString(entity) : 
/*  95 */               null;
/*     */           }
/*  97 */           throw new ClientProtocolException(
/*  98 */             "Unexpected response status: " + status);
/*     */         }
/*     */       };
/* 102 */       System.out.println("===========执行登录=============");
/* 103 */       String response = (String)httpclient.execute(httpHost, httppost, 
/* 104 */         responseHandler, context);
/* 105 */       System.out.println(response);
/* 106 */       httppost.releaseConnection();
/*     */ 
/* 108 */       httppost = new HttpPost("/api/s/" + site + "/cmd/stamgr");
/* 109 */       data = "{'cmd':'authorize-guest','mac':'" + mac + "','minutes':'" + minutes + "'}";
/* 110 */       List formparams = new ArrayList();
/* 111 */       formparams.add(new BasicNameValuePair("json", data));
/* 112 */       UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, 
/* 113 */         Consts.UTF_8);
/* 114 */       httppost.setEntity(entity);
/*     */ 
/* 116 */       responseHandler = new ResponseHandler()
/*     */       {
/*     */         public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
/* 119 */           int status = response.getStatusLine().getStatusCode();
/*     */ 
/* 121 */           if ((status >= 200) && (status < 303)) {
/* 122 */             HttpEntity entity = response.getEntity();
/* 123 */             return entity != null ? EntityUtils.toString(entity) : 
/* 124 */               null;
/*     */           }
/* 126 */           throw new ClientProtocolException(
/* 127 */             "Unexpected response status: " + status);
/*     */         }
/*     */       };
/* 131 */       System.out.println("===========执行授权===========");
/* 132 */       response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
/* 133 */         context);
/* 134 */       System.out.println(response);
/* 135 */       httppost.releaseConnection();
/*     */ 
/* 137 */       httppost = new HttpPost("/api/logout");
/* 138 */       responseHandler = new ResponseHandler()
/*     */       {
/*     */         public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
/* 141 */           int status = response.getStatusLine().getStatusCode();
/*     */ 
/* 143 */           if ((status >= 200) && (status < 303)) {
/* 144 */             HttpEntity entity = response.getEntity();
/* 145 */             return entity != null ? EntityUtils.toString(entity) : 
/* 146 */               null;
/*     */           }
/* 148 */           throw new ClientProtocolException(
/* 149 */             "Unexpected response status: " + status);
/*     */         }
/*     */       };
/* 153 */       System.out.println("===========执行退出=============");
/* 154 */       response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
/* 155 */         context);
/* 156 */       System.out.println(response);
/* 157 */       httppost.releaseConnection();
/*     */     }
/*     */     catch (Exception e) {
/* 160 */       if (basConfig.getIsdebug().equals("1")) {
/* 161 */         log.info("连接UniFi服务器,发生错误:" + e.getMessage());
/*     */       }
/* 163 */       return false;
/*     */     } finally {
/* 165 */       if (httpclient != null) {
/*     */         try {
/* 167 */           httpclient.close();
/*     */         } catch (IOException e) {
/* 169 */           if (basConfig.getIsdebug().equals("1")) {
/* 170 */             log.info("关闭资源,发生错误:" + e.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean loginOut(String mac, String site, String basip) {
/* 179 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get(basip);
/* 180 */     CloseableHttpClient httpclient = null;
/*     */     try {
/* 182 */       CookieStore cookieStore = new BasicCookieStore();
/*     */ 
/* 184 */       HttpClientContext context = HttpClientContext.create();
/* 185 */       context.setCookieStore(cookieStore);
/*     */ 
/* 187 */       RequestConfig globalConfig = RequestConfig.custom()
/* 188 */         .setCookieSpec("compatibility").build();
/*     */ 
/* 190 */       SSLContext sslcontext = SSLContexts.custom()
/* 191 */         .loadTrustMaterial(null, new TrustSelfSignedStrategy())
/* 192 */         .build();
/*     */ 
/* 194 */       SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
/* 195 */         sslcontext, new String[] { "TLSv1" }, null, 
/* 196 */         SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
/*     */ 
/* 198 */       httpclient = HttpClients.custom()
/* 199 */         .setDefaultRequestConfig(globalConfig)
/* 200 */         .setDefaultCookieStore(cookieStore)
/* 201 */         .setSSLSocketFactory(sslsf).build();
/*     */ 
/* 204 */       HttpHost httpHost = new HttpHost(basConfig.getBasIp(), 8443, "https");
/* 205 */       HttpPost httppost = new HttpPost("/api/login");
/* 206 */       String data = "{'login':'login','username':'" + basConfig.getBasUser() + "','password':'" + basConfig.getBasPwd() + "'}";
/* 207 */       StringEntity stringEntity = new StringEntity(data, "UTF-8");
/* 208 */       stringEntity.setContentEncoding("UTF-8");
/* 209 */       stringEntity.setContentType("application/json");
/* 210 */       httppost.setEntity(stringEntity);
/*     */ 
/* 212 */       ResponseHandler responseHandler = new ResponseHandler()
/*     */       {
/*     */         public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
/* 215 */           int status = response.getStatusLine().getStatusCode();
/*     */ 
/* 217 */           if ((status >= 200) && (status < 303)) {
/* 218 */             HttpEntity entity = response.getEntity();
/* 219 */             return entity != null ? EntityUtils.toString(entity) : 
/* 220 */               null;
/*     */           }
/* 222 */           throw new ClientProtocolException(
/* 223 */             "Unexpected response status: " + status);
/*     */         }
/*     */       };
/* 227 */       System.out.println("===========执行登录=============");
/* 228 */       String response = (String)httpclient.execute(httpHost, httppost, 
/* 229 */         responseHandler, context);
/* 230 */       System.out.println(response);
/* 231 */       httppost.releaseConnection();
/*     */ 
/* 233 */       httppost = new HttpPost("/api/s/" + site + "/cmd/stamgr");
/* 234 */       data = "{'cmd':'unauthorize-guest','mac':'" + mac + "'}";
/* 235 */       List formparams = new ArrayList();
/* 236 */       formparams.add(new BasicNameValuePair("json", data));
/* 237 */       UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, 
/* 238 */         Consts.UTF_8);
/* 239 */       httppost.setEntity(entity);
/*     */ 
/* 241 */       responseHandler = new ResponseHandler()
/*     */       {
/*     */         public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
/* 244 */           int status = response.getStatusLine().getStatusCode();
/*     */ 
/* 246 */           if ((status >= 200) && (status < 303)) {
/* 247 */             HttpEntity entity = response.getEntity();
/* 248 */             return entity != null ? EntityUtils.toString(entity) : 
/* 249 */               null;
/*     */           }
/* 251 */           throw new ClientProtocolException(
/* 252 */             "Unexpected response status: " + status);
/*     */         }
/*     */       };
/* 256 */       System.out.println("===========执行下线===========");
/* 257 */       response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
/* 258 */         context);
/* 259 */       System.out.println(response);
/* 260 */       httppost.releaseConnection();
/*     */ 
/* 262 */       httppost = new HttpPost("/api/logout");
/* 263 */       responseHandler = new ResponseHandler()
/*     */       {
/*     */         public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
/* 266 */           int status = response.getStatusLine().getStatusCode();
/*     */ 
/* 268 */           if ((status >= 200) && (status < 303)) {
/* 269 */             HttpEntity entity = response.getEntity();
/* 270 */             return entity != null ? EntityUtils.toString(entity) : 
/* 271 */               null;
/*     */           }
/* 273 */           throw new ClientProtocolException(
/* 274 */             "Unexpected response status: " + status);
/*     */         }
/*     */       };
/* 278 */       System.out.println("===========执行退出=============");
/* 279 */       response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
/* 280 */         context);
/* 281 */       System.out.println(response);
/* 282 */       httppost.releaseConnection();
/*     */     }
/*     */     catch (Exception e) {
/* 285 */       if (basConfig.getIsdebug().equals("1")) {
/* 286 */         log.info("连接UniFi服务器,发生错误:" + e.getMessage());
/*     */       }
/* 288 */       return false;
/*     */     } finally {
/* 290 */       if (httpclient != null) {
/*     */         try {
/* 292 */           httpclient.close();
/*     */         } catch (IOException e) {
/* 294 */           if (basConfig.getIsdebug().equals("1")) {
/* 295 */             log.info("关闭资源,发生错误:" + e.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 300 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.unifi.HttpUtil
 * JD-Core Version:    0.6.2
 */