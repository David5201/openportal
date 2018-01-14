package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadiuslinkrecordalloutQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private Date creatDate;
  private String url;
  private boolean urlLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public RadiuslinkrecordalloutQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public RadiuslinkrecordalloutQuery setName(String name) {
    this.name = name;
    return this;
  }

  public RadiuslinkrecordalloutQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public Date getCreatDate() {
    return this.creatDate;
  }
  public RadiuslinkrecordalloutQuery setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
    return this;
  }

  public String getUrl() {
    return this.url;
  }
  public RadiuslinkrecordalloutQuery setUrl(String url) {
    this.url = url;
    return this;
  }

  public RadiuslinkrecordalloutQuery setUrlLike(boolean isLike) {
    this.urlLike = isLike;
    return this;
  }

  public RadiuslinkrecordalloutQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordalloutQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordalloutQuery orderbyCreatDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordalloutQuery orderbyUrl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("creatDate", "creatDate");
      fieldMap.put("url", "url");
    }
    return fieldMap;
  }

  public String getFields() {
    return this.fields;
  }
  public void setFields(String fields) {
    if (fields == null)
      return;
    String[] array = fields.split(",");
    StringBuffer buffer = new StringBuffer();
    for (String field : array) {
      if (getFieldSet().containsKey(field)) {
        buffer.append((String)getFieldSet().get(field)).append(" as ")
          .append(field).append(" ,");
      }
      if (getFieldSet().containsKey("`" + field + "`")) {
        buffer.append("`" + (String)getFieldSet().get(field) + "`").append(" as ")
          .append(field).append(" ,");
      }
    }
    if (buffer.length() != 0)
      this.fields = buffer.substring(0, buffer.length() - 1);
    else
      this.fields = " 1 ";
  }

  public class OrderField
  {
    private String fieldName;
    private String order;

    public OrderField(String fieldName, String order)
    {
      this.fieldName = fieldName;
      this.order = order;
    }

    public String getFieldName()
    {
      return this.fieldName;
    }
    public OrderField setFieldName(String fieldName) {
      this.fieldName = fieldName;
      return this;
    }
    public String getOrder() {
      return this.order;
    }
    public OrderField setOrder(String order) {
      this.order = order;
      return this;
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.query.RadiuslinkrecordalloutQuery
 * JD-Core Version:    0.6.2
 */