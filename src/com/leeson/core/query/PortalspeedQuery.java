package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalspeedQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private Long up;
  private Long down;
  private Long mup;
  private Long mdown;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalspeedQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalspeedQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalspeedQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public Long getUp() {
    return this.up;
  }
  public PortalspeedQuery setUp(Long up) {
    this.up = up;
    return this;
  }

  public Long getDown() {
    return this.down;
  }
  public PortalspeedQuery setDown(Long down) {
    this.down = down;
    return this;
  }

  public Long getMup() {
    return this.mup;
  }
  public PortalspeedQuery setMup(Long mup) {
    this.mup = mup;
    return this;
  }

  public Long getMdown() {
    return this.mdown;
  }
  public PortalspeedQuery setMdown(Long mdown) {
    this.mdown = mdown;
    return this;
  }

  public PortalspeedQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalspeedQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalspeedQuery orderbyUp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("up", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalspeedQuery orderbyDown(boolean isAsc)
  {
    this.orderFields.add(new OrderField("down", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalspeedQuery orderbyMup(boolean isAsc)
  {
    this.orderFields.add(new OrderField("mup", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalspeedQuery orderbyMdown(boolean isAsc)
  {
    this.orderFields.add(new OrderField("mdown", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("up", "up");
      fieldMap.put("down", "down");
      fieldMap.put("mup", "mup");
      fieldMap.put("mdown", "mdown");
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
 * Qualified Name:     com.leeson.core.query.PortalspeedQuery
 * JD-Core Version:    0.6.2
 */