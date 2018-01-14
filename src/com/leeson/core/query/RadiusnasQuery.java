/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RadiusnasQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String ip;
/*     */   private boolean ipLike;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private String type;
/*     */   private boolean typeLike;
/*     */   private String sharedSecret;
/*     */   private boolean sharedSecretLike;
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
/* 250 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public RadiusnasQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/*  24 */     return this.ip;
/*     */   }
/*     */   public RadiusnasQuery setIp(String ip) {
/*  27 */     this.ip = ip;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setIpLike(boolean isLike) {
/*  32 */     this.ipLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  37 */     return this.name;
/*     */   }
/*     */   public RadiusnasQuery setName(String name) {
/*  40 */     this.name = name;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setNameLike(boolean isLike) {
/*  45 */     this.nameLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  50 */     return this.description;
/*     */   }
/*     */   public RadiusnasQuery setDescription(String description) {
/*  53 */     this.description = description;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setDescriptionLike(boolean isLike) {
/*  58 */     this.descriptionLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  63 */     return this.type;
/*     */   }
/*     */   public RadiusnasQuery setType(String type) {
/*  66 */     this.type = type;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setTypeLike(boolean isLike) {
/*  71 */     this.typeLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSharedSecret() {
/*  76 */     return this.sharedSecret;
/*     */   }
/*     */   public RadiusnasQuery setSharedSecret(String sharedSecret) {
/*  79 */     this.sharedSecret = sharedSecret;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setSharedSecretLike(boolean isLike) {
/*  84 */     this.sharedSecretLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/*  89 */     return this.ex1;
/*     */   }
/*     */   public RadiusnasQuery setEx1(String ex1) {
/*  92 */     this.ex1 = ex1;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx1Like(boolean isLike) {
/*  97 */     this.ex1Like = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 102 */     return this.ex2;
/*     */   }
/*     */   public RadiusnasQuery setEx2(String ex2) {
/* 105 */     this.ex2 = ex2;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx2Like(boolean isLike) {
/* 110 */     this.ex2Like = isLike;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 115 */     return this.ex3;
/*     */   }
/*     */   public RadiusnasQuery setEx3(String ex3) {
/* 118 */     this.ex3 = ex3;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx3Like(boolean isLike) {
/* 123 */     this.ex3Like = isLike;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 128 */     return this.ex4;
/*     */   }
/*     */   public RadiusnasQuery setEx4(String ex4) {
/* 131 */     this.ex4 = ex4;
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx4Like(boolean isLike) {
/* 136 */     this.ex4Like = isLike;
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 141 */     return this.ex5;
/*     */   }
/*     */   public RadiusnasQuery setEx5(String ex5) {
/* 144 */     this.ex5 = ex5;
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx5Like(boolean isLike) {
/* 149 */     this.ex5Like = isLike;
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 154 */     return this.ex6;
/*     */   }
/*     */   public RadiusnasQuery setEx6(String ex6) {
/* 157 */     this.ex6 = ex6;
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx6Like(boolean isLike) {
/* 162 */     this.ex6Like = isLike;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 167 */     return this.ex7;
/*     */   }
/*     */   public RadiusnasQuery setEx7(String ex7) {
/* 170 */     this.ex7 = ex7;
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx7Like(boolean isLike) {
/* 175 */     this.ex7Like = isLike;
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 180 */     return this.ex8;
/*     */   }
/*     */   public RadiusnasQuery setEx8(String ex8) {
/* 183 */     this.ex8 = ex8;
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx8Like(boolean isLike) {
/* 188 */     this.ex8Like = isLike;
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 193 */     return this.ex9;
/*     */   }
/*     */   public RadiusnasQuery setEx9(String ex9) {
/* 196 */     this.ex9 = ex9;
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx9Like(boolean isLike) {
/* 201 */     this.ex9Like = isLike;
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 206 */     return this.ex10;
/*     */   }
/*     */   public RadiusnasQuery setEx10(String ex10) {
/* 209 */     this.ex10 = ex10;
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery setEx10Like(boolean isLike) {
/* 214 */     this.ex10Like = isLike;
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyId(boolean isAsc)
/*     */   {
/* 258 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyIp(boolean isAsc)
/*     */   {
/* 268 */     this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
/* 269 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyName(boolean isAsc)
/*     */   {
/* 278 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 288 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 289 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyType(boolean isAsc)
/*     */   {
/* 298 */     this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
/* 299 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbySharedSecret(boolean isAsc)
/*     */   {
/* 308 */     this.orderFields.add(new OrderField("sharedSecret", isAsc ? "ASC" : "DESC"));
/* 309 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 318 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 319 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 328 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 329 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 338 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 339 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 348 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 349 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 358 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 359 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 368 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 369 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 378 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 379 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 388 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 389 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 398 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 399 */     return this;
/*     */   }
/*     */ 
/*     */   public RadiusnasQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 408 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 409 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 418 */     if (fieldMap == null) {
/* 419 */       fieldMap = new HashMap();
/* 420 */       fieldMap.put("id", "id");
/* 421 */       fieldMap.put("ip", "ip");
/* 422 */       fieldMap.put("name", "name");
/* 423 */       fieldMap.put("description", "description");
/* 424 */       fieldMap.put("type", "type");
/* 425 */       fieldMap.put("sharedSecret", "sharedSecret");
/* 426 */       fieldMap.put("ex1", "ex1");
/* 427 */       fieldMap.put("ex2", "ex2");
/* 428 */       fieldMap.put("ex3", "ex3");
/* 429 */       fieldMap.put("ex4", "ex4");
/* 430 */       fieldMap.put("ex5", "ex5");
/* 431 */       fieldMap.put("ex6", "ex6");
/* 432 */       fieldMap.put("ex7", "ex7");
/* 433 */       fieldMap.put("ex8", "ex8");
/* 434 */       fieldMap.put("ex9", "ex9");
/* 435 */       fieldMap.put("ex10", "ex10");
/*     */     }
/* 437 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 441 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 444 */     if (fields == null)
/* 445 */       return;
/* 446 */     String[] array = fields.split(",");
/* 447 */     StringBuffer buffer = new StringBuffer();
/* 448 */     for (String field : array) {
/* 449 */       if (getFieldSet().containsKey(field)) {
/* 450 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 451 */           .append(field).append(" ,");
/*     */       }
/* 453 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 454 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 455 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 458 */     if (buffer.length() != 0)
/* 459 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 461 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 224 */       this.fieldName = fieldName;
/* 225 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 231 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 234 */       this.fieldName = fieldName;
/* 235 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 238 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 241 */       this.order = order;
/* 242 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.RadiusnasQuery
 * JD-Core Version:    0.6.2
 */