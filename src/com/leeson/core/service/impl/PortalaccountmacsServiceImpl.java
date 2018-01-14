package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.dao.PortalaccountmacsDao;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.service.PortalaccountmacsService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalaccountmacsServiceImpl
  implements PortalaccountmacsService
{
  private static final Log log = LogFactory.getLog(PortalaccountmacsServiceImpl.class);

  @Resource
  PortalaccountmacsDao portalaccountmacsDao;

  public Long addPortalaccountmacs(Portalaccountmacs portalaccountmacs)
  {
    try
    {
      return this.portalaccountmacsDao.addPortalaccountmacs(portalaccountmacs);
    } catch (SQLException e) {
      log.error("dao addPortalaccountmacs error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalaccountmacs getPortalaccountmacsByKey(Long id)
  {
    try
    {
      return this.portalaccountmacsDao.getPortalaccountmacsByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalaccountmacsbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalaccountmacs> getPortalaccountmacsByKeys(List<Long> idList)
  {
    try {
      return this.portalaccountmacsDao.getPortalaccountmacsByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalaccountmacssByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalaccountmacsDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalaccountmacsQuery portalaccountmacs)
  {
    try
    {
      return this.portalaccountmacsDao.deleteByQuery(portalaccountmacs);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalaccountmacsDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalaccountmacsByKey(Portalaccountmacs portalaccountmacs)
  {
    try
    {
      return this.portalaccountmacsDao.updatePortalaccountmacsByKey(portalaccountmacs);
    } catch (SQLException e) {
      log.error("dao updatePortalaccountmacs error.Portalaccountmacs:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalaccountmacsByKeyAll(Portalaccountmacs portalaccountmacs)
  {
    try
    {
      return this.portalaccountmacsDao.updatePortalaccountmacsByKeyAll(portalaccountmacs);
    } catch (SQLException e) {
      log.error("dao updatePortalaccountmacsAll error.Portalaccountmacs:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalaccountmacsListWithPage(PortalaccountmacsQuery portalaccountmacsQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalaccountmacsDao.getPortalaccountmacsListWithPage(portalaccountmacsQuery));

      page.setTotalCount(this.portalaccountmacsDao.getPortalaccountmacsListCount(portalaccountmacsQuery).intValue());
      page.setPageNo(portalaccountmacsQuery.getPage());
      page.setPageSize(portalaccountmacsQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalaccountmacs pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalaccountmacs> getPortalaccountmacsList(PortalaccountmacsQuery portalaccountmacsQuery)
  {
    try
    {
      return this.portalaccountmacsDao.getPortalaccountmacsList(portalaccountmacsQuery);
    } catch (SQLException e) {
      log.error("get Portalaccountmacs list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalaccountmacsCount(PortalaccountmacsQuery portalaccountmacsQuery)
  {
    try
    {
      return this.portalaccountmacsDao.getPortalaccountmacsListCount(portalaccountmacsQuery);
    } catch (SQLException e) {
      log.error("get Portalaccountmacs list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalaccountmacsServiceImpl
 * JD-Core Version:    0.6.2
 */