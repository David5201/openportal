/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalbasQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String bas;
/*     */   private boolean basLike;
/*     */   private String basname;
/*     */   private boolean basnameLike;
/*     */   private String basIp;
/*     */   private boolean basIpLike;
/*     */   private String basPort;
/*     */   private boolean basPortLike;
/*     */   private String portalVer;
/*     */   private boolean portalVerLike;
/*     */   private String authType;
/*     */   private boolean authTypeLike;
/*     */   private String sharedSecret;
/*     */   private boolean sharedSecretLike;
/*     */   private String basUser;
/*     */   private boolean basUserLike;
/*     */   private String basPwd;
/*     */   private boolean basPwdLike;
/*     */   private String timeoutSec;
/*     */   private boolean timeoutSecLike;
/*     */   private String isPortalCheck;
/*     */   private boolean isPortalCheckLike;
/*     */   private String isOut;
/*     */   private boolean isOutLike;
/*     */   private String authInterface;
/*     */   private boolean authInterfaceLike;
/*     */   private String isComputer;
/*     */   private boolean isComputerLike;
/*     */   private Long web;
/*     */   private String isdebug;
/*     */   private boolean isdebugLike;
/*     */   private Integer lateAuth;
/*     */   private Long lateAuthTime;
/*     */   private Integer isNtf;
/* 282 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalbasQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBas() {
/*  24 */     return this.bas;
/*     */   }
/*     */   public PortalbasQuery setBas(String bas) {
/*  27 */     this.bas = bas;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setBasLike(boolean isLike) {
/*  32 */     this.basLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasname() {
/*  37 */     return this.basname;
/*     */   }
/*     */   public PortalbasQuery setBasname(String basname) {
/*  40 */     this.basname = basname;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setBasnameLike(boolean isLike) {
/*  45 */     this.basnameLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasIp() {
/*  50 */     return this.basIp;
/*     */   }
/*     */   public PortalbasQuery setBasIp(String basIp) {
/*  53 */     this.basIp = basIp;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setBasIpLike(boolean isLike) {
/*  58 */     this.basIpLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasPort() {
/*  63 */     return this.basPort;
/*     */   }
/*     */   public PortalbasQuery setBasPort(String basPort) {
/*  66 */     this.basPort = basPort;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setBasPortLike(boolean isLike) {
/*  71 */     this.basPortLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPortalVer() {
/*  76 */     return this.portalVer;
/*     */   }
/*     */   public PortalbasQuery setPortalVer(String portalVer) {
/*  79 */     this.portalVer = portalVer;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setPortalVerLike(boolean isLike) {
/*  84 */     this.portalVerLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAuthType() {
/*  89 */     return this.authType;
/*     */   }
/*     */   public PortalbasQuery setAuthType(String authType) {
/*  92 */     this.authType = authType;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setAuthTypeLike(boolean isLike) {
/*  97 */     this.authTypeLike = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSharedSecret() {
/* 102 */     return this.sharedSecret;
/*     */   }
/*     */   public PortalbasQuery setSharedSecret(String sharedSecret) {
/* 105 */     this.sharedSecret = sharedSecret;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setSharedSecretLike(boolean isLike) {
/* 110 */     this.sharedSecretLike = isLike;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasUser() {
/* 115 */     return this.basUser;
/*     */   }
/*     */   public PortalbasQuery setBasUser(String basUser) {
/* 118 */     this.basUser = basUser;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setBasUserLike(boolean isLike) {
/* 123 */     this.basUserLike = isLike;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasPwd() {
/* 128 */     return this.basPwd;
/*     */   }
/*     */   public PortalbasQuery setBasPwd(String basPwd) {
/* 131 */     this.basPwd = basPwd;
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setBasPwdLike(boolean isLike) {
/* 136 */     this.basPwdLike = isLike;
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public String getTimeoutSec() {
/* 141 */     return this.timeoutSec;
/*     */   }
/*     */   public PortalbasQuery setTimeoutSec(String timeoutSec) {
/* 144 */     this.timeoutSec = timeoutSec;
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setTimeoutSecLike(boolean isLike) {
/* 149 */     this.timeoutSecLike = isLike;
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIsPortalCheck() {
/* 154 */     return this.isPortalCheck;
/*     */   }
/*     */   public PortalbasQuery setIsPortalCheck(String isPortalCheck) {
/* 157 */     this.isPortalCheck = isPortalCheck;
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setIsPortalCheckLike(boolean isLike) {
/* 162 */     this.isPortalCheckLike = isLike;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIsOut() {
/* 167 */     return this.isOut;
/*     */   }
/*     */   public PortalbasQuery setIsOut(String isOut) {
/* 170 */     this.isOut = isOut;
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setIsOutLike(boolean isLike) {
/* 175 */     this.isOutLike = isLike;
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAuthInterface() {
/* 180 */     return this.authInterface;
/*     */   }
/*     */   public PortalbasQuery setAuthInterface(String authInterface) {
/* 183 */     this.authInterface = authInterface;
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setAuthInterfaceLike(boolean isLike) {
/* 188 */     this.authInterfaceLike = isLike;
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIsComputer() {
/* 193 */     return this.isComputer;
/*     */   }
/*     */   public PortalbasQuery setIsComputer(String isComputer) {
/* 196 */     this.isComputer = isComputer;
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setIsComputerLike(boolean isLike) {
/* 201 */     this.isComputerLike = isLike;
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getWeb() {
/* 206 */     return this.web;
/*     */   }
/*     */   public PortalbasQuery setWeb(Long web) {
/* 209 */     this.web = web;
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIsdebug() {
/* 214 */     return this.isdebug;
/*     */   }
/*     */   public PortalbasQuery setIsdebug(String isdebug) {
/* 217 */     this.isdebug = isdebug;
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery setIsdebugLike(boolean isLike) {
/* 222 */     this.isdebugLike = isLike;
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getLateAuth() {
/* 227 */     return this.lateAuth;
/*     */   }
/*     */   public PortalbasQuery setLateAuth(Integer lateAuth) {
/* 230 */     this.lateAuth = lateAuth;
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getLateAuthTime() {
/* 235 */     return this.lateAuthTime;
/*     */   }
/*     */   public PortalbasQuery setLateAuthTime(Long lateAuthTime) {
/* 238 */     this.lateAuthTime = lateAuthTime;
/* 239 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getIsNtf() {
/* 243 */     return this.isNtf;
/*     */   }
/*     */   public PortalbasQuery setIsNtf(Integer isNtf) {
/* 246 */     this.isNtf = isNtf;
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyId(boolean isAsc)
/*     */   {
/* 290 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 291 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyBas(boolean isAsc)
/*     */   {
/* 300 */     this.orderFields.add(new OrderField("bas", isAsc ? "ASC" : "DESC"));
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyBasname(boolean isAsc)
/*     */   {
/* 310 */     this.orderFields.add(new OrderField("basname", isAsc ? "ASC" : "DESC"));
/* 311 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyBasIp(boolean isAsc)
/*     */   {
/* 320 */     this.orderFields.add(new OrderField("bas_ip", isAsc ? "ASC" : "DESC"));
/* 321 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyBasPort(boolean isAsc)
/*     */   {
/* 330 */     this.orderFields.add(new OrderField("bas_port", isAsc ? "ASC" : "DESC"));
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyPortalVer(boolean isAsc)
/*     */   {
/* 340 */     this.orderFields.add(new OrderField("portalVer", isAsc ? "ASC" : "DESC"));
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyAuthType(boolean isAsc)
/*     */   {
/* 350 */     this.orderFields.add(new OrderField("authType", isAsc ? "ASC" : "DESC"));
/* 351 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbySharedSecret(boolean isAsc)
/*     */   {
/* 360 */     this.orderFields.add(new OrderField("sharedSecret", isAsc ? "ASC" : "DESC"));
/* 361 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyBasUser(boolean isAsc)
/*     */   {
/* 370 */     this.orderFields.add(new OrderField("bas_user", isAsc ? "ASC" : "DESC"));
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyBasPwd(boolean isAsc)
/*     */   {
/* 380 */     this.orderFields.add(new OrderField("bas_pwd", isAsc ? "ASC" : "DESC"));
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyTimeoutSec(boolean isAsc)
/*     */   {
/* 390 */     this.orderFields.add(new OrderField("timeoutSec", isAsc ? "ASC" : "DESC"));
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyIsPortalCheck(boolean isAsc)
/*     */   {
/* 400 */     this.orderFields.add(new OrderField("isPortalCheck", isAsc ? "ASC" : "DESC"));
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyIsOut(boolean isAsc)
/*     */   {
/* 410 */     this.orderFields.add(new OrderField("isOut", isAsc ? "ASC" : "DESC"));
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyAuthInterface(boolean isAsc)
/*     */   {
/* 420 */     this.orderFields.add(new OrderField("auth_interface", isAsc ? "ASC" : "DESC"));
/* 421 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyIsComputer(boolean isAsc)
/*     */   {
/* 430 */     this.orderFields.add(new OrderField("isComputer", isAsc ? "ASC" : "DESC"));
/* 431 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyWeb(boolean isAsc)
/*     */   {
/* 440 */     this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyIsdebug(boolean isAsc)
/*     */   {
/* 450 */     this.orderFields.add(new OrderField("isdebug", isAsc ? "ASC" : "DESC"));
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyLateAuth(boolean isAsc)
/*     */   {
/* 460 */     this.orderFields.add(new OrderField("lateAuth", isAsc ? "ASC" : "DESC"));
/* 461 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyLateAuthTime(boolean isAsc)
/*     */   {
/* 470 */     this.orderFields.add(new OrderField("lateAuthTime", isAsc ? "ASC" : "DESC"));
/* 471 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalbasQuery orderbyIsNtf(boolean isAsc)
/*     */   {
/* 480 */     this.orderFields.add(new OrderField("isNtf", isAsc ? "ASC" : "DESC"));
/* 481 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 490 */     if (fieldMap == null) {
/* 491 */       fieldMap = new HashMap();
/* 492 */       fieldMap.put("id", "id");
/* 493 */       fieldMap.put("bas", "bas");
/* 494 */       fieldMap.put("basname", "basname");
/* 495 */       fieldMap.put("basIp", "bas_ip");
/* 496 */       fieldMap.put("basPort", "bas_port");
/* 497 */       fieldMap.put("portalVer", "portalVer");
/* 498 */       fieldMap.put("authType", "authType");
/* 499 */       fieldMap.put("sharedSecret", "sharedSecret");
/* 500 */       fieldMap.put("basUser", "bas_user");
/* 501 */       fieldMap.put("basPwd", "bas_pwd");
/* 502 */       fieldMap.put("timeoutSec", "timeoutSec");
/* 503 */       fieldMap.put("isPortalCheck", "isPortalCheck");
/* 504 */       fieldMap.put("isOut", "isOut");
/* 505 */       fieldMap.put("authInterface", "auth_interface");
/* 506 */       fieldMap.put("isComputer", "isComputer");
/* 507 */       fieldMap.put("web", "web");
/* 508 */       fieldMap.put("isdebug", "isdebug");
/* 509 */       fieldMap.put("lateAuth", "lateAuth");
/* 510 */       fieldMap.put("lateAuthTime", "lateAuthTime");
/* 511 */       fieldMap.put("isNtf", "isNtf");
/*     */     }
/* 513 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 517 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 520 */     if (fields == null)
/* 521 */       return;
/* 522 */     String[] array = fields.split(",");
/* 523 */     StringBuffer buffer = new StringBuffer();
/* 524 */     for (String field : array) {
/* 525 */       if (getFieldSet().containsKey(field)) {
/* 526 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 527 */           .append(field).append(" ,");
/*     */       }
/* 529 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 530 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 531 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 534 */     if (buffer.length() != 0)
/* 535 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 537 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 256 */       this.fieldName = fieldName;
/* 257 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 263 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 266 */       this.fieldName = fieldName;
/* 267 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 270 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 273 */       this.order = order;
/* 274 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalbasQuery
 * JD-Core Version:    0.6.2
 */