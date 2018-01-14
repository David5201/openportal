/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortallinkrecordService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class WifidogKick
/*     */ {
/*  20 */   private static Logger log = Logger.getLogger(WifidogKick.class);
/*  21 */   private static Config configDefault = Config.getInstance();
/*  22 */   private static WifiDogOnlineMap onlineMap = WifiDogOnlineMap.getInstance();
/*     */ 
/*     */   public static void kickUser(String token)
/*     */   {
/*  26 */     Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
/*  27 */     String[] loginInfo = null;
/*  28 */     loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
/*  29 */     if (loginInfo != null) {
/*  30 */       String username = loginInfo[0];
/*  31 */       String ip = loginInfo[4];
/*     */ 
/*  33 */       if (config.getAuthInterface().contains("1")) {
/*  34 */         doLinkRecord(loginInfo);
/*     */       }
/*  36 */       doLogRecord("IP:" + ip + ",用户:" + username + ",被Portal服务器踢下线！");
/*     */ 
/*  38 */       if (config.getIsdebug().equals("1")) {
/*  39 */         log.info("用户IP: " + ip + " 用户名: " + username + 
/*  40 */           " 被Portal服务器踢下线！");
/*     */       }
/*     */ 
/*  43 */       onlineMap.getWifiDogOnlineMap().remove(token);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void ApKickUser(String token)
/*     */   {
/*  50 */     Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
/*  51 */     String[] loginInfo = null;
/*  52 */     loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
/*  53 */     if (loginInfo != null) {
/*  54 */       String username = loginInfo[0];
/*  55 */       String ip = loginInfo[4];
/*     */ 
/*  57 */       if (config.getAuthInterface().contains("1")) {
/*  58 */         doLinkRecord(loginInfo);
/*     */       }
/*  60 */       doLogRecord("IP:" + ip + ",用户:" + username + ",被AP踢下线！");
/*     */ 
/*  62 */       if (config.getIsdebug().equals("1")) {
/*  63 */         log.info("用户IP: " + ip + " 用户名: " + username + 
/*  64 */           " 被AP踢下线！");
/*     */       }
/*     */ 
/*  67 */       onlineMap.getWifiDogOnlineMap().remove(token);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void offLine(String token)
/*     */   {
/*  74 */     Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
/*  75 */     String[] loginInfo = null;
/*  76 */     loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
/*  77 */     if (loginInfo != null) {
/*  78 */       String username = loginInfo[0];
/*  79 */       String ip = loginInfo[4];
/*  80 */       if (config.getAuthInterface().contains("1")) {
/*  81 */         doLinkRecord(loginInfo);
/*     */       }
/*  83 */       doLogRecord("IP:" + ip + ",用户:" + username + ",主动下线成功!");
/*     */ 
/*  85 */       if (config.getIsdebug().equals("1")) {
/*  86 */         log.info("用户IP: " + ip + " 用户名: " + username + ",主动下线成功!");
/*     */       }
/*     */ 
/*  89 */       onlineMap.getWifiDogOnlineMap().remove(token);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void WeixinOffLine(String token)
/*     */   {
/*  95 */     Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
/*  96 */     String[] loginInfo = null;
/*  97 */     loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
/*  98 */     if (loginInfo != null) {
/*  99 */       String username = loginInfo[0];
/* 100 */       String ip = loginInfo[4];
/*     */ 
/* 102 */       doLogRecord("IP:" + ip + ",用户:" + username + ",未通过微信认证下线成功!");
/*     */ 
/* 104 */       if (config.getIsdebug().equals("1")) {
/* 105 */         log.info("用户IP: " + ip + " 用户名: " + username + ",未通过微信认证下线成功!");
/*     */       }
/* 107 */       onlineMap.getWifiDogOnlineMap().remove(token);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void doLinkRecord(String[] loginInfo)
/*     */   {
/* 119 */     PortallinkrecordService portallinkrecordService = (PortallinkrecordService)
/* 120 */       SpringContextHelper.getBean("portallinkrecordServiceImpl");
/* 121 */     PortalaccountService portalaccountService = (PortalaccountService)
/* 122 */       SpringContextHelper.getBean("portalaccountServiceImpl");
/*     */ 
/* 124 */     String uid = loginInfo[1];
/* 125 */     String rid = loginInfo[2];
/* 126 */     if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
/* 127 */       return;
/*     */     }
/* 129 */     Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
/* 130 */     Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
/* 131 */     if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || (recordId.longValue() == 0L)) {
/* 132 */       return;
/*     */     }
/* 134 */     Portallinkrecord linkRecord = portallinkrecordService
/* 135 */       .getPortallinkrecordByKey(recordId);
/* 136 */     Portalaccount account = portalaccountService
/* 137 */       .getPortalaccountByKey(userId);
/* 138 */     String state = account.getState();
/*     */ 
/* 140 */     long in = Long.valueOf(loginInfo[8]).longValue();
/* 141 */     long out = Long.valueOf(loginInfo[9]).longValue();
/* 142 */     long octets = in + out;
/* 143 */     linkRecord.setIns(Long.valueOf(out));
/* 144 */     linkRecord.setOuts(Long.valueOf(in));
/* 145 */     linkRecord.setOctets(Long.valueOf(octets));
/* 146 */     Date now = new Date();
/* 147 */     linkRecord.setEndDate(now);
/* 148 */     Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
/* 149 */     linkRecord.setTime(costTime);
/* 150 */     linkRecord.setState(state);
/* 151 */     linkRecord.setEx1("Portal");
/* 152 */     linkRecord.setEx2("用户下线");
/* 153 */     portallinkrecordService.updatePortallinkrecordByKey(linkRecord);
/*     */ 
/* 155 */     if (!state.equals("1")) {
/* 156 */       Long haveTime = account.getTime();
/* 157 */       Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
/* 158 */       if ((state.equals("3")) && 
/* 159 */         (account.getDate().getTime() <= now.getTime())) {
/* 160 */         account.setState("2");
/* 161 */         portalaccountService.updatePortalaccountByKey(account);
/*     */       }
/*     */ 
/* 164 */       if (state.equals("2")) {
/* 165 */         if (newHaveTime.longValue() <= 0L) {
/* 166 */           account.setState("4");
/*     */         }
/* 168 */         account.setTime(newHaveTime);
/* 169 */         portalaccountService.updatePortalaccountByKey(account);
/*     */       }
/* 171 */       if ((state.equals("4")) || (state.equals("0"))) {
/* 172 */         long haveOctets = account.getOctets().longValue() - octets;
/* 173 */         if (haveOctets <= 0L) {
/* 174 */           account.setState("0");
/*     */         }
/* 176 */         account.setOctets(Long.valueOf(haveOctets));
/* 177 */         portalaccountService.updatePortalaccountByKey(account);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void doLogRecord(String info)
/*     */   {
/* 184 */     PortallogrecordService portallogrecordService = (PortallogrecordService)
/* 185 */       SpringContextHelper.getBean("portallogrecordServiceImpl");
/* 186 */     Portallogrecord logRecord = new Portallogrecord();
/* 187 */     Date nowDate = new Date();
/* 188 */     logRecord.setInfo(info);
/* 189 */     logRecord.setRecDate(nowDate);
/* 190 */     portallogrecordService.addPortallogrecord(logRecord);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.WifidogKick
 * JD-Core Version:    0.6.2
 */