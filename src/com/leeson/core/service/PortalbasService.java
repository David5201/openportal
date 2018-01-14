package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.query.PortalbasQuery;
import java.util.List;

public abstract interface PortalbasService
{
  public abstract Long addPortalbas(Portalbas paramPortalbas);

  public abstract Portalbas getPortalbasByKey(Long paramLong);

  public abstract List<Portalbas> getPortalbasByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalbasQuery paramPortalbasQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalbasByKey(Portalbas paramPortalbas);

  public abstract Integer updatePortalbasByKeyAll(Portalbas paramPortalbas);

  public abstract Pagination getPortalbasListWithPage(PortalbasQuery paramPortalbasQuery);

  public abstract List<Portalbas> getPortalbasList(PortalbasQuery paramPortalbasQuery);

  public abstract Integer getPortalbasCount(PortalbasQuery paramPortalbasQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalbasService
 * JD-Core Version:    0.6.2
 */