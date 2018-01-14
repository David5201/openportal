/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalsmsapi;
/*     */ import com.leeson.core.query.PortalsmsapiQuery;
/*     */ import com.leeson.core.query.PortalsmslogsQuery;
/*     */ import com.leeson.core.service.PortalsmsapiService;
/*     */ import com.leeson.core.service.PortalsmslogsService;
/*     */ import com.leeson.core.utils.HttpsUtils;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalsmslogsController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalsmslogsService portalsmslogsService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalsmsapiService portalsmsapiService;
/*  43 */   private static Config config = Config.getInstance();
/*  44 */   private static Logger logger = Logger.getLogger(PortalsmslogsController.class);
/*     */ 
/*     */   @RequestMapping({"/portalsmslogs/list.action"})
/*     */   public String page(PortalsmslogsQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  49 */     query.setInfoLike(true);
/*  50 */     query.setPhoneLike(true);
/*  51 */     query.setTypeLike(false);
/*  52 */     if (stringUtils.isBlank(query.getInfo())) {
/*  53 */       query.setInfo(null);
/*     */     }
/*  55 */     if (stringUtils.isBlank(query.getPhone())) {
/*  56 */       query.setPhone(null);
/*     */     }
/*  58 */     if (stringUtils.isBlank(query.getType())) {
/*  59 */       query.setType(null);
/*     */     }
/*  61 */     query.orderbyId(false);
/*  62 */     Pagination pagination = this.portalsmslogsService.getPortalsmslogsListWithPage(query);
/*  63 */     model.addAttribute("pagination", pagination);
/*  64 */     model.addAttribute("query", query);
/*     */ 
/*  66 */     PortalsmsapiQuery q = new PortalsmsapiQuery();
/*  67 */     q.setState("1");
/*  68 */     List smsList = this.portalsmsapiService.getPortalsmsapiList(q);
/*  69 */     Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*  70 */     if (smsList.size() > 0) {
/*  71 */       smsapi = (Portalsmsapi)smsList.get(0);
/*     */     }
/*  73 */     if ("10".equals(smsapi.getType())) {
/*  74 */       String url = "https://dx.ipyy.net/smsJson.aspx";
/*  75 */       String params = "action=overage&userid=&account=" + smsapi.getAppkey() + "&password=" + smsapi.getAppsecret();
/*  76 */       String result = HttpsUtils.httpsRequest(url, "POST", params);
/*  77 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  78 */         logger.info("SMS Send Result = " + result);
/*     */       }
/*  80 */       if (result.contains("Success")) {
/*  81 */         JSONObject jsonObject = null;
/*  82 */         jsonObject = JSONObject.fromObject(result);
/*  83 */         String overage = jsonObject.getString("overage");
/*  84 */         String sendTotal = jsonObject.getString("sendTotal");
/*  85 */         model.addAttribute("overage", overage);
/*  86 */         model.addAttribute("sendTotal", sendTotal);
/*     */       }
/*     */     }
/*  89 */     return "portalsmslogs/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalsmslogs/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/*  95 */     this.portalsmslogsService.deleteByKey(id);
/*  96 */     return "redirect:/portalsmslogs/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalsmslogs/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 103 */     List list = Arrays.asList(ids);
/* 104 */     this.portalsmslogsService.deleteByKeys(list);
/*     */ 
/* 106 */     return "redirect:/portalsmslogs/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalsmslogs/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 112 */     this.portalsmslogsService.deleteAll();
/* 113 */     return "redirect:/portalsmslogs/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalsmslogsController
 * JD-Core Version:    0.6.2
 */