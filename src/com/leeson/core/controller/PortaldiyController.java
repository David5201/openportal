/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.utils.DiyUtils;
/*     */ import com.leeson.portal.core.listener.ReportService;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class PortaldiyController
/*     */ {
/*     */   @RequestMapping({"/portaldiy/diy.action"})
/*     */   public String in(ModelMap model, HttpServletRequest request)
/*     */   {
/*  40 */     model.addAttribute("err", Integer.valueOf(2));
/*  41 */     return "portaldiy/diy";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaldiy/download.action"})
/*     */   public String backupSQL(ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try
/*     */     {
/*  50 */       String descDir = request.getServletContext().getRealPath("/");
/*  51 */       String dir = descDir + "/version";
/*  52 */       File fileLocation = new File(dir);
/*     */ 
/*  54 */       if (!fileLocation.exists()) {
/*  55 */         boolean isCreated = fileLocation.mkdir();
/*  56 */         if (!isCreated) {
/*  57 */           model.addAttribute("msg", "发生错误!");
/*  58 */           model.addAttribute("err", Integer.valueOf(1));
/*  59 */           return "portaldiy/diy";
/*     */         }
/*     */       }
/*     */ 
/*  63 */       String path = DiyUtils.Zip(descDir);
/*  64 */       File file = new File(path);
/*     */ 
/*  66 */       response.reset();
/*  67 */       response.setHeader("Content-Disposition", 
/*  68 */         "attachment; filename=\"" + file.getName() + "\"");
/*  69 */       response.addHeader("Content-Length", file.length()+"");
/*  70 */       response.setContentType("application/octet-stream;charset=UTF-8");
/*     */ 
/*  72 */       InputStream fis = new BufferedInputStream(new FileInputStream(
/*  73 */         path));
/*  74 */       byte[] buffer = new byte[fis.available()];
/*  75 */       fis.read(buffer);
/*  76 */       fis.close();
/*  77 */       OutputStream outputStream = new BufferedOutputStream(
/*  78 */         response.getOutputStream());
/*  79 */       outputStream.write(buffer);
/*  80 */       outputStream.flush();
/*  81 */       outputStream.close();
/*     */     }
/*     */     catch (IOException e) {
/*  84 */       model.addAttribute("msg", "发生错误!");
/*  85 */       model.addAttribute("err", Integer.valueOf(1));
/*  86 */       return "portaldiy/diy";
/*     */     }
/*  88 */     model.addAttribute("err", Integer.valueOf(0));
/*  89 */     return "portaldiy/diy";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaldiy/update.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String restoreSQL(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
/*     */   {
/*  99 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/* 100 */     if (!ext.equals("zip")) {
/* 101 */       model.addAttribute("msg", "文件错误!");
/* 102 */       model.addAttribute("err", Integer.valueOf(1));
/* 103 */       return "portaldiy/diy";
/*     */     }
/*     */ 
/* 106 */     Date now = new Date();
/* 107 */     SimpleDateFormat format = new SimpleDateFormat(
/* 108 */       "yyyy-MM-dd-HH-mm-ss");
/* 109 */     String nowString = format.format(now);
/* 110 */     String sourceFile = "web-" + nowString + ".zip";
/* 111 */     String dir = null;
/*     */     try
/*     */     {
/* 114 */       InputStream in = file.getInputStream();
/* 115 */       dir = request.getServletContext().getRealPath("/version");
/* 116 */       File fileLocation = new File(dir);
/*     */ 
/* 118 */       if (!fileLocation.exists()) {
/* 119 */         boolean isCreated = fileLocation.mkdir();
/* 120 */         if (!isCreated)
/*     */         {
/* 122 */           model.addAttribute("msg", "权限不足!");
/* 123 */           model.addAttribute("err", Integer.valueOf(1));
/* 124 */           return "portaldiy/diy";
/*     */         }
/*     */       }
/*     */ 
/* 128 */       File uploadFile = new File(dir, sourceFile);
/*     */ 
/* 130 */       FileUtils.copyInputStreamToFile(in, uploadFile);
/* 131 */       in.close();
/*     */     } catch (Exception ex) {
/* 133 */       model.addAttribute("msg", "上传出错!");
/* 134 */       model.addAttribute("err", Integer.valueOf(1));
/* 135 */       File df = new File(dir);
/* 136 */       if (df.exists()) {
/* 137 */         ReportService.deleteAll(df);
/*     */       }
/* 139 */       return "portaldiy/diy";
/*     */     }
/*     */     try
/*     */     {
/* 143 */       String descDir = request.getServletContext().getRealPath("/");
/* 144 */       sourceFile = dir + "/" + sourceFile;
/* 145 */       DiyUtils.unZip(descDir, sourceFile);
/*     */     }
/*     */     catch (Exception e) {
/* 148 */       model.addAttribute("msg", "更新出错!");
/* 149 */       model.addAttribute("err", Integer.valueOf(1));
/* 150 */       File df = new File(dir);
/* 151 */       if (df.exists()) {
/* 152 */         ReportService.deleteAll(df);
/*     */       }
/* 154 */       return "portaldiy/diy";
/*     */     }
/*     */ 
/* 157 */     File df = new File(dir);
/* 158 */     if (df.exists()) {
/* 159 */       ReportService.deleteAll(df);
/*     */     }
/* 161 */     model.addAttribute("msg", "更新成功!");
/* 162 */     model.addAttribute("err", Integer.valueOf(0));
/* 163 */     return "portaldiy/diy";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaldiyController
 * JD-Core Version:    0.6.2
 */