/*     */ package com.leeson.portal.core.controller.wifidog;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortallinkrecordService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.core.utils.WifidogKick;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AuthController extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = -8404975286682591703L;
/*  40 */   private static Config config = Config.getInstance();
/*     */ 
/*  42 */   private static Logger logger = Logger.getLogger(AuthController.class);
/*     */ 
/*     */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  59 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  60 */       logger.info("WifiDog Auth : " + request.getRequestURL().toString() + "?" + request.getQueryString());
/*     */     }
/*     */ 
/*  63 */     String stage = request.getParameter("stage");
/*  64 */     String token = request.getParameter("token");
/*     */ 
/*  66 */     if ("logout".equals(stage)) {
/*  67 */       WifidogKick.ApKickUser(token.trim());
/*  68 */       System.out.println("=============================logout");
/*  69 */       response.getOutputStream().write("Auth: 1".getBytes());
/*     */     } else {
/*  71 */       String ip = request.getParameter("ip");
/*  72 */       String mac = request.getParameter("mac");
/*  73 */       mac = PortalUtil.MacFormat(mac);
/*  74 */       String incoming = request.getParameter("incoming");
/*  75 */       String outgoing = request.getParameter("outgoing");
/*     */ 
/*  77 */       String[] loginInfo = null;
/*  78 */       loginInfo = 
/*  79 */         (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
/*  79 */         .get(token);
/*  80 */       if (loginInfo != null) {
/*  81 */         loginInfo[4] = ip;
/*  82 */         loginInfo[5] = mac;
/*  83 */         loginInfo[8] = incoming;
/*  84 */         loginInfo[9] = outgoing;
/*     */ 
/*  86 */         if ("login".equals(stage)) {
/*  87 */           String username = loginInfo[0];
/*  88 */           String time = loginInfo[3];
/*  89 */           String userIDStr = loginInfo[1];
/*  90 */           String basip = loginInfo[7];
/*  91 */           Date loginTime = null;
/*     */           try {
/*  93 */             loginTime = ThreadLocalDateUtil.parse(time);
/*     */           } catch (ParseException e) {
/*  95 */             loginTime = new Date();
/*  96 */             logger.error("WifiDog Auth Login DataParse Error : ", e);
/*     */           }
/*     */ 
/*  99 */           if (stringUtils.isNotBlank(userIDStr)) {
/* 100 */             Long userID = Long.valueOf(userIDStr);
/* 101 */             PortalaccountService accountService = (PortalaccountService)SpringContextHelper.getBean("portalaccountServiceImpl");
/* 102 */             String userState = accountService.getPortalaccountByKey(userID).getState();
/* 103 */             Long linkRecordID = doLinkRecord(username, ip, basip, mac, userState, userID, loginTime);
/* 104 */             loginInfo[2] = String.valueOf(linkRecordID);
/*     */           }
/*     */ 
/* 107 */           PortallogrecordService logRecordService = (PortallogrecordService)SpringContextHelper.getBean("portallogrecordServiceImpl");
/* 108 */           Portallogrecord logRecord = new Portallogrecord();
/*     */ 
/* 110 */           logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + mac + 
/* 111 */             " 用户: " + username + ",wifidog登录成功!");
/* 112 */           logRecord.setRecDate(loginTime);
/* 113 */           logRecordService.addPortallogrecord(logRecord);
/*     */         }
/*     */ 
/* 116 */         WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
/* 117 */           .put(token, loginInfo);
/* 118 */         System.out.println("=============================auth: 1");
/* 119 */         response.getOutputStream().write("Auth: 1".getBytes());
/*     */       } else {
/* 121 */         System.out.println("=============================auth: 0");
/* 122 */         response.getOutputStream().write("Auth: 0".getBytes());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Long doLinkRecord(String username, String ip, String basip, String mac, String userState, Long userID, Date loginTime)
/*     */   {
/* 141 */     Portallinkrecord linkRecord = new Portallinkrecord();
/* 142 */     linkRecord.setStartDate(loginTime);
/* 143 */     linkRecord.setIp(ip);
/* 144 */     linkRecord.setBasip(basip);
/* 145 */     linkRecord.setMac(mac);
/* 146 */     linkRecord.setIns(Long.valueOf(0L));
/* 147 */     linkRecord.setOuts(Long.valueOf(0L));
/* 148 */     linkRecord.setOctets(Long.valueOf(0L));
/* 149 */     linkRecord.setLoginName(username);
/* 150 */     linkRecord.setState(userState);
/* 151 */     linkRecord.setUid(userID);
/* 152 */     PortallinkrecordService linkRecordService = (PortallinkrecordService)SpringContextHelper.getBean("portallinkrecordServiceImpl");
/* 153 */     linkRecordService.addPortallinkrecord(linkRecord);
/* 154 */     return linkRecord.getId();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.AuthController
 * JD-Core Version:    0.6.2
 */