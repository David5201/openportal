package com.leeson.core.dao;

import com.leeson.core.bean.Portalcardcategory;
import com.leeson.core.query.PortalcardcategoryQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalcardcategoryDao
{
  public abstract Long addPortalcardcategory(Portalcardcategory paramPortalcardcategory)
    throws SQLException;

  public abstract Portalcardcategory getPortalcardcategoryByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalcardcategory> getPortalcardcategoryByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalcardcategoryQuery paramPortalcardcategoryQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortalcardcategoryByKey(Portalcardcategory paramPortalcardcategory)
    throws SQLException;

  public abstract Integer updatePortalcardcategoryByKeyAll(Portalcardcategory paramPortalcardcategory)
    throws SQLException;

  public abstract List<Portalcardcategory> getPortalcardcategoryListWithPage(PortalcardcategoryQuery paramPortalcardcategoryQuery)
    throws SQLException;

  public abstract List<Portalcardcategory> getPortalcardcategoryList(PortalcardcategoryQuery paramPortalcardcategoryQuery)
    throws SQLException;

  public abstract Integer getPortalcardcategoryListCount(PortalcardcategoryQuery paramPortalcardcategoryQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalcardcategoryDao
 * JD-Core Version:    0.6.2
 */