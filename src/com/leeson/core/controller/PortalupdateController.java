/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import AC.Server.ACServer;
/*     */ import com.leeson.common.utils.MySQLUtils;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.core.utils.UpdateOnlineRequest;
/*     */ import com.leeson.portal.core.listener.ReportService;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.service.ReportServer;
/*     */ import com.leeson.radius.core.RadiusMain;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class PortalupdateController
/*     */ {
/*  54 */   private static Config config = Config.getInstance();
/*     */ 
/*  56 */   private static Logger logger = Logger.getLogger(PortalupdateController.class);
/*  57 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*     */ 
/*     */   @RequestMapping({"/portalupdate/version.action"})
/*     */   public String in(ModelMap model, HttpServletRequest request)
/*     */   {
/*  62 */     String version = ReadVersionInfo(request);
/*  63 */     model.addAttribute("version", version);
/*     */ 
/*  65 */     String versionLocal = ReadVersionLocalInfo(request);
/*  66 */     if (stringUtils.isNotBlank(versionLocal)) {
/*  67 */       String[] getV = UpdateOnlineRequest.send(versionLocal);
/*  68 */       if ((getV != null) && (getV.length == 2)) {
/*  69 */         String v = getV[0];
/*  70 */         String vInfo = getV[1];
/*  71 */         model.addAttribute("versionOnlne", vInfo);
/*  72 */         model.addAttribute("v", v);
/*     */       } else {
/*  74 */         model.addAttribute("versionOnlne", null);
/*     */       }
/*     */     } else {
/*  77 */       model.addAttribute("versionOnlne", null);
/*     */     }
/*  79 */     return "portalupdate/version";
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/portalupdate/update"})
/*     */   public Map<String, String> updateOnline(String v, ModelMap model, HttpServletRequest request)
/*     */   {
/*  87 */     Map map = new HashMap();
/*  88 */     String dir = request.getServletContext().getRealPath("/version");
/*  89 */     Date now = new Date();
/*  90 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/*  91 */     String nowString = format.format(now);
/*  92 */     File fileLocation = new File(dir);
/*     */ 
/*  94 */     if (!fileLocation.exists()) {
/*  95 */       boolean isCreated = fileLocation.mkdir();
/*  96 */       if (!isCreated)
/*     */       {
/*  98 */         map.put("ret", "1");
/*  99 */         map.put("msg", "权限不足！！");
/* 100 */         return map;
/*     */       }
/*     */     }
/* 103 */     dir = request.getServletContext().getRealPath("/version");
/* 104 */     String localFile = dir + "/" + nowString + ".patch";
/*     */ 
/* 106 */     kicks();
/* 107 */     ReportServer.closeServer();
/* 108 */     ACServer.closeServer();
/* 109 */     RadiusMain.stop(1);
/*     */ 
/* 111 */     Boolean state = 
/* 112 */       Boolean.valueOf(UpdateOnlineRequest.updateService(v, localFile, request));
/* 113 */     if (!state.booleanValue()) {
/* 114 */       map.put("ret", "1");
/* 115 */       map.put("msg", "升级失败,请等待服务重启！！");
/* 116 */       return map;
/*     */     }
/* 118 */     map.put("ret", "0");
/* 119 */     map.put("msg", "升级成功,请等待服务重启！！");
/* 120 */     return map;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalupdate/backupSQL.action"})
/*     */   public String backupSQL(ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try
/*     */     {
/* 129 */       String descDir = request.getServletContext().getRealPath("/");
/* 130 */       Properties props = new Properties();
/* 131 */       String propPath = descDir + 
/* 132 */         "WEB-INF/classes/properties/jdbc.properties";
/*     */ 
/* 134 */       FileInputStream in = new FileInputStream(propPath);
/* 135 */       props.load(in);
/* 136 */       String username = props.getProperty("jdbc1.username");
/* 137 */       String password = props.getProperty("jdbc1.password");
/*     */ 
/* 139 */       in.close();
/* 140 */       String versionPath = descDir + "version/";
/*     */ 
/* 142 */       File isH = new File(versionPath);
/* 143 */       if (!isH.exists()) {
/* 144 */         isH.mkdir();
/*     */       }
/*     */ 
/* 147 */       SimpleDateFormat format = new SimpleDateFormat(
/* 148 */         "yyyy-MM-dd-HH-mm-ss");
/* 149 */       String nowString = format.format(new Date());
/* 150 */       String path = versionPath + nowString + ".dat";
/*     */ 
/* 152 */       if (MySQLUtils.backup(username, password, "openportalserver", path)) {
/* 153 */         File file = new File(path);
/*     */ 
/* 155 */         response.reset();
/* 156 */         response.setHeader("Content-Disposition", 
/* 157 */           "attachment; filename=\"" + file.getName() + "\"");
/* 158 */         response.addHeader("Content-Length", file.length()+"");
/* 159 */         response.setContentType("application/octet-stream;charset=UTF-8");
/*     */ 
/* 161 */         InputStream fis = new BufferedInputStream(new FileInputStream(
/* 162 */           path));
/* 163 */         byte[] buffer = new byte[fis.available()];
/* 164 */         fis.read(buffer);
/* 165 */         fis.close();
/* 166 */         OutputStream outputStream = new BufferedOutputStream(
/* 167 */           response.getOutputStream());
/* 168 */         outputStream.write(buffer);
/* 169 */         outputStream.flush();
/* 170 */         outputStream.close();
/*     */       } else {
/* 172 */         model.addAttribute("msg", "备份数据库出错!");
/* 173 */         model.addAttribute("err", Integer.valueOf(0));
/* 174 */         return "portalupdate/version";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 178 */       logger.error("backupSQL", e);
/* 179 */       model.addAttribute("msg", "备份数据库出错!");
/* 180 */       model.addAttribute("err", Integer.valueOf(0));
/* 181 */       return "portalupdate/version";
/*     */     }
/*     */ 
/* 184 */     String version = ReadVersionInfo(request);
/* 185 */     model.addAttribute("version", version);
/* 186 */     return "portalupdate/version";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalupdate/restoreSQL.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String restoreSQL(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
/*     */   {
/* 195 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/* 196 */     if (!ext.equals("dat")) {
/* 197 */       model.addAttribute("msg", "文件格式错误!");
/* 198 */       model.addAttribute("err", Integer.valueOf(0));
/* 199 */       return "portalupdate/version";
/*     */     }
/*     */ 
/* 202 */     String dir = null;
/* 203 */     String nowString = null;
/* 204 */     String tempPath = "";
/*     */     try {
/* 206 */       InputStream in = file.getInputStream();
/* 207 */       dir = request.getServletContext().getRealPath("/version");
/* 208 */       File fileLocation = new File(dir);
/*     */ 
/* 210 */       if (!fileLocation.exists()) {
/* 211 */         boolean isCreated = fileLocation.mkdir();
/* 212 */         if (!isCreated)
/*     */         {
/* 214 */           model.addAttribute("msg", "权限不足!");
/* 215 */           model.addAttribute("err", Integer.valueOf(0));
/* 216 */           return "portalupdate/version";
/*     */         }
/*     */       }
/* 219 */       dir = request.getServletContext().getRealPath("/version");
/*     */ 
/* 221 */       Date now = new Date();
/* 222 */       SimpleDateFormat format = new SimpleDateFormat(
/* 223 */         "yyyy-MM-dd-HH-mm-ss");
/* 224 */       nowString = format.format(now);
/*     */ 
/* 226 */       File uploadFile = new File(dir, nowString + ".dat");
/*     */ 
/* 228 */       FileUtils.copyInputStreamToFile(in, uploadFile);
/* 229 */       in.close();
/*     */     } catch (Exception ex) {
/* 231 */       model.addAttribute("msg", "传送文件异常!");
/* 232 */       model.addAttribute("err", Integer.valueOf(0));
/* 233 */       File df = new File(dir);
/* 234 */       if (df.exists()) {
/* 235 */         ReportService.deleteAll(df);
/*     */       }
/* 237 */       return "portalupdate/version";
/*     */     }
/*     */     try
/*     */     {
/* 241 */       String descDir = request.getServletContext().getRealPath("/");
/* 242 */       String sys = System.getProperty("os.name");
/* 243 */       if (sys.startsWith("W")) {
/* 244 */         String unZipPath = descDir.replace("ROOT\\", "");
/* 245 */         tempPath = unZipPath.replace("webapps\\", "");
/* 246 */       } else if (sys.startsWith("L")) {
/* 247 */         String unZipPath = descDir.replace("ROOT/", "");
/* 248 */         tempPath = unZipPath.replace("webapps/", "");
/*     */       } else {
/* 250 */         model.addAttribute("msg", "获取OS信息失败!");
/* 251 */         model.addAttribute("err", Integer.valueOf(0));
/* 252 */         File df = new File(dir);
/* 253 */         if (df.exists()) {
/* 254 */           ReportService.deleteAll(df);
/*     */         }
/* 256 */         return "portalupdate/version";
/*     */       }
/*     */ 
/* 259 */       tempPath = tempPath + "temp/";
/*     */ 
/* 261 */       Properties props = new Properties();
/* 262 */       String propPath = descDir + 
/* 263 */         "WEB-INF/classes/properties/jdbc.properties";
/* 264 */       FileInputStream in = new FileInputStream(propPath);
/* 265 */       props.load(in);
/* 266 */       String username = props.getProperty("jdbc1.username");
/* 267 */       String password = props.getProperty("jdbc1.password");
/* 268 */       in.close();
/*     */ 
/* 270 */       String path = dir + "/" + nowString + ".dat";
/*     */ 
/* 272 */       kicks();
/* 273 */       ReportServer.closeServer();
/* 274 */       ACServer.closeServer();
/* 275 */       RadiusMain.stop(1);
/*     */ 
/* 278 */       if (!MySQLUtils.restore(username, password, "openportalserver", 
/* 278 */         path)) {
/* 279 */         model.addAttribute("msg", "数据库还原错误!");
/* 280 */         model.addAttribute("err", Integer.valueOf(0));
/* 281 */         File df = new File(dir);
/* 282 */         if (df.exists()) {
/* 283 */           ReportService.deleteAll(df);
/*     */         }
/* 285 */         return "portalupdate/version";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 289 */       model.addAttribute("msg", "数据库还原错误!");
/* 290 */       model.addAttribute("err", Integer.valueOf(0));
/* 291 */       File df = new File(dir);
/* 292 */       if (df.exists()) {
/* 293 */         ReportService.deleteAll(df);
/*     */       }
/* 295 */       return "portalupdate/version";
/*     */     }
/*     */ 
/* 298 */     File df = new File(dir);
/* 299 */     if (df.exists()) {
/* 300 */       ReportService.deleteAll(df);
/*     */     }
/* 302 */     File tempFile = new File(tempPath);
/* 303 */     if (tempFile.exists()) {
/* 304 */       ReportService.deleteAll(tempFile);
/*     */     }
/*     */ 
/* 307 */     dir = request.getServletContext().getRealPath("/version");
/* 308 */     File fileLocation = new File(dir);
/*     */ 
/* 310 */     if (!fileLocation.exists()) {
/* 311 */       boolean isCreated = fileLocation.mkdir();
/* 312 */       if (!isCreated)
/*     */       {
/* 314 */         model.addAttribute("msg", "权限不足!");
/* 315 */         model.addAttribute("err", Integer.valueOf(0));
/* 316 */         return "portalupdate/version";
/*     */       }
/*     */     }
/* 319 */     dir = request.getServletContext().getRealPath("/version");
/* 320 */     UpdateOnlineRequest.restartService(dir, request);
/*     */ 
/* 322 */     String version = ReadVersionInfo(request);
/* 323 */     model.addAttribute("msg", "数据库还原成功,请等待服务重启！！");
/* 324 */     model.addAttribute("version", version);
/* 325 */     model.addAttribute("err", Integer.valueOf(1));
/* 326 */     return "portalupdate/version";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalupdate/clear.action"})
/*     */   public String clear(ModelMap model, HttpServletRequest request)
/*     */   {
/* 332 */     String rootPath = request.getServletContext().getRealPath("/");
/* 333 */     String sys = System.getProperty("os.name");
/* 334 */     if (sys.startsWith("W")) {
/* 335 */       rootPath = rootPath.replace("ROOT\\", "");
/* 336 */       rootPath = rootPath.replace("webapps\\", "");
/* 337 */     } else if (sys.startsWith("L")) {
/* 338 */       rootPath = rootPath.replace("ROOT/", "");
/* 339 */       rootPath = rootPath.replace("webapps/", "");
/*     */     } else {
/* 341 */       model.addAttribute("msg", "获取OS信息失败!");
/* 342 */       model.addAttribute("err", Integer.valueOf(0));
/* 343 */       return "portalupdate/version";
/*     */     }
/*     */ 
/* 347 */     String fastworkPath = rootPath + "fastwork/";
/* 348 */     String logsPath = rootPath + "logs/";
/*     */ 
/* 350 */     String versionPath = request.getServletContext()
/* 351 */       .getRealPath("/version");
/*     */ 
/* 360 */     File file = new File(fastworkPath);
/* 361 */     if (file.exists()) {
/* 362 */       ReportService.deleteAll(file);
/*     */     }
/* 364 */     if (!file.exists()) {
/* 365 */       file.mkdir();
/*     */     }
/* 367 */     file = new File(logsPath);
/* 368 */     if (file.exists()) {
/* 369 */       ReportService.deleteAll(file);
/*     */     }
/* 371 */     if (!file.exists()) {
/* 372 */       file.mkdir();
/*     */     }
/*     */ 
/* 381 */     file = new File(versionPath);
/* 382 */     if (file.exists()) {
/* 383 */       ReportService.deleteAll(file);
/*     */     }
/* 385 */     if (!file.exists()) {
/* 386 */       file.mkdir();
/*     */     }
/*     */ 
/* 389 */     String version = ReadVersionInfo(request);
/* 390 */     model.addAttribute("msg", "缓存清除成功!  ");
/* 391 */     model.addAttribute("version", version);
/* 392 */     model.addAttribute("err", Integer.valueOf(1));
/* 393 */     return "portalupdate/version";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalupdate/update.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String update(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
/*     */   {
/* 401 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/* 402 */     if (!ext.equals("patch")) {
/* 403 */       model.addAttribute("msg", "文件格式错误!");
/* 404 */       model.addAttribute("err", Integer.valueOf(0));
/* 405 */       return "portalupdate/version";
/*     */     }
/*     */ 
/* 408 */     String dir = null;
/* 409 */     String nowString = null;
/*     */     try {
/* 411 */       InputStream in = file.getInputStream();
/* 412 */       dir = request.getServletContext().getRealPath("/version");
/* 413 */       File fileLocation = new File(dir);
/*     */ 
/* 415 */       if (!fileLocation.exists()) {
/* 416 */         boolean isCreated = fileLocation.mkdir();
/* 417 */         if (!isCreated)
/*     */         {
/* 419 */           model.addAttribute("msg", "权限不足!");
/* 420 */           model.addAttribute("err", Integer.valueOf(0));
/* 421 */           return "portalupdate/version";
/*     */         }
/*     */       }
/* 424 */       dir = request.getServletContext().getRealPath("/version");
/*     */ 
/* 426 */       Date now = new Date();
/* 427 */       SimpleDateFormat format = new SimpleDateFormat(
/* 428 */         "yyyy-MM-dd-HH-mm-ss");
/* 429 */       nowString = format.format(now);
/*     */ 
/* 431 */       File uploadFile = new File(dir, nowString + ".patch");
/*     */ 
/* 433 */       FileUtils.copyInputStreamToFile(in, uploadFile);
/* 434 */       in.close();
/*     */     } catch (Exception ex) {
/* 436 */       model.addAttribute("msg", "传送文件异常!");
/* 437 */       model.addAttribute("err", Integer.valueOf(0));
/* 438 */       File df = new File(dir);
/* 439 */       if (df.exists()) {
/* 440 */         ReportService.deleteAll(df);
/*     */       }
/* 442 */       return "portalupdate/version";
/*     */     }
/* 444 */     String localFile = dir + "/" + nowString + ".patch";
/*     */ 
/* 446 */     kicks();
/* 447 */     ReportServer.closeServer();
/* 448 */     ACServer.closeServer();
/* 449 */     RadiusMain.stop(1);
/*     */ 
/* 451 */     Boolean state = Boolean.valueOf(UpdateOnlineRequest.updateService(localFile, request));
/* 452 */     if (!state.booleanValue()) {
/* 453 */       model.addAttribute("msg", "升级失败,请等待服务重启！！");
/* 454 */       model.addAttribute("err", Integer.valueOf(0));
/* 455 */       return "portalupdate/version";
/*     */     }
/* 457 */     String version = ReadVersionInfo(request);
/* 458 */     model.addAttribute("msg", "升级成功,请等待服务重启！！");
/* 459 */     model.addAttribute("version", version);
/* 460 */     model.addAttribute("err", Integer.valueOf(1));
/* 461 */     return "portalupdate/version";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalupdate/restart.action"})
/*     */   public String restart(ModelMap model, HttpServletRequest request)
/*     */   {
/* 470 */     String dir = request.getServletContext().getRealPath("/version");
/* 471 */     File fileLocation = new File(dir);
/*     */ 
/* 473 */     if (!fileLocation.exists()) {
/* 474 */       boolean isCreated = fileLocation.mkdir();
/* 475 */       if (!isCreated)
/*     */       {
/* 477 */         model.addAttribute("msg", "权限不足!");
/* 478 */         model.addAttribute("err", Integer.valueOf(0));
/* 479 */         return "portalupdate/version";
/*     */       }
/*     */     }
/* 482 */     dir = request.getServletContext().getRealPath("/version");
/*     */ 
/* 484 */     kicks();
/* 485 */     ReportServer.closeServer();
/* 486 */     ACServer.closeServer();
/* 487 */     RadiusMain.stop(1);
/*     */ 
/* 489 */     Boolean state = Boolean.valueOf(UpdateOnlineRequest.restartService(dir, request));
/*     */ 
/* 491 */     String version = ReadVersionInfo(request);
/* 492 */     model.addAttribute("version", version);
/* 493 */     String versionLocal = ReadVersionLocalInfo(request);
/* 494 */     if (stringUtils.isNotBlank(versionLocal)) {
/* 495 */       String[] getV = UpdateOnlineRequest.send(versionLocal);
/* 496 */       if ((getV != null) && (getV.length == 2)) {
/* 497 */         String v = getV[0];
/* 498 */         String vInfo = getV[1];
/* 499 */         model.addAttribute("versionOnlne", vInfo);
/* 500 */         model.addAttribute("v", v);
/*     */       } else {
/* 502 */         model.addAttribute("versionOnlne", null);
/*     */       }
/*     */     } else {
/* 505 */       model.addAttribute("versionOnlne", null);
/*     */     }
/* 507 */     if (state.booleanValue())
/* 508 */       model.addAttribute("msg", "请等待服务重启！！");
/*     */     else {
/* 510 */       model.addAttribute("msg", "请求服务重启失败！！");
/*     */     }
/* 512 */     model.addAttribute("err", Integer.valueOf(1));
/* 513 */     return "portalupdate/version";
/*     */   }
/*     */ 
/*     */   private void kicks() {
/* 517 */     HashMap OnlineUserMap = new HashMap();
/* 518 */     OnlineUserMap.putAll(onlineMap.getOnlineUserMap());
/* 519 */     Iterator iterator = OnlineUserMap.keySet().iterator();
/* 520 */     while (iterator.hasNext()) {
/* 521 */       Object o = iterator.next();
/* 522 */       String host = o.toString();
/* 523 */       Kick.kickUserUpdate(host);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String ReadVersionInfo(HttpServletRequest request)
/*     */   {
/* 529 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 530 */     String filePath = request.getServletContext().getRealPath("/") + 
/* 531 */       "/info.txt";
/* 532 */     String msg = "";
/*     */     try {
/* 534 */       String encoding = "utf-8";
/* 535 */       File file = new File(filePath);
/* 536 */       if ((file.isFile()) && (file.exists())) {
/* 537 */         InputStreamReader read = new InputStreamReader(
/* 538 */           new FileInputStream(file), encoding);
/* 539 */         BufferedReader bufferedReader = new BufferedReader(read);
/* 540 */         String lineTxt = null;
/*     */ 
/* 542 */         while ((lineTxt = bufferedReader.readLine()) != null) {
/* 543 */           msg = msg + lineTxt;
/*     */         }
/* 545 */         read.close();
/*     */       }
/* 547 */       else if (basConfig.getIsdebug().equals("1")) {
/* 548 */         logger.info("版本信息文件info.txt不存在!");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 552 */       if (basConfig.getIsdebug().equals("1")) {
/* 553 */         logger.info("读取版本信息文件info.txt出错!");
/*     */       }
/*     */     }
/* 556 */     return msg;
/*     */   }
/*     */ 
/*     */   private String ReadVersionLocalInfo(HttpServletRequest request) {
/* 560 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 561 */     String filePath = request.getServletContext().getRealPath("/WEB-INF") + 
/* 562 */       "/v.version";
/* 563 */     String msg = "";
/*     */     try {
/* 565 */       String encoding = "utf-8";
/* 566 */       File file = new File(filePath);
/* 567 */       if ((file.isFile()) && (file.exists())) {
/* 568 */         InputStreamReader read = new InputStreamReader(
/* 569 */           new FileInputStream(file), encoding);
/* 570 */         BufferedReader bufferedReader = new BufferedReader(read);
/* 571 */         String lineTxt = null;
/*     */ 
/* 573 */         while ((lineTxt = bufferedReader.readLine()) != null) {
/* 574 */           msg = msg + lineTxt;
/*     */         }
/* 576 */         read.close();
/*     */       }
/* 578 */       else if (basConfig.getIsdebug().equals("1")) {
/* 579 */         logger.info("版本信息文件v.version不存在!");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 583 */       if (basConfig.getIsdebug().equals("1")) {
/* 584 */         logger.info("读取版本信息文件v.version出错!");
/*     */       }
/*     */     }
/* 587 */     return msg.trim();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalupdateController
 * JD-Core Version:    0.6.2
 */