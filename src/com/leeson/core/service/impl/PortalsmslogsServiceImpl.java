/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalsmslogs;
/*     */ import com.leeson.core.dao.PortalsmslogsDao;
/*     */ import com.leeson.core.query.PortalsmslogsQuery;
/*     */ import com.leeson.core.service.PortalsmslogsService;
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
/*     */ public class PortalsmslogsServiceImpl
/*     */   implements PortalsmslogsService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalsmslogsServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalsmslogsDao portalsmslogsDao;
/*     */ 
/*     */   public Long addPortalsmslogs(Portalsmslogs portalsmslogs)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalsmslogsDao.addPortalsmslogs(portalsmslogs);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalsmslogs error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalsmslogs getPortalsmslogsByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalsmslogsDao.getPortalsmslogsByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalsmslogsbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalsmslogs> getPortalsmslogsByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalsmslogsDao.getPortalsmslogsByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalsmslogssByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalsmslogsDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalsmslogsQuery portalsmslogsQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalsmslogsDao.deleteByQuery(portalsmslogsQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalsmslogsDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalsmslogsDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalsmslogsByKey(Portalsmslogs portalsmslogs)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portalsmslogsDao.updatePortalsmslogsByKey(portalsmslogs);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortalsmslogs error.Portalsmslogs:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalsmslogsByKeyAll(Portalsmslogs portalsmslogs)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portalsmslogsDao.updatePortalsmslogsByKeyAll(portalsmslogs);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortalsmslogsAll error.Portalsmslogs:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalsmslogsListWithPage(PortalsmslogsQuery portalsmslogsQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portalsmslogsDao.getPortalsmslogsListWithPage(portalsmslogsQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portalsmslogsDao.getPortalsmslogsListCount(portalsmslogsQuery).intValue());
/* 164 */       page.setPageNo(portalsmslogsQuery.getPage());
/* 165 */       page.setPageSize(portalsmslogsQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portalsmslogs pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalsmslogs> getPortalsmslogsList(PortalsmslogsQuery portalsmslogsQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portalsmslogsDao.getPortalsmslogsList(portalsmslogsQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portalsmslogs list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalsmslogsCount(PortalsmslogsQuery portalsmslogsQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portalsmslogsDao.getPortalsmslogsListCount(portalsmslogsQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portalsmslogs list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalsmslogsServiceImpl
 * JD-Core Version:    0.6.2
 */