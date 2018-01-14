/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Radiuslinkrecordall;
/*     */ import com.leeson.core.query.RadiuslinkrecordallQuery;
/*     */ import com.leeson.core.service.RadiuslinkrecordallService;
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
/*     */ public class RadiuslinkrecordallController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private RadiuslinkrecordallService radiuslinkrecordallService;
/*  39 */   private static SimpleDateFormat format = new SimpleDateFormat(
/*  40 */     "yyyy-MM-dd HH:mm:ss");
/*     */ 
/*  41 */   private static DecimalFormat df = new DecimalFormat(".##");
/*     */ 
/*     */   @RequestMapping({"/radiuslinkrecordall/list.action"})
/*     */   public String page(RadiuslinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  48 */     query.setEx3Like(true);
/*  49 */     query.setAcctsessionidLike(true);
/*  50 */     query.setCallingstationidLike(true);
/*  51 */     query.setNameLike(true);
/*  52 */     query.setNasipLike(true);
/*  53 */     query.setSourceipLike(true);
/*  54 */     query.setStateLike(true);
/*  55 */     query.setUseripLike(true);
/*  56 */     query.orderbyId(false);
/*  57 */     if (stringUtils.isBlank(query.getEx3())) {
/*  58 */       query.setEx3(null);
/*     */     }
/*  60 */     if (stringUtils.isBlank(query.getAcctsessionid())) {
/*  61 */       query.setAcctsessionid(null);
/*     */     }
/*  63 */     if (stringUtils.isBlank(query.getCallingstationid())) {
/*  64 */       query.setCallingstationid(null);
/*     */     }
/*  66 */     if (stringUtils.isBlank(query.getName())) {
/*  67 */       query.setName(null);
/*     */     }
/*  69 */     if (stringUtils.isBlank(query.getNasip())) {
/*  70 */       query.setNasip(null);
/*     */     }
/*  72 */     if (stringUtils.isBlank(query.getSourceip())) {
/*  73 */       query.setSourceip(null);
/*     */     }
/*  75 */     if (stringUtils.isBlank(query.getState())) {
/*  76 */       query.setState(null);
/*     */     }
/*  78 */     if (stringUtils.isBlank(query.getUserip())) {
/*  79 */       query.setUserip(null);
/*     */     }
/*  81 */     if (stringUtils.isBlank(query_begin_time)) {
/*  82 */       query_begin_time = null;
/*     */     }
/*  84 */     if (stringUtils.isBlank(query_end_time)) {
/*  85 */       query_end_time = null;
/*     */     }
/*  87 */     Date begin_time = null;
/*  88 */     Date end_time = null;
/*     */     try {
/*  90 */       if (stringUtils.isNotBlank(query_begin_time)) {
/*  91 */         begin_time = format.parse(query_begin_time + " 00:00:00");
/*  92 */         query.setBegin_time(begin_time);
/*     */       }
/*     */     } catch (Exception e) {
/*  95 */       model.addAttribute("msg", "开始日期格式错误！");
/*  96 */       query_begin_time = null;
/*     */     }
/*     */     try {
/*  99 */       if (stringUtils.isNotBlank(query_end_time)) {
/* 100 */         end_time = format.parse(query_end_time + " 23:59:59");
/* 101 */         query.setEnd_time(end_time);
/*     */       }
/*     */     } catch (Exception e) {
/* 104 */       model.addAttribute("msg", "结束日期格式错误！");
/* 105 */       query_end_time = null;
/*     */     }
/*     */ 
/* 129 */     Pagination pagination = this.radiuslinkrecordallService
/* 130 */       .getRadiuslinkrecordallListWithPage(query);
/* 131 */     model.addAttribute("pagination", pagination);
/* 132 */     model.addAttribute("query", query);
/* 133 */     model.addAttribute("query_begin_time", query_begin_time);
/* 134 */     model.addAttribute("query_end_time", query_end_time);
/* 135 */     return "radiuslinkrecordall/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiuslinkrecordall/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 141 */     this.radiuslinkrecordallService.deleteByKey(id);
/* 142 */     return "redirect:/radiuslinkrecordall/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiuslinkrecordall/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 149 */     List list = Arrays.asList(ids);
/* 150 */     this.radiuslinkrecordallService.deleteByKeys(list);
/*     */ 
/* 152 */     return "redirect:/radiuslinkrecordall/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/radiuslinkrecordall/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/* 158 */     this.radiuslinkrecordallService.deleteAll();
/* 159 */     return "redirect:/radiuslinkrecordall/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/radiuslinkrecordall/outlist.action"})
/*     */   public String outlist(RadiuslinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 168 */     query.setEx3Like(true);
/* 169 */     query.setAcctsessionidLike(true);
/* 170 */     query.setCallingstationidLike(true);
/* 171 */     query.setNameLike(true);
/* 172 */     query.setNasipLike(true);
/* 173 */     query.setSourceipLike(true);
/* 174 */     query.setStateLike(true);
/* 175 */     query.setUseripLike(true);
/* 176 */     query.orderbyId(false);
/* 177 */     if (stringUtils.isBlank(query.getEx3())) {
/* 178 */       query.setEx3(null);
/*     */     }
/* 180 */     if (stringUtils.isBlank(query.getAcctsessionid())) {
/* 181 */       query.setAcctsessionid(null);
/*     */     }
/* 183 */     if (stringUtils.isBlank(query.getCallingstationid())) {
/* 184 */       query.setCallingstationid(null);
/*     */     }
/* 186 */     if (stringUtils.isBlank(query.getName())) {
/* 187 */       query.setName(null);
/*     */     }
/* 189 */     if (stringUtils.isBlank(query.getNasip())) {
/* 190 */       query.setNasip(null);
/*     */     }
/* 192 */     if (stringUtils.isBlank(query.getSourceip())) {
/* 193 */       query.setSourceip(null);
/*     */     }
/* 195 */     if (stringUtils.isBlank(query.getState())) {
/* 196 */       query.setState(null);
/*     */     }
/* 198 */     if (stringUtils.isBlank(query.getUserip())) {
/* 199 */       query.setUserip(null);
/*     */     }
/* 201 */     if (stringUtils.isBlank(query_begin_time)) {
/* 202 */       query_begin_time = null;
/*     */     }
/* 204 */     if (stringUtils.isBlank(query_end_time)) {
/* 205 */       query_end_time = null;
/*     */     }
/* 207 */     Date begin_time = null;
/* 208 */     Date end_time = null;
/*     */     try {
/* 210 */       if (stringUtils.isNotBlank(query_begin_time)) {
/* 211 */         begin_time = format.parse(query_begin_time + " 00:00:00");
/* 212 */         query.setBegin_time(begin_time);
/*     */       }
/*     */     } catch (Exception e) {
/* 215 */       model.addAttribute("msg", "开始日期格式错误！");
/* 216 */       query_begin_time = null;
/*     */     }
/*     */     try {
/* 219 */       if (stringUtils.isNotBlank(query_end_time)) {
/* 220 */         end_time = format.parse(query_end_time + " 23:59:59");
/* 221 */         query.setEnd_time(end_time);
/*     */       }
/*     */     } catch (Exception e) {
/* 224 */       model.addAttribute("msg", "结束日期格式错误！");
/* 225 */       query_end_time = null;
/*     */     }
/*     */ 
/* 228 */     long onlineTime = 0L;
/* 229 */     long octets = 0L;
/* 230 */     List<Radiuslinkrecordall> linkAll = this.radiuslinkrecordallService
/* 231 */       .getRadiuslinkrecordallList(query);
/* 232 */     for (Radiuslinkrecordall link : linkAll) {
/* 233 */       onlineTime += link.getTime().longValue();
/* 234 */       octets = octets + link.getIns().longValue() + link.getOuts().longValue();
/*     */     }
/* 236 */     onlineTime /= 60000L;
/* 237 */     octets = octets / 1024L / 1024L;
/* 238 */     String onlineTimeS = df.format(onlineTime);
/* 239 */     String octetsS = df.format(octets);
/* 240 */     if (onlineTimeS.startsWith(".")) {
/* 241 */       onlineTimeS = "0" + onlineTimeS;
/*     */     }
/* 243 */     if (octetsS.startsWith(".")) {
/* 244 */       octetsS = "0" + octetsS;
/*     */     }
/*     */ 
/* 247 */     String cfgPath = request.getServletContext().getRealPath("/");
/* 248 */     Date now = new Date();
/* 249 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 250 */     String nowString = format.format(now);
/* 251 */     File dir = new File(cfgPath + "ExcelOut/");
/* 252 */     if (!dir.exists()) {
/* 253 */       dir.mkdirs();
/*     */     }
/* 255 */     String fileName = cfgPath + "ExcelOut/RadiusLinks_" + nowString + ".xls";
/*     */     try {
/* 257 */       ExcelUtils.writeRadiusLinksToExcel(fileName, linkAll, onlineTimeS, octetsS);
/*     */     } catch (Exception e) {
/* 259 */       model.addAttribute("msg", "文件创建失败！");
/* 260 */       model.addAttribute("downUrl", null);
/* 261 */       model.addAttribute("err", Integer.valueOf(1));
/* 262 */       return "radiuslinkrecordall/outResult";
/*     */     }
/* 264 */     model.addAttribute("msg", "文件创建成功！");
/* 265 */     model.addAttribute("downUrl", "RadiusLinks_" + nowString + ".xls");
/* 266 */     model.addAttribute("creatDate", nowString);
/* 267 */     model.addAttribute("err", Integer.valueOf(0));
/* 268 */     return "radiuslinkrecordall/outResult";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiuslinkrecordallController
 * JD-Core Version:    0.6.2
 */