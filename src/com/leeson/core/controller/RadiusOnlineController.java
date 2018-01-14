/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.RadiusOnlineInfo;
/*     */ import com.leeson.radius.core.RadiusCOA;
/*     */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*     */ import com.leeson.radius.core.utils.DoRecord;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
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
/*     */ public class RadiusOnlineController
/*     */ {
/*  30 */   private static DecimalFormat df = new DecimalFormat(".##");
/*     */ 
/*     */   @RequestMapping({"/radiusOnline/list.action"})
/*     */   public String pageOnline(String nasnameQ, String ipQ, String loginNameQ, String macQ, Integer Page, ModelMap model)
/*     */     throws ParseException
/*     */   {
/*  36 */     int count = 0;
/*  37 */     Iterator it = RadiusOnlineMap.getInstance()
/*  38 */       .getRadiusOnlineMap().keySet().iterator();
/*  39 */     while (it.hasNext()) {
/*  40 */       Object o = it.next();
/*  41 */       String acctSessionId = o.toString();
/*  42 */       String[] loginInfo = 
/*  43 */         (String[])RadiusOnlineMap.getInstance()
/*  43 */         .getRadiusOnlineMap().get(acctSessionId);
/*  44 */       String ip = loginInfo[2];
/*  45 */       String mac = loginInfo[3];
/*  46 */       String username = loginInfo[4];
/*  47 */       String nasname = loginInfo[16];
/*  48 */       Boolean isIt = Boolean.valueOf(true);
/*  49 */       if ((stringUtils.isNotBlank(nasnameQ)) && 
/*  50 */         (!nasname.contains(nasnameQ))) {
/*  51 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  54 */       if ((stringUtils.isNotBlank(ipQ)) && 
/*  55 */         (!ip.contains(ipQ))) {
/*  56 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  59 */       if ((stringUtils.isNotBlank(loginNameQ)) && 
/*  60 */         (!username.contains(loginNameQ))) {
/*  61 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  64 */       if ((stringUtils.isNotBlank(macQ)) && 
/*  65 */         (!mac.contains(macQ))) {
/*  66 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/*  70 */       if (stringUtils.isBlank(nasnameQ)) {
/*  71 */         nasnameQ = null;
/*     */       }
/*  73 */       if (stringUtils.isBlank(ipQ)) {
/*  74 */         ipQ = null;
/*     */       }
/*  76 */       if (stringUtils.isBlank(loginNameQ)) {
/*  77 */         loginNameQ = null;
/*     */       }
/*  79 */       if (stringUtils.isBlank(macQ)) {
/*  80 */         macQ = null;
/*     */       }
/*  82 */       if (isIt.booleanValue()) {
/*  83 */         count++;
/*     */       }
/*     */     }
/*     */ 
/*  87 */     List onlineInfos = new ArrayList();
/*  88 */     if ((Page == null) || (Page.intValue() <= 0)) {
/*  89 */       Page = Integer.valueOf(1);
/*     */     }
/*  91 */     Integer id = Integer.valueOf(1);
/*  92 */     Iterator iterator = RadiusOnlineMap.getInstance()
/*  93 */       .getRadiusOnlineMap().keySet().iterator();
/*  94 */     while (iterator.hasNext()) {
/*  95 */       RadiusOnlineInfo onlineInfo = new RadiusOnlineInfo();
/*  96 */       Object o = iterator.next();
/*  97 */       String acctSessionId = o.toString();
/*  98 */       String[] loginInfo = 
/*  99 */         (String[])RadiusOnlineMap.getInstance()
/*  99 */         .getRadiusOnlineMap().get(acctSessionId);
/* 100 */       String ip = loginInfo[2];
/* 101 */       String mac = loginInfo[3];
/* 102 */       String username = loginInfo[4];
/* 103 */       String nasname = loginInfo[16];
/* 104 */       Boolean isIt = Boolean.valueOf(true);
/* 105 */       if ((stringUtils.isNotBlank(nasnameQ)) && 
/* 106 */         (!nasname.contains(nasnameQ))) {
/* 107 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 110 */       if ((stringUtils.isNotBlank(ipQ)) && 
/* 111 */         (!ip.contains(ipQ))) {
/* 112 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 115 */       if ((stringUtils.isNotBlank(loginNameQ)) && 
/* 116 */         (!username.contains(loginNameQ))) {
/* 117 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 120 */       if ((stringUtils.isNotBlank(macQ)) && 
/* 121 */         (!mac.contains(macQ))) {
/* 122 */         isIt = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 127 */       if (stringUtils.isBlank(nasnameQ)) {
/* 128 */         nasnameQ = null;
/*     */       }
/* 130 */       if (stringUtils.isBlank(ipQ)) {
/* 131 */         ipQ = null;
/*     */       }
/* 133 */       if (stringUtils.isBlank(loginNameQ)) {
/* 134 */         loginNameQ = null;
/*     */       }
/* 136 */       if (stringUtils.isBlank(macQ)) {
/* 137 */         macQ = null;
/*     */       }
/* 139 */       if (isIt.booleanValue())
/*     */       {
/* 141 */         if (id.intValue() > (Page.intValue() - 1) * 10) {
/* 142 */           String inS = "0 M";
/* 143 */           String outS = "0 M";
/* 144 */           String allS = "0 M";
/* 145 */           String octetsS = "0 M";
/*     */           try {
/* 147 */             double in = Double.valueOf(loginInfo[11]).doubleValue();
/* 148 */             double out = Double.valueOf(loginInfo[12]).doubleValue();
/* 149 */             double all = Double.valueOf(loginInfo[7]).doubleValue();
/* 150 */             double octets = in + out;
/* 151 */             in /= 1048576.0D;
/* 152 */             out /= 1048576.0D;
/* 153 */             all /= 1048576.0D;
/* 154 */             octets /= 1048576.0D;
/* 155 */             inS = df.format(in);
/* 156 */             outS = df.format(out);
/* 157 */             allS = df.format(all);
/* 158 */             octetsS = df.format(octets);
/* 159 */             if (inS.startsWith(".")) {
/* 160 */               inS = "0" + inS;
/*     */             }
/* 162 */             if (outS.startsWith(".")) {
/* 163 */               outS = "0" + outS;
/*     */             }
/* 165 */             if (allS.startsWith(".")) {
/* 166 */               allS = "0" + allS;
/*     */             }
/* 168 */             if (octetsS.startsWith(".")) {
/* 169 */               octetsS = "0" + octetsS;
/*     */             }
/* 171 */             inS = inS + "M";
/* 172 */             outS = outS + "M";
/* 173 */             allS = allS + "M";
/* 174 */             octetsS = octetsS + " M";
/*     */           }
/*     */           catch (Exception localException) {
/*     */           }
/* 178 */           onlineInfo.setNasIP(loginInfo[0]);
/* 179 */           onlineInfo.setIp(loginInfo[1]);
/* 180 */           onlineInfo.setUserIP(ip);
/* 181 */           onlineInfo.setCallingStationId(mac);
/* 182 */           onlineInfo.setName(username);
/* 183 */           onlineInfo.setSharedSecret(loginInfo[5]);
/* 184 */           onlineInfo.setSessionTime(loginInfo[6]);
/* 185 */           onlineInfo.setOctets(allS);
/* 186 */           onlineInfo.setClientType(loginInfo[8]);
/* 187 */           onlineInfo.setStartDate(loginInfo[9]);
/* 188 */           onlineInfo.setCostTime(loginInfo[10]);
/* 189 */           onlineInfo.setInS(inS);
/* 190 */           onlineInfo.setOutS(outS);
/* 191 */           onlineInfo.setCostOctets(octetsS);
/* 192 */           onlineInfo.setAcctSessionId(acctSessionId);
/* 193 */           onlineInfo.setState(loginInfo[15]);
/* 194 */           onlineInfo.setUpdateDate(loginInfo[14]);
/* 195 */           onlineInfo.setNasname(loginInfo[16]);
/*     */ 
/* 197 */           onlineInfo.setId(id);
/* 198 */           onlineInfos.add(onlineInfo);
/*     */         }
/*     */ 
/* 201 */         id = Integer.valueOf(id.intValue() + 1);
/* 202 */         if (id.intValue() > Page.intValue() * 10)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 208 */     Pagination pagination = new Pagination(Page.intValue(), 10, count, onlineInfos);
/* 209 */     model.addAttribute("pagination", pagination);
/* 210 */     model.addAttribute("ipQ", ipQ);
/* 211 */     model.addAttribute("loginNameQ", loginNameQ);
/* 212 */     model.addAttribute("macQ", macQ);
/* 213 */     model.addAttribute("nasnameQ", nasnameQ);
/* 214 */     return "radiusOnline/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiusOnline/kick.action"})
/*     */   public String kick(@RequestParam String acctSessionId)
/*     */   {
/* 220 */     RadiusCOA.req_COA(
/* 221 */       (String[])RadiusOnlineMap.getInstance()
/* 221 */       .getRadiusOnlineMap().get(acctSessionId), "Radius Admin Kick COA");
/*     */ 
/* 224 */     return "redirect:/radiusOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiusOnline/kicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String kicks(@RequestParam String[] acctSessionIds)
/*     */   {
/* 230 */     for (String acctSessionId : acctSessionIds) {
/* 231 */       RadiusCOA.req_COA(
/* 232 */         (String[])RadiusOnlineMap.getInstance()
/* 232 */         .getRadiusOnlineMap().get(acctSessionId), "Radius Admin Kick COA");
/*     */     }
/*     */ 
/* 236 */     return "redirect:/radiusOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiusOnline/kicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String kicks()
/*     */   {
/* 242 */     Iterator iterator = RadiusOnlineMap.getInstance()
/* 243 */       .getRadiusOnlineMap().keySet().iterator();
/* 244 */     while (iterator.hasNext()) {
/* 245 */       Object o = iterator.next();
/* 246 */       String acctSessionId = o.toString();
/* 247 */       RadiusCOA.req_COA(
/* 248 */         (String[])RadiusOnlineMap.getInstance()
/* 248 */         .getRadiusOnlineMap().get(acctSessionId), "Radius Admin Kick COA");
/*     */     }
/*     */ 
/* 252 */     return "redirect:/radiusOnline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiusOnline/deleteOnline.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deleteOnline()
/*     */   {
/* 258 */     Iterator iterator = RadiusOnlineMap.getInstance()
/* 259 */       .getRadiusOnlineMap().keySet().iterator();
/* 260 */     while (iterator.hasNext()) {
/* 261 */       Object o = iterator.next();
/* 262 */       String acctSessionId = o.toString();
/* 263 */       DoRecord.coreMethod(acctSessionId, "Radius Admin Delete");
/*     */     }
/* 265 */     return "redirect:/radiusOnline/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiusOnlineController
 * JD-Core Version:    0.6.2
 */