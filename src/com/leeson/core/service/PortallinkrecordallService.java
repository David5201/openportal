package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallinkrecordall;
import com.leeson.core.query.PortallinkrecordallQuery;
import java.util.List;

public abstract interface PortallinkrecordallService
{
  public abstract Long addPortallinkrecordall(Portallinkrecordall paramPortallinkrecordall);

  public abstract Portallinkrecordall getPortallinkrecordallByKey(Long paramLong);

  public abstract List<Portallinkrecordall> getPortallinkrecordallByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortallinkrecordallQuery paramPortallinkrecordallQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortallinkrecordallByKey(Portallinkrecordall paramPortallinkrecordall);

  public abstract Integer updatePortallinkrecordallByKeyAll(Portallinkrecordall paramPortallinkrecordall);

  public abstract Pagination getPortallinkrecordallListWithPage(PortallinkrecordallQuery paramPortallinkrecordallQuery);

  public abstract List<Portallinkrecordall> getPortallinkrecordallList(PortallinkrecordallQuery paramPortallinkrecordallQuery);

  public abstract Integer getPortallinkrecordallCount(PortallinkrecordallQuery paramPortallinkrecordallQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortallinkrecordallService
 * JD-Core Version:    0.6.2
 */