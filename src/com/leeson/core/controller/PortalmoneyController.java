/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalcard;
/*     */ import com.leeson.core.bean.Portalorder;
/*     */ import com.leeson.core.query.PortalcardQuery;
/*     */ import com.leeson.core.query.PortalorderQuery;
/*     */ import com.leeson.core.service.PortalcardService;
/*     */ import com.leeson.core.service.PortalcardcategoryService;
/*     */ import com.leeson.core.service.PortalorderService;
/*     */ import com.leeson.core.utils.ExcelUtils;
/*     */ import java.io.File;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class PortalmoneyController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardService portalcardService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardcategoryService portalcardcategoryService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalorderService portalorderService;
/*  45 */   private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  46 */   private static DecimalFormat df = new DecimalFormat(".##");
/*     */ 
/*     */   @RequestMapping({"/portalmoney/order.action"})
/*     */   public String order(PortalorderQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  52 */     query.setNameLike(true);
/*  53 */     query.setAccountNameLike(true);
/*  54 */     if (stringUtils.isBlank(query.getName())) {
/*  55 */       query.setName(null);
/*     */     }
/*  57 */     if (stringUtils.isBlank(query.getAccountName())) {
/*  58 */       query.setAccountName(null);
/*     */     }
/*  60 */     if (stringUtils.isBlank(query.getCategoryType())) {
/*  61 */       query.setCategoryType(null);
/*     */     }
/*  63 */     if (stringUtils.isBlank(query.getPayType())) {
/*  64 */       query.setPayType(null);
/*     */     }
/*  66 */     if (stringUtils.isBlank(query.getState())) {
/*  67 */       query.setState(null);
/*     */     }
/*  69 */     if (stringUtils.isBlank(query.getBuyer())) {
/*  70 */       query.setBuyer(null);
/*     */     }
/*  72 */     if (stringUtils.isBlank(query.getSeller())) {
/*  73 */       query.setSeller(null);
/*     */     }
/*     */ 
/*  76 */     query.setState("1");
/*  77 */     query.setStateLike(false);
/*  78 */     query.orderbyBuyDate(true);
/*  79 */     query.orderbyPayDate(true);
/*     */ 
/*  82 */     Double money = Double.valueOf(0.0D);
/*  83 */     String begintimeQS = null;
/*  84 */     String endtimeQS = null;
/*  85 */     Date begin_time = null;
/*  86 */     Date end_time = null;
/*     */ 
/*  88 */     if (stringUtils.isNotBlank(begintime)) {
/*  89 */       begintimeQS = begintime + " 00:00:00";
/*     */       try {
/*  91 */         begin_time = format.parse(begintimeQS);
/*  92 */         query.setBegin_time1(begin_time);
/*     */       } catch (ParseException e) {
/*  94 */         model.addAttribute("msg", "开始日期格式错误！");
/*  95 */         begintime = null;
/*     */       }
/*     */     }
/*  98 */     if (stringUtils.isNotBlank(endtime)) {
/*  99 */       endtimeQS = endtime + " 23:59:59";
/*     */       try {
/* 101 */         end_time = format.parse(endtimeQS);
/* 102 */         query.setEnd_time1(end_time);
/*     */       } catch (ParseException e) {
/* 104 */         model.addAttribute("msg", "结束日期格式错误！");
/* 105 */         endtime = null;
/*     */       }
/*     */     }
/* 108 */     Pagination pagination = this.portalorderService
/* 109 */       .getPortalorderListWithPage(query);
/* 110 */     List<Portalorder> orders = this.portalorderService.getPortalorderList(query);
/* 111 */     for (Portalorder order : orders) {
/* 112 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*     */     }
/* 114 */     String moneyS = df.format(money);
/* 115 */     if (moneyS.startsWith(".")) {
/* 116 */       moneyS = "0" + moneyS;
/*     */     }
/* 118 */     model.addAttribute("pagination", pagination);
/* 119 */     model.addAttribute("query", query);
/* 120 */     model.addAttribute("begintime", begintime);
/* 121 */     model.addAttribute("endtime", endtime);
/* 122 */     model.addAttribute("money", moneyS);
/* 123 */     return "portalmoney/order";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmoney/card.action"})
/*     */   public String card(PortalcardQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 130 */     query.setNameLike(true);
/* 131 */     query.setAccountNameLike(true);
/* 132 */     if (stringUtils.isBlank(query.getName())) {
/* 133 */       query.setName(null);
/*     */     }
/* 135 */     if (stringUtils.isBlank(query.getAccountName())) {
/* 136 */       query.setAccountName(null);
/*     */     }
/* 138 */     if (stringUtils.isBlank(query.getCategoryType())) {
/* 139 */       query.setCategoryType(null);
/*     */     }
/* 141 */     if (stringUtils.isBlank(query.getPayType())) {
/* 142 */       query.setPayType(null);
/*     */     }
/* 144 */     if (stringUtils.isBlank(query.getState())) {
/* 145 */       query.setState(null);
/*     */     }
/* 147 */     query.setState("0");
/* 148 */     query.orderbyBuyDate(true);
/* 149 */     query.orderbyPayDate(true);
/*     */ 
/* 152 */     Double money = Double.valueOf(0.0D);
/* 153 */     String begintimeQS = null;
/* 154 */     String endtimeQS = null;
/* 155 */     Date begin_time = null;
/* 156 */     Date end_time = null;
/*     */ 
/* 158 */     if (stringUtils.isNotBlank(begintime)) {
/* 159 */       begintimeQS = begintime + " 00:00:00";
/*     */       try {
/* 161 */         begin_time = format.parse(begintimeQS);
/* 162 */         query.setBegin_time1(begin_time);
/*     */       } catch (ParseException e) {
/* 164 */         model.addAttribute("msg", "开始日期格式错误！");
/* 165 */         begintime = null;
/*     */       }
/*     */     }
/* 168 */     if (stringUtils.isNotBlank(endtime)) {
/* 169 */       endtimeQS = endtime + " 23:59:59";
/*     */       try {
/* 171 */         end_time = format.parse(endtimeQS);
/* 172 */         query.setEnd_time1(end_time);
/*     */       } catch (ParseException e) {
/* 174 */         model.addAttribute("msg", "结束日期格式错误！");
/* 175 */         endtime = null;
/*     */       }
/*     */     }
/* 178 */     Pagination pagination = this.portalcardService
/* 179 */       .getPortalcardSaleListWithPage(query);
/* 180 */     List<Portalcard> cards = this.portalcardService.getPortalcardSaleList(query);
/* 181 */     for (Portalcard card : cards) {
/* 182 */       money = Double.valueOf(money.doubleValue() + card.getMoney().doubleValue());
/*     */     }
/* 184 */     String moneyS = df.format(money);
/* 185 */     if (moneyS.startsWith(".")) {
/* 186 */       moneyS = "0" + moneyS;
/*     */     }
/* 188 */     model.addAttribute("pagination", pagination);
/* 189 */     model.addAttribute("query", query);
/* 190 */     model.addAttribute("begintime", begintime);
/* 191 */     model.addAttribute("endtime", endtime);
/* 192 */     model.addAttribute("money", moneyS);
/* 193 */     return "portalmoney/card";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmoney/outCard.action"})
/*     */   public String outCard(PortalcardQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 200 */     query.setNameLike(true);
/* 201 */     query.setAccountNameLike(true);
/* 202 */     if (stringUtils.isBlank(query.getName())) {
/* 203 */       query.setName(null);
/*     */     }
/* 205 */     if (stringUtils.isBlank(query.getAccountName())) {
/* 206 */       query.setAccountName(null);
/*     */     }
/* 208 */     if (stringUtils.isBlank(query.getCategoryType())) {
/* 209 */       query.setCategoryType(null);
/*     */     }
/* 211 */     if (stringUtils.isBlank(query.getPayType())) {
/* 212 */       query.setPayType(null);
/*     */     }
/* 214 */     if (stringUtils.isBlank(query.getState())) {
/* 215 */       query.setState(null);
/*     */     }
/* 217 */     query.setState("0");
/* 218 */     query.orderbyBuyDate(true);
/* 219 */     query.orderbyPayDate(true);
/*     */ 
/* 222 */     Double money = Double.valueOf(0.0D);
/* 223 */     String begintimeQS = null;
/* 224 */     String endtimeQS = null;
/* 225 */     Date begin_time = null;
/* 226 */     Date end_time = null;
/*     */ 
/* 228 */     if (stringUtils.isNotBlank(begintime)) {
/* 229 */       begintimeQS = begintime + " 00:00:00";
/*     */       try {
/* 231 */         begin_time = format.parse(begintimeQS);
/* 232 */         query.setBegin_time1(begin_time);
/*     */       } catch (ParseException e) {
/* 234 */         model.addAttribute("msg", "开始日期格式错误！");
/* 235 */         begintime = null;
/*     */       }
/*     */     }
/* 238 */     if (stringUtils.isNotBlank(endtime)) {
/* 239 */       endtimeQS = endtime + " 23:59:59";
/*     */       try {
/* 241 */         end_time = format.parse(endtimeQS);
/* 242 */         query.setEnd_time1(end_time);
/*     */       } catch (ParseException e) {
/* 244 */         model.addAttribute("msg", "结束日期格式错误！");
/* 245 */         endtime = null;
/*     */       }
/*     */     }
/* 248 */     List<Portalcard> cards = this.portalcardService.getPortalcardSaleList(query);
/* 249 */     for (Portalcard card : cards) {
/* 250 */       money = Double.valueOf(money.doubleValue() + card.getMoney().doubleValue());
/*     */     }
/* 252 */     String moneyS = df.format(money);
/* 253 */     if (moneyS.startsWith(".")) {
/* 254 */       moneyS = "0" + moneyS;
/*     */     }
/*     */ 
/* 257 */     String cfgPath = request.getServletContext().getRealPath("/");
/* 258 */     Date now = new Date();
/* 259 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 260 */     String nowString = format.format(now);
/* 261 */     File dir = new File(cfgPath + "ExcelOut/");
/* 262 */     if (!dir.exists()) {
/* 263 */       dir.mkdirs();
/*     */     }
/* 265 */     String fileName = cfgPath + "ExcelOut/Cards_" + nowString + ".xls";
/*     */     try {
/* 267 */       ExcelUtils.writeCardsToExcel(fileName, cards, moneyS);
/*     */     }
/*     */     catch (Exception e) {
/* 270 */       model.addAttribute("msg", "文件创建失败！");
/* 271 */       model.addAttribute("downUrl", null);
/* 272 */       model.addAttribute("err", Integer.valueOf(1));
/* 273 */       return "portalmoney/outResult";
/*     */     }
/* 275 */     model.addAttribute("msg", "文件创建成功！");
/* 276 */     model.addAttribute("downUrl", "Cards_" + nowString + ".xls");
/* 277 */     model.addAttribute("creatDate", nowString);
/* 278 */     model.addAttribute("err", Integer.valueOf(0));
/* 279 */     return "portalmoney/outResult";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalmoney/outOrder.action"})
/*     */   public String outOrder(PortalorderQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 285 */     query.setNameLike(true);
/* 286 */     query.setAccountNameLike(true);
/* 287 */     if (stringUtils.isBlank(query.getName())) {
/* 288 */       query.setName(null);
/*     */     }
/* 290 */     if (stringUtils.isBlank(query.getAccountName())) {
/* 291 */       query.setAccountName(null);
/*     */     }
/* 293 */     if (stringUtils.isBlank(query.getCategoryType())) {
/* 294 */       query.setCategoryType(null);
/*     */     }
/* 296 */     if (stringUtils.isBlank(query.getPayType())) {
/* 297 */       query.setPayType(null);
/*     */     }
/* 299 */     if (stringUtils.isBlank(query.getState())) {
/* 300 */       query.setState(null);
/*     */     }
/* 302 */     if (stringUtils.isBlank(query.getBuyer())) {
/* 303 */       query.setBuyer(null);
/*     */     }
/* 305 */     if (stringUtils.isBlank(query.getSeller())) {
/* 306 */       query.setSeller(null);
/*     */     }
/*     */ 
/* 309 */     query.setState("1");
/* 310 */     query.setStateLike(false);
/* 311 */     query.orderbyBuyDate(true);
/* 312 */     query.orderbyPayDate(true);
/*     */ 
/* 315 */     Double money = Double.valueOf(0.0D);
/* 316 */     String begintimeQS = null;
/* 317 */     String endtimeQS = null;
/* 318 */     Date begin_time = null;
/* 319 */     Date end_time = null;
/*     */ 
/* 321 */     if (stringUtils.isNotBlank(begintime)) {
/* 322 */       begintimeQS = begintime + " 00:00:00";
/*     */       try {
/* 324 */         begin_time = format.parse(begintimeQS);
/* 325 */         query.setBegin_time1(begin_time);
/*     */       } catch (ParseException e) {
/* 327 */         model.addAttribute("msg", "开始日期格式错误！");
/* 328 */         begintime = null;
/*     */       }
/*     */     }
/* 331 */     if (stringUtils.isNotBlank(endtime)) {
/* 332 */       endtimeQS = endtime + " 23:59:59";
/*     */       try {
/* 334 */         end_time = format.parse(endtimeQS);
/* 335 */         query.setEnd_time1(end_time);
/*     */       } catch (ParseException e) {
/* 337 */         model.addAttribute("msg", "结束日期格式错误！");
/* 338 */         endtime = null;
/*     */       }
/*     */     }
/* 341 */     List<Portalorder> orders = this.portalorderService.getPortalorderList(query);
/* 342 */     for (Portalorder order : orders) {
/* 343 */       money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
/*     */     }
/* 345 */     String moneyS = df.format(money);
/* 346 */     if (moneyS.startsWith(".")) {
/* 347 */       moneyS = "0" + moneyS;
/*     */     }
/*     */ 
/* 350 */     String cfgPath = request.getServletContext().getRealPath("/");
/* 351 */     Date now = new Date();
/* 352 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 353 */     String nowString = format.format(now);
/* 354 */     File dir = new File(cfgPath + "ExcelOut/");
/* 355 */     if (!dir.exists()) {
/* 356 */       dir.mkdirs();
/*     */     }
/* 358 */     String fileName = cfgPath + "ExcelOut/Orders_" + nowString + ".xls";
/*     */     try {
/* 360 */       ExcelUtils.writeOrdersToExcel(fileName, orders, moneyS);
/*     */     }
/*     */     catch (Exception e) {
/* 363 */       model.addAttribute("msg", "文件创建失败！");
/* 364 */       model.addAttribute("downUrl", null);
/* 365 */       model.addAttribute("err", Integer.valueOf(1));
/* 366 */       return "portalmoney/outResult";
/*     */     }
/* 368 */     model.addAttribute("msg", "文件创建成功！");
/* 369 */     model.addAttribute("downUrl", "Orders_" + nowString + ".xls");
/* 370 */     model.addAttribute("creatDate", nowString);
/* 371 */     model.addAttribute("err", Integer.valueOf(0));
/* 372 */     return "portalmoney/outResult";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalmoneyController
 * JD-Core Version:    0.6.2
 */