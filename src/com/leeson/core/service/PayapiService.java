package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Payapi;
import com.leeson.core.query.PayapiQuery;
import java.util.List;

public abstract interface PayapiService
{
  public abstract Long addPayapi(Payapi paramPayapi);

  public abstract Payapi getPayapiByKey(Long paramLong);

  public abstract List<Payapi> getPayapiByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PayapiQuery paramPayapiQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePayapiByKey(Payapi paramPayapi);

  public abstract Integer updatePayapiByKeyAll(Payapi paramPayapi);

  public abstract Pagination getPayapiListWithPage(PayapiQuery paramPayapiQuery);

  public abstract List<Payapi> getPayapiList(PayapiQuery paramPayapiQuery);

  public abstract Integer getPayapiCount(PayapiQuery paramPayapiQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PayapiService
 * JD-Core Version:    0.6.2
 */