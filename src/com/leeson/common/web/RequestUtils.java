/*     */ package com.leeson.common.web;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ public class RequestUtils
/*     */ {
/*  26 */   private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);
/*     */ 
/*     */   public static String getQueryParam(HttpServletRequest request, String name)
/*     */   {
/*  39 */     if (StringUtils.isBlank(name)) {
/*  40 */       return null;
/*     */     }
/*  42 */     if (request.getMethod().equalsIgnoreCase("POST")) {
/*  43 */       return request.getParameter(name);
/*     */     }
/*  45 */     String s = request.getQueryString();
/*  46 */     if (StringUtils.isBlank(s))
/*  47 */       return null;
/*     */     try
/*     */     {
/*  50 */       s = URLDecoder.decode(s, "UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/*  52 */       log.error("encoding UTF-8 not support?", e);
/*     */     }
/*  54 */     String[] values = (String[])parseQueryString(s).get(name);
/*  55 */     if ((values != null) && (values.length > 0)) {
/*  56 */       return values[(values.length - 1)];
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   public static Map<String, Object> getQueryParams(HttpServletRequest request)
/*     */   {
/*     */     Map<String, String[]> map = null;
/*  64 */     if (request.getMethod().equalsIgnoreCase("POST")) {
/*  65 */       map = request.getParameterMap();
/*     */     } else {
/*  67 */       String s = request.getQueryString();
/*  68 */       if (StringUtils.isBlank(s))
/*  69 */         return new HashMap();
/*     */       try
/*     */       {
/*  72 */         s = URLDecoder.decode(s, "UTF-8");
/*     */       } catch (UnsupportedEncodingException e) {
/*  74 */         log.error("encoding UTF-8 not support?", e);
/*     */       }
/*  76 */       map = parseQueryString(s);
/*     */     }
/*     */ 
/*  79 */     Map params = new HashMap(map.size());
/*     */ 
/*  81 */     for (Map.Entry entry : map.entrySet()) {
/*  82 */       int len = ((String[])entry.getValue()).length;
/*  83 */       if (len == 1)
/*  84 */         params.put((String)entry.getKey(), ((String[])entry.getValue())[0]);
/*  85 */       else if (len > 1) {
/*  86 */         params.put((String)entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/*  89 */     return params;
/*     */   }
/*     */ 
/*     */   public static Map<String, String[]> parseQueryString(String s)
/*     */   {
/* 122 */     String[] valArray = null;
/* 123 */     if (s == null) {
/* 124 */       throw new IllegalArgumentException();
/*     */     }
/* 126 */     Map ht = new HashMap();
/* 127 */     StringTokenizer st = new StringTokenizer(s, "&");
/* 128 */     while (st.hasMoreTokens()) {
/* 129 */       String pair = st.nextToken();
/* 130 */       int pos = pair.indexOf('=');
/* 131 */       if (pos != -1)
/*     */       {
/* 134 */         String key = pair.substring(0, pos);
/* 135 */         String val = pair.substring(pos + 1, pair.length());
/* 136 */         if (ht.containsKey(key)) {
/* 137 */           String[] oldVals = (String[])ht.get(key);
/* 138 */           valArray = new String[oldVals.length + 1];
/* 139 */           for (int i = 0; i < oldVals.length; i++) {
/* 140 */             valArray[i] = oldVals[i];
/*     */           }
/* 142 */           valArray[oldVals.length] = val;
/*     */         } else {
/* 144 */           valArray = new String[1];
/* 145 */           valArray[0] = val;
/*     */         }
/* 147 */         ht.put(key, valArray);
/*     */       }
/*     */     }
/* 149 */     return ht;
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix)
/*     */   {
/* 154 */     return getRequestMap(request, prefix, false);
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix)
/*     */   {
/* 159 */     return getRequestMap(request, prefix, true);
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
/*     */   {
/* 164 */     Map map = new HashMap();
/* 165 */     Enumeration names = request.getParameterNames();
/*     */ 
/* 167 */     while (names.hasMoreElements()) {
/* 168 */       String name = (String)names.nextElement();
/* 169 */       if (name.startsWith(prefix)) {
/* 170 */         String key = nameWithPrefix ? name : name.substring(prefix.length());
/* 171 */         String value = StringUtils.join(request.getParameterValues(name), ',');
/* 172 */         map.put(key, value);
/*     */       }
/*     */     }
/* 175 */     return map;
/*     */   }
/*     */ 
/*     */   public static String getIpAddr(HttpServletRequest request)
/*     */   {
/* 190 */     String ip = request.getHeader("X-Real-IP");
/* 191 */     if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
/* 192 */       return ip;
/*     */     }
/* 194 */     ip = request.getHeader("X-Forwarded-For");
/* 195 */     if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip)))
/*     */     {
/* 197 */       int index = ip.indexOf(',');
/* 198 */       if (index != -1) {
/* 199 */         return ip.substring(0, index);
/*     */       }
/* 201 */       return ip;
/*     */     }
/*     */ 
/* 204 */     return request.getRemoteAddr();
/*     */   }
/*     */ 
/*     */   public static String getLocation(HttpServletRequest request)
/*     */   {
/* 217 */     UrlPathHelper helper = new UrlPathHelper();
/* 218 */     StringBuffer buff = request.getRequestURL();
/* 219 */     String uri = request.getRequestURI();
/* 220 */     String origUri = helper.getOriginatingRequestUri(request);
/* 221 */     buff.replace(buff.length() - uri.length(), buff.length(), origUri);
/* 222 */     String queryString = helper.getOriginatingQueryString(request);
/* 223 */     if (queryString != null) {
/* 224 */       buff.append("?").append(queryString);
/*     */     }
/* 226 */     return buff.toString();
/*     */   }
/*     */ 
/*     */   public static String getRequestedSessionId(HttpServletRequest request)
/*     */   {
/* 239 */     String sid = request.getRequestedSessionId();
/* 240 */     String ctx = request.getContextPath();
/*     */ 
/* 242 */     if ((request.isRequestedSessionIdFromURL()) || (StringUtils.isBlank(ctx))) {
/* 243 */       return sid;
/*     */     }
/*     */ 
/* 246 */     Cookie cookie = CookieUtils.getCookie(request, 
/* 247 */       "JSESSIONID");
/* 248 */     if (cookie != null) {
/* 249 */       return cookie.getValue();
/*     */     }
/* 251 */     return request.getSession().getId();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.RequestUtils
 * JD-Core Version:    0.6.2
 */