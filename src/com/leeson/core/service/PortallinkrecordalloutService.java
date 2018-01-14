package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallinkrecordallout;
import com.leeson.core.query.PortallinkrecordalloutQuery;
import java.util.List;

public abstract interface PortallinkrecordalloutService
{
  public abstract Long addPortallinkrecordallout(Portallinkrecordallout paramPortallinkrecordallout);

  public abstract Portallinkrecordallout getPortallinkrecordalloutByKey(Long paramLong);

  public abstract List<Portallinkrecordallout> getPortallinkrecordalloutByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortallinkrecordalloutByKey(Portallinkrecordallout paramPortallinkrecordallout);

  public abstract Integer updatePortallinkrecordalloutByKeyAll(Portallinkrecordallout paramPortallinkrecordallout);

  public abstract Pagination getPortallinkrecordalloutListWithPage(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery);

  public abstract List<Portallinkrecordallout> getPortallinkrecordalloutList(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery);

  public abstract Integer getPortallinkrecordalloutCount(PortallinkrecordalloutQuery paramPortallinkrecordalloutQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortallinkrecordalloutService
 * JD-Core Version:    0.6.2
 */