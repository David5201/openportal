/*     */ package com.leeson.portal.core.listener;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.controller.AjaxInterFaceController;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortallinkrecordService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.core.utils.CheckAutoLoginUtils;
/*     */ import com.leeson.core.utils.CheckTimeUtils;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.UniFiMacSiteMap;
/*     */ import com.leeson.portal.core.service.InterfaceControl;
/*     */ import com.leeson.portal.core.service.action.unifi.UniFiMethod;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ 
/*     */ public class CheckOnline extends Thread
/*     */ {
/*  37 */   private static Config config = Config.getInstance();
/*  38 */   private static Logger log = Logger.getLogger(CheckOnline.class);
/*  39 */   ApplicationContext ac = SpringContextHelper.getApplicationContext();
/*     */ 
/*  41 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*  42 */   private Map<String, String[]> onlineUserMap = onlineMap.getOnlineUserMap();
/*     */   private String host;
/*     */   private String username;
/*     */   private String ip;
/*     */   private String basip;
/*     */   private String mac;
/*     */   private Portalbas basConfig;
/*     */   String[] loginInfo;
/*     */   Long userId;
/*     */   Long recordId;
/*     */   PortallinkrecordService linkRecordService;
/*     */   PortalaccountService accountService;
/*     */   Portallinkrecord linkRecord;
/*     */   Portalaccount account;
/*     */   String state;
/*     */   boolean isHave;
/*     */ 
/*     */   public CheckOnline(String host, String username, String[] loginInfo)
/*     */   {
/*  67 */     int i = host.lastIndexOf(":");
/*  68 */     String basip = host.substring(i + 1);
/*  69 */     String ip = host.substring(0, i);
/*     */ 
/*  71 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get(basip);
/*  72 */     this.basConfig = basConfig;
/*  73 */     this.basip = basip;
/*  74 */     this.ip = ip;
/*  75 */     this.isHave = ((this.onlineUserMap.containsKey(host)) && (basConfig != null));
/*  76 */     if (this.isHave) {
/*  77 */       this.host = host;
/*  78 */       this.username = username;
/*  79 */       this.loginInfo = loginInfo;
/*  80 */       this.mac = loginInfo[4];
/*     */ 
/*  82 */       if ("1".equals(loginInfo[6])) {
/*  83 */         String uid = loginInfo[1];
/*  84 */         String rid = loginInfo[2];
/*  85 */         if ((!stringUtils.isBlank(uid)) && (!stringUtils.isBlank(rid)))
/*     */         {
/*  88 */           this.userId = Long.valueOf(Long.parseLong(loginInfo[1]));
/*  89 */           this.recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
/*     */ 
/*  91 */           this.linkRecordService = 
/*  92 */             ((PortallinkrecordService)this.ac
/*  92 */             .getBean("portallinkrecordServiceImpl"));
/*  93 */           this.linkRecord = this.linkRecordService
/*  94 */             .getPortallinkrecordByKey(this.recordId);
/*  95 */           this.accountService = 
/*  96 */             ((PortalaccountService)this.ac
/*  96 */             .getBean("portalaccountServiceImpl"));
/*  97 */           this.account = this.accountService.getPortalaccountByKey(this.userId);
/*  98 */           this.state = this.account.getState();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 107 */     String type = this.loginInfo[6];
/* 108 */     if ((this.isHave) && ("1".equals(type)) && ("1".equals(this.basConfig.getIsPortalCheck())) && 
/* 109 */       (this.account != null) && (this.linkRecord != null) && 
/* 110 */       (this.state != null) && (!this.state.equals("")))
/* 111 */       startCheckOverTime();
/*     */   }
/*     */ 
/*     */   private void startCheckOverTime()
/*     */   {
/* 117 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 118 */       log.info("Start Check isOnline , host: " + this.host + " mac: " + this.mac + 
/* 119 */         " user: " + this.username);
/*     */     }
/* 121 */     if ((!this.state.equals("1")) && 
/* 122 */       (!doCheckOverTime())) {
/* 123 */       if (this.basConfig.getBas().equals("3")) {
/* 124 */         if (stringUtils.isNotBlank(this.mac)) {
/* 125 */           String site = 
/* 126 */             (String)UniFiMacSiteMap.getInstance()
/* 126 */             .getMacSiteMap().get(this.mac);
/* 127 */           UniFiMethod.sendUnauthorization(this.mac, site, this.basip);
/*     */         }
/* 129 */       } else if ((!this.basConfig.getBas().equals("5")) && 
/* 130 */         (!this.basConfig.getBas().equals("6")) && 
/* 131 */         (!this.basConfig
/* 131 */         .getBas().equals("7")) && (!this.basConfig.getBas().equals("8"))) {
/* 132 */         InterfaceControl.Method("PORTAL_LOGINOUT", this.username, 
/* 133 */           "password", this.ip, this.basip, "");
/*     */       }
/* 135 */       CheckTimeUtils.recordTime(this.loginInfo);
/* 136 */       CheckAutoLoginUtils.recordTime(this.loginInfo);
/* 137 */       this.onlineUserMap.remove(this.host);
/* 138 */       AjaxInterFaceController.SangforLogout(this.ip, this.username);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean doCheckOverTime()
/*     */   {
/* 147 */     Date now = new Date();
/* 148 */     Long costTime = Long.valueOf(now.getTime() - this.linkRecord.getStartDate().getTime());
/* 149 */     Date toDate = this.account.getDate();
/* 150 */     Long haveTime = this.account.getTime();
/* 151 */     Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
/* 152 */     long in = Long.valueOf(this.loginInfo[7]).longValue();
/* 153 */     long out = Long.valueOf(this.loginInfo[8]).longValue();
/* 154 */     long octets = in + out;
/* 155 */     if ((this.state.equals("3")) && 
/* 156 */       (toDate.getTime() <= now.getTime())) {
/*     */       try {
/* 158 */         Kick.doLinkAll(this.loginInfo, "买断过期");
/* 159 */         this.linkRecord.setEndDate(now);
/* 160 */         this.linkRecord.setTime(costTime);
/* 161 */         this.linkRecord.setState(this.state);
/* 162 */         this.linkRecord.setIns(Long.valueOf(in));
/* 163 */         this.linkRecord.setOuts(Long.valueOf(out));
/* 164 */         this.linkRecord.setOctets(Long.valueOf(octets));
/* 165 */         this.linkRecord.setEx1("Portal");
/* 166 */         this.linkRecord.setEx2("买断过期");
/* 167 */         this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
/* 168 */         this.account.setState("2");
/* 169 */         this.accountService.updatePortalaccountByKey(this.account);
/*     */ 
/* 171 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 172 */           log.info("IP: " + this.ip + " mac: " + this.mac + " user: " + 
/* 173 */             this.username + 
/* 174 */             " , Portal Kick By User Over Date!");
/*     */         }
/* 176 */         doLogRecord("IP: " + this.host + " mac: " + this.mac + " 用户: " + 
/* 177 */           this.username + " 上线时间: " + this.loginInfo[3] + " 在线时长: " + 
/* 178 */           costTime + "分钟,买断过期！");
/*     */       } catch (Exception localException) {
/*     */       }
/* 181 */       return false;
/*     */     }
/*     */ 
/* 184 */     if ((this.state.equals("2")) && 
/* 185 */       (newHaveTime.longValue() <= 0L)) {
/*     */       try {
/* 187 */         Kick.doLinkAll(this.loginInfo, "时长不足");
/* 188 */         this.account.setTime(newHaveTime);
/* 189 */         this.accountService.updatePortalaccountByKey(this.account);
/* 190 */         this.linkRecord.setEndDate(now);
/* 191 */         this.linkRecord.setTime(costTime);
/* 192 */         this.linkRecord.setState(this.state);
/* 193 */         this.linkRecord.setIns(Long.valueOf(in));
/* 194 */         this.linkRecord.setOuts(Long.valueOf(out));
/* 195 */         this.linkRecord.setOctets(Long.valueOf(octets));
/* 196 */         this.linkRecord.setEx1("Portal");
/* 197 */         this.linkRecord.setEx2("时长不足");
/* 198 */         this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
/* 199 */         this.account.setState("4");
/* 200 */         this.accountService.updatePortalaccountByKey(this.account);
/*     */ 
/* 202 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 203 */           log.info("IP: " + this.ip + " mac: " + this.mac + " user: " + 
/* 204 */             this.username + 
/* 205 */             " , Portal Kick By User Have Not Time!");
/*     */         }
/* 207 */         doLogRecord("IP: " + this.host + " mac: " + this.mac + " 用户: " + 
/* 208 */           this.username + " 上线时间: " + this.loginInfo[3] + " 在线时长: " + 
/* 209 */           costTime + "分钟,时长不足！");
/*     */       } catch (Exception localException1) {
/*     */       }
/* 212 */       return false;
/*     */     }
/*     */ 
/* 215 */     if (((this.state.equals("4")) || (this.state.equals("0"))) && 
/* 216 */       (this.account.getOctets().longValue() <= octets)) {
/*     */       try {
/* 218 */         Kick.doLinkAll(this.loginInfo, "流量不足");
/* 219 */         this.account.setOctets(Long.valueOf(0L));
/* 220 */         this.accountService.updatePortalaccountByKey(this.account);
/* 221 */         this.linkRecord.setEndDate(now);
/* 222 */         this.linkRecord.setTime(costTime);
/* 223 */         this.linkRecord.setState(this.state);
/* 224 */         this.linkRecord.setIns(Long.valueOf(in));
/* 225 */         this.linkRecord.setOuts(Long.valueOf(out));
/* 226 */         this.linkRecord.setOctets(Long.valueOf(octets));
/* 227 */         this.linkRecord.setEx1("Portal");
/* 228 */         this.linkRecord.setEx2("流量不足");
/* 229 */         this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
/* 230 */         this.account.setState("0");
/* 231 */         this.accountService.updatePortalaccountByKey(this.account);
/*     */ 
/* 233 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 234 */           log.info("IP: " + this.ip + " mac: " + this.mac + " user: " + 
/* 235 */             this.username + 
/* 236 */             " , Portal Kick By User Have Not Octets!");
/*     */         }
/* 238 */         doLogRecord("IP: " + this.host + " mac: " + this.mac + " 用户: " + 
/* 239 */           this.username + " 上线时间: " + this.loginInfo[3] + " 在线时长: " + 
/* 240 */           costTime + "分钟,流量不足！");
/*     */       } catch (Exception localException2) {
/*     */       }
/* 243 */       return false;
/*     */     }
/*     */ 
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   private void doLogRecord(String info) {
/*     */     try {
/* 251 */       Portallogrecord logRecord = new Portallogrecord();
/* 252 */       Date nowDate = new Date();
/* 253 */       logRecord.setInfo(info);
/* 254 */       logRecord.setRecDate(nowDate);
/* 255 */       PortallogrecordService logRecordService = (PortallogrecordService)this.ac
/* 256 */         .getBean("portallogrecordServiceImpl");
/* 257 */       logRecordService.addPortallogrecord(logRecord);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean doPing() {
/* 265 */     boolean a = false;
/*     */     try {
/* 267 */       int timeOut = 3;
/* 268 */       a = InetAddress.getByName(this.ip).isReachable(timeOut * 1000);
/*     */     } catch (Exception e) {
/* 270 */       return a;
/*     */     }
/* 272 */     return a;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.CheckOnline
 * JD-Core Version:    0.6.2
 */