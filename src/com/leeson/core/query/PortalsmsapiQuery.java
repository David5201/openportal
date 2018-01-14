package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalsmsapiQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String url;
  private boolean urlLike;
  private Long count;
  private String state;
  private boolean stateLike;
  private String type;
  private boolean typeLike;
  private String more;
  private boolean moreLike;
  private Integer time;
  private String text;
  private boolean textLike;
  private String appkey;
  private boolean appkeyLike;
  private String appsecret;
  private boolean appsecretLike;
  private String smssign;
  private boolean smssignLike;
  private String smstemplate;
  private boolean smstemplateLike;
  private String company;
  private boolean companyLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalsmsapiQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalsmsapiQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalsmsapiQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getUrl() {
    return this.url;
  }
  public PortalsmsapiQuery setUrl(String url) {
    this.url = url;
    return this;
  }

  public PortalsmsapiQuery setUrlLike(boolean isLike) {
    this.urlLike = isLike;
    return this;
  }

  public Long getCount() {
    return this.count;
  }
  public PortalsmsapiQuery setCount(Long count) {
    this.count = count;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortalsmsapiQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortalsmsapiQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public String getType() {
    return this.type;
  }
  public PortalsmsapiQuery setType(String type) {
    this.type = type;
    return this;
  }

  public PortalsmsapiQuery setTypeLike(boolean isLike) {
    this.typeLike = isLike;
    return this;
  }

  public String getMore() {
    return this.more;
  }
  public PortalsmsapiQuery setMore(String more) {
    this.more = more;
    return this;
  }

  public PortalsmsapiQuery setMoreLike(boolean isLike) {
    this.moreLike = isLike;
    return this;
  }

  public Integer getTime() {
    return this.time;
  }
  public PortalsmsapiQuery setTime(Integer time) {
    this.time = time;
    return this;
  }

  public String getText() {
    return this.text;
  }
  public PortalsmsapiQuery setText(String text) {
    this.text = text;
    return this;
  }

  public PortalsmsapiQuery setTextLike(boolean isLike) {
    this.textLike = isLike;
    return this;
  }

  public String getAppkey() {
    return this.appkey;
  }
  public PortalsmsapiQuery setAppkey(String appkey) {
    this.appkey = appkey;
    return this;
  }

  public PortalsmsapiQuery setAppkeyLike(boolean isLike) {
    this.appkeyLike = isLike;
    return this;
  }

  public String getAppsecret() {
    return this.appsecret;
  }
  public PortalsmsapiQuery setAppsecret(String appsecret) {
    this.appsecret = appsecret;
    return this;
  }

  public PortalsmsapiQuery setAppsecretLike(boolean isLike) {
    this.appsecretLike = isLike;
    return this;
  }

  public String getSmssign() {
    return this.smssign;
  }
  public PortalsmsapiQuery setSmssign(String smssign) {
    this.smssign = smssign;
    return this;
  }

  public PortalsmsapiQuery setSmssignLike(boolean isLike) {
    this.smssignLike = isLike;
    return this;
  }

  public String getSmstemplate() {
    return this.smstemplate;
  }
  public PortalsmsapiQuery setSmstemplate(String smstemplate) {
    this.smstemplate = smstemplate;
    return this;
  }

  public PortalsmsapiQuery setSmstemplateLike(boolean isLike) {
    this.smstemplateLike = isLike;
    return this;
  }

  public String getCompany() {
    return this.company;
  }
  public PortalsmsapiQuery setCompany(String company) {
    this.company = company;
    return this;
  }

  public PortalsmsapiQuery setCompanyLike(boolean isLike) {
    this.companyLike = isLike;
    return this;
  }

  public PortalsmsapiQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyUrl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("count", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("type", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyMore(boolean isAsc)
  {
    this.orderFields.add(new OrderField("more", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyText(boolean isAsc)
  {
    this.orderFields.add(new OrderField("text", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyAppkey(boolean isAsc)
  {
    this.orderFields.add(new OrderField("appkey", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyAppsecret(boolean isAsc)
  {
    this.orderFields.add(new OrderField("appsecret", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbySmssign(boolean isAsc)
  {
    this.orderFields.add(new OrderField("smssign", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbySmstemplate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("smstemplate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalsmsapiQuery orderbyCompany(boolean isAsc)
  {
    this.orderFields.add(new OrderField("company", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("url", "url");
      fieldMap.put("count", "count");
      fieldMap.put("state", "state");
      fieldMap.put("type", "type");
      fieldMap.put("more", "more");
      fieldMap.put("time", "time");
      fieldMap.put("text", "text");
      fieldMap.put("appkey", "appkey");
      fieldMap.put("appsecret", "appsecret");
      fieldMap.put("smssign", "smssign");
      fieldMap.put("smstemplate", "smstemplate");
      fieldMap.put("company", "company");
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
 * Qualified Name:     com.leeson.core.query.PortalsmsapiQuery
 * JD-Core Version:    0.6.2
 */