package com.leeson.core.dao;

import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.query.PortalsmslogsQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalsmslogsDao
{
  public abstract Long addPortalsmslogs(Portalsmslogs paramPortalsmslogs)
    throws SQLException;

  public abstract Portalsmslogs getPortalsmslogsByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalsmslogs> getPortalsmslogsByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalsmslogsQuery paramPortalsmslogsQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalsmslogsByKey(Portalsmslogs paramPortalsmslogs)
    throws SQLException;

  public abstract Integer updatePortalsmslogsByKeyAll(Portalsmslogs paramPortalsmslogs)
    throws SQLException;

  public abstract List<Portalsmslogs> getPortalsmslogsListWithPage(PortalsmslogsQuery paramPortalsmslogsQuery)
    throws SQLException;

  public abstract List<Portalsmslogs> getPortalsmslogsList(PortalsmslogsQuery paramPortalsmslogsQuery)
    throws SQLException;

  public abstract Integer getPortalsmslogsListCount(PortalsmslogsQuery paramPortalsmslogsQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalsmslogsDao
 * JD-Core Version:    0.6.2
 */