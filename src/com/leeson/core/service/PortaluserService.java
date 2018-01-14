package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.PortaluserQuery;
import java.util.List;

public abstract interface PortaluserService
{
  public abstract Long addPortaluser(Portaluser paramPortaluser);

  public abstract Portaluser getPortaluserByKey(Long paramLong);

  public abstract List<Portaluser> getPortaluserByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortaluserQuery paramPortaluserQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortaluserByKey(Portaluser paramPortaluser);

  public abstract Integer updatePortaluserByKeyAll(Portaluser paramPortaluser);

  public abstract Pagination getPortaluserListWithPage(PortaluserQuery paramPortaluserQuery);

  public abstract List<Portaluser> getPortaluserList(PortaluserQuery paramPortaluserQuery);

  public abstract Integer getPortaluserCount(PortaluserQuery paramPortaluserQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortaluserService
 * JD-Core Version:    0.6.2
 */