package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.dao.PortalaccountgroupDao;
import com.leeson.core.query.PortalaccountgroupQuery;
import com.leeson.core.service.PortalaccountgroupService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalaccountgroupServiceImpl
  implements PortalaccountgroupService
{
  private static final Log log = LogFactory.getLog(PortalaccountgroupServiceImpl.class);

  @Resource
  PortalaccountgroupDao portalaccountgroupDao;

  public Long addPortalaccountgroup(Portalaccountgroup portalaccountgroup)
  {
    try
    {
      return this.portalaccountgroupDao.addPortalaccountgroup(portalaccountgroup);
    } catch (SQLException e) {
      log.error("dao addPortalaccountgroup error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalaccountgroup getPortalaccountgroupByKey(Long id)
  {
    try
    {
      return this.portalaccountgroupDao.getPortalaccountgroupByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalaccountgroupbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalaccountgroup> getPortalaccountgroupByKeys(List<Long> idList)
  {
    try {
      return this.portalaccountgroupDao.getPortalaccountgroupByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalaccountgroupsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalaccountgroupDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalaccountgroupQuery portalaccountgroupQuery)
  {
    try
    {
      return this.portalaccountgroupDao.deleteByQuery(portalaccountgroupQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalaccountgroupDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalaccountgroupDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalaccountgroupByKey(Portalaccountgroup portalaccountgroup)
  {
    try
    {
      return this.portalaccountgroupDao.updatePortalaccountgroupByKey(portalaccountgroup);
    } catch (SQLException e) {
      log.error("dao updatePortalaccountgroup error.Portalaccountgroup:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalaccountgroupByKeyAll(Portalaccountgroup portalaccountgroup)
  {
    try
    {
      return this.portalaccountgroupDao.updatePortalaccountgroupByKeyAll(portalaccountgroup);
    } catch (SQLException e) {
      log.error("dao updatePortalaccountgroupAll error.Portalaccountgroup:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalaccountgroupListWithPage(PortalaccountgroupQuery portalaccountgroupQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalaccountgroupDao.getPortalaccountgroupListWithPage(portalaccountgroupQuery));

      page.setTotalCount(this.portalaccountgroupDao.getPortalaccountgroupListCount(portalaccountgroupQuery).intValue());
      page.setPageNo(portalaccountgroupQuery.getPage());
      page.setPageSize(portalaccountgroupQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalaccountgroup pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalaccountgroup> getPortalaccountgroupList(PortalaccountgroupQuery portalaccountgroupQuery)
  {
    try
    {
      return this.portalaccountgroupDao.getPortalaccountgroupList(portalaccountgroupQuery);
    } catch (SQLException e) {
      log.error("get Portalaccountgroup list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalaccountgroupCount(PortalaccountgroupQuery portalaccountgroupQuery)
  {
    try
    {
      return this.portalaccountgroupDao.getPortalaccountgroupListCount(portalaccountgroupQuery);
    } catch (SQLException e) {
      log.error("get Portalaccountgroup list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalaccountgroupServiceImpl
 * JD-Core Version:    0.6.2
 */