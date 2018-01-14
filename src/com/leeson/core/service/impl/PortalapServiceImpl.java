package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalap;
import com.leeson.core.dao.PortalapDao;
import com.leeson.core.query.PortalapQuery;
import com.leeson.core.service.PortalapService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalapServiceImpl
  implements PortalapService
{
  private static final Log log = LogFactory.getLog(PortalapServiceImpl.class);

  @Resource
  PortalapDao portalapDao;

  public Long addPortalap(Portalap portalap)
  {
    try
    {
      return this.portalapDao.addPortalap(portalap);
    } catch (SQLException e) {
      log.error("dao addPortalap error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalap getPortalapByKey(Long id)
  {
    try
    {
      return this.portalapDao.getPortalapByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalapbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalap> getPortalapByKeys(List<Long> idList)
  {
    try {
      return this.portalapDao.getPortalapByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalapsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalapDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalapQuery portalapQuery)
  {
    try
    {
      return this.portalapDao.deleteByQuery(portalapQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalapDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalapByKey(Portalap portalap)
  {
    try
    {
      return this.portalapDao.updatePortalapByKey(portalap);
    } catch (SQLException e) {
      log.error("dao updatePortalap error.Portalap:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalapByKeyAll(Portalap portalap)
  {
    try
    {
      return this.portalapDao.updatePortalapByKeyAll(portalap);
    } catch (SQLException e) {
      log.error("dao updatePortalapAll error.Portalap:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalapListWithPage(PortalapQuery portalapQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalapDao.getPortalapListWithPage(portalapQuery));

      page.setTotalCount(this.portalapDao.getPortalapListCount(portalapQuery).intValue());
      page.setPageNo(portalapQuery.getPage());
      page.setPageSize(portalapQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalap pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalap> getPortalapList(PortalapQuery portalapQuery)
  {
    try
    {
      return this.portalapDao.getPortalapList(portalapQuery);
    } catch (SQLException e) {
      log.error("get Portalap list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalapCount(PortalapQuery portalapQuery)
  {
    try
    {
      return this.portalapDao.getPortalapListCount(portalapQuery);
    } catch (SQLException e) {
      log.error("get Portalap list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalapServiceImpl
 * JD-Core Version:    0.6.2
 */