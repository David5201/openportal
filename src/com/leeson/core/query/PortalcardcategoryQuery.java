package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalcardcategoryQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private Long time;
  private String state;
  private boolean stateLike;
  private Double money;
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
  public PortalcardcategoryQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalcardcategoryQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalcardcategoryQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalcardcategoryQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalcardcategoryQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Long getTime() {
    return this.time;
  }
  public PortalcardcategoryQuery setTime(Long time) {
    this.time = time;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortalcardcategoryQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortalcardcategoryQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public Double getMoney() {
    return this.money;
  }
  public PortalcardcategoryQuery setMoney(Double money) {
    this.money = money;
    return this;
  }

  public Long getSpeed() {
    return this.speed;
  }
  public PortalcardcategoryQuery setSpeed(Long speed) {
    this.speed = speed;
    return this;
  }

  public Integer getMaclimit() {
    return this.maclimit;
  }
  public PortalcardcategoryQuery setMaclimit(Integer maclimit) {
    this.maclimit = maclimit;
    return this;
  }

  public Integer getMaclimitcount() {
    return this.maclimitcount;
  }
  public PortalcardcategoryQuery setMaclimitcount(Integer maclimitcount) {
    this.maclimitcount = maclimitcount;
    return this;
  }

  public Integer getAutologin() {
    return this.autologin;
  }
  public PortalcardcategoryQuery setAutologin(Integer autologin) {
    this.autologin = autologin;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public PortalcardcategoryQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public PortalcardcategoryQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public PortalcardcategoryQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public PortalcardcategoryQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public PortalcardcategoryQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public PortalcardcategoryQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public PortalcardcategoryQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public PortalcardcategoryQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public PortalcardcategoryQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public PortalcardcategoryQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public PortalcardcategoryQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public PortalcardcategoryQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public PortalcardcategoryQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public PortalcardcategoryQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public PortalcardcategoryQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public PortalcardcategoryQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public PortalcardcategoryQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public PortalcardcategoryQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public PortalcardcategoryQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public PortalcardcategoryQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public PortalcardcategoryQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyMoney(boolean isAsc)
  {
    this.orderFields.add(new OrderField("money", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbySpeed(boolean isAsc)
  {
    this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyMaclimit(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyMaclimitcount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyAutologin(boolean isAsc)
  {
    this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalcardcategoryQuery orderbyEx10(boolean isAsc)
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
      fieldMap.put("time", "time");
      fieldMap.put("state", "state");
      fieldMap.put("money", "money");
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
 * Qualified Name:     com.leeson.core.query.PortalcardcategoryQuery
 * JD-Core Version:    0.6.2
 */