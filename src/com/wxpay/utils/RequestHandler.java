/*     */ package com.wxpay.utils;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Iterator;
import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class RequestHandler
/*     */ {
/*     */   private String tokenUrl;
/*     */   private String gateUrl;
/*     */   private String notifyUrl;
/*     */   private String appid;
/*     */   private String appkey;
/*     */   private String partnerkey;
/*     */   private String appsecret;
/*     */   private String key;
/*     */   private SortedMap parameters;
/*     */   private String Token;
/*     */   private String charset;
/*     */   private String debugInfo;
/*     */   private String last_errcode;
/*     */   private HttpServletRequest request;
/*     */   private HttpServletResponse response;
/*     */ 
/*     */   public RequestHandler(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  69 */     this.last_errcode = "0";
/*  70 */     this.request = request;
/*  71 */     this.response = response;
/*     */ 
/*  73 */     this.charset = "UTF-8";
/*  74 */     this.parameters = new TreeMap();
/*     */ 
/*  76 */     this.notifyUrl = "https://gw.tenpay.com/gateway/simpleverifynotifyid.xml";
/*     */   }
/*     */ 
/*     */   public void init(String app_id, String app_secret, String partner_key)
/*     */   {
/*  84 */     this.last_errcode = "0";
/*  85 */     this.Token = "token_";
/*  86 */     this.debugInfo = "";
/*  87 */     this.appid = app_id;
/*  88 */     this.partnerkey = partner_key;
/*  89 */     this.appsecret = app_secret;
/*  90 */     this.key = partner_key;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getLasterrCode()
/*     */   {
/* 100 */     return this.last_errcode;
/*     */   }
/*     */ 
/*     */   public String getGateUrl()
/*     */   {
/* 107 */     return this.gateUrl;
/*     */   }
/*     */ 
/*     */   public String getParameter(String parameter)
/*     */   {
/* 118 */     String s = (String)this.parameters.get(parameter);
/* 119 */     return s == null ? "" : s;
/*     */   }
/*     */ 
/*     */   public void setKey(String key)
/*     */   {
/* 126 */     this.partnerkey = key;
/*     */   }
/*     */ 
/*     */   public void setAppKey(String key) {
/* 130 */     this.appkey = key;
/*     */   }
/*     */ 
/*     */   public String UrlEncode(String src) throws UnsupportedEncodingException
/*     */   {
/* 135 */     return URLEncoder.encode(src, this.charset).replace("+", "%20");
/*     */   }
/*     */ 
/*     */   public String genPackage(SortedMap<String, String> packageParams)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 141 */     String sign = createSign(packageParams);
/*     */ 
/* 143 */     StringBuffer sb = new StringBuffer();
/* 144 */     Set es = packageParams.entrySet();
/* 145 */     Iterator it = es.iterator();
/* 146 */     while (it.hasNext()) {
/* 147 */       Map.Entry entry = (Map.Entry)it.next();
/* 148 */       String k = (String)entry.getKey();
/* 149 */       String v = (String)entry.getValue();
/* 150 */       sb.append(k + "=" + UrlEncode(v) + "&");
/*     */     }
/*     */ 
/* 154 */     String packageValue = new StringBuilder("sign=").append(sign).toString();
/*     */ 
/* 156 */     return packageValue;
/*     */   }
/*     */ 
/*     */   public String createSign(SortedMap<String, String> packageParams)
/*     */   {
/* 163 */     StringBuffer sb = new StringBuffer();
/* 164 */     Set es = packageParams.entrySet();
/* 165 */     Iterator it = es.iterator();
/* 166 */     while (it.hasNext()) {
/* 167 */       Map.Entry entry = (Map.Entry)it.next();
/* 168 */       String k = (String)entry.getKey();
/* 169 */       String v = (String)entry.getValue();
/* 170 */       if ((v != null) && (!"".equals(v)) && (!"sign".equals(k)) && 
/* 171 */         (!"key".equals(k))) {
/* 172 */         sb.append(k + "=" + v + "&");
/*     */       }
/*     */     }
/* 175 */     sb.append("key=" + getKey());
/* 176 */     String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
/* 177 */       .toUpperCase();
/* 178 */     return sign;
/*     */   }
/*     */ 
/*     */   public boolean createMd5Sign(String signParams)
/*     */   {
/* 185 */     StringBuffer sb = new StringBuffer();
/* 186 */     Set es = this.parameters.entrySet();
/* 187 */     Iterator it = es.iterator();
/* 188 */     while (it.hasNext()) {
/* 189 */       Map.Entry entry = (Map.Entry)it.next();
/* 190 */       String k = (String)entry.getKey();
/* 191 */       String v = (String)entry.getValue();
/* 192 */       if ((!"sign".equals(k)) && (v != null) && (!"".equals(v))) {
/* 193 */         sb.append(k + "=" + v + "&");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 198 */     String enc = TenpayUtil.getCharacterEncoding(this.request, 
/* 199 */       this.response);
/* 200 */     String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
/*     */ 
/* 202 */     String tenpaySign = getParameter("sign").toLowerCase();
/*     */ 
/* 205 */     setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:" + 
/* 206 */       tenpaySign);
/*     */ 
/* 208 */     return tenpaySign.equals(sign);
/*     */   }
/*     */ 
/*     */   public String parseXML()
/*     */   {
/* 215 */     StringBuffer sb = new StringBuffer();
/* 216 */     sb.append("<xml>");
/* 217 */     Set es = this.parameters.entrySet();
/* 218 */     Iterator it = es.iterator();
/* 219 */     while (it.hasNext()) {
/* 220 */       Map.Entry entry = (Map.Entry)it.next();
/* 221 */       String k = (String)entry.getKey();
/* 222 */       String v = (String)entry.getValue();
/* 223 */       if ((v != null) && (!"".equals(v)) && (!"appkey".equals(k)))
/*     */       {
/* 225 */         sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">\n");
/*     */       }
/*     */     }
/* 228 */     sb.append("</xml>");
/* 229 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected void setDebugInfo(String debugInfo)
/*     */   {
/* 236 */     this.debugInfo = debugInfo;
/*     */   }
/*     */   public void setPartnerkey(String partnerkey) {
/* 239 */     this.partnerkey = partnerkey;
/*     */   }
/*     */   public String getDebugInfo() {
/* 242 */     return this.debugInfo;
/*     */   }
/*     */   public String getKey() {
/* 245 */     return this.key;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.RequestHandler
 * JD-Core Version:    0.6.2
 */