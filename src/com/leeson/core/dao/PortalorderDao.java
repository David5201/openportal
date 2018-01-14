package com.leeson.core.dao;

import com.leeson.core.bean.Portalorder;
import com.leeson.core.query.PortalorderQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalorderDao
{
  public abstract Long addPortalorder(Portalorder paramPortalorder)
    throws SQLException;

  public abstract Portalorder getPortalorderByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalorder> getPortalorderByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalorderQuery paramPortalorderQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalorderByKey(Portalorder paramPortalorder)
    throws SQLException;

  public abstract Integer updatePortalorderByKeyAll(Portalorder paramPortalorder)
    throws SQLException;

  public abstract List<Portalorder> getPortalorderListWithPage(PortalorderQuery paramPortalorderQuery)
    throws SQLException;

  public abstract List<Portalorder> getPortalorderList(PortalorderQuery paramPortalorderQuery)
    throws SQLException;

  public abstract Integer getPortalorderListCount(PortalorderQuery paramPortalorderQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalorderDao
 * JD-Core Version:    0.6.2
 */