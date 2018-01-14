/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalip;
/*     */ import com.leeson.core.query.PortalipQuery;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.PortalipService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import com.leeson.core.utils.IPv4Util;
/*     */ import java.util.Arrays;
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
/*     */ public class PortalipController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalipService portalipService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalwebService portalwebService;
/*     */ 
/*     */   @RequestMapping({"/portalip/list.action"})
/*     */   public String page(PortalipQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  44 */     query.setNameLike(true);
/*  45 */     query.setDescriptionLike(true);
/*  46 */     if (stringUtils.isBlank(query.getName())) {
/*  47 */       query.setName(null);
/*     */     }
/*  49 */     if (stringUtils.isBlank(query.getDescription())) {
/*  50 */       query.setDescription(null);
/*     */     }
/*  52 */     Pagination pagination = this.portalipService.getPortalipListWithPage(query);
/*  53 */     model.addAttribute("pagination", pagination);
/*  54 */     model.addAttribute("query", query);
/*     */ 
/*  56 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  57 */     model.addAttribute("webs", webs);
/*  58 */     return "portalip/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalip/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  64 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  65 */     model.addAttribute("webs", webs);
/*  66 */     return "portalip/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalip/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalip e, ModelMap model)
/*     */   {
/*  72 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  73 */     model.addAttribute("webs", webs);
/*     */ 
/*  75 */     PortalipQuery q = new PortalipQuery();
/*  76 */     q.setName(e.getName());
/*  77 */     q.setNameLike(false);
/*  78 */     if (this.portalipService.getPortalipList(q).size() > 0) {
/*  79 */       model.addAttribute("msg", "该名称已经存在！");
/*  80 */       model.addAttribute("entity", e);
/*  81 */       return "portalip/save";
/*     */     }
/*     */ 
/*  84 */     if (!IPv4Util.isIP(e.getStart())) {
/*  85 */       model.addAttribute("msg", "开始IP非法!");
/*  86 */       model.addAttribute("entity", e);
/*  87 */       return "portalip/save";
/*     */     }
/*  89 */     if (!IPv4Util.isIP(e.getEnd())) {
/*  90 */       model.addAttribute("msg", "结束IP非法!");
/*  91 */       model.addAttribute("entity", e);
/*  92 */       return "portalip/save";
/*     */     }
/*     */     try {
/*  95 */       long startL = IPv4Util.ipToLong(e.getStart());
/*  96 */       long endL = IPv4Util.ipToLong(e.getEnd());
/*  97 */       if (startL > endL) {
/*  98 */         model.addAttribute("msg", "IP范围错误!");
/*  99 */         model.addAttribute("entity", e);
/* 100 */         return "portalip/save";
/*     */       }
/*     */ 
/* 103 */       List<Portalip> ipList = this.portalipService.getPortalipList(new PortalipQuery());
/* 104 */       for (Portalip portalip : ipList) {
/* 105 */         long startH = IPv4Util.ipToLong(portalip.getStart());
/* 106 */         long endH = IPv4Util.ipToLong(portalip.getEnd());
/* 107 */         if ((startL >= startH) && (startL <= endH)) {
/* 108 */           model.addAttribute("msg", "IP范围已存在!");
/* 109 */           model.addAttribute("entity", e);
/* 110 */           return "portalip/save";
/*     */         }
/* 112 */         if ((endL >= startH) && (endL <= endH)) {
/* 113 */           model.addAttribute("msg", "IP范围已存在!");
/* 114 */           model.addAttribute("entity", e);
/* 115 */           return "portalip/save";
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 119 */       model.addAttribute("msg", "IP地址错误!");
/* 120 */       model.addAttribute("entity", e);
/* 121 */       return "portalip/save";
/*     */     }
/*     */ 
/* 124 */     this.portalipService.addPortalip(e);
/*     */ 
/* 126 */     return "redirect:/portalip/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalip/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/* 132 */     Portalip e = this.portalipService.getPortalipByKey(id);
/* 133 */     model.addAttribute("entity", e);
/* 134 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 135 */     model.addAttribute("webs", webs);
/* 136 */     return "portalip/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalip/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalip e, ModelMap model)
/*     */   {
/* 142 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 143 */     model.addAttribute("webs", webs);
/*     */ 
/* 145 */     PortalipQuery q = new PortalipQuery();
/* 146 */     q.setName(e.getName());
/* 147 */     q.setNameLike(false);
/* 148 */     List ips = this.portalipService.getPortalipList(q);
/* 149 */     if ((ips != null) && (ips.size() > 0) && 
/* 150 */       (((Portalip)ips.get(0)).getId() != e.getId())) {
/* 151 */       model.addAttribute("msg", "该名称已经存在！");
/* 152 */       model.addAttribute("entity", e);
/* 153 */       return "portalip/save";
/*     */     }
/*     */ 
/* 157 */     if (!IPv4Util.isIP(e.getStart())) {
/* 158 */       model.addAttribute("msg", "开始IP非法!");
/* 159 */       model.addAttribute("entity", e);
/* 160 */       return "portalip/save";
/*     */     }
/* 162 */     if (!IPv4Util.isIP(e.getEnd())) {
/* 163 */       model.addAttribute("msg", "结束IP非法!");
/* 164 */       model.addAttribute("entity", e);
/* 165 */       return "portalip/save";
/*     */     }
/*     */     try {
/* 168 */       long startL = IPv4Util.ipToLong(e.getStart());
/* 169 */       long endL = IPv4Util.ipToLong(e.getEnd());
/* 170 */       if (startL > endL) {
/* 171 */         model.addAttribute("msg", "IP范围错误!");
/* 172 */         model.addAttribute("entity", e);
/* 173 */         return "portalip/save";
/*     */       }
/*     */ 
/* 176 */       List<Portalip> iplList = this.portalipService.getPortalipList(new PortalipQuery());
/* 177 */       for (Portalip portalip : iplList)
/* 178 */         if (portalip.getId() != e.getId()) {
/* 179 */           long startH = IPv4Util.ipToLong(portalip.getStart());
/* 180 */           long endH = IPv4Util.ipToLong(portalip.getEnd());
/* 181 */           if ((startL >= startH) && (startL <= endH)) {
/* 182 */             model.addAttribute("msg", "IP范围已存在!");
/* 183 */             model.addAttribute("entity", e);
/* 184 */             return "portalip/save";
/*     */           }
/* 186 */           if ((endL >= startH) && (endL <= endH)) {
/* 187 */             model.addAttribute("msg", "IP范围已存在!");
/* 188 */             model.addAttribute("entity", e);
/* 189 */             return "portalip/save";
/*     */           }
/*     */         }
/*     */     }
/*     */     catch (Exception ex) {
/* 194 */       model.addAttribute("msg", "IP地址错误!");
/* 195 */       model.addAttribute("entity", e);
/* 196 */       return "portalip/save";
/*     */     }
/* 198 */     this.portalipService.updatePortalipByKey(e);
/* 199 */     return "redirect:/portalip/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalip/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 207 */     this.portalipService.deleteByKey(id);
/* 208 */     return "redirect:/portalip/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalip/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 215 */     List list = Arrays.asList(ids);
/* 216 */     this.portalipService.deleteByKeys(list);
/*     */ 
/* 218 */     return "redirect:/portalip/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalip/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 224 */     this.portalipService.deleteAll();
/* 225 */     return "redirect:/portalip/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalipController
 * JD-Core Version:    0.6.2
 */