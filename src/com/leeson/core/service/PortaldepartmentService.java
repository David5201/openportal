package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaldepartment;
import com.leeson.core.query.PortaldepartmentQuery;
import java.util.List;

public abstract interface PortaldepartmentService
{
  public abstract Long addPortaldepartment(Portaldepartment paramPortaldepartment);

  public abstract Portaldepartment getPortaldepartmentByKey(Long paramLong);

  public abstract List<Portaldepartment> getPortaldepartmentByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortaldepartmentQuery paramPortaldepartmentQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortaldepartmentByKey(Portaldepartment paramPortaldepartment);

  public abstract Integer updatePortaldepartmentByKeyAll(Portaldepartment paramPortaldepartment);

  public abstract Pagination getPortaldepartmentListWithPage(PortaldepartmentQuery paramPortaldepartmentQuery);

  public abstract List<Portaldepartment> getPortaldepartmentList(PortaldepartmentQuery paramPortaldepartmentQuery);

  public abstract Integer getPortaldepartmentCount(PortaldepartmentQuery paramPortaldepartmentQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortaldepartmentService
 * JD-Core Version:    0.6.2
 */