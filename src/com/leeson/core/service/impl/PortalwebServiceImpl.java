/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalweb;
/*     */ import com.leeson.core.dao.PortalwebDao;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.PortalwebService;
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
/*     */ public class PortalwebServiceImpl
/*     */   implements PortalwebService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalwebServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalwebDao portalwebDao;
/*     */ 
/*     */   public Long addPortalweb(Portalweb portalweb)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalwebDao.addPortalweb(portalweb);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalweb error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalweb getPortalwebByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalwebDao.getPortalwebByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalwebbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalweb> getPortalwebByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalwebDao.getPortalwebByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalwebsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalwebDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalwebQuery portalwebQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalwebDao.deleteByQuery(portalwebQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalwebDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalwebDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalwebByKey(Portalweb portalweb)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalwebDao.updatePortalwebByKey(portalweb);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalweb error.Portalweb:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalwebByKeyAll(Portalweb portalweb)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalwebDao.updatePortalwebByKeyAll(portalweb);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalwebAll error.Portalweb:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalwebListWithPage(PortalwebQuery portalwebQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalwebDao.getPortalwebListWithPage(portalwebQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalwebDao.getPortalwebListCount(portalwebQuery).intValue());
/* 164 */       page.setPageNo(portalwebQuery.getPage());
/* 165 */       page.setPageSize(portalwebQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalweb pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalweb> getPortalwebList(PortalwebQuery portalwebQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalwebDao.getPortalwebList(portalwebQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalweb list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalwebCount(PortalwebQuery portalwebQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalwebDao.getPortalwebListCount(portalwebQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalweb list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalwebServiceImpl
 * JD-Core Version:    0.6.2
 */