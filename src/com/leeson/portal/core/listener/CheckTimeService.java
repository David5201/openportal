/*     */ package com.leeson.portal.core.listener;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalonlinelimit;
/*     */ import com.leeson.core.service.PortalonlinelimitService;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.model.CheckTimeDateMap;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.MacLimitMap;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.UserLimitMap;
/*     */ import com.leeson.portal.core.model.isDo;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
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
/*     */ public class CheckTimeService
/*     */   implements ServletContextListener
/*     */ {
/*  41 */   private static Config config = Config.getInstance();
/*  42 */   private static Logger logger = Logger.getLogger(CheckTimeService.class);
/*     */   private static PortalonlinelimitService onlinelimitService;
/*  45 */   private static Timer timerstartCheck = new Timer();
/*     */ 
/*     */   public void contextDestroyed(ServletContextEvent servletContextEvent)
/*     */   {
/*  49 */     timerstartCheck.cancel();
/*  50 */     CheckTimeDateMapToDisk(servletContextEvent);
/*  51 */     limitUserMapToDisk(servletContextEvent);
/*  52 */     limitMacMapToDisk(servletContextEvent);
/*     */   }
/*     */ 
/*     */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*     */   {
/*  57 */     CheckTimeDateMapFromDisk(servletContextEvent);
/*  58 */     limitUserMapFromDisk(servletContextEvent);
/*  59 */     limitMacMapFromDisk(servletContextEvent);
/*     */ 
/*  61 */     onlinelimitService = (PortalonlinelimitService)
/*  62 */       SpringContextHelper.getBean("portalonlinelimitServiceImpl");
/*     */ 
/*  64 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/*  69 */           CheckTimeService.this.startCheck();
/*  70 */           CheckTimeService.logger.info("One Day Online Time Check Servcie Start Success !!");
/*     */         }
/*     */         catch (Exception e) {
/*  73 */           CheckTimeService.logger.error("One Day Online Time Check Servcie Start ERROR !!");
/*     */         }
/*     */       }
/*     */     }
/*  77 */     .start();
/*     */   }
/*     */ 
/*     */   private void startCheck()
/*     */   {
/*  83 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/*  88 */           Date saveDate = 
/*  89 */             (Date)CheckTimeDateMap.getInstance()
/*  89 */             .getCheckTimeDateMap().get("date");
/*  90 */           Date nowDate = new Date();
/*  91 */           if (nowDate.getDate() != saveDate.getDate())
/*     */           {
/*  93 */             CheckTimeDateMap.getInstance().getCheckTimeDateMap()
/*  94 */               .put("date", nowDate);
/*     */ 
/*  96 */             Iterator it = MacLimitMap.getInstance()
/*  97 */               .getMacLimitMap().keySet().iterator();
/*  98 */             while (it.hasNext()) {
/*  99 */               Object o = it.next();
/* 100 */               String t = o.toString();
/* 101 */               String[] TimeInfo = 
/* 102 */                 (String[])MacLimitMap.getInstance()
/* 102 */                 .getMacLimitMap().get(t);
/*     */ 
/* 107 */               TimeInfo[1] = "0";
/* 108 */               MacLimitMap.getInstance().getMacLimitMap()
/* 109 */                 .put(t, TimeInfo);
/*     */             }
/*     */ 
/* 112 */             Iterator iterator = UserLimitMap.getInstance()
/* 113 */               .getUserLimitMap().keySet()
/* 114 */               .iterator();
/* 115 */             while (iterator.hasNext()) {
/* 116 */               Object o = iterator.next();
/* 117 */               String t = o.toString();
/* 118 */               String[] TimeInfo = 
/* 119 */                 (String[])UserLimitMap.getInstance()
/* 119 */                 .getUserLimitMap().get(t);
/*     */ 
/* 124 */               TimeInfo[1] = "0";
/* 125 */               UserLimitMap.getInstance().getUserLimitMap()
/* 126 */                 .put(t, TimeInfo);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 131 */           if (CheckTimeService.Do()) {
/* 132 */             Iterator iteratorMac = MacLimitMap.getInstance()
/* 133 */               .getMacLimitMap().keySet()
/* 134 */               .iterator();
/* 135 */             while (iteratorMac.hasNext()) {
/* 136 */               Object o = iteratorMac.next();
/* 137 */               String t = o.toString();
/* 138 */               String[] TimeInfo = 
/* 139 */                 (String[])MacLimitMap.getInstance()
/* 139 */                 .getMacLimitMap().get(t);
/* 140 */               Long id = Long.valueOf(TimeInfo[2]);
/* 141 */               Portalonlinelimit onlinelimit = CheckTimeService.onlinelimitService
/* 142 */                 .getPortalonlinelimitByKey(id);
/* 143 */               if ((onlinelimit != null) && 
/* 144 */                 (onlinelimit.getState().intValue() == 1)) {
/* 145 */                 Long timepermit = onlinelimit.getTime();
/* 146 */                 if (timepermit != null) {
/* 147 */                   String loginTimeString = TimeInfo[0];
/* 148 */                   Long oldcostTime = Long.valueOf(TimeInfo[1]);
/* 149 */                   if (stringUtils.isNotBlank(loginTimeString)) {
/* 150 */                     Date loginTime = 
/* 151 */                       ThreadLocalDateUtil.parse(loginTimeString);
/* 152 */                     String nowString = 
/* 153 */                       ThreadLocalDateUtil.format(new Date());
/* 154 */                     Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 155 */                     Long costTime = Long.valueOf(nowTime.getTime() - 
/* 156 */                       loginTime.getTime() + oldcostTime.longValue());
/* 157 */                     System.out.println("mac: " + t + " id: " + 
/* 158 */                       id + " timepermit: " + timepermit + 
/* 159 */                       " costTime:" + costTime.longValue() / 1000L);
/* 160 */                     if (costTime.longValue() >= timepermit.longValue()) {
/* 161 */                       Iterator it = 
/* 162 */                         OnlineMap.getInstance()
/* 163 */                         .getOnlineUserMap()
/* 164 */                         .keySet().iterator();
/* 165 */                       while (it.hasNext()) {
/* 166 */                         Object oo = it.next();
/* 167 */                         String tt = oo.toString();
/* 168 */                         String[] loginInfo = 
/* 171 */                           (String[])OnlineMap.getInstance()
/* 170 */                           .getOnlineUserMap()
/* 171 */                           .get(tt);
/* 172 */                         String mac = loginInfo[4];
/* 173 */                         if (t.equals(mac)) {
/* 174 */                           Kick.kickUserOneDayLimit(tt);
/* 175 */                           break;
/*     */                         }
/*     */                       }
/* 178 */                       TimeInfo[0] = "";
/* 179 */                       TimeInfo[1] = String.valueOf(costTime);
/* 180 */                       MacLimitMap.getInstance()
/* 181 */                         .getMacLimitMap()
/* 182 */                         .put(t, TimeInfo);
/*     */                     }
/*     */                   }
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 191 */             Iterator iterator = UserLimitMap.getInstance()
/* 192 */               .getUserLimitMap().keySet()
/* 193 */               .iterator();
/* 194 */             while (iterator.hasNext()) {
/* 195 */               Object o = iterator.next();
/* 196 */               String t = o.toString();
/* 197 */               String[] TimeInfo = 
/* 198 */                 (String[])UserLimitMap.getInstance()
/* 198 */                 .getUserLimitMap().get(t);
/* 199 */               Long id = Long.valueOf(TimeInfo[2]);
/* 200 */               Portalonlinelimit onlinelimit = CheckTimeService.onlinelimitService
/* 201 */                 .getPortalonlinelimitByKey(id);
/* 202 */               if ((onlinelimit != null) && 
/* 203 */                 (onlinelimit.getType().intValue() == 1)) {
/* 204 */                 Long timepermit = onlinelimit.getTime();
/* 205 */                 if (timepermit != null) {
/* 206 */                   String loginTimeString = TimeInfo[0];
/* 207 */                   Long oldcostTime = Long.valueOf(TimeInfo[1]);
/* 208 */                   if (stringUtils.isNotBlank(loginTimeString)) {
/* 209 */                     Date loginTime = 
/* 210 */                       ThreadLocalDateUtil.parse(loginTimeString);
/* 211 */                     String nowString = 
/* 212 */                       ThreadLocalDateUtil.format(new Date());
/* 213 */                     Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 214 */                     Long costTime = Long.valueOf(nowTime.getTime() - 
/* 215 */                       loginTime.getTime() + oldcostTime.longValue());
/* 216 */                     System.out.println("mac: " + t + " id: " + 
/* 217 */                       id + " timepermit: " + timepermit + 
/* 218 */                       " costTime:" + costTime.longValue() / 1000L);
/* 219 */                     if (costTime.longValue() >= timepermit.longValue()) {
/* 220 */                       Iterator it = 
/* 221 */                         OnlineMap.getInstance()
/* 222 */                         .getOnlineUserMap()
/* 223 */                         .keySet().iterator();
/* 224 */                       while (it.hasNext()) {
/* 225 */                         Object oo = it.next();
/* 226 */                         String tt = oo.toString();
/* 227 */                         String[] loginInfo = 
/* 230 */                           (String[])OnlineMap.getInstance()
/* 229 */                           .getOnlineUserMap()
/* 230 */                           .get(tt);
/* 231 */                         String username = loginInfo[0];
/* 232 */                         if (t.equals(username))
/*     */                         {
/* 234 */                           Kick.kickUserOneDayLimit(tt);
/* 235 */                           break;
/*     */                         }
/*     */                       }
/* 238 */                       TimeInfo[0] = "";
/* 239 */                       TimeInfo[1] = String.valueOf(costTime);
/* 240 */                       UserLimitMap.getInstance()
/* 241 */                         .getUserLimitMap()
/* 242 */                         .put(t, TimeInfo);
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 254 */           CheckTimeService.logger.error("One Day Online Time Check Start ERROR!!");
/* 255 */           CheckTimeService.logger.error("==============ERROR Start=============");
/* 256 */           CheckTimeService.logger.error(e);
/* 257 */           CheckTimeService.logger.error("ERROR INFO ", e);
/* 258 */           CheckTimeService.logger.error("==============ERROR End=============");
/*     */         }
/*     */       }
/*     */     };
/* 264 */     long delay = 60000L;
/* 265 */     long intevalPeriod = 600000L;
/*     */ 
/* 268 */     timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
/*     */   }
/*     */ 
/*     */   private void limitUserMapToDisk(ServletContextEvent servletContextEvent) {
/* 272 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 273 */     ObjectOutputStream os = null;
/*     */     try {
/* 275 */       os = new ObjectOutputStream(new FileOutputStream(
/* 276 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 277 */         "/limitUserMap.dat"));
/* 278 */       os.writeObject(UserLimitMap.getInstance().getUserLimitMap());
/* 279 */       if (basConfig.getIsdebug().equals("1"))
/* 280 */         logger.info("limitUserMapToDisk !!");
/*     */     }
/*     */     catch (Exception e) {
/* 283 */       logger.error("==============ERROR Start=============");
/* 284 */       logger.error(e);
/* 285 */       logger.error("ERROR INFO ", e);
/* 286 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 289 */         if (os != null)
/* 290 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 293 */         logger.error("==============ERROR Start=============");
/* 294 */         logger.error(e1);
/* 295 */         logger.error("ERROR INFO ", e1);
/* 296 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 289 */         if (os != null)
/* 290 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 293 */         logger.error("==============ERROR Start=============");
/* 294 */         logger.error(e);
/* 295 */         logger.error("ERROR INFO ", e);
/* 296 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void limitUserMapFromDisk(ServletContextEvent servletContextEvent) {
/* 302 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 303 */     ObjectInputStream is = null;
/* 304 */     File parent = new File(servletContextEvent.getServletContext()
/* 305 */       .getRealPath("/") + "/limitUserMap.dat");
/*     */ 
/* 307 */     label316: 
/*     */     try { if (parent.exists()) {
/* 308 */         is = new ObjectInputStream(new FileInputStream(
/* 309 */           servletContextEvent.getServletContext()
/* 310 */           .getRealPath("/") + "/limitUserMap.dat"));
/* 311 */         Map limitUserMap = (ConcurrentHashMap)is
/* 312 */           .readObject();
/* 313 */         UserLimitMap.getInstance().setUserLimitMap(limitUserMap);
/* 314 */         if (basConfig.getIsdebug().equals("1")) {
/* 315 */           logger.info("limitUserMapFromDisk !!");
/*     */ 
/* 318 */           break label316;
/*     */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 320 */         logger.info("limitUserMap File Not Exists !!");
/*     */       }
/*     */     } catch (Exception e)
/*     */     {
/* 324 */       logger.error("==============ERROR Start=============");
/* 325 */       logger.error(e);
/* 326 */       logger.error("ERROR INFO ", e);
/* 327 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 330 */         if (is != null) {
/* 331 */           is.close();
/*     */         }
/* 333 */         parent = null;
/*     */       } catch (IOException e1) {
/* 335 */         logger.error("==============ERROR Start=============");
/* 336 */         logger.error(e1);
/* 337 */         logger.error("ERROR INFO ", e1);
/* 338 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 330 */         if (is != null) {
/* 331 */           is.close();
/*     */         }
/* 333 */         parent = null;
/*     */       } catch (IOException e) {
/* 335 */         logger.error("==============ERROR Start=============");
/* 336 */         logger.error(e);
/* 337 */         logger.error("ERROR INFO ", e);
/* 338 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void limitMacMapToDisk(ServletContextEvent servletContextEvent) {
/* 344 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 345 */     ObjectOutputStream os = null;
/*     */     try {
/* 347 */       os = new ObjectOutputStream(new FileOutputStream(
/* 348 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 349 */         "/limitMacMap.dat"));
/* 350 */       os.writeObject(MacLimitMap.getInstance().getMacLimitMap());
/* 351 */       if (basConfig.getIsdebug().equals("1"))
/* 352 */         logger.info("limitMacMapToDisk !!");
/*     */     }
/*     */     catch (Exception e) {
/* 355 */       logger.error("==============ERROR Start=============");
/* 356 */       logger.error(e);
/* 357 */       logger.error("ERROR INFO ", e);
/* 358 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 361 */         if (os != null)
/* 362 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 365 */         logger.error("==============ERROR Start=============");
/* 366 */         logger.error(e1);
/* 367 */         logger.error("ERROR INFO ", e1);
/* 368 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 361 */         if (os != null)
/* 362 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 365 */         logger.error("==============ERROR Start=============");
/* 366 */         logger.error(e);
/* 367 */         logger.error("ERROR INFO ", e);
/* 368 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void limitMacMapFromDisk(ServletContextEvent servletContextEvent) {
/* 374 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 375 */     ObjectInputStream is = null;
/* 376 */     File parent = new File(servletContextEvent.getServletContext()
/* 377 */       .getRealPath("/") + "/limitMacMap.dat");
/*     */ 
/* 379 */     label320: 
/*     */     try { if (parent.exists()) {
/* 380 */         is = new ObjectInputStream(new FileInputStream(
/* 381 */           servletContextEvent.getServletContext()
/* 382 */           .getRealPath("/") + "/limitMacMap.dat"));
/* 383 */         Map limitMacMap = (ConcurrentHashMap)is
/* 384 */           .readObject();
/* 385 */         MacLimitMap.getInstance().setMacLimitMap(limitMacMap);
/* 386 */         if (basConfig.getIsdebug().equals("1")) {
/* 387 */           logger.info("limitMacMapFromDisk !!");
/*     */ 
/* 390 */           break label320;
/*     */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 392 */         logger.info("limitMacMap File Not Exists !!");
/*     */       }
/*     */     } catch (Exception e)
/*     */     {
/* 396 */       logger.error("==============ERROR Start=============");
/* 397 */       logger.error(e);
/* 398 */       logger.error("ERROR INFO ", e);
/* 399 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 402 */         if (is != null) {
/* 403 */           is.close();
/*     */         }
/* 405 */         parent = null;
/*     */       } catch (IOException e1) {
/* 407 */         logger.error("==============ERROR Start=============");
/* 408 */         logger.error(e1);
/* 409 */         logger.error("ERROR INFO ", e1);
/* 410 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 402 */         if (is != null) {
/* 403 */           is.close();
/*     */         }
/* 405 */         parent = null;
/*     */       } catch (IOException e) {
/* 407 */         logger.error("==============ERROR Start=============");
/* 408 */         logger.error(e);
/* 409 */         logger.error("ERROR INFO ", e);
/* 410 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void CheckTimeDateMapToDisk(ServletContextEvent servletContextEvent) {
/* 416 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 417 */     ObjectOutputStream os = null;
/*     */     try {
/* 419 */       os = new ObjectOutputStream(new FileOutputStream(
/* 420 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 421 */         "/CheckTimeDateMap.dat"));
/* 422 */       os.writeObject(CheckTimeDateMap.getInstance().getCheckTimeDateMap());
/* 423 */       if (basConfig.getIsdebug().equals("1"))
/* 424 */         logger.info("CheckTimeDateMapToDisk !!");
/*     */     }
/*     */     catch (Exception e) {
/* 427 */       logger.error("==============ERROR Start=============");
/* 428 */       logger.error(e);
/* 429 */       logger.error("ERROR INFO ", e);
/* 430 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 433 */         if (os != null)
/* 434 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 437 */         logger.error("==============ERROR Start=============");
/* 438 */         logger.error(e1);
/* 439 */         logger.error("ERROR INFO ", e1);
/* 440 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 433 */         if (os != null)
/* 434 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 437 */         logger.error("==============ERROR Start=============");
/* 438 */         logger.error(e);
/* 439 */         logger.error("ERROR INFO ", e);
/* 440 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void CheckTimeDateMapFromDisk(ServletContextEvent servletContextEvent)
/*     */   {
/* 447 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 448 */     ObjectInputStream is = null;
/* 449 */     File parent = new File(servletContextEvent.getServletContext()
/* 450 */       .getRealPath("/") + "/CheckTimeDateMap.dat");
/*     */ 
/* 452 */     label342: 
/*     */     try { if (parent.exists()) {
/* 453 */         is = new ObjectInputStream(new FileInputStream(
/* 454 */           servletContextEvent.getServletContext()
/* 455 */           .getRealPath("/") + "/CheckTimeDateMap.dat"));
/* 456 */         Map TimeDateMap = (ConcurrentHashMap)is
/* 457 */           .readObject();
/* 458 */         CheckTimeDateMap.getInstance().setCheckTimeDateMap(TimeDateMap);
/* 459 */         if (basConfig.getIsdebug().equals("1")) {
/* 460 */           logger.info("CheckTimeDateMapFromDisk !!");
/*     */ 
/* 463 */           break label342;
/*     */         } } else { if (basConfig.getIsdebug().equals("1")) {
/* 465 */           logger.info("CheckTimeDateMap File Not Exists !!");
/*     */         }
/* 467 */         CheckTimeDateMap.getInstance().getCheckTimeDateMap()
/* 468 */           .put("date", new Date()); }
/*     */     } catch (Exception e)
/*     */     {
/* 471 */       logger.error("==============ERROR Start=============");
/* 472 */       logger.error(e);
/* 473 */       logger.error("ERROR INFO ", e);
/* 474 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 477 */         if (is != null) {
/* 478 */           is.close();
/*     */         }
/* 480 */         parent = null;
/*     */       } catch (IOException e1) {
/* 482 */         logger.error("==============ERROR Start=============");
/* 483 */         logger.error(e1);
/* 484 */         logger.error("ERROR INFO ", e1);
/* 485 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 477 */         if (is != null) {
/* 478 */           is.close();
/*     */         }
/* 480 */         parent = null;
/*     */       } catch (IOException e) {
/* 482 */         logger.error("==============ERROR Start=============");
/* 483 */         logger.error(e);
/* 484 */         logger.error("ERROR INFO ", e);
/* 485 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean Do() {
/* 491 */     Long isThis = Long.valueOf(new Date().getTime());
/* 492 */     boolean Do = false;
/* 493 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 494 */       Do = true;
/*     */     }
/* 496 */     return Do;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.CheckTimeService
 * JD-Core Version:    0.6.2
 */