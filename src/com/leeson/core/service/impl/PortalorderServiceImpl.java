/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalorder;
/*     */ import com.leeson.core.dao.PortalorderDao;
/*     */ import com.leeson.core.query.PortalorderQuery;
/*     */ import com.leeson.core.service.PortalorderService;
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
/*     */ public class PortalorderServiceImpl
/*     */   implements PortalorderService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalorderServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalorderDao portalorderDao;
/*     */ 
/*     */   public Long addPortalorder(Portalorder portalorder)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalorderDao.addPortalorder(portalorder);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalorder error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalorder getPortalorderByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalorderDao.getPortalorderByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalorderbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalorder> getPortalorderByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalorderDao.getPortalorderByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalordersByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalorderDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalorderQuery portalorderQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalorderDao.deleteByQuery(portalorderQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalorderDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalorderDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalorderByKey(Portalorder portalorder)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalorderDao.updatePortalorderByKey(portalorder);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalorder error.Portalorder:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalorderByKeyAll(Portalorder portalorder)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalorderDao.updatePortalorderByKeyAll(portalorder);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalorderAll error.Portalorder:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalorderListWithPage(PortalorderQuery portalorderQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalorderDao.getPortalorderListWithPage(portalorderQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalorderDao.getPortalorderListCount(portalorderQuery).intValue());
/* 164 */       page.setPageNo(portalorderQuery.getPage());
/* 165 */       page.setPageSize(portalorderQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalorder pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalorder> getPortalorderList(PortalorderQuery portalorderQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalorderDao.getPortalorderList(portalorderQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalorder list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalorderCount(PortalorderQuery portalorderQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalorderDao.getPortalorderListCount(portalorderQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalorder list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalorderServiceImpl
 * JD-Core Version:    0.6.2
 */