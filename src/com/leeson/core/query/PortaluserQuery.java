package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortaluserQuery extends BaseQuery
{
  private Long id;
  private String loginName;
  private boolean loginNameLike;
  private String password;
  private boolean passwordLike;
  private String name;
  private boolean nameLike;
  private String gender;
  private boolean genderLike;
  private String phoneNumber;
  private boolean phoneNumberLike;
  private String email;
  private boolean emailLike;
  private String description;
  private boolean descriptionLike;
  private Long departmentId;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortaluserQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getLoginName() {
    return this.loginName;
  }
  public PortaluserQuery setLoginName(String loginName) {
    this.loginName = loginName;
    return this;
  }

  public PortaluserQuery setLoginNameLike(boolean isLike) {
    this.loginNameLike = isLike;
    return this;
  }

  public String getPassword() {
    return this.password;
  }
  public PortaluserQuery setPassword(String password) {
    this.password = password;
    return this;
  }

  public PortaluserQuery setPasswordLike(boolean isLike) {
    this.passwordLike = isLike;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortaluserQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortaluserQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getGender() {
    return this.gender;
  }
  public PortaluserQuery setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public PortaluserQuery setGenderLike(boolean isLike) {
    this.genderLike = isLike;
    return this;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  public PortaluserQuery setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public PortaluserQuery setPhoneNumberLike(boolean isLike) {
    this.phoneNumberLike = isLike;
    return this;
  }

  public String getEmail() {
    return this.email;
  }
  public PortaluserQuery setEmail(String email) {
    this.email = email;
    return this;
  }

  public PortaluserQuery setEmailLike(boolean isLike) {
    this.emailLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortaluserQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortaluserQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Long getDepartmentId() {
    return this.departmentId;
  }
  public PortaluserQuery setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
    return this;
  }

  public PortaluserQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyLoginName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyPassword(boolean isAsc)
  {
    this.orderFields.add(new OrderField("password", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyGender(boolean isAsc)
  {
    this.orderFields.add(new OrderField("gender", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyPhoneNumber(boolean isAsc)
  {
    this.orderFields.add(new OrderField("phoneNumber", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyEmail(boolean isAsc)
  {
    this.orderFields.add(new OrderField("email", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortaluserQuery orderbyDepartmentId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("departmentId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("loginName", "loginName");
      fieldMap.put("password", "password");
      fieldMap.put("name", "name");
      fieldMap.put("gender", "gender");
      fieldMap.put("phoneNumber", "phoneNumber");
      fieldMap.put("email", "email");
      fieldMap.put("description", "description");
      fieldMap.put("departmentId", "departmentId");
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
 * Qualified Name:     com.leeson.core.query.PortaluserQuery
 * JD-Core Version:    0.6.2
 */