/*    */ package com.leeson.common.web.springmvc;
/*    */ 
/*    */ import java.beans.PropertyEditorSupport;
/*    */ import java.sql.Timestamp;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import org.springframework.util.StringUtils;
/*    */ 
/*    */ public class DateTypeEditor extends PropertyEditorSupport
/*    */ {
/* 18 */   public static final DateFormat DF_LONG = new SimpleDateFormat(
/* 19 */     "yyyy-MM-dd HH:mm:ss");
/*    */ 
/* 20 */   public static final DateFormat DF_SHORT = new SimpleDateFormat("yyyy-MM-dd");
/* 21 */   public static final DateFormat DF_YEAR = new SimpleDateFormat("yyyy");
/* 22 */   public static final DateFormat DF_MONTH = new SimpleDateFormat("yyyy-MM");
/*    */   public static final int SHORT_DATE = 10;
/*    */   public static final int YEAR_DATE = 4;
/*    */   public static final int MONTH_DATE = 7;
/*    */ 
/*    */   public void setAsText(String text)
/*    */     throws IllegalArgumentException
/*    */   {
/* 33 */     text = text.trim();
/* 34 */     if (!StringUtils.hasText(text)) {
/* 35 */       setValue(null);
/* 36 */       return;
/*    */     }
/*    */     try {
/* 39 */       if (text.length() <= 4)
/* 40 */         setValue(new java.sql.Date(DF_YEAR.parse(text).getTime()));
/* 41 */       else if (text.length() <= 7)
/* 42 */         setValue(new java.sql.Date(DF_MONTH.parse(text).getTime()));
/* 43 */       else if (text.length() <= 10)
/* 44 */         setValue(new java.sql.Date(DF_SHORT.parse(text).getTime()));
/*    */       else
/* 46 */         setValue(new Timestamp(DF_LONG.parse(text).getTime()));
/*    */     }
/*    */     catch (ParseException ex) {
/* 49 */       IllegalArgumentException iae = new IllegalArgumentException(
/* 50 */         "Could not parse date: " + ex.getMessage());
/* 51 */       iae.initCause(ex);
/* 52 */       throw iae;
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getAsText()
/*    */   {
/* 60 */     java.util.Date value = (java.util.Date)getValue();
/* 61 */     return value != null ? DF_LONG.format(value) : "";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.DateTypeEditor
 * JD-Core Version:    0.6.2
 */