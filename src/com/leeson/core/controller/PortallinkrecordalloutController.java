/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portallinkrecordallout;
/*     */ import com.leeson.core.query.PortallinkrecordalloutQuery;
/*     */ import com.leeson.core.service.PortallinkrecordalloutService;
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
/*     */ public class PortallinkrecordalloutController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortallinkrecordalloutService portallinkrecordalloutService;
/*     */ 
/*     */   @RequestMapping({"/portallinkrecordallout/list.action"})
/*     */   public String page(PortallinkrecordalloutQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  43 */     Pagination pagination = this.portallinkrecordalloutService.getPortallinkrecordalloutListWithPage(query);
/*  44 */     model.addAttribute("pagination", pagination);
/*  45 */     model.addAttribute("query", query);
/*  46 */     return "portallinkrecordallout/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portallinkrecordallout/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  53 */     return "portallinkrecordallout/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallinkrecordallout/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portallinkrecordallout e)
/*     */   {
/*  60 */     this.portallinkrecordalloutService.addPortallinkrecordallout(e);
/*     */ 
/*  62 */     return "redirect:/portallinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portallinkrecordallout/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/*  68 */     Portallinkrecordallout e = this.portallinkrecordalloutService.getPortallinkrecordalloutByKey(id);
/*  69 */     model.addAttribute("entity", e);
/*  70 */     return "portallinkrecordallout/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallinkrecordallout/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portallinkrecordallout e)
/*     */   {
/*  76 */     this.portallinkrecordalloutService.updatePortallinkrecordalloutByKey(e);
/*  77 */     return "redirect:/portallinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portallinkrecordallout/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/*  85 */     this.portallinkrecordalloutService.deleteByKey(id);
/*  86 */     return "redirect:/portallinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallinkrecordallout/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/*  93 */     List list = Arrays.asList(ids);
/*  94 */     this.portallinkrecordalloutService.deleteByKeys(list);
/*     */ 
/*  96 */     return "redirect:/portallinkrecordallout/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallinkrecordallout/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 102 */     this.portallinkrecordalloutService.deleteAll();
/* 103 */     return "redirect:/portallinkrecordallout/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallinkrecordalloutController
 * JD-Core Version:    0.6.2
 */