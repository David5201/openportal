package com.leeson.common.page;

import java.io.Serializable;
import java.util.List;

public class Pagination extends SimplePage
  implements Serializable, Paginable
{
  private List<?> list;

  public Pagination()
  {
  }

  public Pagination(int pageNo, int pageSize, int totalCount)
  {
    super(pageNo, pageSize, totalCount);
  }

  public Pagination(int pageNo, int pageSize, int totalCount, List<?> list)
  {
    super(pageNo, pageSize, totalCount);
    this.list = list;
  }

  public int getFirstResult()
  {
    return (this.pageNo - 1) * this.pageSize;
  }

  public List<?> getList()
  {
    return this.list;
  }

  public void setList(List list)
  {
    this.list = list;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.page.Pagination
 * JD-Core Version:    0.6.2
 */