/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class GuestAuthMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -5944369478225145039L;
/* 14 */   private HashMap<String, String> guestAuthMap = new HashMap();
/*    */ 
/* 16 */   private static GuestAuthMap instance = new GuestAuthMap();
/*    */ 
/*    */   public static GuestAuthMap getInstance()
/*    */   {
/* 22 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getGuestAuthMap() {
/* 26 */     return this.guestAuthMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.GuestAuthMap
 * JD-Core Version:    0.6.2
 */