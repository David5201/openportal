/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalspeed;
/*     */ import com.leeson.core.dao.PortalspeedDao;
/*     */ import com.leeson.core.query.PortalspeedQuery;
/*     */ import com.leeson.core.service.PortalspeedService;
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
/*     */ public class PortalspeedServiceImpl
/*     */   implements PortalspeedService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalspeedServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalspeedDao portalspeedDao;
/*     */ 
/*     */   public Long addPortalspeed(Portalspeed portalspeed)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalspeedDao.addPortalspeed(portalspeed);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalspeed error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalspeed getPortalspeedByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalspeedDao.getPortalspeedByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalspeedbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalspeed> getPortalspeedByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalspeedDao.getPortalspeedByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalspeedsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalspeedDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalspeedQuery portalspeedQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalspeedDao.deleteByQuery(portalspeedQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalspeedDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalspeedByKey(Portalspeed portalspeed)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalspeedDao.updatePortalspeedByKey(portalspeed);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalspeed error.Portalspeed:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalspeedByKeyAll(Portalspeed portalspeed)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalspeedDao.updatePortalspeedByKeyAll(portalspeed);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalspeedAll error.Portalspeed:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalspeedListWithPage(PortalspeedQuery portalspeedQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalspeedDao.getPortalspeedListWithPage(portalspeedQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalspeedDao.getPortalspeedListCount(portalspeedQuery).intValue());
/* 149 */       page.setPageNo(portalspeedQuery.getPage());
/* 150 */       page.setPageSize(portalspeedQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalspeed pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalspeed> getPortalspeedList(PortalspeedQuery portalspeedQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalspeedDao.getPortalspeedList(portalspeedQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalspeed list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalspeedCount(PortalspeedQuery portalspeedQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalspeedDao.getPortalspeedListCount(portalspeedQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalspeed list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalspeedServiceImpl
 * JD-Core Version:    0.6.2
 */