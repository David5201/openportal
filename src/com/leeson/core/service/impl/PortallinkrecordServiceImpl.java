/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portallinkrecord;
/*     */ import com.leeson.core.dao.PortallinkrecordDao;
/*     */ import com.leeson.core.query.PortallinkrecordQuery;
/*     */ import com.leeson.core.service.PortallinkrecordService;
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
/*     */ public class PortallinkrecordServiceImpl
/*     */   implements PortallinkrecordService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortallinkrecordServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortallinkrecordDao portallinkrecordDao;
/*     */ 
/*     */   public Long addPortallinkrecord(Portallinkrecord portallinkrecord)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portallinkrecordDao.addPortallinkrecord(portallinkrecord);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortallinkrecord error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portallinkrecord getPortallinkrecordByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portallinkrecordDao.getPortallinkrecordByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortallinkrecordbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallinkrecord> getPortallinkrecordByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portallinkrecordDao.getPortallinkrecordByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortallinkrecordsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portallinkrecordDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortallinkrecordQuery portallinkrecordQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portallinkrecordDao.deleteByQuery(portallinkrecordQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portallinkrecordDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portallinkrecordDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortallinkrecordByKey(Portallinkrecord portallinkrecord)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portallinkrecordDao.updatePortallinkrecordByKey(portallinkrecord);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortallinkrecord error.Portallinkrecord:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortallinkrecordByKeyAll(Portallinkrecord portallinkrecord)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portallinkrecordDao.updatePortallinkrecordByKeyAll(portallinkrecord);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortallinkrecordAll error.Portallinkrecord:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortallinkrecordListWithPage(PortallinkrecordQuery portallinkrecordQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portallinkrecordDao.getPortallinkrecordListWithPage(portallinkrecordQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portallinkrecordDao.getPortallinkrecordListCount(portallinkrecordQuery).intValue());
/* 164 */       page.setPageNo(portallinkrecordQuery.getPage());
/* 165 */       page.setPageSize(portallinkrecordQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portallinkrecord pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portallinkrecord> getPortallinkrecordList(PortallinkrecordQuery portallinkrecordQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portallinkrecordDao.getPortallinkrecordList(portallinkrecordQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portallinkrecord list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortallinkrecordCount(PortallinkrecordQuery portallinkrecordQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portallinkrecordDao.getPortallinkrecordListCount(portallinkrecordQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portallinkrecord list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallinkrecordServiceImpl
 * JD-Core Version:    0.6.2
 */