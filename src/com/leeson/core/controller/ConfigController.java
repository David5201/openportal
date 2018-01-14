/*    */ package com.leeson.core.controller;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.core.bean.Portalconfig;
/*    */ import com.leeson.core.bean.Portalweixinwifi;
/*    */ import com.leeson.core.query.PortalbasQuery;
/*    */ import com.leeson.core.query.PortalweixinwifiQuery;
/*    */ import com.leeson.core.service.ConfigService;
/*    */ import com.leeson.core.service.PortalbasService;
/*    */ import com.leeson.core.service.PortalconfigService;
/*    */ import com.leeson.core.service.PortalweixinwifiService;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class ConfigController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private ConfigService configService;
/*    */ 
/*    */   @Autowired
/*    */   private PortalbasService portalbasService;
/*    */ 
/*    */   @Autowired
/*    */   private PortalconfigService portalconfigService;
/*    */ 
/*    */   @Autowired
/*    */   private PortalweixinwifiService portalweixinwifiService;
/*    */ 
/*    */   @RequestMapping({"/config/show.action"})
/*    */   public String edit(ModelMap model)
/*    */   {
/* 46 */     com.leeson.core.bean.Config e = this.configService.getConfigByKey(Long.valueOf(1L));
/* 47 */     model.addAttribute("entity", e);
/* 48 */     return "config/config";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/config/save.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String edit(com.leeson.core.bean.Config e, ModelMap model)
/*    */   {
/* 54 */     e.setId(Long.valueOf(1L));
/* 55 */     String domain = e.getDomain().trim();
/* 56 */     if (!domain.startsWith("http://")) {
/* 57 */       domain = "http://" + domain;
/*    */     }
/* 59 */     while (domain.endsWith("/")) {
/* 60 */       domain = domain.substring(0, domain.length() - 1);
/*    */     }
/* 62 */     e.setDomain(domain);
/* 63 */     this.configService.updateConfigByKey(e);
/* 64 */     e = this.configService.getConfigByKey(Long.valueOf(1L));
/* 65 */     model.addAttribute("entity", e);
/* 66 */     model.addAttribute("msg", "全局设置保存成功！！");
/*    */ 
/* 68 */     Map configMap = new ConcurrentHashMap();
/* 69 */     configMap.putAll(com.leeson.portal.core.model.Config.getInstance().getConfigMap());
/* 70 */     Iterator iterator = configMap.keySet().iterator();
/*    */     Portalbas bas;
/* 71 */     while (iterator.hasNext()) {
/* 72 */       Object o = iterator.next();
/* 73 */       String t = o.toString();
/* 74 */       bas = (Portalbas)com.leeson.portal.core.model.Config.getInstance().getConfigMap().get(t);
/* 75 */       bas.setIsdebug(String.valueOf(e.getIsDebug()));
/* 76 */       com.leeson.portal.core.model.Config.getInstance().getConfigMap().put(t, bas);
/*    */     }
/* 78 */     List<Portalbas> bass = this.portalbasService.getPortalbasList(new PortalbasQuery());
/* 79 */     for (Portalbas bas1 : bass) {
/* 80 */       bas1.setIsdebug(String.valueOf(e.getIsDebug()));
/* 81 */       this.portalbasService.updatePortalbasByKey(bas1);
/*    */     }
/* 83 */     Portalconfig base = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 84 */     base.setIsdebug(String.valueOf(e.getIsDebug()));
/* 85 */     this.portalconfigService.updatePortalconfigByKey(base);
/*    */ 
/* 87 */     List<Portalweixinwifi> weixins = this.portalweixinwifiService.getPortalweixinwifiList(new PortalweixinwifiQuery());
/* 88 */     for (Portalweixinwifi weixin : weixins) {
/* 89 */       weixin.setDomain(e.getDomain());
/* 90 */       this.portalweixinwifiService.updatePortalweixinwifiByKey(weixin);
/*    */     }
/* 92 */     return "config/config";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.ConfigController
 * JD-Core Version:    0.6.2
 */