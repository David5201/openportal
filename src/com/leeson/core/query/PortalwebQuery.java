package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalwebQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private Long countShow;
  private Long countAuth;
  private Long adv;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalwebQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalwebQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalwebQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalwebQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalwebQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Long getCountShow() {
    return this.countShow;
  }
  public PortalwebQuery setCountShow(Long countShow) {
    this.countShow = countShow;
    return this;
  }

  public Long getCountAuth() {
    return this.countAuth;
  }
  public PortalwebQuery setCountAuth(Long countAuth) {
    this.countAuth = countAuth;
    return this;
  }

  public Long getAdv() {
    return this.adv;
  }
  public PortalwebQuery setAdv(Long adv) {
    this.adv = adv;
    return this;
  }

  public PortalwebQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalwebQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalwebQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalwebQuery orderbyCountShow(boolean isAsc)
  {
    this.orderFields.add(new OrderField("countShow", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalwebQuery orderbyCountAuth(boolean isAsc)
  {
    this.orderFields.add(new OrderField("countAuth", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalwebQuery orderbyAdv(boolean isAsc)
  {
    this.orderFields.add(new OrderField("adv", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("description", "description");
      fieldMap.put("countShow", "countShow");
      fieldMap.put("countAuth", "countAuth");
      fieldMap.put("adv", "adv");
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
 * Qualified Name:     com.leeson.core.query.PortalwebQuery
 * JD-Core Version:    0.6.2
 */