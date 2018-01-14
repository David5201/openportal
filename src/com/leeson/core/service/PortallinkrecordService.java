package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.query.PortallinkrecordQuery;
import java.util.List;

public abstract interface PortallinkrecordService
{
  public abstract Long addPortallinkrecord(Portallinkrecord paramPortallinkrecord);

  public abstract Portallinkrecord getPortallinkrecordByKey(Long paramLong);

  public abstract List<Portallinkrecord> getPortallinkrecordByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortallinkrecordQuery paramPortallinkrecordQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortallinkrecordByKey(Portallinkrecord paramPortallinkrecord);

  public abstract Integer updatePortallinkrecordByKeyAll(Portallinkrecord paramPortallinkrecord);

  public abstract Pagination getPortallinkrecordListWithPage(PortallinkrecordQuery paramPortallinkrecordQuery);

  public abstract List<Portallinkrecord> getPortallinkrecordList(PortallinkrecordQuery paramPortallinkrecordQuery);

  public abstract Integer getPortallinkrecordCount(PortallinkrecordQuery paramPortallinkrecordQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortallinkrecordService
 * JD-Core Version:    0.6.2
 */