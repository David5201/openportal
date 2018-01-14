/*      */ package com.leeson.portal.core.controller;
/*      */ 
/*      */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.api.AccountAPIRequest;
/*      */ import com.leeson.core.bean.Advadv;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalaccountmacs;
/*      */ import com.leeson.core.bean.Portalap;
/*      */ import com.leeson.core.bean.Portalautologin;
/*      */ import com.leeson.core.bean.Portalbas;
/*      */ import com.leeson.core.bean.Portalbasauth;
/*      */ import com.leeson.core.bean.Portalip;
/*      */ import com.leeson.core.bean.Portallinkrecord;
/*      */ import com.leeson.core.bean.Portallogrecord;
/*      */ import com.leeson.core.bean.Portalonlinelimit;
/*      */ import com.leeson.core.bean.Portalssid;
/*      */ import com.leeson.core.bean.Portaltimeweb;
/*      */ import com.leeson.core.bean.Portalurlparameter;
/*      */ import com.leeson.core.bean.Portalweb;
/*      */ import com.leeson.core.controller.AjaxInterFaceController;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalaccountmacsQuery;
/*      */ import com.leeson.core.query.PortalapQuery;
/*      */ import com.leeson.core.query.PortalbasQuery;
/*      */ import com.leeson.core.query.PortalbasauthQuery;
/*      */ import com.leeson.core.query.PortalipQuery;
/*      */ import com.leeson.core.query.PortalssidQuery;
/*      */ import com.leeson.core.query.PortaltimewebQuery;
/*      */ import com.leeson.core.service.AdvadvService;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalaccountmacsService;
/*      */ import com.leeson.core.service.PortalapService;
/*      */ import com.leeson.core.service.PortalautologinService;
/*      */ import com.leeson.core.service.PortalbasService;
/*      */ import com.leeson.core.service.PortalbasauthService;
/*      */ import com.leeson.core.service.PortalipService;
/*      */ import com.leeson.core.service.PortallinkrecordService;
/*      */ import com.leeson.core.service.PortallogrecordService;
/*      */ import com.leeson.core.service.PortalonlinelimitService;
/*      */ import com.leeson.core.service.PortalssidService;
/*      */ import com.leeson.core.service.PortaltimewebService;
/*      */ import com.leeson.core.service.PortalurlparameterService;
/*      */ import com.leeson.core.service.PortalwebService;
/*      */ import com.leeson.core.utils.IPv4Util;
/*      */ import com.leeson.core.utils.Kick;
/*      */ import com.leeson.portal.core.controller.utils.BASE64;
/*      */ import com.leeson.portal.core.model.AccountAPIMap;
/*      */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*      */ import com.leeson.portal.core.model.AutoLoginMap;
/*      */ import com.leeson.portal.core.model.LateAuthMap;
/*      */ import com.leeson.portal.core.model.MacLimitMap;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.portal.core.model.UserLimitMap;
/*      */ import com.leeson.portal.core.model.iKuaiMacIpMap;
/*      */ import com.leeson.portal.core.model.ipMacMap;
/*      */ import com.leeson.portal.core.model.ipMap;
/*      */ import com.leeson.portal.core.model.isDo;
/*      */ import com.leeson.portal.core.service.InterfaceControl;
/*      */ import com.leeson.portal.core.service.action.unifi.UniFiMethod;
/*      */ import com.leeson.portal.core.service.utils.PortalUtil;
/*      */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*      */ import com.leeson.portal.core.utils.SpringContextHelper;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.URLDecoder;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.servlet.RequestDispatcher;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServlet;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.lang.StringEscapeUtils;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class IndexAction extends HttpServlet
/*      */ {
/*      */   private static final long serialVersionUID = -1966047929923869408L;
/*   97 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*      */ 
/*   99 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*      */ 
/*  101 */   private static Logger logger = Logger.getLogger(IndexAction.class);
/*      */ 
/*  103 */   private static PortalaccountService accountService = (PortalaccountService)
/*  104 */     SpringContextHelper.getBean("portalaccountServiceImpl");
/*      */ 
/*  105 */   private static PortalaccountmacsService macsService = (PortalaccountmacsService)
/*  106 */     SpringContextHelper.getBean("portalaccountmacsServiceImpl");
/*      */ 
/*  107 */   private static PortallinkrecordService linkRecordService = (PortallinkrecordService)
/*  108 */     SpringContextHelper.getBean("portallinkrecordServiceImpl");
/*      */ 
/*  109 */   private static PortallogrecordService logrecordService = (PortallogrecordService)
/*  110 */     SpringContextHelper.getBean("portallogrecordServiceImpl");
/*      */ 
/*  111 */   private static PortalbasService basService = (PortalbasService)
/*  112 */     SpringContextHelper.getBean("portalbasServiceImpl");
/*      */ 
/*  113 */   private static PortalurlparameterService urlparametersService = (PortalurlparameterService)
/*  114 */     SpringContextHelper.getBean("portalurlparameterServiceImpl");
/*      */ 
/*  115 */   private static PortalbasauthService basauthService = (PortalbasauthService)
/*  116 */     SpringContextHelper.getBean("portalbasauthServiceImpl");
/*      */ 
/*  117 */   private static PortalonlinelimitService onlinelimitService = (PortalonlinelimitService)
/*  118 */     SpringContextHelper.getBean("portalonlinelimitServiceImpl");
/*      */ 
/*  119 */   private static PortalwebService webService = (PortalwebService)
/*  120 */     SpringContextHelper.getBean("portalwebServiceImpl");
/*      */ 
/*  121 */   private static PortalautologinService autologinService = (PortalautologinService)
/*  122 */     SpringContextHelper.getBean("portalautologinServiceImpl");
/*      */ 
/*  123 */   private static PortalapService apService = (PortalapService)
/*  124 */     SpringContextHelper.getBean("portalapServiceImpl");
/*      */ 
/*  125 */   private static PortalssidService ssidService = (PortalssidService)
/*  126 */     SpringContextHelper.getBean("portalssidServiceImpl");
/*      */ 
/*  127 */   private static PortalipService ipService = (PortalipService)
/*  128 */     SpringContextHelper.getBean("portalipServiceImpl");
/*      */ 
/*  129 */   private static ConfigService configService = (ConfigService)
/*  130 */     SpringContextHelper.getBean("configServiceImpl");
/*      */ 
/*  131 */   private static PortaltimewebService portaltimewebService = (PortaltimewebService)
/*  132 */     SpringContextHelper.getBean("portaltimewebServiceImpl");
/*      */ 
/*  133 */   private static AdvadvService advadvService = (AdvadvService)
/*  134 */     SpringContextHelper.getBean("advadvServiceImpl");
/*      */ 
/*      */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*      */     throws ServletException, IOException
/*      */   {
/*  139 */     response.setContentType("text/html;charset=utf-8");
/*  140 */     request.setCharacterEncoding("utf-8");
/*      */ 
/*  142 */     HttpSession session = request.getSession();
/*      */ 
/*  145 */     String ikenc = request.getParameter("ikenc");
/*  146 */     String refer = request.getParameter("refer");
/*      */ 
/*  148 */     String ikcs = "";
/*  149 */     String ikweb = (String)session.getAttribute("ikweb");
/*      */ 
/*  151 */     if ((stringUtils.isNotBlank(ikenc)) && (stringUtils.isNotBlank(refer)))
/*      */       try {
/*  153 */         ikcs = BASE64.decryptBASE64(ikenc);
/*  154 */         ikweb = BASE64.decryptBASE64(refer);
/*      */       }
/*      */       catch (Exception localException1) {
/*      */       }
/*  158 */     String ikbasip = "";
/*  159 */     String ikip = "";
/*  160 */     String ssid = "";
/*  161 */     String nasname = "";
/*  162 */     String apmac = "";
/*  163 */     String ikmac = (String)session.getAttribute("ikmac");
/*      */ 
/*  165 */     if (stringUtils.isNotBlank(ikcs))
/*      */     {
/*  169 */       String[] cs = ikcs.split("&");
/*  170 */       for (String c : cs) {
/*  171 */         if (c.startsWith("nasname=")) {
/*  172 */           nasname = c.replace("nasname=", "");
/*      */         }
/*  174 */         if (c.startsWith("basip=")) {
/*  175 */           ikbasip = c.replace("basip=", "").trim();
/*      */         }
/*  177 */         if (c.startsWith("user_ip=")) {
/*  178 */           ikip = c.replace("user_ip=", "");
/*      */         }
/*  180 */         if (c.startsWith("mac=")) {
/*  181 */           ikmac = c.replace("mac=", "");
/*      */         }
/*  183 */         if (c.startsWith("apmac=")) {
/*  184 */           apmac = c.replace("apmac=", "");
/*      */         }
/*  186 */         if (c.startsWith("ssid=")) {
/*  187 */           ssid = c.replace("ssid=", "");
/*  188 */           ssid = URLDecoder.decode(ssid, "utf-8");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  195 */     Cookie[] cookies = request.getCookies();
/*  196 */     String cip = "";
/*  197 */     String cbasip = "";
/*  198 */     String cpassword = "";
/*  199 */     String cmac = "";
/*  200 */     String cSite = "";
/*  201 */     if (cookies != null) {
/*  202 */       for (int i = 0; i < cookies.length; i++) {
/*  203 */         if (cookies[i].getName().equals("ip"))
/*  204 */           cip = cookies[i].getValue();
/*  205 */         if (cookies[i].getName().equals("basip"))
/*  206 */           cbasip = cookies[i].getValue();
/*  207 */         if (cookies[i].getName().equals("password"))
/*  208 */           cpassword = cookies[i].getValue();
/*  209 */         if (cookies[i].getName().equals("mac"))
/*  210 */           cmac = cookies[i].getValue();
/*  211 */         if (cookies[i].getName().equals("site")) {
/*  212 */           cSite = cookies[i].getValue();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  217 */     String ip = "";
/*  218 */     String basip = "";
/*      */ 
/*  220 */     String pip = request.getParameter(urlparametersService
/*  221 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getUserip());
/*  222 */     String pmac = request.getParameter(urlparametersService
/*  223 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getUsermac());
/*  224 */     String pnasname = request.getParameter(urlparametersService
/*  225 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getBasname());
/*  226 */     String purl = request.getParameter(urlparametersService
/*  227 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getUrl());
/*  228 */     String pbasip = request.getParameter(urlparametersService
/*  229 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getBasip());
/*  230 */     String pssid = request.getParameter(urlparametersService
/*  231 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getSsid());
/*  232 */     String papmac = request.getParameter(urlparametersService
/*  233 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getApmac());
/*      */ 
/*  237 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  238 */     if (stringUtils.isNotBlank(pnasname)) {
/*  239 */       PortalbasQuery bq = new PortalbasQuery();
/*  240 */       bq.setBasname(pnasname);
/*  241 */       bq.setBasnameLike(false);
/*  242 */       List bs = basService.getPortalbasList(bq);
/*  243 */       if (bs.size() > 0) {
/*  244 */         basConfig = (Portalbas)bs.get(0);
/*      */       }
/*      */     }
/*  247 */     if (stringUtils.isNotBlank(nasname)) {
/*  248 */       PortalbasQuery bq = new PortalbasQuery();
/*  249 */       bq.setBasname(nasname);
/*  250 */       bq.setBasnameLike(false);
/*  251 */       List bs = basService.getPortalbasList(bq);
/*  252 */       if (bs.size() > 0) {
/*  253 */         basConfig = (Portalbas)bs.get(0);
/*      */       }
/*      */     }
/*      */ 
/*  257 */     basip = ikbasip;
/*  258 */     if (stringUtils.isBlank(basip)) {
/*  259 */       basip = pbasip;
/*      */     }
/*  261 */     if ((stringUtils.isBlank(basip)) && 
/*  262 */       (stringUtils.isNotBlank(pnasname))) {
/*  263 */       PortalbasQuery bq = new PortalbasQuery();
/*  264 */       bq.setBasname(pnasname);
/*  265 */       bq.setBasnameLike(false);
/*  266 */       List bs = basService.getPortalbasList(bq);
/*  267 */       if (bs.size() > 0) {
/*  268 */         basip = ((Portalbas)bs.get(0)).getBasIp();
/*      */       }
/*      */     }
/*      */ 
/*  272 */     if ((stringUtils.isBlank(basip)) && 
/*  273 */       (stringUtils.isNotBlank(nasname))) {
/*  274 */       PortalbasQuery bq = new PortalbasQuery();
/*  275 */       bq.setBasname(nasname);
/*  276 */       bq.setBasnameLike(false);
/*  277 */       List bs = basService.getPortalbasList(bq);
/*  278 */       if (bs.size() > 0) {
/*  279 */         basip = ((Portalbas)bs.get(0)).getBasIp();
/*      */       }
/*      */     }
/*      */ 
/*  283 */     if (stringUtils.isBlank(basip)) {
/*  284 */       basip = (String)session.getAttribute("basip");
/*      */     }
/*  286 */     if (stringUtils.isBlank(basip)) {
/*  287 */       basip = cbasip;
/*      */     }
/*  289 */     if (stringUtils.isBlank(basip)) {
/*  290 */       if ("0".equals(basConfig.getIsOut())) {
/*  291 */         basip = basConfig.getBasIp();
/*      */       }
/*      */       else {
/*  294 */         basip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */       }
/*      */     }
/*      */ 
/*  298 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/*      */ 
/*  300 */     if (basConfigFind != null) {
/*  301 */       if (stringUtils.isNotBlank(basConfigFind.getBasIp()))
/*  302 */         basConfig = basConfigFind;
/*      */     }
/*      */     else {
/*  305 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/*  309 */     if ((basConfig.getBas().equals("1")) || 
/*  310 */       (basConfig.getBas().equals("0")))
/*      */     {
/*  312 */       ip = pip;
/*  313 */       if (stringUtils.isBlank(ip)) {
/*  314 */         ip = (String)session.getAttribute("ip");
/*      */       }
/*  316 */       if (stringUtils.isBlank(ip)) {
/*  317 */         ip = cip;
/*      */       }
/*  319 */       if (stringUtils.isBlank(ip)) {
/*  320 */         if ("0".equals(basConfig.getIsOut()))
/*      */         {
/*  322 */           ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/*  324 */         else ip = "0.0.0.0";
/*      */ 
/*      */       }
/*      */ 
/*  328 */       if (stringUtils.isNotBlank(pmac)) {
/*  329 */         ikmac = pmac;
/*      */       }
/*  331 */       if (stringUtils.isBlank(ikmac)) {
/*  332 */         ikmac = cmac;
/*      */       }
/*  334 */       if (stringUtils.isNotBlank(purl)) {
/*  335 */         purl = new String(purl.getBytes("iso-8859-1"), "utf-8");
/*  336 */         ikweb = purl;
/*      */       }
/*      */ 
/*  339 */       if (stringUtils.isNotBlank(pssid)) {
/*  340 */         pssid = new String(pssid.getBytes("iso-8859-1"), "utf-8");
/*  341 */         ssid = pssid;
/*      */       }
/*  343 */       if (stringUtils.isBlank(ssid)) {
/*  344 */         ssid = (String)session.getAttribute("ssid");
/*      */       }
/*  346 */       if (stringUtils.isBlank(ssid)) {
/*  347 */         ssid = pnasname;
/*      */       }
/*      */ 
/*  350 */       if (stringUtils.isNotBlank(papmac)) {
/*  351 */         apmac = papmac;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  358 */     if ((basConfig.getBas().equals("2")) || 
/*  359 */       (basConfig.getBas().equals("4"))) {
/*  360 */       basip = ikbasip;
/*  361 */       if ((stringUtils.isBlank(basip)) && 
/*  362 */         (stringUtils.isNotBlank(nasname))) {
/*  363 */         PortalbasQuery bq = new PortalbasQuery();
/*  364 */         bq.setBasname(nasname);
/*  365 */         bq.setBasnameLike(false);
/*  366 */         List bs = basService.getPortalbasList(bq);
/*  367 */         if (bs.size() > 0) {
/*  368 */           basip = ((Portalbas)bs.get(0)).getBasIp();
/*      */         }
/*      */       }
/*      */ 
/*  372 */       if (stringUtils.isBlank(basip)) {
/*  373 */         basip = (String)session.getAttribute("basip");
/*      */       }
/*  375 */       if (stringUtils.isBlank(basip)) {
/*  376 */         basip = cbasip;
/*      */       }
/*  378 */       if (stringUtils.isBlank(basip)) {
/*  379 */         if ("0".equals(basConfig.getIsOut())) {
/*  380 */           basip = basConfig.getBasIp();
/*      */         }
/*      */         else {
/*  383 */           basip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/*      */       }
/*      */ 
/*  387 */       if (stringUtils.isBlank(ikmac)) {
/*  388 */         ikmac = cmac;
/*      */       }
/*  390 */       ikmac = PortalUtil.MacFormat(ikmac);
/*      */ 
/*  392 */       ip = ikip;
/*  393 */       if (stringUtils.isBlank(ip)) {
/*  394 */         ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/*      */       }
/*  396 */       if (stringUtils.isBlank(ip)) {
/*  397 */         if ("0".equals(basConfig.getIsOut()))
/*      */         {
/*  399 */           ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/*  401 */         else ip = "0.0.0.0";
/*      */ 
/*      */       }
/*      */ 
/*  405 */       if (stringUtils.isBlank(ssid)) {
/*  406 */         ssid = (String)session.getAttribute("ssid");
/*      */       }
/*  408 */       if (stringUtils.isBlank(ssid)) {
/*  409 */         ssid = nasname;
/*      */       }
/*      */ 
/*  412 */       if (stringUtils.isNotBlank(ikmac)) {
/*  413 */         ikmac = PortalUtil.MacFormat(ikmac);
/*      */ 
/*  415 */         iKuaiMacIpMap.getInstance().getMacIpMap().put(ikmac, ip);
/*  416 */         Cookie cookieMac = new Cookie("mac", ikmac);
/*  417 */         cookieMac.setMaxAge(86400);
/*  418 */         response.addCookie(cookieMac);
/*  419 */         session.setAttribute("ikmac", ikmac);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  425 */     if (basConfig.getBas().equals("3")) {
/*  426 */       basip = basConfig.getBasIp();
/*      */ 
/*  428 */       if (stringUtils.isBlank(ikmac)) {
/*  429 */         ikmac = cmac;
/*      */       }
/*      */ 
/*  433 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*  434 */       ip = ikmac;
/*      */ 
/*  436 */       if (stringUtils.isBlank(ssid)) {
/*  437 */         ssid = (String)session.getAttribute("ssid");
/*      */       }
/*      */ 
/*  440 */       String site = (String)session.getAttribute("site");
/*  441 */       if (stringUtils.isBlank(site)) {
/*  442 */         site = cSite;
/*      */       }
/*  444 */       if (stringUtils.isBlank(site)) {
/*  445 */         site = "default";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  450 */     basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/*  451 */     if (basConfigFind != null) {
/*  452 */       if (stringUtils.isNotBlank(basConfigFind.getBasIp()))
/*  453 */         basConfig = basConfigFind;
/*      */     }
/*      */     else {
/*  456 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/*  459 */     if (stringUtils.isBlank(ssid)) {
/*  460 */       ssid = (String)session.getAttribute("ssid");
/*      */     }
/*  462 */     if (stringUtils.isBlank(ssid)) {
/*  463 */       ssid = nasname;
/*      */     }
/*  465 */     if (stringUtils.isNotBlank(ikmac)) {
/*  466 */       ikmac = PortalUtil.MacFormat(ikmac);
/*      */ 
/*  468 */       Cookie cookieMac = new Cookie("mac", ikmac);
/*  469 */       cookieMac.setMaxAge(86400);
/*  470 */       response.addCookie(cookieMac);
/*  471 */       session.setAttribute("ikmac", ikmac);
/*      */     }
/*      */ 
/*  474 */     if (stringUtils.isBlank(apmac)) {
/*  475 */       apmac = (String)session.getAttribute("apmac");
/*      */     }
/*  477 */     if (stringUtils.isNotBlank(apmac)) {
/*  478 */       session.setAttribute("apmac", apmac);
/*      */     }
/*      */ 
/*  481 */     if (stringUtils.isNotBlank(ssid)) {
/*  482 */       ssid = URLDecoder.decode(ssid);
/*  483 */       ssid = StringEscapeUtils.unescapeHtml(ssid);
/*  484 */       session.setAttribute("ssid", ssid);
/*      */     }
/*  486 */     if (stringUtils.isNotBlank(ikweb)) {
/*  487 */       ikweb = URLDecoder.decode(ikweb);
/*  488 */       ikweb = StringEscapeUtils.unescapeHtml(ikweb);
/*  489 */       if (!ikweb.startsWith("http")) {
/*  490 */         ikweb = "http://" + ikweb;
/*      */       }
/*      */     }
/*      */ 
/*  494 */     String webMod = getWebMod(ssid, apmac, ip, basConfig.getWeb());
/*  495 */     session.setAttribute("web", webMod);
/*      */ 
/*  497 */     String userAgent = request.getHeader("user-agent");
/*  498 */     boolean isComputer = false;
/*  499 */     String agent = "";
/*  500 */     if (stringUtils.isNotBlank(userAgent)) {
/*  501 */       if (userAgent.contains("Windows")) {
/*  502 */         isComputer = true;
/*  503 */         agent = "Windows";
/*  504 */       } else if (userAgent.contains("Macintosh")) {
/*  505 */         isComputer = true;
/*  506 */         agent = "MacOS";
/*      */       }
/*  508 */       else if (userAgent.contains("Linux")) {
/*  509 */         isComputer = false;
/*  510 */         agent = "Android";
/*  511 */       } else if (userAgent.contains("Android")) {
/*  512 */         isComputer = false;
/*  513 */         agent = "Android";
/*  514 */       } else if (userAgent.contains("iPhone")) {
/*  515 */         isComputer = false;
/*  516 */         agent = "IOS";
/*  517 */       } else if (userAgent.contains("iPad")) {
/*  518 */         isComputer = false;
/*  519 */         agent = "IOS";
/*  520 */       } else if (userAgent.contains("iPod")) {
/*  521 */         isComputer = false;
/*  522 */         agent = "IOS";
/*      */       }
/*      */     }
/*      */ 
/*  526 */     session.setAttribute("agent", agent);
/*  527 */     if (!"1".equals(basConfig.getIsComputer()))
/*      */     {
/*  530 */       if (isComputer) {
/*  531 */         session.setAttribute("web", "");
/*  532 */         request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
/*  533 */         request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(
/*  534 */           request, response);
/*  535 */         return;
/*      */       }
/*      */     }
/*      */ 
/*  539 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/*  540 */       ip + ":" + basip);
/*  541 */     if (isLogin) {
/*  542 */       if (basConfig.getIsNtf().intValue() == 1) {
/*  543 */         String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
/*  544 */           ip + ":" + basip);
/*  545 */         GetMacTimeLimitMethod(loginInfo, ikmac, session);
/*  546 */         String username = loginInfo[0];
/*  547 */         session.setAttribute("username", username);
/*  548 */         session.setAttribute("password", cpassword);
/*  549 */         session.setAttribute("ip", ip);
/*  550 */         session.setAttribute("basip", basip);
/*  551 */         session.setAttribute("ssid", ssid);
/*      */ 
/*  553 */         session.setAttribute("ikweb", ikweb);
/*      */ 
/*  563 */         if (stringUtils.isNotBlank(ikweb))
/*  564 */           request.getRequestDispatcher(
/*  565 */             "/" + webMod + "ok.jsp?l=" + ikweb).forward(
/*  566 */             request, response);
/*      */         else {
/*  568 */           request.getRequestDispatcher("/" + webMod + "ok.jsp")
/*  569 */             .forward(request, response);
/*      */         }
/*  571 */         return;
/*      */       }
/*  573 */       Kick.kickUserSyn(ip + ":" + basip);
/*      */     }
/*      */ 
/*  579 */     if (onlineMap.getOnlineUserMap().size() >= 
/*  579 */       Integer.valueOf(
/*  580 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  580 */       .get("core"))[1]).intValue())
/*      */     {
/*  581 */       session.setAttribute("ip", ip);
/*  582 */       session.setAttribute("basip", basip);
/*  583 */       session.setAttribute("ssid", ssid);
/*  584 */       request.setAttribute("msg", "已超过允许最大用户数限制！！");
/*  585 */       request.getRequestDispatcher("/" + webMod + "OL.jsp").forward(
/*  586 */         request, response);
/*  587 */       return;
/*      */     }
/*      */ 
/*  590 */     if ((Do()) && 
/*  591 */       (!ipMap.getInstance().getIpmap().containsKey(ip))) {
/*  592 */       ipMap.getInstance().getIpmap().put(ip, Integer.valueOf(1));
/*      */ 
/*  595 */       if (stringUtils.isNotBlank(ikmac)) {
/*  596 */         Integer lateAuthState = basConfig.getLateAuth();
/*  597 */         Long lateAuthTime = basConfig.getLateAuthTime();
/*  598 */         if ((lateAuthState != null) && (lateAuthTime != null) && 
/*  599 */           (1 == lateAuthState.intValue()) && (0L < lateAuthTime.longValue()))
/*      */         {
/*  601 */           if (!LateAuthMap.getInstance().getLateAuthMap()
/*  601 */             .containsKey(ikmac))
/*      */           {
/*  603 */             String authUser = basConfig.getBasUser();
/*  604 */             String authPwd = basConfig.getBasPwd();
/*  605 */             String authUrl = ikweb;
/*  606 */             if (stringUtils.isBlank(authUrl)) {
/*  607 */               authUrl = "/" + webMod + "ok.jsp";
/*      */             }
/*      */ 
/*  610 */             boolean info = false;
/*  611 */             if ((basConfig.getBas().equals("1")) || 
/*  613 */               (basConfig.getBas().equals(
/*  613 */               "0"))) {
/*  614 */               info = InterfaceControl.Method("PORTAL_LOGIN", 
/*  615 */                 authUser, authPwd, ip, basip, ikmac).booleanValue();
/*      */             }
/*      */ 
/*  617 */             if ((basConfig.getBas().equals("2")) || 
/*  619 */               (basConfig.getBas()
/*  619 */               .equals("4"))) {
/*  620 */               info = InterfaceControl.Method("PORTAL_LOGIN", 
/*  621 */                 authUser, authPwd, ip, basip, ikmac).booleanValue();
/*      */             }
/*      */ 
/*  623 */             if (basConfig.getBas().equals("3")) {
/*  624 */               String site = (String)session
/*  625 */                 .getAttribute("site");
/*  626 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  627 */               aq.setLoginName(authUser);
/*  628 */               aq.setLoginNameLike(false);
/*  629 */               List accs = accountService
/*  630 */                 .getPortalaccountList(aq);
/*  631 */               int accTime = 1440;
/*  632 */               if (accs.size() == 1) {
/*  633 */                 long accTimeLong = ((Portalaccount)accs.get(0)).getTime().longValue() / 60000L;
/*  634 */                 if (accTimeLong > 0L) {
/*  635 */                   accTime = (int)accTimeLong;
/*      */                 }
/*      */               }
/*  638 */               info = UniFiMethod.sendAuthorization(ikmac, 
/*  639 */                 accTime, site, basip);
/*      */             }
/*  641 */             if (info) {
/*  642 */               session.setAttribute("username", "延迟认证");
/*  643 */               session.setAttribute("ip", ip);
/*  644 */               session.setAttribute("basip", basip);
/*      */ 
/*  646 */               Cookie cookieIp = new Cookie("ip", ip);
/*  647 */               cookieIp.setMaxAge(86400);
/*  648 */               response.addCookie(cookieIp);
/*      */ 
/*  650 */               Cookie cookieBasIp = new Cookie("basip", basip);
/*  651 */               cookieBasIp.setMaxAge(86400);
/*  652 */               response.addCookie(cookieBasIp);
/*      */ 
/*  655 */               String[] loginInfo = new String[16];
/*  656 */               loginInfo[0] = "延迟认证";
/*  657 */               Date now = new Date();
/*  658 */               loginInfo[3] = ThreadLocalDateUtil.format(now);
/*  659 */               loginInfo[4] = ikmac;
/*  660 */               loginInfo[6] = "8";
/*  661 */               loginInfo[7] = "0";
/*  662 */               loginInfo[8] = "0";
/*      */ 
/*  664 */               loginInfo[9] = ip;
/*  665 */               loginInfo[10] = basip;
/*  666 */               loginInfo[11] = 
/*  667 */                 ((Portalbas)config.getConfigMap()
/*  667 */                 .get(basip)).getBasname();
/*  668 */               loginInfo[12] = ssid;
/*  669 */               loginInfo[13] = apmac;
/*  670 */               loginInfo[14] = "no";
/*  671 */               loginInfo[15] = agent;
/*      */ 
/*  673 */               onlineMap.getOnlineUserMap().put(
/*  674 */                 ip + ":" + basip, loginInfo);
/*      */ 
/*  677 */               Portallogrecord logRecord = new Portallogrecord();
/*  678 */               logRecord.setInfo("IP: " + ip + ":" + basip + 
/*  679 */                 " mac: " + ikmac + " 延迟认证登录成功!");
/*  680 */               logRecord.setRecDate(now);
/*  681 */               logrecordService.addPortallogrecord(logRecord);
/*  682 */               if (stringUtils.isNotBlank(ssid)) {
/*      */                 try {
/*  684 */                   PortalssidQuery apq = new PortalssidQuery();
/*  685 */                   apq.setSsid(ssid);
/*  686 */                   apq.setSsidLike(false);
/*  687 */                   List aps = ssidService
/*  688 */                     .getPortalssidList(apq);
/*  689 */                   if ((aps != null) && (aps.size() > 0)) {
/*  690 */                     Portalssid ap = (Portalssid)aps.get(0);
/*  691 */                     ap.setBasip(basip);
/*  692 */                     long count = ap.getCount().longValue() + 1L;
/*  693 */                     ap.setCount(Long.valueOf(count));
/*  694 */                     ssidService
/*  695 */                       .updatePortalssidByKey(ap);
/*      */                   } else {
/*  697 */                     Portalssid ap = new Portalssid();
/*  698 */                     ap.setSsid(ssid);
/*  699 */                     ap.setBasip(basip);
/*  700 */                     ap.setCount(Long.valueOf(1L));
/*  701 */                     ssidService.addPortalssid(ap);
/*      */                   }
/*      */                 } catch (Exception e) {
/*  704 */                   logger.error("==============ERROR Start=============");
/*  705 */                   logger.error(e);
/*  706 */                   logger.error("ERROR INFO ", e);
/*  707 */                   logger.error("==============ERROR End=============");
/*      */                 }
/*      */               }
/*  710 */               if (stringUtils.isNotBlank(apmac)) {
/*      */                 try {
/*  712 */                   PortalapQuery apq = new PortalapQuery();
/*  713 */                   apq.setMac(apmac);
/*  714 */                   apq.setMacLike(false);
/*  715 */                   List aps = apService
/*  716 */                     .getPortalapList(apq);
/*  717 */                   if ((aps != null) && (aps.size() > 0)) {
/*  718 */                     Portalap ap = (Portalap)aps.get(0);
/*  719 */                     ap.setBasip(basip);
/*  720 */                     long count = ap.getCount().longValue() + 1L;
/*  721 */                     ap.setCount(Long.valueOf(count));
/*  722 */                     apService.updatePortalapByKey(ap);
/*      */                   } else {
/*  724 */                     Portalap ap = new Portalap();
/*  725 */                     ap.setMac(apmac);
/*  726 */                     ap.setBasip(basip);
/*  727 */                     ap.setCount(Long.valueOf(1L));
/*  728 */                     apService.addPortalap(ap);
/*      */                   }
/*      */                 } catch (Exception e) {
/*  731 */                   logger.error("==============ERROR Start=============");
/*  732 */                   logger.error(e);
/*  733 */                   logger.error("ERROR INFO ", e);
/*  734 */                   logger.error("==============ERROR End=============");
/*      */                 }
/*      */               }
/*      */ 
/*  738 */               LateAuthMap.getInstance().getLateAuthMap()
/*  739 */                 .put(ikmac, new Date());
/*      */ 
/*  741 */               AjaxInterFaceController.SangforLogin(ip, 
/*  742 */                 "延迟认证", ikmac, apmac, basip);
/*      */ 
/*  744 */               ipMap.getInstance().getIpmap().remove(ip);
/*      */ 
/*  747 */               ikweb = 
/*  748 */                 ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
/*  748 */                 .getCoreConfigMap().get("core"))[0];
/*      */ 
/*  750 */               if (stringUtils.isNotBlank(ikweb)) {
/*  751 */                 response.sendRedirect(ikweb);
/*  752 */                 return;
/*      */               }
/*  754 */               response.sendRedirect(authUrl);
/*      */               return;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       String authPwd;
/*      */       String password="";
/*  764 */       if (stringUtils.isNotBlank(ikmac)) {
/*  765 */         String[] macTimeInfo = 
/*  766 */           (String[])AutoLoginMacMap.getInstance()
/*  766 */           .getAutoLoginMacMap().get(ikmac);
/*  767 */         String[] userinfo = 
/*  768 */           (String[])AutoLoginMap.getInstance()
/*  768 */           .getAutoLoginMap().get(ikmac);
/*  769 */         if ((macTimeInfo != null) && (userinfo != null)) {
/*      */           try {
/*  771 */             Long id = Long.valueOf(macTimeInfo[2]);
/*  772 */             if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
/*  773 */               String authUser = userinfo[0];
/*  774 */               authPwd = userinfo[1];
/*  775 */               String username = userinfo[2];
/*  776 */               System.out.println(authUser + " " + authPwd + 
/*  777 */                 " " + username);
/*  778 */               Portalautologin autologin = autologinService
/*  779 */                 .getPortalautologinByKey(id);
/*  780 */               if ((autologin != null) && 
/*  781 */                 (autologin.getState().intValue() == 1)) {
/*  782 */                 Long timepermit = autologin.getTime();
/*  783 */                 String loginTimeString = macTimeInfo[0];
/*  784 */                 Long oldcostTime = 
/*  785 */                   Long.valueOf(macTimeInfo[1]);
/*  786 */                 Long costTime = oldcostTime;
/*      */ 
/*  788 */                 if (stringUtils.isNotBlank(loginTimeString)) {
/*  789 */                   Date loginTime = 
/*  790 */                     ThreadLocalDateUtil.parse(loginTimeString);
/*  791 */                   String nowString = 
/*  792 */                     ThreadLocalDateUtil.format(new Date());
/*  793 */                   Date nowTime = 
/*  794 */                     ThreadLocalDateUtil.parse(nowString);
/*  795 */                   costTime = Long.valueOf(nowTime.getTime() - 
/*  796 */                     loginTime.getTime() + 
/*  797 */                     oldcostTime.longValue());
/*      */                 }
/*  799 */                 if ((costTime.longValue() < timepermit.longValue()) || 
/*  800 */                   (timepermit.longValue() <= 0L)) {
/*  801 */                   Long userId = Long.valueOf(0L);
/*  802 */                   String userState = null;
/*  803 */                   password = authPwd;
/*  804 */                   boolean CheckAccount = false;
/*      */                   Object o;
/*  805 */                   if (3L == id.longValue())
/*      */                   {
/*  807 */                     PortalaccountQuery accq = new PortalaccountQuery();
/*  808 */                     accq.setLoginNameLike(false);
/*  809 */                     accq.setLoginName(username);
/*      */ 
/*  811 */                     if (!"1".equals(basConfig
/*  811 */                       .getIsPortalCheck())) {
/*  812 */                       accq.setPasswordLike(false);
/*  813 */                       accq.setPassword(password);
/*      */                     }
/*  815 */                     List accs = accountService
/*  816 */                       .getPortalaccountList(accq);
/*  817 */                     if ((accs != null) && 
/*  818 */                       (accs.size() == 1)) {
/*  819 */                       Portalaccount acc = 
/*  820 */                         (Portalaccount)accs
/*  820 */                         .get(0);
/*  821 */                       if (acc != null) {
/*  822 */                         userId = acc.getId();
/*  823 */                         Date userDate = acc
/*  824 */                           .getDate();
/*  825 */                         Long userTime = acc
/*  826 */                           .getTime();
/*  827 */                         Long octets = acc
/*  828 */                           .getOctets();
/*  829 */                         if (octets == null) {
/*  830 */                           octets = Long.valueOf(0L);
/*      */                         }
/*  832 */                         userState = acc
/*  833 */                           .getState();
/*  834 */                         password = acc
/*  835 */                           .getPassword();
/*      */ 
/*  842 */                         if (checkTimeOut(
/*  838 */                           userState, 
/*  839 */                           userId, 
/*  840 */                           userDate, 
/*  841 */                           userTime, 
/*  842 */                           octets)) {
/*  843 */                           CheckAccount = true;
/*      */                         }
/*      */ 
/*  848 */                         if (!"1"
/*  847 */                           .equals(basConfig
/*  848 */                           .getIsPortalCheck()))
/*      */                         {
/*  850 */                           if (!password
/*  850 */                             .equals(authPwd)) {
/*  851 */                             CheckAccount = false;
/*      */                           }
/*      */ 
/*      */                         }
/*      */ 
/*  856 */                         if (CheckAccount)
/*      */                         {
/*  858 */                           if ("1".equals(basConfig
/*  858 */                             .getIsPortalCheck())) {
/*  859 */                             Integer limitCount = acc
/*  860 */                               .getMaclimitcount();
/*  861 */                             if ((limitCount != null) && 
/*  862 */                               (limitCount.intValue() > 0)) {
/*  863 */                               int count = 0;
/*  864 */                               Iterator iterator = 
/*  865 */                                 OnlineMap.getInstance()
/*  866 */                                 .getOnlineUserMap()
/*  867 */                                 .keySet()
/*  868 */                                 .iterator();
/*      */ 
/*  870 */                               while (iterator
/*  870 */                                 .hasNext()) {
/*  871 */                                 o = iterator
/*  872 */                                   .next();
/*  873 */                                 String t = o
/*  874 */                                   .toString();
/*  875 */                                 String[] loginInfo = 
/*  878 */                                   (String[])OnlineMap.getInstance()
/*  877 */                                   .getOnlineUserMap()
/*  878 */                                   .get(t);
/*  879 */                                 String haveUsername = loginInfo[0];
/*      */ 
/*  881 */                                 if (username
/*  881 */                                   .equals(haveUsername)) {
/*  882 */                                   count++;
/*      */                                 }
/*      */                               }
/*  885 */                               if (count >= limitCount.intValue()) {
/*  886 */                                 CheckAccount = false;
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                         }
/*      */ 
/*      */                       }
/*      */ 
/*      */                     }
/*      */ 
/*      */                   }
/*  897 */                   else if (4L == id.longValue()) {
/*  898 */                     String accountAPI_Url = 
/*  901 */                       (String)AccountAPIMap.getInstance()
/*  900 */                       .getAccountAPIMap()
/*  901 */                       .get("autourl");
/*  902 */                     String accountAPI_State = 
/*  905 */                       (String)AccountAPIMap.getInstance()
/*  904 */                       .getAccountAPIMap()
/*  905 */                       .get("autostate");
/*      */ 
/*  907 */                     if (stringUtils.isNotBlank(accountAPI_Url))
/*      */                     {
/*  909 */                       if ((stringUtils.isNotBlank(accountAPI_State)) && 
/*  910 */                         ("1".equals(accountAPI_State))) {
/*  911 */                         boolean apiResult = 
/*  912 */                           AccountAPIRequest.send(accountAPI_Url, 
/*  913 */                           authUser, 
/*  914 */                           authPwd, 
/*  915 */                           ip, 
/*  916 */                           ikmac, 
/*  917 */                           agent);
/*  918 */                         if (apiResult) {
/*  919 */                           CheckAccount = true;
/*  920 */                           break label4786;
/*  921 */                         }CheckAccount = false;
/*      */ 
/*  923 */                         break label4786; } 
/*  924 */                     }CheckAccount = true;
/*      */                   }
/*      */                   else
/*      */                   {
/*  928 */                     CheckAccount = true;
/*      */                   }
/*      */ 
/*  932 */                   label4786: if (CheckAccount) {
/*  933 */                     Boolean info = Boolean.valueOf(false);
/*      */ 
/*  938 */                     if (((Portalbas)config
/*  935 */                       .getConfigMap()
/*  936 */                       .get(basip))
/*  937 */                       .getBas()
/*  938 */                       .equals("3")) {
/*  939 */                       String site = (String)session
/*  940 */                         .getAttribute("site");
/*  941 */                       PortalaccountQuery aq = new PortalaccountQuery();
/*  942 */                       aq.setLoginName(authUser);
/*  943 */                       aq.setLoginNameLike(false);
/*  944 */                       List accs = accountService
/*  945 */                         .getPortalaccountList(aq);
/*  946 */                       int accTime = 1440;
/*  947 */                       if (accs.size() == 1) {
/*  948 */                         long accTimeLong = 
/*  949 */                           ((Portalaccount)accs
/*  949 */                           .get(0))
/*  950 */                           .getTime().longValue() / 60000L;
/*  951 */                         if (accTimeLong > 0L) {
/*  952 */                           accTime = (int)accTimeLong;
/*      */                         }
/*      */                       }
/*  955 */                       info = 
/*  956 */                         Boolean.valueOf(UniFiMethod.sendAuthorization(
/*  957 */                         ikmac, 
/*  958 */                         accTime, 
/*  959 */                         site, basip));
/*      */                     } else {
/*  961 */                       info = 
/*  962 */                         InterfaceControl.Method("PORTAL_LOGIN", 
/*  963 */                         authUser, 
/*  964 */                         authPwd, 
/*  965 */                         ip, basip, 
/*  966 */                         ikmac);
/*      */                     }
/*      */ 
/*  969 */                     if (info.booleanValue())
/*      */                     {
/*  972 */                       if (stringUtils.isNotBlank(ssid)) {
/*      */                         try {
/*  974 */                           PortalssidQuery apq = new PortalssidQuery();
/*  975 */                           apq.setSsid(ssid);
/*  976 */                           apq.setSsidLike(false);
/*  977 */                           List aps = ssidService
/*  978 */                             .getPortalssidList(apq);
/*  979 */                           if ((aps != null) && 
/*  980 */                             (aps.size() > 0)) {
/*  981 */                             Portalssid ap = 
/*  982 */                               (Portalssid)aps
/*  982 */                               .get(0);
/*  983 */                             ap.setBasip(basip);
/*  984 */                             long count = ap
/*  985 */                               .getCount().longValue() + 1L;
/*  986 */                             ap.setCount(Long.valueOf(count));
/*  987 */                             ssidService
/*  988 */                               .updatePortalssidByKey(ap);
/*      */                           } else {
/*  990 */                             Portalssid ap = new Portalssid();
/*  991 */                             ap.setSsid(ssid);
/*  992 */                             ap.setBasip(basip);
/*  993 */                             ap.setCount(Long.valueOf(1L));
/*  994 */                             ssidService
/*  995 */                               .addPortalssid(ap);
/*      */                           }
/*      */                         } catch (Exception e) {
/*  998 */                           logger.error("==============ERROR Start=============");
/*  999 */                           logger.error(e);
/* 1000 */                           logger.error(
/* 1001 */                             "ERROR INFO ", 
/* 1002 */                             e);
/* 1003 */                           logger.error("==============ERROR End=============");
/*      */                         }
/*      */ 
/*      */                       }
/*      */ 
/* 1008 */                       if (stringUtils.isNotBlank(apmac)) {
/*      */                         try {
/* 1010 */                           PortalapQuery apq = new PortalapQuery();
/* 1011 */                           apq.setMac(apmac);
/* 1012 */                           apq.setMacLike(false);
/* 1013 */                           List aps = apService
/* 1014 */                             .getPortalapList(apq);
/* 1015 */                           if ((aps != null) && 
/* 1016 */                             (aps.size() > 0)) {
/* 1017 */                             Portalap ap = 
/* 1018 */                               (Portalap)aps
/* 1018 */                               .get(0);
/* 1019 */                             ap.setBasip(basip);
/* 1020 */                             long count = ap
/* 1021 */                               .getCount().longValue() + 1L;
/* 1022 */                             ap.setCount(Long.valueOf(count));
/* 1023 */                             apService
/* 1024 */                               .updatePortalapByKey(ap);
/*      */                           } else {
/* 1026 */                             Portalap ap = new Portalap();
/* 1027 */                             ap.setMac(apmac);
/* 1028 */                             ap.setBasip(basip);
/* 1029 */                             ap.setCount(Long.valueOf(1L));
/* 1030 */                             apService
/* 1031 */                               .addPortalap(ap);
/*      */                           }
/*      */                         } catch (Exception e) {
/* 1034 */                           logger.error("==============ERROR Start=============");
/* 1035 */                           logger.error(e);
/* 1036 */                           logger.error(
/* 1037 */                             "ERROR INFO ", 
/* 1038 */                             e);
/* 1039 */                           logger.error("==============ERROR End=============");
/*      */                         }
/*      */ 
/*      */                       }
/*      */ 
/* 1044 */                       if ((stringUtils.isBlank(ikmac)) || 
/* 1046 */                         ("error"
/* 1046 */                         .equals(ikmac))) {
/* 1047 */                         ikmac = 
/* 1050 */                           (String)ipMacMap.getInstance()
/* 1049 */                           .getIpMacMap()
/* 1050 */                           .get(ip + ":" + 
/* 1051 */                           basip);
/*      */                       }
/*      */ 
/* 1055 */                       UpdateMacTimeLimitMethod(
/* 1056 */                         ikmac, id, session, 
/* 1057 */                         username);
/* 1058 */                       AutoLoginPutMethod(ikmac, 
/* 1059 */                         id);
/*      */ 
/* 1061 */                       session.setAttribute(
/* 1062 */                         "username", 
/* 1063 */                         username);
/* 1064 */                       session.setAttribute(
/* 1065 */                         "password", 
/* 1066 */                         password);
/* 1067 */                       session.setAttribute("ip", 
/* 1068 */                         ip);
/* 1069 */                       session.setAttribute(
/* 1070 */                         "basip", basip);
/*      */ 
/* 1072 */                       String[] loginInfo = new String[16];
/* 1073 */                       loginInfo[0] = username;
/* 1074 */                       Date now = new Date();
/* 1075 */                       loginInfo[3] = 
/* 1076 */                         ThreadLocalDateUtil.format(now);
/* 1077 */                       loginInfo[4] = ikmac;
/* 1078 */                       if ((3L == id.longValue()) || (4L == id.longValue())) {
/* 1079 */                         loginInfo[5] = "ok";
/*      */                       }
/* 1081 */                       if (3L == id.longValue())
/*      */                       {
/* 1083 */                         if ((stringUtils.isNotBlank(userState)) && 
/* 1084 */                           (userId != null)) {
/* 1085 */                           Long recordId = doLinkRecord(
/* 1086 */                             username, ip, 
/* 1087 */                             basip, 
/* 1088 */                             userState, 
/* 1089 */                             userId, ikmac);
/* 1090 */                           loginInfo[1] = 
/* 1091 */                             String.valueOf(userId);
/* 1092 */                           loginInfo[2] = 
/* 1093 */                             String.valueOf(recordId);
/* 1094 */                           session.setAttribute(
/* 1095 */                             "password", 
/* 1096 */                             password);
/*      */                         }
/*      */                       }
/* 1099 */                       String tid = "0";
/* 1100 */                       if (1L == id.longValue())
/* 1101 */                         tid = "4";
/* 1102 */                       else if (2L == id.longValue())
/* 1103 */                         tid = "0";
/* 1104 */                       else if (3L == id.longValue())
/* 1105 */                         tid = "1";
/* 1106 */                       else if (4L == id.longValue())
/* 1107 */                         tid = "2";
/* 1108 */                       else if (5L == id.longValue())
/* 1109 */                         tid = "3";
/* 1110 */                       else if (6L == id.longValue())
/* 1111 */                         tid = "5";
/* 1112 */                       else if (7L == id.longValue())
/* 1113 */                         tid = "6";
/* 1114 */                       else if (8L == id.longValue()) {
/* 1115 */                         tid = "7";
/*      */                       }
/* 1117 */                       loginInfo[6] = tid;
/* 1118 */                       loginInfo[7] = "0";
/* 1119 */                       loginInfo[8] = "0";
/*      */ 
/* 1121 */                       loginInfo[9] = ip;
/* 1122 */                       loginInfo[10] = basip;
/* 1123 */                       loginInfo[11] = 
/* 1125 */                         ((Portalbas)config
/* 1124 */                         .getConfigMap()
/* 1125 */                         .get(basip))
/* 1126 */                         .getBasname();
/* 1127 */                       loginInfo[12] = ssid;
/* 1128 */                       loginInfo[13] = apmac;
/* 1129 */                       loginInfo[14] = "yes";
/* 1130 */                       loginInfo[15] = agent;
/*      */ 
/* 1133 */                       onlineMap
/* 1134 */                         .getOnlineUserMap()
/* 1135 */                         .put(ip + ":" + 
/* 1136 */                         basip, 
/* 1137 */                         loginInfo);
/* 1138 */                       Portallogrecord logRecord = new Portallogrecord();
/*      */ 
/* 1140 */                       logRecord.setInfo("IP: " + 
/* 1141 */                         ip + ":" + basip + 
/* 1142 */                         " mac: " + ikmac + 
/* 1143 */                         " 用户: " + 
/* 1144 */                         username + 
/* 1145 */                         " ,无感知登录成功!");
/* 1146 */                       logRecord.setRecDate(now);
/* 1147 */                       logrecordService
/* 1148 */                         .addPortallogrecord(logRecord);
/*      */ 
/* 1150 */                       String authUrl = ikweb;
/* 1151 */                       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1152 */                       bsq.setBasip(basip);
/* 1153 */                       List<Portalbasauth> basauths = basauthService
/* 1154 */                         .getPortalbasauthList(bsq);
/* 1155 */                       if (basauths.size() > 0) {
/* 1156 */                         for (Portalbasauth ba : basauths) {
/* 1157 */                           if (ba.getType() == 
/* 1158 */                             Integer.valueOf(tid)) {
/* 1159 */                             authUrl = ba
/* 1160 */                               .getUrl();
/*      */ 
/* 1162 */                             if (!stringUtils.isBlank(authUrl)) break;
/* 1163 */                             authUrl = ikweb;
/*      */ 
/* 1165 */                             break;
/*      */                           }
/*      */                         }
/*      */ 
/*      */                       }
/*      */ 
/* 1171 */                       AjaxInterFaceController.SangforLogin(ip, 
/* 1172 */                         username, 
/* 1173 */                         ikmac, 
/* 1174 */                         apmac, 
/* 1175 */                         basip);
/*      */ 
/* 1177 */                       ipMap.getInstance()
/* 1178 */                         .getIpmap()
/* 1179 */                         .remove(ip);
/*      */ 
/* 1182 */                       if (stringUtils.isNotBlank(authUrl)) {
/* 1183 */                         response.sendRedirect(authUrl);
/* 1184 */                         return;
/*      */                       }
/* 1186 */                       authUrl = "/" + webMod + 
/* 1187 */                         "ok.jsp";
/* 1188 */                       response.sendRedirect(authUrl);
/* 1189 */                       return;
/*      */                     }
/*      */ 
/* 1194 */                     AutoLoginMacMap.getInstance()
/* 1195 */                       .getAutoLoginMacMap()
/* 1196 */                       .remove(ikmac);
/* 1197 */                     AutoLoginMap.getInstance()
/* 1198 */                       .getAutoLoginMap()
/* 1199 */                       .remove(ikmac);
/*      */                   }
/*      */                   else {
/* 1202 */                     AutoLoginMacMap.getInstance()
/* 1203 */                       .getAutoLoginMacMap()
/* 1204 */                       .remove(ikmac);
/* 1205 */                     AutoLoginMap.getInstance()
/* 1206 */                       .getAutoLoginMap()
/* 1207 */                       .remove(ikmac);
/*      */                   }
/*      */                 }
/*      */                 else {
/* 1211 */                   AutoLoginMacMap.getInstance()
/* 1212 */                     .getAutoLoginMacMap()
/* 1213 */                     .remove(ikmac);
/* 1214 */                   AutoLoginMap.getInstance()
/* 1215 */                     .getAutoLoginMap()
/* 1216 */                     .remove(ikmac);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 1222 */             logger.error("==============ERROR Start=============");
/* 1223 */             logger.error(e);
/* 1224 */             logger.error("ERROR INFO ", e);
/* 1225 */             logger.error("==============ERROR End=============");
/* 1226 */             AutoLoginMacMap.getInstance().getAutoLoginMacMap()
/* 1227 */               .remove(ikmac);
/* 1228 */             AutoLoginMap.getInstance().getAutoLoginMap()
/* 1229 */               .remove(ikmac);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1235 */       if ((1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) && 
/* 1236 */         (1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))))
/*      */       {
/* 1238 */         Boolean info = Boolean.valueOf(false);
/*      */ 
/* 1240 */         String userMac = ikmac;
/*      */ 
/* 1242 */         if (stringUtils.isNotBlank(userMac))
/*      */         {
/* 1244 */           List<Portalaccountmacs> macs = macsService
/* 1245 */             .getPortalaccountmacsList(new PortalaccountmacsQuery());
/* 1246 */           label8524: label8675: for (Portalaccountmacs mac : macs) {
/* 1247 */             if (mac.getMac().equals(userMac)) {
/* 1248 */               Portalaccount acc = accountService
/* 1249 */                 .getPortalaccountByKey(mac
/* 1250 */                 .getAccountId());
/* 1251 */               if (acc == null) break;
/* 1252 */               int state = acc.getAutologin().intValue();
/* 1253 */               if (state != 1)
/*      */                 break;
/* 1255 */               if (basConfig.getAuthInterface()
/* 1255 */                 .contains("1"))
/*      */               {
/* 1257 */                 PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1258 */                 bsq.setBasip(basip);
/* 1259 */                 String authUser = basConfig
/* 1260 */                   .getBasUser();
/* 1261 */                  authPwd = basConfig
/* 1262 */                   .getBasPwd();
/* 1263 */                 String authUrl = ikweb;
/* 1264 */                 if (stringUtils.isBlank(authUrl)) {
/* 1265 */                   authUrl = "/" + webMod + 
/* 1266 */                     "ok.jsp";
/*      */                 }
/* 1268 */               List<Portalbasauth>  basauths = basauthService
/* 1269 */                   .getPortalbasauthList(bsq);
/* 1270 */                 if (basauths.size() > 0) {
/* 1271 */                   for (Portalbasauth ba : basauths) {
/* 1272 */                     if (ba.getType().intValue() == 1) {
/* 1273 */                       authUser = ba
/* 1274 */                         .getUsername();
/* 1275 */                       authPwd = ba
/* 1276 */                         .getPassword();
/*      */ 
/* 1278 */                       if (stringUtils.isBlank(ikweb)) {
/* 1279 */                         authUrl = ba
/* 1280 */                           .getUrl();
/*      */                       }
/*      */ 
/* 1283 */                       if ((stringUtils.isBlank(authUser)) || 
/* 1285 */                         (stringUtils.isBlank(authPwd))) {
/* 1286 */                         authUser = basConfig
/* 1287 */                           .getBasUser();
/* 1288 */                         authPwd = basConfig
/* 1289 */                           .getBasPwd();
/*      */                       }
/*      */ 
/* 1292 */                       if (!stringUtils.isBlank(authUrl)) break;
/* 1293 */                       authUrl = "/" + 
/* 1294 */                         webMod + 
/* 1295 */                         "ok.jsp";
/*      */ 
/* 1297 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */ 
/* 1302 */                 if (acc == null) break;
/* 1303 */                 Long userId = acc.getId();
/* 1304 */                 Date userDate = acc.getDate();
/* 1305 */                 Long userTime = acc.getTime();
/* 1306 */                 Long octets = acc.getOctets();
/* 1307 */                 if (octets == null) {
/* 1308 */                   octets = Long.valueOf(0L);
/*      */                 }
/* 1310 */                 String username = acc
/* 1311 */                   .getLoginName();
/* 1312 */                 String userState = acc
/* 1313 */                   .getState();
/* 1314 */                  password = acc
/* 1315 */                   .getPassword();
/*      */ 
/* 1318 */                 if (!"1".equals(basConfig
/* 1318 */                   .getIsPortalCheck())) {
/* 1319 */                   authUser = username;
/* 1320 */                   authPwd = password;
/*      */                 }
/*      */ 
/* 1325 */                 if (!checkTimeOut(userState, 
/* 1324 */                   userId, userDate, 
/* 1325 */                   userTime, octets)) {
/*      */                   break;
/*      */                 }
/* 1328 */                 boolean checkOnlineLimit = true;
/*      */ 
/* 1330 */                 if ("1".equals(basConfig
/* 1330 */                   .getIsPortalCheck())) {
/* 1331 */                   Integer limitCount = acc
/* 1332 */                     .getMaclimitcount();
/* 1333 */                   if ((limitCount != null) && 
/* 1334 */                     (limitCount.intValue() > 0)) {
/* 1335 */                     int countOnline = 0;
/* 1336 */                     Iterator iterator = 
/* 1337 */                       OnlineMap.getInstance()
/* 1338 */                       .getOnlineUserMap()
/* 1339 */                       .keySet()
/* 1340 */                       .iterator();
/*      */ 
/* 1342 */                     while (iterator
/* 1342 */                       .hasNext()) {
/* 1343 */                       Object o = iterator
/* 1344 */                         .next();
/* 1345 */                       String t = o
/* 1346 */                         .toString();
/* 1347 */                       String[] loginInfo = 
/* 1350 */                         (String[])OnlineMap.getInstance()
/* 1349 */                         .getOnlineUserMap()
/* 1350 */                         .get(t);
/* 1351 */                       String haveUsername = loginInfo[0];
/*      */ 
/* 1353 */                       if (username
/* 1353 */                         .equals(haveUsername)) {
/* 1354 */                         countOnline++;
/*      */                       }
/*      */                     }
/* 1357 */                     if (countOnline >= limitCount.intValue()) {
/* 1358 */                       checkOnlineLimit = false;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */ 
/* 1363 */                 if (!checkOnlineLimit) {
/*      */                   break;
/*      */                 }
/* 1366 */                 if ((basConfig
/* 1365 */                   .getBas()
/* 1366 */                   .equals("1")) || 
/* 1369 */                   (basConfig
/* 1368 */                   .getBas()
/* 1369 */                   .equals("0"))) {
/* 1370 */                   info = 
/* 1371 */                     InterfaceControl.Method("PORTAL_LOGIN", 
/* 1372 */                     authUser, 
/* 1373 */                     authPwd, 
/* 1374 */                     ip, 
/* 1375 */                     basip, 
/* 1376 */                     ikmac);
/*      */                 }
/*      */ 
/* 1401 */                 if ((basConfig
/* 1400 */                   .getBas()
/* 1401 */                   .equals("2")) || 
/* 1404 */                   (basConfig
/* 1403 */                   .getBas()
/* 1404 */                   .equals("4"))) {
/* 1405 */                   info = 
/* 1406 */                     InterfaceControl.Method("PORTAL_LOGIN", 
/* 1407 */                     authUser, 
/* 1408 */                     authPwd, 
/* 1409 */                     ip, 
/* 1410 */                     basip, 
/* 1411 */                     ikmac);
/*      */                 }
/*      */ 
/* 1415 */                 if (basConfig
/* 1414 */                   .getBas()
/* 1415 */                   .equals("3")) {
/* 1416 */                   String site = (String)session
/* 1417 */                     .getAttribute("site");
/* 1418 */                   PortalaccountQuery aq = new PortalaccountQuery();
/* 1419 */                   aq.setLoginName(authUser);
/* 1420 */                   aq.setLoginNameLike(false);
/* 1421 */                   List accs = accountService
/* 1422 */                     .getPortalaccountList(aq);
/* 1423 */                   int accTime = 1440;
/* 1424 */                   if (accs.size() == 1) {
/* 1425 */                     long accTimeLong = 
/* 1426 */                       ((Portalaccount)accs
/* 1426 */                       .get(0))
/* 1427 */                       .getTime().longValue() / 60000L;
/* 1428 */                     if (accTimeLong > 0L) {
/* 1429 */                       accTime = (int)accTimeLong;
/*      */                     }
/*      */                   }
/* 1432 */                   info = 
/* 1433 */                     Boolean.valueOf(UniFiMethod.sendAuthorization(
/* 1434 */                     ikmac, 
/* 1435 */                     accTime, 
/* 1436 */                     site, 
/* 1437 */                     basip));
/*      */                 }
/* 1439 */                 if (!info.booleanValue())
/*      */                   break;
/* 1441 */                 Long recordId = doLinkRecord(
/* 1442 */                   username, 
/* 1443 */                   ip, basip, 
/* 1444 */                   userState, 
/* 1445 */                   userId, 
/* 1446 */                   ikmac);
/*      */ 
/* 1448 */                 session.setAttribute(
/* 1449 */                   "password", 
/* 1450 */                   password);
/* 1451 */                 Cookie cookiePwd = new Cookie(
/* 1452 */                   "password", 
/* 1453 */                   password);
/* 1454 */                 cookiePwd
/* 1455 */                   .setMaxAge(86400);
/* 1456 */                 response.addCookie(cookiePwd);
/* 1457 */                 session.setAttribute(
/* 1458 */                   "username", 
/* 1459 */                   username);
/* 1460 */                 session.setAttribute(
/* 1461 */                   "ip", ip);
/* 1462 */                 session.setAttribute(
/* 1463 */                   "basip", 
/* 1464 */                   basip);
/*      */ 
/* 1466 */                 Cookie cookieIp = new Cookie(
/* 1467 */                   "ip", ip);
/* 1468 */                 cookieIp.setMaxAge(86400);
/* 1469 */                 response.addCookie(cookieIp);
/*      */ 
/* 1471 */                 Cookie cookieBasIp = new Cookie(
/* 1472 */                   "basip", 
/* 1473 */                   basip);
/* 1474 */                 cookieBasIp
/* 1475 */                   .setMaxAge(86400);
/* 1476 */                 response.addCookie(cookieBasIp);
/*      */ 
/* 1479 */                 String[] loginInfo = new String[16];
/* 1480 */                 loginInfo[0] = username;
/* 1481 */                 loginInfo[1] = 
/* 1482 */                   String.valueOf(userId);
/* 1483 */                 loginInfo[2] = 
/* 1484 */                   String.valueOf(recordId);
/*      */ 
/* 1486 */                 Date now = new Date();
/* 1487 */                 loginInfo[3] = 
/* 1488 */                   ThreadLocalDateUtil.format(now);
/* 1489 */                 loginInfo[4] = ikmac;
/* 1490 */                 loginInfo[5] = "ok";
/* 1491 */                 loginInfo[6] = "1";
/* 1492 */                 loginInfo[7] = "0";
/* 1493 */                 loginInfo[8] = "0";
/*      */ 
/* 1495 */                 loginInfo[9] = ip;
/* 1496 */                 loginInfo[10] = basip;
/* 1497 */                 loginInfo[11] = 
/* 1499 */                   ((Portalbas)config
/* 1498 */                   .getConfigMap()
/* 1499 */                   .get(basip))
/* 1500 */                   .getBasname();
/* 1501 */                 loginInfo[12] = ssid;
/* 1502 */                 loginInfo[13] = apmac;
/* 1503 */                 loginInfo[14] = "yes";
/* 1504 */                 loginInfo[15] = agent;
/*      */ 
/* 1507 */                 onlineMap
/* 1508 */                   .getOnlineUserMap()
/* 1509 */                   .put(ip + 
/* 1510 */                   ":" + 
/* 1511 */                   basip, 
/* 1512 */                   loginInfo);
/*      */ 
/* 1515 */                 Portallogrecord logRecord = new Portallogrecord();
/* 1516 */                 logRecord
/* 1517 */                   .setInfo("IP: " + 
/* 1518 */                   ip + 
/* 1519 */                   ":" + 
/* 1520 */                   basip + 
/* 1521 */                   " mac: " + 
/* 1522 */                   ikmac + 
/* 1523 */                   " 系统用户: " + 
/* 1524 */                   username + 
/* 1525 */                   ",无感知登录成功!");
/* 1526 */                 logRecord
/* 1527 */                   .setRecDate(now);
/* 1528 */                 logrecordService
/* 1529 */                   .addPortallogrecord(logRecord);
/*      */ 
/* 1533 */                 UpdateMacTimeLimitMethod(
/* 1534 */                   ikmac, 
/* 1535 */                   Long.valueOf(3L), 
/* 1536 */                   request.getSession(), 
/* 1537 */                   username);
/*      */ 
/* 1540 */                 if (stringUtils.isNotBlank(ssid)) {
/*      */                   try {
/* 1542 */                     PortalssidQuery apq = new PortalssidQuery();
/* 1543 */                     apq.setSsid(ssid);
/* 1544 */                     apq.setSsidLike(false);
/* 1545 */                     List aps = ssidService
/* 1546 */                       .getPortalssidList(apq);
/* 1547 */                     if ((aps != null) && 
/* 1548 */                       (aps.size() > 0)) {
/* 1549 */                       Portalssid ap = 
/* 1550 */                         (Portalssid)aps
/* 1550 */                         .get(0);
/* 1551 */                       ap.setBasip(basip);
/* 1552 */                       long count = ap
/* 1553 */                         .getCount().longValue() + 1L;
/* 1554 */                       ap.setCount(Long.valueOf(count));
/* 1555 */                       ssidService
/* 1556 */                         .updatePortalssidByKey(ap);
/*      */                     } else {
/* 1558 */                       Portalssid ap = new Portalssid();
/* 1559 */                       ap.setSsid(ssid);
/* 1560 */                       ap.setBasip(basip);
/* 1561 */                       ap.setCount(Long.valueOf(1L));
/* 1562 */                       ssidService
/* 1563 */                         .addPortalssid(ap);
/*      */                     }
/*      */                   } catch (Exception e) {
/* 1566 */                     logger.error("==============ERROR Start=============");
/* 1567 */                     logger.error(e);
/* 1568 */                     logger.error(
/* 1569 */                       "ERROR INFO ", 
/* 1570 */                       e);
/* 1571 */                     logger.error("==============ERROR End=============");
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/* 1576 */                 if (stringUtils.isNotBlank(apmac)) {
/*      */                   try {
/* 1578 */                     PortalapQuery apq = new PortalapQuery();
/* 1579 */                     apq.setMac(apmac);
/* 1580 */                     apq.setMacLike(false);
/* 1581 */                     List aps = apService
/* 1582 */                       .getPortalapList(apq);
/* 1583 */                     if ((aps != null) && 
/* 1584 */                       (aps.size() > 0)) {
/* 1585 */                       Portalap ap = 
/* 1586 */                         (Portalap)aps
/* 1586 */                         .get(0);
/* 1587 */                       ap.setBasip(basip);
/* 1588 */                       long count = ap
/* 1589 */                         .getCount().longValue() + 1L;
/* 1590 */                       ap.setCount(Long.valueOf(count));
/* 1591 */                       apService
/* 1592 */                         .updatePortalapByKey(ap);
/*      */                     } else {
/* 1594 */                       Portalap ap = new Portalap();
/* 1595 */                       ap.setMac(apmac);
/* 1596 */                       ap.setBasip(basip);
/* 1597 */                       ap.setCount(Long.valueOf(1L));
/* 1598 */                       apService
/* 1599 */                         .addPortalap(ap);
/*      */                     }
/*      */                   } catch (Exception e) {
/* 1602 */                     logger.error("==============ERROR Start=============");
/* 1603 */                     logger.error(e);
/* 1604 */                     logger.error(
/* 1605 */                       "ERROR INFO ", 
/* 1606 */                       e);
/* 1607 */                     logger.error("==============ERROR End=============");
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/* 1612 */                 AjaxInterFaceController.SangforLogin(
/* 1613 */                   ip, 
/* 1614 */                   username, 
/* 1615 */                   ikmac, 
/* 1616 */                   apmac, 
/* 1617 */                   basip);
/*      */ 
/* 1619 */                 ipMap.getInstance()
/* 1620 */                   .getIpmap()
/* 1621 */                   .remove(ip);
/*      */ 
/* 1624 */                 if (stringUtils.isNotBlank(ikweb)) {
/* 1625 */                   response.sendRedirect(ikweb);
/* 1626 */                   return;
/*      */                 }
/*      */ 
/* 1630 */                 response.sendRedirect(authUrl);
/* 1631 */                 return;
/*      */               }
/*      */ 
/* 1641 */               PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1642 */               bsq.setBasip(basip);
/* 1643 */               String authUrl = ikweb;
/* 1644 */               if (stringUtils.isBlank(authUrl)) {
/* 1645 */                 authUrl = "/" + webMod + 
/* 1646 */                   "ok.jsp";
/*      */               }
/* 1648 */               List<Portalbasauth> basauths = basauthService.getPortalbasauthList(bsq);
/* 1650 */               if (basauths.size() > 0) {
/* 1651 */                 for (Portalbasauth ba : basauths) {
/* 1652 */                   if (ba.getType().intValue() == 2)
/*      */                   {
/* 1654 */                     if (stringUtils.isBlank(ikweb)) {
/* 1655 */                       authUrl = ba
/* 1656 */                         .getUrl();
/*      */                     }
/*      */ 
/* 1659 */                     if (!stringUtils.isBlank(authUrl)) break;
/* 1660 */                     authUrl = "/" + 
/* 1661 */                       webMod + 
/* 1662 */                       "ok.jsp";
/*      */ 
/* 1664 */                     break;
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/* 1669 */               String[] userinfo = null;
/* 1670 */               if ((!stringUtils.isBlank(userMac)) && 
/* 1671 */                 (!"error"
/* 1671 */                 .equals(userMac))) {
/* 1672 */                 userinfo = 
/* 1675 */                   (String[])AutoLoginMap.getInstance()
/* 1674 */                   .getAutoLoginMap()
/* 1675 */                   .get(userMac);
/*      */               }
/* 1677 */               String username = "";
/* 1679 */               String phone = "";	
/* 1680 */               if ((userinfo != null) && 
/* 1681 */                 (userinfo.length == 2)) {
/* 1682 */                 username = userinfo[0];
/* 1683 */                 password = userinfo[1];
/*      */               }
/* 1685 */               if ((userinfo != null) && 
/* 1686 */                 (userinfo.length == 3)) {
/* 1687 */                 username = userinfo[0];
/* 1688 */                 password = userinfo[1];
/* 1689 */                 phone = userinfo[2];
/*      */               }
/*      */ 
/* 1692 */               if (stringUtils.isNotBlank(username))
/*      */               {
/* 1694 */                 if (stringUtils.isNotBlank(password))
/*      */                 {
/* 1696 */                   boolean CheckAccount = false;
/* 1697 */                   String accountAPI_Url = 
/* 1700 */                     (String)AccountAPIMap.getInstance()
/* 1699 */                     .getAccountAPIMap()
/* 1700 */                     .get("autourl");
/* 1701 */                   String accountAPI_State = 
/* 1704 */                     (String)AccountAPIMap.getInstance()
/* 1703 */                     .getAccountAPIMap()
/* 1704 */                     .get("autostate");
/*      */ 
/* 1706 */                   if (stringUtils.isNotBlank(accountAPI_Url))
/*      */                   {
/* 1708 */                     if ((stringUtils.isNotBlank(accountAPI_State)) && 
/* 1709 */                       ("1".equals(accountAPI_State))) {
/* 1710 */                       boolean apiResult = 
/* 1711 */                         AccountAPIRequest.send(accountAPI_Url, username, 
/* 1713 */                         password, 
/* 1714 */                         ip, ikmac, 
/* 1715 */                         agent);
/* 1716 */                       if (apiResult) {
/* 1717 */                         CheckAccount = true;
/* 1718 */                         break label8524;
/* 1719 */                       }CheckAccount = false;
/*      */ 
/* 1721 */                       break label8524; } 
/* 1722 */                   }CheckAccount = true;
/*      */ 
/* 1725 */                   if (!CheckAccount) {
/*      */                     break label8675;
/*      */                   }
/* 1728 */                   if ((basConfig
/* 1727 */                     .getBas()
/* 1728 */                     .equals("1")) || 
/* 1731 */                     (basConfig
/* 1730 */                     .getBas()
/* 1731 */                     .equals("0"))) {
/* 1732 */                     info = 
/* 1733 */                       InterfaceControl.Method("PORTAL_LOGIN", 
/* 1734 */                       username, 
/* 1735 */                       password, 
/* 1736 */                       ip, 
/* 1737 */                       basip, 
/* 1738 */                       ikmac);
/*      */                   }
/*      */ 
/* 1742 */                   if ((basConfig
/* 1741 */                     .getBas()
/* 1742 */                     .equals("2")) || 
/* 1745 */                     (basConfig
/* 1744 */                     .getBas()
/* 1745 */                     .equals("4"))) {
/* 1746 */                     info = 
/* 1747 */                       InterfaceControl.Method("PORTAL_LOGIN", 
/* 1748 */                       username, 
/* 1749 */                       password, 
/* 1750 */                       ip, 
/* 1751 */                       basip, 
/* 1752 */                       ikmac);
/*      */                   }
/*      */ 
/* 1756 */                   if (!basConfig
/* 1755 */                     .getBas()
/* 1756 */                     .equals("3")) break label8675;
/* 1757 */                   String site = (String)session
/* 1758 */                     .getAttribute("site");
/* 1759 */                   info = 
/* 1760 */                     Boolean.valueOf(UniFiMethod.sendAuthorization(
/* 1761 */                     ikmac, 
/* 1762 */                     1440, 
/* 1763 */                     site, 
/* 1764 */                     basip));
/*      */ 
/* 1768 */                   break label8675; } 
/* 1769 */               }info = Boolean.valueOf(false);
/*      */ 
/* 1772 */               if (!info.booleanValue()) {
/*      */                 break;
/*      */               }
/* 1775 */               if (stringUtils.isNotBlank(phone)) {
/* 1776 */                 username = phone;
/*      */               }
/*      */ 
/* 1779 */               session.setAttribute(
/* 1780 */                 "username", username);
/* 1781 */               session.setAttribute(
/* 1782 */                 "password", password);
/* 1783 */               session.setAttribute("ip", ip);
/* 1784 */               session.setAttribute("basip", 
/* 1785 */                 basip);
/*      */ 
/* 1787 */               Cookie cookieIp = new Cookie(
/* 1788 */                 "ip", ip);
/* 1789 */               cookieIp.setMaxAge(86400);
/* 1790 */               response.addCookie(cookieIp);
/*      */ 
/* 1792 */               Cookie cookieBasIp = new Cookie(
/* 1793 */                 "basip", basip);
/* 1794 */               cookieBasIp
/* 1795 */                 .setMaxAge(86400);
/* 1796 */               response.addCookie(cookieBasIp);
/*      */ 
/* 1799 */               String[] loginInfo = new String[16];
/* 1800 */               loginInfo[0] = username;
/*      */ 
/* 1802 */               Date now = new Date();
/* 1803 */               loginInfo[3] = 
/* 1804 */                 ThreadLocalDateUtil.format(now);
/* 1805 */               loginInfo[4] = ikmac;
/* 1806 */               loginInfo[5] = "ok";
/* 1807 */               loginInfo[6] = "2";
/* 1808 */               loginInfo[7] = "0";
/* 1809 */               loginInfo[8] = "0";
/*      */ 
/* 1811 */               loginInfo[9] = ip;
/* 1812 */               loginInfo[10] = basip;
/* 1813 */               loginInfo[11] = 
/* 1815 */                 ((Portalbas)config
/* 1814 */                 .getConfigMap()
/* 1815 */                 .get(basip))
/* 1816 */                 .getBasname();
/* 1817 */               loginInfo[12] = ssid;
/* 1818 */               loginInfo[13] = apmac;
/* 1819 */               loginInfo[14] = "yes";
/* 1820 */               loginInfo[15] = agent;
/*      */ 
/* 1822 */               onlineMap.getOnlineUserMap()
/* 1823 */                 .put(ip + ":" + basip, 
/* 1824 */                 loginInfo);
/*      */ 
/* 1827 */               Portallogrecord logRecord = new Portallogrecord();
/* 1828 */               logRecord.setInfo("IP: " + ip + 
/* 1829 */                 ":" + basip + 
/* 1830 */                 " mac: " + ikmac + 
/* 1831 */                 " Radius用户: " + 
/* 1832 */                 username + 
/* 1833 */                 ",无感知登录成功!");
/* 1834 */               logRecord.setRecDate(now);
/* 1835 */               logrecordService
/* 1836 */                 .addPortallogrecord(logRecord);
/*      */ 
/* 1841 */               UpdateMacTimeLimitMethod(ikmac, 
/* 1842 */                 Long.valueOf(4L), 
/* 1843 */                 request.getSession(), 
/* 1844 */                 username);
/*      */ 
/* 1847 */               if (stringUtils.isNotBlank(ssid)) {
/*      */                 try {
/* 1849 */                   PortalssidQuery apq = new PortalssidQuery();
/* 1850 */                   apq.setSsid(ssid);
/* 1851 */                   apq.setSsidLike(false);
/* 1852 */                   List aps = ssidService
/* 1853 */                     .getPortalssidList(apq);
/* 1854 */                   if ((aps != null) && 
/* 1855 */                     (aps.size() > 0)) {
/* 1856 */                     Portalssid ap = 
/* 1857 */                       (Portalssid)aps
/* 1857 */                       .get(0);
/* 1858 */                     ap.setBasip(basip);
/* 1859 */                     long count = ap
/* 1860 */                       .getCount().longValue() + 1L;
/* 1861 */                     ap.setCount(Long.valueOf(count));
/* 1862 */                     ssidService
/* 1863 */                       .updatePortalssidByKey(ap);
/*      */                   } else {
/* 1865 */                     Portalssid ap = new Portalssid();
/* 1866 */                     ap.setSsid(ssid);
/* 1867 */                     ap.setBasip(basip);
/* 1868 */                     ap.setCount(Long.valueOf(1L));
/* 1869 */                     ssidService
/* 1870 */                       .addPortalssid(ap);
/*      */                   }
/*      */                 } catch (Exception e) {
/* 1873 */                   logger.error("==============ERROR Start=============");
/* 1874 */                   logger.error(e);
/* 1875 */                   logger.error(
/* 1876 */                     "ERROR INFO ", 
/* 1877 */                     e);
/* 1878 */                   logger.error("==============ERROR End=============");
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/* 1883 */               if (stringUtils.isNotBlank(apmac)) {
/*      */                 try {
/* 1885 */                   PortalapQuery apq = new PortalapQuery();
/* 1886 */                   apq.setMac(apmac);
/* 1887 */                   apq.setMacLike(false);
/* 1888 */                   List aps = apService
/* 1889 */                     .getPortalapList(apq);
/* 1890 */                   if ((aps != null) && 
/* 1891 */                     (aps.size() > 0)) {
/* 1892 */                     Portalap ap = 
/* 1893 */                       (Portalap)aps
/* 1893 */                       .get(0);
/* 1894 */                     ap.setBasip(basip);
/* 1895 */                     long count = ap
/* 1896 */                       .getCount().longValue() + 1L;
/* 1897 */                     ap.setCount(Long.valueOf(count));
/* 1898 */                     apService
/* 1899 */                       .updatePortalapByKey(ap);
/*      */                   } else {
/* 1901 */                     Portalap ap = new Portalap();
/* 1902 */                     ap.setMac(apmac);
/* 1903 */                     ap.setBasip(basip);
/* 1904 */                     ap.setCount(Long.valueOf(1L));
/* 1905 */                     apService
/* 1906 */                       .addPortalap(ap);
/*      */                   }
/*      */                 } catch (Exception e) {
/* 1909 */                   logger.error("==============ERROR Start=============");
/* 1910 */                   logger.error(e);
/* 1911 */                   logger.error(
/* 1912 */                     "ERROR INFO ", 
/* 1913 */                     e);
/* 1914 */                   logger.error("==============ERROR End=============");
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/* 1919 */               AjaxInterFaceController.SangforLogin(ip, 
/* 1920 */                 username, 
/* 1921 */                 ikmac, apmac, 
/* 1922 */                 basip);
/*      */ 
/* 1924 */               ipMap.getInstance().getIpmap()
/* 1925 */                 .remove(ip);
/*      */ 
/* 1928 */               if (stringUtils.isNotBlank(ikweb)) {
/* 1929 */                 response.sendRedirect(ikweb);
/* 1930 */                 return;
/*      */               }
/*      */ 
/* 1934 */               response.sendRedirect(authUrl);
/* 1935 */               return;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1949 */       ipMap.getInstance().getIpmap().remove(ip);
/*      */     }
/*      */ 
/* 1953 */     ipMap.getInstance().getIpmap().remove(ip);
/*      */ 
/* 1957 */     Cookie cookieIP = new Cookie("ip", null);
/* 1958 */     cookieIP.setMaxAge(-1);
/* 1959 */     response.addCookie(cookieIP);
/* 1960 */     Cookie cookieBasIp = new Cookie("basip", null);
/* 1961 */     cookieBasIp.setMaxAge(-1);
/* 1962 */     response.addCookie(cookieBasIp);
/* 1963 */     Cookie cookiePwd = new Cookie("password", null);
/* 1964 */     cookiePwd.setMaxAge(-1);
/* 1965 */     response.addCookie(cookiePwd);
/*      */ 
/* 1968 */     if ((basConfig.getBas().equals("1")) || 
/* 1969 */       (basConfig.getBas().equals("0")))
/*      */     {
/* 1971 */       ip = pip;
/* 1972 */       if (stringUtils.isBlank(ip)) {
/* 1973 */         ip = (String)session.getAttribute("ip");
/*      */       }
/* 1975 */       if (stringUtils.isBlank(ip)) {
/* 1976 */         if ("0".equals(basConfig.getIsOut()))
/*      */         {
/* 1978 */           ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/* 1980 */         else ip = "0.0.0.0";
/*      */ 
/*      */       }
/*      */ 
/* 1984 */       basip = pbasip;
/* 1985 */       if ((stringUtils.isBlank(basip)) && 
/* 1986 */         (stringUtils.isNotBlank(pnasname))) {
/* 1987 */         PortalbasQuery bq = new PortalbasQuery();
/* 1988 */         bq.setBasname(pnasname);
/* 1989 */         bq.setBasnameLike(false);
/* 1990 */         List bs = basService.getPortalbasList(bq);
/* 1991 */         if (bs.size() > 0) {
/* 1992 */           basip = ((Portalbas)bs.get(0)).getBasIp();
/*      */         }
/*      */       }
/*      */ 
/* 1996 */       if (stringUtils.isBlank(basip)) {
/* 1997 */         basip = (String)session.getAttribute("basip");
/*      */       }
/* 1999 */       if (stringUtils.isBlank(basip)) {
/* 2000 */         if ("0".equals(basConfig.getIsOut())) {
/* 2001 */           basip = basConfig.getBasIp();
/*      */         }
/*      */         else {
/* 2004 */           basip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2012 */     if ((basConfig.getBas().equals("2")) || 
/* 2013 */       (basConfig.getBas().equals("4"))) {
/* 2014 */       basip = ikbasip;
/* 2015 */       if ((stringUtils.isBlank(basip)) && 
/* 2016 */         (stringUtils.isNotBlank(nasname))) {
/* 2017 */         PortalbasQuery bq = new PortalbasQuery();
/* 2018 */         bq.setBasname(nasname);
/* 2019 */         bq.setBasnameLike(false);
/* 2020 */         List bs = basService.getPortalbasList(bq);
/* 2021 */         if (bs.size() > 0) {
/* 2022 */           basip = ((Portalbas)bs.get(0)).getBasIp();
/*      */         }
/*      */       }
/*      */ 
/* 2026 */       if (stringUtils.isBlank(basip)) {
/* 2027 */         basip = (String)session.getAttribute("basip");
/*      */       }
/* 2029 */       if (stringUtils.isBlank(basip)) {
/* 2030 */         if ("0".equals(basConfig.getIsOut())) {
/* 2031 */           basip = basConfig.getBasIp();
/*      */         }
/*      */         else {
/* 2034 */           basip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/*      */       }
/*      */ 
/* 2038 */       ip = ikip;
/* 2039 */       if (stringUtils.isBlank(ip)) {
/* 2040 */         ip = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
/*      */       }
/* 2042 */       if (stringUtils.isBlank(ip)) {
/* 2043 */         if ("0".equals(basConfig.getIsOut()))
/*      */         {
/* 2045 */           ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */         }
/* 2047 */         else ip = "0.0.0.0";
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2054 */     if (basConfig.getBas().equals("3")) {
/* 2055 */       basip = basConfig.getBasIp();
/*      */ 
/* 2057 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */     }
/*      */ 
/* 2061 */     basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/* 2062 */     if (basConfigFind != null) {
/* 2063 */       if (stringUtils.isNotBlank(basConfigFind.getBasIp()))
/* 2064 */         basConfig = basConfigFind;
/*      */     }
/*      */     else {
/* 2067 */       basip = basConfig.getBasIp();
/*      */     }
/*      */ 
/* 2070 */     session.setAttribute("ip", ip);
/* 2071 */     session.setAttribute("basip", basip);
/* 2072 */     session.setAttribute("ikweb", ikweb);
/* 2073 */     session.setAttribute("ssid", ssid);
/*      */ 
/* 2075 */     String auth = basConfig.getAuthInterface();
/* 2076 */     session.setAttribute("auth", auth);
/*      */ 
/* 2078 */     session.setAttribute("api", "default");
/*      */ 
/* 2080 */     if (Do()) {
/*      */       try {
/* 2082 */         if (stringUtils.isNotBlank(webMod)) {
/* 2083 */           String ids = webMod.replace("/", "");
/* 2084 */           Long id = Long.valueOf(ids);
/* 2085 */           Portalweb web = webService.getPortalwebByKey(id);
/* 2086 */           Long advID = Long.valueOf(0L);
/* 2087 */           if (web != null) {
/* 2088 */             advID = web.getAdv();
/* 2089 */             Advadv adv = advadvService.getAdvadvByKey(advID);
/* 2090 */             if (adv != null) {
/* 2091 */               int state = adv.getState().intValue();
/* 2092 */               if (state == 1) {
/* 2093 */                 Date startDate = adv.getShowDate();
/* 2094 */                 Date endDate = adv.getEndDate();
/* 2095 */                 Date nowDate = new Date();
/* 2096 */                 if (((startDate == null) || 
/* 2098 */                   (nowDate.getTime() >= startDate
/* 2098 */                   .getTime())) && (
/* 2099 */                   (endDate == null) || 
/* 2101 */                   (endDate.getTime() >= nowDate
/* 2101 */                   .getTime()))) {
/* 2102 */                   request.getRequestDispatcher(
/* 2103 */                     "/portal.action?id=" + advID + 
/* 2104 */                     "&auth=0").forward(
/* 2105 */                     request, response);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception localException2)
/*      */       {
/*      */       }
/*      */     }
/* 2116 */     request.getRequestDispatcher("/" + webMod + "auth.jsp").forward(
/* 2117 */       request, response);
/*      */   }
/*      */ 
/*      */   public static String getWebMod(String ssid, String apmac, String ip, Long basConfigWeb)
/*      */   {
/* 2124 */     String webMod = "";
/* 2125 */     Long webID = Long.valueOf(0L);
/* 2126 */     if (Do()) {
/*      */       try {
/*      */         PortaltimewebQuery timewebq;
/*      */         try {
/* 2130 */           Date date = new Date();
/* 2131 */           int weekDay = date.getDay();
/* 2132 */           if (weekDay == 0) {
/* 2133 */             weekDay = 7;
/*      */           }
/* 2135 */           int dateDay = date.getDate();
/* 2136 */           int monthDay = date.getMonth() + 1;
/*      */ 
/* 2138 */           timewebq = new PortaltimewebQuery();
/* 2139 */           timewebq.orderbyPos(true);
/* 2140 */           timewebq.orderbyId(true);
/* 2141 */           List<Portaltimeweb> timewebs = portaltimewebService
/* 2142 */             .getPortaltimewebList(timewebq);
/* 2143 */           if ((timewebs != null) && (timewebs.size() > 0))
/* 2144 */             for (Portaltimeweb timeweb : timewebs) {
/* 2145 */               Date setday = timeweb.getViewdate();
/* 2146 */               int weekday = timeweb.getViewweekday().intValue();
/* 2147 */               int dateday = timeweb.getViewdateday().intValue();
/* 2148 */               int monthday = timeweb.getViewmonth().intValue();
/* 2149 */               if ((setday != null) || (weekday != 0) || (dateday != 0) || 
/* 2150 */                 (monthday != 0))
/*      */               {
/* 2153 */                 if (setday != null) {
/* 2154 */                   long time = date.getTime();
/* 2155 */                   long endTime = setday.getTime() + 86400000L;
/* 2156 */                   if ((setday.getTime() <= time) && (time <= endTime)) {
/* 2157 */                     Portalweb web = webService
/* 2158 */                       .getPortalwebByKey(timeweb.getWeb());
/* 2159 */                     if (web != null) {
/* 2160 */                       webMod = String.valueOf(web.getId()) + 
/* 2161 */                         "/";
/* 2162 */                       webID = web.getId();
/* 2163 */                       break;
/*      */                     }
/*      */                   }
/*      */                 } else {
/* 2167 */                   boolean weekState = false;
/* 2168 */                   boolean dayState = false;
/* 2169 */                   boolean monthState = false;
/* 2170 */                   if (weekday == 0) {
/* 2171 */                     weekState = true;
/*      */                   }
/* 2173 */                   else if (weekDay == weekday) {
/* 2174 */                     weekState = true;
/*      */                   }
/*      */ 
/* 2177 */                   if (dateday == 0) {
/* 2178 */                     dayState = true;
/*      */                   }
/* 2180 */                   else if (dateDay == dateday) {
/* 2181 */                     dayState = true;
/*      */                   }
/*      */ 
/* 2184 */                   if (monthday == 0) {
/* 2185 */                     monthState = true;
/*      */                   }
/* 2187 */                   else if (monthDay == monthday) {
/* 2188 */                     monthState = true;
/*      */                   }
/*      */ 
/* 2191 */                   if ((weekState) && (dayState) && (monthState)) {
/* 2192 */                     Portalweb web = webService
/* 2193 */                       .getPortalwebByKey(timeweb.getWeb());
/* 2194 */                     if (web != null) {
/* 2195 */                       webMod = String.valueOf(web.getId()) + 
/* 2196 */                         "/";
/* 2197 */                       webID = web.getId();
/* 2198 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */         }
/*      */         catch (Exception localException1) {
/*      */         }
/* 2207 */         if ((stringUtils.isBlank(webMod)) && 
/* 2208 */           (stringUtils.isNotBlank(ssid))) {
/* 2209 */           PortalssidQuery ssidq = new PortalssidQuery();
/* 2210 */           ssidq.setSsid(ssid);
/* 2211 */           ssidq.setSsidLike(false);
/* 2212 */           List ssids = ssidService
/* 2213 */             .getPortalssidList(ssidq);
/* 2214 */           if ((ssids != null) && (ssids.size() > 0)) {
/* 2215 */             Portalssid sside = (Portalssid)ssids.get(0);
/* 2216 */             Portalweb web = webService.getPortalwebByKey(sside
/* 2217 */               .getWeb());
/* 2218 */             if (web != null) {
/* 2219 */               webMod = String.valueOf(web.getId()) + "/";
/* 2220 */               webID = web.getId();
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 2225 */         if ((stringUtils.isBlank(webMod)) && 
/* 2226 */           (stringUtils.isNotBlank(apmac))) {
/* 2227 */           PortalapQuery apq = new PortalapQuery();
/* 2228 */           apq.setMac(apmac);
/* 2229 */           apq.setMacLike(false);
/* 2230 */           List aps = apService.getPortalapList(apq);
/* 2231 */           if ((aps != null) && (aps.size() > 0)) {
/* 2232 */             Portalap ap = (Portalap)aps.get(0);
/* 2233 */             Portalweb web = webService.getPortalwebByKey(ap
/* 2234 */               .getWeb());
/* 2235 */             if (web != null) {
/* 2236 */               webMod = String.valueOf(web.getId()) + "/";
/* 2237 */               webID = web.getId();
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 2242 */         if (stringUtils.isBlank(webMod)) {
/* 2243 */           Portalweb web = webService.getPortalwebByKey(basConfigWeb);
/* 2244 */           if (web != null) {
/* 2245 */             webMod = String.valueOf(web.getId()) + "/";
/* 2246 */             webID = web.getId();
/*      */           }
/*      */         }
/* 2249 */         if (stringUtils.isBlank(webMod)) {
/* 2250 */           long ipL = IPv4Util.ipToLong(ip);
/* 2251 */           List<Portalip> iplList = ipService
/* 2252 */             .getPortalipList(new PortalipQuery());
/* 2253 */           for (Portalip portalip : iplList) {
/* 2254 */             long startH = IPv4Util.ipToLong(portalip.getStart());
/* 2255 */             long endH = IPv4Util.ipToLong(portalip.getEnd());
/* 2256 */             if ((ipL >= startH) && (ipL <= endH)) {
/* 2257 */               Portalweb web = webService
/* 2258 */                 .getPortalwebByKey(portalip.getWeb());
/* 2259 */               if (web == null) break;
/* 2260 */               webMod = String.valueOf(web.getId()) + "/";
/* 2261 */               webID = web.getId();
/*      */ 
/* 2263 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/* 2268 */         webMod = "";
/* 2269 */         webID = Long.valueOf(0L);
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 2274 */       if (webID.longValue() != 0L) {
/* 2275 */         Portalweb portalweb = webService.getPortalwebByKey(webID);
/* 2276 */         if (portalweb != null) {
/* 2277 */           portalweb.setCountShow(Long.valueOf(portalweb.getCountShow().longValue() + 1L));
/* 2278 */           webService.updatePortalwebByKey(portalweb);
/*      */         }
/*      */       } else {
/* 2281 */         com.leeson.core.bean.Config config = configService
/* 2282 */           .getConfigByKey(Long.valueOf(1L));
/* 2283 */         if (config != null) {
/* 2284 */           config.setCountShow(Long.valueOf(config.getCountShow().longValue() + 1L));
/* 2285 */           configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException2) {
/*      */     }
/* 2291 */     return webMod;
/*      */   }
/*      */ 
/*      */   private static boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets)
/*      */   {
/* 2303 */     Portalaccount account = accountService.getPortalaccountByKey(userId);
/*      */ 
/* 2305 */     if (userState.equals("0")) {
/* 2306 */       return false;
/*      */     }
/*      */ 
/* 2309 */     if (userState.equals("1")) {
/* 2310 */       return true;
/*      */     }
/*      */ 
/* 2313 */     if (userState.equals("3")) {
/* 2314 */       Date now = new Date();
/* 2315 */       if (userDate.getTime() > now.getTime()) {
/* 2316 */         return true;
/*      */       }
/* 2318 */       account.setState("2");
/* 2319 */       accountService.updatePortalaccountByKey(account);
/* 2320 */       userState = "2";
/*      */     }
/*      */ 
/* 2324 */     if (userState.equals("2")) {
/* 2325 */       if (userTime.longValue() > 0L) {
/* 2326 */         return true;
/*      */       }
/* 2328 */       account.setState("4");
/* 2329 */       accountService.updatePortalaccountByKey(account);
/* 2330 */       userState = "4";
/*      */     }
/*      */ 
/* 2334 */     if (userState.equals("4")) {
/* 2335 */       if (octets.longValue() > 0L) {
/* 2336 */         return true;
/*      */       }
/* 2338 */       account.setState("0");
/* 2339 */       accountService.updatePortalaccountByKey(account);
/* 2340 */       return false;
/*      */     }
/*      */ 
/* 2343 */     return false;
/*      */   }
/*      */ 
/*      */   private static Long doLinkRecord(String username, String ip, String basip, String userState, Long userId, String mac)
/*      */   {
/* 2355 */     Portalbas basconfig = (Portalbas)config.getConfigMap().get(basip);
/* 2356 */     if ((basconfig != null) && 
/* 2357 */       ("1".equals(basconfig.getIsPortalCheck()))) {
/* 2358 */       Portallinkrecord linkRecord = new Portallinkrecord();
/* 2359 */       linkRecord.setStartDate(new Date());
/* 2360 */       linkRecord.setIp(ip);
/* 2361 */       linkRecord.setBasip(basip);
/* 2362 */       linkRecord.setMac(mac);
/* 2363 */       linkRecord.setIns(Long.valueOf(0L));
/* 2364 */       linkRecord.setOuts(Long.valueOf(0L));
/* 2365 */       linkRecord.setOctets(Long.valueOf(0L));
/* 2366 */       linkRecord.setLoginName(username);
/* 2367 */       linkRecord.setState(userState);
/* 2368 */       linkRecord.setUid(userId);
/* 2369 */       linkRecordService.addPortallinkrecord(linkRecord);
/* 2370 */       return linkRecord.getId();
/*      */     }
/*      */ 
/* 2373 */     return null;
/*      */   }
/*      */ 
/*      */   private static int CheckMacTimeLimitMethod(String param, Long id)
/*      */   {
/* 2384 */     Portalonlinelimit onlinelimit = onlinelimitService
/* 2385 */       .getPortalonlinelimitByKey(id);
/* 2386 */     if (onlinelimit.getState().intValue() == 1) {
/* 2387 */       if (onlinelimit.getType().intValue() == 0) {
/* 2388 */         if ((stringUtils.isNotBlank(param)) && (!"error".equals(param))) {
/* 2389 */           String[] TimeInfo = 
/* 2390 */             (String[])MacLimitMap.getInstance()
/* 2390 */             .getMacLimitMap().get(param);
/* 2391 */           if (TimeInfo != null) {
/* 2392 */             Long timepermit = onlinelimit.getTime();
/* 2393 */             if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 2394 */               return 1;
/*      */           }
/*      */         }
/*      */         else {
/* 2398 */           return 2;
/*      */         }
/*      */       }
/* 2401 */       else if (stringUtils.isNotBlank(param)) {
/* 2402 */         String[] TimeInfo = 
/* 2403 */           (String[])UserLimitMap.getInstance()
/* 2403 */           .getUserLimitMap().get(param);
/* 2404 */         if (TimeInfo != null) {
/* 2405 */           Long timepermit = onlinelimit.getTime();
/* 2406 */           if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 2407 */             return 1;
/*      */         }
/*      */       }
/*      */       else {
/* 2411 */         return 2;
/*      */       }
/*      */     }
/*      */ 
/* 2415 */     return 0;
/*      */   }
/*      */ 
/*      */   private static void GetMacTimeLimitMethod(String[] loginInfo, String mac, HttpSession session)
/*      */   {
/* 2425 */     String username = loginInfo[0];
/*      */ 
/* 2428 */     String loginTimeString = loginInfo[3];
/* 2429 */     if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
/* 2430 */       mac = loginInfo[4];
/*      */     }
/* 2432 */     Date nowTime = new Date();
/* 2433 */     Date loginTime = nowTime;
/*      */     try {
/* 2435 */       loginTime = ThreadLocalDateUtil.parse(loginTimeString);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/* 2441 */     int costTime = (int)((nowTime.getTime() - loginTime.getTime()) / 60000L);
/*      */ 
/* 2445 */     int haveTime = (int)(getTime(username).longValue() / 60000L);
/*      */ 
/* 2447 */     int toHaveTime = haveTime;
/*      */ 
/* 2449 */     Long oldcostTime = Long.valueOf(0L);
/* 2450 */     Boolean notLimit = Boolean.valueOf(true);
/* 2451 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 2452 */       String[] macTimeInfo = 
/* 2453 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 2453 */         .get(mac);
/* 2454 */       if (macTimeInfo != null) {
/* 2455 */         Long id = Long.valueOf(macTimeInfo[2]);
/* 2456 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/* 2457 */         Portalonlinelimit onlinelimit = onlinelimitService
/* 2458 */           .getPortalonlinelimitByKey(id);
/* 2459 */         if (onlinelimit != null) {
/* 2460 */           Long timepermit = onlinelimit.getTime();
/* 2461 */           loginTimeString = macTimeInfo[0];
/* 2462 */           if (onlinelimit.getState().intValue() == 1)
/*      */           {
/* 2464 */             if (stringUtils.isNotBlank(loginTimeString))
/*      */               try {
/* 2466 */                 loginTime = 
/* 2467 */                   ThreadLocalDateUtil.parse(loginTimeString);
/*      */               }
/*      */               catch (Exception localException1)
/*      */               {
/*      */               }
/* 2472 */             costTime = (int)((nowTime.getTime() - 
/* 2473 */               loginTime.getTime() + oldcostTime.longValue()) / 60000L);
/* 2474 */             haveTime = (int)(timepermit.longValue() / 60000L);
/* 2475 */             notLimit = Boolean.valueOf(false);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2481 */     int overTime = costTime;
/* 2482 */     if (!notLimit.booleanValue()) {
/* 2483 */       haveTime -= overTime;
/*      */     }
/* 2485 */     if (haveTime > toHaveTime) {
/* 2486 */       haveTime = toHaveTime;
/*      */     }
/* 2488 */     if (haveTime < 0) {
/* 2489 */       haveTime = 0;
/*      */     }
/* 2491 */     if (notLimit.booleanValue()) {
/* 2492 */       overTime = costTime + (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/*      */ 
/* 2495 */     String haveTimeString = String.valueOf(haveTime);
/* 2496 */     String overTimeString = String.valueOf(overTime);
/* 2497 */     session.setAttribute("haveTime", haveTimeString);
/* 2498 */     session.setAttribute("overTime", overTimeString);
/*      */   }
/*      */ 
/*      */   public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, String username)
/*      */   {
/* 2508 */     Portalonlinelimit onlinelimit = onlinelimitService
/* 2509 */       .getPortalonlinelimitByKey(id);
/* 2510 */     Long timepermit = onlinelimit.getTime();
/* 2511 */     Long costTime = Long.valueOf(0L);
/* 2512 */     int haveTime = (int)(getTime(username).longValue() / 60000L);
/* 2513 */     int toHaveTime = haveTime;
/* 2514 */     Long oldcostTime = Long.valueOf(0L);
/* 2515 */     Boolean notLimit = Boolean.valueOf(true);
/* 2516 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 2517 */       String[] macTimeInfo = 
/* 2518 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 2518 */         .get(mac);
/* 2519 */       if (macTimeInfo != null) {
/* 2520 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/*      */       }
/* 2522 */       if ((onlinelimit.getState().intValue() == 1) && 
/* 2523 */         (onlinelimit.getType().intValue() == 0)) {
/* 2524 */         Date now = new Date();
/* 2525 */         String nowString = ThreadLocalDateUtil.format(now);
/* 2526 */         if (macTimeInfo == null) {
/* 2527 */           macTimeInfo = new String[3];
/* 2528 */           macTimeInfo[1] = "0";
/*      */         }
/* 2530 */         macTimeInfo[0] = nowString;
/* 2531 */         macTimeInfo[2] = String.valueOf(id);
/* 2532 */         MacLimitMap.getInstance().getMacLimitMap()
/* 2533 */           .put(mac, macTimeInfo);
/* 2534 */         costTime = oldcostTime;
/* 2535 */         haveTime = (int)(timepermit.longValue() / 60000L);
/* 2536 */         notLimit = Boolean.valueOf(false);
/*      */       }
/*      */     }
/*      */ 
/* 2540 */     int overTime = (int)(costTime.longValue() / 60000L);
/* 2541 */     haveTime -= overTime;
/* 2542 */     if (haveTime > toHaveTime) {
/* 2543 */       haveTime = toHaveTime;
/*      */     }
/* 2545 */     if (haveTime < 0) {
/* 2546 */       haveTime = 0;
/*      */     }
/* 2548 */     if (notLimit.booleanValue()) {
/* 2549 */       overTime += (int)(oldcostTime.longValue() / 60000L);
/*      */     }
/* 2551 */     String haveTimeString = String.valueOf(haveTime);
/* 2552 */     String overTimeString = String.valueOf(overTime);
/* 2553 */     session.setAttribute("haveTime", haveTimeString);
/* 2554 */     session.setAttribute("overTime", overTimeString);
/*      */   }
/*      */ 
/*      */   private static Long getTime(String username)
/*      */   {
/* 2564 */     PortalaccountQuery query = new PortalaccountQuery();
/* 2565 */     query.setLoginName(username);
/* 2566 */     query.setLoginNameLike(false);
/* 2567 */     List as = accountService.getPortalaccountList(query);
/* 2568 */     if ((as != null) && (as.size() > 0)) {
/* 2569 */       Portalaccount a = (Portalaccount)as.get(0);
/* 2570 */       String userState = a.getState();
/* 2571 */       Date userDate = a.getDate();
/* 2572 */       Long userTime = a.getTime();
/*      */ 
/* 2574 */       if (userState.equals("0")) {
/* 2575 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 2578 */       if (userState.equals("1")) {
/* 2579 */         if (userTime.longValue() > 0L) {
/* 2580 */           return userTime;
/*      */         }
/* 2582 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 2586 */       if (userState.equals("3")) {
/* 2587 */         Date now = new Date();
/* 2588 */         if (userDate.getTime() > now.getTime()) {
/* 2589 */           return Long.valueOf(userDate.getTime() - now.getTime());
/*      */         }
/* 2591 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 2595 */       if (userState.equals("2")) {
/* 2596 */         if (userTime.longValue() > 0L) {
/* 2597 */           return userTime;
/*      */         }
/* 2599 */         return Long.valueOf(0L);
/*      */       }
/*      */ 
/* 2603 */       return Long.valueOf(0L);
/*      */     }
/* 2605 */     return Long.valueOf(getHaveTime() * 60000L);
/*      */   }
/*      */ 
/*      */   private static int getHaveTime()
/*      */   {
/*      */     try
/*      */     {
/* 2616 */       Date nowdate = new Date();
/* 2617 */       Calendar calendar = new GregorianCalendar();
/* 2618 */       calendar.setTime(nowdate);
/* 2619 */       calendar.add(5, 1);
/* 2620 */       Date tdate = calendar.getTime();
/* 2621 */       SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/* 2622 */       String tsString = fs.format(tdate);
/* 2623 */       Date t = fs.parse(tsString);
/* 2624 */       return (int)((t.getTime() - nowdate.getTime()) / 60000L); } catch (Exception e) {
/*      */     }
/* 2626 */     return 1440;
/*      */   }
/*      */ 
/*      */   public static boolean Do()
/*      */   {
/* 2631 */     Long isThis = Long.valueOf(new Date().getTime());
/* 2632 */     boolean Do = false;
/* 2633 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 2634 */       Do = true;
/*      */     }
/* 2636 */     return Do;
/*      */   }
/*      */ 
/*      */   public static void AutoLoginPutMethod(String mac, Long id)
/*      */   {
/* 2645 */     Portalautologin autologin = autologinService
/* 2646 */       .getPortalautologinByKey(id);
/* 2647 */     if ((autologin != null) && (autologin.getState().intValue() == 1) && 
/* 2648 */       (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 2649 */       String[] macTimeInfo = 
/* 2650 */         (String[])AutoLoginMacMap.getInstance()
/* 2650 */         .getAutoLoginMacMap().get(mac);
/* 2651 */       Date now = new Date();
/* 2652 */       String nowString = ThreadLocalDateUtil.format(now);
/* 2653 */       if (macTimeInfo == null) {
/* 2654 */         macTimeInfo = new String[3];
/* 2655 */         macTimeInfo[1] = "0";
/*      */       }
/* 2657 */       macTimeInfo[0] = nowString;
/* 2658 */       macTimeInfo[2] = String.valueOf(id);
/* 2659 */       AutoLoginMacMap.getInstance().getAutoLoginMacMap()
/* 2660 */         .put(mac, macTimeInfo);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.IndexAction
 * JD-Core Version:    0.6.2
 */