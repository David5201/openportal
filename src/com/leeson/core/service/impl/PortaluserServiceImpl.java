/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.dao.PortaluserDao;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.PortaluserService;
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
/*     */ public class PortaluserServiceImpl
/*     */   implements PortaluserService
/*     */ {
/*  26 */   private static final Log log = LogFactory.getLog(PortaluserServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortaluserDao portaluserDao;
/*     */ 
/*     */   public Long addPortaluser(Portaluser portaluser)
/*     */   {
/*     */     try
/*     */     {
/*  38 */       return this.portaluserDao.addPortaluser(portaluser);
/*     */     } catch (SQLException e) {
/*  40 */       log.error("dao addPortaluser error.:" + e.getMessage(), e);
/*  41 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portaluser getPortaluserByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  52 */       return this.portaluserDao.getPortaluserByKey(id);
/*     */     } catch (SQLException e) {
/*  54 */       log.error("dao getPortaluserbyKey error.:" + e.getMessage(), e);
/*  55 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaluser> getPortaluserByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  62 */       return this.portaluserDao.getPortaluserByKeys(idList);
/*     */     } catch (SQLException e) {
/*  64 */       log.error("dao getPortalusersByKeys erorr." + e.getMessage(), e);
/*  65 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  77 */       return this.portaluserDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  79 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  80 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortaluserQuery portaluser)
/*     */   {
/*     */     try
/*     */     {
/*  91 */       return this.portaluserDao.deleteByQuery(portaluser);
/*     */     } catch (SQLException e) {
/*  93 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  94 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 105 */       return this.portaluserDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 107 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 108 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortaluserByKey(Portaluser portaluser)
/*     */   {
/*     */     try
/*     */     {
/* 120 */       return this.portaluserDao.updatePortaluserByKey(portaluser);
/*     */     } catch (SQLException e) {
/* 122 */       log.error("dao updatePortaluser error.Portaluser:" + e.getMessage(), e);
/* 123 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortaluserByKeyAll(Portaluser portaluser)
/*     */   {
/*     */     try
/*     */     {
/* 134 */       return this.portaluserDao.updatePortaluserByKeyAll(portaluser);
/*     */     } catch (SQLException e) {
/* 136 */       log.error("dao updatePortaluserAll error.Portaluser:" + e.getMessage(), e);
/* 137 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortaluserListWithPage(PortaluserQuery portaluserQuery)
/*     */   {
/*     */     try {
/* 144 */       Pagination page = new Pagination();
/* 145 */       page.setList(this.portaluserDao.getPortaluserListWithPage(portaluserQuery));
/*     */ 
/* 147 */       page.setTotalCount(this.portaluserDao.getPortaluserListCount(portaluserQuery).intValue());
/* 148 */       page.setPageNo(portaluserQuery.getPage());
/* 149 */       page.setPageSize(portaluserQuery.getPageSize());
/* 150 */       return page;
/*     */     } catch (Exception e) {
/* 152 */       log.error("get Portaluser pagination error." + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaluser> getPortaluserList(PortaluserQuery portaluserQuery)
/*     */   {
/*     */     try
/*     */     {
/* 163 */       return this.portaluserDao.getPortaluserList(portaluserQuery);
/*     */     } catch (SQLException e) {
/* 165 */       log.error("get Portaluser list error." + e.getMessage(), e);
/* 166 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortaluserCount(PortaluserQuery portaluserQuery)
/*     */   {
/*     */     try
/*     */     {
/* 174 */       return this.portaluserDao.getPortaluserListCount(portaluserQuery);
/*     */     } catch (SQLException e) {
/* 176 */       log.error("get Portaluser list Count." + e.getMessage(), e);
/* 177 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaluserServiceImpl
 * JD-Core Version:    0.6.2
 */