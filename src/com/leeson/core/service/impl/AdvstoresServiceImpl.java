/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Advadv;
/*     */ import com.leeson.core.bean.Advstores;
/*     */ import com.leeson.core.dao.AdvstoresDao;
/*     */ import com.leeson.core.query.AdvadvQuery;
/*     */ import com.leeson.core.query.AdvstoresQuery;
/*     */ import com.leeson.core.service.AdvadvService;
/*     */ import com.leeson.core.service.AdvstoresService;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class AdvstoresServiceImpl
/*     */   implements AdvstoresService
/*     */ {
/*  31 */   private static final Log log = LogFactory.getLog(AdvstoresServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   AdvstoresDao advstoresDao;
/*     */ 
/*     */   @Autowired
/*     */   private AdvadvService advadvService;
/*     */ 
/*     */   public Long addAdvstores(Advstores advstores)
/*     */   {
/*     */     try
/*     */     {
/*  45 */       return this.advstoresDao.addAdvstores(advstores);
/*     */     } catch (SQLException e) {
/*  47 */       log.error("dao addAdvstores error.:" + e.getMessage(), e);
/*  48 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Advstores getAdvstoresByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  59 */       return this.advstoresDao.getAdvstoresByKey(id);
/*     */     } catch (SQLException e) {
/*  61 */       log.error("dao getAdvstoresbyKey error.:" + e.getMessage(), e);
/*  62 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advstores> getAdvstoresByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  69 */       return this.advstoresDao.getAdvstoresByKeys(idList);
/*     */     } catch (SQLException e) {
/*  71 */       log.error("dao getAdvstoressByKeys erorr." + e.getMessage(), e);
/*  72 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  84 */       AdvadvQuery aq = new AdvadvQuery();
/*  85 */       aq.setSid(id);
/*  86 */       List<Advadv> advs = this.advadvService.getAdvadvList(aq);
/*  87 */       for (Advadv adv : advs) {
/*  88 */         this.advadvService.deleteByKey(adv.getId());
/*     */       }
/*  90 */       return this.advstoresDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  92 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  93 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(AdvstoresQuery advstoresQuery)
/*     */   {
/*     */     try
/*     */     {
/* 104 */       return this.advstoresDao.deleteByQuery(advstoresQuery);
/*     */     } catch (SQLException e) {
/* 106 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/* 107 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 118 */       return this.advstoresDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 120 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 121 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 133 */       return this.advstoresDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 135 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 136 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvstoresByKey(Advstores advstores)
/*     */   {
/*     */     try
/*     */     {
/* 148 */       return this.advstoresDao.updateAdvstoresByKey(advstores);
/*     */     } catch (SQLException e) {
/* 150 */       log.error("dao updateAdvstores error.Advstores:" + e.getMessage(), e);
/* 151 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvstoresByKeyAll(Advstores advstores)
/*     */   {
/*     */     try
/*     */     {
/* 162 */       return this.advstoresDao.updateAdvstoresByKeyAll(advstores);
/*     */     } catch (SQLException e) {
/* 164 */       log.error("dao updateAdvstoresAll error.Advstores:" + e.getMessage(), e);
/* 165 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getAdvstoresListWithPage(AdvstoresQuery advstoresQuery)
/*     */   {
/*     */     try {
/* 172 */       Pagination page = new Pagination();
/* 173 */       page.setList(this.advstoresDao.getAdvstoresListWithPage(advstoresQuery));
/*     */ 
/* 175 */       page.setTotalCount(this.advstoresDao.getAdvstoresListCount(advstoresQuery).intValue());
/* 176 */       page.setPageNo(advstoresQuery.getPage());
/* 177 */       page.setPageSize(advstoresQuery.getPageSize());
/* 178 */       return page;
/*     */     } catch (Exception e) {
/* 180 */       log.error("get Advstores pagination error." + e.getMessage(), e);
/* 181 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advstores> getAdvstoresList(AdvstoresQuery advstoresQuery)
/*     */   {
/*     */     try
/*     */     {
/* 191 */       return this.advstoresDao.getAdvstoresList(advstoresQuery);
/*     */     } catch (SQLException e) {
/* 193 */       log.error("get Advstores list error." + e.getMessage(), e);
/* 194 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getAdvstoresCount(AdvstoresQuery advstoresQuery)
/*     */   {
/*     */     try
/*     */     {
/* 202 */       return this.advstoresDao.getAdvstoresListCount(advstoresQuery);
/*     */     } catch (SQLException e) {
/* 204 */       log.error("get Advstores list Count." + e.getMessage(), e);
/* 205 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.AdvstoresServiceImpl
 * JD-Core Version:    0.6.2
 */