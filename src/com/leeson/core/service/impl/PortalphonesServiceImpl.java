package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalphones;
import com.leeson.core.dao.PortalphonesDao;
import com.leeson.core.query.PortalphonesQuery;
import com.leeson.core.service.PortalphonesService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalphonesServiceImpl
  implements PortalphonesService
{
  private static final Log log = LogFactory.getLog(PortalphonesServiceImpl.class);

  @Resource
  PortalphonesDao portalphonesDao;

  public Long addPortalphones(Portalphones portalphones)
  {
    try
    {
      return this.portalphonesDao.addPortalphones(portalphones);
    } catch (SQLException e) {
      log.error("dao addPortalphones error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalphones getPortalphonesByKey(Long id)
  {
    try
    {
      return this.portalphonesDao.getPortalphonesByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalphonesbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalphones> getPortalphonesByKeys(List<Long> idList)
  {
    try {
      return this.portalphonesDao.getPortalphonesByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalphonessByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalphonesDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalphonesQuery portalphonesQuery)
  {
    try
    {
      return this.portalphonesDao.deleteByQuery(portalphonesQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalphonesDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalphonesDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalphonesByKey(Portalphones portalphones)
  {
    try
    {
      return this.portalphonesDao.updatePortalphonesByKey(portalphones);
    } catch (SQLException e) {
      log.error("dao updatePortalphones error.Portalphones:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalphonesByKeyAll(Portalphones portalphones)
  {
    try
    {
      return this.portalphonesDao.updatePortalphonesByKeyAll(portalphones);
    } catch (SQLException e) {
      log.error("dao updatePortalphonesAll error.Portalphones:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalphonesListWithPage(PortalphonesQuery portalphonesQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalphonesDao.getPortalphonesListWithPage(portalphonesQuery));

      page.setTotalCount(this.portalphonesDao.getPortalphonesListCount(portalphonesQuery).intValue());
      page.setPageNo(portalphonesQuery.getPage());
      page.setPageSize(portalphonesQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalphones pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalphones> getPortalphonesList(PortalphonesQuery portalphonesQuery)
  {
    try
    {
      return this.portalphonesDao.getPortalphonesList(portalphonesQuery);
    } catch (SQLException e) {
      log.error("get Portalphones list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalphonesCount(PortalphonesQuery portalphonesQuery)
  {
    try
    {
      return this.portalphonesDao.getPortalphonesListCount(portalphonesQuery);
    } catch (SQLException e) {
      log.error("get Portalphones list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalphonesServiceImpl
 * JD-Core Version:    0.6.2
 */