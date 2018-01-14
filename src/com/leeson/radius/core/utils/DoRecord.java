/*     */ package com.leeson.radius.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Config;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.bean.Radiuslinkrecordall;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortallinkrecordService;
/*     */ import com.leeson.core.service.RadiuslinkrecordallService;
/*     */ import com.leeson.portal.core.model.RadiusAPIMap;
/*     */ import com.leeson.portal.core.utils.HttpRequest;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import com.leeson.radius.core.Tool;
/*     */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ 
/*     */ public class DoRecord
/*     */ {
/*  26 */   private static Logger log = Logger.getLogger(DoRecord.class);
/*     */ 
/*  29 */   private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
/*  30 */   private static PortallinkrecordService portallinkrecordService = (PortallinkrecordService)ac
/*  31 */     .getBean("portallinkrecordServiceImpl");
/*     */ 
/*  32 */   private static PortalaccountService portalaccountService = (PortalaccountService)ac
/*  33 */     .getBean("portalaccountServiceImpl");
/*     */ 
/*  34 */   private static RadiuslinkrecordallService radiuslinkallService = (RadiuslinkrecordallService)ac
/*  35 */     .getBean("radiuslinkrecordallServiceImpl");
/*     */ 
/*  36 */   private static ConfigService configService = (ConfigService)
/*  37 */     SpringContextHelper.getBean("configServiceImpl");
/*     */ 
/*     */   public static void coreMethod(String AcctSessionId, String logInfo)
/*     */   {
/*  66 */     if (stringUtils.isNotBlank(AcctSessionId)) {
/*     */       try {
/*  68 */         String[] radiusOnlineInfo = 
/*  69 */           (String[])RadiusOnlineMap.getInstance()
/*  69 */           .getRadiusOnlineMap().get(AcctSessionId);
/*  70 */         if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0)) {
/*  71 */           doLinkAll(radiusOnlineInfo, logInfo);
/*  72 */           Tool.writeLog(logInfo, " NasIP:" + radiusOnlineInfo[0] + 
/*  73 */             " UserIP: " + radiusOnlineInfo[2] + " Mac: " + 
/*  74 */             radiusOnlineInfo[3] + " UserName: " + 
/*  75 */             radiusOnlineInfo[4] + " !");
/*  76 */           if (1 == configService.getConfigByKey(Long.valueOf(1L)).getRadiusOn().intValue()) {
/*  77 */             PortalaccountQuery aq = new PortalaccountQuery();
/*  78 */             aq.setLoginName(radiusOnlineInfo[4]);
/*  79 */             aq.setLoginNameLike(false);
/*  80 */             List users = portalaccountService
/*  81 */               .getPortalaccountList(aq);
/*  82 */             if ((users != null) && (users.size() > 0)) {
/*  83 */               Portalaccount account = (Portalaccount)users.get(0);
/*  84 */               String state = account.getState();
/*  85 */               Date now = new Date();
/*  86 */               Date loginTime = ThreadLocalDateUtil.parse(radiusOnlineInfo[9]);
/*  87 */               Long costTime = Long.valueOf(now.getTime() - loginTime.getTime());
/*  88 */               long in = 0L;
/*  89 */               long out = 0L;
/*     */               try {
/*  91 */                 in = Long.valueOf(radiusOnlineInfo[11]).longValue();
/*  92 */                 out = Long.valueOf(radiusOnlineInfo[12]).longValue();
/*     */               } catch (Exception localException1) {
/*     */               }
/*  95 */               long octets = in + out;
/*  96 */               Portallinkrecord linkRecord = new Portallinkrecord();
/*  97 */               linkRecord.setStartDate(loginTime);
/*  98 */               linkRecord.setIp(radiusOnlineInfo[2]);
/*  99 */               linkRecord.setBasip(radiusOnlineInfo[0]);
/* 100 */               linkRecord.setMac(radiusOnlineInfo[3]);
/* 101 */               linkRecord.setIns(Long.valueOf(in));
/* 102 */               linkRecord.setOuts(Long.valueOf(out));
/* 103 */               linkRecord.setOctets(Long.valueOf(octets));
/* 104 */               linkRecord.setLoginName(radiusOnlineInfo[4]);
/* 105 */               linkRecord.setState(state);
/* 106 */               linkRecord.setUid(account.getId());
/* 107 */               linkRecord.setEndDate(now);
/* 108 */               linkRecord.setTime(costTime);
/* 109 */               linkRecord.setEx1("Radius");
/* 110 */               linkRecord.setEx2(logInfo);
/* 111 */               portallinkrecordService
/* 112 */                 .addPortallinkrecord(linkRecord);
/* 113 */               if (!state.equals("1")) {
/* 114 */                 Long haveTime = account.getTime();
/* 115 */                 Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
/* 116 */                 if ((state.equals("3")) && 
/* 117 */                   (account.getDate().getTime() <= now.getTime())) {
/* 118 */                   account.setState("2");
/* 119 */                   portalaccountService.updatePortalaccountByKey(account);
/*     */                 }
/*     */ 
/* 122 */                 if (state.equals("2")) {
/* 123 */                   if (newHaveTime.longValue() <= 0L) {
/* 124 */                     account.setState("4");
/*     */                   }
/* 126 */                   account.setTime(newHaveTime);
/* 127 */                   portalaccountService.updatePortalaccountByKey(account);
/*     */                 }
/* 129 */                 if ((state.equals("4")) || (state.equals("0"))) {
/* 130 */                   long haveOctets = account.getOctets().longValue() - octets;
/* 131 */                   if (haveOctets <= 0L) {
/* 132 */                     account.setState("0");
/*     */                   }
/* 134 */                   account.setOctets(Long.valueOf(haveOctets));
/* 135 */                   portalaccountService.updatePortalaccountByKey(account);
/*     */                 }
/*     */               }
/*     */ 
/* 139 */               RadiusOnlineMap.getInstance().getRadiusOnlineMap().remove(AcctSessionId);
/*     */               try
/*     */               {
/* 142 */                 String radiusUrl = (String)RadiusAPIMap.getInstance().getRadiusAPIMap().get("url");
/* 143 */                 String radiusState = (String)RadiusAPIMap.getInstance().getRadiusAPIMap().get("state");
/* 144 */                 if ((stringUtils.isNotBlank(radiusState)) && (stringUtils.isNotBlank(radiusUrl)) && ("1".equals(radiusState))) {
/* 145 */                   String params = "u=" + radiusOnlineInfo[4] + "&ip=" + radiusOnlineInfo[2] + "&mac=" + radiusOnlineInfo[3] + "&t=" + costTime + "&out=" + out + "&in=" + in + "&oct=" + octets;
/* 146 */                   HttpRequest.sendPost(radiusUrl, params);
/*     */                 }
/*     */               }
/*     */               catch (Exception localException2) {
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 156 */         log.error("==============ERROR Start=============");
/* 157 */         log.error(e);
/* 158 */         log.error("ERROR INFO ", e);
/* 159 */         log.error("==============ERROR End=============");
/*     */       }
/* 161 */       RadiusOnlineMap.getInstance().getRadiusOnlineMap()
/* 162 */         .remove(AcctSessionId);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void doLinkAll(String[] loginInfo, String info) {
/*     */     try {
/* 168 */       if (loginInfo != null) {
/* 169 */         String time = loginInfo[9];
/* 170 */         Date loginTime = new Date();
/* 171 */         String nowString = ThreadLocalDateUtil.format(new Date());
/* 172 */         Date nowTime = new Date();
/*     */         try {
/* 174 */           loginTime = ThreadLocalDateUtil.parse(time);
/* 175 */           nowTime = ThreadLocalDateUtil.parse(nowString);
/*     */         } catch (Exception localException1) {
/*     */         }
/* 178 */         Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 179 */         long in = 0L;
/* 180 */         long out = 0L;
/*     */         try {
/* 182 */           in = Long.valueOf(loginInfo[11]).longValue();
/* 183 */           out = Long.valueOf(loginInfo[12]).longValue();
/*     */         } catch (Exception localException2) {
/*     */         }
/* 186 */         long octets = in + out;
/* 187 */         Radiuslinkrecordall linkAll = new Radiuslinkrecordall();
/* 188 */         linkAll.setTime(costTime);
/* 189 */         linkAll.setStartDate(loginTime);
/* 190 */         linkAll.setEndDate(nowTime);
/* 191 */         linkAll.setName(loginInfo[4]);
/* 192 */         linkAll.setState(loginInfo[15]);
/* 193 */         linkAll.setAcctsessionid(loginInfo[13]);
/* 194 */         linkAll.setCallingstationid(loginInfo[3]);
/* 195 */         linkAll.setIns(Long.valueOf(in));
/* 196 */         linkAll.setOuts(Long.valueOf(out));
/* 197 */         linkAll.setOctets(Long.valueOf(octets));
/* 198 */         linkAll.setNasip(loginInfo[0]);
/* 199 */         linkAll.setSourceip(loginInfo[1]);
/* 200 */         linkAll.setUserip(loginInfo[2]);
/* 201 */         linkAll.setEx1(loginInfo[8]);
/* 202 */         linkAll.setEx2(info);
/* 203 */         linkAll.setEx3(loginInfo[16]);
/* 204 */         radiuslinkallService.addRadiuslinkrecordall(linkAll);
/*     */       }
/*     */     } catch (Exception e) {
/* 207 */       log.error("==============ERROR Start=============");
/* 208 */       log.error(e);
/* 209 */       log.error("ERROR INFO ", e);
/* 210 */       log.error("==============ERROR End=============");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.DoRecord
 * JD-Core Version:    0.6.2
 */