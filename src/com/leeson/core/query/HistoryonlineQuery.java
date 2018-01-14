/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HistoryonlineQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private Long counts;
/*     */   private Date recDate;
/*     */   private Integer recYear;
/*     */   private Integer recMonth;
/*     */   private Integer recDay;
/*     */   private Integer recWeek;
/*     */   private Integer recTime;
/*     */   private String ex1;
/*     */   private boolean ex1Like;
/*     */   private String ex2;
/*     */   private boolean ex2Like;
/*     */   private String ex3;
/*     */   private boolean ex3Like;
/*     */   private String ex4;
/*     */   private boolean ex4Like;
/*     */   private String ex5;
/*     */   private boolean ex5Like;
/*     */   private String ex6;
/*     */   private boolean ex6Like;
/*     */   private String ex7;
/*     */   private boolean ex7Like;
/*     */   private String ex8;
/*     */   private boolean ex8Like;
/*     */   private String ex9;
/*     */   private boolean ex9Like;
/*     */   private String ex10;
/*     */   private boolean ex10Like;
/* 241 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public HistoryonlineQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getCounts() {
/*  24 */     return this.counts;
/*     */   }
/*     */   public HistoryonlineQuery setCounts(Long counts) {
/*  27 */     this.counts = counts;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getRecDate() {
/*  32 */     return this.recDate;
/*     */   }
/*     */   public HistoryonlineQuery setRecDate(Date recDate) {
/*  35 */     this.recDate = recDate;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getRecYear() {
/*  40 */     return this.recYear;
/*     */   }
/*     */   public HistoryonlineQuery setRecYear(Integer recYear) {
/*  43 */     this.recYear = recYear;
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getRecMonth() {
/*  48 */     return this.recMonth;
/*     */   }
/*     */   public HistoryonlineQuery setRecMonth(Integer recMonth) {
/*  51 */     this.recMonth = recMonth;
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getRecDay() {
/*  56 */     return this.recDay;
/*     */   }
/*     */   public HistoryonlineQuery setRecDay(Integer recDay) {
/*  59 */     this.recDay = recDay;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getRecWeek() {
/*  64 */     return this.recWeek;
/*     */   }
/*     */   public HistoryonlineQuery setRecWeek(Integer recWeek) {
/*  67 */     this.recWeek = recWeek;
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getRecTime() {
/*  72 */     return this.recTime;
/*     */   }
/*     */   public HistoryonlineQuery setRecTime(Integer recTime) {
/*  75 */     this.recTime = recTime;
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/*  80 */     return this.ex1;
/*     */   }
/*     */   public HistoryonlineQuery setEx1(String ex1) {
/*  83 */     this.ex1 = ex1;
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx1Like(boolean isLike) {
/*  88 */     this.ex1Like = isLike;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/*  93 */     return this.ex2;
/*     */   }
/*     */   public HistoryonlineQuery setEx2(String ex2) {
/*  96 */     this.ex2 = ex2;
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx2Like(boolean isLike) {
/* 101 */     this.ex2Like = isLike;
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 106 */     return this.ex3;
/*     */   }
/*     */   public HistoryonlineQuery setEx3(String ex3) {
/* 109 */     this.ex3 = ex3;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx3Like(boolean isLike) {
/* 114 */     this.ex3Like = isLike;
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 119 */     return this.ex4;
/*     */   }
/*     */   public HistoryonlineQuery setEx4(String ex4) {
/* 122 */     this.ex4 = ex4;
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx4Like(boolean isLike) {
/* 127 */     this.ex4Like = isLike;
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 132 */     return this.ex5;
/*     */   }
/*     */   public HistoryonlineQuery setEx5(String ex5) {
/* 135 */     this.ex5 = ex5;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx5Like(boolean isLike) {
/* 140 */     this.ex5Like = isLike;
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 145 */     return this.ex6;
/*     */   }
/*     */   public HistoryonlineQuery setEx6(String ex6) {
/* 148 */     this.ex6 = ex6;
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx6Like(boolean isLike) {
/* 153 */     this.ex6Like = isLike;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 158 */     return this.ex7;
/*     */   }
/*     */   public HistoryonlineQuery setEx7(String ex7) {
/* 161 */     this.ex7 = ex7;
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx7Like(boolean isLike) {
/* 166 */     this.ex7Like = isLike;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 171 */     return this.ex8;
/*     */   }
/*     */   public HistoryonlineQuery setEx8(String ex8) {
/* 174 */     this.ex8 = ex8;
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx8Like(boolean isLike) {
/* 179 */     this.ex8Like = isLike;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 184 */     return this.ex9;
/*     */   }
/*     */   public HistoryonlineQuery setEx9(String ex9) {
/* 187 */     this.ex9 = ex9;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx9Like(boolean isLike) {
/* 192 */     this.ex9Like = isLike;
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 197 */     return this.ex10;
/*     */   }
/*     */   public HistoryonlineQuery setEx10(String ex10) {
/* 200 */     this.ex10 = ex10;
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery setEx10Like(boolean isLike) {
/* 205 */     this.ex10Like = isLike;
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyId(boolean isAsc)
/*     */   {
/* 249 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyCounts(boolean isAsc)
/*     */   {
/* 259 */     this.orderFields.add(new OrderField("counts", isAsc ? "ASC" : "DESC"));
/* 260 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyRecDate(boolean isAsc)
/*     */   {
/* 269 */     this.orderFields.add(new OrderField("recDate", isAsc ? "ASC" : "DESC"));
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyRecYear(boolean isAsc)
/*     */   {
/* 279 */     this.orderFields.add(new OrderField("recYear", isAsc ? "ASC" : "DESC"));
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyRecMonth(boolean isAsc)
/*     */   {
/* 289 */     this.orderFields.add(new OrderField("recMonth", isAsc ? "ASC" : "DESC"));
/* 290 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyRecDay(boolean isAsc)
/*     */   {
/* 299 */     this.orderFields.add(new OrderField("recDay", isAsc ? "ASC" : "DESC"));
/* 300 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyRecWeek(boolean isAsc)
/*     */   {
/* 309 */     this.orderFields.add(new OrderField("recWeek", isAsc ? "ASC" : "DESC"));
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyRecTime(boolean isAsc)
/*     */   {
/* 319 */     this.orderFields.add(new OrderField("recTime", isAsc ? "ASC" : "DESC"));
/* 320 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 329 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 339 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 340 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 349 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 350 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 359 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 360 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 369 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 370 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 379 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 380 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 389 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 390 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 399 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 400 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 409 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 410 */     return this;
/*     */   }
/*     */ 
/*     */   public HistoryonlineQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 419 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 420 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 429 */     if (fieldMap == null) {
/* 430 */       fieldMap = new HashMap();
/* 431 */       fieldMap.put("id", "id");
/* 432 */       fieldMap.put("counts", "counts");
/* 433 */       fieldMap.put("recDate", "recDate");
/* 434 */       fieldMap.put("recYear", "recYear");
/* 435 */       fieldMap.put("recMonth", "recMonth");
/* 436 */       fieldMap.put("recDay", "recDay");
/* 437 */       fieldMap.put("recWeek", "recWeek");
/* 438 */       fieldMap.put("recTime", "recTime");
/* 439 */       fieldMap.put("ex1", "ex1");
/* 440 */       fieldMap.put("ex2", "ex2");
/* 441 */       fieldMap.put("ex3", "ex3");
/* 442 */       fieldMap.put("ex4", "ex4");
/* 443 */       fieldMap.put("ex5", "ex5");
/* 444 */       fieldMap.put("ex6", "ex6");
/* 445 */       fieldMap.put("ex7", "ex7");
/* 446 */       fieldMap.put("ex8", "ex8");
/* 447 */       fieldMap.put("ex9", "ex9");
/* 448 */       fieldMap.put("ex10", "ex10");
/*     */     }
/* 450 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 454 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 457 */     if (fields == null)
/* 458 */       return;
/* 459 */     String[] array = fields.split(",");
/* 460 */     StringBuffer buffer = new StringBuffer();
/* 461 */     for (String field : array) {
/* 462 */       if (getFieldSet().containsKey(field)) {
/* 463 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 464 */           .append(field).append(" ,");
/*     */       }
/* 466 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 467 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 468 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 471 */     if (buffer.length() != 0)
/* 472 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 474 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 215 */       this.fieldName = fieldName;
/* 216 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 222 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 225 */       this.fieldName = fieldName;
/* 226 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 229 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 232 */       this.order = order;
/* 233 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.HistoryonlineQuery
 * JD-Core Version:    0.6.2
 */