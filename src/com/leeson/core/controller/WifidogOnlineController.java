/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.utils.WifidogKick;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class WifidogOnlineController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountService portalaccountService;
/*  40 */   private static Config config = Config.getInstance();
/*  41 */   private static WifiDogOnlineMap onlineMap = WifiDogOnlineMap.getInstance();
/*     */ 
/*     */   @RequestMapping({"/wifidogOnline/list.action"})
/*     */   public String pageOnline(String ipQ, String gwidQ, String loginNameQ, Integer Page, ModelMap model)
/*     */     throws ParseException
/*     */   {
/*  49 */     HashMap onlineUserMap = new HashMap();
/*  50 */     onlineUserMap.putAll(onlineMap.getWifiDogOnlineMap());
/*  51 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/*     */ 
/*  53 */     int count = 0;
/*  54 */     Iterator it = onlineUserMap.keySet().iterator();
/*  55 */     while (it.hasNext()) {
/*  56 */       Object o = it.next();
/*  57 */       String token = o.toString();
/*  58 */       String[] loginInfo = (String[])onlineUserMap.get(token);
/*  59 */       String username = loginInfo[0];
/*  60 */       String ip = loginInfo[4];
/*  61 */       String gwid = loginInfo[6];
/*  62 */       if (gwid == null) {
/*  63 */         gwid = "";
/*     */       }
/*     */ 
/*  66 */       Boolean isIt = Boolean.valueOf(true);
/*  67 */       if ((stringUtils.isNotBlank(gwidQ)) && 
/*  68 */         (!gwid.contains(gwidQ))) {
/*  69 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  72 */       if ((stringUtils.isNotBlank(ipQ)) && 
/*  73 */         (!ip.contains(ipQ))) {
/*  74 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  77 */       if ((stringUtils.isNotBlank(loginNameQ)) && 
/*  78 */         (!username.contains(loginNameQ))) {
/*  79 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  82 */       if (stringUtils.isBlank(gwidQ)) {
/*  83 */         gwidQ = null;
/*     */       }
/*  85 */       if (stringUtils.isBlank(ipQ)) {
/*  86 */         ipQ = null;
/*     */       }
/*  88 */       if (stringUtils.isBlank(loginNameQ)) {
/*  89 */         loginNameQ = null;
/*     */       }
/*  91 */       if (isIt.booleanValue()) {
/*  92 */         count++;
/*     */       }
/*     */     }
/*     */ 
/*  96 */     List onlineInfos = new ArrayList();
/*  97 */     if ((Page == null) || (Page.intValue() <= 0)) {
/*  98 */       Page = Integer.valueOf(1);
/*     */     }
/* 100 */     Integer id = Integer.valueOf(1);
/* 101 */     Iterator iterator = onlineUserMap.keySet().iterator();
/* 102 */     while (iterator.hasNext()) {
/* 103 */       OnlineInfo onlineInfo = new OnlineInfo();
/* 104 */       Object o = iterator.next();
/* 105 */       String token = o.toString();
/* 106 */       String[] loginInfo = (String[])onlineUserMap.get(token);
/* 107 */       String username = loginInfo[0];
/* 108 */       String time = loginInfo[3];
/* 109 */       String ip = loginInfo[4];
/* 110 */       String gwid = loginInfo[6];
/* 111 */       if (gwid == null) {
/* 112 */         gwid = "";
/*     */       }
/*     */ 
/* 115 */       Date loginTime = format.parse(time);
/* 116 */       String nowString = format.format(new Date());
/* 117 */       Date nowTime = format.parse(nowString);
/* 118 */       Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 119 */       costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
/* 120 */       Boolean isIt = Boolean.valueOf(true);
/* 121 */       if ((stringUtils.isNotBlank(gwidQ)) && 
/* 122 */         (!gwid.contains(gwidQ))) {
/* 123 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 126 */       if ((stringUtils.isNotBlank(ipQ)) && 
/* 127 */         (!ip.contains(ipQ))) {
/* 128 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 131 */       if ((stringUtils.isNotBlank(loginNameQ)) && 
/* 132 */         (!username.contains(loginNameQ))) {
/* 133 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 136 */       if (stringUtils.isBlank(gwidQ)) {
/* 137 */         gwidQ = null;
/*     */       }
/* 139 */       if (stringUtils.isBlank(ipQ)) {
/* 140 */         ipQ = null;
/*     */       }
/* 142 */       if (stringUtils.isBlank(loginNameQ)) {
/* 143 */         loginNameQ = null;
/*     */       }
/* 145 */       if (isIt.booleanValue())
/*     */       {
/* 147 */         if (id.intValue() > (Page.intValue() - 1) * 10) {
/* 148 */           onlineInfo.setId(id);
/* 149 */           onlineInfo.setGwid(gwid);
/* 150 */           onlineInfo.setToken(token);
/* 151 */           onlineInfo.setIp(ip);
/* 152 */           onlineInfo.setLoginName(username);
/* 153 */           onlineInfo.setStartDate(loginTime);
/* 154 */           onlineInfo.setTime(costTime);
/* 155 */           onlineInfo.setMac(loginInfo[5]);
/* 156 */           onlineInfo.setIncoming(loginInfo[8]);
/* 157 */           onlineInfo.setOutgoing(loginInfo[9]);
/* 158 */           String authInterface = ((Portalbas)config.getConfigMap().get("")).getAuthInterface();
/* 159 */           if (authInterface.contains("1")) {
/* 160 */             PortalaccountQuery aq = new PortalaccountQuery();
/* 161 */             aq.setLoginName(username);
/* 162 */             List a = this.portalaccountService
/* 163 */               .getPortalaccountList(aq);
/* 164 */             if ((a != null) && (a.size() == 1)) {
/* 165 */               onlineInfo.setState(((Portalaccount)a.get(0)).getState());
/*     */             }
/*     */           }
/* 168 */           onlineInfos.add(onlineInfo);
/*     */         }
/*     */ 
/* 171 */         id = Integer.valueOf(id.intValue() + 1);
/* 172 */         if (id.intValue() > Page.intValue() * 10)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 178 */     Pagination pagination = new Pagination(Page.intValue(), 10, count, onlineInfos);
/* 179 */     model.addAttribute("pagination", pagination);
/* 180 */     model.addAttribute("ipQ", ipQ);
/* 181 */     model.addAttribute("loginNameQ", loginNameQ);
/* 182 */     model.addAttribute("gwidQ", gwidQ);
/* 183 */     return "wifidog/listOnline";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/wifidogOnline/kick.action"})
/*     */   public String kick(@RequestParam String token)
/*     */   {
/* 189 */     WifidogKick.kickUser(token);
/* 190 */     return "redirect:/wifidogOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/wifidogOnline/kicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String kicks(@RequestParam String[] tokens)
/*     */   {
/* 196 */     for (String token : tokens) {
/* 197 */       WifidogKick.kickUser(token);
/*     */     }
/* 199 */     return "redirect:/wifidogOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/wifidogOnline/kicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String kicks()
/*     */   {
/* 205 */     HashMap OnlineUserMap = new HashMap();
/* 206 */     OnlineUserMap.putAll(onlineMap.getWifiDogOnlineMap());
/* 207 */     Iterator iterator = OnlineUserMap.keySet().iterator();
/* 208 */     while (iterator.hasNext()) {
/* 209 */       Object o = iterator.next();
/* 210 */       String token = o.toString();
/* 211 */       WifidogKick.kickUser(token);
/*     */     }
/* 213 */     return "redirect:/wifidogOnline/list.action";
/*     */   }
/*     */ 
/*     */   public class OnlineInfo
/*     */   {
/*     */     Integer id;
/*     */     String token;
/*     */     String mac;
/*     */     String gwid;
/*     */     String incoming;
/*     */     String outgoing;
/*     */     String loginName;
/*     */     Date startDate;
/*     */     String ip;
/*     */     Long time;
/*     */     String state;
/*     */ 
/*     */     public OnlineInfo()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Integer getId()
/*     */     {
/* 247 */       return this.id;
/*     */     }
/*     */     public void setId(Integer id) {
/* 250 */       this.id = id;
/*     */     }
/*     */     public String getToken() {
/* 253 */       return this.token;
/*     */     }
/*     */     public void setToken(String token) {
/* 256 */       this.token = token;
/*     */     }
/*     */     public String getMac() {
/* 259 */       return this.mac;
/*     */     }
/*     */     public void setMac(String mac) {
/* 262 */       this.mac = mac;
/*     */     }
/*     */     public String getGwid() {
/* 265 */       return this.gwid;
/*     */     }
/*     */     public void setGwid(String gwid) {
/* 268 */       this.gwid = gwid;
/*     */     }
/*     */     public String getIncoming() {
/* 271 */       return this.incoming;
/*     */     }
/*     */     public void setIncoming(String incoming) {
/* 274 */       this.incoming = incoming;
/*     */     }
/*     */     public String getOutgoing() {
/* 277 */       return this.outgoing;
/*     */     }
/*     */     public void setOutgoing(String outgoing) {
/* 280 */       this.outgoing = outgoing;
/*     */     }
/*     */     public String getLoginName() {
/* 283 */       return this.loginName;
/*     */     }
/*     */     public void setLoginName(String loginName) {
/* 286 */       this.loginName = loginName;
/*     */     }
/*     */     public Date getStartDate() {
/* 289 */       return this.startDate;
/*     */     }
/*     */     public void setStartDate(Date startDate) {
/* 292 */       this.startDate = startDate;
/*     */     }
/*     */     public String getIp() {
/* 295 */       return this.ip;
/*     */     }
/*     */     public void setIp(String ip) {
/* 298 */       this.ip = ip;
/*     */     }
/*     */     public Long getTime() {
/* 301 */       return this.time;
/*     */     }
/*     */     public void setTime(Long time) {
/* 304 */       this.time = time;
/*     */     }
/*     */     public String getState() {
/* 307 */       return this.state;
/*     */     }
/*     */     public void setState(String state) {
/* 310 */       this.state = state;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 314 */       return "OnlineInfo [id=" + this.id + ", token=" + this.token + ", mac=" + this.mac + 
/* 315 */         ", gwid=" + this.gwid + ", incoming=" + this.incoming + 
/* 316 */         ", outgoing=" + this.outgoing + ", loginName=" + this.loginName + 
/* 317 */         ", startDate=" + this.startDate + ", ip=" + this.ip + ", time=" + 
/* 318 */         this.time + ", state=" + this.state + "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.WifidogOnlineController
 * JD-Core Version:    0.6.2
 */