/*    */ package com.leeson.portal.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class isDo
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3726982045234113660L;
/* 16 */   private static Long id = Long.valueOf(0L);
/*    */ 
/* 18 */   private static isDo instance = new isDo();
/*    */ 
/*    */   public static isDo getInstance()
/*    */   {
/* 24 */     return instance;
/*    */   }
/*    */ 
/*    */   public Long getId() {
/* 28 */     return id;
/*    */   }
/*    */ 
/*    */   public void setId(Long i) {
/* 32 */     id = i;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.isDo
 * JD-Core Version:    0.6.2
 */