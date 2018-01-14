package com.leeson.common.page;

public abstract interface Paginable
{
  public abstract int getTotalCount();

  public abstract int getTotalPage();

  public abstract int getPageSize();

  public abstract int getPageNo();

  public abstract boolean isFirstPage();

  public abstract boolean isLastPage();

  public abstract int getNextPage();

  public abstract int getPrePage();
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.page.Paginable
 * JD-Core Version:    0.6.2
 */