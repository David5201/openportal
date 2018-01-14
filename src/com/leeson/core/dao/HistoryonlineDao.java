package com.leeson.core.dao;

import com.leeson.core.bean.Historyonline;
import com.leeson.core.query.HistoryonlineQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface HistoryonlineDao
{
  public abstract Long addHistoryonline(Historyonline paramHistoryonline)
    throws SQLException;

  public abstract Historyonline getHistoryonlineByKey(Long paramLong)
    throws SQLException;

  public abstract List<Historyonline> getHistoryonlineByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(HistoryonlineQuery paramHistoryonlineQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateHistoryonlineByKey(Historyonline paramHistoryonline)
    throws SQLException;

  public abstract Integer updateHistoryonlineByKeyAll(Historyonline paramHistoryonline)
    throws SQLException;

  public abstract List<Historyonline> getHistoryonlineListWithPage(HistoryonlineQuery paramHistoryonlineQuery)
    throws SQLException;

  public abstract List<Historyonline> getHistoryonlineList(HistoryonlineQuery paramHistoryonlineQuery)
    throws SQLException;

  public abstract Integer getHistoryonlineListCount(HistoryonlineQuery paramHistoryonlineQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.HistoryonlineDao
 * JD-Core Version:    0.6.2
 */