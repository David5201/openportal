package com.leeson.core.dao;

import com.leeson.core.bean.Portalurlparameter;
import com.leeson.core.query.PortalurlparameterQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalurlparameterDao
{
  public abstract Long addPortalurlparameter(Portalurlparameter paramPortalurlparameter)
    throws SQLException;

  public abstract Portalurlparameter getPortalurlparameterByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalurlparameter> getPortalurlparameterByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalurlparameterQuery paramPortalurlparameterQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalurlparameterByKey(Portalurlparameter paramPortalurlparameter)
    throws SQLException;

  public abstract Integer updatePortalurlparameterByKeyAll(Portalurlparameter paramPortalurlparameter)
    throws SQLException;

  public abstract List<Portalurlparameter> getPortalurlparameterListWithPage(PortalurlparameterQuery paramPortalurlparameterQuery)
    throws SQLException;

  public abstract List<Portalurlparameter> getPortalurlparameterList(PortalurlparameterQuery paramPortalurlparameterQuery)
    throws SQLException;

  public abstract Integer getPortalurlparameterListCount(PortalurlparameterQuery paramPortalurlparameterQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalurlparameterDao
 * JD-Core Version:    0.6.2
 */