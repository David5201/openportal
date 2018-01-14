package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalphonesQuery extends BaseQuery
{
  private Long id;
  private Long did;
  private Long uid;
  private String name;
  private boolean nameLike;
  private String phone;
  private boolean phoneLike;
  private String description;
  private boolean descriptionLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalphonesQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getDid() {
    return this.did;
  }
  public PortalphonesQuery setDid(Long did) {
    this.did = did;
    return this;
  }

  public Long getUid() {
    return this.uid;
  }
  public PortalphonesQuery setUid(Long uid) {
    this.uid = uid;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalphonesQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalphonesQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getPhone() {
    return this.phone;
  }
  public PortalphonesQuery setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public PortalphonesQuery setPhoneLike(boolean isLike) {
    this.phoneLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalphonesQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalphonesQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public PortalphonesQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalphonesQuery orderbyDid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("did", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalphonesQuery orderbyUid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalphonesQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalphonesQuery orderbyPhone(boolean isAsc)
  {
    this.orderFields.add(new OrderField("phone", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalphonesQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("did", "did");
      fieldMap.put("uid", "uid");
      fieldMap.put("name", "name");
      fieldMap.put("phone", "phone");
      fieldMap.put("description", "description");
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
 * Qualified Name:     com.leeson.core.query.PortalphonesQuery
 * JD-Core Version:    0.6.2
 */