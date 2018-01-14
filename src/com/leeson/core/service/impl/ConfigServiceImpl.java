package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Config;
import com.leeson.core.dao.ConfigDao;
import com.leeson.core.query.ConfigQuery;
import com.leeson.core.service.ConfigService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigServiceImpl
  implements ConfigService
{
  private static final Log log = LogFactory.getLog(ConfigServiceImpl.class);

  @Resource
  ConfigDao configDao;

  public Long addConfig(Config config)
  {
    try
    {
      return this.configDao.addConfig(config);
    } catch (SQLException e) {
      log.error("dao addConfig error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Config getConfigByKey(Long id)
  {
    try
    {
      return this.configDao.getConfigByKey(id);
    } catch (SQLException e) {
      log.error("dao getConfigbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Config> getConfigByKeys(List<Long> idList)
  {
    try {
      return this.configDao.getConfigByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getConfigsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.configDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(ConfigQuery configQuery)
  {
    try
    {
      return this.configDao.deleteByQuery(configQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.configDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.configDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateConfigByKey(Config config)
  {
    try
    {
      return this.configDao.updateConfigByKey(config);
    } catch (SQLException e) {
      log.error("dao updateConfig error.Config:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateConfigByKeyAll(Config config)
  {
    try
    {
      return this.configDao.updateConfigByKeyAll(config);
    } catch (SQLException e) {
      log.error("dao updateConfigAll error.Config:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getConfigListWithPage(ConfigQuery configQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.configDao.getConfigListWithPage(configQuery));

      page.setTotalCount(this.configDao.getConfigListCount(configQuery).intValue());
      page.setPageNo(configQuery.getPage());
      page.setPageSize(configQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Config pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Config> getConfigList(ConfigQuery configQuery)
  {
    try
    {
      return this.configDao.getConfigList(configQuery);
    } catch (SQLException e) {
      log.error("get Config list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getConfigCount(ConfigQuery configQuery)
  {
    try
    {
      return this.configDao.getConfigListCount(configQuery);
    } catch (SQLException e) {
      log.error("get Config list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.ConfigServiceImpl
 * JD-Core Version:    0.6.2
 */