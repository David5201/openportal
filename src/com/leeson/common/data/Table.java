package com.leeson.common.data;

public class Table
{
  private String name;
  private String comment;
  private String engine;
  private Integer rows;
  private Integer auto_increment;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getEngine() {
    return this.engine;
  }

  public void setEngine(String engine) {
    this.engine = engine;
  }

  public Integer getRows() {
    return this.rows;
  }

  public void setRows(Integer rows) {
    this.rows = rows;
  }

  public Integer getAuto_increment() {
    return this.auto_increment;
  }

  public void setAuto_increment(Integer auto_increment) {
    this.auto_increment = auto_increment;
  }

  public String toString()
  {
    return "Table [name=" + this.name + ", comment=" + this.comment + ", engine=" + 
      this.engine + ", rows=" + this.rows + ", auto_increment=" + 
      this.auto_increment + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.Table
 * JD-Core Version:    0.6.2
 */