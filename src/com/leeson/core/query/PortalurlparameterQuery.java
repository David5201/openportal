package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalurlparameterQuery extends BaseQuery
{
  private Long id;
  private String basname;
  private boolean basnameLike;
  private String userip;
  private boolean useripLike;
  private String usermac;
  private boolean usermacLike;
  private String url;
  private boolean urlLike;
  private String basip;
  private boolean basipLike;
  private String ssid;
  private boolean ssidLike;
  private String apmac;
  private boolean apmacLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalurlparameterQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getBasname() {
    return this.basname;
  }
  public PortalurlparameterQuery setBasname(String basname) {
    this.basname = basname;
    return this;
  }

  public PortalurlparameterQuery setBasnameLike(boolean isLike) {
    this.basnameLike = isLike;
    return this;
  }

  public String getUserip() {
    return this.userip;
  }
  public PortalurlparameterQuery setUserip(String userip) {
    this.userip = userip;
    return this;
  }

  public PortalurlparameterQuery setUseripLike(boolean isLike) {
    this.useripLike = isLike;
    return this;
  }

  public String getUsermac() {
    return this.usermac;
  }
  public PortalurlparameterQuery setUsermac(String usermac) {
    this.usermac = usermac;
    return this;
  }

  public PortalurlparameterQuery setUsermacLike(boolean isLike) {
    this.usermacLike = isLike;
    return this;
  }

  public String getUrl() {
    return this.url;
  }
  public PortalurlparameterQuery setUrl(String url) {
    this.url = url;
    return this;
  }

  public PortalurlparameterQuery setUrlLike(boolean isLike) {
    this.urlLike = isLike;
    return this;
  }

  public String getBasip() {
    return this.basip;
  }
  public PortalurlparameterQuery setBasip(String basip) {
    this.basip = basip;
    return this;
  }

  public PortalurlparameterQuery setBasipLike(boolean isLike) {
    this.basipLike = isLike;
    return this;
  }

  public String getSsid() {
    return this.ssid;
  }
  public PortalurlparameterQuery setSsid(String ssid) {
    this.ssid = ssid;
    return this;
  }

  public PortalurlparameterQuery setSsidLike(boolean isLike) {
    this.ssidLike = isLike;
    return this;
  }

  public String getApmac() {
    return this.apmac;
  }
  public PortalurlparameterQuery setApmac(String apmac) {
    this.apmac = apmac;
    return this;
  }

  public PortalurlparameterQuery setApmacLike(boolean isLike) {
    this.apmacLike = isLike;
    return this;
  }

  public PortalurlparameterQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalurlparameterQuery orderbyBasname(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basname", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalurlparameterQuery orderbyUserip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("userip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalurlparameterQuery orderbyUsermac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("usermac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalurlparameterQuery orderbyUrl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalurlparameterQuery orderbyBasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalurlparameterQuery orderbySsid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalurlparameterQuery orderbyApmac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("apmac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("basname", "basname");
      fieldMap.put("userip", "userip");
      fieldMap.put("usermac", "usermac");
      fieldMap.put("url", "url");
      fieldMap.put("basip", "basip");
      fieldMap.put("ssid", "ssid");
      fieldMap.put("apmac", "apmac");
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
 * Qualified Name:     com.leeson.core.query.PortalurlparameterQuery
 * JD-Core Version:    0.6.2
 */