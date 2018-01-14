package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advbanner;
import com.leeson.core.dao.AdvbannerDao;
import com.leeson.core.query.AdvbannerQuery;
import com.leeson.core.service.AdvbannerService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvbannerServiceImpl
  implements AdvbannerService
{
  private static final Log log = LogFactory.getLog(AdvbannerServiceImpl.class);

  @Resource
  AdvbannerDao advbannerDao;

  public Long addAdvbanner(Advbanner advbanner)
  {
    try
    {
      return this.advbannerDao.addAdvbanner(advbanner);
    } catch (SQLException e) {
      log.error("dao addAdvbanner error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Advbanner getAdvbannerByKey(Long id)
  {
    try
    {
      return this.advbannerDao.getAdvbannerByKey(id);
    } catch (SQLException e) {
      log.error("dao getAdvbannerbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Advbanner> getAdvbannerByKeys(List<Long> idList)
  {
    try {
      return this.advbannerDao.getAdvbannerByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getAdvbannersByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.advbannerDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(AdvbannerQuery advbannerQuery)
  {
    try
    {
      return this.advbannerDao.deleteByQuery(advbannerQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.advbannerDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.advbannerDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateAdvbannerByKey(Advbanner advbanner)
  {
    try
    {
      return this.advbannerDao.updateAdvbannerByKey(advbanner);
    } catch (SQLException e) {
      log.error("dao updateAdvbanner error.Advbanner:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateAdvbannerByKeyAll(Advbanner advbanner)
  {
    try
    {
      return this.advbannerDao.updateAdvbannerByKeyAll(advbanner);
    } catch (SQLException e) {
      log.error("dao updateAdvbannerAll error.Advbanner:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getAdvbannerListWithPage(AdvbannerQuery advbannerQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.advbannerDao.getAdvbannerListWithPage(advbannerQuery));

      page.setTotalCount(this.advbannerDao.getAdvbannerListCount(advbannerQuery).intValue());
      page.setPageNo(advbannerQuery.getPage());
      page.setPageSize(advbannerQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Advbanner pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Advbanner> getAdvbannerList(AdvbannerQuery advbannerQuery)
  {
    try
    {
      return this.advbannerDao.getAdvbannerList(advbannerQuery);
    } catch (SQLException e) {
      log.error("get Advbanner list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getAdvbannerCount(AdvbannerQuery advbannerQuery)
  {
    try
    {
      return this.advbannerDao.getAdvbannerListCount(advbannerQuery);
    } catch (SQLException e) {
      log.error("get Advbanner list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.AdvbannerServiceImpl
 * JD-Core Version:    0.6.2
 */