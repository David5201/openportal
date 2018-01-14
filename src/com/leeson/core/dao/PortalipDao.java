package com.leeson.core.dao;

import com.leeson.core.bean.Portalip;
import com.leeson.core.query.PortalipQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalipDao
{
  public abstract Long addPortalip(Portalip paramPortalip)
    throws SQLException;

  public abstract Portalip getPortalipByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalip> getPortalipByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalipQuery paramPortalipQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalipByKey(Portalip paramPortalip)
    throws SQLException;

  public abstract Integer updatePortalipByKeyAll(Portalip paramPortalip)
    throws SQLException;

  public abstract List<Portalip> getPortalipListWithPage(PortalipQuery paramPortalipQuery)
    throws SQLException;

  public abstract List<Portalip> getPortalipList(PortalipQuery paramPortalipQuery)
    throws SQLException;

  public abstract Integer getPortalipListCount(PortalipQuery paramPortalipQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalipDao
 * JD-Core Version:    0.6.2
 */