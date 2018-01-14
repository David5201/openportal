/*    */ package com.leeson.common.interceptor;
/*    */ 
/*    */ import com.leeson.core.bean.Portaluser;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ public class LoginHandlerInterceptor extends HandlerInterceptorAdapter
/*    */ {
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*    */     throws Exception
/*    */   {
/* 17 */     String path = request.getServletPath();
/* 18 */     if (path.matches(".*/((login)|(logout)|(code)).*")) {
/* 19 */       return true;
/*    */     }
/* 21 */     HttpSession session = request.getSession();
/* 22 */     Portaluser user = (Portaluser)session.getAttribute("sessionUser");
/* 23 */     if (user != null) {
/* 24 */       return true;
/*    */     }
/* 26 */     response.sendRedirect(request.getContextPath() + "/login.html");
/* 27 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.LoginHandlerInterceptor
 * JD-Core Version:    0.6.2
 */