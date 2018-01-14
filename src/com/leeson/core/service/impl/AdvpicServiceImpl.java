package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advpic;
import com.leeson.core.dao.AdvpicDao;
import com.leeson.core.query.AdvpicQuery;
import com.leeson.core.service.AdvpicService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvpicServiceImpl
  implements AdvpicService
{
  private static final Log log = LogFactory.getLog(AdvpicServiceImpl.class);

  @Resource
  AdvpicDao advpicDao;

  public Long addAdvpic(Advpic advpic)
  {
    try
    {
      return this.advpicDao.addAdvpic(advpic);
    } catch (SQLException e) {
      log.error("dao addAdvpic error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Advpic getAdvpicByKey(Long id)
  {
    try
    {
      return this.advpicDao.getAdvpicByKey(id);
    } catch (SQLException e) {
      log.error("dao getAdvpicbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Advpic> getAdvpicByKeys(List<Long> idList)
  {
    try {
      return this.advpicDao.getAdvpicByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getAdvpicsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.advpicDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(AdvpicQuery advpicQuery)
  {
    try
    {
      return this.advpicDao.deleteByQuery(advpicQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.advpicDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.advpicDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateAdvpicByKey(Advpic advpic)
  {
    try
    {
      return this.advpicDao.updateAdvpicByKey(advpic);
    } catch (SQLException e) {
      log.error("dao updateAdvpic error.Advpic:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateAdvpicByKeyAll(Advpic advpic)
  {
    try
    {
      return this.advpicDao.updateAdvpicByKeyAll(advpic);
    } catch (SQLException e) {
      log.error("dao updateAdvpicAll error.Advpic:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getAdvpicListWithPage(AdvpicQuery advpicQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.advpicDao.getAdvpicListWithPage(advpicQuery));

      page.setTotalCount(this.advpicDao.getAdvpicListCount(advpicQuery).intValue());
      page.setPageNo(advpicQuery.getPage());
      page.setPageSize(advpicQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Advpic pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Advpic> getAdvpicList(AdvpicQuery advpicQuery)
  {
    try
    {
      return this.advpicDao.getAdvpicList(advpicQuery);
    } catch (SQLException e) {
      log.error("get Advpic list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getAdvpicCount(AdvpicQuery advpicQuery)
  {
    try
    {
      return this.advpicDao.getAdvpicListCount(advpicQuery);
    } catch (SQLException e) {
      log.error("get Advpic list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.AdvpicServiceImpl
 * JD-Core Version:    0.6.2
 */