/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalroleprivilege;
/*     */ import com.leeson.core.dao.PortalroleprivilegeDao;
/*     */ import com.leeson.core.query.PortalroleprivilegeQuery;
/*     */ import com.leeson.core.service.PortalroleprivilegeService;
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
/*     */ public class PortalroleprivilegeServiceImpl
/*     */   implements PortalroleprivilegeService
/*     */ {
/*  26 */   private static final Log log = LogFactory.getLog(PortalroleprivilegeServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalroleprivilegeDao portalroleprivilegeDao;
/*     */ 
/*     */   public Long addPortalroleprivilege(Portalroleprivilege portalroleprivilege)
/*     */   {
/*     */     try
/*     */     {
/*  38 */       return this.portalroleprivilegeDao.addPortalroleprivilege(portalroleprivilege);
/*     */     } catch (SQLException e) {
/*  40 */       log.error("dao addPortalroleprivilege error.:" + e.getMessage(), e);
/*  41 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalroleprivilege getPortalroleprivilegeByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  52 */       return this.portalroleprivilegeDao.getPortalroleprivilegeByKey(id);
/*     */     } catch (SQLException e) {
/*  54 */       log.error("dao getPortalroleprivilegebyKey error.:" + e.getMessage(), e);
/*  55 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalroleprivilege> getPortalroleprivilegeByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  62 */       return this.portalroleprivilegeDao.getPortalroleprivilegeByKeys(idList);
/*     */     } catch (SQLException e) {
/*  64 */       log.error("dao getPortalroleprivilegesByKeys erorr." + e.getMessage(), e);
/*  65 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  77 */       return this.portalroleprivilegeDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  79 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  80 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalroleprivilegeQuery portalroleprivilege)
/*     */   {
/*     */     try
/*     */     {
/*  91 */       return this.portalroleprivilegeDao.deleteByQuery(portalroleprivilege);
/*     */     } catch (SQLException e) {
/*  93 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  94 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 105 */       return this.portalroleprivilegeDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 107 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 108 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalroleprivilegeByKey(Portalroleprivilege portalroleprivilege)
/*     */   {
/*     */     try
/*     */     {
/* 120 */       return this.portalroleprivilegeDao.updatePortalroleprivilegeByKey(portalroleprivilege);
/*     */     } catch (SQLException e) {
/* 122 */       log.error("dao updatePortalroleprivilege error.Portalroleprivilege:" + e.getMessage(), e);
/* 123 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalroleprivilegeListWithPage(PortalroleprivilegeQuery portalroleprivilegeQuery)
/*     */   {
/*     */     try {
/* 130 */       Pagination page = new Pagination();
/* 131 */       page.setList(this.portalroleprivilegeDao.getPortalroleprivilegeListWithPage(portalroleprivilegeQuery));
/*     */ 
/* 133 */       page.setTotalCount(this.portalroleprivilegeDao.getPortalroleprivilegeListCount(portalroleprivilegeQuery).intValue());
/* 134 */       page.setPageNo(portalroleprivilegeQuery.getPage());
/* 135 */       page.setPageSize(portalroleprivilegeQuery.getPageSize());
/* 136 */       return page;
/*     */     } catch (Exception e) {
/* 138 */       log.error("get Portalroleprivilege pagination error." + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalroleprivilege> getPortalroleprivilegeList(PortalroleprivilegeQuery portalroleprivilegeQuery)
/*     */   {
/*     */     try
/*     */     {
/* 149 */       return this.portalroleprivilegeDao.getPortalroleprivilegeList(portalroleprivilegeQuery);
/*     */     } catch (SQLException e) {
/* 151 */       log.error("get Portalroleprivilege list error." + e.getMessage(), e);
/* 152 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalroleprivilegeCount(PortalroleprivilegeQuery portalroleprivilegeQuery)
/*     */   {
/*     */     try
/*     */     {
/* 160 */       return this.portalroleprivilegeDao.getPortalroleprivilegeListCount(portalroleprivilegeQuery);
/*     */     } catch (SQLException e) {
/* 162 */       log.error("get Portalroleprivilege list Count." + e.getMessage(), e);
/* 163 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalroleprivilegeServiceImpl
 * JD-Core Version:    0.6.2
 */