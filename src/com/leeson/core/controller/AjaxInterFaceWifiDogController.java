/*      */ package com.leeson.core.controller;
/*      */ 
/*      */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalaccountmacs;
/*      */ import com.leeson.core.bean.Portalbas;
/*      */ import com.leeson.core.bean.Portalbasauth;
/*      */ import com.leeson.core.bean.Portalsmsapi;
/*      */ import com.leeson.core.bean.Portalweb;
/*      */ import com.leeson.core.bean.Portalweixinwifi;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalaccountmacsQuery;
/*      */ import com.leeson.core.query.PortalbasauthQuery;
/*      */ import com.leeson.core.query.PortalsmsapiQuery;
/*      */ import com.leeson.core.query.PortalweixinwifiQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalaccountmacsService;
/*      */ import com.leeson.core.service.PortalbasService;
/*      */ import com.leeson.core.service.PortalbasauthService;
/*      */ import com.leeson.core.service.PortalsmsapiService;
/*      */ import com.leeson.core.service.PortalwebService;
/*      */ import com.leeson.core.service.PortalweixinwifiService;
/*      */ import com.leeson.core.utils.WifidogKick;
/*      */ import com.leeson.portal.core.model.AutoLoginMap;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.portal.core.model.PhoneCodeMap;
/*      */ import com.leeson.portal.core.model.WeixinMap;
/*      */ import com.leeson.portal.core.model.WeixinSMSMap;
/*      */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*      */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*      */ import java.io.IOException;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.codec.digest.DigestUtils;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Controller;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.ResponseBody;
/*      */ 
/*      */ @Controller
/*      */ public class AjaxInterFaceWifiDogController
/*      */ {
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountService accountService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalweixinwifiService weixinwifiService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountmacsService macsService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalbasService basService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalsmsapiService portalsmsapiService;
/*      */ 
/*      */   @Autowired
/*      */   private ConfigService configService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalwebService webService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalbasauthService portalbasauthService;
/*   85 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*      */ 
/*   87 */   private static Logger logger = Logger.getLogger(AjaxInterFaceWifiDogController.class);
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_wifidog_sms"})
/*      */   public Map<String, String> sms(String phone, String yzm, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException
/*      */   {
/*   96 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */ 
/*   98 */     Map map = new HashMap();
/*      */ 
/*  100 */     if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
/*  101 */       map.put("ret", "2");
/*  102 */       map.put("msg", "请输入手机号和验证码！！");
/*  103 */       return map;
/*      */     }
/*  105 */     if (phone.length() != 11) {
/*  106 */       map.put("ret", "2");
/*  107 */       map.put("msg", "手机号码不正确！");
/*  108 */       return map;
/*      */     }
/*      */     try {
/*  111 */       Long.parseLong(phone);
/*      */     } catch (Exception e) {
/*  113 */       map.put("ret", "2");
/*  114 */       map.put("msg", "手机号码不正确！");
/*  115 */       return map;
/*      */     }
/*  117 */     String code = "";
/*  118 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/*  119 */     query.setState("1");
/*  120 */     List smsList = this.portalsmsapiService
/*  121 */       .getPortalsmsapiList(query);
/*      */     Portalsmsapi smsapi;
/*  123 */     if (smsList.size() > 0)
/*  124 */       smsapi = (Portalsmsapi)smsList.get(0);
/*      */     else {
/*  126 */       smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*      */     }
/*  128 */     Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
/*  129 */     Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
/*      */     try {
/*  131 */       Date saveDate = (Date)objs[1];
/*  132 */       Long nowTime = Long.valueOf(new Date().getTime());
/*  133 */       if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
/*  134 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  135 */         map.put("ret", "2");
/*  136 */         map.put("msg", "验证码已过期，请重新获取验证码！");
/*  137 */         return map;
/*      */       }
/*  139 */       code = (String)objs[0];
/*      */     } catch (Exception e) {
/*  141 */       map.put("ret", "2");
/*  142 */       map.put("msg", "手机号或验证码不正确！");
/*  143 */       return map;
/*      */     }
/*      */ 
/*  146 */     if (!yzm.equals(code)) {
/*  147 */       map.put("ret", "2");
/*  148 */       map.put("msg", "验证码不正确！");
/*  149 */       return map;
/*      */     }
/*      */ 
/*  152 */     String more = smsapi.getMore();
/*  153 */     if ("0".equals(more)) {
/*  154 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*      */     }
/*      */ 
/*  157 */     HttpSession session = request.getSession();
/*      */ 
/*  159 */     String gw_id = (String)session.getAttribute("gw_id");
/*  160 */     String gw_address = (String)session.getAttribute("gw_address");
/*  161 */     String gw_port = (String)session.getAttribute("gw_port");
/*  162 */     String mac = (String)session.getAttribute("mac");
/*  163 */     String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */ 
/*  165 */     String url = (String)session.getAttribute("url");
/*      */     String basName;
/*  167 */     if (stringUtils.isNotBlank(gw_id))
/*      */       try
/*      */       {
/*  170 */         Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
/*  171 */         while (iteratorConfig.hasNext()) {
/*  172 */           Object o = iteratorConfig.next();
/*  173 */           String t = o.toString();
/*  174 */           Portalbas bas = (Portalbas)config.getConfigMap().get(t);
/*  175 */           basName = bas.getBasname();
/*  176 */           if ((stringUtils.isNotBlank(basName)) && 
/*  177 */             (basName.equals(gw_id))) {
/*  178 */             basConfig = bas;
/*  179 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  184 */         basConfig = (Portalbas)config.getConfigMap().get("");
/*      */       }
/*      */     try
/*      */     {
/*  188 */       String web = (String)session.getAttribute("web");
/*  189 */       if (stringUtils.isNotBlank(web)) {
/*  190 */         while (web.endsWith("/")) {
/*  191 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  193 */         Long webID = Long.valueOf(web);
/*  194 */         if (webID.longValue() != 0L) {
/*  195 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  196 */           if (portalweb != null) {
/*  197 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  198 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  201 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  202 */           if (config != null) {
/*  203 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  204 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  208 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  209 */         if (config != null) {
/*  210 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  211 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1) {
/*      */     }
/*  217 */     String goUrl = url;
/*  218 */     PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  219 */     bsq.setBasip(basConfig.getBasIp());
/*  220 */     List<Portalbasauth> basauths = this.portalbasauthService
/*  221 */       .getPortalbasauthList(bsq);
/*  222 */     if (basauths.size() > 0) {
/*  223 */       for (Portalbasauth ba : basauths) {
/*  224 */         if (ba.getType().intValue() == 4) {
/*  225 */           goUrl = ba.getUrl();
/*  226 */           if (!stringUtils.isBlank(goUrl)) break;
/*  227 */           goUrl = url;
/*      */ 
/*  229 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  233 */     if (stringUtils.isBlank(goUrl)) {
/*  234 */       goUrl = 
/*  235 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  235 */         .get("core"))[0];
/*      */     }
/*  237 */     url = goUrl;
/*  238 */     session.setAttribute("url", url);
/*      */ 
/*  241 */     String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);
/*      */ 
/*  243 */     if (basConfig.getAuthInterface().contains("4"))
/*      */     {
/*  245 */       session.setAttribute("username", phone);
/*  246 */       session.setAttribute("ip", ip);
/*  247 */       session.setAttribute("token", token);
/*      */ 
/*  249 */       String[] loginInfo = new String[10];
/*  250 */       loginInfo[0] = phone;
/*  251 */       Date now = new Date();
/*  252 */       loginInfo[3] = ThreadLocalDateUtil.format(now);
/*  253 */       loginInfo[4] = ip;
/*  254 */       loginInfo[5] = mac;
/*  255 */       loginInfo[6] = gw_id;
/*  256 */       loginInfo[7] = gw_address;
/*  257 */       loginInfo[8] = "0";
/*  258 */       loginInfo[9] = "0";
/*      */ 
/*  260 */       WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
/*  261 */         .put(token, loginInfo);
/*      */ 
/*  263 */       map.put("ret", "0");
/*  264 */       map.put("i", ip);
/*  265 */       map.put("u", phone);
/*  266 */       map.put("msg", "认证成功！");
/*  267 */       map.put("l", url);
/*  268 */       map.put("gw_address", gw_address);
/*  269 */       map.put("gw_port", gw_port);
/*  270 */       map.put("token", token);
/*  271 */       return map;
/*      */     }
/*      */ 
/*  274 */     map.put("ret", "3");
/*  275 */     map.put("i", "");
/*  276 */     map.put("u", phone);
/*  277 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/*  278 */     if (stringUtils.isBlank(url)) {
/*  279 */       url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */     }
/*  281 */     map.put("l", url);
/*  282 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_wifidog_weixinsms"})
/*      */   public Map<String, String> weixinsms(String pwd, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException
/*      */   {
/*  294 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */ 
/*  296 */     Map map = new HashMap();
/*  297 */     HttpSession session = request.getSession();
/*  298 */     String gw_id = (String)session.getAttribute("gw_id");
/*  299 */     String gw_address = (String)session.getAttribute("gw_address");
/*  300 */     String gw_port = (String)session.getAttribute("gw_port");
/*  301 */     String mac = (String)session.getAttribute("mac");
/*  302 */     String url = (String)session.getAttribute("url");
/*      */ 
/*  304 */     String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */ 
/*  306 */     String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);
/*      */ 
/*  308 */     if (stringUtils.isBlank(pwd)) {
/*  309 */       map.put("ret", "2");
/*  310 */       map.put("i", ip);
/*  311 */       map.put("u", "微信获取密码认证");
/*  312 */       map.put("msg", "上网密码不正确！！");
/*  313 */       if (stringUtils.isBlank(url)) {
/*  314 */         url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */       }
/*  316 */       map.put("l", url);
/*  317 */       return map;
/*      */     }
/*  319 */     if (!pwd.equals(WeixinSMSMap.getInstance().getPwd())) {
/*  320 */       map.put("ret", "2");
/*  321 */       map.put("i", ip);
/*  322 */       map.put("u", "微信获取密码认证");
/*  323 */       map.put("msg", "上网密码不正确！！");
/*  324 */       if (stringUtils.isBlank(url)) {
/*  325 */         url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */       }
/*  327 */       map.put("l", url);
/*  328 */       return map;
/*      */     }
/*      */ 
/*  331 */     if (basConfig.getAuthInterface().contains("6"))
/*      */     {
/*  333 */       session.setAttribute("username", "微信获取密码认证");
/*  334 */       session.setAttribute("ip", ip);
/*  335 */       session.setAttribute("token", token);
/*      */ 
/*  337 */       String[] loginInfo = new String[10];
/*  338 */       loginInfo[0] = "微信获取密码认证";
/*  339 */       Date now = new Date();
/*  340 */       loginInfo[3] = ThreadLocalDateUtil.format(now);
/*  341 */       loginInfo[4] = ip;
/*  342 */       loginInfo[5] = mac;
/*  343 */       loginInfo[6] = gw_id;
/*  344 */       loginInfo[7] = gw_address;
/*  345 */       loginInfo[8] = "0";
/*  346 */       loginInfo[9] = "0";
/*      */ 
/*  348 */       WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
/*  349 */         .put(token, loginInfo);
/*      */ 
/*  351 */       map.put("ret", "0");
/*  352 */       map.put("i", ip);
/*  353 */       map.put("u", "微信获取密码认证");
/*  354 */       map.put("msg", "认证成功！");
/*  355 */       if (stringUtils.isBlank(url)) {
/*  356 */         url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */       }
/*  358 */       map.put("l", url);
/*      */ 
/*  360 */       map.put("gw_address", gw_address);
/*  361 */       map.put("gw_port", gw_port);
/*  362 */       map.put("token", token);
/*  363 */       return map;
/*      */     }
/*      */ 
/*  366 */     map.put("ret", "3");
/*  367 */     map.put("i", ip);
/*  368 */     map.put("u", "微信获取密码认证");
/*  369 */     map.put("msg", "系统不是该验证模式，请联系管理员！！");
/*  370 */     if (stringUtils.isBlank(url)) {
/*  371 */       url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */     }
/*  373 */     map.put("l", url);
/*  374 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/wifidog_check_weixin"})
/*      */   public Map<String, String> wifidog_check_weixin(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  384 */     HttpSession session = request.getSession();
/*  385 */     String token = (String)session.getAttribute("token");
/*      */ 
/*  387 */     Map map = new HashMap();
/*  388 */     if (stringUtils.isBlank(token)) {
/*  389 */       map.put("ret", "1");
/*  390 */       return map;
/*      */     }
/*      */     try {
/*  393 */       String[] loginInfo = (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().get(token);
/*  394 */       Date wxt = 
/*  395 */         (Date)WeixinMap.getInstance().getWeixinipmap()
/*  395 */         .get("wifidog" + token);
/*  396 */       if ((loginInfo != null) && (wxt == null)) {
/*  397 */         map.put("ret", "0");
/*  398 */         return map;
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {
/*  402 */       map.put("ret", "1");
/*  403 */     }return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_wifidog_weixin"})
/*      */   public Map<String, String> weixin(HttpServletRequest request, HttpServletResponse response) {
/*  410 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */ 
/*  412 */     Map map = new HashMap();
/*  413 */     HttpSession session = request.getSession();
/*  414 */     String gw_id = (String)session.getAttribute("gw_id");
/*  415 */     String gw_address = (String)session.getAttribute("gw_address");
/*  416 */     String gw_port = (String)session.getAttribute("gw_port");
/*  417 */     String mac = (String)session.getAttribute("mac");
/*  418 */     String ssid = gw_id;
/*  419 */     String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */ 
/*  421 */     String url = (String)session.getAttribute("url");
/*      */     String basName;
/*  423 */     if (stringUtils.isNotBlank(gw_id))
/*      */       try
/*      */       {
/*  426 */         Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
/*  427 */         while (iteratorConfig.hasNext()) {
/*  428 */           Object o = iteratorConfig.next();
/*  429 */           String t = o.toString();
/*  430 */           Portalbas bas = (Portalbas)config.getConfigMap().get(t);
/*  431 */           basName = bas.getBasname();
/*  432 */           if ((stringUtils.isNotBlank(basName)) && 
/*  433 */             (basName.equals(gw_id))) {
/*  434 */             basConfig = bas;
/*  435 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  440 */         basConfig = (Portalbas)config.getConfigMap().get("");
/*      */       }
/*      */     try
/*      */     {
/*  444 */       String web = (String)session.getAttribute("web");
/*  445 */       if (stringUtils.isNotBlank(web)) {
/*  446 */         while (web.endsWith("/")) {
/*  447 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  449 */         Long webID = Long.valueOf(web);
/*  450 */         if (webID.longValue() != 0L) {
/*  451 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  452 */           if (portalweb != null) {
/*  453 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  454 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  457 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  458 */           if (config != null) {
/*  459 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  460 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  464 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  465 */         if (config != null) {
/*  466 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  467 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1) {
/*      */     }
/*  473 */     String goUrl = url;
/*  474 */     PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  475 */     bsq.setBasip(basConfig.getBasIp());
/*  476 */     List<Portalbasauth> basauths = this.portalbasauthService
/*  477 */       .getPortalbasauthList(bsq);
/*  478 */     if (basauths.size() > 0) {
/*  479 */       for (Portalbasauth ba : basauths) {
/*  480 */         if (ba.getType().intValue() == 5) {
/*  481 */           goUrl = ba.getUrl();
/*  482 */           if (!stringUtils.isBlank(goUrl)) break;
/*  483 */           goUrl = url;
/*      */ 
/*  485 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  489 */     if (stringUtils.isBlank(goUrl)) {
/*  490 */       goUrl = 
/*  491 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  491 */         .get("core"))[0];
/*      */     }
/*  493 */     url = goUrl;
/*  494 */     session.setAttribute("url", url);
/*      */ 
/*  496 */     String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);
/*      */ 
/*  498 */     if (basConfig.getAuthInterface().contains("5"))
/*      */     {
/*  500 */       session.setAttribute("username", "微信验证");
/*  501 */       session.setAttribute("ip", ip);
/*  502 */       session.setAttribute("token", token);
/*      */ 
/*  504 */       String[] loginInfo = new String[11];
/*  505 */       loginInfo[0] = "微信验证";
/*  506 */       Date now = new Date();
/*  507 */       loginInfo[3] = ThreadLocalDateUtil.format(now);
/*  508 */       loginInfo[4] = ip;
/*  509 */       loginInfo[5] = mac;
/*  510 */       loginInfo[6] = gw_id;
/*  511 */       loginInfo[7] = gw_address;
/*  512 */       loginInfo[8] = "0";
/*  513 */       loginInfo[9] = "0";
/*  514 */       loginInfo[10] = "weixin";
/*      */ 
/*  516 */       WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
/*  517 */         .put(token, loginInfo);
/*  518 */       WeixinMap.getInstance().getWeixinipmap()
/*  519 */         .put("wifidog" + token.trim(), now);
/*      */ 
/*  521 */       Long time = Long.valueOf(now.getTime());
/*      */ 
/*  523 */       Portalweixinwifi wifi = null;
/*  524 */       if (stringUtils.isNotBlank(ssid)) {
/*  525 */         PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/*  526 */         wxq.setSsid(ssid);
/*  527 */         wxq.setSsidLike(false);
/*  528 */         wxq.setBasip(gw_address);
/*  529 */         wxq.setBasipLike(false);
/*  530 */         List wxs = this.weixinwifiService
/*  531 */           .getPortalweixinwifiList(wxq);
/*  532 */         if (wxs.size() > 0) {
/*  533 */           wifi = (Portalweixinwifi)wxs.get(0);
/*      */         } else {
/*  535 */           wxq = new PortalweixinwifiQuery();
/*  536 */           wxq.setSsid(ssid);
/*  537 */           wxq.setSsidLike(false);
/*  538 */           wxs = this.weixinwifiService.getPortalweixinwifiList(wxq);
/*  539 */           if (wxs.size() > 0) {
/*  540 */             wifi = (Portalweixinwifi)wxs.get(0);
/*      */           }
/*      */         }
/*      */       }
/*  544 */       if (wifi == null) {
/*  545 */         PortalweixinwifiQuery wxq = new PortalweixinwifiQuery();
/*  546 */         wxq.setBasip(gw_address);
/*  547 */         wxq.setBasipLike(false);
/*  548 */         List wxs = this.weixinwifiService
/*  549 */           .getPortalweixinwifiList(wxq);
/*  550 */         if (wxs.size() > 0) {
/*  551 */           wifi = (Portalweixinwifi)wxs.get(0);
/*      */         }
/*      */       }
/*  554 */       if (wifi == null) {
/*  555 */         wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */       }
/*      */ 
/*  558 */       if (!AjaxInterFaceController.Do()) {
/*  559 */         wifi = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L));
/*      */       }
/*      */ 
/*  562 */       if (wifi != null) {
/*  563 */         String appId = wifi.getAppId();
/*  564 */         String extend = "wifidog" + token.trim();
/*  565 */         String timestamp = String.valueOf(time);
/*  566 */         String shop_id = wifi.getShopId();
/*      */ 
/*  568 */         String authUrl = request.getScheme() + "://" + 
/*  569 */           request.getServerName() + ":" + 
/*  570 */           request.getServerPort() + request.getContextPath() + "/weixinAuth.action";
/*  571 */         ssid = wifi.getSsid();
/*  572 */         String secretKey = wifi.getSecretKey();
/*  573 */         mac = "";
/*  574 */         String bssid = "";
/*      */ 
/*  576 */         String code = appId + extend + timestamp + shop_id + authUrl + 
/*  577 */           mac + ssid + bssid + secretKey;
/*  578 */         String sign = DigestUtils.md5Hex(code);
/*      */ 
/*  580 */         if (basConfig.getIsdebug().equals("1")) {
/*  581 */           logger.info("finally ssid=" + ssid + " token=" + token + 
/*  582 */             " ip=" + ip + " code=" + code + " sign" + sign);
/*      */         }
/*      */ 
/*  585 */         map.put("ret", "0");
/*  586 */         map.put("appId", appId);
/*  587 */         map.put("extend", extend);
/*  588 */         map.put("timestamp", timestamp);
/*  589 */         map.put("sign", sign);
/*  590 */         map.put("shop_id", shop_id);
/*  591 */         map.put("authUrl", authUrl);
/*  592 */         map.put("mac", mac);
/*  593 */         map.put("ssid", ssid);
/*  594 */         map.put("bssid", bssid);
/*      */ 
/*  596 */         map.put("gw_address", gw_address);
/*  597 */         map.put("gw_port", gw_port);
/*  598 */         map.put("token", token);
/*      */ 
/*  600 */         session.setAttribute("appId", appId);
/*  601 */         session.setAttribute("extend", extend);
/*  602 */         session.setAttribute("timestamp", timestamp);
/*  603 */         session.setAttribute("sign", sign);
/*  604 */         session.setAttribute("shop_id", shop_id);
/*  605 */         session.setAttribute("authUrl", authUrl);
/*  606 */         session.setAttribute("mac", mac);
/*  607 */         session.setAttribute("ssid", ssid);
/*  608 */         session.setAttribute("bssid", bssid);
/*      */ 
/*  610 */         session.setAttribute("token", token);
/*      */ 
/*  612 */         return map;
/*      */       }
/*  614 */       map.put("ret", "1");
/*  615 */       return map;
/*      */     }
/*      */ 
/*  619 */     map.put("ret", "3");
/*  620 */     return map;
/*      */   }
/*      */ 
/*      */   private Map<String, Object> checkLocalAccount(String username, String password)
/*      */   {
/*  630 */     PortalaccountQuery accountqQuery = new PortalaccountQuery();
/*  631 */     accountqQuery.setLoginNameLike(false);
/*  632 */     accountqQuery.setPasswordLike(false);
/*  633 */     accountqQuery.setLoginName(username);
/*  634 */     accountqQuery.setPassword(password);
/*  635 */     List accounts = this.accountService
/*  636 */       .getPortalaccountList(accountqQuery);
/*      */ 
/*  638 */     Map map = new HashMap();
/*  639 */     if (accounts.size() != 0) {
/*  640 */       Portalaccount account = (Portalaccount)accounts.get(0);
/*  641 */       if (password.equals(account.getPassword())) {
/*  642 */         map.put("id", account.getId());
/*  643 */         map.put("state", account.getState());
/*  644 */         map.put("date", account.getDate());
/*  645 */         map.put("time", account.getTime());
/*  646 */         if (account.getOctets() == null)
/*  647 */           map.put("octets", Long.valueOf(0L));
/*      */         else {
/*  649 */           map.put("octets", account.getOctets());
/*      */         }
/*  651 */         map.put("result", Integer.valueOf(1));
/*  652 */         return map;
/*      */       }
/*      */     }
/*  655 */     map.put("result", Integer.valueOf(0));
/*  656 */     return map;
/*      */   }
/*      */ 
/*      */   private boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets)
/*      */   {
/*  669 */     Portalaccount account = this.accountService.getPortalaccountByKey(userId);
/*      */ 
/*  671 */     if (userState.equals("0")) {
/*  672 */       return false;
/*      */     }
/*      */ 
/*  675 */     if (userState.equals("1")) {
/*  676 */       return true;
/*      */     }
/*      */ 
/*  679 */     if (userState.equals("3")) {
/*  680 */       Date now = new Date();
/*  681 */       if (userDate.getTime() > now.getTime()) {
/*  682 */         return true;
/*      */       }
/*  684 */       account.setState("2");
/*  685 */       this.accountService.updatePortalaccountByKey(account);
/*  686 */       userState = "2";
/*      */     }
/*      */ 
/*  690 */     if (userState.equals("2")) {
/*  691 */       if (userTime.longValue() > 0L) {
/*  692 */         return true;
/*      */       }
/*  694 */       account.setState("4");
/*  695 */       this.accountService.updatePortalaccountByKey(account);
/*  696 */       userState = "4";
/*      */     }
/*      */ 
/*  700 */     if (userState.equals("4")) {
/*  701 */       if (octets.longValue() > 0L) {
/*  702 */         return true;
/*      */       }
/*  704 */       account.setState("0");
/*  705 */       this.accountService.updatePortalaccountByKey(account);
/*  706 */       return false;
/*      */     }
/*      */ 
/*  709 */     return false;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_wifidog_LoginOut"})
/*      */   public Map<String, String> LoginOut(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  717 */     Map map = new HashMap();
/*      */ 
/*  719 */     HttpSession session = request.getSession();
/*  720 */     String token = (String)session.getAttribute("token");
/*      */ 
/*  722 */     if (stringUtils.isNotBlank(token)) {
/*  723 */       String[] loginInfo = (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().get(token);
/*      */ 
/*  727 */       WifidogKick.offLine(token);
/*      */     }
/*      */ 
/*  730 */     map.put("ret", "0");
/*  731 */     map.put("msg", "下线成功！");
/*  732 */     session.removeAttribute("username");
/*  733 */     session.removeAttribute("password");
/*  734 */     session.removeAttribute("ip");
/*  735 */     session.removeAttribute("token");
/*  736 */     return map;
/*      */   }
/*      */ 
/*      */   @ResponseBody
/*      */   @RequestMapping({"/ajax_wifidog_Login"})
/*      */   public Map<String, String> Login(String usr, String pwd, HttpServletRequest request, HttpServletResponse response) throws IOException {
/*  743 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */ 
/*  745 */     Map map = new HashMap();
/*  746 */     HttpSession session = request.getSession();
/*  747 */     String gw_id = (String)session.getAttribute("gw_id");
/*  748 */     String gw_address = (String)session.getAttribute("gw_address");
/*  749 */     String gw_port = (String)session.getAttribute("gw_port");
/*  750 */     String mac = (String)session.getAttribute("mac");
/*      */ 
/*  752 */     String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*      */ 
/*  754 */     String url = (String)session.getAttribute("url");
/*      */ 
/*  756 */     if (stringUtils.isNotBlank(gw_id))
/*      */       try
/*      */       {
/*  759 */         Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
/*  760 */         while (iteratorConfig.hasNext()) {
/*  761 */           Object o = iteratorConfig.next();
/*  762 */           String t = o.toString();
/*  763 */           Portalbas bas = (Portalbas)config.getConfigMap().get(t);
/*  764 */           String basName = bas.getBasname();
/*  765 */           if ((stringUtils.isNotBlank(basName)) && 
/*  766 */             (basName.equals(gw_id))) {
/*  767 */             basConfig = bas;
/*  768 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  773 */         basConfig = (Portalbas)config.getConfigMap().get("");
/*      */       }
/*      */     try
/*      */     {
/*  777 */       String web = (String)session.getAttribute("web");
/*  778 */       if (stringUtils.isNotBlank(web)) {
/*  779 */         while (web.endsWith("/")) {
/*  780 */           web = web.substring(0, web.length() - 1);
/*      */         }
/*  782 */         Long webID = Long.valueOf(web);
/*  783 */         if (webID.longValue() != 0L) {
/*  784 */           Portalweb portalweb = this.webService.getPortalwebByKey(webID);
/*  785 */           if (portalweb != null) {
/*  786 */             portalweb.setCountAuth(Long.valueOf(portalweb.getCountAuth().longValue() + 1L));
/*  787 */             this.webService.updatePortalwebByKey(portalweb);
/*      */           }
/*      */         } else {
/*  790 */           com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  791 */           if (config != null) {
/*  792 */             config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  793 */             this.configService.updateConfigByKey(config);
/*      */           }
/*      */         }
/*      */       } else {
/*  797 */         com.leeson.core.bean.Config config = this.configService.getConfigByKey(Long.valueOf(1L));
/*  798 */         if (config != null) {
/*  799 */           config.setCountAuth(Long.valueOf(config.getCountAuth().longValue() + 1L));
/*  800 */           this.configService.updateConfigByKey(config);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/*  807 */     String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + mac);
/*      */ 
/*  810 */     if ((basConfig.getAuthInterface().contains("0")) && 
/*  811 */       (stringUtils.isBlank(usr)) && (stringUtils.isBlank(pwd)))
/*      */     {
/*  813 */       session.setAttribute("username", "一键认证");
/*  814 */       session.setAttribute("ip", ip);
/*  815 */       session.setAttribute("token", token);
/*      */ 
/*  817 */       String[] loginInfo = new String[10];
/*  818 */       loginInfo[0] = "一键认证";
/*  819 */       Date now = new Date();
/*  820 */       loginInfo[3] = ThreadLocalDateUtil.format(now);
/*  821 */       loginInfo[4] = ip;
/*  822 */       loginInfo[5] = mac;
/*  823 */       loginInfo[6] = gw_id;
/*  824 */       loginInfo[7] = gw_address;
/*  825 */       loginInfo[8] = "0";
/*  826 */       loginInfo[9] = "0";
/*      */ 
/*  828 */       WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
/*  829 */         .put(token, loginInfo);
/*      */ 
/*  831 */       map.put("ret", "0");
/*  832 */       map.put("i", ip);
/*  833 */       map.put("u", "一键认证");
/*  834 */       map.put("msg", "认证成功！");
/*  835 */       String goUrl = url;
/*  836 */       PortalbasauthQuery bsq = new PortalbasauthQuery();
/*  837 */       bsq.setBasip(basConfig.getBasIp());
/*  838 */       List<Portalbasauth> basauths = this.portalbasauthService
/*  839 */         .getPortalbasauthList(bsq);
/*  840 */       if (basauths.size() > 0) {
/*  841 */         for (Portalbasauth ba : basauths) {
/*  842 */           if (ba.getType().intValue() == 0) {
/*  843 */             goUrl = ba.getUrl();
/*  844 */             if (!stringUtils.isBlank(goUrl)) break;
/*  845 */             goUrl = url;
/*      */ 
/*  847 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  851 */       if (stringUtils.isBlank(goUrl)) {
/*  852 */         goUrl = 
/*  853 */           ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/*  853 */           .get("core"))[0];
/*      */       }
/*  855 */       url = goUrl;
/*  856 */       session.setAttribute("url", url);
/*  857 */       map.put("l", url);
/*      */ 
/*  859 */       map.put("gw_address", gw_address);
/*  860 */       map.put("gw_port", gw_port);
/*  861 */       map.put("token", token);
/*  862 */       return map;
/*      */     }
/*      */ 
/*  866 */     if ((stringUtils.isBlank(usr)) || (stringUtils.isBlank(pwd))) {
/*  867 */       map.put("ret", "1");
/*  868 */       map.put("i", ip);
/*  869 */       map.put("u", "");
/*  870 */       map.put("msg", "用户名和密码不能为空！！");
/*  871 */       if (stringUtils.isBlank(url)) {
/*  872 */         url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */       }
/*  874 */       map.put("l", url);
/*  875 */       return map;
/*      */     }
/*      */ 
/*  878 */     String username = usr;
/*  879 */     String password = pwd;
/*  880 */     if (basConfig.getIsdebug().equals("1")) {
/*  881 */       logger.info("请求认证    用户：" + username + " IP地址:" + ip);
/*      */     }
/*      */ 
/*  888 */     String userState = "";
/*  889 */     Long userId = Long.valueOf(0L);
/*  890 */     Date userDate = new Date();
/*  891 */     Long userTime = Long.valueOf(0L);
/*  892 */     Long octets = Long.valueOf(0L);
/*      */     String userMac;
/*  895 */     if (basConfig.getAuthInterface().contains("1"))
/*      */     {
/*  897 */       Map resultCheck = checkLocalAccount(username, 
/*  898 */         password);
/*  899 */       int state = ((Integer)resultCheck.get("result")).intValue();
/*      */ 
/*  901 */       if (state == 0) {
/*  902 */         map.put("ret", "1");
/*  903 */         map.put("i", ip);
/*  904 */         map.put("u", username);
/*  905 */         map.put("msg", "用户名密码错误,或账户已过期！！");
/*  906 */         if (stringUtils.isBlank(url)) {
/*  907 */           url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */         }
/*  909 */         map.put("l", url);
/*  910 */         return map;
/*      */       }
/*      */ 
/*  913 */       userState = (String)resultCheck.get("state");
/*  914 */       userId = (Long)resultCheck.get("id");
/*  915 */       userDate = (Date)resultCheck.get("date");
/*  916 */       userTime = (Long)resultCheck.get("time");
/*  917 */       octets = (Long)resultCheck.get("octets");
/*      */ 
/*  919 */       if (!checkTimeOut(userState, userId, userDate, userTime, octets)) {
/*  920 */         map.put("ret", "1");
/*  921 */         map.put("i", ip);
/*  922 */         map.put("u", username);
/*  923 */         map.put("msg", "账户已过期，请及时联系管理员充值！！");
/*  924 */         if (stringUtils.isBlank(url)) {
/*  925 */           url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */         }
/*  927 */         map.put("l", url);
/*  928 */         return map;
/*      */       }
/*      */ 
/*  931 */       Portalaccount acc = this.accountService.getPortalaccountByKey(userId);
/*  932 */       if (acc != null) {
/*  933 */         Integer limitCount = acc.getMaclimitcount();
/*  934 */         if ((limitCount != null) && (limitCount.intValue() > 0)) {
/*  935 */           int count = 0;
/*  936 */           Iterator iterator = OnlineMap.getInstance()
/*  937 */             .getOnlineUserMap().keySet()
/*  938 */             .iterator();
/*  939 */           while (iterator.hasNext()) {
/*  940 */             Object o = iterator.next();
/*  941 */             String t = o.toString();
/*  942 */             String[] loginInfo = 
/*  943 */               (String[])OnlineMap.getInstance()
/*  943 */               .getOnlineUserMap().get(t);
/*  944 */             String haveUsername = loginInfo[0];
/*  945 */             if (username.equals(haveUsername)) {
/*  946 */               count++;
/*      */             }
/*      */           }
/*  949 */           if (count >= limitCount.intValue()) {
/*  950 */             map.put("ret", "1");
/*  951 */             map.put("i", ip);
/*  952 */             map.put("u", username);
/*  953 */             map.put("msg", "该账户同时在线数已超出限制，请等待使用该账户的其他设备下线！！");
/*  954 */             return map;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  960 */       int limitMac = acc.getMaclimit().intValue();
/*  961 */       int limitCount = acc.getMaclimitcount().intValue();
/*  962 */       int auto = acc.getAutologin().intValue();
/*      */ 
/*  964 */       userMac = mac;
/*      */ 
/*  966 */       if (limitMac == 1) {
/*  967 */         if (stringUtils.isBlank(userMac))
/*      */         {
/*  969 */           map.put("ret", "1");
/*  970 */           map.put("i", ip);
/*  971 */           map.put("u", username);
/*  972 */           map.put("msg", "协议不支持MAC绑定，请联系管理员修改账户属性！！");
/*  973 */           if (stringUtils.isBlank(url)) {
/*  974 */             url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */           }
/*  976 */           map.put("l", url);
/*  977 */           return map;
/*      */         }
/*      */ 
/*  980 */         PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/*  981 */         mq.setAccountId(userId);
/*  982 */         List<Portalaccountmacs> accountmacs = this.macsService
/*  983 */           .getPortalaccountmacsList(mq);
/*  984 */         if ((accountmacs.size() < 1) || (limitCount == 0) || (accountmacs.size() < limitCount))
/*      */         {
/*  986 */           if ((stringUtils.isNotBlank(userMac)) && 
/*  987 */             (!"error".equals(userMac))) {
/*  988 */             Boolean isHave = Boolean.valueOf(false);
/*  989 */             for (Portalaccountmacs amacs : accountmacs)
/*      */             {
/*  991 */               if (amacs.getMac().toLowerCase()
/*  991 */                 .equals(userMac)) {
/*  992 */                 isHave = Boolean.valueOf(true);
/*  993 */                 break;
/*      */               }
/*      */             }
/*  996 */             if (!isHave.booleanValue()) {
/*  997 */               Portalaccountmacs macs = new Portalaccountmacs();
/*  998 */               macs.setAccountId(userId);
/*  999 */               macs.setMac(userMac);
/* 1000 */               this.macsService.addPortalaccountmacs(macs);
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1006 */           Boolean isHave = Boolean.valueOf(false);
/* 1007 */           for (Portalaccountmacs amacs : accountmacs) {
/* 1008 */             if (amacs.getMac().toLowerCase().equals(userMac)) {
/* 1009 */               isHave = Boolean.valueOf(true);
/* 1010 */               break;
/*      */             }
/*      */           }
/* 1013 */           if (!isHave.booleanValue())
/*      */           {
/* 1015 */             map.put("ret", "1");
/* 1016 */             map.put("i", ip);
/* 1017 */             map.put("u", username);
/* 1018 */             map.put("msg", "此账号没有绑定该设备，请联系管理员！！");
/* 1019 */             if (stringUtils.isBlank(url)) {
/* 1020 */               url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*      */             }
/* 1022 */             map.put("l", url);
/* 1023 */             return map;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1028 */       if ((auto == 1) && 
/* 1029 */         (stringUtils.isNotBlank(userMac))) {
/* 1030 */         PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/* 1031 */         mq.setAccountId(userId);
/* 1032 */         List<Portalaccountmacs> accountmacs = this.macsService
/* 1033 */           .getPortalaccountmacsList(mq);
/* 1034 */         if ((limitCount == 0) || (accountmacs.size() < limitCount)) {
/* 1035 */           boolean macNotHave = true;
/* 1036 */           for (Portalaccountmacs macs : accountmacs) {
/* 1037 */             if (userMac.equals(macs.getMac())) {
/* 1038 */               macNotHave = false;
/*      */             }
/*      */           }
/* 1041 */           if (macNotHave) {
/* 1042 */             Portalaccountmacs macs = new Portalaccountmacs();
/* 1043 */             macs.setAccountId(userId);
/* 1044 */             macs.setMac(userMac);
/* 1045 */             this.macsService.addPortalaccountmacs(macs);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1056 */        userMac = mac;
/*      */ 
/* 1058 */       if (stringUtils.isNotBlank(userMac))
/*      */       {
/* 1060 */         boolean macHave = false;
/* 1061 */         List<Portalaccountmacs> accountmacs = this.macsService
/* 1062 */           .getPortalaccountmacsList(new PortalaccountmacsQuery());
/* 1063 */         for (Portalaccountmacs macs : accountmacs) {
/* 1064 */           if (macs.getMac().equals(userMac)) {
/* 1065 */             macHave = true;
/* 1066 */             break;
/*      */           }
/*      */         }
/* 1069 */         if (!macHave) {
/* 1070 */           List accs = this.accountService.getPortalaccountList(new PortalaccountQuery());
/* 1071 */           if ((accs.size() > 0) && 
/* 1072 */             (((Portalaccount)accs.get(0)).getAutologin().intValue() == 1)) {
/* 1073 */             Portalaccountmacs accMac = new Portalaccountmacs();
/* 1074 */             accMac.setAccountId(((Portalaccount)accs.get(0)).getId());
/* 1075 */             accMac.setMac(userMac);
/* 1076 */             this.macsService.addPortalaccountmacs(accMac);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1090 */     if (basConfig.getAuthInterface()
/* 1090 */       .contains("1")) {
/* 1091 */       session.setAttribute("password", password);
/*      */     }
/*      */ 
/* 1094 */     if (stringUtils.isNotBlank(mac)) {
/* 1095 */       String[] userinfo = new String[2];
/* 1096 */       userinfo[0] = username;
/* 1097 */       userinfo[1] = password;
/* 1098 */       AutoLoginMap.getInstance().getAutoLoginMap()
/* 1099 */         .put(mac, userinfo);
/*      */     }
/*      */ 
/* 1103 */     session.setAttribute("username", username);
/* 1104 */     session.setAttribute("ip", ip);
/* 1105 */     session.setAttribute("token", token);
/*      */ 
/* 1107 */     String[] loginInfo = new String[10];
/* 1108 */     loginInfo[0] = username;
/* 1109 */     loginInfo[1] = String.valueOf(userId);
/* 1110 */     Date now = new Date();
/* 1111 */     loginInfo[3] = ThreadLocalDateUtil.format(now);
/* 1112 */     loginInfo[4] = ip;
/* 1113 */     loginInfo[5] = mac;
/* 1114 */     loginInfo[6] = gw_id;
/* 1115 */     loginInfo[7] = gw_address;
/* 1116 */     loginInfo[8] = "0";
/* 1117 */     loginInfo[9] = "0";
/*      */ 
/* 1119 */     WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
/* 1120 */       .put(token, loginInfo);
/*      */ 
/* 1122 */     map.put("ret", "0");
/* 1123 */     map.put("i", ip);
/* 1124 */     map.put("u", username);
/* 1125 */     map.put("msg", "认证成功！");
/* 1126 */     String goUrl = url;
/* 1127 */     PortalbasauthQuery bsq = new PortalbasauthQuery();
/* 1128 */     bsq.setBasip(basConfig.getBasIp());
/* 1129 */     List<Portalbasauth> basauths = this.portalbasauthService
/* 1130 */       .getPortalbasauthList(bsq);
/* 1131 */     if (basauths.size() > 0) {
/* 1132 */       for (Portalbasauth ba : basauths) {
/* 1133 */         if (ba.getType().intValue() == 1) {
/* 1134 */           goUrl = ba.getUrl();
/* 1135 */           if (!stringUtils.isBlank(goUrl)) break;
/* 1136 */           goUrl = url;
/*      */ 
/* 1138 */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1142 */     if (stringUtils.isBlank(goUrl)) {
/* 1143 */       goUrl = 
/* 1144 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 1144 */         .get("core"))[0];
/*      */     }
/* 1146 */     url = goUrl;
/* 1147 */     session.setAttribute("url", url);
/*      */ 
/* 1149 */     map.put("gw_address", gw_address);
/* 1150 */     map.put("gw_port", gw_port);
/* 1151 */     map.put("token", token);
/* 1152 */     return map;
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxInterFaceWifiDogController
 * JD-Core Version:    0.6.2
 */