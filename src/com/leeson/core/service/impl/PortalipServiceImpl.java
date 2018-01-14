package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalip;
import com.leeson.core.dao.PortalipDao;
import com.leeson.core.query.PortalipQuery;
import com.leeson.core.service.PortalipService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalipServiceImpl
  implements PortalipService
{
  private static final Log log = LogFactory.getLog(PortalipServiceImpl.class);

  @Resource
  PortalipDao portalipDao;

  public Long addPortalip(Portalip portalip)
  {
    try
    {
      return this.portalipDao.addPortalip(portalip);
    } catch (SQLException e) {
      log.error("dao addPortalip error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalip getPortalipByKey(Long id)
  {
    try
    {
      return this.portalipDao.getPortalipByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalipbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalip> getPortalipByKeys(List<Long> idList)
  {
    try {
      return this.portalipDao.getPortalipByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalipsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalipDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalipQuery portalipQuery)
  {
    try
    {
      return this.portalipDao.deleteByQuery(portalipQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalipDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalipDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalipByKey(Portalip portalip)
  {
    try
    {
      return this.portalipDao.updatePortalipByKey(portalip);
    } catch (SQLException e) {
      log.error("dao updatePortalip error.Portalip:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalipByKeyAll(Portalip portalip)
  {
    try
    {
      return this.portalipDao.updatePortalipByKeyAll(portalip);
    } catch (SQLException e) {
      log.error("dao updatePortalipAll error.Portalip:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalipListWithPage(PortalipQuery portalipQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalipDao.getPortalipListWithPage(portalipQuery));

      page.setTotalCount(this.portalipDao.getPortalipListCount(portalipQuery).intValue());
      page.setPageNo(portalipQuery.getPage());
      page.setPageSize(portalipQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalip pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalip> getPortalipList(PortalipQuery portalipQuery)
  {
    try
    {
      return this.portalipDao.getPortalipList(portalipQuery);
    } catch (SQLException e) {
      log.error("get Portalip list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalipCount(PortalipQuery portalipQuery)
  {
    try
    {
      return this.portalipDao.getPortalipListCount(portalipQuery);
    } catch (SQLException e) {
      log.error("get Portalip list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalipServiceImpl
 * JD-Core Version:    0.6.2
 */