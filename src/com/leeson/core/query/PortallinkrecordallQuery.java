/*     */ package com.leeson.core.query;
/*     */ 
/*     */ import com.leeson.common.base.BaseQuery;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PortallinkrecordallQuery extends BaseQuery
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
/*     */   private String methodtype;
/*     */   private boolean methodtypeLike;
/*     */   private String mac;
/*     */   private boolean macLike;
/*     */   private String basname;
/*     */   private boolean basnameLike;
/*     */   private String ssid;
/*     */   private boolean ssidLike;
/*     */   private String apmac;
/*     */   private boolean apmacLike;
/*     */   private String auto;
/*     */   private boolean autoLike;
/*     */   private String agent;
/*     */   private boolean agentLike;
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
/* 376 */   private List<OrderField> orderFields = new ArrayList();
/*     */   private String fields;
/*     */   private static Map<String, String> fieldMap;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  16 */     return this.id;
/*     */   }
/*     */   public PortallinkrecordallQuery setId(Long id) {
/*  19 */     this.id = id;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/*  24 */     return this.ip;
/*     */   }
/*     */   public PortallinkrecordallQuery setIp(String ip) {
/*  27 */     this.ip = ip;
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setIpLike(boolean isLike) {
/*  32 */     this.ipLike = isLike;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasip() {
/*  37 */     return this.basip;
/*     */   }
/*     */   public PortallinkrecordallQuery setBasip(String basip) {
/*  40 */     this.basip = basip;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setBasipLike(boolean isLike) {
/*  45 */     this.basipLike = isLike;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public String getLoginName() {
/*  50 */     return this.loginName;
/*     */   }
/*     */   public PortallinkrecordallQuery setLoginName(String loginName) {
/*  53 */     this.loginName = loginName;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setLoginNameLike(boolean isLike) {
/*  58 */     this.loginNameLike = isLike;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  63 */     return this.state;
/*     */   }
/*     */   public PortallinkrecordallQuery setState(String state) {
/*  66 */     this.state = state;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setStateLike(boolean isLike) {
/*  71 */     this.stateLike = isLike;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getStartDate() {
/*  76 */     return this.startDate;
/*     */   }
/*     */   public PortallinkrecordallQuery setStartDate(Date startDate) {
/*  79 */     this.startDate = startDate;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public Date getEndDate() {
/*  84 */     return this.endDate;
/*     */   }
/*     */   public PortallinkrecordallQuery setEndDate(Date endDate) {
/*  87 */     this.endDate = endDate;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/*  92 */     return this.time;
/*     */   }
/*     */   public PortallinkrecordallQuery setTime(Long time) {
/*  95 */     this.time = time;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getIns() {
/* 100 */     return this.ins;
/*     */   }
/*     */   public PortallinkrecordallQuery setIns(Long ins) {
/* 103 */     this.ins = ins;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOuts() {
/* 108 */     return this.outs;
/*     */   }
/*     */   public PortallinkrecordallQuery setOuts(Long outs) {
/* 111 */     this.outs = outs;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   public Long getOctets() {
/* 116 */     return this.octets;
/*     */   }
/*     */   public PortallinkrecordallQuery setOctets(Long octets) {
/* 119 */     this.octets = octets;
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public String getMethodtype() {
/* 124 */     return this.methodtype;
/*     */   }
/*     */   public PortallinkrecordallQuery setMethodtype(String methodtype) {
/* 127 */     this.methodtype = methodtype;
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setMethodtypeLike(boolean isLike) {
/* 132 */     this.methodtypeLike = isLike;
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */   public String getMac() {
/* 137 */     return this.mac;
/*     */   }
/*     */   public PortallinkrecordallQuery setMac(String mac) {
/* 140 */     this.mac = mac;
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setMacLike(boolean isLike) {
/* 145 */     this.macLike = isLike;
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public String getBasname() {
/* 150 */     return this.basname;
/*     */   }
/*     */   public PortallinkrecordallQuery setBasname(String basname) {
/* 153 */     this.basname = basname;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setBasnameLike(boolean isLike) {
/* 158 */     this.basnameLike = isLike;
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSsid() {
/* 163 */     return this.ssid;
/*     */   }
/*     */   public PortallinkrecordallQuery setSsid(String ssid) {
/* 166 */     this.ssid = ssid;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setSsidLike(boolean isLike) {
/* 171 */     this.ssidLike = isLike;
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   public String getApmac() {
/* 176 */     return this.apmac;
/*     */   }
/*     */   public PortallinkrecordallQuery setApmac(String apmac) {
/* 179 */     this.apmac = apmac;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setApmacLike(boolean isLike) {
/* 184 */     this.apmacLike = isLike;
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAuto() {
/* 189 */     return this.auto;
/*     */   }
/*     */   public PortallinkrecordallQuery setAuto(String auto) {
/* 192 */     this.auto = auto;
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setAutoLike(boolean isLike) {
/* 197 */     this.autoLike = isLike;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   public String getAgent() {
/* 202 */     return this.agent;
/*     */   }
/*     */   public PortallinkrecordallQuery setAgent(String agent) {
/* 205 */     this.agent = agent;
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setAgentLike(boolean isLike) {
/* 210 */     this.agentLike = isLike;
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx1() {
/* 215 */     return this.ex1;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx1(String ex1) {
/* 218 */     this.ex1 = ex1;
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx1Like(boolean isLike) {
/* 223 */     this.ex1Like = isLike;
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx2() {
/* 228 */     return this.ex2;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx2(String ex2) {
/* 231 */     this.ex2 = ex2;
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx2Like(boolean isLike) {
/* 236 */     this.ex2Like = isLike;
/* 237 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx3() {
/* 241 */     return this.ex3;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx3(String ex3) {
/* 244 */     this.ex3 = ex3;
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx3Like(boolean isLike) {
/* 249 */     this.ex3Like = isLike;
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx4() {
/* 254 */     return this.ex4;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx4(String ex4) {
/* 257 */     this.ex4 = ex4;
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx4Like(boolean isLike) {
/* 262 */     this.ex4Like = isLike;
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx5() {
/* 267 */     return this.ex5;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx5(String ex5) {
/* 270 */     this.ex5 = ex5;
/* 271 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx5Like(boolean isLike) {
/* 275 */     this.ex5Like = isLike;
/* 276 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx6() {
/* 280 */     return this.ex6;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx6(String ex6) {
/* 283 */     this.ex6 = ex6;
/* 284 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx6Like(boolean isLike) {
/* 288 */     this.ex6Like = isLike;
/* 289 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx7() {
/* 293 */     return this.ex7;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx7(String ex7) {
/* 296 */     this.ex7 = ex7;
/* 297 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx7Like(boolean isLike) {
/* 301 */     this.ex7Like = isLike;
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx8() {
/* 306 */     return this.ex8;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx8(String ex8) {
/* 309 */     this.ex8 = ex8;
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx8Like(boolean isLike) {
/* 314 */     this.ex8Like = isLike;
/* 315 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx9() {
/* 319 */     return this.ex9;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx9(String ex9) {
/* 322 */     this.ex9 = ex9;
/* 323 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx9Like(boolean isLike) {
/* 327 */     this.ex9Like = isLike;
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */   public String getEx10() {
/* 332 */     return this.ex10;
/*     */   }
/*     */   public PortallinkrecordallQuery setEx10(String ex10) {
/* 335 */     this.ex10 = ex10;
/* 336 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery setEx10Like(boolean isLike) {
/* 340 */     this.ex10Like = isLike;
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyId(boolean isAsc)
/*     */   {
/* 384 */     this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
/* 385 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyIp(boolean isAsc)
/*     */   {
/* 394 */     this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
/* 395 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyBasip(boolean isAsc)
/*     */   {
/* 404 */     this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
/* 405 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyLoginName(boolean isAsc)
/*     */   {
/* 414 */     this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyState(boolean isAsc)
/*     */   {
/* 424 */     this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
/* 425 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyStartDate(boolean isAsc)
/*     */   {
/* 434 */     this.orderFields.add(new OrderField("startDate", isAsc ? "ASC" : "DESC"));
/* 435 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEndDate(boolean isAsc)
/*     */   {
/* 444 */     this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
/* 445 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyTime(boolean isAsc)
/*     */   {
/* 454 */     this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
/* 455 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyIns(boolean isAsc)
/*     */   {
/* 464 */     this.orderFields.add(new OrderField("ins", isAsc ? "ASC" : "DESC"));
/* 465 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyOuts(boolean isAsc)
/*     */   {
/* 474 */     this.orderFields.add(new OrderField("outs", isAsc ? "ASC" : "DESC"));
/* 475 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyOctets(boolean isAsc)
/*     */   {
/* 484 */     this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
/* 485 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyMethodtype(boolean isAsc)
/*     */   {
/* 494 */     this.orderFields.add(new OrderField("methodtype", isAsc ? "ASC" : "DESC"));
/* 495 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyMac(boolean isAsc)
/*     */   {
/* 504 */     this.orderFields.add(new OrderField("mac", isAsc ? "ASC" : "DESC"));
/* 505 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyBasname(boolean isAsc)
/*     */   {
/* 514 */     this.orderFields.add(new OrderField("basname", isAsc ? "ASC" : "DESC"));
/* 515 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbySsid(boolean isAsc)
/*     */   {
/* 524 */     this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
/* 525 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyApmac(boolean isAsc)
/*     */   {
/* 534 */     this.orderFields.add(new OrderField("apmac", isAsc ? "ASC" : "DESC"));
/* 535 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyAuto(boolean isAsc)
/*     */   {
/* 544 */     this.orderFields.add(new OrderField("auto", isAsc ? "ASC" : "DESC"));
/* 545 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyAgent(boolean isAsc)
/*     */   {
/* 554 */     this.orderFields.add(new OrderField("agent", isAsc ? "ASC" : "DESC"));
/* 555 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx1(boolean isAsc)
/*     */   {
/* 564 */     this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
/* 565 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx2(boolean isAsc)
/*     */   {
/* 574 */     this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
/* 575 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx3(boolean isAsc)
/*     */   {
/* 584 */     this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
/* 585 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx4(boolean isAsc)
/*     */   {
/* 594 */     this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
/* 595 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx5(boolean isAsc)
/*     */   {
/* 604 */     this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
/* 605 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx6(boolean isAsc)
/*     */   {
/* 614 */     this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
/* 615 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx7(boolean isAsc)
/*     */   {
/* 624 */     this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
/* 625 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx8(boolean isAsc)
/*     */   {
/* 634 */     this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
/* 635 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx9(boolean isAsc)
/*     */   {
/* 644 */     this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
/* 645 */     return this;
/*     */   }
/*     */ 
/*     */   public PortallinkrecordallQuery orderbyEx10(boolean isAsc)
/*     */   {
/* 654 */     this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
/* 655 */     return this;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getFieldSet()
/*     */   {
/* 664 */     if (fieldMap == null) {
/* 665 */       fieldMap = new HashMap();
/* 666 */       fieldMap.put("id", "id");
/* 667 */       fieldMap.put("ip", "ip");
/* 668 */       fieldMap.put("basip", "basip");
/* 669 */       fieldMap.put("loginName", "loginName");
/* 670 */       fieldMap.put("state", "state");
/* 671 */       fieldMap.put("startDate", "startDate");
/* 672 */       fieldMap.put("endDate", "endDate");
/* 673 */       fieldMap.put("time", "time");
/* 674 */       fieldMap.put("ins", "ins");
/* 675 */       fieldMap.put("outs", "outs");
/* 676 */       fieldMap.put("octets", "octets");
/* 677 */       fieldMap.put("methodtype", "methodtype");
/* 678 */       fieldMap.put("mac", "mac");
/* 679 */       fieldMap.put("basname", "basname");
/* 680 */       fieldMap.put("ssid", "ssid");
/* 681 */       fieldMap.put("apmac", "apmac");
/* 682 */       fieldMap.put("auto", "auto");
/* 683 */       fieldMap.put("agent", "agent");
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
/*     */     }
/* 695 */     return fieldMap;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 699 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 702 */     if (fields == null)
/* 703 */       return;
/* 704 */     String[] array = fields.split(",");
/* 705 */     StringBuffer buffer = new StringBuffer();
/* 706 */     for (String field : array) {
/* 707 */       if (getFieldSet().containsKey(field)) {
/* 708 */         buffer.append((String)getFieldSet().get(field)).append(" as ")
/* 709 */           .append(field).append(" ,");
/*     */       }
/* 711 */       if (getFieldSet().containsKey("`" + field + "`")) {
/* 712 */         buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
/* 713 */           .append(field).append(" ,");
/*     */       }
/*     */     }
/* 716 */     if (buffer.length() != 0)
/* 717 */       this.fields = buffer.substring(0, buffer.length() - 1);
/*     */     else
/* 719 */       this.fields = " 1 ";
/*     */   }
/*     */ 
/*     */   public class OrderField
/*     */   {
/*     */     private String fieldName;
/*     */     private String order;
/*     */ 
/*     */     public OrderField(String fieldName, String order)
/*     */     {
/* 350 */       this.fieldName = fieldName;
/* 351 */       this.order = order;
/*     */     }
/*     */ 
/*     */     public String getFieldName()
/*     */     {
/* 357 */       return this.fieldName;
/*     */     }
/*     */     public OrderField setFieldName(String fieldName) {
/* 360 */       this.fieldName = fieldName;
/* 361 */       return this;
/*     */     }
/*     */     public String getOrder() {
/* 364 */       return this.order;
/*     */     }
/*     */     public OrderField setOrder(String order) {
/* 367 */       this.order = order;
/* 368 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.PortallinkrecordallQuery
 * JD-Core Version:    0.6.2
 */