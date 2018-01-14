/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalcardcategory;
/*     */ import com.leeson.core.query.PortalcardcategoryQuery;
/*     */ import com.leeson.core.query.PortalspeedQuery;
/*     */ import com.leeson.core.service.PortalcardcategoryService;
/*     */ import com.leeson.core.service.PortalspeedService;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalcardcategoryController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardcategoryService portalcardcategoryService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalspeedService portalspeedService;
/*     */ 
/*     */   @RequestMapping({"/portalcardcategory/list.action"})
/*     */   public String page(PortalcardcategoryQuery query, ModelMap model)
/*     */   {
/*  39 */     query.setNameLike(true);
/*  40 */     query.setDescriptionLike(true);
/*  41 */     if (stringUtils.isBlank(query.getName())) {
/*  42 */       query.setName(null);
/*     */     }
/*  44 */     if (stringUtils.isBlank(query.getDescription())) {
/*  45 */       query.setDescription(null);
/*     */     }
/*  47 */     if (stringUtils.isBlank(query.getState())) {
/*  48 */       query.setState(null);
/*     */     }
/*  50 */     List speeds = this.portalspeedService
/*  51 */       .getPortalspeedList(new PortalspeedQuery());
/*  52 */     model.addAttribute("speeds", speeds);
/*  53 */     Pagination pagination = this.portalcardcategoryService.getPortalcardcategoryListWithPage(query);
/*  54 */     model.addAttribute("pagination", pagination);
/*  55 */     model.addAttribute("query", query);
/*  56 */     return "portalcardcategory/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcardcategory/addV.action"})
/*     */   public String addV(ModelMap model)
/*     */   {
/*  62 */     List speeds = this.portalspeedService
/*  63 */       .getPortalspeedList(new PortalspeedQuery());
/*  64 */     model.addAttribute("speeds", speeds);
/*  65 */     return "portalcardcategory/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalcardcategory/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalcardcategory e, ModelMap model)
/*     */   {
/*  71 */     if ((stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getState())) || (e.getTime() == null) || (e.getTime().longValue() == 0L)) {
/*  72 */       model.addAttribute("msg", "名称 类型和计数不能为空！");
/*  73 */       model.addAttribute("entity", e);
/*  74 */       List speeds = this.portalspeedService
/*  75 */         .getPortalspeedList(new PortalspeedQuery());
/*  76 */       model.addAttribute("speeds", speeds);
/*  77 */       return "portalcardcategory/save";
/*     */     }
/*  79 */     if (e.getMoney() == null) {
/*  80 */       model.addAttribute("msg", "售价不能为空！");
/*  81 */       model.addAttribute("entity", e);
/*  82 */       List speeds = this.portalspeedService
/*  83 */         .getPortalspeedList(new PortalspeedQuery());
/*  84 */       model.addAttribute("speeds", speeds);
/*  85 */       return "portalcardcategory/save";
/*     */     }
/*  87 */     if (e.getMaclimitcount() == null) {
/*  88 */       e.setMaclimitcount(Integer.valueOf(1));
/*     */     }
/*  90 */     if (e.getMoney() == null) {
/*  91 */       e.setMoney(Double.valueOf(0.0D));
/*     */     }
/*  93 */     this.portalcardcategoryService.addPortalcardcategory(e);
/*     */ 
/*  95 */     return "redirect:/portalcardcategory/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcardcategory/editV.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 101 */     Portalcardcategory e = this.portalcardcategoryService.getPortalcardcategoryByKey(id);
/* 102 */     model.addAttribute("entity", e);
/* 103 */     List speeds = this.portalspeedService
/* 104 */       .getPortalspeedList(new PortalspeedQuery());
/* 105 */     model.addAttribute("speeds", speeds);
/* 106 */     return "portalcardcategory/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalcardcategory/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalcardcategory e, ModelMap model)
/*     */   {
/* 112 */     if ((stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getState())) || (e.getTime() == null) || (e.getTime().longValue() == 0L)) {
/* 113 */       model.addAttribute("msg", "名称 类型和计数不能为空！");
/* 114 */       model.addAttribute("entity", e);
/* 115 */       List speeds = this.portalspeedService
/* 116 */         .getPortalspeedList(new PortalspeedQuery());
/* 117 */       model.addAttribute("speeds", speeds);
/* 118 */       return "portalcardcategory/save";
/*     */     }
/* 120 */     if (e.getMoney() == null) {
/* 121 */       model.addAttribute("msg", "售价不能为空！");
/* 122 */       model.addAttribute("entity", e);
/* 123 */       List speeds = this.portalspeedService
/* 124 */         .getPortalspeedList(new PortalspeedQuery());
/* 125 */       model.addAttribute("speeds", speeds);
/* 126 */       return "portalcardcategory/save";
/*     */     }
/* 128 */     if (e.getMaclimitcount() == null) {
/* 129 */       e.setMaclimitcount(Integer.valueOf(1));
/*     */     }
/* 131 */     if (e.getMoney() == null) {
/* 132 */       e.setMoney(Double.valueOf(0.0D));
/*     */     }
/* 134 */     this.portalcardcategoryService.updatePortalcardcategoryByKeyAll(e);
/* 135 */     return "redirect:/portalcardcategory/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcardcategory/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 143 */     this.portalcardcategoryService.deleteByKey(id);
/* 144 */     return "redirect:/portalcardcategory/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalcardcategory/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 151 */     List list = Arrays.asList(ids);
/* 152 */     this.portalcardcategoryService.deleteByKeys(list);
/*     */ 
/* 154 */     return "redirect:/portalcardcategory/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalcardcategoryController
 * JD-Core Version:    0.6.2
 */