/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class UniFiMacSiteMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 6044857178756983860L;
/* 17 */   private HashMap<String, String> macSiteMap = new HashMap();
/*    */ 
/* 19 */   private static UniFiMacSiteMap instance = new UniFiMacSiteMap();
/*    */ 
/*    */   public static UniFiMacSiteMap getInstance()
/*    */   {
/* 25 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getMacSiteMap() {
/* 29 */     return this.macSiteMap;
/*    */   }
/*    */ 
/*    */   public void setMacSiteMap(HashMap<String, String> macSiteMap) {
/* 33 */     this.macSiteMap = macSiteMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.UniFiMacSiteMap
 * JD-Core Version:    0.6.2
 */