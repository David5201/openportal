/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Advpic;
/*     */ import com.leeson.core.dao.AdvpicDao;
/*     */ import com.leeson.core.query.AdvpicQuery;
/*     */ import com.leeson.core.service.AdvpicService;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class AdvpicServiceImpl
/*     */   implements AdvpicService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(AdvpicServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   AdvpicDao advpicDao;
/*     */ 
/*     */   public Long addAdvpic(Advpic advpic)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.advpicDao.addAdvpic(advpic);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addAdvpic error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Advpic getAdvpicByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.advpicDao.getAdvpicByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getAdvpicbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advpic> getAdvpicByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.advpicDao.getAdvpicByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getAdvpicsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.advpicDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(AdvpicQuery advpicQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.advpicDao.deleteByQuery(advpicQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.advpicDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.advpicDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvpicByKey(Advpic advpic)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.advpicDao.updateAdvpicByKey(advpic);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateAdvpic error.Advpic:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvpicByKeyAll(Advpic advpic)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.advpicDao.updateAdvpicByKeyAll(advpic);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateAdvpicAll error.Advpic:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getAdvpicListWithPage(AdvpicQuery advpicQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.advpicDao.getAdvpicListWithPage(advpicQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.advpicDao.getAdvpicListCount(advpicQuery).intValue());
/* 164 */       page.setPageNo(advpicQuery.getPage());
/* 165 */       page.setPageSize(advpicQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Advpic pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advpic> getAdvpicList(AdvpicQuery advpicQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.advpicDao.getAdvpicList(advpicQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Advpic list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getAdvpicCount(AdvpicQuery advpicQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.advpicDao.getAdvpicListCount(advpicQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Advpic list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.AdvpicServiceImpl
 * JD-Core Version:    0.6.2
 */