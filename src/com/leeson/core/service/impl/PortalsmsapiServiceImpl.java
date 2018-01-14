package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.dao.PortalsmsapiDao;
import com.leeson.core.query.PortalsmsapiQuery;
import com.leeson.core.service.PortalsmsapiService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalsmsapiServiceImpl
  implements PortalsmsapiService
{
  private static final Log log = LogFactory.getLog(PortalsmsapiServiceImpl.class);

  @Resource
  PortalsmsapiDao portalsmsapiDao;

  public Long addPortalsmsapi(Portalsmsapi portalsmsapi)
  {
    try
    {
      return this.portalsmsapiDao.addPortalsmsapi(portalsmsapi);
    } catch (SQLException e) {
      log.error("dao addPortalsmsapi error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalsmsapi getPortalsmsapiByKey(Long id)
  {
    try
    {
      return this.portalsmsapiDao.getPortalsmsapiByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalsmsapibyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalsmsapi> getPortalsmsapiByKeys(List<Long> idList)
  {
    try {
      return this.portalsmsapiDao.getPortalsmsapiByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalsmsapisByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalsmsapiDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalsmsapiQuery portalsmsapiQuery)
  {
    try
    {
      return this.portalsmsapiDao.deleteByQuery(portalsmsapiQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalsmsapiDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalsmsapiByKey(Portalsmsapi portalsmsapi)
  {
    try
    {
      return this.portalsmsapiDao.updatePortalsmsapiByKey(portalsmsapi);
    } catch (SQLException e) {
      log.error("dao updatePortalsmsapi error.Portalsmsapi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalsmsapiByKeyAll(Portalsmsapi portalsmsapi)
  {
    try
    {
      return this.portalsmsapiDao.updatePortalsmsapiByKeyAll(portalsmsapi);
    } catch (SQLException e) {
      log.error("dao updatePortalsmsapiAll error.Portalsmsapi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalsmsapiListWithPage(PortalsmsapiQuery portalsmsapiQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalsmsapiDao.getPortalsmsapiListWithPage(portalsmsapiQuery));

      page.setTotalCount(this.portalsmsapiDao.getPortalsmsapiListCount(portalsmsapiQuery).intValue());
      page.setPageNo(portalsmsapiQuery.getPage());
      page.setPageSize(portalsmsapiQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalsmsapi pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalsmsapi> getPortalsmsapiList(PortalsmsapiQuery portalsmsapiQuery)
  {
    try
    {
      return this.portalsmsapiDao.getPortalsmsapiList(portalsmsapiQuery);
    } catch (SQLException e) {
      log.error("get Portalsmsapi list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalsmsapiCount(PortalsmsapiQuery portalsmsapiQuery)
  {
    try
    {
      return this.portalsmsapiDao.getPortalsmsapiListCount(portalsmsapiQuery);
    } catch (SQLException e) {
      log.error("get Portalsmsapi list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalsmsapiServiceImpl
 * JD-Core Version:    0.6.2
 */