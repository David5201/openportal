package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalrole;
import com.leeson.core.query.PortalroleQuery;
import java.util.List;

public abstract interface PortalroleService
{
  public abstract Long addPortalrole(Portalrole paramPortalrole);

  public abstract Portalrole getPortalroleByKey(Long paramLong);

  public abstract List<Portalrole> getPortalroleByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalroleQuery paramPortalroleQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalroleByKey(Portalrole paramPortalrole);

  public abstract Pagination getPortalroleListWithPage(PortalroleQuery paramPortalroleQuery);

  public abstract List<Portalrole> getPortalroleList(PortalroleQuery paramPortalroleQuery);

  public abstract Integer getPortalroleCount(PortalroleQuery paramPortalroleQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalroleService
 * JD-Core Version:    0.6.2
 */