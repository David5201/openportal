/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalaccountmacs;
/*     */ import com.leeson.core.dao.PortalaccountmacsDao;
/*     */ import com.leeson.core.query.PortalaccountmacsQuery;
/*     */ import com.leeson.core.service.PortalaccountmacsService;
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
/*     */ public class PortalaccountmacsServiceImpl
/*     */   implements PortalaccountmacsService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalaccountmacsServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalaccountmacsDao portalaccountmacsDao;
/*     */ 
/*     */   public Long addPortalaccountmacs(Portalaccountmacs portalaccountmacs)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalaccountmacsDao.addPortalaccountmacs(portalaccountmacs);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalaccountmacs error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalaccountmacs getPortalaccountmacsByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalaccountmacsDao.getPortalaccountmacsByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalaccountmacsbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalaccountmacs> getPortalaccountmacsByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalaccountmacsDao.getPortalaccountmacsByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalaccountmacssByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalaccountmacsDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalaccountmacsQuery portalaccountmacs)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalaccountmacsDao.deleteByQuery(portalaccountmacs);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalaccountmacsDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalaccountmacsByKey(Portalaccountmacs portalaccountmacs)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalaccountmacsDao.updatePortalaccountmacsByKey(portalaccountmacs);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalaccountmacs error.Portalaccountmacs:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalaccountmacsByKeyAll(Portalaccountmacs portalaccountmacs)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalaccountmacsDao.updatePortalaccountmacsByKeyAll(portalaccountmacs);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalaccountmacsAll error.Portalaccountmacs:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalaccountmacsListWithPage(PortalaccountmacsQuery portalaccountmacsQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalaccountmacsDao.getPortalaccountmacsListWithPage(portalaccountmacsQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalaccountmacsDao.getPortalaccountmacsListCount(portalaccountmacsQuery).intValue());
/* 149 */       page.setPageNo(portalaccountmacsQuery.getPage());
/* 150 */       page.setPageSize(portalaccountmacsQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalaccountmacs pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalaccountmacs> getPortalaccountmacsList(PortalaccountmacsQuery portalaccountmacsQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalaccountmacsDao.getPortalaccountmacsList(portalaccountmacsQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalaccountmacs list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalaccountmacsCount(PortalaccountmacsQuery portalaccountmacsQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalaccountmacsDao.getPortalaccountmacsListCount(portalaccountmacsQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalaccountmacs list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalaccountmacsServiceImpl
 * JD-Core Version:    0.6.2
 */