/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalaccountgroupQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Date date;
/*     */   private Long time;
/*     */   private Long octets;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private Long speed;
/*     */   private Integer maclimit;
/*     */   private Integer maclimitcount;
/*     */   private Integer autologin;
/*     */   private Integer autoKick;
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
/*     */   private String isp;
/*     */   private boolean ispLike;
/*     */   private String radius;
/*     */   private boolean radiusLike;
/*     */   private Date creatDate;
/*     */   private Integer unlockMac;
/*     */   private Integer clearHaveAll;
/*     */   private Integer clearHaveLimit;
/*     */   private Long octetsLimit;
/*     */   private Long timeLimit;
/* 362 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalaccountgroupQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalaccountgroupQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalaccountgroupQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getDate() {
/*  50 */     return this.date;
/*     */   }
/*     */   public PortalaccountgroupQuery setDate(Date date) {
/*  53 */     this.date = date;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/*  58 */     return this.time;
/*     */   }
/*     */   public PortalaccountgroupQuery setTime(Long time) {
/*  61 */     this.time = time;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOctets() {
/*  66 */     return this.octets;
/*     */   }
/*     */   public PortalaccountgroupQuery setOctets(Long octets) {
/*  69 */     this.octets = octets;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  74 */     return this.state;
/*     */   }
/*     */   public PortalaccountgroupQuery setState(String state) {
/*  77 */     this.state = state;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setStateLike(boolean isLike) {
/*  82 */     this.stateLike = isLike;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSpeed() {
/*  87 */     return this.speed;
/*     */   }
/*     */   public PortalaccountgroupQuery setSpeed(Long speed) {
/*  90 */     this.speed = speed;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimit() {
/*  95 */     return this.maclimit;
/*     */   }
/*     */   public PortalaccountgroupQuery setMaclimit(Integer maclimit) {
/*  98 */     this.maclimit = maclimit;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimitcount() {
/* 103 */     return this.maclimitcount;
/*     */   }
/*     */   public PortalaccountgroupQuery setMaclimitcount(Integer maclimitcount) {
/* 106 */     this.maclimitcount = maclimitcount;
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAutologin() {
/* 111 */     return this.autologin;
/*     */   }
/*     */   public PortalaccountgroupQuery setAutologin(Integer autologin) {
/* 114 */     this.autologin = autologin;
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAutoKick() {
/* 119 */     return this.autoKick;
/*     */   }
/*     */   public PortalaccountgroupQuery setAutoKick(Integer autoKick) {
/* 122 */     this.autoKick = autoKick;
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/* 127 */     return this.ex1;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx1(String ex1) {
/* 130 */     this.ex1 = ex1;
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx1Like(boolean isLike) {
/* 135 */     this.ex1Like = isLike;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 140 */     return this.ex2;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx2(String ex2) {
/* 143 */     this.ex2 = ex2;
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx2Like(boolean isLike) {
/* 148 */     this.ex2Like = isLike;
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 153 */     return this.ex3;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx3(String ex3) {
/* 156 */     this.ex3 = ex3;
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx3Like(boolean isLike) {
/* 161 */     this.ex3Like = isLike;
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 166 */     return this.ex4;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx4(String ex4) {
/* 169 */     this.ex4 = ex4;
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx4Like(boolean isLike) {
/* 174 */     this.ex4Like = isLike;
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 179 */     return this.ex5;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx5(String ex5) {
/* 182 */     this.ex5 = ex5;
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx5Like(boolean isLike) {
/* 187 */     this.ex5Like = isLike;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 192 */     return this.ex6;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx6(String ex6) {
/* 195 */     this.ex6 = ex6;
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx6Like(boolean isLike) {
/* 200 */     this.ex6Like = isLike;
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 205 */     return this.ex7;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx7(String ex7) {
/* 208 */     this.ex7 = ex7;
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx7Like(boolean isLike) {
/* 213 */     this.ex7Like = isLike;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 218 */     return this.ex8;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx8(String ex8) {
/* 221 */     this.ex8 = ex8;
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx8Like(boolean isLike) {
/* 226 */     this.ex8Like = isLike;
/* 227 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 231 */     return this.ex9;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx9(String ex9) {
/* 234 */     this.ex9 = ex9;
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx9Like(boolean isLike) {
/* 239 */     this.ex9Like = isLike;
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 244 */     return this.ex10;
/*     */   }
/*     */   public PortalaccountgroupQuery setEx10(String ex10) {
/* 247 */     this.ex10 = ex10;
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setEx10Like(boolean isLike) {
/* 252 */     this.ex10Like = isLike;
/* 253 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIsp() {
/* 257 */     return this.isp;
/*     */   }
/*     */   public PortalaccountgroupQuery setIsp(String isp) {
/* 260 */     this.isp = isp;
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setIspLike(boolean isLike) {
/* 265 */     this.ispLike = isLike;
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */   public String getRadius() {
/* 270 */     return this.radius;
/*     */   }
/*     */   public PortalaccountgroupQuery setRadius(String radius) {
/* 273 */     this.radius = radius;
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery setRadiusLike(boolean isLike) {
/* 278 */     this.radiusLike = isLike;
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getCreatDate() {
/* 283 */     return this.creatDate;
/*     */   }
/*     */   public PortalaccountgroupQuery setCreatDate(Date creatDate) {
/* 286 */     this.creatDate = creatDate;
/* 287 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getUnlockMac() {
/* 291 */     return this.unlockMac;
/*     */   }
/*     */   public PortalaccountgroupQuery setUnlockMac(Integer unlockMac) {
/* 294 */     this.unlockMac = unlockMac;
/* 295 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getClearHaveAll() {
/* 299 */     return this.clearHaveAll;
/*     */   }
/*     */   public PortalaccountgroupQuery setClearHaveAll(Integer clearHaveAll) {
/* 302 */     this.clearHaveAll = clearHaveAll;
/* 303 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getClearHaveLimit() {
/* 307 */     return this.clearHaveLimit;
/*     */   }
/*     */   public PortalaccountgroupQuery setClearHaveLimit(Integer clearHaveLimit) {
/* 310 */     this.clearHaveLimit = clearHaveLimit;
/* 311 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOctetsLimit() {
/* 315 */     return this.octetsLimit;
/*     */   }
/*     */   public PortalaccountgroupQuery setOctetsLimit(Long octetsLimit) {
/* 318 */     this.octetsLimit = octetsLimit;
/* 319 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTimeLimit() {
/* 323 */     return this.timeLimit;
/*     */   }
/*     */   public PortalaccountgroupQuery setTimeLimit(Long timeLimit) {
/* 326 */     this.timeLimit = timeLimit;
/* 327 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyId(boolean isAsc)
/*     */   {
/* 370 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyName(boolean isAsc)
/*     */   {
/* 380 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 390 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyDate(boolean isAsc)
/*     */   {
/* 400 */     this.orderFields.add(new OrderField("date", isAsc ? "ASC" : "DESC"));
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyTime(boolean isAsc)
/*     */   {
/* 410 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyOctets(boolean isAsc)
/*     */   {
/* 420 */     this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
/* 421 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyState(boolean isAsc)
/*     */   {
/* 430 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 431 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbySpeed(boolean isAsc)
/*     */   {
/* 440 */     this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyMaclimit(boolean isAsc)
/*     */   {
/* 450 */     this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyMaclimitcount(boolean isAsc)
/*     */   {
/* 460 */     this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
/* 461 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyAutologin(boolean isAsc)
/*     */   {
/* 470 */     this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
/* 471 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyAutoKick(boolean isAsc)
/*     */   {
/* 480 */     this.orderFields.add(new OrderField("autoKick", isAsc ? "ASC" : "DESC"));
/* 481 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 490 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 491 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 500 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 501 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 510 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 511 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 520 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 521 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 530 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 531 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 540 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 541 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 550 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 551 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 560 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 561 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 570 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 571 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 580 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 581 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyIsp(boolean isAsc)
/*     */   {
/* 590 */     this.orderFields.add(new OrderField("isp", isAsc ? "ASC" : "DESC"));
/* 591 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyRadius(boolean isAsc)
/*     */   {
/* 600 */     this.orderFields.add(new OrderField("radius", isAsc ? "ASC" : "DESC"));
/* 601 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyCreatDate(boolean isAsc)
/*     */   {
/* 610 */     this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
/* 611 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyUnlockMac(boolean isAsc)
/*     */   {
/* 620 */     this.orderFields.add(new OrderField("unlockMac", isAsc ? "ASC" : "DESC"));
/* 621 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyClearHaveAll(boolean isAsc)
/*     */   {
/* 630 */     this.orderFields.add(new OrderField("clearHaveAll", isAsc ? "ASC" : "DESC"));
/* 631 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyClearHaveLimit(boolean isAsc)
/*     */   {
/* 640 */     this.orderFields.add(new OrderField("clearHaveLimit", isAsc ? "ASC" : "DESC"));
/* 641 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyOctetsLimit(boolean isAsc)
/*     */   {
/* 650 */     this.orderFields.add(new OrderField("octetsLimit", isAsc ? "ASC" : "DESC"));
/* 651 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountgroupQuery orderbyTimeLimit(boolean isAsc)
/*     */   {
/* 660 */     this.orderFields.add(new OrderField("timeLimit", isAsc ? "ASC" : "DESC"));
/* 661 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 670 */     if (fieldMap == null) {
/* 671 */       fieldMap = new HashMap();
/* 672 */       fieldMap.put("id", "id");
/* 673 */       fieldMap.put("name", "name");
/* 674 */       fieldMap.put("description", "description");
/* 675 */       fieldMap.put("date", "date");
/* 676 */       fieldMap.put("time", "time");
/* 677 */       fieldMap.put("octets", "octets");
/* 678 */       fieldMap.put("state", "state");
/* 679 */       fieldMap.put("speed", "speed");
/* 680 */       fieldMap.put("maclimit", "maclimit");
/* 681 */       fieldMap.put("maclimitcount", "maclimitcount");
/* 682 */       fieldMap.put("autologin", "autologin");
/* 683 */       fieldMap.put("autoKick", "autoKick");
/* 684 */       fieldMap.put("ex1", "ex1");
/* 685 */       fieldMap.put("ex2", "ex2");
/* 686 */       fieldMap.put("ex3", "ex3");
/* 687 */       fieldMap.put("ex4", "ex4");
/* 688 */       fieldMap.put("ex5", "ex5");
/* 689 */       fieldMap.put("ex6", "ex6");
/* 690 */       fieldMap.put("ex7", "ex7");
/* 691 */       fieldMap.put("ex8", "ex8");
/* 692 */       fieldMap.put("ex9", "ex9");
/* 693 */       fieldMap.put("ex10", "ex10");
/* 694 */       fieldMap.put("isp", "isp");
/* 695 */       fieldMap.put("radius", "radius");
/* 696 */       fieldMap.put("creatDate", "creatDate");
/* 697 */       fieldMap.put("unlockMac", "unlockMac");
/* 698 */       fieldMap.put("clearHaveAll", "clearHaveAll");
/* 699 */       fieldMap.put("clearHaveLimit", "clearHaveLimit");
/* 700 */       fieldMap.put("octetsLimit", "octetsLimit");
/* 701 */       fieldMap.put("timeLimit", "timeLimit");
/*     */     }
/* 703 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 707 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 710 */     if (fields == null)
/* 711 */       return;
/* 712 */     String[] array = fields.split(",");
/* 713 */     StringBuffer buffer = new StringBuffer();
/* 714 */     for (String field : array) {
/* 715 */       if (getFieldSet().containsKey(field)) {
/* 716 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 717 */           .append(field).append(" ,");
/*     */       }
/* 719 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 720 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 721 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 724 */     if (buffer.length() != 0)
/* 725 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 727 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 336 */       this.fieldName = fieldName;
/* 337 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 343 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 346 */       this.fieldName = fieldName;
/* 347 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 350 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 353 */       this.order = order;
/* 354 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalaccountgroupQuery
 * JD-Core Version:    0.6.2
 */