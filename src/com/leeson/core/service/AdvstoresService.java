package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advstores;
import com.leeson.core.query.AdvstoresQuery;
import java.util.List;

public abstract interface AdvstoresService
{
  public abstract Long addAdvstores(Advstores paramAdvstores);

  public abstract Advstores getAdvstoresByKey(Long paramLong);

  public abstract List<Advstores> getAdvstoresByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(AdvstoresQuery paramAdvstoresQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateAdvstoresByKey(Advstores paramAdvstores);

  public abstract Integer updateAdvstoresByKeyAll(Advstores paramAdvstores);

  public abstract Pagination getAdvstoresListWithPage(AdvstoresQuery paramAdvstoresQuery);

  public abstract List<Advstores> getAdvstoresList(AdvstoresQuery paramAdvstoresQuery);

  public abstract Integer getAdvstoresCount(AdvstoresQuery paramAdvstoresQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.AdvstoresService
 * JD-Core Version:    0.6.2
 */