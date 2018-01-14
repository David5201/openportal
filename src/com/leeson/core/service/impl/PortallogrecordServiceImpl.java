/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.dao.PortallogrecordDao;
/*     */ import com.leeson.core.query.PortallogrecordQuery;
/*     */ import com.leeson.core.service.PortallogrecordService;
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
/*     */ public class PortallogrecordServiceImpl
/*     */   implements PortallogrecordService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortallogrecordServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortallogrecordDao portallogrecordDao;
/*     */ 
/*     */   public Long addPortallogrecord(Portallogrecord portallogrecord)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portallogrecordDao.addPortallogrecord(portallogrecord);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortallogrecord error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portallogrecord getPortallogrecordByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portallogrecordDao.getPortallogrecordByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortallogrecordbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallogrecord> getPortallogrecordByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portallogrecordDao.getPortallogrecordByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortallogrecordsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portallogrecordDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortallogrecordQuery portallogrecord)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portallogrecordDao.deleteByQuery(portallogrecord);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portallogrecordDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portallogrecordDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortallogrecordByKey(Portallogrecord portallogrecord)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portallogrecordDao.updatePortallogrecordByKey(portallogrecord);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortallogrecord error.Portallogrecord:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortallogrecordListWithPage(PortallogrecordQuery portallogrecordQuery)
/*     */   {
/*     */     try {
/* 146 */       Pagination page = new Pagination();
/* 147 */       page.setList(this.portallogrecordDao.getPortallogrecordListWithPage(portallogrecordQuery));
/*     */ 
/* 149 */       page.setTotalCount(this.portallogrecordDao.getPortallogrecordListCount(portallogrecordQuery).intValue());
/* 150 */       page.setPageNo(portallogrecordQuery.getPage());
/* 151 */       page.setPageSize(portallogrecordQuery.getPageSize());
/* 152 */       return page;
/*     */     } catch (Exception e) {
/* 154 */       log.error("get Portallogrecord pagination error." + e.getMessage(), e);
/* 155 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallogrecord> getPortallogrecordList(PortallogrecordQuery portallogrecordQuery)
/*     */   {
/*     */     try
/*     */     {
/* 165 */       return this.portallogrecordDao.getPortallogrecordList(portallogrecordQuery);
/*     */     } catch (SQLException e) {
/* 167 */       log.error("get Portallogrecord list error." + e.getMessage(), e);
/* 168 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortallogrecordCount(PortallogrecordQuery portallogrecordQuery)
/*     */   {
/*     */     try
/*     */     {
/* 176 */       return this.portallogrecordDao.getPortallogrecordListCount(portallogrecordQuery);
/*     */     } catch (SQLException e) {
/* 178 */       log.error("get Portallogrecord list Count." + e.getMessage(), e);
/* 179 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallogrecordServiceImpl
 * JD-Core Version:    0.6.2
 */