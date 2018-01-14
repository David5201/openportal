package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advbanner;
import com.leeson.core.query.AdvbannerQuery;
import java.util.List;

public abstract interface AdvbannerService
{
  public abstract Long addAdvbanner(Advbanner paramAdvbanner);

  public abstract Advbanner getAdvbannerByKey(Long paramLong);

  public abstract List<Advbanner> getAdvbannerByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(AdvbannerQuery paramAdvbannerQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateAdvbannerByKey(Advbanner paramAdvbanner);

  public abstract Integer updateAdvbannerByKeyAll(Advbanner paramAdvbanner);

  public abstract Pagination getAdvbannerListWithPage(AdvbannerQuery paramAdvbannerQuery);

  public abstract List<Advbanner> getAdvbannerList(AdvbannerQuery paramAdvbannerQuery);

  public abstract Integer getAdvbannerCount(AdvbannerQuery paramAdvbannerQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.AdvbannerService
 * JD-Core Version:    0.6.2
 */