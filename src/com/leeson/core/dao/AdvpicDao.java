package com.leeson.core.dao;

import com.leeson.core.bean.Advpic;
import com.leeson.core.query.AdvpicQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface AdvpicDao
{
  public abstract Long addAdvpic(Advpic paramAdvpic)
    throws SQLException;

  public abstract Advpic getAdvpicByKey(Long paramLong)
    throws SQLException;

  public abstract List<Advpic> getAdvpicByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(AdvpicQuery paramAdvpicQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateAdvpicByKey(Advpic paramAdvpic)
    throws SQLException;

  public abstract Integer updateAdvpicByKeyAll(Advpic paramAdvpic)
    throws SQLException;

  public abstract List<Advpic> getAdvpicListWithPage(AdvpicQuery paramAdvpicQuery)
    throws SQLException;

  public abstract List<Advpic> getAdvpicList(AdvpicQuery paramAdvpicQuery)
    throws SQLException;

  public abstract Integer getAdvpicListCount(AdvpicQuery paramAdvpicQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.AdvpicDao
 * JD-Core Version:    0.6.2
 */