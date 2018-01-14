/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class iKuaiMacIpMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5220960469752563771L;
/* 20 */   private HashMap<String, String> macIpMap = new HashMap();
/*    */ 
/* 22 */   private static iKuaiMacIpMap instance = new iKuaiMacIpMap();
/*    */ 
/*    */   public static iKuaiMacIpMap getInstance()
/*    */   {
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getMacIpMap() {
/* 32 */     return this.macIpMap;
/*    */   }
/*    */ 
/*    */   public void setMacIpMap(HashMap<String, String> macIpMap) {
/* 36 */     this.macIpMap = macIpMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.iKuaiMacIpMap
 * JD-Core Version:    0.6.2
 */