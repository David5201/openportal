package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalcardcategory;
import com.leeson.core.query.PortalcardcategoryQuery;
import java.util.List;

public abstract interface PortalcardcategoryService
{
  public abstract Long addPortalcardcategory(Portalcardcategory paramPortalcardcategory);

  public abstract Portalcardcategory getPortalcardcategoryByKey(Long paramLong);

  public abstract List<Portalcardcategory> getPortalcardcategoryByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(PortalcardcategoryQuery paramPortalcardcategoryQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updatePortalcardcategoryByKey(Portalcardcategory paramPortalcardcategory);

  public abstract Integer updatePortalcardcategoryByKeyAll(Portalcardcategory paramPortalcardcategory);

  public abstract Pagination getPortalcardcategoryListWithPage(PortalcardcategoryQuery paramPortalcardcategoryQuery);

  public abstract List<Portalcardcategory> getPortalcardcategoryList(PortalcardcategoryQuery paramPortalcardcategoryQuery);

  public abstract Integer getPortalcardcategoryCount(PortalcardcategoryQuery paramPortalcardcategoryQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.PortalcardcategoryService
 * JD-Core Version:    0.6.2
 */