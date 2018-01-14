/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portallinkrecordall;
/*     */ import com.leeson.core.query.PortallinkrecordallQuery;
/*     */ import com.leeson.core.service.PortallinkrecordallService;
/*     */ import com.leeson.core.utils.ExcelUtils;
/*     */ import java.io.File;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortallinkrecordallController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortallinkrecordallService portallinkrecordallService;
/*  39 */   private static SimpleDateFormat format = new SimpleDateFormat(
/*  40 */     "yyyy-MM-dd HH:mm:ss");
/*     */ 
/*  41 */   private static DecimalFormat df = new DecimalFormat(".##");
/*     */ 
/*     */   @RequestMapping({"/portallinkrecordall/list.action"})
/*     */   public String page(PortallinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  48 */     query.setLoginNameLike(true);
/*  49 */     query.setIpLike(true);
/*  50 */     query.setBasipLike(true);
/*  51 */     query.setApmacLike(true);
/*  52 */     query.setBasnameLike(true);
/*  53 */     query.setMacLike(true);
/*  54 */     query.setSsidLike(true);
/*  55 */     query.orderbyId(false);
/*  56 */     if (stringUtils.isBlank(query.getLoginName())) {
/*  57 */       query.setLoginName(null);
/*     */     }
/*  59 */     if (stringUtils.isBlank(query.getIp())) {
/*  60 */       query.setIp(null);
/*     */     }
/*  62 */     if (stringUtils.isBlank(query.getBasip())) {
/*  63 */       query.setBasip(null);
/*     */     }
/*  65 */     if (stringUtils.isBlank(query.getApmac())) {
/*  66 */       query.setApmac(null);
/*     */     }
/*  68 */     if (stringUtils.isBlank(query.getBasname())) {
/*  69 */       query.setBasname(null);
/*     */     }
/*  71 */     if (stringUtils.isBlank(query.getMac())) {
/*  72 */       query.setMac(null);
/*     */     }
/*  74 */     if (stringUtils.isBlank(query.getSsid())) {
/*  75 */       query.setSsid(null);
/*     */     }
/*  77 */     if (stringUtils.isBlank(query.getAgent())) {
/*  78 */       query.setAgent(null);
/*     */     }
/*  80 */     if (stringUtils.isBlank(query.getAuto())) {
/*  81 */       query.setAuto(null);
/*     */     }
/*  83 */     if (stringUtils.isBlank(query.getMethodtype())) {
/*  84 */       query.setMethodtype(null);
/*     */     }
/*  86 */     if (stringUtils.isBlank(query.getState())) {
/*  87 */       query.setState(null);
/*     */     }
/*     */ 
/*  90 */     if (stringUtils.isBlank(query_begin_time)) {
/*  91 */       query_begin_time = null;
/*     */     }
/*  93 */     if (stringUtils.isBlank(query_end_time)) {
/*  94 */       query_end_time = null;
/*     */     }
/*  96 */     Date begin_time = null;
/*  97 */     Date end_time = null;
/*     */     try {
/*  99 */       if (stringUtils.isNotBlank(query_begin_time)) {
/* 100 */         begin_time = format.parse(query_begin_time + " 00:00:00");
/* 101 */         query.setBegin_time(begin_time);
/*     */       }
/*     */     } catch (Exception e) {
/* 104 */       model.addAttribute("msg", "开始日期格式错误！");
/* 105 */       query_begin_time = null;
/*     */     }
/*     */     try {
/* 108 */       if (stringUtils.isNotBlank(query_end_time)) {
/* 109 */         end_time = format.parse(query_end_time + " 23:59:59");
/* 110 */         query.setEnd_time(end_time);
/*     */       }
/*     */     } catch (Exception e) {
/* 113 */       model.addAttribute("msg", "结束日期格式错误！");
/* 114 */       query_end_time = null;
/*     */     }
/*     */ 
/* 130 */     Pagination pagination = this.portallinkrecordallService
/* 131 */       .getPortallinkrecordallListWithPage(query);
/* 132 */     model.addAttribute("pagination", pagination);
/* 133 */     model.addAttribute("query", query);
/* 134 */     model.addAttribute("query_begin_time", query_begin_time);
/* 135 */     model.addAttribute("query_end_time", query_end_time);
/* 136 */     return "portallinkrecordall/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portallinkrecordall/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 142 */     this.portallinkrecordallService.deleteByKey(id);
/* 143 */     return "redirect:/portallinkrecordall/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallinkrecordall/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 150 */     List list = Arrays.asList(ids);
/* 151 */     this.portallinkrecordallService.deleteByKeys(list);
/* 152 */     return "redirect:/portallinkrecordall/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portallinkrecordall/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 159 */     this.portallinkrecordallService.deleteAll();
/* 160 */     return "redirect:/portallinkrecordall/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portallinkrecordall/outlist.action"})
/*     */   public String outlist(PortallinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 168 */     query.setLoginNameLike(true);
/* 169 */     query.setIpLike(true);
/* 170 */     query.setBasipLike(true);
/* 171 */     query.setApmacLike(true);
/* 172 */     query.setBasnameLike(true);
/* 173 */     query.setMacLike(true);
/* 174 */     query.setSsidLike(true);
/* 175 */     query.orderbyId(false);
/* 176 */     if (stringUtils.isBlank(query.getLoginName())) {
/* 177 */       query.setLoginName(null);
/*     */     }
/* 179 */     if (stringUtils.isBlank(query.getIp())) {
/* 180 */       query.setIp(null);
/*     */     }
/* 182 */     if (stringUtils.isBlank(query.getBasip())) {
/* 183 */       query.setBasip(null);
/*     */     }
/* 185 */     if (stringUtils.isBlank(query.getApmac())) {
/* 186 */       query.setApmac(null);
/*     */     }
/* 188 */     if (stringUtils.isBlank(query.getBasname())) {
/* 189 */       query.setBasname(null);
/*     */     }
/* 191 */     if (stringUtils.isBlank(query.getMac())) {
/* 192 */       query.setMac(null);
/*     */     }
/* 194 */     if (stringUtils.isBlank(query.getSsid())) {
/* 195 */       query.setSsid(null);
/*     */     }
/* 197 */     if (stringUtils.isBlank(query.getAgent())) {
/* 198 */       query.setAgent(null);
/*     */     }
/* 200 */     if (stringUtils.isBlank(query.getAuto())) {
/* 201 */       query.setAuto(null);
/*     */     }
/* 203 */     if (stringUtils.isBlank(query.getMethodtype())) {
/* 204 */       query.setMethodtype(null);
/*     */     }
/* 206 */     if (stringUtils.isBlank(query.getState())) {
/* 207 */       query.setState(null);
/*     */     }
/*     */ 
/* 210 */     if (stringUtils.isBlank(query_begin_time)) {
/* 211 */       query_begin_time = null;
/*     */     }
/* 213 */     if (stringUtils.isBlank(query_end_time)) {
/* 214 */       query_end_time = null;
/*     */     }
/* 216 */     Date begin_time = null;
/* 217 */     Date end_time = null;
/*     */     try {
/* 219 */       if (stringUtils.isNotBlank(query_begin_time)) {
/* 220 */         begin_time = format.parse(query_begin_time + " 00:00:00");
/* 221 */         query.setBegin_time(begin_time);
/*     */       }
/*     */     } catch (Exception e) {
/* 224 */       model.addAttribute("msg", "开始日期格式错误！");
/* 225 */       query_begin_time = null;
/*     */     }
/*     */     try {
/* 228 */       if (stringUtils.isNotBlank(query_end_time)) {
/* 229 */         end_time = format.parse(query_end_time + " 23:59:59");
/* 230 */         query.setEnd_time(end_time);
/*     */       }
/*     */     } catch (Exception e) {
/* 233 */       model.addAttribute("msg", "结束日期格式错误！");
/* 234 */       query_end_time = null;
/*     */     }
/*     */ 
/* 237 */     long onlineTime = 0L;
/* 238 */     List<Portallinkrecordall> linkAll = this.portallinkrecordallService
/* 239 */       .getPortallinkrecordallList(query);
/* 240 */     for (Portallinkrecordall link : linkAll) {
/* 241 */       onlineTime += link.getTime().longValue();
/*     */     }
/* 243 */     onlineTime /= 60000L;
/* 244 */     String onlineTimeS = df.format(onlineTime);
/* 245 */     if (onlineTimeS.startsWith(".")) {
/* 246 */       onlineTimeS = "0" + onlineTimeS;
/*     */     }
/*     */ 
/* 249 */     String cfgPath = request.getServletContext().getRealPath("/");
/* 250 */     Date now = new Date();
/* 251 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 252 */     String nowString = format.format(now);
/* 253 */     File dir = new File(cfgPath + "ExcelOut/");
/* 254 */     if (!dir.exists()) {
/* 255 */       dir.mkdirs();
/*     */     }
/* 257 */     String fileName = cfgPath + "ExcelOut/Links_" + nowString + ".xls";
/*     */     try {
/* 259 */       ExcelUtils.writeLinksToExcel(fileName, linkAll, onlineTimeS);
/*     */     } catch (Exception e) {
/* 261 */       model.addAttribute("msg", "文件创建失败！");
/* 262 */       model.addAttribute("downUrl", null);
/* 263 */       model.addAttribute("err", Integer.valueOf(1));
/* 264 */       return "portallinkrecordall/outResult";
/*     */     }
/* 266 */     model.addAttribute("msg", "文件创建成功！");
/* 267 */     model.addAttribute("downUrl", "Links_" + nowString + ".xls");
/* 268 */     model.addAttribute("creatDate", nowString);
/* 269 */     model.addAttribute("err", Integer.valueOf(0));
/* 270 */     return "portallinkrecordall/outResult";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallinkrecordallController
 * JD-Core Version:    0.6.2
 */