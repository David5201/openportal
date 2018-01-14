package com.leeson.core.dao;

import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.query.PortalaccountmacsQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalaccountmacsDao
{
  public abstract Long addPortalaccountmacs(Portalaccountmacs paramPortalaccountmacs)
    throws SQLException;

  public abstract Portalaccountmacs getPortalaccountmacsByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalaccountmacs> getPortalaccountmacsByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalaccountmacsQuery paramPortalaccountmacsQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalaccountmacsByKey(Portalaccountmacs paramPortalaccountmacs)
    throws SQLException;

  public abstract Integer updatePortalaccountmacsByKeyAll(Portalaccountmacs paramPortalaccountmacs)
    throws SQLException;

  public abstract List<Portalaccountmacs> getPortalaccountmacsListWithPage(PortalaccountmacsQuery paramPortalaccountmacsQuery)
    throws SQLException;

  public abstract List<Portalaccountmacs> getPortalaccountmacsList(PortalaccountmacsQuery paramPortalaccountmacsQuery)
    throws SQLException;

  public abstract Integer getPortalaccountmacsListCount(PortalaccountmacsQuery paramPortalaccountmacsQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalaccountmacsDao
 * JD-Core Version:    0.6.2
 */