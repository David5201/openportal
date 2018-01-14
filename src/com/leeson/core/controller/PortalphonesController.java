/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalphones;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.PortaldepartmentQuery;
/*     */ import com.leeson.core.query.PortalphonesQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.PortaldepartmentService;
/*     */ import com.leeson.core.service.PortalphonesService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import com.leeson.core.utils.DepartmentUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalphonesController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalphonesService portalphonesService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaluserService portaluserService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaldepartmentService portaldepartmentService;
/*     */ 
/*     */   @RequestMapping({"/portalphones/list.action"})
/*     */   public String page(PortalphonesQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  50 */     List users = this.portaluserService
/*  51 */       .getPortaluserList(new PortaluserQuery());
/*  52 */     model.addAttribute("users", users);
/*  53 */     List departmentList = this.portaldepartmentService
/*  54 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/*  55 */     model.addAttribute("departmentList", departmentList);
/*     */ 
/*  57 */     HttpSession session = request.getSession();
/*  58 */     Portaluser user = (Portaluser)session.getAttribute("user");
/*  59 */     if ((user == null) || (user.getId() == null)) {
/*  60 */       return "homeAction/index";
/*     */     }
/*  62 */     long uid = user.getId().longValue();
/*  63 */     if (!"admin".equals(user.getLoginName())) {
/*  64 */       query.setUid(Long.valueOf(uid));
/*     */     }
/*     */ 
/*  67 */     query.setNameLike(true);
/*  68 */     query.setDescriptionLike(true);
/*  69 */     query.setPhoneLike(true);
/*  70 */     if (stringUtils.isBlank(query.getName())) {
/*  71 */       query.setName(null);
/*     */     }
/*  73 */     if (stringUtils.isBlank(query.getDescription())) {
/*  74 */       query.setDescription(null);
/*     */     }
/*  76 */     if (stringUtils.isBlank(query.getPhone())) {
/*  77 */       query.setPhone(null);
/*     */     }
/*  79 */     Pagination pagination = this.portalphonesService
/*  80 */       .getPortalphonesListWithPage(query);
/*  81 */     model.addAttribute("pagination", pagination);
/*  82 */     model.addAttribute("query", query);
/*  83 */     return "portalphones/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalphones/add.action"})
/*     */   public String add(ModelMap model, HttpServletRequest request)
/*     */   {
/*  89 */     HttpSession session = request.getSession();
/*  90 */     Portaluser user = (Portaluser)session.getAttribute("user");
/*  91 */     if ((user == null) || (user.getId() == null)) {
/*  92 */       return "homeAction/index";
/*     */     }
/*     */ 
/*  95 */     List departmentList = this.portaldepartmentService
/*  96 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/*  97 */     model.addAttribute("chooseList", 
/*  98 */       DepartmentUtils.getAllDepartments(departmentList));
/*  99 */     return "portalphones/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalphones/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalphones e, HttpServletRequest request)
/*     */   {
/* 105 */     HttpSession session = request.getSession();
/* 106 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 107 */     if ((user == null) || (user.getId() == null)) {
/* 108 */       return "homeAction/index";
/*     */     }
/*     */ 
/* 111 */     e.setUid(user.getId());
/* 112 */     e.setDid(user.getDepartmentId());
/* 113 */     this.portalphonesService.addPortalphones(e);
/* 114 */     return "redirect:/portalphones/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalphones/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*     */   {
/* 121 */     HttpSession session = request.getSession();
/* 122 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 123 */     if ((user == null) || (user.getId() == null)) {
/* 124 */       return "homeAction/index";
/*     */     }
/*     */ 
/* 127 */     List departmentList = this.portaldepartmentService
/* 128 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/* 129 */     model.addAttribute("chooseList", 
/* 130 */       DepartmentUtils.getAllDepartments(departmentList));
/*     */ 
/* 132 */     Portalphones e = this.portalphonesService.getPortalphonesByKey(id);
/* 133 */     long uid = user.getId().longValue();
/* 134 */     if (!"admin".equals(user.getLoginName())) {
/* 135 */       long suid = e.getUid().longValue();
/* 136 */       if (suid != uid) {
/* 137 */         return "redirect:/advstores/list.action";
/*     */       }
/*     */     }
/* 140 */     model.addAttribute("entity", e);
/* 141 */     return "portalphones/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalphones/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalphones e, HttpServletRequest request)
/*     */   {
/* 147 */     HttpSession session = request.getSession();
/* 148 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 149 */     if ((user == null) || (user.getId() == null)) {
/* 150 */       return "homeAction/index";
/*     */     }
/*     */ 
/* 153 */     Portalphones s = this.portalphonesService.getPortalphonesByKey(e.getId());
/* 154 */     if (s != null) {
/* 155 */       long uid = user.getId().longValue();
/* 156 */       if (!"admin".equals(user.getLoginName())) {
/* 157 */         long suid = s.getUid().longValue();
/* 158 */         if (suid != uid) {
/* 159 */           return "redirect:/portalphones/list.action";
/*     */         }
/* 161 */         e.setDid(user.getDepartmentId());
/*     */       }
/* 163 */       else if (uid == s.getUid().longValue()) {
/* 164 */         e.setDid(user.getDepartmentId());
/*     */       }
/*     */ 
/* 167 */       this.portalphonesService.updatePortalphonesByKey(e);
/*     */     }
/* 169 */     return "redirect:/portalphones/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalphones/delete.action"})
/*     */   public String delete(@RequestParam Long id, HttpServletRequest request)
/*     */   {
/* 175 */     HttpSession session = request.getSession();
/* 176 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 177 */     if ((user == null) || (user.getId() == null)) {
/* 178 */       return "homeAction/index";
/*     */     }
/*     */ 
/* 181 */     Portalphones s = this.portalphonesService.getPortalphonesByKey(id);
/* 182 */     if (s != null) {
/* 183 */       long uid = user.getId().longValue();
/* 184 */       if (!"admin".equals(user.getLoginName())) {
/* 185 */         long suid = s.getUid().longValue();
/* 186 */         if (suid == uid)
/* 187 */           this.portalphonesService.deleteByKey(id);
/*     */       }
/*     */       else {
/* 190 */         this.portalphonesService.deleteByKey(id);
/*     */       }
/*     */     }
/* 193 */     return "redirect:/portalphones/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalphones/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
/*     */   {
/* 199 */     HttpSession session = request.getSession();
/* 200 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 201 */     if ((user == null) || (user.getId() == null)) {
/* 202 */       return "homeAction/index";
/*     */     }
/*     */ 
/* 206 */     List<Long> list = Arrays.asList(ids);
/* 207 */     for (Long id : list) {
/* 208 */       Portalphones s = this.portalphonesService.getPortalphonesByKey(id);
/* 209 */       if (s != null) {
/* 210 */         long uid = user.getId().longValue();
/* 211 */         if (!"admin".equals(user.getLoginName())) {
/* 212 */           long suid = s.getUid().longValue();
/* 213 */           if (suid == uid)
/* 214 */             this.portalphonesService.deleteByKey(id);
/*     */         }
/*     */         else {
/* 217 */           this.portalphonesService.deleteByKey(id);
/*     */         }
/*     */       }
/*     */     }
/* 221 */     return "redirect:/portalphones/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalphones/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes(HttpServletRequest request)
/*     */   {
/* 227 */     HttpSession session = request.getSession();
/* 228 */     Portaluser user = (Portaluser)session.getAttribute("user");
/* 229 */     if ((user == null) || (user.getId() == null)) {
/* 230 */       return "homeAction/index";
/*     */     }
/*     */ 
/* 233 */     long uid = user.getId().longValue();
/* 234 */     PortalphonesQuery query = new PortalphonesQuery();
/* 235 */     if (!"admin".equals(user.getLoginName())) {
/* 236 */       query.setUid(Long.valueOf(uid));
/*     */     }
/* 238 */     this.portalphonesService.deleteByQuery(query);
/* 239 */     return "redirect:/portalphones/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalphonesController
 * JD-Core Version:    0.6.2
 */