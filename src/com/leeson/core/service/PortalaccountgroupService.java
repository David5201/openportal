package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.query.PortalaccountgroupQuery;
import java.util.List;

public abstract interface PortalaccountgroupService
{
  public abstract Long addPortalaccountgroup(Portalaccountgroup paramPortalaccountgroup);

  public abstract Portalaccountgroup getPortalaccountgroupByKey(Long paramLong);

  public abstract List<Portalaccountgroup> getPortalaccountgroupByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalaccountgroupQuery paramPortalaccountgroupQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalaccountgroupByKey(Portalaccountgroup paramPortalaccountgroup);

  public abstract Integer updatePortalaccountgroupByKeyAll(Portalaccountgroup paramPortalaccountgroup);

  public abstract Pagination getPortalaccountgroupListWithPage(PortalaccountgroupQuery paramPortalaccountgroupQuery);

  public abstract List<Portalaccountgroup> getPortalaccountgroupList(PortalaccountgroupQuery paramPortalaccountgroupQuery);

  public abstract Integer getPortalaccountgroupCount(PortalaccountgroupQuery paramPortalaccountgroupQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalaccountgroupService
 * JD-Core Version:    0.6.2
 */