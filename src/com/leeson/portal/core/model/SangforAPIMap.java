/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class SangforAPIMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1338978636302146102L;
/* 17 */   private HashMap<String, String> sangforAPIMap = new HashMap();
/*    */ 
/* 19 */   private static SangforAPIMap instance = new SangforAPIMap();
/*    */ 
/*    */   private SangforAPIMap() {
/* 22 */     this.sangforAPIMap.put("type", "");
/* 23 */     this.sangforAPIMap.put("url", "");
/* 24 */     this.sangforAPIMap.put("port", "");
/*    */   }
/*    */ 
/*    */   public static SangforAPIMap getInstance() {
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getSangforAPIMap() {
/* 32 */     return this.sangforAPIMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.SangforAPIMap
 * JD-Core Version:    0.6.2
 */