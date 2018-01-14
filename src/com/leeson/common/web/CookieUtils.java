/*     */ package com.leeson.common.web;
/*     */ 
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class CookieUtils
/*     */ {
/*     */   public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
/*     */   public static final int DEFAULT_SIZE = 20;
/*     */   public static final int MAX_SIZE = 200;
/*     */ 
/*     */   public static int getPageSize(HttpServletRequest request)
/*     */   {
/*  38 */     Assert.notNull(request);
/*  39 */     Cookie cookie = getCookie(request, "_cookie_page_size");
/*  40 */     int count = 0;
/*  41 */     if ((cookie != null) && 
/*  42 */       (NumberUtils.isDigits(cookie.getValue()))) {
/*  43 */       count = Integer.parseInt(cookie.getValue());
/*     */     }
/*     */ 
/*  46 */     if (count <= 0)
/*  47 */       count = 20;
/*  48 */     else if (count > 200) {
/*  49 */       count = 200;
/*     */     }
/*  51 */     return count;
/*     */   }
/*     */ 
/*     */   public static Cookie getCookie(HttpServletRequest request, String name)
/*     */   {
/*  64 */     Assert.notNull(request);
/*  65 */     Cookie[] cookies = request.getCookies();
/*  66 */     if ((cookies != null) && (cookies.length > 0)) {
/*  67 */       for (Cookie c : cookies) {
/*  68 */         if (c.getName().equals(name)) {
/*  69 */           return c;
/*     */         }
/*     */       }
/*     */     }
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */   public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain)
/*     */   {
/*  90 */     Cookie cookie = new Cookie(name, value);
/*  91 */     if (expiry != null) {
/*  92 */       cookie.setMaxAge(expiry.intValue());
/*     */     }
/*  94 */     if (StringUtils.isNotBlank(domain)) {
/*  95 */       cookie.setDomain(domain);
/*     */     }
/*  97 */     String ctx = request.getContextPath();
/*  98 */     cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
/*  99 */     response.addCookie(cookie);
/* 100 */     return cookie;
/*     */   }
/*     */ 
/*     */   public static void cancleCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain)
/*     */   {
/* 113 */     Cookie cookie = new Cookie(name, "");
/* 114 */     cookie.setMaxAge(0);
/* 115 */     String ctx = request.getContextPath();
/* 116 */     cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
/* 117 */     if (StringUtils.isNotBlank(domain)) {
/* 118 */       cookie.setDomain(domain);
/*     */     }
/* 120 */     response.addCookie(cookie);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.CookieUtils
 * JD-Core Version:    0.6.2
 */