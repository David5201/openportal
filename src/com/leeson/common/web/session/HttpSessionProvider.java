/*    */ package com.leeson.common.web.session;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class HttpSessionProvider
/*    */   implements SessionProvider
/*    */ {
/*    */   public Serializable getAttribute(HttpServletRequest request, String name)
/*    */   {
/* 15 */     HttpSession session = request.getSession(false);
/* 16 */     if (session != null) {
/* 17 */       return (Serializable)session.getAttribute(name);
/*    */     }
/* 19 */     return null;
/*    */   }
/*    */ 
/*    */   public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value)
/*    */   {
/* 25 */     HttpSession session = request.getSession();
/* 26 */     session.setAttribute(name, value);
/*    */   }
/*    */ 
/*    */   public String getSessionId(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 31 */     return request.getSession().getId();
/*    */   }
/*    */ 
/*    */   public void logout(HttpServletRequest request, HttpServletResponse response) {
/* 35 */     HttpSession session = request.getSession(false);
/* 36 */     if (session != null)
/* 37 */       session.invalidate();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.HttpSessionProvider
 * JD-Core Version:    0.6.2
 */