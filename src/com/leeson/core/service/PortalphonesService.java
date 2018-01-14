package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalphones;
import com.leeson.core.query.PortalphonesQuery;
import java.util.List;

public abstract interface PortalphonesService
{
  public abstract Long addPortalphones(Portalphones paramPortalphones);

  public abstract Portalphones getPortalphonesByKey(Long paramLong);

  public abstract List<Portalphones> getPortalphonesByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalphonesQuery paramPortalphonesQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalphonesByKey(Portalphones paramPortalphones);

  public abstract Integer updatePortalphonesByKeyAll(Portalphones paramPortalphones);

  public abstract Pagination getPortalphonesListWithPage(PortalphonesQuery paramPortalphonesQuery);

  public abstract List<Portalphones> getPortalphonesList(PortalphonesQuery paramPortalphonesQuery);

  public abstract Integer getPortalphonesCount(PortalphonesQuery paramPortalphonesQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalphonesService
 * JD-Core Version:    0.6.2
 */