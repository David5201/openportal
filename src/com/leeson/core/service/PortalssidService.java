package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.query.PortalssidQuery;
import java.util.List;

public abstract interface PortalssidService
{
  public abstract Long addPortalssid(Portalssid paramPortalssid);

  public abstract Portalssid getPortalssidByKey(Long paramLong);

  public abstract List<Portalssid> getPortalssidByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalssidQuery paramPortalssidQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalssidByKey(Portalssid paramPortalssid);

  public abstract Integer updatePortalssidByKeyAll(Portalssid paramPortalssid);

  public abstract Pagination getPortalssidListWithPage(PortalssidQuery paramPortalssidQuery);

  public abstract List<Portalssid> getPortalssidList(PortalssidQuery paramPortalssidQuery);

  public abstract Integer getPortalssidCount(PortalssidQuery paramPortalssidQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalssidService
 * JD-Core Version:    0.6.2
 */