package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalaccountmacsQuery extends BaseQuery
{
  private Long id;
  private Long accountId;
  private String mac;
  private boolean macLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalaccountmacsQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getAccountId() {
    return this.accountId;
  }
  public PortalaccountmacsQuery setAccountId(Long accountId) {
    this.accountId = accountId;
    return this;
  }

  public String getMac() {
    return this.mac;
  }
  public PortalaccountmacsQuery setMac(String mac) {
    this.mac = mac;
    return this;
  }

  public PortalaccountmacsQuery setMacLike(boolean isLike) {
    this.macLike = isLike;
    return this;
  }

  public PortalaccountmacsQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountmacsQuery orderbyAccountId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountmacsQuery orderbyMac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("mac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("accountId", "accountId");
      fieldMap.put("mac", "mac");
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
 * Qualified Name:     com.leeson.core.query.PortalaccountmacsQuery
 * JD-Core Version:    0.6.2
 */