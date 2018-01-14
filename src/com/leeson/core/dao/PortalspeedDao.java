package com.leeson.core.dao;

import com.leeson.core.bean.Portalspeed;
import com.leeson.core.query.PortalspeedQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalspeedDao
{
  public abstract Long addPortalspeed(Portalspeed paramPortalspeed)
    throws SQLException;

  public abstract Portalspeed getPortalspeedByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalspeed> getPortalspeedByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalspeedQuery paramPortalspeedQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalspeedByKey(Portalspeed paramPortalspeed)
    throws SQLException;

  public abstract Integer updatePortalspeedByKeyAll(Portalspeed paramPortalspeed)
    throws SQLException;

  public abstract List<Portalspeed> getPortalspeedListWithPage(PortalspeedQuery paramPortalspeedQuery)
    throws SQLException;

  public abstract List<Portalspeed> getPortalspeedList(PortalspeedQuery paramPortalspeedQuery)
    throws SQLException;

  public abstract Integer getPortalspeedListCount(PortalspeedQuery paramPortalspeedQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalspeedDao
 * JD-Core Version:    0.6.2
 */