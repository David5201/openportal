/*     */ package com.leeson.portal.core.controller.wifidog;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Advadv;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalweb;
/*     */ import com.leeson.core.service.AdvadvService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import com.leeson.portal.core.controller.WISPr.utils.Tools;
/*     */ import com.leeson.portal.core.controller.utils.BASE64;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LoginController extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = -2471043149508545912L;
/*  45 */   private static Config config = Config.getInstance();
/*     */ 
/*  47 */   private static Logger logger = Logger.getLogger(LoginController.class);
/*     */ 
/*  49 */   private static AdvadvService advadvService = (AdvadvService)
/*  50 */     SpringContextHelper.getBean("advadvServiceImpl");
/*     */ 
/*  51 */   private static PortalwebService webService = (PortalwebService)
/*  52 */     SpringContextHelper.getBean("portalwebServiceImpl");
/*     */ 
/*     */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  57 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  58 */     HttpSession session = request.getSession();
/*     */ 
/*  73 */     String gw_id = request.getParameter("gw_id");
/*  74 */     String gw_address = request.getParameter("gw_address");
/*  75 */     String gw_port = request.getParameter("gw_port");
/*  76 */     String mac = request.getParameter("mac");
/*  77 */     String url = request.getParameter("url");
/*  78 */     String apmac = request.getParameter("gw_mac");
/*  79 */     String ssid = request.getParameter("ssid");
/*     */ 
/*  81 */     if (stringUtils.isNotBlank(gw_id)) {
/*     */       try {
/*  83 */         Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
/*  84 */         while (iteratorConfig.hasNext()) {
/*  85 */           Object o = iteratorConfig.next();
/*  86 */           String t = o.toString();
/*  87 */           Portalbas bas = (Portalbas)config.getConfigMap().get(t);
/*  88 */           String basName = bas.getBasname();
/*  89 */           if ((stringUtils.isNotBlank(basName)) && 
/*  90 */             (basName.equals(gw_id))) {
/*  91 */             basConfig = bas;
/*  92 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/*  97 */         basConfig = (Portalbas)config.getConfigMap().get("");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 102 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 103 */       logger.info("WifiDog Login : " + request.getRequestURL().toString() + 
/* 104 */         "?" + request.getQueryString());
/*     */     }
/*     */ 
/* 107 */     String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*     */ 
/* 109 */     if (stringUtils.isNotBlank(url)) {
/*     */       try {
/* 111 */         url = BASE64.decryptBASE64(url);
/*     */       } catch (Exception localException1) {
/*     */       }
/* 114 */       url = URLDecoder.decode(url);
/* 115 */       url = StringEscapeUtils.unescapeHtml(url);
/* 116 */       session.setAttribute("url", url);
/*     */     }
/* 118 */     if (stringUtils.isNotBlank(ssid)) {
/*     */       try {
/* 120 */         ssid = BASE64.decryptBASE64(ssid);
/*     */       } catch (Exception localException2) {
/*     */       }
/* 123 */       ssid = URLDecoder.decode(ssid);
/* 124 */       ssid = StringEscapeUtils.unescapeHtml(ssid);
/* 125 */       session.setAttribute("ssid", ssid);
/*     */     }
/*     */ 
/* 128 */     String webMod = Tools.getWebMod(ssid, apmac, ip, basConfig.getWeb());
/* 129 */     session.setAttribute("web", webMod);
/* 130 */     mac = PortalUtil.MacFormat(mac);
/* 131 */     if (OnlineMap.getInstance().getOnlineUserMap().size() >= Integer.valueOf(((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
/* 132 */       session.setAttribute("gw_id", gw_id);
/* 133 */       session.setAttribute("gw_address", gw_address);
/* 134 */       session.setAttribute("gw_port", gw_port);
/* 135 */       session.setAttribute("mac", mac);
/* 136 */       request.setAttribute("msg", "已超过允许最大用户数限制！！");
/* 137 */       session.setAttribute("web", "");
/* 138 */       request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, 
/* 139 */         response);
/* 140 */       return;
/*     */     }
/*     */ 
/* 144 */     if (!"1".equals(basConfig.getIsComputer())) {
/* 145 */       String userAgent = request.getHeader("user-agent");
/* 146 */       System.out.println("user-agent " + userAgent);
/* 147 */       boolean isComputer = false;
/* 148 */       if (stringUtils.isNotBlank(userAgent)) {
/* 149 */         if (userAgent.contains("Windows")) {
/* 150 */           isComputer = true;
/*     */         }
/* 152 */         else if ((userAgent.contains("Android")) || 
/* 153 */           (userAgent.contains("iPhone")) || 
/* 154 */           (userAgent.contains("iPod")) || 
/* 155 */           (userAgent.contains("iPad"))) {
/* 156 */           isComputer = false;
/*     */         }
/*     */       }
/*     */ 
/* 160 */       System.out.println("isComputer:" + isComputer);
/* 161 */       if (isComputer) {
/* 162 */         session.setAttribute("web", "");
/* 163 */         request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
/* 164 */         request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, 
/* 165 */           response);
/* 166 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 170 */     session.setAttribute("gw_id", gw_id);
/* 171 */     session.setAttribute("gw_address", gw_address);
/* 172 */     session.setAttribute("gw_port", gw_port);
/* 173 */     session.setAttribute("mac", mac);
/* 174 */     session.setAttribute("apmac", apmac);
/*     */ 
/* 176 */     String auth = basConfig.getAuthInterface();
/* 177 */     session.setAttribute("auth", auth);
/*     */ 
/* 179 */     if (Tools.Do()) {
/*     */       try {
/* 181 */         if (stringUtils.isNotBlank(webMod)) {
/* 182 */           String ids = webMod.replace("/", "");
/* 183 */           Long id = Long.valueOf(ids);
/* 184 */           Portalweb web = webService.getPortalwebByKey(id);
/* 185 */           Long advID = Long.valueOf(0L);
/* 186 */           if (web != null) {
/* 187 */             advID = web.getAdv();
/* 188 */             Advadv adv = advadvService.getAdvadvByKey(advID);
/* 189 */             if (adv != null) {
/* 190 */               int state = adv.getState().intValue();
/* 191 */               if (state == 1) {
/* 192 */                 Date startDate = adv.getShowDate();
/* 193 */                 Date endDate = adv.getEndDate();
/* 194 */                 Date nowDate = new Date();
/* 195 */                 if (((startDate == null) || 
/* 196 */                   (nowDate.getTime() >= startDate.getTime())) && (
/* 197 */                   (endDate == null) || 
/* 198 */                   (endDate.getTime() >= nowDate.getTime()))) {
/* 199 */                   request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=2").forward(
/* 200 */                     request, response);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception localException3)
/*     */       {
/*     */       }
/*     */     }
/* 211 */     request.getRequestDispatcher("/" + webMod + "/wifidogAuth.jsp").forward(request, 
/* 212 */       response);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.LoginController
 * JD-Core Version:    0.6.2
 */