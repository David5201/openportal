/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class AutoLoginMacMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5366716655819873198L;
/* 18 */   private Map<String, String[]> AutoLoginMacMap = new ConcurrentHashMap();
/*    */ 
/* 20 */   private static AutoLoginMacMap instance = new AutoLoginMacMap();
/*    */ 
/*    */   public static AutoLoginMacMap getInstance()
/*    */   {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, String[]> getAutoLoginMacMap() {
/* 30 */     return this.AutoLoginMacMap;
/*    */   }
/*    */ 
/*    */   public void setAutoLoginMacMap(Map<String, String[]> autoLoginMacMap) {
/* 34 */     this.AutoLoginMacMap = autoLoginMacMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AutoLoginMacMap
 * JD-Core Version:    0.6.2
 */