package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalip;
import com.leeson.core.query.PortalipQuery;
import java.util.List;

public abstract interface PortalipService
{
  public abstract Long addPortalip(Portalip paramPortalip);

  public abstract Portalip getPortalipByKey(Long paramLong);

  public abstract List<Portalip> getPortalipByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalipQuery paramPortalipQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalipByKey(Portalip paramPortalip);

  public abstract Integer updatePortalipByKeyAll(Portalip paramPortalip);

  public abstract Pagination getPortalipListWithPage(PortalipQuery paramPortalipQuery);

  public abstract List<Portalip> getPortalipList(PortalipQuery paramPortalipQuery);

  public abstract Integer getPortalipCount(PortalipQuery paramPortalipQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalipService
 * JD-Core Version:    0.6.2
 */