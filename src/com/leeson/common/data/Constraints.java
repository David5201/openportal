package com.leeson.common.data;

public class Constraints
{
  private String name;
  private String tableName;
  private String columnName;
  private String referencedTableName;
  private String referencedColumnName;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getColumnName() {
    return this.columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getReferencedTableName() {
    return this.referencedTableName;
  }

  public void setReferencedTableName(String referencedTableName) {
    this.referencedTableName = referencedTableName;
  }

  public String getReferencedColumnName() {
    return this.referencedColumnName;
  }

  public void setReferencedColumnName(String referencedColumnName) {
    this.referencedColumnName = referencedColumnName;
  }

  public String toString()
  {
    return "Constraints [name=" + this.name + ", tableName=" + this.tableName + 
      ", columnName=" + this.columnName + ", referencedTableName=" + 
      this.referencedTableName + ", referencedColumnName=" + 
      this.referencedColumnName + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.Constraints
 * JD-Core Version:    0.6.2
 */