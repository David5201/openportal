package com.leeson.core.dao;

import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.query.PortalaccountgroupQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalaccountgroupDao
{
  public abstract Long addPortalaccountgroup(Portalaccountgroup paramPortalaccountgroup)
    throws SQLException;

  public abstract Portalaccountgroup getPortalaccountgroupByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalaccountgroup> getPortalaccountgroupByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalaccountgroupQuery paramPortalaccountgroupQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalaccountgroupByKey(Portalaccountgroup paramPortalaccountgroup)
    throws SQLException;

  public abstract Integer updatePortalaccountgroupByKeyAll(Portalaccountgroup paramPortalaccountgroup)
    throws SQLException;

  public abstract List<Portalaccountgroup> getPortalaccountgroupListWithPage(PortalaccountgroupQuery paramPortalaccountgroupQuery)
    throws SQLException;

  public abstract List<Portalaccountgroup> getPortalaccountgroupList(PortalaccountgroupQuery paramPortalaccountgroupQuery)
    throws SQLException;

  public abstract Integer getPortalaccountgroupListCount(PortalaccountgroupQuery paramPortalaccountgroupQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalaccountgroupDao
 * JD-Core Version:    0.6.2
 */