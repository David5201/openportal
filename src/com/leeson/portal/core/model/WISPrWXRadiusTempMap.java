/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class WISPrWXRadiusTempMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 849360672903806770L;
/* 18 */   private Map<String, String> wisprWXRadiusTempMap = new ConcurrentHashMap();
/*    */ 
/* 20 */   private static WISPrWXRadiusTempMap instance = new WISPrWXRadiusTempMap();
/*    */ 
/*    */   public static WISPrWXRadiusTempMap getInstance()
/*    */   {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, String> getWisprWXRadiusTempMap() {
/* 30 */     return this.wisprWXRadiusTempMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.WISPrWXRadiusTempMap
 * JD-Core Version:    0.6.2
 */