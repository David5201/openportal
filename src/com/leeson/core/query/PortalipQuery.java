package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalipQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private String start;
  private boolean startLike;
  private String end;
  private boolean endLike;
  private Long web;
  private Long count;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalipQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalipQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalipQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalipQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalipQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public String getStart() {
    return this.start;
  }
  public PortalipQuery setStart(String start) {
    this.start = start;
    return this;
  }

  public PortalipQuery setStartLike(boolean isLike) {
    this.startLike = isLike;
    return this;
  }

  public String getEnd() {
    return this.end;
  }
  public PortalipQuery setEnd(String end) {
    this.end = end;
    return this;
  }

  public PortalipQuery setEndLike(boolean isLike) {
    this.endLike = isLike;
    return this;
  }

  public Long getWeb() {
    return this.web;
  }
  public PortalipQuery setWeb(Long web) {
    this.web = web;
    return this;
  }

  public Long getCount() {
    return this.count;
  }
  public PortalipQuery setCount(Long count) {
    this.count = count;
    return this;
  }

  public PortalipQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalipQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalipQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalipQuery orderbyStart(boolean isAsc)
  {
    this.orderFields.add(new OrderField("start", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalipQuery orderbyEnd(boolean isAsc)
  {
    this.orderFields.add(new OrderField("end", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalipQuery orderbyWeb(boolean isAsc)
  {
    this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalipQuery orderbyCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("description", "description");
      fieldMap.put("start", "start");
      fieldMap.put("end", "end");
      fieldMap.put("web", "web");
      fieldMap.put("count", "count");
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
 * Qualified Name:     com.leeson.core.query.PortalipQuery
 * JD-Core Version:    0.6.2
 */