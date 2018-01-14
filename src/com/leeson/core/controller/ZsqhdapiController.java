/*    */ package com.leeson.core.controller;
/*    */ 
/*    */ import com.leeson.core.bean.Zsqhdapi;
/*    */ import com.leeson.core.service.ZsqhdapiService;
/*    */ import com.leeson.portal.core.model.AccountAPIMap;
/*    */ import java.util.HashMap;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class ZsqhdapiController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private ZsqhdapiService zsqhdapiService;
/*    */ 
/*    */   @RequestMapping({"/zsqhdapi/config.action"})
/*    */   public String edit(ModelMap model)
/*    */   {
/* 28 */     Zsqhdapi e = this.zsqhdapiService.getZsqhdapiByKey(Long.valueOf(1L));
/* 29 */     model.addAttribute("entity", e);
/* 30 */     return "zsqhdAPI/config";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/zsqhdapi/save.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String edit(Zsqhdapi e, ModelMap model)
/*    */   {
/* 36 */     e.setId(Long.valueOf(1L));
/* 37 */     this.zsqhdapiService.updateZsqhdapiByKey(e);
/* 38 */     Zsqhdapi api = this.zsqhdapiService.getZsqhdapiByKey(Long.valueOf(1L));
/* 39 */     model.addAttribute("entity", api);
/*    */ 
/* 41 */     String url = "";
/* 42 */     String state = "";
/* 43 */     String publicurl = "";
/* 44 */     String publicstate = "";
/* 45 */     String autourl = "";
/* 46 */     String autostate = "";
/*    */ 
/* 48 */     if (api != null) {
/* 49 */       url = api.getUrl();
/* 50 */       state = String.valueOf(api.getState());
/* 51 */       publicurl = api.getPublicurl();
/* 52 */       publicstate = String.valueOf(api.getPublicstate());
/* 53 */       autourl = api.getAutourl();
/* 54 */       autostate = String.valueOf(api.getAutostate());
/*    */     }
/*    */ 
/* 57 */     AccountAPIMap.getInstance().getAccountAPIMap().put("url", url);
/* 58 */     AccountAPIMap.getInstance().getAccountAPIMap().put("state", state);
/* 59 */     AccountAPIMap.getInstance().getAccountAPIMap().put("publicurl", publicurl);
/* 60 */     AccountAPIMap.getInstance().getAccountAPIMap().put("publicstate", publicstate);
/* 61 */     AccountAPIMap.getInstance().getAccountAPIMap().put("autourl", autourl);
/* 62 */     AccountAPIMap.getInstance().getAccountAPIMap().put("autostate", autostate);
/*    */ 
/* 64 */     model.addAttribute("msg", "保存成功！！");
/* 65 */     return "zsqhdAPI/config";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.ZsqhdapiController
 * JD-Core Version:    0.6.2
 */