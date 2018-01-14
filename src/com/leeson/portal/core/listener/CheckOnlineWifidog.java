/*     */ package com.leeson.portal.core.listener;
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
/*     */ import java.net.InetAddress;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ 
/*     */ public class CheckOnlineWifidog extends Thread
/*     */ {
/*  28 */   private static Config config = Config.getInstance();
/*  29 */   private static Logger log = Logger.getLogger(CheckOnlineWifidog.class);
/*     */ 
/*  34 */   ApplicationContext ac = SpringContextHelper.getApplicationContext();
/*     */ 
/*  36 */   private static WifiDogOnlineMap onlineMap = WifiDogOnlineMap.getInstance();
/*  37 */   private Map<String, String[]> onlineUserMap = onlineMap.getWifiDogOnlineMap();
/*     */   private String host;
/*     */   private String username;
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
/*     */   public CheckOnlineWifidog(String host, String username, String[] loginInfo)
/*     */   {
/*  55 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  56 */     this.isHave = this.onlineUserMap.containsKey(host);
/*  57 */     if (this.isHave) {
/*  58 */       this.host = host;
/*  59 */       this.username = username;
/*  60 */       this.loginInfo = loginInfo;
/*     */ 
/*  62 */       if (basConfig.getAuthInterface().contains("1")) {
/*  63 */         String uid = loginInfo[1];
/*  64 */         String rid = loginInfo[2];
/*  65 */         if ((!stringUtils.isBlank(uid)) && (!stringUtils.isBlank(rid)))
/*     */         {
/*  68 */           this.userId = Long.valueOf(Long.parseLong(loginInfo[1]));
/*  69 */           this.recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
/*     */ 
/*  71 */           this.linkRecordService = 
/*  72 */             ((PortallinkrecordService)this.ac
/*  72 */             .getBean("portallinkrecordServiceImpl"));
/*  73 */           this.linkRecord = this.linkRecordService
/*  74 */             .getPortallinkrecordByKey(this.recordId);
/*  75 */           this.accountService = 
/*  76 */             ((PortalaccountService)this.ac
/*  76 */             .getBean("portalaccountServiceImpl"));
/*  77 */           this.account = this.accountService.getPortalaccountByKey(this.userId);
/*  78 */           this.state = this.account.getState();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  87 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  88 */     if ((this.isHave) && (basConfig.getAuthInterface().contains("1")) && 
/*  89 */       (this.account != null) && (this.linkRecord != null) && 
/*  90 */       (this.state != null) && (!this.state.equals("")))
/*  91 */       startCheckOverTime();
/*     */   }
/*     */ 
/*     */   private boolean startCheckOverTime()
/*     */   {
/*  97 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  98 */     if (basConfig.getIsdebug().equals("1")) {
/*  99 */       log.info("wifidog开始检测余额   网络地址: " + this.loginInfo[4] + "登录名: " + this.username);
/*     */     }
/* 101 */     if ((!this.state.equals("1")) && 
/* 102 */       (!doCheckOverTime())) {
/* 103 */       if (basConfig.getIsdebug().equals("1")) {
/* 104 */         log.info("wifidog IP:" + this.loginInfo[4] + "账户" + this.username + 
/* 105 */           "余额不足，强制下线成功！！");
/*     */       }
/* 107 */       doLogRecord("IP:" + this.loginInfo[4] + "账户" + this.username + 
/* 108 */         "余额不足，强制下线成功！！");
/* 109 */       return false;
/*     */     }
/*     */ 
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean doCheckOverTime()
/*     */   {
/* 118 */     Date now = new Date();
/* 119 */     Long costTime = Long.valueOf(now.getTime() - this.linkRecord.getStartDate().getTime());
/* 120 */     Date toDate = this.account.getDate();
/* 121 */     Long haveTime = this.account.getTime();
/* 122 */     Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
/* 123 */     long in = Long.valueOf(this.loginInfo[8]).longValue();
/* 124 */     long out = Long.valueOf(this.loginInfo[9]).longValue();
/* 125 */     long octets = in + out;
/*     */ 
/* 127 */     if ((this.state.equals("3")) && 
/* 128 */       (toDate.getTime() <= now.getTime())) {
/* 129 */       this.onlineUserMap.remove(this.host);
/* 130 */       this.linkRecord.setEndDate(now);
/* 131 */       this.linkRecord.setTime(costTime);
/* 132 */       this.linkRecord.setState(this.state);
/* 133 */       this.linkRecord.setIns(Long.valueOf(out));
/* 134 */       this.linkRecord.setOuts(Long.valueOf(in));
/* 135 */       this.linkRecord.setOctets(Long.valueOf(octets));
/* 136 */       this.linkRecord.setEx1("Portal");
/* 137 */       this.linkRecord.setEx2("买断过期");
/* 138 */       this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
/* 139 */       this.account.setState("2");
/* 140 */       this.accountService.updatePortalaccountByKey(this.account);
/* 141 */       return false;
/*     */     }
/*     */ 
/* 144 */     if ((this.state.equals("2")) && 
/* 145 */       (newHaveTime.longValue() <= 0L)) {
/* 146 */       this.onlineUserMap.remove(this.host);
/* 147 */       this.account.setTime(newHaveTime);
/* 148 */       this.account.setState("4");
/* 149 */       this.accountService.updatePortalaccountByKey(this.account);
/* 150 */       this.linkRecord.setEndDate(now);
/* 151 */       this.linkRecord.setTime(costTime);
/* 152 */       this.linkRecord.setState(this.state);
/* 153 */       this.linkRecord.setIns(Long.valueOf(out));
/* 154 */       this.linkRecord.setOuts(Long.valueOf(in));
/* 155 */       this.linkRecord.setOctets(Long.valueOf(octets));
/* 156 */       this.linkRecord.setEx1("Portal");
/* 157 */       this.linkRecord.setEx2("时长不足");
/* 158 */       this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
/* 159 */       return false;
/*     */     }
/*     */ 
/* 162 */     if (((this.state.equals("4")) || (this.state.equals("0"))) && 
/* 163 */       (this.account.getOctets().longValue() <= octets)) {
/*     */       try {
/* 165 */         this.account.setState("0");
/* 166 */         this.account.setOctets(Long.valueOf(0L));
/* 167 */         this.accountService.updatePortalaccountByKey(this.account);
/* 168 */         this.linkRecord.setEndDate(now);
/* 169 */         this.linkRecord.setTime(costTime);
/* 170 */         this.linkRecord.setState(this.state);
/* 171 */         this.linkRecord.setIns(Long.valueOf(out));
/* 172 */         this.linkRecord.setOuts(Long.valueOf(in));
/* 173 */         this.linkRecord.setOctets(Long.valueOf(octets));
/* 174 */         this.linkRecord.setEx1("Portal");
/* 175 */         this.linkRecord.setEx2("流量不足");
/* 176 */         this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
/*     */       } catch (Exception localException) {
/*     */       }
/* 179 */       return false;
/*     */     }
/*     */ 
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */   private void doLogRecord(String info) {
/* 186 */     Portallogrecord logRecord = new Portallogrecord();
/* 187 */     Date nowDate = new Date();
/* 188 */     logRecord.setInfo(info);
/* 189 */     logRecord.setRecDate(nowDate);
/* 190 */     PortallogrecordService logRecordService = (PortallogrecordService)this.ac
/* 191 */       .getBean("portallogrecordServiceImpl");
/* 192 */     logRecordService.addPortallogrecord(logRecord);
/*     */   }
/*     */ 
/*     */   public boolean doPing()
/*     */   {
/* 197 */     boolean a = false;
/*     */     try
/*     */     {
/* 200 */       String ip = this.loginInfo[4];
/* 201 */       int timeOut = Integer.parseInt(((Portalbas)config.getConfigMap().get("")).getTimeoutSec());
/* 202 */       a = InetAddress.getByName(ip).isReachable(timeOut * 1000);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 206 */       e.printStackTrace();
/* 207 */       return a;
/*     */     }
/* 209 */     return a;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.CheckOnlineWifidog
 * JD-Core Version:    0.6.2
 */