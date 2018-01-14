/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalcardQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Long payTime;
/*     */   private String payType;
/*     */   private boolean payTypeLike;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private String cdKey;
/*     */   private boolean cdKeyLike;
/*     */   private String categoryType;
/*     */   private boolean categoryTypeLike;
/*     */   private String accountName;
/*     */   private boolean accountNameLike;
/*     */   private Long accountId;
/*     */   private Date payDate;
/*     */   private Integer userDel;
/*     */   private Integer accountDel;
/*     */   private Double money;
/*     */   private Date buyDate;
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
/* 364 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalcardQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalcardQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalcardQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getPayTime() {
/*  50 */     return this.payTime;
/*     */   }
/*     */   public PortalcardQuery setPayTime(Long payTime) {
/*  53 */     this.payTime = payTime;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPayType() {
/*  58 */     return this.payType;
/*     */   }
/*     */   public PortalcardQuery setPayType(String payType) {
/*  61 */     this.payType = payType;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setPayTypeLike(boolean isLike) {
/*  66 */     this.payTypeLike = isLike;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  71 */     return this.state;
/*     */   }
/*     */   public PortalcardQuery setState(String state) {
/*  74 */     this.state = state;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setStateLike(boolean isLike) {
/*  79 */     this.stateLike = isLike;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public String getCdKey() {
/*  84 */     return this.cdKey;
/*     */   }
/*     */   public PortalcardQuery setCdKey(String cdKey) {
/*  87 */     this.cdKey = cdKey;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setCdKeyLike(boolean isLike) {
/*  92 */     this.cdKeyLike = isLike;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public String getCategoryType() {
/*  97 */     return this.categoryType;
/*     */   }
/*     */   public PortalcardQuery setCategoryType(String categoryType) {
/* 100 */     this.categoryType = categoryType;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setCategoryTypeLike(boolean isLike) {
/* 105 */     this.categoryTypeLike = isLike;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAccountName() {
/* 110 */     return this.accountName;
/*     */   }
/*     */   public PortalcardQuery setAccountName(String accountName) {
/* 113 */     this.accountName = accountName;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setAccountNameLike(boolean isLike) {
/* 118 */     this.accountNameLike = isLike;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getAccountId() {
/* 123 */     return this.accountId;
/*     */   }
/*     */   public PortalcardQuery setAccountId(Long accountId) {
/* 126 */     this.accountId = accountId;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getPayDate() {
/* 131 */     return this.payDate;
/*     */   }
/*     */   public PortalcardQuery setPayDate(Date payDate) {
/* 134 */     this.payDate = payDate;
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getUserDel() {
/* 139 */     return this.userDel;
/*     */   }
/*     */   public PortalcardQuery setUserDel(Integer userDel) {
/* 142 */     this.userDel = userDel;
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAccountDel() {
/* 147 */     return this.accountDel;
/*     */   }
/*     */   public PortalcardQuery setAccountDel(Integer accountDel) {
/* 150 */     this.accountDel = accountDel;
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   public Double getMoney() {
/* 155 */     return this.money;
/*     */   }
/*     */   public PortalcardQuery setMoney(Double money) {
/* 158 */     this.money = money;
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getBuyDate() {
/* 163 */     return this.buyDate;
/*     */   }
/*     */   public PortalcardQuery setBuyDate(Date buyDate) {
/* 166 */     this.buyDate = buyDate;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSpeed() {
/* 171 */     return this.speed;
/*     */   }
/*     */   public PortalcardQuery setSpeed(Long speed) {
/* 174 */     this.speed = speed;
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimit() {
/* 179 */     return this.maclimit;
/*     */   }
/*     */   public PortalcardQuery setMaclimit(Integer maclimit) {
/* 182 */     this.maclimit = maclimit;
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimitcount() {
/* 187 */     return this.maclimitcount;
/*     */   }
/*     */   public PortalcardQuery setMaclimitcount(Integer maclimitcount) {
/* 190 */     this.maclimitcount = maclimitcount;
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAutologin() {
/* 195 */     return this.autologin;
/*     */   }
/*     */   public PortalcardQuery setAutologin(Integer autologin) {
/* 198 */     this.autologin = autologin;
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/* 203 */     return this.ex1;
/*     */   }
/*     */   public PortalcardQuery setEx1(String ex1) {
/* 206 */     this.ex1 = ex1;
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx1Like(boolean isLike) {
/* 211 */     this.ex1Like = isLike;
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 216 */     return this.ex2;
/*     */   }
/*     */   public PortalcardQuery setEx2(String ex2) {
/* 219 */     this.ex2 = ex2;
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx2Like(boolean isLike) {
/* 224 */     this.ex2Like = isLike;
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 229 */     return this.ex3;
/*     */   }
/*     */   public PortalcardQuery setEx3(String ex3) {
/* 232 */     this.ex3 = ex3;
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx3Like(boolean isLike) {
/* 237 */     this.ex3Like = isLike;
/* 238 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 242 */     return this.ex4;
/*     */   }
/*     */   public PortalcardQuery setEx4(String ex4) {
/* 245 */     this.ex4 = ex4;
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx4Like(boolean isLike) {
/* 250 */     this.ex4Like = isLike;
/* 251 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 255 */     return this.ex5;
/*     */   }
/*     */   public PortalcardQuery setEx5(String ex5) {
/* 258 */     this.ex5 = ex5;
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx5Like(boolean isLike) {
/* 263 */     this.ex5Like = isLike;
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 268 */     return this.ex6;
/*     */   }
/*     */   public PortalcardQuery setEx6(String ex6) {
/* 271 */     this.ex6 = ex6;
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx6Like(boolean isLike) {
/* 276 */     this.ex6Like = isLike;
/* 277 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 281 */     return this.ex7;
/*     */   }
/*     */   public PortalcardQuery setEx7(String ex7) {
/* 284 */     this.ex7 = ex7;
/* 285 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx7Like(boolean isLike) {
/* 289 */     this.ex7Like = isLike;
/* 290 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 294 */     return this.ex8;
/*     */   }
/*     */   public PortalcardQuery setEx8(String ex8) {
/* 297 */     this.ex8 = ex8;
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx8Like(boolean isLike) {
/* 302 */     this.ex8Like = isLike;
/* 303 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 307 */     return this.ex9;
/*     */   }
/*     */   public PortalcardQuery setEx9(String ex9) {
/* 310 */     this.ex9 = ex9;
/* 311 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx9Like(boolean isLike) {
/* 315 */     this.ex9Like = isLike;
/* 316 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 320 */     return this.ex10;
/*     */   }
/*     */   public PortalcardQuery setEx10(String ex10) {
/* 323 */     this.ex10 = ex10;
/* 324 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery setEx10Like(boolean isLike) {
/* 328 */     this.ex10Like = isLike;
/* 329 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyId(boolean isAsc)
/*     */   {
/* 372 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 373 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyName(boolean isAsc)
/*     */   {
/* 382 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 383 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 392 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 393 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyPayTime(boolean isAsc)
/*     */   {
/* 402 */     this.orderFields.add(new OrderField("payTime", isAsc ? "ASC" : "DESC"));
/* 403 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyPayType(boolean isAsc)
/*     */   {
/* 412 */     this.orderFields.add(new OrderField("payType", isAsc ? "ASC" : "DESC"));
/* 413 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyState(boolean isAsc)
/*     */   {
/* 422 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 423 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyCdKey(boolean isAsc)
/*     */   {
/* 432 */     this.orderFields.add(new OrderField("cdKey", isAsc ? "ASC" : "DESC"));
/* 433 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyCategoryType(boolean isAsc)
/*     */   {
/* 442 */     this.orderFields.add(new OrderField("categoryType", isAsc ? "ASC" : "DESC"));
/* 443 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyAccountName(boolean isAsc)
/*     */   {
/* 452 */     this.orderFields.add(new OrderField("accountName", isAsc ? "ASC" : "DESC"));
/* 453 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyAccountId(boolean isAsc)
/*     */   {
/* 462 */     this.orderFields.add(new OrderField("accountId", isAsc ? "ASC" : "DESC"));
/* 463 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyPayDate(boolean isAsc)
/*     */   {
/* 472 */     this.orderFields.add(new OrderField("payDate", isAsc ? "ASC" : "DESC"));
/* 473 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyUserDel(boolean isAsc)
/*     */   {
/* 482 */     this.orderFields.add(new OrderField("userDel", isAsc ? "ASC" : "DESC"));
/* 483 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyAccountDel(boolean isAsc)
/*     */   {
/* 492 */     this.orderFields.add(new OrderField("accountDel", isAsc ? "ASC" : "DESC"));
/* 493 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyMoney(boolean isAsc)
/*     */   {
/* 502 */     this.orderFields.add(new OrderField("money", isAsc ? "ASC" : "DESC"));
/* 503 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyBuyDate(boolean isAsc)
/*     */   {
/* 512 */     this.orderFields.add(new OrderField("buyDate", isAsc ? "ASC" : "DESC"));
/* 513 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbySpeed(boolean isAsc)
/*     */   {
/* 522 */     this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
/* 523 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyMaclimit(boolean isAsc)
/*     */   {
/* 532 */     this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
/* 533 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyMaclimitcount(boolean isAsc)
/*     */   {
/* 542 */     this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
/* 543 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyAutologin(boolean isAsc)
/*     */   {
/* 552 */     this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
/* 553 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 562 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 563 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 572 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 573 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 582 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 583 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 592 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 593 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 602 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 603 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 612 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 613 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 622 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 623 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 632 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 633 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 642 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 643 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalcardQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 652 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 653 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 662 */     if (fieldMap == null) {
/* 663 */       fieldMap = new HashMap();
/* 664 */       fieldMap.put("id", "id");
/* 665 */       fieldMap.put("name", "name");
/* 666 */       fieldMap.put("description", "description");
/* 667 */       fieldMap.put("payTime", "payTime");
/* 668 */       fieldMap.put("payType", "payType");
/* 669 */       fieldMap.put("state", "state");
/* 670 */       fieldMap.put("cdKey", "cdKey");
/* 671 */       fieldMap.put("categoryType", "categoryType");
/* 672 */       fieldMap.put("accountName", "accountName");
/* 673 */       fieldMap.put("accountId", "accountId");
/* 674 */       fieldMap.put("payDate", "payDate");
/* 675 */       fieldMap.put("userDel", "userDel");
/* 676 */       fieldMap.put("accountDel", "accountDel");
/* 677 */       fieldMap.put("money", "money");
/* 678 */       fieldMap.put("buyDate", "buyDate");
/* 679 */       fieldMap.put("speed", "speed");
/* 680 */       fieldMap.put("maclimit", "maclimit");
/* 681 */       fieldMap.put("maclimitcount", "maclimitcount");
/* 682 */       fieldMap.put("autologin", "autologin");
/* 683 */       fieldMap.put("ex1", "ex1");
/* 684 */       fieldMap.put("ex2", "ex2");
/* 685 */       fieldMap.put("ex3", "ex3");
/* 686 */       fieldMap.put("ex4", "ex4");
/* 687 */       fieldMap.put("ex5", "ex5");
/* 688 */       fieldMap.put("ex6", "ex6");
/* 689 */       fieldMap.put("ex7", "ex7");
/* 690 */       fieldMap.put("ex8", "ex8");
/* 691 */       fieldMap.put("ex9", "ex9");
/* 692 */       fieldMap.put("ex10", "ex10");
/*     */     }
/* 694 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 698 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 701 */     if (fields == null)
/* 702 */       return;
/* 703 */     String[] array = fields.split(",");
/* 704 */     StringBuffer buffer = new StringBuffer();
/* 705 */     for (String field : array) {
/* 706 */       if (getFieldSet().containsKey(field)) {
/* 707 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 708 */           .append(field).append(" ,");
/*     */       }
/* 710 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 711 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 712 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 715 */     if (buffer.length() != 0)
/* 716 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 718 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 338 */       this.fieldName = fieldName;
/* 339 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 345 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 348 */       this.fieldName = fieldName;
/* 349 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 352 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 355 */       this.order = order;
/* 356 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalcardQuery
 * JD-Core Version:    0.6.2
 */