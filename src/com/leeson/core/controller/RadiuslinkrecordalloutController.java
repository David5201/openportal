/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Radiuslinkrecordallout;
/*     */ import com.leeson.core.query.RadiuslinkrecordalloutQuery;
/*     */ import com.leeson.core.service.RadiuslinkrecordalloutService;
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
/*     */ public class RadiuslinkrecordalloutController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private RadiuslinkrecordalloutService radiuslinkrecordalloutService;
/*     */ 
/*     */   @RequestMapping({"/radiuslinkrecordallout/list.action"})
/*     */   public String page(RadiuslinkrecordalloutQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  43 */     Pagination pagination = this.radiuslinkrecordalloutService.getRadiuslinkrecordalloutListWithPage(query);
/*  44 */     model.addAttribute("pagination", pagination);
/*  45 */     model.addAttribute("query", query);
/*  46 */     return "radiuslinkrecordallout/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiuslinkrecordallout/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  53 */     return "radiuslinkrecordallout/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiuslinkrecordallout/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Radiuslinkrecordallout e)
/*     */   {
/*  60 */     this.radiuslinkrecordalloutService.addRadiuslinkrecordallout(e);
/*     */ 
/*  62 */     return "redirect:/radiuslinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiuslinkrecordallout/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/*  68 */     Radiuslinkrecordallout e = this.radiuslinkrecordalloutService.getRadiuslinkrecordalloutByKey(id);
/*  69 */     model.addAttribute("entity", e);
/*  70 */     return "radiuslinkrecordallout/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiuslinkrecordallout/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Radiuslinkrecordallout e)
/*     */   {
/*  76 */     this.radiuslinkrecordalloutService.updateRadiuslinkrecordalloutByKey(e);
/*  77 */     return "redirect:/radiuslinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiuslinkrecordallout/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/*  85 */     this.radiuslinkrecordalloutService.deleteByKey(id);
/*  86 */     return "redirect:/radiuslinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiuslinkrecordallout/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/*  93 */     List list = Arrays.asList(ids);
/*  94 */     this.radiuslinkrecordalloutService.deleteByKeys(list);
/*     */ 
/*  96 */     return "redirect:/radiuslinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiuslinkrecordallout/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 102 */     this.radiuslinkrecordalloutService.deleteAll();
/* 103 */     return "redirect:/radiuslinkrecordallout/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiuslinkrecordalloutController
 * JD-Core Version:    0.6.2
 */