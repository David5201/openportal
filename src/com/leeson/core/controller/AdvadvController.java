/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Advadv;
/*     */ import com.leeson.core.bean.Advbanner;
/*     */ import com.leeson.core.bean.Advpic;
/*     */ import com.leeson.core.bean.Advstores;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.AdvadvQuery;
/*     */ import com.leeson.core.query.AdvbannerQuery;
/*     */ import com.leeson.core.query.AdvpicQuery;
/*     */ import com.leeson.core.query.AdvstoresQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.AdvadvService;
/*     */ import com.leeson.core.service.AdvbannerService;
/*     */ import com.leeson.core.service.AdvpicService;
/*     */ import com.leeson.core.service.AdvstoresService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import com.leeson.core.utils.ImageUtil;
/*     */ import com.leeson.portal.core.controller.WISPr.utils.Tools;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.util.FileCopyUtils;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ import org.springframework.web.multipart.MultipartHttpServletRequest;
/*     */ 
/*     */ @Controller
/*     */ public class AdvadvController
/*     */ {
/*  59 */   private static Logger logger = Logger.getLogger(AdvadvController.class);
/*     */ 
/*     */   @Autowired
/*     */   private AdvadvService advadvService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaluserService portaluserService;
/*     */ 
/*     */   @Autowired
/*     */   private AdvstoresService advstoresService;
/*     */ 
/*     */   @Autowired
/*     */   private AdvbannerService advbannerService;
/*     */ 
/*     */   @Autowired
/*     */   private AdvpicService advpicService;
/*     */ 
/*  75 */   @RequestMapping({"/pic.action"})
/*     */   public void clickPic(Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException { String url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */     try {
/*  77 */       Advpic pic = this.advpicService.getAdvpicByKey(id);
/*  78 */       url = pic.getUrl();
/*  79 */       pic.setClickCount(Long.valueOf(pic.getClickCount().longValue() + 1L));
/*  80 */       this.advpicService.updateAdvpicByKey(pic);
/*     */ 
/*  82 */       Advadv adv = this.advadvService.getAdvadvByKey(pic.getAid());
/*  83 */       adv.setClickCount(Long.valueOf(adv.getClickCount().longValue() + 1L));
/*  84 */       this.advadvService.updateAdvadvByKey(adv);
/*     */     } catch (Exception localException) {
/*     */     }
/*  87 */     response.sendRedirect(url); }
/*     */ 
/*     */   @RequestMapping({"/banner.action"})
/*     */   public void clickBanner(Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException
/*     */   {
/*  92 */     String url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */     try {
/*  94 */       Advbanner banner = this.advbannerService.getAdvbannerByKey(id);
/*  95 */       url = banner.getUrl();
/*  96 */       banner.setClickCount(Long.valueOf(banner.getClickCount().longValue() + 1L));
/*  97 */       this.advbannerService.updateAdvbannerByKey(banner);
/*     */ 
/*  99 */       Advadv adv = this.advadvService.getAdvadvByKey(banner.getAid());
/* 100 */       adv.setClickCount(Long.valueOf(adv.getClickCount().longValue() + 1L));
/* 101 */       this.advadvService.updateAdvadvByKey(adv);
/*     */     } catch (Exception localException) {
/*     */     }
/* 104 */     response.sendRedirect(url);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/adv.action"})
/*     */   public void clickAdv(Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
/* 109 */     String url = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */     try {
/* 111 */       Advadv adv = this.advadvService.getAdvadvByKey(id);
/* 112 */       url = adv.getUrl();
/* 113 */       adv.setClickCount(Long.valueOf(adv.getClickCount().longValue() + 1L));
/* 114 */       this.advadvService.updateAdvadvByKey(adv);
/*     */     } catch (Exception localException) {
/*     */     }
/* 117 */     response.sendRedirect(url);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portal.action"})
/*     */   public String portal(Long id, Integer auth, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 123 */     int result = 0;
/*     */ 
/* 125 */     if (Tools.Do()) {
/*     */       try {
/* 127 */         Advadv adv = this.advadvService.getAdvadvByKey(id);
/* 128 */         if (adv != null) {
/* 129 */           adv.setShowCount(Long.valueOf(adv.getShowCount().longValue() + 1L));
/* 130 */           this.advadvService.updateAdvadvByKey(adv);
/* 131 */           int state = adv.getState().intValue();
/* 132 */           if (state == 1) {
/* 133 */             Date startDate = adv.getShowDate();
/* 134 */             Date endDate = adv.getEndDate();
/* 135 */             Date nowDate = new Date();
/* 136 */             if (((startDate == null) || 
/* 137 */               (nowDate.getTime() >= startDate.getTime())) && (
/* 138 */               (endDate == null) || 
/* 139 */               (endDate.getTime() >= nowDate.getTime()))) {
/* 140 */               Advstores store = this.advstoresService
/* 141 */                 .getAdvstoresByKey(adv.getSid());
/* 142 */               if (store != null) {
/* 143 */                 Long advID = adv.getId();
/*     */ 
/* 145 */                 AdvbannerQuery bq = new AdvbannerQuery();
/* 146 */                 bq.setAid(advID);
/* 147 */                 bq.orderbyPos(true);
/* 148 */                 List<Advbanner> banners = this.advbannerService
/* 149 */                   .getAdvbannerList(bq);
/* 150 */                 for (Advbanner banner : banners) {
/* 151 */                   banner.setShowCount(Long.valueOf(banner.getShowCount().longValue() + 1L));
/* 152 */                   this.advbannerService
/* 153 */                     .updateAdvbannerByKey(banner);
/*     */                 }
/*     */ 
/* 156 */                 AdvpicQuery pq = new AdvpicQuery();
/* 157 */                 pq.setAid(advID);
/* 158 */                 pq.orderbyPos(true);
/* 159 */                 List<Advpic> pics = this.advpicService.getAdvpicList(pq);
/*     */ 
/* 162 */                 int onePic = 0;
/* 163 */                 if ((pics != null) && (pics.size() > 0)) {
/* 164 */                   int picsNum = pics.size();
/* 165 */                   if (picsNum % 2 != 0) {
/* 166 */                     onePic = 1;
/*     */                   }
/*     */                 }
/* 169 */                 model.addAttribute("onePic", Integer.valueOf(onePic));
/* 170 */                 for (Advpic pic : pics) {
/* 171 */                   pic.setShowCount(Long.valueOf(pic.getShowCount().longValue() + 1L));
/* 172 */                   this.advpicService.updateAdvpicByKey(pic);
/*     */                 }
/*     */ 
/* 175 */                 model.addAttribute("store", store);
/* 176 */                 model.addAttribute("adv", adv);
/* 177 */                 model.addAttribute("banners", banners);
/* 178 */                 model.addAttribute("pics", pics);
/* 179 */                 result = 1;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*     */     }
/* 189 */     model.addAttribute("ret", Integer.valueOf(result));
/* 190 */     if (auth == null) {
/* 191 */       return "adv/portal";
/*     */     }
/* 193 */     if (auth.intValue() == 0)
/* 194 */       return "adv/portalAuth";
/* 195 */     if (auth.intValue() == 1)
/* 196 */       return "adv/portalWispr";
/* 197 */     if (auth.intValue() == 2) {
/* 198 */       return "adv/portalWifidog";
/*     */     }
/* 200 */     return "adv/portal";
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/advadv/upload"})
/*     */   public Map<String, String> uploadPreviewImage(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 209 */     Map map = new HashMap();
/*     */     try {
/* 211 */       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/* 212 */       MultipartFile image = multipartRequest.getFile("advimg");
/*     */ 
/* 214 */       String imageName = image.getOriginalFilename();
/* 215 */       String file_ext = imageName
/* 216 */         .substring(imageName.lastIndexOf(".") + 1);
/* 217 */       String tempImageName = UUID.randomUUID().toString() + "." + 
/* 218 */         file_ext;
/*     */ 
/* 220 */       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
/* 221 */       String filename = "/" + df.format(new Date()) + "/";
/*     */ 
/* 223 */       String filePath = request.getServletContext()
/* 224 */         .getRealPath("/advPic") + filename;
/* 225 */       File fileLocation = new File(filePath);
/* 226 */       if (!fileLocation.exists()) {
/* 227 */         fileLocation.mkdir();
/*     */       }
/* 229 */       filePath = filePath + tempImageName;
/* 230 */       File file = new File(filePath);
/* 231 */       byte[] advImageBytes = null;
/* 232 */       InputStream advImageStream = null;
/* 233 */       file.createNewFile();
/* 234 */       advImageStream = image.getInputStream();
/* 235 */       advImageBytes = FileCopyUtils.copyToByteArray(advImageStream);
/* 236 */       FileCopyUtils.copy(advImageBytes, file);
/* 237 */       advImageStream.close();
/* 238 */       int result = ImageUtil.checkImgHW(filePath, 600, 150);
/* 239 */       if (result == 0) {
/* 240 */         String tempPath = "/advPic/" + filename + tempImageName;
/* 241 */         map.put("tempPath", tempPath);
/* 242 */         map.put("ret", "0");
/* 243 */         map.put("msg", "上传成功！！");
/* 244 */       } else if (result == 1) {
/* 245 */         map.put("ret", "1");
/* 246 */         map.put("msg", "图片尺寸错误！！");
/*     */       } else {
/* 248 */         map.put("ret", "1");
/* 249 */         map.put("msg", "图片格式错误！！");
/*     */       }
/*     */     } catch (Exception e) {
/* 252 */       logger.error("==============ERROR Start=============");
/* 253 */       logger.error(e);
/* 254 */       logger.error("ERROR INFO ", e);
/* 255 */       logger.error("==============ERROR End=============");
/* 256 */       map.put("ret", "1");
/* 257 */       map.put("msg", "发生错误！！");
/*     */     }
/* 259 */     return map;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advadv/list.action"})
/*     */   public String page(AdvadvQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 266 */     List users = this.portaluserService
/* 267 */       .getPortaluserList(new PortaluserQuery());
/* 268 */     model.addAttribute("users", users);
/*     */ 
/* 270 */     HttpSession session = request.getSession();
/* 271 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 272 */     if ((user == null) || (user.getId() == null)) {
/* 273 */       return "homeAction/index";
/*     */     }
/* 275 */     long uid = user.getId().longValue();
/* 276 */     if (!"admin".equals(user.getLoginName())) {
/* 277 */       query.setUid(Long.valueOf(uid));
/*     */     }
/*     */ 
/* 280 */     query.setNameLike(true);
/* 281 */     query.setDescriptionLike(true);
/* 282 */     if (stringUtils.isBlank(query.getName())) {
/* 283 */       query.setName(null);
/*     */     }
/* 285 */     if (stringUtils.isBlank(query.getDescription())) {
/* 286 */       query.setDescription(null);
/*     */     }
/* 288 */     Pagination pagination = this.advadvService.getAdvadvListWithPage(query);
/* 289 */     model.addAttribute("pagination", pagination);
/* 290 */     model.addAttribute("query", query);
/*     */ 
/* 292 */     AdvstoresQuery squery = new AdvstoresQuery();
/* 293 */     if (!"admin".equals(user.getLoginName())) {
/* 294 */       squery.setUid(Long.valueOf(uid));
/*     */     }
/* 296 */     List stores = this.advstoresService.getAdvstoresList(squery);
/* 297 */     model.addAttribute("stores", stores);
/* 298 */     return "advadv/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advadv/add.action"})
/*     */   public String add(Advadv e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 304 */     HttpSession session = request.getSession();
/* 305 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 306 */     if ((user == null) || (user.getId() == null)) {
/* 307 */       return "homeAction/index";
/*     */     }
/* 309 */     long uid = user.getId().longValue();
/* 310 */     AdvstoresQuery query = new AdvstoresQuery();
/* 311 */     if (!"admin".equals(user.getLoginName())) {
/* 312 */       query.setUid(Long.valueOf(uid));
/*     */     }
/* 314 */     List stores = this.advstoresService.getAdvstoresList(query);
/* 315 */     model.addAttribute("stores", stores);
/* 316 */     model.addAttribute("entity", e);
/* 317 */     return "advadv/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advadv/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Advadv e, String showDateS, String endDateS, ModelMap model, HttpServletRequest request)
/*     */   {
/* 324 */     SimpleDateFormat format = new SimpleDateFormat(
/* 325 */       "yyyy-MM-dd HH:mm:ss");
/* 326 */     HttpSession session = request.getSession();
/* 327 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 328 */     if ((user == null) || (user.getId() == null)) {
/* 329 */       return "homeAction/index";
/*     */     }
/* 331 */     long uid = user.getId().longValue();
/*     */ 
/* 333 */     if ((showDateS != null) && (!"".equals(showDateS)))
/*     */       try {
/* 335 */         Date viewDate = format.parse(showDateS + " 00:00:00");
/* 336 */         e.setShowDate(viewDate);
/*     */       } catch (Exception ex) {
/* 338 */         model.addAttribute("entity", e);
/* 339 */         model.addAttribute("msg", "日期格式不正确！");
/* 340 */         AdvstoresQuery query = new AdvstoresQuery();
/* 341 */         if (!"admin".equals(user.getLoginName())) {
/* 342 */           query.setUid(Long.valueOf(uid));
/*     */         }
/* 344 */         List stores = this.advstoresService
/* 345 */           .getAdvstoresList(query);
/* 346 */         model.addAttribute("stores", stores);
/* 347 */         return "advadv/save";
/*     */       }
/*     */     else {
/* 350 */       e.setShowDate(new Date());
/*     */     }
/* 352 */     if ((endDateS != null) && (!"".equals(endDateS))) {
/*     */       try {
/* 354 */         Date viewDate = format.parse(endDateS + " 00:00:00");
/* 355 */         e.setEndDate(viewDate);
/*     */       } catch (Exception ex) {
/* 357 */         model.addAttribute("entity", e);
/* 358 */         model.addAttribute("msg", "日期格式不正确！");
/* 359 */         AdvstoresQuery query = new AdvstoresQuery();
/* 360 */         if (!"admin".equals(user.getLoginName())) {
/* 361 */           query.setUid(Long.valueOf(uid));
/*     */         }
/* 363 */         List stores = this.advstoresService
/* 364 */           .getAdvstoresList(query);
/* 365 */         model.addAttribute("stores", stores);
/* 366 */         return "advadv/save";
/*     */       }
/*     */     }
/*     */ 
/* 370 */     String domain = e.getUrl().trim();
/* 371 */     if (stringUtils.isNotBlank(domain)) {
/* 372 */       if ((!domain.startsWith("http://")) && (!domain.startsWith("https://"))) {
/* 373 */         domain = "http://" + domain;
/*     */       }
/* 375 */       while (domain.endsWith("/"))
/* 376 */         domain = domain.substring(0, domain.length() - 1);
/*     */     }
/*     */     else {
/* 379 */       domain = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */     }
/* 381 */     e.setUrl(domain);
/*     */ 
/* 383 */     e.setUid(Long.valueOf(uid));
/* 384 */     e.setCreatDate(new Date());
/* 385 */     this.advadvService.addAdvadv(e);
/*     */ 
/* 387 */     return "redirect:/advadv/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advadv/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 394 */     Advadv e = this.advadvService.getAdvadvByKey(id);
/* 395 */     HttpSession session = request.getSession();
/* 396 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 397 */     if ((user == null) || (user.getId() == null)) {
/* 398 */       return "homeAction/index";
/*     */     }
/* 400 */     long uid = user.getId().longValue();
/* 401 */     if (!"admin".equals(user.getLoginName())) {
/* 402 */       long suid = e.getUid().longValue();
/* 403 */       if (suid != uid) {
/* 404 */         return "redirect:/advadv/list.action";
/*     */       }
/*     */     }
/* 407 */     AdvstoresQuery query = new AdvstoresQuery();
/* 408 */     if (!"admin".equals(user.getLoginName())) {
/* 409 */       query.setUid(Long.valueOf(uid));
/*     */     }
/* 411 */     List stores = this.advstoresService.getAdvstoresList(query);
/* 412 */     model.addAttribute("stores", stores);
/* 413 */     model.addAttribute("entity", e);
/* 414 */     return "advadv/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advadv/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Advadv e, String showDateS, String endDateS, ModelMap model, HttpServletRequest request)
/*     */   {
/* 421 */     SimpleDateFormat format = new SimpleDateFormat(
/* 422 */       "yyyy-MM-dd HH:mm:ss");
/* 423 */     Advadv s = this.advadvService.getAdvadvByKey(e.getId());
/* 424 */     HttpSession session = request.getSession();
/* 425 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 426 */     if ((user == null) || (user.getId() == null)) {
/* 427 */       return "homeAction/index";
/*     */     }
/* 429 */     long uid = user.getId().longValue();
/* 430 */     if (!"admin".equals(user.getLoginName())) {
/* 431 */       long suid = s.getUid().longValue();
/* 432 */       if (suid != uid) {
/* 433 */         return "redirect:/advadv/list.action";
/*     */       }
/*     */     }
/*     */ 
/* 437 */     if ((showDateS != null) && (!"".equals(showDateS)))
/*     */       try {
/* 439 */         Date viewDate = format.parse(showDateS + " 00:00:00");
/* 440 */         e.setShowDate(viewDate);
/*     */       } catch (Exception ex) {
/* 442 */         model.addAttribute("entity", e);
/* 443 */         model.addAttribute("msg", "日期格式不正确！");
/* 444 */         AdvstoresQuery query = new AdvstoresQuery();
/* 445 */         if (!"admin".equals(user.getLoginName())) {
/* 446 */           query.setUid(Long.valueOf(uid));
/*     */         }
/* 448 */         List stores = this.advstoresService
/* 449 */           .getAdvstoresList(query);
/* 450 */         model.addAttribute("stores", stores);
/* 451 */         return "advadv/save";
/*     */       }
/*     */     else {
/* 454 */       e.setShowDate(new Date());
/*     */     }
/* 456 */     if ((endDateS != null) && (!"".equals(endDateS))) {
/*     */       try {
/* 458 */         Date viewDate = format.parse(endDateS + " 00:00:00");
/* 459 */         e.setEndDate(viewDate);
/*     */       } catch (Exception ex) {
/* 461 */         model.addAttribute("entity", e);
/* 462 */         model.addAttribute("msg", "日期格式不正确！");
/* 463 */         AdvstoresQuery query = new AdvstoresQuery();
/* 464 */         if (!"admin".equals(user.getLoginName())) {
/* 465 */           query.setUid(Long.valueOf(uid));
/*     */         }
/* 467 */         List stores = this.advstoresService
/* 468 */           .getAdvstoresList(query);
/* 469 */         model.addAttribute("stores", stores);
/* 470 */         return "advadv/save";
/*     */       }
/*     */     }
/*     */ 
/* 474 */     String domain = e.getUrl().trim();
/* 475 */     if (stringUtils.isNotBlank(domain)) {
/* 476 */       if ((!domain.startsWith("http://")) && (!domain.startsWith("https://"))) {
/* 477 */         domain = "http://" + domain;
/*     */       }
/* 479 */       while (domain.endsWith("/"))
/* 480 */         domain = domain.substring(0, domain.length() - 1);
/*     */     }
/*     */     else {
/* 483 */       domain = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
/*     */     }
/* 485 */     e.setUrl(domain);
/*     */ 
/* 487 */     this.advadvService.updateAdvadvByKey(e);
/* 488 */     return "redirect:/advadv/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advadv/delete.action"})
/*     */   public String delete(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 494 */     Advadv s = this.advadvService.getAdvadvByKey(id);
/* 495 */     HttpSession session = request.getSession();
/* 496 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 497 */     if ((user == null) || (user.getId() == null)) {
/* 498 */       return "homeAction/index";
/*     */     }
/* 500 */     long uid = user.getId().longValue();
/* 501 */     if (!"admin".equals(user.getLoginName())) {
/* 502 */       long suid = s.getUid().longValue();
/* 503 */       if (suid != uid) {
/* 504 */         return "redirect:/advadv/list.action";
/*     */       }
/*     */     }
/* 507 */     this.advadvService.deleteByKey(id);
/* 508 */     return "redirect:/advadv/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advadv/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
/*     */   {
/* 515 */     List<Long> list = Arrays.asList(ids);
/* 516 */     for (Long id : list) {
/* 517 */       Advadv s = this.advadvService.getAdvadvByKey(id);
/* 518 */       HttpSession session = request.getSession();
/* 519 */       Portaluser user = (Portaluser)session.getAttribute("user");
/* 520 */       if ((user == null) || (user.getId() == null)) {
/* 521 */         return "homeAction/index";
/*     */       }
/* 523 */       long uid = user.getId().longValue();
/* 524 */       if (!"admin".equals(user.getLoginName())) {
/* 525 */         long suid = s.getUid().longValue();
/* 526 */         if (suid == uid)
/* 527 */           this.advadvService.deleteByKey(id);
/*     */       }
/*     */       else {
/* 530 */         this.advadvService.deleteByKey(id);
/*     */       }
/*     */     }
/*     */ 
/* 534 */     return "redirect:/advadv/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advadv/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes(HttpServletRequest request)
/*     */   {
/* 540 */     HttpSession session = request.getSession();
/* 541 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 542 */     if ((user == null) || (user.getId() == null)) {
/* 543 */       return "homeAction/index";
/*     */     }
/* 545 */     long uid = user.getId().longValue();
/* 546 */     AdvadvQuery query = new AdvadvQuery();
/* 547 */     if (!"admin".equals(user.getLoginName())) {
/* 548 */       query.setUid(Long.valueOf(uid));
/*     */     }
/* 550 */     List<Advadv> advs = this.advadvService.getAdvadvList(query);
/* 551 */     for (Advadv adv : advs) {
/* 552 */       this.advadvService.deleteByKey(adv.getId());
/*     */     }
/* 554 */     return "redirect:/advadv/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AdvadvController
 * JD-Core Version:    0.6.2
 */