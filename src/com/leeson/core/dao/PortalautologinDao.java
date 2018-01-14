package com.leeson.core.dao;

import com.leeson.core.bean.Portalautologin;
import com.leeson.core.query.PortalautologinQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalautologinDao
{
  public abstract Long addPortalautologin(Portalautologin paramPortalautologin)
    throws SQLException;

  public abstract Portalautologin getPortalautologinByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalautologin> getPortalautologinByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalautologinQuery paramPortalautologinQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalautologinByKey(Portalautologin paramPortalautologin)
    throws SQLException;

  public abstract Integer updatePortalautologinByKeyAll(Portalautologin paramPortalautologin)
    throws SQLException;

  public abstract List<Portalautologin> getPortalautologinListWithPage(PortalautologinQuery paramPortalautologinQuery)
    throws SQLException;

  public abstract List<Portalautologin> getPortalautologinList(PortalautologinQuery paramPortalautologinQuery)
    throws SQLException;

  public abstract Integer getPortalautologinListCount(PortalautologinQuery paramPortalautologinQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalautologinDao
 * JD-Core Version:    0.6.2
 */