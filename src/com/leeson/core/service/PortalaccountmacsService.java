package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.query.PortalaccountmacsQuery;
import java.util.List;

public abstract interface PortalaccountmacsService
{
  public abstract Long addPortalaccountmacs(Portalaccountmacs paramPortalaccountmacs);

  public abstract Portalaccountmacs getPortalaccountmacsByKey(Long paramLong);

  public abstract List<Portalaccountmacs> getPortalaccountmacsByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalaccountmacsQuery paramPortalaccountmacsQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalaccountmacsByKey(Portalaccountmacs paramPortalaccountmacs);

  public abstract Integer updatePortalaccountmacsByKeyAll(Portalaccountmacs paramPortalaccountmacs);

  public abstract Pagination getPortalaccountmacsListWithPage(PortalaccountmacsQuery paramPortalaccountmacsQuery);

  public abstract List<Portalaccountmacs> getPortalaccountmacsList(PortalaccountmacsQuery paramPortalaccountmacsQuery);

  public abstract Integer getPortalaccountmacsCount(PortalaccountmacsQuery paramPortalaccountmacsQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalaccountmacsService
 * JD-Core Version:    0.6.2
 */