package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortallinkrecordallQuery extends BaseQuery
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
  private String methodtype;
  private boolean methodtypeLike;
  private String mac;
  private boolean macLike;
  private String basname;
  private boolean basnameLike;
  private String ssid;
  private boolean ssidLike;
  private String apmac;
  private boolean apmacLike;
  private String auto;
  private boolean autoLike;
  private String agent;
  private boolean agentLike;
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
  public PortallinkrecordallQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getIp() {
    return this.ip;
  }
  public PortallinkrecordallQuery setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public PortallinkrecordallQuery setIpLike(boolean isLike) {
    this.ipLike = isLike;
    return this;
  }

  public String getBasip() {
    return this.basip;
  }
  public PortallinkrecordallQuery setBasip(String basip) {
    this.basip = basip;
    return this;
  }

  public PortallinkrecordallQuery setBasipLike(boolean isLike) {
    this.basipLike = isLike;
    return this;
  }

  public String getLoginName() {
    return this.loginName;
  }
  public PortallinkrecordallQuery setLoginName(String loginName) {
    this.loginName = loginName;
    return this;
  }

  public PortallinkrecordallQuery setLoginNameLike(boolean isLike) {
    this.loginNameLike = isLike;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortallinkrecordallQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortallinkrecordallQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public Date getStartDate() {
    return this.startDate;
  }
  public PortallinkrecordallQuery setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public Date getEndDate() {
    return this.endDate;
  }
  public PortallinkrecordallQuery setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public Long getTime() {
    return this.time;
  }
  public PortallinkrecordallQuery setTime(Long time) {
    this.time = time;
    return this;
  }

  public Long getIns() {
    return this.ins;
  }
  public PortallinkrecordallQuery setIns(Long ins) {
    this.ins = ins;
    return this;
  }

  public Long getOuts() {
    return this.outs;
  }
  public PortallinkrecordallQuery setOuts(Long outs) {
    this.outs = outs;
    return this;
  }

  public Long getOctets() {
    return this.octets;
  }
  public PortallinkrecordallQuery setOctets(Long octets) {
    this.octets = octets;
    return this;
  }

  public String getMethodtype() {
    return this.methodtype;
  }
  public PortallinkrecordallQuery setMethodtype(String methodtype) {
    this.methodtype = methodtype;
    return this;
  }

  public PortallinkrecordallQuery setMethodtypeLike(boolean isLike) {
    this.methodtypeLike = isLike;
    return this;
  }

  public String getMac() {
    return this.mac;
  }
  public PortallinkrecordallQuery setMac(String mac) {
    this.mac = mac;
    return this;
  }

  public PortallinkrecordallQuery setMacLike(boolean isLike) {
    this.macLike = isLike;
    return this;
  }

  public String getBasname() {
    return this.basname;
  }
  public PortallinkrecordallQuery setBasname(String basname) {
    this.basname = basname;
    return this;
  }

  public PortallinkrecordallQuery setBasnameLike(boolean isLike) {
    this.basnameLike = isLike;
    return this;
  }

  public String getSsid() {
    return this.ssid;
  }
  public PortallinkrecordallQuery setSsid(String ssid) {
    this.ssid = ssid;
    return this;
  }

  public PortallinkrecordallQuery setSsidLike(boolean isLike) {
    this.ssidLike = isLike;
    return this;
  }

  public String getApmac() {
    return this.apmac;
  }
  public PortallinkrecordallQuery setApmac(String apmac) {
    this.apmac = apmac;
    return this;
  }

  public PortallinkrecordallQuery setApmacLike(boolean isLike) {
    this.apmacLike = isLike;
    return this;
  }

  public String getAuto() {
    return this.auto;
  }
  public PortallinkrecordallQuery setAuto(String auto) {
    this.auto = auto;
    return this;
  }

  public PortallinkrecordallQuery setAutoLike(boolean isLike) {
    this.autoLike = isLike;
    return this;
  }

  public String getAgent() {
    return this.agent;
  }
  public PortallinkrecordallQuery setAgent(String agent) {
    this.agent = agent;
    return this;
  }

  public PortallinkrecordallQuery setAgentLike(boolean isLike) {
    this.agentLike = isLike;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public PortallinkrecordallQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public PortallinkrecordallQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public PortallinkrecordallQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public PortallinkrecordallQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public PortallinkrecordallQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public PortallinkrecordallQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public PortallinkrecordallQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public PortallinkrecordallQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public PortallinkrecordallQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public PortallinkrecordallQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public PortallinkrecordallQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public PortallinkrecordallQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public PortallinkrecordallQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public PortallinkrecordallQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public PortallinkrecordallQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public PortallinkrecordallQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public PortallinkrecordallQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public PortallinkrecordallQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public PortallinkrecordallQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public PortallinkrecordallQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public PortallinkrecordallQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyIp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyBasip(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyLoginName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("loginName", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyStartDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("startDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEndDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("time", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyIns(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ins", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyOuts(boolean isAsc)
  {
    this.orderFields.add(new OrderField("outs", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyOctets(boolean isAsc)
  {
    this.orderFields.add(new OrderField("octets", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyMethodtype(boolean isAsc)
  {
    this.orderFields.add(new OrderField("methodtype", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyMac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("mac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyBasname(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basname", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbySsid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ssid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyApmac(boolean isAsc)
  {
    this.orderFields.add(new OrderField("apmac", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyAuto(boolean isAsc)
  {
    this.orderFields.add(new OrderField("auto", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyAgent(boolean isAsc)
  {
    this.orderFields.add(new OrderField("agent", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallinkrecordallQuery orderbyEx10(boolean isAsc)
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
      fieldMap.put("methodtype", "methodtype");
      fieldMap.put("mac", "mac");
      fieldMap.put("basname", "basname");
      fieldMap.put("ssid", "ssid");
      fieldMap.put("apmac", "apmac");
      fieldMap.put("auto", "auto");
      fieldMap.put("agent", "agent");
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
 * Qualified Name:     com.leeson.core.query.PortallinkrecordallQuery
 * JD-Core Version:    0.6.2
 */