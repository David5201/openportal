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
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class ProcessTimeFilter
/*    */   implements Filter
/*    */ {
/* 21 */   protected final Logger log = LoggerFactory.getLogger(ProcessTimeFilter.class);
/*    */   public static final String START_TIME = "_start_time";
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 32 */     HttpServletRequest request = (HttpServletRequest)req;
/* 33 */     long time = System.currentTimeMillis();
/* 34 */     request.setAttribute("_start_time", Long.valueOf(time));
/* 35 */     chain.doFilter(request, response);
/* 36 */     time = System.currentTimeMillis() - time;
/* 37 */     this.log.debug("process in {} ms: {}", Long.valueOf(time), request.getRequestURI());
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig arg0)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.ProcessTimeFilter
 * JD-Core Version:    0.6.2
 */