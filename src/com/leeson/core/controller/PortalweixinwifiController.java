/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Config;
/*     */ import com.leeson.core.bean.Portalweixinwifi;
/*     */ import com.leeson.core.query.PortalweixinwifiQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalweixinwifiService;
/*     */ import com.leeson.portal.core.model.WySlot15gasa;
/*     */ import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalweixinwifiController
/*     */ {
/*  37 */   private static SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */   @Autowired
/*     */   private PortalweixinwifiService portalweixinwifiService;
/*     */ 
/*     */   @Autowired
/*     */   private ConfigService configService;
/*     */ 
/*  47 */   @RequestMapping({"/portalweixinwifi/edit"})
/*     */   public String show(@RequestParam Long id, ModelMap model) { Portalweixinwifi e = this.portalweixinwifiService
/*  48 */       .getPortalweixinwifiByKey(id);
/*  49 */     Portalweixinwifi temp = this.portalweixinwifiService
/*  50 */       .getPortalweixinwifiByKey(Long.valueOf(1L));
/*  51 */     String domain = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
/*  52 */     Long outTime = temp.getOutTime();
/*  53 */     model.addAttribute("domain", domain);
/*  54 */     model.addAttribute("outTime", outTime);
/*  55 */     model.addAttribute("entity", e);
/*  56 */     if (id.longValue() == 1L) {
/*  57 */       model.addAttribute("isTemp", Integer.valueOf(1));
/*     */     }
/*  59 */     return "portalweixinwifi/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalweixinwifi/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalweixinwifi e, ModelMap model)
/*     */   {
/*  65 */     Portalweixinwifi temp = this.portalweixinwifiService
/*  66 */       .getPortalweixinwifiByKey(Long.valueOf(1L));
/*  67 */     String domainT = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
/*  68 */     Long outTimeT = temp.getOutTime();
/*  69 */     model.addAttribute("domain", domainT);
/*  70 */     model.addAttribute("outTime", outTimeT);
/*     */ 
/*  72 */     e.setAppId(e.getAppId().trim());
/*  73 */     e.setBasip(e.getBasip().trim());
/*  74 */     e.setSecretKey(e.getSecretKey().trim());
/*  75 */     e.setShopId(e.getShopId().trim());
/*  76 */     e.setSsid(e.getSsid().trim());
/*     */ 
/*  78 */     Integer count = this.portalweixinwifiService.getPortalweixinwifiCount(new PortalweixinwifiQuery());
/*  79 */     if ((count != null) && 
/*  80 */       (count.intValue() > getAP())) {
/*  81 */       model.addAttribute("msg", "系统未授权，只能添加1个设置！!");
/*  82 */       model.addAttribute("entity", e);
/*  83 */       return "portalweixinwifi/save";
/*     */     }
/*     */ 
/*  87 */     if ((stringUtils.isBlank(e.getBasip())) || (stringUtils.isBlank(e.getAppId())) || (stringUtils.isBlank(e.getDomain())) || (stringUtils.isBlank(e.getSecretKey())) || (stringUtils.isBlank(e.getShopId())) || (stringUtils.isBlank(e.getSsid()))) {
/*  88 */       model.addAttribute("msg", "所有信息不能为空！！");
/*  89 */       model.addAttribute("entity", e);
/*  90 */       return "portalweixinwifi/save";
/*     */     }
/*  92 */     PortalweixinwifiQuery q = new PortalweixinwifiQuery();
/*  93 */     q.setSsid(e.getSsid());
/*  94 */     q.setSsidLike(false);
/*  95 */     q.setBasip(e.getBasip());
/*  96 */     q.setBasipLike(false);
/*  97 */     List wxwfs = this.portalweixinwifiService.getPortalweixinwifiList(q);
/*  98 */     if ((wxwfs.size() > 0) && 
/*  99 */       (((Portalweixinwifi)wxwfs.get(0)).getId() != e.getId())) {
/* 100 */       model.addAttribute("msg", "该BasIP下此SSID已经存在！");
/* 101 */       model.addAttribute("entity", e);
/* 102 */       return "portalweixinwifi/save";
/*     */     }
/*     */ 
/* 106 */     e.setDomain(domainT);
/* 107 */     if ((e.getOutTime() == null) || (e.getOutTime().longValue() < 10L)) {
/* 108 */       e.setOutTime(Long.valueOf(10L));
/*     */     }
/*     */ 
/* 111 */     this.portalweixinwifiService.updatePortalweixinwifiByKey(e);
/*     */ 
/* 113 */     Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
/* 114 */     model.addAttribute("pagination", pagination);
/* 115 */     return "portalweixinwifi/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalweixinwifi/list.action"})
/*     */   public String page(PortalweixinwifiQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 132 */     query.setSsidLike(true);
/* 133 */     query.setAppIdLike(true);
/* 134 */     query.setShopIdLike(true);
/* 135 */     query.setBasipLike(true);
/* 136 */     Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(query);
/* 137 */     model.addAttribute("pagination", pagination);
/* 138 */     model.addAttribute("query", query);
/* 139 */     return "portalweixinwifi/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalweixinwifi/add.action"})
/*     */   public String addV(ModelMap model)
/*     */   {
/* 145 */     Portalweixinwifi temp = this.portalweixinwifiService
/* 146 */       .getPortalweixinwifiByKey(Long.valueOf(1L));
/* 147 */     String domain = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
/* 148 */     Long outTime = temp.getOutTime();
/* 149 */     model.addAttribute("domain", domain);
/* 150 */     model.addAttribute("outTime", outTime);
/* 151 */     return "portalweixinwifi/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalweixinwifi/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalweixinwifi e, ModelMap model)
/*     */   {
/* 157 */     Portalweixinwifi temp = this.portalweixinwifiService
/* 158 */       .getPortalweixinwifiByKey(Long.valueOf(1L));
/* 159 */     String domainT = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
/* 160 */     Long outTimeT = temp.getOutTime();
/* 161 */     model.addAttribute("domain", domainT);
/* 162 */     model.addAttribute("outTime", outTimeT);
/*     */ 
/* 164 */     e.setAppId(e.getAppId().trim());
/* 165 */     e.setBasip(e.getBasip().trim());
/* 166 */     e.setSecretKey(e.getSecretKey().trim());
/* 167 */     e.setShopId(e.getShopId().trim());
/* 168 */     e.setSsid(e.getSsid().trim());
/*     */ 
/* 170 */     Integer count = this.portalweixinwifiService.getPortalweixinwifiCount(new PortalweixinwifiQuery());
/* 171 */     if ((count != null) && 
/* 172 */       (count.intValue() >= getAP())) {
/* 173 */       model.addAttribute("msg", "系统未授权，只能添加1个设置！!");
/* 174 */       model.addAttribute("entity", e);
/* 175 */       return "portalweixinwifi/save";
/*     */     }
/*     */ 
/* 179 */     if ((stringUtils.isBlank(e.getBasip())) || (stringUtils.isBlank(e.getAppId())) || (stringUtils.isBlank(e.getDomain())) || (stringUtils.isBlank(e.getSecretKey())) || (stringUtils.isBlank(e.getShopId())) || (stringUtils.isBlank(e.getSsid()))) {
/* 180 */       model.addAttribute("msg", "所有信息不能为空！！");
/* 181 */       model.addAttribute("entity", e);
/* 182 */       return "portalweixinwifi/save";
/*     */     }
/* 184 */     PortalweixinwifiQuery q = new PortalweixinwifiQuery();
/* 185 */     q.setSsid(e.getSsid());
/* 186 */     q.setSsidLike(false);
/* 187 */     q.setBasip(e.getBasip());
/* 188 */     q.setBasipLike(false);
/* 189 */     if (this.portalweixinwifiService.getPortalweixinwifiList(q).size() > 0) {
/* 190 */       model.addAttribute("msg", "该BasIP下此SSID已经存在！");
/* 191 */       model.addAttribute("entity", e);
/* 192 */       return "portalweixinwifi/save";
/*     */     }
/*     */ 
/* 196 */     e.setDomain(domainT);
/* 197 */     if ((e.getOutTime() == null) || (e.getOutTime().longValue() < 10L)) {
/* 198 */       e.setOutTime(Long.valueOf(10L));
/*     */     }
/* 200 */     this.portalweixinwifiService.addPortalweixinwifi(e);
/*     */ 
/* 202 */     Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
/* 203 */     model.addAttribute("pagination", pagination);
/* 204 */     return "portalweixinwifi/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalweixinwifi/delete.action"})
/*     */   public String delete(@RequestParam Long id, ModelMap model)
/*     */   {
/* 211 */     if (id.longValue() != 1L) {
/* 212 */       this.portalweixinwifiService.deleteByKey(id);
/*     */     }
/* 214 */     Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
/* 215 */     model.addAttribute("pagination", pagination);
/* 216 */     return "portalweixinwifi/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalweixinwifi/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, ModelMap model)
/*     */   {
/* 224 */     List list = Arrays.asList(ids);
/* 225 */     if (list.contains(Long.valueOf(1L))) {
/* 226 */       list.remove(Long.valueOf(1L));
/*     */     }
/* 228 */     this.portalweixinwifiService.deleteByKeys(list);
/*     */ 
/* 230 */     Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
/* 231 */     model.addAttribute("pagination", pagination);
/* 232 */     return "portalweixinwifi/list";
/*     */   }
/*     */ 
/*     */   public static int getAP()
/*     */   {
/* 238 */     int apCount = 1;
/* 239 */     Date nowDate = new Date();
/* 240 */     String nowString = fs.format(nowDate);
/*     */     try {
/* 242 */       nowDate = fs.parse(nowString);
/*     */     } catch (ParseException e) {
/* 244 */       nowDate = new Date();
/*     */     }
/* 246 */     String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = 
/* 247 */       (String)Wz3ofg0r225avuerr.getInstance()
/* 247 */       .getXr9hk0cvnsx().get("mec");
/* 248 */     if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
/* 249 */       RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
/*     */     }
/* 251 */     String[] MxMzIyRDMzMzAy = 
/* 253 */       (String[])WySlot15gasa.getInstance()
/* 252 */       .getAmkbYQX3eQjuwtnxpbjYgQGZOr()
/* 253 */       .get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
/* 254 */     if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
/*     */       Date saveDate;
/*     */       try {
/* 258 */         saveDate = fs.parse(toDateString);
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/* 260 */         saveDate = nowDate;
/*     */       }
/* 262 */       if (nowDate.getTime() < saveDate.getTime()) {
/* 263 */         apCount = Integer.valueOf(MxMzIyRDMzMzAy[1]).intValue();
/*     */       }
/*     */     }
/* 266 */     return apCount;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalweixinwifiController
 * JD-Core Version:    0.6.2
 */