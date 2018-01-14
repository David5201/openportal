package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalweixinwifiQuery extends BaseQuery
{
  private Long id;
  private String appId;
  private boolean appIdLike;
  private String shopId;
  private boolean shopIdLike;
  private String ssid;
  private boolean ssidLike;
  private String domain;
  private boolean domainLike;
  private String bssid;
  private boolean bssidLike;
  private String secretKey;
  private boolean secretKeyLike;
  private Long outTime;
  private String basip;
  private boolean basipLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalweixinwifiQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getAppId() {
    return this.appId;
  }
  public PortalweixinwifiQuery setAppId(String appId) {
    this.appId = appId;
    return this;
  }

  public PortalweixinwifiQuery setAppIdLike(boolean isLike) {
    this.appIdLike = isLike;
    return this;
  }

  public String getShopId() {
    return this.shopId;
  }
  public PortalweixinwifiQuery setShopId(String shopId) {
    this.shopId = shopId;
    return this;
  }

  public PortalweixinwifiQuery setShopIdLike(boolean isLike) {
    this.shopIdLike = isLike;
    return this;
  }

  public String getSsid() {
    return this.ssid;
  }
  public PortalweixinwifiQuery setSsid(String ssid) {
    this.ssid = ssid;
    return this;
  }

  public PortalweixinwifiQuery setSsidLike(boolean isLike) {
    this.ssidLike = isLike;
    return this;
  }

  public String getDomain() {
    return this.domain;
  }
  public PortalweixinwifiQuery setDomain(String domain) {
    this.domain = domain;
    return this;
  }

  public PortalweixinwifiQuery setDomainLike(boolean isLike) {
    this.domainLike = isLike;
    return this;
  }

  public String getBssid() {
    return this.bssid;
  }
  public PortalweixinwifiQuery setBssid(String bssid) {
    this.bssid = bssid;
    return this;
  }

  public PortalweixinwifiQuery setBssidLike(boolean isLike) {
    this.bssidLike = isLike;
    return this;
  }

  public String getSecretKey() {
    return this.secretKey;
  }
  public PortalweixinwifiQuery setSecretKey(String secretKey) {
    this.secretKey = secretKey;
    return this;
  }

  public PortalweixinwifiQuery setSecretKeyLike(boolean isLike) {
    this.secretKeyLike = isLike;
    return this;
  }

  public Long getOutTime() {
    return this.outTime;
  }
  public PortalweixinwifiQuery setOutTime(Long outTime) {
    this.outTime = outTime;
    return this;
  }

  public String getBasip() {
    return this.basip;
  }
  public PortalweixinwifiQuery setBasip(String basip) {
    this.basip = basip;
    return this;
  }

  public PortalweixinwifiQuery setBasipLike(boolean isLike) {
    this.basipLike = isLike;
    return this;
  }

  public PortalweixinwifiQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbyAppId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("appId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbyShopId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("shopId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbySsid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbyDomain(boolean isAsc)
  {
    this.orderFields.add(new OrderField("domain", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbyBssid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("bssid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbySecretKey(boolean isAsc)
  {
    this.orderFields.add(new OrderField("secretKey", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbyOutTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("outTime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalweixinwifiQuery orderbyBasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("appId", "appId");
      fieldMap.put("shopId", "shopId");
      fieldMap.put("ssid", "ssid");
      fieldMap.put("domain", "domain");
      fieldMap.put("bssid", "bssid");
      fieldMap.put("secretKey", "secretKey");
      fieldMap.put("outTime", "outTime");
      fieldMap.put("basip", "basip");
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
 * Qualified Name:     com.leeson.core.query.PortalweixinwifiQuery
 * JD-Core Version:    0.6.2
 */