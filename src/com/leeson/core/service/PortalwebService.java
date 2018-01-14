package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.query.PortalwebQuery;
import java.util.List;

public abstract interface PortalwebService
{
  public abstract Long addPortalweb(Portalweb paramPortalweb);

  public abstract Portalweb getPortalwebByKey(Long paramLong);

  public abstract List<Portalweb> getPortalwebByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalwebQuery paramPortalwebQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalwebByKey(Portalweb paramPortalweb);

  public abstract Integer updatePortalwebByKeyAll(Portalweb paramPortalweb);

  public abstract Pagination getPortalwebListWithPage(PortalwebQuery paramPortalwebQuery);

  public abstract List<Portalweb> getPortalwebList(PortalwebQuery paramPortalwebQuery);

  public abstract Integer getPortalwebCount(PortalwebQuery paramPortalwebQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalwebService
 * JD-Core Version:    0.6.2
 */