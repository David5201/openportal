/*    */ package com.leeson.common.web.springmvc;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.web.context.WebApplicationContext;
/*    */ import org.springframework.web.servlet.LocaleResolver;
/*    */ import org.springframework.web.servlet.support.RequestContextUtils;
/*    */ 
/*    */ public final class MessageResolver
/*    */ {
/*    */   public static String getMessage(HttpServletRequest request, String code, Object[] args)
/*    */   {
/* 30 */     WebApplicationContext messageSource = 
/* 31 */       RequestContextUtils.getWebApplicationContext(request);
/* 32 */     if (messageSource == null) {
/* 33 */       throw new IllegalStateException("WebApplicationContext not found!");
/*    */     }
/* 35 */     LocaleResolver localeResolver = 
/* 36 */       RequestContextUtils.getLocaleResolver(request);
/*    */     Locale locale;
/* 38 */     if (localeResolver != null)
/* 39 */       locale = localeResolver.resolveLocale(request);
/*    */     else {
/* 41 */       locale = request.getLocale();
/*    */     }
/* 43 */     return messageSource.getMessage(code, args, locale);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.MessageResolver
 * JD-Core Version:    0.6.2
 */