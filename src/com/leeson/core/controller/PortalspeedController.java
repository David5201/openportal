/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalspeed;
/*     */ import com.leeson.core.query.PortalspeedQuery;
/*     */ import com.leeson.core.service.PortalspeedService;
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
/*     */ public class PortalspeedController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalspeedService portalspeedService;
/*     */ 
/*     */   @RequestMapping({"/portalspeed/list.action"})
/*     */   public String page(PortalspeedQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  37 */     if (stringUtils.isBlank(query.getName())) {
/*  38 */       query.setName(null);
/*     */     }
/*  40 */     Pagination pagination = this.portalspeedService.getPortalspeedListWithPage(query);
/*  41 */     model.addAttribute("pagination", pagination);
/*  42 */     model.addAttribute("query", query);
/*  43 */     return "portalspeed/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalspeed/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  49 */     return "portalspeed/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalspeed/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalspeed e, ModelMap model)
/*     */   {
/*  55 */     if (stringUtils.isBlank(e.getName())) {
/*  56 */       model.addAttribute("msg", "请填写名称！");
/*  57 */       model.addAttribute("entity", e);
/*  58 */       return "portalspeed/save";
/*     */     }
/*  60 */     this.portalspeedService.addPortalspeed(e);
/*  61 */     return "redirect:/portalspeed/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalspeed/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/*  67 */     Portalspeed e = this.portalspeedService.getPortalspeedByKey(id);
/*  68 */     model.addAttribute("entity", e);
/*  69 */     return "portalspeed/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalspeed/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalspeed e, ModelMap model)
/*     */   {
/*  75 */     if (stringUtils.isBlank(e.getName())) {
/*  76 */       model.addAttribute("msg", "请填写名称！");
/*  77 */       model.addAttribute("entity", e);
/*  78 */       return "portalspeed/save";
/*     */     }
/*  80 */     this.portalspeedService.updatePortalspeedByKey(e);
/*  81 */     return "redirect:/portalspeed/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalspeed/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/*  89 */     if (id.longValue() != 1L) {
/*  90 */       this.portalspeedService.deleteByKey(id);
/*     */     }
/*     */ 
/*  93 */     return "redirect:/portalspeed/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalspeed/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 100 */     List list = Arrays.asList(ids);
/* 101 */     if (list.contains(Long.valueOf(1L))) {
/* 102 */       list.remove(Long.valueOf(1L));
/*     */     }
/* 104 */     this.portalspeedService.deleteByKeys(list);
/*     */ 
/* 106 */     return "redirect:/portalspeed/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalspeedController
 * JD-Core Version:    0.6.2
 */