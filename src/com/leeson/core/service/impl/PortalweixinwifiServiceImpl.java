/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalweixinwifi;
/*     */ import com.leeson.core.dao.PortalweixinwifiDao;
/*     */ import com.leeson.core.query.PortalweixinwifiQuery;
/*     */ import com.leeson.core.service.PortalweixinwifiService;
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
/*     */ public class PortalweixinwifiServiceImpl
/*     */   implements PortalweixinwifiService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalweixinwifiServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalweixinwifiDao portalweixinwifiDao;
/*     */ 
/*     */   public Long addPortalweixinwifi(Portalweixinwifi portalweixinwifi)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalweixinwifiDao.addPortalweixinwifi(portalweixinwifi);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalweixinwifi error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalweixinwifi getPortalweixinwifiByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalweixinwifiDao.getPortalweixinwifiByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalweixinwifibyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalweixinwifi> getPortalweixinwifiByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalweixinwifiDao.getPortalweixinwifiByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalweixinwifisByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalweixinwifiDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalweixinwifiQuery portalweixinwifi)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalweixinwifiDao.deleteByQuery(portalweixinwifi);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalweixinwifiDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalweixinwifiByKey(Portalweixinwifi portalweixinwifi)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalweixinwifiDao.updatePortalweixinwifiByKey(portalweixinwifi);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalweixinwifi error.Portalweixinwifi:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalweixinwifiByKeyAll(Portalweixinwifi portalweixinwifi)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalweixinwifiDao.updatePortalweixinwifiByKeyAll(portalweixinwifi);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalweixinwifiAll error.Portalweixinwifi:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalweixinwifiListWithPage(PortalweixinwifiQuery portalweixinwifiQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalweixinwifiDao.getPortalweixinwifiListWithPage(portalweixinwifiQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalweixinwifiDao.getPortalweixinwifiListCount(portalweixinwifiQuery).intValue());
/* 149 */       page.setPageNo(portalweixinwifiQuery.getPage());
/* 150 */       page.setPageSize(portalweixinwifiQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalweixinwifi pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalweixinwifi> getPortalweixinwifiList(PortalweixinwifiQuery portalweixinwifiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalweixinwifiDao.getPortalweixinwifiList(portalweixinwifiQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalweixinwifi list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalweixinwifiCount(PortalweixinwifiQuery portalweixinwifiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalweixinwifiDao.getPortalweixinwifiListCount(portalweixinwifiQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalweixinwifi list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalweixinwifiServiceImpl
 * JD-Core Version:    0.6.2
 */