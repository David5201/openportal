/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class CoreConfigMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 6739281968095706043L;
/* 14 */   private HashMap<String, String[]> coreConfigMap = new HashMap();
/*    */ 
/* 16 */   private static CoreConfigMap instance = new CoreConfigMap();
/*    */ 
/*    */   private CoreConfigMap() {
/* 19 */     String[] core = new String[2];
/* 20 */     core[0] = "http://www.openportal.com.cn";
/* 21 */     core[1] = "100";
/* 22 */     this.coreConfigMap.put("core", core);
/*    */   }
/*    */ 
/*    */   public static CoreConfigMap getInstance() {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String[]> getCoreConfigMap() {
/* 30 */     return this.coreConfigMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.CoreConfigMap
 * JD-Core Version:    0.6.2
 */