/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Portaldepartment;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.dao.PortaldepartmentDao;
/*     */ import com.leeson.core.query.PortaldepartmentQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.service.PortaldepartmentService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class PortaldepartmentServiceImpl
/*     */   implements PortaldepartmentService
/*     */ {
/*  30 */   private static final Log log = LogFactory.getLog(PortaldepartmentServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortaldepartmentDao portaldepartmentDao;
/*     */ 
/*     */   @Resource
/*     */   PortaluserService portaluserService;
/*     */ 
/*     */   public Long addPortaldepartment(Portaldepartment portaldepartment)
/*     */   {
/*     */     try
/*     */     {
/*  44 */       return this.portaldepartmentDao.addPortaldepartment(portaldepartment);
/*     */     } catch (SQLException e) {
/*  46 */       log.error("dao addPortaldepartment error.:" + e.getMessage(), e);
/*  47 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portaldepartment getPortaldepartmentByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  58 */       return this.portaldepartmentDao.getPortaldepartmentByKey(id);
/*     */     } catch (SQLException e) {
/*  60 */       log.error("dao getPortaldepartmentbyKey error.:" + e.getMessage(), e);
/*  61 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaldepartment> getPortaldepartmentByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  68 */       return this.portaldepartmentDao.getPortaldepartmentByKeys(idList);
/*     */     } catch (SQLException e) {
/*  70 */       log.error("dao getPortaldepartmentsByKeys erorr." + e.getMessage(), e);
/*  71 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  83 */       List ids = new ArrayList();
/*  84 */       ids.add(id);
/*  85 */       WalkDelete(ids);
/*     */ 
/*  87 */       PortaluserQuery uq = new PortaluserQuery();
/*  88 */       uq.setDepartmentId(id);
/*  89 */       List<Portaluser> us = this.portaluserService.getPortaluserList(uq);
/*  90 */       for (Portaluser u : us) {
/*  91 */         u.setDepartmentId(null);
/*  92 */         this.portaluserService.updatePortaluserByKeyAll(u);
/*     */       }
/*     */ 
/*  95 */       return this.portaldepartmentDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  97 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  98 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortaldepartmentQuery portaldepartment)
/*     */   {
/*     */     try
/*     */     {
/* 109 */       List ids = new ArrayList();
/* 110 */       ids.add(portaldepartment.getId());
/* 111 */       WalkDelete(ids);
/*     */ 
/* 113 */       PortaluserQuery uq = new PortaluserQuery();
/* 114 */       uq.setDepartmentId(portaldepartment.getId());
/* 115 */       List<Portaluser> us = this.portaluserService.getPortaluserList(uq);
/* 116 */       for (Portaluser u : us) {
/* 117 */         u.setDepartmentId(null);
/* 118 */         this.portaluserService.updatePortaluserByKeyAll(u);
/*     */       }
/*     */ 
/* 121 */       return this.portaldepartmentDao.deleteByQuery(portaldepartment);
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       WalkDelete(idList);
/*     */       Iterator localIterator2;
/* 137 */       for (Iterator localIterator1 = idList.iterator(); localIterator1.hasNext(); 
/* 141 */         localIterator2.hasNext())
/*     */       {
/* 137 */         Long id = (Long)localIterator1.next();
/* 138 */         PortaluserQuery uq = new PortaluserQuery();
/* 139 */         uq.setDepartmentId(id);
/* 140 */         List us = this.portaluserService.getPortaluserList(uq);
/* 141 */         localIterator2 = us.iterator(); 
				  Portaluser u = (Portaluser)localIterator2.next();
/* 142 */         u.setDepartmentId(null);
/* 143 */         this.portaluserService.updatePortaluserByKeyAll(u);
				  continue; 
/*     */       }
/*     */ 
/* 148 */       return this.portaldepartmentDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 150 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 151 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortaldepartmentByKey(Portaldepartment portaldepartment)
/*     */   {
/*     */     try
/*     */     {
/* 163 */       return this.portaldepartmentDao.updatePortaldepartmentByKey(portaldepartment);
/*     */     } catch (SQLException e) {
/* 165 */       log.error("dao updatePortaldepartment error.Portaldepartment:" + e.getMessage(), e);
/* 166 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortaldepartmentByKeyAll(Portaldepartment portaldepartment)
/*     */   {
/*     */     try
/*     */     {
/* 177 */       return this.portaldepartmentDao.updatePortaldepartmentByKeyAll(portaldepartment);
/*     */     } catch (SQLException e) {
/* 179 */       log.error("dao updatePortaldepartmentAll error.Portaldepartment:" + e.getMessage(), e);
/* 180 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortaldepartmentListWithPage(PortaldepartmentQuery portaldepartmentQuery)
/*     */   {
/*     */     try {
/* 187 */       Pagination page = new Pagination();
/* 188 */       page.setList(this.portaldepartmentDao.getPortaldepartmentListWithPage(portaldepartmentQuery));
/*     */ 
/* 190 */       page.setTotalCount(this.portaldepartmentDao.getPortaldepartmentListCount(portaldepartmentQuery).intValue());
/* 191 */       page.setPageNo(portaldepartmentQuery.getPage());
/* 192 */       page.setPageSize(portaldepartmentQuery.getPageSize());
/* 193 */       return page;
/*     */     } catch (Exception e) {
/* 195 */       log.error("get Portaldepartment pagination error." + e.getMessage(), e);
/* 196 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portaldepartment> getPortaldepartmentList(PortaldepartmentQuery portaldepartmentQuery)
/*     */   {
/*     */     try
/*     */     {
/* 206 */       return this.portaldepartmentDao.getPortaldepartmentList(portaldepartmentQuery);
/*     */     } catch (SQLException e) {
/* 208 */       log.error("get Portaldepartment list error." + e.getMessage(), e);
/* 209 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortaldepartmentCount(PortaldepartmentQuery portaldepartmentQuery)
/*     */   {
/*     */     try
/*     */     {
/* 217 */       return this.portaldepartmentDao.getPortaldepartmentListCount(portaldepartmentQuery);
/*     */     } catch (SQLException e) {
/* 219 */       log.error("get Portaldepartment list Count." + e.getMessage(), e);
/* 220 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public void WalkDelete(List<Long> list)
/*     */   {
/* 227 */     for (Long id : list) {
/* 228 */       PortaldepartmentQuery pq = new PortaldepartmentQuery();
/* 229 */       pq.setParentId(id);
/* 230 */       List ps = getPortaldepartmentList(pq);
/* 231 */       if (ps.size() > 0) {
/* 232 */         List sids = new ArrayList();
/*     */         Iterator localIterator3;
/* 233 */         for (Iterator localIterator2 = ps.iterator(); localIterator2.hasNext(); 
/* 239 */           localIterator3.hasNext())
/*     */         {
/* 233 */           Portaldepartment p = (Portaldepartment)localIterator2.next();
/* 234 */           sids.add(p.getId());
/*     */ 
/* 236 */           PortaluserQuery uq = new PortaluserQuery();
/* 237 */           uq.setDepartmentId(p.getId());
/* 238 */           List us = this.portaluserService.getPortaluserList(uq);
/* 239 */           localIterator3 = us.iterator(); 
					Portaluser u = (Portaluser)localIterator3.next();
/* 240 */           u.setDepartmentId(null);
/* 241 */           this.portaluserService.updatePortaluserByKeyAll(u);
					continue;
/*     */         }
/*     */ 
/* 244 */         WalkDelete(sids);
/* 245 */         deleteByKeys(sids);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaldepartmentServiceImpl
 * JD-Core Version:    0.6.2
 */