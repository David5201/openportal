/*    */ package com.leeson.common.web;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URLDecoder;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletRequestWrapper;
/*    */ 
/*    */ public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper
/*    */ {
/*    */   private String[] filterChars;
/*    */   private String[] replaceChars;
/*    */ 
/*    */   public XssHttpServletRequestWrapper(HttpServletRequest request, String filterChar, String replaceChar, String splitChar)
/*    */   {
/* 19 */     super(request);
/* 20 */     if ((filterChar != null) && (filterChar.length() > 0)) {
/* 21 */       this.filterChars = filterChar.split(splitChar);
/*    */     }
/* 23 */     if ((replaceChar != null) && (replaceChar.length() > 0))
/* 24 */       this.replaceChars = replaceChar.split(splitChar);
/*    */   }
/*    */ 
/*    */   public String getQueryString() {
/* 28 */     String value = super.getQueryString();
/* 29 */     if (value != null) {
/* 30 */       value = xssEncode(value);
/*    */     }
/* 32 */     return value;
/*    */   }
/*    */ 
/*    */   public String getParameter(String name)
/*    */   {
/* 41 */     String value = super.getParameter(xssEncode(name));
/* 42 */     if (value != null) {
/* 43 */       value = xssEncode(value);
/*    */     }
/* 45 */     return value;
/*    */   }
/*    */ 
/*    */   public String[] getParameterValues(String name) {
/* 49 */     String[] parameters = super.getParameterValues(name);
/* 50 */     if ((parameters == null) || (parameters.length == 0)) {
/* 51 */       return null;
/*    */     }
/* 53 */     for (int i = 0; i < parameters.length; i++) {
/* 54 */       parameters[i] = xssEncode(parameters[i]);
/*    */     }
/* 56 */     return parameters;
/*    */   }
/*    */ 
/*    */   public String getHeader(String name)
/*    */   {
/* 65 */     String value = super.getHeader(xssEncode(name));
/* 66 */     if (value != null) {
/* 67 */       value = xssEncode(value);
/*    */     }
/* 69 */     return value;
/*    */   }
/*    */ 
/*    */   private String xssEncode(String s)
/*    */   {
/* 79 */     if ((s == null) || (s.equals("")))
/* 80 */       return s;
/*    */     try
/*    */     {
/* 83 */       s = URLDecoder.decode(s, "UTF-8");
/*    */     }
/*    */     catch (UnsupportedEncodingException e) {
/* 86 */       e.printStackTrace();
/*    */     }
/* 88 */     for (int i = 0; i < this.filterChars.length; i++) {
/* 89 */       if (s.contains(this.filterChars[i])) {
/* 90 */         s = s.replace(this.filterChars[i], this.replaceChars[i]);
/*    */       }
/*    */     }
/* 93 */     return s;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.XssHttpServletRequestWrapper
 * JD-Core Version:    0.6.2
 */