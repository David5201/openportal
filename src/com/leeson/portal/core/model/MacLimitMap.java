/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class MacLimitMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5366716655819873198L;
/* 18 */   private Map<String, String[]> MacLimitMap = new ConcurrentHashMap();
/*    */ 
/* 20 */   private static MacLimitMap instance = new MacLimitMap();
/*    */ 
/*    */   public static MacLimitMap getInstance()
/*    */   {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, String[]> getMacLimitMap() {
/* 30 */     return this.MacLimitMap;
/*    */   }
/*    */ 
/*    */   public void setMacLimitMap(Map<String, String[]> macLimitMap) {
/* 34 */     this.MacLimitMap = macLimitMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.MacLimitMap
 * JD-Core Version:    0.6.2
 */