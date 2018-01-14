package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.query.PortalautologinQuery;
import java.util.List;

public abstract interface PortalautologinService
{
  public abstract Long addPortalautologin(Portalautologin paramPortalautologin);

  public abstract Portalautologin getPortalautologinByKey(Long paramLong);

  public abstract List<Portalautologin> getPortalautologinByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalautologinQuery paramPortalautologinQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalautologinByKey(Portalautologin paramPortalautologin);

  public abstract Integer updatePortalautologinByKeyAll(Portalautologin paramPortalautologin);

  public abstract Pagination getPortalautologinListWithPage(PortalautologinQuery paramPortalautologinQuery);

  public abstract List<Portalautologin> getPortalautologinList(PortalautologinQuery paramPortalautologinQuery);

  public abstract Integer getPortalautologinCount(PortalautologinQuery paramPortalautologinQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalautologinService
 * JD-Core Version:    0.6.2
 */