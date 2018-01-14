/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalmessage;
/*     */ import com.leeson.core.dao.PortalmessageDao;
/*     */ import com.leeson.core.query.PortalmessageQuery;
/*     */ import com.leeson.core.service.PortalmessageService;
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
/*     */ public class PortalmessageServiceImpl
/*     */   implements PortalmessageService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalmessageServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalmessageDao portalmessageDao;
/*     */ 
/*     */   public Long addPortalmessage(Portalmessage portalmessage)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalmessageDao.addPortalmessage(portalmessage);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalmessage error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalmessage getPortalmessageByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalmessageDao.getPortalmessageByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalmessagebyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalmessage> getPortalmessageByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalmessageDao.getPortalmessageByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalmessagesByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalmessageDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalmessageQuery portalmessage)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalmessageDao.deleteByQuery(portalmessage);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalmessageDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalmessageByKey(Portalmessage portalmessage)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalmessageDao.updatePortalmessageByKey(portalmessage);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalmessage error.Portalmessage:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalmessageListWithPage(PortalmessageQuery portalmessageQuery)
/*     */   {
/*     */     try {
/* 131 */       Pagination page = new Pagination();
/* 132 */       page.setList(this.portalmessageDao.getPortalmessageListWithPage(portalmessageQuery));
/*     */ 
/* 134 */       page.setTotalCount(this.portalmessageDao.getPortalmessageListCount(portalmessageQuery).intValue());
/* 135 */       page.setPageNo(portalmessageQuery.getPage());
/* 136 */       page.setPageSize(portalmessageQuery.getPageSize());
/* 137 */       return page;
/*     */     } catch (Exception e) {
/* 139 */       log.error("get Portalmessage pagination error." + e.getMessage(), e);
/* 140 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalmessage> getPortalmessageList(PortalmessageQuery portalmessageQuery)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalmessageDao.getPortalmessageList(portalmessageQuery);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("get Portalmessage list error." + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalmessageCount(PortalmessageQuery portalmessageQuery)
/*     */   {
/*     */     try
/*     */     {
/* 161 */       return this.portalmessageDao.getPortalmessageListCount(portalmessageQuery);
/*     */     } catch (SQLException e) {
/* 163 */       log.error("get Portalmessage list Count." + e.getMessage(), e);
/* 164 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalmessageServiceImpl
 * JD-Core Version:    0.6.2
 */