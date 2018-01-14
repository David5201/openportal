/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class ipMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5220960469752563771L;
/* 21 */   private Map<String, Integer> ipmap = new ConcurrentHashMap();
/*    */ 
/* 23 */   private static ipMap instance = new ipMap();
/*    */ 
/*    */   public static ipMap getInstance()
/*    */   {
/* 29 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, Integer> getIpmap() {
/* 33 */     return this.ipmap;
/*    */   }
/*    */ 
/*    */   public void setIpmap(Map<String, Integer> ipmap) {
/* 37 */     this.ipmap = ipmap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.ipMap
 * JD-Core Version:    0.6.2
 */