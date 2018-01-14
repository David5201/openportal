package com.leeson.core.dao;

import com.leeson.core.bean.Portalroleprivilege;
import com.leeson.core.query.PortalroleprivilegeQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalroleprivilegeDao
{
  public abstract Long addPortalroleprivilege(Portalroleprivilege paramPortalroleprivilege)
    throws SQLException;

  public abstract Portalroleprivilege getPortalroleprivilegeByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalroleprivilege> getPortalroleprivilegeByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalroleprivilegeQuery paramPortalroleprivilegeQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalroleprivilegeByKey(Portalroleprivilege paramPortalroleprivilege)
    throws SQLException;

  public abstract Integer updatePortalroleprivilegeByKeyAll(Portalroleprivilege paramPortalroleprivilege)
    throws SQLException;

  public abstract List<Portalroleprivilege> getPortalroleprivilegeListWithPage(PortalroleprivilegeQuery paramPortalroleprivilegeQuery)
    throws SQLException;

  public abstract List<Portalroleprivilege> getPortalroleprivilegeList(PortalroleprivilegeQuery paramPortalroleprivilegeQuery)
    throws SQLException;

  public abstract Integer getPortalroleprivilegeListCount(PortalroleprivilegeQuery paramPortalroleprivilegeQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalroleprivilegeDao
 * JD-Core Version:    0.6.2
 */