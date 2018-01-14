/*     */ package com.leeson.portal.core.controller.WISPr.ruckus;
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
/*     */ public class RuckusController extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 4629097038325367725L;
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
/*  85 */     String pip = request.getParameter("uip");
/*  86 */     String pmac = request.getParameter("client_mac");
/*  87 */     String purl = request.getParameter("url");
/*  88 */     String pbasip = request.getParameter("sip");
/*  89 */     String pssid = request.getParameter("ssid");
/*  90 */     String papmac = request.getParameter("mac");
/*  91 */     String proxy = request.getParameter("proxy");
/*  92 */     if (stringUtils.isNotBlank(purl)) {
/*  93 */       purl = new String(purl.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*  95 */     if (stringUtils.isNotBlank(pssid)) {
/*  96 */       pssid = new String(pssid.getBytes("iso-8859-1"), "utf-8");
/*     */     }
/*     */ 
/*  99 */     if (stringUtils.isNotBlank(papmac)) {
/* 100 */       apmac = papmac;
/*     */     }
/*     */ 
/* 103 */     System.out.println("Ruckus basip=" + pbasip + "----ip=" + pip + 
/* 104 */       "----mac=" + pmac + "----url=" + purl + "----ssid=" + pssid + 
/* 105 */       "----apMac=" + papmac);
/*     */ 
/* 108 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 110 */     if (stringUtils.isNotBlank(pbasip)) {
/* 111 */       basip = pbasip;
/*     */     }
/* 113 */     if (stringUtils.isBlank(basip)) {
/* 114 */       basip = cbasip;
/*     */     }
/* 116 */     if (stringUtils.isBlank(basip)) {
/* 117 */       basip = basConfig.getBasIp();
/*     */     }
/*     */ 
/* 120 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 121 */     if ((basConfigFind != null) && 
/* 122 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/* 123 */       basConfig = basConfigFind;
/*     */     }
/*     */ 
/* 126 */     basip = basConfig.getBasIp();
/*     */ 
/* 128 */     if (!basConfig.getBas().equals("6")) {
/* 129 */       request.getRequestDispatcher("/error.html").forward(request, 
/* 130 */         response);
/* 131 */       return;
/*     */     }
/*     */ 
/* 134 */     if (stringUtils.isNotBlank(pip)) {
/* 135 */       ip = pip;
/*     */     }
/* 137 */     if (stringUtils.isBlank(ip)) {
/* 138 */       ip = cip;
/*     */     }
/* 140 */     if (stringUtils.isBlank(ip)) {
/* 141 */       if ("0".equals(basConfig.getIsOut())) {
/* 142 */         ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*     */       } else {
/* 144 */         request.getRequestDispatcher("/error.html").forward(request, 
/* 145 */           response);
/* 146 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 150 */     if (stringUtils.isNotBlank(pmac)) {
/* 151 */       ikmac = pmac;
/*     */     }
/* 153 */     if (stringUtils.isBlank(ikmac)) {
/* 154 */       ikmac = cmac;
/*     */     }
/*     */ 
/* 157 */     if (stringUtils.isNotBlank(purl)) {
/* 158 */       ikweb = purl;
/*     */     }
/*     */ 
/* 161 */     if (stringUtils.isNotBlank(pssid)) {
/* 162 */       ssid = pssid;
/*     */     }
/*     */ 
/* 165 */     if (stringUtils.isBlank(ssid)) {
/* 166 */       ssid = (String)session.getAttribute("ssid");
/*     */     }
/*     */ 
/* 169 */     if (stringUtils.isNotBlank(ikmac))
/*     */     {
/* 171 */       Cookie cookieMac = new Cookie("mac", ikmac);
/* 172 */       cookieMac.setMaxAge(86400);
/* 173 */       response.addCookie(cookieMac);
/* 174 */       session.setAttribute("ikmac", ikmac);
/*     */     }
/*     */ 
/* 177 */     if (stringUtils.isNotBlank(apmac)) {
/* 178 */       session.setAttribute("apmac", apmac);
/*     */     }
/*     */ 
/* 181 */     if (stringUtils.isNotBlank(ssid)) {
/* 182 */       ssid = URLDecoder.decode(ssid);
/* 183 */       ssid = StringEscapeUtils.unescapeHtml(ssid);
/* 184 */       session.setAttribute("ssid", ssid);
/*     */     }
/* 186 */     if (stringUtils.isNotBlank(ikweb)) {
/* 187 */       ikweb = URLDecoder.decode(ikweb);
/* 188 */       ikweb = StringEscapeUtils.unescapeHtml(ikweb);
/* 189 */       if (!ikweb.startsWith("http")) {
/* 190 */         ikweb = "http://" + ikweb;
/*     */       }
/*     */     }
/*     */ 
/* 194 */     String webMod = Tools.getWebMod(ssid, apmac, ip, basConfig.getWeb());
/* 195 */     session.setAttribute("web", webMod);
/*     */ 
/* 197 */     String userAgent = request.getHeader("user-agent");
/* 198 */     boolean isComputer = false;
/* 199 */     String agent = "";
/* 200 */     if (stringUtils.isNotBlank(userAgent)) {
/* 201 */       if (userAgent.contains("Windows")) {
/* 202 */         isComputer = true;
/* 203 */         agent = "Windows";
/* 204 */       } else if (userAgent.contains("Macintosh")) {
/* 205 */         isComputer = true;
/* 206 */         agent = "MacOS";
/*     */       }
/* 208 */       else if (userAgent.contains("Linux")) {
/* 209 */         isComputer = false;
/* 210 */         agent = "Android";
/* 211 */       } else if (userAgent.contains("Android")) {
/* 212 */         isComputer = false;
/* 213 */         agent = "Android";
/* 214 */       } else if (userAgent.contains("iPhone")) {
/* 215 */         isComputer = false;
/* 216 */         agent = "IOS";
/* 217 */       } else if (userAgent.contains("iPad")) {
/* 218 */         isComputer = false;
/* 219 */         agent = "IOS";
/* 220 */       } else if (userAgent.contains("iPod")) {
/* 221 */         isComputer = false;
/* 222 */         agent = "IOS";
/*     */       }
/*     */     }
/*     */ 
/* 226 */     session.setAttribute("agent", agent);
/* 227 */     if (!"1".equals(basConfig.getIsComputer()))
/*     */     {
/* 230 */       if (isComputer) {
/* 231 */         session.setAttribute("web", "");
/* 232 */         request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
/* 233 */         request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, 
/* 234 */           response);
/* 235 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 239 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 240 */       ip + ":" + basip);
/* 241 */     if (isLogin) {
/* 242 */       Kick.kickUserWISPrSyn(ip + ":" + basip, ikmac, "");
/*     */     }
/*     */ 
/* 245 */     if (onlineMap.getOnlineUserMap().size() >= Integer.valueOf(((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
/* 246 */       request.setAttribute("msg", "已超过允许最大用户数限制！！");
/* 247 */       request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, response);
/* 248 */       return;
/*     */     }
/*     */ 
/* 251 */     session.setAttribute("ip", ip);
/* 252 */     session.setAttribute("basip", basip);
/* 253 */     session.setAttribute("ikweb", ikweb);
/* 254 */     session.setAttribute("ssid", ssid);
/*     */ 
/* 256 */     String auth = basConfig.getAuthInterface();
/* 257 */     auth = auth.replace("3", "");
/* 258 */     session.setAttribute("auth", auth);
/*     */ 
/* 260 */     if (stringUtils.isNotBlank(proxy)) {
/* 261 */       session.setAttribute("proxy", proxy);
/*     */ 
/* 263 */       String loginUrl = "http://" + basip + ":9997/SubscriberPortal/hotspotlogin";
/* 264 */       session.setAttribute("loginUrl", loginUrl);
/* 265 */       String logoutUrl = "http://" + basip + ":9997//SubscriberPortal/hotspotlogout";
/* 266 */       session.setAttribute("logoutUrl", logoutUrl);
/*     */     }
/*     */     else
/*     */     {
/* 270 */       String loginUrl = "http://" + basip + ":9997/login";
/* 271 */       session.setAttribute("loginUrl", loginUrl);
/* 272 */       String logoutUrl = "http://" + basip + ":9997/logout";
/* 273 */       session.setAttribute("logoutUrl", logoutUrl);
/*     */     }
/*     */ 
/* 278 */     if (Tools.autoLogin(basip, ip, ikmac, basConfig, session)) {
/* 279 */       request.getRequestDispatcher("/APIGoAuthRuckus.jsp").forward(
/* 280 */         request, response);
/* 281 */       return;
/*     */     }
/*     */ 
/* 286 */     Cookie cookieIP = new Cookie("ip", null);
/* 287 */     cookieIP.setMaxAge(-1);
/* 288 */     response.addCookie(cookieIP);
/* 289 */     Cookie cookieBasIp = new Cookie("basip", null);
/* 290 */     cookieBasIp.setMaxAge(-1);
/* 291 */     response.addCookie(cookieBasIp);
/* 292 */     Cookie cookiePwd = new Cookie("password", null);
/* 293 */     cookiePwd.setMaxAge(-1);
/* 294 */     response.addCookie(cookiePwd);
/*     */ 
/* 296 */     ipMap.getInstance().getIpmap().remove(ip);
/*     */ 
/* 298 */     session.setAttribute("api", "ruckus");
/*     */ 
/* 300 */     if (Tools.Do()) {
/*     */       try {
/* 302 */         if (stringUtils.isNotBlank(webMod)) {
/* 303 */           String ids = webMod.replace("/", "");
/* 304 */           Long id = Long.valueOf(ids);
/* 305 */           Portalweb web = webService.getPortalwebByKey(id);
/* 306 */           Long advID = Long.valueOf(0L);
/* 307 */           if (web != null) {
/* 308 */             advID = web.getAdv();
/* 309 */             Advadv adv = advadvService.getAdvadvByKey(advID);
/* 310 */             if (adv != null) {
/* 311 */               int state = adv.getState().intValue();
/* 312 */               if (state == 1) {
/* 313 */                 Date startDate = adv.getShowDate();
/* 314 */                 Date endDate = adv.getEndDate();
/* 315 */                 Date nowDate = new Date();
/* 316 */                 if (((startDate == null) || 
/* 317 */                   (nowDate.getTime() >= startDate.getTime())) && (
/* 318 */                   (endDate == null) || 
/* 319 */                   (endDate.getTime() >= nowDate.getTime()))) {
/* 320 */                   request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=1").forward(
/* 321 */                     request, response);
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
/* 332 */     request.getRequestDispatcher("/" + webMod + "APIauth.jsp").forward(
/* 333 */       request, response);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.ruckus.RuckusController
 * JD-Core Version:    0.6.2
 */