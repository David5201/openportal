package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayapiQuery extends BaseQuery
{
  private Long id;
  private String alipayPartner;
  private boolean alipayPartnerLike;
  private String alipayKey;
  private boolean alipayKeyLike;
  private String weixinAppId;
  private boolean weixinAppIdLike;
  private String weixinAppSecret;
  private boolean weixinAppSecretLike;
  private String weixinPartner;
  private boolean weixinPartnerLike;
  private String weixinKey;
  private boolean weixinKeyLike;
  private String weixinPartnerExd;
  private boolean weixinPartnerExdLike;
  private Integer alipay;
  private Integer weixin;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PayapiQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getAlipayPartner() {
    return this.alipayPartner;
  }
  public PayapiQuery setAlipayPartner(String alipayPartner) {
    this.alipayPartner = alipayPartner;
    return this;
  }

  public PayapiQuery setAlipayPartnerLike(boolean isLike) {
    this.alipayPartnerLike = isLike;
    return this;
  }

  public String getAlipayKey() {
    return this.alipayKey;
  }
  public PayapiQuery setAlipayKey(String alipayKey) {
    this.alipayKey = alipayKey;
    return this;
  }

  public PayapiQuery setAlipayKeyLike(boolean isLike) {
    this.alipayKeyLike = isLike;
    return this;
  }

  public String getWeixinAppId() {
    return this.weixinAppId;
  }
  public PayapiQuery setWeixinAppId(String weixinAppId) {
    this.weixinAppId = weixinAppId;
    return this;
  }

  public PayapiQuery setWeixinAppIdLike(boolean isLike) {
    this.weixinAppIdLike = isLike;
    return this;
  }

  public String getWeixinAppSecret() {
    return this.weixinAppSecret;
  }
  public PayapiQuery setWeixinAppSecret(String weixinAppSecret) {
    this.weixinAppSecret = weixinAppSecret;
    return this;
  }

  public PayapiQuery setWeixinAppSecretLike(boolean isLike) {
    this.weixinAppSecretLike = isLike;
    return this;
  }

  public String getWeixinPartner() {
    return this.weixinPartner;
  }
  public PayapiQuery setWeixinPartner(String weixinPartner) {
    this.weixinPartner = weixinPartner;
    return this;
  }

  public PayapiQuery setWeixinPartnerLike(boolean isLike) {
    this.weixinPartnerLike = isLike;
    return this;
  }

  public String getWeixinKey() {
    return this.weixinKey;
  }
  public PayapiQuery setWeixinKey(String weixinKey) {
    this.weixinKey = weixinKey;
    return this;
  }

  public PayapiQuery setWeixinKeyLike(boolean isLike) {
    this.weixinKeyLike = isLike;
    return this;
  }

  public String getWeixinPartnerExd() {
    return this.weixinPartnerExd;
  }
  public PayapiQuery setWeixinPartnerExd(String weixinPartnerExd) {
    this.weixinPartnerExd = weixinPartnerExd;
    return this;
  }

  public PayapiQuery setWeixinPartnerExdLike(boolean isLike) {
    this.weixinPartnerExdLike = isLike;
    return this;
  }

  public Integer getAlipay() {
    return this.alipay;
  }
  public PayapiQuery setAlipay(Integer alipay) {
    this.alipay = alipay;
    return this;
  }

  public Integer getWeixin() {
    return this.weixin;
  }
  public PayapiQuery setWeixin(Integer weixin) {
    this.weixin = weixin;
    return this;
  }

  public PayapiQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyAlipayPartner(boolean isAsc)
  {
    this.orderFields.add(new OrderField("alipayPartner", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyAlipayKey(boolean isAsc)
  {
    this.orderFields.add(new OrderField("alipayKey", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyWeixinAppId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("weixinAppId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyWeixinAppSecret(boolean isAsc)
  {
    this.orderFields.add(new OrderField("weixinAppSecret", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyWeixinPartner(boolean isAsc)
  {
    this.orderFields.add(new OrderField("weixinPartner", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyWeixinKey(boolean isAsc)
  {
    this.orderFields.add(new OrderField("weixinKey", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyWeixinPartnerExd(boolean isAsc)
  {
    this.orderFields.add(new OrderField("weixinPartnerExd", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyAlipay(boolean isAsc)
  {
    this.orderFields.add(new OrderField("alipay", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PayapiQuery orderbyWeixin(boolean isAsc)
  {
    this.orderFields.add(new OrderField("weixin", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("alipayPartner", "alipayPartner");
      fieldMap.put("alipayKey", "alipayKey");
      fieldMap.put("weixinAppId", "weixinAppId");
      fieldMap.put("weixinAppSecret", "weixinAppSecret");
      fieldMap.put("weixinPartner", "weixinPartner");
      fieldMap.put("weixinKey", "weixinKey");
      fieldMap.put("weixinPartnerExd", "weixinPartnerExd");
      fieldMap.put("alipay", "alipay");
      fieldMap.put("weixin", "weixin");
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
 * Qualified Name:     com.leeson.core.query.PayapiQuery
 * JD-Core Version:    0.6.2
 */