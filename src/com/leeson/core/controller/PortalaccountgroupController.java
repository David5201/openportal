/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccountgroup;
/*     */ import com.leeson.core.query.PortalaccountgroupQuery;
/*     */ import com.leeson.core.query.PortalspeedQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalaccountgroupService;
/*     */ import com.leeson.core.service.PortalspeedService;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalaccountgroupController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountgroupService portalaccountgroupService;
/*     */ 
/*     */   @Autowired
/*     */   private ConfigService configService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalspeedService portalspeedService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountService portalaccountService;
/*  49 */   private static SimpleDateFormat format = new SimpleDateFormat(
/*  50 */     "yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   @RequestMapping({"/portalaccountgroup/list.action"})
/*     */   public String page(PortalaccountgroupQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  58 */     query.setNameLike(true);
/*  59 */     if (stringUtils.isBlank(query.getName())) {
/*  60 */       query.setName(null);
/*     */     }
/*  62 */     if (stringUtils.isBlank(query.getState())) {
/*  63 */       query.setState(null);
/*     */     }
/*     */ 
/*  66 */     Pagination pagination = this.portalaccountgroupService
/*  67 */       .getPortalaccountgroupListWithPage(query);
/*  68 */     model.addAttribute("pagination", pagination);
/*  69 */     model.addAttribute("query", query);
/*     */ 
/*  71 */     List speeds = this.portalspeedService
/*  72 */       .getPortalspeedList(new PortalspeedQuery());
/*  73 */     model.addAttribute("speeds", speeds);
/*  74 */     return "portalaccountgroup/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccountgroup/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  80 */     List speeds = this.portalspeedService
/*  81 */       .getPortalspeedList(new PortalspeedQuery());
/*  82 */     model.addAttribute("speeds", speeds);
/*     */ 
/*  84 */     return "portalaccountgroup/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccountgroup/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalaccountgroup e, String dateS, ModelMap model)
/*     */   {
/*  90 */     List speeds = this.portalspeedService
/*  91 */       .getPortalspeedList(new PortalspeedQuery());
/*  92 */     model.addAttribute("speeds", speeds);
/*     */ 
/*  94 */     if (stringUtils.isBlank(e.getName())) {
/*  95 */       model.addAttribute("msg", "名称不能为空！");
/*  96 */       model.addAttribute("entity", e);
/*  97 */       return "portalaccountgroup/save";
/*     */     }
/*     */ 
/* 100 */     PortalaccountgroupQuery aq = new PortalaccountgroupQuery();
/* 101 */     aq.setName(e.getName());
/* 102 */     aq.setNameLike(false);
/* 103 */     if (this.portalaccountgroupService.getPortalaccountgroupList(aq).size() > 0) {
/* 104 */       model.addAttribute("msg", "名称已经存在！");
/* 105 */       model.addAttribute("entity", e);
/* 106 */       return "portalaccountgroup/save";
/*     */     }
/*     */ 
/* 109 */     if (stringUtils.isBlank(e.getState())) {
/* 110 */       e.setState("0");
/*     */     }
/*     */ 
/* 113 */     if (e.getMaclimitcount() == null) {
/* 114 */       e.setMaclimitcount(Integer.valueOf(1));
/*     */     }
/*     */ 
/* 117 */     if (e.getTime() == null)
/* 118 */       e.setTime(Long.valueOf(0L));
/*     */     else {
/* 120 */       e.setTime(Long.valueOf(e.getTime().longValue() * 1000L * 60L));
/*     */     }
/* 122 */     if (e.getOctets() == null)
/* 123 */       e.setOctets(Long.valueOf(0L));
/*     */     else {
/* 125 */       e.setOctets(Long.valueOf(e.getOctets().longValue() * 1024L * 1024L));
/*     */     }
/* 127 */     if (stringUtils.isBlank(dateS)) {
/* 128 */       e.setDate(new Date());
/*     */     } else {
/* 130 */       Date date = new Date();
/*     */       try {
/* 132 */         date = format.parse(dateS + " 23:59:59");
/*     */       } catch (Exception ex) {
/* 134 */         date = new Date();
/*     */       }
/* 136 */       e.setDate(date);
/*     */     }
/*     */ 
/* 139 */     if (e.getOctetsLimit() == null)
/* 140 */       e.setOctetsLimit(Long.valueOf(0L));
/*     */     else {
/* 142 */       e.setOctetsLimit(Long.valueOf(e.getOctetsLimit().longValue() * 1024L * 1024L));
/*     */     }
/*     */ 
/* 145 */     if (e.getTimeLimit() == null)
/* 146 */       e.setTimeLimit(Long.valueOf(0L));
/*     */     else {
/* 148 */       e.setTimeLimit(Long.valueOf(e.getTimeLimit().longValue() * 60L * 1000L));
/*     */     }
/*     */ 
/* 151 */     this.portalaccountgroupService.addPortalaccountgroup(e);
/*     */ 
/* 153 */     return "redirect:/portalaccountgroup/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccountgroup/editV.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/* 159 */     Portalaccountgroup e = this.portalaccountgroupService
/* 160 */       .getPortalaccountgroupByKey(id);
/* 161 */     model.addAttribute("entity", e);
/*     */ 
/* 163 */     List speeds = this.portalspeedService
/* 164 */       .getPortalspeedList(new PortalspeedQuery());
/* 165 */     model.addAttribute("speeds", speeds);
/*     */ 
/* 167 */     return "portalaccountgroup/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccountgroup/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalaccountgroup e, String dateS, ModelMap model)
/*     */   {
/* 173 */     if (e.getTime() == null)
/* 174 */       e.setTime(Long.valueOf(0L));
/*     */     else {
/* 176 */       e.setTime(Long.valueOf(e.getTime().longValue() * 1000L * 60L));
/*     */     }
/* 178 */     if (e.getOctets() == null)
/* 179 */       e.setOctets(Long.valueOf(0L));
/*     */     else {
/* 181 */       e.setOctets(Long.valueOf(e.getOctets().longValue() * 1024L * 1024L));
/*     */     }
/* 183 */     if (stringUtils.isBlank(dateS)) {
/* 184 */       e.setDate(new Date());
/*     */     } else {
/* 186 */       Date date = new Date();
/*     */       try {
/* 188 */         date = format.parse(dateS + " 23:59:59");
/*     */       } catch (Exception ex) {
/* 190 */         date = new Date();
/*     */       }
/* 192 */       e.setDate(date);
/*     */     }
/*     */ 
/* 195 */     if (e.getOctetsLimit() == null)
/* 196 */       e.setOctetsLimit(Long.valueOf(0L));
/*     */     else {
/* 198 */       e.setOctetsLimit(Long.valueOf(e.getOctetsLimit().longValue() * 1024L * 1024L));
/*     */     }
/*     */ 
/* 201 */     if (e.getTimeLimit() == null)
/* 202 */       e.setTimeLimit(Long.valueOf(0L));
/*     */     else {
/* 204 */       e.setTimeLimit(Long.valueOf(e.getTimeLimit().longValue() * 60L * 1000L));
/*     */     }
/* 206 */     this.portalaccountgroupService.updatePortalaccountgroupByKey(e);
/*     */ 
/* 248 */     return "redirect:/portalaccountgroup/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccountgroup/edit.action"})
/*     */   public String editState(@RequestParam Long id, @RequestParam String to)
/*     */   {
/* 258 */     Portalaccountgroup account = this.portalaccountgroupService
/* 259 */       .getPortalaccountgroupByKey(id);
/* 260 */     if (to.equals("state")) {
/* 261 */       int state = Integer.valueOf(account.getState()).intValue() + 1;
/* 262 */       if (state >= 5) {
/* 263 */         state = 0;
/*     */       }
/* 265 */       account.setState(String.valueOf(state));
/*     */     }
/*     */ 
/* 272 */     if (to.equals("maclimit")) {
/* 273 */       if (account.getMaclimit().intValue() == 0)
/* 274 */         account.setMaclimit(Integer.valueOf(1));
/*     */       else {
/* 276 */         account.setMaclimit(Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 284 */     if (to.equals("auto")) {
/* 285 */       if (account.getAutologin().intValue() == 0)
/* 286 */         account.setAutologin(Integer.valueOf(1));
/*     */       else {
/* 288 */         account.setAutologin(Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 296 */     if (to.equals("autoKick")) {
/* 297 */       if (account.getAutoKick().intValue() == 0)
/* 298 */         account.setAutoKick(Integer.valueOf(1));
/*     */       else {
/* 300 */         account.setAutoKick(Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 309 */     if (to.equals("clearHaveAll")) {
/* 310 */       if (account.getClearHaveAll().intValue() == 0)
/* 311 */         account.setClearHaveAll(Integer.valueOf(1));
/*     */       else {
/* 313 */         account.setClearHaveAll(Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 321 */     if (to.equals("clearHaveLimit")) {
/* 322 */       if (account.getClearHaveLimit().intValue() == 0)
/* 323 */         account.setClearHaveLimit(Integer.valueOf(1));
/*     */       else {
/* 325 */         account.setClearHaveLimit(Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 334 */     this.portalaccountgroupService.updatePortalaccountgroupByKey(account);
/* 335 */     return "redirect:/portalaccountgroup/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccountgroup/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 341 */     if (id.longValue() != 1L) {
/* 342 */       this.portalaccountgroupService.deleteByKey(id);
/*     */     }
/* 344 */     return "redirect:/portalaccountgroup/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccountgroup/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 351 */     List list = Arrays.asList(ids);
/* 352 */     if (list.contains(Long.valueOf(1L))) {
/* 353 */       list.remove(Long.valueOf(1L));
/*     */     }
/* 355 */     this.portalaccountgroupService.deleteByKeys(list);
/*     */ 
/* 357 */     return "redirect:/portalaccountgroup/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalaccountgroupController
 * JD-Core Version:    0.6.2
 */