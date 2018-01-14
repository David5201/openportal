package com.leeson.core.dao;

import com.leeson.core.bean.Portalphones;
import com.leeson.core.query.PortalphonesQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalphonesDao
{
  public abstract Long addPortalphones(Portalphones paramPortalphones)
    throws SQLException;

  public abstract Portalphones getPortalphonesByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalphones> getPortalphonesByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalphonesQuery paramPortalphonesQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalphonesByKey(Portalphones paramPortalphones)
    throws SQLException;

  public abstract Integer updatePortalphonesByKeyAll(Portalphones paramPortalphones)
    throws SQLException;

  public abstract List<Portalphones> getPortalphonesListWithPage(PortalphonesQuery paramPortalphonesQuery)
    throws SQLException;

  public abstract List<Portalphones> getPortalphonesList(PortalphonesQuery paramPortalphonesQuery)
    throws SQLException;

  public abstract Integer getPortalphonesListCount(PortalphonesQuery paramPortalphonesQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalphonesDao
 * JD-Core Version:    0.6.2
 */