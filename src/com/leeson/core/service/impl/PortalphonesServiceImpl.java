/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalphones;
/*     */ import com.leeson.core.dao.PortalphonesDao;
/*     */ import com.leeson.core.query.PortalphonesQuery;
/*     */ import com.leeson.core.service.PortalphonesService;
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
/*     */ public class PortalphonesServiceImpl
/*     */   implements PortalphonesService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalphonesServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalphonesDao portalphonesDao;
/*     */ 
/*     */   public Long addPortalphones(Portalphones portalphones)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalphonesDao.addPortalphones(portalphones);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalphones error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalphones getPortalphonesByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalphonesDao.getPortalphonesByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalphonesbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalphones> getPortalphonesByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalphonesDao.getPortalphonesByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalphonessByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalphonesDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalphonesQuery portalphonesQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalphonesDao.deleteByQuery(portalphonesQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalphonesDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalphonesDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalphonesByKey(Portalphones portalphones)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalphonesDao.updatePortalphonesByKey(portalphones);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalphones error.Portalphones:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalphonesByKeyAll(Portalphones portalphones)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalphonesDao.updatePortalphonesByKeyAll(portalphones);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalphonesAll error.Portalphones:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalphonesListWithPage(PortalphonesQuery portalphonesQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalphonesDao.getPortalphonesListWithPage(portalphonesQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalphonesDao.getPortalphonesListCount(portalphonesQuery).intValue());
/* 164 */       page.setPageNo(portalphonesQuery.getPage());
/* 165 */       page.setPageSize(portalphonesQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalphones pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalphones> getPortalphonesList(PortalphonesQuery portalphonesQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalphonesDao.getPortalphonesList(portalphonesQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalphones list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalphonesCount(PortalphonesQuery portalphonesQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalphonesDao.getPortalphonesListCount(portalphonesQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalphones list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalphonesServiceImpl
 * JD-Core Version:    0.6.2
 */