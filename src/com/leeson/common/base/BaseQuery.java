package com.leeson.common.base;

import java.io.Serializable;
import java.util.Date;

public class BaseQuery
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final int DEFAULT_SIZE = 10;
  protected int pageSize = 10;
  protected int startRow;
  protected int endRow;
  protected int page = 1;
  protected String fields;
  protected Date begin_time;
  protected Date end_time;
  protected Date begin_time1;
  protected Date end_time1;

  public Date getBegin_time1()
  {
    return this.begin_time1;
  }
  public void setBegin_time1(Date begin_time1) {
    this.begin_time1 = begin_time1;
  }
  public Date getEnd_time1() {
    return this.end_time1;
  }
  public void setEnd_time1(Date end_time1) {
    this.end_time1 = end_time1;
  }
  public Date getBegin_time() {
    return this.begin_time;
  }
  public void setBegin_time(Date begin_time) {
    this.begin_time = begin_time;
  }
  public Date getEnd_time() {
    return this.end_time;
  }
  public void setEnd_time(Date end_time) {
    this.end_time = end_time;
  }

  public BaseQuery() {
  }

  public BaseQuery(int page, int pageSize) {
    this.page = page;
    this.pageSize = pageSize;
    this.startRow = ((page - 1) * this.pageSize);
    this.endRow = (this.startRow + this.pageSize - 1);
  }

  public int getStartRow() {
    return this.startRow;
  }
  public BaseQuery setStartRow(int startRow) {
    this.startRow = startRow;
    return this;
  }
  public int getEndRow() {
    return this.endRow;
  }
  public BaseQuery setEndRow(int endRow) {
    this.endRow = endRow;
    return this;
  }
  public BaseQuery setPage(int page) {
    this.page = page;
    this.startRow = ((page - 1) * this.pageSize);
    this.endRow = (this.startRow + this.pageSize - 1);
    return this;
  }
  public int getPageSize() {
    return this.pageSize;
  }
  public BaseQuery setPageSize(int pageSize) {
    this.pageSize = pageSize;
    if ((pageSize != 10) && (this.page > 0)) {
      this.startRow = ((this.page - 1) * this.pageSize);
      this.endRow = (this.startRow + this.pageSize - 1);
    }
    return this;
  }
  public int getPage() {
    return this.page;
  }

  public String getFields() {
    return this.fields;
  }
  public void setFields(String fields) {
    this.fields = fields;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.base.BaseQuery
 * JD-Core Version:    0.6.2
 */