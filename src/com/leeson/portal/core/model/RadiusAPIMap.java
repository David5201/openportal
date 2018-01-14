/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class RadiusAPIMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3748694201323103019L;
/* 17 */   private HashMap<String, String> radiusAPIMap = new HashMap();
/*    */ 
/* 19 */   private static RadiusAPIMap instance = new RadiusAPIMap();
/*    */ 
/*    */   private RadiusAPIMap() {
/* 22 */     this.radiusAPIMap.put("state", "0");
/* 23 */     this.radiusAPIMap.put("url", "http://127.0.0.1/radiusAPI");
/*    */   }
/*    */ 
/*    */   public static RadiusAPIMap getInstance() {
/* 27 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getRadiusAPIMap() {
/* 31 */     return this.radiusAPIMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.RadiusAPIMap
 * JD-Core Version:    0.6.2
 */