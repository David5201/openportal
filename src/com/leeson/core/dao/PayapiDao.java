package com.leeson.core.dao;

import com.leeson.core.bean.Payapi;
import com.leeson.core.query.PayapiQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PayapiDao
{
  public abstract Long addPayapi(Payapi paramPayapi)
    throws SQLException;

  public abstract Payapi getPayapiByKey(Long paramLong)
    throws SQLException;

  public abstract List<Payapi> getPayapiByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PayapiQuery paramPayapiQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePayapiByKey(Payapi paramPayapi)
    throws SQLException;

  public abstract Integer updatePayapiByKeyAll(Payapi paramPayapi)
    throws SQLException;

  public abstract List<Payapi> getPayapiListWithPage(PayapiQuery paramPayapiQuery)
    throws SQLException;

  public abstract List<Payapi> getPayapiList(PayapiQuery paramPayapiQuery)
    throws SQLException;

  public abstract Integer getPayapiListCount(PayapiQuery paramPayapiQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PayapiDao
 * JD-Core Version:    0.6.2
 */