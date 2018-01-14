package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.dao.PortalsmslogsDao;
import com.leeson.core.query.PortalsmslogsQuery;
import com.leeson.core.service.PortalsmslogsService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalsmslogsServiceImpl
  implements PortalsmslogsService
{
  private static final Log log = LogFactory.getLog(PortalsmslogsServiceImpl.class);

  @Resource
  PortalsmslogsDao portalsmslogsDao;

  public Long addPortalsmslogs(Portalsmslogs portalsmslogs)
  {
    try
    {
      return this.portalsmslogsDao.addPortalsmslogs(portalsmslogs);
    } catch (SQLException e) {
      log.error("dao addPortalsmslogs error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalsmslogs getPortalsmslogsByKey(Long id)
  {
    try
    {
      return this.portalsmslogsDao.getPortalsmslogsByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalsmslogsbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalsmslogs> getPortalsmslogsByKeys(List<Long> idList)
  {
    try {
      return this.portalsmslogsDao.getPortalsmslogsByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalsmslogssByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalsmslogsDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalsmslogsQuery portalsmslogsQuery)
  {
    try
    {
      return this.portalsmslogsDao.deleteByQuery(portalsmslogsQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalsmslogsDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalsmslogsDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalsmslogsByKey(Portalsmslogs portalsmslogs)
  {
    try
    {
      return this.portalsmslogsDao.updatePortalsmslogsByKey(portalsmslogs);
    } catch (SQLException e) {
      log.error("dao updatePortalsmslogs error.Portalsmslogs:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalsmslogsByKeyAll(Portalsmslogs portalsmslogs)
  {
    try
    {
      return this.portalsmslogsDao.updatePortalsmslogsByKeyAll(portalsmslogs);
    } catch (SQLException e) {
      log.error("dao updatePortalsmslogsAll error.Portalsmslogs:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalsmslogsListWithPage(PortalsmslogsQuery portalsmslogsQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalsmslogsDao.getPortalsmslogsListWithPage(portalsmslogsQuery));

      page.setTotalCount(this.portalsmslogsDao.getPortalsmslogsListCount(portalsmslogsQuery).intValue());
      page.setPageNo(portalsmslogsQuery.getPage());
      page.setPageSize(portalsmslogsQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalsmslogs pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalsmslogs> getPortalsmslogsList(PortalsmslogsQuery portalsmslogsQuery)
  {
    try
    {
      return this.portalsmslogsDao.getPortalsmslogsList(portalsmslogsQuery);
    } catch (SQLException e) {
      log.error("get Portalsmslogs list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalsmslogsCount(PortalsmslogsQuery portalsmslogsQuery)
  {
    try
    {
      return this.portalsmslogsDao.getPortalsmslogsListCount(portalsmslogsQuery);
    } catch (SQLException e) {
      log.error("get Portalsmslogs list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalsmslogsServiceImpl
 * JD-Core Version:    0.6.2
 */