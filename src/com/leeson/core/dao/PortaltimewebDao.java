package com.leeson.core.dao;

import com.leeson.core.bean.Portaltimeweb;
import com.leeson.core.query.PortaltimewebQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortaltimewebDao
{
  public abstract Long addPortaltimeweb(Portaltimeweb paramPortaltimeweb)
    throws SQLException;

  public abstract Portaltimeweb getPortaltimewebByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portaltimeweb> getPortaltimewebByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortaltimewebQuery paramPortaltimewebQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortaltimewebByKey(Portaltimeweb paramPortaltimeweb)
    throws SQLException;

  public abstract Integer updatePortaltimewebByKeyAll(Portaltimeweb paramPortaltimeweb)
    throws SQLException;

  public abstract List<Portaltimeweb> getPortaltimewebListWithPage(PortaltimewebQuery paramPortaltimewebQuery)
    throws SQLException;

  public abstract List<Portaltimeweb> getPortaltimewebList(PortaltimewebQuery paramPortaltimewebQuery)
    throws SQLException;

  public abstract Integer getPortaltimewebListCount(PortaltimewebQuery paramPortaltimewebQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortaltimewebDao
 * JD-Core Version:    0.6.2
 */