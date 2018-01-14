/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class PhoneUserMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6341584999536185129L;
/* 17 */   private HashMap<String, String> phoneUserMap = new HashMap();
/*    */ 
/* 19 */   private static PhoneUserMap instance = new PhoneUserMap();
/*    */ 
/*    */   public HashMap<String, String> getPhoneUserMap()
/*    */   {
/* 25 */     return this.phoneUserMap;
/*    */   }
/*    */ 
/*    */   public static PhoneUserMap getInstance() {
/* 29 */     return instance;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.PhoneUserMap
 * JD-Core Version:    0.6.2
 */