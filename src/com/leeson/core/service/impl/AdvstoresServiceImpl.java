package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advadv;
import com.leeson.core.bean.Advstores;
import com.leeson.core.dao.AdvstoresDao;
import com.leeson.core.query.AdvadvQuery;
import com.leeson.core.query.AdvstoresQuery;
import com.leeson.core.service.AdvadvService;
import com.leeson.core.service.AdvstoresService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvstoresServiceImpl
  implements AdvstoresService
{
  private static final Log log = LogFactory.getLog(AdvstoresServiceImpl.class);

  @Resource
  AdvstoresDao advstoresDao;

  @Autowired
  private AdvadvService advadvService;

  public Long addAdvstores(Advstores advstores)
  {
    try
    {
      return this.advstoresDao.addAdvstores(advstores);
    } catch (SQLException e) {
      log.error("dao addAdvstores error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Advstores getAdvstoresByKey(Long id)
  {
    try
    {
      return this.advstoresDao.getAdvstoresByKey(id);
    } catch (SQLException e) {
      log.error("dao getAdvstoresbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Advstores> getAdvstoresByKeys(List<Long> idList)
  {
    try {
      return this.advstoresDao.getAdvstoresByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getAdvstoressByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      AdvadvQuery aq = new AdvadvQuery();
      aq.setSid(id);
      List<Advadv> advs = this.advadvService.getAdvadvList(aq);
      for (Advadv adv : advs) {
        this.advadvService.deleteByKey(adv.getId());
      }
      return this.advstoresDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(AdvstoresQuery advstoresQuery)
  {
    try
    {
      return this.advstoresDao.deleteByQuery(advstoresQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.advstoresDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.advstoresDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateAdvstoresByKey(Advstores advstores)
  {
    try
    {
      return this.advstoresDao.updateAdvstoresByKey(advstores);
    } catch (SQLException e) {
      log.error("dao updateAdvstores error.Advstores:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateAdvstoresByKeyAll(Advstores advstores)
  {
    try
    {
      return this.advstoresDao.updateAdvstoresByKeyAll(advstores);
    } catch (SQLException e) {
      log.error("dao updateAdvstoresAll error.Advstores:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getAdvstoresListWithPage(AdvstoresQuery advstoresQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.advstoresDao.getAdvstoresListWithPage(advstoresQuery));

      page.setTotalCount(this.advstoresDao.getAdvstoresListCount(advstoresQuery).intValue());
      page.setPageNo(advstoresQuery.getPage());
      page.setPageSize(advstoresQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Advstores pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Advstores> getAdvstoresList(AdvstoresQuery advstoresQuery)
  {
    try
    {
      return this.advstoresDao.getAdvstoresList(advstoresQuery);
    } catch (SQLException e) {
      log.error("get Advstores list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getAdvstoresCount(AdvstoresQuery advstoresQuery)
  {
    try
    {
      return this.advstoresDao.getAdvstoresListCount(advstoresQuery);
    } catch (SQLException e) {
      log.error("get Advstores list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.AdvstoresServiceImpl
 * JD-Core Version:    0.6.2
 */