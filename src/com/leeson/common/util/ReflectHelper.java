/*    */ package com.leeson.common.util;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class ReflectHelper
/*    */ {
/*    */   public static Field getFieldByFieldName(Object obj, String fieldName)
/*    */   {
/* 17 */     for (Class superClass = obj.getClass(); superClass != Object.class; ) {
/*    */       try
/*    */       {
/* 20 */         return superClass.getDeclaredField(fieldName);
/*    */       }
/*    */       catch (NoSuchFieldException localNoSuchFieldException)
/*    */       {
/* 17 */         superClass = superClass
/* 18 */           .getSuperclass();
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */   public static Object getValueByFieldName(Object obj, String fieldName)
/*    */     throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
/*    */   {
/* 40 */     Field field = getFieldByFieldName(obj, fieldName);
/* 41 */     Object value = null;
/* 42 */     if (field != null) {
/* 43 */       if (field.isAccessible()) {
/* 44 */         value = field.get(obj);
/*    */       } else {
/* 46 */         field.setAccessible(true);
/* 47 */         value = field.get(obj);
/* 48 */         field.setAccessible(false);
/*    */       }
/*    */     }
/* 51 */     return value;
/*    */   }
/*    */ 
/*    */   public static void setValueByFieldName(Object obj, String fieldName, Object value)
/*    */     throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
/*    */   {
/* 67 */     Field field = obj.getClass().getDeclaredField(fieldName);
/* 68 */     if (field.isAccessible()) {
/* 69 */       field.set(obj, value);
/*    */     } else {
/* 71 */       field.setAccessible(true);
/* 72 */       field.set(obj, value);
/* 73 */       field.setAccessible(false);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.ReflectHelper
 * JD-Core Version:    0.6.2
 */