package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.query.PortalorderQuery;
import java.util.List;

public abstract interface PortalorderService
{
  public abstract Long addPortalorder(Portalorder paramPortalorder);

  public abstract Portalorder getPortalorderByKey(Long paramLong);

  public abstract List<Portalorder> getPortalorderByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalorderQuery paramPortalorderQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalorderByKey(Portalorder paramPortalorder);

  public abstract Integer updatePortalorderByKeyAll(Portalorder paramPortalorder);

  public abstract Pagination getPortalorderListWithPage(PortalorderQuery paramPortalorderQuery);

  public abstract List<Portalorder> getPortalorderList(PortalorderQuery paramPortalorderQuery);

  public abstract Integer getPortalorderCount(PortalorderQuery paramPortalorderQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalorderService
 * JD-Core Version:    0.6.2
 */