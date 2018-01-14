/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalorderQuery extends BaseQuery
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
/*     */   private Integer payby;
/*     */   private String tradeno;
/*     */   private boolean tradenoLike;
/*     */   private String buyer;
/*     */   private boolean buyerLike;
/*     */   private String seller;
/*     */   private boolean sellerLike;
/* 249 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalorderQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public PortalorderQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public PortalorderQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getPayTime() {
/*  50 */     return this.payTime;
/*     */   }
/*     */   public PortalorderQuery setPayTime(Long payTime) {
/*  53 */     this.payTime = payTime;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPayType() {
/*  58 */     return this.payType;
/*     */   }
/*     */   public PortalorderQuery setPayType(String payType) {
/*  61 */     this.payType = payType;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setPayTypeLike(boolean isLike) {
/*  66 */     this.payTypeLike = isLike;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  71 */     return this.state;
/*     */   }
/*     */   public PortalorderQuery setState(String state) {
/*  74 */     this.state = state;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setStateLike(boolean isLike) {
/*  79 */     this.stateLike = isLike;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public String getCdKey() {
/*  84 */     return this.cdKey;
/*     */   }
/*     */   public PortalorderQuery setCdKey(String cdKey) {
/*  87 */     this.cdKey = cdKey;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setCdKeyLike(boolean isLike) {
/*  92 */     this.cdKeyLike = isLike;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public String getCategoryType() {
/*  97 */     return this.categoryType;
/*     */   }
/*     */   public PortalorderQuery setCategoryType(String categoryType) {
/* 100 */     this.categoryType = categoryType;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setCategoryTypeLike(boolean isLike) {
/* 105 */     this.categoryTypeLike = isLike;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAccountName() {
/* 110 */     return this.accountName;
/*     */   }
/*     */   public PortalorderQuery setAccountName(String accountName) {
/* 113 */     this.accountName = accountName;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setAccountNameLike(boolean isLike) {
/* 118 */     this.accountNameLike = isLike;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getAccountId() {
/* 123 */     return this.accountId;
/*     */   }
/*     */   public PortalorderQuery setAccountId(Long accountId) {
/* 126 */     this.accountId = accountId;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getPayDate() {
/* 131 */     return this.payDate;
/*     */   }
/*     */   public PortalorderQuery setPayDate(Date payDate) {
/* 134 */     this.payDate = payDate;
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getUserDel() {
/* 139 */     return this.userDel;
/*     */   }
/*     */   public PortalorderQuery setUserDel(Integer userDel) {
/* 142 */     this.userDel = userDel;
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAccountDel() {
/* 147 */     return this.accountDel;
/*     */   }
/*     */   public PortalorderQuery setAccountDel(Integer accountDel) {
/* 150 */     this.accountDel = accountDel;
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   public Double getMoney() {
/* 155 */     return this.money;
/*     */   }
/*     */   public PortalorderQuery setMoney(Double money) {
/* 158 */     this.money = money;
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getBuyDate() {
/* 163 */     return this.buyDate;
/*     */   }
/*     */   public PortalorderQuery setBuyDate(Date buyDate) {
/* 166 */     this.buyDate = buyDate;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getPayby() {
/* 171 */     return this.payby;
/*     */   }
/*     */   public PortalorderQuery setPayby(Integer payby) {
/* 174 */     this.payby = payby;
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   public String getTradeno() {
/* 179 */     return this.tradeno;
/*     */   }
/*     */   public PortalorderQuery setTradeno(String tradeno) {
/* 182 */     this.tradeno = tradeno;
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setTradenoLike(boolean isLike) {
/* 187 */     this.tradenoLike = isLike;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBuyer() {
/* 192 */     return this.buyer;
/*     */   }
/*     */   public PortalorderQuery setBuyer(String buyer) {
/* 195 */     this.buyer = buyer;
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setBuyerLike(boolean isLike) {
/* 200 */     this.buyerLike = isLike;
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSeller() {
/* 205 */     return this.seller;
/*     */   }
/*     */   public PortalorderQuery setSeller(String seller) {
/* 208 */     this.seller = seller;
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery setSellerLike(boolean isLike) {
/* 213 */     this.sellerLike = isLike;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyId(boolean isAsc)
/*     */   {
/* 257 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyName(boolean isAsc)
/*     */   {
/* 267 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 277 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 278 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyPayTime(boolean isAsc)
/*     */   {
/* 287 */     this.orderFields.add(new OrderField("payTime", isAsc ? "ASC" : "DESC"));
/* 288 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyPayType(boolean isAsc)
/*     */   {
/* 297 */     this.orderFields.add(new OrderField("payType", isAsc ? "ASC" : "DESC"));
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyState(boolean isAsc)
/*     */   {
/* 307 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyCdKey(boolean isAsc)
/*     */   {
/* 317 */     this.orderFields.add(new OrderField("cdKey", isAsc ? "ASC" : "DESC"));
/* 318 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyCategoryType(boolean isAsc)
/*     */   {
/* 327 */     this.orderFields.add(new OrderField("categoryType", isAsc ? "ASC" : "DESC"));
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyAccountName(boolean isAsc)
/*     */   {
/* 337 */     this.orderFields.add(new OrderField("accountName", isAsc ? "ASC" : "DESC"));
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyAccountId(boolean isAsc)
/*     */   {
/* 347 */     this.orderFields.add(new OrderField("accountId", isAsc ? "ASC" : "DESC"));
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyPayDate(boolean isAsc)
/*     */   {
/* 357 */     this.orderFields.add(new OrderField("payDate", isAsc ? "ASC" : "DESC"));
/* 358 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyUserDel(boolean isAsc)
/*     */   {
/* 367 */     this.orderFields.add(new OrderField("userDel", isAsc ? "ASC" : "DESC"));
/* 368 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyAccountDel(boolean isAsc)
/*     */   {
/* 377 */     this.orderFields.add(new OrderField("accountDel", isAsc ? "ASC" : "DESC"));
/* 378 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyMoney(boolean isAsc)
/*     */   {
/* 387 */     this.orderFields.add(new OrderField("money", isAsc ? "ASC" : "DESC"));
/* 388 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyBuyDate(boolean isAsc)
/*     */   {
/* 397 */     this.orderFields.add(new OrderField("buyDate", isAsc ? "ASC" : "DESC"));
/* 398 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyPayby(boolean isAsc)
/*     */   {
/* 407 */     this.orderFields.add(new OrderField("payby", isAsc ? "ASC" : "DESC"));
/* 408 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyTradeno(boolean isAsc)
/*     */   {
/* 417 */     this.orderFields.add(new OrderField("tradeno", isAsc ? "ASC" : "DESC"));
/* 418 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbyBuyer(boolean isAsc)
/*     */   {
/* 427 */     this.orderFields.add(new OrderField("buyer", isAsc ? "ASC" : "DESC"));
/* 428 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalorderQuery orderbySeller(boolean isAsc)
/*     */   {
/* 437 */     this.orderFields.add(new OrderField("seller", isAsc ? "ASC" : "DESC"));
/* 438 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 447 */     if (fieldMap == null) {
/* 448 */       fieldMap = new HashMap();
/* 449 */       fieldMap.put("id", "id");
/* 450 */       fieldMap.put("name", "name");
/* 451 */       fieldMap.put("description", "description");
/* 452 */       fieldMap.put("payTime", "payTime");
/* 453 */       fieldMap.put("payType", "payType");
/* 454 */       fieldMap.put("state", "state");
/* 455 */       fieldMap.put("cdKey", "cdKey");
/* 456 */       fieldMap.put("categoryType", "categoryType");
/* 457 */       fieldMap.put("accountName", "accountName");
/* 458 */       fieldMap.put("accountId", "accountId");
/* 459 */       fieldMap.put("payDate", "payDate");
/* 460 */       fieldMap.put("userDel", "userDel");
/* 461 */       fieldMap.put("accountDel", "accountDel");
/* 462 */       fieldMap.put("money", "money");
/* 463 */       fieldMap.put("buyDate", "buyDate");
/* 464 */       fieldMap.put("payby", "payby");
/* 465 */       fieldMap.put("tradeno", "tradeno");
/* 466 */       fieldMap.put("buyer", "buyer");
/* 467 */       fieldMap.put("seller", "seller");
/*     */     }
/* 469 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 473 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 476 */     if (fields == null)
/* 477 */       return;
/* 478 */     String[] array = fields.split(",");
/* 479 */     StringBuffer buffer = new StringBuffer();
/* 480 */     for (String field : array) {
/* 481 */       if (getFieldSet().containsKey(field)) {
/* 482 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 483 */           .append(field).append(" ,");
/*     */       }
/* 485 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 486 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 487 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 490 */     if (buffer.length() != 0)
/* 491 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 493 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 223 */       this.fieldName = fieldName;
/* 224 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 230 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 233 */       this.fieldName = fieldName;
/* 234 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 237 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 240 */       this.order = order;
/* 241 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalorderQuery
 * JD-Core Version:    0.6.2
 */