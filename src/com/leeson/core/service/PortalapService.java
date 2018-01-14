package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalap;
import com.leeson.core.query.PortalapQuery;
import java.util.List;

public abstract interface PortalapService
{
  public abstract Long addPortalap(Portalap paramPortalap);

  public abstract Portalap getPortalapByKey(Long paramLong);

  public abstract List<Portalap> getPortalapByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalapQuery paramPortalapQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalapByKey(Portalap paramPortalap);

  public abstract Integer updatePortalapByKeyAll(Portalap paramPortalap);

  public abstract Pagination getPortalapListWithPage(PortalapQuery paramPortalapQuery);

  public abstract List<Portalap> getPortalapList(PortalapQuery paramPortalapQuery);

  public abstract Integer getPortalapCount(PortalapQuery paramPortalapQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalapService
 * JD-Core Version:    0.6.2
 */