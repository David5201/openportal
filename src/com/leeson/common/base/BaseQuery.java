/*     */ package com.leeson.common.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class BaseQuery
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int DEFAULT_SIZE = 10;
/*  17 */   protected int pageSize = 10;
/*     */   protected int startRow;
/*     */   protected int endRow;
/*  20 */   protected int page = 1;
/*     */   protected String fields;
/*     */   protected Date begin_time;
/*     */   protected Date end_time;
/*     */   protected Date begin_time1;
/*     */   protected Date end_time1;
/*     */ 
/*     */   public Date getBegin_time1()
/*     */   {
/*  31 */     return this.begin_time1;
/*     */   }
/*     */   public void setBegin_time1(Date begin_time1) {
/*  34 */     this.begin_time1 = begin_time1;
/*     */   }
/*     */   public Date getEnd_time1() {
/*  37 */     return this.end_time1;
/*     */   }
/*     */   public void setEnd_time1(Date end_time1) {
/*  40 */     this.end_time1 = end_time1;
/*     */   }
/*     */   public Date getBegin_time() {
/*  43 */     return this.begin_time;
/*     */   }
/*     */   public void setBegin_time(Date begin_time) {
/*  46 */     this.begin_time = begin_time;
/*     */   }
/*     */   public Date getEnd_time() {
/*  49 */     return this.end_time;
/*     */   }
/*     */   public void setEnd_time(Date end_time) {
/*  52 */     this.end_time = end_time;
/*     */   }
/*     */ 
/*     */   public BaseQuery() {
/*     */   }
/*     */ 
/*     */   public BaseQuery(int page, int pageSize) {
/*  59 */     this.page = page;
/*  60 */     this.pageSize = pageSize;
/*  61 */     this.startRow = ((page - 1) * this.pageSize);
/*  62 */     this.endRow = (this.startRow + this.pageSize - 1);
/*     */   }
/*     */ 
/*     */   public int getStartRow() {
/*  66 */     return this.startRow;
/*     */   }
/*     */   public BaseQuery setStartRow(int startRow) {
/*  69 */     this.startRow = startRow;
/*  70 */     return this;
/*     */   }
/*     */   public int getEndRow() {
/*  73 */     return this.endRow;
/*     */   }
/*     */   public BaseQuery setEndRow(int endRow) {
/*  76 */     this.endRow = endRow;
/*  77 */     return this;
/*     */   }
/*     */   public BaseQuery setPage(int page) {
/*  80 */     this.page = page;
/*  81 */     this.startRow = ((page - 1) * this.pageSize);
/*  82 */     this.endRow = (this.startRow + this.pageSize - 1);
/*  83 */     return this;
/*     */   }
/*     */   public int getPageSize() {
/*  86 */     return this.pageSize;
/*     */   }
/*     */   public BaseQuery setPageSize(int pageSize) {
/*  89 */     this.pageSize = pageSize;
/*  90 */     if ((pageSize != 10) && (this.page > 0)) {
/*  91 */       this.startRow = ((this.page - 1) * this.pageSize);
/*  92 */       this.endRow = (this.startRow + this.pageSize - 1);
/*     */     }
/*  94 */     return this;
/*     */   }
/*     */   public int getPage() {
/*  97 */     return this.page;
/*     */   }
/*     */ 
/*     */   public String getFields() {
/* 101 */     return this.fields;
/*     */   }
/*     */   public void setFields(String fields) {
/* 104 */     this.fields = fields;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.base.BaseQuery
 * JD-Core Version:    0.6.2
 */