package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortaluserroleQuery extends BaseQuery
{
  private Long roleId;
  private Long userId;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getRoleId()
  {
    return this.roleId;
  }
  public PortaluserroleQuery setRoleId(Long roleId) {
    this.roleId = roleId;
    return this;
  }

  public Long getUserId() {
    return this.userId;
  }
  public PortaluserroleQuery setUserId(Long userId) {
    this.userId = userId;
    return this;
  }

  public PortaluserroleQuery orderbyRoleId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("roleId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserroleQuery orderbyUserId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("userId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("roleId", "roleId");
      fieldMap.put("userId", "userId");
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
 * Qualified Name:     com.leeson.core.query.PortaluserroleQuery
 * JD-Core Version:    0.6.2
 */