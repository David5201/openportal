/*      */ package com.leeson.core.controller;
/*      */ 
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalaccountgroup;
/*      */ import com.leeson.core.bean.Portalspeed;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalaccountmacsQuery;
/*      */ import com.leeson.core.query.PortalspeedQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalaccountgroupService;
/*      */ import com.leeson.core.service.PortalaccountmacsService;
/*      */ import com.leeson.core.service.PortalconfigService;
/*      */ import com.leeson.core.service.PortalspeedService;
/*      */ import com.leeson.core.utils.Kick;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*      */ import com.leeson.radius.core.utils.COAThread;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Controller;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.RequestParam;
/*      */ import sun.misc.BASE64Decoder;
/*      */ 
/*      */ @Controller
/*      */ public class APIController
/*      */ {
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountService portalaccountService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountmacsService macsService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalconfigService portalconfigService;
/*      */ 
/*      */   @Autowired
/*      */   private ConfigService configService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalspeedService portalspeedService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountgroupService portalaccountgroupService;
/*   67 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*      */ 
/*   69 */   private static Logger log = Logger.getLogger(APIController.class);
/*      */ 
/*      */   @RequestMapping(value={"/CheckIn.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*      */   public void checkIn(Portalaccount e, Integer macLimit, Integer count, HttpServletResponse response, HttpServletRequest request)
/*      */   {
/*      */     try
/*      */     {
/*   76 */       if ((stringUtils.isBlank(e.getLoginName())) || 
/*   77 */         (stringUtils.isBlank(e.getPassword()))) {
/*   78 */         String respMessage = "error";
/*   79 */         PrintWriter out = response.getWriter();
/*   80 */         out.print(respMessage);
/*   81 */         out.close();
/*   82 */         return;
/*      */       }
/*      */ 
/*   85 */       int accountState = Integer.valueOf(e.getState()).intValue();
/*   86 */       if ((accountState < 0) || (accountState > 3)) {
/*   87 */         String respMessage = "error";
/*   88 */         PrintWriter out = response.getWriter();
/*   89 */         out.print(respMessage);
/*   90 */         out.close();
/*   91 */         return;
/*      */       }
/*      */ 
/*   94 */       Long userTime = this.portalaccountgroupService
/*   95 */         .getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
/*   96 */       if ((accountState == 2) && 
/*   97 */         (e.getTime() == null)) {
/*   98 */         e.setTime(userTime);
/*      */       }
/*      */ 
/*  106 */       if ((accountState == 3) && 
/*  107 */         (e.getDate() == null)) {
/*  108 */         String respMessage = "error";
/*  109 */         PrintWriter out = response.getWriter();
/*  110 */         out.print(respMessage);
/*  111 */         out.close();
/*  112 */         return;
/*      */       }
/*      */ 
/*  115 */       if (e.getDate() == null) {
/*  116 */         e.setDate(new Date());
/*      */       }
/*      */ 
/*  120 */       if (count == null) {
/*  121 */         count = Integer.valueOf(1);
/*      */       }
/*  123 */       if (macLimit == null) {
/*  124 */         macLimit = Integer.valueOf(0);
/*      */       }
/*  126 */       if (stringUtils.isBlank(e.getGender())) {
/*  127 */         e.setGender(null);
/*      */       }
/*      */ 
/*  130 */       PortalaccountQuery aq = new PortalaccountQuery();
/*  131 */       aq.setLoginName(e.getLoginName());
/*  132 */       aq.setLoginNameLike(false);
/*  133 */       List accountList = this.portalaccountService
/*  134 */         .getPortalaccountList(aq);
/*  135 */       if (accountList.size() > 0)
/*      */       {
/*  137 */         e.setId(((Portalaccount)accountList.get(0)).getId());
/*  138 */         this.portalaccountService.updatePortalaccountByKeyAll(e);
/*      */ 
/*  140 */         PortalaccountmacsQuery amsq = new PortalaccountmacsQuery();
/*  141 */         amsq.setAccountId(e.getId());
/*  142 */         this.macsService.deleteByQuery(amsq);
/*      */ 
/*  144 */         String respMessage = "ok";
/*  145 */         PrintWriter out = response.getWriter();
/*  146 */         out.print(respMessage);
/*  147 */         out.close();
/*  148 */         return;
/*      */       }
/*      */ 
/*  151 */       e.setAutologin(Integer.valueOf(0));
/*  152 */       e.setMaclimit(macLimit);
/*  153 */       e.setMaclimitcount(count);
/*  154 */       this.portalaccountService.addPortalaccount(e);
/*  155 */       String respMessage = "ok";
/*  156 */       PrintWriter out = response.getWriter();
/*  157 */       out.print(respMessage);
/*  158 */       out.close();
/*  159 */       return;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */       try {
/*  164 */         String respMessage = "error";
/*  165 */         PrintWriter out = response.getWriter();
/*  166 */         out.print(respMessage);
/*  167 */         out.close();
/*  168 */         return;
/*      */       }
/*      */       catch (Exception localException1)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/CheckOut.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*      */   public void checkOut(String loginName, HttpServletResponse response, HttpServletRequest request)
/*      */   {
/*      */     try {
/*  180 */       if (stringUtils.isBlank(loginName)) {
/*  181 */         String respMessage = "error";
/*  182 */         PrintWriter out = response.getWriter();
/*  183 */         out.print(respMessage);
/*  184 */         out.close();
/*  185 */         return;
/*      */       }
/*      */ 
/*  188 */       PortalaccountQuery aq = new PortalaccountQuery();
/*  189 */       aq.setLoginName(loginName);
/*  190 */       aq.setLoginNameLike(false);
/*  191 */       List accountList = this.portalaccountService
/*  192 */         .getPortalaccountList(aq);
/*  193 */       if (accountList.size() > 0) {
/*  194 */         Portalaccount acc = (Portalaccount)accountList.get(0);
/*  195 */         Long id = acc.getId();
/*  196 */         Set<Map.Entry<String, String[]>> entries = onlineMap
/*  197 */           .getOnlineUserMap().entrySet();
/*  198 */         for (Map.Entry entry : entries) {
/*  199 */           String[] info = (String[])entry.getValue();
/*  200 */           String ip = (String)entry.getKey();
/*  201 */           if ((stringUtils.isNotBlank(info[1])) && 
/*  202 */             (Long.valueOf(info[1]) == id)) {
/*  203 */             Kick.kickUserDeleteUser(ip);
/*      */           }
/*      */         }
/*      */ 
/*  207 */         int accountState = Integer.valueOf(acc.getState()).intValue();
/*  208 */         acc = this.portalaccountService.getPortalaccountByKey(id);
/*  209 */         acc.setState("0");
/*  210 */         this.portalaccountService.updatePortalaccountByKey(acc);
/*      */ 
/*  212 */         Long costTime = Long.valueOf(0L);
/*  213 */         if (accountState == 2) {
/*  214 */           Long userTime = this.portalaccountgroupService
/*  215 */             .getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
/*  216 */           costTime = Long.valueOf(userTime.longValue() - acc.getTime().longValue());
/*      */         }
/*  218 */         String respMessage = String.valueOf(costTime);
/*  219 */         PrintWriter out = response.getWriter();
/*  220 */         out.print(respMessage);
/*  221 */         out.close();
/*  222 */         return;
/*      */       }
/*  224 */       String respMessage = "error";
/*  225 */       PrintWriter out = response.getWriter();
/*  226 */       out.print(respMessage);
/*  227 */       out.close();
/*  228 */       return;
/*      */     }
/*      */     catch (Exception e) {
/*      */       try {
/*  232 */         String respMessage = "error";
/*  233 */         PrintWriter out = response.getWriter();
/*  234 */         out.print(respMessage);
/*  235 */         out.close();
/*  236 */         return;
/*      */       }
/*      */       catch (Exception localException1)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/AccAPI.action"})
/*      */   public void AccAPI(HttpServletResponse response, HttpServletRequest request, @RequestParam("business") String business)
/*      */     throws IOException
/*      */   {
/*  248 */     String respMessage = "E99";
/*  249 */     Integer haveAcc = this.portalaccountService
/*  250 */       .getPortalaccountCount(new PortalaccountQuery());
/*  251 */     if ((haveAcc != null) && 
/*  252 */       (haveAcc.intValue() >= Integer.valueOf(
/*  253 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
/*  253 */       .getCoreConfigMap().get("core"))[1]).intValue()))
/*      */     {
/*  254 */       respMessage = "E83";
/*  255 */       PrintWriter out = response.getWriter();
/*  256 */       out.print(respMessage);
/*  257 */       out.close();
/*  258 */       return;
/*      */     }
/*      */ 
/*  262 */     if (stringUtils.isNotBlank(business)) {
/*  263 */       String key = decodeBase64(business);
/*  264 */       if (stringUtils.isNotBlank(key)) {
/*      */         try {
/*  266 */           String[] keys = key.split("\\t");
/*      */ 
/*  269 */           if (keys[0].equals("001")) {
/*  270 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  271 */               if (stringUtils.isNotEmpty(keys[2])) {
/*  272 */                 PortalaccountQuery aq = new PortalaccountQuery();
/*  273 */                 aq.setLoginName(keys[1]);
/*  274 */                 aq.setLoginNameLike(false);
/*  275 */                 List accs = this.portalaccountService
/*  276 */                   .getPortalaccountList(aq);
/*  277 */                 if (accs.size() < 1) {
/*  278 */                   Portalaccount acc = new Portalaccount();
/*  279 */                   acc.setLoginName(keys[1]);
/*  280 */                   acc.setPassword(keys[2]);
/*  281 */                   acc.setName(keys[4]);
/*      */ 
/*  284 */                   boolean haveSpeed = false;
/*  285 */                   if (keys[3].equals("1")) {
/*  286 */                     if (this.portalspeedService
/*  287 */                       .getPortalspeedByKey(Long.valueOf(1L)) != null) {
/*  288 */                       acc.setSpeed(Long.valueOf(1L));
/*  289 */                       haveSpeed = true;
/*      */                     } else {
/*  291 */                       respMessage = "E30";
/*      */                     }
/*  293 */                   } else if (keys[3].equals("2")) {
/*  294 */                     if (this.portalspeedService
/*  295 */                       .getPortalspeedByKey(Long.valueOf(2L)) != null) {
/*  296 */                       acc.setSpeed(Long.valueOf(2L));
/*  297 */                       haveSpeed = true;
/*      */                     } else {
/*  299 */                       respMessage = "E30";
/*      */                     }
/*  301 */                   } else if (keys[3].equals("3")) {
/*  302 */                     if (this.portalspeedService
/*  303 */                       .getPortalspeedByKey(Long.valueOf(3L)) != null) {
/*  304 */                       acc.setSpeed(Long.valueOf(3L));
/*  305 */                       haveSpeed = true;
/*      */                     } else {
/*  307 */                       respMessage = "E30";
/*      */                     }
/*  309 */                   } else if (keys[3].equals("4")) {
/*  310 */                     if (this.portalspeedService
/*  311 */                       .getPortalspeedByKey(Long.valueOf(4L)) != null) {
/*  312 */                       acc.setSpeed(Long.valueOf(4L));
/*  313 */                       haveSpeed = true;
/*      */                     } else {
/*  315 */                       respMessage = "E30";
/*      */                     }
/*      */                   }
/*  318 */                   else respMessage = "E30";
/*      */ 
/*  321 */                   if (haveSpeed)
/*      */                   {
/*  323 */                     Portalaccountgroup ag = this.portalaccountgroupService
/*  324 */                       .getPortalaccountgroupByKey(Long.valueOf(1L));
/*  325 */                     if (stringUtils.isBlank(acc.getState())) {
/*  326 */                       acc.setState(ag.getState());
/*      */                     }
/*  328 */                     if (acc.getMaclimitcount() == null) {
/*  329 */                       acc.setMaclimitcount(ag
/*  330 */                         .getMaclimitcount());
/*      */                     }
/*  332 */                     if (acc.getMaclimit() == null) {
/*  333 */                       acc.setMaclimit(ag.getMaclimit());
/*      */                     }
/*  335 */                     if (acc.getAutologin() == null) {
/*  336 */                       acc.setAutologin(ag.getAutologin());
/*      */                     }
/*  338 */                     if (acc.getSpeed() == null) {
/*  339 */                       acc.setSpeed(ag.getSpeed());
/*      */                     }
/*  341 */                     acc.setDate(ag.getDate());
/*  342 */                     acc.setTime(ag.getTime());
/*  343 */                     acc.setOctets(ag.getOctets());
/*      */ 
/*  345 */                     this.portalaccountService
/*  346 */                       .addPortalaccount(acc);
/*  347 */                     respMessage = "E00";
/*      */                   }
/*      */                 } else {
/*  350 */                   respMessage = "E13";
/*      */                 }
/*      */               } else {
/*  353 */                 respMessage = "E20";
/*      */               }
/*      */             }
/*  356 */             else respMessage = "E10";
/*      */ 
/*      */           }
/*      */ 
/*  361 */           if (keys[0].equals("002")) {
/*  362 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  363 */               if (stringUtils.isNotEmpty(keys[2])) {
/*  364 */                 PortalaccountQuery aq = new PortalaccountQuery();
/*  365 */                 aq.setLoginName(keys[1]);
/*  366 */                 aq.setLoginNameLike(false);
/*  367 */                 List accs = this.portalaccountService
/*  368 */                   .getPortalaccountList(aq);
/*  369 */                 if (accs.size() == 1) {
/*  370 */                   Portalaccount acc = (Portalaccount)accs.get(0);
/*  371 */                   acc.setPassword(keys[2]);
/*  372 */                   this.portalaccountService
/*  373 */                     .updatePortalaccountByKey(acc);
/*  374 */                   respMessage = "E00";
/*      */                 } else {
/*  376 */                   respMessage = "E14";
/*      */                 }
/*      */               } else {
/*  379 */                 respMessage = "E20";
/*      */               }
/*      */             }
/*  382 */             else respMessage = "E14";
/*      */ 
/*      */           }
/*      */ 
/*  387 */           if (keys[0].equals("004")) {
/*  388 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  389 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  390 */               aq.setLoginName(keys[1]);
/*  391 */               aq.setLoginNameLike(false);
/*  392 */               List accs = this.portalaccountService
/*  393 */                 .getPortalaccountList(aq);
/*  394 */               if (accs.size() == 1) {
/*  395 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  396 */                 kickAcc(acc.getId(), acc.getLoginName());
/*  397 */                 acc.setState("0");
/*  398 */                 this.portalaccountService
/*  399 */                   .updatePortalaccountByKey(acc);
/*  400 */                 respMessage = "E00";
/*      */               } else {
/*  402 */                 respMessage = "E14";
/*      */               }
/*      */             } else {
/*  405 */               respMessage = "E14";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  410 */           if (keys[0].equals("005")) {
/*  411 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  412 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  413 */               aq.setLoginName(keys[1]);
/*  414 */               aq.setLoginNameLike(false);
/*  415 */               List accs = this.portalaccountService
/*  416 */                 .getPortalaccountList(aq);
/*  417 */               if (accs.size() == 1) {
/*  418 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  419 */                 acc.setState("1");
/*  420 */                 this.portalaccountService
/*  421 */                   .updatePortalaccountByKey(acc);
/*  422 */                 respMessage = "E00";
/*      */               } else {
/*  424 */                 respMessage = "E14";
/*      */               }
/*      */             } else {
/*  427 */               respMessage = "E14";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  432 */           if (keys[0].equals("006")) {
/*  433 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  434 */               if (stringUtils.isNotEmpty(keys[2])) {
/*  435 */                 PortalaccountQuery aq = new PortalaccountQuery();
/*  436 */                 aq.setLoginName(keys[1]);
/*  437 */                 aq.setLoginNameLike(false);
/*  438 */                 List accs = this.portalaccountService
/*  439 */                   .getPortalaccountList(aq);
/*  440 */                 if (accs.size() == 1) {
/*  441 */                   Portalaccount acc = (Portalaccount)accs.get(0);
/*  442 */                   if (keys[2].equals("1")) {
/*  443 */                     if (this.portalspeedService
/*  444 */                       .getPortalspeedByKey(Long.valueOf(1L)) != null) {
/*  445 */                       kickAcc(acc.getId(), 
/*  446 */                         acc.getLoginName());
/*  447 */                       acc.setSpeed(Long.valueOf(1L));
/*  448 */                       this.portalaccountService
/*  449 */                         .updatePortalaccountByKey(acc);
/*  450 */                       respMessage = "E00";
/*      */                     } else {
/*  452 */                       respMessage = "E30";
/*      */                     }
/*  454 */                   } else if (keys[2].equals("2")) {
/*  455 */                     if (this.portalspeedService
/*  456 */                       .getPortalspeedByKey(Long.valueOf(2L)) != null) {
/*  457 */                       kickAcc(acc.getId(), 
/*  458 */                         acc.getLoginName());
/*  459 */                       acc.setSpeed(Long.valueOf(2L));
/*  460 */                       this.portalaccountService
/*  461 */                         .updatePortalaccountByKey(acc);
/*  462 */                       respMessage = "E00";
/*      */                     } else {
/*  464 */                       respMessage = "E30";
/*      */                     }
/*  466 */                   } else if (keys[2].equals("3")) {
/*  467 */                     if (this.portalspeedService
/*  468 */                       .getPortalspeedByKey(Long.valueOf(3L)) != null) {
/*  469 */                       kickAcc(acc.getId(), 
/*  470 */                         acc.getLoginName());
/*  471 */                       acc.setSpeed(Long.valueOf(3L));
/*  472 */                       this.portalaccountService
/*  473 */                         .updatePortalaccountByKey(acc);
/*  474 */                       respMessage = "E00";
/*      */                     } else {
/*  476 */                       respMessage = "E30";
/*      */                     }
/*  478 */                   } else if (keys[2].equals("4")) {
/*  479 */                     if (this.portalspeedService
/*  480 */                       .getPortalspeedByKey(Long.valueOf(4L)) != null) {
/*  481 */                       kickAcc(acc.getId(), 
/*  482 */                         acc.getLoginName());
/*  483 */                       acc.setSpeed(Long.valueOf(4L));
/*  484 */                       this.portalaccountService
/*  485 */                         .updatePortalaccountByKey(acc);
/*  486 */                       respMessage = "E00";
/*      */                     } else {
/*  488 */                       respMessage = "E30";
/*      */                     }
/*      */                   }
/*  491 */                   else respMessage = "E30"; 
/*      */                 }
/*      */                 else
/*      */                 {
/*  494 */                   respMessage = "E14";
/*      */                 }
/*      */               } else {
/*  497 */                 respMessage = "E30";
/*      */               }
/*      */             }
/*  500 */             else respMessage = "E14";
/*      */ 
/*      */           }
/*      */ 
/*  505 */           if (keys[0].equals("007")) {
/*  506 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  507 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  508 */               aq.setLoginName(keys[1]);
/*  509 */               aq.setLoginNameLike(false);
/*  510 */               List accs = this.portalaccountService
/*  511 */                 .getPortalaccountList(aq);
/*  512 */               if (accs.size() == 1) {
/*  513 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  514 */                 kickAcc(acc.getId(), acc.getLoginName());
/*  515 */                 PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/*  516 */                 macsq.setAccountId(acc.getId());
/*  517 */                 this.macsService.deleteByQuery(macsq);
/*  518 */                 this.portalaccountService.deleteByKey(acc.getId());
/*  519 */                 respMessage = "E00";
/*      */               } else {
/*  521 */                 respMessage = "E14";
/*      */               }
/*      */             } else {
/*  524 */               respMessage = "E14";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  529 */           if (keys[0].equals("014")) {
/*  530 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  531 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  532 */               aq.setLoginName(keys[1]);
/*  533 */               aq.setLoginNameLike(false);
/*  534 */               List accs = this.portalaccountService
/*  535 */                 .getPortalaccountList(aq);
/*  536 */               if (accs.size() == 1) {
/*  537 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  538 */                 kickAcc(acc.getId(), acc.getLoginName());
/*  539 */                 respMessage = "E00";
/*      */               } else {
/*  541 */                 respMessage = "E14";
/*      */               }
/*      */             } else {
/*  544 */               respMessage = "E14";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  549 */           if (keys[0].equals("091"))
/*  550 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  551 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  552 */               aq.setLoginName(keys[1]);
/*  553 */               aq.setLoginNameLike(false);
/*  554 */               List accs = this.portalaccountService
/*  555 */                 .getPortalaccountList(aq);
/*  556 */               if (accs.size() == 1) {
/*  557 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  558 */                 respMessage = "E00\\t" + acc.getLoginName() + 
/*  559 */                   "\\t" + acc.getSpeed() + "\\t" + 
/*  560 */                   acc.getState();
/*      */               } else {
/*  562 */                 respMessage = "E14";
/*      */               }
/*      */             } else {
/*  565 */               respMessage = "E14";
/*      */             }
/*      */         }
/*      */         catch (Exception e) {
/*  569 */           log.error("Account API Error ！");
/*  570 */           log.error("==============ERROR Start=============");
/*  571 */           log.error(e);
/*  572 */           log.error("ERROR INFO ", e);
/*  573 */           log.error("==============ERROR End=============");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  578 */     PrintWriter out = response.getWriter();
/*  579 */     out.print(respMessage);
/*  580 */     out.close();
/*      */   }
/*      */ 
/*      */   private void kickAcc(Long id, String username)
/*      */   {
/*  585 */     Set<Map.Entry<String, String[]>> entries = onlineMap.getOnlineUserMap()
/*  586 */       .entrySet();
/*  587 */     for (Map.Entry entry : entries) {
/*  588 */       String[] info = (String[])entry.getValue();
/*  589 */       String ip = (String)entry.getKey();
/*  590 */       if ((stringUtils.isNotBlank(info[1])) && 
/*  591 */         (Long.valueOf(info[1]) == id)) {
/*  592 */         Kick.kickUserDeleteUser(ip);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  598 */     Iterator iterator = RadiusOnlineMap.getInstance()
/*  599 */       .getRadiusOnlineMap().keySet().iterator();
/*  600 */     while (iterator.hasNext()) {
/*  601 */       Object o = iterator.next();
/*  602 */       String acctSessionId = o.toString();
/*  603 */       String[] radiusOnlineInfo = 
/*  604 */         (String[])RadiusOnlineMap.getInstance()
/*  604 */         .getRadiusOnlineMap().get(acctSessionId);
/*  605 */       if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/*  606 */         (username.equals(radiusOnlineInfo[4])))
/*  607 */         COAThread.COA_Account_Cost(radiusOnlineInfo, 
/*  608 */           "Radius Account API COA");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String decodeBase64(String input)
/*      */   {
/*  615 */     String key = "";
/*      */     try {
/*  617 */       input = input.trim();
/*  618 */       BASE64Decoder decoder = new BASE64Decoder();
/*      */ 
/*  620 */       byte[] b = decoder.decodeBuffer(input);
/*  621 */       key = new String(b, "GBK");
/*      */     } catch (Exception e) {
/*  623 */       key = "";
/*      */     }
/*  625 */     return key;
/*      */   }
/*      */ 
/*      */   public static String decodeBase64Utf8(String input) {
/*  629 */     String key = "";
/*      */     try {
/*  631 */       input = input.trim();
/*  632 */       BASE64Decoder decoder = new BASE64Decoder();
/*      */ 
/*  634 */       byte[] b = decoder.decodeBuffer(input);
/*  635 */       key = new String(b, "UTF-8");
/*      */     } catch (Exception e) {
/*  637 */       key = "";
/*      */     }
/*  639 */     return key;
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/AccountAPI.action"})
/*      */   public void AccountAPI(HttpServletResponse response, HttpServletRequest request, @RequestParam("business") String business)
/*      */     throws IOException
/*      */   {
/*  648 */     String respMessage = "E99";
/*  649 */     Integer haveAcc = this.portalaccountService
/*  650 */       .getPortalaccountCount(new PortalaccountQuery());
/*  651 */     if ((haveAcc != null) && 
/*  652 */       (haveAcc.intValue() >= Integer.valueOf(
/*  653 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
/*  653 */       .getCoreConfigMap().get("core"))[1]).intValue()))
/*      */     {
/*  654 */       respMessage = "E90";
/*  655 */       PrintWriter out = response.getWriter();
/*  656 */       out.print(respMessage);
/*  657 */       out.close();
/*  658 */       return;
/*      */     }
/*      */ 
/*  662 */     if (stringUtils.isNotBlank(business)) {
/*  663 */       String key = decodeBase64Utf8(business);
/*  664 */       System.out.println(key);
/*  665 */       if (stringUtils.isNotBlank(key)) {
/*      */         try {
/*  667 */           String[] keys = key.split("\\t");
/*      */ 
/*  670 */           if (keys[0].equals("001")) {
/*  671 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  672 */               if (stringUtils.isNotEmpty(keys[2])) {
/*  673 */                 if (stringUtils.isNotEmpty(keys[3])) {
/*  674 */                   if (stringUtils.isNotEmpty(keys[4])) {
/*  675 */                     if (stringUtils.isNotEmpty(keys[5])) {
/*  676 */                       if (stringUtils.isNotEmpty(keys[6]))
/*      */                       {
/*  678 */                         if (stringUtils.isNotEmpty(keys[7]))
/*      */                         {
/*  680 */                           if (stringUtils.isNotEmpty(keys[8]))
/*      */                           {
/*  682 */                             if (stringUtils.isNotEmpty(keys[9]))
/*      */                             {
/*  684 */                               if (stringUtils.isNotEmpty(keys[10]))
/*      */                               {
/*  686 */                                 PortalaccountQuery aq = new PortalaccountQuery();
/*  687 */                                 aq.setLoginName(keys[1]);
/*  688 */                                 aq.setLoginNameLike(false);
/*  689 */                                 List accs = this.portalaccountService
/*  690 */                                   .getPortalaccountList(aq);
/*  691 */                                 if (accs.size() < 1) {
/*  692 */                                   Portalaccount acc = new Portalaccount();
/*  693 */                                   acc.setLoginName(keys[1]);
/*  694 */                                   acc.setPassword(keys[2]);
/*  695 */                                   acc.setName(keys[11]);
/*  696 */                                   acc.setPhoneNumber(keys[12]);
/*      */ 
/*  698 */                                   boolean haveSpeed = false;
/*  699 */                                   PortalspeedQuery sq = new PortalspeedQuery();
/*  700 */                                   sq.setNameLike(false);
/*  701 */                                   sq.setName(keys[7]);
/*  702 */                                   List speeds = this.portalspeedService
/*  703 */                                     .getPortalspeedList(sq);
/*  704 */                                   if (speeds
/*  705 */                                     .size() == 1) {
/*  706 */                                     acc.setSpeed(
/*  707 */                                       ((Portalspeed)speeds
/*  707 */                                       .get(0))
/*  708 */                                       .getId());
/*  709 */                                     haveSpeed = true;
/*      */                                   } else {
/*  711 */                                     respMessage = "E07";
/*      */                                   }
/*  713 */                                   if (haveSpeed) {
/*  714 */                                     acc.setState(keys[3]);
/*  715 */                                     acc.setTime(
/*  716 */                                       Long.valueOf(keys[4]));
/*  717 */                                     SimpleDateFormat format = new SimpleDateFormat(
/*  718 */                                       "yyyy-MM-dd HH:mm:ss");
/*  719 */                                     Date date = format
/*  720 */                                       .parse(keys[5] + 
/*  721 */                                       " 23:59:59");
/*  722 */                                     acc.setDate(date);
/*  723 */                                     acc.setOctets(
/*  724 */                                       Long.valueOf(keys[6]));
/*  725 */                                     acc.setMaclimitcount(
/*  726 */                                       Integer.valueOf(keys[8]));
/*  727 */                                     acc.setMaclimit(
/*  728 */                                       Integer.valueOf(keys[9]));
/*  729 */                                     acc.setAutologin(
/*  730 */                                       Integer.valueOf(keys[10]));
/*  731 */                                     this.portalaccountService
/*  732 */                                       .addPortalaccount(acc);
/*  733 */                                     respMessage = "E00";
/*      */                                   }
/*      */                                 } else {
/*  736 */                                   respMessage = "E20";
/*      */                                 }
/*      */                               }
/*      */                               else {
/*  740 */                                 respMessage = "E10";
/*      */                               }
/*      */                             }
/*  743 */                             else respMessage = "E09";
/*      */                           }
/*      */                           else
/*  746 */                             respMessage = "E08";
/*      */                         }
/*      */                         else
/*  749 */                           respMessage = "E07";
/*      */                       }
/*      */                       else
/*  752 */                         respMessage = "E06";
/*      */                     }
/*      */                     else
/*  755 */                       respMessage = "E05";
/*      */                   }
/*      */                   else
/*  758 */                     respMessage = "E04";
/*      */                 }
/*      */                 else
/*  761 */                   respMessage = "E03";
/*      */               }
/*      */               else
/*  764 */                 respMessage = "E02";
/*      */             }
/*      */             else {
/*  767 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  773 */           if (keys[0].equals("002")) {
/*  774 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  775 */               if (stringUtils.isNotEmpty(keys[2])) {
/*  776 */                 if (stringUtils.isNotEmpty(keys[3])) {
/*  777 */                   if (stringUtils.isNotEmpty(keys[4])) {
/*  778 */                     if (stringUtils.isNotEmpty(keys[5])) {
/*  779 */                       if (stringUtils.isNotEmpty(keys[6]))
/*      */                       {
/*  781 */                         if (stringUtils.isNotEmpty(keys[7]))
/*      */                         {
/*  783 */                           if (stringUtils.isNotEmpty(keys[8]))
/*      */                           {
/*  785 */                             if (stringUtils.isNotEmpty(keys[9]))
/*      */                             {
/*  787 */                               if (stringUtils.isNotEmpty(keys[10]))
/*      */                               {
/*  789 */                                 PortalaccountQuery aq = new PortalaccountQuery();
/*  790 */                                 aq.setLoginName(keys[1]);
/*  791 */                                 aq.setLoginNameLike(false);
/*  792 */                                 List accs = this.portalaccountService
/*  793 */                                   .getPortalaccountList(aq);
/*  794 */                                 if (accs.size() == 1) {
/*  795 */                                   Portalaccount acc = (Portalaccount)accs.get(0);
/*  796 */                                   kickAcc(acc.getId(), acc.getLoginName());
/*      */ 
/*  798 */                                   acc.setPassword(keys[2]);
/*  799 */                                   acc.setName(keys[11]);
/*  800 */                                   acc.setPhoneNumber(keys[12]);
/*      */ 
/*  802 */                                   boolean haveSpeed = false;
/*  803 */                                   PortalspeedQuery sq = new PortalspeedQuery();
/*  804 */                                   sq.setNameLike(false);
/*  805 */                                   sq.setName(keys[7]);
/*  806 */                                   List speeds = this.portalspeedService
/*  807 */                                     .getPortalspeedList(sq);
/*  808 */                                   if (speeds
/*  809 */                                     .size() == 1) {
/*  810 */                                     acc.setSpeed(
/*  811 */                                       ((Portalspeed)speeds
/*  811 */                                       .get(0))
/*  812 */                                       .getId());
/*  813 */                                     haveSpeed = true;
/*      */                                   } else {
/*  815 */                                     respMessage = "E07";
/*      */                                   }
/*  817 */                                   if (haveSpeed) {
/*  818 */                                     if (!"none".equals(keys[4])) {
/*  819 */                                       acc.setTime(
/*  820 */                                         Long.valueOf(keys[4]));
/*      */                                     }
/*  822 */                                     if (!"none".equals(keys[5])) {
/*  823 */                                       SimpleDateFormat format = new SimpleDateFormat(
/*  824 */                                         "yyyy-MM-dd HH:mm:ss");
/*  825 */                                       Date date = format
/*  826 */                                         .parse(keys[5] + 
/*  827 */                                         " 23:59:59");
/*  828 */                                       acc.setDate(date);
/*      */                                     }
/*  830 */                                     if (!"none".equals(keys[5])) {
/*  831 */                                       acc.setOctets(
/*  832 */                                         Long.valueOf(keys[6]));
/*      */                                     }
/*  834 */                                     acc.setState(keys[3]);
/*  835 */                                     acc.setMaclimitcount(
/*  836 */                                       Integer.valueOf(keys[8]));
/*  837 */                                     acc.setMaclimit(
/*  838 */                                       Integer.valueOf(keys[9]));
/*  839 */                                     acc.setAutologin(
/*  840 */                                       Integer.valueOf(keys[10]));
/*  841 */                                     this.portalaccountService
/*  842 */                                       .updatePortalaccountByKey(acc);
/*  843 */                                     respMessage = "E00";
/*      */                                   }
/*      */                                 } else {
/*  846 */                                   respMessage = "E30";
/*      */                                 }
/*      */                               }
/*      */                               else {
/*  850 */                                 respMessage = "E10";
/*      */                               }
/*      */                             }
/*  853 */                             else respMessage = "E09";
/*      */                           }
/*      */                           else
/*  856 */                             respMessage = "E08";
/*      */                         }
/*      */                         else
/*  859 */                           respMessage = "E07";
/*      */                       }
/*      */                       else
/*  862 */                         respMessage = "E06";
/*      */                     }
/*      */                     else
/*  865 */                       respMessage = "E05";
/*      */                   }
/*      */                   else
/*  868 */                     respMessage = "E04";
/*      */                 }
/*      */                 else
/*  871 */                   respMessage = "E03";
/*      */               }
/*      */               else
/*  874 */                 respMessage = "E02";
/*      */             }
/*      */             else {
/*  877 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  882 */           if (keys[0].equals("003")) {
/*  883 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  884 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  885 */               aq.setLoginName(keys[1]);
/*  886 */               aq.setLoginNameLike(false);
/*  887 */               List accs = this.portalaccountService
/*  888 */                 .getPortalaccountList(aq);
/*  889 */               if (accs.size() == 1) {
/*  890 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  891 */                 kickAcc(acc.getId(), acc.getLoginName());
/*  892 */                 PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/*  893 */                 macsq.setAccountId(acc.getId());
/*  894 */                 this.macsService.deleteByQuery(macsq);
/*  895 */                 this.portalaccountService.deleteByKey(acc.getId());
/*  896 */                 respMessage = "E00";
/*      */               } else {
/*  898 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/*  901 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  906 */           if (keys[0].equals("004")) {
/*  907 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  908 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  909 */               aq.setLoginName(keys[1]);
/*  910 */               aq.setLoginNameLike(false);
/*  911 */               List accs = this.portalaccountService
/*  912 */                 .getPortalaccountList(aq);
/*  913 */               if (accs.size() == 1) {
/*  914 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  915 */                 kickAcc(acc.getId(), acc.getLoginName());
/*  916 */                 PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/*  917 */                 macsq.setAccountId(acc.getId());
/*  918 */                 this.macsService.deleteByQuery(macsq);
/*  919 */                 respMessage = "E00";
/*      */               } else {
/*  921 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/*  924 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  929 */           if (keys[0].equals("005")) {
/*  930 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  931 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  932 */               aq.setLoginName(keys[1]);
/*  933 */               aq.setLoginNameLike(false);
/*  934 */               List accs = this.portalaccountService
/*  935 */                 .getPortalaccountList(aq);
/*  936 */               if (accs.size() == 1) {
/*  937 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  938 */                 kickAcc(acc.getId(), acc.getLoginName());
/*  939 */                 respMessage = "E00";
/*      */               } else {
/*  941 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/*  944 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  949 */           if (keys[0].equals("006")) {
/*  950 */             if (stringUtils.isNotEmpty(keys[1])) {
/*  951 */               PortalaccountQuery aq = new PortalaccountQuery();
/*  952 */               aq.setLoginName(keys[1]);
/*  953 */               aq.setLoginNameLike(false);
/*  954 */               List accs = this.portalaccountService
/*  955 */                 .getPortalaccountList(aq);
/*  956 */               if (accs.size() == 1) {
/*  957 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/*  958 */                 respMessage = "E00\\t" + acc.toString();
/*      */               } else {
/*  960 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/*  963 */               respMessage = "E01";
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*  972 */     PrintWriter out = response.getWriter();
/*  973 */     out.print(respMessage);
/*  974 */     out.close();
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/AccountAPIGBK.action"})
/*      */   public void AccountAPIGBK(HttpServletResponse response, HttpServletRequest request, @RequestParam("business") String business)
/*      */     throws IOException
/*      */   {
/*  983 */     String respMessage = "E99";
/*  984 */     Integer haveAcc = this.portalaccountService
/*  985 */       .getPortalaccountCount(new PortalaccountQuery());
/*  986 */     if ((haveAcc != null) && 
/*  987 */       (haveAcc.intValue() >= Integer.valueOf(
/*  988 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
/*  988 */       .getCoreConfigMap().get("core"))[1]).intValue()))
/*      */     {
/*  989 */       respMessage = "E90";
/*  990 */       PrintWriter out = response.getWriter();
/*  991 */       out.print(respMessage);
/*  992 */       out.close();
/*  993 */       return;
/*      */     }
/*      */ 
/*  997 */     if (stringUtils.isNotBlank(business)) {
/*  998 */       String key = decodeBase64(business);
/*  999 */       System.out.println(key);
/* 1000 */       if (stringUtils.isNotBlank(key)) {
/*      */         try {
/* 1002 */           String[] keys = key.split("\\t");
/*      */ 
/* 1005 */           if (keys[0].equals("001")) {
/* 1006 */             if (stringUtils.isNotEmpty(keys[1])) {
/* 1007 */               if (stringUtils.isNotEmpty(keys[2])) {
/* 1008 */                 if (stringUtils.isNotEmpty(keys[3])) {
/* 1009 */                   if (stringUtils.isNotEmpty(keys[4])) {
/* 1010 */                     if (stringUtils.isNotEmpty(keys[5])) {
/* 1011 */                       if (stringUtils.isNotEmpty(keys[6]))
/*      */                       {
/* 1013 */                         if (stringUtils.isNotEmpty(keys[7]))
/*      */                         {
/* 1015 */                           if (stringUtils.isNotEmpty(keys[8]))
/*      */                           {
/* 1017 */                             if (stringUtils.isNotEmpty(keys[9]))
/*      */                             {
/* 1019 */                               if (stringUtils.isNotEmpty(keys[10]))
/*      */                               {
/* 1021 */                                 PortalaccountQuery aq = new PortalaccountQuery();
/* 1022 */                                 aq.setLoginName(keys[1]);
/* 1023 */                                 aq.setLoginNameLike(false);
/* 1024 */                                 List accs = this.portalaccountService
/* 1025 */                                   .getPortalaccountList(aq);
/* 1026 */                                 if (accs.size() < 1) {
/* 1027 */                                   Portalaccount acc = new Portalaccount();
/* 1028 */                                   acc.setLoginName(keys[1]);
/* 1029 */                                   acc.setPassword(keys[2]);
/* 1030 */                                   acc.setName(keys[11]);
/* 1031 */                                   acc.setPhoneNumber(keys[12]);
/*      */ 
/* 1033 */                                   boolean haveSpeed = false;
/* 1034 */                                   PortalspeedQuery sq = new PortalspeedQuery();
/* 1035 */                                   sq.setNameLike(false);
/* 1036 */                                   sq.setName(keys[7]);
/* 1037 */                                   List speeds = this.portalspeedService
/* 1038 */                                     .getPortalspeedList(sq);
/* 1039 */                                   if (speeds
/* 1040 */                                     .size() == 1) {
/* 1041 */                                     acc.setSpeed(
/* 1042 */                                       ((Portalspeed)speeds
/* 1042 */                                       .get(0))
/* 1043 */                                       .getId());
/* 1044 */                                     haveSpeed = true;
/*      */                                   } else {
/* 1046 */                                     respMessage = "E07";
/*      */                                   }
/* 1048 */                                   if (haveSpeed) {
/* 1049 */                                     acc.setState(keys[3]);
/* 1050 */                                     acc.setTime(
/* 1051 */                                       Long.valueOf(keys[4]));
/* 1052 */                                     SimpleDateFormat format = new SimpleDateFormat(
/* 1053 */                                       "yyyy-MM-dd HH:mm:ss");
/* 1054 */                                     Date date = format
/* 1055 */                                       .parse(keys[5] + 
/* 1056 */                                       " 23:59:59");
/* 1057 */                                     acc.setDate(date);
/* 1058 */                                     acc.setOctets(
/* 1059 */                                       Long.valueOf(keys[6]));
/* 1060 */                                     acc.setMaclimitcount(
/* 1061 */                                       Integer.valueOf(keys[8]));
/* 1062 */                                     acc.setMaclimit(
/* 1063 */                                       Integer.valueOf(keys[9]));
/* 1064 */                                     acc.setAutologin(
/* 1065 */                                       Integer.valueOf(keys[10]));
/* 1066 */                                     this.portalaccountService
/* 1067 */                                       .addPortalaccount(acc);
/* 1068 */                                     respMessage = "E00";
/*      */                                   }
/*      */                                 } else {
/* 1071 */                                   respMessage = "E20";
/*      */                                 }
/*      */                               }
/*      */                               else {
/* 1075 */                                 respMessage = "E10";
/*      */                               }
/*      */                             }
/* 1078 */                             else respMessage = "E09";
/*      */                           }
/*      */                           else
/* 1081 */                             respMessage = "E08";
/*      */                         }
/*      */                         else
/* 1084 */                           respMessage = "E07";
/*      */                       }
/*      */                       else
/* 1087 */                         respMessage = "E06";
/*      */                     }
/*      */                     else
/* 1090 */                       respMessage = "E05";
/*      */                   }
/*      */                   else
/* 1093 */                     respMessage = "E04";
/*      */                 }
/*      */                 else
/* 1096 */                   respMessage = "E03";
/*      */               }
/*      */               else
/* 1099 */                 respMessage = "E02";
/*      */             }
/*      */             else {
/* 1102 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1108 */           if (keys[0].equals("002")) {
/* 1109 */             if (stringUtils.isNotEmpty(keys[1])) {
/* 1110 */               if (stringUtils.isNotEmpty(keys[2])) {
/* 1111 */                 if (stringUtils.isNotEmpty(keys[3])) {
/* 1112 */                   if (stringUtils.isNotEmpty(keys[4])) {
/* 1113 */                     if (stringUtils.isNotEmpty(keys[5])) {
/* 1114 */                       if (stringUtils.isNotEmpty(keys[6]))
/*      */                       {
/* 1116 */                         if (stringUtils.isNotEmpty(keys[7]))
/*      */                         {
/* 1118 */                           if (stringUtils.isNotEmpty(keys[8]))
/*      */                           {
/* 1120 */                             if (stringUtils.isNotEmpty(keys[9]))
/*      */                             {
/* 1122 */                               if (stringUtils.isNotEmpty(keys[10]))
/*      */                               {
/* 1124 */                                 PortalaccountQuery aq = new PortalaccountQuery();
/* 1125 */                                 aq.setLoginName(keys[1]);
/* 1126 */                                 aq.setLoginNameLike(false);
/* 1127 */                                 List accs = this.portalaccountService
/* 1128 */                                   .getPortalaccountList(aq);
/* 1129 */                                 if (accs.size() == 1) {
/* 1130 */                                   Portalaccount acc = (Portalaccount)accs.get(0);
/* 1131 */                                   kickAcc(acc.getId(), acc.getLoginName());
/*      */ 
/* 1133 */                                   acc.setPassword(keys[2]);
/* 1134 */                                   acc.setName(keys[11]);
/* 1135 */                                   acc.setPhoneNumber(keys[12]);
/*      */ 
/* 1137 */                                   boolean haveSpeed = false;
/* 1138 */                                   PortalspeedQuery sq = new PortalspeedQuery();
/* 1139 */                                   sq.setNameLike(false);
/* 1140 */                                   sq.setName(keys[7]);
/* 1141 */                                   List speeds = this.portalspeedService
/* 1142 */                                     .getPortalspeedList(sq);
/* 1143 */                                   if (speeds
/* 1144 */                                     .size() == 1) {
/* 1145 */                                     acc.setSpeed(
/* 1146 */                                       ((Portalspeed)speeds
/* 1146 */                                       .get(0))
/* 1147 */                                       .getId());
/* 1148 */                                     haveSpeed = true;
/*      */                                   } else {
/* 1150 */                                     respMessage = "E07";
/*      */                                   }
/* 1152 */                                   if (haveSpeed) {
/* 1153 */                                     if (!"none".equals(keys[4])) {
/* 1154 */                                       acc.setTime(
/* 1155 */                                         Long.valueOf(keys[4]));
/*      */                                     }
/* 1157 */                                     if (!"none".equals(keys[5])) {
/* 1158 */                                       SimpleDateFormat format = new SimpleDateFormat(
/* 1159 */                                         "yyyy-MM-dd HH:mm:ss");
/* 1160 */                                       Date date = format
/* 1161 */                                         .parse(keys[5] + 
/* 1162 */                                         " 23:59:59");
/* 1163 */                                       acc.setDate(date);
/*      */                                     }
/* 1165 */                                     if (!"none".equals(keys[5])) {
/* 1166 */                                       acc.setOctets(
/* 1167 */                                         Long.valueOf(keys[6]));
/*      */                                     }
/* 1169 */                                     acc.setState(keys[3]);
/* 1170 */                                     acc.setMaclimitcount(
/* 1171 */                                       Integer.valueOf(keys[8]));
/* 1172 */                                     acc.setMaclimit(
/* 1173 */                                       Integer.valueOf(keys[9]));
/* 1174 */                                     acc.setAutologin(
/* 1175 */                                       Integer.valueOf(keys[10]));
/* 1176 */                                     this.portalaccountService
/* 1177 */                                       .updatePortalaccountByKey(acc);
/* 1178 */                                     respMessage = "E00";
/*      */                                   }
/*      */                                 } else {
/* 1181 */                                   respMessage = "E30";
/*      */                                 }
/*      */                               }
/*      */                               else {
/* 1185 */                                 respMessage = "E10";
/*      */                               }
/*      */                             }
/* 1188 */                             else respMessage = "E09";
/*      */                           }
/*      */                           else
/* 1191 */                             respMessage = "E08";
/*      */                         }
/*      */                         else
/* 1194 */                           respMessage = "E07";
/*      */                       }
/*      */                       else
/* 1197 */                         respMessage = "E06";
/*      */                     }
/*      */                     else
/* 1200 */                       respMessage = "E05";
/*      */                   }
/*      */                   else
/* 1203 */                     respMessage = "E04";
/*      */                 }
/*      */                 else
/* 1206 */                   respMessage = "E03";
/*      */               }
/*      */               else
/* 1209 */                 respMessage = "E02";
/*      */             }
/*      */             else {
/* 1212 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1217 */           if (keys[0].equals("003")) {
/* 1218 */             if (stringUtils.isNotEmpty(keys[1])) {
/* 1219 */               PortalaccountQuery aq = new PortalaccountQuery();
/* 1220 */               aq.setLoginName(keys[1]);
/* 1221 */               aq.setLoginNameLike(false);
/* 1222 */               List accs = this.portalaccountService
/* 1223 */                 .getPortalaccountList(aq);
/* 1224 */               if (accs.size() == 1) {
/* 1225 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/* 1226 */                 kickAcc(acc.getId(), acc.getLoginName());
/* 1227 */                 PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1228 */                 macsq.setAccountId(acc.getId());
/* 1229 */                 this.macsService.deleteByQuery(macsq);
/* 1230 */                 this.portalaccountService.deleteByKey(acc.getId());
/* 1231 */                 respMessage = "E00";
/*      */               } else {
/* 1233 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/* 1236 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1241 */           if (keys[0].equals("004")) {
/* 1242 */             if (stringUtils.isNotEmpty(keys[1])) {
/* 1243 */               PortalaccountQuery aq = new PortalaccountQuery();
/* 1244 */               aq.setLoginName(keys[1]);
/* 1245 */               aq.setLoginNameLike(false);
/* 1246 */               List accs = this.portalaccountService
/* 1247 */                 .getPortalaccountList(aq);
/* 1248 */               if (accs.size() == 1) {
/* 1249 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/* 1250 */                 kickAcc(acc.getId(), acc.getLoginName());
/* 1251 */                 PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1252 */                 macsq.setAccountId(acc.getId());
/* 1253 */                 this.macsService.deleteByQuery(macsq);
/* 1254 */                 respMessage = "E00";
/*      */               } else {
/* 1256 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/* 1259 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1264 */           if (keys[0].equals("005")) {
/* 1265 */             if (stringUtils.isNotEmpty(keys[1])) {
/* 1266 */               PortalaccountQuery aq = new PortalaccountQuery();
/* 1267 */               aq.setLoginName(keys[1]);
/* 1268 */               aq.setLoginNameLike(false);
/* 1269 */               List accs = this.portalaccountService
/* 1270 */                 .getPortalaccountList(aq);
/* 1271 */               if (accs.size() == 1) {
/* 1272 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/* 1273 */                 kickAcc(acc.getId(), acc.getLoginName());
/* 1274 */                 respMessage = "E00";
/*      */               } else {
/* 1276 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/* 1279 */               respMessage = "E01";
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1284 */           if (keys[0].equals("006")) {
/* 1285 */             if (stringUtils.isNotEmpty(keys[1])) {
/* 1286 */               PortalaccountQuery aq = new PortalaccountQuery();
/* 1287 */               aq.setLoginName(keys[1]);
/* 1288 */               aq.setLoginNameLike(false);
/* 1289 */               List accs = this.portalaccountService
/* 1290 */                 .getPortalaccountList(aq);
/* 1291 */               if (accs.size() == 1) {
/* 1292 */                 Portalaccount acc = (Portalaccount)accs.get(0);
/* 1293 */                 respMessage = "E00\\t" + acc.toString();
/*      */               } else {
/* 1295 */                 respMessage = "E30";
/*      */               }
/*      */             } else {
/* 1298 */               respMessage = "E01";
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/* 1307 */     PrintWriter out = response.getWriter();
/* 1308 */     out.print(respMessage);
/* 1309 */     out.close();
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.APIController
 * JD-Core Version:    0.6.2
 */