package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalprivilege;
import com.leeson.core.query.PortalprivilegeQuery;
import java.util.Collection;
import java.util.List;

public abstract interface PortalprivilegeService
{
  public abstract Long addPortalprivilege(Portalprivilege paramPortalprivilege);

  public abstract Portalprivilege getPortalprivilegeByKey(Long paramLong);

  public abstract List<Portalprivilege> getPortalprivilegeByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalprivilegeQuery paramPortalprivilegeQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalprivilegeByKey(Portalprivilege paramPortalprivilege);

  public abstract Integer updatePortalprivilegeByKeyAll(Portalprivilege paramPortalprivilege);

  public abstract Pagination getPortalprivilegeListWithPage(PortalprivilegeQuery paramPortalprivilegeQuery);

  public abstract List<Portalprivilege> getPortalprivilegeList(PortalprivilegeQuery paramPortalprivilegeQuery);

  public abstract Integer getPortalprivilegeCount(PortalprivilegeQuery paramPortalprivilegeQuery);

  public abstract List<Portalprivilege> findTopList();

  public abstract Collection<String> getAllPrivilegeUrls();

  public abstract List<Portalprivilege> findChildrenList(Long paramLong);

  public abstract void editPosUP(Long paramLong);

  public abstract void editPosDown(Long paramLong);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalprivilegeService
 * JD-Core Version:    0.6.2
 */