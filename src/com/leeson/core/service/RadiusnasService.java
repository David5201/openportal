package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Radiusnas;
import com.leeson.core.query.RadiusnasQuery;
import java.util.List;

public abstract interface RadiusnasService
{
  public abstract Long addRadiusnas(Radiusnas paramRadiusnas);

  public abstract Radiusnas getRadiusnasByKey(Long paramLong);

  public abstract List<Radiusnas> getRadiusnasByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(RadiusnasQuery paramRadiusnasQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateRadiusnasByKey(Radiusnas paramRadiusnas);

  public abstract Integer updateRadiusnasByKeyAll(Radiusnas paramRadiusnas);

  public abstract Pagination getRadiusnasListWithPage(RadiusnasQuery paramRadiusnasQuery);

  public abstract List<Radiusnas> getRadiusnasList(RadiusnasQuery paramRadiusnasQuery);

  public abstract Integer getRadiusnasCount(RadiusnasQuery paramRadiusnasQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.RadiusnasService
 * JD-Core Version:    0.6.2
 */