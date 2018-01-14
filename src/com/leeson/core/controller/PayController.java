/*    */ package com.leeson.core.controller;
/*    */ 
/*    */ import com.leeson.core.bean.Payapi;
/*    */ import com.leeson.core.service.PayapiService;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class PayController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PayapiService payapiService;
/*    */ 
/*    */   @RequestMapping({"/portalpay/config"})
/*    */   public String show(ModelMap model)
/*    */   {
/* 28 */     Payapi e = this.payapiService.getPayapiByKey(Long.valueOf(1L));
/* 29 */     model.addAttribute("entity", e);
/* 30 */     return "portalpay/config";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/portalpay/save"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String save(Payapi e, ModelMap model, HttpServletRequest request)
/*    */   {
/* 36 */     e.setId(Long.valueOf(1L));
/* 37 */     this.payapiService.updatePayapiByKeyAll(e);
/* 38 */     model.addAttribute("msg", "保存成功！!");
/* 39 */     model.addAttribute("entity", e);
/* 40 */     return "portalpay/config";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PayController
 * JD-Core Version:    0.6.2
 */