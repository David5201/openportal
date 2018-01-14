package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.dao.PortalwebDao;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.PortalwebService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalwebServiceImpl
  implements PortalwebService
{
  private static final Log log = LogFactory.getLog(PortalwebServiceImpl.class);

  @Resource
  PortalwebDao portalwebDao;

  public Long addPortalweb(Portalweb portalweb)
  {
    try
    {
      return this.portalwebDao.addPortalweb(portalweb);
    } catch (SQLException e) {
      log.error("dao addPortalweb error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalweb getPortalwebByKey(Long id)
  {
    try
    {
      return this.portalwebDao.getPortalwebByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalwebbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalweb> getPortalwebByKeys(List<Long> idList)
  {
    try {
      return this.portalwebDao.getPortalwebByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalwebsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalwebDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalwebQuery portalwebQuery)
  {
    try
    {
      return this.portalwebDao.deleteByQuery(portalwebQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalwebDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalwebDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalwebByKey(Portalweb portalweb)
  {
    try
    {
      return this.portalwebDao.updatePortalwebByKey(portalweb);
    } catch (SQLException e) {
      log.error("dao updatePortalweb error.Portalweb:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalwebByKeyAll(Portalweb portalweb)
  {
    try
    {
      return this.portalwebDao.updatePortalwebByKeyAll(portalweb);
    } catch (SQLException e) {
      log.error("dao updatePortalwebAll error.Portalweb:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalwebListWithPage(PortalwebQuery portalwebQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalwebDao.getPortalwebListWithPage(portalwebQuery));

      page.setTotalCount(this.portalwebDao.getPortalwebListCount(portalwebQuery).intValue());
      page.setPageNo(portalwebQuery.getPage());
      page.setPageSize(portalwebQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalweb pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalweb> getPortalwebList(PortalwebQuery portalwebQuery)
  {
    try
    {
      return this.portalwebDao.getPortalwebList(portalwebQuery);
    } catch (SQLException e) {
      log.error("get Portalweb list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalwebCount(PortalwebQuery portalwebQuery)
  {
    try
    {
      return this.portalwebDao.getPortalwebListCount(portalwebQuery);
    } catch (SQLException e) {
      log.error("get Portalweb list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalwebServiceImpl
 * JD-Core Version:    0.6.2
 */