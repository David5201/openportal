/*    */ package com.leeson.portal.core.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.springframework.beans.BeansException;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.ApplicationContextAware;
/*    */ 
/*    */ public class SpringContextHelper
/*    */   implements ApplicationContextAware
/*    */ {
/*    */   private static ApplicationContext context;
/*    */ 
/*    */   public void setApplicationContext(ApplicationContext context)
/*    */     throws BeansException
/*    */   {
/* 19 */     context = context;
/*    */ 
/* 26 */     System.out.println(context);
/*    */   }
/*    */ 
/*    */   public static ApplicationContext getApplicationContext() {
/* 30 */     return context;
/*    */   }
/*    */ 
/*    */   public static Object getBean(String beanName) {
/* 34 */     return context.getBean(beanName);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.SpringContextHelper
 * JD-Core Version:    0.6.2
 */