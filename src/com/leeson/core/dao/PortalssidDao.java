package com.leeson.core.dao;

import com.leeson.core.bean.Portalssid;
import com.leeson.core.query.PortalssidQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalssidDao
{
  public abstract Long addPortalssid(Portalssid paramPortalssid)
    throws SQLException;

  public abstract Portalssid getPortalssidByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalssid> getPortalssidByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalssidQuery paramPortalssidQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalssidByKey(Portalssid paramPortalssid)
    throws SQLException;

  public abstract Integer updatePortalssidByKeyAll(Portalssid paramPortalssid)
    throws SQLException;

  public abstract List<Portalssid> getPortalssidListWithPage(PortalssidQuery paramPortalssidQuery)
    throws SQLException;

  public abstract List<Portalssid> getPortalssidList(PortalssidQuery paramPortalssidQuery)
    throws SQLException;

  public abstract Integer getPortalssidListCount(PortalssidQuery paramPortalssidQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalssidDao
 * JD-Core Version:    0.6.2
 */