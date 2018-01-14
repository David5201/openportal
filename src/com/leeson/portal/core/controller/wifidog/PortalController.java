/*    */ package com.leeson.portal.core.controller.wifidog;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PortalController extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = -8206480691084456762L;
/* 29 */   private static Config config = Config.getInstance();
/*    */ 
/* 31 */   private static Logger logger = Logger.getLogger(PortalController.class);
/*    */ 
/*    */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 44 */     HttpSession session = request.getSession();
/* 45 */     String url = (String)session.getAttribute("url");
/* 46 */     String token = (String)session.getAttribute("token");
/* 47 */     String webMod = (String)session.getAttribute("web");
/* 48 */     if (stringUtils.isBlank(webMod)) {
/* 49 */       webMod = "";
/*    */     }
/*    */ 
/* 52 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 53 */       logger.info("WifiDog Portal : " + 
/* 54 */         request.getRequestURL().toString() + "?" + 
/* 55 */         request.getQueryString());
/*    */     }
/*    */ 
/* 58 */     if (stringUtils.isNotBlank(token)) {
/* 59 */       String[] info = (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().get(token);
/* 60 */       if ((info.length == 11) && 
/* 61 */         ("weixin".equals(info[10])))
/*    */       {
/* 72 */         String weburl = "/wifidogWx.jsp";
/* 73 */         request.getRequestDispatcher("/" + webMod + weburl).forward(request, response);
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 78 */     if (stringUtils.isNotBlank(url))
/* 79 */       request.getRequestDispatcher("/" + webMod + "/wifidogOk.jsp?l=" + url).forward(request, response);
/*    */     else
/* 81 */       request.getRequestDispatcher("/" + webMod + "/wifidogOk.jsp").forward(request, response);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.PortalController
 * JD-Core Version:    0.6.2
 */