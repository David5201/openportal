package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalmessageQuery extends BaseQuery
{
  private Long id;
  private String title;
  private boolean titleLike;
  private String description;
  private boolean descriptionLike;
  private Date date;
  private String state;
  private boolean stateLike;
  private String fromid;
  private boolean fromidLike;
  private String toid;
  private boolean toidLike;
  private String ip;
  private boolean ipLike;
  private String toname;
  private boolean tonameLike;
  private String fromname;
  private boolean fromnameLike;
  private String delin;
  private boolean delinLike;
  private String delout;
  private boolean deloutLike;
  private String fromPos;
  private boolean fromPosLike;
  private String toPos;
  private boolean toPosLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortalmessageQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return this.title;
  }
  public PortalmessageQuery setTitle(String title) {
    this.title = title;
    return this;
  }

  public PortalmessageQuery setTitleLike(boolean isLike) {
    this.titleLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public PortalmessageQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public PortalmessageQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Date getDate() {
    return this.date;
  }
  public PortalmessageQuery setDate(Date date) {
    this.date = date;
    return this;
  }

  public String getState() {
    return this.state;
  }
  public PortalmessageQuery setState(String state) {
    this.state = state;
    return this;
  }

  public PortalmessageQuery setStateLike(boolean isLike) {
    this.stateLike = isLike;
    return this;
  }

  public String getFromid() {
    return this.fromid;
  }
  public PortalmessageQuery setFromid(String fromid) {
    this.fromid = fromid;
    return this;
  }

  public PortalmessageQuery setFromidLike(boolean isLike) {
    this.fromidLike = isLike;
    return this;
  }

  public String getToid() {
    return this.toid;
  }
  public PortalmessageQuery setToid(String toid) {
    this.toid = toid;
    return this;
  }

  public PortalmessageQuery setToidLike(boolean isLike) {
    this.toidLike = isLike;
    return this;
  }

  public String getIp() {
    return this.ip;
  }
  public PortalmessageQuery setIp(String ip) {
    this.ip = ip;
    return this;
  }

  public PortalmessageQuery setIpLike(boolean isLike) {
    this.ipLike = isLike;
    return this;
  }

  public String getToname() {
    return this.toname;
  }
  public PortalmessageQuery setToname(String toname) {
    this.toname = toname;
    return this;
  }

  public PortalmessageQuery setTonameLike(boolean isLike) {
    this.tonameLike = isLike;
    return this;
  }

  public String getFromname() {
    return this.fromname;
  }
  public PortalmessageQuery setFromname(String fromname) {
    this.fromname = fromname;
    return this;
  }

  public PortalmessageQuery setFromnameLike(boolean isLike) {
    this.fromnameLike = isLike;
    return this;
  }

  public String getDelin() {
    return this.delin;
  }
  public PortalmessageQuery setDelin(String delin) {
    this.delin = delin;
    return this;
  }

  public PortalmessageQuery setDelinLike(boolean isLike) {
    this.delinLike = isLike;
    return this;
  }

  public String getDelout() {
    return this.delout;
  }
  public PortalmessageQuery setDelout(String delout) {
    this.delout = delout;
    return this;
  }

  public PortalmessageQuery setDeloutLike(boolean isLike) {
    this.deloutLike = isLike;
    return this;
  }

  public String getFromPos() {
    return this.fromPos;
  }
  public PortalmessageQuery setFromPos(String fromPos) {
    this.fromPos = fromPos;
    return this;
  }

  public PortalmessageQuery setFromPosLike(boolean isLike) {
    this.fromPosLike = isLike;
    return this;
  }

  public String getToPos() {
    return this.toPos;
  }
  public PortalmessageQuery setToPos(String toPos) {
    this.toPos = toPos;
    return this;
  }

  public PortalmessageQuery setToPosLike(boolean isLike) {
    this.toPosLike = isLike;
    return this;
  }

  public PortalmessageQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyTitle(boolean isAsc)
  {
    this.orderFields.add(new OrderField("title", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("date", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyFromid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("fromid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyToid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("toid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyIp(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ip", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyToname(boolean isAsc)
  {
    this.orderFields.add(new OrderField("toname", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyFromname(boolean isAsc)
  {
    this.orderFields.add(new OrderField("fromname", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyDelin(boolean isAsc)
  {
    this.orderFields.add(new OrderField("delin", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyDelout(boolean isAsc)
  {
    this.orderFields.add(new OrderField("delout", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyFromPos(boolean isAsc)
  {
    this.orderFields.add(new OrderField("fromPos", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortalmessageQuery orderbyToPos(boolean isAsc)
  {
    this.orderFields.add(new OrderField("toPos", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("title", "title");
      fieldMap.put("description", "description");
      fieldMap.put("date", "date");
      fieldMap.put("state", "state");
      fieldMap.put("fromid", "fromid");
      fieldMap.put("toid", "toid");
      fieldMap.put("ip", "ip");
      fieldMap.put("toname", "toname");
      fieldMap.put("fromname", "fromname");
      fieldMap.put("delin", "delin");
      fieldMap.put("delout", "delout");
      fieldMap.put("fromPos", "fromPos");
      fieldMap.put("toPos", "toPos");
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
 * Qualified Name:     com.leeson.core.query.PortalmessageQuery
 * JD-Core Version:    0.6.2
 */