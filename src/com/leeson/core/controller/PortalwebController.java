/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.DiyUtils;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalweb;
/*     */ import com.leeson.core.query.AdvadvQuery;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.AdvadvService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import com.leeson.portal.core.listener.ReportService;
/*     */ import com.leeson.portal.core.model.isDo;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public class PortalwebController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalwebService portalwebService;
/*     */ 
/*     */   @Autowired
/*     */   private AdvadvService advadvService;
/*     */ 
/*     */   @RequestMapping({"/portalweb/list.action"})
/*     */   public String page(PortalwebQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  58 */     query.setNameLike(true);
/*  59 */     query.setDescriptionLike(true);
/*  60 */     if (stringUtils.isBlank(query.getName())) {
/*  61 */       query.setName(null);
/*     */     }
/*  63 */     if (stringUtils.isBlank(query.getDescription())) {
/*  64 */       query.setDescription(null);
/*     */     }
/*  66 */     Pagination pagination = this.portalwebService
/*  67 */       .getPortalwebListWithPage(query);
/*  68 */     model.addAttribute("pagination", pagination);
/*  69 */     model.addAttribute("query", query);
/*     */ 
/*  71 */     List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
/*  72 */     model.addAttribute("advs", advs);
/*  73 */     return "portalweb/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalweb/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  79 */     List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
/*  80 */     model.addAttribute("advs", advs);
/*  81 */     return "portalweb/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalweb/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(@RequestParam(required=false) MultipartFile file, Portalweb e, ModelMap model, HttpServletRequest request)
/*     */   {
/*  88 */     List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
/*  89 */     model.addAttribute("advs", advs);
/*     */ 
/*  91 */     if (!Do()) {
/*  92 */       model.addAttribute("msg", "系统未授权或者已经过期！");
/*  93 */       model.addAttribute("entity", e);
/*  94 */       return "portalweb/save";
/*     */     }
/*  96 */     PortalwebQuery q = new PortalwebQuery();
/*  97 */     q.setName(e.getName());
/*  98 */     q.setNameLike(false);
/*  99 */     if (this.portalwebService.getPortalwebList(q).size() > 0) {
/* 100 */       model.addAttribute("msg", "该名称已经存在！");
/* 101 */       model.addAttribute("entity", e);
/* 102 */       return "portalweb/save";
/*     */     }
/*     */ 
/* 106 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/* 107 */     if (!ext.equals("zip")) {
/* 108 */       model.addAttribute("msg", "文件错误!");
/* 109 */       model.addAttribute("entity", e);
/* 110 */       return "portalweb/save";
/*     */     }
/*     */ 
/* 113 */     this.portalwebService.addPortalweb(e);
/* 114 */     String id = String.valueOf(e.getId());
/*     */ 
/* 116 */     Date now = new Date();
/* 117 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 118 */     String nowString = format.format(now);
/* 119 */     String sourceFile = "web-" + nowString + ".zip";
/* 120 */     String dir = null;
/*     */     try
/*     */     {
/* 123 */       InputStream in = file.getInputStream();
/* 124 */       dir = request.getServletContext().getRealPath("/version");
/* 125 */       File fileLocation = new File(dir);
/*     */ 
/* 127 */       if (!fileLocation.exists()) {
/* 128 */         boolean isCreated = fileLocation.mkdir();
/* 129 */         if (!isCreated)
/*     */         {
/* 131 */           model.addAttribute("msg", "权限不足!");
/* 132 */           model.addAttribute("entity", e);
/* 133 */           return "portalweb/save";
/*     */         }
/*     */       }
/* 136 */       dir = request.getServletContext().getRealPath("/version");
/*     */ 
/* 138 */       File uploadFile = new File(dir, sourceFile);
/*     */ 
/* 140 */       FileUtils.copyInputStreamToFile(in, uploadFile);
/* 141 */       in.close();
/*     */     } catch (Exception ex) {
/* 143 */       model.addAttribute("msg", "上传出错!");
/* 144 */       File df = new File(dir, sourceFile);
/* 145 */       if (df.exists()) {
/* 146 */         ReportService.deleteAll(df);
/*     */       }
/* 148 */       model.addAttribute("entity", e);
/* 149 */       return "portalweb/save";
/*     */     }
/*     */     try
/*     */     {
/* 153 */       String descDir = request.getServletContext().getRealPath("/");
/* 154 */       descDir = descDir + "/" + id + "/";
/* 155 */       File idpath = new File(descDir);
/*     */ 
/* 157 */       if (!idpath.exists()) {
/* 158 */         boolean isCreated = idpath.mkdir();
/* 159 */         if (!isCreated)
/*     */         {
/* 161 */           model.addAttribute("msg", "权限不足!");
/* 162 */           model.addAttribute("entity", e);
/* 163 */           return "portalweb/save";
/*     */         }
/*     */       }
/* 166 */       sourceFile = dir + "/" + sourceFile;
/* 167 */       DiyUtils.unZip(descDir, sourceFile);
/*     */     }
/*     */     catch (Exception ex) {
/* 170 */       model.addAttribute("msg", "解压出错!");
/* 171 */       File df = new File(dir);
/* 172 */       if (df.exists()) {
/* 173 */         ReportService.deleteAll(df);
/*     */       }
/* 175 */       model.addAttribute("entity", e);
/* 176 */       return "portalweb/save";
/*     */     }
/*     */ 
/* 179 */     File df = new File(dir);
/* 180 */     if (df.exists()) {
/* 181 */       ReportService.deleteAll(df);
/*     */     }
/* 183 */     model.addAttribute("msg", "添加成功!");
/*     */ 
/* 185 */     return "redirect:/portalweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalweb/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/* 191 */     Portalweb e = this.portalwebService.getPortalwebByKey(id);
/* 192 */     model.addAttribute("entity", e);
/* 193 */     List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
/* 194 */     model.addAttribute("advs", advs);
/* 195 */     return "portalweb/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalweb/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(@RequestParam(required=false) MultipartFile file, Portalweb e, ModelMap model, HttpServletRequest request)
/*     */   {
/* 202 */     List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
/* 203 */     model.addAttribute("advs", advs);
/*     */ 
/* 205 */     if (!Do()) {
/* 206 */       model.addAttribute("msg", "系统未授权或者已经过期！");
/* 207 */       model.addAttribute("entity", e);
/* 208 */       return "portalweb/save";
/*     */     }
/* 210 */     PortalwebQuery q = new PortalwebQuery();
/* 211 */     q.setName(e.getName());
/* 212 */     q.setNameLike(false);
/* 213 */     List webs = this.portalwebService.getPortalwebList(q);
/* 214 */     if ((webs != null) && (webs.size() > 0) && 
/* 215 */       (((Portalweb)webs.get(0)).getId() != e.getId())) {
/* 216 */       model.addAttribute("msg", "该名称已经存在！");
/* 217 */       model.addAttribute("entity", e);
/* 218 */       return "portalweb/save";
/*     */     }
/*     */ 
/* 222 */     this.portalwebService.updatePortalwebByKey(e);
/*     */ 
/* 224 */     if (!file.isEmpty()) {
/* 225 */       String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/* 226 */       if (!ext.equals("zip")) {
/* 227 */         model.addAttribute("msg", "文件错误!");
/* 228 */         model.addAttribute("entity", e);
/* 229 */         return "portalweb/save";
/*     */       }
/* 231 */       String id = String.valueOf(e.getId());
/* 232 */       Date now = new Date();
/* 233 */       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 234 */       String nowString = format.format(now);
/* 235 */       String sourceFile = "web-" + nowString + ".zip";
/* 236 */       String dir = null;
/*     */       try
/*     */       {
/* 239 */         InputStream in = file.getInputStream();
/* 240 */         dir = request.getServletContext().getRealPath("/version");
/* 241 */         File fileLocation = new File(dir);
/*     */ 
/* 243 */         if (!fileLocation.exists()) {
/* 244 */           boolean isCreated = fileLocation.mkdir();
/* 245 */           if (!isCreated)
/*     */           {
/* 247 */             model.addAttribute("msg", "权限不足!");
/* 248 */             model.addAttribute("entity", e);
/* 249 */             return "portalweb/save";
/*     */           }
/*     */         }
/*     */ 
/* 253 */         File uploadFile = new File(dir, sourceFile);
/*     */ 
/* 255 */         FileUtils.copyInputStreamToFile(in, uploadFile);
/* 256 */         in.close();
/*     */       } catch (Exception ex) {
/* 258 */         model.addAttribute("msg", "上传出错!");
/* 259 */         File df = new File(dir, sourceFile);
/* 260 */         if (df.exists()) {
/* 261 */           ReportService.deleteAll(df);
/*     */         }
/* 263 */         model.addAttribute("entity", e);
/* 264 */         return "portalweb/save";
/*     */       }
/*     */       try
/*     */       {
/* 268 */         String descDir = request.getServletContext().getRealPath("/");
/* 269 */         descDir = descDir + "/" + id + "/";
/* 270 */         File idpath = new File(descDir);
/*     */ 
/* 272 */         if (!idpath.exists()) {
/* 273 */           boolean isCreated = idpath.mkdir();
/* 274 */           if (!isCreated)
/*     */           {
/* 276 */             model.addAttribute("msg", "权限不足!");
/* 277 */             model.addAttribute("entity", e);
/* 278 */             return "portalweb/save";
/*     */           }
/*     */         }
/* 281 */         sourceFile = dir + "/" + sourceFile;
/* 282 */         DiyUtils.unZip(descDir, sourceFile);
/*     */       }
/*     */       catch (Exception ex) {
/* 285 */         model.addAttribute("msg", "解压出错!");
/* 286 */         File df = new File(dir);
/* 287 */         if (df.exists()) {
/* 288 */           ReportService.deleteAll(df);
/*     */         }
/* 290 */         model.addAttribute("entity", e);
/* 291 */         return "portalweb/save";
/*     */       }
/*     */ 
/* 294 */       File df = new File(dir);
/* 295 */       if (df.exists()) {
/* 296 */         ReportService.deleteAll(df);
/*     */       }
/*     */     }
/*     */ 
/* 300 */     model.addAttribute("msg", "更新成功!");
/*     */ 
/* 302 */     return "redirect:/portalweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalweb/delete.action"})
/*     */   public String delete(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 308 */     this.portalwebService.deleteByKey(id);
/* 309 */     String dir = request.getServletContext().getRealPath("/" + id + "/");
/* 310 */     File df = new File(dir);
/* 311 */     if (df.exists()) {
/* 312 */       ReportService.deleteAll(df);
/*     */     }
/* 314 */     return "redirect:/portalweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalweb/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
/*     */   {
/* 321 */     List<Long> list = Arrays.asList(ids);
/* 322 */     this.portalwebService.deleteByKeys(list);
/* 323 */     for (Long id : list) {
/* 324 */       String dir = request.getServletContext()
/* 325 */         .getRealPath("/" + id + "/");
/* 326 */       File df = new File(dir);
/* 327 */       if (df.exists()) {
/* 328 */         ReportService.deleteAll(df);
/*     */       }
/*     */     }
/*     */ 
/* 332 */     return "redirect:/portalweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalweb/down.action"})
/*     */   public String down(@RequestParam Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try
/*     */     {
/* 341 */       String descDir = request.getServletContext().getRealPath("/");
/* 342 */       String idS = String.valueOf(id);
/* 343 */       String path = DiyUtils.Zip(descDir, idS);
/* 344 */       File file = new File(path);
/*     */ 
/* 346 */       response.reset();
/* 347 */       response.setHeader("Content-Disposition", "attachment; filename=\"" + 
/* 348 */         file.getName() + "\"");
/* 349 */       response.addHeader("Content-Length", file.length()+"");
/* 350 */       response.setContentType("application/octet-stream;charset=UTF-8");
/*     */ 
/* 352 */       InputStream fis = new BufferedInputStream(new FileInputStream(path));
/* 353 */       byte[] buffer = new byte[fis.available()];
/* 354 */       fis.read(buffer);
/* 355 */       fis.close();
/* 356 */       OutputStream outputStream = new BufferedOutputStream(
/* 357 */         response.getOutputStream());
/* 358 */       outputStream.write(buffer);
/* 359 */       outputStream.flush();
/* 360 */       outputStream.close();
/*     */     } catch (IOException localIOException) {
/*     */     }
/* 363 */     return "redirect:/portalweb/list.action";
/*     */   }
/*     */ 
/*     */   public static boolean Do()
/*     */   {
/* 369 */     Long isThis = Long.valueOf(new Date().getTime());
/* 370 */     boolean Do = false;
/* 371 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 372 */       Do = true;
/*     */     }
/* 374 */     return Do;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalwebController
 * JD-Core Version:    0.6.2
 */