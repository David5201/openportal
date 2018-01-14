package com.leeson.core.dao;

import com.leeson.core.bean.Portalprivilege;
import com.leeson.core.query.PortalprivilegeQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalprivilegeDao
{
  public abstract Long addPortalprivilege(Portalprivilege paramPortalprivilege)
    throws SQLException;

  public abstract Portalprivilege getPortalprivilegeByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalprivilege> getPortalprivilegeByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalprivilegeQuery paramPortalprivilegeQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalprivilegeByKey(Portalprivilege paramPortalprivilege)
    throws SQLException;

  public abstract Integer updatePortalprivilegeByKeyAll(Portalprivilege paramPortalprivilege)
    throws SQLException;

  public abstract List<Portalprivilege> getPortalprivilegeListWithPage(PortalprivilegeQuery paramPortalprivilegeQuery)
    throws SQLException;

  public abstract List<Portalprivilege> getPortalprivilegeList(PortalprivilegeQuery paramPortalprivilegeQuery)
    throws SQLException;

  public abstract Integer getPortalprivilegeListCount(PortalprivilegeQuery paramPortalprivilegeQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalprivilegeDao
 * JD-Core Version:    0.6.2
 */