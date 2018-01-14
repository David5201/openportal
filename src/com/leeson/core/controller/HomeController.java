/*      */ package com.leeson.core.controller;
/*      */ 
/*      */ import com.leeson.common.page.Pagination;
/*      */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.common.utils.sysinfo.MonitorInfoBean;
/*      */ import com.leeson.common.utils.sysinfo.MonitorService;
/*      */ import com.leeson.core.bean.Portalap;
/*      */ import com.leeson.core.bean.Portalbas;
/*      */ import com.leeson.core.bean.Portalorder;
/*      */ import com.leeson.core.bean.Portalssid;
/*      */ import com.leeson.core.bean.Portaluser;
/*      */ import com.leeson.core.bean.Portalweb;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalapQuery;
/*      */ import com.leeson.core.query.PortalbasQuery;
/*      */ import com.leeson.core.query.PortallinkrecordallQuery;
/*      */ import com.leeson.core.query.PortallogrecordQuery;
/*      */ import com.leeson.core.query.PortalmessageQuery;
/*      */ import com.leeson.core.query.PortalorderQuery;
/*      */ import com.leeson.core.query.PortalssidQuery;
/*      */ import com.leeson.core.query.PortalwebQuery;
/*      */ import com.leeson.core.query.PortalweixinwifiQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalapService;
/*      */ import com.leeson.core.service.PortalbasService;
/*      */ import com.leeson.core.service.PortallinkrecordallService;
/*      */ import com.leeson.core.service.PortallogrecordService;
/*      */ import com.leeson.core.service.PortalmessageService;
/*      */ import com.leeson.core.service.PortalorderService;
/*      */ import com.leeson.core.service.PortalssidService;
/*      */ import com.leeson.core.service.PortaluserService;
/*      */ import com.leeson.core.service.PortalwebService;
/*      */ import com.leeson.core.service.PortalweixinwifiService;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*      */ import com.leeson.portal.core.model.WySlot15gasa;
/*      */ import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
/*      */ import com.leeson.portal.core.utils.AaHpl8Ha9bNPen9OLddV;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Controller;
/*      */ import org.springframework.ui.ModelMap;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.ResponseBody;
/*      */ 
/*      */ @Controller
/*      */ public class HomeController
/*      */ {
/*      */ 
/*      */   @Autowired
/*      */   private PortaluserService portaluserService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalmessageService portalmessageService;
/*      */ 
/*      */   @Autowired
/*      */   private PortallogrecordService portallogrecordService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountService portalaccountService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalweixinwifiService portalweixinwifiService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalbasService portalbasService;
/*      */ 
/*      */   @Autowired
/*      */   private PortallinkrecordallService portallinkrecordallService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalorderService portalorderService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalssidService portalssidService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalapService portalapService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalwebService portalwebService;
/*      */ 
/*      */   @Autowired
/*      */   private ConfigService configService;
/*   96 */   private static DecimalFormat df = new DecimalFormat(".##");
/*      */ 
/*      */   @RequestMapping({"/homeAction/showCount.action"})
/*      */   public String showCount(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*      */   {
/*  102 */     linkCount(model);
/*  103 */     moneyCount(model);
/*  104 */     authCount(model);
/*  105 */     ssidCount(model);
/*  106 */     apCount(model);
/*  107 */     webCount(model);
/*      */ 
/*  110 */     return "homeAction/showCount";
/*      */   }
/*      */   @ResponseBody
/*      */   @RequestMapping({"/get_ajax_allCount.action"})
/*      */   public Map<String, Object> get_ajax_allCount(HttpServletRequest request, HttpServletResponse response) {
/*  116 */     Map map = new HashMap();
/*  117 */     linkCount(map);
/*  118 */     moneyCount(map);
/*  119 */     authCount(map);
/*      */ 
/*  121 */     ssidCount(map);
/*  122 */     apCount(map);
/*  123 */     webCount(map);
/*  124 */     return map;
/*      */   }
/*      */ 
/*      */   private void ssidCount(Map<String, Object> resultMap) {
/*  128 */     PortalssidQuery q = new PortalssidQuery();
/*  129 */     q.setFields("ssid,count");
/*  130 */     List<Portalssid> ssids = this.portalssidService
/*  131 */       .getPortalssidList(q);
/*  132 */     Map map = new HashMap();
/*  133 */     for (Portalssid ssid : ssids) {
/*  134 */       map.put(ssid.getSsid(), ssid.getCount());
/*      */     }
/*  136 */     resultMap.put("ssids", map);
/*      */   }
/*      */ 
/*      */   private void apCount(Map<String, Object> resultMap) {
/*  140 */     PortalapQuery q = new PortalapQuery();
/*  141 */     q.setFields("mac,count");
/*  142 */     List<Portalap> aps = this.portalapService
/*  143 */       .getPortalapList(q);
/*  144 */     Map map = new HashMap();
/*  145 */     for (Portalap ap : aps) {
/*  146 */       map.put(ap.getMac(), ap.getCount());
/*      */     }
/*  148 */     resultMap.put("aps", map);
/*      */   }
/*      */ 
/*      */   private void webCount(Map<String, Object> resultMap) {
/*  152 */     com.leeson.core.bean.Config baseWeb = this.configService.getConfigByKey(Long.valueOf(1L));
/*  153 */     resultMap.put("baseShow", baseWeb.getCountShow());
/*  154 */     resultMap.put("baseAuth", baseWeb.getCountAuth());
/*  155 */     PortalwebQuery q = new PortalwebQuery();
/*  156 */     q.setFields("name,countShow,countAuth");
/*  157 */     List<Portalweb> webs = this.portalwebService
/*  158 */       .getPortalwebList(q);
/*  159 */     Map map = new HashMap();
/*  160 */     for (Portalweb web : webs) {
/*  161 */       Long[] counts = new Long[2];
/*  162 */       counts[0] = web.getCountShow();
/*  163 */       counts[1] = web.getCountAuth();
/*  164 */       map.put(web.getName(), counts);
/*      */     }
/*  166 */     resultMap.put("webs", map);
/*      */   }
/*      */ 
/*      */   private void linkCount(Map<String, Object> resultMap)
/*      */   {
/*  273 */     int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();
/*      */ 
/*  275 */     int linkAll = this.portallinkrecordallService
/*  276 */       .getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue() + 
/*  277 */       onlineCount;
/*  278 */     int linkAllUp = linkAll / 7;
/*  279 */     resultMap.put("linkAll", Integer.valueOf(linkAll));
/*  280 */     resultMap.put("linkAllUp", Integer.valueOf(linkAllUp));
/*      */ 
/*  282 */     PortallinkrecordallQuery linkTodayQuery = new PortallinkrecordallQuery();
/*  283 */     Date now = new Date();
/*  284 */     Date today = new Date(now.getYear(), now.getMonth(), now.getDate());
/*  285 */     linkTodayQuery.setBegin_time(today);
/*  286 */     int linkToday = this.portallinkrecordallService
/*  287 */       .getPortallinkrecordallCount(linkTodayQuery).intValue();
/*  288 */     Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/*  289 */     while (iteratorOnline.hasNext()) {
/*  290 */       Object o = iteratorOnline.next();
/*  291 */       String t = o.toString();
/*  292 */       String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
/*      */       try {
/*  294 */         String time = loginInfo[3];
/*  295 */         Date loginTime = ThreadLocalDateUtil.parse(time);
/*  296 */         if (loginTime.getTime() >= today.getTime())
/*  297 */           linkToday++;
/*      */       }
/*      */       catch (Exception localException) {
/*      */       }
/*      */     }
/*  302 */     int linkTodayUp = linkToday / 7;
/*  303 */     resultMap.put("linkToday", Integer.valueOf(linkToday));
/*  304 */     resultMap.put("linkTodayUp", Integer.valueOf(linkTodayUp));
/*      */ 
/*  306 */     PortallinkrecordallQuery linkWeekQuery = new PortallinkrecordallQuery();
/*  307 */     linkWeekQuery.setBegin_time(new Date(new Date().getTime() - 604800000L));
/*      */ 
/*  309 */     int linkWeek = this.portallinkrecordallService
/*  310 */       .getPortallinkrecordallCount(linkWeekQuery).intValue() + onlineCount;
/*  311 */     int linkWeekUp = linkWeek / 7;
/*  312 */     resultMap.put("linkWeek", Integer.valueOf(linkWeek));
/*  313 */     resultMap.put("linkWeekUp", Integer.valueOf(linkWeekUp));
/*      */ 
/*  315 */     PortallinkrecordallQuery linkMonthQuery = new PortallinkrecordallQuery();
/*  316 */     linkMonthQuery.setBegin_time(new Date(new Date().getTime() - 2592000000L));
/*      */ 
/*  318 */     int linkMonth = this.portallinkrecordallService
/*  319 */       .getPortallinkrecordallCount(linkMonthQuery).intValue() + onlineCount;
/*  320 */     int linkMonthUp = linkMonth / 7;
/*  321 */     resultMap.put("linkMonth", Integer.valueOf(linkMonth));
/*  322 */     resultMap.put("linkMonthUp", Integer.valueOf(linkMonthUp));
/*      */   }
/*      */ 
/*      */   private void moneyCount(Map<String, Object> resultMap) {
/*  326 */     Double money = Double.valueOf(0.0D);
/*  327 */     String moneyS = "0.00";
/*      */ 
/*  329 */     PortalorderQuery q = new PortalorderQuery();
/*  330 */     q.setFields("money");
/*  331 */     List<Portalorder> orders = this.portalorderService
/*  332 */       .getPortalorderList(q);
/*  333 */     for (Portalorder order : orders) {
/*  334 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  336 */     moneyS = df.format(money);
/*  337 */     if (moneyS.startsWith(".")) {
/*  338 */       moneyS = "0" + moneyS;
/*      */     }
/*  340 */     resultMap.put("moneyAll", moneyS);
/*      */ 
/*  342 */     money = Double.valueOf(0.0D);
/*  343 */     moneyS = "0.00";
/*  344 */     Date now = new Date();
/*  345 */     q.setBegin_time1(new Date(now.getYear(), now.getMonth(), now.getDate()));
/*  346 */     orders = this.portalorderService.getPortalorderList(q);
/*  347 */     for (Portalorder order : orders) {
/*  348 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  350 */     moneyS = df.format(money);
/*  351 */     if (moneyS.startsWith(".")) {
/*  352 */       moneyS = "0" + moneyS;
/*      */     }
/*  354 */     resultMap.put("moneyToday", moneyS);
/*      */ 
/*  356 */     money = Double.valueOf(0.0D);
/*  357 */     moneyS = "0.00";
/*  358 */     q.setBegin_time1(new Date(now.getTime() - 604800000L));
/*      */ 
/*  360 */     orders = this.portalorderService.getPortalorderList(q);
/*  361 */     for (Portalorder order : orders) {
/*  362 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  364 */     moneyS = df.format(money);
/*  365 */     if (moneyS.startsWith(".")) {
/*  366 */       moneyS = "0" + moneyS;
/*      */     }
/*  368 */     resultMap.put("moneyWeek", moneyS);
/*      */ 
/*  370 */     money = Double.valueOf(0.0D);
/*  371 */     moneyS = "0.00";
/*  372 */     q.setBegin_time1(new Date(now.getTime() - 2592000000L));
/*      */ 
/*  374 */     orders = this.portalorderService.getPortalorderList(q);
/*  375 */     for (Portalorder order : orders) {
/*  376 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  378 */     moneyS = df.format(money);
/*  379 */     if (moneyS.startsWith(".")) {
/*  380 */       moneyS = "0" + moneyS;
/*      */     }
/*  382 */     resultMap.put("moneyMonth", moneyS);
/*      */   }
/*      */ 
/*      */   private void authCount(Map<String, Object> resultMap) {
/*  386 */     int onekey = 0;
/*  387 */     int portaluser = 0;
/*  388 */     int radius = 0;
/*  389 */     int sms = 0;
/*  390 */     int app = 0;
/*  391 */     int weixin = 0;
/*  392 */     int gzh = 0;
/*  393 */     int guest = 0;
/*  394 */     int count = 0;
/*      */ 
/*  396 */     int ios = 0;
/*  397 */     int windows = 0;
/*  398 */     int android = 0;
/*  399 */     int macos = 0;
/*      */ 
/*  401 */     int onekeyB = 0;
/*  402 */     int portaluserB = 0;
/*  403 */     int radiusB = 0;
/*  404 */     int smsB = 0;
/*  405 */     int appB = 0;
/*  406 */     int weixinB = 0;
/*  407 */     int gzhB = 0;
/*  408 */     int guestB = 0;
/*      */ 
/*  410 */     int iosB = 0;
/*  411 */     int windowsB = 0;
/*  412 */     int androidB = 0;
/*  413 */     int macosB = 0;
/*      */ 
/*  415 */     Map map = new HashMap();
/*  416 */     Iterator iterator = com.leeson.portal.core.model.Config.getInstance().getConfigMap().keySet().iterator();
/*  417 */     while (iterator.hasNext()) {
/*  418 */       Object o = iterator.next();
/*  419 */       String t = o.toString();
/*  420 */       if (stringUtils.isNotBlank(t)) {
/*  421 */         map.put(t, Integer.valueOf(0));
/*      */       }
/*      */     }
/*      */ 
/*  425 */     int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();
/*  426 */     count += onlineCount;
/*      */ 
/*  428 */     Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/*  429 */     while (iteratorOnline.hasNext()) {
/*  430 */       Object o = iteratorOnline.next();
/*  431 */       String t = o.toString();
/*  432 */       int i = t.lastIndexOf(":");
/*  433 */       String basip = t.substring(i + 1);
/*  434 */       if (map.containsKey(basip)) {
/*  435 */         int basCount = ((Integer)map.get(basip)).intValue();
/*  436 */         basCount++;
/*  437 */         map.put(basip, Integer.valueOf(basCount));
/*      */       } else {
/*  439 */         map.put(basip, Integer.valueOf(1));
/*      */       }
/*      */ 
/*  442 */       String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
/*      */ 
/*  444 */       String type = loginInfo[6];
/*  445 */       if ("0".equals(type))
/*  446 */         onekey++;
/*  447 */       else if ("1".equals(type))
/*  448 */         portaluser++;
/*  449 */       else if ("2".equals(type))
/*  450 */         radius++;
/*  451 */       else if ("3".equals(type))
/*  452 */         app++;
/*  453 */       else if ("4".equals(type))
/*  454 */         sms++;
/*  455 */       else if ("5".equals(type))
/*  456 */         weixin++;
/*  457 */       else if ("6".equals(type))
/*  458 */         gzh++;
/*  459 */       else if ("7".equals(type)) {
/*  460 */         guest++;
/*      */       }
/*      */ 
/*  463 */       String client = loginInfo[15];
/*  464 */       if ("Windows".equals(client)) {
/*  465 */         windows++;
/*      */       }
/*  467 */       if ("MacOS".equals(client)) {
/*  468 */         macos++;
/*      */       }
/*  470 */       if ("Android".equals(client)) {
/*  471 */         android++;
/*      */       }
/*  473 */       if ("IOS".equals(client)) {
/*  474 */         ios++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  480 */     PortallinkrecordallQuery basQ = new PortallinkrecordallQuery();
/*  481 */     basQ.setBasipLike(false);
/*  482 */     Iterator iteratorBas = map.keySet().iterator();
/*  483 */     while (iteratorBas.hasNext()) {
/*  484 */       String basip = (String)iteratorBas.next();
/*  485 */       int basCount = ((Integer)map.get(basip)).intValue();
/*  486 */       basQ.setBasip(basip);
/*  487 */       basCount += this.portallinkrecordallService.getPortallinkrecordallCount(basQ).intValue();
/*  488 */       map.put(basip, Integer.valueOf(basCount));
/*      */     }
/*      */ 
/*  491 */     PortallinkrecordallQuery authQ = new PortallinkrecordallQuery();
/*  492 */     authQ.setMethodtypeLike(false);
/*  493 */     authQ.setMethodtype("一键认证");
/*  494 */     onekey += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  495 */     authQ.setMethodtype("本地用户");
/*  496 */     portaluser += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  497 */     authQ.setMethodtype("Radius");
/*  498 */     radius += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  499 */     authQ.setMethodtype("APP认证");
/*  500 */     app += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  501 */     authQ.setMethodtype("短信认证");
/*  502 */     sms += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  503 */     authQ.setMethodtype("微信认证");
/*  504 */     weixin += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  505 */     authQ.setMethodtype("公众号认证");
/*  506 */     gzh += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  507 */     authQ.setMethodtype("访客认证");
/*  508 */     guest += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*      */ 
/*  510 */     PortallinkrecordallQuery clientQ = new PortallinkrecordallQuery();
/*  511 */     clientQ.setAgentLike(false);
/*  512 */     clientQ.setAgent("Windows");
/*  513 */     windows += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*  514 */     clientQ.setAgent("MacOS");
/*  515 */     macos += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*  516 */     clientQ.setAgent("Android");
/*  517 */     android += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*  518 */     clientQ.setAgent("IOS");
/*  519 */     ios += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*      */ 
/*  521 */     count += this.portallinkrecordallService.getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue();
/*      */ 
/*  523 */     resultMap.put("onekey", Integer.valueOf(onekey));
/*  524 */     resultMap.put("portaluser", Integer.valueOf(portaluser));
/*  525 */     resultMap.put("radius", Integer.valueOf(radius));
/*  526 */     resultMap.put("sms", Integer.valueOf(sms));
/*  527 */     resultMap.put("weixin", Integer.valueOf(weixin));
/*  528 */     resultMap.put("gzh", Integer.valueOf(gzh));
/*  529 */     resultMap.put("app", Integer.valueOf(app));
/*  530 */     resultMap.put("guest", Integer.valueOf(guest));
/*      */ 
/*  532 */     resultMap.put("windows", Integer.valueOf(windows));
/*  533 */     resultMap.put("macos", Integer.valueOf(macos));
/*  534 */     resultMap.put("android", Integer.valueOf(android));
/*  535 */     resultMap.put("ios", Integer.valueOf(ios));
/*      */ 
/*  537 */     if (count != 0) {
/*  538 */       onekeyB = (int)Math.rint(onekey / count * 100.0D);
/*  539 */       portaluserB = (int)Math.rint(portaluser / count * 100.0D);
/*  540 */       radiusB = (int)Math.rint(radius / count * 100.0D);
/*  541 */       smsB = (int)Math.rint(sms / count * 100.0D);
/*  542 */       weixinB = (int)Math.rint(weixin / count * 100.0D);
/*  543 */       gzhB = (int)Math.rint(gzh / count * 100.0D);
/*  544 */       appB = (int)Math.rint(app / count * 100.0D);
/*  545 */       guestB = (int)Math.rint(guest / count * 100.0D);
/*      */ 
/*  547 */       windowsB = (int)Math.rint(windows / count * 100.0D);
/*  548 */       macosB = (int)Math.rint(macos / count * 100.0D);
/*  549 */       androidB = (int)Math.rint(android / count * 100.0D);
/*  550 */       iosB = (int)Math.rint(ios / count * 100.0D);
/*      */     }
/*  552 */     resultMap.put("onekeyB", Integer.valueOf(onekeyB));
/*  553 */     resultMap.put("portaluserB", Integer.valueOf(portaluserB));
/*  554 */     resultMap.put("radiusB", Integer.valueOf(radiusB));
/*  555 */     resultMap.put("smsB", Integer.valueOf(smsB));
/*  556 */     resultMap.put("weixinB", Integer.valueOf(weixinB));
/*  557 */     resultMap.put("gzhB", Integer.valueOf(gzhB));
/*  558 */     resultMap.put("appB", Integer.valueOf(appB));
/*  559 */     resultMap.put("guestB", Integer.valueOf(guestB));
/*      */ 
/*  561 */     resultMap.put("windowsB", Integer.valueOf(windowsB));
/*  562 */     resultMap.put("macosB", Integer.valueOf(macosB));
/*  563 */     resultMap.put("androidB", Integer.valueOf(androidB));
/*  564 */     resultMap.put("iosB", Integer.valueOf(iosB));
/*      */ 
/*  566 */     resultMap.put("count", Integer.valueOf(count));
/*      */ 
/*  568 */     resultMap.put("basCount", map);
/*      */   }
/*      */ 
/*      */   private void linkCount(ModelMap model)
/*      */   {
/*  674 */     int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();
/*      */ 
/*  676 */     int linkAll = this.portallinkrecordallService
/*  677 */       .getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue() + 
/*  678 */       onlineCount;
/*  679 */     int linkAllUp = linkAll / 7;
/*  680 */     model.put("linkAll", Integer.valueOf(linkAll));
/*  681 */     model.put("linkAllUp", Integer.valueOf(linkAllUp));
/*      */ 
/*  683 */     PortallinkrecordallQuery linkTodayQuery = new PortallinkrecordallQuery();
/*  684 */     Date now = new Date();
/*  685 */     Date today = new Date(now.getYear(), now.getMonth(), now.getDate());
/*  686 */     linkTodayQuery.setBegin_time(today);
/*  687 */     int linkToday = this.portallinkrecordallService
/*  688 */       .getPortallinkrecordallCount(linkTodayQuery).intValue();
/*  689 */     Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/*  690 */     while (iteratorOnline.hasNext()) {
/*  691 */       Object o = iteratorOnline.next();
/*  692 */       String t = o.toString();
/*  693 */       String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
/*      */       try {
/*  695 */         String time = loginInfo[3];
/*  696 */         Date loginTime = ThreadLocalDateUtil.parse(time);
/*  697 */         if (loginTime.getTime() >= today.getTime())
/*  698 */           linkToday++;
/*      */       }
/*      */       catch (Exception localException) {
/*      */       }
/*      */     }
/*  703 */     int linkTodayUp = linkToday / 7;
/*  704 */     model.put("linkToday", Integer.valueOf(linkToday));
/*  705 */     model.put("linkTodayUp", Integer.valueOf(linkTodayUp));
/*      */ 
/*  707 */     PortallinkrecordallQuery linkWeekQuery = new PortallinkrecordallQuery();
/*  708 */     linkWeekQuery.setBegin_time(new Date(new Date().getTime() - 604800000L));
/*      */ 
/*  710 */     int linkWeek = this.portallinkrecordallService
/*  711 */       .getPortallinkrecordallCount(linkWeekQuery).intValue() + onlineCount;
/*  712 */     int linkWeekUp = linkWeek / 7;
/*  713 */     model.put("linkWeek", Integer.valueOf(linkWeek));
/*  714 */     model.put("linkWeekUp", Integer.valueOf(linkWeekUp));
/*      */ 
/*  716 */     PortallinkrecordallQuery linkMonthQuery = new PortallinkrecordallQuery();
/*  717 */     linkMonthQuery.setBegin_time(new Date(new Date().getTime() - 2592000000L));
/*      */ 
/*  719 */     int linkMonth = this.portallinkrecordallService
/*  720 */       .getPortallinkrecordallCount(linkMonthQuery).intValue() + onlineCount;
/*  721 */     int linkMonthUp = linkMonth / 7;
/*  722 */     model.put("linkMonth", Integer.valueOf(linkMonth));
/*  723 */     model.put("linkMonthUp", Integer.valueOf(linkMonthUp));
/*      */   }
/*      */ 
/*      */   private void moneyCount(ModelMap model) {
/*  727 */     Double money = Double.valueOf(0.0D);
/*  728 */     String moneyS = "0.00";
/*      */ 
/*  730 */     PortalorderQuery q = new PortalorderQuery();
/*  731 */     q.setFields("money");
/*  732 */     List<Portalorder> orders = this.portalorderService
/*  733 */       .getPortalorderList(q);
/*  734 */     for (Portalorder order : orders) {
/*  735 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  737 */     moneyS = df.format(money);
/*  738 */     if (moneyS.startsWith(".")) {
/*  739 */       moneyS = "0" + moneyS;
/*      */     }
/*  741 */     model.put("moneyAll", moneyS);
/*      */ 
/*  743 */     money = Double.valueOf(0.0D);
/*  744 */     moneyS = "0.00";
/*  745 */     Date now = new Date();
/*  746 */     q.setBegin_time1(new Date(now.getYear(), now.getMonth(), now.getDate()));
/*  747 */     orders = this.portalorderService.getPortalorderList(q);
/*  748 */     for (Portalorder order : orders) {
/*  749 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  751 */     moneyS = df.format(money);
/*  752 */     if (moneyS.startsWith(".")) {
/*  753 */       moneyS = "0" + moneyS;
/*      */     }
/*  755 */     model.put("moneyToday", moneyS);
/*      */ 
/*  757 */     money = Double.valueOf(0.0D);
/*  758 */     moneyS = "0.00";
/*  759 */     q.setBegin_time1(new Date(now.getTime() - 604800000L));
/*      */ 
/*  761 */     orders = this.portalorderService.getPortalorderList(q);
/*  762 */     for (Portalorder order : orders) {
/*  763 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  765 */     moneyS = df.format(money);
/*  766 */     if (moneyS.startsWith(".")) {
/*  767 */       moneyS = "0" + moneyS;
/*      */     }
/*  769 */     model.put("moneyWeek", moneyS);
/*      */ 
/*  771 */     money = Double.valueOf(0.0D);
/*  772 */     moneyS = "0.00";
/*  773 */     q.setBegin_time1(new Date(now.getTime() - 2592000000L));
/*      */ 
/*  775 */     orders = this.portalorderService.getPortalorderList(q);
/*  776 */     for (Portalorder order : orders) {
/*  777 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*      */     }
/*  779 */     moneyS = df.format(money);
/*  780 */     if (moneyS.startsWith(".")) {
/*  781 */       moneyS = "0" + moneyS;
/*      */     }
/*  783 */     model.put("moneyMonth", moneyS);
/*      */   }
/*      */ 
/*      */   private void authCount(ModelMap model) {
/*  787 */     int onekey = 0;
/*  788 */     int portaluser = 0;
/*  789 */     int radius = 0;
/*  790 */     int sms = 0;
/*  791 */     int app = 0;
/*  792 */     int weixin = 0;
/*  793 */     int gzh = 0;
/*  794 */     int guest = 0;
/*  795 */     int count = 0;
/*      */ 
/*  797 */     int ios = 0;
/*  798 */     int windows = 0;
/*  799 */     int android = 0;
/*  800 */     int macos = 0;
/*      */ 
/*  802 */     int onekeyB = 0;
/*  803 */     int portaluserB = 0;
/*  804 */     int radiusB = 0;
/*  805 */     int smsB = 0;
/*  806 */     int appB = 0;
/*  807 */     int weixinB = 0;
/*  808 */     int gzhB = 0;
/*  809 */     int guestB = 0;
/*      */ 
/*  811 */     int iosB = 0;
/*  812 */     int windowsB = 0;
/*  813 */     int androidB = 0;
/*  814 */     int macosB = 0;
/*      */ 
/*  816 */     Map map = new HashMap();
/*  817 */     Iterator iterator = com.leeson.portal.core.model.Config.getInstance().getConfigMap().keySet().iterator();
/*  818 */     while (iterator.hasNext()) {
/*  819 */       Object o = iterator.next();
/*  820 */       String t = o.toString();
/*  821 */       if (stringUtils.isNotBlank(t)) {
/*  822 */         map.put(t, Integer.valueOf(0));
/*      */       }
/*      */     }
/*      */ 
/*  826 */     int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();
/*  827 */     count += onlineCount;
/*      */ 
/*  829 */     Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/*  830 */     while (iteratorOnline.hasNext()) {
/*  831 */       Object o = iteratorOnline.next();
/*  832 */       String t = o.toString();
/*  833 */       int i = t.lastIndexOf(":");
/*  834 */       String basip = t.substring(i + 1);
/*  835 */       if (map.containsKey(basip)) {
/*  836 */         int basCount = ((Integer)map.get(basip)).intValue();
/*  837 */         basCount++;
/*  838 */         map.put(basip, Integer.valueOf(basCount));
/*      */       } else {
/*  840 */         map.put(basip, Integer.valueOf(1));
/*      */       }
/*      */ 
/*  843 */       String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
/*      */ 
/*  845 */       String type = loginInfo[6];
/*  846 */       if ("0".equals(type))
/*  847 */         onekey++;
/*  848 */       else if ("1".equals(type))
/*  849 */         portaluser++;
/*  850 */       else if ("2".equals(type))
/*  851 */         radius++;
/*  852 */       else if ("3".equals(type))
/*  853 */         app++;
/*  854 */       else if ("4".equals(type))
/*  855 */         sms++;
/*  856 */       else if ("5".equals(type))
/*  857 */         weixin++;
/*  858 */       else if ("6".equals(type))
/*  859 */         gzh++;
/*  860 */       else if ("7".equals(type)) {
/*  861 */         guest++;
/*      */       }
/*      */ 
/*  864 */       String client = loginInfo[15];
/*  865 */       if ("Windows".equals(client)) {
/*  866 */         windows++;
/*      */       }
/*  868 */       if ("MacOS".equals(client)) {
/*  869 */         macos++;
/*      */       }
/*  871 */       if ("Android".equals(client)) {
/*  872 */         android++;
/*      */       }
/*  874 */       if ("IOS".equals(client)) {
/*  875 */         ios++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  881 */     PortallinkrecordallQuery basQ = new PortallinkrecordallQuery();
/*  882 */     basQ.setBasipLike(false);
/*  883 */     Iterator iteratorBas = map.keySet().iterator();
/*  884 */     while (iteratorBas.hasNext()) {
/*  885 */       String basip = (String)iteratorBas.next();
/*  886 */       int basCount = ((Integer)map.get(basip)).intValue();
/*  887 */       basQ.setBasip(basip);
/*  888 */       basCount += this.portallinkrecordallService.getPortallinkrecordallCount(basQ).intValue();
/*  889 */       map.put(basip, Integer.valueOf(basCount));
/*      */     }
/*      */ 
/*  892 */     PortallinkrecordallQuery authQ = new PortallinkrecordallQuery();
/*  893 */     authQ.setMethodtypeLike(false);
/*  894 */     authQ.setMethodtype("一键认证");
/*  895 */     onekey += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  896 */     authQ.setMethodtype("本地用户");
/*  897 */     portaluser += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  898 */     authQ.setMethodtype("Radius");
/*  899 */     radius += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  900 */     authQ.setMethodtype("APP认证");
/*  901 */     app += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  902 */     authQ.setMethodtype("短信认证");
/*  903 */     sms += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  904 */     authQ.setMethodtype("微信认证");
/*  905 */     weixin += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  906 */     authQ.setMethodtype("公众号认证");
/*  907 */     gzh += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*  908 */     authQ.setMethodtype("访客认证");
/*  909 */     guest += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
/*      */ 
/*  911 */     PortallinkrecordallQuery clientQ = new PortallinkrecordallQuery();
/*  912 */     clientQ.setAgentLike(false);
/*  913 */     clientQ.setAgent("Windows");
/*  914 */     windows += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*  915 */     clientQ.setAgent("MacOS");
/*  916 */     macos += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*  917 */     clientQ.setAgent("Android");
/*  918 */     android += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*  919 */     clientQ.setAgent("IOS");
/*  920 */     ios += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
/*      */ 
/*  922 */     count += this.portallinkrecordallService.getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue();
/*      */ 
/*  924 */     model.put("onekey", Integer.valueOf(onekey));
/*  925 */     model.put("portaluser", Integer.valueOf(portaluser));
/*  926 */     model.put("radius", Integer.valueOf(radius));
/*  927 */     model.put("sms", Integer.valueOf(sms));
/*  928 */     model.put("weixin", Integer.valueOf(weixin));
/*  929 */     model.put("gzh", Integer.valueOf(gzh));
/*  930 */     model.put("app", Integer.valueOf(app));
/*  931 */     model.put("guest", Integer.valueOf(guest));
/*      */ 
/*  933 */     model.put("windows", Integer.valueOf(windows));
/*  934 */     model.put("macos", Integer.valueOf(macos));
/*  935 */     model.put("android", Integer.valueOf(android));
/*  936 */     model.put("ios", Integer.valueOf(ios));
/*      */ 
/*  938 */     if (count != 0) {
/*  939 */       onekeyB = (int)Math.rint(onekey / count * 100.0D);
/*  940 */       portaluserB = (int)Math.rint(portaluser / count * 100.0D);
/*  941 */       radiusB = (int)Math.rint(radius / count * 100.0D);
/*  942 */       smsB = (int)Math.rint(sms / count * 100.0D);
/*  943 */       weixinB = (int)Math.rint(weixin / count * 100.0D);
/*  944 */       gzhB = (int)Math.rint(gzh / count * 100.0D);
/*  945 */       appB = (int)Math.rint(app / count * 100.0D);
/*  946 */       guestB = (int)Math.rint(guest / count * 100.0D);
/*      */ 
/*  948 */       windowsB = (int)Math.rint(windows / count * 100.0D);
/*  949 */       macosB = (int)Math.rint(macos / count * 100.0D);
/*  950 */       androidB = (int)Math.rint(android / count * 100.0D);
/*  951 */       iosB = (int)Math.rint(ios / count * 100.0D);
/*      */     }
/*      */ 
/*  954 */     model.put("onekeyB", Integer.valueOf(onekeyB));
/*  955 */     model.put("portaluserB", Integer.valueOf(portaluserB));
/*  956 */     model.put("radiusB", Integer.valueOf(radiusB));
/*  957 */     model.put("smsB", Integer.valueOf(smsB));
/*  958 */     model.put("weixinB", Integer.valueOf(weixinB));
/*  959 */     model.put("gzhB", Integer.valueOf(gzhB));
/*  960 */     model.put("appB", Integer.valueOf(appB));
/*  961 */     model.put("guestB", Integer.valueOf(guestB));
/*      */ 
/*  963 */     model.put("windowsB", Integer.valueOf(windowsB));
/*  964 */     model.put("macosB", Integer.valueOf(macosB));
/*  965 */     model.put("androidB", Integer.valueOf(androidB));
/*  966 */     model.put("iosB", Integer.valueOf(iosB));
/*      */ 
/*  968 */     model.put("count", Integer.valueOf(count));
/*      */ 
/*  970 */     model.put("basCount", map);
/*      */   }
/*      */ 
/*      */   private void ssidCount(ModelMap model) {
/*  974 */     PortalssidQuery q = new PortalssidQuery();
/*  975 */     q.setFields("ssid,count");
/*  976 */     List ssids = this.portalssidService
/*  977 */       .getPortalssidList(q);
/*  978 */     model.put("ssids", ssids);
/*      */   }
/*      */ 
/*      */   private void apCount(ModelMap model) {
/*  982 */     PortalapQuery q = new PortalapQuery();
/*  983 */     q.setFields("mac,count");
/*  984 */     List aps = this.portalapService
/*  985 */       .getPortalapList(q);
/*  986 */     model.put("aps", aps);
/*      */   }
/*      */ 
/*      */   private void webCount(ModelMap model) {
/*  990 */     com.leeson.core.bean.Config baseWeb = this.configService.getConfigByKey(Long.valueOf(1L));
/*  991 */     model.put("baseShow", baseWeb.getCountShow());
/*  992 */     model.put("baseAuth", baseWeb.getCountAuth());
/*  993 */     PortalwebQuery q = new PortalwebQuery();
/*  994 */     q.setFields("name,countShow,countAuth");
/*  995 */     List webs = this.portalwebService
/*  996 */       .getPortalwebList(q);
/*  997 */     model.put("webs", webs);
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/homeAction/index.action"})
/*      */   public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*      */   {
/* 1004 */     SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/* 1005 */     Portaluser user = (Portaluser)request.getSession()
/* 1006 */       .getAttribute("user");
/* 1007 */     if ((user == null) || (user.getId() == null)) {
/* 1008 */       model.put("msgCount", Integer.valueOf(0));
/* 1009 */       return "homeAction/index";
/*      */     }
/* 1011 */     String toid = user.getId().toString();
/* 1012 */     PortalmessageQuery message = new PortalmessageQuery();
/* 1013 */     message.setToid(toid);
/* 1014 */     message.setToPos("0");
/* 1015 */     message.setState("0");
/* 1016 */     message.setDelin("0");
/* 1017 */     model.addAttribute("msgCount", 
/* 1018 */       this.portalmessageService.getPortalmessageCount(message));
/*      */ 
/* 1020 */     int license = 0;
/* 1021 */     String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = 
/* 1022 */       (String)Wz3ofg0r225avuerr.getInstance()
/* 1022 */       .getXr9hk0cvnsx().get("mec");
/* 1023 */     if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
/* 1024 */       RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
/*      */     }
/* 1026 */     String[] MxMzIyRDMzMzAy = 
/* 1028 */       (String[])WySlot15gasa.getInstance()
/* 1027 */       .getAmkbYQX3eQjuwtnxpbjYgQGZOr()
/* 1028 */       .get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
/* 1029 */     if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
/* 1031 */       String apCount = MxMzIyRDMzMzAy[1];
/* 1032 */       String toDateString = MxMzIyRDMzMzAy[2];
/* 1033 */       String basCount = MxMzIyRDMzMzAy[4];
/* 1034 */       String macCount = MxMzIyRDMzMzAy[5];
/*      */       Date saveDate;
/*      */       try { saveDate = fs.parse(toDateString); }
/*      */       catch (ParseException e)
/*      */       {
/* 1039 */         saveDate = new Date();
/* 1041 */       }Date now = new Date();
/* 1042 */       String nowString = fs.format(now);
/*      */       Date nowDate;
/*      */       try { nowDate = fs.parse(nowString); }
/*      */       catch (ParseException e)
/*      */       {
/* 1047 */         nowDate = saveDate;
/*      */       }
/*      */ 
/* 1050 */       long to = saveDate.getTime();
/* 1051 */       long from = nowDate.getTime();
/*      */ 
/* 1054 */       int doMac = OnlineMap.getInstance().getOnlineUserMap().size();
/* 1055 */       int doAp = this.portalweixinwifiService
/* 1056 */         .getPortalweixinwifiCount(new PortalweixinwifiQuery()).intValue();
/* 1057 */       int doBas = this.portalbasService
/* 1058 */         .getPortalbasCount(new PortalbasQuery()).intValue();
/* 1059 */       int apB = 100;
/* 1060 */       int basB = 100;
/* 1061 */       int macB = 100;
/*      */       try {
/* 1063 */         if (Integer.valueOf(apCount).intValue() > 0)
/* 1064 */           apB = (int)Math.rint(doAp / 
/* 1065 */             Integer.valueOf(apCount).intValue() * 100.0D);
/*      */       }
/*      */       catch (Exception e) {
/* 1068 */         apB = 100;
/*      */       }
/*      */       try {
/* 1071 */         if (Integer.valueOf(basCount).intValue() > 0)
/* 1072 */           basB = (int)Math.rint(doBas / 
/* 1073 */             Integer.valueOf(basCount).intValue() * 100.0D);
/*      */       }
/*      */       catch (Exception e) {
/* 1076 */         basB = 100;
/*      */       }
/*      */       try {
/* 1079 */         if (Integer.valueOf(macCount).intValue() > 0)
/* 1080 */           macB = (int)Math.rint(doMac / 
/* 1081 */             Integer.valueOf(macCount).intValue() * 100.0D);
/*      */       }
/*      */       catch (Exception e) {
/* 1084 */         macB = 100;
/*      */       }
/*      */ 
/* 1087 */       int haveDay = (int)((to - from) / 86400000L);
/*      */ 
/* 1089 */       if (nowDate.getTime() < saveDate.getTime()) {
/* 1090 */         model.addAttribute("to", p);
/* 1091 */         model.addAttribute("ap", apCount);
/* 1092 */         model.addAttribute("bas", basCount);
/* 1093 */         model.addAttribute("mac", macCount);
/* 1094 */         model.addAttribute("todate", toDateString);
/* 1095 */         model.addAttribute("nowdate", nowString);
/* 1096 */         model.addAttribute("doAp", Integer.valueOf(doAp));
/* 1097 */         model.addAttribute("doBas", Integer.valueOf(doBas));
/* 1098 */         model.addAttribute("doMac", Integer.valueOf(doMac));
/* 1099 */         model.addAttribute("apB", Integer.valueOf(apB));
/* 1100 */         model.addAttribute("basB", Integer.valueOf(basB));
/* 1101 */         model.addAttribute("macB", Integer.valueOf(macB));
/* 1102 */         model.addAttribute("haveDay", Integer.valueOf(haveDay));
/* 1103 */         license = 1;
/*      */       } else {
/* 1105 */         model.addAttribute("to", p);
/* 1106 */         model.addAttribute("ap", apCount);
/* 1107 */         model.addAttribute("bas", basCount);
/* 1108 */         model.addAttribute("mac", macCount);
/* 1109 */         model.addAttribute("todate", toDateString);
/* 1110 */         model.addAttribute("nowdate", nowString);
/* 1111 */         model.addAttribute("doAp", Integer.valueOf(doAp));
/* 1112 */         model.addAttribute("doBas", Integer.valueOf(doBas));
/* 1113 */         model.addAttribute("doMac", Integer.valueOf(doMac));
/* 1114 */         model.addAttribute("apB", Integer.valueOf(apB));
/* 1115 */         model.addAttribute("basB", Integer.valueOf(basB));
/* 1116 */         model.addAttribute("macB", Integer.valueOf(macB));
/* 1117 */         model.addAttribute("haveDay", Integer.valueOf(haveDay));
/* 1118 */         license = 2;
/*      */       }
/*      */     }
/* 1121 */     model.addAttribute("license", Integer.valueOf(license));
/*      */ 
/* 1123 */     return "homeAction/index";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/homeAction/right.action"})
/*      */   public String right(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*      */   {
/* 1131 */     PortallogrecordQuery logrecordQuery = new PortallogrecordQuery();
/* 1132 */     logrecordQuery.setPageSize(7);
/* 1133 */     logrecordQuery.setPage(1);
/* 1134 */     logrecordQuery.orderbyId(false);
/* 1135 */     List operationRecords = this.portallogrecordService
/* 1136 */       .getPortallogrecordListWithPage(logrecordQuery).getList();
/*      */ 
/* 1138 */     PortalaccountQuery portalaccountQuery = new PortalaccountQuery();
/* 1139 */     portalaccountQuery.setState("0");
/*      */ 
/* 1141 */     int lock_count = this.portalaccountService
/* 1142 */       .getPortalaccountCount(portalaccountQuery).intValue();
/* 1143 */     int acc_count = this.portalaccountService
/* 1144 */       .getPortalaccountCount(new PortalaccountQuery()).intValue();
/*      */ 
/* 1146 */     int online_count = OnlineMap.getInstance().getOnlineUserMap().size() + 
/* 1147 */       WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().size();
/*      */ 
/* 1149 */     model.addAttribute("config", 
/* 1150 */       ((Portalbas)com.leeson.portal.core.model.Config.getInstance().getConfigMap()
/* 1150 */       .get("")).getAuthInterface());
/*      */ 
/* 1152 */     model.addAttribute("operationRecords", operationRecords);
/* 1153 */     int outline_count = acc_count - online_count;
/* 1154 */     if (outline_count < 0) {
/* 1155 */       outline_count = 0;
/*      */     }
/* 1157 */     model.addAttribute("outline_count", Integer.valueOf(outline_count));
/* 1158 */     model.addAttribute("online_count", Integer.valueOf(online_count));
/* 1159 */     model.addAttribute("acc_count", Integer.valueOf(acc_count));
/* 1160 */     model.addAttribute("lock_count", Integer.valueOf(lock_count));
/* 1161 */     model.addAttribute("true_count", Integer.valueOf(acc_count - lock_count));
/*      */ 
/* 1163 */     MonitorInfoBean sysInfo = MonitorService.getMonitorInfoBean();
/* 1164 */     model.addAttribute("System", sysInfo.getOsName());
/* 1165 */     model.addAttribute("CpuRatio", Double.valueOf(sysInfo.getCpuRatio()));
/* 1166 */     model.addAttribute("TotalThread", Integer.valueOf(sysInfo.getTotalThread()));
/* 1167 */     model.addAttribute("Memory", sysInfo.getFreePhysicalMemorySize() / 1024L + "MB / " + sysInfo.getTotalMemorySize() / 1024L + "MB");
/* 1168 */     model.addAttribute("Disk", sysInfo.getDiskFreeSpace() + "GB / " + sysInfo.getDiskTotalSpace() + "GB");
/*      */ 
/* 1170 */     return "homeAction/right";
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.HomeController
 * JD-Core Version:    0.6.2
 */