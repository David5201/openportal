package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advpic;
import com.leeson.core.query.AdvpicQuery;
import java.util.List;

public abstract interface AdvpicService
{
  public abstract Long addAdvpic(Advpic paramAdvpic);

  public abstract Advpic getAdvpicByKey(Long paramLong);

  public abstract List<Advpic> getAdvpicByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(AdvpicQuery paramAdvpicQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateAdvpicByKey(Advpic paramAdvpic);

  public abstract Integer updateAdvpicByKeyAll(Advpic paramAdvpic);

  public abstract Pagination getAdvpicListWithPage(AdvpicQuery paramAdvpicQuery);

  public abstract List<Advpic> getAdvpicList(AdvpicQuery paramAdvpicQuery);

  public abstract Integer getAdvpicCount(AdvpicQuery paramAdvpicQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.AdvpicService
 * JD-Core Version:    0.6.2
 */