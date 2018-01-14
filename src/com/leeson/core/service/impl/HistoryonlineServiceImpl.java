/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Historyonline;
/*     */ import com.leeson.core.dao.HistoryonlineDao;
/*     */ import com.leeson.core.query.HistoryonlineQuery;
/*     */ import com.leeson.core.service.HistoryonlineService;
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
/*     */ public class HistoryonlineServiceImpl
/*     */   implements HistoryonlineService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(HistoryonlineServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   HistoryonlineDao historyonlineDao;
/*     */ 
/*     */   public Long addHistoryonline(Historyonline historyonline)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.historyonlineDao.addHistoryonline(historyonline);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addHistoryonline error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Historyonline getHistoryonlineByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.historyonlineDao.getHistoryonlineByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getHistoryonlinebyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Historyonline> getHistoryonlineByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.historyonlineDao.getHistoryonlineByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getHistoryonlinesByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.historyonlineDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(HistoryonlineQuery historyonlineQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.historyonlineDao.deleteByQuery(historyonlineQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.historyonlineDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.historyonlineDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateHistoryonlineByKey(Historyonline historyonline)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.historyonlineDao.updateHistoryonlineByKey(historyonline);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateHistoryonline error.Historyonline:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateHistoryonlineByKeyAll(Historyonline historyonline)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.historyonlineDao.updateHistoryonlineByKeyAll(historyonline);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateHistoryonlineAll error.Historyonline:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getHistoryonlineListWithPage(HistoryonlineQuery historyonlineQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.historyonlineDao.getHistoryonlineListWithPage(historyonlineQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.historyonlineDao.getHistoryonlineListCount(historyonlineQuery).intValue());
/* 164 */       page.setPageNo(historyonlineQuery.getPage());
/* 165 */       page.setPageSize(historyonlineQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Historyonline pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Historyonline> getHistoryonlineList(HistoryonlineQuery historyonlineQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.historyonlineDao.getHistoryonlineList(historyonlineQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Historyonline list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getHistoryonlineCount(HistoryonlineQuery historyonlineQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.historyonlineDao.getHistoryonlineListCount(historyonlineQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Historyonline list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.HistoryonlineServiceImpl
 * JD-Core Version:    0.6.2
 */