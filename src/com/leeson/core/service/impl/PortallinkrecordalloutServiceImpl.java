/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portallinkrecordallout;
/*     */ import com.leeson.core.dao.PortallinkrecordalloutDao;
/*     */ import com.leeson.core.query.PortallinkrecordalloutQuery;
/*     */ import com.leeson.core.service.PortallinkrecordalloutService;
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
/*     */ public class PortallinkrecordalloutServiceImpl
/*     */   implements PortallinkrecordalloutService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortallinkrecordalloutServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortallinkrecordalloutDao portallinkrecordalloutDao;
/*     */ 
/*     */   public Long addPortallinkrecordallout(Portallinkrecordallout portallinkrecordallout)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portallinkrecordalloutDao.addPortallinkrecordallout(portallinkrecordallout);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortallinkrecordallout error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portallinkrecordallout getPortallinkrecordalloutByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portallinkrecordalloutDao.getPortallinkrecordalloutByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortallinkrecordalloutbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallinkrecordallout> getPortallinkrecordalloutByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portallinkrecordalloutDao.getPortallinkrecordalloutByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortallinkrecordalloutsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portallinkrecordalloutDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portallinkrecordalloutDao.deleteByQuery(portallinkrecordalloutQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portallinkrecordalloutDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portallinkrecordalloutDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortallinkrecordalloutByKey(Portallinkrecordallout portallinkrecordallout)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portallinkrecordalloutDao.updatePortallinkrecordalloutByKey(portallinkrecordallout);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortallinkrecordallout error.Portallinkrecordallout:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortallinkrecordalloutByKeyAll(Portallinkrecordallout portallinkrecordallout)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portallinkrecordalloutDao.updatePortallinkrecordalloutByKeyAll(portallinkrecordallout);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortallinkrecordalloutAll error.Portallinkrecordallout:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortallinkrecordalloutListWithPage(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portallinkrecordalloutDao.getPortallinkrecordalloutListWithPage(portallinkrecordalloutQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portallinkrecordalloutDao.getPortallinkrecordalloutListCount(portallinkrecordalloutQuery).intValue());
/* 164 */       page.setPageNo(portallinkrecordalloutQuery.getPage());
/* 165 */       page.setPageSize(portallinkrecordalloutQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portallinkrecordallout pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallinkrecordallout> getPortallinkrecordalloutList(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portallinkrecordalloutDao.getPortallinkrecordalloutList(portallinkrecordalloutQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portallinkrecordallout list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortallinkrecordalloutCount(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portallinkrecordalloutDao.getPortallinkrecordalloutListCount(portallinkrecordalloutQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portallinkrecordallout list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallinkrecordalloutServiceImpl
 * JD-Core Version:    0.6.2
 */