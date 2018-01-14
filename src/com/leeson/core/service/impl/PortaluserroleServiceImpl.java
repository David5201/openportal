/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portaluserrole;
/*     */ import com.leeson.core.dao.PortaluserroleDao;
/*     */ import com.leeson.core.query.PortaluserroleQuery;
/*     */ import com.leeson.core.service.PortaluserroleService;
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
/*     */ public class PortaluserroleServiceImpl
/*     */   implements PortaluserroleService
/*     */ {
/*  26 */   private static final Log log = LogFactory.getLog(PortaluserroleServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortaluserroleDao portaluserroleDao;
/*     */ 
/*     */   public Long addPortaluserrole(Portaluserrole portaluserrole)
/*     */   {
/*     */     try
/*     */     {
/*  38 */       return this.portaluserroleDao.addPortaluserrole(portaluserrole);
/*     */     } catch (SQLException e) {
/*  40 */       log.error("dao addPortaluserrole error.:" + e.getMessage(), e);
/*  41 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portaluserrole getPortaluserroleByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  52 */       return this.portaluserroleDao.getPortaluserroleByKey(id);
/*     */     } catch (SQLException e) {
/*  54 */       log.error("dao getPortaluserrolebyKey error.:" + e.getMessage(), e);
/*  55 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaluserrole> getPortaluserroleByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  62 */       return this.portaluserroleDao.getPortaluserroleByKeys(idList);
/*     */     } catch (SQLException e) {
/*  64 */       log.error("dao getPortaluserrolesByKeys erorr." + e.getMessage(), e);
/*  65 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  77 */       return this.portaluserroleDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  79 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  80 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortaluserroleQuery portaluserrole)
/*     */   {
/*     */     try
/*     */     {
/*  91 */       return this.portaluserroleDao.deleteByQuery(portaluserrole);
/*     */     } catch (SQLException e) {
/*  93 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  94 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 105 */       return this.portaluserroleDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 107 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 108 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortaluserroleByKey(Portaluserrole portaluserrole)
/*     */   {
/*     */     try
/*     */     {
/* 120 */       return this.portaluserroleDao.updatePortaluserroleByKey(portaluserrole);
/*     */     } catch (SQLException e) {
/* 122 */       log.error("dao updatePortaluserrole error.Portaluserrole:" + e.getMessage(), e);
/* 123 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortaluserroleListWithPage(PortaluserroleQuery portaluserroleQuery)
/*     */   {
/*     */     try {
/* 130 */       Pagination page = new Pagination();
/* 131 */       page.setList(this.portaluserroleDao.getPortaluserroleListWithPage(portaluserroleQuery));
/*     */ 
/* 133 */       page.setTotalCount(this.portaluserroleDao.getPortaluserroleListCount(portaluserroleQuery).intValue());
/* 134 */       page.setPageNo(portaluserroleQuery.getPage());
/* 135 */       page.setPageSize(portaluserroleQuery.getPageSize());
/* 136 */       return page;
/*     */     } catch (Exception e) {
/* 138 */       log.error("get Portaluserrole pagination error." + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaluserrole> getPortaluserroleList(PortaluserroleQuery portaluserroleQuery)
/*     */   {
/*     */     try
/*     */     {
/* 149 */       return this.portaluserroleDao.getPortaluserroleList(portaluserroleQuery);
/*     */     } catch (SQLException e) {
/* 151 */       log.error("get Portaluserrole list error." + e.getMessage(), e);
/* 152 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortaluserroleCount(PortaluserroleQuery portaluserroleQuery)
/*     */   {
/*     */     try
/*     */     {
/* 160 */       return this.portaluserroleDao.getPortaluserroleListCount(portaluserroleQuery);
/*     */     } catch (SQLException e) {
/* 162 */       log.error("get Portaluserrole list Count." + e.getMessage(), e);
/* 163 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaluserroleServiceImpl
 * JD-Core Version:    0.6.2
 */