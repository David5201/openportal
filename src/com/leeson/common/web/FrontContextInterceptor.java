/*     */ package com.leeson.common.web;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ public class FrontContextInterceptor extends HandlerInterceptorAdapter
/*     */ {
/*     */   public static final String RETURN_URL = "returnUrl";
/*     */   private Integer adminId;
/*     */   private String[] excludeUrls;
/*     */   private String loginUrl;
/*     */   private String processUrl;
/*     */   private String returnUrl;
/*     */ 
/*     */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*     */     throws Exception
/*     */   {
/*  29 */     return true;
/*     */   }
/*     */ 
/*     */   private String getLoginUrl(HttpServletRequest request)
/*     */   {
/*  41 */     StringBuilder buff = new StringBuilder();
/*  42 */     if (this.loginUrl.startsWith("/")) {
/*  43 */       String ctx = request.getContextPath();
/*  44 */       if (!StringUtils.isBlank(ctx)) {
/*  45 */         buff.append(ctx);
/*     */       }
/*     */     }
/*  48 */     buff.append(this.loginUrl);
/*     */ 
/*  55 */     System.out.println(buff.toString());
/*  56 */     return buff.toString();
/*     */   }
/*     */ 
/*     */   private String getProcessUrl(HttpServletRequest request) {
/*  60 */     StringBuilder buff = new StringBuilder();
/*  61 */     if (this.loginUrl.startsWith("/")) {
/*  62 */       String ctx = request.getContextPath();
/*  63 */       if (!StringUtils.isBlank(ctx)) {
/*  64 */         buff.append(ctx);
/*     */       }
/*     */     }
/*  67 */     buff.append(this.processUrl);
/*  68 */     return buff.toString();
/*     */   }
/*     */ 
/*     */   private boolean exclude(String uri) {
/*  72 */     if (this.excludeUrls != null) {
/*  73 */       for (String exc : this.excludeUrls) {
/*  74 */         if (exc.equals(uri)) {
/*  75 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean permistionPass(String uri, Set<String> perms, boolean viewOnly)
/*     */   {
/*  84 */     String u = null;
/*     */ 
/*  86 */     for (String perm : perms) {
/*  87 */       if (uri.startsWith(perm))
/*     */       {
/*  89 */         if (viewOnly)
/*     */         {
/*  91 */           int i = uri.lastIndexOf("/");
/*  92 */           if (i == -1) {
/*  93 */             throw new RuntimeException("uri must start width '/':" + 
/*  94 */               uri);
/*     */           }
/*  96 */           u = uri.substring(i + 1);
/*     */ 
/*  98 */           if (u.startsWith("o_")) {
/*  99 */             return false;
/*     */           }
/*     */         }
/* 102 */         return true;
/*     */       }
/*     */     }
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   private static String getURI(HttpServletRequest request)
/*     */     throws IllegalStateException
/*     */   {
/* 117 */     UrlPathHelper helper = new UrlPathHelper();
/* 118 */     String uri = helper.getOriginatingRequestUri(request);
/* 119 */     String ctxPath = helper.getOriginatingContextPath(request);
/* 120 */     if (ctxPath != null) {
/* 121 */       uri = uri.substring(ctxPath.length());
/*     */     }
/* 123 */     return uri;
/*     */   }
/*     */ 
/*     */   public void setExcludeUrls(String[] excludeUrls)
/*     */   {
/* 135 */     this.excludeUrls = excludeUrls;
/*     */   }
/*     */ 
/*     */   public void setAdminId(Integer adminId) {
/* 139 */     this.adminId = adminId;
/*     */   }
/*     */ 
/*     */   public void setLoginUrl(String loginUrl) {
/* 143 */     this.loginUrl = loginUrl;
/*     */   }
/*     */ 
/*     */   public void setProcessUrl(String processUrl) {
/* 147 */     this.processUrl = processUrl;
/*     */   }
/*     */ 
/*     */   public void setReturnUrl(String returnUrl) {
/* 151 */     this.returnUrl = returnUrl;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.FrontContextInterceptor
 * JD-Core Version:    0.6.2
 */