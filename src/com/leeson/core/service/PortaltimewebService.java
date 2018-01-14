package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaltimeweb;
import com.leeson.core.query.PortaltimewebQuery;
import java.util.List;

public abstract interface PortaltimewebService
{
  public abstract Long addPortaltimeweb(Portaltimeweb paramPortaltimeweb);

  public abstract Portaltimeweb getPortaltimewebByKey(Long paramLong);

  public abstract List<Portaltimeweb> getPortaltimewebByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortaltimewebQuery paramPortaltimewebQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortaltimewebByKey(Portaltimeweb paramPortaltimeweb);

  public abstract Integer updatePortaltimewebByKeyAll(Portaltimeweb paramPortaltimeweb);

  public abstract Pagination getPortaltimewebListWithPage(PortaltimewebQuery paramPortaltimewebQuery);

  public abstract List<Portaltimeweb> getPortaltimewebList(PortaltimewebQuery paramPortaltimewebQuery);

  public abstract Integer getPortaltimewebCount(PortaltimewebQuery paramPortaltimewebQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortaltimewebService
 * JD-Core Version:    0.6.2
 */