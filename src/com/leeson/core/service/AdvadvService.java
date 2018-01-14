package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advadv;
import com.leeson.core.query.AdvadvQuery;
import java.util.List;

public  interface AdvadvService
{
  public  Long addAdvadv(Advadv paramAdvadv);
  public  Advadv getAdvadvByKey(Long paramLong);
  public  List<Advadv> getAdvadvByKeys(List<Long> paramList);
  public  Integer deleteByKey(Long paramLong);
  public  Integer deleteByQuery(AdvadvQuery paramAdvadvQuery);
  public  Integer deleteByKeys(List<Long> paramList);
  public  Integer deleteAll();
  public  Integer updateAdvadvByKey(Advadv paramAdvadv);
  public  Integer updateAdvadvByKeyAll(Advadv paramAdvadv);
  public  Pagination getAdvadvListWithPage(AdvadvQuery paramAdvadvQuery);
  public  List<Advadv> getAdvadvList(AdvadvQuery paramAdvadvQuery);
  public  Integer getAdvadvCount(AdvadvQuery paramAdvadvQuery);
}