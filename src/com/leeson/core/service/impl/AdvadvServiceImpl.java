/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Advadv;
/*     */ import com.leeson.core.dao.AdvadvDao;
/*     */ import com.leeson.core.query.AdvadvQuery;
/*     */ import com.leeson.core.service.AdvadvService;
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
/*     */ public class AdvadvServiceImpl
/*     */   implements AdvadvService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(AdvadvServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   AdvadvDao advadvDao;
/*     */ 
/*     */   public Long addAdvadv(Advadv advadv)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.advadvDao.addAdvadv(advadv);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addAdvadv error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Advadv getAdvadvByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.advadvDao.getAdvadvByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getAdvadvbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advadv> getAdvadvByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.advadvDao.getAdvadvByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getAdvadvsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.advadvDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(AdvadvQuery advadvQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.advadvDao.deleteByQuery(advadvQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.advadvDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.advadvDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvadvByKey(Advadv advadv)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.advadvDao.updateAdvadvByKey(advadv);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateAdvadv error.Advadv:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateAdvadvByKeyAll(Advadv advadv)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.advadvDao.updateAdvadvByKeyAll(advadv);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateAdvadvAll error.Advadv:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getAdvadvListWithPage(AdvadvQuery advadvQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.advadvDao.getAdvadvListWithPage(advadvQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.advadvDao.getAdvadvListCount(advadvQuery).intValue());
/* 164 */       page.setPageNo(advadvQuery.getPage());
/* 165 */       page.setPageSize(advadvQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Advadv pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advadv> getAdvadvList(AdvadvQuery advadvQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.advadvDao.getAdvadvList(advadvQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Advadv list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getAdvadvCount(AdvadvQuery advadvQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.advadvDao.getAdvadvListCount(advadvQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Advadv list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.AdvadvServiceImpl
 * JD-Core Version:    0.6.2
 */