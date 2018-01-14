package com.leeson.core.dao;

import com.leeson.core.bean.Portalbas;
import com.leeson.core.query.PortalbasQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalbasDao
{
  public abstract Long addPortalbas(Portalbas paramPortalbas)
    throws SQLException;

  public abstract Portalbas getPortalbasByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalbas> getPortalbasByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalbasQuery paramPortalbasQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalbasByKey(Portalbas paramPortalbas)
    throws SQLException;

  public abstract Integer updatePortalbasByKeyAll(Portalbas paramPortalbas)
    throws SQLException;

  public abstract List<Portalbas> getPortalbasListWithPage(PortalbasQuery paramPortalbasQuery)
    throws SQLException;

  public abstract List<Portalbas> getPortalbasList(PortalbasQuery paramPortalbasQuery)
    throws SQLException;

  public abstract Integer getPortalbasListCount(PortalbasQuery paramPortalbasQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalbasDao
 * JD-Core Version:    0.6.2
 */