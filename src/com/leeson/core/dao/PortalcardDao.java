package com.leeson.core.dao;

import com.leeson.core.bean.Portalcard;
import com.leeson.core.query.PortalcardQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalcardDao
{
  public abstract Long addPortalcard(Portalcard paramPortalcard)
    throws SQLException;

  public abstract Portalcard getPortalcardByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalcard> getPortalcardByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalcardQuery paramPortalcardQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalcardByKey(Portalcard paramPortalcard)
    throws SQLException;

  public abstract Integer updatePortalcardByKeyAll(Portalcard paramPortalcard)
    throws SQLException;

  public abstract List<Portalcard> getPortalcardListWithPage(PortalcardQuery paramPortalcardQuery)
    throws SQLException;

  public abstract List<Portalcard> getPortalcardList(PortalcardQuery paramPortalcardQuery)
    throws SQLException;

  public abstract Integer getPortalcardListCount(PortalcardQuery paramPortalcardQuery)
    throws SQLException;

  public abstract List<Portalcard> getPortalcardSaleListWithPage(PortalcardQuery paramPortalcardQuery)
    throws SQLException;

  public abstract List<Portalcard> getPortalcardSaleList(PortalcardQuery paramPortalcardQuery)
    throws SQLException;

  public abstract Integer getPortalcardSaleListCount(PortalcardQuery paramPortalcardQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalcardDao
 * JD-Core Version:    0.6.2
 */