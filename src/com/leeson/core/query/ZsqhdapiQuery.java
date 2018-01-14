package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZsqhdapiQuery extends BaseQuery
{
  private Long id;
  private String url;
  private boolean urlLike;
  private String publicurl;
  private boolean publicurlLike;
  private String autourl;
  private boolean autourlLike;
  private Integer state;
  private Integer publicstate;
  private Integer autostate;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public ZsqhdapiQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUrl() {
    return this.url;
  }
  public ZsqhdapiQuery setUrl(String url) {
    this.url = url;
    return this;
  }

  public ZsqhdapiQuery setUrlLike(boolean isLike) {
    this.urlLike = isLike;
    return this;
  }

  public String getPublicurl() {
    return this.publicurl;
  }
  public ZsqhdapiQuery setPublicurl(String publicurl) {
    this.publicurl = publicurl;
    return this;
  }

  public ZsqhdapiQuery setPublicurlLike(boolean isLike) {
    this.publicurlLike = isLike;
    return this;
  }

  public String getAutourl() {
    return this.autourl;
  }
  public ZsqhdapiQuery setAutourl(String autourl) {
    this.autourl = autourl;
    return this;
  }

  public ZsqhdapiQuery setAutourlLike(boolean isLike) {
    this.autourlLike = isLike;
    return this;
  }

  public Integer getState() {
    return this.state;
  }
  public ZsqhdapiQuery setState(Integer state) {
    this.state = state;
    return this;
  }

  public Integer getPublicstate() {
    return this.publicstate;
  }
  public ZsqhdapiQuery setPublicstate(Integer publicstate) {
    this.publicstate = publicstate;
    return this;
  }

  public Integer getAutostate() {
    return this.autostate;
  }
  public ZsqhdapiQuery setAutostate(Integer autostate) {
    this.autostate = autostate;
    return this;
  }

  public ZsqhdapiQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ZsqhdapiQuery orderbyUrl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ZsqhdapiQuery orderbyPublicurl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("publicurl", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ZsqhdapiQuery orderbyAutourl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("autourl", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ZsqhdapiQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ZsqhdapiQuery orderbyPublicstate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("publicstate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public ZsqhdapiQuery orderbyAutostate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("autostate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("url", "url");
      fieldMap.put("publicurl", "publicurl");
      fieldMap.put("autourl", "autourl");
      fieldMap.put("state", "state");
      fieldMap.put("publicstate", "publicstate");
      fieldMap.put("autostate", "autostate");
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
 * Qualified Name:     com.leeson.core.query.ZsqhdapiQuery
 * JD-Core Version:    0.6.2
 */