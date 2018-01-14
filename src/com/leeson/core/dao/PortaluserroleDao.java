package com.leeson.core.dao;

import com.leeson.core.bean.Portaluserrole;
import com.leeson.core.query.PortaluserroleQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortaluserroleDao
{
  public abstract Long addPortaluserrole(Portaluserrole paramPortaluserrole)
    throws SQLException;

  public abstract Portaluserrole getPortaluserroleByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portaluserrole> getPortaluserroleByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortaluserroleQuery paramPortaluserroleQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortaluserroleByKey(Portaluserrole paramPortaluserrole)
    throws SQLException;

  public abstract Integer updatePortaluserroleByKeyAll(Portaluserrole paramPortaluserrole)
    throws SQLException;

  public abstract List<Portaluserrole> getPortaluserroleListWithPage(PortaluserroleQuery paramPortaluserroleQuery)
    throws SQLException;

  public abstract List<Portaluserrole> getPortaluserroleList(PortaluserroleQuery paramPortaluserroleQuery)
    throws SQLException;

  public abstract Integer getPortaluserroleListCount(PortaluserroleQuery paramPortaluserroleQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortaluserroleDao
 * JD-Core Version:    0.6.2
 */