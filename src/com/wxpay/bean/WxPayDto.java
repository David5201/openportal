/*    */ package com.wxpay.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class WxPayDto
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4641374184740720071L;
/*    */   private String orderId;
/*    */   private String totalFee;
/*    */   private String spbillCreateIp;
/*    */   private String notifyUrl;
/*    */   private String body;
/*    */   private String openId;
/*    */ 
/*    */   public String getOrderId()
/*    */   {
/* 21 */     return this.orderId;
/*    */   }
/*    */ 
/*    */   public void setOrderId(String orderId)
/*    */   {
/* 27 */     this.orderId = orderId;
/*    */   }
/*    */ 
/*    */   public String getTotalFee()
/*    */   {
/* 33 */     return this.totalFee;
/*    */   }
/*    */ 
/*    */   public void setTotalFee(String totalFee)
/*    */   {
/* 39 */     this.totalFee = totalFee;
/*    */   }
/*    */ 
/*    */   public String getSpbillCreateIp()
/*    */   {
/* 45 */     return this.spbillCreateIp;
/*    */   }
/*    */ 
/*    */   public void setSpbillCreateIp(String spbillCreateIp)
/*    */   {
/* 51 */     this.spbillCreateIp = spbillCreateIp;
/*    */   }
/*    */ 
/*    */   public String getNotifyUrl()
/*    */   {
/* 57 */     return this.notifyUrl;
/*    */   }
/*    */ 
/*    */   public void setNotifyUrl(String notifyUrl)
/*    */   {
/* 63 */     this.notifyUrl = notifyUrl;
/*    */   }
/*    */ 
/*    */   public String getBody()
/*    */   {
/* 69 */     return this.body;
/*    */   }
/*    */ 
/*    */   public void setBody(String body)
/*    */   {
/* 75 */     this.body = body;
/*    */   }
/*    */ 
/*    */   public String getOpenId()
/*    */   {
/* 81 */     return this.openId;
/*    */   }
/*    */ 
/*    */   public void setOpenId(String openId)
/*    */   {
/* 87 */     this.openId = openId;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 91 */     return "WxPayDto [orderId=" + this.orderId + ", totalFee=" + this.totalFee + 
/* 92 */       ", spbillCreateIp=" + this.spbillCreateIp + ", notifyUrl=" + 
/* 93 */       this.notifyUrl + ", body=" + this.body + ", openId=" + this.openId + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.bean.WxPayDto
 * JD-Core Version:    0.6.2
 */