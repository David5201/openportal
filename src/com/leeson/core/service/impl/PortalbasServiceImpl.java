/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.dao.PortalbasDao;
/*     */ import com.leeson.core.query.PortalbasQuery;
/*     */ import com.leeson.core.service.PortalbasService;
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
/*     */ public class PortalbasServiceImpl
/*     */   implements PortalbasService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalbasServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalbasDao portalbasDao;
/*     */ 
/*     */   public Long addPortalbas(Portalbas portalbas)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalbasDao.addPortalbas(portalbas);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalbas error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalbas getPortalbasByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalbasDao.getPortalbasByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalbasbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalbas> getPortalbasByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalbasDao.getPortalbasByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalbassByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalbasDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalbasQuery portalbasQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalbasDao.deleteByQuery(portalbasQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalbasDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalbasDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalbasByKey(Portalbas portalbas)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalbasDao.updatePortalbasByKey(portalbas);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalbas error.Portalbas:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalbasByKeyAll(Portalbas portalbas)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalbasDao.updatePortalbasByKeyAll(portalbas);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalbasAll error.Portalbas:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalbasListWithPage(PortalbasQuery portalbasQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalbasDao.getPortalbasListWithPage(portalbasQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalbasDao.getPortalbasListCount(portalbasQuery).intValue());
/* 164 */       page.setPageNo(portalbasQuery.getPage());
/* 165 */       page.setPageSize(portalbasQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalbas pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalbas> getPortalbasList(PortalbasQuery portalbasQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalbasDao.getPortalbasList(portalbasQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalbas list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalbasCount(PortalbasQuery portalbasQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalbasDao.getPortalbasListCount(portalbasQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalbas list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalbasServiceImpl
 * JD-Core Version:    0.6.2
 */