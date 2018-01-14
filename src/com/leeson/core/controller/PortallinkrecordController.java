/*    */ package com.leeson.core.controller;
/*    */ 
/*    */ import com.leeson.common.page.Pagination;
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portallinkrecord;
/*    */ import com.leeson.core.query.PortallinkrecordQuery;
/*    */ import com.leeson.core.service.PortallinkrecordService;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ 
/*    */ @Controller
/*    */ public class PortallinkrecordController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PortallinkrecordService portallinkrecordService;
/*    */ 
/*    */   @RequestMapping({"/portallinkrecord/list.action"})
/*    */   public String page(PortallinkrecordQuery query, ModelMap model)
/*    */   {
/* 34 */     query.setLoginNameLike(true);
/* 35 */     query.setIpLike(true);
/* 36 */     query.orderbyId(false);
/* 37 */     query.setUserDel(Integer.valueOf(0));
/* 38 */     if (stringUtils.isBlank(query.getIp())) {
/* 39 */       query.setIp(null);
/*    */     }
/* 41 */     if (stringUtils.isBlank(query.getLoginName())) {
/* 42 */       query.setLoginName(null);
/*    */     }
/* 44 */     if (stringUtils.isBlank(query.getState())) {
/* 45 */       query.setState(null);
/*    */     }
/* 47 */     if (stringUtils.isBlank(query.getEx1())) {
/* 48 */       query.setEx1(null);
/*    */     }
/*    */ 
/* 51 */     Pagination pagination = this.portallinkrecordService.getPortallinkrecordListWithPage(query);
/* 52 */     model.addAttribute("pagination", pagination);
/* 53 */     model.addAttribute("query", query);
/* 54 */     return "portallinkrecord/list";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/portallinkrecord/delete.action"})
/*    */   public String delete(@RequestParam Long id)
/*    */   {
/* 60 */     Portallinkrecord lr = new Portallinkrecord();
/* 61 */     lr.setId(id);
/* 62 */     lr.setUserDel(Integer.valueOf(1));
/* 63 */     this.portallinkrecordService.updatePortallinkrecordByKey(lr);
/* 64 */     return "redirect:/portallinkrecord/list.action";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/portallinkrecord/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String deletes(@RequestParam Long[] ids)
/*    */   {
/* 71 */     List<Long> list = Arrays.asList(ids);
/* 72 */     for (Long id : list) {
/* 73 */       Portallinkrecord lr = new Portallinkrecord();
/* 74 */       lr.setId(id);
/* 75 */       lr.setUserDel(Integer.valueOf(1));
/* 76 */       this.portallinkrecordService.updatePortallinkrecordByKey(lr);
/*    */     }
/* 78 */     return "redirect:/portallinkrecord/list.action";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/portallinkrecord/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*    */   public String deletes()
/*    */   {
/* 85 */     this.portallinkrecordService.deleteAll();
/*    */ 
/* 87 */     return "redirect:/portallinkrecord/list.action";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallinkrecordController
 * JD-Core Version:    0.6.2
 */