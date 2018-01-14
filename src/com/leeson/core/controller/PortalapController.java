/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalap;
/*     */ import com.leeson.core.query.PortalapQuery;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.PortalapService;
/*     */ import com.leeson.core.service.PortalwebService;
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
/*     */ public class PortalapController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalapService portalapService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalwebService portalwebService;
/*     */ 
/*     */   @RequestMapping({"/portalap/list.action"})
/*     */   public String page(PortalapQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  42 */     query.setIpLike(true);
/*  43 */     query.setMacLike(true);
/*  44 */     query.setAddressLike(true);
/*  45 */     query.setNameLike(true);
/*  46 */     if (stringUtils.isBlank(query.getIp())) {
/*  47 */       query.setIp(null);
/*     */     }
/*  49 */     if (stringUtils.isBlank(query.getMac())) {
/*  50 */       query.setMac(null);
/*     */     }
/*  52 */     if (stringUtils.isBlank(query.getAddress())) {
/*  53 */       query.setAddress(null);
/*     */     }
/*  55 */     if (stringUtils.isBlank(query.getName())) {
/*  56 */       query.setName(null);
/*     */     }
/*  58 */     Pagination pagination = this.portalapService.getPortalapListWithPage(query);
/*  59 */     model.addAttribute("pagination", pagination);
/*  60 */     model.addAttribute("query", query);
/*     */ 
/*  62 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  63 */     model.addAttribute("webs", webs);
/*  64 */     return "portalap/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalap/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  70 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  71 */     model.addAttribute("webs", webs);
/*  72 */     return "portalap/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalap/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalap e, ModelMap model)
/*     */   {
/*  78 */     if (stringUtils.isBlank(e.getMac())) {
/*  79 */       model.addAttribute("msg", "MAC不能为空！！");
/*  80 */       model.addAttribute("entity", e);
/*  81 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  82 */       model.addAttribute("webs", webs);
/*  83 */       return "portalap/save";
/*     */     }
/*  85 */     PortalapQuery apq = new PortalapQuery();
/*  86 */     apq.setMac(e.getMac());
/*  87 */     if (this.portalapService.getPortalapList(apq).size() > 0) {
/*  88 */       model.addAttribute("msg", "该MAC的AP已经存在！");
/*  89 */       e.setMac(null);
/*  90 */       model.addAttribute("entity", e);
/*  91 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  92 */       model.addAttribute("webs", webs);
/*  93 */       return "portalap/save";
/*     */     }
/*  95 */     this.portalapService.addPortalap(e);
/*  96 */     return "redirect:/portalap/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalap/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/* 102 */     Portalap e = this.portalapService.getPortalapByKey(id);
/* 103 */     model.addAttribute("entity", e);
/* 104 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 105 */     model.addAttribute("webs", webs);
/* 106 */     return "portalap/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalap/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalap e, ModelMap model)
/*     */   {
/* 112 */     if (stringUtils.isBlank(e.getMac())) {
/* 113 */       model.addAttribute("msg", "MAC不能为空！！");
/* 114 */       model.addAttribute("entity", e);
/* 115 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 116 */       model.addAttribute("webs", webs);
/* 117 */       return "portalap/save";
/*     */     }
/* 119 */     PortalapQuery apq = new PortalapQuery();
/* 120 */     apq.setMac(e.getMac());
/* 121 */     List aps = this.portalapService.getPortalapList(apq);
/* 122 */     if ((aps.size() > 0) && 
/* 123 */       (((Portalap)aps.get(0)).getId() != e.getId())) {
/* 124 */       model.addAttribute("msg", "该MAC的AP已经存在！");
/* 125 */       e.setMac(null);
/* 126 */       model.addAttribute("entity", e);
/* 127 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 128 */       model.addAttribute("webs", webs);
/* 129 */       return "portalap/save";
/*     */     }
/*     */ 
/* 132 */     this.portalapService.updatePortalapByKey(e);
/* 133 */     return "redirect:/portalap/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalap/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 141 */     this.portalapService.deleteByKey(id);
/* 142 */     return "redirect:/portalap/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalap/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 149 */     List list = Arrays.asList(ids);
/* 150 */     this.portalapService.deleteByKeys(list);
/* 151 */     return "redirect:/portalap/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalapController
 * JD-Core Version:    0.6.2
 */