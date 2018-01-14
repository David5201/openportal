package com.leeson.core.dao;

import com.leeson.core.bean.Portalrole;
import com.leeson.core.query.PortalroleQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalroleDao
{
  public abstract Long addPortalrole(Portalrole paramPortalrole)
    throws SQLException;

  public abstract Portalrole getPortalroleByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalrole> getPortalroleByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalroleQuery paramPortalroleQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalroleByKey(Portalrole paramPortalrole)
    throws SQLException;

  public abstract Integer updatePortalroleByKeyAll(Portalrole paramPortalrole)
    throws SQLException;

  public abstract List<Portalrole> getPortalroleListWithPage(PortalroleQuery paramPortalroleQuery)
    throws SQLException;

  public abstract List<Portalrole> getPortalroleList(PortalroleQuery paramPortalroleQuery)
    throws SQLException;

  public abstract Integer getPortalroleListCount(PortalroleQuery paramPortalroleQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalroleDao
 * JD-Core Version:    0.6.2
 */