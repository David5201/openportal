/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.portal.core.model.WifiDogAPMap;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class WifidogAPController
/*     */ {
/*  28 */   private static WifiDogAPMap onlineMap = WifiDogAPMap.getInstance();
/*     */ 
/*     */   @RequestMapping({"/wifidogAP/list.action"})
/*     */   public String pageOnline(String ipQ, String gwidQ, Integer Page, ModelMap model)
/*     */     throws ParseException
/*     */   {
/*  34 */     HashMap APMap = new HashMap();
/*  35 */     APMap.putAll(onlineMap.getWifiDogAPMap());
/*     */ 
/*  37 */     int count = 0;
/*  38 */     Iterator it = APMap.keySet().iterator();
/*  39 */     while (it.hasNext()) {
/*  40 */       Object o = it.next();
/*  41 */       String gwid = o.toString();
/*  42 */       String[] APInfo = (String[])APMap.get(gwid);
/*  43 */       String ip = APInfo[4];
/*     */ 
/*  45 */       Boolean isIt = Boolean.valueOf(true);
/*  46 */       if ((stringUtils.isNotBlank(ipQ)) && 
/*  47 */         (!ip.contains(ipQ))) {
/*  48 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  51 */       if ((stringUtils.isNotBlank(gwidQ)) && 
/*  52 */         (!gwid.contains(gwidQ))) {
/*  53 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  56 */       if (stringUtils.isBlank(ipQ)) {
/*  57 */         ipQ = null;
/*     */       }
/*  59 */       if (stringUtils.isBlank(gwidQ)) {
/*  60 */         gwidQ = null;
/*     */       }
/*  62 */       if (isIt.booleanValue()) {
/*  63 */         count++;
/*     */       }
/*     */     }
/*     */ 
/*  67 */     List ApInfos = new ArrayList();
/*  68 */     if ((Page == null) || (Page.intValue() <= 0)) {
/*  69 */       Page = Integer.valueOf(1);
/*     */     }
/*  71 */     Integer id = Integer.valueOf(1);
/*  72 */     Iterator iterator = APMap.keySet().iterator();
/*  73 */     while (iterator.hasNext()) {
/*  74 */       ApInfo apInfo = new ApInfo();
/*  75 */       Object o = iterator.next();
/*  76 */       String gwid = o.toString();
/*  77 */       String[] APInfo = (String[])APMap.get(gwid);
/*  78 */       String ip = APInfo[4];
/*     */ 
/*  80 */       Boolean isIt = Boolean.valueOf(true);
/*  81 */       if ((stringUtils.isNotBlank(ipQ)) && 
/*  82 */         (!ip.contains(ipQ))) {
/*  83 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  86 */       if ((stringUtils.isNotBlank(gwidQ)) && 
/*  87 */         (!gwid.contains(gwidQ))) {
/*  88 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  91 */       if (stringUtils.isBlank(ipQ)) {
/*  92 */         ipQ = null;
/*     */       }
/*  94 */       if (stringUtils.isBlank(gwidQ)) {
/*  95 */         gwidQ = null;
/*     */       }
/*  97 */       if (isIt.booleanValue())
/*     */       {
/*  99 */         if (id.intValue() > (Page.intValue() - 1) * 10) {
/* 100 */           apInfo.setId(id);
/* 101 */           apInfo.setGw_id(gwid);
/* 102 */           apInfo.setIp(ip);
/* 103 */           apInfo.setTime(APInfo[5]);
/* 104 */           apInfo.setSys_uptime(APInfo[0]);
/* 105 */           apInfo.setSys_memfree(APInfo[1]);
/* 106 */           apInfo.setSys_load(APInfo[2]);
/* 107 */           apInfo.setWifidog_uptime(APInfo[3]);
/* 108 */           ApInfos.add(apInfo);
/*     */         }
/*     */ 
/* 111 */         id = Integer.valueOf(id.intValue() + 1);
/* 112 */         if (id.intValue() > Page.intValue() * 10)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 118 */     Pagination pagination = new Pagination(Page.intValue(), 10, count, ApInfos);
/* 119 */     model.addAttribute("pagination", pagination);
/* 120 */     model.addAttribute("ipQ", ipQ);
/* 121 */     model.addAttribute("gwidQ", gwidQ);
/* 122 */     return "wifidog/listAP";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/wifidogAP/delete.action"})
/*     */   public String kick(@RequestParam String gwid)
/*     */   {
/* 128 */     WifiDogAPMap.getInstance().getWifiDogAPMap().remove(gwid);
/* 129 */     return "redirect:/wifidogAP/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/wifidogAP/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String kicks(@RequestParam String[] gwids)
/*     */   {
/* 135 */     for (String gwid : gwids) {
/* 136 */       WifiDogAPMap.getInstance().getWifiDogAPMap().remove(gwid);
/*     */     }
/* 138 */     return "redirect:/wifidogAP/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/wifidogAP/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String kicks()
/*     */   {
/* 144 */     WifiDogAPMap.getInstance().getWifiDogAPMap().clear();
/* 145 */     return "redirect:/wifidogAP/list.action";
/*     */   }
/*     */   public class ApInfo {
/*     */     Integer id;
/*     */     String gw_id;
/*     */     String ip;
/*     */     String time;
/*     */     String sys_uptime;
/*     */     String sys_memfree;
/*     */     String sys_load;
/*     */     String wifidog_uptime;
/*     */ 
/*     */     public ApInfo() {
/*     */     }
/*     */     public Integer getId() {
/* 167 */       return this.id;
/*     */     }
/*     */ 
/*     */     public void setId(Integer id) {
/* 171 */       this.id = id;
/*     */     }
/*     */ 
/*     */     public String getGw_id() {
/* 175 */       return this.gw_id;
/*     */     }
/*     */ 
/*     */     public void setGw_id(String gw_id) {
/* 179 */       this.gw_id = gw_id;
/*     */     }
/*     */ 
/*     */     public String getIp() {
/* 183 */       return this.ip;
/*     */     }
/*     */ 
/*     */     public void setIp(String ip) {
/* 187 */       this.ip = ip;
/*     */     }
/*     */ 
/*     */     public String getTime() {
/* 191 */       return this.time;
/*     */     }
/*     */ 
/*     */     public void setTime(String time) {
/* 195 */       this.time = time;
/*     */     }
/*     */ 
/*     */     public String getSys_uptime() {
/* 199 */       return this.sys_uptime;
/*     */     }
/*     */ 
/*     */     public void setSys_uptime(String sys_uptime) {
/* 203 */       this.sys_uptime = sys_uptime;
/*     */     }
/*     */ 
/*     */     public String getSys_memfree() {
/* 207 */       return this.sys_memfree;
/*     */     }
/*     */ 
/*     */     public void setSys_memfree(String sys_memfree) {
/* 211 */       this.sys_memfree = sys_memfree;
/*     */     }
/*     */ 
/*     */     public String getSys_load() {
/* 215 */       return this.sys_load;
/*     */     }
/*     */ 
/*     */     public void setSys_load(String sys_load) {
/* 219 */       this.sys_load = sys_load;
/*     */     }
/*     */ 
/*     */     public String getWifidog_uptime() {
/* 223 */       return this.wifidog_uptime;
/*     */     }
/*     */ 
/*     */     public void setWifidog_uptime(String wifidog_uptime) {
/* 227 */       this.wifidog_uptime = wifidog_uptime;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 232 */       return "ApInfo [id=" + this.id + ", gw_id=" + this.gw_id + ", ip=" + this.ip + 
/* 233 */         ", time=" + this.time + ", sys_uptime=" + this.sys_uptime + 
/* 234 */         ", sys_memfree=" + this.sys_memfree + ", sys_load=" + this.sys_load + 
/* 235 */         ", wifidog_uptime=" + this.wifidog_uptime + "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.WifidogAPController
 * JD-Core Version:    0.6.2
 */