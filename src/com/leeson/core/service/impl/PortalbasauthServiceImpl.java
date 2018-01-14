package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.dao.PortalbasauthDao;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.service.PortalbasauthService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalbasauthServiceImpl
  implements PortalbasauthService
{
  private static final Log log = LogFactory.getLog(PortalbasauthServiceImpl.class);

  @Resource
  PortalbasauthDao portalbasauthDao;

  public Long addPortalbasauth(Portalbasauth portalbasauth)
  {
    try
    {
      return this.portalbasauthDao.addPortalbasauth(portalbasauth);
    } catch (SQLException e) {
      log.error("dao addPortalbasauth error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalbasauth getPortalbasauthByKey(Long id)
  {
    try
    {
      return this.portalbasauthDao.getPortalbasauthByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalbasauthbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalbasauth> getPortalbasauthByKeys(List<Long> idList)
  {
    try {
      return this.portalbasauthDao.getPortalbasauthByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalbasauthsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalbasauthDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalbasauthQuery portalbasauthQuery)
  {
    try
    {
      return this.portalbasauthDao.deleteByQuery(portalbasauthQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalbasauthDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalbasauthByKey(Portalbasauth portalbasauth)
  {
    try
    {
      return this.portalbasauthDao.updatePortalbasauthByKey(portalbasauth);
    } catch (SQLException e) {
      log.error("dao updatePortalbasauth error.Portalbasauth:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalbasauthByKeyAll(Portalbasauth portalbasauth)
  {
    try
    {
      return this.portalbasauthDao.updatePortalbasauthByKeyAll(portalbasauth);
    } catch (SQLException e) {
      log.error("dao updatePortalbasauthAll error.Portalbasauth:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalbasauthListWithPage(PortalbasauthQuery portalbasauthQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalbasauthDao.getPortalbasauthListWithPage(portalbasauthQuery));

      page.setTotalCount(this.portalbasauthDao.getPortalbasauthListCount(portalbasauthQuery).intValue());
      page.setPageNo(portalbasauthQuery.getPage());
      page.setPageSize(portalbasauthQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalbasauth pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalbasauth> getPortalbasauthList(PortalbasauthQuery portalbasauthQuery)
  {
    try
    {
      return this.portalbasauthDao.getPortalbasauthList(portalbasauthQuery);
    } catch (SQLException e) {
      log.error("get Portalbasauth list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalbasauthCount(PortalbasauthQuery portalbasauthQuery)
  {
    try
    {
      return this.portalbasauthDao.getPortalbasauthListCount(portalbasauthQuery);
    } catch (SQLException e) {
      log.error("get Portalbasauth list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalbasauthServiceImpl
 * JD-Core Version:    0.6.2
 */