/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RadiuslinkrecordallQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String nasip;
/*     */   private boolean nasipLike;
/*     */   private String sourceip;
/*     */   private boolean sourceipLike;
/*     */   private String userip;
/*     */   private boolean useripLike;
/*     */   private String callingstationid;
/*     */   private boolean callingstationidLike;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private Date startDate;
/*     */   private Date endDate;
/*     */   private Long time;
/*     */   private Long ins;
/*     */   private Long outs;
/*     */   private Long octets;
/*     */   private String acctsessionid;
/*     */   private boolean acctsessionidLike;
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
/* 324 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getNasip() {
/*  24 */     return this.nasip;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setNasip(String nasip) {
/*  27 */     this.nasip = nasip;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setNasipLike(boolean isLike) {
/*  32 */     this.nasipLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSourceip() {
/*  37 */     return this.sourceip;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setSourceip(String sourceip) {
/*  40 */     this.sourceip = sourceip;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setSourceipLike(boolean isLike) {
/*  45 */     this.sourceipLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getUserip() {
/*  50 */     return this.userip;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setUserip(String userip) {
/*  53 */     this.userip = userip;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setUseripLike(boolean isLike) {
/*  58 */     this.useripLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getCallingstationid() {
/*  63 */     return this.callingstationid;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setCallingstationid(String callingstationid) {
/*  66 */     this.callingstationid = callingstationid;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setCallingstationidLike(boolean isLike) {
/*  71 */     this.callingstationidLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  76 */     return this.name;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setName(String name) {
/*  79 */     this.name = name;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setNameLike(boolean isLike) {
/*  84 */     this.nameLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  89 */     return this.state;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setState(String state) {
/*  92 */     this.state = state;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setStateLike(boolean isLike) {
/*  97 */     this.stateLike = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getStartDate() {
/* 102 */     return this.startDate;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setStartDate(Date startDate) {
/* 105 */     this.startDate = startDate;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getEndDate() {
/* 110 */     return this.endDate;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEndDate(Date endDate) {
/* 113 */     this.endDate = endDate;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/* 118 */     return this.time;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setTime(Long time) {
/* 121 */     this.time = time;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getIns() {
/* 126 */     return this.ins;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setIns(Long ins) {
/* 129 */     this.ins = ins;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOuts() {
/* 134 */     return this.outs;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setOuts(Long outs) {
/* 137 */     this.outs = outs;
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOctets() {
/* 142 */     return this.octets;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setOctets(Long octets) {
/* 145 */     this.octets = octets;
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAcctsessionid() {
/* 150 */     return this.acctsessionid;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setAcctsessionid(String acctsessionid) {
/* 153 */     this.acctsessionid = acctsessionid;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setAcctsessionidLike(boolean isLike) {
/* 158 */     this.acctsessionidLike = isLike;
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/* 163 */     return this.ex1;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx1(String ex1) {
/* 166 */     this.ex1 = ex1;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx1Like(boolean isLike) {
/* 171 */     this.ex1Like = isLike;
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 176 */     return this.ex2;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx2(String ex2) {
/* 179 */     this.ex2 = ex2;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx2Like(boolean isLike) {
/* 184 */     this.ex2Like = isLike;
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 189 */     return this.ex3;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx3(String ex3) {
/* 192 */     this.ex3 = ex3;
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx3Like(boolean isLike) {
/* 197 */     this.ex3Like = isLike;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 202 */     return this.ex4;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx4(String ex4) {
/* 205 */     this.ex4 = ex4;
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx4Like(boolean isLike) {
/* 210 */     this.ex4Like = isLike;
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 215 */     return this.ex5;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx5(String ex5) {
/* 218 */     this.ex5 = ex5;
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx5Like(boolean isLike) {
/* 223 */     this.ex5Like = isLike;
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 228 */     return this.ex6;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx6(String ex6) {
/* 231 */     this.ex6 = ex6;
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx6Like(boolean isLike) {
/* 236 */     this.ex6Like = isLike;
/* 237 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 241 */     return this.ex7;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx7(String ex7) {
/* 244 */     this.ex7 = ex7;
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx7Like(boolean isLike) {
/* 249 */     this.ex7Like = isLike;
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 254 */     return this.ex8;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx8(String ex8) {
/* 257 */     this.ex8 = ex8;
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx8Like(boolean isLike) {
/* 262 */     this.ex8Like = isLike;
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 267 */     return this.ex9;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx9(String ex9) {
/* 270 */     this.ex9 = ex9;
/* 271 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx9Like(boolean isLike) {
/* 275 */     this.ex9Like = isLike;
/* 276 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 280 */     return this.ex10;
/*     */   }
/*     */   public RadiuslinkrecordallQuery setEx10(String ex10) {
/* 283 */     this.ex10 = ex10;
/* 284 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery setEx10Like(boolean isLike) {
/* 288 */     this.ex10Like = isLike;
/* 289 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyId(boolean isAsc)
/*     */   {
/* 332 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 333 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyNasip(boolean isAsc)
/*     */   {
/* 342 */     this.orderFields.add(new OrderField("nasip", isAsc ? "ASC" : "DESC"));
/* 343 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbySourceip(boolean isAsc)
/*     */   {
/* 352 */     this.orderFields.add(new OrderField("sourceip", isAsc ? "ASC" : "DESC"));
/* 353 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyUserip(boolean isAsc)
/*     */   {
/* 362 */     this.orderFields.add(new OrderField("userip", isAsc ? "ASC" : "DESC"));
/* 363 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyCallingstationid(boolean isAsc)
/*     */   {
/* 372 */     this.orderFields.add(new OrderField("callingstationid", isAsc ? "ASC" : "DESC"));
/* 373 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyName(boolean isAsc)
/*     */   {
/* 382 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 383 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyState(boolean isAsc)
/*     */   {
/* 392 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 393 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyStartDate(boolean isAsc)
/*     */   {
/* 402 */     this.orderFields.add(new OrderField("startDate", isAsc ? "ASC" : "DESC"));
/* 403 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEndDate(boolean isAsc)
/*     */   {
/* 412 */     this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
/* 413 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyTime(boolean isAsc)
/*     */   {
/* 422 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/* 423 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyIns(boolean isAsc)
/*     */   {
/* 432 */     this.orderFields.add(new OrderField("ins", isAsc ? "ASC" : "DESC"));
/* 433 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyOuts(boolean isAsc)
/*     */   {
/* 442 */     this.orderFields.add(new OrderField("outs", isAsc ? "ASC" : "DESC"));
/* 443 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyOctets(boolean isAsc)
/*     */   {
/* 452 */     this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
/* 453 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyAcctsessionid(boolean isAsc)
/*     */   {
/* 462 */     this.orderFields.add(new OrderField("acctsessionid", isAsc ? "ASC" : "DESC"));
/* 463 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 472 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 473 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 482 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 483 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 492 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 493 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 502 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 503 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 512 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 513 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 522 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 523 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 532 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 533 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 542 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 543 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 552 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 553 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiuslinkrecordallQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 562 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 563 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 572 */     if (fieldMap == null) {
/* 573 */       fieldMap = new HashMap();
/* 574 */       fieldMap.put("id", "id");
/* 575 */       fieldMap.put("nasip", "nasip");
/* 576 */       fieldMap.put("sourceip", "sourceip");
/* 577 */       fieldMap.put("userip", "userip");
/* 578 */       fieldMap.put("callingstationid", "callingstationid");
/* 579 */       fieldMap.put("name", "name");
/* 580 */       fieldMap.put("state", "state");
/* 581 */       fieldMap.put("startDate", "startDate");
/* 582 */       fieldMap.put("endDate", "endDate");
/* 583 */       fieldMap.put("time", "time");
/* 584 */       fieldMap.put("ins", "ins");
/* 585 */       fieldMap.put("outs", "outs");
/* 586 */       fieldMap.put("octets", "octets");
/* 587 */       fieldMap.put("acctsessionid", "acctsessionid");
/* 588 */       fieldMap.put("ex1", "ex1");
/* 589 */       fieldMap.put("ex2", "ex2");
/* 590 */       fieldMap.put("ex3", "ex3");
/* 591 */       fieldMap.put("ex4", "ex4");
/* 592 */       fieldMap.put("ex5", "ex5");
/* 593 */       fieldMap.put("ex6", "ex6");
/* 594 */       fieldMap.put("ex7", "ex7");
/* 595 */       fieldMap.put("ex8", "ex8");
/* 596 */       fieldMap.put("ex9", "ex9");
/* 597 */       fieldMap.put("ex10", "ex10");
/*     */     }
/* 599 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 603 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 606 */     if (fields == null)
/* 607 */       return;
/* 608 */     String[] array = fields.split(",");
/* 609 */     StringBuffer buffer = new StringBuffer();
/* 610 */     for (String field : array) {
/* 611 */       if (getFieldSet().containsKey(field)) {
/* 612 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 613 */           .append(field).append(" ,");
/*     */       }
/* 615 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 616 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 617 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 620 */     if (buffer.length() != 0)
/* 621 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 623 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 298 */       this.fieldName = fieldName;
/* 299 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 305 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 308 */       this.fieldName = fieldName;
/* 309 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 312 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 315 */       this.order = order;
/* 316 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.RadiuslinkrecordallQuery
 * JD-Core Version:    0.6.2
 */