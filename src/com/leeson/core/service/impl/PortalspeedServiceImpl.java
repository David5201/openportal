package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalspeed;
import com.leeson.core.dao.PortalspeedDao;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.PortalspeedService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalspeedServiceImpl
  implements PortalspeedService
{
  private static final Log log = LogFactory.getLog(PortalspeedServiceImpl.class);

  @Resource
  PortalspeedDao portalspeedDao;

  public Long addPortalspeed(Portalspeed portalspeed)
  {
    try
    {
      return this.portalspeedDao.addPortalspeed(portalspeed);
    } catch (SQLException e) {
      log.error("dao addPortalspeed error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalspeed getPortalspeedByKey(Long id)
  {
    try
    {
      return this.portalspeedDao.getPortalspeedByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalspeedbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalspeed> getPortalspeedByKeys(List<Long> idList)
  {
    try {
      return this.portalspeedDao.getPortalspeedByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalspeedsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalspeedDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalspeedQuery portalspeedQuery)
  {
    try
    {
      return this.portalspeedDao.deleteByQuery(portalspeedQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalspeedDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalspeedByKey(Portalspeed portalspeed)
  {
    try
    {
      return this.portalspeedDao.updatePortalspeedByKey(portalspeed);
    } catch (SQLException e) {
      log.error("dao updatePortalspeed error.Portalspeed:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalspeedByKeyAll(Portalspeed portalspeed)
  {
    try
    {
      return this.portalspeedDao.updatePortalspeedByKeyAll(portalspeed);
    } catch (SQLException e) {
      log.error("dao updatePortalspeedAll error.Portalspeed:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalspeedListWithPage(PortalspeedQuery portalspeedQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalspeedDao.getPortalspeedListWithPage(portalspeedQuery));

      page.setTotalCount(this.portalspeedDao.getPortalspeedListCount(portalspeedQuery).intValue());
      page.setPageNo(portalspeedQuery.getPage());
      page.setPageSize(portalspeedQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalspeed pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalspeed> getPortalspeedList(PortalspeedQuery portalspeedQuery)
  {
    try
    {
      return this.portalspeedDao.getPortalspeedList(portalspeedQuery);
    } catch (SQLException e) {
      log.error("get Portalspeed list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalspeedCount(PortalspeedQuery portalspeedQuery)
  {
    try
    {
      return this.portalspeedDao.getPortalspeedListCount(portalspeedQuery);
    } catch (SQLException e) {
      log.error("get Portalspeed list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalspeedServiceImpl
 * JD-Core Version:    0.6.2
 */