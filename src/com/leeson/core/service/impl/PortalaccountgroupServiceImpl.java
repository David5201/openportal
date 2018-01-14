/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalaccountgroup;
/*     */ import com.leeson.core.dao.PortalaccountgroupDao;
/*     */ import com.leeson.core.query.PortalaccountgroupQuery;
/*     */ import com.leeson.core.service.PortalaccountgroupService;
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
/*     */ public class PortalaccountgroupServiceImpl
/*     */   implements PortalaccountgroupService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalaccountgroupServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalaccountgroupDao portalaccountgroupDao;
/*     */ 
/*     */   public Long addPortalaccountgroup(Portalaccountgroup portalaccountgroup)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalaccountgroupDao.addPortalaccountgroup(portalaccountgroup);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalaccountgroup error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalaccountgroup getPortalaccountgroupByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalaccountgroupDao.getPortalaccountgroupByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalaccountgroupbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalaccountgroup> getPortalaccountgroupByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalaccountgroupDao.getPortalaccountgroupByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalaccountgroupsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalaccountgroupDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalaccountgroupQuery portalaccountgroupQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalaccountgroupDao.deleteByQuery(portalaccountgroupQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalaccountgroupDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalaccountgroupDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalaccountgroupByKey(Portalaccountgroup portalaccountgroup)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalaccountgroupDao.updatePortalaccountgroupByKey(portalaccountgroup);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalaccountgroup error.Portalaccountgroup:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalaccountgroupByKeyAll(Portalaccountgroup portalaccountgroup)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalaccountgroupDao.updatePortalaccountgroupByKeyAll(portalaccountgroup);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalaccountgroupAll error.Portalaccountgroup:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalaccountgroupListWithPage(PortalaccountgroupQuery portalaccountgroupQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalaccountgroupDao.getPortalaccountgroupListWithPage(portalaccountgroupQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalaccountgroupDao.getPortalaccountgroupListCount(portalaccountgroupQuery).intValue());
/* 164 */       page.setPageNo(portalaccountgroupQuery.getPage());
/* 165 */       page.setPageSize(portalaccountgroupQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalaccountgroup pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalaccountgroup> getPortalaccountgroupList(PortalaccountgroupQuery portalaccountgroupQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalaccountgroupDao.getPortalaccountgroupList(portalaccountgroupQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalaccountgroup list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalaccountgroupCount(PortalaccountgroupQuery portalaccountgroupQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalaccountgroupDao.getPortalaccountgroupListCount(portalaccountgroupQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalaccountgroup list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalaccountgroupServiceImpl
 * JD-Core Version:    0.6.2
 */