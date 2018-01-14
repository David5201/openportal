package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Payapi;
import com.leeson.core.dao.PayapiDao;
import com.leeson.core.query.PayapiQuery;
import com.leeson.core.service.PayapiService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PayapiServiceImpl
  implements PayapiService
{
  private static final Log log = LogFactory.getLog(PayapiServiceImpl.class);

  @Resource
  PayapiDao payapiDao;

  public Long addPayapi(Payapi payapi)
  {
    try
    {
      return this.payapiDao.addPayapi(payapi);
    } catch (SQLException e) {
      log.error("dao addPayapi error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Payapi getPayapiByKey(Long id)
  {
    try
    {
      return this.payapiDao.getPayapiByKey(id);
    } catch (SQLException e) {
      log.error("dao getPayapibyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Payapi> getPayapiByKeys(List<Long> idList)
  {
    try {
      return this.payapiDao.getPayapiByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPayapisByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.payapiDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PayapiQuery payapiQuery)
  {
    try
    {
      return this.payapiDao.deleteByQuery(payapiQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.payapiDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.payapiDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePayapiByKey(Payapi payapi)
  {
    try
    {
      return this.payapiDao.updatePayapiByKey(payapi);
    } catch (SQLException e) {
      log.error("dao updatePayapi error.Payapi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePayapiByKeyAll(Payapi payapi)
  {
    try
    {
      return this.payapiDao.updatePayapiByKeyAll(payapi);
    } catch (SQLException e) {
      log.error("dao updatePayapiAll error.Payapi:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPayapiListWithPage(PayapiQuery payapiQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.payapiDao.getPayapiListWithPage(payapiQuery));

      page.setTotalCount(this.payapiDao.getPayapiListCount(payapiQuery).intValue());
      page.setPageNo(payapiQuery.getPage());
      page.setPageSize(payapiQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Payapi pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Payapi> getPayapiList(PayapiQuery payapiQuery)
  {
    try
    {
      return this.payapiDao.getPayapiList(payapiQuery);
    } catch (SQLException e) {
      log.error("get Payapi list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPayapiCount(PayapiQuery payapiQuery)
  {
    try
    {
      return this.payapiDao.getPayapiListCount(payapiQuery);
    } catch (SQLException e) {
      log.error("get Payapi list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PayapiServiceImpl
 * JD-Core Version:    0.6.2
 */