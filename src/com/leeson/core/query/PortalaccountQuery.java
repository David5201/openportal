/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortalaccountQuery extends BaseQuery
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
/*     */   private Date date;
/*     */   private Long time;
/*     */   private Long octets;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private String idnumber;
/*     */   private boolean idnumberLike;
/*     */   private String address;
/*     */   private boolean addressLike;
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
/* 371 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortalaccountQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getLoginName() {
/*  24 */     return this.loginName;
/*     */   }
/*     */   public PortalaccountQuery setLoginName(String loginName) {
/*  27 */     this.loginName = loginName;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setLoginNameLike(boolean isLike) {
/*  32 */     this.loginNameLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/*  37 */     return this.password;
/*     */   }
/*     */   public PortalaccountQuery setPassword(String password) {
/*  40 */     this.password = password;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setPasswordLike(boolean isLike) {
/*  45 */     this.passwordLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */   public PortalaccountQuery setName(String name) {
/*  53 */     this.name = name;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setNameLike(boolean isLike) {
/*  58 */     this.nameLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getGender() {
/*  63 */     return this.gender;
/*     */   }
/*     */   public PortalaccountQuery setGender(String gender) {
/*  66 */     this.gender = gender;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setGenderLike(boolean isLike) {
/*  71 */     this.genderLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public String getPhoneNumber() {
/*  76 */     return this.phoneNumber;
/*     */   }
/*     */   public PortalaccountQuery setPhoneNumber(String phoneNumber) {
/*  79 */     this.phoneNumber = phoneNumber;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setPhoneNumberLike(boolean isLike) {
/*  84 */     this.phoneNumberLike = isLike;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  89 */     return this.email;
/*     */   }
/*     */   public PortalaccountQuery setEmail(String email) {
/*  92 */     this.email = email;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEmailLike(boolean isLike) {
/*  97 */     this.emailLike = isLike;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 102 */     return this.description;
/*     */   }
/*     */   public PortalaccountQuery setDescription(String description) {
/* 105 */     this.description = description;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setDescriptionLike(boolean isLike) {
/* 110 */     this.descriptionLike = isLike;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getDate() {
/* 115 */     return this.date;
/*     */   }
/*     */   public PortalaccountQuery setDate(Date date) {
/* 118 */     this.date = date;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/* 123 */     return this.time;
/*     */   }
/*     */   public PortalaccountQuery setTime(Long time) {
/* 126 */     this.time = time;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOctets() {
/* 131 */     return this.octets;
/*     */   }
/*     */   public PortalaccountQuery setOctets(Long octets) {
/* 134 */     this.octets = octets;
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/* 139 */     return this.state;
/*     */   }
/*     */   public PortalaccountQuery setState(String state) {
/* 142 */     this.state = state;
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setStateLike(boolean isLike) {
/* 147 */     this.stateLike = isLike;
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIdnumber() {
/* 152 */     return this.idnumber;
/*     */   }
/*     */   public PortalaccountQuery setIdnumber(String idnumber) {
/* 155 */     this.idnumber = idnumber;
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setIdnumberLike(boolean isLike) {
/* 160 */     this.idnumberLike = isLike;
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAddress() {
/* 165 */     return this.address;
/*     */   }
/*     */   public PortalaccountQuery setAddress(String address) {
/* 168 */     this.address = address;
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setAddressLike(boolean isLike) {
/* 173 */     this.addressLike = isLike;
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getSpeed() {
/* 178 */     return this.speed;
/*     */   }
/*     */   public PortalaccountQuery setSpeed(Long speed) {
/* 181 */     this.speed = speed;
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimit() {
/* 186 */     return this.maclimit;
/*     */   }
/*     */   public PortalaccountQuery setMaclimit(Integer maclimit) {
/* 189 */     this.maclimit = maclimit;
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getMaclimitcount() {
/* 194 */     return this.maclimitcount;
/*     */   }
/*     */   public PortalaccountQuery setMaclimitcount(Integer maclimitcount) {
/* 197 */     this.maclimitcount = maclimitcount;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAutologin() {
/* 202 */     return this.autologin;
/*     */   }
/*     */   public PortalaccountQuery setAutologin(Integer autologin) {
/* 205 */     this.autologin = autologin;
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/* 210 */     return this.ex1;
/*     */   }
/*     */   public PortalaccountQuery setEx1(String ex1) {
/* 213 */     this.ex1 = ex1;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx1Like(boolean isLike) {
/* 218 */     this.ex1Like = isLike;
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 223 */     return this.ex2;
/*     */   }
/*     */   public PortalaccountQuery setEx2(String ex2) {
/* 226 */     this.ex2 = ex2;
/* 227 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx2Like(boolean isLike) {
/* 231 */     this.ex2Like = isLike;
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 236 */     return this.ex3;
/*     */   }
/*     */   public PortalaccountQuery setEx3(String ex3) {
/* 239 */     this.ex3 = ex3;
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx3Like(boolean isLike) {
/* 244 */     this.ex3Like = isLike;
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 249 */     return this.ex4;
/*     */   }
/*     */   public PortalaccountQuery setEx4(String ex4) {
/* 252 */     this.ex4 = ex4;
/* 253 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx4Like(boolean isLike) {
/* 257 */     this.ex4Like = isLike;
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 262 */     return this.ex5;
/*     */   }
/*     */   public PortalaccountQuery setEx5(String ex5) {
/* 265 */     this.ex5 = ex5;
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx5Like(boolean isLike) {
/* 270 */     this.ex5Like = isLike;
/* 271 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 275 */     return this.ex6;
/*     */   }
/*     */   public PortalaccountQuery setEx6(String ex6) {
/* 278 */     this.ex6 = ex6;
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx6Like(boolean isLike) {
/* 283 */     this.ex6Like = isLike;
/* 284 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 288 */     return this.ex7;
/*     */   }
/*     */   public PortalaccountQuery setEx7(String ex7) {
/* 291 */     this.ex7 = ex7;
/* 292 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx7Like(boolean isLike) {
/* 296 */     this.ex7Like = isLike;
/* 297 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 301 */     return this.ex8;
/*     */   }
/*     */   public PortalaccountQuery setEx8(String ex8) {
/* 304 */     this.ex8 = ex8;
/* 305 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx8Like(boolean isLike) {
/* 309 */     this.ex8Like = isLike;
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 314 */     return this.ex9;
/*     */   }
/*     */   public PortalaccountQuery setEx9(String ex9) {
/* 317 */     this.ex9 = ex9;
/* 318 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx9Like(boolean isLike) {
/* 322 */     this.ex9Like = isLike;
/* 323 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 327 */     return this.ex10;
/*     */   }
/*     */   public PortalaccountQuery setEx10(String ex10) {
/* 330 */     this.ex10 = ex10;
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery setEx10Like(boolean isLike) {
/* 335 */     this.ex10Like = isLike;
/* 336 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyId(boolean isAsc)
/*     */   {
/* 379 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 380 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyLoginName(boolean isAsc)
/*     */   {
/* 389 */     this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
/* 390 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyPassword(boolean isAsc)
/*     */   {
/* 399 */     this.orderFields.add(new OrderField("password", isAsc ? "ASC" : "DESC"));
/* 400 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyName(boolean isAsc)
/*     */   {
/* 409 */     this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
/* 410 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyGender(boolean isAsc)
/*     */   {
/* 419 */     this.orderFields.add(new OrderField("gender", isAsc ? "ASC" : "DESC"));
/* 420 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyPhoneNumber(boolean isAsc)
/*     */   {
/* 429 */     this.orderFields.add(new OrderField("phoneNumber", isAsc ? "ASC" : "DESC"));
/* 430 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEmail(boolean isAsc)
/*     */   {
/* 439 */     this.orderFields.add(new OrderField("email", isAsc ? "ASC" : "DESC"));
/* 440 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyDescription(boolean isAsc)
/*     */   {
/* 449 */     this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
/* 450 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyDate(boolean isAsc)
/*     */   {
/* 459 */     this.orderFields.add(new OrderField("date", isAsc ? "ASC" : "DESC"));
/* 460 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyTime(boolean isAsc)
/*     */   {
/* 469 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/* 470 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyOctets(boolean isAsc)
/*     */   {
/* 479 */     this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
/* 480 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyState(boolean isAsc)
/*     */   {
/* 489 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 490 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyIdnumber(boolean isAsc)
/*     */   {
/* 499 */     this.orderFields.add(new OrderField("idnumber", isAsc ? "ASC" : "DESC"));
/* 500 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyAddress(boolean isAsc)
/*     */   {
/* 509 */     this.orderFields.add(new OrderField("address", isAsc ? "ASC" : "DESC"));
/* 510 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbySpeed(boolean isAsc)
/*     */   {
/* 519 */     this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
/* 520 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyMaclimit(boolean isAsc)
/*     */   {
/* 529 */     this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
/* 530 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyMaclimitcount(boolean isAsc)
/*     */   {
/* 539 */     this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
/* 540 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyAutologin(boolean isAsc)
/*     */   {
/* 549 */     this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
/* 550 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 559 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 560 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 569 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 570 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 579 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 580 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 589 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 590 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 599 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 600 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 609 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 610 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 619 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 620 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 629 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 630 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 639 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 640 */     return this;
/*     */   }
/*     */ 
/*     */   public PortalaccountQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 649 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 650 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 659 */     if (fieldMap == null) {
/* 660 */       fieldMap = new HashMap();
/* 661 */       fieldMap.put("id", "id");
/* 662 */       fieldMap.put("loginName", "loginName");
/* 663 */       fieldMap.put("password", "password");
/* 664 */       fieldMap.put("name", "name");
/* 665 */       fieldMap.put("gender", "gender");
/* 666 */       fieldMap.put("phoneNumber", "phoneNumber");
/* 667 */       fieldMap.put("email", "email");
/* 668 */       fieldMap.put("description", "description");
/* 669 */       fieldMap.put("date", "date");
/* 670 */       fieldMap.put("time", "time");
/* 671 */       fieldMap.put("octets", "octets");
/* 672 */       fieldMap.put("state", "state");
/* 673 */       fieldMap.put("idnumber", "idnumber");
/* 674 */       fieldMap.put("address", "address");
/* 675 */       fieldMap.put("speed", "speed");
/* 676 */       fieldMap.put("maclimit", "maclimit");
/* 677 */       fieldMap.put("maclimitcount", "maclimitcount");
/* 678 */       fieldMap.put("autologin", "autologin");
/* 679 */       fieldMap.put("ex1", "ex1");
/* 680 */       fieldMap.put("ex2", "ex2");
/* 681 */       fieldMap.put("ex3", "ex3");
/* 682 */       fieldMap.put("ex4", "ex4");
/* 683 */       fieldMap.put("ex5", "ex5");
/* 684 */       fieldMap.put("ex6", "ex6");
/* 685 */       fieldMap.put("ex7", "ex7");
/* 686 */       fieldMap.put("ex8", "ex8");
/* 687 */       fieldMap.put("ex9", "ex9");
/* 688 */       fieldMap.put("ex10", "ex10");
/*     */     }
/* 690 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 694 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 697 */     if (fields == null)
/* 698 */       return;
/* 699 */     String[] array = fields.split(",");
/* 700 */     StringBuffer buffer = new StringBuffer();
/* 701 */     for (String field : array) {
/* 702 */       if (getFieldSet().containsKey(field)) {
/* 703 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 704 */           .append(field).append(" ,");
/*     */       }
/* 706 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 707 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 708 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 711 */     if (buffer.length() != 0)
/* 712 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 714 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 345 */       this.fieldName = fieldName;
/* 346 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 352 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 355 */       this.fieldName = fieldName;
/* 356 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 359 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 362 */       this.order = order;
/* 363 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortalaccountQuery
 * JD-Core Version:    0.6.2
 */