package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.dao.PortalautologinDao;
import com.leeson.core.query.PortalautologinQuery;
import com.leeson.core.service.PortalautologinService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalautologinServiceImpl
  implements PortalautologinService
{
  private static final Log log = LogFactory.getLog(PortalautologinServiceImpl.class);

  @Resource
  PortalautologinDao portalautologinDao;

  public Long addPortalautologin(Portalautologin portalautologin)
  {
    try
    {
      return this.portalautologinDao.addPortalautologin(portalautologin);
    } catch (SQLException e) {
      log.error("dao addPortalautologin error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalautologin getPortalautologinByKey(Long id)
  {
    try
    {
      return this.portalautologinDao.getPortalautologinByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalautologinbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalautologin> getPortalautologinByKeys(List<Long> idList)
  {
    try {
      return this.portalautologinDao.getPortalautologinByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalautologinsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalautologinDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalautologinQuery portalautologinQuery)
  {
    try
    {
      return this.portalautologinDao.deleteByQuery(portalautologinQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalautologinDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalautologinByKey(Portalautologin portalautologin)
  {
    try
    {
      return this.portalautologinDao.updatePortalautologinByKey(portalautologin);
    } catch (SQLException e) {
      log.error("dao updatePortalautologin error.Portalautologin:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalautologinByKeyAll(Portalautologin portalautologin)
  {
    try
    {
      return this.portalautologinDao.updatePortalautologinByKeyAll(portalautologin);
    } catch (SQLException e) {
      log.error("dao updatePortalautologinAll error.Portalautologin:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalautologinListWithPage(PortalautologinQuery portalautologinQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalautologinDao.getPortalautologinListWithPage(portalautologinQuery));

      page.setTotalCount(this.portalautologinDao.getPortalautologinListCount(portalautologinQuery).intValue());
      page.setPageNo(portalautologinQuery.getPage());
      page.setPageSize(portalautologinQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalautologin pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalautologin> getPortalautologinList(PortalautologinQuery portalautologinQuery)
  {
    try
    {
      return this.portalautologinDao.getPortalautologinList(portalautologinQuery);
    } catch (SQLException e) {
      log.error("get Portalautologin list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalautologinCount(PortalautologinQuery portalautologinQuery)
  {
    try
    {
      return this.portalautologinDao.getPortalautologinListCount(portalautologinQuery);
    } catch (SQLException e) {
      log.error("get Portalautologin list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalautologinServiceImpl
 * JD-Core Version:    0.6.2
 */