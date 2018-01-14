package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalcardQuery extends BaseQuery
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
  private Long speed;
  private Integer maclimit;
  private Integer maclimitcount;
  private Integer autologin;
  private String ex1;
  private boolean ex1Like;
  private String ex2;
  private boolean ex2Like;
  private String ex3;
  private boolean ex3Like;
  private String ex4;
  private boolean ex4Like;
  private String ex5;
  private boolean ex5Like;
  private String ex6;
  private boolean ex6Like;
  private String ex7;
  private boolean ex7Like;
  private String ex8;
  private boolean ex8Like;
  private String ex9;
  private boolean ex9Like;
  private String ex10;
  private boolean ex10Like;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalcardQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalcardQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalcardQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalcardQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalcardQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Long getPayTime() {
    return this.payTime;
  }
  public PortalcardQuery setPayTime(Long payTime) {
    this.payTime = payTime;
    return this;
  }

  public String getPayType() {
    return this.payType;
  }
  public PortalcardQuery setPayType(String payType) {
    this.payType = payType;
    return this;
  }

  public PortalcardQuery setPayTypeLike(boolean isLike) {
    this.payTypeLike = isLike;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortalcardQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortalcardQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public String getCdKey() {
    return this.cdKey;
  }
  public PortalcardQuery setCdKey(String cdKey) {
    this.cdKey = cdKey;
    return this;
  }

  public PortalcardQuery setCdKeyLike(boolean isLike) {
    this.cdKeyLike = isLike;
    return this;
  }

  public String getCategoryType() {
    return this.categoryType;
  }
  public PortalcardQuery setCategoryType(String categoryType) {
    this.categoryType = categoryType;
    return this;
  }

  public PortalcardQuery setCategoryTypeLike(boolean isLike) {
    this.categoryTypeLike = isLike;
    return this;
  }

  public String getAccountName() {
    return this.accountName;
  }
  public PortalcardQuery setAccountName(String accountName) {
    this.accountName = accountName;
    return this;
  }

  public PortalcardQuery setAccountNameLike(boolean isLike) {
    this.accountNameLike = isLike;
    return this;
  }

  public Long getAccountId() {
    return this.accountId;
  }
  public PortalcardQuery setAccountId(Long accountId) {
    this.accountId = accountId;
    return this;
  }

  public Date getPayDate() {
    return this.payDate;
  }
  public PortalcardQuery setPayDate(Date payDate) {
    this.payDate = payDate;
    return this;
  }

  public Integer getUserDel() {
    return this.userDel;
  }
  public PortalcardQuery setUserDel(Integer userDel) {
    this.userDel = userDel;
    return this;
  }

  public Integer getAccountDel() {
    return this.accountDel;
  }
  public PortalcardQuery setAccountDel(Integer accountDel) {
    this.accountDel = accountDel;
    return this;
  }

  public Double getMoney() {
    return this.money;
  }
  public PortalcardQuery setMoney(Double money) {
    this.money = money;
    return this;
  }

  public Date getBuyDate() {
    return this.buyDate;
  }
  public PortalcardQuery setBuyDate(Date buyDate) {
    this.buyDate = buyDate;
    return this;
  }

  public Long getSpeed() {
    return this.speed;
  }
  public PortalcardQuery setSpeed(Long speed) {
    this.speed = speed;
    return this;
  }

  public Integer getMaclimit() {
    return this.maclimit;
  }
  public PortalcardQuery setMaclimit(Integer maclimit) {
    this.maclimit = maclimit;
    return this;
  }

  public Integer getMaclimitcount() {
    return this.maclimitcount;
  }
  public PortalcardQuery setMaclimitcount(Integer maclimitcount) {
    this.maclimitcount = maclimitcount;
    return this;
  }

  public Integer getAutologin() {
    return this.autologin;
  }
  public PortalcardQuery setAutologin(Integer autologin) {
    this.autologin = autologin;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public PortalcardQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public PortalcardQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public PortalcardQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public PortalcardQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public PortalcardQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public PortalcardQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public PortalcardQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public PortalcardQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public PortalcardQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public PortalcardQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public PortalcardQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public PortalcardQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public PortalcardQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public PortalcardQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public PortalcardQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public PortalcardQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public PortalcardQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public PortalcardQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public PortalcardQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public PortalcardQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public PortalcardQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyPayTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("payTime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyPayType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("payType", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyCdKey(boolean isAsc)
  {
    this.orderFields.add(new OrderField("cdKey", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyCategoryType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("categoryType", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyAccountName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountName", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyAccountId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountId", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyPayDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("payDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyUserDel(boolean isAsc)
  {
    this.orderFields.add(new OrderField("userDel", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyAccountDel(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountDel", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyMoney(boolean isAsc)
  {
    this.orderFields.add(new OrderField("money", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyBuyDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("buyDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbySpeed(boolean isAsc)
  {
    this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyMaclimit(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyMaclimitcount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyAutologin(boolean isAsc)
  {
    this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardQuery orderbyEx10(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
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
      fieldMap.put("speed", "speed");
      fieldMap.put("maclimit", "maclimit");
      fieldMap.put("maclimitcount", "maclimitcount");
      fieldMap.put("autologin", "autologin");
      fieldMap.put("ex1", "ex1");
      fieldMap.put("ex2", "ex2");
      fieldMap.put("ex3", "ex3");
      fieldMap.put("ex4", "ex4");
      fieldMap.put("ex5", "ex5");
      fieldMap.put("ex6", "ex6");
      fieldMap.put("ex7", "ex7");
      fieldMap.put("ex8", "ex8");
      fieldMap.put("ex9", "ex9");
      fieldMap.put("ex10", "ex10");
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
 * Qualified Name:     com.leeson.core.query.PortalcardQuery
 * JD-Core Version:    0.6.2
 */