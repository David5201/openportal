/*     */ package com.leeson.core.service.impl;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.core.bean.Config;
/*     */ import com.leeson.core.dao.ConfigDao;
/*     */ import com.leeson.core.query.ConfigQuery;
/*     */ import com.leeson.core.service.ConfigService;
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
/*     */ public class ConfigServiceImpl
/*     */   implements ConfigService
/*     */ {
/*  27 */   private static final Log log = LogFactory.getLog(ConfigServiceImpl.class);
/*     */ 
/*     */   @Resource
/*     */   ConfigDao configDao;
/*     */ 
/*     */   public Long addConfig(Config config)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       return this.configDao.addConfig(config);
/*     */     } catch (SQLException e) {
/*  41 */       log.error("dao addConfig error.:" + e.getMessage(), e);
/*  42 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Config getConfigByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return this.configDao.getConfigByKey(id);
/*     */     } catch (SQLException e) {
/*  55 */       log.error("dao getConfigbyKey error.:" + e.getMessage(), e);
/*  56 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Config> getConfigByKeys(List<Long> idList)
/*     */   {
/*     */     try {
/*  63 */       return this.configDao.getConfigByKeys(idList);
/*     */     } catch (SQLException e) {
/*  65 */       log.error("dao getConfigsByKeys erorr." + e.getMessage(), e);
/*  66 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKey(Long id)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       return this.configDao.deleteByKey(id);
/*     */     } catch (SQLException e) {
/*  80 */       log.error("dao deleteByKey error. :" + e.getMessage(), e);
/*  81 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByQuery(ConfigQuery configQuery)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       return this.configDao.deleteByQuery(configQuery);
/*     */     } catch (SQLException e) {
/*  94 */       log.error("dao deleteByQuery error. :" + e.getMessage(), e);
/*  95 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteByKeys(List<Long> idList)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       return this.configDao.deleteByKeys(idList);
/*     */     } catch (SQLException e) {
/* 108 */       log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
/* 109 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer deleteAll()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this.configDao.deleteAll();
/*     */     } catch (SQLException e) {
/* 123 */       log.error("dao deleteAll error. s:" + e.getMessage(), e);
/* 124 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateConfigByKey(Config config)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return this.configDao.updateConfigByKey(config);
/*     */     } catch (SQLException e) {
/* 138 */       log.error("dao updateConfig error.Config:" + e.getMessage(), e);
/* 139 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   public Integer updateConfigByKeyAll(Config config)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return this.configDao.updateConfigByKeyAll(config);
/*     */     } catch (SQLException e) {
/* 152 */       log.error("dao updateConfigAll error.Config:" + e.getMessage(), e);
/* 153 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getConfigListWithPage(ConfigQuery configQuery)
/*     */   {
/*     */     try {
/* 160 */       Pagination page = new Pagination();
/* 161 */       page.setList(this.configDao.getConfigListWithPage(configQuery));
/*     */ 
/* 163 */       page.setTotalCount(this.configDao.getConfigListCount(configQuery).intValue());
/* 164 */       page.setPageNo(configQuery.getPage());
/* 165 */       page.setPageSize(configQuery.getPageSize());
/* 166 */       return page;
/*     */     } catch (Exception e) {
/* 168 */       log.error("get Config pagination error." + e.getMessage(), e);
/* 169 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Config> getConfigList(ConfigQuery configQuery)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       return this.configDao.getConfigList(configQuery);
/*     */     } catch (SQLException e) {
/* 181 */       log.error("get Config list error." + e.getMessage(), e);
/* 182 */     }throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Integer getConfigCount(ConfigQuery configQuery)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       return this.configDao.getConfigListCount(configQuery);
/*     */     } catch (SQLException e) {
/* 192 */       log.error("get Config list Count." + e.getMessage(), e);
/* 193 */     }throw new RuntimeException();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.ConfigServiceImpl
 * JD-Core Version:    0.6.2
 */