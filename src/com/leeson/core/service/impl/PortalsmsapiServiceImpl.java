/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalsmsapi;
/*     */ import com.leeson.core.dao.PortalsmsapiDao;
/*     */ import com.leeson.core.query.PortalsmsapiQuery;
/*     */ import com.leeson.core.service.PortalsmsapiService;
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
/*     */ public class PortalsmsapiServiceImpl
/*     */   implements PortalsmsapiService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalsmsapiServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalsmsapiDao portalsmsapiDao;
/*     */ 
/*     */   public Long addPortalsmsapi(Portalsmsapi portalsmsapi)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalsmsapiDao.addPortalsmsapi(portalsmsapi);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalsmsapi error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalsmsapi getPortalsmsapiByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalsmsapiDao.getPortalsmsapiByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalsmsapibyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalsmsapi> getPortalsmsapiByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalsmsapiDao.getPortalsmsapiByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalsmsapisByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalsmsapiDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalsmsapiQuery portalsmsapiQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalsmsapiDao.deleteByQuery(portalsmsapiQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalsmsapiDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalsmsapiByKey(Portalsmsapi portalsmsapi)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalsmsapiDao.updatePortalsmsapiByKey(portalsmsapi);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalsmsapi error.Portalsmsapi:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalsmsapiByKeyAll(Portalsmsapi portalsmsapi)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalsmsapiDao.updatePortalsmsapiByKeyAll(portalsmsapi);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalsmsapiAll error.Portalsmsapi:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalsmsapiListWithPage(PortalsmsapiQuery portalsmsapiQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalsmsapiDao.getPortalsmsapiListWithPage(portalsmsapiQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalsmsapiDao.getPortalsmsapiListCount(portalsmsapiQuery).intValue());
/* 149 */       page.setPageNo(portalsmsapiQuery.getPage());
/* 150 */       page.setPageSize(portalsmsapiQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalsmsapi pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalsmsapi> getPortalsmsapiList(PortalsmsapiQuery portalsmsapiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalsmsapiDao.getPortalsmsapiList(portalsmsapiQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalsmsapi list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalsmsapiCount(PortalsmsapiQuery portalsmsapiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalsmsapiDao.getPortalsmsapiListCount(portalsmsapiQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalsmsapi list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalsmsapiServiceImpl
 * JD-Core Version:    0.6.2
 */