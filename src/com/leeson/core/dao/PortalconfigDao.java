package com.leeson.core.dao;

import com.leeson.core.bean.Portalconfig;
import com.leeson.core.query.PortalconfigQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalconfigDao
{
  public abstract Long addPortalconfig(Portalconfig paramPortalconfig)
    throws SQLException;

  public abstract Portalconfig getPortalconfigByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalconfig> getPortalconfigByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalconfigQuery paramPortalconfigQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalconfigByKey(Portalconfig paramPortalconfig)
    throws SQLException;

  public abstract Integer updatePortalconfigByKeyAll(Portalconfig paramPortalconfig)
    throws SQLException;

  public abstract List<Portalconfig> getPortalconfigListWithPage(PortalconfigQuery paramPortalconfigQuery)
    throws SQLException;

  public abstract List<Portalconfig> getPortalconfigList(PortalconfigQuery paramPortalconfigQuery)
    throws SQLException;

  public abstract Integer getPortalconfigListCount(PortalconfigQuery paramPortalconfigQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalconfigDao
 * JD-Core Version:    0.6.2
 */