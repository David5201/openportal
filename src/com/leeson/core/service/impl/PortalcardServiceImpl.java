package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.dao.PortalcardDao;
import com.leeson.core.query.PortalcardQuery;
import com.leeson.core.service.PortalcardService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalcardServiceImpl
  implements PortalcardService
{
  private static final Log log = LogFactory.getLog(PortalcardServiceImpl.class);

  @Resource
  PortalcardDao portalcardDao;

  public Long addPortalcard(Portalcard portalcard)
  {
    try
    {
      return this.portalcardDao.addPortalcard(portalcard);
    } catch (SQLException e) {
      log.error("dao addPortalcard error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalcard getPortalcardByKey(Long id)
  {
    try
    {
      return this.portalcardDao.getPortalcardByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalcardbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalcard> getPortalcardByKeys(List<Long> idList)
  {
    try {
      return this.portalcardDao.getPortalcardByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalcardsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalcardDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalcardQuery portalcardQuery)
  {
    try
    {
      return this.portalcardDao.deleteByQuery(portalcardQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalcardDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalcardDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalcardByKey(Portalcard portalcard)
  {
    try
    {
      return this.portalcardDao.updatePortalcardByKey(portalcard);
    } catch (SQLException e) {
      log.error("dao updatePortalcard error.Portalcard:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalcardByKeyAll(Portalcard portalcard)
  {
    try
    {
      return this.portalcardDao.updatePortalcardByKeyAll(portalcard);
    } catch (SQLException e) {
      log.error("dao updatePortalcardAll error.Portalcard:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalcardListWithPage(PortalcardQuery portalcardQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalcardDao.getPortalcardListWithPage(portalcardQuery));

      page.setTotalCount(this.portalcardDao.getPortalcardListCount(portalcardQuery).intValue());
      page.setPageNo(portalcardQuery.getPage());
      page.setPageSize(portalcardQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalcard pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalcard> getPortalcardList(PortalcardQuery portalcardQuery)
  {
    try
    {
      return this.portalcardDao.getPortalcardList(portalcardQuery);
    } catch (SQLException e) {
      log.error("get Portalcard list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalcardCount(PortalcardQuery portalcardQuery)
  {
    try
    {
      return this.portalcardDao.getPortalcardListCount(portalcardQuery);
    } catch (SQLException e) {
      log.error("get Portalcard list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalcardSaleListWithPage(PortalcardQuery portalcardQuery)
  {
    try
    {
      Pagination page = new Pagination();
      page.setList(this.portalcardDao.getPortalcardSaleListWithPage(portalcardQuery));

      page.setTotalCount(this.portalcardDao.getPortalcardSaleListCount(portalcardQuery).intValue());
      page.setPageNo(portalcardQuery.getPage());
      page.setPageSize(portalcardQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalcard pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalcard> getPortalcardSaleList(PortalcardQuery portalcardQuery)
  {
    try
    {
      return this.portalcardDao.getPortalcardSaleList(portalcardQuery);
    } catch (SQLException e) {
      log.error("get Portalcard list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalcardSaleCount(PortalcardQuery portalcardQuery)
  {
    try
    {
      return this.portalcardDao.getPortalcardSaleListCount(portalcardQuery);
    } catch (SQLException e) {
      log.error("get Portalcard list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalcardServiceImpl
 * JD-Core Version:    0.6.2
 */