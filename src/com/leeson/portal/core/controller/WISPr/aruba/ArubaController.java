/*     */ package com.leeson.portal.core.controller.WISPr.aruba;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Advadv;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalweb;
/*     */ import com.leeson.core.service.AdvadvService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.controller.WISPr.utils.Tools;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.ipMap;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ 
/*     */ public class ArubaController extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = -1966047929923869408L;
/*  41 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*     */ 
/*  43 */   private static Config config = Config.getInstance();
/*     */ 
/*  45 */   private static AdvadvService advadvService = (AdvadvService)
/*  46 */     SpringContextHelper.getBean("advadvServiceImpl");
/*     */ 
/*  47 */   private static PortalwebService webService = (PortalwebService)
/*  48 */     SpringContextHelper.getBean("portalwebServiceImpl");
/*     */ 
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  53 */     response.setContentType("text/html;charset=utf-8");
/*  54 */     request.setCharacterEncoding("utf-8");
/*     */ 
/*  56 */     HttpSession session = request.getSession();
/*     */ 
/*  58 */     String apmac = (String)session.getAttribute("apmac");
/*  59 */     String ikmac = (String)session.getAttribute("ikmac");
/*  60 */     String ssid = "";
/*  61 */     String ikweb = "";
/*     */ 
/*  64 */     Cookie[] cookies = request.getCookies();
/*  65 */     String cip = "";
/*  66 */     String cbasip = "";
/*  67 */     String cmac = "";
/*  68 */     if (cookies != null) {
/*  69 */       for (int i = 0; i < cookies.length; i++) {
/*  70 */         if (cookies[i].getName().equals("ip"))
/*  71 */           cip = cookies[i].getValue();
/*  72 */         if (cookies[i].getName().equals("basip"))
/*  73 */           cbasip = cookies[i].getValue();
/*  74 */         if (cookies[i].getName().equals("mac")) {
/*  75 */           cmac = cookies[i].getValue();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  80 */     String ip = (String)session.getAttribute("ip");
/*  81 */     String basip = (String)session.getAttribute("basip");
/*     */ 
/*  85 */     String pip = request.getParameter("ip");
/*  86 */     String pmac = request.getParameter("mac");
/*  87 */     String purl = request.getParameter("url");
/*  88 */     if (stringUtils.isNotBlank(purl)) {
/*  89 */       purl = new String(purl.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*  91 */     String pbasip = request.getParameter("sip");
/*  92 */     String pssid = request.getParameter("essid");
/*  93 */     if (stringUtils.isNotBlank(pssid)) {
/*  94 */       pssid = new String(pssid.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*     */ 
/*  97 */     System.out.println("basip=" + pbasip + "----ip=" + pip + "----mac=" + 
/*  98 */       pmac + "----url=" + purl + "----ssid=" + pssid);
/*     */ 
/* 101 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 103 */     if (stringUtils.isNotBlank(pbasip)) {
/* 104 */       basip = pbasip;
/*     */     }
/* 106 */     if (stringUtils.isBlank(basip)) {
/* 107 */       basip = cbasip;
/*     */     }
/* 109 */     if (stringUtils.isBlank(basip)) {
/* 110 */       basip = basConfig.getBasIp();
/*     */     }
/*     */ 
/* 113 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 114 */     if ((basConfigFind != null) && 
/* 115 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/* 116 */       basConfig = basConfigFind;
/*     */     }
/*     */ 
/* 119 */     basip = basConfig.getBasIp();
/*     */ 
/* 121 */     if (!basConfig.getBas().equals("7")) {
/* 122 */       request.getRequestDispatcher("/error.html").forward(request, 
/* 123 */         response);
/* 124 */       return;
/*     */     }
/*     */ 
/* 127 */     if (stringUtils.isNotBlank(pip)) {
/* 128 */       ip = pip;
/*     */     }
/* 130 */     if (stringUtils.isBlank(ip)) {
/* 131 */       ip = cip;
/*     */     }
/* 133 */     if (stringUtils.isBlank(ip)) {
/* 134 */       if ("0".equals(basConfig.getIsOut())) {
/* 135 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*     */       } else {
/* 137 */         request.getRequestDispatcher("/error.html").forward(request, 
/* 138 */           response);
/* 139 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 143 */     if (stringUtils.isNotBlank(pmac)) {
/* 144 */       ikmac = pmac;
/*     */     }
/* 146 */     if (stringUtils.isBlank(ikmac)) {
/* 147 */       ikmac = cmac;
/*     */     }
/*     */ 
/* 150 */     if (stringUtils.isNotBlank(purl)) {
/* 151 */       ikweb = purl;
/*     */     }
/*     */ 
/* 154 */     if (stringUtils.isNotBlank(pssid)) {
/* 155 */       ssid = pssid;
/*     */     }
/*     */ 
/* 158 */     if (stringUtils.isBlank(ssid)) {
/* 159 */       ssid = (String)session.getAttribute("ssid");
/*     */     }
/*     */ 
/* 162 */     if (stringUtils.isNotBlank(ikmac)) {
/* 163 */       ikmac = PortalUtil.MacFormat(ikmac);
/* 164 */       Cookie cookieMac = new Cookie("mac", ikmac);
/* 165 */       cookieMac.setMaxAge(86400);
/* 166 */       response.addCookie(cookieMac);
/* 167 */       session.setAttribute("ikmac", ikmac);
/*     */     }
/*     */ 
/* 170 */     if (stringUtils.isNotBlank(apmac)) {
/* 171 */       session.setAttribute("apmac", apmac);
/*     */     }
/*     */ 
/* 174 */     if (stringUtils.isNotBlank(ssid)) {
/* 175 */       ssid = URLDecoder.decode(ssid);
/* 176 */       ssid = StringEscapeUtils.unescapeHtml(ssid);
/* 177 */       session.setAttribute("ssid", ssid);
/*     */     }
/* 179 */     if (stringUtils.isNotBlank(ikweb)) {
/* 180 */       ikweb = URLDecoder.decode(ikweb);
/* 181 */       ikweb = StringEscapeUtils.unescapeHtml(ikweb);
/* 182 */       if (!ikweb.startsWith("http")) {
/* 183 */         ikweb = "http://" + ikweb;
/*     */       }
/*     */     }
/*     */ 
/* 187 */     String webMod = Tools.getWebMod(ssid, apmac, ip, basConfig.getWeb());
/* 188 */     session.setAttribute("web", webMod);
/*     */ 
/* 190 */     String userAgent = request.getHeader("user-agent");
/* 191 */     boolean isComputer = false;
/* 192 */     String agent = "";
/* 193 */     if (stringUtils.isNotBlank(userAgent)) {
/* 194 */       if (userAgent.contains("Windows")) {
/* 195 */         isComputer = true;
/* 196 */         agent = "Windows";
/* 197 */       } else if (userAgent.contains("Macintosh")) {
/* 198 */         isComputer = true;
/* 199 */         agent = "MacOS";
/*     */       }
/* 201 */       else if (userAgent.contains("Linux")) {
/* 202 */         isComputer = false;
/* 203 */         agent = "Android";
/* 204 */       } else if (userAgent.contains("Android")) {
/* 205 */         isComputer = false;
/* 206 */         agent = "Android";
/* 207 */       } else if (userAgent.contains("iPhone")) {
/* 208 */         isComputer = false;
/* 209 */         agent = "IOS";
/* 210 */       } else if (userAgent.contains("iPad")) {
/* 211 */         isComputer = false;
/* 212 */         agent = "IOS";
/* 213 */       } else if (userAgent.contains("iPod")) {
/* 214 */         isComputer = false;
/* 215 */         agent = "IOS";
/*     */       }
/*     */     }
/*     */ 
/* 219 */     session.setAttribute("agent", agent);
/* 220 */     if (!"1".equals(basConfig.getIsComputer()))
/*     */     {
/* 223 */       if (isComputer) {
/* 224 */         session.setAttribute("web", "");
/* 225 */         request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
/* 226 */         request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, 
/* 227 */           response);
/* 228 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 232 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 233 */       ip + ":" + basip);
/* 234 */     if (isLogin) {
/* 235 */       Kick.kickUserWISPrSyn(ip + ":" + basip, ikmac, "");
/*     */     }
/*     */ 
/* 238 */     if (onlineMap.getOnlineUserMap().size() >= Integer.valueOf(((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
/* 239 */       request.setAttribute("msg", "已超过允许最大用户数限制！！");
/* 240 */       request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, response);
/* 241 */       return;
/*     */     }
/*     */ 
/* 244 */     session.setAttribute("ip", ip);
/* 245 */     session.setAttribute("basip", basip);
/* 246 */     session.setAttribute("ikweb", ikweb);
/* 247 */     session.setAttribute("ssid", ssid);
/*     */ 
/* 249 */     String auth = basConfig.getAuthInterface();
/* 250 */     auth = auth.replace("3", "");
/* 251 */     session.setAttribute("auth", auth);
/*     */ 
/* 254 */     String callbackPath = request.getScheme() + "://" + 
/* 255 */       request.getServerName() + ":" + request.getServerPort() + 
/* 256 */       request.getContextPath() + "/AuthAPI";
/* 257 */     session.setAttribute("callbackPath", callbackPath);
/*     */ 
/* 259 */     String loginUrl = "http://securelogin.arubanetworks.com/cgi-bin/login";
/* 260 */     session.setAttribute("loginUrl", loginUrl);
/*     */ 
/* 262 */     String logoutUrl = "http://securelogin.arubanetworks.com/cgi-bin/logout";
/* 263 */     session.setAttribute("logoutUrl", logoutUrl);
/*     */ 
/* 266 */     if (Tools.autoLogin(basip, ip, ikmac, basConfig, session)) {
/* 267 */       request.getRequestDispatcher("/APIGoAuthAruba.jsp").forward(
/* 268 */         request, response);
/* 269 */       return;
/*     */     }
/*     */ 
/* 274 */     Cookie cookieIP = new Cookie("ip", null);
/* 275 */     cookieIP.setMaxAge(-1);
/* 276 */     response.addCookie(cookieIP);
/* 277 */     Cookie cookieBasIp = new Cookie("basip", null);
/* 278 */     cookieBasIp.setMaxAge(-1);
/* 279 */     response.addCookie(cookieBasIp);
/* 280 */     Cookie cookiePwd = new Cookie("password", null);
/* 281 */     cookiePwd.setMaxAge(-1);
/* 282 */     response.addCookie(cookiePwd);
/*     */ 
/* 284 */     ipMap.getInstance().getIpmap().remove(ip);
/*     */ 
/* 286 */     session.setAttribute("api", "aruba");
/*     */ 
/* 288 */     if (Tools.Do()) {
/*     */       try {
/* 290 */         if (stringUtils.isNotBlank(webMod)) {
/* 291 */           String ids = webMod.replace("/", "");
/* 292 */           Long id = Long.valueOf(ids);
/* 293 */           Portalweb web = webService.getPortalwebByKey(id);
/* 294 */           Long advID = Long.valueOf(0L);
/* 295 */           if (web != null) {
/* 296 */             advID = web.getAdv();
/* 297 */             Advadv adv = advadvService.getAdvadvByKey(advID);
/* 298 */             if (adv != null) {
/* 299 */               int state = adv.getState().intValue();
/* 300 */               if (state == 1) {
/* 301 */                 Date startDate = adv.getShowDate();
/* 302 */                 Date endDate = adv.getEndDate();
/* 303 */                 Date nowDate = new Date();
/* 304 */                 if (((startDate == null) || 
/* 305 */                   (nowDate.getTime() >= startDate.getTime())) && (
/* 306 */                   (endDate == null) || 
/* 307 */                   (endDate.getTime() >= nowDate.getTime()))) {
/* 308 */                   request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=1").forward(
/* 309 */                     request, response);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*     */     }
/* 320 */     request.getRequestDispatcher("/" + webMod + "APIauth.jsp").forward(
/* 321 */       request, response);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.aruba.ArubaController
 * JD-Core Version:    0.6.2
 */