/*    */ package com.leeson.common.web;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class XssFilter
/*    */   implements Filter
/*    */ {
/*    */   private String filterChar;
/*    */   private String replaceChar;
/*    */   private String splitChar;
/* 26 */   FilterConfig filterConfig = null;
/*    */ 
/* 28 */   public void init(FilterConfig filterConfig) throws ServletException { this.filterChar = filterConfig.getInitParameter("FilterChar");
/* 29 */     this.replaceChar = filterConfig.getInitParameter("ReplaceChar");
/* 30 */     this.splitChar = filterConfig.getInitParameter("SplitChar");
/* 31 */     this.filterConfig = filterConfig; }
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 35 */     this.filterConfig = null;
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 41 */     chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)request, this.filterChar, this.replaceChar, this.splitChar), response);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.XssFilter
 * JD-Core Version:    0.6.2
 */