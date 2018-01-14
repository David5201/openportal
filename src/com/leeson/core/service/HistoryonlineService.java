package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Historyonline;
import com.leeson.core.query.HistoryonlineQuery;
import java.util.List;

public abstract interface HistoryonlineService
{
  public abstract Long addHistoryonline(Historyonline paramHistoryonline);

  public abstract Historyonline getHistoryonlineByKey(Long paramLong);

  public abstract List<Historyonline> getHistoryonlineByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(HistoryonlineQuery paramHistoryonlineQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateHistoryonlineByKey(Historyonline paramHistoryonline);

  public abstract Integer updateHistoryonlineByKeyAll(Historyonline paramHistoryonline);

  public abstract Pagination getHistoryonlineListWithPage(HistoryonlineQuery paramHistoryonlineQuery);

  public abstract List<Historyonline> getHistoryonlineList(HistoryonlineQuery paramHistoryonlineQuery);

  public abstract Integer getHistoryonlineCount(HistoryonlineQuery paramHistoryonlineQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.HistoryonlineService
 * JD-Core Version:    0.6.2
 */