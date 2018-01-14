/*     */ package com.leeson.portal.core.controller.WISPr.ros;
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
/*     */ public class RosController extends HttpServlet
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
/*  86 */     String pip = request.getParameter("wlanuserip");
/*  87 */     String pmac = request.getParameter("mac");
/*  88 */     String purl = request.getParameter("url");
/*  89 */     String pbasip = request.getParameter("basip");
/*  90 */     String pssid = request.getParameter("ssid");
/*  91 */     String loginUrl = request.getParameter("login");
/*  92 */     String result = request.getParameter("result");
/*  93 */     if (stringUtils.isNotBlank(purl)) {
/*  94 */       purl = new String(purl.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*  96 */     if (stringUtils.isNotBlank(pssid)) {
/*  97 */       pssid = new String(pssid.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*     */ 
/* 100 */     System.out.println("basip=" + pbasip + "----ip=" + pip + "----mac=" + 
/* 101 */       pmac + "----url=" + purl + "----ssid=" + pssid + 
/* 102 */       "----loginUrl=" + loginUrl + "----result=" + result);
/*     */ 
/* 105 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 107 */     if (stringUtils.isNotBlank(pbasip)) {
/* 108 */       basip = pbasip;
/*     */     }
/* 110 */     if (stringUtils.isBlank(basip)) {
/* 111 */       basip = cbasip;
/*     */     }
/* 113 */     if (stringUtils.isBlank(basip)) {
/* 114 */       basip = basConfig.getBasIp();
/*     */     }
/*     */ 
/* 117 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 118 */     if ((basConfigFind != null) && 
/* 119 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/* 120 */       basConfig = basConfigFind;
/*     */     }
/*     */ 
/* 123 */     basip = basConfig.getBasIp();
/*     */ 
/* 125 */     if (!basConfig.getBas().equals("5")) {
/* 126 */       request.getRequestDispatcher("/error.html").forward(request, 
/* 127 */         response);
/* 128 */       return;
/*     */     }
/*     */ 
/* 131 */     if (stringUtils.isNotBlank(pip)) {
/* 132 */       ip = pip;
/*     */     }
/* 134 */     if (stringUtils.isBlank(ip)) {
/* 135 */       ip = cip;
/*     */     }
/* 137 */     if (stringUtils.isBlank(ip)) {
/* 138 */       if ("0".equals(basConfig.getIsOut())) {
/* 139 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*     */       } else {
/* 141 */         request.getRequestDispatcher("/error.html").forward(request, 
/* 142 */           response);
/* 143 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 147 */     if (stringUtils.isNotBlank(pmac)) {
/* 148 */       ikmac = pmac;
/*     */     }
/* 150 */     if (stringUtils.isBlank(ikmac)) {
/* 151 */       ikmac = cmac;
/*     */     }
/*     */ 
/* 154 */     if (stringUtils.isNotBlank(purl)) {
/* 155 */       ikweb = purl;
/*     */     }
/*     */ 
/* 158 */     if (stringUtils.isNotBlank(pssid)) {
/* 159 */       ssid = pssid;
/*     */     }
/*     */ 
/* 162 */     if (stringUtils.isBlank(ssid)) {
/* 163 */       ssid = (String)session.getAttribute("ssid");
/*     */     }
/*     */ 
/* 166 */     if (stringUtils.isNotBlank(ikmac)) {
/* 167 */       ikmac = PortalUtil.MacFormat(ikmac);
/* 168 */       Cookie cookieMac = new Cookie("mac", ikmac);
/* 169 */       cookieMac.setMaxAge(86400);
/* 170 */       response.addCookie(cookieMac);
/* 171 */       session.setAttribute("ikmac", ikmac);
/*     */     }
/*     */ 
/* 174 */     if (stringUtils.isNotBlank(apmac)) {
/* 175 */       session.setAttribute("apmac", apmac);
/*     */     }
/*     */ 
/* 178 */     if (stringUtils.isNotBlank(ssid)) {
/* 179 */       ssid = URLDecoder.decode(ssid);
/* 180 */       ssid = StringEscapeUtils.unescapeHtml(ssid);
/* 181 */       session.setAttribute("ssid", ssid);
/*     */     }
/* 183 */     if (stringUtils.isNotBlank(ikweb)) {
/* 184 */       ikweb = URLDecoder.decode(ikweb);
/* 185 */       ikweb = StringEscapeUtils.unescapeHtml(ikweb);
/* 186 */       if (!ikweb.startsWith("http")) {
/* 187 */         ikweb = "http://" + ikweb;
/*     */       }
/*     */     }
/*     */ 
/* 191 */     String webMod = Tools.getWebMod(ssid, apmac, ip, basConfig.getWeb());
/* 192 */     session.setAttribute("web", webMod);
/*     */ 
/* 194 */     String userAgent = request.getHeader("user-agent");
/* 195 */     boolean isComputer = false;
/* 196 */     String agent = "";
/* 197 */     if (stringUtils.isNotBlank(userAgent)) {
/* 198 */       if (userAgent.contains("Windows")) {
/* 199 */         isComputer = true;
/* 200 */         agent = "Windows";
/* 201 */       } else if (userAgent.contains("Macintosh")) {
/* 202 */         isComputer = true;
/* 203 */         agent = "MacOS";
/*     */       }
/* 205 */       else if (userAgent.contains("Linux")) {
/* 206 */         isComputer = false;
/* 207 */         agent = "Android";
/* 208 */       } else if (userAgent.contains("Android")) {
/* 209 */         isComputer = false;
/* 210 */         agent = "Android";
/* 211 */       } else if (userAgent.contains("iPhone")) {
/* 212 */         isComputer = false;
/* 213 */         agent = "IOS";
/* 214 */       } else if (userAgent.contains("iPad")) {
/* 215 */         isComputer = false;
/* 216 */         agent = "IOS";
/* 217 */       } else if (userAgent.contains("iPod")) {
/* 218 */         isComputer = false;
/* 219 */         agent = "IOS";
/*     */       }
/*     */     }
/*     */ 
/* 223 */     session.setAttribute("agent", agent);
/* 224 */     if (!"1".equals(basConfig.getIsComputer()))
/*     */     {
/* 227 */       if (isComputer) {
/* 228 */         session.setAttribute("web", "");
/* 229 */         request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
/* 230 */         request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, 
/* 231 */           response);
/* 232 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 236 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 237 */       ip + ":" + basip);
/* 238 */     if (isLogin) {
/* 239 */       Kick.kickUserWISPrSyn(ip + ":" + basip, ikmac, "");
/*     */     }
/*     */ 
/* 243 */     if (onlineMap.getOnlineUserMap().size() >= 
/* 243 */       Integer.valueOf(
/* 244 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 244 */       .get("core"))[1]).intValue())
/*     */     {
/* 245 */       request.setAttribute("msg", "已超过允许最大用户数限制！！");
/* 246 */       request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, response);
/* 247 */       return;
/*     */     }
/*     */ 
/* 250 */     session.setAttribute("ip", ip);
/* 251 */     session.setAttribute("basip", basip);
/* 252 */     session.setAttribute("ikweb", ikweb);
/* 253 */     session.setAttribute("ssid", ssid);
/*     */ 
/* 255 */     String auth = basConfig.getAuthInterface();
/* 256 */     auth = auth.replace("3", "");
/* 257 */     session.setAttribute("auth", auth);
/*     */ 
/* 260 */     if ("1".equals(result))
/* 261 */       session.setAttribute("msg", "认证失败！！");
/*     */     else {
/* 263 */       session.setAttribute("msg", null);
/*     */     }
/*     */ 
/* 266 */     String callbackPath = request.getScheme() + "://" + 
/* 267 */       request.getServerName() + ":" + request.getServerPort() + 
/* 268 */       request.getContextPath() + "/AuthAPI";
/* 269 */     session.setAttribute("callbackPath", callbackPath);
/*     */ 
/* 271 */     if (stringUtils.isNotBlank(loginUrl)) {
/* 272 */       session.setAttribute("loginUrl", loginUrl);
/*     */     } else {
/* 274 */       request.getRequestDispatcher("/error.html").forward(request, 
/* 275 */         response);
/* 276 */       return;
/*     */     }
/* 278 */     String logoutUrl = "http://" + basip + "/logout";
/* 279 */     session.setAttribute("logoutUrl", logoutUrl);
/*     */ 
/* 282 */     if ((Tools.autoLogin(basip, ip, ikmac, basConfig, session)) && (!"1".equals(result))) {
/* 283 */       request.getRequestDispatcher("/APIGoAuthRos.jsp").forward(request, 
/* 284 */         response);
/* 285 */       return;
/*     */     }
/*     */ 
/* 290 */     Cookie cookieIP = new Cookie("ip", null);
/* 291 */     cookieIP.setMaxAge(-1);
/* 292 */     response.addCookie(cookieIP);
/* 293 */     Cookie cookieBasIp = new Cookie("basip", null);
/* 294 */     cookieBasIp.setMaxAge(-1);
/* 295 */     response.addCookie(cookieBasIp);
/* 296 */     Cookie cookiePwd = new Cookie("password", null);
/* 297 */     cookiePwd.setMaxAge(-1);
/* 298 */     response.addCookie(cookiePwd);
/*     */ 
/* 300 */     ipMap.getInstance().getIpmap().remove(ip);
/*     */ 
/* 302 */     session.setAttribute("api", "ros");
/*     */ 
/* 304 */     if (Tools.Do()) {
/*     */       try {
/* 306 */         if (stringUtils.isNotBlank(webMod)) {
/* 307 */           String ids = webMod.replace("/", "");
/* 308 */           Long id = Long.valueOf(ids);
/* 309 */           Portalweb web = webService.getPortalwebByKey(id);
/* 310 */           Long advID = Long.valueOf(0L);
/* 311 */           if (web != null) {
/* 312 */             advID = web.getAdv();
/* 313 */             Advadv adv = advadvService.getAdvadvByKey(advID);
/* 314 */             if (adv != null) {
/* 315 */               int state = adv.getState().intValue();
/* 316 */               if (state == 1) {
/* 317 */                 Date startDate = adv.getShowDate();
/* 318 */                 Date endDate = adv.getEndDate();
/* 319 */                 Date nowDate = new Date();
/* 320 */                 if (((startDate == null) || 
/* 321 */                   (nowDate.getTime() >= startDate.getTime())) && (
/* 322 */                   (endDate == null) || 
/* 323 */                   (endDate.getTime() >= nowDate.getTime()))) {
/* 324 */                   request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=1").forward(
/* 325 */                     request, response);
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
/* 336 */     request.getRequestDispatcher("/" + webMod + "APIauth.jsp").forward(
/* 337 */       request, response);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.ros.RosController
 * JD-Core Version:    0.6.2
 */