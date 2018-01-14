/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortallinkrecordQuery extends BaseQuery
/*     */ {
/*     */   private Long id;
/*     */   private String ip;
/*     */   private boolean ipLike;
/*     */   private String basip;
/*     */   private boolean basipLike;
/*     */   private String loginName;
/*     */   private boolean loginNameLike;
/*     */   private String state;
/*     */   private boolean stateLike;
/*     */   private Date startDate;
/*     */   private Date endDate;
/*     */   private Long time;
/*     */   private Long ins;
/*     */   private Long outs;
/*     */   private Long octets;
/*     */   private Long uid;
/*     */   private Integer userDel;
/*     */   private Integer accountDel;
/*     */   private String mac;
/*     */   private boolean macLike;
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
/* 322 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortallinkrecordQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/*  24 */     return this.ip;
/*     */   }
/*     */   public PortallinkrecordQuery setIp(String ip) {
/*  27 */     this.ip = ip;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setIpLike(boolean isLike) {
/*  32 */     this.ipLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasip() {
/*  37 */     return this.basip;
/*     */   }
/*     */   public PortallinkrecordQuery setBasip(String basip) {
/*  40 */     this.basip = basip;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setBasipLike(boolean isLike) {
/*  45 */     this.basipLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getLoginName() {
/*  50 */     return this.loginName;
/*     */   }
/*     */   public PortallinkrecordQuery setLoginName(String loginName) {
/*  53 */     this.loginName = loginName;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setLoginNameLike(boolean isLike) {
/*  58 */     this.loginNameLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  63 */     return this.state;
/*     */   }
/*     */   public PortallinkrecordQuery setState(String state) {
/*  66 */     this.state = state;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setStateLike(boolean isLike) {
/*  71 */     this.stateLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getStartDate() {
/*  76 */     return this.startDate;
/*     */   }
/*     */   public PortallinkrecordQuery setStartDate(Date startDate) {
/*  79 */     this.startDate = startDate;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getEndDate() {
/*  84 */     return this.endDate;
/*     */   }
/*     */   public PortallinkrecordQuery setEndDate(Date endDate) {
/*  87 */     this.endDate = endDate;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/*  92 */     return this.time;
/*     */   }
/*     */   public PortallinkrecordQuery setTime(Long time) {
/*  95 */     this.time = time;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getIns() {
/* 100 */     return this.ins;
/*     */   }
/*     */   public PortallinkrecordQuery setIns(Long ins) {
/* 103 */     this.ins = ins;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOuts() {
/* 108 */     return this.outs;
/*     */   }
/*     */   public PortallinkrecordQuery setOuts(Long outs) {
/* 111 */     this.outs = outs;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOctets() {
/* 116 */     return this.octets;
/*     */   }
/*     */   public PortallinkrecordQuery setOctets(Long octets) {
/* 119 */     this.octets = octets;
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getUid() {
/* 124 */     return this.uid;
/*     */   }
/*     */   public PortallinkrecordQuery setUid(Long uid) {
/* 127 */     this.uid = uid;
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getUserDel() {
/* 132 */     return this.userDel;
/*     */   }
/*     */   public PortallinkrecordQuery setUserDel(Integer userDel) {
/* 135 */     this.userDel = userDel;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public Integer getAccountDel() {
/* 140 */     return this.accountDel;
/*     */   }
/*     */   public PortallinkrecordQuery setAccountDel(Integer accountDel) {
/* 143 */     this.accountDel = accountDel;
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   public String getMac() {
/* 148 */     return this.mac;
/*     */   }
/*     */   public PortallinkrecordQuery setMac(String mac) {
/* 151 */     this.mac = mac;
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setMacLike(boolean isLike) {
/* 156 */     this.macLike = isLike;
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/* 161 */     return this.ex1;
/*     */   }
/*     */   public PortallinkrecordQuery setEx1(String ex1) {
/* 164 */     this.ex1 = ex1;
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx1Like(boolean isLike) {
/* 169 */     this.ex1Like = isLike;
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 174 */     return this.ex2;
/*     */   }
/*     */   public PortallinkrecordQuery setEx2(String ex2) {
/* 177 */     this.ex2 = ex2;
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx2Like(boolean isLike) {
/* 182 */     this.ex2Like = isLike;
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 187 */     return this.ex3;
/*     */   }
/*     */   public PortallinkrecordQuery setEx3(String ex3) {
/* 190 */     this.ex3 = ex3;
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx3Like(boolean isLike) {
/* 195 */     this.ex3Like = isLike;
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 200 */     return this.ex4;
/*     */   }
/*     */   public PortallinkrecordQuery setEx4(String ex4) {
/* 203 */     this.ex4 = ex4;
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx4Like(boolean isLike) {
/* 208 */     this.ex4Like = isLike;
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 213 */     return this.ex5;
/*     */   }
/*     */   public PortallinkrecordQuery setEx5(String ex5) {
/* 216 */     this.ex5 = ex5;
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx5Like(boolean isLike) {
/* 221 */     this.ex5Like = isLike;
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 226 */     return this.ex6;
/*     */   }
/*     */   public PortallinkrecordQuery setEx6(String ex6) {
/* 229 */     this.ex6 = ex6;
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx6Like(boolean isLike) {
/* 234 */     this.ex6Like = isLike;
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 239 */     return this.ex7;
/*     */   }
/*     */   public PortallinkrecordQuery setEx7(String ex7) {
/* 242 */     this.ex7 = ex7;
/* 243 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx7Like(boolean isLike) {
/* 247 */     this.ex7Like = isLike;
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 252 */     return this.ex8;
/*     */   }
/*     */   public PortallinkrecordQuery setEx8(String ex8) {
/* 255 */     this.ex8 = ex8;
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx8Like(boolean isLike) {
/* 260 */     this.ex8Like = isLike;
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 265 */     return this.ex9;
/*     */   }
/*     */   public PortallinkrecordQuery setEx9(String ex9) {
/* 268 */     this.ex9 = ex9;
/* 269 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx9Like(boolean isLike) {
/* 273 */     this.ex9Like = isLike;
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 278 */     return this.ex10;
/*     */   }
/*     */   public PortallinkrecordQuery setEx10(String ex10) {
/* 281 */     this.ex10 = ex10;
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery setEx10Like(boolean isLike) {
/* 286 */     this.ex10Like = isLike;
/* 287 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyId(boolean isAsc)
/*     */   {
/* 330 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyIp(boolean isAsc)
/*     */   {
/* 340 */     this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyBasip(boolean isAsc)
/*     */   {
/* 350 */     this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
/* 351 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyLoginName(boolean isAsc)
/*     */   {
/* 360 */     this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
/* 361 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyState(boolean isAsc)
/*     */   {
/* 370 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyStartDate(boolean isAsc)
/*     */   {
/* 380 */     this.orderFields.add(new OrderField("startDate", isAsc ? "ASC" : "DESC"));
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEndDate(boolean isAsc)
/*     */   {
/* 390 */     this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyTime(boolean isAsc)
/*     */   {
/* 400 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyIns(boolean isAsc)
/*     */   {
/* 410 */     this.orderFields.add(new OrderField("ins", isAsc ? "ASC" : "DESC"));
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyOuts(boolean isAsc)
/*     */   {
/* 420 */     this.orderFields.add(new OrderField("outs", isAsc ? "ASC" : "DESC"));
/* 421 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyOctets(boolean isAsc)
/*     */   {
/* 430 */     this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
/* 431 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyUid(boolean isAsc)
/*     */   {
/* 440 */     this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyUserDel(boolean isAsc)
/*     */   {
/* 450 */     this.orderFields.add(new OrderField("userDel", isAsc ? "ASC" : "DESC"));
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyAccountDel(boolean isAsc)
/*     */   {
/* 460 */     this.orderFields.add(new OrderField("accountDel", isAsc ? "ASC" : "DESC"));
/* 461 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyMac(boolean isAsc)
/*     */   {
/* 470 */     this.orderFields.add(new OrderField("mac", isAsc ? "ASC" : "DESC"));
/* 471 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 480 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 481 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 490 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 491 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 500 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 501 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 510 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 511 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 520 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 521 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 530 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 531 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 540 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 541 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 550 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 551 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 560 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 561 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 570 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 571 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 580 */     if (fieldMap == null) {
/* 581 */       fieldMap = new HashMap();
/* 582 */       fieldMap.put("id", "id");
/* 583 */       fieldMap.put("ip", "ip");
/* 584 */       fieldMap.put("basip", "basip");
/* 585 */       fieldMap.put("loginName", "loginName");
/* 586 */       fieldMap.put("state", "state");
/* 587 */       fieldMap.put("startDate", "startDate");
/* 588 */       fieldMap.put("endDate", "endDate");
/* 589 */       fieldMap.put("time", "time");
/* 590 */       fieldMap.put("ins", "ins");
/* 591 */       fieldMap.put("outs", "outs");
/* 592 */       fieldMap.put("octets", "octets");
/* 593 */       fieldMap.put("uid", "uid");
/* 594 */       fieldMap.put("userDel", "userDel");
/* 595 */       fieldMap.put("accountDel", "accountDel");
/* 596 */       fieldMap.put("mac", "mac");
/* 597 */       fieldMap.put("ex1", "ex1");
/* 598 */       fieldMap.put("ex2", "ex2");
/* 599 */       fieldMap.put("ex3", "ex3");
/* 600 */       fieldMap.put("ex4", "ex4");
/* 601 */       fieldMap.put("ex5", "ex5");
/* 602 */       fieldMap.put("ex6", "ex6");
/* 603 */       fieldMap.put("ex7", "ex7");
/* 604 */       fieldMap.put("ex8", "ex8");
/* 605 */       fieldMap.put("ex9", "ex9");
/* 606 */       fieldMap.put("ex10", "ex10");
/*     */     }
/* 608 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 612 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 615 */     if (fields == null)
/* 616 */       return;
/* 617 */     String[] array = fields.split(",");
/* 618 */     StringBuffer buffer = new StringBuffer();
/* 619 */     for (String field : array) {
/* 620 */       if (getFieldSet().containsKey(field)) {
/* 621 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 622 */           .append(field).append(" ,");
/*     */       }
/* 624 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 625 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 626 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 629 */     if (buffer.length() != 0)
/* 630 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 632 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 296 */       this.fieldName = fieldName;
/* 297 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 303 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 306 */       this.fieldName = fieldName;
/* 307 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 310 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 313 */       this.order = order;
/* 314 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortallinkrecordQuery
 * JD-Core Version:    0.6.2
 */