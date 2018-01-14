/*     */ package com.leeson.common.page;
/*     */ 
/*     */ public abstract class SimplePage
/*     */   implements Paginable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int DEF_COUNT = 10;
/* 122 */   protected int totalCount = 0;
/* 123 */   protected int pageSize = 10;
/* 124 */   protected int pageNo = 1;
/*     */   private int beginPageIndex;
/*     */   private int endPageIndex;
/*     */ 
/*     */   public static int cpn(Integer pageNo)
/*     */   {
/*  18 */     return (pageNo == null) || (pageNo.intValue() < 1) ? 1 : pageNo.intValue();
/*     */   }
/*     */ 
/*     */   public SimplePage()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SimplePage(int pageNo, int pageSize, int totalCount)
/*     */   {
/*  35 */     setTotalCount(totalCount);
/*  36 */     setPageSize(pageSize);
/*  37 */     setPageNo(pageNo);
/*  38 */     adjustPageNo();
/*     */   }
/*     */ 
/*     */   public void adjustPageNo()
/*     */   {
/*  45 */     if (this.pageNo == 1) {
/*  46 */       return;
/*     */     }
/*  48 */     int tp = getTotalPage();
/*  49 */     if (this.pageNo > tp)
/*  50 */       this.pageNo = tp;
/*     */   }
/*     */ 
/*     */   public int getPageNo()
/*     */   {
/*  58 */     return this.pageNo;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/*  65 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public int getTotalCount()
/*     */   {
/*  72 */     return this.totalCount;
/*     */   }
/*     */ 
/*     */   public int getTotalPage()
/*     */   {
/*  79 */     int totalPage = this.totalCount / this.pageSize;
/*  80 */     if ((totalPage == 0) || (this.totalCount % this.pageSize != 0)) {
/*  81 */       totalPage++;
/*     */     }
/*  83 */     return totalPage;
/*     */   }
/*     */ 
/*     */   public boolean isFirstPage()
/*     */   {
/*  90 */     return this.pageNo <= 1;
/*     */   }
/*     */ 
/*     */   public boolean isLastPage()
/*     */   {
/*  97 */     return this.pageNo >= getTotalPage();
/*     */   }
/*     */ 
/*     */   public int getNextPage()
/*     */   {
/* 104 */     if (isLastPage()) {
/* 105 */       return this.pageNo;
/*     */     }
/* 107 */     return this.pageNo + 1;
/*     */   }
/*     */ 
/*     */   public int getPrePage()
/*     */   {
/* 115 */     if (isFirstPage()) {
/* 116 */       return this.pageNo;
/*     */     }
/* 118 */     return this.pageNo - 1;
/*     */   }
/*     */ 
/*     */   public void setTotalCount(int totalCount)
/*     */   {
/* 135 */     if (totalCount < 0)
/* 136 */       this.totalCount = 0;
/*     */     else
/* 138 */       this.totalCount = totalCount;
/*     */   }
/*     */ 
/*     */   public void setPageSize(int pageSize)
/*     */   {
/* 148 */     if (pageSize < 1)
/* 149 */       this.pageSize = 10;
/*     */     else
/* 151 */       this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public void setPageNo(int pageNo)
/*     */   {
/* 161 */     if (pageNo < 1)
/* 162 */       this.pageNo = 1;
/*     */     else {
/* 164 */       this.pageNo = pageNo;
/*     */     }
/* 166 */     setBE();
/*     */   }
/*     */ 
/*     */   private void setBE()
/*     */   {
/* 173 */     if (getTotalPage() <= 10) {
/* 174 */       this.beginPageIndex = 1;
/* 175 */       this.endPageIndex = getTotalPage();
/*     */     }
/*     */     else
/*     */     {
/* 180 */       this.beginPageIndex = (this.pageNo - 4);
/* 181 */       this.endPageIndex = (this.pageNo + 5);
/*     */ 
/* 183 */       if (this.beginPageIndex < 1) {
/* 184 */         this.beginPageIndex = 1;
/* 185 */         this.endPageIndex = 10;
/*     */       }
/*     */ 
/* 188 */       if (this.endPageIndex > getTotalPage()) {
/* 189 */         this.endPageIndex = getTotalPage();
/* 190 */         this.beginPageIndex = (getTotalPage() - 10 + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getBeginPageIndex() {
/* 196 */     return this.beginPageIndex;
/*     */   }
/*     */ 
/*     */   public int getEndPageIndex()
/*     */   {
/* 202 */     return this.endPageIndex;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.page.SimplePage
 * JD-Core Version:    0.6.2
 */