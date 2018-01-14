package com.leeson.common.data;

import java.util.List;

public abstract interface DataDao
{
  public abstract List<String> listDBs();

  public abstract List<Table> listTables();

  public abstract List<Field> listFields(String paramString);

  public abstract List<Constraints> listConstraints(String paramString);

  public abstract Table findTable(String paramString);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.DataDao
 * JD-Core Version:    0.6.2
 */