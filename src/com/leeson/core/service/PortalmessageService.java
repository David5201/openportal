package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalmessage;
import com.leeson.core.query.PortalmessageQuery;
import java.util.List;

public abstract interface PortalmessageService
{
  public abstract Long addPortalmessage(Portalmessage paramPortalmessage);

  public abstract Portalmessage getPortalmessageByKey(Long paramLong);

  public abstract List<Portalmessage> getPortalmessageByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalmessageQuery paramPortalmessageQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalmessageByKey(Portalmessage paramPortalmessage);

  public abstract Pagination getPortalmessageListWithPage(PortalmessageQuery paramPortalmessageQuery);

  public abstract List<Portalmessage> getPortalmessageList(PortalmessageQuery paramPortalmessageQuery);

  public abstract Integer getPortalmessageCount(PortalmessageQuery paramPortalmessageQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalmessageService
 * JD-Core Version:    0.6.2
 */