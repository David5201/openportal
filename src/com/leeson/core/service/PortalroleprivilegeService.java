package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalroleprivilege;
import com.leeson.core.query.PortalroleprivilegeQuery;
import java.util.List;

public abstract interface PortalroleprivilegeService
{
  public abstract Long addPortalroleprivilege(Portalroleprivilege paramPortalroleprivilege);

  public abstract Portalroleprivilege getPortalroleprivilegeByKey(Long paramLong);

  public abstract List<Portalroleprivilege> getPortalroleprivilegeByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalroleprivilegeQuery paramPortalroleprivilegeQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalroleprivilegeByKey(Portalroleprivilege paramPortalroleprivilege);

  public abstract Pagination getPortalroleprivilegeListWithPage(PortalroleprivilegeQuery paramPortalroleprivilegeQuery);

  public abstract List<Portalroleprivilege> getPortalroleprivilegeList(PortalroleprivilegeQuery paramPortalroleprivilegeQuery);

  public abstract Integer getPortalroleprivilegeCount(PortalroleprivilegeQuery paramPortalroleprivilegeQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalroleprivilegeService
 * JD-Core Version:    0.6.2
 */