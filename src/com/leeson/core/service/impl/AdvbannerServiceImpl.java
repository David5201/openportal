/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Advbanner;
/*     */ import com.leeson.core.dao.AdvbannerDao;
/*     */ import com.leeson.core.query.AdvbannerQuery;
/*     */ import com.leeson.core.service.AdvbannerService;
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
/*     */ public class AdvbannerServiceImpl
/*     */   implements AdvbannerService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(AdvbannerServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   AdvbannerDao advbannerDao;
/*     */ 
/*     */   public Long addAdvbanner(Advbanner advbanner)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.advbannerDao.addAdvbanner(advbanner);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addAdvbanner error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Advbanner getAdvbannerByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.advbannerDao.getAdvbannerByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getAdvbannerbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advbanner> getAdvbannerByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.advbannerDao.getAdvbannerByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getAdvbannersByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.advbannerDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(AdvbannerQuery advbannerQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.advbannerDao.deleteByQuery(advbannerQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.advbannerDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.advbannerDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvbannerByKey(Advbanner advbanner)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.advbannerDao.updateAdvbannerByKey(advbanner);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateAdvbanner error.Advbanner:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvbannerByKeyAll(Advbanner advbanner)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.advbannerDao.updateAdvbannerByKeyAll(advbanner);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateAdvbannerAll error.Advbanner:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getAdvbannerListWithPage(AdvbannerQuery advbannerQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.advbannerDao.getAdvbannerListWithPage(advbannerQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.advbannerDao.getAdvbannerListCount(advbannerQuery).intValue());
/* 164 */       page.setPageNo(advbannerQuery.getPage());
/* 165 */       page.setPageSize(advbannerQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Advbanner pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advbanner> getAdvbannerList(AdvbannerQuery advbannerQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.advbannerDao.getAdvbannerList(advbannerQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Advbanner list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getAdvbannerCount(AdvbannerQuery advbannerQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.advbannerDao.getAdvbannerListCount(advbannerQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Advbanner list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.AdvbannerServiceImpl
 * JD-Core Version:    0.6.2
 */