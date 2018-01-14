/*     */ package com.leeson.common.web.session;
/*     */ 
/*     */ import com.leeson.common.web.RequestUtils;
/*     */ import com.leeson.common.web.session.cache.SessionCache;
/*     */ import com.leeson.common.web.session.id.SessionIdGenerator;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class CacheSessionProvider
/*     */   implements SessionProvider, InitializingBean
/*     */ {
/*     */   private SessionCache sessionCache;
/*     */   private SessionIdGenerator sessionIdGenerator;
/*  91 */   private int sessionTimeout = 30;
/*     */ 
/*     */   public Serializable getAttribute(HttpServletRequest request, String name)
/*     */   {
/*  27 */     Map session = null;
/*     */ 
/*  29 */     String root = RequestUtils.getRequestedSessionId(request);
/*  30 */     if (StringUtils.isBlank(root)) {
/*  31 */       return null;
/*     */     }
/*  33 */     session = this.sessionCache.getSession(root);
/*  34 */     if (session != null) {
/*  35 */       return (Serializable)session.get(name);
/*     */     }
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value)
/*     */   {
/*  43 */     Map session = new HashMap();
/*  44 */     String root = RequestUtils.getRequestedSessionId(request);
/*     */ 
/*  49 */     session.put(name, value);
/*  50 */     this.sessionCache.setSession(root, session, this.sessionTimeout);
/*     */   }
/*     */ 
/*     */   public String getSessionId(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  55 */     String root = RequestUtils.getRequestedSessionId(request);
/*  56 */     if ((root == null) || (root.length() != 32) || (!this.sessionCache.exist(root))) {
/*     */       do
/*  58 */         root = this.sessionIdGenerator.get();
/*  59 */       while (this.sessionCache.exist(root));
/*  60 */       this.sessionCache.setSession(root, new HashMap(), 
/*  61 */         this.sessionTimeout);
/*  62 */       response.addCookie(createCookie(request, root));
/*     */     }
/*  64 */     return root;
/*     */   }
/*     */ 
/*     */   public void logout(HttpServletRequest request, HttpServletResponse response) {
/*  68 */     String root = RequestUtils.getRequestedSessionId(request);
/*  69 */     if (!StringUtils.isBlank(root)) {
/*  70 */       this.sessionCache.clear(root);
/*  71 */       Cookie cookie = createCookie(request, null);
/*  72 */       cookie.setMaxAge(0);
/*  73 */       response.addCookie(cookie);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Cookie createCookie(HttpServletRequest request, String value) {
/*  78 */     Cookie cookie = new Cookie("JSESSIONID", value);
/*  79 */     String ctx = request.getContextPath();
/*  80 */     cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
/*  81 */     return cookie;
/*     */   }
/*     */ 
/*     */   public void afterPropertiesSet() throws Exception {
/*  85 */     Assert.notNull(this.sessionCache);
/*  86 */     Assert.notNull(this.sessionIdGenerator);
/*     */   }
/*     */ 
/*     */   public void setSessionCache(SessionCache sessionCache)
/*     */   {
/*  94 */     this.sessionCache = sessionCache;
/*     */   }
/*     */ 
/*     */   public void setSessionTimeout(int sessionTimeout)
/*     */   {
/* 104 */     Assert.isTrue(sessionTimeout > 0);
/* 105 */     this.sessionTimeout = sessionTimeout;
/*     */   }
/*     */ 
/*     */   public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
/* 109 */     this.sessionIdGenerator = sessionIdGenerator;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.CacheSessionProvider
 * JD-Core Version:    0.6.2
 */