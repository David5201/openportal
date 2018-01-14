/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalrole;
/*     */ import com.leeson.core.dao.PortalroleDao;
/*     */ import com.leeson.core.query.PortalroleQuery;
/*     */ import com.leeson.core.service.PortalroleService;
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
/*     */ public class PortalroleServiceImpl
/*     */   implements PortalroleService
/*     */ {
/*  26 */   private static final Log log = LogFactory.getLog(PortalroleServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalroleDao portalroleDao;
/*     */ 
/*     */   public Long addPortalrole(Portalrole portalrole)
/*     */   {
/*     */     try
/*     */     {
/*  38 */       return this.portalroleDao.addPortalrole(portalrole);
/*     */     } catch (SQLException e) {
/*  40 */       log.error("dao addPortalrole error.:" + e.getMessage(), e);
/*  41 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalrole getPortalroleByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  52 */       return this.portalroleDao.getPortalroleByKey(id);
/*     */     } catch (SQLException e) {
/*  54 */       log.error("dao getPortalrolebyKey error.:" + e.getMessage(), e);
/*  55 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalrole> getPortalroleByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  62 */       return this.portalroleDao.getPortalroleByKeys(idList);
/*     */     } catch (SQLException e) {
/*  64 */       log.error("dao getPortalrolesByKeys erorr." + e.getMessage(), e);
/*  65 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  77 */       return this.portalroleDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  79 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  80 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalroleQuery portalrole)
/*     */   {
/*     */     try
/*     */     {
/*  91 */       return this.portalroleDao.deleteByQuery(portalrole);
/*     */     } catch (SQLException e) {
/*  93 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  94 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 105 */       return this.portalroleDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 107 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 108 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalroleByKey(Portalrole portalrole)
/*     */   {
/*     */     try
/*     */     {
/* 120 */       return this.portalroleDao.updatePortalroleByKey(portalrole);
/*     */     } catch (SQLException e) {
/* 122 */       log.error("dao updatePortalrole error.Portalrole:" + e.getMessage(), e);
/* 123 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalroleListWithPage(PortalroleQuery portalroleQuery)
/*     */   {
/*     */     try {
/* 130 */       Pagination page = new Pagination();
/* 131 */       page.setList(this.portalroleDao.getPortalroleListWithPage(portalroleQuery));
/*     */ 
/* 133 */       page.setTotalCount(this.portalroleDao.getPortalroleListCount(portalroleQuery).intValue());
/* 134 */       page.setPageNo(portalroleQuery.getPage());
/* 135 */       page.setPageSize(portalroleQuery.getPageSize());
/* 136 */       return page;
/*     */     } catch (Exception e) {
/* 138 */       log.error("get Portalrole pagination error." + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalrole> getPortalroleList(PortalroleQuery portalroleQuery)
/*     */   {
/*     */     try
/*     */     {
/* 149 */       return this.portalroleDao.getPortalroleList(portalroleQuery);
/*     */     } catch (SQLException e) {
/* 151 */       log.error("get Portalrole list error." + e.getMessage(), e);
/* 152 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalroleCount(PortalroleQuery portalroleQuery)
/*     */   {
/*     */     try
/*     */     {
/* 160 */       return this.portalroleDao.getPortalroleListCount(portalroleQuery);
/*     */     } catch (SQLException e) {
/* 162 */       log.error("get Portalrole list Count." + e.getMessage(), e);
/* 163 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalroleServiceImpl
 * JD-Core Version:    0.6.2
 */