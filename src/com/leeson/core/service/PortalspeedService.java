package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalspeed;
import com.leeson.core.query.PortalspeedQuery;
import java.util.List;

public abstract interface PortalspeedService
{
  public abstract Long addPortalspeed(Portalspeed paramPortalspeed);

  public abstract Portalspeed getPortalspeedByKey(Long paramLong);

  public abstract List<Portalspeed> getPortalspeedByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalspeedQuery paramPortalspeedQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalspeedByKey(Portalspeed paramPortalspeed);

  public abstract Integer updatePortalspeedByKeyAll(Portalspeed paramPortalspeed);

  public abstract Pagination getPortalspeedListWithPage(PortalspeedQuery paramPortalspeedQuery);

  public abstract List<Portalspeed> getPortalspeedList(PortalspeedQuery paramPortalspeedQuery);

  public abstract Integer getPortalspeedCount(PortalspeedQuery paramPortalspeedQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalspeedService
 * JD-Core Version:    0.6.2
 */