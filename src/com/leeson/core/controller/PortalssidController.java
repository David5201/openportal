/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalssid;
/*     */ import com.leeson.core.query.PortalssidQuery;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.PortalssidService;
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
/*     */ public class PortalssidController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalssidService portalssidService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalwebService portalwebService;
/*     */ 
/*     */   @RequestMapping({"/portalssid/list.action"})
/*     */   public String page(PortalssidQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  42 */     query.setSsidLike(true);
/*  43 */     query.setAddressLike(true);
/*  44 */     query.setNameLike(true);
/*  45 */     if (stringUtils.isBlank(query.getSsid())) {
/*  46 */       query.setSsid(null);
/*     */     }
/*  48 */     if (stringUtils.isBlank(query.getAddress())) {
/*  49 */       query.setAddress(null);
/*     */     }
/*  51 */     if (stringUtils.isBlank(query.getName())) {
/*  52 */       query.setName(null);
/*     */     }
/*  54 */     Pagination pagination = this.portalssidService.getPortalssidListWithPage(query);
/*  55 */     model.addAttribute("pagination", pagination);
/*  56 */     model.addAttribute("query", query);
/*     */ 
/*  58 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  59 */     model.addAttribute("webs", webs);
/*  60 */     return "portalssid/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalssid/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  66 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  67 */     model.addAttribute("webs", webs);
/*  68 */     return "portalssid/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalssid/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalssid e, ModelMap model)
/*     */   {
/*  74 */     if (stringUtils.isBlank(e.getSsid())) {
/*  75 */       model.addAttribute("msg", "SSID不能为空！！");
/*  76 */       model.addAttribute("entity", e);
/*  77 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  78 */       model.addAttribute("webs", webs);
/*  79 */       return "portalssid/save";
/*     */     }
/*  81 */     PortalssidQuery apq = new PortalssidQuery();
/*  82 */     apq.setSsid(e.getSsid());
/*  83 */     if (this.portalssidService.getPortalssidList(apq).size() > 0) {
/*  84 */       model.addAttribute("msg", "该SSID已经存在！");
/*  85 */       e.setSsid(null);
/*  86 */       model.addAttribute("entity", e);
/*  87 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  88 */       model.addAttribute("webs", webs);
/*  89 */       return "portalssid/save";
/*     */     }
/*  91 */     this.portalssidService.addPortalssid(e);
/*  92 */     return "redirect:/portalssid/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalssid/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/*  98 */     Portalssid e = this.portalssidService.getPortalssidByKey(id);
/*  99 */     model.addAttribute("entity", e);
/* 100 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 101 */     model.addAttribute("webs", webs);
/* 102 */     return "portalssid/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalssid/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalssid e, ModelMap model)
/*     */   {
/* 108 */     if (stringUtils.isBlank(e.getSsid())) {
/* 109 */       model.addAttribute("msg", "SSID不能为空！！");
/* 110 */       model.addAttribute("entity", e);
/* 111 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 112 */       model.addAttribute("webs", webs);
/* 113 */       return "portalssid/save";
/*     */     }
/* 115 */     PortalssidQuery apq = new PortalssidQuery();
/* 116 */     apq.setSsid(e.getSsid());
/* 117 */     List aps = this.portalssidService.getPortalssidList(apq);
/* 118 */     if ((aps.size() > 0) && 
/* 119 */       (((Portalssid)aps.get(0)).getId() != e.getId())) {
/* 120 */       model.addAttribute("msg", "该SSID已经存在！");
/* 121 */       e.setSsid(null);
/* 122 */       model.addAttribute("entity", e);
/* 123 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 124 */       model.addAttribute("webs", webs);
/* 125 */       return "portalssid/save";
/*     */     }
/*     */ 
/* 128 */     this.portalssidService.updatePortalssidByKey(e);
/* 129 */     return "redirect:/portalssid/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalssid/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 137 */     this.portalssidService.deleteByKey(id);
/* 138 */     return "redirect:/portalssid/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalssid/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 145 */     List list = Arrays.asList(ids);
/* 146 */     this.portalssidService.deleteByKeys(list);
/* 147 */     return "redirect:/portalssid/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalssidController
 * JD-Core Version:    0.6.2
 */