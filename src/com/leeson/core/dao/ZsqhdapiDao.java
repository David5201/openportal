package com.leeson.core.dao;

import com.leeson.core.bean.Zsqhdapi;
import com.leeson.core.query.ZsqhdapiQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface ZsqhdapiDao
{
  public abstract Long addZsqhdapi(Zsqhdapi paramZsqhdapi)
    throws SQLException;

  public abstract Zsqhdapi getZsqhdapiByKey(Long paramLong)
    throws SQLException;

  public abstract List<Zsqhdapi> getZsqhdapiByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(ZsqhdapiQuery paramZsqhdapiQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateZsqhdapiByKey(Zsqhdapi paramZsqhdapi)
    throws SQLException;

  public abstract Integer updateZsqhdapiByKeyAll(Zsqhdapi paramZsqhdapi)
    throws SQLException;

  public abstract List<Zsqhdapi> getZsqhdapiListWithPage(ZsqhdapiQuery paramZsqhdapiQuery)
    throws SQLException;

  public abstract List<Zsqhdapi> getZsqhdapiList(ZsqhdapiQuery paramZsqhdapiQuery)
    throws SQLException;

  public abstract Integer getZsqhdapiListCount(ZsqhdapiQuery paramZsqhdapiQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.ZsqhdapiDao
 * JD-Core Version:    0.6.2
 */