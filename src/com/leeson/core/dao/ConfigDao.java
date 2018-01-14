package com.leeson.core.dao;

import com.leeson.core.bean.Config;
import com.leeson.core.query.ConfigQuery;
import java.sql.SQLException;
import java.util.List;

public abstract interface ConfigDao
{
  public abstract Long addConfig(Config paramConfig)
    throws SQLException;

  public abstract Config getConfigByKey(Long paramLong)
    throws SQLException;

  public abstract List<Config> getConfigByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteByKey(Long paramLong)
    throws SQLException;

  public abstract Integer deleteByQuery(ConfigQuery paramConfigQuery)
    throws SQLException;

  public abstract Integer deleteByKeys(List<Long> paramList)
    throws SQLException;

  public abstract Integer deleteAll()
    throws SQLException;

  public abstract Integer updateConfigByKey(Config paramConfig)
    throws SQLException;

  public abstract Integer updateConfigByKeyAll(Config paramConfig)
    throws SQLException;

  public abstract List<Config> getConfigListWithPage(ConfigQuery paramConfigQuery)
    throws SQLException;

  public abstract List<Config> getConfigList(ConfigQuery paramConfigQuery)
    throws SQLException;

  public abstract Integer getConfigListCount(ConfigQuery paramConfigQuery)
    throws SQLException;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.dao.ConfigDao
 * JD-Core Version:    0.6.2
 */