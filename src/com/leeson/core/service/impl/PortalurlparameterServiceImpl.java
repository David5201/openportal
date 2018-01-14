package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalurlparameter;
import com.leeson.core.dao.PortalurlparameterDao;
import com.leeson.core.query.PortalurlparameterQuery;
import com.leeson.core.service.PortalurlparameterService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalurlparameterServiceImpl
  implements PortalurlparameterService
{
  private static final Log log = LogFactory.getLog(PortalurlparameterServiceImpl.class);

  @Resource
  PortalurlparameterDao portalurlparameterDao;

  public Long addPortalurlparameter(Portalurlparameter portalurlparameter)
  {
    try
    {
      return this.portalurlparameterDao.addPortalurlparameter(portalurlparameter);
    } catch (SQLException e) {
      log.error("dao addPortalurlparameter error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalurlparameter getPortalurlparameterByKey(Long id)
  {
    try
    {
      return this.portalurlparameterDao.getPortalurlparameterByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalurlparameterbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalurlparameter> getPortalurlparameterByKeys(List<Long> idList)
  {
    try {
      return this.portalurlparameterDao.getPortalurlparameterByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalurlparametersByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalurlparameterDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalurlparameterQuery portalurlparameterQuery)
  {
    try
    {
      return this.portalurlparameterDao.deleteByQuery(portalurlparameterQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalurlparameterDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalurlparameterByKey(Portalurlparameter portalurlparameter)
  {
    try
    {
      return this.portalurlparameterDao.updatePortalurlparameterByKey(portalurlparameter);
    } catch (SQLException e) {
      log.error("dao updatePortalurlparameter error.Portalurlparameter:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalurlparameterByKeyAll(Portalurlparameter portalurlparameter)
  {
    try
    {
      return this.portalurlparameterDao.updatePortalurlparameterByKeyAll(portalurlparameter);
    } catch (SQLException e) {
      log.error("dao updatePortalurlparameterAll error.Portalurlparameter:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalurlparameterListWithPage(PortalurlparameterQuery portalurlparameterQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalurlparameterDao.getPortalurlparameterListWithPage(portalurlparameterQuery));

      page.setTotalCount(this.portalurlparameterDao.getPortalurlparameterListCount(portalurlparameterQuery).intValue());
      page.setPageNo(portalurlparameterQuery.getPage());
      page.setPageSize(portalurlparameterQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalurlparameter pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalurlparameter> getPortalurlparameterList(PortalurlparameterQuery portalurlparameterQuery)
  {
    try
    {
      return this.portalurlparameterDao.getPortalurlparameterList(portalurlparameterQuery);
    } catch (SQLException e) {
      log.error("get Portalurlparameter list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalurlparameterCount(PortalurlparameterQuery portalurlparameterQuery)
  {
    try
    {
      return this.portalurlparameterDao.getPortalurlparameterListCount(portalurlparameterQuery);
    } catch (SQLException e) {
      log.error("get Portalurlparameter list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalurlparameterServiceImpl
 * JD-Core Version:    0.6.2
 */