/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalaccountgroup;
/*     */ import com.leeson.core.bean.Portalcard;
/*     */ import com.leeson.core.bean.Portalcardcategory;
/*     */ import com.leeson.core.bean.Portalorder;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.query.PortalaccountmacsQuery;
/*     */ import com.leeson.core.query.PortalcardcategoryQuery;
/*     */ import com.leeson.core.query.PortalspeedQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalaccountgroupService;
/*     */ import com.leeson.core.service.PortalaccountmacsService;
/*     */ import com.leeson.core.service.PortalcardService;
/*     */ import com.leeson.core.service.PortalcardcategoryService;
/*     */ import com.leeson.core.service.PortalconfigService;
/*     */ import com.leeson.core.service.PortalorderService;
/*     */ import com.leeson.core.service.PortalspeedService;
/*     */ import com.leeson.core.utils.ExcelUtils;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*     */ import com.leeson.radius.core.utils.COAThread;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class PortalaccountController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountService portalaccountService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountmacsService macsService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalconfigService portalconfigService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalspeedService portalspeedService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardcategoryService portalcardcategoryService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardService portalcardService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalorderService portalorderService;
/*     */ 
/*     */   @Autowired
/*     */   private ConfigService configService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountgroupService portalaccountgroupService;
/*  85 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*     */ 
/*     */   @RequestMapping({"/portalaccount/list.action"})
/*     */   public String page(PortalaccountQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  91 */     query.setLoginNameLike(true);
/*  92 */     query.setNameLike(true);
/*  93 */     if (stringUtils.isBlank(query.getLoginName())) {
/*  94 */       query.setLoginName(null);
/*     */     }
/*  96 */     if (stringUtils.isBlank(query.getName())) {
/*  97 */       query.setName(null);
/*     */     }
/*  99 */     if (stringUtils.isBlank(query.getState())) {
/* 100 */       query.setState(null);
/*     */     }
/*     */ 
/* 103 */     Pagination pagination = this.portalaccountService
/* 104 */       .getPortalaccountListWithPage(query);
/* 105 */     model.addAttribute("pagination", pagination);
/* 106 */     model.addAttribute("query", query);
/*     */ 
/* 108 */     List speeds = this.portalspeedService
/* 109 */       .getPortalspeedList(new PortalspeedQuery());
/* 110 */     model.addAttribute("speeds", speeds);
/*     */ 
/* 112 */     return "portalaccount/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/addV.action"})
/*     */   public String addV(ModelMap model)
/*     */   {
/* 118 */     List speeds = this.portalspeedService
/* 119 */       .getPortalspeedList(new PortalspeedQuery());
/* 120 */     model.addAttribute("speeds", speeds);
/*     */ 
/* 122 */     return "portalaccount/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalaccount e, Long speed, ModelMap model)
/*     */   {
/* 128 */     Integer haveAcc = this.portalaccountService
/* 129 */       .getPortalaccountCount(new PortalaccountQuery());
/* 130 */     if ((haveAcc != null) && 
/* 131 */       (haveAcc.intValue() >= Integer.valueOf(
/* 132 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
/* 132 */       .getCoreConfigMap().get("core"))[1]).intValue()))
/*     */     {
/* 133 */       model.addAttribute("msg", "账户超过授权数！");
/* 134 */       model.addAttribute("entity", e);
/* 135 */       List speeds = this.portalspeedService
/* 136 */         .getPortalspeedList(new PortalspeedQuery());
/* 137 */       model.addAttribute("speeds", speeds);
/* 138 */       return "portalaccount/save";
/*     */     }
/*     */ 
/* 142 */     if ((stringUtils.isBlank(e.getLoginName())) || 
/* 143 */       (stringUtils.isBlank(e.getPassword()))) {
/* 144 */       model.addAttribute("msg", "登录名和密码不能为空！");
/* 145 */       model.addAttribute("entity", e);
/* 146 */       List speeds = this.portalspeedService
/* 147 */         .getPortalspeedList(new PortalspeedQuery());
/* 148 */       model.addAttribute("speeds", speeds);
/* 149 */       return "portalaccount/save";
/*     */     }
/* 151 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 152 */     aq.setLoginName(e.getLoginName());
/* 153 */     aq.setLoginNameLike(false);
/* 154 */     if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
/* 155 */       model.addAttribute("msg", "登录名已经存在！");
/* 156 */       model.addAttribute("entity", e);
/* 157 */       List speeds = this.portalspeedService
/* 158 */         .getPortalspeedList(new PortalspeedQuery());
/* 159 */       model.addAttribute("speeds", speeds);
/* 160 */       return "portalaccount/save";
/*     */     }
/*     */ 
/* 163 */     if (stringUtils.isBlank(e.getGender())) {
/* 164 */       e.setGender(null);
/*     */     }
/*     */ 
/* 167 */     Portalaccountgroup ag = this.portalaccountgroupService
/* 168 */       .getPortalaccountgroupByKey(Long.valueOf(1L));
/* 169 */     if (stringUtils.isBlank(e.getState())) {
/* 170 */       e.setState(ag.getState());
/*     */     }
/* 172 */     if (e.getMaclimitcount() == null) {
/* 173 */       e.setMaclimitcount(ag.getMaclimitcount());
/*     */     }
/* 175 */     if (e.getMaclimit() == null) {
/* 176 */       e.setMaclimit(ag.getMaclimit());
/*     */     }
/* 178 */     if (e.getAutologin() == null) {
/* 179 */       e.setAutologin(ag.getAutologin());
/*     */     }
/* 181 */     if (e.getSpeed() == null) {
/* 182 */       e.setSpeed(ag.getSpeed());
/*     */     }
/* 184 */     e.setDate(ag.getDate());
/* 185 */     e.setTime(ag.getTime());
/* 186 */     e.setOctets(ag.getOctets());
/* 187 */     this.portalaccountService.addPortalaccount(e);
/*     */ 
/* 189 */     return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/editV.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 195 */     Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
/* 196 */     model.addAttribute("entity", e);
/*     */ 
/* 198 */     List speeds = this.portalspeedService
/* 199 */       .getPortalspeedList(new PortalspeedQuery());
/* 200 */     model.addAttribute("speeds", speeds);
/*     */ 
/* 202 */     return "portalaccount/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalaccount e, ModelMap model)
/*     */   {
/* 208 */     Portalaccount u = this.portalaccountService.getPortalaccountByKey(e.getId());
/* 209 */     if (stringUtils.isBlank(e.getPassword())) {
/* 210 */       e.setPassword(u.getPassword());
/*     */     }
/* 212 */     e.setLoginName(u.getLoginName());
/* 213 */     e.setDate(u.getDate());
/* 214 */     e.setTime(u.getTime());
/* 215 */     e.setOctets(u.getOctets());
/* 216 */     if (stringUtils.isBlank(e.getState())) {
/* 217 */       e.setState("0");
/*     */     }
/* 219 */     if (stringUtils.isBlank(e.getGender())) {
/* 220 */       e.setGender(null);
/*     */     }
/* 222 */     if (e.getMaclimitcount() == null) {
/* 223 */       e.setMaclimitcount(Integer.valueOf(1));
/*     */     }
/* 225 */     if (stringUtils.isNotBlank(e.getEx4()))
/*     */       try {
/* 227 */         int ex4 = Integer.valueOf(e.getEx4()).intValue();
/* 228 */         if (ex4 != 0) {
/* 229 */           ex4--;
/* 230 */           e.setEx4(String.valueOf(ex4));
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/* 235 */     this.portalaccountService.updatePortalaccountByKeyAll(e);
/*     */ 
/* 237 */     return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/edit.action"})
/*     */   public String editState(@RequestParam Long id, @RequestParam String to)
/*     */   {
/* 243 */     Portalaccount account = this.portalaccountService.getPortalaccountByKey(id);
/* 244 */     if (to.equals("state")) {
/* 245 */       int state = Integer.valueOf(account.getState()).intValue() + 1;
/* 246 */       if (state >= 5) {
/* 247 */         state = 0;
/*     */       }
/* 249 */       account.setState(String.valueOf(state));
/*     */     }
/* 251 */     if (to.equals("password")) {
/* 252 */       account.setPassword("1234");
/*     */     }
/* 254 */     if (to.equals("maclimit")) {
/* 255 */       if (account.getMaclimit().intValue() == 0)
/* 256 */         account.setMaclimit(Integer.valueOf(1));
/*     */       else {
/* 258 */         account.setMaclimit(Integer.valueOf(0));
/*     */       }
/*     */     }
/* 261 */     if (to.equals("auto")) {
/* 262 */       if (account.getAutologin().intValue() == 0)
/* 263 */         account.setAutologin(Integer.valueOf(1));
/*     */       else {
/* 265 */         account.setAutologin(Integer.valueOf(0));
/*     */       }
/*     */     }
/*     */ 
/* 269 */     this.portalaccountService.updatePortalaccountByKey(account);
/* 270 */     return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/pay.action"})
/*     */   public String pay(@RequestParam Long id, ModelMap model)
/*     */   {
/* 276 */     Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
/* 277 */     model.addAttribute("entity", e);
/* 278 */     List list = this.portalcardcategoryService
/* 279 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 280 */     model.addAttribute("list", list);
/* 281 */     return "portalaccount/pay";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/pay.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String payDo(@RequestParam Long id, Long cardCategoryId, ModelMap model)
/*     */   {
/* 288 */     if (id == null) {
/* 289 */       model.addAttribute("msg", "用户信息丢失，请重新选择！！");
/* 290 */       return "redirect:/portalaccount/list.action";
/*     */     }
/* 292 */     Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
/* 293 */     if (e == null) {
/* 294 */       model.addAttribute("msg", "用户不存在，请重新选择！！");
/* 295 */       return "redirect:/portalaccount/list.action";
/*     */     }
/* 297 */     if (cardCategoryId == null) {
/* 298 */       model.addAttribute("entity", e);
/* 299 */       List list = this.portalcardcategoryService
/* 300 */         .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 301 */       model.addAttribute("list", list);
/* 302 */       model.addAttribute("msg", "请正确选择充值卡类型！！");
/* 303 */       return "portalaccount/pay";
/*     */     }
/* 305 */     Portalcardcategory card = this.portalcardcategoryService
/* 306 */       .getPortalcardcategoryByKey(cardCategoryId);
/* 307 */     if (card == null) {
/* 308 */       model.addAttribute("entity", e);
/* 309 */       List list = this.portalcardcategoryService
/* 310 */         .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 311 */       model.addAttribute("list", list);
/* 312 */       model.addAttribute("msg", "该充值卡类型不存在！！");
/* 313 */       return "portalaccount/pay";
/*     */     }
/* 315 */     model.addAttribute("entity", e);
/* 316 */     model.addAttribute("card", card);
/* 317 */     List speeds = this.portalspeedService
/* 318 */       .getPortalspeedList(new PortalspeedQuery());
/* 319 */     model.addAttribute("speeds", speeds);
/* 320 */     return "portalaccount/payDo";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/pays.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String pays(@RequestParam Long id, Long cardCategoryId, ModelMap model, HttpServletRequest request)
/*     */   {
/* 327 */     Portaluser user = (Portaluser)request.getSession()
/* 328 */       .getAttribute("user");
/* 329 */     if ((user == null) || (user.getId() == null)) {
/* 330 */       model.addAttribute("msg", "管理员登录信息丢失，请重新选择！！");
/* 331 */       return "redirect:/portalaccount/list.action";
/*     */     }
/* 333 */     if (id == null) {
/* 334 */       model.addAttribute("msg", "用户信息丢失，请重新选择！！");
/* 335 */       return "redirect:/portalaccount/list.action";
/*     */     }
/* 337 */     Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
/* 338 */     if (e == null) {
/* 339 */       model.addAttribute("msg", "用户不存在，请重新选择！！");
/* 340 */       return "redirect:/portalaccount/list.action";
/*     */     }
/* 342 */     if (cardCategoryId == null) {
/* 343 */       model.addAttribute("entity", e);
/* 344 */       List list = this.portalcardcategoryService
/* 345 */         .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 346 */       model.addAttribute("list", list);
/* 347 */       model.addAttribute("msg", "请正确选择充值卡类型！！");
/* 348 */       return "portalaccount/pay";
/*     */     }
/* 350 */     Portalcardcategory card = this.portalcardcategoryService
/* 351 */       .getPortalcardcategoryByKey(cardCategoryId);
/* 352 */     if (card == null) {
/* 353 */       model.addAttribute("entity", e);
/* 354 */       List list = this.portalcardcategoryService
/* 355 */         .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 356 */       model.addAttribute("list", list);
/* 357 */       model.addAttribute("msg", "该充值卡类型不存在！！");
/* 358 */       return "portalaccount/pay";
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 363 */       String categoryType = card.getState();
/*     */       String payType;
/* 365 */       if (categoryType.equals("0")) {
/* 366 */         payType = "2";
/*     */       }
/*     */       else
/*     */       {
/* 367 */         if (categoryType.equals("4"))
/* 368 */           payType = "4";
/*     */         else
/* 370 */           payType = "3";
/*     */       }
/* 372 */       if (card.getMoney() == null) {
/* 373 */         card.setMoney(Double.valueOf(0.0D));
/*     */       }
/* 375 */       Long time = card.getTime();
/* 376 */       Long payTime = Long.valueOf(0L);
/* 377 */       if (categoryType.equals("0"))
/* 378 */         payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L);
/* 379 */       else if (categoryType.equals("1"))
/* 380 */         payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L);
/* 381 */       else if (categoryType.equals("2"))
/* 382 */         payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L);
/* 383 */       else if (categoryType.equals("3"))
/* 384 */         payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L * 12L);
/* 385 */       else if (categoryType.equals("4")) {
/* 386 */         payTime = Long.valueOf(time.longValue() * 1024L * 1024L);
/*     */       }
/* 388 */       String cdkey = UUID.randomUUID().toString();
/* 389 */       Portalcard cardOrder = new Portalcard();
/* 390 */       cardOrder.setName(card.getName());
/* 391 */       cardOrder.setDescription(card.getDescription());
/* 392 */       cardOrder.setCategoryType(categoryType);
/* 393 */       cardOrder.setPayType(payType);
/* 394 */       cardOrder.setPayTime(payTime);
/* 395 */       cardOrder.setAccountDel(Integer.valueOf(0));
/* 396 */       cardOrder.setUserDel(Integer.valueOf(0));
/* 397 */       cardOrder.setCdKey(cdkey);
/* 398 */       cardOrder.setMoney(card.getMoney());
/* 399 */       cardOrder.setState("2");
/* 400 */       cardOrder.setBuyDate(new Date());
/* 401 */       cardOrder.setAccountName(e.getLoginName());
/* 402 */       cardOrder.setAccountId(id);
/* 403 */       cardOrder.setMaclimit(card.getMaclimit());
/* 404 */       cardOrder.setMaclimitcount(card.getMaclimitcount());
/* 405 */       cardOrder.setAutologin(card.getAutologin());
/* 406 */       cardOrder.setSpeed(card.getSpeed());
/* 407 */       cardOrder.setPayDate(new Date());
/*     */ 
/* 410 */       String state = e.getState();
/* 411 */       Long oldDate = Long.valueOf(e.getDate().getTime());
/* 412 */       Long oldTime = e.getTime();
/* 413 */       Long oldOctets = e.getOctets();
/* 414 */       if (oldOctets == null) {
/* 415 */         oldOctets = Long.valueOf(0L);
/*     */       }
/* 417 */       if (oldTime == null) {
/* 418 */         oldTime = Long.valueOf(0L);
/*     */       }
/* 420 */       Long now = Long.valueOf(new Date().getTime());
/*     */ 
/* 423 */       if (payType.equals("3"))
/*     */       {
/*     */         Long newDate;
/* 425 */         if (oldDate.longValue() < new Date().getTime())
/* 426 */           newDate = Long.valueOf(now.longValue() + payTime.longValue());
/*     */         else {
/* 428 */           newDate = Long.valueOf(oldDate.longValue() + payTime.longValue());
/*     */         }
/* 430 */         e.setDate(new Date(newDate.longValue()));
/* 431 */         e.setState(payType);
/*     */       }
/*     */ 
/* 434 */       if (payType.equals("2")) {
/* 435 */         if (oldTime.longValue() < 0L)
/* 436 */           e.setTime(payTime);
/*     */         else {
/* 438 */           e.setTime(Long.valueOf(oldTime.longValue() + payTime.longValue()));
/*     */         }
/* 440 */         e.setState(payType);
/*     */       }
/*     */ 
/* 443 */       if (payType.equals("4")) {
/* 444 */         if (oldOctets.longValue() < 0L)
/* 445 */           e.setOctets(payTime);
/*     */         else {
/* 447 */           e.setOctets(Long.valueOf(oldOctets.longValue() + payTime.longValue()));
/*     */         }
/* 449 */         e.setState(payType);
/*     */       }
/* 451 */       if (state.equals("1")) {
/* 452 */         e.setState(state);
/*     */       }
/* 454 */       e.setMaclimit(card.getMaclimit());
/* 455 */       e.setMaclimitcount(card.getMaclimitcount());
/* 456 */       e.setAutologin(card.getAutologin());
/* 457 */       e.setSpeed(card.getSpeed());
/* 458 */       this.portalaccountService.updatePortalaccountByKey(e);
/*     */ 
/* 460 */       this.portalcardService.addPortalcard(cardOrder);
/*     */ 
/* 462 */       Portalorder order = new Portalorder();
/* 463 */       order.setAccountDel(cardOrder.getAccountDel());
/* 464 */       order.setAccountId(cardOrder.getAccountId());
/* 465 */       order.setAccountName(cardOrder.getAccountName());
/* 466 */       order.setBuyDate(cardOrder.getBuyDate());
/* 467 */       order.setBuyer(cardOrder.getAccountName());
/* 468 */       order.setCategoryType(cardOrder.getCategoryType());
/* 469 */       order.setCdKey(cardOrder.getCdKey());
/* 470 */       order.setDescription(cardOrder.getDescription());
/* 471 */       order.setMoney(cardOrder.getMoney());
/* 472 */       order.setName(cardOrder.getName());
/* 473 */       order.setPayby(Integer.valueOf(0));
/* 474 */       order.setPayDate(cardOrder.getPayDate());
/* 475 */       order.setPayTime(cardOrder.getPayTime());
/* 476 */       order.setPayType(cardOrder.getPayType());
/* 477 */       order.setSeller(user.getLoginName());
/* 478 */       order.setState("1");
/* 479 */       order.setTradeno(cardOrder.getCdKey());
/* 480 */       order.setUserDel(cardOrder.getUserDel());
/* 481 */       this.portalorderService.addPortalorder(order);
/*     */ 
/* 483 */       model.addAttribute("msg", "充值成功！！");
/* 484 */       PortalaccountQuery query = new PortalaccountQuery();
/* 485 */       query.setId(id);
/* 486 */       Pagination pagination = this.portalaccountService
/* 487 */         .getPortalaccountListWithPage(query);
/* 488 */       model.addAttribute("pagination", pagination);
/* 489 */       model.addAttribute("query", query);
/* 490 */       List speeds = this.portalspeedService
/* 491 */         .getPortalspeedList(new PortalspeedQuery());
/* 492 */       model.addAttribute("speeds", speeds);
/* 493 */       return "portalaccount/list";
/*     */     } catch (Exception ex) {
/* 495 */       model.addAttribute("msg", "发生错误！！");
/* 496 */     }return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 503 */     Set<Map.Entry<String,String[]>> entries = onlineMap.getOnlineUserMap()
/* 504 */       .entrySet();
/* 505 */     for (Map.Entry entry : entries) {
/* 506 */       String[] info = (String[])entry.getValue();
/* 507 */       String ip = (String)entry.getKey();
/* 508 */       if ((stringUtils.isNotBlank(info[1])) && 
/* 509 */         (Long.valueOf(info[1]) == id)) {
/* 510 */         Kick.kickUserDeleteUser(ip);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 515 */     Portalaccount acc = this.portalaccountService.getPortalaccountByKey(id);
/* 516 */     if (acc != null) {
/* 517 */       String username = acc.getLoginName();
/* 518 */       Iterator iterator = RadiusOnlineMap.getInstance()
/* 519 */         .getRadiusOnlineMap().keySet().iterator();
/* 520 */       while (iterator.hasNext()) {
/* 521 */         Object o = iterator.next();
/* 522 */         String acctSessionId = o.toString();
/* 523 */         String[] radiusOnlineInfo = 
/* 524 */           (String[])RadiusOnlineMap.getInstance()
/* 524 */           .getRadiusOnlineMap().get(acctSessionId);
/* 525 */         if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 526 */           (username.equals(radiusOnlineInfo[4]))) {
/* 527 */           COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 528 */             "Radius Account Delete COA");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 534 */     this.portalaccountService.deleteByKey(id);
/*     */ 
/* 536 */     PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 537 */     macsq.setAccountId(id);
/* 538 */     this.macsService.deleteByQuery(macsq);
/*     */ 
/* 540 */     return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 546 */     List<Long> list = Arrays.asList(ids);
/*     */ 
/* 548 */     Set<Map.Entry <String,String[]>> entries = onlineMap.getOnlineUserMap()
/* 549 */       .entrySet();
/* 550 */     for (Long id : list) {
/* 551 */       for (Map.Entry entry : entries) {
/* 552 */         String[] info = (String[])entry.getValue();
/* 553 */         String ip = (String)entry.getKey();
/* 554 */         if ((stringUtils.isNotBlank(info[1])) && 
/* 555 */           (Long.valueOf(info[1]) == id)) {
/* 556 */           Kick.kickUserDeleteUser(ip);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 561 */       Portalaccount acc = this.portalaccountService.getPortalaccountByKey(id);
/* 562 */       if (acc != null) {
/* 563 */         String username = acc.getLoginName();
/* 564 */         Iterator iterator = RadiusOnlineMap.getInstance()
/* 565 */           .getRadiusOnlineMap().keySet().iterator();
/* 566 */         while (iterator.hasNext()) {
/* 567 */           Object o = iterator.next();
/* 568 */           String acctSessionId = o.toString();
/* 569 */           String[] radiusOnlineInfo = 
/* 570 */             (String[])RadiusOnlineMap.getInstance()
/* 570 */             .getRadiusOnlineMap().get(acctSessionId);
/* 571 */           if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 572 */             (username.equals(radiusOnlineInfo[4]))) {
/* 573 */             COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 574 */               "Radius Account Delete COA");
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 580 */       PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 581 */       macsq.setAccountId(id);
/* 582 */       this.macsService.deleteByQuery(macsq);
/*     */     }
/* 584 */     this.portalaccountService.deleteByKeys(list);
/* 585 */     return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 591 */     Set<Map.Entry<String,String[]>> entries = onlineMap.getOnlineUserMap()
/* 592 */       .entrySet();
/* 593 */     for (Map.Entry entry : entries) {
/* 594 */       String ip = (String)entry.getKey();
/* 595 */       Kick.kickUserDeleteUser(ip);
/*     */     }
/*     */ 
/* 598 */     Iterator iterator = RadiusOnlineMap.getInstance()
/* 599 */       .getRadiusOnlineMap().keySet().iterator();
/* 600 */     while (iterator.hasNext()) {
/* 601 */       Object o = iterator.next();
/* 602 */       String acctSessionId = o.toString();
/* 603 */       String[] radiusOnlineInfo = 
/* 604 */         (String[])RadiusOnlineMap.getInstance()
/* 604 */         .getRadiusOnlineMap().get(acctSessionId);
/* 605 */       if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0)) {
/* 606 */         COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 607 */           "Radius Account Delete COA");
/*     */       }
/*     */     }
/*     */ 
/* 611 */     this.portalaccountService.deleteByQuery(new PortalaccountQuery());
/* 612 */     this.macsService.deleteByQuery(new PortalaccountmacsQuery());
/* 613 */     return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/deleteQuery.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deleteQuery(PortalaccountQuery query)
/*     */   {
/* 619 */     query.setLoginNameLike(true);
/* 620 */     query.setNameLike(true);
/* 621 */     if (stringUtils.isBlank(query.getLoginName())) {
/* 622 */       query.setLoginName(null);
/*     */     }
/* 624 */     if (stringUtils.isBlank(query.getName())) {
/* 625 */       query.setName(null);
/*     */     }
/* 627 */     if (stringUtils.isBlank(query.getState())) {
/* 628 */       query.setState(null);
/*     */     }
/* 630 */     if ((query.getId() == null) && (query.getState() == null) && 
/* 631 */       (query.getName() == null) && (query.getLoginName() == null)) {
/* 632 */       return "redirect:/portalaccount/list.action";
/*     */     }
/*     */ 
/* 635 */     List<Portalaccount> list = this.portalaccountService
/* 636 */       .getPortalaccountList(query);
/*     */ 
/* 638 */     Set<Map.Entry<String, String[]>> entries = onlineMap.getOnlineUserMap()
/* 639 */       .entrySet();
/* 640 */     for (Portalaccount acc : list) {
/* 641 */       for (Map.Entry entry : entries) {
/* 642 */         String[] info = (String[])entry.getValue();
/* 643 */         String ip = (String)entry.getKey();
/* 644 */         if ((stringUtils.isNotBlank(info[1])) && 
/* 645 */           (Long.valueOf(info[1]) == acc.getId())) {
/* 646 */           Kick.kickUserDeleteUser(ip);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 651 */       if (acc != null) {
/* 652 */         String username = acc.getLoginName();
/* 653 */         Iterator iterator = RadiusOnlineMap.getInstance()
/* 654 */           .getRadiusOnlineMap().keySet().iterator();
/* 655 */         while (iterator.hasNext()) {
/* 656 */           Object o = iterator.next();
/* 657 */           String acctSessionId = o.toString();
/* 658 */           String[] radiusOnlineInfo = 
/* 659 */             (String[])RadiusOnlineMap.getInstance()
/* 659 */             .getRadiusOnlineMap().get(acctSessionId);
/* 660 */           if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 661 */             (username.equals(radiusOnlineInfo[4]))) {
/* 662 */             COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 663 */               "Radius Account Delete COA");
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 669 */       this.portalaccountService.deleteByKey(acc.getId());
/* 670 */       PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 671 */       macsq.setAccountId(acc.getId());
/* 672 */       this.macsService.deleteByQuery(macsq);
/*     */     }
/* 674 */     this.portalaccountService.deleteByQuery(query);
/* 675 */     return "redirect:/portalaccount/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/out.action"})
/*     */   public String out()
/*     */   {
/* 681 */     return "portalaccount/out";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/outV.action"})
/*     */   public String outV(ModelMap model, HttpServletRequest request)
/*     */   {
/* 687 */     List accounts = this.portalaccountService
/* 688 */       .getPortalaccountList(new PortalaccountQuery());
/* 689 */     String cfgPath = request.getServletContext().getRealPath("/");
/* 690 */     Date now = new Date();
/* 691 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 692 */     String nowString = format.format(now);
/* 693 */     File dir = new File(cfgPath + "ExcelOut/");
/* 694 */     if (!dir.exists()) {
/* 695 */       dir.mkdirs();
/*     */     }
/* 697 */     String fileName = cfgPath + "ExcelOut/" + nowString + ".xls";
/*     */     try {
/* 699 */       ExcelUtils.writeAccountToExcel(fileName, accounts);
/*     */     } catch (Exception e) {
/* 701 */       e.printStackTrace();
/* 702 */       model.addAttribute("msg", "文件创建失败！");
/* 703 */       model.addAttribute("downUrl", null);
/* 704 */       model.addAttribute("err", Integer.valueOf(1));
/* 705 */       return "portalaccount/outResult";
/*     */     }
/* 707 */     model.addAttribute("msg", "文件创建成功！");
/* 708 */     model.addAttribute("downUrl", nowString + ".xls");
/* 709 */     model.addAttribute("creatDate", nowString);
/* 710 */     model.addAttribute("err", Integer.valueOf(0));
/* 711 */     return "portalaccount/outResult";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/in.action"})
/*     */   public String in(ModelMap model)
/*     */   {
/* 717 */     model.addAttribute("msg", "接入账户文件导入!");
/* 718 */     return "portalaccount/in";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/in.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String in(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
/*     */   {
/* 726 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/* 727 */     if ((!ext.equals("xls")) && (!ext.equals("xlsx"))) {
/* 728 */       model.addAttribute("msg", "文件格式错误!");
/* 729 */       model.addAttribute("err", Integer.valueOf(0));
/* 730 */       return "portalaccount/in";
/*     */     }
/*     */ 
/* 733 */     String dir = null;
/* 734 */     String nowString = null;
/* 735 */     List unInAccounts = new ArrayList();
/*     */     try {
/* 737 */       InputStream in = file.getInputStream();
/* 738 */       dir = request.getServletContext().getRealPath("/ExcelIn");
/* 739 */       File fileLocation = new File(dir);
/*     */ 
/* 741 */       if (!fileLocation.exists()) {
/* 742 */         boolean isCreated = fileLocation.mkdir();
/* 743 */         if (!isCreated)
/*     */         {
/* 745 */           model.addAttribute("msg", "权限不足!");
/* 746 */           model.addAttribute("err", Integer.valueOf(0));
/* 747 */           return "portalaccount/in";
/*     */         }
/*     */       }
/*     */ 
/* 751 */       Date now = new Date();
/* 752 */       SimpleDateFormat format = new SimpleDateFormat(
/* 753 */         "yyyy-MM-dd-HH-mm-ss");
/* 754 */       nowString = format.format(now);
/*     */ 
/* 756 */       File uploadFile = new File(dir, nowString + ".xls");
/*     */ 
/* 758 */       FileUtils.copyInputStreamToFile(in, uploadFile);
/*     */ 
/* 765 */       in.close();
/*     */     }
/*     */     catch (Exception ex) {
/* 768 */       model.addAttribute("msg", "传送文件异常!");
/* 769 */       model.addAttribute("err", Integer.valueOf(0));
/* 770 */       File df = new File(dir + "/" + nowString + ".xls");
/* 771 */       if (df.exists()) {
/* 772 */         df.delete();
/*     */       }
/* 774 */       return "portalaccount/in";
/*     */     }
/*     */     try
/*     */     {
/* 778 */       unInAccounts = ExcelUtils.readExcelAccount(dir + "/" + nowString + 
/* 779 */         ".xls");
/*     */     } catch (Exception e) {
/* 781 */       model.addAttribute("msg", "文件读取错误!");
/* 782 */       model.addAttribute("err", Integer.valueOf(0));
/* 783 */       File df = new File(dir + "/" + nowString + ".xls");
/* 784 */       if (df.exists()) {
/* 785 */         df.delete();
/*     */       }
/* 787 */       return "portalaccount/in";
/*     */     }
/* 789 */     File df = new File(dir + "/" + nowString + ".xls");
/* 790 */     if (df.exists()) {
/* 791 */       df.delete();
/*     */     }
/* 793 */     model.addAttribute("unInAccounts", unInAccounts);
/* 794 */     return "portalaccount/inResult";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalaccountController
 * JD-Core Version:    0.6.2
 */