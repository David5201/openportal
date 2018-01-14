package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalaccountgroupQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private Date date;
  private Long time;
  private Long octets;
  private String state;
  private boolean stateLike;
  private Long speed;
  private Integer maclimit;
  private Integer maclimitcount;
  private Integer autologin;
  private Integer autoKick;
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
  private String isp;
  private boolean ispLike;
  private String radius;
  private boolean radiusLike;
  private Date creatDate;
  private Integer unlockMac;
  private Integer clearHaveAll;
  private Integer clearHaveLimit;
  private Long octetsLimit;
  private Long timeLimit;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalaccountgroupQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalaccountgroupQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalaccountgroupQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalaccountgroupQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalaccountgroupQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Date getDate() {
    return this.date;
  }
  public PortalaccountgroupQuery setDate(Date date) {
    this.date = date;
    return this;
  }

  public Long getTime() {
    return this.time;
  }
  public PortalaccountgroupQuery setTime(Long time) {
    this.time = time;
    return this;
  }

  public Long getOctets() {
    return this.octets;
  }
  public PortalaccountgroupQuery setOctets(Long octets) {
    this.octets = octets;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortalaccountgroupQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortalaccountgroupQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public Long getSpeed() {
    return this.speed;
  }
  public PortalaccountgroupQuery setSpeed(Long speed) {
    this.speed = speed;
    return this;
  }

  public Integer getMaclimit() {
    return this.maclimit;
  }
  public PortalaccountgroupQuery setMaclimit(Integer maclimit) {
    this.maclimit = maclimit;
    return this;
  }

  public Integer getMaclimitcount() {
    return this.maclimitcount;
  }
  public PortalaccountgroupQuery setMaclimitcount(Integer maclimitcount) {
    this.maclimitcount = maclimitcount;
    return this;
  }

  public Integer getAutologin() {
    return this.autologin;
  }
  public PortalaccountgroupQuery setAutologin(Integer autologin) {
    this.autologin = autologin;
    return this;
  }

  public Integer getAutoKick() {
    return this.autoKick;
  }
  public PortalaccountgroupQuery setAutoKick(Integer autoKick) {
    this.autoKick = autoKick;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public PortalaccountgroupQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public PortalaccountgroupQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public PortalaccountgroupQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public PortalaccountgroupQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public PortalaccountgroupQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public PortalaccountgroupQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public PortalaccountgroupQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public PortalaccountgroupQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public PortalaccountgroupQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public PortalaccountgroupQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public PortalaccountgroupQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public PortalaccountgroupQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public PortalaccountgroupQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public PortalaccountgroupQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public PortalaccountgroupQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public PortalaccountgroupQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public PortalaccountgroupQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public PortalaccountgroupQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public PortalaccountgroupQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public PortalaccountgroupQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public String getIsp() {
    return this.isp;
  }
  public PortalaccountgroupQuery setIsp(String isp) {
    this.isp = isp;
    return this;
  }

  public PortalaccountgroupQuery setIspLike(boolean isLike) {
    this.ispLike = isLike;
    return this;
  }

  public String getRadius() {
    return this.radius;
  }
  public PortalaccountgroupQuery setRadius(String radius) {
    this.radius = radius;
    return this;
  }

  public PortalaccountgroupQuery setRadiusLike(boolean isLike) {
    this.radiusLike = isLike;
    return this;
  }

  public Date getCreatDate() {
    return this.creatDate;
  }
  public PortalaccountgroupQuery setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
    return this;
  }

  public Integer getUnlockMac() {
    return this.unlockMac;
  }
  public PortalaccountgroupQuery setUnlockMac(Integer unlockMac) {
    this.unlockMac = unlockMac;
    return this;
  }

  public Integer getClearHaveAll() {
    return this.clearHaveAll;
  }
  public PortalaccountgroupQuery setClearHaveAll(Integer clearHaveAll) {
    this.clearHaveAll = clearHaveAll;
    return this;
  }

  public Integer getClearHaveLimit() {
    return this.clearHaveLimit;
  }
  public PortalaccountgroupQuery setClearHaveLimit(Integer clearHaveLimit) {
    this.clearHaveLimit = clearHaveLimit;
    return this;
  }

  public Long getOctetsLimit() {
    return this.octetsLimit;
  }
  public PortalaccountgroupQuery setOctetsLimit(Long octetsLimit) {
    this.octetsLimit = octetsLimit;
    return this;
  }

  public Long getTimeLimit() {
    return this.timeLimit;
  }
  public PortalaccountgroupQuery setTimeLimit(Long timeLimit) {
    this.timeLimit = timeLimit;
    return this;
  }

  public PortalaccountgroupQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("date", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyOctets(boolean isAsc)
  {
    this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbySpeed(boolean isAsc)
  {
    this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyMaclimit(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyMaclimitcount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyAutologin(boolean isAsc)
  {
    this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyAutoKick(boolean isAsc)
  {
    this.orderFields.add(new OrderField("autoKick", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyEx10(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyIsp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("isp", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyRadius(boolean isAsc)
  {
    this.orderFields.add(new OrderField("radius", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyCreatDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyUnlockMac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("unlockMac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyClearHaveAll(boolean isAsc)
  {
    this.orderFields.add(new OrderField("clearHaveAll", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyClearHaveLimit(boolean isAsc)
  {
    this.orderFields.add(new OrderField("clearHaveLimit", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyOctetsLimit(boolean isAsc)
  {
    this.orderFields.add(new OrderField("octetsLimit", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountgroupQuery orderbyTimeLimit(boolean isAsc)
  {
    this.orderFields.add(new OrderField("timeLimit", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("description", "description");
      fieldMap.put("date", "date");
      fieldMap.put("time", "time");
      fieldMap.put("octets", "octets");
      fieldMap.put("state", "state");
      fieldMap.put("speed", "speed");
      fieldMap.put("maclimit", "maclimit");
      fieldMap.put("maclimitcount", "maclimitcount");
      fieldMap.put("autologin", "autologin");
      fieldMap.put("autoKick", "autoKick");
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
      fieldMap.put("isp", "isp");
      fieldMap.put("radius", "radius");
      fieldMap.put("creatDate", "creatDate");
      fieldMap.put("unlockMac", "unlockMac");
      fieldMap.put("clearHaveAll", "clearHaveAll");
      fieldMap.put("clearHaveLimit", "clearHaveLimit");
      fieldMap.put("octetsLimit", "octetsLimit");
      fieldMap.put("timeLimit", "timeLimit");
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
 * Qualified Name:     com.leeson.core.query.PortalaccountgroupQuery
 * JD-Core Version:    0.6.2
 */