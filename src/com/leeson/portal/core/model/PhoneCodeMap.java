/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class PhoneCodeMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6341584999536185129L;
/* 18 */   private Map<String, Object[]> phoneCodeMap = new ConcurrentHashMap();
/*    */ 
/* 20 */   private static PhoneCodeMap instance = new PhoneCodeMap();
/*    */ 
/*    */   public static PhoneCodeMap getInstance()
/*    */   {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, Object[]> getPhoneCodeMap() {
/* 30 */     return this.phoneCodeMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.PhoneCodeMap
 * JD-Core Version:    0.6.2
 */