/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.dao.PortalaccountDao;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
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
/*     */ public class PortalaccountServiceImpl
/*     */   implements PortalaccountService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalaccountServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalaccountDao portalaccountDao;
/*     */ 
/*     */   public Long addPortalaccount(Portalaccount portalaccount)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalaccountDao.addPortalaccount(portalaccount);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalaccount error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalaccount getPortalaccountByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalaccountDao.getPortalaccountByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalaccountbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalaccount> getPortalaccountByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalaccountDao.getPortalaccountByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalaccountsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalaccountDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalaccountQuery portalaccountQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalaccountDao.deleteByQuery(portalaccountQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalaccountDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalaccountDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalaccountByKey(Portalaccount portalaccount)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalaccountDao.updatePortalaccountByKey(portalaccount);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalaccount error.Portalaccount:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalaccountByKeyAll(Portalaccount portalaccount)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalaccountDao.updatePortalaccountByKeyAll(portalaccount);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalaccountAll error.Portalaccount:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalaccountListWithPage(PortalaccountQuery portalaccountQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalaccountDao.getPortalaccountListWithPage(portalaccountQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalaccountDao.getPortalaccountListCount(portalaccountQuery).intValue());
/* 164 */       page.setPageNo(portalaccountQuery.getPage());
/* 165 */       page.setPageSize(portalaccountQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalaccount pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalaccount> getPortalaccountList(PortalaccountQuery portalaccountQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalaccountDao.getPortalaccountList(portalaccountQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalaccount list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalaccountCount(PortalaccountQuery portalaccountQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalaccountDao.getPortalaccountListCount(portalaccountQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalaccount list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalaccountServiceImpl
 * JD-Core Version:    0.6.2
 */