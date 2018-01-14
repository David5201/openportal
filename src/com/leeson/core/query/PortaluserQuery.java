/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortaluserQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String loginName;
/*     */   private boolean loginNameLike;
/*     */   private String password;
/*     */   private boolean passwordLike;
/*     */   private String name;
/*     */   private boolean nameLike;
/*     */   private String gender;
/*     */   private boolean genderLike;
/*     */   private String phoneNumber;
/*     */   private boolean phoneNumberLike;
/*     */   private String email;
/*     */   private boolean emailLike;
/*     */   private String description;
/*     */   private boolean descriptionLike;
/*     */   private Long departmentId;
/* 154 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortaluserQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getLoginName() {
/*  24 */     return this.loginName;
/*     */   }
/*     */   public PortaluserQuery setLoginName(String loginName) {
/*  27 */     this.loginName = loginName;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery setLoginNameLike(boolean isLike) {
/*  32 */     this.loginNameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/*  37 */     return this.password;
/*     */   }
/*     */   public PortaluserQuery setPassword(String password) {
/*  40 */     this.password = password;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery setPasswordLike(boolean isLike) {
/*  45 */     this.passwordLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */   public PortaluserQuery setName(String name) {
/*  53 */     this.name = name;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery setNameLike(boolean isLike) {
/*  58 */     this.nameLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getGender() {
/*  63 */     return this.gender;
/*     */   }
/*     */   public PortaluserQuery setGender(String gender) {
/*  66 */     this.gender = gender;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery setGenderLike(boolean isLike) {
/*  71 */     this.genderLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPhoneNumber() {
/*  76 */     return this.phoneNumber;
/*     */   }
/*     */   public PortaluserQuery setPhoneNumber(String phoneNumber) {
/*  79 */     this.phoneNumber = phoneNumber;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery setPhoneNumberLike(boolean isLike) {
/*  84 */     this.phoneNumberLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  89 */     return this.email;
/*     */   }
/*     */   public PortaluserQuery setEmail(String email) {
/*  92 */     this.email = email;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery setEmailLike(boolean isLike) {
/*  97 */     this.emailLike = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 102 */     return this.description;
/*     */   }
/*     */   public PortaluserQuery setDescription(String description) {
/* 105 */     this.description = description;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery setDescriptionLike(boolean isLike) {
/* 110 */     this.descriptionLike = isLike;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getDepartmentId() {
/* 115 */     return this.departmentId;
/*     */   }
/*     */   public PortaluserQuery setDepartmentId(Long departmentId) {
/* 118 */     this.departmentId = departmentId;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyId(boolean isAsc)
/*     */   {
/* 162 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyLoginName(boolean isAsc)
/*     */   {
/* 172 */     this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyPassword(boolean isAsc)
/*     */   {
/* 182 */     this.orderFields.add(new OrderField("password", isAsc ? "ASC" : "DESC"));
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyName(boolean isAsc)
/*     */   {
/* 192 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyGender(boolean isAsc)
/*     */   {
/* 202 */     this.orderFields.add(new OrderField("gender", isAsc ? "ASC" : "DESC"));
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyPhoneNumber(boolean isAsc)
/*     */   {
/* 212 */     this.orderFields.add(new OrderField("phoneNumber", isAsc ? "ASC" : "DESC"));
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyEmail(boolean isAsc)
/*     */   {
/* 222 */     this.orderFields.add(new OrderField("email", isAsc ? "ASC" : "DESC"));
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 232 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   public PortaluserQuery orderbyDepartmentId(boolean isAsc)
/*     */   {
/* 242 */     this.orderFields.add(new OrderField("departmentId", isAsc ? "ASC" : "DESC"));
/* 243 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 252 */     if (fieldMap == null) {
/* 253 */       fieldMap = new HashMap();
/* 254 */       fieldMap.put("id", "id");
/* 255 */       fieldMap.put("loginName", "loginName");
/* 256 */       fieldMap.put("password", "password");
/* 257 */       fieldMap.put("name", "name");
/* 258 */       fieldMap.put("gender", "gender");
/* 259 */       fieldMap.put("phoneNumber", "phoneNumber");
/* 260 */       fieldMap.put("email", "email");
/* 261 */       fieldMap.put("description", "description");
/* 262 */       fieldMap.put("departmentId", "departmentId");
/*     */     }
/* 264 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 268 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 271 */     if (fields == null)
/* 272 */       return;
/* 273 */     String[] array = fields.split(",");
/* 274 */     StringBuffer buffer = new StringBuffer();
/* 275 */     for (String field : array) {
/* 276 */       if (getFieldSet().containsKey(field)) {
/* 277 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 278 */           .append(field).append(" ,");
/*     */       }
/* 280 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 281 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 282 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 285 */     if (buffer.length() != 0)
/* 286 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 288 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 128 */       this.fieldName = fieldName;
/* 129 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 135 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 138 */       this.fieldName = fieldName;
/* 139 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 142 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 145 */       this.order = order;
/* 146 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortaluserQuery
 * JD-Core Version:    0.6.2
 */