package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallinkrecordallout;
import com.leeson.core.dao.PortallinkrecordalloutDao;
import com.leeson.core.query.PortallinkrecordalloutQuery;
import com.leeson.core.service.PortallinkrecordalloutService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortallinkrecordalloutServiceImpl
  implements PortallinkrecordalloutService
{
  private static final Log log = LogFactory.getLog(PortallinkrecordalloutServiceImpl.class);

  @Resource
  PortallinkrecordalloutDao portallinkrecordalloutDao;

  public Long addPortallinkrecordallout(Portallinkrecordallout portallinkrecordallout)
  {
    try
    {
      return this.portallinkrecordalloutDao.addPortallinkrecordallout(portallinkrecordallout);
    } catch (SQLException e) {
      log.error("dao addPortallinkrecordallout error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portallinkrecordallout getPortallinkrecordalloutByKey(Long id)
  {
    try
    {
      return this.portallinkrecordalloutDao.getPortallinkrecordalloutByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortallinkrecordalloutbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallinkrecordallout> getPortallinkrecordalloutByKeys(List<Long> idList)
  {
    try {
      return this.portallinkrecordalloutDao.getPortallinkrecordalloutByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortallinkrecordalloutsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portallinkrecordalloutDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
  {
    try
    {
      return this.portallinkrecordalloutDao.deleteByQuery(portallinkrecordalloutQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portallinkrecordalloutDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portallinkrecordalloutDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortallinkrecordalloutByKey(Portallinkrecordallout portallinkrecordallout)
  {
    try
    {
      return this.portallinkrecordalloutDao.updatePortallinkrecordalloutByKey(portallinkrecordallout);
    } catch (SQLException e) {
      log.error("dao updatePortallinkrecordallout error.Portallinkrecordallout:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortallinkrecordalloutByKeyAll(Portallinkrecordallout portallinkrecordallout)
  {
    try
    {
      return this.portallinkrecordalloutDao.updatePortallinkrecordalloutByKeyAll(portallinkrecordallout);
    } catch (SQLException e) {
      log.error("dao updatePortallinkrecordalloutAll error.Portallinkrecordallout:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortallinkrecordalloutListWithPage(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portallinkrecordalloutDao.getPortallinkrecordalloutListWithPage(portallinkrecordalloutQuery));

      page.setTotalCount(this.portallinkrecordalloutDao.getPortallinkrecordalloutListCount(portallinkrecordalloutQuery).intValue());
      page.setPageNo(portallinkrecordalloutQuery.getPage());
      page.setPageSize(portallinkrecordalloutQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portallinkrecordallout pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallinkrecordallout> getPortallinkrecordalloutList(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
  {
    try
    {
      return this.portallinkrecordalloutDao.getPortallinkrecordalloutList(portallinkrecordalloutQuery);
    } catch (SQLException e) {
      log.error("get Portallinkrecordallout list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortallinkrecordalloutCount(PortallinkrecordalloutQuery portallinkrecordalloutQuery)
  {
    try
    {
      return this.portallinkrecordalloutDao.getPortallinkrecordalloutListCount(portallinkrecordalloutQuery);
    } catch (SQLException e) {
      log.error("get Portallinkrecordallout list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallinkrecordalloutServiceImpl
 * JD-Core Version:    0.6.2
 */