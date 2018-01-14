/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalprivilege;
/*     */ import com.leeson.core.query.PortalprivilegeQuery;
/*     */ import com.leeson.core.service.PortalprivilegeService;
/*     */ import com.leeson.core.utils.InitListener;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalprivilegeController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalprivilegeService portalprivilegeService;
/*     */ 
/*     */   @RequestMapping({"/portalprivilege/listTree.action"})
/*     */   public String listTree(ModelMap model, HttpServletRequest request)
/*     */   {
/*  39 */     return "portalprivilege/listTree";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalprivilege/list.action"})
/*     */   public String page(PortalprivilegeQuery query, Long parentId, ModelMap model) {
/*  44 */     query.setNameLike(true);
/*  45 */     query.setUrlLike(true);
/*  46 */     if (stringUtils.isBlank(query.getName())) {
/*  47 */       query.setName(null);
/*     */     }
/*  49 */     if (stringUtils.isBlank(query.getUrl())) {
/*  50 */       query.setUrl(null);
/*     */     }
/*  52 */     if ((parentId != null) && (parentId.longValue() != 0L)) {
/*  53 */       query.setParentId(parentId);
/*     */     }
/*     */ 
/*  56 */     Pagination pagination = this.portalprivilegeService.getPortalprivilegeListWithPage(query);
/*  57 */     if ((parentId != null) && (parentId.longValue() == 0L)) {
/*  58 */       pagination.setList(this.portalprivilegeService.findTopList());
/*     */     }
/*  60 */     model.addAttribute("pagination", pagination);
/*  61 */     model.addAttribute("query", query);
/*  62 */     return "portalprivilege/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalprivilege/addV.action"})
/*     */   public String addV(Long parentId, ModelMap model)
/*     */   {
/*  68 */     if ((parentId != null) && (parentId.longValue() != 0L)) {
/*  69 */       Portalprivilege e = new Portalprivilege();
/*  70 */       e.setParentId(parentId);
/*  71 */       model.addAttribute("entity", e);
/*     */     }
/*  73 */     return "portalprivilege/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalprivilege/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalprivilege e, HttpServletRequest request)
/*     */   {
/*  79 */     PortalprivilegeQuery pq = new PortalprivilegeQuery();
/*  80 */     if (e.getParentId() == null) {
/*  81 */       int a = this.portalprivilegeService.findTopList().size();
/*  82 */       e.setPosition(Integer.valueOf(a + 1));
/*     */     } else {
/*  84 */       pq.setParentId(e.getParentId());
/*  85 */       pq.orderbyPosition(false);
/*  86 */       List tt = this.portalprivilegeService.getPortalprivilegeList(pq);
/*  87 */       if (tt.size() == 0)
/*  88 */         e.setPosition(Integer.valueOf(e.getParentId().intValue() * 10 + 1));
/*     */       else {
/*  90 */         e.setPosition(Integer.valueOf(((Portalprivilege)tt.get(0)).getPosition().intValue() + 1));
/*     */       }
/*     */     }
/*  93 */     this.portalprivilegeService.addPortalprivilege(e);
/*     */ 
/*  95 */     ServletContext servletContext = request.getServletContext();
/*  96 */     InitListener.initData(servletContext, this.portalprivilegeService);
/*  97 */     return "redirect:/portalprivilege/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalprivilege/editV.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 103 */     Portalprivilege e = this.portalprivilegeService.getPortalprivilegeByKey(id);
/* 104 */     model.addAttribute("entity", e);
/* 105 */     return "portalprivilege/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalprivilege/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalprivilege e, HttpServletRequest request)
/*     */   {
/* 111 */     Portalprivilege et = this.portalprivilegeService.getPortalprivilegeByKey(e.getId());
/* 112 */     e.setPosition(et.getPosition());
/* 113 */     this.portalprivilegeService.updatePortalprivilegeByKeyAll(e);
/* 114 */     ServletContext servletContext = request.getServletContext();
/* 115 */     InitListener.initData(servletContext, this.portalprivilegeService);
/* 116 */     return "redirect:/portalprivilege/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalprivilege/delete.action"})
/*     */   public String delete(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 125 */     this.portalprivilegeService.deleteByKey(id);
/*     */ 
/* 127 */     ServletContext servletContext = request.getServletContext();
/* 128 */     InitListener.initData(servletContext, this.portalprivilegeService);
/* 129 */     return "redirect:/portalprivilege/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalprivilege/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
/*     */   {
/* 136 */     List list = Arrays.asList(ids);
/* 137 */     this.portalprivilegeService.deleteByKeys(list);
/*     */ 
/* 139 */     ServletContext servletContext = request.getServletContext();
/* 140 */     InitListener.initData(servletContext, this.portalprivilegeService);
/* 141 */     return "redirect:/portalprivilege/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalprivilege/moveUp.action"})
/*     */   public String moveUp(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 147 */     this.portalprivilegeService.editPosUP(id);
/* 148 */     ServletContext servletContext = request.getServletContext();
/* 149 */     InitListener.initData(servletContext, this.portalprivilegeService);
/* 150 */     return "portalprivilege/listTree";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalprivilege/moveDown.action"})
/*     */   public String moveDown(@RequestParam Long id, HttpServletRequest request) {
/* 155 */     this.portalprivilegeService.editPosDown(id);
/* 156 */     ServletContext servletContext = request.getServletContext();
/* 157 */     InitListener.initData(servletContext, this.portalprivilegeService);
/* 158 */     return "portalprivilege/listTree";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalprivilegeController
 * JD-Core Version:    0.6.2
 */