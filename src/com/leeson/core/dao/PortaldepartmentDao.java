package com.leeson.core.dao;

import com.leeson.core.bean.Portaldepartment;
import com.leeson.core.query.PortaldepartmentQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface PortaldepartmentDao
{
  public abstract Long addPortaldepartment(Portaldepartment paramPortaldepartment)
    throws SQLException;

  public abstract Portaldepartment getPortaldepartmentByKey(Long paramLong)
    throws SQLException;

  public abstract List<Portaldepartment> getPortaldepartmentByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(PortaldepartmentQuery paramPortaldepartmentQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer updatePortaldepartmentByKey(Portaldepartment paramPortaldepartment)
    throws SQLException;

  public abstract Integer updatePortaldepartmentByKeyAll(Portaldepartment paramPortaldepartment)
    throws SQLException;

  public abstract List<Portaldepartment> getPortaldepartmentListWithPage(PortaldepartmentQuery paramPortaldepartmentQuery)
    throws SQLException;

  public abstract List<Portaldepartment> getPortaldepartmentList(PortaldepartmentQuery paramPortaldepartmentQuery)
    throws SQLException;

  public abstract Integer getPortaldepartmentListCount(PortaldepartmentQuery paramPortaldepartmentQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.PortaldepartmentDao
 * JD-Core Version:    0.6.2
 */