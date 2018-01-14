package com.leeson.core.dao;

import com.leeson.core.bean.Advbanner;
import com.leeson.core.query.AdvbannerQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface AdvbannerDao
{
  public abstract Long addAdvbanner(Advbanner paramAdvbanner)
    throws SQLException;

  public abstract Advbanner getAdvbannerByKey(Long paramLong)
    throws SQLException;

  public abstract List<Advbanner> getAdvbannerByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(AdvbannerQuery paramAdvbannerQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateAdvbannerByKey(Advbanner paramAdvbanner)
    throws SQLException;

  public abstract Integer updateAdvbannerByKeyAll(Advbanner paramAdvbanner)
    throws SQLException;

  public abstract List<Advbanner> getAdvbannerListWithPage(AdvbannerQuery paramAdvbannerQuery)
    throws SQLException;

  public abstract List<Advbanner> getAdvbannerList(AdvbannerQuery paramAdvbannerQuery)
    throws SQLException;

  public abstract Integer getAdvbannerListCount(AdvbannerQuery paramAdvbannerQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.AdvbannerDao
 * JD-Core Version:    0.6.2
 */