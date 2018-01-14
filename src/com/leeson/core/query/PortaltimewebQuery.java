package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortaltimewebQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private Date viewdate;
  private Integer viewweekday;
  private Integer viewdateday;
  private Integer viewmonth;
  private Long web;
  private Long count;
  private Long pos;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortaltimewebQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortaltimewebQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortaltimewebQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortaltimewebQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortaltimewebQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Date getViewdate() {
    return this.viewdate;
  }
  public PortaltimewebQuery setViewdate(Date viewdate) {
    this.viewdate = viewdate;
    return this;
  }

  public Integer getViewweekday() {
    return this.viewweekday;
  }
  public PortaltimewebQuery setViewweekday(Integer viewweekday) {
    this.viewweekday = viewweekday;
    return this;
  }

  public Integer getViewdateday() {
    return this.viewdateday;
  }
  public PortaltimewebQuery setViewdateday(Integer viewdateday) {
    this.viewdateday = viewdateday;
    return this;
  }

  public Integer getViewmonth() {
    return this.viewmonth;
  }
  public PortaltimewebQuery setViewmonth(Integer viewmonth) {
    this.viewmonth = viewmonth;
    return this;
  }

  public Long getWeb() {
    return this.web;
  }
  public PortaltimewebQuery setWeb(Long web) {
    this.web = web;
    return this;
  }

  public Long getCount() {
    return this.count;
  }
  public PortaltimewebQuery setCount(Long count) {
    this.count = count;
    return this;
  }

  public Long getPos() {
    return this.pos;
  }
  public PortaltimewebQuery setPos(Long pos) {
    this.pos = pos;
    return this;
  }

  public PortaltimewebQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyViewdate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("viewdate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyViewweekday(boolean isAsc)
  {
    this.orderFields.add(new OrderField("viewweekday", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyViewdateday(boolean isAsc)
  {
    this.orderFields.add(new OrderField("viewdateday", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyViewmonth(boolean isAsc)
  {
    this.orderFields.add(new OrderField("viewmonth", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyWeb(boolean isAsc)
  {
    this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaltimewebQuery orderbyPos(boolean isAsc)
  {
    this.orderFields.add(new OrderField("pos", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("description", "description");
      fieldMap.put("viewdate", "viewdate");
      fieldMap.put("viewweekday", "viewweekday");
      fieldMap.put("viewdateday", "viewdateday");
      fieldMap.put("viewmonth", "viewmonth");
      fieldMap.put("web", "web");
      fieldMap.put("count", "count");
      fieldMap.put("pos", "pos");
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
 * Qualified Name:     com.leeson.core.query.PortaltimewebQuery
 * JD-Core Version:    0.6.2
 */