/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portaldepartment;
/*     */ import com.leeson.core.query.PortaldepartmentQuery;
/*     */ import com.leeson.core.service.PortaldepartmentService;
/*     */ import com.leeson.core.utils.DepartmentUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortaldepartmentController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortaldepartmentService portaldepartmentService;
/*     */ 
/*     */   @RequestMapping({"/portaldepartment/listTree.action"})
/*     */   public String listTree(ModelMap model)
/*     */   {
/*  37 */     List departmentList = this.portaldepartmentService.getPortaldepartmentList(new PortaldepartmentQuery());
/*  38 */     model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
/*  39 */     model.addAttribute("departmentList", departmentList);
/*  40 */     return "portaldepartment/listTree";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaldepartment/list.action"})
/*     */   public String page(PortaldepartmentQuery query, Long parentId, ModelMap model)
/*     */   {
/*  47 */     query.setNameLike(true);
/*  48 */     query.setDescriptionLike(true);
/*  49 */     if (stringUtils.isBlank(query.getName())) {
/*  50 */       query.setName(null);
/*     */     }
/*  52 */     if (stringUtils.isBlank(query.getDescription())) {
/*  53 */       query.setDescription(null);
/*     */     }
/*  55 */     if ((parentId != null) && (parentId.longValue() != 0L)) {
/*  56 */       query.setParentId(parentId);
/*     */     }
/*     */ 
/*  59 */     Pagination pagination = this.portaldepartmentService
/*  60 */       .getPortaldepartmentListWithPage(query);
/*  61 */     List<Portaldepartment> departmentList = this.portaldepartmentService
/*  62 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/*     */ 
/*  64 */     if ((parentId != null) && (parentId.longValue() == 0L)) {
/*  65 */       List fd = new ArrayList();
/*  66 */       for (Portaldepartment d : departmentList) {
/*  67 */         if (d.getParentId() == null) {
/*  68 */           fd.add(d);
/*     */         }
/*     */       }
/*  71 */       pagination.setList(fd);
/*     */     }
/*     */ 
/*  74 */     model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
/*  75 */     model.addAttribute("departmentList", departmentList);
/*  76 */     model.addAttribute("pagination", pagination);
/*  77 */     model.addAttribute("query", query);
/*  78 */     return "portaldepartment/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaldepartment/addV.action"})
/*     */   public String addV(Long parentId, ModelMap model)
/*     */   {
/*  84 */     if ((parentId != null) && (parentId.longValue() != 0L)) {
/*  85 */       Portaldepartment e = new Portaldepartment();
/*  86 */       e.setParentId(parentId);
/*  87 */       model.addAttribute("entity", e);
/*     */     }
/*  89 */     List departmentList = this.portaldepartmentService
/*  90 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/*  91 */     model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
/*  92 */     model.addAttribute("departmentList", departmentList);
/*  93 */     return "portaldepartment/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaldepartment/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portaldepartment e)
/*     */   {
/* 100 */     this.portaldepartmentService.addPortaldepartment(e);
/*     */ 
/* 102 */     return "redirect:/portaldepartment/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaldepartment/editV.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 108 */     Portaldepartment e = this.portaldepartmentService
/* 109 */       .getPortaldepartmentByKey(id);
/* 110 */     List departmentList = this.portaldepartmentService
/* 111 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/* 112 */     model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
/* 113 */     model.addAttribute("departmentList", departmentList);
/* 114 */     model.addAttribute("entity", e);
/* 115 */     return "portaldepartment/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaldepartment/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portaldepartment e)
/*     */   {
/* 121 */     this.portaldepartmentService.updatePortaldepartmentByKeyAll(e);
/* 122 */     return "redirect:/portaldepartment/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaldepartment/delete.action"})
/*     */   public String delete(@RequestParam Long id, ModelMap model)
/*     */   {
/* 142 */     this.portaldepartmentService.deleteByKey(id);
/* 143 */     return "redirect:/portaldepartment/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaldepartment/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, ModelMap model)
/*     */   {
/* 150 */     List list = Arrays.asList(ids);
/*     */ 
/* 168 */     this.portaldepartmentService.deleteByKeys(list);
/*     */ 
/* 170 */     return "redirect:/portaldepartment/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaldepartmentController
 * JD-Core Version:    0.6.2
 */