/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Zsqhdapi;
/*     */ import com.leeson.core.dao.ZsqhdapiDao;
/*     */ import com.leeson.core.query.ZsqhdapiQuery;
/*     */ import com.leeson.core.service.ZsqhdapiService;
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
/*     */ public class ZsqhdapiServiceImpl
/*     */   implements ZsqhdapiService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(ZsqhdapiServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   ZsqhdapiDao zsqhdapiDao;
/*     */ 
/*     */   public Long addZsqhdapi(Zsqhdapi zsqhdapi)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.zsqhdapiDao.addZsqhdapi(zsqhdapi);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addZsqhdapi error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Zsqhdapi getZsqhdapiByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.zsqhdapiDao.getZsqhdapiByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getZsqhdapibyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Zsqhdapi> getZsqhdapiByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.zsqhdapiDao.getZsqhdapiByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getZsqhdapisByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.zsqhdapiDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(ZsqhdapiQuery zsqhdapiQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.zsqhdapiDao.deleteByQuery(zsqhdapiQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.zsqhdapiDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.zsqhdapiDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateZsqhdapiByKey(Zsqhdapi zsqhdapi)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.zsqhdapiDao.updateZsqhdapiByKey(zsqhdapi);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateZsqhdapi error.Zsqhdapi:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateZsqhdapiByKeyAll(Zsqhdapi zsqhdapi)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.zsqhdapiDao.updateZsqhdapiByKeyAll(zsqhdapi);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateZsqhdapiAll error.Zsqhdapi:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getZsqhdapiListWithPage(ZsqhdapiQuery zsqhdapiQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.zsqhdapiDao.getZsqhdapiListWithPage(zsqhdapiQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.zsqhdapiDao.getZsqhdapiListCount(zsqhdapiQuery).intValue());
/* 164 */       page.setPageNo(zsqhdapiQuery.getPage());
/* 165 */       page.setPageSize(zsqhdapiQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Zsqhdapi pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Zsqhdapi> getZsqhdapiList(ZsqhdapiQuery zsqhdapiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.zsqhdapiDao.getZsqhdapiList(zsqhdapiQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Zsqhdapi list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getZsqhdapiCount(ZsqhdapiQuery zsqhdapiQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.zsqhdapiDao.getZsqhdapiListCount(zsqhdapiQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Zsqhdapi list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.ZsqhdapiServiceImpl
 * JD-Core Version:    0.6.2
 */