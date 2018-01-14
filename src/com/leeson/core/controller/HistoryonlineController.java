/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Historyonline;
/*     */ import com.leeson.core.query.HistoryonlineQuery;
/*     */ import com.leeson.core.service.HistoryonlineService;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
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
/*     */ public class HistoryonlineController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private HistoryonlineService historyonlineService;
/*     */ 
/*     */   @RequestMapping({"/historyonline/list.action"})
/*     */   public String page(HistoryonlineQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  41 */     Pagination pagination = this.historyonlineService
/*  42 */       .getHistoryonlineListWithPage(query);
/*  43 */     model.addAttribute("pagination", pagination);
/*  44 */     model.addAttribute("query", query);
/*  45 */     return "historyonline/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/*  51 */     this.historyonlineService.deleteByKey(id);
/*  52 */     return "redirect:/historyonline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/historyonline/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/*  59 */     List list = Arrays.asList(ids);
/*  60 */     this.historyonlineService.deleteByKeys(list);
/*     */ 
/*  62 */     return "redirect:/historyonline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/historyonline/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String deletes()
/*     */   {
/*  68 */     this.historyonlineService.deleteAll();
/*  69 */     return "redirect:/historyonline/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/year.action"})
/*     */   public String year(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  94 */     List list = new ArrayList();
/*  95 */     Date now = new Date();
/*  96 */     int year = now.getYear() + 1900;
/*  97 */     int yearO = year - 9;
/*  98 */     while (yearO <= year) {
/*  99 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 100 */       q.setRecYear(Integer.valueOf(yearO));
/* 101 */       List<Historyonline> hs = this.historyonlineService
/* 102 */         .getHistoryonlineList(q);
/* 103 */       long counts = 0L;
/* 104 */       if (hs.size() != 0) {
/* 105 */         for (Historyonline h : hs) {
/* 106 */           counts += h.getCounts().longValue();
/*     */         }
/* 108 */         counts /= hs.size();
/*     */       }
/* 110 */       OnlineCounts e = new OnlineCounts();
/* 111 */       e.setId(Integer.valueOf(yearO));
/* 112 */       e.setCount(Long.valueOf(counts));
/* 113 */       list.add(e);
/* 114 */       yearO++;
/*     */     }
/* 116 */     model.addAttribute("e", list);
/* 117 */     model.addAttribute("tag", "year");
/* 118 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/month.action"})
/*     */   public String month(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 124 */     List list = new ArrayList();
/* 125 */     int monthO = 1;
/* 126 */     int month = 12;
/* 127 */     while (monthO <= month) {
/* 128 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 129 */       q.setRecMonth(Integer.valueOf(monthO));
/* 130 */       List<Historyonline> hs = this.historyonlineService
/* 131 */         .getHistoryonlineList(q);
/* 132 */       long counts = 0L;
/* 133 */       if (hs.size() != 0) {
/* 134 */         for (Historyonline h : hs) {
/* 135 */           counts += h.getCounts().longValue();
/*     */         }
/* 137 */         counts /= hs.size();
/*     */       }
/* 139 */       OnlineCounts e = new OnlineCounts();
/* 140 */       e.setId(Integer.valueOf(monthO));
/* 141 */       e.setCount(Long.valueOf(counts));
/* 142 */       list.add(e);
/* 143 */       monthO++;
/*     */     }
/* 145 */     model.addAttribute("e", list);
/* 146 */     model.addAttribute("tag", "month");
/* 147 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/monthO.action"})
/*     */   public String monthO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 153 */     List list = new ArrayList();
/* 154 */     int dayO = 0;
/* 155 */     int day = 12;
/* 156 */     while (dayO <= day) {
/* 157 */       Calendar cal = Calendar.getInstance();
/* 158 */       cal.add(2, -dayO);
/* 159 */       String old = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
/* 160 */       System.out.println(old);
/* 161 */       Date thisDate = cal.getTime();
/*     */ 
/* 163 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 164 */       q.setRecMonth(Integer.valueOf(thisDate.getMonth() + 1));
/* 165 */       q.setRecYear(Integer.valueOf(thisDate.getYear() + 1900));
/* 166 */       List<Historyonline> hs = this.historyonlineService
/* 167 */         .getHistoryonlineList(q);
/* 168 */       long counts = 0L;
/* 169 */       if (hs.size() != 0) {
/* 170 */         for (Historyonline h : hs) {
/* 171 */           counts += h.getCounts().longValue();
/*     */         }
/* 173 */         counts /= hs.size();
/*     */       }
/* 175 */       OnlineCounts e = new OnlineCounts();
/* 176 */       e.setId(Integer.valueOf(thisDate.getMonth() + 1));
/* 177 */       e.setCount(Long.valueOf(counts));
/* 178 */       list.add(e);
/* 179 */       dayO++;
/*     */     }
/*     */ 
/* 182 */     int a = list.size();
/* 183 */     List listOK = new ArrayList();
/* 184 */     for (int i = a - 1; i >= 0; i--) {
/* 185 */       listOK.add((OnlineCounts)list.get(i));
/*     */     }
/* 187 */     model.addAttribute("e", listOK);
/* 188 */     model.addAttribute("tag", "monthO");
/* 189 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/week.action"})
/*     */   public String week(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 195 */     List list = new ArrayList();
/* 196 */     int weekO = 1;
/* 197 */     int week = 7;
/* 198 */     while (weekO <= week) {
/* 199 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 200 */       q.setRecWeek(Integer.valueOf(weekO));
/* 201 */       List<Historyonline> hs = this.historyonlineService
/* 202 */         .getHistoryonlineList(q);
/* 203 */       long counts = 0L;
/* 204 */       if (hs.size() != 0) {
/* 205 */         for (Historyonline h : hs) {
/* 206 */           counts += h.getCounts().longValue();
/*     */         }
/* 208 */         counts /= hs.size();
/*     */       }
/* 210 */       OnlineCounts e = new OnlineCounts();
/* 211 */       e.setId(Integer.valueOf(weekO));
/* 212 */       e.setCount(Long.valueOf(counts));
/* 213 */       list.add(e);
/* 214 */       weekO++;
/*     */     }
/* 216 */     model.addAttribute("e", list);
/* 217 */     model.addAttribute("tag", "week");
/* 218 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/weekO.action"})
/*     */   public String weekO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 224 */     List list = new ArrayList();
/* 225 */     int dayO = 0;
/* 226 */     int day = 7;
/* 227 */     while (dayO <= day) {
/* 228 */       Calendar cal = Calendar.getInstance();
/* 229 */       cal.add(5, -dayO);
/* 230 */       String old = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
/* 231 */       System.out.println(old);
/* 232 */       Date thisDate = cal.getTime();
/*     */ 
/* 234 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 235 */       q.setRecDay(Integer.valueOf(thisDate.getDate()));
/* 236 */       q.setRecMonth(Integer.valueOf(thisDate.getMonth() + 1));
/* 237 */       q.setRecYear(Integer.valueOf(thisDate.getYear() + 1900));
/* 238 */       List<Historyonline> hs = this.historyonlineService
/* 239 */         .getHistoryonlineList(q);
/* 240 */       long counts = 0L;
/* 241 */       if (hs.size() != 0) {
/* 242 */         for (Historyonline h : hs) {
/* 243 */           counts += h.getCounts().longValue();
/*     */         }
/* 245 */         counts /= hs.size();
/*     */       }
/* 247 */       OnlineCounts e = new OnlineCounts();
/* 248 */       int week = thisDate.getDay();
/* 249 */       if (week == 0) {
/* 250 */         week = 7;
/*     */       }
/* 252 */       e.setId(Integer.valueOf(week));
/* 253 */       e.setCount(Long.valueOf(counts));
/* 254 */       list.add(e);
/* 255 */       dayO++;
/*     */     }
/*     */ 
/* 258 */     int a = list.size();
/* 259 */     List listOK = new ArrayList();
/* 260 */     for (int i = a - 1; i >= 0; i--) {
/* 261 */       listOK.add((OnlineCounts)list.get(i));
/*     */     }
/* 263 */     model.addAttribute("e", listOK);
/* 264 */     model.addAttribute("tag", "weekO");
/* 265 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/day.action"})
/*     */   public String day(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 271 */     List list = new ArrayList();
/* 272 */     int dayO = 1;
/* 273 */     int day = 31;
/* 274 */     while (dayO <= day) {
/* 275 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 276 */       q.setRecDay(Integer.valueOf(dayO));
/* 277 */       List<Historyonline> hs = this.historyonlineService
/* 278 */         .getHistoryonlineList(q);
/* 279 */       long counts = 0L;
/* 280 */       if (hs.size() != 0) {
/* 281 */         for (Historyonline h : hs) {
/* 282 */           counts += h.getCounts().longValue();
/*     */         }
/* 284 */         counts /= hs.size();
/*     */       }
/* 286 */       OnlineCounts e = new OnlineCounts();
/* 287 */       e.setId(Integer.valueOf(dayO));
/* 288 */       e.setCount(Long.valueOf(counts));
/* 289 */       list.add(e);
/* 290 */       dayO++;
/*     */     }
/* 292 */     model.addAttribute("e", list);
/* 293 */     model.addAttribute("tag", "day");
/* 294 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/dayO.action"})
/*     */   public String dayO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 300 */     List list = new ArrayList();
/* 301 */     int dayO = 0;
/* 302 */     int day = 31;
/* 303 */     while (dayO <= day) {
/* 304 */       Calendar cal = Calendar.getInstance();
/* 305 */       cal.add(5, -dayO);
/* 306 */       String old = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
/* 307 */       System.out.println(old);
/* 308 */       Date thisDate = cal.getTime();
/*     */ 
/* 310 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 311 */       q.setRecDay(Integer.valueOf(thisDate.getDate()));
/* 312 */       q.setRecMonth(Integer.valueOf(thisDate.getMonth() + 1));
/* 313 */       q.setRecYear(Integer.valueOf(thisDate.getYear() + 1900));
/* 314 */       List<Historyonline> hs = this.historyonlineService
/* 315 */         .getHistoryonlineList(q);
/* 316 */       long counts = 0L;
/* 317 */       if (hs.size() != 0) {
/* 318 */         for (Historyonline h : hs) {
/* 319 */           counts += h.getCounts().longValue();
/*     */         }
/* 321 */         counts /= hs.size();
/*     */       }
/* 323 */       OnlineCounts e = new OnlineCounts();
/* 324 */       e.setId(Integer.valueOf(thisDate.getDate()));
/* 325 */       e.setCount(Long.valueOf(counts));
/* 326 */       list.add(e);
/* 327 */       dayO++;
/*     */     }
/*     */ 
/* 330 */     int a = list.size();
/* 331 */     List listOK = new ArrayList();
/* 332 */     for (int i = a - 1; i >= 0; i--) {
/* 333 */       listOK.add((OnlineCounts)list.get(i));
/*     */     }
/* 335 */     model.addAttribute("e", listOK);
/* 336 */     model.addAttribute("tag", "dayO");
/* 337 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/hour.action"})
/*     */   public String hour(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 343 */     List list = new ArrayList();
/* 344 */     int hourO = 0;
/* 345 */     int hour = 23;
/* 346 */     while (hourO <= hour) {
/* 347 */       HistoryonlineQuery q = new HistoryonlineQuery();
/* 348 */       q.setRecTime(Integer.valueOf(hourO));
/* 349 */       List<Historyonline> hs = this.historyonlineService
/* 350 */         .getHistoryonlineList(q);
/* 351 */       long counts = 0L;
/* 352 */       if (hs.size() != 0) {
/* 353 */         for (Historyonline h : hs) {
/* 354 */           counts += h.getCounts().longValue();
/*     */         }
/* 356 */         counts /= hs.size();
/*     */       }
/* 358 */       OnlineCounts e = new OnlineCounts();
/* 359 */       e.setId(Integer.valueOf(hourO));
/* 360 */       e.setCount(Long.valueOf(counts));
/* 361 */       list.add(e);
/* 362 */       hourO++;
/*     */     }
/* 364 */     model.addAttribute("e", list);
/* 365 */     model.addAttribute("tag", "hour");
/* 366 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/historyonline/hourO.action"})
/*     */   public String hourO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 372 */     List list = new ArrayList();
/* 373 */     HistoryonlineQuery q = new HistoryonlineQuery();
/* 374 */     q.orderbyId(false);
/* 375 */     q.setPageSize(24);
/* 376 */     q.setPage(1);
/* 377 */     Pagination pagination = this.historyonlineService.getHistoryonlineListWithPage(q);
/* 378 */     List<Historyonline> hs = (List<Historyonline>) pagination.getList();
/* 379 */     if (hs.size() != 0) {
/* 380 */       for (Historyonline h : hs) {
/* 381 */         OnlineCounts e = new OnlineCounts();
/* 382 */         e.setId(h.getRecTime());
/* 383 */         e.setCount(h.getCounts());
/* 384 */         list.add(e);
/*     */       }
/*     */     } else {
/* 387 */       OnlineCounts e = new OnlineCounts();
/* 388 */       e.setId(Integer.valueOf(new Date().getHours()));
/* 389 */       e.setCount(Long.valueOf(0L));
/* 390 */       list.add(e);
/*     */     }
/*     */ 
/* 393 */     int a = list.size();
/* 394 */     Object listOK = new ArrayList();
/* 395 */     for (int i = a - 1; i >= 0; i--) {
/* 396 */       ((List)listOK).add((OnlineCounts)list.get(i));
/*     */     }
/* 398 */     model.addAttribute("e", listOK);
/* 399 */     model.addAttribute("tag", "hourO");
/* 400 */     return "homeAction/onlineLogs";
/*     */   }
/*     */ 
/*     */   public class OnlineCounts
/*     */   {
/*     */     Integer id;
/*     */     Long count;
/*     */ 
/*     */     public OnlineCounts()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Integer getId()
/*     */     {
/*  77 */       return this.id;
/*     */     }
/*     */     public void setId(Integer id) {
/*  80 */       this.id = id;
/*     */     }
/*     */     public Long getCount() {
/*  83 */       return this.count;
/*     */     }
/*     */     public void setCount(Long count) {
/*  86 */       this.count = count;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.HistoryonlineController
 * JD-Core Version:    0.6.2
 */