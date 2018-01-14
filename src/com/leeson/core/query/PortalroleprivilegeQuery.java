package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalroleprivilegeQuery extends BaseQuery
{
  private Long privilegeId;
  private Long roleId;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getPrivilegeId()
  {
    return this.privilegeId;
  }
  public PortalroleprivilegeQuery setPrivilegeId(Long privilegeId) {
    this.privilegeId = privilegeId;
    return this;
  }

  public Long getRoleId() {
    return this.roleId;
  }
  public PortalroleprivilegeQuery setRoleId(Long roleId) {
    this.roleId = roleId;
    return this;
  }

  public PortalroleprivilegeQuery orderbyPrivilegeId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("privilegeId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalroleprivilegeQuery orderbyRoleId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("roleId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("privilegeId", "privilegeId");
      fieldMap.put("roleId", "roleId");
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
 * Qualified Name:     com.leeson.core.query.PortalroleprivilegeQuery
 * JD-Core Version:    0.6.2
 */