/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AdvstoresQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Date creatDate;
/*     */   private Long uid;
/*     */   private String address;
/*     */   private boolean addressLike;
/*     */   private String phone;
/*     */   private boolean phoneLike;
/*     */   private String img;
/*     */   private boolean imgLike;
/*     */   private Integer showInfo;
/*     */   private String x;
/*     */   private boolean xLike;
/*     */   private String y;
/*     */   private boolean yLike;
/* 170 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public AdvstoresQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */   public AdvstoresQuery setName(String name) {
/*  27 */     this.name = name;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery setNameLike(boolean isLike) {
/*  32 */     this.nameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  37 */     return this.description;
/*     */   }
/*     */   public AdvstoresQuery setDescription(String description) {
/*  40 */     this.description = description;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery setDescriptionLike(boolean isLike) {
/*  45 */     this.descriptionLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getCreatDate() {
/*  50 */     return this.creatDate;
/*     */   }
/*     */   public AdvstoresQuery setCreatDate(Date creatDate) {
/*  53 */     this.creatDate = creatDate;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getUid() {
/*  58 */     return this.uid;
/*     */   }
/*     */   public AdvstoresQuery setUid(Long uid) {
/*  61 */     this.uid = uid;
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAddress() {
/*  66 */     return this.address;
/*     */   }
/*     */   public AdvstoresQuery setAddress(String address) {
/*  69 */     this.address = address;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery setAddressLike(boolean isLike) {
/*  74 */     this.addressLike = isLike;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPhone() {
/*  79 */     return this.phone;
/*     */   }
/*     */   public AdvstoresQuery setPhone(String phone) {
/*  82 */     this.phone = phone;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery setPhoneLike(boolean isLike) {
/*  87 */     this.phoneLike = isLike;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public String getImg() {
/*  92 */     return this.img;
/*     */   }
/*     */   public AdvstoresQuery setImg(String img) {
/*  95 */     this.img = img;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery setImgLike(boolean isLike) {
/* 100 */     this.imgLike = isLike;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getShowInfo() {
/* 105 */     return this.showInfo;
/*     */   }
/*     */   public AdvstoresQuery setShowInfo(Integer showInfo) {
/* 108 */     this.showInfo = showInfo;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   public String getX() {
/* 113 */     return this.x;
/*     */   }
/*     */   public AdvstoresQuery setX(String x) {
/* 116 */     this.x = x;
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery setXLike(boolean isLike) {
/* 121 */     this.xLike = isLike;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   public String getY() {
/* 126 */     return this.y;
/*     */   }
/*     */   public AdvstoresQuery setY(String y) {
/* 129 */     this.y = y;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery setYLike(boolean isLike) {
/* 134 */     this.yLike = isLike;
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyId(boolean isAsc)
/*     */   {
/* 178 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyName(boolean isAsc)
/*     */   {
/* 188 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 198 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyCreatDate(boolean isAsc)
/*     */   {
/* 208 */     this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyUid(boolean isAsc)
/*     */   {
/* 218 */     this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyAddress(boolean isAsc)
/*     */   {
/* 228 */     this.orderFields.add(new OrderField("address", isAsc ? "ASC" : "DESC"));
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyPhone(boolean isAsc)
/*     */   {
/* 238 */     this.orderFields.add(new OrderField("phone", isAsc ? "ASC" : "DESC"));
/* 239 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyImg(boolean isAsc)
/*     */   {
/* 248 */     this.orderFields.add(new OrderField("img", isAsc ? "ASC" : "DESC"));
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyShowInfo(boolean isAsc)
/*     */   {
/* 258 */     this.orderFields.add(new OrderField("showInfo", isAsc ? "ASC" : "DESC"));
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyX(boolean isAsc)
/*     */   {
/* 268 */     this.orderFields.add(new OrderField("x", isAsc ? "ASC" : "DESC"));
/* 269 */     return this;
/*     */   }
/*     */ 
/*     */   public AdvstoresQuery orderbyY(boolean isAsc)
/*     */   {
/* 278 */     this.orderFields.add(new OrderField("y", isAsc ? "ASC" : "DESC"));
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 288 */     if (fieldMap == null) {
/* 289 */       fieldMap = new HashMap();
/* 290 */       fieldMap.put("id", "id");
/* 291 */       fieldMap.put("name", "name");
/* 292 */       fieldMap.put("description", "description");
/* 293 */       fieldMap.put("creatDate", "creatDate");
/* 294 */       fieldMap.put("uid", "uid");
/* 295 */       fieldMap.put("address", "address");
/* 296 */       fieldMap.put("phone", "phone");
/* 297 */       fieldMap.put("img", "img");
/* 298 */       fieldMap.put("showInfo", "showInfo");
/* 299 */       fieldMap.put("x", "x");
/* 300 */       fieldMap.put("y", "y");
/*     */     }
/* 302 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 306 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 309 */     if (fields == null)
/* 310 */       return;
/* 311 */     String[] array = fields.split(",");
/* 312 */     StringBuffer buffer = new StringBuffer();
/* 313 */     for (String field : array) {
/* 314 */       if (getFieldSet().containsKey(field)) {
/* 315 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 316 */           .append(field).append(" ,");
/*     */       }
/* 318 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 319 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 320 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 323 */     if (buffer.length() != 0)
/* 324 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 326 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 144 */       this.fieldName = fieldName;
/* 145 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 151 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 154 */       this.fieldName = fieldName;
/* 155 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 158 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 161 */       this.order = order;
/* 162 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.AdvstoresQuery
 * JD-Core Version:    0.6.2
 */