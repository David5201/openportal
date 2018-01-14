package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalaccountQuery extends BaseQuery
{
  private Long id;
  private String loginName;
  private boolean loginNameLike;
  private String password;
  private boolean passwordLike;
  private String name;
  private boolean nameLike;
  private String gender;
  private boolean genderLike;
  private String phoneNumber;
  private boolean phoneNumberLike;
  private String email;
  private boolean emailLike;
  private String description;
  private boolean descriptionLike;
  private Date date;
  private Long time;
  private Long octets;
  private String state;
  private boolean stateLike;
  private String idnumber;
  private boolean idnumberLike;
  private String address;
  private boolean addressLike;
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
  public PortalaccountQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getLoginName() {
    return this.loginName;
  }
  public PortalaccountQuery setLoginName(String loginName) {
    this.loginName = loginName;
    return this;
  }

  public PortalaccountQuery setLoginNameLike(boolean isLike) {
    this.loginNameLike = isLike;
    return this;
  }

  public String getPassword() {
    return this.password;
  }
  public PortalaccountQuery setPassword(String password) {
    this.password = password;
    return this;
  }

  public PortalaccountQuery setPasswordLike(boolean isLike) {
    this.passwordLike = isLike;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public PortalaccountQuery setName(String name) {
    this.name = name;
    return this;
  }

  public PortalaccountQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getGender() {
    return this.gender;
  }
  public PortalaccountQuery setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public PortalaccountQuery setGenderLike(boolean isLike) {
    this.genderLike = isLike;
    return this;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  public PortalaccountQuery setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public PortalaccountQuery setPhoneNumberLike(boolean isLike) {
    this.phoneNumberLike = isLike;
    return this;
  }

  public String getEmail() {
    return this.email;
  }
  public PortalaccountQuery setEmail(String email) {
    this.email = email;
    return this;
  }

  public PortalaccountQuery setEmailLike(boolean isLike) {
    this.emailLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalaccountQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalaccountQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Date getDate() {
    return this.date;
  }
  public PortalaccountQuery setDate(Date date) {
    this.date = date;
    return this;
  }

  public Long getTime() {
    return this.time;
  }
  public PortalaccountQuery setTime(Long time) {
    this.time = time;
    return this;
  }

  public Long getOctets() {
    return this.octets;
  }
  public PortalaccountQuery setOctets(Long octets) {
    this.octets = octets;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortalaccountQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortalaccountQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public String getIdnumber() {
    return this.idnumber;
  }
  public PortalaccountQuery setIdnumber(String idnumber) {
    this.idnumber = idnumber;
    return this;
  }

  public PortalaccountQuery setIdnumberLike(boolean isLike) {
    this.idnumberLike = isLike;
    return this;
  }

  public String getAddress() {
    return this.address;
  }
  public PortalaccountQuery setAddress(String address) {
    this.address = address;
    return this;
  }

  public PortalaccountQuery setAddressLike(boolean isLike) {
    this.addressLike = isLike;
    return this;
  }

  public Long getSpeed() {
    return this.speed;
  }
  public PortalaccountQuery setSpeed(Long speed) {
    this.speed = speed;
    return this;
  }

  public Integer getMaclimit() {
    return this.maclimit;
  }
  public PortalaccountQuery setMaclimit(Integer maclimit) {
    this.maclimit = maclimit;
    return this;
  }

  public Integer getMaclimitcount() {
    return this.maclimitcount;
  }
  public PortalaccountQuery setMaclimitcount(Integer maclimitcount) {
    this.maclimitcount = maclimitcount;
    return this;
  }

  public Integer getAutologin() {
    return this.autologin;
  }
  public PortalaccountQuery setAutologin(Integer autologin) {
    this.autologin = autologin;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public PortalaccountQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public PortalaccountQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public PortalaccountQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public PortalaccountQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public PortalaccountQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public PortalaccountQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public PortalaccountQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public PortalaccountQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public PortalaccountQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public PortalaccountQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public PortalaccountQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public PortalaccountQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public PortalaccountQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public PortalaccountQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public PortalaccountQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public PortalaccountQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public PortalaccountQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public PortalaccountQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public PortalaccountQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public PortalaccountQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public PortalaccountQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyLoginName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyPassword(boolean isAsc)
  {
    this.orderFields.add(new OrderField("password", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyGender(boolean isAsc)
  {
    this.orderFields.add(new OrderField("gender", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyPhoneNumber(boolean isAsc)
  {
    this.orderFields.add(new OrderField("phoneNumber", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEmail(boolean isAsc)
  {
    this.orderFields.add(new OrderField("email", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("date", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyOctets(boolean isAsc)
  {
    this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyIdnumber(boolean isAsc)
  {
    this.orderFields.add(new OrderField("idnumber", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyAddress(boolean isAsc)
  {
    this.orderFields.add(new OrderField("address", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbySpeed(boolean isAsc)
  {
    this.orderFields.add(new OrderField("speed", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyMaclimit(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimit", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyMaclimitcount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("maclimitcount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyAutologin(boolean isAsc)
  {
    this.orderFields.add(new OrderField("autologin", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalaccountQuery orderbyEx10(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("loginName", "loginName");
      fieldMap.put("password", "password");
      fieldMap.put("name", "name");
      fieldMap.put("gender", "gender");
      fieldMap.put("phoneNumber", "phoneNumber");
      fieldMap.put("email", "email");
      fieldMap.put("description", "description");
      fieldMap.put("date", "date");
      fieldMap.put("time", "time");
      fieldMap.put("octets", "octets");
      fieldMap.put("state", "state");
      fieldMap.put("idnumber", "idnumber");
      fieldMap.put("address", "address");
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
 * Qualified Name:     com.leeson.core.query.PortalaccountQuery
 * JD-Core Version:    0.6.2
 */