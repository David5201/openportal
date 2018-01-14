package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.dao.PortalssidDao;
import com.leeson.core.query.PortalssidQuery;
import com.leeson.core.service.PortalssidService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalssidServiceImpl
  implements PortalssidService
{
  private static final Log log = LogFactory.getLog(PortalssidServiceImpl.class);

  @Resource
  PortalssidDao portalssidDao;

  public Long addPortalssid(Portalssid portalssid)
  {
    try
    {
      return this.portalssidDao.addPortalssid(portalssid);
    } catch (SQLException e) {
      log.error("dao addPortalssid error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalssid getPortalssidByKey(Long id)
  {
    try
    {
      return this.portalssidDao.getPortalssidByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalssidbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalssid> getPortalssidByKeys(List<Long> idList)
  {
    try {
      return this.portalssidDao.getPortalssidByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalssidsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalssidDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalssidQuery portalssidQuery)
  {
    try
    {
      return this.portalssidDao.deleteByQuery(portalssidQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalssidDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalssidByKey(Portalssid portalssid)
  {
    try
    {
      return this.portalssidDao.updatePortalssidByKey(portalssid);
    } catch (SQLException e) {
      log.error("dao updatePortalssid error.Portalssid:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalssidByKeyAll(Portalssid portalssid)
  {
    try
    {
      return this.portalssidDao.updatePortalssidByKeyAll(portalssid);
    } catch (SQLException e) {
      log.error("dao updatePortalssidAll error.Portalssid:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalssidListWithPage(PortalssidQuery portalssidQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalssidDao.getPortalssidListWithPage(portalssidQuery));

      page.setTotalCount(this.portalssidDao.getPortalssidListCount(portalssidQuery).intValue());
      page.setPageNo(portalssidQuery.getPage());
      page.setPageSize(portalssidQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalssid pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalssid> getPortalssidList(PortalssidQuery portalssidQuery)
  {
    try
    {
      return this.portalssidDao.getPortalssidList(portalssidQuery);
    } catch (SQLException e) {
      log.error("get Portalssid list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalssidCount(PortalssidQuery portalssidQuery)
  {
    try
    {
      return this.portalssidDao.getPortalssidListCount(portalssidQuery);
    } catch (SQLException e) {
      log.error("get Portalssid list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalssidServiceImpl
 * JD-Core Version:    0.6.2
 */