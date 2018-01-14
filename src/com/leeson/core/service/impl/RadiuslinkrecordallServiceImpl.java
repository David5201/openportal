/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Radiuslinkrecordall;
/*     */ import com.leeson.core.dao.RadiuslinkrecordallDao;
/*     */ import com.leeson.core.query.RadiuslinkrecordallQuery;
/*     */ import com.leeson.core.service.RadiuslinkrecordallService;
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
/*     */ public class RadiuslinkrecordallServiceImpl
/*     */   implements RadiuslinkrecordallService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(RadiuslinkrecordallServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   RadiuslinkrecordallDao radiuslinkrecordallDao;
/*     */ 
/*     */   public Long addRadiuslinkrecordall(Radiuslinkrecordall radiuslinkrecordall)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.radiuslinkrecordallDao.addRadiuslinkrecordall(radiuslinkrecordall);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addRadiuslinkrecordall error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Radiuslinkrecordall getRadiuslinkrecordallByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.radiuslinkrecordallDao.getRadiuslinkrecordallByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getRadiuslinkrecordallbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Radiuslinkrecordall> getRadiuslinkrecordallByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.radiuslinkrecordallDao.getRadiuslinkrecordallByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getRadiuslinkrecordallsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.radiuslinkrecordallDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.radiuslinkrecordallDao.deleteByQuery(radiuslinkrecordallQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.radiuslinkrecordallDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.radiuslinkrecordallDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateRadiuslinkrecordallByKey(Radiuslinkrecordall radiuslinkrecordall)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.radiuslinkrecordallDao.updateRadiuslinkrecordallByKey(radiuslinkrecordall);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateRadiuslinkrecordall error.Radiuslinkrecordall:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateRadiuslinkrecordallByKeyAll(Radiuslinkrecordall radiuslinkrecordall)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.radiuslinkrecordallDao.updateRadiuslinkrecordallByKeyAll(radiuslinkrecordall);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateRadiuslinkrecordallAll error.Radiuslinkrecordall:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getRadiuslinkrecordallListWithPage(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.radiuslinkrecordallDao.getRadiuslinkrecordallListWithPage(radiuslinkrecordallQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.radiuslinkrecordallDao.getRadiuslinkrecordallListCount(radiuslinkrecordallQuery).intValue());
/* 164 */       page.setPageNo(radiuslinkrecordallQuery.getPage());
/* 165 */       page.setPageSize(radiuslinkrecordallQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Radiuslinkrecordall pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Radiuslinkrecordall> getRadiuslinkrecordallList(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.radiuslinkrecordallDao.getRadiuslinkrecordallList(radiuslinkrecordallQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Radiuslinkrecordall list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getRadiuslinkrecordallCount(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.radiuslinkrecordallDao.getRadiuslinkrecordallListCount(radiuslinkrecordallQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Radiuslinkrecordall list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.RadiuslinkrecordallServiceImpl
 * JD-Core Version:    0.6.2
 */