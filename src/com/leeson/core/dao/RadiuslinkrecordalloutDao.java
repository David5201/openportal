package com.leeson.core.dao;

import com.leeson.core.bean.Radiuslinkrecordallout;
import com.leeson.core.query.RadiuslinkrecordalloutQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface RadiuslinkrecordalloutDao
{
  public abstract Long addRadiuslinkrecordallout(Radiuslinkrecordallout paramRadiuslinkrecordallout)
    throws SQLException;

  public abstract Radiuslinkrecordallout getRadiuslinkrecordalloutByKey(Long paramLong)
    throws SQLException;

  public abstract List<Radiuslinkrecordallout> getRadiuslinkrecordalloutByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateRadiuslinkrecordalloutByKey(Radiuslinkrecordallout paramRadiuslinkrecordallout)
    throws SQLException;

  public abstract Integer updateRadiuslinkrecordalloutByKeyAll(Radiuslinkrecordallout paramRadiuslinkrecordallout)
    throws SQLException;

  public abstract List<Radiuslinkrecordallout> getRadiuslinkrecordalloutListWithPage(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery)
    throws SQLException;

  public abstract List<Radiuslinkrecordallout> getRadiuslinkrecordalloutList(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery)
    throws SQLException;

  public abstract Integer getRadiuslinkrecordalloutListCount(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.RadiuslinkrecordalloutDao
 * JD-Core Version:    0.6.2
 */