package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.query.PortalonlinelimitQuery;
import java.util.List;

public abstract interface PortalonlinelimitService
{
  public abstract Long addPortalonlinelimit(Portalonlinelimit paramPortalonlinelimit);

  public abstract Portalonlinelimit getPortalonlinelimitByKey(Long paramLong);

  public abstract List<Portalonlinelimit> getPortalonlinelimitByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalonlinelimitQuery paramPortalonlinelimitQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalonlinelimitByKey(Portalonlinelimit paramPortalonlinelimit);

  public abstract Integer updatePortalonlinelimitByKeyAll(Portalonlinelimit paramPortalonlinelimit);

  public abstract Pagination getPortalonlinelimitListWithPage(PortalonlinelimitQuery paramPortalonlinelimitQuery);

  public abstract List<Portalonlinelimit> getPortalonlinelimitList(PortalonlinelimitQuery paramPortalonlinelimitQuery);

  public abstract Integer getPortalonlinelimitCount(PortalonlinelimitQuery paramPortalonlinelimitQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalonlinelimitService
 * JD-Core Version:    0.6.2
 */