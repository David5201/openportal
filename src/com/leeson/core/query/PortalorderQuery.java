package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalorderQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private Long payTime;
  private String payType;
  private boolean payTypeLike;
  private String state;
  private boolean stateLike;
  private String cdKey;
  private boolean cdKeyLike;
  private String categoryType;
  private boolean categoryTypeLike;
  private String accountName;
  private boolean accountNameLike;
  private Long accountId;
  private Date payDate;
  private Integer userDel;
  private Integer accountDel;
  private Double money;
  private Date buyDate;
  private Integer payby;
  private String tradeno;
  private boolean tradenoLike;
  private String buyer;
  private boolean buyerLike;
  private String seller;
  private boolean sellerLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalorderQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalorderQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalorderQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalorderQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalorderQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Long getPayTime() {
    return this.payTime;
  }
  public PortalorderQuery setPayTime(Long payTime) {
    this.payTime = payTime;
    return this;
  }

  public String getPayType() {
    return this.payType;
  }
  public PortalorderQuery setPayType(String payType) {
    this.payType = payType;
    return this;
  }

  public PortalorderQuery setPayTypeLike(boolean isLike) {
    this.payTypeLike = isLike;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortalorderQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortalorderQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public String getCdKey() {
    return this.cdKey;
  }
  public PortalorderQuery setCdKey(String cdKey) {
    this.cdKey = cdKey;
    return this;
  }

  public PortalorderQuery setCdKeyLike(boolean isLike) {
    this.cdKeyLike = isLike;
    return this;
  }

  public String getCategoryType() {
    return this.categoryType;
  }
  public PortalorderQuery setCategoryType(String categoryType) {
    this.categoryType = categoryType;
    return this;
  }

  public PortalorderQuery setCategoryTypeLike(boolean isLike) {
    this.categoryTypeLike = isLike;
    return this;
  }

  public String getAccountName() {
    return this.accountName;
  }
  public PortalorderQuery setAccountName(String accountName) {
    this.accountName = accountName;
    return this;
  }

  public PortalorderQuery setAccountNameLike(boolean isLike) {
    this.accountNameLike = isLike;
    return this;
  }

  public Long getAccountId() {
    return this.accountId;
  }
  public PortalorderQuery setAccountId(Long accountId) {
    this.accountId = accountId;
    return this;
  }

  public Date getPayDate() {
    return this.payDate;
  }
  public PortalorderQuery setPayDate(Date payDate) {
    this.payDate = payDate;
    return this;
  }

  public Integer getUserDel() {
    return this.userDel;
  }
  public PortalorderQuery setUserDel(Integer userDel) {
    this.userDel = userDel;
    return this;
  }

  public Integer getAccountDel() {
    return this.accountDel;
  }
  public PortalorderQuery setAccountDel(Integer accountDel) {
    this.accountDel = accountDel;
    return this;
  }

  public Double getMoney() {
    return this.money;
  }
  public PortalorderQuery setMoney(Double money) {
    this.money = money;
    return this;
  }

  public Date getBuyDate() {
    return this.buyDate;
  }
  public PortalorderQuery setBuyDate(Date buyDate) {
    this.buyDate = buyDate;
    return this;
  }

  public Integer getPayby() {
    return this.payby;
  }
  public PortalorderQuery setPayby(Integer payby) {
    this.payby = payby;
    return this;
  }

  public String getTradeno() {
    return this.tradeno;
  }
  public PortalorderQuery setTradeno(String tradeno) {
    this.tradeno = tradeno;
    return this;
  }

  public PortalorderQuery setTradenoLike(boolean isLike) {
    this.tradenoLike = isLike;
    return this;
  }

  public String getBuyer() {
    return this.buyer;
  }
  public PortalorderQuery setBuyer(String buyer) {
    this.buyer = buyer;
    return this;
  }

  public PortalorderQuery setBuyerLike(boolean isLike) {
    this.buyerLike = isLike;
    return this;
  }

  public String getSeller() {
    return this.seller;
  }
  public PortalorderQuery setSeller(String seller) {
    this.seller = seller;
    return this;
  }

  public PortalorderQuery setSellerLike(boolean isLike) {
    this.sellerLike = isLike;
    return this;
  }

  public PortalorderQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyPayTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("payTime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyPayType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("payType", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyCdKey(boolean isAsc)
  {
    this.orderFields.add(new OrderField("cdKey", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyCategoryType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("categoryType", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyAccountName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountName", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyAccountId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyPayDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("payDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyUserDel(boolean isAsc)
  {
    this.orderFields.add(new OrderField("userDel", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyAccountDel(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountDel", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyMoney(boolean isAsc)
  {
    this.orderFields.add(new OrderField("money", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyBuyDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("buyDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyPayby(boolean isAsc)
  {
    this.orderFields.add(new OrderField("payby", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyTradeno(boolean isAsc)
  {
    this.orderFields.add(new OrderField("tradeno", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbyBuyer(boolean isAsc)
  {
    this.orderFields.add(new OrderField("buyer", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalorderQuery orderbySeller(boolean isAsc)
  {
    this.orderFields.add(new OrderField("seller", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("description", "description");
      fieldMap.put("payTime", "payTime");
      fieldMap.put("payType", "payType");
      fieldMap.put("state", "state");
      fieldMap.put("cdKey", "cdKey");
      fieldMap.put("categoryType", "categoryType");
      fieldMap.put("accountName", "accountName");
      fieldMap.put("accountId", "accountId");
      fieldMap.put("payDate", "payDate");
      fieldMap.put("userDel", "userDel");
      fieldMap.put("accountDel", "accountDel");
      fieldMap.put("money", "money");
      fieldMap.put("buyDate", "buyDate");
      fieldMap.put("payby", "payby");
      fieldMap.put("tradeno", "tradeno");
      fieldMap.put("buyer", "buyer");
      fieldMap.put("seller", "seller");
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
 * Qualified Name:     com.leeson.core.query.PortalorderQuery
 * JD-Core Version:    0.6.2
 */