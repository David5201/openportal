/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Radiuslinkrecordallout;
/*     */ import com.leeson.core.dao.RadiuslinkrecordalloutDao;
/*     */ import com.leeson.core.query.RadiuslinkrecordalloutQuery;
/*     */ import com.leeson.core.service.RadiuslinkrecordalloutService;
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
/*     */ public class RadiuslinkrecordalloutServiceImpl
/*     */   implements RadiuslinkrecordalloutService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(RadiuslinkrecordalloutServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   RadiuslinkrecordalloutDao radiuslinkrecordalloutDao;
/*     */ 
/*     */   public Long addRadiuslinkrecordallout(Radiuslinkrecordallout radiuslinkrecordallout)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.radiuslinkrecordalloutDao.addRadiuslinkrecordallout(radiuslinkrecordallout);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addRadiuslinkrecordallout error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Radiuslinkrecordallout getRadiuslinkrecordalloutByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getRadiuslinkrecordalloutbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Radiuslinkrecordallout> getRadiuslinkrecordalloutByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getRadiuslinkrecordalloutsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.radiuslinkrecordalloutDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.radiuslinkrecordalloutDao.deleteByQuery(radiuslinkrecordalloutQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.radiuslinkrecordalloutDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.radiuslinkrecordalloutDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateRadiuslinkrecordalloutByKey(Radiuslinkrecordallout radiuslinkrecordallout)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.radiuslinkrecordalloutDao.updateRadiuslinkrecordalloutByKey(radiuslinkrecordallout);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateRadiuslinkrecordallout error.Radiuslinkrecordallout:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateRadiuslinkrecordalloutByKeyAll(Radiuslinkrecordallout radiuslinkrecordallout)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.radiuslinkrecordalloutDao.updateRadiuslinkrecordalloutByKeyAll(radiuslinkrecordallout);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateRadiuslinkrecordalloutAll error.Radiuslinkrecordallout:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getRadiuslinkrecordalloutListWithPage(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutListWithPage(radiuslinkrecordalloutQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutListCount(radiuslinkrecordalloutQuery).intValue());
/* 164 */       page.setPageNo(radiuslinkrecordalloutQuery.getPage());
/* 165 */       page.setPageSize(radiuslinkrecordalloutQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Radiuslinkrecordallout pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Radiuslinkrecordallout> getRadiuslinkrecordalloutList(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutList(radiuslinkrecordalloutQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Radiuslinkrecordallout list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getRadiuslinkrecordalloutCount(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutListCount(radiuslinkrecordalloutQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Radiuslinkrecordallout list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.RadiuslinkrecordalloutServiceImpl
 * JD-Core Version:    0.6.2
 */