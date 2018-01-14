/*    */ package com.leeson.core.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Payapi
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long id;
/*    */   private String alipayPartner;
/*    */   private String alipayKey;
/*    */   private String weixinAppId;
/*    */   private String weixinAppSecret;
/*    */   private String weixinPartner;
/*    */   private String weixinKey;
/*    */   private String weixinPartnerExd;
/*    */   private Integer alipay;
/*    */   private Integer weixin;
/*    */ 
/*    */   public Long getId()
/*    */   {
/* 28 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 31 */     this.id = id;
/*    */   }
/*    */   public String getAlipayPartner() {
/* 34 */     return this.alipayPartner;
/*    */   }
/*    */   public void setAlipayPartner(String alipayPartner) {
/* 37 */     this.alipayPartner = alipayPartner;
/*    */   }
/*    */   public String getAlipayKey() {
/* 40 */     return this.alipayKey;
/*    */   }
/*    */   public void setAlipayKey(String alipayKey) {
/* 43 */     this.alipayKey = alipayKey;
/*    */   }
/*    */   public String getWeixinAppId() {
/* 46 */     return this.weixinAppId;
/*    */   }
/*    */   public void setWeixinAppId(String weixinAppId) {
/* 49 */     this.weixinAppId = weixinAppId;
/*    */   }
/*    */   public String getWeixinAppSecret() {
/* 52 */     return this.weixinAppSecret;
/*    */   }
/*    */   public void setWeixinAppSecret(String weixinAppSecret) {
/* 55 */     this.weixinAppSecret = weixinAppSecret;
/*    */   }
/*    */   public String getWeixinPartner() {
/* 58 */     return this.weixinPartner;
/*    */   }
/*    */   public void setWeixinPartner(String weixinPartner) {
/* 61 */     this.weixinPartner = weixinPartner;
/*    */   }
/*    */   public String getWeixinKey() {
/* 64 */     return this.weixinKey;
/*    */   }
/*    */   public void setWeixinKey(String weixinKey) {
/* 67 */     this.weixinKey = weixinKey;
/*    */   }
/*    */   public String getWeixinPartnerExd() {
/* 70 */     return this.weixinPartnerExd;
/*    */   }
/*    */   public void setWeixinPartnerExd(String weixinPartnerExd) {
/* 73 */     this.weixinPartnerExd = weixinPartnerExd;
/*    */   }
/*    */   public Integer getAlipay() {
/* 76 */     return this.alipay;
/*    */   }
/*    */   public void setAlipay(Integer alipay) {
/* 79 */     this.alipay = alipay;
/*    */   }
/*    */   public Integer getWeixin() {
/* 82 */     return this.weixin;
/*    */   }
/*    */   public void setWeixin(Integer weixin) {
/* 85 */     this.weixin = weixin;
/*    */   }
/*    */   public String toString() {
/* 88 */     return "Payapi [id=" + this.id + ",alipayPartner=" + this.alipayPartner + ",alipayKey=" + this.alipayKey + ",weixinAppId=" + this.weixinAppId + ",weixinAppSecret=" + this.weixinAppSecret + ",weixinPartner=" + this.weixinPartner + ",weixinKey=" + this.weixinKey + ",weixinPartnerExd=" + this.weixinPartnerExd + ",alipay=" + this.alipay + ",weixin=" + this.weixin + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Payapi
 * JD-Core Version:    0.6.2
 */