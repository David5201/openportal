/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalip;
/*     */ import com.leeson.core.dao.PortalipDao;
/*     */ import com.leeson.core.query.PortalipQuery;
/*     */ import com.leeson.core.service.PortalipService;
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
/*     */ public class PortalipServiceImpl
/*     */   implements PortalipService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalipServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalipDao portalipDao;
/*     */ 
/*     */   public Long addPortalip(Portalip portalip)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalipDao.addPortalip(portalip);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalip error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalip getPortalipByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalipDao.getPortalipByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalipbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalip> getPortalipByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalipDao.getPortalipByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalipsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalipDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalipQuery portalipQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalipDao.deleteByQuery(portalipQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalipDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalipDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalipByKey(Portalip portalip)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalipDao.updatePortalipByKey(portalip);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalip error.Portalip:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalipByKeyAll(Portalip portalip)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalipDao.updatePortalipByKeyAll(portalip);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalipAll error.Portalip:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalipListWithPage(PortalipQuery portalipQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalipDao.getPortalipListWithPage(portalipQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalipDao.getPortalipListCount(portalipQuery).intValue());
/* 164 */       page.setPageNo(portalipQuery.getPage());
/* 165 */       page.setPageSize(portalipQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalip pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalip> getPortalipList(PortalipQuery portalipQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalipDao.getPortalipList(portalipQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalip list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalipCount(PortalipQuery portalipQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalipDao.getPortalipListCount(portalipQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalip list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalipServiceImpl
 * JD-Core Version:    0.6.2
 */