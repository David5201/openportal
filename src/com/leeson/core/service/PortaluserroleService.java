package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaluserrole;
import com.leeson.core.query.PortaluserroleQuery;
import java.util.List;

public abstract interface PortaluserroleService
{
  public abstract Long addPortaluserrole(Portaluserrole paramPortaluserrole);

  public abstract Portaluserrole getPortaluserroleByKey(Long paramLong);

  public abstract List<Portaluserrole> getPortaluserroleByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortaluserroleQuery paramPortaluserroleQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortaluserroleByKey(Portaluserrole paramPortaluserrole);

  public abstract Pagination getPortaluserroleListWithPage(PortaluserroleQuery paramPortaluserroleQuery);

  public abstract List<Portaluserrole> getPortaluserroleList(PortaluserroleQuery paramPortaluserroleQuery);

  public abstract Integer getPortaluserroleCount(PortaluserroleQuery paramPortaluserroleQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortaluserroleService
 * JD-Core Version:    0.6.2
 */