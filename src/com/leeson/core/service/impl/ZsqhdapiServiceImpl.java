package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Zsqhdapi;
import com.leeson.core.dao.ZsqhdapiDao;
import com.leeson.core.query.ZsqhdapiQuery;
import com.leeson.core.service.ZsqhdapiService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ZsqhdapiServiceImpl
  implements ZsqhdapiService
{
  private static final Log log = LogFactory.getLog(ZsqhdapiServiceImpl.class);

  @Resource
  ZsqhdapiDao zsqhdapiDao;

  public Long addZsqhdapi(Zsqhdapi zsqhdapi)
  {
    try
    {
      return this.zsqhdapiDao.addZsqhdapi(zsqhdapi);
    } catch (SQLException e) {
      log.error("dao addZsqhdapi error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Zsqhdapi getZsqhdapiByKey(Long id)
  {
    try
    {
      return this.zsqhdapiDao.getZsqhdapiByKey(id);
    } catch (SQLException e) {
      log.error("dao getZsqhdapibyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Zsqhdapi> getZsqhdapiByKeys(List<Long> idList)
  {
    try {
      return this.zsqhdapiDao.getZsqhdapiByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getZsqhdapisByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.zsqhdapiDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(ZsqhdapiQuery zsqhdapiQuery)
  {
    try
    {
      return this.zsqhdapiDao.deleteByQuery(zsqhdapiQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.zsqhdapiDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.zsqhdapiDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateZsqhdapiByKey(Zsqhdapi zsqhdapi)
  {
    try
    {
      return this.zsqhdapiDao.updateZsqhdapiByKey(zsqhdapi);
    } catch (SQLException e) {
      log.error("dao updateZsqhdapi error.Zsqhdapi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateZsqhdapiByKeyAll(Zsqhdapi zsqhdapi)
  {
    try
    {
      return this.zsqhdapiDao.updateZsqhdapiByKeyAll(zsqhdapi);
    } catch (SQLException e) {
      log.error("dao updateZsqhdapiAll error.Zsqhdapi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getZsqhdapiListWithPage(ZsqhdapiQuery zsqhdapiQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.zsqhdapiDao.getZsqhdapiListWithPage(zsqhdapiQuery));

      page.setTotalCount(this.zsqhdapiDao.getZsqhdapiListCount(zsqhdapiQuery).intValue());
      page.setPageNo(zsqhdapiQuery.getPage());
      page.setPageSize(zsqhdapiQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Zsqhdapi pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Zsqhdapi> getZsqhdapiList(ZsqhdapiQuery zsqhdapiQuery)
  {
    try
    {
      return this.zsqhdapiDao.getZsqhdapiList(zsqhdapiQuery);
    } catch (SQLException e) {
      log.error("get Zsqhdapi list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getZsqhdapiCount(ZsqhdapiQuery zsqhdapiQuery)
  {
    try
    {
      return this.zsqhdapiDao.getZsqhdapiListCount(zsqhdapiQuery);
    } catch (SQLException e) {
      log.error("get Zsqhdapi list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.ZsqhdapiServiceImpl
 * JD-Core Version:    0.6.2
 */