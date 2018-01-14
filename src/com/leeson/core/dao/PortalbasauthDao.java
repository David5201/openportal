package com.leeson.core.dao;

import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.query.PortalbasauthQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalbasauthDao
{
  public abstract Long addPortalbasauth(Portalbasauth paramPortalbasauth)
    throws SQLException;

  public abstract Portalbasauth getPortalbasauthByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalbasauth> getPortalbasauthByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalbasauthQuery paramPortalbasauthQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalbasauthByKey(Portalbasauth paramPortalbasauth)
    throws SQLException;

  public abstract Integer updatePortalbasauthByKeyAll(Portalbasauth paramPortalbasauth)
    throws SQLException;

  public abstract List<Portalbasauth> getPortalbasauthListWithPage(PortalbasauthQuery paramPortalbasauthQuery)
    throws SQLException;

  public abstract List<Portalbasauth> getPortalbasauthList(PortalbasauthQuery paramPortalbasauthQuery)
    throws SQLException;

  public abstract Integer getPortalbasauthListCount(PortalbasauthQuery paramPortalbasauthQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalbasauthDao
 * JD-Core Version:    0.6.2
 */