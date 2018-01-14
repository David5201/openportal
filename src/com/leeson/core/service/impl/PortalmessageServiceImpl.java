package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalmessage;
import com.leeson.core.dao.PortalmessageDao;
import com.leeson.core.query.PortalmessageQuery;
import com.leeson.core.service.PortalmessageService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalmessageServiceImpl
  implements PortalmessageService
{
  private static final Log log = LogFactory.getLog(PortalmessageServiceImpl.class);

  @Resource
  PortalmessageDao portalmessageDao;

  public Long addPortalmessage(Portalmessage portalmessage)
  {
    try
    {
      return this.portalmessageDao.addPortalmessage(portalmessage);
    } catch (SQLException e) {
      log.error("dao addPortalmessage error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalmessage getPortalmessageByKey(Long id)
  {
    try
    {
      return this.portalmessageDao.getPortalmessageByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalmessagebyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalmessage> getPortalmessageByKeys(List<Long> idList)
  {
    try {
      return this.portalmessageDao.getPortalmessageByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalmessagesByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalmessageDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalmessageQuery portalmessage)
  {
    try
    {
      return this.portalmessageDao.deleteByQuery(portalmessage);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalmessageDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalmessageByKey(Portalmessage portalmessage)
  {
    try
    {
      return this.portalmessageDao.updatePortalmessageByKey(portalmessage);
    } catch (SQLException e) {
      log.error("dao updatePortalmessage error.Portalmessage:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalmessageListWithPage(PortalmessageQuery portalmessageQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalmessageDao.getPortalmessageListWithPage(portalmessageQuery));

      page.setTotalCount(this.portalmessageDao.getPortalmessageListCount(portalmessageQuery).intValue());
      page.setPageNo(portalmessageQuery.getPage());
      page.setPageSize(portalmessageQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalmessage pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalmessage> getPortalmessageList(PortalmessageQuery portalmessageQuery)
  {
    try
    {
      return this.portalmessageDao.getPortalmessageList(portalmessageQuery);
    } catch (SQLException e) {
      log.error("get Portalmessage list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalmessageCount(PortalmessageQuery portalmessageQuery)
  {
    try
    {
      return this.portalmessageDao.getPortalmessageListCount(portalmessageQuery);
    } catch (SQLException e) {
      log.error("get Portalmessage list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalmessageServiceImpl
 * JD-Core Version:    0.6.2
 */