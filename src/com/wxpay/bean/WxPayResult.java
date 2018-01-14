/*     */ package com.wxpay.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WxPayResult
/*     */   implements Serializable
/*     */ {
/*     */   private String appid;
/*     */   private String bankType;
/*     */   private String cashFee;
/*     */   private String feeType;
/*     */   private String isSubscribe;
/*     */   private String mchId;
/*     */   private String nonceStr;
/*     */   private String openid;
/*     */   private String outTradeNo;
/*     */   private String resultCode;
/*     */   private String returnCode;
/*     */   private String sign;
/*     */   private String timeEnd;
/*     */   private String totalFee;
/*     */   private String tradeType;
/*     */   private String transactionId;
/*     */   private static final long serialVersionUID = -1227026039888867970L;
/*     */ 
/*     */   public String getAppid()
/*     */   {
/*  41 */     return this.appid;
/*     */   }
/*     */ 
/*     */   public void setAppid(String appid)
/*     */   {
/*  47 */     this.appid = appid;
/*     */   }
/*     */ 
/*     */   public String getBankType()
/*     */   {
/*  53 */     return this.bankType;
/*     */   }
/*     */ 
/*     */   public void setBankType(String bankType)
/*     */   {
/*  59 */     this.bankType = bankType;
/*     */   }
/*     */ 
/*     */   public String getCashFee()
/*     */   {
/*  65 */     return this.cashFee;
/*     */   }
/*     */ 
/*     */   public void setCashFee(String cashFee)
/*     */   {
/*  71 */     this.cashFee = cashFee;
/*     */   }
/*     */ 
/*     */   public String getFeeType()
/*     */   {
/*  77 */     return this.feeType;
/*     */   }
/*     */ 
/*     */   public void setFeeType(String feeType)
/*     */   {
/*  83 */     this.feeType = feeType;
/*     */   }
/*     */ 
/*     */   public String getIsSubscribe()
/*     */   {
/*  89 */     return this.isSubscribe;
/*     */   }
/*     */ 
/*     */   public void setIsSubscribe(String isSubscribe)
/*     */   {
/*  95 */     this.isSubscribe = isSubscribe;
/*     */   }
/*     */ 
/*     */   public String getMchId()
/*     */   {
/* 101 */     return this.mchId;
/*     */   }
/*     */ 
/*     */   public void setMchId(String mchId)
/*     */   {
/* 107 */     this.mchId = mchId;
/*     */   }
/*     */ 
/*     */   public String getNonceStr()
/*     */   {
/* 113 */     return this.nonceStr;
/*     */   }
/*     */ 
/*     */   public void setNonceStr(String nonceStr)
/*     */   {
/* 119 */     this.nonceStr = nonceStr;
/*     */   }
/*     */ 
/*     */   public String getOpenid()
/*     */   {
/* 125 */     return this.openid;
/*     */   }
/*     */ 
/*     */   public void setOpenid(String openid)
/*     */   {
/* 131 */     this.openid = openid;
/*     */   }
/*     */ 
/*     */   public String getOutTradeNo()
/*     */   {
/* 137 */     return this.outTradeNo;
/*     */   }
/*     */ 
/*     */   public void setOutTradeNo(String outTradeNo)
/*     */   {
/* 143 */     this.outTradeNo = outTradeNo;
/*     */   }
/*     */ 
/*     */   public String getResultCode()
/*     */   {
/* 149 */     return this.resultCode;
/*     */   }
/*     */ 
/*     */   public void setResultCode(String resultCode)
/*     */   {
/* 155 */     this.resultCode = resultCode;
/*     */   }
/*     */ 
/*     */   public String getReturnCode()
/*     */   {
/* 161 */     return this.returnCode;
/*     */   }
/*     */ 
/*     */   public void setReturnCode(String returnCode)
/*     */   {
/* 167 */     this.returnCode = returnCode;
/*     */   }
/*     */ 
/*     */   public String getSign()
/*     */   {
/* 173 */     return this.sign;
/*     */   }
/*     */ 
/*     */   public void setSign(String sign)
/*     */   {
/* 179 */     this.sign = sign;
/*     */   }
/*     */ 
/*     */   public String getTimeEnd()
/*     */   {
/* 185 */     return this.timeEnd;
/*     */   }
/*     */ 
/*     */   public void setTimeEnd(String timeEnd)
/*     */   {
/* 191 */     this.timeEnd = timeEnd;
/*     */   }
/*     */ 
/*     */   public String getTotalFee()
/*     */   {
/* 197 */     return this.totalFee;
/*     */   }
/*     */ 
/*     */   public void setTotalFee(String totalFee)
/*     */   {
/* 203 */     this.totalFee = totalFee;
/*     */   }
/*     */ 
/*     */   public String getTradeType()
/*     */   {
/* 209 */     return this.tradeType;
/*     */   }
/*     */ 
/*     */   public void setTradeType(String tradeType)
/*     */   {
/* 215 */     this.tradeType = tradeType;
/*     */   }
/*     */ 
/*     */   public String getTransactionId()
/*     */   {
/* 221 */     return this.transactionId;
/*     */   }
/*     */ 
/*     */   public void setTransactionId(String transactionId)
/*     */   {
/* 227 */     this.transactionId = transactionId;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 231 */     return "WxPayResult [appid=" + this.appid + ", bankType=" + this.bankType + 
/* 232 */       ", cashFee=" + this.cashFee + ", feeType=" + this.feeType + 
/* 233 */       ", isSubscribe=" + this.isSubscribe + ", mchId=" + this.mchId + 
/* 234 */       ", nonceStr=" + this.nonceStr + ", openid=" + this.openid + 
/* 235 */       ", outTradeNo=" + this.outTradeNo + ", resultCode=" + this.resultCode + 
/* 236 */       ", returnCode=" + this.returnCode + ", sign=" + this.sign + 
/* 237 */       ", timeEnd=" + this.timeEnd + ", totalFee=" + this.totalFee + 
/* 238 */       ", tradeType=" + this.tradeType + ", transactionId=" + 
/* 239 */       this.transactionId + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.bean.WxPayResult
 * JD-Core Version:    0.6.2
 */