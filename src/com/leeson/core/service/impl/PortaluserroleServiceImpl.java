package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaluserrole;
import com.leeson.core.dao.PortaluserroleDao;
import com.leeson.core.query.PortaluserroleQuery;
import com.leeson.core.service.PortaluserroleService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortaluserroleServiceImpl
  implements PortaluserroleService
{
  private static final Log log = LogFactory.getLog(PortaluserroleServiceImpl.class);

  @Resource
  PortaluserroleDao portaluserroleDao;

  public Long addPortaluserrole(Portaluserrole portaluserrole)
  {
    try
    {
      return this.portaluserroleDao.addPortaluserrole(portaluserrole);
    } catch (SQLException e) {
      log.error("dao addPortaluserrole error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portaluserrole getPortaluserroleByKey(Long id)
  {
    try
    {
      return this.portaluserroleDao.getPortaluserroleByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortaluserrolebyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaluserrole> getPortaluserroleByKeys(List<Long> idList)
  {
    try {
      return this.portaluserroleDao.getPortaluserroleByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortaluserrolesByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portaluserroleDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortaluserroleQuery portaluserrole)
  {
    try
    {
      return this.portaluserroleDao.deleteByQuery(portaluserrole);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portaluserroleDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortaluserroleByKey(Portaluserrole portaluserrole)
  {
    try
    {
      return this.portaluserroleDao.updatePortaluserroleByKey(portaluserrole);
    } catch (SQLException e) {
      log.error("dao updatePortaluserrole error.Portaluserrole:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortaluserroleListWithPage(PortaluserroleQuery portaluserroleQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portaluserroleDao.getPortaluserroleListWithPage(portaluserroleQuery));

      page.setTotalCount(this.portaluserroleDao.getPortaluserroleListCount(portaluserroleQuery).intValue());
      page.setPageNo(portaluserroleQuery.getPage());
      page.setPageSize(portaluserroleQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portaluserrole pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaluserrole> getPortaluserroleList(PortaluserroleQuery portaluserroleQuery)
  {
    try
    {
      return this.portaluserroleDao.getPortaluserroleList(portaluserroleQuery);
    } catch (SQLException e) {
      log.error("get Portaluserrole list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortaluserroleCount(PortaluserroleQuery portaluserroleQuery)
  {
    try
    {
      return this.portaluserroleDao.getPortaluserroleListCount(portaluserroleQuery);
    } catch (SQLException e) {
      log.error("get Portaluserrole list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaluserroleServiceImpl
 * JD-Core Version:    0.6.2
 */