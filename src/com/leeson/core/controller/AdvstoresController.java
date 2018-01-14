/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Advstores;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.AdvstoresQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
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
/*     */ public class AdvstoresController
/*     */ {
/*  47 */   private static Logger logger = Logger.getLogger(AdvstoresController.class);
/*     */ 
/*     */   @Autowired
/*     */   private AdvstoresService advstoresService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaluserService portaluserService;
/*     */ 
/*  58 */   @ResponseBody
/*     */   @RequestMapping({"/advstores/upload"})
/*     */   public Map<String, String> uploadPreviewImage(HttpServletRequest request, HttpServletResponse response) { Map map = new HashMap();
/*     */     try {
/*  60 */       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/*  61 */       MultipartFile image = multipartRequest.getFile("advimg");
/*     */ 
/*  63 */       String imageName = image.getOriginalFilename();
/*  64 */       String file_ext = imageName.substring(imageName.lastIndexOf(".") + 1);
/*  65 */       String tempImageName = UUID.randomUUID().toString() + "." + file_ext;
/*     */ 
/*  67 */       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
/*  68 */       String filename = "/" + df.format(new Date()) + "/";
/*     */ 
/*  70 */       String filePath = request.getServletContext().getRealPath("/advPic") + filename;
/*  71 */       File fileLocation = new File(filePath);
/*  72 */       if (!fileLocation.exists()) {
/*  73 */         fileLocation.mkdir();
/*     */       }
/*  75 */       filePath = filePath + tempImageName;
/*  76 */       File file = new File(filePath);
/*  77 */       byte[] advImageBytes = null;
/*  78 */       InputStream advImageStream = null;
/*  79 */       file.createNewFile();
/*  80 */       advImageStream = image.getInputStream();
/*  81 */       advImageBytes = FileCopyUtils.copyToByteArray(advImageStream);
/*  82 */       FileCopyUtils.copy(advImageBytes, file);
/*  83 */       advImageStream.close();
/*  84 */       int result = ImageUtil.checkImgHW(filePath, 96, 96);
/*  85 */       if (result == 0) {
/*  86 */         String tempPath = "/advPic/" + filename + tempImageName;
/*  87 */         map.put("tempPath", tempPath);
/*  88 */         map.put("ret", "0");
/*  89 */         map.put("msg", "上传成功！！");
/*  90 */       } else if (result == 1) {
/*  91 */         map.put("ret", "1");
/*  92 */         map.put("msg", "图片尺寸错误！！");
/*     */       } else {
/*  94 */         map.put("ret", "1");
/*  95 */         map.put("msg", "图片格式错误！！");
/*     */       }
/*     */     } catch (Exception e) {
/*  98 */       logger.error("==============ERROR Start=============");
/*  99 */       logger.error(e);
/* 100 */       logger.error("ERROR INFO ", e);
/* 101 */       logger.error("==============ERROR End=============");
/* 102 */       map.put("ret", "1");
/* 103 */       map.put("msg", "发生错误！！");
/*     */     }
/* 105 */     return map;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advstores/list.action"})
/*     */   public String page(AdvstoresQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 112 */     List users = this.portaluserService
/* 113 */       .getPortaluserList(new PortaluserQuery());
/* 114 */     model.addAttribute("users", users);
/*     */ 
/* 116 */     HttpSession session = request.getSession();
/* 117 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 118 */     if ((user == null) || (user.getId() == null)) {
/* 119 */       return "homeAction/index";
/*     */     }
/* 121 */     long uid = user.getId().longValue();
/* 122 */     if (!"admin".equals(user.getLoginName())) {
/* 123 */       query.setUid(Long.valueOf(uid));
/*     */     }
/*     */ 
/* 126 */     query.setNameLike(true);
/* 127 */     query.setDescriptionLike(true);
/* 128 */     if (stringUtils.isBlank(query.getName())) {
/* 129 */       query.setName(null);
/*     */     }
/* 131 */     if (stringUtils.isBlank(query.getDescription())) {
/* 132 */       query.setDescription(null);
/*     */     }
/* 134 */     Pagination pagination = this.advstoresService
/* 135 */       .getAdvstoresListWithPage(query);
/* 136 */     model.addAttribute("pagination", pagination);
/* 137 */     model.addAttribute("query", query);
/* 138 */     return "advstores/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advstores/add.action"})
/*     */   public String add(ModelMap model, HttpServletRequest request)
/*     */   {
/* 144 */     HttpSession session = request.getSession();
/* 145 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 146 */     if ((user == null) || (user.getId() == null)) {
/* 147 */       return "homeAction/index";
/*     */     }
/* 149 */     return "advstores/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advstores/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Advstores e, HttpServletRequest request)
/*     */   {
/* 155 */     HttpSession session = request.getSession();
/* 156 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 157 */     if ((user == null) || (user.getId() == null)) {
/* 158 */       return "homeAction/index";
/*     */     }
/* 160 */     long uid = user.getId().longValue();
/* 161 */     e.setUid(Long.valueOf(uid));
/* 162 */     e.setCreatDate(new Date());
/* 163 */     this.advstoresService.addAdvstores(e);
/*     */ 
/* 165 */     return "redirect:/advstores/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advstores/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 171 */     Advstores e = this.advstoresService.getAdvstoresByKey(id);
/* 172 */     HttpSession session = request.getSession();
/* 173 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 174 */     if ((user == null) || (user.getId() == null)) {
/* 175 */       return "homeAction/index";
/*     */     }
/* 177 */     long uid = user.getId().longValue();
/* 178 */     if (!"admin".equals(user.getLoginName())) {
/* 179 */       long suid = e.getUid().longValue();
/* 180 */       if (suid != uid) {
/* 181 */         return "redirect:/advstores/list.action";
/*     */       }
/*     */     }
/* 184 */     model.addAttribute("entity", e);
/* 185 */     return "advstores/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advstores/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Advstores e, HttpServletRequest request)
/*     */   {
/* 191 */     Advstores s = this.advstoresService.getAdvstoresByKey(e.getId());
/* 192 */     HttpSession session = request.getSession();
/* 193 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 194 */     if ((user == null) || (user.getId() == null)) {
/* 195 */       return "homeAction/index";
/*     */     }
/* 197 */     long uid = user.getId().longValue();
/* 198 */     if (!"admin".equals(user.getLoginName())) {
/* 199 */       long suid = s.getUid().longValue();
/* 200 */       if (suid != uid) {
/* 201 */         return "redirect:/advstores/list.action";
/*     */       }
/*     */     }
/* 204 */     this.advstoresService.updateAdvstoresByKey(e);
/* 205 */     return "redirect:/advstores/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advstores/delete.action"})
/*     */   public String delete(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 211 */     Advstores s = this.advstoresService.getAdvstoresByKey(id);
/* 212 */     HttpSession session = request.getSession();
/* 213 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 214 */     if ((user == null) || (user.getId() == null)) {
/* 215 */       return "homeAction/index";
/*     */     }
/* 217 */     long uid = user.getId().longValue();
/* 218 */     if (!"admin".equals(user.getLoginName())) {
/* 219 */       long suid = s.getUid().longValue();
/* 220 */       if (suid != uid) {
/* 221 */         return "redirect:/advstores/list.action";
/*     */       }
/*     */     }
/* 224 */     this.advstoresService.deleteByKey(id);
/* 225 */     return "redirect:/advstores/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advstores/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
/*     */   {
/* 232 */     List<Long> list = Arrays.asList(ids);
/* 233 */     for (Long id : list) {
/* 234 */       Advstores s = this.advstoresService.getAdvstoresByKey(id);
/* 235 */       HttpSession session = request.getSession();
/* 236 */       Portaluser user = (Portaluser)session.getAttribute("user");
/* 237 */       if ((user == null) || (user.getId() == null)) {
/* 238 */         return "homeAction/index";
/*     */       }
/* 240 */       long uid = user.getId().longValue();
/* 241 */       if (!"admin".equals(user.getLoginName())) {
/* 242 */         long suid = s.getUid().longValue();
/* 243 */         if (suid == uid)
/* 244 */           this.advstoresService.deleteByKey(id);
/*     */       }
/*     */       else {
/* 247 */         this.advstoresService.deleteByKey(id);
/*     */       }
/*     */     }
/*     */ 
/* 251 */     return "redirect:/advstores/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/advstores/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes(HttpServletRequest request)
/*     */   {
/* 257 */     HttpSession session = request.getSession();
/* 258 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 259 */     if ((user == null) || (user.getId() == null)) {
/* 260 */       return "homeAction/index";
/*     */     }
/* 262 */     long uid = user.getId().longValue();
/* 263 */     AdvstoresQuery query = new AdvstoresQuery();
/* 264 */     if (!"admin".equals(user.getLoginName())) {
/* 265 */       query.setUid(Long.valueOf(uid));
/*     */     }
/* 267 */     List<Advstores> stores = this.advstoresService.getAdvstoresList(query);
/* 268 */     for (Advstores s : stores) {
/* 269 */       this.advstoresService.deleteByKey(s.getId());
/*     */     }
/* 271 */     return "redirect:/advstores/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AdvstoresController
 * JD-Core Version:    0.6.2
 */