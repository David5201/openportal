/*     */ package com.leeson.core.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Portalorder
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String description;
/*     */   private Long payTime;
/*     */   private String payType;
/*     */   private String state;
/*     */   private String cdKey;
/*     */   private String categoryType;
/*     */   private String accountName;
/*     */   private Long accountId;
/*     */   private Date payDate;
/*     */   private Integer userDel;
/*     */   private Integer accountDel;
/*     */   private Double money;
/*     */   private Date buyDate;
/*     */   private Integer payby;
/*     */   private String tradeno;
/*     */   private String buyer;
/*     */   private String seller;
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  37 */     return this.id;
/*     */   }
/*     */   public void setId(Long id) {
/*  40 */     this.id = id;
/*     */   }
/*     */   public String getName() {
/*  43 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  46 */     this.name = name;
/*     */   }
/*     */   public String getDescription() {
/*  49 */     return this.description;
/*     */   }
/*     */   public void setDescription(String description) {
/*  52 */     this.description = description;
/*     */   }
/*     */   public Long getPayTime() {
/*  55 */     return this.payTime;
/*     */   }
/*     */   public void setPayTime(Long payTime) {
/*  58 */     this.payTime = payTime;
/*     */   }
/*     */   public String getPayType() {
/*  61 */     return this.payType;
/*     */   }
/*     */   public void setPayType(String payType) {
/*  64 */     this.payType = payType;
/*     */   }
/*     */   public String getState() {
/*  67 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  70 */     this.state = state;
/*     */   }
/*     */   public String getCdKey() {
/*  73 */     return this.cdKey;
/*     */   }
/*     */   public void setCdKey(String cdKey) {
/*  76 */     this.cdKey = cdKey;
/*     */   }
/*     */   public String getCategoryType() {
/*  79 */     return this.categoryType;
/*     */   }
/*     */   public void setCategoryType(String categoryType) {
/*  82 */     this.categoryType = categoryType;
/*     */   }
/*     */   public String getAccountName() {
/*  85 */     return this.accountName;
/*     */   }
/*     */   public void setAccountName(String accountName) {
/*  88 */     this.accountName = accountName;
/*     */   }
/*     */   public Long getAccountId() {
/*  91 */     return this.accountId;
/*     */   }
/*     */   public void setAccountId(Long accountId) {
/*  94 */     this.accountId = accountId;
/*     */   }
/*     */   public Date getPayDate() {
/*  97 */     return this.payDate;
/*     */   }
/*     */   public void setPayDate(Date payDate) {
/* 100 */     this.payDate = payDate;
/*     */   }
/*     */   public Integer getUserDel() {
/* 103 */     return this.userDel;
/*     */   }
/*     */   public void setUserDel(Integer userDel) {
/* 106 */     this.userDel = userDel;
/*     */   }
/*     */   public Integer getAccountDel() {
/* 109 */     return this.accountDel;
/*     */   }
/*     */   public void setAccountDel(Integer accountDel) {
/* 112 */     this.accountDel = accountDel;
/*     */   }
/*     */   public Double getMoney() {
/* 115 */     return this.money;
/*     */   }
/*     */   public void setMoney(Double money) {
/* 118 */     this.money = money;
/*     */   }
/*     */   public Date getBuyDate() {
/* 121 */     return this.buyDate;
/*     */   }
/*     */   public void setBuyDate(Date buyDate) {
/* 124 */     this.buyDate = buyDate;
/*     */   }
/*     */   public Integer getPayby() {
/* 127 */     return this.payby;
/*     */   }
/*     */   public void setPayby(Integer payby) {
/* 130 */     this.payby = payby;
/*     */   }
/*     */   public String getTradeno() {
/* 133 */     return this.tradeno;
/*     */   }
/*     */   public void setTradeno(String tradeno) {
/* 136 */     this.tradeno = tradeno;
/*     */   }
/*     */   public String getBuyer() {
/* 139 */     return this.buyer;
/*     */   }
/*     */   public void setBuyer(String buyer) {
/* 142 */     this.buyer = buyer;
/*     */   }
/*     */   public String getSeller() {
/* 145 */     return this.seller;
/*     */   }
/*     */   public void setSeller(String seller) {
/* 148 */     this.seller = seller;
/*     */   }
/*     */   public String toString() {
/* 151 */     return "Portalorder [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",payTime=" + this.payTime + ",payType=" + this.payType + ",state=" + this.state + ",cdKey=" + this.cdKey + ",categoryType=" + this.categoryType + ",accountName=" + this.accountName + ",accountId=" + this.accountId + ",payDate=" + this.payDate + ",userDel=" + this.userDel + ",accountDel=" + this.accountDel + ",money=" + this.money + ",buyDate=" + this.buyDate + ",payby=" + this.payby + ",tradeno=" + this.tradeno + ",buyer=" + this.buyer + ",seller=" + this.seller + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalorder
 * JD-Core Version:    0.6.2
 */