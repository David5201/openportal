/*    */ package com.leeson.common.interceptor;
/*    */ 
/*    */ import com.leeson.core.bean.Portaluser;
/*    */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*    */ import java.util.Collection;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.shiro.SecurityUtils;
/*    */ import org.apache.shiro.authz.AuthorizationException;
/*    */ import org.apache.shiro.subject.Subject;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ public class PermissionInterceptorAdapter extends HandlerInterceptorAdapter
/*    */ {
/* 25 */   private static Logger logger = Logger.getLogger(PermissionInterceptorAdapter.class);
/*    */ 
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*    */     throws Exception
/*    */   {
/* 31 */     boolean isPermissioin = false;
/* 32 */     Subject currentUser = SecurityUtils.getSubject();
/* 33 */     Portaluser user = (Portaluser)request.getSession()
/* 34 */       .getAttribute("user");
/* 35 */     if ((user != null) && (user.getLoginName().equals("admin"))) {
/* 36 */       return true;
/*    */     }
/* 38 */     String privUrl = request.getServletPath();
/*    */ 
/* 40 */     int pos = privUrl.indexOf("?");
/* 41 */     if (pos > -1) {
/* 42 */       privUrl = privUrl.substring(0, pos);
/*    */     }
/*    */ 
/* 45 */     pos = privUrl.indexOf(".action");
/* 46 */     if (pos > -1) {
/* 47 */       privUrl = privUrl.substring(0, pos);
/*    */     }
/*    */ 
/* 50 */     if (privUrl.endsWith("UI")) {
/* 51 */       privUrl = privUrl.substring(0, privUrl.length() - 2);
/*    */     }
/*    */ 
/* 54 */     if (privUrl.endsWith("V")) {
/* 55 */       privUrl = privUrl.substring(0, privUrl.length() - 1);
/*    */     }
/*    */ 
/* 58 */     if (privUrl.endsWith("deletes")) {
/* 59 */       privUrl = privUrl.substring(0, privUrl.length() - 1);
/*    */     }
/*    */ 
/* 62 */     if (privUrl.endsWith("kicks")) {
/* 63 */       privUrl = privUrl.substring(0, privUrl.length() - 1);
/*    */     }
/*    */ 
/* 67 */     Collection allPrivilegeUrls = (Collection)request
/* 68 */       .getServletContext().getAttribute("allPrivilegeUrls");
/* 69 */     if (!allPrivilegeUrls.contains(privUrl)) {
/* 70 */       isPermissioin = true;
/*    */     }
/* 74 */     else if (currentUser.isPermitted(privUrl)) {
/* 75 */       isPermissioin = true;
/*    */     }
/*    */ 
/* 81 */     if (isPermissioin)
/*    */     {
/* 83 */       return true;
/*    */     }
/*    */ 
/* 86 */     logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Login Username: [" + user.getLoginName() + "] request URL: " + privUrl + " Not Have Privileges !!!");
/* 87 */     throw new AuthorizationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.PermissionInterceptorAdapter
 * JD-Core Version:    0.6.2
 */