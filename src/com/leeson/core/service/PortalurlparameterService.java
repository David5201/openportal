package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalurlparameter;
import com.leeson.core.query.PortalurlparameterQuery;
import java.util.List;

public abstract interface PortalurlparameterService
{
  public abstract Long addPortalurlparameter(Portalurlparameter paramPortalurlparameter);

  public abstract Portalurlparameter getPortalurlparameterByKey(Long paramLong);

  public abstract List<Portalurlparameter> getPortalurlparameterByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalurlparameterQuery paramPortalurlparameterQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalurlparameterByKey(Portalurlparameter paramPortalurlparameter);

  public abstract Integer updatePortalurlparameterByKeyAll(Portalurlparameter paramPortalurlparameter);

  public abstract Pagination getPortalurlparameterListWithPage(PortalurlparameterQuery paramPortalurlparameterQuery);

  public abstract List<Portalurlparameter> getPortalurlparameterList(PortalurlparameterQuery paramPortalurlparameterQuery);

  public abstract Integer getPortalurlparameterCount(PortalurlparameterQuery paramPortalurlparameterQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalurlparameterService
 * JD-Core Version:    0.6.2
 */