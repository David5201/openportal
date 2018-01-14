package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortallinkrecordQuery extends BaseQuery
{
  private Long id;
  private String ip;
  private boolean ipLike;
  private String basip;
  private boolean basipLike;
  private String loginName;
  private boolean loginNameLike;
  private String state;
  private boolean stateLike;
  private Date startDate;
  private Date endDate;
  private Long time;
  private Long ins;
  private Long outs;
  private Long octets;
  private Long uid;
  private Integer userDel;
  private Integer accountDel;
  private String mac;
  private boolean macLike;
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
  public PortallinkrecordQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getIp() {
    return this.ip;
  }
  public PortallinkrecordQuery setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public PortallinkrecordQuery setIpLike(boolean isLike) {
    this.ipLike = isLike;
    return this;
  }

  public String getBasip() {
    return this.basip;
  }
  public PortallinkrecordQuery setBasip(String basip) {
    this.basip = basip;
    return this;
  }

  public PortallinkrecordQuery setBasipLike(boolean isLike) {
    this.basipLike = isLike;
    return this;
  }

  public String getLoginName() {
    return this.loginName;
  }
  public PortallinkrecordQuery setLoginName(String loginName) {
    this.loginName = loginName;
    return this;
  }

  public PortallinkrecordQuery setLoginNameLike(boolean isLike) {
    this.loginNameLike = isLike;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortallinkrecordQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortallinkrecordQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public Date getStartDate() {
    return this.startDate;
  }
  public PortallinkrecordQuery setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public Date getEndDate() {
    return this.endDate;
  }
  public PortallinkrecordQuery setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public Long getTime() {
    return this.time;
  }
  public PortallinkrecordQuery setTime(Long time) {
    this.time = time;
    return this;
  }

  public Long getIns() {
    return this.ins;
  }
  public PortallinkrecordQuery setIns(Long ins) {
    this.ins = ins;
    return this;
  }

  public Long getOuts() {
    return this.outs;
  }
  public PortallinkrecordQuery setOuts(Long outs) {
    this.outs = outs;
    return this;
  }

  public Long getOctets() {
    return this.octets;
  }
  public PortallinkrecordQuery setOctets(Long octets) {
    this.octets = octets;
    return this;
  }

  public Long getUid() {
    return this.uid;
  }
  public PortallinkrecordQuery setUid(Long uid) {
    this.uid = uid;
    return this;
  }

  public Integer getUserDel() {
    return this.userDel;
  }
  public PortallinkrecordQuery setUserDel(Integer userDel) {
    this.userDel = userDel;
    return this;
  }

  public Integer getAccountDel() {
    return this.accountDel;
  }
  public PortallinkrecordQuery setAccountDel(Integer accountDel) {
    this.accountDel = accountDel;
    return this;
  }

  public String getMac() {
    return this.mac;
  }
  public PortallinkrecordQuery setMac(String mac) {
    this.mac = mac;
    return this;
  }

  public PortallinkrecordQuery setMacLike(boolean isLike) {
    this.macLike = isLike;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public PortallinkrecordQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public PortallinkrecordQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public PortallinkrecordQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public PortallinkrecordQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public PortallinkrecordQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public PortallinkrecordQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public PortallinkrecordQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public PortallinkrecordQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public PortallinkrecordQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public PortallinkrecordQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public PortallinkrecordQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public PortallinkrecordQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public PortallinkrecordQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public PortallinkrecordQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public PortallinkrecordQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public PortallinkrecordQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public PortallinkrecordQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public PortallinkrecordQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public PortallinkrecordQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public PortallinkrecordQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public PortallinkrecordQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyIp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyBasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyLoginName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyStartDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("startDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEndDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyIns(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ins", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyOuts(boolean isAsc)
  {
    this.orderFields.add(new OrderField("outs", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyOctets(boolean isAsc)
  {
    this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyUid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyUserDel(boolean isAsc)
  {
    this.orderFields.add(new OrderField("userDel", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyAccountDel(boolean isAsc)
  {
    this.orderFields.add(new OrderField("accountDel", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyMac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("mac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordQuery orderbyEx10(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("ip", "ip");
      fieldMap.put("basip", "basip");
      fieldMap.put("loginName", "loginName");
      fieldMap.put("state", "state");
      fieldMap.put("startDate", "startDate");
      fieldMap.put("endDate", "endDate");
      fieldMap.put("time", "time");
      fieldMap.put("ins", "ins");
      fieldMap.put("outs", "outs");
      fieldMap.put("octets", "octets");
      fieldMap.put("uid", "uid");
      fieldMap.put("userDel", "userDel");
      fieldMap.put("accountDel", "accountDel");
      fieldMap.put("mac", "mac");
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
 * Qualified Name:     com.leeson.core.query.PortallinkrecordQuery
 * JD-Core Version:    0.6.2
 */