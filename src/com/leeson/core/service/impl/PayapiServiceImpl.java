/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Payapi;
/*     */ import com.leeson.core.dao.PayapiDao;
/*     */ import com.leeson.core.query.PayapiQuery;
/*     */ import com.leeson.core.service.PayapiService;
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
/*     */ public class PayapiServiceImpl
/*     */   implements PayapiService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PayapiServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PayapiDao payapiDao;
/*     */ 
/*     */   public Long addPayapi(Payapi payapi)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.payapiDao.addPayapi(payapi);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPayapi error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Payapi getPayapiByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.payapiDao.getPayapiByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPayapibyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Payapi> getPayapiByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.payapiDao.getPayapiByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPayapisByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.payapiDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PayapiQuery payapiQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.payapiDao.deleteByQuery(payapiQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.payapiDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.payapiDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePayapiByKey(Payapi payapi)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.payapiDao.updatePayapiByKey(payapi);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePayapi error.Payapi:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePayapiByKeyAll(Payapi payapi)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.payapiDao.updatePayapiByKeyAll(payapi);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePayapiAll error.Payapi:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPayapiListWithPage(PayapiQuery payapiQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.payapiDao.getPayapiListWithPage(payapiQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.payapiDao.getPayapiListCount(payapiQuery).intValue());
/* 164 */       page.setPageNo(payapiQuery.getPage());
/* 165 */       page.setPageSize(payapiQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Payapi pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Payapi> getPayapiList(PayapiQuery payapiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.payapiDao.getPayapiList(payapiQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Payapi list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPayapiCount(PayapiQuery payapiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.payapiDao.getPayapiListCount(payapiQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Payapi list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PayapiServiceImpl
 * JD-Core Version:    0.6.2
 */