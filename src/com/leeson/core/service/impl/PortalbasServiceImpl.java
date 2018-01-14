package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.dao.PortalbasDao;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.service.PortalbasService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalbasServiceImpl
  implements PortalbasService
{
  private static final Log log = LogFactory.getLog(PortalbasServiceImpl.class);

  @Resource
  PortalbasDao portalbasDao;

  public Long addPortalbas(Portalbas portalbas)
  {
    try
    {
      return this.portalbasDao.addPortalbas(portalbas);
    } catch (SQLException e) {
      log.error("dao addPortalbas error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalbas getPortalbasByKey(Long id)
  {
    try
    {
      return this.portalbasDao.getPortalbasByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalbasbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalbas> getPortalbasByKeys(List<Long> idList)
  {
    try {
      return this.portalbasDao.getPortalbasByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalbassByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalbasDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalbasQuery portalbasQuery)
  {
    try
    {
      return this.portalbasDao.deleteByQuery(portalbasQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalbasDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalbasDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalbasByKey(Portalbas portalbas)
  {
    try
    {
      return this.portalbasDao.updatePortalbasByKey(portalbas);
    } catch (SQLException e) {
      log.error("dao updatePortalbas error.Portalbas:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalbasByKeyAll(Portalbas portalbas)
  {
    try
    {
      return this.portalbasDao.updatePortalbasByKeyAll(portalbas);
    } catch (SQLException e) {
      log.error("dao updatePortalbasAll error.Portalbas:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalbasListWithPage(PortalbasQuery portalbasQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalbasDao.getPortalbasListWithPage(portalbasQuery));

      page.setTotalCount(this.portalbasDao.getPortalbasListCount(portalbasQuery).intValue());
      page.setPageNo(portalbasQuery.getPage());
      page.setPageSize(portalbasQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalbas pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalbas> getPortalbasList(PortalbasQuery portalbasQuery)
  {
    try
    {
      return this.portalbasDao.getPortalbasList(portalbasQuery);
    } catch (SQLException e) {
      log.error("get Portalbas list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalbasCount(PortalbasQuery portalbasQuery)
  {
    try
    {
      return this.portalbasDao.getPortalbasListCount(portalbasQuery);
    } catch (SQLException e) {
      log.error("get Portalbas list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalbasServiceImpl
 * JD-Core Version:    0.6.2
 */