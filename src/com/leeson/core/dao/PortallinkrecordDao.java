package com.leeson.core.dao;

import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.query.PortallinkrecordQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortallinkrecordDao
{
  public abstract Long addPortallinkrecord(Portallinkrecord paramPortallinkrecord)
    throws SQLException;

  public abstract Portallinkrecord getPortallinkrecordByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portallinkrecord> getPortallinkrecordByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortallinkrecordQuery paramPortallinkrecordQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortallinkrecordByKey(Portallinkrecord paramPortallinkrecord)
    throws SQLException;

  public abstract Integer updatePortallinkrecordByKeyAll(Portallinkrecord paramPortallinkrecord)
    throws SQLException;

  public abstract List<Portallinkrecord> getPortallinkrecordListWithPage(PortallinkrecordQuery paramPortallinkrecordQuery)
    throws SQLException;

  public abstract List<Portallinkrecord> getPortallinkrecordList(PortallinkrecordQuery paramPortallinkrecordQuery)
    throws SQLException;

  public abstract Integer getPortallinkrecordListCount(PortallinkrecordQuery paramPortallinkrecordQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortallinkrecordDao
 * JD-Core Version:    0.6.2
 */