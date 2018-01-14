package com.leeson.common.data;

public class Field
{
  private String name;
  private String fieldType;
  private String fieldDefault;
  private String fieldProperty;
  private String comment;
  private String nullable;
  private String extra;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFieldType() {
    return this.fieldType;
  }

  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }

  public String getFieldDefault() {
    return this.fieldDefault;
  }

  public void setFieldDefault(String fieldDefault) {
    this.fieldDefault = fieldDefault;
  }

  public String getFieldProperty() {
    return this.fieldProperty;
  }

  public void setFieldProperty(String fieldProperty) {
    this.fieldProperty = fieldProperty;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getNullable() {
    return this.nullable;
  }

  public void setNullable(String nullable) {
    this.nullable = nullable;
  }

  public String getExtra() {
    return this.extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public String toString()
  {
    return "Field [name=" + this.name + ", fieldType=" + this.fieldType + 
      ", fieldDefault=" + this.fieldDefault + ", fieldProperty=" + 
      this.fieldProperty + ", comment=" + this.comment + ", nullable=" + 
      this.nullable + ", extra=" + this.extra + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.Field
 * JD-Core Version:    0.6.2
 */