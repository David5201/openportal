package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.dao.PortallogrecordDao;
import com.leeson.core.query.PortallogrecordQuery;
import com.leeson.core.service.PortallogrecordService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortallogrecordServiceImpl
  implements PortallogrecordService
{
  private static final Log log = LogFactory.getLog(PortallogrecordServiceImpl.class);

  @Resource
  PortallogrecordDao portallogrecordDao;

  public Long addPortallogrecord(Portallogrecord portallogrecord)
  {
    try
    {
      return this.portallogrecordDao.addPortallogrecord(portallogrecord);
    } catch (SQLException e) {
      log.error("dao addPortallogrecord error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portallogrecord getPortallogrecordByKey(Long id)
  {
    try
    {
      return this.portallogrecordDao.getPortallogrecordByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortallogrecordbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallogrecord> getPortallogrecordByKeys(List<Long> idList)
  {
    try {
      return this.portallogrecordDao.getPortallogrecordByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortallogrecordsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portallogrecordDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortallogrecordQuery portallogrecord)
  {
    try
    {
      return this.portallogrecordDao.deleteByQuery(portallogrecord);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portallogrecordDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portallogrecordDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortallogrecordByKey(Portallogrecord portallogrecord)
  {
    try
    {
      return this.portallogrecordDao.updatePortallogrecordByKey(portallogrecord);
    } catch (SQLException e) {
      log.error("dao updatePortallogrecord error.Portallogrecord:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortallogrecordListWithPage(PortallogrecordQuery portallogrecordQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portallogrecordDao.getPortallogrecordListWithPage(portallogrecordQuery));

      page.setTotalCount(this.portallogrecordDao.getPortallogrecordListCount(portallogrecordQuery).intValue());
      page.setPageNo(portallogrecordQuery.getPage());
      page.setPageSize(portallogrecordQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portallogrecord pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portallogrecord> getPortallogrecordList(PortallogrecordQuery portallogrecordQuery)
  {
    try
    {
      return this.portallogrecordDao.getPortallogrecordList(portallogrecordQuery);
    } catch (SQLException e) {
      log.error("get Portallogrecord list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortallogrecordCount(PortallogrecordQuery portallogrecordQuery)
  {
    try
    {
      return this.portallogrecordDao.getPortallogrecordListCount(portallogrecordQuery);
    } catch (SQLException e) {
      log.error("get Portallogrecord list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortallogrecordServiceImpl
 * JD-Core Version:    0.6.2
 */