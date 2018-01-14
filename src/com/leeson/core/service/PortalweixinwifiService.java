package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.query.PortalweixinwifiQuery;
import java.util.List;

public abstract interface PortalweixinwifiService
{
  public abstract Long addPortalweixinwifi(Portalweixinwifi paramPortalweixinwifi);

  public abstract Portalweixinwifi getPortalweixinwifiByKey(Long paramLong);

  public abstract List<Portalweixinwifi> getPortalweixinwifiByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalweixinwifiQuery paramPortalweixinwifiQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalweixinwifiByKey(Portalweixinwifi paramPortalweixinwifi);

  public abstract Integer updatePortalweixinwifiByKeyAll(Portalweixinwifi paramPortalweixinwifi);

  public abstract Pagination getPortalweixinwifiListWithPage(PortalweixinwifiQuery paramPortalweixinwifiQuery);

  public abstract List<Portalweixinwifi> getPortalweixinwifiList(PortalweixinwifiQuery paramPortalweixinwifiQuery);

  public abstract Integer getPortalweixinwifiCount(PortalweixinwifiQuery paramPortalweixinwifiQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalweixinwifiService
 * JD-Core Version:    0.6.2
 */