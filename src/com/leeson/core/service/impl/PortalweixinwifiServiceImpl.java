package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.dao.PortalweixinwifiDao;
import com.leeson.core.query.PortalweixinwifiQuery;
import com.leeson.core.service.PortalweixinwifiService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalweixinwifiServiceImpl
  implements PortalweixinwifiService
{
  private static final Log log = LogFactory.getLog(PortalweixinwifiServiceImpl.class);

  @Resource
  PortalweixinwifiDao portalweixinwifiDao;

  public Long addPortalweixinwifi(Portalweixinwifi portalweixinwifi)
  {
    try
    {
      return this.portalweixinwifiDao.addPortalweixinwifi(portalweixinwifi);
    } catch (SQLException e) {
      log.error("dao addPortalweixinwifi error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalweixinwifi getPortalweixinwifiByKey(Long id)
  {
    try
    {
      return this.portalweixinwifiDao.getPortalweixinwifiByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalweixinwifibyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalweixinwifi> getPortalweixinwifiByKeys(List<Long> idList)
  {
    try {
      return this.portalweixinwifiDao.getPortalweixinwifiByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalweixinwifisByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalweixinwifiDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalweixinwifiQuery portalweixinwifi)
  {
    try
    {
      return this.portalweixinwifiDao.deleteByQuery(portalweixinwifi);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalweixinwifiDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalweixinwifiByKey(Portalweixinwifi portalweixinwifi)
  {
    try
    {
      return this.portalweixinwifiDao.updatePortalweixinwifiByKey(portalweixinwifi);
    } catch (SQLException e) {
      log.error("dao updatePortalweixinwifi error.Portalweixinwifi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalweixinwifiByKeyAll(Portalweixinwifi portalweixinwifi)
  {
    try
    {
      return this.portalweixinwifiDao.updatePortalweixinwifiByKeyAll(portalweixinwifi);
    } catch (SQLException e) {
      log.error("dao updatePortalweixinwifiAll error.Portalweixinwifi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalweixinwifiListWithPage(PortalweixinwifiQuery portalweixinwifiQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalweixinwifiDao.getPortalweixinwifiListWithPage(portalweixinwifiQuery));

      page.setTotalCount(this.portalweixinwifiDao.getPortalweixinwifiListCount(portalweixinwifiQuery).intValue());
      page.setPageNo(portalweixinwifiQuery.getPage());
      page.setPageSize(portalweixinwifiQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalweixinwifi pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalweixinwifi> getPortalweixinwifiList(PortalweixinwifiQuery portalweixinwifiQuery)
  {
    try
    {
      return this.portalweixinwifiDao.getPortalweixinwifiList(portalweixinwifiQuery);
    } catch (SQLException e) {
      log.error("get Portalweixinwifi list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalweixinwifiCount(PortalweixinwifiQuery portalweixinwifiQuery)
  {
    try
    {
      return this.portalweixinwifiDao.getPortalweixinwifiListCount(portalweixinwifiQuery);
    } catch (SQLException e) {
      log.error("get Portalweixinwifi list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalweixinwifiServiceImpl
 * JD-Core Version:    0.6.2
 */