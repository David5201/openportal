package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Radiuslinkrecordallout;
import com.leeson.core.query.RadiuslinkrecordalloutQuery;
import java.util.List;

public abstract interface RadiuslinkrecordalloutService
{
  public abstract Long addRadiuslinkrecordallout(Radiuslinkrecordallout paramRadiuslinkrecordallout);

  public abstract Radiuslinkrecordallout getRadiuslinkrecordalloutByKey(Long paramLong);

  public abstract List<Radiuslinkrecordallout> getRadiuslinkrecordalloutByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateRadiuslinkrecordalloutByKey(Radiuslinkrecordallout paramRadiuslinkrecordallout);

  public abstract Integer updateRadiuslinkrecordalloutByKeyAll(Radiuslinkrecordallout paramRadiuslinkrecordallout);

  public abstract Pagination getRadiuslinkrecordalloutListWithPage(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery);

  public abstract List<Radiuslinkrecordallout> getRadiuslinkrecordalloutList(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery);

  public abstract Integer getRadiuslinkrecordalloutCount(RadiuslinkrecordalloutQuery paramRadiuslinkrecordalloutQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.RadiuslinkrecordalloutService
 * JD-Core Version:    0.6.2
 */