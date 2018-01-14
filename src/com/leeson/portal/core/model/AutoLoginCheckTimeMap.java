/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class AutoLoginCheckTimeMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6574632632398370165L;
/* 18 */   private Map<String, Long> AutoLoginCheckTimeMap = new ConcurrentHashMap();
/*    */ 
/* 20 */   private static AutoLoginCheckTimeMap instance = new AutoLoginCheckTimeMap();
/*    */ 
/*    */   public static AutoLoginCheckTimeMap getInstance()
/*    */   {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, Long> getAutoLoginCheckTimeMap() {
/* 30 */     return this.AutoLoginCheckTimeMap;
/*    */   }
/*    */ 
/*    */   public void setAutoLoginCheckTimeMap(Map<String, Long> autoLoginCheckTimeMap) {
/* 34 */     this.AutoLoginCheckTimeMap = autoLoginCheckTimeMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AutoLoginCheckTimeMap
 * JD-Core Version:    0.6.2
 */