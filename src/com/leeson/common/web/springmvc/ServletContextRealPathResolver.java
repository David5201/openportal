/*    */ package com.leeson.common.web.springmvc;
/*    */ 
/*    */ import javax.servlet.ServletContext;
/*    */ import org.springframework.stereotype.Component;
/*    */ import org.springframework.web.context.ServletContextAware;
/*    */ 
/*    */ @Component
/*    */ public class ServletContextRealPathResolver
/*    */   implements RealPathResolver, ServletContextAware
/*    */ {
/*    */   private ServletContext context;
/*    */ 
/*    */   public String get(String path)
/*    */   {
/* 12 */     String realpath = this.context.getRealPath(path);
/*    */ 
/* 14 */     if (realpath == null) {
/* 15 */       realpath = this.context.getRealPath("/") + path;
/*    */     }
/* 17 */     return realpath;
/*    */   }
/*    */ 
/*    */   public void setServletContext(ServletContext servletContext) {
/* 21 */     this.context = servletContext;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.ServletContextRealPathResolver
 * JD-Core Version:    0.6.2
 */