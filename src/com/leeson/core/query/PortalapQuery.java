package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalapQuery extends BaseQuery
{
  private Long id;
  private Long basid;
  private String ip;
  private boolean ipLike;
  private String mac;
  private boolean macLike;
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
  public PortalapQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getBasid() {
    return this.basid;
  }
  public PortalapQuery setBasid(Long basid) {
    this.basid = basid;
    return this;
  }

  public String getIp() {
    return this.ip;
  }
  public PortalapQuery setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public PortalapQuery setIpLike(boolean isLike) {
    this.ipLike = isLike;
    return this;
  }

  public String getMac() {
    return this.mac;
  }
  public PortalapQuery setMac(String mac) {
    this.mac = mac;
    return this;
  }

  public PortalapQuery setMacLike(boolean isLike) {
    this.macLike = isLike;
    return this;
  }

  public String getAddress() {
    return this.address;
  }
  public PortalapQuery setAddress(String address) {
    this.address = address;
    return this;
  }

  public PortalapQuery setAddressLike(boolean isLike) {
    this.addressLike = isLike;
    return this;
  }

  public String getBasip() {
    return this.basip;
  }
  public PortalapQuery setBasip(String basip) {
    this.basip = basip;
    return this;
  }

  public PortalapQuery setBasipLike(boolean isLike) {
    this.basipLike = isLike;
    return this;
  }

  public String getX() {
    return this.x;
  }
  public PortalapQuery setX(String x) {
    this.x = x;
    return this;
  }

  public PortalapQuery setXLike(boolean isLike) {
    this.xLike = isLike;
    return this;
  }

  public String getY() {
    return this.y;
  }
  public PortalapQuery setY(String y) {
    this.y = y;
    return this;
  }

  public PortalapQuery setYLike(boolean isLike) {
    this.yLike = isLike;
    return this;
  }

  public String getDes() {
    return this.des;
  }
  public PortalapQuery setDes(String des) {
    this.des = des;
    return this;
  }

  public PortalapQuery setDesLike(boolean isLike) {
    this.desLike = isLike;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalapQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalapQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public Long getWeb() {
    return this.web;
  }
  public PortalapQuery setWeb(Long web) {
    this.web = web;
    return this;
  }

  public Long getCount() {
    return this.count;
  }
  public PortalapQuery setCount(Long count) {
    this.count = count;
    return this;
  }

  public PortalapQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyBasid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyIp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyMac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("mac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyAddress(boolean isAsc)
  {
    this.orderFields.add(new OrderField("address", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyBasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyX(boolean isAsc)
  {
    this.orderFields.add(new OrderField("x", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyY(boolean isAsc)
  {
    this.orderFields.add(new OrderField("y", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyDes(boolean isAsc)
  {
    this.orderFields.add(new OrderField("des", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyWeb(boolean isAsc)
  {
    this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalapQuery orderbyCount(boolean isAsc)
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
      fieldMap.put("mac", "mac");
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
 * Qualified Name:     com.leeson.core.query.PortalapQuery
 * JD-Core Version:    0.6.2
 */