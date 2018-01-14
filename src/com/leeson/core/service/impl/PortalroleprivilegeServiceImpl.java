package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalroleprivilege;
import com.leeson.core.dao.PortalroleprivilegeDao;
import com.leeson.core.query.PortalroleprivilegeQuery;
import com.leeson.core.service.PortalroleprivilegeService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalroleprivilegeServiceImpl
  implements PortalroleprivilegeService
{
  private static final Log log = LogFactory.getLog(PortalroleprivilegeServiceImpl.class);

  @Resource
  PortalroleprivilegeDao portalroleprivilegeDao;

  public Long addPortalroleprivilege(Portalroleprivilege portalroleprivilege)
  {
    try
    {
      return this.portalroleprivilegeDao.addPortalroleprivilege(portalroleprivilege);
    } catch (SQLException e) {
      log.error("dao addPortalroleprivilege error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalroleprivilege getPortalroleprivilegeByKey(Long id)
  {
    try
    {
      return this.portalroleprivilegeDao.getPortalroleprivilegeByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalroleprivilegebyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalroleprivilege> getPortalroleprivilegeByKeys(List<Long> idList)
  {
    try {
      return this.portalroleprivilegeDao.getPortalroleprivilegeByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalroleprivilegesByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalroleprivilegeDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalroleprivilegeQuery portalroleprivilege)
  {
    try
    {
      return this.portalroleprivilegeDao.deleteByQuery(portalroleprivilege);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalroleprivilegeDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalroleprivilegeByKey(Portalroleprivilege portalroleprivilege)
  {
    try
    {
      return this.portalroleprivilegeDao.updatePortalroleprivilegeByKey(portalroleprivilege);
    } catch (SQLException e) {
      log.error("dao updatePortalroleprivilege error.Portalroleprivilege:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalroleprivilegeListWithPage(PortalroleprivilegeQuery portalroleprivilegeQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalroleprivilegeDao.getPortalroleprivilegeListWithPage(portalroleprivilegeQuery));

      page.setTotalCount(this.portalroleprivilegeDao.getPortalroleprivilegeListCount(portalroleprivilegeQuery).intValue());
      page.setPageNo(portalroleprivilegeQuery.getPage());
      page.setPageSize(portalroleprivilegeQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalroleprivilege pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalroleprivilege> getPortalroleprivilegeList(PortalroleprivilegeQuery portalroleprivilegeQuery)
  {
    try
    {
      return this.portalroleprivilegeDao.getPortalroleprivilegeList(portalroleprivilegeQuery);
    } catch (SQLException e) {
      log.error("get Portalroleprivilege list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalroleprivilegeCount(PortalroleprivilegeQuery portalroleprivilegeQuery)
  {
    try
    {
      return this.portalroleprivilegeDao.getPortalroleprivilegeListCount(portalroleprivilegeQuery);
    } catch (SQLException e) {
      log.error("get Portalroleprivilege list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalroleprivilegeServiceImpl
 * JD-Core Version:    0.6.2
 */