/*    */ package com.leeson.common.web;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class FrontUtils
/*    */ {
/*    */   public static final String MESSAGE = "message";
/*    */   public static final String LOGIN_URL = "user/login.html";
/*    */   public static final String RETURN_URL = "returnUrl";
/*    */ 
/*    */   public static String showLogin(HttpServletRequest request)
/*    */   {
/* 30 */     StringBuilder buff = new StringBuilder("redirect:");
/* 31 */     buff.append("user/login.html").append("?");
/* 32 */     buff.append("returnUrl").append("=");
/* 33 */     buff.append(RequestUtils.getLocation(request));
/* 34 */     System.out.println(buff.toString());
/* 35 */     return buff.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.FrontUtils
 * JD-Core Version:    0.6.2
 */