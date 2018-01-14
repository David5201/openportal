package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalbasQuery extends BaseQuery
{
  private Long id;
  private String bas;
  private boolean basLike;
  private String basname;
  private boolean basnameLike;
  private String basIp;
  private boolean basIpLike;
  private String basPort;
  private boolean basPortLike;
  private String portalVer;
  private boolean portalVerLike;
  private String authType;
  private boolean authTypeLike;
  private String sharedSecret;
  private boolean sharedSecretLike;
  private String basUser;
  private boolean basUserLike;
  private String basPwd;
  private boolean basPwdLike;
  private String timeoutSec;
  private boolean timeoutSecLike;
  private String isPortalCheck;
  private boolean isPortalCheckLike;
  private String isOut;
  private boolean isOutLike;
  private String authInterface;
  private boolean authInterfaceLike;
  private String isComputer;
  private boolean isComputerLike;
  private Long web;
  private String isdebug;
  private boolean isdebugLike;
  private Integer lateAuth;
  private Long lateAuthTime;
  private Integer isNtf;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalbasQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getBas() {
    return this.bas;
  }
  public PortalbasQuery setBas(String bas) {
    this.bas = bas;
    return this;
  }

  public PortalbasQuery setBasLike(boolean isLike) {
    this.basLike = isLike;
    return this;
  }

  public String getBasname() {
    return this.basname;
  }
  public PortalbasQuery setBasname(String basname) {
    this.basname = basname;
    return this;
  }

  public PortalbasQuery setBasnameLike(boolean isLike) {
    this.basnameLike = isLike;
    return this;
  }

  public String getBasIp() {
    return this.basIp;
  }
  public PortalbasQuery setBasIp(String basIp) {
    this.basIp = basIp;
    return this;
  }

  public PortalbasQuery setBasIpLike(boolean isLike) {
    this.basIpLike = isLike;
    return this;
  }

  public String getBasPort() {
    return this.basPort;
  }
  public PortalbasQuery setBasPort(String basPort) {
    this.basPort = basPort;
    return this;
  }

  public PortalbasQuery setBasPortLike(boolean isLike) {
    this.basPortLike = isLike;
    return this;
  }

  public String getPortalVer() {
    return this.portalVer;
  }
  public PortalbasQuery setPortalVer(String portalVer) {
    this.portalVer = portalVer;
    return this;
  }

  public PortalbasQuery setPortalVerLike(boolean isLike) {
    this.portalVerLike = isLike;
    return this;
  }

  public String getAuthType() {
    return this.authType;
  }
  public PortalbasQuery setAuthType(String authType) {
    this.authType = authType;
    return this;
  }

  public PortalbasQuery setAuthTypeLike(boolean isLike) {
    this.authTypeLike = isLike;
    return this;
  }

  public String getSharedSecret() {
    return this.sharedSecret;
  }
  public PortalbasQuery setSharedSecret(String sharedSecret) {
    this.sharedSecret = sharedSecret;
    return this;
  }

  public PortalbasQuery setSharedSecretLike(boolean isLike) {
    this.sharedSecretLike = isLike;
    return this;
  }

  public String getBasUser() {
    return this.basUser;
  }
  public PortalbasQuery setBasUser(String basUser) {
    this.basUser = basUser;
    return this;
  }

  public PortalbasQuery setBasUserLike(boolean isLike) {
    this.basUserLike = isLike;
    return this;
  }

  public String getBasPwd() {
    return this.basPwd;
  }
  public PortalbasQuery setBasPwd(String basPwd) {
    this.basPwd = basPwd;
    return this;
  }

  public PortalbasQuery setBasPwdLike(boolean isLike) {
    this.basPwdLike = isLike;
    return this;
  }

  public String getTimeoutSec() {
    return this.timeoutSec;
  }
  public PortalbasQuery setTimeoutSec(String timeoutSec) {
    this.timeoutSec = timeoutSec;
    return this;
  }

  public PortalbasQuery setTimeoutSecLike(boolean isLike) {
    this.timeoutSecLike = isLike;
    return this;
  }

  public String getIsPortalCheck() {
    return this.isPortalCheck;
  }
  public PortalbasQuery setIsPortalCheck(String isPortalCheck) {
    this.isPortalCheck = isPortalCheck;
    return this;
  }

  public PortalbasQuery setIsPortalCheckLike(boolean isLike) {
    this.isPortalCheckLike = isLike;
    return this;
  }

  public String getIsOut() {
    return this.isOut;
  }
  public PortalbasQuery setIsOut(String isOut) {
    this.isOut = isOut;
    return this;
  }

  public PortalbasQuery setIsOutLike(boolean isLike) {
    this.isOutLike = isLike;
    return this;
  }

  public String getAuthInterface() {
    return this.authInterface;
  }
  public PortalbasQuery setAuthInterface(String authInterface) {
    this.authInterface = authInterface;
    return this;
  }

  public PortalbasQuery setAuthInterfaceLike(boolean isLike) {
    this.authInterfaceLike = isLike;
    return this;
  }

  public String getIsComputer() {
    return this.isComputer;
  }
  public PortalbasQuery setIsComputer(String isComputer) {
    this.isComputer = isComputer;
    return this;
  }

  public PortalbasQuery setIsComputerLike(boolean isLike) {
    this.isComputerLike = isLike;
    return this;
  }

  public Long getWeb() {
    return this.web;
  }
  public PortalbasQuery setWeb(Long web) {
    this.web = web;
    return this;
  }

  public String getIsdebug() {
    return this.isdebug;
  }
  public PortalbasQuery setIsdebug(String isdebug) {
    this.isdebug = isdebug;
    return this;
  }

  public PortalbasQuery setIsdebugLike(boolean isLike) {
    this.isdebugLike = isLike;
    return this;
  }

  public Integer getLateAuth() {
    return this.lateAuth;
  }
  public PortalbasQuery setLateAuth(Integer lateAuth) {
    this.lateAuth = lateAuth;
    return this;
  }

  public Long getLateAuthTime() {
    return this.lateAuthTime;
  }
  public PortalbasQuery setLateAuthTime(Long lateAuthTime) {
    this.lateAuthTime = lateAuthTime;
    return this;
  }

  public Integer getIsNtf() {
    return this.isNtf;
  }
  public PortalbasQuery setIsNtf(Integer isNtf) {
    this.isNtf = isNtf;
    return this;
  }

  public PortalbasQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyBas(boolean isAsc)
  {
    this.orderFields.add(new OrderField("bas", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyBasname(boolean isAsc)
  {
    this.orderFields.add(new OrderField("basname", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyBasIp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("bas_ip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyBasPort(boolean isAsc)
  {
    this.orderFields.add(new OrderField("bas_port", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyPortalVer(boolean isAsc)
  {
    this.orderFields.add(new OrderField("portalVer", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyAuthType(boolean isAsc)
  {
    this.orderFields.add(new OrderField("authType", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbySharedSecret(boolean isAsc)
  {
    this.orderFields.add(new OrderField("sharedSecret", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyBasUser(boolean isAsc)
  {
    this.orderFields.add(new OrderField("bas_user", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyBasPwd(boolean isAsc)
  {
    this.orderFields.add(new OrderField("bas_pwd", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyTimeoutSec(boolean isAsc)
  {
    this.orderFields.add(new OrderField("timeoutSec", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyIsPortalCheck(boolean isAsc)
  {
    this.orderFields.add(new OrderField("isPortalCheck", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyIsOut(boolean isAsc)
  {
    this.orderFields.add(new OrderField("isOut", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyAuthInterface(boolean isAsc)
  {
    this.orderFields.add(new OrderField("auth_interface", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyIsComputer(boolean isAsc)
  {
    this.orderFields.add(new OrderField("isComputer", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyWeb(boolean isAsc)
  {
    this.orderFields.add(new OrderField("web", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyIsdebug(boolean isAsc)
  {
    this.orderFields.add(new OrderField("isdebug", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyLateAuth(boolean isAsc)
  {
    this.orderFields.add(new OrderField("lateAuth", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyLateAuthTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("lateAuthTime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalbasQuery orderbyIsNtf(boolean isAsc)
  {
    this.orderFields.add(new OrderField("isNtf", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("bas", "bas");
      fieldMap.put("basname", "basname");
      fieldMap.put("basIp", "bas_ip");
      fieldMap.put("basPort", "bas_port");
      fieldMap.put("portalVer", "portalVer");
      fieldMap.put("authType", "authType");
      fieldMap.put("sharedSecret", "sharedSecret");
      fieldMap.put("basUser", "bas_user");
      fieldMap.put("basPwd", "bas_pwd");
      fieldMap.put("timeoutSec", "timeoutSec");
      fieldMap.put("isPortalCheck", "isPortalCheck");
      fieldMap.put("isOut", "isOut");
      fieldMap.put("authInterface", "auth_interface");
      fieldMap.put("isComputer", "isComputer");
      fieldMap.put("web", "web");
      fieldMap.put("isdebug", "isdebug");
      fieldMap.put("lateAuth", "lateAuth");
      fieldMap.put("lateAuthTime", "lateAuthTime");
      fieldMap.put("isNtf", "isNtf");
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
 * Qualified Name:     com.leeson.core.query.PortalbasQuery
 * JD-Core Version:    0.6.2
 */