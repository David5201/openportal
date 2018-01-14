package com.leeson.core.dao;

import com.leeson.core.bean.Advadv;
import com.leeson.core.query.AdvadvQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface AdvadvDao
{
  public abstract Long addAdvadv(Advadv paramAdvadv)
    throws SQLException;

  public abstract Advadv getAdvadvByKey(Long paramLong)
    throws SQLException;

  public abstract List<Advadv> getAdvadvByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(AdvadvQuery paramAdvadvQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateAdvadvByKey(Advadv paramAdvadv)
    throws SQLException;

  public abstract Integer updateAdvadvByKeyAll(Advadv paramAdvadv)
    throws SQLException;

  public abstract List<Advadv> getAdvadvListWithPage(AdvadvQuery paramAdvadvQuery)
    throws SQLException;

  public abstract List<Advadv> getAdvadvList(AdvadvQuery paramAdvadvQuery)
    throws SQLException;

  public abstract Integer getAdvadvListCount(AdvadvQuery paramAdvadvQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.AdvadvDao
 * JD-Core Version:    0.6.2
 */