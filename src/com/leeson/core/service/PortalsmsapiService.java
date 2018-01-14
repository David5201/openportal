package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.query.PortalsmsapiQuery;
import java.util.List;

public abstract interface PortalsmsapiService
{
  public abstract Long addPortalsmsapi(Portalsmsapi paramPortalsmsapi);

  public abstract Portalsmsapi getPortalsmsapiByKey(Long paramLong);

  public abstract List<Portalsmsapi> getPortalsmsapiByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalsmsapiQuery paramPortalsmsapiQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalsmsapiByKey(Portalsmsapi paramPortalsmsapi);

  public abstract Integer updatePortalsmsapiByKeyAll(Portalsmsapi paramPortalsmsapi);

  public abstract Pagination getPortalsmsapiListWithPage(PortalsmsapiQuery paramPortalsmsapiQuery);

  public abstract List<Portalsmsapi> getPortalsmsapiList(PortalsmsapiQuery paramPortalsmsapiQuery);

  public abstract Integer getPortalsmsapiCount(PortalsmsapiQuery paramPortalsmsapiQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalsmsapiService
 * JD-Core Version:    0.6.2
 */