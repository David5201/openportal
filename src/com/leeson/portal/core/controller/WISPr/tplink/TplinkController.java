/*     */ package com.leeson.portal.core.controller.WISPr.tplink;
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
/*     */ public class TplinkController extends HttpServlet
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
/*  85 */     String pip = request.getParameter("staIp");
/*  86 */     String pmac = request.getParameter("staMac");
/*  87 */     String papmac = request.getParameter("apMac");
/*  88 */     String papip = request.getParameter("apIp");
/*  89 */     String pvlan = request.getParameter("vlan");
/*     */ 
/*  91 */     String url = request.getParameter("url");
/*  92 */     String gw_address = request.getParameter("gw_address");
/*  93 */     String gw_port = request.getParameter("gw_port");
/*  94 */     String gw_id = request.getParameter("gw_id");
/*     */ 
/*  96 */     boolean isAC = true;
/*  97 */     if (stringUtils.isNotEmpty(gw_address)) {
/*  98 */       isAC = false;
/*  99 */       pip = request.getParameter("ip");
/* 100 */       pmac = request.getParameter("mac");
/*     */     }
/*     */ 
/* 103 */     if (stringUtils.isNotBlank(papmac)) {
/* 104 */       apmac = papmac;
/*     */     }
/*     */ 
/* 108 */     if (isAC) {
/* 109 */       if ((stringUtils.isBlank(pip)) || (stringUtils.isBlank(pmac)) || (stringUtils.isBlank(papmac)) || (stringUtils.isBlank(papip)) || (stringUtils.isBlank(pvlan))) {
/* 110 */         request.getRequestDispatcher("/error.html").forward(request, 
/* 111 */           response);
/*     */       }
/*     */ 
/*     */     }
/* 115 */     else if ((stringUtils.isBlank(pip)) || (stringUtils.isBlank(pmac)) || (stringUtils.isBlank(url)) || (stringUtils.isBlank(gw_address)) || (stringUtils.isBlank(gw_id)) || (stringUtils.isBlank(gw_port))) {
/* 116 */       request.getRequestDispatcher("/error.html").forward(request, 
/* 117 */         response);
/* 118 */       return;
/*     */     }
/*     */ 
/* 123 */     session.setAttribute("apip", papip);
/* 124 */     session.setAttribute("vlan", pvlan);
/* 125 */     session.setAttribute("ip", ip);
/*     */ 
/* 128 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 131 */     if (stringUtils.isBlank(basip)) {
/* 132 */       basip = cbasip;
/*     */     }
/* 134 */     if (stringUtils.isBlank(basip)) {
/* 135 */       basip = basConfig.getBasIp();
/*     */     }
/*     */ 
/* 138 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 139 */     if ((basConfigFind != null) && 
/* 140 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/* 141 */       basConfig = basConfigFind;
/*     */     }
/*     */ 
/* 144 */     basip = basConfig.getBasIp();
/*     */ 
/* 146 */     if (!basConfig.getBas().equals("8")) {
/* 147 */       request.getRequestDispatcher("/error.html").forward(request, 
/* 148 */         response);
/* 149 */       return;
/*     */     }
/*     */ 
/* 152 */     if (stringUtils.isNotBlank(pip)) {
/* 153 */       ip = pip;
/*     */     }
/* 155 */     if (stringUtils.isBlank(ip)) {
/* 156 */       ip = cip;
/*     */     }
/* 158 */     if (stringUtils.isBlank(ip)) {
/* 159 */       if ("0".equals(basConfig.getIsOut())) {
/* 160 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*     */       } else {
/* 162 */         request.getRequestDispatcher("/error.html").forward(request, 
/* 163 */           response);
/* 164 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 168 */     if (stringUtils.isNotBlank(pmac)) {
/* 169 */       ikmac = pmac;
/*     */     }
/* 171 */     if (stringUtils.isBlank(ikmac)) {
/* 172 */       ikmac = cmac;
/*     */     }
/*     */ 
/* 175 */     if (stringUtils.isNotBlank(ikmac)) {
/* 176 */       ikmac = PortalUtil.MacFormat(ikmac);
/* 177 */       Cookie cookieMac = new Cookie("mac", ikmac);
/* 178 */       cookieMac.setMaxAge(86400);
/* 179 */       response.addCookie(cookieMac);
/* 180 */       session.setAttribute("ikmac", ikmac);
/*     */     }
/*     */ 
/* 183 */     if (stringUtils.isNotBlank(pmac)) {
/* 184 */       session.setAttribute("pmac", pmac);
/*     */     }
/*     */ 
/* 187 */     if (stringUtils.isNotBlank(apmac)) {
/* 188 */       session.setAttribute("apmac", apmac);
/*     */     }
/*     */ 
/* 191 */     if (stringUtils.isNotBlank(ssid)) {
/* 192 */       ssid = URLDecoder.decode(ssid);
/* 193 */       ssid = StringEscapeUtils.unescapeHtml(ssid);
/* 194 */       session.setAttribute("ssid", ssid);
/*     */     }
/* 196 */     if (stringUtils.isNotBlank(ikweb)) {
/* 197 */       ikweb = URLDecoder.decode(ikweb);
/* 198 */       ikweb = StringEscapeUtils.unescapeHtml(ikweb);
/* 199 */       if (!ikweb.startsWith("http")) {
/* 200 */         ikweb = "http://" + ikweb;
/*     */       }
/*     */     }
/*     */ 
/* 204 */     String webMod = Tools.getWebMod(ssid, apmac, ip, basConfig.getWeb());
/* 205 */     session.setAttribute("web", webMod);
/*     */ 
/* 207 */     String userAgent = request.getHeader("user-agent");
/* 208 */     boolean isComputer = false;
/* 209 */     String agent = "";
/* 210 */     if (stringUtils.isNotBlank(userAgent)) {
/* 211 */       if (userAgent.contains("Windows")) {
/* 212 */         isComputer = true;
/* 213 */         agent = "Windows";
/* 214 */       } else if (userAgent.contains("Macintosh")) {
/* 215 */         isComputer = true;
/* 216 */         agent = "MacOS";
/*     */       }
/* 218 */       else if (userAgent.contains("Linux")) {
/* 219 */         isComputer = false;
/* 220 */         agent = "Android";
/* 221 */       } else if (userAgent.contains("Android")) {
/* 222 */         isComputer = false;
/* 223 */         agent = "Android";
/* 224 */       } else if (userAgent.contains("iPhone")) {
/* 225 */         isComputer = false;
/* 226 */         agent = "IOS";
/* 227 */       } else if (userAgent.contains("iPad")) {
/* 228 */         isComputer = false;
/* 229 */         agent = "IOS";
/* 230 */       } else if (userAgent.contains("iPod")) {
/* 231 */         isComputer = false;
/* 232 */         agent = "IOS";
/*     */       }
/*     */     }
/*     */ 
/* 236 */     session.setAttribute("agent", agent);
/* 237 */     if (!"1".equals(basConfig.getIsComputer()))
/*     */     {
/* 240 */       if (isComputer) {
/* 241 */         session.setAttribute("web", "");
/* 242 */         request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
/* 243 */         request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, 
/* 244 */           response);
/* 245 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 249 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 250 */       ip + ":" + basip);
/* 251 */     if (isLogin) {
/* 252 */       Kick.kickUserWISPrSyn(ip + ":" + basip, ikmac, "");
/*     */     }
/*     */ 
/* 255 */     if (onlineMap.getOnlineUserMap().size() >= Integer.valueOf(((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
/* 256 */       request.setAttribute("msg", "已超过允许最大用户数限制！！");
/* 257 */       request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, response);
/* 258 */       return;
/*     */     }
/*     */ 
/* 261 */     session.setAttribute("ip", ip);
/* 262 */     session.setAttribute("basip", basip);
/* 263 */     session.setAttribute("ikweb", ikweb);
/* 264 */     session.setAttribute("ssid", ssid);
/*     */ 
/* 266 */     String auth = basConfig.getAuthInterface();
/* 267 */     auth = auth.replace("3", "");
/* 268 */     session.setAttribute("auth", auth);
/*     */ 
/* 271 */     String callbackPath = request.getScheme() + "://" + 
/* 272 */       request.getServerName() + ":" + request.getServerPort() + 
/* 273 */       request.getContextPath() + "/AuthAPI";
/* 274 */     session.setAttribute("callbackPath", callbackPath);
/*     */ 
/* 277 */     if (isAC) {
/* 278 */       String loginUrl = "http://" + basip + ":8080/portal/auth";
/* 279 */       session.setAttribute("loginUrl", loginUrl);
/* 280 */       session.setAttribute("AC", "yes");
/*     */     } else {
/* 282 */       String loginUrl = "http://" + basip + ":8080/wifidog/logincheck/";
/* 283 */       session.setAttribute("loginUrl", loginUrl);
/* 284 */       session.setAttribute("AC", "no");
/*     */ 
/* 286 */       session.setAttribute("url", callbackPath);
/* 287 */       session.setAttribute("gw_address", gw_address);
/* 288 */       session.setAttribute("gw_id", gw_id);
/* 289 */       session.setAttribute("gw_port", gw_port);
/*     */     }
/*     */ 
/* 292 */     String logoutUrl = "http://" + basip + ":8080/portal/logout";
/* 293 */     session.setAttribute("logoutUrl", logoutUrl);
/*     */ 
/* 296 */     if (Tools.autoLogin(basip, ip, ikmac, basConfig, session)) {
/* 297 */       request.getRequestDispatcher("/APIGoAuthTplink.jsp").forward(request, 
/* 298 */         response);
/* 299 */       return;
/*     */     }
/*     */ 
/* 304 */     Cookie cookieIP = new Cookie("ip", null);
/* 305 */     cookieIP.setMaxAge(-1);
/* 306 */     response.addCookie(cookieIP);
/* 307 */     Cookie cookieBasIp = new Cookie("basip", null);
/* 308 */     cookieBasIp.setMaxAge(-1);
/* 309 */     response.addCookie(cookieBasIp);
/* 310 */     Cookie cookiePwd = new Cookie("password", null);
/* 311 */     cookiePwd.setMaxAge(-1);
/* 312 */     response.addCookie(cookiePwd);
/*     */ 
/* 314 */     ipMap.getInstance().getIpmap().remove(ip);
/*     */ 
/* 316 */     session.setAttribute("api", "tplink");
/*     */ 
/* 318 */     if (Tools.Do()) {
/*     */       try {
/* 320 */         if (stringUtils.isNotBlank(webMod)) {
/* 321 */           String ids = webMod.replace("/", "");
/* 322 */           Long id = Long.valueOf(ids);
/* 323 */           Portalweb web = webService.getPortalwebByKey(id);
/* 324 */           Long advID = Long.valueOf(0L);
/* 325 */           if (web != null) {
/* 326 */             advID = web.getAdv();
/* 327 */             Advadv adv = advadvService.getAdvadvByKey(advID);
/* 328 */             if (adv != null) {
/* 329 */               int state = adv.getState().intValue();
/* 330 */               if (state == 1) {
/* 331 */                 Date startDate = adv.getShowDate();
/* 332 */                 Date endDate = adv.getEndDate();
/* 333 */                 Date nowDate = new Date();
/* 334 */                 if (((startDate == null) || 
/* 335 */                   (nowDate.getTime() >= startDate.getTime())) && (
/* 336 */                   (endDate == null) || 
/* 337 */                   (endDate.getTime() >= nowDate.getTime()))) {
/* 338 */                   request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=1").forward(
/* 339 */                     request, response);
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
/* 350 */     request.getRequestDispatcher("/" + webMod + "APIauth.jsp").forward(
/* 351 */       request, response);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.tplink.TplinkController
 * JD-Core Version:    0.6.2
 */