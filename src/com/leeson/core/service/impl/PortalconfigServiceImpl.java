/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalconfig;
/*     */ import com.leeson.core.dao.PortalconfigDao;
/*     */ import com.leeson.core.query.PortalconfigQuery;
/*     */ import com.leeson.core.service.PortalconfigService;
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
/*     */ public class PortalconfigServiceImpl
/*     */   implements PortalconfigService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalconfigServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalconfigDao portalconfigDao;
/*     */ 
/*     */   public Long addPortalconfig(Portalconfig portalconfig)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalconfigDao.addPortalconfig(portalconfig);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalconfig error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalconfig getPortalconfigByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalconfigDao.getPortalconfigByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalconfigbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalconfig> getPortalconfigByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalconfigDao.getPortalconfigByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalconfigsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalconfigDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalconfigQuery portalconfigQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalconfigDao.deleteByQuery(portalconfigQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalconfigDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalconfigDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalconfigByKey(Portalconfig portalconfig)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalconfigDao.updatePortalconfigByKey(portalconfig);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalconfig error.Portalconfig:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalconfigByKeyAll(Portalconfig portalconfig)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalconfigDao.updatePortalconfigByKeyAll(portalconfig);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalconfigAll error.Portalconfig:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalconfigListWithPage(PortalconfigQuery portalconfigQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalconfigDao.getPortalconfigListWithPage(portalconfigQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalconfigDao.getPortalconfigListCount(portalconfigQuery).intValue());
/* 164 */       page.setPageNo(portalconfigQuery.getPage());
/* 165 */       page.setPageSize(portalconfigQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalconfig pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalconfig> getPortalconfigList(PortalconfigQuery portalconfigQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalconfigDao.getPortalconfigList(portalconfigQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalconfig list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalconfigCount(PortalconfigQuery portalconfigQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalconfigDao.getPortalconfigListCount(portalconfigQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalconfig list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalconfigServiceImpl
 * JD-Core Version:    0.6.2
 */