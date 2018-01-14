package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.query.PortalaccountQuery;
import java.util.List;

public abstract interface PortalaccountService
{
  public abstract Long addPortalaccount(Portalaccount paramPortalaccount);

  public abstract Portalaccount getPortalaccountByKey(Long paramLong);

  public abstract List<Portalaccount> getPortalaccountByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalaccountQuery paramPortalaccountQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalaccountByKey(Portalaccount paramPortalaccount);

  public abstract Integer updatePortalaccountByKeyAll(Portalaccount paramPortalaccount);

  public abstract Pagination getPortalaccountListWithPage(PortalaccountQuery paramPortalaccountQuery);

  public abstract List<Portalaccount> getPortalaccountList(PortalaccountQuery paramPortalaccountQuery);

  public abstract Integer getPortalaccountCount(PortalaccountQuery paramPortalaccountQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalaccountService
 * JD-Core Version:    0.6.2
 */