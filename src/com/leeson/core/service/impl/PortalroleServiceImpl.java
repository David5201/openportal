package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalrole;
import com.leeson.core.dao.PortalroleDao;
import com.leeson.core.query.PortalroleQuery;
import com.leeson.core.service.PortalroleService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalroleServiceImpl
  implements PortalroleService
{
  private static final Log log = LogFactory.getLog(PortalroleServiceImpl.class);

  @Resource
  PortalroleDao portalroleDao;

  public Long addPortalrole(Portalrole portalrole)
  {
    try
    {
      return this.portalroleDao.addPortalrole(portalrole);
    } catch (SQLException e) {
      log.error("dao addPortalrole error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalrole getPortalroleByKey(Long id)
  {
    try
    {
      return this.portalroleDao.getPortalroleByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalrolebyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalrole> getPortalroleByKeys(List<Long> idList)
  {
    try {
      return this.portalroleDao.getPortalroleByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalrolesByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalroleDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalroleQuery portalrole)
  {
    try
    {
      return this.portalroleDao.deleteByQuery(portalrole);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalroleDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalroleByKey(Portalrole portalrole)
  {
    try
    {
      return this.portalroleDao.updatePortalroleByKey(portalrole);
    } catch (SQLException e) {
      log.error("dao updatePortalrole error.Portalrole:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalroleListWithPage(PortalroleQuery portalroleQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalroleDao.getPortalroleListWithPage(portalroleQuery));

      page.setTotalCount(this.portalroleDao.getPortalroleListCount(portalroleQuery).intValue());
      page.setPageNo(portalroleQuery.getPage());
      page.setPageSize(portalroleQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalrole pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalrole> getPortalroleList(PortalroleQuery portalroleQuery)
  {
    try
    {
      return this.portalroleDao.getPortalroleList(portalroleQuery);
    } catch (SQLException e) {
      log.error("get Portalrole list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalroleCount(PortalroleQuery portalroleQuery)
  {
    try
    {
      return this.portalroleDao.getPortalroleListCount(portalroleQuery);
    } catch (SQLException e) {
      log.error("get Portalrole list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalroleServiceImpl
 * JD-Core Version:    0.6.2
 */