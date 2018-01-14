package com.leeson.core.dao;

import com.leeson.core.bean.Portalweb;
import com.leeson.core.query.PortalwebQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalwebDao
{
  public abstract Long addPortalweb(Portalweb paramPortalweb)
    throws SQLException;

  public abstract Portalweb getPortalwebByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalweb> getPortalwebByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalwebQuery paramPortalwebQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalwebByKey(Portalweb paramPortalweb)
    throws SQLException;

  public abstract Integer updatePortalwebByKeyAll(Portalweb paramPortalweb)
    throws SQLException;

  public abstract List<Portalweb> getPortalwebListWithPage(PortalwebQuery paramPortalwebQuery)
    throws SQLException;

  public abstract List<Portalweb> getPortalwebList(PortalwebQuery paramPortalwebQuery)
    throws SQLException;

  public abstract Integer getPortalwebListCount(PortalwebQuery paramPortalwebQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalwebDao
 * JD-Core Version:    0.6.2
 */