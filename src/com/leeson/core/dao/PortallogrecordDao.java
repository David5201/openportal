package com.leeson.core.dao;

import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.query.PortallogrecordQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortallogrecordDao
{
  public abstract Long addPortallogrecord(Portallogrecord paramPortallogrecord)
    throws SQLException;

  public abstract Portallogrecord getPortallogrecordByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portallogrecord> getPortallogrecordByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortallogrecordQuery paramPortallogrecordQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortallogrecordByKey(Portallogrecord paramPortallogrecord)
    throws SQLException;

  public abstract Integer updatePortallogrecordByKeyAll(Portallogrecord paramPortallogrecord)
    throws SQLException;

  public abstract List<Portallogrecord> getPortallogrecordListWithPage(PortallogrecordQuery paramPortallogrecordQuery)
    throws SQLException;

  public abstract List<Portallogrecord> getPortallogrecordList(PortallogrecordQuery paramPortallogrecordQuery)
    throws SQLException;

  public abstract Integer getPortallogrecordListCount(PortallogrecordQuery paramPortallogrecordQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortallogrecordDao
 * JD-Core Version:    0.6.2
 */