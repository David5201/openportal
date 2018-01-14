package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalsmslogsQuery extends BaseQuery
{
  private Long id;
  private String info;
  private boolean infoLike;
  private Date sendDate;
  private String phone;
  private boolean phoneLike;
  private Long sid;
  private String type;
  private boolean typeLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalsmslogsQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getInfo() {
    return this.info;
  }
  public PortalsmslogsQuery setInfo(String info) {
    this.info = info;
    return this;
  }

  public PortalsmslogsQuery setInfoLike(boolean isLike) {
    this.infoLike = isLike;
    return this;
  }

  public Date getSendDate() {
    return this.sendDate;
  }
  public PortalsmslogsQuery setSendDate(Date sendDate) {
    this.sendDate = sendDate;
    return this;
  }

  public String getPhone() {
    return this.phone;
  }
  public PortalsmslogsQuery setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public PortalsmslogsQuery setPhoneLike(boolean isLike) {
    this.phoneLike = isLike;
    return this;
  }

  public Long getSid() {
    return this.sid;
  }
  public PortalsmslogsQuery setSid(Long sid) {
    this.sid = sid;
    return this;
  }

  public String getType() {
    return this.type;
  }
  public PortalsmslogsQuery setType(String type) {
    this.type = type;
    return this;
  }

  public PortalsmslogsQuery setTypeLike(boolean isLike) {
    this.typeLike = isLike;
    return this;
  }

  public PortalsmslogsQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmslogsQuery orderbyInfo(boolean isAsc)
  {
    this.orderFields.add(new OrderField("info", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmslogsQuery orderbySendDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("sendDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmslogsQuery orderbyPhone(boolean isAsc)
  {
    this.orderFields.add(new OrderField("phone", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmslogsQuery orderbySid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("sid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmslogsQuery orderbyType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("info", "info");
      fieldMap.put("sendDate", "sendDate");
      fieldMap.put("phone", "phone");
      fieldMap.put("sid", "sid");
      fieldMap.put("type", "type");
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
 * Qualified Name:     com.leeson.core.query.PortalsmslogsQuery
 * JD-Core Version:    0.6.2
 */