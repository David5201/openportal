package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalconfig;
import com.leeson.core.dao.PortalconfigDao;
import com.leeson.core.query.PortalconfigQuery;
import com.leeson.core.service.PortalconfigService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalconfigServiceImpl
  implements PortalconfigService
{
  private static final Log log = LogFactory.getLog(PortalconfigServiceImpl.class);

  @Resource
  PortalconfigDao portalconfigDao;

  public Long addPortalconfig(Portalconfig portalconfig)
  {
    try
    {
      return this.portalconfigDao.addPortalconfig(portalconfig);
    } catch (SQLException e) {
      log.error("dao addPortalconfig error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalconfig getPortalconfigByKey(Long id)
  {
    try
    {
      return this.portalconfigDao.getPortalconfigByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalconfigbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalconfig> getPortalconfigByKeys(List<Long> idList)
  {
    try {
      return this.portalconfigDao.getPortalconfigByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalconfigsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalconfigDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalconfigQuery portalconfigQuery)
  {
    try
    {
      return this.portalconfigDao.deleteByQuery(portalconfigQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalconfigDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalconfigDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalconfigByKey(Portalconfig portalconfig)
  {
    try
    {
      return this.portalconfigDao.updatePortalconfigByKey(portalconfig);
    } catch (SQLException e) {
      log.error("dao updatePortalconfig error.Portalconfig:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalconfigByKeyAll(Portalconfig portalconfig)
  {
    try
    {
      return this.portalconfigDao.updatePortalconfigByKeyAll(portalconfig);
    } catch (SQLException e) {
      log.error("dao updatePortalconfigAll error.Portalconfig:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalconfigListWithPage(PortalconfigQuery portalconfigQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalconfigDao.getPortalconfigListWithPage(portalconfigQuery));

      page.setTotalCount(this.portalconfigDao.getPortalconfigListCount(portalconfigQuery).intValue());
      page.setPageNo(portalconfigQuery.getPage());
      page.setPageSize(portalconfigQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalconfig pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalconfig> getPortalconfigList(PortalconfigQuery portalconfigQuery)
  {
    try
    {
      return this.portalconfigDao.getPortalconfigList(portalconfigQuery);
    } catch (SQLException e) {
      log.error("get Portalconfig list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalconfigCount(PortalconfigQuery portalconfigQuery)
  {
    try
    {
      return this.portalconfigDao.getPortalconfigListCount(portalconfigQuery);
    } catch (SQLException e) {
      log.error("get Portalconfig list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalconfigServiceImpl
 * JD-Core Version:    0.6.2
 */