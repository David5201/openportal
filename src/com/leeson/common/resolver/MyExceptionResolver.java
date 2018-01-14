/*    */ package com.leeson.common.resolver;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.web.servlet.HandlerExceptionResolver;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ 
/*    */ public class MyExceptionResolver
/*    */   implements HandlerExceptionResolver
/*    */ {
/* 14 */   private static Logger logger = Logger.getLogger(MyExceptionResolver.class);
/* 15 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
/*    */   {
/* 19 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*    */ 
/* 21 */     if (basConfig.getIsdebug().equals("1")) {
/* 22 */       logger.error("==============Error Start=============");
/* 23 */       logger.error(ex);
/* 24 */       logger.error("Error Info", ex);
/* 25 */       logger.error("==============Error Stop=============");
/*    */     }
/*    */ 
/* 28 */     ModelAndView mv = new ModelAndView("errors");
/*    */ 
/* 35 */     return mv;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.resolver.MyExceptionResolver
 * JD-Core Version:    0.6.2
 */