/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class AccountAPIMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7966240873304008863L;
/* 17 */   private HashMap<String, String> accountAPIMap = new HashMap();
/*    */ 
/* 19 */   private static AccountAPIMap instance = new AccountAPIMap();
/*    */ 
/*    */   private AccountAPIMap() {
/* 22 */     this.accountAPIMap.put("url", "");
/* 23 */     this.accountAPIMap.put("state", "");
/*    */ 
/* 25 */     this.accountAPIMap.put("publicurl", "");
/* 26 */     this.accountAPIMap.put("publicstate", "");
/*    */ 
/* 28 */     this.accountAPIMap.put("autourl", "");
/* 29 */     this.accountAPIMap.put("autostate", "");
/*    */   }
/*    */ 
/*    */   public static AccountAPIMap getInstance() {
/* 33 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getAccountAPIMap() {
/* 37 */     return this.accountAPIMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AccountAPIMap
 * JD-Core Version:    0.6.2
 */