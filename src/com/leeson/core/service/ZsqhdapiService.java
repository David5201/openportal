package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Zsqhdapi;
import com.leeson.core.query.ZsqhdapiQuery;
import java.util.List;

public abstract interface ZsqhdapiService
{
  public abstract Long addZsqhdapi(Zsqhdapi paramZsqhdapi);

  public abstract Zsqhdapi getZsqhdapiByKey(Long paramLong);

  public abstract List<Zsqhdapi> getZsqhdapiByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(ZsqhdapiQuery paramZsqhdapiQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateZsqhdapiByKey(Zsqhdapi paramZsqhdapi);

  public abstract Integer updateZsqhdapiByKeyAll(Zsqhdapi paramZsqhdapi);

  public abstract Pagination getZsqhdapiListWithPage(ZsqhdapiQuery paramZsqhdapiQuery);

  public abstract List<Zsqhdapi> getZsqhdapiList(ZsqhdapiQuery paramZsqhdapiQuery);

  public abstract Integer getZsqhdapiCount(ZsqhdapiQuery paramZsqhdapiQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.ZsqhdapiService
 * JD-Core Version:    0.6.2
 */