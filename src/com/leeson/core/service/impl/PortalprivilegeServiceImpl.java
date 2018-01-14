/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalprivilege;
/*     */ import com.leeson.core.dao.PortalprivilegeDao;
/*     */ import com.leeson.core.dao.PortalroleprivilegeDao;
/*     */ import com.leeson.core.query.PortalprivilegeQuery;
/*     */ import com.leeson.core.service.PortalprivilegeService;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class PortalprivilegeServiceImpl
/*     */   implements PortalprivilegeService
/*     */ {
/*  31 */   private static final Log log = LogFactory.getLog(PortalprivilegeServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   PortalprivilegeDao portalprivilegeDao;
/*     */ 
/*     */   @Resource
/*     */   PortalroleprivilegeDao portalroleprivilegeDao;
/*     */ 
/*     */   public Long addPortalprivilege(Portalprivilege portalprivilege)
/*     */   {
/*     */     try
/*     */     {
/*  45 */       return this.portalprivilegeDao.addPortalprivilege(portalprivilege);
/*     */     } catch (SQLException e) {
/*  47 */       log.error("dao addPortalprivilege error.:" + e.getMessage(), e);
/*  48 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Portalprivilege getPortalprivilegeByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  59 */       return this.portalprivilegeDao.getPortalprivilegeByKey(id);
/*     */     } catch (SQLException e) {
/*  61 */       log.error("dao getPortalprivilegebyKey error.:" + e.getMessage(), e);
/*  62 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalprivilege> getPortalprivilegeByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  69 */       return this.portalprivilegeDao.getPortalprivilegeByKeys(idList);
/*     */     } catch (SQLException e) {
/*  71 */       log.error("dao getPortalprivilegesByKeys erorr." + e.getMessage(), e);
/*  72 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  84 */       List ids = new ArrayList();
/*  85 */       ids.add(id);
/*  86 */       WalkDelete(ids);
/*  87 */       return this.portalprivilegeDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  89 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  90 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(PortalprivilegeQuery portalprivilege)
/*     */   {
/*     */     try
/*     */     {
/* 101 */       List ids = new ArrayList();
/* 102 */       ids.add(portalprivilege.getId());
/* 103 */       WalkDelete(ids);
/* 104 */       return this.portalprivilegeDao.deleteByQuery(portalprivilege);
/*     */     } catch (SQLException e) {
/* 106 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/* 107 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 118 */       WalkDelete(idList);
/* 119 */       return this.portalprivilegeDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 121 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 122 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalprivilegeByKey(Portalprivilege portalprivilege)
/*     */   {
/*     */     try
/*     */     {
/* 134 */       return this.portalprivilegeDao.updatePortalprivilegeByKey(portalprivilege);
/*     */     } catch (SQLException e) {
/* 136 */       log.error("dao updatePortalprivilege error.Portalprivilege:" + e.getMessage(), e);
/* 137 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updatePortalprivilegeByKeyAll(Portalprivilege portalprivilege)
/*     */   {
/*     */     try
/*     */     {
/* 148 */       return this.portalprivilegeDao.updatePortalprivilegeByKeyAll(portalprivilege);
/*     */     } catch (SQLException e) {
/* 150 */       log.error("dao updatePortalprivilegeAll error.Portalprivilege:" + e.getMessage(), e);
/* 151 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPortalprivilegeListWithPage(PortalprivilegeQuery portalprivilegeQuery)
/*     */   {
/*     */     try {
/* 158 */       Pagination page = new Pagination();
/* 159 */       page.setList(this.portalprivilegeDao.getPortalprivilegeListWithPage(portalprivilegeQuery));
/*     */ 
/* 161 */       page.setTotalCount(this.portalprivilegeDao.getPortalprivilegeListCount(portalprivilegeQuery).intValue());
/* 162 */       page.setPageNo(portalprivilegeQuery.getPage());
/* 163 */       page.setPageSize(portalprivilegeQuery.getPageSize());
/* 164 */       return page;
/*     */     } catch (Exception e) {
/* 166 */       log.error("get Portalprivilege pagination error." + e.getMessage(), e);
/* 167 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalprivilege> getPortalprivilegeList(PortalprivilegeQuery portalprivilegeQuery)
/*     */   {
/*     */     try
/*     */     {
/* 177 */       return this.portalprivilegeDao.getPortalprivilegeList(portalprivilegeQuery);
/*     */     } catch (SQLException e) {
/* 179 */       log.error("get Portalprivilege list error." + e.getMessage(), e);
/* 180 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getPortalprivilegeCount(PortalprivilegeQuery portalprivilegeQuery)
/*     */   {
/*     */     try
/*     */     {
/* 188 */       return this.portalprivilegeDao.getPortalprivilegeListCount(portalprivilegeQuery);
/*     */     } catch (SQLException e) {
/* 190 */       log.error("get Portalprivilege list Count." + e.getMessage(), e);
/* 191 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalprivilege> findTopList()
/*     */   {
/* 200 */     List<Portalprivilege> allprivileges = new ArrayList();
/* 201 */     PortalprivilegeQuery portalprivilegeQuery = new PortalprivilegeQuery();
/* 202 */     portalprivilegeQuery.orderbyPosition(true);
/*     */     try {
/* 204 */       allprivileges = this.portalprivilegeDao.getPortalprivilegeList(portalprivilegeQuery);
/*     */     }
/*     */     catch (SQLException e) {
/* 207 */       log.error("get Portalprivilege findTopList error." + e.getMessage(), e);
/* 208 */       return null;
/*     */     }
/* 210 */     List topList = new ArrayList();
/* 211 */     for (Portalprivilege privilege : allprivileges) {
/* 212 */       if ((privilege.getUrl() != null) && (stringUtils.isBlank(privilege.getUrl()))) {
/* 213 */         privilege.setUrl(null);
/*     */       }
/* 215 */       if (privilege.getParentId() == null) {
/* 216 */         topList.add(privilege);
/*     */       }
/*     */     }
/* 219 */     return topList;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Collection<String> getAllPrivilegeUrls() {
/* 224 */     List<Portalprivilege> privileges = new ArrayList<Portalprivilege>();
/*     */     try {
/* 226 */       privileges = this.portalprivilegeDao.getPortalprivilegeList(new PortalprivilegeQuery());
/*     */     }
/*     */     catch (SQLException e) {
/* 229 */       log.error("get Portalprivilege getAllPrivilegeUrls error." + e.getMessage(), e);
/* 230 */       return null;
/*     */     }
/* 232 */     Collection allPriviUrls = new HashSet(privileges.size());
/* 233 */     for (Portalprivilege privilege : privileges) {
/* 234 */       if (stringUtils.isNotBlank(privilege.getUrl())) {
/* 235 */         allPriviUrls.add(privilege.getUrl());
/*     */       }
/*     */     }
/* 238 */     return allPriviUrls;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Portalprivilege> findChildrenList(Long parentId) {
/* 243 */     PortalprivilegeQuery portalprivilegeQuery = new PortalprivilegeQuery();
/* 244 */     portalprivilegeQuery.setParentId(parentId);
/* 245 */     portalprivilegeQuery.orderbyPosition(true);
/*     */     try {
/* 247 */       return this.portalprivilegeDao.getPortalprivilegeList(portalprivilegeQuery);
/*     */     }
/*     */     catch (SQLException e) {
/* 250 */       log.error("get Portalprivilege findChildrenList error." + e.getMessage(), e);
/*     */     }
/* 252 */     return null;
/*     */   }
/*     */ 
/*     */   private void WalkDelete(List<Long> list) throws SQLException
/*     */   {
/* 257 */     for (Long id : list) {
/* 258 */       PortalprivilegeQuery pq = new PortalprivilegeQuery();
/* 259 */       pq.setParentId(id);
/* 260 */       List<Portalprivilege> ps = getPortalprivilegeList(pq);
/* 261 */       if (ps.size() > 0) {
/* 262 */         List sids = new ArrayList();
/* 263 */         for (Portalprivilege p : ps) {
/* 264 */           sids.add(p.getId());
/*     */         }
/* 266 */         this.portalroleprivilegeDao.deleteByKeys(sids);
/* 267 */         WalkDelete(sids);
/* 268 */         deleteByKeys(sids);
/*     */       }
/*     */     }
/* 271 */     this.portalroleprivilegeDao.deleteByKeys(list);
/*     */   }
/*     */ 
/*     */   public void editPosUP(Long id) {
/* 275 */     Portalprivilege p = getPortalprivilegeByKey(id);
/* 276 */     PortalprivilegeQuery pq = new PortalprivilegeQuery();
/* 277 */     if (p.getParentId() != null) {
/* 278 */       pq.setParentId(p.getParentId());
/*     */     }
/* 280 */     pq.setParentId(p.getParentId());
/* 281 */     pq.orderbyPosition(true);
/* 282 */     pq.setFields("id,position");
/* 283 */     List<Portalprivilege> ps = getPortalprivilegeList(pq);
/* 284 */     int[] b = new int[ps.size()];
/* 285 */     Long[] bId = new Long[ps.size()];
/* 286 */     int index = 0;
/* 287 */     int tempPos = 0;
/* 288 */     Long tempId = null;
/* 289 */     for (Portalprivilege cp : ps) {
/* 290 */       b[index] = cp.getPosition().intValue();
/* 291 */       bId[index] = cp.getId();
/* 292 */       if (cp.getId() == id) {
/* 293 */         if (index == 0) {
/* 294 */           tempPos = b[0];
/* 295 */           tempId = bId[0];
/*     */         } else {
/* 297 */           tempPos = b[(index - 1)];
/* 298 */           tempId = bId[(index - 1)];
/*     */         }
/*     */       }
/*     */ 
/* 302 */       index++;
/*     */     }
/* 304 */     Portalprivilege tP = new Portalprivilege();
/* 305 */     tP.setId(tempId);
/* 306 */     tP.setPosition(p.getPosition());
/* 307 */     updatePortalprivilegeByKey(tP);
/* 308 */     tP.setId(id);
/* 309 */     tP.setPosition(Integer.valueOf(tempPos));
/* 310 */     updatePortalprivilegeByKey(tP);
/*     */   }
/*     */ 
/*     */   public void editPosDown(Long id)
/*     */   {
/* 315 */     Portalprivilege p = getPortalprivilegeByKey(id);
/* 316 */     PortalprivilegeQuery pq = new PortalprivilegeQuery();
/* 317 */     if (p.getParentId() != null) {
/* 318 */       pq.setParentId(p.getParentId());
/*     */     }
/* 320 */     pq.setParentId(p.getParentId());
/* 321 */     pq.orderbyPosition(false);
/* 322 */     pq.setFields("id,position");
/* 323 */     List<Portalprivilege> ps = getPortalprivilegeList(pq);
/* 324 */     int[] b = new int[ps.size()];
/* 325 */     Long[] bId = new Long[ps.size()];
/* 326 */     int index = 0;
/* 327 */     int tempPos = 0;
/* 328 */     Long tempId = null;
/* 329 */     for (Portalprivilege cp : ps) {
/* 330 */       b[index] = cp.getPosition().intValue();
/* 331 */       bId[index] = cp.getId();
/* 332 */       if (cp.getId() == id) {
/* 333 */         if (index == 0) {
/* 334 */           tempPos = b[0];
/* 335 */           tempId = bId[0];
/*     */         } else {
/* 337 */           tempPos = b[(index - 1)];
/* 338 */           tempId = bId[(index - 1)];
/*     */         }
/*     */       }
/*     */ 
/* 342 */       index++;
/*     */     }
/* 344 */     Portalprivilege tP = new Portalprivilege();
/* 345 */     tP.setId(tempId);
/* 346 */     tP.setPosition(p.getPosition());
/* 347 */     updatePortalprivilegeByKey(tP);
/* 348 */     tP.setId(id);
/* 349 */     tP.setPosition(Integer.valueOf(tempPos));
/* 350 */     updatePortalprivilegeByKey(tP);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalprivilegeServiceImpl
 * JD-Core Version:    0.6.2
 */