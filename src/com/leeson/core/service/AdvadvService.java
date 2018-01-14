package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advadv;
import com.leeson.core.query.AdvadvQuery;
import java.util.List;

public abstract interface AdvadvService
{
  public abstract Long addAdvadv(Advadv paramAdvadv);

  public abstract Advadv getAdvadvByKey(Long paramLong);

  public abstract List<Advadv> getAdvadvByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(AdvadvQuery paramAdvadvQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateAdvadvByKey(Advadv paramAdvadv);

  public abstract Integer updateAdvadvByKeyAll(Advadv paramAdvadv);

  public abstract Pagination getAdvadvListWithPage(AdvadvQuery paramAdvadvQuery);

  public abstract List<Advadv> getAdvadvList(AdvadvQuery paramAdvadvQuery);

  public abstract Integer getAdvadvCount(AdvadvQuery paramAdvadvQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.AdvadvService
 * JD-Core Version:    0.6.2
 */