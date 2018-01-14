package com.leeson.core.service;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Config;
import com.leeson.core.query.ConfigQuery;
import java.util.List;

public abstract interface ConfigService
{
  public abstract Long addConfig(Config paramConfig);

  public abstract Config getConfigByKey(Long paramLong);

  public abstract List<Config> getConfigByKeys(List<Long> paramList);

  public abstract Integer deleteByKey(Long paramLong);

  public abstract Integer deleteByQuery(ConfigQuery paramConfigQuery);

  public abstract Integer deleteByKeys(List<Long> paramList);

  public abstract Integer deleteAll();

  public abstract Integer updateConfigByKey(Config paramConfig);

  public abstract Integer updateConfigByKeyAll(Config paramConfig);

  public abstract Pagination getConfigListWithPage(ConfigQuery paramConfigQuery);

  public abstract List<Config> getConfigList(ConfigQuery paramConfigQuery);

  public abstract Integer getConfigCount(ConfigQuery paramConfigQuery);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.ConfigService
 * JD-Core Version:    0.6.2
 */