/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class WifiDogOnlineMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2448351617673082825L;
/* 33 */   private Map<String, String[]> wifiDogOnlineMap = new ConcurrentHashMap();
/*    */ 
/* 35 */   private static WifiDogOnlineMap instance = new WifiDogOnlineMap();
/*    */ 
/*    */   public static WifiDogOnlineMap getInstance()
/*    */   {
/* 41 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, String[]> getWifiDogOnlineMap() {
/* 45 */     return this.wifiDogOnlineMap;
/*    */   }
/*    */ 
/*    */   public void setWifiDogOnlineMap(Map<String, String[]> wifiDogOnlineMap) {
/* 49 */     this.wifiDogOnlineMap = wifiDogOnlineMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.WifiDogOnlineMap
 * JD-Core Version:    0.6.2
 */