/*    */ package com.leeson.radius.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class RadiusOnlineMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8746602081460381251L;
/* 18 */   private Map<String, String[]> radiusOnlineMap = new ConcurrentHashMap();
/*    */ 
/* 20 */   private static RadiusOnlineMap instance = new RadiusOnlineMap();
/*    */ 
/*    */   public static RadiusOnlineMap getInstance()
/*    */   {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, String[]> getRadiusOnlineMap() {
/* 30 */     return this.radiusOnlineMap;
/*    */   }
/*    */ 
/*    */   public void setRadiusOnlineMap(Map<String, String[]> radiusOnlineMap) {
/* 34 */     this.radiusOnlineMap = radiusOnlineMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.model.RadiusOnlineMap
 * JD-Core Version:    0.6.2
 */