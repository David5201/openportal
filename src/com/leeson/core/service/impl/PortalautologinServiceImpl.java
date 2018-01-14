/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalautologin;
/*     */ import com.leeson.core.dao.PortalautologinDao;
/*     */ import com.leeson.core.query.PortalautologinQuery;
/*     */ import com.leeson.core.service.PortalautologinService;
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
/*     */ public class PortalautologinServiceImpl
/*     */   implements PortalautologinService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalautologinServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalautologinDao portalautologinDao;
/*     */ 
/*     */   public Long addPortalautologin(Portalautologin portalautologin)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalautologinDao.addPortalautologin(portalautologin);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalautologin error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalautologin getPortalautologinByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalautologinDao.getPortalautologinByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalautologinbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalautologin> getPortalautologinByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalautologinDao.getPortalautologinByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalautologinsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalautologinDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalautologinQuery portalautologinQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalautologinDao.deleteByQuery(portalautologinQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalautologinDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalautologinByKey(Portalautologin portalautologin)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalautologinDao.updatePortalautologinByKey(portalautologin);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalautologin error.Portalautologin:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalautologinByKeyAll(Portalautologin portalautologin)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalautologinDao.updatePortalautologinByKeyAll(portalautologin);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalautologinAll error.Portalautologin:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalautologinListWithPage(PortalautologinQuery portalautologinQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalautologinDao.getPortalautologinListWithPage(portalautologinQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalautologinDao.getPortalautologinListCount(portalautologinQuery).intValue());
/* 149 */       page.setPageNo(portalautologinQuery.getPage());
/* 150 */       page.setPageSize(portalautologinQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalautologin pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalautologin> getPortalautologinList(PortalautologinQuery portalautologinQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalautologinDao.getPortalautologinList(portalautologinQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalautologin list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalautologinCount(PortalautologinQuery portalautologinQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalautologinDao.getPortalautologinListCount(portalautologinQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalautologin list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalautologinServiceImpl
 * JD-Core Version:    0.6.2
 */