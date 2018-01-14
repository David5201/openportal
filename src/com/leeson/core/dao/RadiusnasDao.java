package com.leeson.core.dao;

import com.leeson.core.bean.Radiusnas;
import com.leeson.core.query.RadiusnasQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface RadiusnasDao
{
  public abstract Long addRadiusnas(Radiusnas paramRadiusnas)
    throws SQLException;

  public abstract Radiusnas getRadiusnasByKey(Long paramLong)
    throws SQLException;

  public abstract List<Radiusnas> getRadiusnasByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(RadiusnasQuery paramRadiusnasQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateRadiusnasByKey(Radiusnas paramRadiusnas)
    throws SQLException;

  public abstract Integer updateRadiusnasByKeyAll(Radiusnas paramRadiusnas)
    throws SQLException;

  public abstract List<Radiusnas> getRadiusnasListWithPage(RadiusnasQuery paramRadiusnasQuery)
    throws SQLException;

  public abstract List<Radiusnas> getRadiusnasList(RadiusnasQuery paramRadiusnasQuery)
    throws SQLException;

  public abstract Integer getRadiusnasListCount(RadiusnasQuery paramRadiusnasQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.RadiusnasDao
 * JD-Core Version:    0.6.2
 */