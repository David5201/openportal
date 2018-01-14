/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Advadv;
/*     */ import com.leeson.core.bean.Advpic;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.AdvadvQuery;
/*     */ import com.leeson.core.query.AdvpicQuery;
/*     */ import com.leeson.core.query.AdvstoresQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.AdvadvService;
/*     */ import com.leeson.core.service.AdvpicService;
/*     */ import com.leeson.core.service.AdvstoresService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import com.leeson.core.utils.ImageUtil;
/*     */ import java.io.File;
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
/*     */ public class AdvpicController
/*     */ {
/*  54 */   private static Logger logger = Logger.getLogger(AdvpicController.class);
/*     */ 
/*     */   @Autowired
/*     */   private AdvpicService advpicService;
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
/*  69 */   @ResponseBody
/*     */   @RequestMapping({"/advpic/uploadw"})
/*     */   public Map<String, String> uploadPreviewImageW(HttpServletRequest request, HttpServletResponse response) { Map map = new HashMap();
/*     */     try {
/*  71 */       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/*  72 */       MultipartFile image = multipartRequest.getFile("advimgw");
/*     */ 
/*  74 */       String imageName = image.getOriginalFilename();
/*  75 */       String file_ext = imageName.substring(imageName.lastIndexOf(".") + 1);
/*  76 */       String tempImageName = UUID.randomUUID().toString() + "." + file_ext;
/*     */ 
/*  78 */       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
/*  79 */       String filename = "/" + df.format(new Date()) + "/";
/*     */ 
/*  81 */       String filePath = request.getServletContext().getRealPath("/advPic") + filename;
/*  82 */       File fileLocation = new File(filePath);
/*  83 */       if (!fileLocation.exists()) {
/*  84 */         fileLocation.mkdir();
/*     */       }
/*  86 */       filePath = filePath + tempImageName;
/*  87 */       File file = new File(filePath);
/*  88 */       byte[] advImageBytes = null;
/*  89 */       InputStream advImageStream = null;
/*  90 */       file.createNewFile();
/*  91 */       advImageStream = image.getInputStream();
/*  92 */       advImageBytes = FileCopyUtils.copyToByteArray(advImageStream);
/*  93 */       FileCopyUtils.copy(advImageBytes, file);
/*  94 */       advImageStream.close();
/*  95 */       int result = ImageUtil.checkImgHW(filePath, 600, 150);
/*  96 */       if (result == 0) {
/*  97 */         String tempPath = "/advPic/" + filename + tempImageName;
/*  98 */         map.put("tempPath", tempPath);
/*  99 */         map.put("ret", "0");
/* 100 */         map.put("msg", "上传成功！！");
/* 101 */       } else if (result == 1) {
/* 102 */         map.put("ret", "1");
/* 103 */         map.put("msg", "图片尺寸错误！！");
/*     */       } else {
/* 105 */         map.put("ret", "1");
/* 106 */         map.put("msg", "图片格式错误！！");
/*     */       }
/*     */     } catch (Exception e) {
/* 109 */       logger.error("==============ERROR Start=============");
/* 110 */       logger.error(e);
/* 111 */       logger.error("ERROR INFO ", e);
/* 112 */       logger.error("==============ERROR End=============");
/* 113 */       map.put("ret", "1");
/* 114 */       map.put("msg", "发生错误！！");
/*     */     }
/* 116 */     return map; }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/advpic/upload"})
/*     */   public Map<String, String> uploadPreviewImage(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 123 */     Map map = new HashMap();
/*     */     try {
/* 125 */       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/* 126 */       MultipartFile image = multipartRequest.getFile("advimg");
/*     */ 
/* 128 */       String imageName = image.getOriginalFilename();
/* 129 */       String file_ext = imageName.substring(imageName.lastIndexOf(".") + 1);
/* 130 */       String tempImageName = UUID.randomUUID().toString() + "." + file_ext;
/*     */ 
/* 132 */       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
/* 133 */       String filename = "/" + df.format(new Date()) + "/";
/*     */ 
/* 135 */       String filePath = request.getServletContext().getRealPath("/advPic") + filename;
/* 136 */       File fileLocation = new File(filePath);
/* 137 */       if (!fileLocation.exists()) {
/* 138 */         fileLocation.mkdir();
/*     */       }
/* 140 */       filePath = filePath + tempImageName;
/* 141 */       File file = new File(filePath);
/* 142 */       byte[] advImageBytes = null;
/* 143 */       InputStream advImageStream = null;
/* 144 */       file.createNewFile();
/* 145 */       advImageStream = image.getInputStream();
/* 146 */       advImageBytes = FileCopyUtils.copyToByteArray(advImageStream);
/* 147 */       FileCopyUtils.copy(advImageBytes, file);
/* 148 */       advImageStream.close();
/* 149 */       int result = ImageUtil.checkImgHW(filePath, 300, 200);
/* 150 */       if (result == 0) {
/* 151 */         String tempPath = "/advPic/" + filename + tempImageName;
/* 152 */         map.put("tempPath", tempPath);
/* 153 */         map.put("ret", "0");
/* 154 */         map.put("msg", "上传成功！！");
/* 155 */       } else if (result == 1) {
/* 156 */         map.put("ret", "1");
/* 157 */         map.put("msg", "图片尺寸错误！！");
/*     */       } else {
/* 159 */         map.put("ret", "1");
/* 160 */         map.put("msg", "图片格式错误！！");
/*     */       }
/*     */     } catch (Exception e) {
/* 163 */       logger.error("==============ERROR Start=============");
/* 164 */       logger.error(e);
/* 165 */       logger.error("ERROR INFO ", e);
/* 166 */       logger.error("==============ERROR End=============");
/* 167 */       map.put("ret", "1");
/* 168 */       map.put("msg", "发生错误！！");
/*     */     }
/* 170 */     return map;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advpic/list.action"})
/*     */   public String page(AdvpicQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 176 */     List users = this.portaluserService
/* 177 */       .getPortaluserList(new PortaluserQuery());
/* 178 */     model.addAttribute("users", users);
/*     */ 
/* 180 */     HttpSession session = request.getSession();
/* 181 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 182 */     if ((user == null) || (user.getId() == null)) {
/* 183 */       return "homeAction/index";
/*     */     }
/* 185 */     long uid = user.getId().longValue();
/* 186 */     if (!"admin".equals(user.getLoginName())) {
/* 187 */       query.setUid(Long.valueOf(uid));
/*     */     }
/*     */ 
/* 190 */     query.setNameLike(true);
/* 191 */     if (stringUtils.isBlank(query.getName())) {
/* 192 */       query.setName(null);
/*     */     }
/*     */ 
/* 195 */     Pagination pagination = this.advpicService.getAdvpicListWithPage(query);
/* 196 */     model.addAttribute("pagination", pagination);
/* 197 */     model.addAttribute("query", query);
/*     */ 
/* 199 */     AdvstoresQuery squery = new AdvstoresQuery();
/* 200 */     AdvadvQuery aQuery = new AdvadvQuery();
/* 201 */     if (!"admin".equals(user.getLoginName())) {
/* 202 */       squery.setUid(Long.valueOf(uid));
/* 203 */       aQuery.setUid(Long.valueOf(uid));
/*     */     }
/* 205 */     List stores = this.advstoresService.getAdvstoresList(squery);
/* 206 */     model.addAttribute("stores", stores);
/* 207 */     List advs = this.advadvService.getAdvadvList(aQuery);
/* 208 */     model.addAttribute("advs", advs);
/* 209 */     return "advpic/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advpic/add.action"})
/*     */   public String addv(Advpic e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 215 */     HttpSession session = request.getSession();
/* 216 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 217 */     if ((user == null) || (user.getId() == null)) {
/* 218 */       return "homeAction/index";
/*     */     }
/* 220 */     long uid = user.getId().longValue();
/* 221 */     AdvadvQuery aQuery = new AdvadvQuery();
/* 222 */     if (!"admin".equals(user.getLoginName())) {
/* 223 */       aQuery.setUid(Long.valueOf(uid));
/*     */     }
/* 225 */     List advs = this.advadvService.getAdvadvList(aQuery);
/* 226 */     model.addAttribute("advs", advs);
/* 227 */     model.addAttribute("entity", e);
/* 228 */     return "advpic/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advpic/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Advpic e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 234 */     HttpSession session = request.getSession();
/* 235 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 236 */     if ((user == null) || (user.getId() == null)) {
/* 237 */       return "homeAction/index";
/*     */     }
/* 239 */     long uid = user.getId().longValue();
/* 240 */     Advadv adv = this.advadvService.getAdvadvByKey(e.getAid());
/* 241 */     long auid = adv.getUid().longValue();
/* 242 */     if (uid != auid) {
/* 243 */       return "redirect:/advpic/list.action";
/*     */     }
/* 245 */     e.setUid(Long.valueOf(uid));
/* 246 */     e.setSid(adv.getSid());
/*     */ 
/* 248 */     String domain = e.getUrl().trim();
/* 249 */     if (stringUtils.isNotBlank(domain)) {
/* 250 */       if ((!domain.startsWith("http://")) && (!domain.startsWith("https://"))) {
/* 251 */         domain = "http://" + domain;
/*     */       }
/* 253 */       while (domain.endsWith("/"))
/* 254 */         domain = domain.substring(0, domain.length() - 1);
/*     */     }
/*     */     else {
/* 257 */       domain = 
/* 258 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 258 */         .get("core"))[0];
/*     */     }
/* 260 */     e.setUrl(domain);
/*     */ 
/* 262 */     this.advpicService.addAdvpic(e);
/*     */ 
/* 264 */     return "redirect:/advpic/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advpic/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 270 */     Advpic e = this.advpicService.getAdvpicByKey(id);
/* 271 */     HttpSession session = request.getSession();
/* 272 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 273 */     if ((user == null) || (user.getId() == null)) {
/* 274 */       return "homeAction/index";
/*     */     }
/* 276 */     long uid = user.getId().longValue();
/* 277 */     if (!"admin".equals(user.getLoginName())) {
/* 278 */       long suid = e.getUid().longValue();
/* 279 */       if (suid != uid) {
/* 280 */         return "redirect:/advpic/list.action";
/*     */       }
/*     */     }
/*     */ 
/* 284 */     AdvadvQuery aQuery = new AdvadvQuery();
/* 285 */     if (!"admin".equals(user.getLoginName())) {
/* 286 */       aQuery.setUid(Long.valueOf(uid));
/*     */     }
/* 288 */     List advs = this.advadvService.getAdvadvList(aQuery);
/* 289 */     model.addAttribute("advs", advs);
/*     */ 
/* 291 */     model.addAttribute("entity", e);
/* 292 */     return "advpic/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advpic/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Advpic e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 298 */     Advpic p = this.advpicService.getAdvpicByKey(e.getId());
/* 299 */     HttpSession session = request.getSession();
/* 300 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 301 */     if ((user == null) || (user.getId() == null)) {
/* 302 */       return "homeAction/index";
/*     */     }
/* 304 */     long uid = user.getId().longValue();
/* 305 */     if (!"admin".equals(user.getLoginName())) {
/* 306 */       long suid = p.getUid().longValue();
/* 307 */       if (suid != uid) {
/* 308 */         return "redirect:/advpic/list.action";
/*     */       }
/*     */     }
/*     */ 
/* 312 */     String domain = e.getUrl().trim();
/* 313 */     if (stringUtils.isNotBlank(domain)) {
/* 314 */       if ((!domain.startsWith("http://")) && (!domain.startsWith("https://"))) {
/* 315 */         domain = "http://" + domain;
/*     */       }
/* 317 */       while (domain.endsWith("/"))
/* 318 */         domain = domain.substring(0, domain.length() - 1);
/*     */     }
/*     */     else {
/* 321 */       domain = 
/* 322 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 322 */         .get("core"))[0];
/*     */     }
/* 324 */     e.setUrl(domain);
/*     */ 
/* 326 */     this.advpicService.updateAdvpicByKey(e);
/* 327 */     return "redirect:/advpic/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advpic/delete.action"})
/*     */   public String delete(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 335 */     Advpic s = this.advpicService.getAdvpicByKey(id);
/* 336 */     HttpSession session = request.getSession();
/* 337 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 338 */     if ((user == null) || (user.getId() == null)) {
/* 339 */       return "homeAction/index";
/*     */     }
/* 341 */     long uid = user.getId().longValue();
/* 342 */     if (!"admin".equals(user.getLoginName())) {
/* 343 */       long suid = s.getUid().longValue();
/* 344 */       if (suid != uid) {
/* 345 */         return "redirect:/advpic/list.action";
/*     */       }
/*     */     }
/* 348 */     this.advpicService.deleteByKey(id);
/* 349 */     return "redirect:/advpic/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advpic/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
/*     */   {
/* 356 */     List<Long> list = Arrays.asList(ids);
/* 357 */     for (Long id : list) {
/* 358 */       Advpic s = this.advpicService.getAdvpicByKey(id);
/* 359 */       HttpSession session = request.getSession();
/* 360 */       Portaluser user = (Portaluser)session.getAttribute("user");
/* 361 */       if ((user == null) || (user.getId() == null)) {
/* 362 */         return "homeAction/index";
/*     */       }
/* 364 */       long uid = user.getId().longValue();
/* 365 */       if (!"admin".equals(user.getLoginName())) {
/* 366 */         long suid = s.getUid().longValue();
/* 367 */         if (suid == uid)
/* 368 */           this.advpicService.deleteByKey(id);
/*     */       }
/*     */       else {
/* 371 */         this.advpicService.deleteByKey(id);
/*     */       }
/*     */     }
/*     */ 
/* 375 */     return "redirect:/advpic/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advpic/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes(HttpServletRequest request)
/*     */   {
/* 381 */     HttpSession session = request.getSession();
/* 382 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 383 */     if ((user == null) || (user.getId() == null)) {
/* 384 */       return "homeAction/index";
/*     */     }
/* 386 */     long uid = user.getId().longValue();
/* 387 */     AdvpicQuery query = new AdvpicQuery();
/* 388 */     if (!"admin".equals(user.getLoginName())) {
/* 389 */       query.setUid(Long.valueOf(uid));
/*     */     }
/* 391 */     List<Advpic> pics = this.advpicService.getAdvpicList(query);
/* 392 */     for (Advpic pic : pics) {
/* 393 */       this.advpicService.deleteByKey(pic.getId());
/*     */     }
/* 395 */     return "redirect:/advpic/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AdvpicController
 * JD-Core Version:    0.6.2
 */