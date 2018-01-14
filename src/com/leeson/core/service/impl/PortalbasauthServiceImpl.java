/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalbasauth;
/*     */ import com.leeson.core.dao.PortalbasauthDao;
/*     */ import com.leeson.core.query.PortalbasauthQuery;
/*     */ import com.leeson.core.service.PortalbasauthService;
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
/*     */ public class PortalbasauthServiceImpl
/*     */   implements PortalbasauthService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalbasauthServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalbasauthDao portalbasauthDao;
/*     */ 
/*     */   public Long addPortalbasauth(Portalbasauth portalbasauth)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalbasauthDao.addPortalbasauth(portalbasauth);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalbasauth error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalbasauth getPortalbasauthByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalbasauthDao.getPortalbasauthByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalbasauthbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalbasauth> getPortalbasauthByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalbasauthDao.getPortalbasauthByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalbasauthsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalbasauthDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalbasauthQuery portalbasauthQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalbasauthDao.deleteByQuery(portalbasauthQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalbasauthDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalbasauthByKey(Portalbasauth portalbasauth)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalbasauthDao.updatePortalbasauthByKey(portalbasauth);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalbasauth error.Portalbasauth:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalbasauthByKeyAll(Portalbasauth portalbasauth)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalbasauthDao.updatePortalbasauthByKeyAll(portalbasauth);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalbasauthAll error.Portalbasauth:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalbasauthListWithPage(PortalbasauthQuery portalbasauthQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalbasauthDao.getPortalbasauthListWithPage(portalbasauthQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalbasauthDao.getPortalbasauthListCount(portalbasauthQuery).intValue());
/* 149 */       page.setPageNo(portalbasauthQuery.getPage());
/* 150 */       page.setPageSize(portalbasauthQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalbasauth pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalbasauth> getPortalbasauthList(PortalbasauthQuery portalbasauthQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalbasauthDao.getPortalbasauthList(portalbasauthQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalbasauth list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalbasauthCount(PortalbasauthQuery portalbasauthQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalbasauthDao.getPortalbasauthListCount(portalbasauthQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalbasauth list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalbasauthServiceImpl
 * JD-Core Version:    0.6.2
 */