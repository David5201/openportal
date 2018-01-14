package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadiuslinkrecordallQuery extends BaseQuery
{
  private Long id;
  private String nasip;
  private boolean nasipLike;
  private String sourceip;
  private boolean sourceipLike;
  private String userip;
  private boolean useripLike;
  private String callingstationid;
  private boolean callingstationidLike;
  private String name;
  private boolean nameLike;
  private String state;
  private boolean stateLike;
  private Date startDate;
  private Date endDate;
  private Long time;
  private Long ins;
  private Long outs;
  private Long octets;
  private String acctsessionid;
  private boolean acctsessionidLike;
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
  public RadiuslinkrecordallQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getNasip() {
    return this.nasip;
  }
  public RadiuslinkrecordallQuery setNasip(String nasip) {
    this.nasip = nasip;
    return this;
  }

  public RadiuslinkrecordallQuery setNasipLike(boolean isLike) {
    this.nasipLike = isLike;
    return this;
  }

  public String getSourceip() {
    return this.sourceip;
  }
  public RadiuslinkrecordallQuery setSourceip(String sourceip) {
    this.sourceip = sourceip;
    return this;
  }

  public RadiuslinkrecordallQuery setSourceipLike(boolean isLike) {
    this.sourceipLike = isLike;
    return this;
  }

  public String getUserip() {
    return this.userip;
  }
  public RadiuslinkrecordallQuery setUserip(String userip) {
    this.userip = userip;
    return this;
  }

  public RadiuslinkrecordallQuery setUseripLike(boolean isLike) {
    this.useripLike = isLike;
    return this;
  }

  public String getCallingstationid() {
    return this.callingstationid;
  }
  public RadiuslinkrecordallQuery setCallingstationid(String callingstationid) {
    this.callingstationid = callingstationid;
    return this;
  }

  public RadiuslinkrecordallQuery setCallingstationidLike(boolean isLike) {
    this.callingstationidLike = isLike;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public RadiuslinkrecordallQuery setName(String name) {
    this.name = name;
    return this;
  }

  public RadiuslinkrecordallQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public RadiuslinkrecordallQuery setState(String state) {
    this.state = state;
    return this;
  }

  public RadiuslinkrecordallQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public Date getStartDate() {
    return this.startDate;
  }
  public RadiuslinkrecordallQuery setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public Date getEndDate() {
    return this.endDate;
  }
  public RadiuslinkrecordallQuery setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public Long getTime() {
    return this.time;
  }
  public RadiuslinkrecordallQuery setTime(Long time) {
    this.time = time;
    return this;
  }

  public Long getIns() {
    return this.ins;
  }
  public RadiuslinkrecordallQuery setIns(Long ins) {
    this.ins = ins;
    return this;
  }

  public Long getOuts() {
    return this.outs;
  }
  public RadiuslinkrecordallQuery setOuts(Long outs) {
    this.outs = outs;
    return this;
  }

  public Long getOctets() {
    return this.octets;
  }
  public RadiuslinkrecordallQuery setOctets(Long octets) {
    this.octets = octets;
    return this;
  }

  public String getAcctsessionid() {
    return this.acctsessionid;
  }
  public RadiuslinkrecordallQuery setAcctsessionid(String acctsessionid) {
    this.acctsessionid = acctsessionid;
    return this;
  }

  public RadiuslinkrecordallQuery setAcctsessionidLike(boolean isLike) {
    this.acctsessionidLike = isLike;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public RadiuslinkrecordallQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public RadiuslinkrecordallQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public RadiuslinkrecordallQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public RadiuslinkrecordallQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public RadiuslinkrecordallQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public RadiuslinkrecordallQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public RadiuslinkrecordallQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public RadiuslinkrecordallQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public RadiuslinkrecordallQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public RadiuslinkrecordallQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public RadiuslinkrecordallQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public RadiuslinkrecordallQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public RadiuslinkrecordallQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public RadiuslinkrecordallQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public RadiuslinkrecordallQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public RadiuslinkrecordallQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public RadiuslinkrecordallQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public RadiuslinkrecordallQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public RadiuslinkrecordallQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public RadiuslinkrecordallQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public RadiuslinkrecordallQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyNasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("nasip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbySourceip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("sourceip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyUserip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("userip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyCallingstationid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("callingstationid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyStartDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("startDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEndDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyIns(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ins", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyOuts(boolean isAsc)
  {
    this.orderFields.add(new OrderField("outs", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyOctets(boolean isAsc)
  {
    this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyAcctsessionid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("acctsessionid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public RadiuslinkrecordallQuery orderbyEx10(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("nasip", "nasip");
      fieldMap.put("sourceip", "sourceip");
      fieldMap.put("userip", "userip");
      fieldMap.put("callingstationid", "callingstationid");
      fieldMap.put("name", "name");
      fieldMap.put("state", "state");
      fieldMap.put("startDate", "startDate");
      fieldMap.put("endDate", "endDate");
      fieldMap.put("time", "time");
      fieldMap.put("ins", "ins");
      fieldMap.put("outs", "outs");
      fieldMap.put("octets", "octets");
      fieldMap.put("acctsessionid", "acctsessionid");
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
 * Qualified Name:     com.leeson.core.query.RadiuslinkrecordallQuery
 * JD-Core Version:    0.6.2
 */