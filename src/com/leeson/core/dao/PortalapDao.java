package com.leeson.core.dao;

import com.leeson.core.bean.Portalap;
import com.leeson.core.query.PortalapQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalapDao
{
  public abstract Long addPortalap(Portalap paramPortalap)
    throws SQLException;

  public abstract Portalap getPortalapByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalap> getPortalapByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalapQuery paramPortalapQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalapByKey(Portalap paramPortalap)
    throws SQLException;

  public abstract Integer updatePortalapByKeyAll(Portalap paramPortalap)
    throws SQLException;

  public abstract List<Portalap> getPortalapListWithPage(PortalapQuery paramPortalapQuery)
    throws SQLException;

  public abstract List<Portalap> getPortalapList(PortalapQuery paramPortalapQuery)
    throws SQLException;

  public abstract Integer getPortalapListCount(PortalapQuery paramPortalapQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalapDao
 * JD-Core Version:    0.6.2
 */