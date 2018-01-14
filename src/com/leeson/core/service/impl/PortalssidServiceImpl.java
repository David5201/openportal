/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalssid;
/*     */ import com.leeson.core.dao.PortalssidDao;
/*     */ import com.leeson.core.query.PortalssidQuery;
/*     */ import com.leeson.core.service.PortalssidService;
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
/*     */ public class PortalssidServiceImpl
/*     */   implements PortalssidService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalssidServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalssidDao portalssidDao;
/*     */ 
/*     */   public Long addPortalssid(Portalssid portalssid)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalssidDao.addPortalssid(portalssid);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalssid error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalssid getPortalssidByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalssidDao.getPortalssidByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalssidbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalssid> getPortalssidByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalssidDao.getPortalssidByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalssidsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalssidDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalssidQuery portalssidQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalssidDao.deleteByQuery(portalssidQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalssidDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalssidByKey(Portalssid portalssid)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalssidDao.updatePortalssidByKey(portalssid);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalssid error.Portalssid:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalssidByKeyAll(Portalssid portalssid)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalssidDao.updatePortalssidByKeyAll(portalssid);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalssidAll error.Portalssid:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalssidListWithPage(PortalssidQuery portalssidQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalssidDao.getPortalssidListWithPage(portalssidQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalssidDao.getPortalssidListCount(portalssidQuery).intValue());
/* 149 */       page.setPageNo(portalssidQuery.getPage());
/* 150 */       page.setPageSize(portalssidQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalssid pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalssid> getPortalssidList(PortalssidQuery portalssidQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalssidDao.getPortalssidList(portalssidQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalssid list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalssidCount(PortalssidQuery portalssidQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalssidDao.getPortalssidListCount(portalssidQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalssid list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalssidServiceImpl
 * JD-Core Version:    0.6.2
 */