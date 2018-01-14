/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portallinkrecordall;
/*     */ import com.leeson.core.dao.PortallinkrecordallDao;
/*     */ import com.leeson.core.query.PortallinkrecordallQuery;
/*     */ import com.leeson.core.service.PortallinkrecordallService;
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
/*     */ public class PortallinkrecordallServiceImpl
/*     */   implements PortallinkrecordallService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortallinkrecordallServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortallinkrecordallDao portallinkrecordallDao;
/*     */ 
/*     */   public Long addPortallinkrecordall(Portallinkrecordall portallinkrecordall)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portallinkrecordallDao.addPortallinkrecordall(portallinkrecordall);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortallinkrecordall error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portallinkrecordall getPortallinkrecordallByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portallinkrecordallDao.getPortallinkrecordallByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortallinkrecordallbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallinkrecordall> getPortallinkrecordallByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portallinkrecordallDao.getPortallinkrecordallByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortallinkrecordallsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portallinkrecordallDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortallinkrecordallQuery portallinkrecordallQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portallinkrecordallDao.deleteByQuery(portallinkrecordallQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portallinkrecordallDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portallinkrecordallDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortallinkrecordallByKey(Portallinkrecordall portallinkrecordall)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portallinkrecordallDao.updatePortallinkrecordallByKey(portallinkrecordall);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortallinkrecordall error.Portallinkrecordall:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortallinkrecordallByKeyAll(Portallinkrecordall portallinkrecordall)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portallinkrecordallDao.updatePortallinkrecordallByKeyAll(portallinkrecordall);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortallinkrecordallAll error.Portallinkrecordall:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortallinkrecordallListWithPage(PortallinkrecordallQuery portallinkrecordallQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portallinkrecordallDao.getPortallinkrecordallListWithPage(portallinkrecordallQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portallinkrecordallDao.getPortallinkrecordallListCount(portallinkrecordallQuery).intValue());
/* 164 */       page.setPageNo(portallinkrecordallQuery.getPage());
/* 165 */       page.setPageSize(portallinkrecordallQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portallinkrecordall pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallinkrecordall> getPortallinkrecordallList(PortallinkrecordallQuery portallinkrecordallQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portallinkrecordallDao.getPortallinkrecordallList(portallinkrecordallQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portallinkrecordall list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortallinkrecordallCount(PortallinkrecordallQuery portallinkrecordallQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portallinkrecordallDao.getPortallinkrecordallListCount(portallinkrecordallQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portallinkrecordall list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallinkrecordallServiceImpl
 * JD-Core Version:    0.6.2
 */