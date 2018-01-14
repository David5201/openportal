package com.leeson.core.dao;

import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.query.PortalsmsapiQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalsmsapiDao
{
  public abstract Long addPortalsmsapi(Portalsmsapi paramPortalsmsapi)
    throws SQLException;

  public abstract Portalsmsapi getPortalsmsapiByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalsmsapi> getPortalsmsapiByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalsmsapiQuery paramPortalsmsapiQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalsmsapiByKey(Portalsmsapi paramPortalsmsapi)
    throws SQLException;

  public abstract Integer updatePortalsmsapiByKeyAll(Portalsmsapi paramPortalsmsapi)
    throws SQLException;

  public abstract List<Portalsmsapi> getPortalsmsapiListWithPage(PortalsmsapiQuery paramPortalsmsapiQuery)
    throws SQLException;

  public abstract List<Portalsmsapi> getPortalsmsapiList(PortalsmsapiQuery paramPortalsmsapiQuery)
    throws SQLException;

  public abstract Integer getPortalsmsapiListCount(PortalsmsapiQuery paramPortalsmsapiQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalsmsapiDao
 * JD-Core Version:    0.6.2
 */