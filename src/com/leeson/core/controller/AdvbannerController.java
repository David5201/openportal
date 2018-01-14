/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Advadv;
/*     */ import com.leeson.core.bean.Advbanner;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.AdvadvQuery;
/*     */ import com.leeson.core.query.AdvbannerQuery;
/*     */ import com.leeson.core.query.AdvstoresQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.AdvadvService;
/*     */ import com.leeson.core.service.AdvbannerService;
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
/*     */ public class AdvbannerController
/*     */ {
/*  54 */   private static Logger logger = Logger.getLogger(AdvbannerController.class);
/*     */ 
/*     */   @Autowired
/*     */   private AdvbannerService advbannerService;
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
/*     */   @RequestMapping({"/advbanner/upload"})
/*     */   public Map<String, String> uploadPreviewImage(HttpServletRequest request, HttpServletResponse response) { Map map = new HashMap();
/*     */     try {
/*  71 */       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/*  72 */       MultipartFile image = multipartRequest.getFile("advimg");
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
/*  95 */       int result = ImageUtil.checkImgHW(filePath, 600, 300);
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
/* 116 */     return map;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advbanner/list.action"})
/*     */   public String page(AdvbannerQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 122 */     List users = this.portaluserService
/* 123 */       .getPortaluserList(new PortaluserQuery());
/* 124 */     model.addAttribute("users", users);
/*     */ 
/* 126 */     HttpSession session = request.getSession();
/* 127 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 128 */     if ((user == null) || (user.getId() == null)) {
/* 129 */       return "homeAction/index";
/*     */     }
/* 131 */     long uid = user.getId().longValue();
/* 132 */     if (!"admin".equals(user.getLoginName())) {
/* 133 */       query.setUid(Long.valueOf(uid));
/*     */     }
/*     */ 
/* 136 */     query.setNameLike(true);
/* 137 */     if (stringUtils.isBlank(query.getName())) {
/* 138 */       query.setName(null);
/*     */     }
/*     */ 
/* 141 */     Pagination pagination = this.advbannerService.getAdvbannerListWithPage(query);
/* 142 */     model.addAttribute("pagination", pagination);
/* 143 */     model.addAttribute("query", query);
/*     */ 
/* 145 */     AdvstoresQuery squery = new AdvstoresQuery();
/* 146 */     AdvadvQuery aQuery = new AdvadvQuery();
/* 147 */     if (!"admin".equals(user.getLoginName())) {
/* 148 */       squery.setUid(Long.valueOf(uid));
/* 149 */       aQuery.setUid(Long.valueOf(uid));
/*     */     }
/* 151 */     List stores = this.advstoresService.getAdvstoresList(squery);
/* 152 */     model.addAttribute("stores", stores);
/* 153 */     List advs = this.advadvService.getAdvadvList(aQuery);
/* 154 */     model.addAttribute("advs", advs);
/* 155 */     return "advbanner/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advbanner/add.action"})
/*     */   public String addv(Advbanner e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 161 */     HttpSession session = request.getSession();
/* 162 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 163 */     if ((user == null) || (user.getId() == null)) {
/* 164 */       return "homeAction/index";
/*     */     }
/* 166 */     long uid = user.getId().longValue();
/* 167 */     AdvadvQuery aQuery = new AdvadvQuery();
/* 168 */     if (!"admin".equals(user.getLoginName())) {
/* 169 */       aQuery.setUid(Long.valueOf(uid));
/*     */     }
/* 171 */     List advs = this.advadvService.getAdvadvList(aQuery);
/* 172 */     model.addAttribute("advs", advs);
/* 173 */     model.addAttribute("entity", e);
/* 174 */     return "advbanner/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advbanner/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Advbanner e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 180 */     HttpSession session = request.getSession();
/* 181 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 182 */     if ((user == null) || (user.getId() == null)) {
/* 183 */       return "homeAction/index";
/*     */     }
/* 185 */     long uid = user.getId().longValue();
/* 186 */     Advadv adv = this.advadvService.getAdvadvByKey(e.getAid());
/* 187 */     long auid = adv.getUid().longValue();
/* 188 */     if (uid != auid) {
/* 189 */       return "redirect:/advbanner/list.action";
/*     */     }
/* 191 */     e.setUid(Long.valueOf(uid));
/* 192 */     e.setSid(adv.getSid());
/*     */ 
/* 194 */     String domain = e.getUrl().trim();
/* 195 */     if (stringUtils.isNotBlank(domain)) {
/* 196 */       if ((!domain.startsWith("http://")) && (!domain.startsWith("https://"))) {
/* 197 */         domain = "http://" + domain;
/*     */       }
/* 199 */       while (domain.endsWith("/"))
/* 200 */         domain = domain.substring(0, domain.length() - 1);
/*     */     }
/*     */     else {
/* 203 */       domain = 
/* 204 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 204 */         .get("core"))[0];
/*     */     }
/* 206 */     e.setUrl(domain);
/*     */ 
/* 208 */     this.advbannerService.addAdvbanner(e);
/*     */ 
/* 210 */     return "redirect:/advbanner/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advbanner/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 216 */     Advbanner e = this.advbannerService.getAdvbannerByKey(id);
/* 217 */     HttpSession session = request.getSession();
/* 218 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 219 */     if ((user == null) || (user.getId() == null)) {
/* 220 */       return "homeAction/index";
/*     */     }
/* 222 */     long uid = user.getId().longValue();
/* 223 */     if (!"admin".equals(user.getLoginName())) {
/* 224 */       long suid = e.getUid().longValue();
/* 225 */       if (suid != uid) {
/* 226 */         return "redirect:/advbanner/list.action";
/*     */       }
/*     */     }
/*     */ 
/* 230 */     AdvadvQuery aQuery = new AdvadvQuery();
/* 231 */     if (!"admin".equals(user.getLoginName())) {
/* 232 */       aQuery.setUid(Long.valueOf(uid));
/*     */     }
/* 234 */     List advs = this.advadvService.getAdvadvList(aQuery);
/* 235 */     model.addAttribute("advs", advs);
/*     */ 
/* 237 */     model.addAttribute("entity", e);
/* 238 */     return "advbanner/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advbanner/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Advbanner e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 244 */     Advbanner b = this.advbannerService.getAdvbannerByKey(e.getId());
/* 245 */     HttpSession session = request.getSession();
/* 246 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 247 */     if ((user == null) || (user.getId() == null)) {
/* 248 */       return "homeAction/index";
/*     */     }
/* 250 */     long uid = user.getId().longValue();
/* 251 */     if (!"admin".equals(user.getLoginName())) {
/* 252 */       long suid = b.getUid().longValue();
/* 253 */       if (suid != uid) {
/* 254 */         return "redirect:/advbanner/list.action";
/*     */       }
/*     */     }
/*     */ 
/* 258 */     String domain = e.getUrl().trim();
/* 259 */     if (stringUtils.isNotBlank(domain)) {
/* 260 */       if ((!domain.startsWith("http://")) && (!domain.startsWith("https://"))) {
/* 261 */         domain = "http://" + domain;
/*     */       }
/* 263 */       while (domain.endsWith("/"))
/* 264 */         domain = domain.substring(0, domain.length() - 1);
/*     */     }
/*     */     else {
/* 267 */       domain = 
/* 268 */         ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
/* 268 */         .get("core"))[0];
/*     */     }
/* 270 */     e.setUrl(domain);
/*     */ 
/* 272 */     this.advbannerService.updateAdvbannerByKey(e);
/* 273 */     return "redirect:/advbanner/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advbanner/delete.action"})
/*     */   public String delete(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 281 */     Advbanner s = this.advbannerService.getAdvbannerByKey(id);
/* 282 */     HttpSession session = request.getSession();
/* 283 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 284 */     if ((user == null) || (user.getId() == null)) {
/* 285 */       return "homeAction/index";
/*     */     }
/* 287 */     long uid = user.getId().longValue();
/* 288 */     if (!"admin".equals(user.getLoginName())) {
/* 289 */       long suid = s.getUid().longValue();
/* 290 */       if (suid != uid) {
/* 291 */         return "redirect:/advbanner/list.action";
/*     */       }
/*     */     }
/* 294 */     this.advbannerService.deleteByKey(id);
/* 295 */     return "redirect:/advbanner/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advbanner/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
/*     */   {
/* 302 */     List<Long> list = Arrays.asList(ids);
/* 303 */     for (Long id : list) {
/* 304 */       Advbanner s = this.advbannerService.getAdvbannerByKey(id);
/* 305 */       HttpSession session = request.getSession();
/* 306 */       Portaluser user = (Portaluser)session.getAttribute("user");
/* 307 */       if ((user == null) || (user.getId() == null)) {
/* 308 */         return "homeAction/index";
/*     */       }
/* 310 */       long uid = user.getId().longValue();
/* 311 */       if (!"admin".equals(user.getLoginName())) {
/* 312 */         long suid = s.getUid().longValue();
/* 313 */         if (suid == uid)
/* 314 */           this.advbannerService.deleteByKey(id);
/*     */       }
/*     */       else {
/* 317 */         this.advbannerService.deleteByKey(id);
/*     */       }
/*     */     }
/*     */ 
/* 321 */     return "redirect:/advbanner/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advbanner/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes(HttpServletRequest request)
/*     */   {
/* 327 */     HttpSession session = request.getSession();
/* 328 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 329 */     if ((user == null) || (user.getId() == null)) {
/* 330 */       return "homeAction/index";
/*     */     }
/* 332 */     long uid = user.getId().longValue();
/* 333 */     AdvbannerQuery query = new AdvbannerQuery();
/* 334 */     if (!"admin".equals(user.getLoginName())) {
/* 335 */       query.setUid(Long.valueOf(uid));
/*     */     }
/* 337 */     List<Advbanner> banners = this.advbannerService.getAdvbannerList(query);
/* 338 */     for (Advbanner banner : banners) {
/* 339 */       this.advbannerService.deleteByKey(banner.getId());
/*     */     }
/* 341 */     return "redirect:/advbanner/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AdvbannerController
 * JD-Core Version:    0.6.2
 */