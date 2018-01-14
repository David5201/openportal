package com.leeson.core.dao;

import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.PortaluserQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortaluserDao
{
  public abstract Long addPortaluser(Portaluser paramPortaluser)
    throws SQLException;

  public abstract Portaluser getPortaluserByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portaluser> getPortaluserByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortaluserQuery paramPortaluserQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortaluserByKey(Portaluser paramPortaluser)
    throws SQLException;

  public abstract Integer updatePortaluserByKeyAll(Portaluser paramPortaluser)
    throws SQLException;

  public abstract List<Portaluser> getPortaluserListWithPage(PortaluserQuery paramPortaluserQuery)
    throws SQLException;

  public abstract List<Portaluser> getPortaluserList(PortaluserQuery paramPortaluserQuery)
    throws SQLException;

  public abstract Integer getPortaluserListCount(PortaluserQuery paramPortaluserQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortaluserDao
 * JD-Core Version:    0.6.2
 */