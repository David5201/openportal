package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.query.PortalbasauthQuery;
import java.util.List;

public abstract interface PortalbasauthService
{
  public abstract Long addPortalbasauth(Portalbasauth paramPortalbasauth);

  public abstract Portalbasauth getPortalbasauthByKey(Long paramLong);

  public abstract List<Portalbasauth> getPortalbasauthByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalbasauthQuery paramPortalbasauthQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer updatePortalbasauthByKey(Portalbasauth paramPortalbasauth);

  public abstract Integer updatePortalbasauthByKeyAll(Portalbasauth paramPortalbasauth);

  public abstract Pagination getPortalbasauthListWithPage(PortalbasauthQuery paramPortalbasauthQuery);

  public abstract List<Portalbasauth> getPortalbasauthList(PortalbasauthQuery paramPortalbasauthQuery);

  public abstract Integer getPortalbasauthCount(PortalbasauthQuery paramPortalbasauthQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalbasauthService
 * JD-Core Version:    0.6.2
 */