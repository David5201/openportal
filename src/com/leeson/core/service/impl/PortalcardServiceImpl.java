/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalcard;
/*     */ import com.leeson.core.dao.PortalcardDao;
/*     */ import com.leeson.core.query.PortalcardQuery;
/*     */ import com.leeson.core.service.PortalcardService;
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
/*     */ public class PortalcardServiceImpl
/*     */   implements PortalcardService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalcardServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalcardDao portalcardDao;
/*     */ 
/*     */   public Long addPortalcard(Portalcard portalcard)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalcardDao.addPortalcard(portalcard);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalcard error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalcard getPortalcardByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalcardDao.getPortalcardByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalcardbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalcard> getPortalcardByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalcardDao.getPortalcardByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalcardsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalcardDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalcardQuery portalcardQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalcardDao.deleteByQuery(portalcardQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalcardDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalcardDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalcardByKey(Portalcard portalcard)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalcardDao.updatePortalcardByKey(portalcard);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalcard error.Portalcard:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalcardByKeyAll(Portalcard portalcard)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalcardDao.updatePortalcardByKeyAll(portalcard);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalcardAll error.Portalcard:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalcardListWithPage(PortalcardQuery portalcardQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalcardDao.getPortalcardListWithPage(portalcardQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalcardDao.getPortalcardListCount(portalcardQuery).intValue());
/* 164 */       page.setPageNo(portalcardQuery.getPage());
/* 165 */       page.setPageSize(portalcardQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalcard pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalcard> getPortalcardList(PortalcardQuery portalcardQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalcardDao.getPortalcardList(portalcardQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalcard list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalcardCount(PortalcardQuery portalcardQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalcardDao.getPortalcardListCount(portalcardQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalcard list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalcardSaleListWithPage(PortalcardQuery portalcardQuery)
/*     */   {
/*     */     try
/*     */     {
/* 201 */       Pagination page = new Pagination();
/* 202 */       page.setList(this.portalcardDao.getPortalcardSaleListWithPage(portalcardQuery));
/*     */ 
/* 204 */       page.setTotalCount(this.portalcardDao.getPortalcardSaleListCount(portalcardQuery).intValue());
/* 205 */       page.setPageNo(portalcardQuery.getPage());
/* 206 */       page.setPageSize(portalcardQuery.getPageSize());
/* 207 */       return page;
/*     */     } catch (Exception e) {
/* 209 */       log.error("get Portalcard pagination error." + e.getMessage(), e);
/* 210 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalcard> getPortalcardSaleList(PortalcardQuery portalcardQuery)
/*     */   {
/*     */     try
/*     */     {
/* 220 */       return this.portalcardDao.getPortalcardSaleList(portalcardQuery);
/*     */     } catch (SQLException e) {
/* 222 */       log.error("get Portalcard list error." + e.getMessage(), e);
/* 223 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalcardSaleCount(PortalcardQuery portalcardQuery)
/*     */   {
/*     */     try
/*     */     {
/* 231 */       return this.portalcardDao.getPortalcardSaleListCount(portalcardQuery);
/*     */     } catch (SQLException e) {
/* 233 */       log.error("get Portalcard list Count." + e.getMessage(), e);
/* 234 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalcardServiceImpl
 * JD-Core Version:    0.6.2
 */