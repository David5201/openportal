package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.query.PortalsmslogsQuery;
import java.util.List;

public abstract interface PortalsmslogsService
{
  public abstract Long addPortalsmslogs(Portalsmslogs paramPortalsmslogs);

  public abstract Portalsmslogs getPortalsmslogsByKey(Long paramLong);

  public abstract List<Portalsmslogs> getPortalsmslogsByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalsmslogsQuery paramPortalsmslogsQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalsmslogsByKey(Portalsmslogs paramPortalsmslogs);

  public abstract Integer updatePortalsmslogsByKeyAll(Portalsmslogs paramPortalsmslogs);

  public abstract Pagination getPortalsmslogsListWithPage(PortalsmslogsQuery paramPortalsmslogsQuery);

  public abstract List<Portalsmslogs> getPortalsmslogsList(PortalsmslogsQuery paramPortalsmslogsQuery);

  public abstract Integer getPortalsmslogsCount(PortalsmslogsQuery paramPortalsmslogsQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalsmslogsService
 * JD-Core Version:    0.6.2
 */