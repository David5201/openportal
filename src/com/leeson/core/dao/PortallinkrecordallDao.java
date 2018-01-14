package com.leeson.core.dao;

import com.leeson.core.bean.Portallinkrecordall;
import com.leeson.core.query.PortallinkrecordallQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortallinkrecordallDao
{
  public abstract Long addPortallinkrecordall(Portallinkrecordall paramPortallinkrecordall)
    throws SQLException;

  public abstract Portallinkrecordall getPortallinkrecordallByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portallinkrecordall> getPortallinkrecordallByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortallinkrecordallQuery paramPortallinkrecordallQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updatePortallinkrecordallByKey(Portallinkrecordall paramPortallinkrecordall)
    throws SQLException;

  public abstract Integer updatePortallinkrecordallByKeyAll(Portallinkrecordall paramPortallinkrecordall)
    throws SQLException;

  public abstract List<Portallinkrecordall> getPortallinkrecordallListWithPage(PortallinkrecordallQuery paramPortallinkrecordallQuery)
    throws SQLException;

  public abstract List<Portallinkrecordall> getPortallinkrecordallList(PortallinkrecordallQuery paramPortallinkrecordallQuery)
    throws SQLException;

  public abstract Integer getPortallinkrecordallListCount(PortallinkrecordallQuery paramPortallinkrecordallQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortallinkrecordallDao
 * JD-Core Version:    0.6.2
 */