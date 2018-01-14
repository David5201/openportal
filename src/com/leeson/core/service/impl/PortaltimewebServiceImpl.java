package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaltimeweb;
import com.leeson.core.dao.PortaltimewebDao;
import com.leeson.core.query.PortaltimewebQuery;
import com.leeson.core.service.PortaltimewebService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortaltimewebServiceImpl
  implements PortaltimewebService
{
  private static final Log log = LogFactory.getLog(PortaltimewebServiceImpl.class);

  @Resource
  PortaltimewebDao portaltimewebDao;

  public Long addPortaltimeweb(Portaltimeweb portaltimeweb)
  {
    try
    {
      return this.portaltimewebDao.addPortaltimeweb(portaltimeweb);
    } catch (SQLException e) {
      log.error("dao addPortaltimeweb error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portaltimeweb getPortaltimewebByKey(Long id)
  {
    try
    {
      return this.portaltimewebDao.getPortaltimewebByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortaltimewebbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaltimeweb> getPortaltimewebByKeys(List<Long> idList)
  {
    try {
      return this.portaltimewebDao.getPortaltimewebByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortaltimewebsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portaltimewebDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortaltimewebQuery portaltimewebQuery)
  {
    try
    {
      return this.portaltimewebDao.deleteByQuery(portaltimewebQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portaltimewebDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portaltimewebDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortaltimewebByKey(Portaltimeweb portaltimeweb)
  {
    try
    {
      return this.portaltimewebDao.updatePortaltimewebByKey(portaltimeweb);
    } catch (SQLException e) {
      log.error("dao updatePortaltimeweb error.Portaltimeweb:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortaltimewebByKeyAll(Portaltimeweb portaltimeweb)
  {
    try
    {
      return this.portaltimewebDao.updatePortaltimewebByKeyAll(portaltimeweb);
    } catch (SQLException e) {
      log.error("dao updatePortaltimewebAll error.Portaltimeweb:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortaltimewebListWithPage(PortaltimewebQuery portaltimewebQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portaltimewebDao.getPortaltimewebListWithPage(portaltimewebQuery));

      page.setTotalCount(this.portaltimewebDao.getPortaltimewebListCount(portaltimewebQuery).intValue());
      page.setPageNo(portaltimewebQuery.getPage());
      page.setPageSize(portaltimewebQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portaltimeweb pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaltimeweb> getPortaltimewebList(PortaltimewebQuery portaltimewebQuery)
  {
    try
    {
      return this.portaltimewebDao.getPortaltimewebList(portaltimewebQuery);
    } catch (SQLException e) {
      log.error("get Portaltimeweb list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortaltimewebCount(PortaltimewebQuery portaltimewebQuery)
  {
    try
    {
      return this.portaltimewebDao.getPortaltimewebListCount(portaltimewebQuery);
    } catch (SQLException e) {
      log.error("get Portaltimeweb list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaltimewebServiceImpl
 * JD-Core Version:    0.6.2
 */