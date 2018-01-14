/*     */ package com.leeson.radius.core;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import com.leeson.radius.core.model.RadiusNasMap;
/*     */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*     */ import com.leeson.radius.core.utils.COAThread;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class RadiusCheckService
/*     */   implements ServletContextListener
/*     */ {
/*  39 */   private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
/*  40 */   private static Logger logger = Logger.getLogger(RadiusCheckService.class);
/*     */   private static ConfigService configService;
/*     */   private String path;
/*  43 */   private static Timer timerstartCheck = new Timer();
/*     */ 
/*     */   public void contextDestroyed(ServletContextEvent servletContextEvent)
/*     */   {
/*  47 */     timerstartCheck.cancel();
/*  48 */     configService = (ConfigService)
/*  49 */       SpringContextHelper.getBean("configServiceImpl");
/*  50 */     if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue())
/*     */     {
/*  63 */       RadiusOnlineMap.getInstance().getRadiusOnlineMap().clear();
/*     */     }
/*  65 */     RadiusOnlineMapToDisk(servletContextEvent);
/*  66 */     COAThread.offThread();
/*     */   }
/*     */ 
/*     */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*     */   {
/*  71 */     configService = (ConfigService)
/*  72 */       SpringContextHelper.getBean("configServiceImpl");
/*  73 */     if (1 != configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue()) {
/*  74 */       RadiusOnlineMapFromDisk(servletContextEvent);
/*     */     }
/*     */ 
/*  77 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/*  82 */           RadiusCheckService.this.startCheck();
/*  83 */           RadiusCheckService.logger.info("Radius Online Check Servcie Start Success!! 1min Later Start , Every " + 
/*  84 */             RadiusCheckService.configService.getConfigByKey(Long.valueOf(1L)).getCheckTime() + 
/*  85 */             " S Check Once !!");
/*     */         }
/*     */         catch (Exception e) {
/*  88 */           RadiusCheckService.logger.error("Radius Online Check Servcie Start ERROR !!");
/*     */         }
/*     */       }
/*     */     }
/*  92 */     .start();
/*     */ 
/*  94 */     this.path = servletContextEvent.getServletContext().getRealPath("/");
/*  95 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 100 */           RadiusCheckService.this.deleteFiles(RadiusCheckService.this.path);
/*     */         }
/*     */         catch (Exception e) {
/* 103 */           RadiusCheckService.logger.error("==============ERROR Start=============");
/* 104 */           RadiusCheckService.logger.error(e);
/* 105 */           RadiusCheckService.logger.error("ERROR INFO ", e);
/* 106 */           RadiusCheckService.logger.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     }
/* 110 */     .start();
/*     */   }
/*     */ 
/*     */   private void startCheck()
/*     */   {
/* 115 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/* 118 */         Portalbas basConfig = (Portalbas)RadiusCheckService.config.getConfigMap().get("");
/*     */         try {
/* 120 */           if (1 == RadiusCheckService.configService.getConfigByKey(Long.valueOf(1L)).getRadiusOn().intValue()) {
/* 121 */             if (basConfig.getIsdebug().equals("1")) {
/* 122 */               RadiusCheckService.logger.info("Start Check Radius Online User List !!");
/*     */             }
/* 124 */             Iterator iterator = RadiusOnlineMap.getInstance()
/* 125 */               .getRadiusOnlineMap().keySet().iterator();
/* 126 */             while (iterator.hasNext()) {
/* 127 */               Object o = iterator.next();
/* 128 */               String t = o.toString();
/* 129 */               String[] radiusOnlineInfo = 
/* 130 */                 (String[])RadiusOnlineMap.getInstance()
/* 130 */                 .getRadiusOnlineMap().get(t);
/* 131 */               if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0))
/*     */                 try {
/* 133 */                   Date updateTime = ThreadLocalDateUtil.parse(radiusOnlineInfo[14]);
/* 134 */                   String nowString = ThreadLocalDateUtil.format(new Date());
/* 135 */                   Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 136 */                   long costTime = nowTime.getTime() - updateTime.getTime();
/* 137 */                   String[] clients = null;
/* 138 */                   String nasip = radiusOnlineInfo[0];
/* 139 */                   String NasIdentifier = radiusOnlineInfo[16];
/* 140 */                   if (stringUtils.isNotBlank(nasip)) {
/* 141 */                     clients = (String[])RadiusNasMap.getInstance().getNasMap().get(nasip);
/*     */                   }
/*     */ 
/* 152 */                   if ((clients == null) || (clients.length == 0)) {
/* 153 */                     String ip = radiusOnlineInfo[1];
/* 154 */                     if ((stringUtils.isNotBlank(ip)) && 
/* 155 */                       (!ip.equals(nasip))) {
/* 156 */                       Iterator iteratorRM = RadiusNasMap.getInstance().getNasMap().keySet().iterator();
/* 157 */                       while (iteratorRM.hasNext()) {
/* 158 */                         Object orm = iteratorRM.next();
/* 159 */                         String ttm = orm.toString();
/* 160 */                         if (ip.equals(RadiusCheckService.DomainToIP(ttm))) {
/* 161 */                           clients = (String[])RadiusNasMap.getInstance().getNasMap().get(ttm);
/* 162 */                           break;
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/*     */ 
/* 168 */                   if (((clients == null) || (clients.length == 0)) && 
/* 169 */                     (NasIdentifier != null) && (NasIdentifier != "")) {
/* 170 */                     Iterator iteratorNas = RadiusNasMap.getInstance().getNasMap().keySet()
/* 171 */                       .iterator();
/* 172 */                     while (iteratorNas.hasNext()) {
/* 173 */                       Object oNas = iteratorNas.next();
/* 174 */                       String tNas = oNas.toString();
/* 175 */                       String[] temp = (String[])RadiusNasMap.getInstance().getNasMap().get(tNas);
/* 176 */                       if ((temp.length > 0) && (NasIdentifier.equals(temp[7]))) {
/* 177 */                         clients = temp;
/* 178 */                         break;
/*     */                       }
/*     */                     }
/*     */ 
/*     */                   }
/*     */ 
/* 184 */                   if ((clients != null) && (clients.length > 0)) {
/* 185 */                     String timeS = clients[4];
/* 186 */                     if (stringUtils.isNotBlank(timeS)) {
/* 187 */                       long time = Long.valueOf(timeS).longValue();
/* 188 */                       if ((time > 0L) && 
/* 189 */                         (costTime >= time * 1000L))
/* 190 */                         COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Update Check COA");
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 catch (Exception e)
/*     */                 {
/* 196 */                   RadiusCheckService.logger.error("Radius Online Check Servcie ERROR!!");
/* 197 */                   RadiusCheckService.logger.error("==============ERROR Start=============");
/* 198 */                   RadiusCheckService.logger.error(e);
/* 199 */                   RadiusCheckService.logger.error("ERROR INFO ", e);
/* 200 */                   RadiusCheckService.logger.error("==============ERROR End=============");
/*     */                 }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/* 206 */           RadiusCheckService.logger.error("Radius Online Check Servcie ERROR!!");
/* 207 */           RadiusCheckService.logger.error("==============ERROR Start=============");
/* 208 */           RadiusCheckService.logger.error(e);
/* 209 */           RadiusCheckService.logger.error("ERROR INFO ", e);
/* 210 */           RadiusCheckService.logger.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     };
/* 215 */     long delay = 60000L;
/* 216 */     long intevalPeriod = configService.getConfigByKey(Long.valueOf(1L)).getCheckTime().longValue() * 1000L;
/*     */ 
/* 219 */     timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
/*     */   }
/*     */ 
/*     */   private void RadiusOnlineMapToDisk(ServletContextEvent servletContextEvent)
/*     */   {
/* 224 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 225 */     ObjectOutputStream os = null;
/*     */     try {
/* 227 */       os = new ObjectOutputStream(new FileOutputStream(
/* 228 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 229 */         "/RadiusOnlineMap.dat"));
/* 230 */       os.writeObject(RadiusOnlineMap.getInstance().getRadiusOnlineMap());
/* 231 */       if (basConfig.getIsdebug().equals("1"))
/* 232 */         logger.info("RadiusOnlineMapToDisk !!");
/*     */     }
/*     */     catch (Exception e) {
/* 235 */       logger.error("==============ERROR Start=============");
/* 236 */       logger.error(e);
/* 237 */       logger.error("ERROR INFO ", e);
/* 238 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 241 */         if (os != null)
/* 242 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 245 */         logger.error("==============ERROR Start=============");
/* 246 */         logger.error(e1);
/* 247 */         logger.error("ERROR INFO ", e1);
/* 248 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 241 */         if (os != null)
/* 242 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 245 */         logger.error("==============ERROR Start=============");
/* 246 */         logger.error(e);
/* 247 */         logger.error("ERROR INFO ", e);
/* 248 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void RadiusOnlineMapFromDisk(ServletContextEvent servletContextEvent)
/*     */   {
/* 255 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 256 */     ObjectInputStream is = null;
/* 257 */     File parent = new File(servletContextEvent.getServletContext()
/* 258 */       .getRealPath("/") + "/RadiusOnlineMap.dat");
/*     */ 
/* 260 */     label318: 
/*     */     try { if (parent.exists()) {
/* 261 */         is = new ObjectInputStream(new FileInputStream(
/* 262 */           servletContextEvent.getServletContext()
/* 263 */           .getRealPath("/") + 
/* 264 */           "/RadiusOnlineMap.dat"));
/* 265 */         Map radiusOnlineMap = (ConcurrentHashMap)is
/* 266 */           .readObject();
/* 267 */         RadiusOnlineMap.getInstance().setRadiusOnlineMap(radiusOnlineMap);
/* 268 */         if (basConfig.getIsdebug().equals("1")) {
/* 269 */           logger.info("RadiusOnlineMapFromDisk !!");
/*     */ 
/* 272 */           break label318;
/*     */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 274 */         logger.info("RadiusOnlineMap File Not Exists !!");
/*     */       }
/*     */     } catch (Exception e)
/*     */     {
/* 278 */       logger.error("==============ERROR Start=============");
/* 279 */       logger.error(e);
/* 280 */       logger.error("ERROR INFO ", e);
/* 281 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 284 */         if (is != null) {
/* 285 */           is.close();
/*     */         }
/* 287 */         parent = null;
/*     */       } catch (IOException e1) {
/* 289 */         logger.error("==============ERROR Start=============");
/* 290 */         logger.error(e1);
/* 291 */         logger.error("ERROR INFO ", e1);
/* 292 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 284 */         if (is != null) {
/* 285 */           is.close();
/*     */         }
/* 287 */         parent = null;
/*     */       } catch (IOException e) {
/* 289 */         logger.error("==============ERROR Start=============");
/* 290 */         logger.error(e);
/* 291 */         logger.error("ERROR INFO ", e);
/* 292 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deleteFiles(String path) {
/* 298 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 299 */     File file = new File(path + "/RadiusOnlineMap.dat");
/* 300 */     if (file.exists()) {
/* 301 */       file.delete();
/* 302 */       if (basConfig.getIsdebug().equals("1")) {
/* 303 */         logger.info("Server Start Format RadiusOnlineMap File!!");
/*     */       }
/*     */     }
/* 306 */     file = null;
/*     */   }
/*     */ 
/*     */   private static String DomainToIP(String domain) {
/* 310 */     if (configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
/* 311 */       return domain;
/*     */     }
/* 313 */     String ip = "";
/*     */     try {
/* 315 */       ip = java.net.InetAddress.getByName(domain).toString().split("/")[1];
/*     */     } catch (UnknownHostException e) {
/* 317 */       Tool.writeErrorLog("Radius DomainToIP ERROR INFO ", e);
/*     */     }
/* 319 */     System.out.println("Domain:" + domain + " IP:" + ip);
/* 320 */     return ip;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusCheckService
 * JD-Core Version:    0.6.2
 */