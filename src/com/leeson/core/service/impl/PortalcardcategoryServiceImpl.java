/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalcardcategory;
/*     */ import com.leeson.core.dao.PortalcardcategoryDao;
/*     */ import com.leeson.core.query.PortalcardcategoryQuery;
/*     */ import com.leeson.core.service.PortalcardcategoryService;
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
/*     */ public class PortalcardcategoryServiceImpl
/*     */   implements PortalcardcategoryService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalcardcategoryServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalcardcategoryDao portalcardcategoryDao;
/*     */ 
/*     */   public Long addPortalcardcategory(Portalcardcategory portalcardcategory)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalcardcategoryDao.addPortalcardcategory(portalcardcategory);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalcardcategory error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalcardcategory getPortalcardcategoryByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalcardcategoryDao.getPortalcardcategoryByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalcardcategorybyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalcardcategory> getPortalcardcategoryByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalcardcategoryDao.getPortalcardcategoryByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalcardcategorysByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalcardcategoryDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalcardcategoryQuery portalcardcategoryQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalcardcategoryDao.deleteByQuery(portalcardcategoryQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalcardcategoryDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalcardcategoryDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalcardcategoryByKey(Portalcardcategory portalcardcategory)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalcardcategoryDao.updatePortalcardcategoryByKey(portalcardcategory);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalcardcategory error.Portalcardcategory:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalcardcategoryByKeyAll(Portalcardcategory portalcardcategory)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalcardcategoryDao.updatePortalcardcategoryByKeyAll(portalcardcategory);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalcardcategoryAll error.Portalcardcategory:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalcardcategoryListWithPage(PortalcardcategoryQuery portalcardcategoryQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalcardcategoryDao.getPortalcardcategoryListWithPage(portalcardcategoryQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalcardcategoryDao.getPortalcardcategoryListCount(portalcardcategoryQuery).intValue());
/* 164 */       page.setPageNo(portalcardcategoryQuery.getPage());
/* 165 */       page.setPageSize(portalcardcategoryQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalcardcategory pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalcardcategory> getPortalcardcategoryList(PortalcardcategoryQuery portalcardcategoryQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalcardcategoryDao.getPortalcardcategoryList(portalcardcategoryQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalcardcategory list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalcardcategoryCount(PortalcardcategoryQuery portalcardcategoryQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalcardcategoryDao.getPortalcardcategoryListCount(portalcardcategoryQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalcardcategory list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalcardcategoryServiceImpl
 * JD-Core Version:    0.6.2
 */