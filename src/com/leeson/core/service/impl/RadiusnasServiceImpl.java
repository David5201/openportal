/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Radiusnas;
/*     */ import com.leeson.core.dao.RadiusnasDao;
/*     */ import com.leeson.core.query.RadiusnasQuery;
/*     */ import com.leeson.core.service.RadiusnasService;
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
/*     */ public class RadiusnasServiceImpl
/*     */   implements RadiusnasService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(RadiusnasServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   RadiusnasDao radiusnasDao;
/*     */ 
/*     */   public Long addRadiusnas(Radiusnas radiusnas)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.radiusnasDao.addRadiusnas(radiusnas);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addRadiusnas error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Radiusnas getRadiusnasByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.radiusnasDao.getRadiusnasByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getRadiusnasbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Radiusnas> getRadiusnasByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.radiusnasDao.getRadiusnasByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getRadiusnassByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.radiusnasDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(RadiusnasQuery radiusnasQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.radiusnasDao.deleteByQuery(radiusnasQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.radiusnasDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.radiusnasDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateRadiusnasByKey(Radiusnas radiusnas)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.radiusnasDao.updateRadiusnasByKey(radiusnas);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateRadiusnas error.Radiusnas:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateRadiusnasByKeyAll(Radiusnas radiusnas)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.radiusnasDao.updateRadiusnasByKeyAll(radiusnas);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateRadiusnasAll error.Radiusnas:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getRadiusnasListWithPage(RadiusnasQuery radiusnasQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.radiusnasDao.getRadiusnasListWithPage(radiusnasQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.radiusnasDao.getRadiusnasListCount(radiusnasQuery).intValue());
/* 164 */       page.setPageNo(radiusnasQuery.getPage());
/* 165 */       page.setPageSize(radiusnasQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Radiusnas pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Radiusnas> getRadiusnasList(RadiusnasQuery radiusnasQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.radiusnasDao.getRadiusnasList(radiusnasQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Radiusnas list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getRadiusnasCount(RadiusnasQuery radiusnasQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.radiusnasDao.getRadiusnasListCount(radiusnasQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Radiusnas list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.RadiusnasServiceImpl
 * JD-Core Version:    0.6.2
 */