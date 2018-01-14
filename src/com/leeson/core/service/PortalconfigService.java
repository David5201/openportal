package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalconfig;
import com.leeson.core.query.PortalconfigQuery;
import java.util.List;

public abstract interface PortalconfigService
{
  public abstract Long addPortalconfig(Portalconfig paramPortalconfig);

  public abstract Portalconfig getPortalconfigByKey(Long paramLong);

  public abstract List<Portalconfig> getPortalconfigByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalconfigQuery paramPortalconfigQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalconfigByKey(Portalconfig paramPortalconfig);

  public abstract Integer updatePortalconfigByKeyAll(Portalconfig paramPortalconfig);

  public abstract Pagination getPortalconfigListWithPage(PortalconfigQuery paramPortalconfigQuery);

  public abstract List<Portalconfig> getPortalconfigList(PortalconfigQuery paramPortalconfigQuery);

  public abstract Integer getPortalconfigCount(PortalconfigQuery paramPortalconfigQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalconfigService
 * JD-Core Version:    0.6.2
 */