/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalaccountmacs;
/*     */ import com.leeson.core.query.PortalaccountmacsQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalaccountmacsService;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class PortalaccountmacsController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountmacsService portalaccountmacsService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountService portalaccountService;
/*     */ 
/*     */   @RequestMapping({"/portalaccount/maclist.action"})
/*     */   public String page(PortalaccountmacsQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  44 */     Portalaccount account = this.portalaccountService.getPortalaccountByKey(query.getAccountId());
/*  45 */     if (stringUtils.isBlank(query.getMac()))
/*  46 */       query.setMac(null);
/*     */     else {
/*  48 */       query.setMacLike(true);
/*     */     }
/*  50 */     Pagination pagination = this.portalaccountmacsService.getPortalaccountmacsListWithPage(query);
/*  51 */     model.addAttribute("pagination", pagination);
/*  52 */     model.addAttribute("query", query);
/*  53 */     model.addAttribute("account", account);
/*  54 */     return "portalaccount/maclist";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/macadd.action"})
/*     */   public String add(Portalaccountmacs e)
/*     */   {
/*  62 */     if (stringUtils.isNotBlank(e.getMac())) {
/*  63 */       this.portalaccountmacsService.addPortalaccountmacs(e);
/*     */     }
/*  65 */     return "redirect:/portalaccount/maclist.action?accountId=" + e.getAccountId();
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/portalaccount/macedit.action"})
/*     */   public Map<String, String> edit(Portalaccountmacs e) {
/*  72 */     if (stringUtils.isBlank(e.getMac())) {
/*  73 */       e.setMac(null);
/*     */     }
/*  75 */     this.portalaccountmacsService.updatePortalaccountmacsByKey(e);
/*  76 */     Map map = new HashMap();
/*  77 */     map.put("ret", "0");
/*  78 */     return map;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalaccount/macdelete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/*  86 */     Long accountId = this.portalaccountmacsService.getPortalaccountmacsByKey(id).getAccountId();
/*  87 */     this.portalaccountmacsService.deleteByKey(id);
/*  88 */     return "redirect:/portalaccount/maclist.action?accountId=" + accountId;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalaccount/macdeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/*  95 */     Long id = ids[0];
/*  96 */     Long accountId = this.portalaccountmacsService.getPortalaccountmacsByKey(id).getAccountId();
/*  97 */     List list = Arrays.asList(ids);
/*  98 */     this.portalaccountmacsService.deleteByKeys(list);
/*     */ 
/* 100 */     return "redirect:/portalaccount/maclist.action?accountId=" + accountId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalaccountmacsController
 * JD-Core Version:    0.6.2
 */