/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Radiusnas;
/*     */ import com.leeson.core.query.RadiusnasQuery;
/*     */ import com.leeson.core.service.RadiusnasService;
/*     */ import com.leeson.radius.core.Tool;
/*     */ import com.leeson.radius.core.model.RadiusNasMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class RadiusnasController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private RadiusnasService radiusnasService;
/*     */ 
/*     */   @RequestMapping({"/radiusnas/list.action"})
/*     */   public String page(RadiusnasQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  39 */     if (stringUtils.isBlank(query.getType())) {
/*  40 */       query.setType(null);
/*     */     }
/*  42 */     query.setNameLike(true);
/*  43 */     query.setDescriptionLike(true);
/*  44 */     Pagination pagination = this.radiusnasService.getRadiusnasListWithPage(query);
/*  45 */     model.addAttribute("pagination", pagination);
/*  46 */     model.addAttribute("query", query);
/*  47 */     return "radiusnas/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiusnas/add.action"})
/*     */   public String addV(Radiusnas e, ModelMap model)
/*     */   {
/*  53 */     e.setEx1("0");
/*  54 */     e.setEx2("300");
/*  55 */     e.setEx3("600");
/*  56 */     e.setEx4("600");
/*  57 */     e.setEx5("1");
/*  58 */     e.setEx6("3799");
/*  59 */     model.addAttribute("entity", e);
/*  60 */     return "radiusnas/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiusnas/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Radiusnas e, ModelMap model)
/*     */   {
/*  66 */     if ((stringUtils.isBlank(e.getIp())) || (stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getSharedSecret()))) {
/*  67 */       model.addAttribute("msg", "地址、名称、密钥必须填写！");
/*  68 */       model.addAttribute("entity", e);
/*  69 */       return "radiusnas/save";
/*     */     }
/*  71 */     RadiusnasQuery q = new RadiusnasQuery();
/*  72 */     q.setIp(e.getIp());
/*  73 */     q.setIpLike(false);
/*  74 */     if (this.radiusnasService.getRadiusnasList(q).size() > 0) {
/*  75 */       model.addAttribute("msg", "该IP已经存在！");
/*  76 */       model.addAttribute("entity", e);
/*  77 */       return "radiusnas/save";
/*     */     }
/*  79 */     q = new RadiusnasQuery();
/*  80 */     q.setName(e.getName());
/*  81 */     q.setNameLike(false);
/*  82 */     if (this.radiusnasService.getRadiusnasList(q).size() > 0) {
/*  83 */       model.addAttribute("msg", "该名称已经存在！");
/*  84 */       model.addAttribute("entity", e);
/*  85 */       return "radiusnas/save";
/*     */     }
/*  87 */     this.radiusnasService.addRadiusnas(e);
/*  88 */     RadiusNasMap.getInstance().getNasMap().remove(e.getIp());
/*  89 */     String[] client = new String[9];
/*  90 */     client[0] = Tool.ByteToHex(e.getSharedSecret().getBytes());
/*  91 */     client[1] = e.getType();
/*  92 */     client[2] = e.getEx1();
/*  93 */     client[3] = e.getEx2();
/*  94 */     client[4] = e.getEx3();
/*  95 */     client[5] = e.getEx4();
/*  96 */     client[6] = e.getEx5();
/*  97 */     client[7] = e.getName();
/*  98 */     client[8] = e.getEx6();
/*  99 */     RadiusNasMap.getInstance().getNasMap().put(e.getIp(), client);
/* 100 */     return "redirect:/radiusnas/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiusnas/edit.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 106 */     Radiusnas e = this.radiusnasService.getRadiusnasByKey(id);
/* 107 */     model.addAttribute("entity", e);
/* 108 */     return "radiusnas/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiusnas/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Radiusnas e, ModelMap model)
/*     */   {
/* 114 */     if ((stringUtils.isBlank(e.getIp())) || (stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getSharedSecret()))) {
/* 115 */       model.addAttribute("msg", "地址、名称、密钥必须填写！");
/* 116 */       model.addAttribute("entity", e);
/* 117 */       return "radiusnas/save";
/*     */     }
/* 119 */     RadiusnasQuery q = new RadiusnasQuery();
/* 120 */     q.setIp(e.getIp());
/* 121 */     q.setIpLike(false);
/* 122 */     List nasList = this.radiusnasService.getRadiusnasList(q);
/* 123 */     if ((nasList.size() > 0) && 
/* 124 */       (((Radiusnas)nasList.get(0)).getId() != e.getId())) {
/* 125 */       model.addAttribute("msg", "该IP已经存在！");
/* 126 */       model.addAttribute("entity", e);
/* 127 */       return "radiusnas/save";
/*     */     }
/*     */ 
/* 130 */     q = new RadiusnasQuery();
/* 131 */     q.setName(e.getName());
/* 132 */     q.setNameLike(false);
/* 133 */     nasList = this.radiusnasService.getRadiusnasList(q);
/* 134 */     if ((nasList.size() > 0) && 
/* 135 */       (((Radiusnas)nasList.get(0)).getId() != e.getId())) {
/* 136 */       model.addAttribute("msg", "该名称已经存在！");
/* 137 */       model.addAttribute("entity", e);
/* 138 */       return "radiusnas/save";
/*     */     }
/*     */ 
/* 141 */     RadiusNasMap.getInstance().getNasMap().remove(this.radiusnasService.getRadiusnasByKey(e.getId()).getIp());
/* 142 */     String[] client = new String[9];
/* 143 */     client[0] = Tool.ByteToHex(e.getSharedSecret().getBytes());
/* 144 */     client[1] = e.getType();
/* 145 */     client[2] = e.getEx1();
/* 146 */     client[3] = e.getEx2();
/* 147 */     client[4] = e.getEx3();
/* 148 */     client[5] = e.getEx4();
/* 149 */     client[6] = e.getEx5();
/* 150 */     client[7] = e.getName();
/* 151 */     client[8] = e.getEx6();
/* 152 */     RadiusNasMap.getInstance().getNasMap().put(e.getIp(), client);
/* 153 */     this.radiusnasService.updateRadiusnasByKey(e);
/* 154 */     return "redirect:/radiusnas/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiusnas/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 162 */     RadiusNasMap.getInstance().getNasMap().remove(this.radiusnasService.getRadiusnasByKey(id).getIp());
/* 163 */     this.radiusnasService.deleteByKey(id);
/* 164 */     return "redirect:/radiusnas/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiusnas/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 171 */     List<Long> list = Arrays.asList(ids);
/* 172 */     for (Long id : list) {
/* 173 */       RadiusNasMap.getInstance().getNasMap().remove(this.radiusnasService.getRadiusnasByKey(id).getIp());
/*     */     }
/* 175 */     this.radiusnasService.deleteByKeys(list);
/*     */ 
/* 177 */     return "redirect:/radiusnas/list.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiusnasController
 * JD-Core Version:    0.6.2
 */