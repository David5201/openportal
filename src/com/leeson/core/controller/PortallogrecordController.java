/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.OnlineInfo;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.query.PortallogrecordQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.core.utils.kickAllThread;
/*     */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*     */ import com.leeson.portal.core.model.AutoLoginMap;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.ipMap;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class PortallogrecordController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortallogrecordService portallogrecordService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountService portalaccountService;
/*  53 */   private static Config config = Config.getInstance();
/*  54 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*  55 */   private static SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  56 */   private static DecimalFormat df = new DecimalFormat(".##");
/*     */ 
/*     */   @RequestMapping({"/portallogrecord/list.action"})
/*     */   public String page(PortallogrecordQuery query, ModelMap model)
/*     */   {
/*  61 */     query.setInfoLike(true);
/*  62 */     if (stringUtils.isBlank(query.getInfo())) {
/*  63 */       query.setInfo(null);
/*     */     }
/*  65 */     query.orderbyId(false);
/*  66 */     Pagination pagination = this.portallogrecordService
/*  67 */       .getPortallogrecordListWithPage(query);
/*  68 */     model.addAttribute("pagination", pagination);
/*  69 */     model.addAttribute("query", query);
/*  70 */     return "portallogrecord/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portallogrecord/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/*  76 */     this.portallogrecordService.deleteByKey(id);
/*  77 */     return "redirect:/portallogrecord/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallogrecord/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/*  84 */     List list = Arrays.asList(ids);
/*  85 */     this.portallogrecordService.deleteByKeys(list);
/*     */ 
/*  87 */     return "redirect:/portallogrecord/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallogrecord/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/*  93 */     this.portallogrecordService.deleteAll();
/*  94 */     return "redirect:/portallogrecord/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalOnline/list.action"})
/*     */   public String pageOnline(String ipQ, String loginNameQ, String macQ, String typeQ, String apmacQ, String ssidQ, String agentQ, String basnameQ, String autoQ, String begintime, String endtime, Integer Page, ModelMap model)
/*     */     throws ParseException
/*     */   {
/* 105 */     int count = 0;
/* 106 */     Iterator it = onlineMap.getOnlineUserMap().keySet().iterator();
/* 107 */     while (it.hasNext()) {
/* 108 */       Object o = it.next();
/* 109 */       String host = o.toString();
/* 110 */       String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
/* 111 */       String username = loginInfo[0];
/* 112 */       String mac = loginInfo[4];
/*     */ 
/* 114 */       String type = loginInfo[6];
/* 115 */       String basname = loginInfo[11];
/* 116 */       String ssid = loginInfo[12];
/* 117 */       String apmac = loginInfo[13];
/* 118 */       String auto = loginInfo[14];
/* 119 */       String agent = loginInfo[15];
/* 120 */       if (stringUtils.isBlank(basname)) {
/* 121 */         basname = "base";
/*     */       }
/* 123 */       if (stringUtils.isBlank(ssid)) {
/* 124 */         ssid = "unknow";
/*     */       }
/* 126 */       if (stringUtils.isBlank(apmac)) {
/* 127 */         apmac = "unknow";
/*     */       }
/* 129 */       if (stringUtils.isBlank(agent)) {
/* 130 */         agent = "unknow";
/*     */       }
/*     */ 
/* 133 */       Boolean isIt = Boolean.valueOf(true);
/* 134 */       if ((stringUtils.isNotBlank(ipQ)) && 
/* 135 */         (!host.contains(ipQ))) {
/* 136 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 139 */       if ((stringUtils.isNotBlank(loginNameQ)) && 
/* 140 */         (!username.contains(loginNameQ))) {
/* 141 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 144 */       if ((stringUtils.isNotBlank(macQ)) && 
/* 145 */         (!mac.contains(macQ))) {
/* 146 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 149 */       if (stringUtils.isBlank(ipQ)) {
/* 150 */         ipQ = null;
/*     */       }
/* 152 */       if (stringUtils.isBlank(loginNameQ)) {
/* 153 */         loginNameQ = null;
/*     */       }
/* 155 */       if (stringUtils.isBlank(macQ)) {
/* 156 */         macQ = null;
/*     */       }
/*     */ 
/* 159 */       if ((stringUtils.isNotBlank(typeQ)) && 
/* 160 */         (!type.contains(typeQ))) {
/* 161 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 164 */       if (stringUtils.isBlank(typeQ)) {
/* 165 */         typeQ = null;
/*     */       }
/*     */ 
/* 168 */       if ((stringUtils.isNotBlank(basnameQ)) && 
/* 169 */         (!basname.contains(basnameQ))) {
/* 170 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 173 */       if (stringUtils.isBlank(basnameQ)) {
/* 174 */         basnameQ = null;
/*     */       }
/*     */ 
/* 177 */       if ((stringUtils.isNotBlank(ssidQ)) && 
/* 178 */         (!ssid.contains(ssidQ))) {
/* 179 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 182 */       if (stringUtils.isBlank(ssidQ)) {
/* 183 */         ssidQ = null;
/*     */       }
/*     */ 
/* 186 */       if ((stringUtils.isNotBlank(apmacQ)) && 
/* 187 */         (!apmac.contains(apmacQ))) {
/* 188 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 191 */       if (stringUtils.isBlank(apmacQ)) {
/* 192 */         apmacQ = null;
/*     */       }
/*     */ 
/* 195 */       if ((stringUtils.isNotBlank(autoQ)) && 
/* 196 */         (!auto.contains(autoQ))) {
/* 197 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 200 */       if (stringUtils.isBlank(autoQ)) {
/* 201 */         autoQ = null;
/*     */       }
/*     */ 
/* 204 */       if ((stringUtils.isNotBlank(agentQ)) && 
/* 205 */         (!agent.contains(agentQ))) {
/* 206 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 209 */       if (stringUtils.isBlank(agentQ)) {
/* 210 */         agentQ = null;
/*     */       }
/*     */ 
/* 213 */       String time = loginInfo[3];
/* 214 */       Date loginTime = ThreadLocalDateUtil.parse(time);
/* 215 */       String begintimeQS = null;
/* 216 */       String endtimeQS = null;
/* 217 */       Date begin_time = null;
/* 218 */       Date end_time = null;
/* 219 */       if (stringUtils.isNotBlank(begintime)) {
/* 220 */         begintimeQS = begintime + " 00:00:00";
/*     */         try {
/* 222 */           begin_time = inputformat.parse(begintimeQS);
/* 223 */           if (loginTime.getTime() < begin_time.getTime())
/* 224 */             isIt = Boolean.valueOf(false);
/*     */         }
/*     */         catch (ParseException localParseException) {
/*     */         }
/*     */       }
/* 229 */       if (stringUtils.isNotBlank(endtime)) {
/* 230 */         endtimeQS = endtime + " 23:59:59";
/*     */         try {
/* 232 */           end_time = inputformat.parse(endtimeQS);
/* 233 */           if (loginTime.getTime() > end_time.getTime())
/* 234 */             isIt = Boolean.valueOf(false);
/*     */         }
/*     */         catch (ParseException localParseException1) {
/*     */         }
/*     */       }
/* 239 */       if (stringUtils.isBlank(begintime)) {
/* 240 */         begintime = null;
/*     */       }
/* 242 */       if (stringUtils.isBlank(endtime)) {
/* 243 */         endtime = null;
/*     */       }
/*     */ 
/* 247 */       if (isIt.booleanValue()) {
/* 248 */         count++;
/*     */       }
/*     */     }
/*     */ 
/* 252 */     List onlineInfos = new ArrayList();
/* 253 */     if ((Page == null) || (Page.intValue() <= 0)) {
/* 254 */       Page = Integer.valueOf(1);
/*     */     }
/* 256 */     Integer id = Integer.valueOf(1);
/* 257 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
/* 258 */     while (iterator.hasNext()) {
/* 259 */       OnlineInfo onlineInfo = new OnlineInfo();
/* 260 */       Object o = iterator.next();
/* 261 */       String host = o.toString();
/* 262 */       String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
/* 263 */       String username = loginInfo[0];
/* 264 */       String time = loginInfo[3];
/* 265 */       String mac = loginInfo[4];
/*     */ 
/* 267 */       String inS = "0 M";
/* 268 */       String outS = "0 M";
/* 269 */       String octetsS = "0 M";
/*     */       try {
/* 271 */         double in = Double.valueOf(loginInfo[7]).doubleValue();
/* 272 */         double out = Double.valueOf(loginInfo[8]).doubleValue();
/* 273 */         double octets = in + out;
/* 274 */         in /= 1048576.0D;
/* 275 */         out /= 1048576.0D;
/* 276 */         octets /= 1048576.0D;
/* 277 */         inS = df.format(in);
/* 278 */         outS = df.format(out);
/* 279 */         octetsS = df.format(octets);
/* 280 */         if (inS.startsWith(".")) {
/* 281 */           inS = "0" + inS;
/*     */         }
/* 283 */         if (outS.startsWith(".")) {
/* 284 */           outS = "0" + outS;
/*     */         }
/* 286 */         if (octetsS.startsWith(".")) {
/* 287 */           octetsS = "0" + octetsS;
/*     */         }
/* 289 */         inS = inS + " M";
/* 290 */         outS = outS + " M";
/* 291 */         octetsS = octetsS + " M";
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/* 295 */       String type = loginInfo[6];
/* 296 */       String basname = loginInfo[11];
/* 297 */       String ssid = loginInfo[12];
/* 298 */       String apmac = loginInfo[13];
/* 299 */       String auto = loginInfo[14];
/* 300 */       String agent = loginInfo[15];
/* 301 */       if (stringUtils.isBlank(basname)) {
/* 302 */         basname = "base";
/*     */       }
/* 304 */       if (stringUtils.isBlank(ssid)) {
/* 305 */         ssid = "unknow";
/*     */       }
/* 307 */       if (stringUtils.isBlank(apmac)) {
/* 308 */         apmac = "unknow";
/*     */       }
/* 310 */       if (stringUtils.isBlank(agent)) {
/* 311 */         agent = "unknow";
/*     */       }
/*     */ 
/* 314 */       Date loginTime = ThreadLocalDateUtil.parse(time);
/* 315 */       String nowString = ThreadLocalDateUtil.format(new Date());
/* 316 */       Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 317 */       Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 318 */       costTime = Long.valueOf(costTime.longValue() / 60000L);
/* 319 */       Boolean isIt = Boolean.valueOf(true);
/* 320 */       if ((stringUtils.isNotBlank(ipQ)) && 
/* 321 */         (!host.contains(ipQ))) {
/* 322 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 325 */       if ((stringUtils.isNotBlank(loginNameQ)) && 
/* 326 */         (!username.contains(loginNameQ))) {
/* 327 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 330 */       if ((stringUtils.isNotBlank(macQ)) && 
/* 331 */         (!mac.contains(macQ))) {
/* 332 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 335 */       if (stringUtils.isBlank(ipQ)) {
/* 336 */         ipQ = null;
/*     */       }
/* 338 */       if (stringUtils.isBlank(loginNameQ)) {
/* 339 */         loginNameQ = null;
/*     */       }
/* 341 */       if (stringUtils.isBlank(macQ)) {
/* 342 */         macQ = null;
/*     */       }
/*     */ 
/* 346 */       if ((stringUtils.isNotBlank(typeQ)) && 
/* 347 */         (!type.contains(typeQ))) {
/* 348 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 351 */       if (stringUtils.isBlank(typeQ)) {
/* 352 */         typeQ = null;
/*     */       }
/*     */ 
/* 355 */       if ((stringUtils.isNotBlank(basnameQ)) && 
/* 356 */         (!basname.contains(basnameQ))) {
/* 357 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 360 */       if (stringUtils.isBlank(basnameQ)) {
/* 361 */         basnameQ = null;
/*     */       }
/*     */ 
/* 364 */       if ((stringUtils.isNotBlank(ssidQ)) && 
/* 365 */         (!ssid.contains(ssidQ))) {
/* 366 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 369 */       if (stringUtils.isBlank(ssidQ)) {
/* 370 */         ssidQ = null;
/*     */       }
/*     */ 
/* 373 */       if ((stringUtils.isNotBlank(apmacQ)) && 
/* 374 */         (!apmac.contains(apmacQ))) {
/* 375 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 378 */       if (stringUtils.isBlank(apmacQ)) {
/* 379 */         apmacQ = null;
/*     */       }
/*     */ 
/* 382 */       if ((stringUtils.isNotBlank(autoQ)) && 
/* 383 */         (!auto.contains(autoQ))) {
/* 384 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 387 */       if (stringUtils.isBlank(autoQ)) {
/* 388 */         autoQ = null;
/*     */       }
/*     */ 
/* 391 */       if ((stringUtils.isNotBlank(agentQ)) && 
/* 392 */         (!agent.contains(agentQ))) {
/* 393 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 396 */       if (stringUtils.isBlank(agentQ)) {
/* 397 */         agentQ = null;
/*     */       }
/*     */ 
/* 400 */       String begintimeQS = null;
/* 401 */       String endtimeQS = null;
/* 402 */       Date begin_time = null;
/* 403 */       Date end_time = null;
/* 404 */       if (stringUtils.isNotBlank(begintime)) {
/* 405 */         begintimeQS = begintime + " 00:00:00";
/*     */         try {
/* 407 */           begin_time = inputformat.parse(begintimeQS);
/* 408 */           if (loginTime.getTime() < begin_time.getTime())
/* 409 */             isIt = Boolean.valueOf(false);
/*     */         }
/*     */         catch (ParseException localParseException2) {
/*     */         }
/*     */       }
/* 414 */       if (stringUtils.isNotBlank(endtime)) {
/* 415 */         endtimeQS = endtime + " 23:59:59";
/*     */         try {
/* 417 */           end_time = inputformat.parse(endtimeQS);
/* 418 */           if (loginTime.getTime() > end_time.getTime())
/* 419 */             isIt = Boolean.valueOf(false);
/*     */         }
/*     */         catch (ParseException localParseException3) {
/*     */         }
/*     */       }
/* 424 */       if (stringUtils.isBlank(begintime)) {
/* 425 */         begintime = null;
/*     */       }
/* 427 */       if (stringUtils.isBlank(endtime)) {
/* 428 */         endtime = null;
/*     */       }
/*     */ 
/* 432 */       if (isIt.booleanValue())
/*     */       {
/* 434 */         if (id.intValue() > (Page.intValue() - 1) * 10) {
/* 435 */           onlineInfo.setIp(host);
/* 436 */           onlineInfo.setLoginName(username);
/* 437 */           onlineInfo.setStartDate(loginTime);
/* 438 */           onlineInfo.setTime(costTime);
/* 439 */           onlineInfo.setMac(mac);
/* 440 */           onlineInfo.setInS(inS);
/* 441 */           onlineInfo.setOutS(outS);
/* 442 */           onlineInfo.setOctetsS(octetsS);
/*     */ 
/* 444 */           if ("0".equals(type))
/* 445 */             onlineInfo.setType("一键认证");
/* 446 */           else if ("1".equals(type))
/* 447 */             onlineInfo.setType("本地用户");
/* 448 */           else if ("2".equals(type))
/* 449 */             onlineInfo.setType("Radius");
/* 450 */           else if ("3".equals(type))
/* 451 */             onlineInfo.setType("APP认证");
/* 452 */           else if ("4".equals(type))
/* 453 */             onlineInfo.setType("短信认证");
/* 454 */           else if ("5".equals(type))
/* 455 */             onlineInfo.setType("微信认证");
/* 456 */           else if ("6".equals(type))
/* 457 */             onlineInfo.setType("公众号认证");
/* 458 */           else if ("7".equals(type))
/* 459 */             onlineInfo.setType("访客认证");
/* 460 */           else if ("8".equals(type)) {
/* 461 */             onlineInfo.setType("延迟认证");
/*     */           }
/* 463 */           onlineInfo.setBasname(basname);
/* 464 */           onlineInfo.setSsid(ssid);
/* 465 */           onlineInfo.setApmac(apmac);
/* 466 */           onlineInfo.setAuto(auto);
/* 467 */           onlineInfo.setAgent(agent);
/*     */ 
/* 469 */           if ("1".equals(type)) {
/* 470 */             PortalaccountQuery aq = new PortalaccountQuery();
/* 471 */             aq.setLoginName(username);
/* 472 */             List a = this.portalaccountService
/* 473 */               .getPortalaccountList(aq);
/* 474 */             if ((a != null) && (a.size() == 1)) {
/* 475 */               onlineInfo.setState(((Portalaccount)a.get(0)).getState());
/*     */             }
/*     */           }
/* 478 */           onlineInfo.setId(id);
/* 479 */           onlineInfos.add(onlineInfo);
/*     */         }
/*     */ 
/* 482 */         id = Integer.valueOf(id.intValue() + 1);
/* 483 */         if (id.intValue() > Page.intValue() * 10)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 489 */     Pagination pagination = new Pagination(Page.intValue(), 10, count, onlineInfos);
/* 490 */     model.addAttribute("pagination", pagination);
/* 491 */     model.addAttribute("ipQ", ipQ);
/* 492 */     model.addAttribute("loginNameQ", loginNameQ);
/* 493 */     model.addAttribute("macQ", macQ);
/*     */ 
/* 495 */     model.addAttribute("typeQ", typeQ);
/* 496 */     model.addAttribute("basnameQ", basnameQ);
/* 497 */     model.addAttribute("ssidQ", ssidQ);
/* 498 */     model.addAttribute("apmacQ", apmacQ);
/* 499 */     model.addAttribute("autoQ", autoQ);
/* 500 */     model.addAttribute("agentQ", agentQ);
/* 501 */     model.addAttribute("begintime", begintime);
/* 502 */     model.addAttribute("endtime", endtime);
/* 503 */     return "portallogrecord/listOnline";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalOnline/kick.action"})
/*     */   public String kick(@RequestParam String ip)
/*     */   {
/* 509 */     Kick.kickUserByAdmin(ip);
/* 510 */     return "redirect:/portalOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalOnline/kicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String kicks(@RequestParam String[] ips)
/*     */   {
/* 516 */     for (String ip : ips) {
/* 517 */       Kick.kickUserByAdmin(ip);
/*     */     }
/* 519 */     return "redirect:/portalOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalOnline/kicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String kicks()
/*     */   {
/* 525 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
/* 526 */     while (iterator.hasNext()) {
/* 527 */       Object o = iterator.next();
/* 528 */       String host = o.toString();
/*     */ 
/* 530 */       kickAllThread.kickAll(host);
/*     */     }
/* 532 */     return "redirect:/portalOnline/list.action";
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/kickAll.action"})
/*     */   public Map<Integer, String> kickAll() {
/* 539 */     AutoLoginMacMap.getInstance().getAutoLoginMacMap().clear();
/* 540 */     AutoLoginMap.getInstance().getAutoLoginMap().clear();
/* 541 */     int count = 1;
/* 542 */     HashMap kickMap = new HashMap();
/* 543 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
/* 544 */     while (iterator.hasNext()) {
/* 545 */       Object o = iterator.next();
/* 546 */       String host = o.toString();
/* 547 */       kickMap.put(Integer.valueOf(count), host);
/* 548 */       Kick.kickUserAllAPI(host);
/* 549 */       count++;
/*     */     }
/* 551 */     return kickMap;
/*     */   }
/*     */   @ResponseBody
/*     */   @RequestMapping({"/kickIP.action"})
/*     */   public Map<String, Integer> kickIP(String ip) {
/* 557 */     HashMap kickMap = new HashMap();
/* 558 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
/* 559 */     while (iterator.hasNext())
/*     */       try {
/* 561 */         Object o = iterator.next();
/* 562 */         String host = o.toString();
/* 563 */         String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
/* 564 */         String mac = loginInfo[4];
/* 565 */         String[] ips = host.split(":");
/* 566 */         String uip = ips[0];
/* 567 */         if (uip.equals(ip)) {
/* 568 */           Kick.kickUserIPAPI(host);
/* 569 */           kickMap.put(uip, Integer.valueOf(1));
/* 570 */           AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(mac);
/* 571 */           AutoLoginMap.getInstance().getAutoLoginMap().remove(mac);
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/* 576 */     return kickMap;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalOnline/deleteOnline.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deleteOnline()
/*     */   {
/* 582 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
/* 583 */     while (iterator.hasNext()) {
/* 584 */       Object o = iterator.next();
/* 585 */       String host = o.toString();
/* 586 */       Kick.deleteOnline(host);
/*     */     }
/* 588 */     return "redirect:/portalOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalOnline/unLock.action"})
/*     */   public String unLock(ModelMap model) throws ParseException
/*     */   {
/* 594 */     ipMap.getInstance().getIpmap().clear();
/* 595 */     model.addAttribute("msg", "已经解锁所有IP地址！！！");
/*     */ 
/* 597 */     String authInterface = ((Portalbas)config.getConfigMap().get("")).getAuthInterface();
/* 598 */     List onlineInfos = new ArrayList();
/* 599 */     Integer id = Integer.valueOf(1);
/* 600 */     Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
/* 601 */     while (iterator.hasNext()) {
/* 602 */       OnlineInfo onlineInfo = new OnlineInfo();
/* 603 */       Object o = iterator.next();
/* 604 */       String host = o.toString();
/* 605 */       String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
/* 606 */       String username = loginInfo[0];
/* 607 */       String time = loginInfo[3];
/* 608 */       Date loginTime = ThreadLocalDateUtil.parse(time);
/* 609 */       String nowString = ThreadLocalDateUtil.format(new Date());
/* 610 */       Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 611 */       Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 612 */       costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/* 613 */       Boolean isIt = Boolean.valueOf(true);
/*     */ 
/* 615 */       if (isIt.booleanValue()) {
/* 616 */         onlineInfo.setIp(host);
/* 617 */         onlineInfo.setLoginName(username);
/* 618 */         onlineInfo.setStartDate(loginTime);
/* 619 */         onlineInfo.setTime(costTime);
/* 620 */         if (authInterface.contains("1")) {
/* 621 */           PortalaccountQuery aq = new PortalaccountQuery();
/* 622 */           aq.setLoginName(username);
/* 623 */           List a = this.portalaccountService
/* 624 */             .getPortalaccountList(aq);
/* 625 */           if ((a != null) && (a.size() == 1)) {
/* 626 */             onlineInfo.setState(((Portalaccount)a.get(0)).getState());
/*     */           }
/*     */         }
/* 629 */         onlineInfo.setId(id);
/* 630 */         onlineInfos.add(onlineInfo);
/* 631 */         id = Integer.valueOf(id.intValue() + 1);
/* 632 */         if (id.intValue() > 10)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 638 */     Pagination pagination = new Pagination(1, 10, onlineInfos.size(), 
/* 639 */       onlineInfos);
/* 640 */     model.addAttribute("pagination", pagination);
/*     */ 
/* 642 */     return "portallogrecord/listOnline";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallogrecordController
 * JD-Core Version:    0.6.2
 */