package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.dao.PortalonlinelimitDao;
import com.leeson.core.query.PortalonlinelimitQuery;
import com.leeson.core.service.PortalonlinelimitService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalonlinelimitServiceImpl
  implements PortalonlinelimitService
{
  private static final Log log = LogFactory.getLog(PortalonlinelimitServiceImpl.class);

  @Resource
  PortalonlinelimitDao portalonlinelimitDao;

  public Long addPortalonlinelimit(Portalonlinelimit portalonlinelimit)
  {
    try
    {
      return this.portalonlinelimitDao.addPortalonlinelimit(portalonlinelimit);
    } catch (SQLException e) {
      log.error("dao addPortalonlinelimit error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalonlinelimit getPortalonlinelimitByKey(Long id)
  {
    try
    {
      return this.portalonlinelimitDao.getPortalonlinelimitByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalonlinelimitbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalonlinelimit> getPortalonlinelimitByKeys(List<Long> idList)
  {
    try {
      return this.portalonlinelimitDao.getPortalonlinelimitByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalonlinelimitsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalonlinelimitDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalonlinelimitQuery portalonlinelimit)
  {
    try
    {
      return this.portalonlinelimitDao.deleteByQuery(portalonlinelimit);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalonlinelimitDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalonlinelimitByKey(Portalonlinelimit portalonlinelimit)
  {
    try
    {
      return this.portalonlinelimitDao.updatePortalonlinelimitByKey(portalonlinelimit);
    } catch (SQLException e) {
      log.error("dao updatePortalonlinelimit error.Portalonlinelimit:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalonlinelimitByKeyAll(Portalonlinelimit portalonlinelimit)
  {
    try
    {
      return this.portalonlinelimitDao.updatePortalonlinelimitByKeyAll(portalonlinelimit);
    } catch (SQLException e) {
      log.error("dao updatePortalonlinelimitAll error.Portalonlinelimit:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalonlinelimitListWithPage(PortalonlinelimitQuery portalonlinelimitQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalonlinelimitDao.getPortalonlinelimitListWithPage(portalonlinelimitQuery));

      page.setTotalCount(this.portalonlinelimitDao.getPortalonlinelimitListCount(portalonlinelimitQuery).intValue());
      page.setPageNo(portalonlinelimitQuery.getPage());
      page.setPageSize(portalonlinelimitQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalonlinelimit pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalonlinelimit> getPortalonlinelimitList(PortalonlinelimitQuery portalonlinelimitQuery)
  {
    try
    {
      return this.portalonlinelimitDao.getPortalonlinelimitList(portalonlinelimitQuery);
    } catch (SQLException e) {
      log.error("get Portalonlinelimit list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalonlinelimitCount(PortalonlinelimitQuery portalonlinelimitQuery)
  {
    try
    {
      return this.portalonlinelimitDao.getPortalonlinelimitListCount(portalonlinelimitQuery);
    } catch (SQLException e) {
      log.error("get Portalonlinelimit list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalonlinelimitServiceImpl
 * JD-Core Version:    0.6.2
 */