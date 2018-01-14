/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalap;
/*     */ import com.leeson.core.dao.PortalapDao;
/*     */ import com.leeson.core.query.PortalapQuery;
/*     */ import com.leeson.core.service.PortalapService;
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
/*     */ public class PortalapServiceImpl
/*     */   implements PortalapService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalapServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalapDao portalapDao;
/*     */ 
/*     */   public Long addPortalap(Portalap portalap)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalapDao.addPortalap(portalap);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalap error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalap getPortalapByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalapDao.getPortalapByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalapbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalap> getPortalapByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalapDao.getPortalapByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalapsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalapDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalapQuery portalapQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalapDao.deleteByQuery(portalapQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalapDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalapByKey(Portalap portalap)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalapDao.updatePortalapByKey(portalap);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalap error.Portalap:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalapByKeyAll(Portalap portalap)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalapDao.updatePortalapByKeyAll(portalap);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalapAll error.Portalap:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalapListWithPage(PortalapQuery portalapQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalapDao.getPortalapListWithPage(portalapQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalapDao.getPortalapListCount(portalapQuery).intValue());
/* 149 */       page.setPageNo(portalapQuery.getPage());
/* 150 */       page.setPageSize(portalapQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalap pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalap> getPortalapList(PortalapQuery portalapQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalapDao.getPortalapList(portalapQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalap list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalapCount(PortalapQuery portalapQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalapDao.getPortalapListCount(portalapQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalap list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalapServiceImpl
 * JD-Core Version:    0.6.2
 */