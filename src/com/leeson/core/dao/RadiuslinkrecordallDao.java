package com.leeson.core.dao;

import com.leeson.core.bean.Radiuslinkrecordall;
import com.leeson.core.query.RadiuslinkrecordallQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface RadiuslinkrecordallDao
{
  public abstract Long addRadiuslinkrecordall(Radiuslinkrecordall paramRadiuslinkrecordall)
    throws SQLException;

  public abstract Radiuslinkrecordall getRadiuslinkrecordallByKey(Long paramLong)
    throws SQLException;

  public abstract List<Radiuslinkrecordall> getRadiuslinkrecordallByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateRadiuslinkrecordallByKey(Radiuslinkrecordall paramRadiuslinkrecordall)
    throws SQLException;

  public abstract Integer updateRadiuslinkrecordallByKeyAll(Radiuslinkrecordall paramRadiuslinkrecordall)
    throws SQLException;

  public abstract List<Radiuslinkrecordall> getRadiuslinkrecordallListWithPage(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery)
    throws SQLException;

  public abstract List<Radiuslinkrecordall> getRadiuslinkrecordallList(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery)
    throws SQLException;

  public abstract Integer getRadiuslinkrecordallListCount(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.RadiuslinkrecordallDao
 * JD-Core Version:    0.6.2
 */