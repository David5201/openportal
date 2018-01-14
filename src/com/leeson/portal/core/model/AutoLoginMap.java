/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class AutoLoginMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4486367212242440408L;
/* 18 */   private Map<String, String[]> autoLoginMap = new ConcurrentHashMap();
/*    */ 
/* 20 */   private static AutoLoginMap instance = new AutoLoginMap();
/*    */ 
/*    */   public static AutoLoginMap getInstance()
/*    */   {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, String[]> getAutoLoginMap() {
/* 30 */     return this.autoLoginMap;
/*    */   }
/*    */ 
/*    */   public void setAutoLoginMap(Map<String, String[]> autoLoginMap) {
/* 34 */     this.autoLoginMap = autoLoginMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AutoLoginMap
 * JD-Core Version:    0.6.2
 */