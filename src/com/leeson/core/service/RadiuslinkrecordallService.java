package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Radiuslinkrecordall;
import com.leeson.core.query.RadiuslinkrecordallQuery;
import java.util.List;

public abstract interface RadiuslinkrecordallService
{
  public abstract Long addRadiuslinkrecordall(Radiuslinkrecordall paramRadiuslinkrecordall);

  public abstract Radiuslinkrecordall getRadiuslinkrecordallByKey(Long paramLong);

  public abstract List<Radiuslinkrecordall> getRadiuslinkrecordallByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateRadiuslinkrecordallByKey(Radiuslinkrecordall paramRadiuslinkrecordall);

  public abstract Integer updateRadiuslinkrecordallByKeyAll(Radiuslinkrecordall paramRadiuslinkrecordall);

  public abstract Pagination getRadiuslinkrecordallListWithPage(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery);

  public abstract List<Radiuslinkrecordall> getRadiuslinkrecordallList(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery);

  public abstract Integer getRadiuslinkrecordallCount(RadiuslinkrecordallQuery paramRadiuslinkrecordallQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.RadiuslinkrecordallService
 * JD-Core Version:    0.6.2
 */