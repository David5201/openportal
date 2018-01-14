/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.core.bean.Portalautologin;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.query.PortalautologinQuery;
/*     */ import com.leeson.core.service.PortalautologinService;
/*     */ import com.leeson.portal.core.model.AutoLoginCheckTimeMap;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalautologinController
/*     */ {
/*  34 */   private static Config config = Config.getInstance();
/*     */ 
/*  36 */   private static Logger logger = Logger.getLogger(PortalautologinController.class);
/*     */ 
/*     */   @Autowired
/*     */   private PortalautologinService portalautologinService;
/*     */ 
/*     */   @RequestMapping({"/portalautologin/show.action"})
/*     */   public String show(ModelMap model) {
/*  44 */     List eAll = this.portalautologinService
/*  45 */       .getPortalautologinList(new PortalautologinQuery());
/*     */ 
/*  59 */     model.addAttribute("pagination", eAll);
/*  60 */     Long time = 
/*  61 */       (Long)AutoLoginCheckTimeMap.getInstance()
/*  61 */       .getAutoLoginCheckTimeMap().get("time");
/*  62 */     model.addAttribute("time", time);
/*  63 */     return "portalautologin/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalautologin/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/*  69 */     Portalautologin e = this.portalautologinService.getPortalautologinByKey(id);
/*  70 */     if (e.getState().intValue() == 0)
/*  71 */       e.setState(Integer.valueOf(1));
/*     */     else {
/*  73 */       e.setState(Integer.valueOf(0));
/*     */     }
/*  75 */     this.portalautologinService.updatePortalautologinByKey(e);
/*  76 */     return "redirect:/portalautologin/show.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalautologin/save.action"})
/*     */   public String save(@RequestParam Long id, ModelMap model)
/*     */   {
/*  82 */     Portalautologin e = this.portalautologinService.getPortalautologinByKey(id);
/*  83 */     int time = (int)(e.getTime().longValue() / 60000L);
/*  84 */     model.addAttribute("entity", e);
/*  85 */     model.addAttribute("time", Integer.valueOf(time));
/*  86 */     return "portalautologin/config";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalautologin/save.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String save(Portalautologin e, ModelMap model)
/*     */   {
/*  92 */     e.setTime(Long.valueOf(e.getTime().longValue() * 60000L));
/*  93 */     this.portalautologinService.updatePortalautologinByKey(e);
/*  94 */     List es = this.portalautologinService
/*  95 */       .getPortalautologinList(new PortalautologinQuery());
/*  96 */     model.addAttribute("pagination", es);
/*  97 */     Long time = 
/*  98 */       (Long)AutoLoginCheckTimeMap.getInstance()
/*  98 */       .getAutoLoginCheckTimeMap().get("time");
/*  99 */     model.addAttribute("time", time);
/* 100 */     model.addAttribute("msg", "保存成功！！");
/* 101 */     return "portalautologin/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalautologin/timeset.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String timeset(Long time, ModelMap model, HttpServletRequest request)
/*     */   {
/* 107 */     if ((time == null) || (10L > time.longValue())) {
/* 108 */       time = Long.valueOf(10L);
/*     */     }
/* 110 */     AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap()
/* 111 */       .put("time", time);
/*     */ 
/* 113 */     AutoLoginCheckTimeMapToDisk(request);
/*     */ 
/* 116 */     model.addAttribute("time", time);
/* 117 */     model.addAttribute("msg", "设置成功！！");
/*     */ 
/* 119 */     List es = this.portalautologinService
/* 120 */       .getPortalautologinList(new PortalautologinQuery());
/* 121 */     model.addAttribute("pagination", es);
/* 122 */     return "portalautologin/list";
/*     */   }
/*     */ 
/*     */   private void AutoLoginCheckTimeMapToDisk(HttpServletRequest request)
/*     */   {
/* 128 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 129 */     ObjectOutputStream os = null;
/*     */     try {
/* 131 */       os = new ObjectOutputStream(new FileOutputStream(
/* 132 */         request.getServletContext().getRealPath("/") + 
/* 133 */         "/AutoLoginCheckTimeMap.dat"));
/* 134 */       os.writeObject(AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap());
/* 135 */       if (basConfig.getIsdebug().equals("1"))
/* 136 */         logger.info("AutoLoginCheckTimeMapToDisk !!");
/*     */     }
/*     */     catch (Exception e) {
/* 139 */       logger.error("==============ERROR Start=============");
/* 140 */       logger.error(e);
/* 141 */       logger.error("ERROR INFO ", e);
/* 142 */       logger.error("==============ERROR End=============");
/*     */       try
/*     */       {
/* 145 */         if (os != null)
/* 146 */           os.close();
/*     */       }
/*     */       catch (IOException e1) {
/* 149 */         logger.error("==============ERROR Start=============");
/* 150 */         logger.error(e1);
/* 151 */         logger.error("ERROR INFO ", e1);
/* 152 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 145 */         if (os != null)
/* 146 */           os.close();
/*     */       }
/*     */       catch (IOException e) {
/* 149 */         logger.error("==============ERROR Start=============");
/* 150 */         logger.error(e);
/* 151 */         logger.error("ERROR INFO ", e);
/* 152 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalautologinController
 * JD-Core Version:    0.6.2
 */