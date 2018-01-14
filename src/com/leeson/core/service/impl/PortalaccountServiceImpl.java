package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.dao.PortalaccountDao;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.PortalaccountService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalaccountServiceImpl
  implements PortalaccountService
{
  private static final Log log = LogFactory.getLog(PortalaccountServiceImpl.class);

  @Resource
  PortalaccountDao portalaccountDao;

  public Long addPortalaccount(Portalaccount portalaccount)
  {
    try
    {
      return this.portalaccountDao.addPortalaccount(portalaccount);
    } catch (SQLException e) {
      log.error("dao addPortalaccount error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalaccount getPortalaccountByKey(Long id)
  {
    try
    {
      return this.portalaccountDao.getPortalaccountByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalaccountbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalaccount> getPortalaccountByKeys(List<Long> idList)
  {
    try {
      return this.portalaccountDao.getPortalaccountByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalaccountsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalaccountDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalaccountQuery portalaccountQuery)
  {
    try
    {
      return this.portalaccountDao.deleteByQuery(portalaccountQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalaccountDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalaccountDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalaccountByKey(Portalaccount portalaccount)
  {
    try
    {
      return this.portalaccountDao.updatePortalaccountByKey(portalaccount);
    } catch (SQLException e) {
      log.error("dao updatePortalaccount error.Portalaccount:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalaccountByKeyAll(Portalaccount portalaccount)
  {
    try
    {
      return this.portalaccountDao.updatePortalaccountByKeyAll(portalaccount);
    } catch (SQLException e) {
      log.error("dao updatePortalaccountAll error.Portalaccount:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalaccountListWithPage(PortalaccountQuery portalaccountQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalaccountDao.getPortalaccountListWithPage(portalaccountQuery));

      page.setTotalCount(this.portalaccountDao.getPortalaccountListCount(portalaccountQuery).intValue());
      page.setPageNo(portalaccountQuery.getPage());
      page.setPageSize(portalaccountQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalaccount pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalaccount> getPortalaccountList(PortalaccountQuery portalaccountQuery)
  {
    try
    {
      return this.portalaccountDao.getPortalaccountList(portalaccountQuery);
    } catch (SQLException e) {
      log.error("get Portalaccount list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalaccountCount(PortalaccountQuery portalaccountQuery)
  {
    try
    {
      return this.portalaccountDao.getPortalaccountListCount(portalaccountQuery);
    } catch (SQLException e) {
      log.error("get Portalaccount list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalaccountServiceImpl
 * JD-Core Version:    0.6.2
 */