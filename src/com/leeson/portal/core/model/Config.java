/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class Config
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7834052616912881869L;
/* 20 */   private Map<String, Portalbas> configMap = new ConcurrentHashMap();
/*    */ 
/* 22 */   private static Config instance = new Config();
/*    */ 
/*    */   public static Config getInstance()
/*    */   {
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   public Map<String, Portalbas> getConfigMap() {
/* 32 */     return this.configMap;
/*    */   }
/*    */ 
/*    */   public void setConfigMap(Map<String, Portalbas> configMap) {
/* 36 */     this.configMap = configMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.Config
 * JD-Core Version:    0.6.2
 */