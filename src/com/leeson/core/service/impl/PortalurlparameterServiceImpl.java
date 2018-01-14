/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalurlparameter;
/*     */ import com.leeson.core.dao.PortalurlparameterDao;
/*     */ import com.leeson.core.query.PortalurlparameterQuery;
/*     */ import com.leeson.core.service.PortalurlparameterService;
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
/*     */ public class PortalurlparameterServiceImpl
/*     */   implements PortalurlparameterService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalurlparameterServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalurlparameterDao portalurlparameterDao;
/*     */ 
/*     */   public Long addPortalurlparameter(Portalurlparameter portalurlparameter)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalurlparameterDao.addPortalurlparameter(portalurlparameter);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalurlparameter error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalurlparameter getPortalurlparameterByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalurlparameterDao.getPortalurlparameterByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalurlparameterbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalurlparameter> getPortalurlparameterByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalurlparameterDao.getPortalurlparameterByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalurlparametersByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalurlparameterDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalurlparameterQuery portalurlparameterQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalurlparameterDao.deleteByQuery(portalurlparameterQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalurlparameterDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalurlparameterByKey(Portalurlparameter portalurlparameter)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalurlparameterDao.updatePortalurlparameterByKey(portalurlparameter);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalurlparameter error.Portalurlparameter:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalurlparameterByKeyAll(Portalurlparameter portalurlparameter)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalurlparameterDao.updatePortalurlparameterByKeyAll(portalurlparameter);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalurlparameterAll error.Portalurlparameter:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalurlparameterListWithPage(PortalurlparameterQuery portalurlparameterQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalurlparameterDao.getPortalurlparameterListWithPage(portalurlparameterQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalurlparameterDao.getPortalurlparameterListCount(portalurlparameterQuery).intValue());
/* 149 */       page.setPageNo(portalurlparameterQuery.getPage());
/* 150 */       page.setPageSize(portalurlparameterQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalurlparameter pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalurlparameter> getPortalurlparameterList(PortalurlparameterQuery portalurlparameterQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalurlparameterDao.getPortalurlparameterList(portalurlparameterQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalurlparameter list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalurlparameterCount(PortalurlparameterQuery portalurlparameterQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalurlparameterDao.getPortalurlparameterListCount(portalurlparameterQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalurlparameter list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalurlparameterServiceImpl
 * JD-Core Version:    0.6.2
 */