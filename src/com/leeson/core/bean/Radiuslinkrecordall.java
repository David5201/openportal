/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Radiuslinkrecordall
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long id;
/*     */   private String nasip;
/*     */   private String sourceip;
/*     */   private String userip;
/*     */   private String callingstationid;
/*     */   private String name;
/*     */   private String state;
/*     */   private Date startDate;
/*     */   private Date endDate;
/*     */   private Long time;
/*     */   private Long ins;
/*     */   private Long outs;
/*     */   private Long octets;
/*     */   private String acctsessionid;
/*     */   private String ex1;
/*     */   private String ex2;
/*     */   private String ex3;
/*     */   private String ex4;
/*     */   private String ex5;
/*     */   private String ex6;
/*     */   private String ex7;
/*     */   private String ex8;
/*     */   private String ex9;
/*     */   private String ex10;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  42 */     return this.id;
/*     */   }
/*     */   public void setId(Long id) {
/*  45 */     this.id = id;
/*     */   }
/*     */   public String getNasip() {
/*  48 */     return this.nasip;
/*     */   }
/*     */   public void setNasip(String nasip) {
/*  51 */     this.nasip = nasip;
/*     */   }
/*     */   public String getSourceip() {
/*  54 */     return this.sourceip;
/*     */   }
/*     */   public void setSourceip(String sourceip) {
/*  57 */     this.sourceip = sourceip;
/*     */   }
/*     */   public String getUserip() {
/*  60 */     return this.userip;
/*     */   }
/*     */   public void setUserip(String userip) {
/*  63 */     this.userip = userip;
/*     */   }
/*     */   public String getCallingstationid() {
/*  66 */     return this.callingstationid;
/*     */   }
/*     */   public void setCallingstationid(String callingstationid) {
/*  69 */     this.callingstationid = callingstationid;
/*     */   }
/*     */   public String getName() {
/*  72 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  75 */     this.name = name;
/*     */   }
/*     */   public String getState() {
/*  78 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  81 */     this.state = state;
/*     */   }
/*     */   public Date getStartDate() {
/*  84 */     return this.startDate;
/*     */   }
/*     */   public void setStartDate(Date startDate) {
/*  87 */     this.startDate = startDate;
/*     */   }
/*     */   public Date getEndDate() {
/*  90 */     return this.endDate;
/*     */   }
/*     */   public void setEndDate(Date endDate) {
/*  93 */     this.endDate = endDate;
/*     */   }
/*     */   public Long getTime() {
/*  96 */     return this.time;
/*     */   }
/*     */   public void setTime(Long time) {
/*  99 */     this.time = time;
/*     */   }
/*     */   public Long getIns() {
/* 102 */     return this.ins;
/*     */   }
/*     */   public void setIns(Long ins) {
/* 105 */     this.ins = ins;
/*     */   }
/*     */   public Long getOuts() {
/* 108 */     return this.outs;
/*     */   }
/*     */   public void setOuts(Long outs) {
/* 111 */     this.outs = outs;
/*     */   }
/*     */   public Long getOctets() {
/* 114 */     return this.octets;
/*     */   }
/*     */   public void setOctets(Long octets) {
/* 117 */     this.octets = octets;
/*     */   }
/*     */   public String getAcctsessionid() {
/* 120 */     return this.acctsessionid;
/*     */   }
/*     */   public void setAcctsessionid(String acctsessionid) {
/* 123 */     this.acctsessionid = acctsessionid;
/*     */   }
/*     */   public String getEx1() {
/* 126 */     return this.ex1;
/*     */   }
/*     */   public void setEx1(String ex1) {
/* 129 */     this.ex1 = ex1;
/*     */   }
/*     */   public String getEx2() {
/* 132 */     return this.ex2;
/*     */   }
/*     */   public void setEx2(String ex2) {
/* 135 */     this.ex2 = ex2;
/*     */   }
/*     */   public String getEx3() {
/* 138 */     return this.ex3;
/*     */   }
/*     */   public void setEx3(String ex3) {
/* 141 */     this.ex3 = ex3;
/*     */   }
/*     */   public String getEx4() {
/* 144 */     return this.ex4;
/*     */   }
/*     */   public void setEx4(String ex4) {
/* 147 */     this.ex4 = ex4;
/*     */   }
/*     */   public String getEx5() {
/* 150 */     return this.ex5;
/*     */   }
/*     */   public void setEx5(String ex5) {
/* 153 */     this.ex5 = ex5;
/*     */   }
/*     */   public String getEx6() {
/* 156 */     return this.ex6;
/*     */   }
/*     */   public void setEx6(String ex6) {
/* 159 */     this.ex6 = ex6;
/*     */   }
/*     */   public String getEx7() {
/* 162 */     return this.ex7;
/*     */   }
/*     */   public void setEx7(String ex7) {
/* 165 */     this.ex7 = ex7;
/*     */   }
/*     */   public String getEx8() {
/* 168 */     return this.ex8;
/*     */   }
/*     */   public void setEx8(String ex8) {
/* 171 */     this.ex8 = ex8;
/*     */   }
/*     */   public String getEx9() {
/* 174 */     return this.ex9;
/*     */   }
/*     */   public void setEx9(String ex9) {
/* 177 */     this.ex9 = ex9;
/*     */   }
/*     */   public String getEx10() {
/* 180 */     return this.ex10;
/*     */   }
/*     */   public void setEx10(String ex10) {
/* 183 */     this.ex10 = ex10;
/*     */   }
/*     */   public String toString() {
/* 186 */     return "Radiuslinkrecordall [id=" + this.id + ",nasip=" + this.nasip + ",sourceip=" + this.sourceip + ",userip=" + this.userip + ",callingstationid=" + this.callingstationid + ",name=" + this.name + ",state=" + this.state + ",startDate=" + this.startDate + ",endDate=" + this.endDate + ",time=" + this.time + ",ins=" + this.ins + ",outs=" + this.outs + ",octets=" + this.octets + ",acctsessionid=" + this.acctsessionid + ",ex1=" + this.ex1 + ",ex2=" + this.ex2 + ",ex3=" + this.ex3 + ",ex4=" + this.ex4 + ",ex5=" + this.ex5 + ",ex6=" + this.ex6 + ",ex7=" + this.ex7 + ",ex8=" + this.ex8 + ",ex9=" + this.ex9 + ",ex10=" + this.ex10 + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Radiuslinkrecordall
 * JD-Core Version:    0.6.2
 */