package com.leeson.core.dao;

import com.leeson.core.bean.Portallinkrecordallout;
import com.leeson.core.query.PortallinkrecordalloutQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortallinkrecordalloutDao
{
  public abstract Long addPortallinkrecordallout(Portallinkrecordallout paramPortallinkrecordallout)
    throws SQLException;

  public abstract Portallinkrecordallout getPortallinkrecordalloutByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portallinkrecordallout> getPortallinkrecordalloutByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortallinkrecordalloutByKey(Portallinkrecordallout paramPortallinkrecordallout)
    throws SQLException;

  public abstract Integer updatePortallinkrecordalloutByKeyAll(Portallinkrecordallout paramPortallinkrecordallout)
    throws SQLException;

  public abstract List<Portallinkrecordallout> getPortallinkrecordalloutListWithPage(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery)
    throws SQLException;

  public abstract List<Portallinkrecordallout> getPortallinkrecordalloutList(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery)
    throws SQLException;

  public abstract Integer getPortallinkrecordalloutListCount(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortallinkrecordalloutDao
 * JD-Core Version:    0.6.2
 */