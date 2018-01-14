/*    */ package com.leeson.core.controller;
/*    */ 
/*    */ import com.leeson.core.bean.Portalurlparameter;
/*    */ import com.leeson.core.service.PortalurlparameterService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class PortalurlparameterController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PortalurlparameterService portalurlparameterService;
/*    */ 
/*    */   @RequestMapping({"/portalurlparameter/show.action"})
/*    */   public String editV(ModelMap model)
/*    */   {
/* 29 */     Portalurlparameter e = this.portalurlparameterService.getPortalurlparameterByKey(Long.valueOf(1L));
/* 30 */     model.addAttribute("entity", e);
/* 31 */     return "portalurlparameter/config";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/portalurlparameter/save.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String edit(Portalurlparameter e, ModelMap model)
/*    */   {
/* 37 */     e.setId(Long.valueOf(1L));
/* 38 */     this.portalurlparameterService.updatePortalurlparameterByKey(e);
/* 39 */     model.addAttribute("entity", e);
/* 40 */     model.addAttribute("msg", "设置更新成功！！");
/* 41 */     return "portalurlparameter/config";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalurlparameterController
 * JD-Core Version:    0.6.2
 */