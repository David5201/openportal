/*     */ package com.leeson.portal.core.controller;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalautologin;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalbasauth;
/*     */ import com.leeson.core.bean.Portalip;
/*     */ import com.leeson.core.bean.Portalonlinelimit;
/*     */ import com.leeson.core.bean.Portalweb;
/*     */ import com.leeson.core.controller.AjaxInterFaceController;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.query.PortalbasauthQuery;
/*     */ import com.leeson.core.query.PortalipQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalautologinService;
/*     */ import com.leeson.core.service.PortalbasauthService;
/*     */ import com.leeson.core.service.PortalipService;
/*     */ import com.leeson.core.service.PortalonlinelimitService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import com.leeson.core.utils.IPv4Util;
/*     */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*     */ import com.leeson.portal.core.model.AutoLoginMap;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.MacLimitMap;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
/*     */ import com.leeson.portal.core.model.WeixinMap;
/*     */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LinkAuthAction extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 2052385989876611487L;
/*  60 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*     */ 
/*  62 */   private static Config config = Config.getInstance();
/*     */ 
/*  64 */   private static Logger logger = Logger.getLogger(LinkAuthAction.class);
/*     */   public static final String WEI_XIN_BROWSER = "MicroMessenger/";
/*  68 */   private static PortalwebService webService = (PortalwebService)
/*  69 */     SpringContextHelper.getBean("portalwebServiceImpl");
/*     */ 
/*  70 */   private static PortalbasauthService basauthService = (PortalbasauthService)
/*  71 */     SpringContextHelper.getBean("portalbasauthServiceImpl");
/*     */ 
/*  72 */   private static PortalonlinelimitService onlinelimitService = (PortalonlinelimitService)
/*  73 */     SpringContextHelper.getBean("portalonlinelimitServiceImpl");
/*     */ 
/*  74 */   private static PortalaccountService accountService = (PortalaccountService)
/*  75 */     SpringContextHelper.getBean("portalaccountServiceImpl");
/*     */ 
/*  76 */   private static PortalautologinService autologinService = (PortalautologinService)
/*  77 */     SpringContextHelper.getBean("portalautologinServiceImpl");
/*     */ 
/*  78 */   private static PortalipService ipService = (PortalipService)
/*  79 */     SpringContextHelper.getBean("portalipServiceImpl");
/*     */ 
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  84 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  85 */     HttpSession session = request.getSession();
/*  86 */     String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*  87 */     if (basConfig.getIsdebug().equals("1")) {
/*  88 */       logger.info("linkAuth RemoteAddrIp= " + ip);
/*     */     }
/*     */ 
/*  91 */     String webMod = "";
/*     */     try {
/*  93 */       long ipL = IPv4Util.ipToLong(ip);
/*  94 */       List<Portalip> iplList = ipService
/*  95 */         .getPortalipList(new PortalipQuery());
/*  96 */       for (Portalip portalip : iplList) {
/*  97 */         long startH = IPv4Util.ipToLong(portalip.getStart());
/*  98 */         long endH = IPv4Util.ipToLong(portalip.getEnd());
/*  99 */         if ((ipL >= startH) && (ipL <= endH)) {
/* 100 */           Portalweb web = webService.getPortalwebByKey(portalip.getWeb());
/* 101 */           if (web == null) break;
/* 102 */           webMod = String.valueOf(web.getId()) + "/";
/*     */ 
/* 104 */           break;
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 108 */       webMod = "";
/*     */     }
/* 110 */     session.setAttribute("web", webMod);
/*     */ 
/* 112 */     if (!isPortalWXAuthInner(request)) {
/* 113 */       request.getRequestDispatcher("/" + webMod + "info.jsp").forward(request, response);
/* 114 */       return;
/*     */     }
/*     */ 
/* 118 */     String host = "";
/* 119 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
/* 120 */     while (iterator.hasNext()) {
/* 121 */       Object o = iterator.next();
/* 122 */       String t = o.toString();
/* 123 */       if (t.contains(ip + ":")) {
/* 124 */         host = t;
/* 125 */         if (WeixinMap.getInstance().getWeixinipmap().get(t) != null) {
/* 126 */           WeixinMap.getInstance().getWeixinipmap().remove(t);
/* 127 */           WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(t);
/* 128 */           if (basConfig.getIsdebug().equals("1")) {
/* 129 */             logger.info("IP:" + t + "Link Auth Success , Remove WeixinTempMap !!");
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 137 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(host);
/* 138 */     if (isLogin) {
/* 139 */       String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
/* 140 */       String[] ts = host.split(":");
/* 141 */       String basip = ts[1];
/* 142 */       String ikmac = loginInfo[4];
/* 143 */       String username = loginInfo[0].replace("-临时放行", "");
/*     */ 
/* 145 */       loginInfo[0] = username;
/* 146 */       onlineMap.getOnlineUserMap().put(host, loginInfo);
/* 147 */       GetMacTimeLimitMethod(loginInfo, ikmac, session);
/* 148 */       session.setAttribute("username", username);
/* 149 */       session.setAttribute("ip", ip);
/* 150 */       session.setAttribute("basip", basip);
/*     */ 
/* 152 */       String authUrlWeb = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/* 153 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 154 */       bsq.setBasip(basip);
/* 155 */       bsq.setBasipLike(false);
/* 156 */       bsq.setType(Integer.valueOf(5));
/* 157 */       String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 158 */       String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 159 */       List<Portalbasauth> basauths = basauthService
/* 160 */         .getPortalbasauthList(bsq);
/* 161 */       if (basauths.size() > 0) {
/* 162 */         for (Portalbasauth ba : basauths) {
/* 163 */           if (ba.getType().intValue() == 5) {
/* 164 */             authUser = ba.getUsername();
/* 165 */             authPwd = ba.getPassword();
/* 166 */             authUrlWeb = ba.getUrl();
/* 167 */             if ((stringUtils.isBlank(authUser)) || 
/* 168 */               (stringUtils.isBlank(authPwd))) {
/* 169 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 170 */                 .getBasUser();
/* 171 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 172 */                 .getBasPwd();
/*     */             }
/* 174 */             if (!stringUtils.isBlank(authUrlWeb)) break;
/* 175 */             authUrlWeb = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */ 
/* 177 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 181 */       if (stringUtils.isBlank(authUrlWeb)) {
/* 182 */         authUrlWeb = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */       }
/*     */ 
/* 185 */       AutoLoginPutMethod(ikmac, Long.valueOf(7L), authUser, authPwd, username);
/*     */ 
/* 187 */       AjaxInterFaceController.SangforLogin(ip, username, ikmac, "", basip);
/*     */ 
/* 189 */       session.setAttribute("ikweb", authUrlWeb);
/*     */ 
/* 191 */       response.sendRedirect("/" + webMod + "ok.jsp?u=公众号认证&i=" + ip + "&m=认证成功&l=" + authUrlWeb);
/* 192 */       return;
/*     */     }
/* 194 */     request.getRequestDispatcher("/" + webMod + "out.jsp").forward(request, response);
/*     */   }
/*     */ 
/*     */   private static void GetMacTimeLimitMethod(String[] loginInfo, String mac, HttpSession session)
/*     */   {
/* 208 */     String username = loginInfo[0];
/* 209 */     String uid = loginInfo[1];
/* 210 */     String rid = loginInfo[2];
/* 211 */     String loginTimeString = loginInfo[3];
/* 212 */     if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
/* 213 */       mac = loginInfo[4];
/*     */     }
/* 215 */     Date nowTime = new Date();
/* 216 */     Date loginTime = nowTime;
/*     */     try {
/* 218 */       loginTime = ThreadLocalDateUtil.parse(loginTimeString);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */ 
/* 224 */     int costTime = (int)((nowTime.getTime() - loginTime.getTime()) / 60000L);
/*     */ 
/* 226 */     int haveTime = getHaveTime();
/* 227 */     if ((!stringUtils.isBlank(uid)) && (!stringUtils.isBlank(rid))) {
/* 228 */       haveTime = (int)(getTime(username).longValue() / 60000L);
/*     */     }
/* 230 */     int toHaveTime = haveTime;
/*     */ 
/* 232 */     Long oldcostTime = Long.valueOf(0L);
/* 233 */     Boolean notLimit = Boolean.valueOf(true);
/* 234 */     if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 235 */       String[] macTimeInfo = 
/* 236 */         (String[])MacLimitMap.getInstance().getMacLimitMap()
/* 236 */         .get(mac);
/* 237 */       if (macTimeInfo != null) {
/* 238 */         Long id = Long.valueOf(macTimeInfo[2]);
/* 239 */         oldcostTime = Long.valueOf(macTimeInfo[1]);
/* 240 */         Portalonlinelimit onlinelimit = onlinelimitService
/* 241 */           .getPortalonlinelimitByKey(id);
/* 242 */         if (onlinelimit != null) {
/* 243 */           Long timepermit = onlinelimit.getTime();
/* 244 */           loginTimeString = macTimeInfo[0];
/* 245 */           if (onlinelimit.getState().intValue() == 1)
/*     */           {
/* 247 */             if (stringUtils.isNotBlank(loginTimeString))
/*     */               try {
/* 249 */                 loginTime = ThreadLocalDateUtil.parse(loginTimeString);
/*     */               }
/*     */               catch (Exception localException1)
/*     */               {
/*     */               }
/* 254 */             costTime = (int)((nowTime.getTime() - 
/* 255 */               loginTime.getTime() + oldcostTime.longValue()) / 60000L);
/* 256 */             haveTime = (int)(timepermit.longValue() / 60000L);
/* 257 */             notLimit = Boolean.valueOf(false);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 263 */     int overTime = costTime;
/* 264 */     if (!notLimit.booleanValue()) {
/* 265 */       haveTime -= overTime;
/*     */     }
/* 267 */     if (haveTime > toHaveTime) {
/* 268 */       haveTime = toHaveTime;
/*     */     }
/* 270 */     if (haveTime < 0) {
/* 271 */       haveTime = 0;
/*     */     }
/* 273 */     if (notLimit.booleanValue()) {
/* 274 */       overTime = costTime + (int)(oldcostTime.longValue() / 60000L);
/*     */     }
/*     */ 
/* 277 */     String haveTimeString = String.valueOf(haveTime);
/* 278 */     String overTimeString = String.valueOf(overTime);
/* 279 */     session.setAttribute("haveTime", haveTimeString);
/* 280 */     session.setAttribute("overTime", overTimeString);
/*     */   }
/*     */ 
/*     */   private static Long getTime(String username)
/*     */   {
/* 291 */     PortalaccountQuery query = new PortalaccountQuery();
/* 292 */     query.setLoginName(username);
/* 293 */     query.setLoginNameLike(false);
/* 294 */     Portalaccount a = (Portalaccount)accountService.getPortalaccountList(query).get(0);
/* 295 */     String userState = a.getState();
/* 296 */     Date userDate = a.getDate();
/* 297 */     Long userTime = a.getTime();
/*     */ 
/* 299 */     if (userState.equals(String.valueOf(0))) {
/* 300 */       return Long.valueOf(0L);
/*     */     }
/*     */ 
/* 303 */     if (userState.equals(String.valueOf(1))) {
/* 304 */       return Long.valueOf(86400000L);
/*     */     }
/*     */ 
/* 307 */     if (userState.equals(String.valueOf(3))) {
/* 308 */       Date now = new Date();
/* 309 */       if (userDate.getTime() > now.getTime()) {
/* 310 */         return Long.valueOf(userDate.getTime() - now.getTime());
/*     */       }
/* 312 */       return Long.valueOf(0L);
/*     */     }
/*     */ 
/* 316 */     if (userState.equals("2")) {
/* 317 */       if (userTime.longValue() > 0L) {
/* 318 */         return userTime;
/*     */       }
/* 320 */       return Long.valueOf(0L);
/*     */     }
/*     */ 
/* 323 */     return Long.valueOf(0L);
/*     */   }
/*     */ 
/*     */   private static int getHaveTime()
/*     */   {
/*     */     try
/*     */     {
/* 333 */       Date nowdate = new Date();
/* 334 */       Calendar calendar = new GregorianCalendar();
/* 335 */       calendar.setTime(nowdate);
/* 336 */       calendar.add(5, 1);
/* 337 */       Date tdate = calendar.getTime();
/* 338 */       SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/* 339 */       String tsString = fs.format(tdate);
/* 340 */       Date t = fs.parse(tsString);
/* 341 */       return (int)((t.getTime() - nowdate.getTime()) / 60000L); } catch (Exception e) {
/*     */     }
/* 343 */     return 1440;
/*     */   }
/*     */ 
/*     */   public static boolean isPortalWXAuthInner(HttpServletRequest request)
/*     */   {
/* 356 */     String userAgent = request.getHeader("user-agent");
/* 357 */     boolean isPortalWXAuthInner = false;
/* 358 */     if (stringUtils.isNotBlank(userAgent))
/*     */     {
/* 360 */       if (userAgent.contains("MicroMessenger/")) {
/* 361 */         isPortalWXAuthInner = true;
/*     */       }
/*     */     }
/* 364 */     return isPortalWXAuthInner;
/*     */   }
/*     */ 
/*     */   private static void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
/*     */   {
/* 376 */     Portalautologin autologin = autologinService
/* 377 */       .getPortalautologinByKey(id);
/* 378 */     if ((autologin != null) && (autologin.getState().intValue() == 1) && 
/* 379 */       (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 380 */       String[] macTimeInfo = (String[])AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(mac);
/* 381 */       Date now = new Date();
/* 382 */       String nowString = ThreadLocalDateUtil.format(now);
/* 383 */       if (macTimeInfo == null) {
/* 384 */         macTimeInfo = new String[3];
/* 385 */         macTimeInfo[1] = "0";
/*     */       }
/* 387 */       macTimeInfo[0] = nowString;
/* 388 */       macTimeInfo[2] = String.valueOf(id);
/* 389 */       AutoLoginMacMap.getInstance().getAutoLoginMacMap().put(mac, macTimeInfo);
/*     */ 
/* 391 */       String[] userinfo = new String[3];
/* 392 */       userinfo[0] = authUser;
/* 393 */       userinfo[1] = authPwd;
/* 394 */       userinfo[2] = username;
/* 395 */       AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.LinkAuthAction
 * JD-Core Version:    0.6.2
 */