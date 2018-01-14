package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.dao.PortalorderDao;
import com.leeson.core.query.PortalorderQuery;
import com.leeson.core.service.PortalorderService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalorderServiceImpl
  implements PortalorderService
{
  private static final Log log = LogFactory.getLog(PortalorderServiceImpl.class);

  @Resource
  PortalorderDao portalorderDao;

  public Long addPortalorder(Portalorder portalorder)
  {
    try
    {
      return this.portalorderDao.addPortalorder(portalorder);
    } catch (SQLException e) {
      log.error("dao addPortalorder error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalorder getPortalorderByKey(Long id)
  {
    try
    {
      return this.portalorderDao.getPortalorderByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalorderbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalorder> getPortalorderByKeys(List<Long> idList)
  {
    try {
      return this.portalorderDao.getPortalorderByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalordersByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalorderDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalorderQuery portalorderQuery)
  {
    try
    {
      return this.portalorderDao.deleteByQuery(portalorderQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalorderDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalorderDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalorderByKey(Portalorder portalorder)
  {
    try
    {
      return this.portalorderDao.updatePortalorderByKey(portalorder);
    } catch (SQLException e) {
      log.error("dao updatePortalorder error.Portalorder:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalorderByKeyAll(Portalorder portalorder)
  {
    try
    {
      return this.portalorderDao.updatePortalorderByKeyAll(portalorder);
    } catch (SQLException e) {
      log.error("dao updatePortalorderAll error.Portalorder:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalorderListWithPage(PortalorderQuery portalorderQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalorderDao.getPortalorderListWithPage(portalorderQuery));

      page.setTotalCount(this.portalorderDao.getPortalorderListCount(portalorderQuery).intValue());
      page.setPageNo(portalorderQuery.getPage());
      page.setPageSize(portalorderQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalorder pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalorder> getPortalorderList(PortalorderQuery portalorderQuery)
  {
    try
    {
      return this.portalorderDao.getPortalorderList(portalorderQuery);
    } catch (SQLException e) {
      log.error("get Portalorder list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalorderCount(PortalorderQuery portalorderQuery)
  {
    try
    {
      return this.portalorderDao.getPortalorderListCount(portalorderQuery);
    } catch (SQLException e) {
      log.error("get Portalorder list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalorderServiceImpl
 * JD-Core Version:    0.6.2
 */