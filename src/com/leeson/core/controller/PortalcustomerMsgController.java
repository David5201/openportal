/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalmessage;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.query.PortalmessageQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalmessageService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import com.leeson.core.utils.MyUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class PortalcustomerMsgController
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
/*     */   @RequestMapping({"/customerMsgListIn.action"})
/*     */   public String pageIn(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
/*     */   {
/*  55 */     HttpSession session = request.getSession();
/*     */ 
/*  57 */     String un = (String)session.getAttribute("username");
/*  58 */     String pwd = (String)session.getAttribute("password");
/*  59 */     if ((stringUtils.isBlank(un)) || 
/*  60 */       (stringUtils.isBlank(pwd))) {
/*  61 */       session.removeAttribute("username");
/*  62 */       session.removeAttribute("password");
/*  63 */       session.removeAttribute("ip");
/*  64 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  65 */       return "portalcustomer/login";
/*     */     }
/*  67 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  68 */     aq.setLoginName(un);
/*  69 */     aq.setPassword(pwd);
/*  70 */     List accounts = this.portalaccountService
/*  71 */       .getPortalaccountList(aq);
/*  72 */     if (accounts.size() != 1) {
/*  73 */       session.removeAttribute("username");
/*  74 */       session.removeAttribute("password");
/*  75 */       session.removeAttribute("ip");
/*  76 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  77 */       return "portalcustomer/login";
/*     */     }
/*  79 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/*  81 */     query.setToid(user.getId().toString());
/*  82 */     query.setToPos("1");
/*  83 */     query.setDelin("0");
/*  84 */     query.orderbyState(true);
/*  85 */     query.orderbyDate(false);
/*  86 */     query.setDescriptionLike(true);
/*  87 */     query.setTitleLike(true);
/*  88 */     if (stringUtils.isBlank(query.getTitle())) {
/*  89 */       query.setTitle(null);
/*     */     }
/*  91 */     if (stringUtils.isBlank(query.getDescription())) {
/*  92 */       query.setDescription(null);
/*     */     }
/*  94 */     if (stringUtils.isBlank(query.getState())) {
/*  95 */       query.setState(null);
/*     */     }
/*  97 */     Pagination pagination = this.portalmessageService
/*  98 */       .getPortalmessageListWithPage(query);
/*  99 */     model.addAttribute("pagination", pagination);
/* 100 */     model.addAttribute("query", query);
/* 101 */     return "portalcustomerMsg/listIn";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerMsgShowIn.action"})
/*     */   public String showIn(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 109 */     HttpSession session = request.getSession();
/*     */ 
/* 111 */     String un = (String)session.getAttribute("username");
/* 112 */     String pwd = (String)session.getAttribute("password");
/* 113 */     if ((stringUtils.isBlank(un)) || 
/* 114 */       (stringUtils.isBlank(pwd))) {
/* 115 */       session.removeAttribute("username");
/* 116 */       session.removeAttribute("password");
/* 117 */       session.removeAttribute("ip");
/* 118 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 119 */       return "portalcustomer/login";
/*     */     }
/* 121 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 122 */     aq.setLoginName(un);
/* 123 */     aq.setPassword(pwd);
/* 124 */     List accounts = this.portalaccountService
/* 125 */       .getPortalaccountList(aq);
/* 126 */     if (accounts.size() != 1) {
/* 127 */       session.removeAttribute("username");
/* 128 */       session.removeAttribute("password");
/* 129 */       session.removeAttribute("ip");
/* 130 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 131 */       return "portalcustomer/login";
/*     */     }
/* 133 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 135 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 136 */     if ((!message.getToid().equals(String.valueOf(user.getId()))) || 
/* 137 */       (!message
/* 137 */       .getToPos().equals("1"))) {
/* 138 */       model.addAttribute("msg", "非法请求！！");
/* 139 */       return "portalcustomerMsg/showIn";
/*     */     }
/* 141 */     message.setState("1");
/* 142 */     this.portalmessageService.updatePortalmessageByKey(message);
/* 143 */     model.addAttribute("entity", message);
/* 144 */     return "portalcustomerMsg/showIn";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerMsgListOut.action"})
/*     */   public String pageOut(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
/*     */   {
/* 152 */     HttpSession session = request.getSession();
/*     */ 
/* 154 */     String un = (String)session.getAttribute("username");
/* 155 */     String pwd = (String)session.getAttribute("password");
/* 156 */     if ((stringUtils.isBlank(un)) || 
/* 157 */       (stringUtils.isBlank(pwd))) {
/* 158 */       session.removeAttribute("username");
/* 159 */       session.removeAttribute("password");
/* 160 */       session.removeAttribute("ip");
/* 161 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 162 */       return "portalcustomer/login";
/*     */     }
/* 164 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 165 */     aq.setLoginName(un);
/* 166 */     aq.setPassword(pwd);
/* 167 */     List accounts = this.portalaccountService
/* 168 */       .getPortalaccountList(aq);
/* 169 */     if (accounts.size() != 1) {
/* 170 */       session.removeAttribute("username");
/* 171 */       session.removeAttribute("password");
/* 172 */       session.removeAttribute("ip");
/* 173 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 174 */       return "portalcustomer/login";
/*     */     }
/* 176 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 178 */     query.setFromid(user.getId().toString());
/* 179 */     query.setFromPos("1");
/* 180 */     query.setDelout("0");
/* 181 */     query.orderbyState(true);
/* 182 */     query.orderbyDate(false);
/* 183 */     query.setDescriptionLike(true);
/* 184 */     query.setTitleLike(true);
/* 185 */     if (stringUtils.isBlank(query.getTitle())) {
/* 186 */       query.setTitle(null);
/*     */     }
/* 188 */     if (stringUtils.isBlank(query.getDescription())) {
/* 189 */       query.setDescription(null);
/*     */     }
/* 191 */     if (stringUtils.isBlank(query.getState())) {
/* 192 */       query.setState(null);
/*     */     }
/* 194 */     Pagination pagination = this.portalmessageService
/* 195 */       .getPortalmessageListWithPage(query);
/* 196 */     model.addAttribute("pagination", pagination);
/* 197 */     model.addAttribute("query", query);
/* 198 */     return "portalcustomerMsg/listOut";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerMsgShowTo.action"})
/*     */   public String showTo(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 206 */     HttpSession session = request.getSession();
/*     */ 
/* 208 */     String un = (String)session.getAttribute("username");
/* 209 */     String pwd = (String)session.getAttribute("password");
/* 210 */     if ((stringUtils.isBlank(un)) || 
/* 211 */       (stringUtils.isBlank(pwd))) {
/* 212 */       session.removeAttribute("username");
/* 213 */       session.removeAttribute("password");
/* 214 */       session.removeAttribute("ip");
/* 215 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 216 */       return "portalcustomer/login";
/*     */     }
/* 218 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 219 */     aq.setLoginName(un);
/* 220 */     aq.setPassword(pwd);
/* 221 */     List accounts = this.portalaccountService
/* 222 */       .getPortalaccountList(aq);
/* 223 */     if (accounts.size() != 1) {
/* 224 */       session.removeAttribute("username");
/* 225 */       session.removeAttribute("password");
/* 226 */       session.removeAttribute("ip");
/* 227 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 228 */       return "portalcustomer/login";
/*     */     }
/* 230 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 232 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 233 */     if ((!message.getFromid().equals(String.valueOf(user.getId()))) || 
/* 234 */       (!message
/* 234 */       .getFromPos().equals("1"))) {
/* 235 */       model.addAttribute("msg", "非法请求！！");
/* 236 */       return "portalcustomerMsg/showTo";
/*     */     }
/* 238 */     model.addAttribute("entity", message);
/* 239 */     return "portalcustomerMsg/showTo";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerMsgEdit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 247 */     HttpSession session = request.getSession();
/*     */ 
/* 249 */     String un = (String)session.getAttribute("username");
/* 250 */     String pwd = (String)session.getAttribute("password");
/* 251 */     if ((stringUtils.isBlank(un)) || 
/* 252 */       (stringUtils.isBlank(pwd))) {
/* 253 */       session.removeAttribute("username");
/* 254 */       session.removeAttribute("password");
/* 255 */       session.removeAttribute("ip");
/* 256 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 257 */       return "portalcustomer/login";
/*     */     }
/* 259 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 260 */     aq.setLoginName(un);
/* 261 */     aq.setPassword(pwd);
/* 262 */     List accounts = this.portalaccountService
/* 263 */       .getPortalaccountList(aq);
/* 264 */     if (accounts.size() != 1) {
/* 265 */       session.removeAttribute("username");
/* 266 */       session.removeAttribute("password");
/* 267 */       session.removeAttribute("ip");
/* 268 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 269 */       return "portalcustomer/login";
/*     */     }
/* 271 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 273 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 274 */     if ((message.getToid().equals(String.valueOf(user.getId()))) && 
/* 275 */       (message.getToPos().equals("1"))) {
/* 276 */       if (message.getState().equals("1"))
/* 277 */         message.setState("0");
/*     */       else {
/* 279 */         message.setState("1");
/*     */       }
/* 281 */       this.portalmessageService.updatePortalmessageByKey(message);
/*     */     }
/*     */ 
/* 284 */     return "redirect:/customerMsgListIn.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerMsgIndelete.action"})
/*     */   public String Indelete(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 292 */     HttpSession session = request.getSession();
/*     */ 
/* 294 */     String un = (String)session.getAttribute("username");
/* 295 */     String pwd = (String)session.getAttribute("password");
/* 296 */     if ((stringUtils.isBlank(un)) || 
/* 297 */       (stringUtils.isBlank(pwd))) {
/* 298 */       session.removeAttribute("username");
/* 299 */       session.removeAttribute("password");
/* 300 */       session.removeAttribute("ip");
/* 301 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 302 */       return "portalcustomer/login";
/*     */     }
/* 304 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 305 */     aq.setLoginName(un);
/* 306 */     aq.setPassword(pwd);
/* 307 */     List accounts = this.portalaccountService
/* 308 */       .getPortalaccountList(aq);
/* 309 */     if (accounts.size() != 1) {
/* 310 */       session.removeAttribute("username");
/* 311 */       session.removeAttribute("password");
/* 312 */       session.removeAttribute("ip");
/* 313 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 314 */       return "portalcustomer/login";
/*     */     }
/* 316 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 318 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 319 */     if ((message.getToid().equals(String.valueOf(user.getId()))) && 
/* 320 */       (message.getToPos().equals("1"))) {
/* 321 */       message.setDelin("1");
/* 322 */       this.portalmessageService.updatePortalmessageByKey(message);
/*     */     }
/*     */ 
/* 325 */     return "redirect:/customerMsgListIn.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/customerMsgIndeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String Indeletes(@RequestParam Long[] ids, ModelMap model, HttpServletRequest request)
/*     */   {
/* 333 */     HttpSession session = request.getSession();
/*     */ 
/* 335 */     String un = (String)session.getAttribute("username");
/* 336 */     String pwd = (String)session.getAttribute("password");
/* 337 */     if ((stringUtils.isBlank(un)) || 
/* 338 */       (stringUtils.isBlank(pwd))) {
/* 339 */       session.removeAttribute("username");
/* 340 */       session.removeAttribute("password");
/* 341 */       session.removeAttribute("ip");
/* 342 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 343 */       return "portalcustomer/login";
/*     */     }
/* 345 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 346 */     aq.setLoginName(un);
/* 347 */     aq.setPassword(pwd);
/* 348 */     List accounts = this.portalaccountService
/* 349 */       .getPortalaccountList(aq);
/* 350 */     if (accounts.size() != 1) {
/* 351 */       session.removeAttribute("username");
/* 352 */       session.removeAttribute("password");
/* 353 */       session.removeAttribute("ip");
/* 354 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 355 */       return "portalcustomer/login";
/*     */     }
/* 357 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 359 */     List list = Arrays.asList(ids);
/* 360 */     List<Portalmessage> messages = this.portalmessageService
/* 361 */       .getPortalmessageByKeys(list);
/* 362 */     for (Portalmessage message : messages) {
/* 363 */       if ((message.getToid().equals(String.valueOf(user.getId()))) && 
/* 364 */         (message.getToPos().equals("1"))) {
/* 365 */         message.setDelin("1");
/* 366 */         this.portalmessageService.updatePortalmessageByKey(message);
/*     */       }
/*     */     }
/*     */ 
/* 370 */     return "redirect:/customerMsgListIn.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerMsgOutdelete.action"})
/*     */   public String Outdelete(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 378 */     HttpSession session = request.getSession();
/*     */ 
/* 380 */     String un = (String)session.getAttribute("username");
/* 381 */     String pwd = (String)session.getAttribute("password");
/* 382 */     if ((stringUtils.isBlank(un)) || 
/* 383 */       (stringUtils.isBlank(pwd))) {
/* 384 */       session.removeAttribute("username");
/* 385 */       session.removeAttribute("password");
/* 386 */       session.removeAttribute("ip");
/* 387 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 388 */       return "portalcustomer/login";
/*     */     }
/* 390 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 391 */     aq.setLoginName(un);
/* 392 */     aq.setPassword(pwd);
/* 393 */     List accounts = this.portalaccountService
/* 394 */       .getPortalaccountList(aq);
/* 395 */     if (accounts.size() != 1) {
/* 396 */       session.removeAttribute("username");
/* 397 */       session.removeAttribute("password");
/* 398 */       session.removeAttribute("ip");
/* 399 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 400 */       return "portalcustomer/login";
/*     */     }
/* 402 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 404 */     Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
/* 405 */     if ((message.getFromid().equals(String.valueOf(user.getId()))) && 
/* 406 */       (message.getFromPos().equals("1"))) {
/* 407 */       message.setDelout("1");
/* 408 */       this.portalmessageService.updatePortalmessageByKey(message);
/*     */     }
/*     */ 
/* 411 */     return "redirect:/customerMsgListOut.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/customerMsgOutdeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String Outdeletes(@RequestParam Long[] ids, ModelMap model, HttpServletRequest request)
/*     */   {
/* 419 */     HttpSession session = request.getSession();
/*     */ 
/* 421 */     String un = (String)session.getAttribute("username");
/* 422 */     String pwd = (String)session.getAttribute("password");
/* 423 */     if ((stringUtils.isBlank(un)) || 
/* 424 */       (stringUtils.isBlank(pwd))) {
/* 425 */       session.removeAttribute("username");
/* 426 */       session.removeAttribute("password");
/* 427 */       session.removeAttribute("ip");
/* 428 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 429 */       return "portalcustomer/login";
/*     */     }
/* 431 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 432 */     aq.setLoginName(un);
/* 433 */     aq.setPassword(pwd);
/* 434 */     List accounts = this.portalaccountService
/* 435 */       .getPortalaccountList(aq);
/* 436 */     if (accounts.size() != 1) {
/* 437 */       session.removeAttribute("username");
/* 438 */       session.removeAttribute("password");
/* 439 */       session.removeAttribute("ip");
/* 440 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 441 */       return "portalcustomer/login";
/*     */     }
/* 443 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 445 */     List list = Arrays.asList(ids);
/* 446 */     List<Portalmessage> messages = this.portalmessageService
/* 447 */       .getPortalmessageByKeys(list);
/* 448 */     for (Portalmessage message : messages) {
/* 449 */       if ((message.getFromid().equals(String.valueOf(user.getId()))) && 
/* 450 */         (message.getFromPos().equals("1"))) {
/* 451 */         message.setDelout("1");
/* 452 */         this.portalmessageService.updatePortalmessageByKey(message);
/*     */       }
/*     */     }
/*     */ 
/* 456 */     return "redirect:/customerMsgListOut.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/customerMsgAdd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String add(Long toid, String toPos, ModelMap model)
/*     */   {
/* 462 */     List users = this.portaluserService
/* 463 */       .getPortaluserList(new PortaluserQuery());
/*     */ 
/* 465 */     Portalmessage e = new Portalmessage();
/* 466 */     if (stringUtils.isNotBlank(toPos)) {
/* 467 */       e.setToPos("0");
/* 468 */       e.setToid(toid.toString());
/*     */     }
/*     */ 
/* 471 */     model.addAttribute("users", users);
/* 472 */     model.addAttribute("entity", e);
/* 473 */     return "portalcustomerMsg/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/customerMsgAdd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalmessage e, HttpServletRequest request, ModelMap model)
/*     */   {
/* 480 */     e.setToPos("0");
/* 481 */     if (stringUtils.isBlank(e.getToid())) {
/* 482 */       model.addAttribute("msg", "请先选择用户！！");
/* 483 */       List users = this.portaluserService
/* 484 */         .getPortaluserList(new PortaluserQuery());
/* 485 */       model.addAttribute("users", users);
/* 486 */       e.setToid(null);
/* 487 */       model.addAttribute("entity", e);
/* 488 */       return "portalcustomerMsg/save";
/*     */     }
/*     */ 
/* 491 */     if ((stringUtils.isBlank(e.getTitle())) || 
/* 492 */       (stringUtils.isBlank(e.getDescription()))) {
/* 493 */       model.addAttribute("msg", "消息标题和内容不能为空！");
/* 494 */       List users = this.portaluserService
/* 495 */         .getPortaluserList(new PortaluserQuery());
/* 496 */       model.addAttribute("users", users);
/* 497 */       model.addAttribute("entity", e);
/* 498 */       return "portalcustomerMsg/save";
/*     */     }
/*     */ 
/* 501 */     e.setTitle(e.getTitle().replace(",", " "));
/* 502 */     e.setTitle(e.getTitle().replace(".", " "));
/* 503 */     e.setTitle(e.getTitle().replace("!", " "));
/* 504 */     e.setTitle(e.getTitle().replace("?", " "));
/* 505 */     e.setTitle(e.getTitle().replace(";", " "));
/* 506 */     e.setTitle(e.getTitle().replace("，", " "));
/* 507 */     e.setTitle(e.getTitle().replace("。", " "));
/* 508 */     e.setTitle(e.getTitle().replace("！", " "));
/* 509 */     e.setTitle(e.getTitle().replace("？", " "));
/* 510 */     e.setTitle(e.getTitle().replace("；", " "));
/*     */ 
/* 512 */     e.setDescription(e.getDescription().replace(",", " "));
/* 513 */     e.setDescription(e.getDescription().replace(".", " "));
/* 514 */     e.setDescription(e.getDescription().replace("!", " "));
/* 515 */     e.setDescription(e.getDescription().replace("?", " "));
/* 516 */     e.setDescription(e.getDescription().replace(";", " "));
/* 517 */     e.setDescription(e.getDescription().replace("，", " "));
/* 518 */     e.setDescription(e.getDescription().replace("。", " "));
/* 519 */     e.setDescription(e.getDescription().replace("！", " "));
/* 520 */     e.setDescription(e.getDescription().replace("？", " "));
/* 521 */     e.setDescription(e.getDescription().replace("；", " "));
/*     */     try
/*     */     {
/* 524 */       if (!MyUtils.checkInput(e.getTitle())) {
/* 525 */         model.addAttribute("msg", "消息标题不符合规范！");
/* 526 */         List users = this.portaluserService
/* 527 */           .getPortaluserList(new PortaluserQuery());
/* 528 */         model.addAttribute("users", users);
/* 529 */         model.addAttribute("entity", e);
/* 530 */         return "portalmessage/sendUI";
/*     */       }
/*     */     } catch (Exception ex) {
/* 533 */       model.addAttribute("msg", "消息标题不符合规范！");
/* 534 */       List users = this.portaluserService
/* 535 */         .getPortaluserList(new PortaluserQuery());
/* 536 */       model.addAttribute("users", users);
/* 537 */       model.addAttribute("entity", e);
/* 538 */       return "portalmessage/sendUI";
/*     */     }
/*     */     try {
/* 541 */       if (!MyUtils.checkInput(e.getDescription())) {
/* 542 */         model.addAttribute("msg", "消息内容不符合规范！");
/* 543 */         List users = this.portaluserService
/* 544 */           .getPortaluserList(new PortaluserQuery());
/* 545 */         model.addAttribute("users", users);
/* 546 */         model.addAttribute("entity", e);
/* 547 */         return "portalmessage/sendUI";
/*     */       }
/*     */     } catch (Exception ex) {
/* 550 */       model.addAttribute("msg", "消息内容不符合规范！");
/* 551 */       List users = this.portaluserService
/* 552 */         .getPortaluserList(new PortaluserQuery());
/* 553 */       model.addAttribute("users", users);
/* 554 */       model.addAttribute("entity", e);
/* 555 */       return "portalmessage/sendUI";
/*     */     }
/*     */ 
/* 559 */     HttpSession session = request.getSession();
/*     */ 
/* 561 */     String un = (String)session.getAttribute("username");
/* 562 */     String pwd = (String)session.getAttribute("password");
/* 563 */     if ((stringUtils.isBlank(un)) || 
/* 564 */       (stringUtils.isBlank(pwd))) {
/* 565 */       session.removeAttribute("username");
/* 566 */       session.removeAttribute("password");
/* 567 */       session.removeAttribute("ip");
/* 568 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 569 */       return "portalcustomer/login";
/*     */     }
/* 571 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 572 */     aq.setLoginName(un);
/* 573 */     aq.setPassword(pwd);
/* 574 */     List accounts = this.portalaccountService
/* 575 */       .getPortalaccountList(aq);
/* 576 */     if (accounts.size() != 1) {
/* 577 */       session.removeAttribute("username");
/* 578 */       session.removeAttribute("password");
/* 579 */       session.removeAttribute("ip");
/* 580 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 581 */       return "portalcustomer/login";
/*     */     }
/* 583 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 585 */     String toUserName = null;
/* 586 */     if (e.getToPos().equals("0")) {
/* 587 */       toUserName = this.portaluserService.getPortaluserByKey(
/* 588 */         Long.valueOf(e.getToid())).getLoginName();
/*     */     }
/* 590 */     if (e.getToPos().equals("1")) {
/* 591 */       toUserName = this.portalaccountService.getPortalaccountByKey(
/* 592 */         Long.valueOf(e.getToid())).getLoginName();
/*     */     }
/* 594 */     e.setDate(new Date());
/* 595 */     e.setDelin("0");
/* 596 */     e.setDelout("0");
/* 597 */     e.setFromid(user.getId().toString());
/* 598 */     e.setFromname(user.getLoginName());
/* 599 */     e.setFromPos("1");
/* 600 */     e.setIp(request.getRemoteHost());
/* 601 */     e.setState("0");
/* 602 */     e.setToname(toUserName);
/*     */ 
/* 604 */     this.portalmessageService.addPortalmessage(e);
/*     */ 
/* 606 */     return "redirect:/customerMsgListOut.action";
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/newMsg"})
/*     */   public Map<String, Object> msgCount(HttpServletRequest request, HttpServletResponse response) {
/* 613 */     Map map = new HashMap();
/*     */ 
/* 615 */     HttpSession session = request.getSession();
/*     */ 
/* 617 */     String un = (String)session.getAttribute("username");
/* 618 */     String pwd = (String)session.getAttribute("password");
/* 619 */     if ((stringUtils.isBlank(un)) || 
/* 620 */       (stringUtils.isBlank(pwd))) {
/* 621 */       session.removeAttribute("username");
/* 622 */       session.removeAttribute("password");
/* 623 */       session.removeAttribute("ip");
/* 624 */       map.put("msg", "登录信息丢失，请重新登录！！");
/* 625 */       return map;
/*     */     }
/* 627 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 628 */     aq.setLoginName(un);
/* 629 */     aq.setPassword(pwd);
/* 630 */     List accounts = this.portalaccountService
/* 631 */       .getPortalaccountList(aq);
/* 632 */     if (accounts.size() != 1) {
/* 633 */       session.removeAttribute("username");
/* 634 */       session.removeAttribute("password");
/* 635 */       session.removeAttribute("ip");
/* 636 */       map.put("msg", "登录信息丢失，请重新登录！！");
/* 637 */       return map;
/*     */     }
/* 639 */     Portalaccount user = (Portalaccount)accounts.get(0);
/*     */ 
/* 641 */     if ((user == null) || (user.getId() == null)) {
/* 642 */       map.put("msg", "登录信息丢失，请重新登录！！");
/* 643 */       return map;
/*     */     }
/* 645 */     String toid = user.getId().toString();
/* 646 */     PortalmessageQuery message = new PortalmessageQuery();
/* 647 */     message.setToid(toid);
/* 648 */     message.setState("0");
/* 649 */     message.setDelin("0");
/* 650 */     message.setToPos("1");
/* 651 */     int count = this.portalmessageService.getPortalmessageCount(message).intValue();
/* 652 */     if (count > 0) {
/* 653 */       map.put("msg", "你有" + count + "条未读消息！！！");
/* 654 */       map.put("newMsg", " 【" + count + "条未读】");
/*     */     } else {
/* 656 */       map.put("msg", Integer.valueOf(0));
/*     */     }
/* 658 */     return map;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalcustomerMsgController
 * JD-Core Version:    0.6.2
 */