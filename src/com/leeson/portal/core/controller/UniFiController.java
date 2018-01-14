/*     */ package com.leeson.portal.core.controller;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.UniFiMacSiteMap;
/*     */ import com.leeson.portal.core.model.iKuaiMacIpMap;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*     */ import java.io.IOException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class UniFiController extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = -1966047929923869408L;
/*  35 */   private static Config config = Config.getInstance();
/*     */ 
/*  37 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*     */ 
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  44 */     response.setContentType("text/html;charset=utf-8");
/*  45 */     request.setCharacterEncoding("utf-8");
/*     */ 
/*  47 */     String id = request.getParameter("id");
/*  48 */     String ssid = request.getParameter("ssid");
/*  49 */     if (stringUtils.isNotBlank(ssid)) {
/*  50 */       ssid = new String(ssid.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*  52 */     String url = request.getParameter("url");
/*  53 */     if (stringUtils.isNotBlank(url)) {
/*  54 */       url = new String(url.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*  56 */     String apmac = request.getParameter("ap");
/*  57 */     String site = request.getRequestURI();
/*  58 */     String[] ss = site.split("s/");
/*  59 */     int pos = ss[1].length();
/*  60 */     site = ss[1].substring(0, pos - 1);
/*     */ 
/*  64 */     HttpSession session = request.getSession();
/*  65 */     Cookie[] cookies = request.getCookies();
/*  66 */     String cpassword = "";
/*  67 */     String cmac = "";
/*  68 */     if (cookies != null) {
/*  69 */       for (int i = 0; i < cookies.length; i++) {
/*  70 */         if (cookies[i].getName().equals("password"))
/*  71 */           cpassword = cookies[i].getValue();
/*  72 */         if (cookies[i].getName().equals("mac")) {
/*  73 */           cmac = cookies[i].getValue();
/*     */         }
/*     */       }
/*     */     }
/*  77 */     String ikweb = url;
/*  78 */     String ikmac = id;
/*     */ 
/*  80 */     if (stringUtils.isBlank(ikweb)) {
/*  81 */       ikweb = (String)session.getAttribute("ikweb");
/*     */     }
/*     */ 
/*  84 */     if (stringUtils.isBlank(apmac)) {
/*  85 */       apmac = (String)session.getAttribute("apmac");
/*     */     }
/*     */ 
/*  88 */     if (stringUtils.isBlank(ikmac)) {
/*  89 */       ikmac = (String)session.getAttribute("ikmac");
/*     */     }
/*  91 */     if (stringUtils.isBlank(ikmac)) {
/*  92 */       ikmac = cmac;
/*     */     }
/*     */ 
/*  95 */     if (stringUtils.isBlank(site)) {
/*  96 */       site = (String)session.getAttribute("site");
/*     */     }
/*  98 */     if (stringUtils.isBlank(site)) {
/*  99 */       site = "default";
/*     */     }
/* 101 */     session.setAttribute("site", site);
/* 102 */     Cookie cookieSite = new Cookie("site", site);
/* 103 */     cookieSite.setMaxAge(86400);
/* 104 */     response.addCookie(cookieSite);
/*     */ 
/* 106 */     String basip = ((Portalbas)config.getConfigMap().get("")).getBasIp();
/*     */ 
/* 109 */     String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/* 110 */     ip = ikmac;
/*     */ 
/* 114 */     if (stringUtils.isNotBlank(ssid)) {
/* 115 */       ssid = URLDecoder.decode(ssid, "utf-8");
/*     */     }
/* 117 */     if (stringUtils.isBlank(ssid)) {
/* 118 */       ssid = (String)session.getAttribute("ssid");
/*     */     }
/*     */ 
/* 121 */     if (stringUtils.isNotBlank(ikmac)) {
/* 122 */       ikmac = PortalUtil.MacFormat(ikmac);
/* 123 */       iKuaiMacIpMap.getInstance().getMacIpMap().put(ikmac, ip);
/* 124 */       UniFiMacSiteMap.getInstance().getMacSiteMap().put(ikmac, site);
/* 125 */       Cookie cookieMac = new Cookie("mac", ikmac);
/* 126 */       cookieMac.setMaxAge(86400);
/* 127 */       response.addCookie(cookieMac);
/* 128 */       session.setAttribute("ikmac", ikmac);
/*     */     }
/*     */ 
/* 132 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 133 */       ip + ":" + basip);
/* 134 */     if (isLogin) {
/* 135 */       String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
/* 136 */         ip + ":" + basip);
/* 137 */       String username = loginInfo[0];
/* 138 */       session.setAttribute("username", username);
/* 139 */       session.setAttribute("password", cpassword);
/* 140 */       session.setAttribute("ip", ip);
/* 141 */       session.setAttribute("basip", basip);
/* 142 */       session.setAttribute("ssid", ssid);
/* 143 */       session.setAttribute("apmac", apmac);
/*     */ 
/* 145 */       request.getRequestDispatcher("/ok.jsp").forward(request, response);
/*     */ 
/* 147 */       return;
/*     */     }
/*     */ 
/* 150 */     session.setAttribute("ikweb", ikweb);
/*     */ 
/* 152 */     session.setAttribute("ssid", ssid);
/*     */ 
/* 154 */     session.setAttribute("apmac", apmac);
/*     */ 
/* 162 */     request.getRequestDispatcher("/index_choose").forward(request, response);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.UniFiController
 * JD-Core Version:    0.6.2
 */