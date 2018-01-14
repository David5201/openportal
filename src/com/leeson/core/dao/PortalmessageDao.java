package com.leeson.core.dao;

import com.leeson.core.bean.Portalmessage;
import com.leeson.core.query.PortalmessageQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalmessageDao
{
  public abstract Long addPortalmessage(Portalmessage paramPortalmessage)
    throws SQLException;

  public abstract Portalmessage getPortalmessageByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalmessage> getPortalmessageByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalmessageQuery paramPortalmessageQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalmessageByKey(Portalmessage paramPortalmessage)
    throws SQLException;

  public abstract Integer updatePortalmessageByKeyAll(Portalmessage paramPortalmessage)
    throws SQLException;

  public abstract List<Portalmessage> getPortalmessageListWithPage(PortalmessageQuery paramPortalmessageQuery)
    throws SQLException;

  public abstract List<Portalmessage> getPortalmessageList(PortalmessageQuery paramPortalmessageQuery)
    throws SQLException;

  public abstract Integer getPortalmessageListCount(PortalmessageQuery paramPortalmessageQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalmessageDao
 * JD-Core Version:    0.6.2
 */