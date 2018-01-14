/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class WeixinSMSMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3996588398323647432L;
/* 16 */   private String pwd = "OpenPortal";
/*    */ 
/* 18 */   private static WeixinSMSMap instance = new WeixinSMSMap();
/*    */ 
/*    */   public static WeixinSMSMap getInstance()
/*    */   {
/* 24 */     return instance;
/*    */   }
/*    */ 
/*    */   public String getPwd() {
/* 28 */     return this.pwd;
/*    */   }
/*    */ 
/*    */   public void setPwd(String pwd) {
/* 32 */     this.pwd = pwd;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.WeixinSMSMap
 * JD-Core Version:    0.6.2
 */