package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.query.PortalcardQuery;
import java.util.List;

public abstract interface PortalcardService
{
  public abstract Long addPortalcard(Portalcard paramPortalcard);

  public abstract Portalcard getPortalcardByKey(Long paramLong);

  public abstract List<Portalcard> getPortalcardByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalcardQuery paramPortalcardQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalcardByKey(Portalcard paramPortalcard);

  public abstract Integer updatePortalcardByKeyAll(Portalcard paramPortalcard);

  public abstract Pagination getPortalcardListWithPage(PortalcardQuery paramPortalcardQuery);

  public abstract List<Portalcard> getPortalcardList(PortalcardQuery paramPortalcardQuery);

  public abstract Integer getPortalcardCount(PortalcardQuery paramPortalcardQuery);

  public abstract Pagination getPortalcardSaleListWithPage(PortalcardQuery paramPortalcardQuery);

  public abstract List<Portalcard> getPortalcardSaleList(PortalcardQuery paramPortalcardQuery);

  public abstract Integer getPortalcardSaleCount(PortalcardQuery paramPortalcardQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalcardService
 * JD-Core Version:    0.6.2
 */