package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigQuery extends BaseQuery
{
  private Long id;
  private Integer portalPort;
  private Integer isDebug;
  private Integer radiusOn;
  private Long checkTime;
  private Integer accountAdd;
  private Integer shutdownKick;
  private String domain;
  private boolean domainLike;
  private Long countShow;
  private Long countAuth;
  private Integer useDomain;
  private Integer authPort;
  private Integer acctPort;
  private Integer smsAuthList;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public ConfigQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public Integer getPortalPort() {
    return this.portalPort;
  }
  public ConfigQuery setPortalPort(Integer portalPort) {
    this.portalPort = portalPort;
    return this;
  }

  public Integer getIsDebug() {
    return this.isDebug;
  }
  public ConfigQuery setIsDebug(Integer isDebug) {
    this.isDebug = isDebug;
    return this;
  }

  public Integer getRadiusOn() {
    return this.radiusOn;
  }
  public ConfigQuery setRadiusOn(Integer radiusOn) {
    this.radiusOn = radiusOn;
    return this;
  }

  public Long getCheckTime() {
    return this.checkTime;
  }
  public ConfigQuery setCheckTime(Long checkTime) {
    this.checkTime = checkTime;
    return this;
  }

  public Integer getAccountAdd() {
    return this.accountAdd;
  }
  public ConfigQuery setAccountAdd(Integer accountAdd) {
    this.accountAdd = accountAdd;
    return this;
  }

  public Integer getShutdownKick() {
    return this.shutdownKick;
  }
  public ConfigQuery setShutdownKick(Integer shutdownKick) {
    this.shutdownKick = shutdownKick;
    return this;
  }

  public String getDomain() {
    return this.domain;
  }
  public ConfigQuery setDomain(String domain) {
    this.domain = domain;
    return this;
  }

  public ConfigQuery setDomainLike(boolean isLike) {
    this.domainLike = isLike;
    return this;
  }

  public Long getCountShow() {
    return this.countShow;
  }
  public ConfigQuery setCountShow(Long countShow) {
    this.countShow = countShow;
    return this;
  }

  public Long getCountAuth() {
    return this.countAuth;
  }
  public ConfigQuery setCountAuth(Long countAuth) {
    this.countAuth = countAuth;
    return this;
  }

  public Integer getUseDomain() {
    return this.useDomain;
  }
  public ConfigQuery setUseDomain(Integer useDomain) {
    this.useDomain = useDomain;
    return this;
  }

  public Integer getAuthPort() {
    return this.authPort;
  }
  public ConfigQuery setAuthPort(Integer authPort) {
    this.authPort = authPort;
    return this;
  }

  public Integer getAcctPort() {
    return this.acctPort;
  }
  public ConfigQuery setAcctPort(Integer acctPort) {
    this.acctPort = acctPort;
    return this;
  }

  public Integer getSmsAuthList() {
    return this.smsAuthList;
  }
  public ConfigQuery setSmsAuthList(Integer smsAuthList) {
    this.smsAuthList = smsAuthList;
    return this;
  }

  public ConfigQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyPortalPort(boolean isAsc)
  {
    this.orderFields.add(new OrderField("portalPort", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyIsDebug(boolean isAsc)
  {
    this.orderFields.add(new OrderField("isDebug", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyRadiusOn(boolean isAsc)
  {
    this.orderFields.add(new OrderField("radiusOn", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyCheckTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("checkTime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyAccountAdd(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountAdd", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyShutdownKick(boolean isAsc)
  {
    this.orderFields.add(new OrderField("shutdownKick", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyDomain(boolean isAsc)
  {
    this.orderFields.add(new OrderField("domain", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyCountShow(boolean isAsc)
  {
    this.orderFields.add(new OrderField("countShow", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyCountAuth(boolean isAsc)
  {
    this.orderFields.add(new OrderField("countAuth", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyUseDomain(boolean isAsc)
  {
    this.orderFields.add(new OrderField("useDomain", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyAuthPort(boolean isAsc)
  {
    this.orderFields.add(new OrderField("authPort", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbyAcctPort(boolean isAsc)
  {
    this.orderFields.add(new OrderField("acctPort", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ConfigQuery orderbySmsAuthList(boolean isAsc)
  {
    this.orderFields.add(new OrderField("smsAuthList", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("portalPort", "portalPort");
      fieldMap.put("isDebug", "isDebug");
      fieldMap.put("radiusOn", "radiusOn");
      fieldMap.put("checkTime", "checkTime");
      fieldMap.put("accountAdd", "accountAdd");
      fieldMap.put("shutdownKick", "shutdownKick");
      fieldMap.put("domain", "domain");
      fieldMap.put("countShow", "countShow");
      fieldMap.put("countAuth", "countAuth");
      fieldMap.put("useDomain", "useDomain");
      fieldMap.put("authPort", "authPort");
      fieldMap.put("acctPort", "acctPort");
      fieldMap.put("smsAuthList", "smsAuthList");
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
 * Qualified Name:     com.leeson.core.query.ConfigQuery
 * JD-Core Version:    0.6.2
 */