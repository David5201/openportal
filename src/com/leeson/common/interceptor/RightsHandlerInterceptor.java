/*    */ package com.leeson.common.interceptor;
/*    */ 
/*    */ import com.leeson.common.util.RightsHelper;
/*    */ import com.leeson.common.util.ServiceHelper;
/*    */ import com.leeson.common.util.Tools;
/*    */ import com.leeson.core.bean.Portaluser;
/*    */ import com.leeson.core.service.PortalprivilegeService;
/*    */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ import org.springframework.web.servlet.ModelAndViewDefiningException;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ public class RightsHandlerInterceptor extends HandlerInterceptorAdapter
/*    */ {
/* 22 */   private static Logger logger = Logger.getLogger(RightsHandlerInterceptor.class);
/*    */ 
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*    */     throws Exception
/*    */   {
/* 28 */     String path = request.getServletPath();
/* 29 */     if (path.matches(".*/((login)|(logout)|(code)).*"))
/* 30 */       return true;
/* 31 */     HttpSession session = request.getSession();
/* 32 */     Portaluser user = (Portaluser)session.getAttribute("sessionUser");
/* 33 */     String menuId = null;
/* 34 */     List<String> subList = (List)ServiceHelper.getPrivilegeService().getAllPrivilegeUrls();
/* 35 */     for (String m : subList) {
/* 36 */       String menuUrl = m;
/* 37 */       if (Tools.notEmpty(menuUrl)) {
/* 38 */         if (path.contains(menuUrl)) {
/* 39 */           menuId = m;
/* 40 */           break;
/*    */         }
/* 42 */         String[] arr = menuUrl.split("\\.");
/* 43 */         String regex = "";
/* 44 */         if (arr.length == 2) {
/* 45 */           regex = "/?" + arr[0] + "(/.*)?." + arr[1];
/*    */         }
/*    */         else {
/* 48 */           regex = "/?" + menuUrl + "(/.*)?.html";
/*    */         }
/* 50 */         if (path.matches(regex)) {
/* 51 */           menuId = m;
/* 52 */           break;
/*    */         }
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 58 */     if (menuId != null)
/*    */     {
/* 60 */       String userRights = (String)session.getAttribute("sessionUserRights");
/* 61 */       String roleRights = (String)session.getAttribute("sessionRoleRights");
/* 62 */       if ((RightsHelper.testRights(userRights, menuId)) || (RightsHelper.testRights(roleRights, menuId))) {
/* 63 */         return true;
/*    */       }
/* 65 */       logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Login Username: [" + user.getLoginName() + "] request URL: " + path + " Not Have Privileges !!!");
/* 66 */       ModelAndView mv = new ModelAndView();
/* 67 */       mv.setViewName("no_rights");
/* 68 */       throw new ModelAndViewDefiningException(mv);
/*    */     }
/*    */ 
/* 71 */     return super.preHandle(request, response, handler);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.RightsHandlerInterceptor
 * JD-Core Version:    0.6.2
 */