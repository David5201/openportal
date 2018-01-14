package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalssidQuery extends BaseQuery
{
  private Long id;
  private Long basid;
  private String ip;
  private boolean ipLike;
  private String ssid;
  private boolean ssidLike;
  private String address;
  private boolean addressLike;
  private String basip;
  private boolean basipLike;
  private String x;
  private boolean xLike;
  private String y;
  private boolean yLike;
  private String des;
  private boolean desLike;
  private String name;
  private boolean nameLike;
  private Long web;
  private Long count;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalssidQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getBasid() {
    return this.basid;
  }
  public PortalssidQuery setBasid(Long basid) {
    this.basid = basid;
    return this;
  }

  public String getIp() {
    return this.ip;
  }
  public PortalssidQuery setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public PortalssidQuery setIpLike(boolean isLike) {
    this.ipLike = isLike;
    return this;
  }

  public String getSsid() {
    return this.ssid;
  }
  public PortalssidQuery setSsid(String ssid) {
    this.ssid = ssid;
    return this;
  }

  public PortalssidQuery setSsidLike(boolean isLike) {
    this.ssidLike = isLike;
    return this;
  }

  public String getAddress() {
    return this.address;
  }
  public PortalssidQuery setAddress(String address) {
    this.address = address;
    return this;
  }

  public PortalssidQuery setAddressLike(boolean isLike) {
    this.addressLike = isLike;
    return this;
  }

  public String getBasip() {
    return this.basip;
  }
  public PortalssidQuery setBasip(String basip) {
    this.basip = basip;
    return this;
  }

  public PortalssidQuery setBasipLike(boolean isLike) {
    this.basipLike = isLike;
    return this;
  }

  public String getX() {
    return this.x;
  }
  public PortalssidQuery setX(String x) {
    this.x = x;
    return this;
  }

  public PortalssidQuery setXLike(boolean isLike) {
    this.xLike = isLike;
    return this;
  }

  public String getY() {
    return this.y;
  }
  public PortalssidQuery setY(String y) {
    this.y = y;
    return this;
  }

  public PortalssidQuery setYLike(boolean isLike) {
    this.yLike = isLike;
    return this;
  }

  public String getDes() {
    return this.des;
  }
  public PortalssidQuery setDes(String des) {
    this.des = des;
    return this;
  }

  public PortalssidQuery setDesLike(boolean isLike) {
    this.desLike = isLike;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalssidQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalssidQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public Long getWeb() {
    return this.web;
  }
  public PortalssidQuery setWeb(Long web) {
    this.web = web;
    return this;
  }

  public Long getCount() {
    return this.count;
  }
  public PortalssidQuery setCount(Long count) {
    this.count = count;
    return this;
  }

  public PortalssidQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyBasid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyIp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbySsid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyAddress(boolean isAsc)
  {
    this.orderFields.add(new OrderField("address", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyBasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyX(boolean isAsc)
  {
    this.orderFields.add(new OrderField("x", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyY(boolean isAsc)
  {
    this.orderFields.add(new OrderField("y", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyDes(boolean isAsc)
  {
    this.orderFields.add(new OrderField("des", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyWeb(boolean isAsc)
  {
    this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalssidQuery orderbyCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("basid", "basid");
      fieldMap.put("ip", "ip");
      fieldMap.put("ssid", "ssid");
      fieldMap.put("address", "address");
      fieldMap.put("basip", "basip");
      fieldMap.put("x", "x");
      fieldMap.put("y", "y");
      fieldMap.put("des", "des");
      fieldMap.put("name", "name");
      fieldMap.put("web", "web");
      fieldMap.put("count", "count");
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
 * Qualified Name:     com.leeson.core.query.PortalssidQuery
 * JD-Core Version:    0.6.2
 */