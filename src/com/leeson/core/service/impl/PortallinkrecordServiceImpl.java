package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.dao.PortallinkrecordDao;
import com.leeson.core.query.PortallinkrecordQuery;
import com.leeson.core.service.PortallinkrecordService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortallinkrecordServiceImpl
  implements PortallinkrecordService
{
  private static final Log log = LogFactory.getLog(PortallinkrecordServiceImpl.class);

  @Resource
  PortallinkrecordDao portallinkrecordDao;

  public Long addPortallinkrecord(Portallinkrecord portallinkrecord)
  {
    try
    {
      return this.portallinkrecordDao.addPortallinkrecord(portallinkrecord);
    } catch (SQLException e) {
      log.error("dao addPortallinkrecord error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portallinkrecord getPortallinkrecordByKey(Long id)
  {
    try
    {
      return this.portallinkrecordDao.getPortallinkrecordByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortallinkrecordbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallinkrecord> getPortallinkrecordByKeys(List<Long> idList)
  {
    try {
      return this.portallinkrecordDao.getPortallinkrecordByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortallinkrecordsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portallinkrecordDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortallinkrecordQuery portallinkrecordQuery)
  {
    try
    {
      return this.portallinkrecordDao.deleteByQuery(portallinkrecordQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portallinkrecordDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portallinkrecordDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortallinkrecordByKey(Portallinkrecord portallinkrecord)
  {
    try
    {
      return this.portallinkrecordDao.updatePortallinkrecordByKey(portallinkrecord);
    } catch (SQLException e) {
      log.error("dao updatePortallinkrecord error.Portallinkrecord:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortallinkrecordByKeyAll(Portallinkrecord portallinkrecord)
  {
    try
    {
      return this.portallinkrecordDao.updatePortallinkrecordByKeyAll(portallinkrecord);
    } catch (SQLException e) {
      log.error("dao updatePortallinkrecordAll error.Portallinkrecord:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortallinkrecordListWithPage(PortallinkrecordQuery portallinkrecordQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portallinkrecordDao.getPortallinkrecordListWithPage(portallinkrecordQuery));

      page.setTotalCount(this.portallinkrecordDao.getPortallinkrecordListCount(portallinkrecordQuery).intValue());
      page.setPageNo(portallinkrecordQuery.getPage());
      page.setPageSize(portallinkrecordQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portallinkrecord pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallinkrecord> getPortallinkrecordList(PortallinkrecordQuery portallinkrecordQuery)
  {
    try
    {
      return this.portallinkrecordDao.getPortallinkrecordList(portallinkrecordQuery);
    } catch (SQLException e) {
      log.error("get Portallinkrecord list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortallinkrecordCount(PortallinkrecordQuery portallinkrecordQuery)
  {
    try
    {
      return this.portallinkrecordDao.getPortallinkrecordListCount(portallinkrecordQuery);
    } catch (SQLException e) {
      log.error("get Portallinkrecord list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallinkrecordServiceImpl
 * JD-Core Version:    0.6.2
 */