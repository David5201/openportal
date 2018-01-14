package com.leeson.core.dao;

import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.query.PortalonlinelimitQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortalonlinelimitDao
{
  public abstract Long addPortalonlinelimit(Portalonlinelimit paramPortalonlinelimit)
    throws SQLException;

  public abstract Portalonlinelimit getPortalonlinelimitByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portalonlinelimit> getPortalonlinelimitByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortalonlinelimitQuery paramPortalonlinelimitQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortalonlinelimitByKey(Portalonlinelimit paramPortalonlinelimit)
    throws SQLException;

  public abstract Integer updatePortalonlinelimitByKeyAll(Portalonlinelimit paramPortalonlinelimit)
    throws SQLException;

  public abstract List<Portalonlinelimit> getPortalonlinelimitListWithPage(PortalonlinelimitQuery paramPortalonlinelimitQuery)
    throws SQLException;

  public abstract List<Portalonlinelimit> getPortalonlinelimitList(PortalonlinelimitQuery paramPortalonlinelimitQuery)
    throws SQLException;

  public abstract Integer getPortalonlinelimitListCount(PortalonlinelimitQuery paramPortalonlinelimitQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortalonlinelimitDao
 * JD-Core Version:    0.6.2
 */