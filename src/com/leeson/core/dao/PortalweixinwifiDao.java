package com.leeson.core.dao;

import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.query.PortalweixinwifiQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalweixinwifiDao
{
  public abstract Long addPortalweixinwifi(Portalweixinwifi paramPortalweixinwifi)
    throws SQLException;

  public abstract Portalweixinwifi getPortalweixinwifiByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalweixinwifi> getPortalweixinwifiByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalweixinwifiQuery paramPortalweixinwifiQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalweixinwifiByKey(Portalweixinwifi paramPortalweixinwifi)
    throws SQLException;

  public abstract Integer updatePortalweixinwifiByKeyAll(Portalweixinwifi paramPortalweixinwifi)
    throws SQLException;

  public abstract List<Portalweixinwifi> getPortalweixinwifiListWithPage(PortalweixinwifiQuery paramPortalweixinwifiQuery)
    throws SQLException;

  public abstract List<Portalweixinwifi> getPortalweixinwifiList(PortalweixinwifiQuery paramPortalweixinwifiQuery)
    throws SQLException;

  public abstract Integer getPortalweixinwifiListCount(PortalweixinwifiQuery paramPortalweixinwifiQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalweixinwifiDao
 * JD-Core Version:    0.6.2
 */