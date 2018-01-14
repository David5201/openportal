/*    */ package com.leeson.core.controller;
/*    */ 
/*    */ import com.leeson.core.bean.Portalonlinelimit;
/*    */ import com.leeson.core.query.PortalonlinelimitQuery;
/*    */ import com.leeson.core.service.PortalonlinelimitService;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ 
/*    */ @Controller
/*    */ public class PortalonlinelimitController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PortalonlinelimitService portalonlinelimitService;
/*    */ 
/*    */   @RequestMapping({"/portalonlinelimit/show.action"})
/*    */   public String show(ModelMap model)
/*    */   {
/* 31 */     List es = this.portalonlinelimitService.getPortalonlinelimitList(new PortalonlinelimitQuery());
/* 32 */     model.addAttribute("pagination", es);
/* 33 */     return "portalonlinelimit/list";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/portalonlinelimit/edit.action"})
/*    */   public String edit(@RequestParam Long id, ModelMap model)
/*    */   {
/* 39 */     Portalonlinelimit e = this.portalonlinelimitService.getPortalonlinelimitByKey(id);
/* 40 */     if (e.getState().intValue() == 0)
/* 41 */       e.setState(Integer.valueOf(1));
/*    */     else {
/* 43 */       e.setState(Integer.valueOf(0));
/*    */     }
/* 45 */     this.portalonlinelimitService.updatePortalonlinelimitByKey(e);
/* 46 */     return "redirect:/portalonlinelimit/show.action";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/portalonlinelimit/save.action"})
/*    */   public String save(@RequestParam Long id, ModelMap model)
/*    */   {
/* 52 */     Portalonlinelimit e = this.portalonlinelimitService.getPortalonlinelimitByKey(id);
/* 53 */     int time = (int)(e.getTime().longValue() / 60000L);
/* 54 */     model.addAttribute("entity", e);
/* 55 */     model.addAttribute("time", Integer.valueOf(time));
/* 56 */     return "portalonlinelimit/config";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/portalonlinelimit/save.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String save(Portalonlinelimit e, ModelMap model)
/*    */   {
/* 62 */     e.setTime(Long.valueOf(e.getTime().longValue() * 60000L));
/* 63 */     this.portalonlinelimitService.updatePortalonlinelimitByKey(e);
/*    */ 
/* 65 */     List es = this.portalonlinelimitService.getPortalonlinelimitList(new PortalonlinelimitQuery());
/* 66 */     model.addAttribute("pagination", es);
/* 67 */     model.addAttribute("msg", "保存成功！！");
/* 68 */     return "portalonlinelimit/list";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalonlinelimitController
 * JD-Core Version:    0.6.2
 */