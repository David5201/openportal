package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Historyonline;
import com.leeson.core.dao.HistoryonlineDao;
import com.leeson.core.query.HistoryonlineQuery;
import com.leeson.core.service.HistoryonlineService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryonlineServiceImpl
  implements HistoryonlineService
{
  private static final Log log = LogFactory.getLog(HistoryonlineServiceImpl.class);

  @Resource
  HistoryonlineDao historyonlineDao;

  public Long addHistoryonline(Historyonline historyonline)
  {
    try
    {
      return this.historyonlineDao.addHistoryonline(historyonline);
    } catch (SQLException e) {
      log.error("dao addHistoryonline error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Historyonline getHistoryonlineByKey(Long id)
  {
    try
    {
      return this.historyonlineDao.getHistoryonlineByKey(id);
    } catch (SQLException e) {
      log.error("dao getHistoryonlinebyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Historyonline> getHistoryonlineByKeys(List<Long> idList)
  {
    try {
      return this.historyonlineDao.getHistoryonlineByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getHistoryonlinesByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.historyonlineDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(HistoryonlineQuery historyonlineQuery)
  {
    try
    {
      return this.historyonlineDao.deleteByQuery(historyonlineQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.historyonlineDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.historyonlineDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateHistoryonlineByKey(Historyonline historyonline)
  {
    try
    {
      return this.historyonlineDao.updateHistoryonlineByKey(historyonline);
    } catch (SQLException e) {
      log.error("dao updateHistoryonline error.Historyonline:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateHistoryonlineByKeyAll(Historyonline historyonline)
  {
    try
    {
      return this.historyonlineDao.updateHistoryonlineByKeyAll(historyonline);
    } catch (SQLException e) {
      log.error("dao updateHistoryonlineAll error.Historyonline:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getHistoryonlineListWithPage(HistoryonlineQuery historyonlineQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.historyonlineDao.getHistoryonlineListWithPage(historyonlineQuery));

      page.setTotalCount(this.historyonlineDao.getHistoryonlineListCount(historyonlineQuery).intValue());
      page.setPageNo(historyonlineQuery.getPage());
      page.setPageSize(historyonlineQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Historyonline pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Historyonline> getHistoryonlineList(HistoryonlineQuery historyonlineQuery)
  {
    try
    {
      return this.historyonlineDao.getHistoryonlineList(historyonlineQuery);
    } catch (SQLException e) {
      log.error("get Historyonline list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getHistoryonlineCount(HistoryonlineQuery historyonlineQuery)
  {
    try
    {
      return this.historyonlineDao.getHistoryonlineListCount(historyonlineQuery);
    } catch (SQLException e) {
      log.error("get Historyonline list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.HistoryonlineServiceImpl
 * JD-Core Version:    0.6.2
 */