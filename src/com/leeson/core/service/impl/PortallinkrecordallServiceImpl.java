package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallinkrecordall;
import com.leeson.core.dao.PortallinkrecordallDao;
import com.leeson.core.query.PortallinkrecordallQuery;
import com.leeson.core.service.PortallinkrecordallService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortallinkrecordallServiceImpl
  implements PortallinkrecordallService
{
  private static final Log log = LogFactory.getLog(PortallinkrecordallServiceImpl.class);

  @Resource
  PortallinkrecordallDao portallinkrecordallDao;

  public Long addPortallinkrecordall(Portallinkrecordall portallinkrecordall)
  {
    try
    {
      return this.portallinkrecordallDao.addPortallinkrecordall(portallinkrecordall);
    } catch (SQLException e) {
      log.error("dao addPortallinkrecordall error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portallinkrecordall getPortallinkrecordallByKey(Long id)
  {
    try
    {
      return this.portallinkrecordallDao.getPortallinkrecordallByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortallinkrecordallbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallinkrecordall> getPortallinkrecordallByKeys(List<Long> idList)
  {
    try {
      return this.portallinkrecordallDao.getPortallinkrecordallByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortallinkrecordallsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portallinkrecordallDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortallinkrecordallQuery portallinkrecordallQuery)
  {
    try
    {
      return this.portallinkrecordallDao.deleteByQuery(portallinkrecordallQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portallinkrecordallDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portallinkrecordallDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortallinkrecordallByKey(Portallinkrecordall portallinkrecordall)
  {
    try
    {
      return this.portallinkrecordallDao.updatePortallinkrecordallByKey(portallinkrecordall);
    } catch (SQLException e) {
      log.error("dao updatePortallinkrecordall error.Portallinkrecordall:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortallinkrecordallByKeyAll(Portallinkrecordall portallinkrecordall)
  {
    try
    {
      return this.portallinkrecordallDao.updatePortallinkrecordallByKeyAll(portallinkrecordall);
    } catch (SQLException e) {
      log.error("dao updatePortallinkrecordallAll error.Portallinkrecordall:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortallinkrecordallListWithPage(PortallinkrecordallQuery portallinkrecordallQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portallinkrecordallDao.getPortallinkrecordallListWithPage(portallinkrecordallQuery));

      page.setTotalCount(this.portallinkrecordallDao.getPortallinkrecordallListCount(portallinkrecordallQuery).intValue());
      page.setPageNo(portallinkrecordallQuery.getPage());
      page.setPageSize(portallinkrecordallQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portallinkrecordall pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallinkrecordall> getPortallinkrecordallList(PortallinkrecordallQuery portallinkrecordallQuery)
  {
    try
    {
      return this.portallinkrecordallDao.getPortallinkrecordallList(portallinkrecordallQuery);
    } catch (SQLException e) {
      log.error("get Portallinkrecordall list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortallinkrecordallCount(PortallinkrecordallQuery portallinkrecordallQuery)
  {
    try
    {
      return this.portallinkrecordallDao.getPortallinkrecordallListCount(portallinkrecordallQuery);
    } catch (SQLException e) {
      log.error("get Portallinkrecordall list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallinkrecordallServiceImpl
 * JD-Core Version:    0.6.2
 */