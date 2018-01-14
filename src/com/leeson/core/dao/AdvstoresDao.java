package com.leeson.core.dao;

import com.leeson.core.bean.Advstores;
import com.leeson.core.query.AdvstoresQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface AdvstoresDao
{
  public abstract Long addAdvstores(Advstores paramAdvstores)
    throws SQLException;

  public abstract Advstores getAdvstoresByKey(Long paramLong)
    throws SQLException;

  public abstract List<Advstores> getAdvstoresByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(AdvstoresQuery paramAdvstoresQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateAdvstoresByKey(Advstores paramAdvstores)
    throws SQLException;

  public abstract Integer updateAdvstoresByKeyAll(Advstores paramAdvstores)
    throws SQLException;

  public abstract List<Advstores> getAdvstoresListWithPage(AdvstoresQuery paramAdvstoresQuery)
    throws SQLException;

  public abstract List<Advstores> getAdvstoresList(AdvstoresQuery paramAdvstoresQuery)
    throws SQLException;

  public abstract Integer getAdvstoresListCount(AdvstoresQuery paramAdvstoresQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.AdvstoresDao
 * JD-Core Version:    0.6.2
 */