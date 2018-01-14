/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalrole;
/*     */ import com.leeson.core.bean.Portalroleprivilege;
/*     */ import com.leeson.core.bean.Portaluserrole;
/*     */ import com.leeson.core.query.PortalroleQuery;
/*     */ import com.leeson.core.query.PortalroleprivilegeQuery;
/*     */ import com.leeson.core.query.PortaluserroleQuery;
/*     */ import com.leeson.core.service.PortalprivilegeService;
/*     */ import com.leeson.core.service.PortalroleService;
/*     */ import com.leeson.core.service.PortalroleprivilegeService;
/*     */ import com.leeson.core.service.PortaluserroleService;
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
/*     */ public class PortalroleController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalroleService portalroleService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaluserroleService portaluserroleService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalroleprivilegeService portalroleprivilegeService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalprivilegeService portalprivilegeService;
/*     */ 
/*     */   @RequestMapping({"/portalrole/list.action"})
/*     */   public String page(PortalroleQuery query, ModelMap model)
/*     */   {
/*  48 */     query.setNameLike(true);
/*  49 */     query.setDescriptionLike(true);
/*  50 */     if (stringUtils.isBlank(query.getName())) {
/*  51 */       query.setName(null);
/*     */     }
/*  53 */     if (stringUtils.isBlank(query.getDescription())) {
/*  54 */       query.setDescription(null);
/*     */     }
/*     */ 
/*  57 */     Pagination pagination = this.portalroleService
/*  58 */       .getPortalroleListWithPage(query);
/*  59 */     model.addAttribute("query", query);
/*  60 */     model.addAttribute("pagination", pagination);
/*  61 */     return "portalrole/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalrole/addV.action"})
/*     */   public String addV(ModelMap model)
/*     */   {
/*  68 */     return "portalrole/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalrole/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalrole e, ModelMap model)
/*     */   {
/*  74 */     if (stringUtils.isBlank(e.getName())) {
/*  75 */       model.addAttribute("msg", "角色名不能为空！");
/*  76 */       model.addAttribute("entity", e);
/*  77 */       return "portalrole/save";
/*     */     }
/*  79 */     this.portalroleService.addPortalrole(e);
/*     */ 
/*  81 */     return "redirect:/portalrole/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalrole/editPrivilegeUI.action"})
/*     */   public String editPrivilegeUI(@RequestParam Long id, ModelMap model)
/*     */   {
/*  87 */     Portalrole portalrole = this.portalroleService.getPortalroleByKey(id);
/*     */ 
/*  89 */     PortalroleprivilegeQuery rpq = new PortalroleprivilegeQuery();
/*  90 */     rpq.setRoleId(id);
/*  91 */     rpq.setFields("privilegeId");
/*  92 */     List<Portalroleprivilege> rps = this.portalroleprivilegeService
/*  93 */       .getPortalroleprivilegeList(rpq);
/*  94 */     Long[] privilegeIds = new Long[rps.size()];
/*  95 */     int index = 0;
/*  96 */     for (Portalroleprivilege rp : rps) {
/*  97 */       privilegeIds[(index++)] = rp.getPrivilegeId();
/*     */     }
/*  99 */     model.addAttribute("privilegeIds", privilegeIds);
/* 100 */     model.addAttribute("portalrole", portalrole);
/* 101 */     return "portalrole/setPrivilegeUI";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalrole/editPrivilege.action"})
/*     */   public String editPrivilege(@RequestParam Long[] privilegeIds, @RequestParam Long id)
/*     */   {
/* 108 */     PortalroleprivilegeQuery rpq = new PortalroleprivilegeQuery();
/* 109 */     rpq.setRoleId(id);
/* 110 */     Portalroleprivilege rp = new Portalroleprivilege();
/* 111 */     rp.setRoleId(id);
/* 112 */     this.portalroleprivilegeService.deleteByQuery(rpq);
/* 113 */     for (Long pid : privilegeIds) {
/* 114 */       rp.setPrivilegeId(pid);
/* 115 */       this.portalroleprivilegeService.addPortalroleprivilege(rp);
/*     */     }
/*     */ 
/* 118 */     PortaluserroleQuery pu = new PortaluserroleQuery();
/* 119 */     pu.setRoleId(id);
/* 120 */     List<Portaluserrole> urs = this.portaluserroleService
/* 121 */       .getPortaluserroleList(pu);
/* 122 */     List<Long> uids = new ArrayList();
/* 123 */     for (Portaluserrole ur : urs) {
/* 124 */       ((List)uids).add(ur.getUserId());
/*     */     }
/* 126 */     this.portaluserroleService.deleteByQuery(pu);
/*     */ 
/* 128 */     for (Long uid : uids) {
/* 129 */       Portaluserrole ar = new Portaluserrole();
/* 130 */       ar.setUserId(uid);
/* 131 */       ar.setRoleId(id);
/* 132 */       this.portaluserroleService.addPortaluserrole(ar);
/*     */     }
/*     */ 
/* 135 */     return "redirect:/portalrole/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalrole/editV.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 141 */     Portalrole e = this.portalroleService.getPortalroleByKey(id);
/* 142 */     model.addAttribute("entity", e);
/* 143 */     return "portalrole/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalrole/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalrole e, ModelMap model)
/*     */   {
/* 149 */     if (stringUtils.isBlank(e.getName())) {
/* 150 */       model.addAttribute("msg", "角色名不能为空！");
/* 151 */       model.addAttribute("entity", e);
/* 152 */       return "portalrole/save";
/*     */     }
/* 154 */     this.portalroleService.updatePortalroleByKey(e);
/* 155 */     return "redirect:/portalrole/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalrole/delete.action"})
/*     */   public String delete(@RequestParam Long id, ModelMap model)
/*     */   {
/* 170 */     PortaluserroleQuery urq = new PortaluserroleQuery();
/* 171 */     urq.setRoleId(id);
/* 172 */     this.portaluserroleService.deleteByQuery(urq);
/*     */ 
/* 174 */     PortalroleprivilegeQuery rp = new PortalroleprivilegeQuery();
/* 175 */     rp.setRoleId(id);
/* 176 */     this.portalroleprivilegeService.deleteByQuery(rp);
/* 177 */     this.portalroleService.deleteByKey(id);
/* 178 */     return "redirect:/portalrole/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalrole/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, ModelMap model)
/*     */   {
/* 197 */     PortaluserroleQuery urq = new PortaluserroleQuery();
/* 198 */     for (Long id : ids) {
/* 199 */       urq.setRoleId(id);
/* 200 */       this.portaluserroleService.deleteByQuery(urq);
/*     */     }
/*     */ 
/* 203 */     List<Long> list = Arrays.asList(ids);
/* 204 */     for (Long id : list) {
/* 205 */       PortalroleprivilegeQuery rp = new PortalroleprivilegeQuery();
/* 206 */       rp.setRoleId(id);
/* 207 */       this.portalroleprivilegeService.deleteByQuery(rp);
/*     */     }
/* 209 */     this.portalroleService.deleteByKeys(list);
/*     */ 
/* 211 */     return "redirect:/portalrole/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalroleController
 * JD-Core Version:    0.6.2
 */