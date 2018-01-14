package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalbasauthQuery extends BaseQuery
{
  private Long id;
  private Long basid;
  private Integer type;
  private String username;
  private boolean usernameLike;
  private String password;
  private boolean passwordLike;
  private String basip;
  private boolean basipLike;
  private String url;
  private boolean urlLike;
  private Long sessiontime;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalbasauthQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getBasid() {
    return this.basid;
  }
  public PortalbasauthQuery setBasid(Long basid) {
    this.basid = basid;
    return this;
  }

  public Integer getType() {
    return this.type;
  }
  public PortalbasauthQuery setType(Integer type) {
    this.type = type;
    return this;
  }

  public String getUsername() {
    return this.username;
  }
  public PortalbasauthQuery setUsername(String username) {
    this.username = username;
    return this;
  }

  public PortalbasauthQuery setUsernameLike(boolean isLike) {
    this.usernameLike = isLike;
    return this;
  }

  public String getPassword() {
    return this.password;
  }
  public PortalbasauthQuery setPassword(String password) {
    this.password = password;
    return this;
  }

  public PortalbasauthQuery setPasswordLike(boolean isLike) {
    this.passwordLike = isLike;
    return this;
  }

  public String getBasip() {
    return this.basip;
  }
  public PortalbasauthQuery setBasip(String basip) {
    this.basip = basip;
    return this;
  }

  public PortalbasauthQuery setBasipLike(boolean isLike) {
    this.basipLike = isLike;
    return this;
  }

  public String getUrl() {
    return this.url;
  }
  public PortalbasauthQuery setUrl(String url) {
    this.url = url;
    return this;
  }

  public PortalbasauthQuery setUrlLike(boolean isLike) {
    this.urlLike = isLike;
    return this;
  }

  public Long getSessiontime() {
    return this.sessiontime;
  }
  public PortalbasauthQuery setSessiontime(Long sessiontime) {
    this.sessiontime = sessiontime;
    return this;
  }

  public PortalbasauthQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasauthQuery orderbyBasid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasauthQuery orderbyType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasauthQuery orderbyUsername(boolean isAsc)
  {
    this.orderFields.add(new OrderField("username", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasauthQuery orderbyPassword(boolean isAsc)
  {
    this.orderFields.add(new OrderField("password", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasauthQuery orderbyBasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasauthQuery orderbyUrl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasauthQuery orderbySessiontime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("sessiontime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("basid", "basid");
      fieldMap.put("type", "type");
      fieldMap.put("username", "username");
      fieldMap.put("password", "password");
      fieldMap.put("basip", "basip");
      fieldMap.put("url", "url");
      fieldMap.put("sessiontime", "sessiontime");
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
 * Qualified Name:     com.leeson.core.query.PortalbasauthQuery
 * JD-Core Version:    0.6.2
 */