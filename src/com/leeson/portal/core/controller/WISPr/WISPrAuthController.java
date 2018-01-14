/*     */ package com.leeson.portal.core.controller.WISPr;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalap;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalbasauth;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.bean.Portalssid;
/*     */ import com.leeson.core.query.PortalapQuery;
/*     */ import com.leeson.core.query.PortalbasauthQuery;
/*     */ import com.leeson.core.query.PortalssidQuery;
/*     */ import com.leeson.core.service.PortalapService;
/*     */ import com.leeson.core.service.PortalbasauthService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.core.service.PortalssidService;
/*     */ import com.leeson.portal.core.controller.WISPr.utils.Tools;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.MagicMap;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.RosAuthMap;
/*     */ import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
/*     */ import com.leeson.portal.core.model.WeixinMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class WISPrAuthController extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = -1966047929923869408L;
/*  51 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*     */ 
/*  53 */   private static Config config = Config.getInstance();
/*     */ 
/*  56 */   PortallogrecordService logrecordService = (PortallogrecordService)SpringContextHelper.getBean("portallogrecordServiceImpl");
/*     */ 
/*  58 */   PortalbasauthService basauthService = (PortalbasauthService)SpringContextHelper.getBean("portalbasauthServiceImpl");
/*     */ 
/*  60 */   PortalapService apService = (PortalapService)SpringContextHelper.getBean("portalapServiceImpl");
/*     */ 
/*  62 */   PortalssidService ssidService = (PortalssidService)SpringContextHelper.getBean("portalssidServiceImpl");
/*     */ 
/*  64 */   private static Logger logger = Logger.getLogger(WISPrAuthController.class);
/*     */ 
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  69 */     HttpSession session = request.getSession();
/*     */ 
/*  71 */     String ip = (String)session.getAttribute("ip");
/*  72 */     String basip = (String)session.getAttribute("basip");
/*  73 */     String ikweb = (String)session.getAttribute("ikweb");
/*  74 */     String ikmac = (String)session.getAttribute("ikmac");
/*  75 */     String ssid = (String)session.getAttribute("ssid");
/*  76 */     String apmac = (String)session.getAttribute("apmac");
/*  77 */     String webMod = (String)session.getAttribute("web");
/*  78 */     String agent = (String)session.getAttribute("agent");
/*  79 */     if (stringUtils.isBlank(webMod)) {
/*  80 */       webMod = "";
/*     */     }
/*     */ 
/*  83 */     System.out.println("AuthAPI basip=" + basip + "----ip=" + ip + 
/*  84 */       "----mac=" + ikmac + "----url=" + ikweb + "----ssid=" + ssid + 
/*  85 */       "----apmac=" + apmac + "----webMod=" + webMod);
/*     */ 
/*  87 */     if ((stringUtils.isBlank(ikmac)) || (stringUtils.isBlank(basip)) || 
/*  88 */       (stringUtils.isBlank(ip))) {
/*  89 */       request.getRequestDispatcher("/" + webMod + "/error.html").forward(request, 
/*  90 */         response);
/*  91 */       return;
/*     */     }
/*  93 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  94 */     Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
/*  95 */     if ((basConfigFind != null) && 
/*  96 */       (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
/*  97 */       basConfig = basConfigFind;
/*     */     }
/*     */ 
/* 100 */     if ((!basConfig.getBas().equals("5")) && 
/* 101 */       (!basConfig.getBas().equals("6")) && 
/* 102 */       (!basConfig
/* 102 */       .getBas().equals("7")) && (!basConfig.getBas().equals("8"))) {
/* 103 */       request.getRequestDispatcher("/" + webMod + "/error.html").forward(request, 
/* 104 */         response);
/* 105 */       return;
/*     */     }
/*     */ 
/* 116 */     String[] rosInfo = (String[])RosAuthMap.getInstance().getRosAuthMap().get(ikmac);
/* 117 */     RosAuthMap.getInstance().getRosAuthMap().remove(ikmac);
/* 118 */     if (rosInfo.length < 5) {
/* 119 */       String auth = basConfig.getAuthInterface();
/* 120 */       request.setAttribute("auth", auth);
/* 121 */       session.setAttribute("msg", "获取认证信息出错！！");
/* 122 */       request.getRequestDispatcher("/" + webMod + "/OL.jsp").forward(request, response);
/* 123 */       return;
/*     */     }
/* 125 */     int basauthType = Integer.valueOf(rosInfo[0]).intValue();
/* 126 */     Long id = Long.valueOf(rosInfo[1]);
/* 127 */     String username = rosInfo[2];
/* 128 */     String authUser = rosInfo[3];
/* 129 */     String authPwd = rosInfo[4];
/*     */ 
/* 131 */     String authUrl = ikweb;
/* 132 */     PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 133 */     bsq.setBasip(basip);
/* 134 */     List<Portalbasauth> basauths = this.basauthService.getPortalbasauthList(bsq);
/* 135 */     if (basauths.size() > 0) {
/* 136 */       for (Portalbasauth ba : basauths) {
/* 137 */         if (ba.getType().intValue() == basauthType) {
/* 138 */           authUrl = ba.getUrl();
/* 139 */           if (!stringUtils.isBlank(authUrl)) break;
/* 140 */           authUrl = ikweb;
/*     */ 
/* 142 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 146 */     if (stringUtils.isBlank(authUrl)) {
/* 147 */       authUrl = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */     }
/* 149 */     session.setAttribute("ikweb", authUrl);
/*     */ 
/* 151 */     boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
/* 152 */       ip + ":" + basip);
/* 153 */     if (isLogin) {
/* 154 */       String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
/* 155 */         ip + ":" + basip);
/* 156 */       Tools.GetMacTimeLimitMethod(loginInfo, ikmac, session);
/* 157 */       request.getRequestDispatcher("/" + webMod + "APIok.jsp").forward(
/* 158 */         request, response);
/* 159 */       return;
/*     */     }
/*     */ 
/* 163 */     String[] loginInfo = new String[16];
/* 164 */     loginInfo[0] = username;
/* 165 */     Date now = new Date();
/* 166 */     loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 167 */     loginInfo[4] = ikmac;
/* 168 */     loginInfo[6] = rosInfo[0];
/* 169 */     loginInfo[7] = "0";
/* 170 */     loginInfo[8] = "0";
/*     */ 
/* 172 */     loginInfo[9] = ip;
/* 173 */     loginInfo[10] = basip;
/* 174 */     loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
/* 175 */     loginInfo[12] = ssid;
/* 176 */     loginInfo[13] = apmac;
/* 177 */     loginInfo[14] = "no";
/* 178 */     if ((rosInfo.length == 6) && 
/* 179 */       (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
/* 180 */       loginInfo[14] = "yes";
/*     */     }
/*     */ 
/* 183 */     loginInfo[15] = agent;
/*     */ 
/* 185 */     Portallogrecord logRecord = new Portallogrecord();
/*     */ 
/* 187 */     if (id.longValue() == 3L) {
/* 188 */       loginInfo[5] = "ok";
/* 189 */       Long userId = Tools.getAccountid(username);
/* 190 */       Long recordId = Tools.doLinkRecord(userId, ip, basip, ikmac);
/* 191 */       loginInfo[1] = String.valueOf(userId);
/* 192 */       loginInfo[2] = String.valueOf(recordId);
/* 193 */       Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), response, 
/* 194 */         username);
/* 195 */     } else if (id.longValue() == 4L) {
/* 196 */       loginInfo[5] = "ok";
/* 197 */       Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), response);
/* 198 */     } else if (id.longValue() == 6L) {
/* 199 */       boolean state = true;
/* 200 */       if ((rosInfo.length == 6) && 
/* 201 */         (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
/* 202 */         state = false;
/*     */       }
/*     */ 
/* 205 */       if (state) {
/* 206 */         WeixinMap.getInstance().getWeixinipmap().put(ip + ":" + basip, now);
/* 207 */         WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().put(ip + ":" + basip, "yes");
/*     */       }
/* 209 */       Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), authUser, 
/* 210 */         authPwd, username.replace("-临时放行", ""), response);
/* 211 */     } else if (id.longValue() == 7L) {
/* 212 */       boolean state = true;
/* 213 */       if ((rosInfo.length == 6) && 
/* 214 */         (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
/* 215 */         state = false;
/*     */       }
/*     */ 
/* 218 */       if (state) {
/* 219 */         String[] magicInfo = new String[3];
/* 220 */         magicInfo[0] = (ip + ":" + basip);
/* 221 */         magicInfo[1] = "";
/* 222 */         magicInfo[2] = ikmac;
/* 223 */         MagicMap.getInstance().getMagicMap()
/* 224 */           .put(ip + ":" + basip, magicInfo);
/* 225 */         WeixinMap.getInstance().getWeixinipmap().put(ip + ":" + basip, now);
/* 226 */         WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().put(ip + ":" + basip, "yes");
/*     */       }
/* 228 */       Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), authUser, 
/* 229 */         authPwd, username.replace("-临时放行", ""), response);
/*     */     } else {
/* 231 */       Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), authUser, 
/* 232 */         authPwd, username, response);
/*     */     }
/*     */ 
/* 235 */     if ((id.longValue() != 6L) && (id.longValue() != 7L)) {
/* 236 */       if (id.longValue() == 3L) {
/* 237 */         Tools.AutoLoginPutMethod(ikmac, id, authUser, authPwd, username.replace("(无感知)", ""));
/*     */       }
/* 239 */       else if (username.contains("无感知"))
/* 240 */         Tools.AutoLoginPutMethod(ikmac, id, authUser, authPwd, username);
/*     */       else {
/* 242 */         Tools.AutoLoginPutMethod(ikmac, id, authUser, authPwd, username + "(无感知)");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 247 */     session.setAttribute("username", username);
/* 248 */     onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
/* 249 */     logRecord.setRecDate(now);
/* 250 */     logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
/* 251 */       " 用户: 【" + username + "】,登录成功!");
/* 252 */     this.logrecordService.addPortallogrecord(logRecord);
/*     */ 
/* 254 */     if (stringUtils.isNotBlank(ssid)) {
/*     */       try {
/* 256 */         PortalssidQuery apq = new PortalssidQuery();
/* 257 */         apq.setSsid(ssid);
/* 258 */         apq.setSsidLike(false);
/* 259 */         List aps = this.ssidService.getPortalssidList(apq);
/* 260 */         if ((aps != null) && (aps.size() > 0)) {
/* 261 */           Portalssid ap = (Portalssid)aps.get(0);
/* 262 */           ap.setBasip(basip);
/* 263 */           long count = ap.getCount().longValue() + 1L;
/* 264 */           ap.setCount(Long.valueOf(count));
/* 265 */           this.ssidService.updatePortalssidByKey(ap);
/*     */         } else {
/* 267 */           Portalssid ap = new Portalssid();
/* 268 */           ap.setSsid(ssid);
/* 269 */           ap.setBasip(basip);
/* 270 */           ap.setCount(Long.valueOf(1L));
/* 271 */           this.ssidService.addPortalssid(ap);
/*     */         }
/*     */       } catch (Exception e) {
/* 274 */         logger.error("==============ERROR Start=============");
/* 275 */         logger.error(e);
/* 276 */         logger.error("ERROR INFO ", e);
/* 277 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/* 280 */     if (stringUtils.isNotBlank(apmac)) {
/*     */       try {
/* 282 */         PortalapQuery apq = new PortalapQuery();
/* 283 */         apq.setMac(apmac);
/* 284 */         apq.setMacLike(false);
/* 285 */         List aps = this.apService.getPortalapList(apq);
/* 286 */         if ((aps != null) && (aps.size() > 0)) {
/* 287 */           Portalap ap = (Portalap)aps.get(0);
/* 288 */           ap.setBasip(basip);
/* 289 */           long count = ap.getCount().longValue() + 1L;
/* 290 */           ap.setCount(Long.valueOf(count));
/* 291 */           this.apService.updatePortalapByKey(ap);
/*     */         } else {
/* 293 */           Portalap ap = new Portalap();
/* 294 */           ap.setMac(apmac);
/* 295 */           ap.setBasip(basip);
/* 296 */           ap.setCount(Long.valueOf(1L));
/* 297 */           this.apService.addPortalap(ap);
/*     */         }
/*     */       } catch (Exception e) {
/* 300 */         logger.error("==============ERROR Start=============");
/* 301 */         logger.error(e);
/* 302 */         logger.error("ERROR INFO ", e);
/* 303 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */ 
/* 307 */     if ((basauthType == 5) || (basauthType == 6)) {
/* 308 */       String userAgent = request.getHeader("user-agent");
/* 309 */       System.out.println("user-agent " + userAgent);
/* 310 */       boolean isComputer = false;
/* 311 */       if (stringUtils.isNotBlank(userAgent)) {
/* 312 */         if (userAgent.contains("Windows")) {
/* 313 */           isComputer = true;
/*     */         }
/* 315 */         else if ((userAgent.contains("Android")) || 
/* 316 */           (userAgent.contains("iPhone")) || 
/* 317 */           (userAgent.contains("iPod")) || 
/* 318 */           (userAgent.contains("iPad"))) {
/* 319 */           isComputer = false;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 324 */       if ((rosInfo.length == 6) && 
/* 325 */         (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
/* 326 */         request.getRequestDispatcher("/" + webMod + "APIok.jsp").forward(request, 
/* 327 */           response);
/* 328 */         return;
/*     */       }
/*     */ 
/* 332 */       if (isComputer) {
/* 333 */         request.getRequestDispatcher("/" + webMod + "APIwxpc.jsp").forward(request, 
/* 334 */           response);
/* 335 */         return;
/*     */       }
/* 337 */       request.getRequestDispatcher("/" + webMod + "APIwx.jsp").forward(request, 
/* 338 */         response);
/* 339 */       return;
/*     */     }
/*     */ 
/* 342 */     request.getRequestDispatcher("/" + webMod + "APIok.jsp").forward(request, 
/* 343 */       response);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.WISPrAuthController
 * JD-Core Version:    0.6.2
 */