package com.leeson.core.dao;

import com.leeson.core.bean.Portalaccount;
import com.leeson.core.query.PortalaccountQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalaccountDao
{
  public abstract Long addPortalaccount(Portalaccount paramPortalaccount)
    throws SQLException;

  public abstract Portalaccount getPortalaccountByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalaccount> getPortalaccountByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalaccountQuery paramPortalaccountQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalaccountByKey(Portalaccount paramPortalaccount)
    throws SQLException;

  public abstract Integer updatePortalaccountByKeyAll(Portalaccount paramPortalaccount)
    throws SQLException;

  public abstract List<Portalaccount> getPortalaccountListWithPage(PortalaccountQuery paramPortalaccountQuery)
    throws SQLException;

  public abstract List<Portalaccount> getPortalaccountList(PortalaccountQuery paramPortalaccountQuery)
    throws SQLException;

  public abstract Integer getPortalaccountListCount(PortalaccountQuery paramPortalaccountQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalaccountDao
 * JD-Core Version:    0.6.2
 */