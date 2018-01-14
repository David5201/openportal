/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class CheckTimeDateMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6574632632398370165L;
/* 19 */   private Map<String, Date> CheckTimeDateMap = new ConcurrentHashMap();
/*    */ 
/* 21 */   private static CheckTimeDateMap instance = new CheckTimeDateMap();
/*    */ 
/*    */   private CheckTimeDateMap() {
/* 24 */     this.CheckTimeDateMap.put("date", new Date());
/*    */   }
/*    */ 
/*    */   public static CheckTimeDateMap getInstance() {
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, Date> getCheckTimeDateMap() {
/* 32 */     return this.CheckTimeDateMap;
/*    */   }
/*    */ 
/*    */   public void setCheckTimeDateMap(Map<String, Date> checkTimeDateMap) {
/* 36 */     this.CheckTimeDateMap = checkTimeDateMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.CheckTimeDateMap
 * JD-Core Version:    0.6.2
 */