/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalcardcategoryQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Long time;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private Double money;
/*     */   private Long speed;
/*     */   private Integer maclimit;
/*     */   private Integer maclimitcount;
/*     */   private Integer autologin;
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
/* 272 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalcardcategoryQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalcardcategoryQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalcardcategoryQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/*  50 */     return this.time;
/*     */   }
/*     */   public PortalcardcategoryQuery setTime(Long time) {
/*  53 */     this.time = time;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  58 */     return this.state;
/*     */   }
/*     */   public PortalcardcategoryQuery setState(String state) {
/*  61 */     this.state = state;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setStateLike(boolean isLike) {
/*  66 */     this.stateLike = isLike;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public Double getMoney() {
/*  71 */     return this.money;
/*     */   }
/*     */   public PortalcardcategoryQuery setMoney(Double money) {
/*  74 */     this.money = money;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSpeed() {
/*  79 */     return this.speed;
/*     */   }
/*     */   public PortalcardcategoryQuery setSpeed(Long speed) {
/*  82 */     this.speed = speed;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimit() {
/*  87 */     return this.maclimit;
/*     */   }
/*     */   public PortalcardcategoryQuery setMaclimit(Integer maclimit) {
/*  90 */     this.maclimit = maclimit;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimitcount() {
/*  95 */     return this.maclimitcount;
/*     */   }
/*     */   public PortalcardcategoryQuery setMaclimitcount(Integer maclimitcount) {
/*  98 */     this.maclimitcount = maclimitcount;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAutologin() {
/* 103 */     return this.autologin;
/*     */   }
/*     */   public PortalcardcategoryQuery setAutologin(Integer autologin) {
/* 106 */     this.autologin = autologin;
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/* 111 */     return this.ex1;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx1(String ex1) {
/* 114 */     this.ex1 = ex1;
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx1Like(boolean isLike) {
/* 119 */     this.ex1Like = isLike;
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 124 */     return this.ex2;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx2(String ex2) {
/* 127 */     this.ex2 = ex2;
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx2Like(boolean isLike) {
/* 132 */     this.ex2Like = isLike;
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 137 */     return this.ex3;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx3(String ex3) {
/* 140 */     this.ex3 = ex3;
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx3Like(boolean isLike) {
/* 145 */     this.ex3Like = isLike;
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 150 */     return this.ex4;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx4(String ex4) {
/* 153 */     this.ex4 = ex4;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx4Like(boolean isLike) {
/* 158 */     this.ex4Like = isLike;
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 163 */     return this.ex5;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx5(String ex5) {
/* 166 */     this.ex5 = ex5;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx5Like(boolean isLike) {
/* 171 */     this.ex5Like = isLike;
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 176 */     return this.ex6;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx6(String ex6) {
/* 179 */     this.ex6 = ex6;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx6Like(boolean isLike) {
/* 184 */     this.ex6Like = isLike;
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 189 */     return this.ex7;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx7(String ex7) {
/* 192 */     this.ex7 = ex7;
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx7Like(boolean isLike) {
/* 197 */     this.ex7Like = isLike;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 202 */     return this.ex8;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx8(String ex8) {
/* 205 */     this.ex8 = ex8;
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx8Like(boolean isLike) {
/* 210 */     this.ex8Like = isLike;
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 215 */     return this.ex9;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx9(String ex9) {
/* 218 */     this.ex9 = ex9;
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx9Like(boolean isLike) {
/* 223 */     this.ex9Like = isLike;
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 228 */     return this.ex10;
/*     */   }
/*     */   public PortalcardcategoryQuery setEx10(String ex10) {
/* 231 */     this.ex10 = ex10;
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery setEx10Like(boolean isLike) {
/* 236 */     this.ex10Like = isLike;
/* 237 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyId(boolean isAsc)
/*     */   {
/* 280 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 281 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyName(boolean isAsc)
/*     */   {
/* 290 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 291 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 300 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyTime(boolean isAsc)
/*     */   {
/* 310 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/* 311 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyState(boolean isAsc)
/*     */   {
/* 320 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 321 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyMoney(boolean isAsc)
/*     */   {
/* 330 */     this.orderFields.add(new OrderField("money", isAsc ? "ASC" : "DESC"));
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbySpeed(boolean isAsc)
/*     */   {
/* 340 */     this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyMaclimit(boolean isAsc)
/*     */   {
/* 350 */     this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
/* 351 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyMaclimitcount(boolean isAsc)
/*     */   {
/* 360 */     this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
/* 361 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyAutologin(boolean isAsc)
/*     */   {
/* 370 */     this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 380 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 390 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 400 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 410 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 420 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 421 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 430 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 431 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 440 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 450 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 460 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 461 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardcategoryQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 470 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 471 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 480 */     if (fieldMap == null) {
/* 481 */       fieldMap = new HashMap();
/* 482 */       fieldMap.put("id", "id");
/* 483 */       fieldMap.put("name", "name");
/* 484 */       fieldMap.put("description", "description");
/* 485 */       fieldMap.put("time", "time");
/* 486 */       fieldMap.put("state", "state");
/* 487 */       fieldMap.put("money", "money");
/* 488 */       fieldMap.put("speed", "speed");
/* 489 */       fieldMap.put("maclimit", "maclimit");
/* 490 */       fieldMap.put("maclimitcount", "maclimitcount");
/* 491 */       fieldMap.put("autologin", "autologin");
/* 492 */       fieldMap.put("ex1", "ex1");
/* 493 */       fieldMap.put("ex2", "ex2");
/* 494 */       fieldMap.put("ex3", "ex3");
/* 495 */       fieldMap.put("ex4", "ex4");
/* 496 */       fieldMap.put("ex5", "ex5");
/* 497 */       fieldMap.put("ex6", "ex6");
/* 498 */       fieldMap.put("ex7", "ex7");
/* 499 */       fieldMap.put("ex8", "ex8");
/* 500 */       fieldMap.put("ex9", "ex9");
/* 501 */       fieldMap.put("ex10", "ex10");
/*     */     }
/* 503 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 507 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 510 */     if (fields == null)
/* 511 */       return;
/* 512 */     String[] array = fields.split(",");
/* 513 */     StringBuffer buffer = new StringBuffer();
/* 514 */     for (String field : array) {
/* 515 */       if (getFieldSet().containsKey(field)) {
/* 516 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 517 */           .append(field).append(" ,");
/*     */       }
/* 519 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 520 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 521 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 524 */     if (buffer.length() != 0)
/* 525 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 527 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 246 */       this.fieldName = fieldName;
/* 247 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 253 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 256 */       this.fieldName = fieldName;
/* 257 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 260 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 263 */       this.order = order;
/* 264 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalcardcategoryQuery
 * JD-Core Version:    0.6.2
 */