package com.leeson.common.page;

public abstract class SimplePage
  implements Paginable
{
  private static final long serialVersionUID = 1L;
  public static final int DEF_COUNT = 10;
  protected int totalCount = 0;
  protected int pageSize = 10;
  protected int pageNo = 1;
  private int beginPageIndex;
  private int endPageIndex;

  public static int cpn(Integer pageNo)
  {
    return (pageNo == null) || (pageNo.intValue() < 1) ? 1 : pageNo.intValue();
  }

  public SimplePage()
  {
  }

  public SimplePage(int pageNo, int pageSize, int totalCount)
  {
    setTotalCount(totalCount);
    setPageSize(pageSize);
    setPageNo(pageNo);
    adjustPageNo();
  }

  public void adjustPageNo()
  {
    if (this.pageNo == 1) {
      return;
    }
    int tp = getTotalPage();
    if (this.pageNo > tp)
      this.pageNo = tp;
  }

  public int getPageNo()
  {
    return this.pageNo;
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public int getTotalCount()
  {
    return this.totalCount;
  }

  public int getTotalPage()
  {
    int totalPage = this.totalCount / this.pageSize;
    if ((totalPage == 0) || (this.totalCount % this.pageSize != 0)) {
      totalPage++;
    }
    return totalPage;
  }

  public boolean isFirstPage()
  {
    return this.pageNo <= 1;
  }

  public boolean isLastPage()
  {
    return this.pageNo >= getTotalPage();
  }

  public int getNextPage()
  {
    if (isLastPage()) {
      return this.pageNo;
    }
    return this.pageNo + 1;
  }

  public int getPrePage()
  {
    if (isFirstPage()) {
      return this.pageNo;
    }
    return this.pageNo - 1;
  }

  public void setTotalCount(int totalCount)
  {
    if (totalCount < 0)
      this.totalCount = 0;
    else
      this.totalCount = totalCount;
  }

  public void setPageSize(int pageSize)
  {
    if (pageSize < 1)
      this.pageSize = 10;
    else
      this.pageSize = pageSize;
  }

  public void setPageNo(int pageNo)
  {
    if (pageNo < 1)
      this.pageNo = 1;
    else {
      this.pageNo = pageNo;
    }
    setBE();
  }

  private void setBE()
  {
    if (getTotalPage() <= 10) {
      this.beginPageIndex = 1;
      this.endPageIndex = getTotalPage();
    }
    else
    {
      this.beginPageIndex = (this.pageNo - 4);
      this.endPageIndex = (this.pageNo + 5);

      if (this.beginPageIndex < 1) {
        this.beginPageIndex = 1;
        this.endPageIndex = 10;
      }

      if (this.endPageIndex > getTotalPage()) {
        this.endPageIndex = getTotalPage();
        this.beginPageIndex = (getTotalPage() - 10 + 1);
      }
    }
  }

  public int getBeginPageIndex() {
    return this.beginPageIndex;
  }

  public int getEndPageIndex()
  {
    return this.endPageIndex;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.page.SimplePage
 * JD-Core Version:    0.6.2
 */