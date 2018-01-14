/*     */ package com.leeson.portal.core.listener;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalconfig;
/*     */ import com.leeson.core.bean.Zsqhdapi;
/*     */ import com.leeson.core.query.PortalbasQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalbasService;
/*     */ import com.leeson.core.service.PortalconfigService;
/*     */ import com.leeson.core.service.ZsqhdapiService;
/*     */ import com.leeson.portal.core.model.AccountAPIMap;
/*     */ import com.leeson.portal.core.model.CoreConfigMap;
/*     */ import com.leeson.portal.core.model.RadiusAPIMap;
/*     */ import com.leeson.portal.core.model.SangforAPIMap;
/*     */ import com.leeson.portal.core.model.WySlot15gasa;
/*     */ import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
/*     */ import com.leeson.portal.core.model.ipMap;
/*     */ import com.leeson.portal.core.model.isDo;
/*     */ import com.leeson.portal.core.service.ReportServer;
/*     */ import com.leeson.portal.core.utils.BoemXwfltxQ41gbgpEPru9p7Tnp;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ReportService
/*     */   implements ServletContextListener
/*     */ {
/*  50 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*     */ 
/*  52 */   private static Logger log = Logger.getLogger(ReportService.class);
/*     */ 
/*  54 */   private static Timer timerUnLockIPService = new Timer();
/*     */ 
/*     */   public void contextDestroyed(ServletContextEvent servletContextEvent)
/*     */   {
/*  58 */     timerUnLockIPService.cancel();
/*  59 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/*  60 */       log.info("PortalServer Stop!!");
/*     */   }
/*     */ 
/*     */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*     */   {
/*  67 */     GetConfigFromDisk(servletContextEvent);
/*  68 */     SangforAPIMapFromDisk(servletContextEvent);
/*  69 */     AccountAPIMapFromDisk(servletContextEvent);
/*  70 */     RadiusAPIMapFromDisk(servletContextEvent);
/*     */ 
/*  72 */     Read(servletContextEvent);
/*     */ 
/*  74 */     godo();
/*     */ 
/*  76 */     InitConfig4DB(servletContextEvent);
/*     */ 
/*  78 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/*  83 */           ReportServer.openServer();
/*     */         }
/*     */         catch (Exception e) {
/*  86 */           ReportService.log.error("PortalServer Start Error ！");
/*  87 */           ReportService.log.error("==============ERROR Start=============");
/*  88 */           ReportService.log.error(e);
/*  89 */           ReportService.log.error("ERROR INFO ", e);
/*  90 */           ReportService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/*  94 */     .start();
/*     */ 
/*  97 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 102 */           ReportService.this.UnLockIPService();
/*     */         }
/*     */         catch (Exception e) {
/* 105 */           ReportService.log.error("UnLock IP-Lock Service Start Error ！");
/* 106 */           ReportService.log.error("==============ERROR Start=============");
/* 107 */           ReportService.log.error(e);
/* 108 */           ReportService.log.error("ERROR INFO ", e);
/* 109 */           ReportService.log.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/* 113 */     .start();
/*     */     try
/*     */     {
/* 116 */       String path_out = servletContextEvent.getServletContext()
/* 117 */         .getRealPath("/") + "ExcelOut/";
/* 118 */       final File file_out = new File(path_out);
/* 119 */       System.out.println("PortalServer Start Format Temp Files!!");
/* 120 */       if (file_out.exists())
/*     */       {
/* 122 */         new Thread()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try {
/* 127 */               ReportService.deleteAll(file_out);
/* 128 */               file_out.mkdir();
/*     */             }
/*     */             catch (Exception e) {
/* 131 */               ReportService.log.error("==============ERROR Start=============");
/* 132 */               ReportService.log.error(e);
/* 133 */               ReportService.log.error("ERROR INFO ", e);
/* 134 */               ReportService.log.error("==============ERROR End=============");
/*     */             }
/*     */           }
/*     */         }
/* 138 */         .start();
/*     */       }
/*     */       else
/* 141 */         file_out.mkdir();
/*     */     }
/*     */     catch (Exception e) {
/* 144 */       log.error("==============ERROR Start=============");
/* 145 */       log.error(e);
/* 146 */       log.error("ERROR INFO ", e);
/* 147 */       log.error("==============ERROR End=============");
/*     */     }
/*     */     try
/*     */     {
/* 151 */       String path_v = servletContextEvent.getServletContext()
/* 152 */         .getRealPath("/") + "version/";
/* 153 */       final File file_v = new File(path_v);
/* 154 */       System.out.println("PortalServer Start Format Version Files!!");
/* 155 */       if (file_v.exists())
/*     */       {
/* 157 */         new Thread()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try {
/* 162 */               ReportService.deleteAll(file_v);
/* 163 */               file_v.mkdir();
/*     */             }
/*     */             catch (Exception e) {
/* 166 */               ReportService.log.error("==============ERROR Start=============");
/* 167 */               ReportService.log.error(e);
/* 168 */               ReportService.log.error("ERROR INFO ", e);
/* 169 */               ReportService.log.error("==============ERROR End=============");
/*     */             }
/*     */           }
/*     */         }
/* 173 */         .start();
/*     */       }
/*     */       else
/* 176 */         file_v.mkdir();
/*     */     }
/*     */     catch (Exception e) {
/* 179 */       log.error("==============ERROR Start=============");
/* 180 */       log.error(e);
/* 181 */       log.error("ERROR INFO ", e);
/* 182 */       log.error("==============ERROR End=============");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void UnLockIPService()
/*     */   {
/* 188 */     TimerTask taskUnLockIP = new TimerTask()
/*     */     {
/*     */       public void run() {
/* 191 */         Portalbas basConfig = (Portalbas)ReportService.config.getConfigMap().get("");
/* 192 */         if (basConfig.getIsdebug().equals("1")) {
/* 193 */           ReportService.log.info("Start UnLock IP-Lock List Service !!");
/*     */         }
/* 195 */         ipMap.getInstance().getIpmap().clear();
/*     */       }
/*     */     };
/* 199 */     long delay = 20000L;
/* 200 */     long intevalPeriod = 30000L;
/*     */ 
/* 202 */     timerUnLockIPService.scheduleAtFixedRate(taskUnLockIP, delay, intevalPeriod);
/*     */   }
/*     */ 
/*     */   private void Read(ServletContextEvent servletContextEvent) {
/* 206 */     String filePath = servletContextEvent.getServletContext()
/* 207 */       .getRealPath("/") + 
/* 208 */       "/license.lic";
/*     */     try {
/* 210 */       String encoding = "utf-8";
/* 211 */       File file = new File(filePath);
/* 212 */       if ((file.isFile()) && (file.exists())) {
/* 213 */         InputStreamReader read = new InputStreamReader(
/* 214 */           new FileInputStream(file), encoding);
/* 215 */         BufferedReader bufferedReader = new BufferedReader(read);
/* 216 */         String lineTxt = null;
/* 217 */         String msg = "";
/* 218 */         while ((lineTxt = bufferedReader.readLine()) != null) {
/* 219 */           msg = msg + lineTxt.trim();
/*     */         }
/* 221 */         read.close();
/* 222 */         if (stringUtils.isNotBlank(msg))
/* 223 */           BoemXwfltxQ41gbgpEPru9p7Tnp.XZluueWcHZVOWoHedyv(msg);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 227 */       log.error("==============ERROR Start=============");
/* 228 */       log.error(e);
/* 229 */       log.error("ERROR INFO ", e);
/* 230 */       log.error("==============ERROR End=============");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void InitConfig4DB(ServletContextEvent servletContextEvent)
/*     */   {
/* 236 */     SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/* 237 */     int basCount = 0;
/* 238 */     Date nowDate = new Date();
/* 239 */     String nowString = fs.format(nowDate);
/*     */     try {
/* 241 */       nowDate = fs.parse(nowString);
/*     */     } catch (ParseException e) {
/* 243 */       nowDate = new Date();
/*     */     }
/* 245 */     String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = 
/* 246 */       (String)Wz3ofg0r225avuerr.getInstance()
/* 246 */       .getXr9hk0cvnsx().get("mec");
/* 247 */     if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
/* 248 */       RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
/*     */     }
/* 250 */     String[] MxMzIyRDMzMzAy = 
/* 252 */       (String[])WySlot15gasa.getInstance()
/* 251 */       .getAmkbYQX3eQjuwtnxpbjYgQGZOr()
/* 252 */       .get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
/* 253 */     Boolean notCan = Boolean.valueOf(false);
/* 254 */     if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
/*     */       Date saveDate;
/*     */       try {
/* 258 */         saveDate = fs.parse(toDateString);
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/* 260 */         saveDate = nowDate;
/*     */       }
/* 262 */       if (nowDate.getTime() < saveDate.getTime()) {
/* 263 */         basCount = Integer.valueOf(MxMzIyRDMzMzAy[4]).intValue();
/*     */       }
/*     */       else
/* 266 */         notCan = Boolean.valueOf(true);
/*     */     }
/*     */     else
/*     */     {
/* 270 */       notCan = Boolean.valueOf(true);
/*     */     }
/*     */ 
/* 273 */     Map configMap = config.getConfigMap();
/*     */ 
/* 275 */     PortalconfigService configService = (PortalconfigService)
/* 276 */       SpringContextHelper.getBean("portalconfigServiceImpl");
/* 277 */     ConfigService configAllService = (ConfigService)
/* 278 */       SpringContextHelper.getBean("configServiceImpl");
/* 279 */     String isdebug = String.valueOf(configAllService.getConfigByKey(Long.valueOf(1L))
/* 280 */       .getIsDebug());
/*     */ 
/* 282 */     Portalconfig config = configService.getPortalconfigByKey(Long.valueOf(1L));
/* 283 */     Portalbas cfg = new Portalbas();
/* 284 */     cfg.setBasIp(config.getBasIp());
/* 285 */     cfg.setBasPort(config.getBasPort());
/* 286 */     cfg.setSharedSecret(config.getSharedSecret());
/* 287 */     cfg.setAuthType(config.getAuthType());
/* 288 */     cfg.setTimeoutSec(config.getTimeoutSec());
/* 289 */     cfg.setPortalVer(config.getPortalVer());
/* 290 */     cfg.setBas(config.getBas());
/* 291 */     cfg.setIsOut(config.getIsOut());
/* 292 */     cfg.setIsPortalCheck(config.getIsPortalCheck());
/* 293 */     cfg.setAuthInterface(config.getAuthInterface());
/* 294 */     cfg.setBasUser(config.getBasUser());
/* 295 */     cfg.setBasPwd(config.getBasPwd());
/* 296 */     cfg.setWeb(config.getWeb());
/* 297 */     cfg.setBasname(config.getBasname());
/* 298 */     cfg.setIsComputer(config.getIsComputer());
/* 299 */     cfg.setIsdebug(isdebug);
/* 300 */     cfg.setLateAuth(config.getLateAuth());
/* 301 */     cfg.setLateAuthTime(config.getLateAuthTime());
/* 302 */     cfg.setIsNtf(config.getIsNtf());
/* 303 */     configMap.put("", cfg);
/*     */ 
/* 306 */     configMap.put(config.getBasIp(), cfg);
/*     */ 
/* 309 */     if (cfg.getIsdebug().equals("1")) {
/* 310 */       log.info("Read Config Setting : " + config);
/*     */     }
/*     */ 
/* 313 */     int count = 0;
/* 314 */     PortalbasService basService = (PortalbasService)
/* 315 */       SpringContextHelper.getBean("portalbasServiceImpl");
/* 316 */     List<Portalbas> bs = basService.getPortalbasList(new PortalbasQuery());
/* 317 */     for (Portalbas bas : bs) {
/* 318 */       count++;
/* 319 */       if (notCan.booleanValue()) {
/* 320 */         if (count <= 2) {
/* 321 */           bas.setIsdebug(isdebug);
/* 322 */           configMap.put(bas.getBasIp(), bas);
/*     */         }
/*     */       }
/* 325 */       else if (count <= basCount) {
/* 326 */         bas.setIsdebug(isdebug);
/* 327 */         configMap.put(bas.getBasIp(), bas);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void deleteAll(File file)
/*     */   {
/* 335 */     if ((file.isFile()) || (file.list().length == 0)) {
/* 336 */       file.delete();
/*     */     } else {
/* 338 */       File[] files = file.listFiles();
/* 339 */       for (int i = 0; i < files.length; i++) {
/* 340 */         deleteAll(files[i]);
/* 341 */         files[i].delete();
/*     */       }
/*     */ 
/* 344 */       if (file.exists())
/* 345 */         file.delete();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void GetConfigFromDisk(ServletContextEvent servletContextEvent)
/*     */   {
/* 351 */     String limit = "100";
/* 352 */     String url = "http://www.openportal.com.cn";
/* 353 */     FileInputStream in = null;
/*     */     try {
/* 355 */       String path = servletContextEvent.getServletContext()
/* 356 */         .getRealPath("/") + 
/* 357 */         "/WEB-INF/lib/core.properties";
/* 358 */       Properties props = new Properties();
/* 359 */       in = new FileInputStream(path);
/* 360 */       props.load(in);
/* 361 */       limit = props.getProperty("limit");
/* 362 */       url = props.getProperty("url");
/* 363 */       in.close();
/*     */     } catch (Exception e) {
/* 365 */       limit = "100";
/* 366 */       url = "http://www.openportal.com.cn";
/*     */       try
/*     */       {
/* 369 */         if (in != null)
/* 370 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 369 */         if (in != null)
/* 370 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException1) {
/*     */       }
/*     */     }
/* 375 */     int limitInt = Integer.valueOf(limit).intValue();
/* 376 */     if (limitInt > 100) {
/* 377 */       limit = "100";
/*     */     }
/* 379 */     String[] core = new String[2];
/* 380 */     core[0] = url;
/* 381 */     core[1] = limit;
/* 382 */     CoreConfigMap.getInstance().getCoreConfigMap().put("core", core);
/*     */   }
/*     */ 
/*     */   private void SangforAPIMapFromDisk(ServletContextEvent servletContextEvent) {
/* 386 */     String type = "";
/* 387 */     String url = "";
/* 388 */     String port = "";
/* 389 */     FileInputStream in = null;
/*     */     try {
/* 391 */       String path = servletContextEvent.getServletContext()
/* 392 */         .getRealPath("/") + 
/* 393 */         "/sangfor.properties";
/* 394 */       Properties props = new Properties();
/* 395 */       in = new FileInputStream(path);
/* 396 */       props.load(in);
/* 397 */       type = props.getProperty("type");
/* 398 */       url = props.getProperty("url");
/* 399 */       port = props.getProperty("port");
/* 400 */       in.close();
/*     */     }
/*     */     catch (Exception localException) {
/*     */       try {
/* 404 */         if (in != null)
/* 405 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 404 */         if (in != null)
/* 405 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException1) {
/*     */       }
/*     */     }
/* 410 */     log.info("Sangfor API Read for Files , Url=" + url);
/* 411 */     SangforAPIMap.getInstance().getSangforAPIMap().put("type", type);
/* 412 */     SangforAPIMap.getInstance().getSangforAPIMap().put("url", url);
/* 413 */     SangforAPIMap.getInstance().getSangforAPIMap().put("port", port);
/*     */   }
/*     */ 
/*     */   private void RadiusAPIMapFromDisk(ServletContextEvent servletContextEvent) {
/* 417 */     String state = "";
/* 418 */     String url = "";
/* 419 */     FileInputStream in = null;
/*     */     try {
/* 421 */       String path = servletContextEvent.getServletContext()
/* 422 */         .getRealPath("/") + 
/* 423 */         "/radiusAPI.properties";
/* 424 */       Properties props = new Properties();
/* 425 */       in = new FileInputStream(path);
/* 426 */       props.load(in);
/* 427 */       state = props.getProperty("state");
/* 428 */       url = props.getProperty("url");
/* 429 */       in.close();
/*     */     }
/*     */     catch (Exception localException) {
/*     */       try {
/* 433 */         if (in != null)
/* 434 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 433 */         if (in != null)
/* 434 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException1) {
/*     */       }
/*     */     }
/* 439 */     log.info("Radius API Read for Files , Url=" + url + " , State=" + state);
/* 440 */     RadiusAPIMap.getInstance().getRadiusAPIMap().put("state", state);
/* 441 */     RadiusAPIMap.getInstance().getRadiusAPIMap().put("url", url);
/*     */   }
/*     */ 
/*     */   private void AccountAPIMapFromDisk(ServletContextEvent servletContextEvent) {
/* 445 */     String url = "";
/* 446 */     String state = "";
/* 447 */     String publicurl = "";
/* 448 */     String publicstate = "";
/* 449 */     String autourl = "";
/* 450 */     String autostate = "";
/*     */ 
/* 452 */     ZsqhdapiService zsqhdapiService = (ZsqhdapiService)
/* 453 */       SpringContextHelper.getBean("zsqhdapiServiceImpl");
/*     */ 
/* 455 */     Zsqhdapi api = zsqhdapiService.getZsqhdapiByKey(Long.valueOf(1L));
/* 456 */     if (api != null) {
/* 457 */       url = api.getUrl();
/* 458 */       state = String.valueOf(api.getState());
/* 459 */       publicurl = api.getPublicurl();
/* 460 */       publicstate = String.valueOf(api.getPublicstate());
/* 461 */       autourl = api.getAutourl();
/* 462 */       autostate = String.valueOf(api.getAutostate());
/*     */     }
/*     */ 
/* 465 */     log.info("account API Read for DB , Url=" + url + " State=" + state + 
/* 466 */       " publicurl=" + publicurl + " publicstate=" + publicstate + " autostate=" + autostate + " autourl=" + autourl);
/* 467 */     AccountAPIMap.getInstance().getAccountAPIMap().put("url", url);
/* 468 */     AccountAPIMap.getInstance().getAccountAPIMap().put("state", state);
/* 469 */     AccountAPIMap.getInstance().getAccountAPIMap().put("publicurl", publicurl);
/* 470 */     AccountAPIMap.getInstance().getAccountAPIMap().put("publicstate", publicstate);
/* 471 */     AccountAPIMap.getInstance().getAccountAPIMap().put("autourl", autourl);
/* 472 */     AccountAPIMap.getInstance().getAccountAPIMap().put("autostate", autostate);
/*     */   }
/*     */ 
/*     */   private void AccountAPIMapFromDisk1(ServletContextEvent servletContextEvent) {
/* 476 */     String url = "";
/* 477 */     String state = "";
/* 478 */     String publicurl = "";
/* 479 */     String publicstate = "";
/* 480 */     String autourl = "";
/* 481 */     String autostate = "";
/* 482 */     FileInputStream in = null;
/*     */     try {
/* 484 */       String path = servletContextEvent.getServletContext()
/* 485 */         .getRealPath("/") + 
/* 486 */         "/accountAPI.properties";
/* 487 */       Properties props = new Properties();
/* 488 */       in = new FileInputStream(path);
/* 489 */       props.load(in);
/* 490 */       url = props.getProperty("url");
/* 491 */       state = props.getProperty("state");
/* 492 */       publicurl = props.getProperty("publicurl");
/* 493 */       publicstate = props.getProperty("publicstate");
/* 494 */       autourl = props.getProperty("autourl");
/* 495 */       autostate = props.getProperty("autostate");
/* 496 */       in.close();
/*     */     }
/*     */     catch (Exception localException) {
/*     */       try {
/* 500 */         if (in != null)
/* 501 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 500 */         if (in != null)
/* 501 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException1) {
/*     */       }
/*     */     }
/* 506 */     log.info("account API Read for Files , Url=" + url + " State=" + state + 
/* 507 */       " publicurl=" + publicurl + " publicstate=" + publicstate);
/* 508 */     AccountAPIMap.getInstance().getAccountAPIMap().put("url", url);
/* 509 */     AccountAPIMap.getInstance().getAccountAPIMap().put("state", state);
/* 510 */     AccountAPIMap.getInstance().getAccountAPIMap().put("publicurl", publicurl);
/* 511 */     AccountAPIMap.getInstance().getAccountAPIMap().put("publicstate", publicstate);
/* 512 */     AccountAPIMap.getInstance().getAccountAPIMap().put("autourl", autourl);
/* 513 */     AccountAPIMap.getInstance().getAccountAPIMap().put("autostate", autostate);
/*     */   }
/*     */ 
/*     */   private static void godo() {
/* 517 */     SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/* 518 */     Date nowDate = new Date();
/* 519 */     String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = 
/* 520 */       (String)Wz3ofg0r225avuerr.getInstance()
/* 520 */       .getXr9hk0cvnsx().get("mec");
/* 521 */     if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
/* 522 */       RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
/*     */     }
/* 524 */     String[] MxMzIyRDMzMzAy = 
/* 526 */       (String[])WySlot15gasa.getInstance()
/* 525 */       .getAmkbYQX3eQjuwtnxpbjYgQGZOr()
/* 526 */       .get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
/* 527 */     if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
/*     */       Date saveDate;
/*     */       try {
/* 531 */         saveDate = fs.parse(toDateString);
/*     */       }
/*     */       catch (ParseException err)
/*     */       {
/* 533 */         saveDate = nowDate;
/*     */       }
/* 535 */       if (nowDate.getTime() < saveDate.getTime()) {
/* 536 */         isDo.getInstance().setId(Long.valueOf(saveDate.getTime()));
/* 537 */         String[] core = 
/* 538 */           (String[])CoreConfigMap.getInstance().getCoreConfigMap()
/* 538 */           .get("core");
/* 539 */         core[1] = MxMzIyRDMzMzAy[5];
/* 540 */         CoreConfigMap.getInstance().getCoreConfigMap()
/* 541 */           .put("core", core);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.ReportService
 * JD-Core Version:    0.6.2
 */