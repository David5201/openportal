/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portalonlinelimit;
/*     */ import com.leeson.core.dao.PortalonlinelimitDao;
/*     */ import com.leeson.core.query.PortalonlinelimitQuery;
/*     */ import com.leeson.core.service.PortalonlinelimitService;
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
/*     */ public class PortalonlinelimitServiceImpl
/*     */   implements PortalonlinelimitService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortalonlinelimitServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalonlinelimitDao portalonlinelimitDao;
/*     */ 
/*     */   public Long addPortalonlinelimit(Portalonlinelimit portalonlinelimit)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portalonlinelimitDao.addPortalonlinelimit(portalonlinelimit);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortalonlinelimit error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalonlinelimit getPortalonlinelimitByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portalonlinelimitDao.getPortalonlinelimitByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortalonlinelimitbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalonlinelimit> getPortalonlinelimitByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portalonlinelimitDao.getPortalonlinelimitByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortalonlinelimitsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portalonlinelimitDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalonlinelimitQuery portalonlinelimit)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portalonlinelimitDao.deleteByQuery(portalonlinelimit);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portalonlinelimitDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalonlinelimitByKey(Portalonlinelimit portalonlinelimit)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portalonlinelimitDao.updatePortalonlinelimitByKey(portalonlinelimit);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao updatePortalonlinelimit error.Portalonlinelimit:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalonlinelimitByKeyAll(Portalonlinelimit portalonlinelimit)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return this.portalonlinelimitDao.updatePortalonlinelimitByKeyAll(portalonlinelimit);
/*     */     } catch (SQLException e) {
/* 137 */       log.error("dao updatePortalonlinelimitAll error.Portalonlinelimit:" + e.getMessage(), e);
/* 138 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalonlinelimitListWithPage(PortalonlinelimitQuery portalonlinelimitQuery)
/*     */   {
/*     */     try {
/* 145 */       Pagination page = new Pagination();
/* 146 */       page.setList(this.portalonlinelimitDao.getPortalonlinelimitListWithPage(portalonlinelimitQuery));
/*     */ 
/* 148 */       page.setTotalCount(this.portalonlinelimitDao.getPortalonlinelimitListCount(portalonlinelimitQuery).intValue());
/* 149 */       page.setPageNo(portalonlinelimitQuery.getPage());
/* 150 */       page.setPageSize(portalonlinelimitQuery.getPageSize());
/* 151 */       return page;
/*     */     } catch (Exception e) {
/* 153 */       log.error("get Portalonlinelimit pagination error." + e.getMessage(), e);
/* 154 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalonlinelimit> getPortalonlinelimitList(PortalonlinelimitQuery portalonlinelimitQuery)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       return this.portalonlinelimitDao.getPortalonlinelimitList(portalonlinelimitQuery);
/*     */     } catch (SQLException e) {
/* 166 */       log.error("get Portalonlinelimit list error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalonlinelimitCount(PortalonlinelimitQuery portalonlinelimitQuery)
/*     */   {
/*     */     try
/*     */     {
/* 175 */       return this.portalonlinelimitDao.getPortalonlinelimitListCount(portalonlinelimitQuery);
/*     */     } catch (SQLException e) {
/* 177 */       log.error("get Portalonlinelimit list Count." + e.getMessage(), e);
/* 178 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalonlinelimitServiceImpl
 * JD-Core Version:    0.6.2
 */