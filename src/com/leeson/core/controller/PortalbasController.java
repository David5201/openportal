/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalbasauth;
/*     */ import com.leeson.core.bean.Portalconfig;
/*     */ import com.leeson.core.query.PortalbasQuery;
/*     */ import com.leeson.core.query.PortalbasauthQuery;
/*     */ import com.leeson.core.query.PortalbasauthView;
/*     */ import com.leeson.core.query.PortalconfigQuery;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalbasService;
/*     */ import com.leeson.core.service.PortalbasauthService;
/*     */ import com.leeson.core.service.PortalconfigService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import com.leeson.portal.core.model.WySlot15gasa;
/*     */ import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
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
/*     */ public class PortalbasController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalbasService portalbasService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalconfigService portalconfigService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalbasauthService portalbasauthService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalwebService portalwebService;
/*     */ 
/*     */   @Autowired
/*     */   private ConfigService configService;
/*  60 */   private static SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */   @RequestMapping({"/portalbas/list.action"})
/*     */   public String page(PortalbasQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  65 */     if (stringUtils.isBlank(query.getBasname())) {
/*  66 */       query.setBasname(null);
/*     */     }
/*  68 */     if (stringUtils.isBlank(query.getBasIp())) {
/*  69 */       query.setBasIp(null);
/*     */     }
/*  71 */     query.setBasnameLike(true);
/*  72 */     query.setBasIpLike(true);
/*     */ 
/*  74 */     Pagination pagination = this.portalbasService.getPortalbasListWithPage(query);
/*  75 */     model.addAttribute("pagination", pagination);
/*  76 */     model.addAttribute("query", query);
/*  77 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  78 */     model.addAttribute("webs", webs);
/*  79 */     return "portalbas/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalbas/add.action"})
/*     */   public String addV(ModelMap model)
/*     */   {
/*  85 */     Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/*  86 */     model.addAttribute("entity", basic);
/*  87 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  88 */     model.addAttribute("webs", webs);
/*  89 */     return "portalbas/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalbas/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalbas e, ModelMap model, PortalbasauthView bav)
/*     */   {
/*  95 */     if ((stringUtils.isBlank(e.getBasname())) || (stringUtils.isBlank(e.getBasIp()))) {
/*  96 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/*  97 */       model.addAttribute("entity", basic);
/*  98 */       model.addAttribute("bas", e);
/*  99 */       model.addAttribute("msg", "Bas名称 和 BasIP 不能为空！!");
/* 100 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 101 */       model.addAttribute("webs", webs);
/* 102 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 105 */     PortalbasQuery bqip = new PortalbasQuery();
/* 106 */     bqip.setBasIp(e.getBasIp());
/* 107 */     bqip.setBasIpLike(false);
/* 108 */     if (this.portalbasService.getPortalbasList(bqip).size() > 0) {
/* 109 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 110 */       model.addAttribute("entity", basic);
/* 111 */       e.setBasIp("");
/* 112 */       model.addAttribute("bas", e);
/* 113 */       model.addAttribute("msg", "Bas IP 已经存在！!");
/* 114 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 115 */       model.addAttribute("webs", webs);
/* 116 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 119 */     PortalconfigQuery cq = new PortalconfigQuery();
/* 120 */     cq.setBasIp(e.getBasIp());
/* 121 */     cq.setBasIpLike(false);
/* 122 */     if (this.portalconfigService.getPortalconfigList(cq).size() > 0) {
/* 123 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 124 */       model.addAttribute("entity", basic);
/* 125 */       e.setBasIp("");
/* 126 */       model.addAttribute("bas", e);
/* 127 */       model.addAttribute("msg", "Bas IP 已经存在！!");
/* 128 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 129 */       model.addAttribute("webs", webs);
/* 130 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 133 */     PortalbasQuery bq = new PortalbasQuery();
/* 134 */     bq.setBasname(e.getBasname());
/* 135 */     bq.setBasnameLike(false);
/* 136 */     if (this.portalbasService.getPortalbasList(bq).size() > 0) {
/* 137 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 138 */       model.addAttribute("entity", basic);
/* 139 */       e.setBasname("");
/* 140 */       model.addAttribute("bas", e);
/* 141 */       model.addAttribute("msg", "Bas名称 已经存在！!");
/* 142 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 143 */       model.addAttribute("webs", webs);
/* 144 */       return "portalbas/save";
/*     */     }
/* 146 */     if ((e.getBas().equals("2")) || (e.getBas().equals("4"))) {
/* 147 */       e.setAuthType("0");
/* 148 */       e.setPortalVer("1");
/*     */     }
/* 150 */     if (e.getAuthInterface() == null) {
/* 151 */       e.setAuthInterface("");
/*     */     }
/*     */ 
/* 155 */     int basCount = 0;
/* 156 */     Date nowDate = new Date();
/* 157 */     String nowString = fs.format(nowDate);
/*     */     try {
/* 159 */       nowDate = fs.parse(nowString);
/*     */     } catch (ParseException err) {
/* 161 */       nowDate = new Date();
/*     */     }
/* 163 */     String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
/* 164 */     if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
/* 165 */       RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
/*     */     }
/* 167 */     String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
/* 168 */     Boolean notCan = Boolean.valueOf(false);
/* 169 */     if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
/*     */       Date saveDate;
/*     */       try {
/* 173 */         saveDate = fs.parse(toDateString);
/*     */       }
/*     */       catch (ParseException err)
/*     */       {
/* 175 */         saveDate = nowDate;
/*     */       }
/* 177 */       if (nowDate.getTime() < saveDate.getTime())
/* 178 */         basCount = Integer.valueOf(MxMzIyRDMzMzAy[4]).intValue();
/*     */       else
/* 180 */         notCan = Boolean.valueOf(true);
/*     */     } else
/*     */     {
/* 183 */       notCan = Boolean.valueOf(true);
/*     */     }
/* 185 */     Integer count = this.portalbasService.getPortalbasCount(new PortalbasQuery());
/* 186 */     if (notCan.booleanValue()) {
/* 187 */       if ((count != null) && 
/* 188 */         (count.intValue() >= 2)) {
/* 189 */         Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 190 */         model.addAttribute("entity", basic);
/* 191 */         model.addAttribute("bas", e);
/* 192 */         model.addAttribute("msg", "系统未授权，只能添加2个多BAS设置！!");
/* 193 */         List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 194 */         model.addAttribute("webs", webs);
/* 195 */         return "portalbas/save";
/*     */       }
/*     */     }
/* 198 */     else if ((count != null) && 
/* 199 */       (count.intValue() >= basCount)) {
/* 200 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 201 */       model.addAttribute("entity", basic);
/* 202 */       model.addAttribute("bas", e);
/* 203 */       model.addAttribute("msg", "BAS授权数已经达到上限！!");
/* 204 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 205 */       model.addAttribute("webs", webs);
/* 206 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 209 */     e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
/* 210 */     if (e.getLateAuthTime() == null) {
/* 211 */       e.setLateAuthTime(Long.valueOf(10L));
/*     */     }
/* 213 */     this.portalbasService.addPortalbas(e);
/* 214 */     SetAuth(e, bav);
/*     */ 
/* 216 */     com.leeson.portal.core.model.Config.getInstance().getConfigMap().put(e.getBasIp(), e);
/* 217 */     return "redirect:/portalbas/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalbas/edit.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 223 */     Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 224 */     model.addAttribute("entity", basic);
/*     */ 
/* 226 */     Portalbas e = this.portalbasService.getPortalbasByKey(id);
/* 227 */     e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
/* 228 */     model.addAttribute("bas", e);
/* 229 */     PortalbasauthQuery baq = new PortalbasauthQuery();
/* 230 */     baq.setBasid(e.getId());
/* 231 */     List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 232 */     for (Portalbasauth ba : basauths) {
/* 233 */       model.addAttribute("username" + ba.getType(), ba.getUsername());
/* 234 */       model.addAttribute("password" + ba.getType(), ba.getPassword());
/* 235 */       model.addAttribute("url" + ba.getType(), ba.getUrl());
/* 236 */       model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */     }
/* 238 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 239 */     model.addAttribute("webs", webs);
/* 240 */     return "portalbas/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalbas/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalbas e, ModelMap model, PortalbasauthView bav)
/*     */   {
/* 246 */     if ((stringUtils.isBlank(e.getBasname())) || (stringUtils.isBlank(e.getBasIp()))) {
/* 247 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 248 */       model.addAttribute("entity", basic);
/* 249 */       model.addAttribute("bas", e);
/* 250 */       model.addAttribute("msg", "Bas名称 和 BasIP 不能为空！!");
/*     */ 
/* 252 */       PortalbasauthQuery baq = new PortalbasauthQuery();
/* 253 */       baq.setBasid(e.getId());
/* 254 */       List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 255 */       for (Portalbasauth ba : basauths) {
/* 256 */         model.addAttribute("username" + ba.getType(), ba.getUsername());
/* 257 */         model.addAttribute("password" + ba.getType(), ba.getPassword());
/* 258 */         model.addAttribute("url" + ba.getType(), ba.getUrl());
/* 259 */         model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */       }
/* 261 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 262 */       model.addAttribute("webs", webs);
/* 263 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 266 */     PortalbasQuery bqip = new PortalbasQuery();
/* 267 */     bqip.setBasIp(e.getBasIp());
/* 268 */     bqip.setBasIpLike(false);
/* 269 */     List bips = this.portalbasService.getPortalbasList(bqip);
/* 270 */     if ((bips.size() > 0) && 
/* 271 */       (((Portalbas)bips.get(0)).getId() != e.getId())) {
/* 272 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 273 */       model.addAttribute("entity", basic);
/* 274 */       e.setBasIp("");
/* 275 */       model.addAttribute("bas", e);
/* 276 */       model.addAttribute("msg", "Bas IP 已经存在！!");
/*     */ 
/* 278 */       PortalbasauthQuery baq = new PortalbasauthQuery();
/* 279 */       baq.setBasid(e.getId());
/* 280 */       List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 281 */       for (Portalbasauth ba : basauths) {
/* 282 */         model.addAttribute("username" + ba.getType(), ba.getUsername());
/* 283 */         model.addAttribute("password" + ba.getType(), ba.getPassword());
/* 284 */         model.addAttribute("url" + ba.getType(), ba.getUrl());
/* 285 */         model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */       }
/* 287 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 288 */       model.addAttribute("webs", webs);
/* 289 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 293 */     PortalconfigQuery cq = new PortalconfigQuery();
/* 294 */     cq.setBasIp(e.getBasIp());
/* 295 */     cq.setBasIpLike(false);
/* 296 */     if (this.portalconfigService.getPortalconfigList(cq).size() > 0) {
/* 297 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 298 */       model.addAttribute("entity", basic);
/* 299 */       e.setBasIp("");
/* 300 */       model.addAttribute("bas", e);
/* 301 */       model.addAttribute("msg", "Bas IP 已经存在，基础配置就是该Bas IP ！!");
/* 302 */       PortalbasauthQuery baq = new PortalbasauthQuery();
/* 303 */       baq.setBasid(e.getId());
/* 304 */       List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 305 */       for (Portalbasauth ba : basauths) {
/* 306 */         model.addAttribute("username" + ba.getType(), ba.getUsername());
/* 307 */         model.addAttribute("password" + ba.getType(), ba.getPassword());
/* 308 */         model.addAttribute("url" + ba.getType(), ba.getUrl());
/* 309 */         model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */       }
/* 311 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 312 */       model.addAttribute("webs", webs);
/* 313 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 316 */     PortalbasQuery bq = new PortalbasQuery();
/* 317 */     bq.setBasname(e.getBasname());
/* 318 */     bq.setBasnameLike(false);
/* 319 */     List bs = this.portalbasService.getPortalbasList(bq);
/* 320 */     if ((bs.size() > 0) && 
/* 321 */       (((Portalbas)bs.get(0)).getId() != e.getId())) {
/* 322 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 323 */       model.addAttribute("entity", basic);
/* 324 */       e.setBasname("");
/* 325 */       model.addAttribute("bas", e);
/* 326 */       model.addAttribute("msg", "Bas名称 已经存在！!");
/* 327 */       PortalbasauthQuery baq = new PortalbasauthQuery();
/* 328 */       baq.setBasid(e.getId());
/* 329 */       List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 330 */       for (Portalbasauth ba : basauths) {
/* 331 */         model.addAttribute("username" + ba.getType(), ba.getUsername());
/* 332 */         model.addAttribute("password" + ba.getType(), ba.getPassword());
/* 333 */         model.addAttribute("url" + ba.getType(), ba.getUrl());
/* 334 */         model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */       }
/* 336 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 337 */       model.addAttribute("webs", webs);
/* 338 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 341 */     if ((e.getBas().equals("2")) || (e.getBas().equals("4"))) {
/* 342 */       e.setAuthType("0");
/* 343 */       e.setPortalVer("1");
/*     */     }
/* 345 */     if (e.getAuthInterface() == null) {
/* 346 */       e.setAuthInterface("");
/*     */     }
/*     */ 
/* 350 */     int basCount = 0;
/* 351 */     Date nowDate = new Date();
/* 352 */     String nowString = fs.format(nowDate);
/*     */     try {
/* 354 */       nowDate = fs.parse(nowString);
/*     */     } catch (ParseException err) {
/* 356 */       nowDate = new Date();
/*     */     }
/* 358 */     String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
/* 359 */     if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
/* 360 */       RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
/*     */     }
/* 362 */     String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
/* 363 */     Boolean notCan = Boolean.valueOf(false);
/* 364 */     if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
/*     */       Date saveDate;
/*     */       try {
/* 368 */         saveDate = fs.parse(toDateString);
/*     */       }
/*     */       catch (ParseException err)
/*     */       {
/* 370 */         saveDate = nowDate;
/*     */       }
/* 372 */       if (nowDate.getTime() < saveDate.getTime())
/* 373 */         basCount = Integer.valueOf(MxMzIyRDMzMzAy[4]).intValue();
/*     */       else
/* 375 */         notCan = Boolean.valueOf(true);
/*     */     } else
/*     */     {
/* 378 */       notCan = Boolean.valueOf(true);
/*     */     }
/* 380 */     Integer count = this.portalbasService.getPortalbasCount(new PortalbasQuery());
/* 381 */     if (notCan.booleanValue()) {
/* 382 */       if ((count != null) && 
/* 383 */         (count.intValue() > 2)) {
/* 384 */         Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 385 */         model.addAttribute("entity", basic);
/* 386 */         model.addAttribute("bas", e);
/* 387 */         model.addAttribute("msg", "系统未授权，只能添加2个多BAS设置！!");
/* 388 */         List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 389 */         model.addAttribute("webs", webs);
/* 390 */         return "portalbas/save";
/*     */       }
/*     */     }
/* 393 */     else if ((count != null) && 
/* 394 */       (count.intValue() > basCount)) {
/* 395 */       Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 396 */       model.addAttribute("entity", basic);
/* 397 */       model.addAttribute("bas", e);
/* 398 */       model.addAttribute("msg", "BAS授权数已经达到上限！!");
/* 399 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 400 */       model.addAttribute("webs", webs);
/* 401 */       return "portalbas/save";
/*     */     }
/*     */ 
/* 405 */     Portalbas oldbas = this.portalbasService.getPortalbasByKey(e.getId());
/* 406 */     com.leeson.portal.core.model.Config.getInstance().getConfigMap().remove(oldbas.getBasIp());
/* 407 */     e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
/* 408 */     if (e.getLateAuthTime() == null) {
/* 409 */       e.setLateAuthTime(Long.valueOf(10L));
/*     */     }
/* 411 */     this.portalbasService.updatePortalbasByKeyAll(e);
/* 412 */     SetAuth(e, bav);
/*     */ 
/* 414 */     com.leeson.portal.core.model.Config.getInstance().getConfigMap().put(e.getBasIp(), e);
/* 415 */     return "redirect:/portalbas/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalbas/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 423 */     Portalbas oldbas = this.portalbasService.getPortalbasByKey(id);
/* 424 */     com.leeson.portal.core.model.Config.getInstance().getConfigMap().remove(oldbas.getBasIp());
/* 425 */     this.portalbasService.deleteByKey(id);
/* 426 */     DelAuth(id);
/* 427 */     return "redirect:/portalbas/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalbas/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 434 */     List<Long> list = Arrays.asList(ids);
/* 435 */     for (Long id : list) {
/* 436 */       Portalbas oldbas = this.portalbasService.getPortalbasByKey(id);
/* 437 */       com.leeson.portal.core.model.Config.getInstance().getConfigMap().remove(oldbas.getBasIp());
/* 438 */       DelAuth(id);
/*     */     }
/* 440 */     this.portalbasService.deleteByKeys(list);
/*     */ 
/* 442 */     return "redirect:/portalbas/list.action";
/*     */   }
/*     */ 
/*     */   private void DelAuth(Long basId)
/*     */   {
/* 448 */     PortalbasauthQuery bas = new PortalbasauthQuery();
/* 449 */     bas.setBasid(basId);
/* 450 */     this.portalbasauthService.deleteByQuery(bas);
/*     */   }
/*     */ 
/*     */   private void SetAuth(Portalbas e, PortalbasauthView bav) {
/* 454 */     if (bav.getTime0() == null) {
/* 455 */       bav.setTime0(Long.valueOf(0L));
/*     */     }
/* 457 */     if (bav.getTime1() == null) {
/* 458 */       bav.setTime1(Long.valueOf(0L));
/*     */     }
/* 460 */     if (bav.getTime2() == null) {
/* 461 */       bav.setTime2(Long.valueOf(0L));
/*     */     }
/* 463 */     if (bav.getTime3() == null) {
/* 464 */       bav.setTime3(Long.valueOf(0L));
/*     */     }
/* 466 */     if (bav.getTime4() == null) {
/* 467 */       bav.setTime4(Long.valueOf(0L));
/*     */     }
/* 469 */     if (bav.getTime5() == null) {
/* 470 */       bav.setTime5(Long.valueOf(0L));
/*     */     }
/* 472 */     if (bav.getTime6() == null) {
/* 473 */       bav.setTime6(Long.valueOf(0L));
/*     */     }
/* 475 */     if (bav.getTime7() == null) {
/* 476 */       bav.setTime7(Long.valueOf(0L));
/*     */     }
/*     */ 
/* 479 */     PortalbasauthQuery baq = new PortalbasauthQuery();
/* 480 */     baq.setBasid(e.getId());
/*     */ 
/* 482 */     baq.setType(Integer.valueOf(0));
/* 483 */     List basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 484 */     if (basauths.size() > 0) {
/* 485 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 486 */       ba.setBasip(e.getBasIp());
/* 487 */       ba.setUsername(bav.getUsername0());
/* 488 */       ba.setPassword(bav.getPassword0());
/* 489 */       ba.setUrl(bav.getUrl0());
/* 490 */       ba.setType(Integer.valueOf(0));
/* 491 */       ba.setSessiontime(Long.valueOf(bav.getTime0().longValue() * 60000L));
/* 492 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 494 */       Portalbasauth ba = new Portalbasauth();
/* 495 */       ba.setBasid(e.getId());
/* 496 */       ba.setBasip(e.getBasIp());
/* 497 */       ba.setUsername(bav.getUsername0());
/* 498 */       ba.setPassword(bav.getPassword0());
/* 499 */       ba.setUrl(bav.getUrl0());
/* 500 */       ba.setType(Integer.valueOf(0));
/* 501 */       ba.setSessiontime(Long.valueOf(bav.getTime0().longValue() * 60000L));
/* 502 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 505 */     baq.setType(Integer.valueOf(1));
/* 506 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 507 */     if (basauths.size() > 0) {
/* 508 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 509 */       ba.setBasip(e.getBasIp());
/* 510 */       ba.setUsername(bav.getUsername1());
/* 511 */       ba.setPassword(bav.getPassword1());
/* 512 */       ba.setUrl(bav.getUrl1());
/* 513 */       ba.setSessiontime(Long.valueOf(bav.getTime1().longValue() * 60000L));
/* 514 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 516 */       Portalbasauth ba = new Portalbasauth();
/* 517 */       ba.setBasid(e.getId());
/* 518 */       ba.setBasip(e.getBasIp());
/* 519 */       ba.setUsername(bav.getUsername1());
/* 520 */       ba.setPassword(bav.getPassword1());
/* 521 */       ba.setUrl(bav.getUrl1());
/* 522 */       ba.setType(Integer.valueOf(1));
/* 523 */       ba.setSessiontime(Long.valueOf(bav.getTime1().longValue() * 60000L));
/* 524 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 527 */     baq.setType(Integer.valueOf(2));
/* 528 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 529 */     if (basauths.size() > 0) {
/* 530 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 531 */       ba.setBasip(e.getBasIp());
/* 532 */       ba.setUsername(bav.getUsername2());
/* 533 */       ba.setPassword(bav.getPassword2());
/* 534 */       ba.setUrl(bav.getUrl2());
/* 535 */       ba.setSessiontime(Long.valueOf(bav.getTime2().longValue() * 60000L));
/* 536 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 538 */       Portalbasauth ba = new Portalbasauth();
/* 539 */       ba.setBasid(e.getId());
/* 540 */       ba.setBasip(e.getBasIp());
/* 541 */       ba.setUsername(bav.getUsername2());
/* 542 */       ba.setPassword(bav.getPassword2());
/* 543 */       ba.setUrl(bav.getUrl2());
/* 544 */       ba.setType(Integer.valueOf(2));
/* 545 */       ba.setSessiontime(Long.valueOf(bav.getTime2().longValue() * 60000L));
/* 546 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 549 */     baq.setType(Integer.valueOf(3));
/* 550 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 551 */     if (basauths.size() > 0) {
/* 552 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 553 */       ba.setBasip(e.getBasIp());
/* 554 */       ba.setUsername(bav.getUsername3());
/* 555 */       ba.setPassword(bav.getPassword3());
/* 556 */       ba.setUrl(bav.getUrl3());
/* 557 */       ba.setSessiontime(Long.valueOf(bav.getTime3().longValue() * 60000L));
/* 558 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 560 */       Portalbasauth ba = new Portalbasauth();
/* 561 */       ba.setBasid(e.getId());
/* 562 */       ba.setBasip(e.getBasIp());
/* 563 */       ba.setUsername(bav.getUsername3());
/* 564 */       ba.setPassword(bav.getPassword3());
/* 565 */       ba.setUrl(bav.getUrl3());
/* 566 */       ba.setType(Integer.valueOf(3));
/* 567 */       ba.setSessiontime(Long.valueOf(bav.getTime3().longValue() * 60000L));
/* 568 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 571 */     baq.setType(Integer.valueOf(4));
/* 572 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 573 */     if (basauths.size() > 0) {
/* 574 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 575 */       ba.setBasip(e.getBasIp());
/* 576 */       ba.setUsername(bav.getUsername4());
/* 577 */       ba.setPassword(bav.getPassword4());
/* 578 */       ba.setUrl(bav.getUrl4());
/* 579 */       ba.setSessiontime(Long.valueOf(bav.getTime4().longValue() * 60000L));
/* 580 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 582 */       Portalbasauth ba = new Portalbasauth();
/* 583 */       ba.setBasid(e.getId());
/* 584 */       ba.setBasip(e.getBasIp());
/* 585 */       ba.setUsername(bav.getUsername4());
/* 586 */       ba.setPassword(bav.getPassword4());
/* 587 */       ba.setUrl(bav.getUrl4());
/* 588 */       ba.setType(Integer.valueOf(4));
/* 589 */       ba.setSessiontime(Long.valueOf(bav.getTime4().longValue() * 60000L));
/* 590 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 593 */     baq.setType(Integer.valueOf(5));
/* 594 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 595 */     if (basauths.size() > 0) {
/* 596 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 597 */       ba.setBasip(e.getBasIp());
/* 598 */       ba.setUsername(bav.getUsername5());
/* 599 */       ba.setPassword(bav.getPassword5());
/* 600 */       ba.setUrl(bav.getUrl5());
/* 601 */       ba.setSessiontime(Long.valueOf(bav.getTime5().longValue() * 60000L));
/* 602 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 604 */       Portalbasauth ba = new Portalbasauth();
/* 605 */       ba.setBasid(e.getId());
/* 606 */       ba.setBasip(e.getBasIp());
/* 607 */       ba.setUsername(bav.getUsername5());
/* 608 */       ba.setPassword(bav.getPassword5());
/* 609 */       ba.setUrl(bav.getUrl5());
/* 610 */       ba.setType(Integer.valueOf(5));
/* 611 */       ba.setSessiontime(Long.valueOf(bav.getTime5().longValue() * 60000L));
/* 612 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 615 */     baq.setType(Integer.valueOf(6));
/* 616 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 617 */     if (basauths.size() > 0) {
/* 618 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 619 */       ba.setBasip(e.getBasIp());
/* 620 */       ba.setUsername(bav.getUsername6());
/* 621 */       ba.setPassword(bav.getPassword6());
/* 622 */       ba.setUrl(bav.getUrl6());
/* 623 */       ba.setSessiontime(Long.valueOf(bav.getTime6().longValue() * 60000L));
/* 624 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 626 */       Portalbasauth ba = new Portalbasauth();
/* 627 */       ba.setBasid(e.getId());
/* 628 */       ba.setBasip(e.getBasIp());
/* 629 */       ba.setUsername(bav.getUsername6());
/* 630 */       ba.setPassword(bav.getPassword6());
/* 631 */       ba.setUrl(bav.getUrl6());
/* 632 */       ba.setType(Integer.valueOf(6));
/* 633 */       ba.setSessiontime(Long.valueOf(bav.getTime6().longValue() * 60000L));
/* 634 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 637 */     baq.setType(Integer.valueOf(7));
/* 638 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 639 */     if (basauths.size() > 0) {
/* 640 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 641 */       ba.setBasip(e.getBasIp());
/* 642 */       ba.setUsername(bav.getUsername7());
/* 643 */       ba.setPassword(bav.getPassword7());
/* 644 */       ba.setUrl(bav.getUrl7());
/* 645 */       ba.setSessiontime(Long.valueOf(bav.getTime7().longValue() * 60000L));
/* 646 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 648 */       Portalbasauth ba = new Portalbasauth();
/* 649 */       ba.setBasid(e.getId());
/* 650 */       ba.setBasip(e.getBasIp());
/* 651 */       ba.setUsername(bav.getUsername7());
/* 652 */       ba.setPassword(bav.getPassword7());
/* 653 */       ba.setUrl(bav.getUrl7());
/* 654 */       ba.setType(Integer.valueOf(7));
/* 655 */       ba.setSessiontime(Long.valueOf(bav.getTime7().longValue() * 60000L));
/* 656 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalbasController
 * JD-Core Version:    0.6.2
 */