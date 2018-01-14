/*    */ package com.leeson.common.listener;
/*    */ 
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import org.springframework.web.context.support.WebApplicationContextUtils;
/*    */ 
/*    */ public class WebAppContextListener
/*    */   implements ServletContextListener
/*    */ {
/*    */   public void contextDestroyed(ServletContextEvent event)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent event)
/*    */   {
/* 18 */     com.leeson.common.util.Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.listener.WebAppContextListener
 * JD-Core Version:    0.6.2
 */