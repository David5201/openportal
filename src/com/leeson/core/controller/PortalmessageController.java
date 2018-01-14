/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalcard;
/*     */ import com.leeson.core.bean.Portalmessage;
/*     */ import com.leeson.core.bean.Portalorder;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.query.PortalmessageQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalcardService;
/*     */ import com.leeson.core.service.PortalmessageService;
/*     */ import com.leeson.core.service.PortalorderService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import com.leeson.core.utils.MyUtils;
/*     */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class PortalmessageController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalmessageService portalmessageService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaluserService portaluserService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountService portalaccountService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardService portalcardService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalorderService portalorderService;
/*     */ 
/*     */   @RequestMapping({"/portalmessage/listIn.action"})
/*     */   public String pageIn(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
/*     */   {
/*  63 */     Portaluser user = (Portaluser)request.getSession()
/*  64 */       .getAttribute("user");
/*  65 */     query.setToid(user.getId().toString());
/*  66 */     query.setToPos("0");
/*  67 */     query.setDelin("0");
/*  68 */     query.orderbyState(true);
/*  69 */     query.orderbyDate(false);
/*  70 */     query.setDescriptionLike(true);
/*  71 */     query.setTitleLike(true);
/*  72 */     if (stringUtils.isBlank(query.getTitle())) {
/*  73 */       query.setTitle(null);
/*     */     }
/*  75 */     if (stringUtils.isBlank(query.getDescription())) {
/*  76 */       query.setDescription(null);
/*     */     }
/*  78 */     if (stringUtils.isBlank(query.getState())) {
/*  79 */       query.setState(null);
/*     */     }
/*  81 */     Pagination pagination = this.portalmessageService
/*  82 */       .getPortalmessageListWithPage(query);
/*  83 */     model.addAttribute("pagination", pagination);
/*  84 */     model.addAttribute("query", query);
/*  85 */     return "portalmessage/listIn";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmessage/showIn.action"})
/*     */   public String showIn(@RequestParam Long id, ModelMap model)
/*     */   {
/*  91 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/*  92 */     message.setState("1");
/*  93 */     this.portalmessageService.updatePortalmessageByKey(message);
/*  94 */     model.addAttribute("entity", message);
/*  95 */     return "portalmessage/showIn";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmessage/listOut.action"})
/*     */   public String pageOut(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
/*     */   {
/* 102 */     Portaluser user = (Portaluser)request.getSession()
/* 103 */       .getAttribute("user");
/* 104 */     query.setFromid(user.getId().toString());
/* 105 */     query.setFromPos("0");
/* 106 */     query.setDelout("0");
/* 107 */     query.orderbyState(true);
/* 108 */     query.orderbyDate(false);
/* 109 */     query.setDescriptionLike(true);
/* 110 */     query.setTitleLike(true);
/* 111 */     if (stringUtils.isBlank(query.getTitle())) {
/* 112 */       query.setTitle(null);
/*     */     }
/* 114 */     if (stringUtils.isBlank(query.getDescription())) {
/* 115 */       query.setDescription(null);
/*     */     }
/* 117 */     if (stringUtils.isBlank(query.getState())) {
/* 118 */       query.setState(null);
/*     */     }
/* 120 */     Pagination pagination = this.portalmessageService
/* 121 */       .getPortalmessageListWithPage(query);
/* 122 */     model.addAttribute("pagination", pagination);
/* 123 */     model.addAttribute("query", query);
/* 124 */     return "portalmessage/listOut";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmessage/showTo.action"})
/*     */   public String showTo(@RequestParam Long id, ModelMap model)
/*     */   {
/* 130 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 131 */     model.addAttribute("entity", message);
/* 132 */     return "portalmessage/showTo";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmessage/edit.action"})
/*     */   public String edit(@RequestParam Long id)
/*     */   {
/* 138 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 139 */     if (message.getState().equals("1"))
/* 140 */       message.setState("0");
/*     */     else {
/* 142 */       message.setState("1");
/*     */     }
/* 144 */     this.portalmessageService.updatePortalmessageByKey(message);
/* 145 */     return "redirect:/portalmessage/listIn.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmessage/Indelete.action"})
/*     */   public String Indelete(@RequestParam Long id)
/*     */   {
/* 151 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 152 */     message.setDelin("1");
/* 153 */     this.portalmessageService.updatePortalmessageByKey(message);
/* 154 */     return "redirect:/portalmessage/listIn.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalmessage/Indeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String Indeletes(@RequestParam Long[] ids)
/*     */   {
/* 160 */     List list = Arrays.asList(ids);
/* 161 */     List<Portalmessage> messages = this.portalmessageService
/* 162 */       .getPortalmessageByKeys(list);
/* 163 */     for (Portalmessage message : messages) {
/* 164 */       message.setDelin("1");
/* 165 */       this.portalmessageService.updatePortalmessageByKey(message);
/*     */     }
/* 167 */     return "redirect:/portalmessage/listIn.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmessage/Outdelete.action"})
/*     */   public String Outdelete(@RequestParam Long id)
/*     */   {
/* 173 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 174 */     message.setDelout("1");
/* 175 */     this.portalmessageService.updatePortalmessageByKey(message);
/* 176 */     return "redirect:/portalmessage/listOut.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalmessage/Outdeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String Outdeletes(@RequestParam Long[] ids)
/*     */   {
/* 182 */     List list = Arrays.asList(ids);
/* 183 */     List<Portalmessage> messages = this.portalmessageService
/* 184 */       .getPortalmessageByKeys(list);
/* 185 */     for (Portalmessage message : messages) {
/* 186 */       message.setDelout("1");
/* 187 */       this.portalmessageService.updatePortalmessageByKey(message);
/*     */     }
/* 189 */     return "redirect:/portalmessage/listOut.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalmessage/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String add(Long toid, String toPos, Long cardId, ModelMap model)
/*     */   {
/* 195 */     List users = this.portaluserService
/* 196 */       .getPortaluserList(new PortaluserQuery());
/* 197 */     List accounts = this.portalaccountService
/* 198 */       .getPortalaccountList(new PortalaccountQuery());
/* 199 */     Portalmessage e = new Portalmessage();
/* 200 */     if (stringUtils.isNotBlank(toPos)) {
/* 201 */       e.setToPos(toPos);
/*     */     }
/* 203 */     if ((toid != null) && (toid.longValue() != 0L)) {
/* 204 */       e.setToid(toid.toString());
/*     */     }
/*     */ 
/* 207 */     if ((stringUtils.isNotBlank(toPos)) && (toPos.equals("0"))) {
/* 208 */       model.addAttribute("users", users);
/*     */     }
/* 210 */     if ((stringUtils.isNotBlank(toPos)) && (toPos.equals("1"))) {
/* 211 */       model.addAttribute("users", accounts);
/*     */     }
/*     */ 
/* 214 */     if ((cardId != null) && (cardId.longValue() != 0L)) {
/* 215 */       Portalcard card = this.portalcardService.getPortalcardByKey(cardId);
/* 216 */       e.setTitle("充值卡CD-KEY");
/* 217 */       String payType = card.getPayType();
/* 218 */       String type = "";
/* 219 */       if ("2".equals(payType)) {
/* 220 */         type = "计时";
/*     */       }
/* 222 */       if ("3".equals(payType)) {
/* 223 */         type = "买断";
/*     */       }
/* 225 */       e.setDescription("充值卡CD-KEY:【" + card.getCdKey() + "】  名称:【" + card.getName() + "】  类型:【" + type + "】  详细信息:【" + card.getDescription() + "】");
/* 226 */       model.addAttribute("cardId", cardId);
/*     */     }
/*     */ 
/* 230 */     model.addAttribute("entity", e);
/* 231 */     return "portalmessage/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalmessage/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalmessage e, Long cardId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 238 */     if (stringUtils.isBlank(e.getToPos())) {
/* 239 */       model.addAttribute("msg", "请先选择用户分类和用户！！");
/* 240 */       e.setToPos(null);
/* 241 */       e.setToid(null);
/* 242 */       model.addAttribute("entity", e);
/* 243 */       return "portalmessage/save";
/*     */     }
/* 245 */     if (stringUtils.isBlank(e.getToid())) {
/* 246 */       model.addAttribute("msg", "请先选择用户！！");
/* 247 */       List users = this.portaluserService
/* 248 */         .getPortaluserList(new PortaluserQuery());
/* 249 */       List accounts = this.portalaccountService
/* 250 */         .getPortalaccountList(new PortalaccountQuery());
/* 251 */       if ((e.getToPos() != null) && (e.getToPos().equals("0"))) {
/* 252 */         model.addAttribute("users", users);
/*     */       }
/* 254 */       if ((e.getToPos() != null) && (e.getToPos().equals("1"))) {
/* 255 */         model.addAttribute("users", accounts);
/*     */       }
/* 257 */       e.setToid(null);
/* 258 */       model.addAttribute("entity", e);
/* 259 */       return "portalmessage/save";
/*     */     }
/*     */ 
/* 262 */     if ((stringUtils.isBlank(e.getTitle())) || 
/* 263 */       (stringUtils.isBlank(e.getDescription()))) {
/* 264 */       model.addAttribute("msg", "消息标题和内容不能为空！");
/* 265 */       List users = this.portaluserService
/* 266 */         .getPortaluserList(new PortaluserQuery());
/* 267 */       List accounts = this.portalaccountService
/* 268 */         .getPortalaccountList(new PortalaccountQuery());
/* 269 */       if ((e.getToPos() != null) && (e.getToPos().equals("0"))) {
/* 270 */         model.addAttribute("users", users);
/*     */       }
/* 272 */       if ((e.getToPos() != null) && (e.getToPos().equals("1"))) {
/* 273 */         model.addAttribute("users", accounts);
/*     */       }
/* 275 */       model.addAttribute("entity", e);
/* 276 */       return "portalmessage/save";
/*     */     }
/*     */ 
/* 279 */     Portaluser user = (Portaluser)request.getSession()
/* 280 */       .getAttribute("user");
/* 281 */     String toUserName = null;
/* 282 */     if (e.getToPos().equals("0")) {
/* 283 */       toUserName = this.portaluserService.getPortaluserByKey(
/* 284 */         Long.valueOf(e.getToid())).getLoginName();
/*     */     }
/* 286 */     if (e.getToPos().equals("1")) {
/* 287 */       toUserName = this.portalaccountService.getPortalaccountByKey(
/* 288 */         Long.valueOf(e.getToid())).getLoginName();
/*     */     }
/* 290 */     e.setDate(new Date());
/* 291 */     e.setDelin("0");
/* 292 */     e.setDelout("0");
/* 293 */     e.setFromid(user.getId().toString());
/* 294 */     e.setFromname(user.getLoginName());
/* 295 */     e.setFromPos("0");
/* 296 */     e.setIp(GetNgnixRemotIP.getRemoteAddrIp(request));
/* 297 */     e.setState("0");
/* 298 */     e.setToname(toUserName);
/*     */ 
/* 300 */     if ((cardId != null) && (cardId.longValue() != 0L)) {
/* 301 */       Portalcard card = this.portalcardService.getPortalcardByKey(cardId);
/* 302 */       if (!card.getState().equals("0")) {
/* 303 */         model.addAttribute("msg", "该充值卡已经被售出！");
/* 304 */         e.setTitle("该充值卡已经被售出！");
/* 305 */         e.setDescription("该充值卡已经被售出,请点击返回！！！");
/* 306 */         model.addAttribute("entity", e);
/* 307 */         model.addAttribute("cardId", cardId);
/* 308 */         return "portalmessage/save";
/*     */       }
/* 310 */       card.setState("1");
/* 311 */       card.setBuyDate(new Date());
/* 312 */       card.setAccountName(toUserName);
/* 313 */       this.portalcardService.updatePortalcardByKey(card);
/*     */ 
/* 315 */       Portalorder order = new Portalorder();
/* 316 */       order.setAccountDel(card.getAccountDel());
/* 317 */       order.setAccountId(card.getAccountId());
/* 318 */       order.setAccountName(card.getAccountName());
/* 319 */       order.setBuyDate(card.getBuyDate());
/* 320 */       order.setBuyer(card.getAccountName());
/* 321 */       order.setCategoryType(card.getCategoryType());
/* 322 */       order.setCdKey(card.getCdKey());
/* 323 */       order.setDescription(card.getDescription());
/* 324 */       order.setMoney(card.getMoney());
/* 325 */       order.setName(card.getName());
/* 326 */       order.setPayby(Integer.valueOf(0));
/* 327 */       order.setPayDate(card.getBuyDate());
/* 328 */       order.setPayTime(card.getPayTime());
/* 329 */       order.setPayType(card.getPayType());
/* 330 */       order.setSeller("系统");
/* 331 */       order.setState("1");
/* 332 */       order.setTradeno(card.getCdKey());
/* 333 */       order.setUserDel(card.getUserDel());
/* 334 */       this.portalorderService.addPortalorder(order);
/*     */     }
/*     */ 
/* 338 */     this.portalmessageService.addPortalmessage(e);
/*     */ 
/* 340 */     return "redirect:/portalmessage/listOut.action";
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/ajax_ChooseSend.action"})
/*     */   public Map<Long, String> ChooseUsers(@RequestParam String toPos, ModelMap model) {
/* 347 */     if (stringUtils.isNotBlank(toPos)) {
/* 348 */       Map map = new HashMap();
/* 349 */       if (toPos.equals("0")) {
/* 350 */         List<Portaluser> users = this.portaluserService
/* 351 */           .getPortaluserList(new PortaluserQuery());
/* 352 */         for (Portaluser user : users) {
/* 353 */           map.put(user.getId(), user.getLoginName());
/*     */         }
/* 355 */         return map;
/*     */       }
/* 357 */       if (toPos.equals("1")) {
/* 358 */         List<Portalaccount> users = this.portalaccountService
/* 359 */           .getPortalaccountList(new PortalaccountQuery());
/* 360 */         for (Portalaccount account : users) {
/* 361 */           map.put(account.getId(), account.getLoginName());
/*     */         }
/* 363 */         return map;
/*     */       }
/*     */     }
/*     */ 
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message_sendUI.action"})
/*     */   public String sendUI(ModelMap model)
/*     */   {
/* 373 */     return "portalmessage/sendUI";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/message_send.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String send(Portalmessage e, HttpServletRequest request, ModelMap model)
/*     */   {
/* 380 */     if ((stringUtils.isBlank(e.getTitle())) || 
/* 381 */       (stringUtils.isBlank(e.getDescription()))) {
/* 382 */       model.addAttribute("msg", "消息标题和内容不能为空！");
/* 383 */       return "portalmessage/sendUI";
/*     */     }
/*     */ 
/* 386 */     e.setTitle(e.getTitle().replace(",", " "));
/* 387 */     e.setTitle(e.getTitle().replace(".", " "));
/* 388 */     e.setTitle(e.getTitle().replace("!", " "));
/* 389 */     e.setTitle(e.getTitle().replace("?", " "));
/* 390 */     e.setTitle(e.getTitle().replace(";", " "));
/* 391 */     e.setTitle(e.getTitle().replace("，", " "));
/* 392 */     e.setTitle(e.getTitle().replace("。", " "));
/* 393 */     e.setTitle(e.getTitle().replace("！", " "));
/* 394 */     e.setTitle(e.getTitle().replace("？", " "));
/* 395 */     e.setTitle(e.getTitle().replace("；", " "));
/*     */ 
/* 397 */     e.setDescription(e.getDescription().replace(",", " "));
/* 398 */     e.setDescription(e.getDescription().replace(".", " "));
/* 399 */     e.setDescription(e.getDescription().replace("!", " "));
/* 400 */     e.setDescription(e.getDescription().replace("?", " "));
/* 401 */     e.setDescription(e.getDescription().replace(";", " "));
/* 402 */     e.setDescription(e.getDescription().replace("，", " "));
/* 403 */     e.setDescription(e.getDescription().replace("。", " "));
/* 404 */     e.setDescription(e.getDescription().replace("！", " "));
/* 405 */     e.setDescription(e.getDescription().replace("？", " "));
/* 406 */     e.setDescription(e.getDescription().replace("；", " "));
/*     */     try
/*     */     {
/* 409 */       if (!MyUtils.checkInput(e.getTitle())) {
/* 410 */         model.addAttribute("msg", "消息标题不符合规范！");
/* 411 */         return "portalmessage/sendUI";
/*     */       }
/*     */     } catch (Exception ex) {
/* 414 */       model.addAttribute("msg", "消息标题不符合规范！");
/* 415 */       return "portalmessage/sendUI";
/*     */     }
/*     */     try {
/* 418 */       if (!MyUtils.checkInput(e.getDescription())) {
/* 419 */         model.addAttribute("msg", "消息内容不符合规范！");
/* 420 */         return "portalmessage/sendUI";
/*     */       }
/*     */     } catch (Exception ex) {
/* 423 */       model.addAttribute("msg", "消息内容不符合规范！");
/* 424 */       return "portalmessage/sendUI";
/*     */     }
/*     */ 
/* 428 */     if (stringUtils.isBlank(e.getFromname())) {
/* 429 */       e.setFromname("游客");
/*     */     }
/*     */     try
/*     */     {
/* 433 */       if (!MyUtils.checkInput(e.getFromname())) {
/* 434 */         model.addAttribute("msg", "用户名不符合规范！");
/* 435 */         return "portalmessage/sendUI";
/*     */       }
/*     */     } catch (Exception ex) {
/* 438 */       model.addAttribute("msg", "用户名不符合规范！");
/* 439 */       return "portalmessage/sendUI";
/*     */     }
/*     */ 
/* 442 */     HttpSession session = request.getSession();
/* 443 */     String ikmac = (String)session.getAttribute("ikmac");
/* 444 */     Cookie[] cookies = request.getCookies();
/* 445 */     String cmac = "";
/* 446 */     if (cookies != null) {
/* 447 */       for (int i = 0; i < cookies.length; i++) {
/* 448 */         if (cookies[i].getName().equals("mac"))
/* 449 */           cmac = cookies[i].getValue();
/*     */       }
/*     */     }
/* 452 */     if (stringUtils.isBlank(ikmac)) {
/* 453 */       ikmac = cmac;
/*     */     }
/* 455 */     if (stringUtils.isNotBlank(ikmac)) {
/* 456 */       e.setDescription("mac=" + ikmac + " " + e.getDescription());
/*     */     }
/* 458 */     e.setFromid("0");
/* 459 */     e.setFromPos("1");
/* 460 */     e.setToname("admin");
/* 461 */     e.setToid("1");
/* 462 */     e.setToPos("0");
/* 463 */     e.setIp(GetNgnixRemotIP.getRemoteAddrIp(request));
/* 464 */     e.setState("0");
/* 465 */     e.setDate(new Date());
/* 466 */     e.setDelin("0");
/* 467 */     e.setDelout("0");
/* 468 */     this.portalmessageService.addPortalmessage(e);
/*     */ 
/* 470 */     return "portalmessage/sendOK";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalmessageController
 * JD-Core Version:    0.6.2
 */