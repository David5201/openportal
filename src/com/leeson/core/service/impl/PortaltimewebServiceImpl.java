/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portaltimeweb;
/*     */ import com.leeson.core.dao.PortaltimewebDao;
/*     */ import com.leeson.core.query.PortaltimewebQuery;
/*     */ import com.leeson.core.service.PortaltimewebService;
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
/*     */ public class PortaltimewebServiceImpl
/*     */   implements PortaltimewebService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(PortaltimewebServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortaltimewebDao portaltimewebDao;
/*     */ 
/*     */   public Long addPortaltimeweb(Portaltimeweb portaltimeweb)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.portaltimewebDao.addPortaltimeweb(portaltimeweb);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addPortaltimeweb error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portaltimeweb getPortaltimewebByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.portaltimewebDao.getPortaltimewebByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getPortaltimewebbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaltimeweb> getPortaltimewebByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.portaltimewebDao.getPortaltimewebByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getPortaltimewebsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.portaltimewebDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortaltimewebQuery portaltimewebQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.portaltimewebDao.deleteByQuery(portaltimewebQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.portaltimewebDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.portaltimewebDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortaltimewebByKey(Portaltimeweb portaltimeweb)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.portaltimewebDao.updatePortaltimewebByKey(portaltimeweb);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updatePortaltimeweb error.Portaltimeweb:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortaltimewebByKeyAll(Portaltimeweb portaltimeweb)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.portaltimewebDao.updatePortaltimewebByKeyAll(portaltimeweb);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updatePortaltimewebAll error.Portaltimeweb:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortaltimewebListWithPage(PortaltimewebQuery portaltimewebQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.portaltimewebDao.getPortaltimewebListWithPage(portaltimewebQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.portaltimewebDao.getPortaltimewebListCount(portaltimewebQuery).intValue());
/* 164 */       page.setPageNo(portaltimewebQuery.getPage());
/* 165 */       page.setPageSize(portaltimewebQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Portaltimeweb pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaltimeweb> getPortaltimewebList(PortaltimewebQuery portaltimewebQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.portaltimewebDao.getPortaltimewebList(portaltimewebQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Portaltimeweb list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortaltimewebCount(PortaltimewebQuery portaltimewebQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.portaltimewebDao.getPortaltimewebListCount(portaltimewebQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Portaltimeweb list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaltimewebServiceImpl
 * JD-Core Version:    0.6.2
 */