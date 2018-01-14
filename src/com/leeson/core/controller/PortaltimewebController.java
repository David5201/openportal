/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portaltimeweb;
/*     */ import com.leeson.core.query.PortaltimewebQuery;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.PortaltimewebService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortaltimewebController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortaltimewebService portaltimewebService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalwebService portalwebService;
/*  41 */   private static SimpleDateFormat format = new SimpleDateFormat(
/*  42 */     "yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   @RequestMapping({"/portaltimeweb/list.action"})
/*     */   public String page(PortaltimewebQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  47 */     query.setNameLike(true);
/*  48 */     query.setDescriptionLike(true);
/*  49 */     if (stringUtils.isBlank(query.getName())) {
/*  50 */       query.setName(null);
/*     */     }
/*  52 */     if (stringUtils.isBlank(query.getDescription())) {
/*  53 */       query.setDescription(null);
/*     */     }
/*  55 */     query.orderbyPos(true);
/*  56 */     query.orderbyId(true);
/*  57 */     Pagination pagination = this.portaltimewebService.getPortaltimewebListWithPage(query);
/*  58 */     model.addAttribute("pagination", pagination);
/*  59 */     model.addAttribute("query", query);
/*     */ 
/*  61 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  62 */     model.addAttribute("webs", webs);
/*  63 */     return "portaltimeweb/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaltimeweb/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  69 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  70 */     model.addAttribute("webs", webs);
/*  71 */     return "portaltimeweb/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaltimeweb/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portaltimeweb e, String queryviewdate, ModelMap model)
/*     */   {
/*  77 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  78 */     model.addAttribute("webs", webs);
/*     */ 
/*  80 */     if ((queryviewdate != null) && (!"".equals(queryviewdate))) {
/*     */       try {
/*  82 */         Date viewDate = format.parse(queryviewdate + " 00:00:00");
/*  83 */         e.setViewdate(viewDate);
/*     */       } catch (Exception ex) {
/*  85 */         model.addAttribute("msg", "日期格式不正确！");
/*  86 */         queryviewdate = null;
/*  87 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/*  90 */     if (e.getViewdateday() != null) {
/*  91 */       if ((e.getViewdateday().intValue() < 1) || (e.getViewdateday().intValue() > 31)) {
/*  92 */         model.addAttribute("msg", "日格式不正确！");
/*  93 */         model.addAttribute("entity", e);
/*  94 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/*  97 */     else e.setViewdateday(Integer.valueOf(0));
/*     */ 
/*  99 */     if (e.getViewmonth() != null) {
/* 100 */       if ((e.getViewmonth().intValue() < 1) || (e.getViewmonth().intValue() > 12)) {
/* 101 */         model.addAttribute("msg", "月格式不正确！");
/* 102 */         model.addAttribute("entity", e);
/* 103 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/* 106 */     else e.setViewmonth(Integer.valueOf(0));
/*     */ 
/* 108 */     if (e.getViewweekday() != null) {
/* 109 */       if ((e.getViewweekday().intValue() < 1) || (e.getViewweekday().intValue() > 7)) {
/* 110 */         model.addAttribute("msg", "星期格式不正确！");
/* 111 */         model.addAttribute("entity", e);
/* 112 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/* 115 */     else e.setViewweekday(Integer.valueOf(0));
/*     */ 
/* 117 */     if (e.getPos() == null) {
/* 118 */       e.setPos(Long.valueOf(0L));
/*     */     }
/* 120 */     if (e.getCount() == null) {
/* 121 */       e.setCount(Long.valueOf(0L));
/*     */     }
/*     */ 
/* 124 */     PortaltimewebQuery q = new PortaltimewebQuery();
/* 125 */     q.setName(e.getName());
/* 126 */     q.setNameLike(false);
/* 127 */     if (this.portaltimewebService.getPortaltimewebList(q).size() > 0) {
/* 128 */       model.addAttribute("msg", "该名称已经存在！");
/* 129 */       model.addAttribute("entity", e);
/* 130 */       return "portaltimeweb/save";
/*     */     }
/*     */ 
/* 133 */     this.portaltimewebService.addPortaltimeweb(e);
/*     */ 
/* 135 */     return "redirect:/portaltimeweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaltimeweb/edit.action"})
/*     */   public String edit(@RequestParam Long id, ModelMap model)
/*     */   {
/* 141 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 142 */     model.addAttribute("webs", webs);
/*     */ 
/* 144 */     Portaltimeweb e = this.portaltimewebService.getPortaltimewebByKey(id);
/* 145 */     model.addAttribute("entity", e);
/* 146 */     return "portaltimeweb/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaltimeweb/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portaltimeweb e, String queryviewdate, ModelMap model)
/*     */   {
/* 152 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 153 */     model.addAttribute("webs", webs);
/*     */ 
/* 155 */     if ((queryviewdate != null) && (!"".equals(queryviewdate))) {
/*     */       try {
/* 157 */         Date viewDate = format.parse(queryviewdate + " 00:00:00");
/* 158 */         e.setViewdate(viewDate);
/*     */       } catch (Exception ex) {
/* 160 */         model.addAttribute("msg", "日期格式不正确！");
/* 161 */         queryviewdate = null;
/* 162 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/* 165 */     if (e.getViewdateday() != null) {
/* 166 */       if ((e.getViewdateday().intValue() < 1) || (e.getViewdateday().intValue() > 31)) {
/* 167 */         model.addAttribute("msg", "日格式不正确！");
/* 168 */         model.addAttribute("entity", e);
/* 169 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/* 172 */     else e.setViewdateday(Integer.valueOf(0));
/*     */ 
/* 174 */     if (e.getViewmonth() != null) {
/* 175 */       if ((e.getViewmonth().intValue() < 1) || (e.getViewmonth().intValue() > 12)) {
/* 176 */         model.addAttribute("msg", "月格式不正确！");
/* 177 */         model.addAttribute("entity", e);
/* 178 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/* 181 */     else e.setViewmonth(Integer.valueOf(0));
/*     */ 
/* 183 */     if (e.getViewweekday() != null) {
/* 184 */       if ((e.getViewweekday().intValue() < 1) || (e.getViewweekday().intValue() > 7)) {
/* 185 */         model.addAttribute("msg", "星期格式不正确！");
/* 186 */         model.addAttribute("entity", e);
/* 187 */         return "portaltimeweb/save";
/*     */       }
/*     */     }
/* 190 */     else e.setViewweekday(Integer.valueOf(0));
/*     */ 
/* 192 */     if (e.getPos() == null) {
/* 193 */       e.setPos(Long.valueOf(0L));
/*     */     }
/* 195 */     if (e.getCount() == null) {
/* 196 */       e.setCount(Long.valueOf(0L));
/*     */     }
/*     */ 
/* 199 */     PortaltimewebQuery q = new PortaltimewebQuery();
/* 200 */     q.setName(e.getName());
/* 201 */     q.setNameLike(false);
/* 202 */     List timewebs = this.portaltimewebService.getPortaltimewebList(q);
/* 203 */     if ((timewebs != null) && (timewebs.size() > 0) && 
/* 204 */       (((Portaltimeweb)timewebs.get(0)).getId() != e.getId())) {
/* 205 */       model.addAttribute("msg", "该名称已经存在！");
/* 206 */       model.addAttribute("entity", e);
/* 207 */       return "portaltimeweb/save";
/*     */     }
/*     */ 
/* 211 */     this.portaltimewebService.updatePortaltimewebByKeyAll(e);
/* 212 */     return "redirect:/portaltimeweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaltimeweb/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 220 */     this.portaltimewebService.deleteByKey(id);
/* 221 */     return "redirect:/portaltimeweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaltimeweb/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 228 */     List list = Arrays.asList(ids);
/* 229 */     this.portaltimewebService.deleteByKeys(list);
/*     */ 
/* 231 */     return "redirect:/portaltimeweb/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaltimeweb/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 237 */     this.portaltimewebService.deleteAll();
/* 238 */     return "redirect:/portaltimeweb/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaltimewebController
 * JD-Core Version:    0.6.2
 */