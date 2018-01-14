/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class OnlineMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5220960469752563771L;
/* 21 */   private Map<String, String[]> OnlineUserMap = new ConcurrentHashMap();
/*    */ 
/* 23 */   private static OnlineMap instance = new OnlineMap();
/*    */ 
/*    */   public static OnlineMap getInstance()
/*    */   {
/* 29 */     return instance;
/*    */   }
/*    */ 
/*    */   public static void setInstance(OnlineMap instance) {
/* 33 */     instance = instance;
/*    */   }
/*    */ 
/*    */   public Map<String, String[]> getOnlineUserMap() {
/* 37 */     return this.OnlineUserMap;
/*    */   }
/*    */ 
/*    */   public void setOnlineUserMap(Map<String, String[]> onlineUserMap) {
/* 41 */     this.OnlineUserMap = onlineUserMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.OnlineMap
 * JD-Core Version:    0.6.2
 */