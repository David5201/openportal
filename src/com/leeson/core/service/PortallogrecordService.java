package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.query.PortallogrecordQuery;
import java.util.List;

public abstract interface PortallogrecordService
{
  public abstract Long addPortallogrecord(Portallogrecord paramPortallogrecord);

  public abstract Portallogrecord getPortallogrecordByKey(Long paramLong);

  public abstract List<Portallogrecord> getPortallogrecordByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortallogrecordQuery paramPortallogrecordQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortallogrecordByKey(Portallogrecord paramPortallogrecord);

  public abstract Pagination getPortallogrecordListWithPage(PortallogrecordQuery paramPortallogrecordQuery);

  public abstract List<Portallogrecord> getPortallogrecordList(PortallogrecordQuery paramPortallogrecordQuery);

  public abstract Integer getPortallogrecordCount(PortallogrecordQuery paramPortallogrecordQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortallogrecordService
 * JD-Core Version:    0.6.2
 */