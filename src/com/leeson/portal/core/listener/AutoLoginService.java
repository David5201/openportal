/*     */ package com.leeson.portal.core.listener;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalautologin;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.service.PortalautologinService;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.model.AutoLoginCheckTimeMap;
/*     */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.LateAuthMap;
/*     */ import com.leeson.portal.core.model.OnlineMap;
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
/*     */ public class AutoLoginService
/*     */   implements ServletContextListener
/*     */ {
/*  40 */   private static Config config = Config.getInstance();
/*  41 */   private static Logger logger = Logger.getLogger(AutoLoginService.class);
/*     */   private static PortalautologinService autoLoginService;
/*  44 */   private static Timer timerstartCheck = new Timer();
/*  45 */   private static Timer timerLateAuthCheck = new Timer();
/*     */ 
/*     */   public void contextDestroyed(ServletContextEvent servletContextEvent)
/*     */   {
/*  49 */     timerstartCheck.cancel();
/*  50 */     timerLateAuthCheck.cancel();
/*  51 */     limitMacMapToDisk(servletContextEvent);
/*  52 */     AutoLoginCheckTimeMapToDisk(servletContextEvent);
/*     */   }
/*     */ 
/*     */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*     */   {
/*  57 */     AutoLoginCheckTimeMapFromDisk(servletContextEvent);
/*  58 */     if ((AutoLoginCheckTimeMap.getInstance()
/*  59 */       .getAutoLoginCheckTimeMap().get("time") == null) || 
/*  61 */       (10L > 
/*  61 */       ((Long)AutoLoginCheckTimeMap.getInstance()
/*  61 */       .getAutoLoginCheckTimeMap().get("time")).longValue())) {
/*  62 */       AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap()
/*  63 */         .put("time", Long.valueOf(10L));
/*     */     }
/*  65 */     limitMacMapFromDisk(servletContextEvent);
/*     */ 
/*  67 */     autoLoginService = (PortalautologinService)
/*  68 */       SpringContextHelper.getBean("portalautologinServiceImpl");
/*     */ 
/*  70 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/*  75 */           AutoLoginService.this.startCheck();
/*  76 */           AutoLoginService.logger.info("AutoLogin Check Servcie Start Success!! 1min Later Start , Every " + 
/*  77 */             AutoLoginCheckTimeMap.getInstance()
/*  78 */             .getAutoLoginCheckTimeMap().get("time") + 
/*  79 */             "min Check Once !!");
/*     */         }
/*     */         catch (Exception e) {
/*  82 */           AutoLoginService.logger.error("AutoLogin Check Servcie Start ERROR !!");
/*     */         }
/*     */       }
/*     */     }
/*  86 */     .start();
/*     */ 
/*  88 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/*  93 */           AutoLoginService.this.LateAuthCheck();
/*  94 */           AutoLoginService.logger.info("LateAuth Check Servcie Start Success!! 1min Later Start , Every 10 S Check Once !!");
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*  98 */           AutoLoginService.logger.error("LateAuth Check Servcie Start ERROR !!");
/*     */         }
/*     */       }
/*     */     }
/* 102 */     .start();
/*     */   }
/*     */ 
/*     */   private void LateAuthCheck()
/*     */   {
/* 107 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/* 110 */         if (CheckOnlineService.Do())
/*     */           try {
/* 112 */             Iterator iteratorMac = LateAuthMap.getInstance()
/* 113 */               .getLateAuthMap().keySet()
/* 114 */               .iterator();
/* 115 */             while (iteratorMac.hasNext()) {
/* 116 */               Object o = iteratorMac.next();
/* 117 */               String t = o.toString();
/* 118 */               Date loginDate = 
/* 119 */                 (Date)LateAuthMap.getInstance()
/* 119 */                 .getLateAuthMap().get(t);
/* 120 */               Date nowDate = new Date();
/* 121 */               long willTime = 14712289280L;
/* 122 */               Date willDate = new Date(nowDate.getTime() + 
/* 123 */                 willTime);
/* 124 */               if ((loginDate != null) && 
/* 125 */                 (loginDate.getTime() < nowDate.getTime())) {
/* 126 */                 Iterator it = OnlineMap.getInstance()
/* 127 */                   .getOnlineUserMap().keySet()
/* 128 */                   .iterator();
/* 129 */                 while (it.hasNext()) {
/* 130 */                   Object oo = it.next();
/* 131 */                   String tt = oo.toString();
/* 132 */                   String[] loginInfo = 
/* 133 */                     (String[])OnlineMap.getInstance()
/* 133 */                     .getOnlineUserMap().get(tt);
/* 134 */                   String mac = loginInfo[4];
/* 135 */                   if (t.equals(mac)) {
/* 136 */                     String basip = loginInfo[10];
/* 137 */                     Portalbas bas = 
/* 138 */                       (Portalbas)AutoLoginService.config.getConfigMap()
/* 138 */                       .get(basip);
/* 139 */                     if (bas != null) {
/* 140 */                       Integer state = bas.getLateAuth();
/* 141 */                       Long perTime = bas
/* 142 */                         .getLateAuthTime();
/* 143 */                       if ((1 == state.intValue()) && (perTime != null) && 
/* 144 */                         (perTime.longValue() > 0L)) {
/* 145 */                         long costTime = nowDate
/* 146 */                           .getTime() - 
/* 147 */                           loginDate.getTime();
/* 148 */                         costTime /= 1000L;
/* 149 */                         System.out
/* 150 */                           .println("LaterAuth Check mac: " + 
/* 151 */                           t + 
/* 152 */                           " timepermit: " + 
/* 153 */                           perTime + 
/* 154 */                           " costTime:" + 
/* 155 */                           costTime);
/* 156 */                         if (costTime <= perTime.longValue()) break;
/* 157 */                         LateAuthMap.getInstance()
/* 158 */                           .getLateAuthMap()
/* 159 */                           .put(t, willDate);
/*     */ 
/* 161 */                         Kick.kickUserLaterAuth(tt);
/*     */ 
/* 163 */                         break;
/* 164 */                       }LateAuthMap.getInstance()
/* 165 */                         .getLateAuthMap()
/* 166 */                         .put(t, willDate);
/*     */ 
/* 168 */                       Kick.kickUserLaterAuth(tt);
/*     */ 
/* 170 */                       break;
/* 171 */                     }LateAuthMap.getInstance()
/* 172 */                       .getLateAuthMap()
/* 173 */                       .put(t, willDate);
/*     */ 
/* 175 */                     Kick.kickUserLaterAuth(tt);
/*     */ 
/* 177 */                     break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           } catch (Exception e) {
/* 183 */             AutoLoginService.logger.error("LateAuth Check Start ERROR!!");
/* 184 */             AutoLoginService.logger.error("==============ERROR Start=============");
/* 185 */             AutoLoginService.logger.error(e);
/* 186 */             AutoLoginService.logger.error("ERROR INFO ", e);
/* 187 */             AutoLoginService.logger.error("==============ERROR End=============");
/*     */           }
/*     */       }
/*     */     };
/* 193 */     long delay = 20000L;
/* 194 */     long intevalPeriod = 10000L;
/* 195 */     timerLateAuthCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
/*     */   }
/*     */ 
/*     */   private void startCheck()
/*     */   {
/* 200 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/* 203 */         if (CheckOnlineService.Do())
/*     */           try {
/* 205 */             Iterator iteratorMac = AutoLoginMacMap.getInstance()
/* 206 */               .getAutoLoginMacMap().keySet()
/* 207 */               .iterator();
/* 208 */             while (iteratorMac.hasNext()) {
/* 209 */               Object o = iteratorMac.next();
/* 210 */               String t = o.toString();
/* 211 */               String[] TimeInfo = 
/* 212 */                 (String[])AutoLoginMacMap.getInstance()
/* 212 */                 .getAutoLoginMacMap().get(t);
/* 213 */               Long id = Long.valueOf(TimeInfo[2]);
/* 214 */               Portalautologin autologin = AutoLoginService.autoLoginService
/* 215 */                 .getPortalautologinByKey(id);
/* 216 */               if ((autologin != null) && 
/* 217 */                 (autologin.getState().intValue() == 1)) {
/* 218 */                 Long timepermit = autologin.getTime();
/* 219 */                 if ((timepermit != null) && (timepermit.longValue() > 0L)) {
/* 220 */                   String loginTimeString = TimeInfo[0];
/* 221 */                   Long oldcostTime = 
/* 222 */                     Long.valueOf(TimeInfo[1]);
/*     */ 
/* 224 */                   if (stringUtils.isNotBlank(loginTimeString)) {
/* 225 */                     Date loginTime = 
/* 226 */                       ThreadLocalDateUtil.parse(loginTimeString);
/* 227 */                     String nowString = 
/* 228 */                       ThreadLocalDateUtil.format(new Date());
/* 229 */                     Date nowTime = 
/* 230 */                       ThreadLocalDateUtil.parse(nowString);
/* 231 */                     Long costTime = Long.valueOf(nowTime.getTime() - 
/* 232 */                       loginTime.getTime() + 
/* 233 */                       oldcostTime.longValue());
/* 234 */                     System.out
/* 235 */                       .println("AutoLogin Check mac: " + 
/* 236 */                       t + 
/* 237 */                       " id: " + 
/* 238 */                       id + 
/* 239 */                       " timepermit: " + 
/* 240 */                       timepermit + 
/* 241 */                       " costTime:" + 
/* 242 */                       costTime.longValue() / 1000L);
/* 243 */                     if (costTime.longValue() >= timepermit.longValue()) {
/* 244 */                       Iterator it = 
/* 245 */                         OnlineMap.getInstance()
/* 246 */                         .getOnlineUserMap()
/* 247 */                         .keySet().iterator();
/* 248 */                       while (it.hasNext()) {
/* 249 */                         Object oo = it.next();
/* 250 */                         String tt = oo.toString();
/* 251 */                         String[] loginInfo = 
/* 254 */                           (String[])OnlineMap.getInstance()
/* 253 */                           .getOnlineUserMap()
/* 254 */                           .get(tt);
/* 255 */                         String mac = loginInfo[4];
/* 256 */                         if (t.equals(mac)) {
/* 257 */                           Kick.kickUserAutoLogin(tt);
/* 258 */                           break;
/*     */                         }
/*     */                       }
/* 261 */                       TimeInfo[0] = "";
/* 262 */                       TimeInfo[1] = 
/* 263 */                         String.valueOf(costTime);
/*     */ 
/* 266 */                       AutoLoginMacMap.getInstance()
/* 267 */                         .getAutoLoginMacMap()
/* 268 */                         .remove(t);
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 278 */             AutoLoginService.logger.error("AutoLogin Check Start ERROR!!");
/* 279 */             AutoLoginService.logger.error("==============ERROR Start=============");
/* 280 */             AutoLoginService.logger.error(e);
/* 281 */             AutoLoginService.logger.error("ERROR INFO ", e);
/* 282 */             AutoLoginService.logger.error("==============ERROR End=============");
/*     */           }
/*     */       }
/*     */     };
/* 288 */     long delay = 60000L;
/* 289 */     long intevalPeriod = 
/* 290 */       ((Long)AutoLoginCheckTimeMap.getInstance()
/* 290 */       .getAutoLoginCheckTimeMap().get("time")).longValue() * 60000L;
/*     */ 
/* 295 */     timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
/*     */   }
/*     */ 
/*     */   private void limitMacMapToDisk(ServletContextEvent servletContextEvent) {
/* 299 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 300 */     ObjectOutputStream os = null;
/*     */     try {
/* 302 */       os = new ObjectOutputStream(new FileOutputStream(
/* 303 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 304 */         "/autologinMap.dat"));
/* 305 */       os.writeObject(AutoLoginMacMap.getInstance().getAutoLoginMacMap());
/* 306 */       if (basConfig.getIsdebug().equals("1"))
/* 307 */         logger.info("autologinMapToDisk !!");
/*     */     }
/*     */     catch (Exception e) {
/* 310 */       logger.error("==============ERROR Start=============");
/* 311 */       logger.error(e);
/* 312 */       logger.error("ERROR INFO ", e);
/* 313 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 316 */         if (os != null)
/* 317 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 320 */         logger.error("==============ERROR Start=============");
/* 321 */         logger.error(e1);
/* 322 */         logger.error("ERROR INFO ", e1);
/* 323 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 316 */         if (os != null)
/* 317 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 320 */         logger.error("==============ERROR Start=============");
/* 321 */         logger.error(e);
/* 322 */         logger.error("ERROR INFO ", e);
/* 323 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void limitMacMapFromDisk(ServletContextEvent servletContextEvent) {
/* 329 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 330 */     ObjectInputStream is = null;
/* 331 */     File parent = new File(servletContextEvent.getServletContext()
/* 332 */       .getRealPath("/") + "/autologinMap.dat");
/*     */ 
/* 334 */     label318: 
/*     */     try { if (parent.exists()) {
/* 335 */         is = new ObjectInputStream(new FileInputStream(
/* 336 */           servletContextEvent.getServletContext()
/* 337 */           .getRealPath("/") + "/autologinMap.dat"));
/* 338 */         Map autologinMap = (ConcurrentHashMap)is
/* 339 */           .readObject();
/* 340 */         AutoLoginMacMap.getInstance().setAutoLoginMacMap(autologinMap);
/* 341 */         if (basConfig.getIsdebug().equals("1")) {
/* 342 */           logger.info("autologinMapFromDisk !!");
/*     */ 
/* 345 */           break label318;
/*     */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 347 */         logger.info("autologinMap File Not Exists !!");
/*     */       }
/*     */     } catch (Exception e)
/*     */     {
/* 351 */       logger.error("==============ERROR Start=============");
/* 352 */       logger.error(e);
/* 353 */       logger.error("ERROR INFO ", e);
/* 354 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 357 */         if (is != null) {
/* 358 */           is.close();
/*     */         }
/* 360 */         parent = null;
/*     */       } catch (IOException e1) {
/* 362 */         logger.error("==============ERROR Start=============");
/* 363 */         logger.error(e1);
/* 364 */         logger.error("ERROR INFO ", e1);
/* 365 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 357 */         if (is != null) {
/* 358 */           is.close();
/*     */         }
/* 360 */         parent = null;
/*     */       } catch (IOException e) {
/* 362 */         logger.error("==============ERROR Start=============");
/* 363 */         logger.error(e);
/* 364 */         logger.error("ERROR INFO ", e);
/* 365 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void AutoLoginCheckTimeMapToDisk(ServletContextEvent servletContextEvent)
/*     */   {
/* 372 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 373 */     ObjectOutputStream os = null;
/*     */     try {
/* 375 */       os = new ObjectOutputStream(new FileOutputStream(
/* 376 */         servletContextEvent.getServletContext().getRealPath("/") + 
/* 377 */         "/AutoLoginCheckTimeMap.dat"));
/* 378 */       os.writeObject(AutoLoginCheckTimeMap.getInstance()
/* 379 */         .getAutoLoginCheckTimeMap());
/* 380 */       if (basConfig.getIsdebug().equals("1"))
/* 381 */         logger.info("AutoLoginCheckTimeMapToDisk !!");
/*     */     }
/*     */     catch (Exception e) {
/* 384 */       logger.error("==============ERROR Start=============");
/* 385 */       logger.error(e);
/* 386 */       logger.error("ERROR INFO ", e);
/* 387 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 390 */         if (os != null)
/* 391 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 394 */         logger.error("==============ERROR Start=============");
/* 395 */         logger.error(e1);
/* 396 */         logger.error("ERROR INFO ", e1);
/* 397 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 390 */         if (os != null)
/* 391 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 394 */         logger.error("==============ERROR Start=============");
/* 395 */         logger.error(e);
/* 396 */         logger.error("ERROR INFO ", e);
/* 397 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void AutoLoginCheckTimeMapFromDisk(ServletContextEvent servletContextEvent)
/*     */   {
/* 404 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 405 */     ObjectInputStream is = null;
/* 406 */     File parent = new File(servletContextEvent.getServletContext()
/* 407 */       .getRealPath("/") + "/AutoLoginCheckTimeMap.dat");
/*     */ 
/* 409 */     label340: 
/*     */     try { if (parent.exists()) {
/* 410 */         is = new ObjectInputStream(new FileInputStream(
/* 411 */           servletContextEvent.getServletContext()
/* 412 */           .getRealPath("/") + 
/* 413 */           "/AutoLoginCheckTimeMap.dat"));
/* 414 */         Map autoLoginCheckTimeMap = (ConcurrentHashMap)is
/* 415 */           .readObject();
/* 416 */         AutoLoginCheckTimeMap.getInstance().setAutoLoginCheckTimeMap(
/* 417 */           autoLoginCheckTimeMap);
/* 418 */         if (basConfig.getIsdebug().equals("1")) {
/* 419 */           logger.info("AutoLoginCheckTimeMapFromDisk !!");
/*     */ 
/* 422 */           break label340;
/*     */         } } else { if (basConfig.getIsdebug().equals("1")) {
/* 424 */           logger.info("AutoLoginCheckTimeMap File Not Exists , Use default 10M !!");
/*     */         }
/* 426 */         AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap()
/* 427 */           .put("time", Long.valueOf(10L)); }
/*     */     } catch (Exception e)
/*     */     {
/* 430 */       logger.error("==============ERROR Start=============");
/* 431 */       logger.error(e);
/* 432 */       logger.error("ERROR INFO ", e);
/* 433 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 436 */         if (is != null) {
/* 437 */           is.close();
/*     */         }
/* 439 */         parent = null;
/*     */       } catch (IOException e1) {
/* 441 */         logger.error("==============ERROR Start=============");
/* 442 */         logger.error(e1);
/* 443 */         logger.error("ERROR INFO ", e1);
/* 444 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 436 */         if (is != null) {
/* 437 */           is.close();
/*     */         }
/* 439 */         parent = null;
/*     */       } catch (IOException e) {
/* 441 */         logger.error("==============ERROR Start=============");
/* 442 */         logger.error(e);
/* 443 */         logger.error("ERROR INFO ", e);
/* 444 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.AutoLoginService
 * JD-Core Version:    0.6.2
 */