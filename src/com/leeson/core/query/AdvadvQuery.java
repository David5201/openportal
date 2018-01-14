package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvadvQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private Date creatDate;
  private Integer state;
  private Date showDate;
  private Date endDate;
  private Long uid;
  private Long sid;
  private Long pos;
  private String img;
  private boolean imgLike;
  private Integer showName;
  private Integer showInfo;
  private Integer showImg;
  private Long showCount;
  private Long clickCount;
  private String url;
  private boolean urlLike;
  private Long lockTime;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public AdvadvQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public AdvadvQuery setName(String name) {
    this.name = name;
    return this;
  }

  public AdvadvQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public AdvadvQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public AdvadvQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Date getCreatDate() {
    return this.creatDate;
  }
  public AdvadvQuery setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
    return this;
  }

  public Integer getState() {
    return this.state;
  }
  public AdvadvQuery setState(Integer state) {
    this.state = state;
    return this;
  }

  public Date getShowDate() {
    return this.showDate;
  }
  public AdvadvQuery setShowDate(Date showDate) {
    this.showDate = showDate;
    return this;
  }

  public Date getEndDate() {
    return this.endDate;
  }
  public AdvadvQuery setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public Long getUid() {
    return this.uid;
  }
  public AdvadvQuery setUid(Long uid) {
    this.uid = uid;
    return this;
  }

  public Long getSid() {
    return this.sid;
  }
  public AdvadvQuery setSid(Long sid) {
    this.sid = sid;
    return this;
  }

  public Long getPos() {
    return this.pos;
  }
  public AdvadvQuery setPos(Long pos) {
    this.pos = pos;
    return this;
  }

  public String getImg() {
    return this.img;
  }
  public AdvadvQuery setImg(String img) {
    this.img = img;
    return this;
  }

  public AdvadvQuery setImgLike(boolean isLike) {
    this.imgLike = isLike;
    return this;
  }

  public Integer getShowName() {
    return this.showName;
  }
  public AdvadvQuery setShowName(Integer showName) {
    this.showName = showName;
    return this;
  }

  public Integer getShowInfo() {
    return this.showInfo;
  }
  public AdvadvQuery setShowInfo(Integer showInfo) {
    this.showInfo = showInfo;
    return this;
  }

  public Integer getShowImg() {
    return this.showImg;
  }
  public AdvadvQuery setShowImg(Integer showImg) {
    this.showImg = showImg;
    return this;
  }

  public Long getShowCount() {
    return this.showCount;
  }
  public AdvadvQuery setShowCount(Long showCount) {
    this.showCount = showCount;
    return this;
  }

  public Long getClickCount() {
    return this.clickCount;
  }
  public AdvadvQuery setClickCount(Long clickCount) {
    this.clickCount = clickCount;
    return this;
  }

  public String getUrl() {
    return this.url;
  }
  public AdvadvQuery setUrl(String url) {
    this.url = url;
    return this;
  }

  public AdvadvQuery setUrlLike(boolean isLike) {
    this.urlLike = isLike;
    return this;
  }

  public Long getLockTime() {
    return this.lockTime;
  }
  public AdvadvQuery setLockTime(Long lockTime) {
    this.lockTime = lockTime;
    return this;
  }

  public AdvadvQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyCreatDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyState(boolean isAsc)
  {
    this.orderFields.add(new OrderField("state", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyShowDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("showDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyEndDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("endDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyUid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbySid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("sid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyPos(boolean isAsc)
  {
    this.orderFields.add(new OrderField("pos", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyImg(boolean isAsc)
  {
    this.orderFields.add(new OrderField("img", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyShowName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("showName", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyShowInfo(boolean isAsc)
  {
    this.orderFields.add(new OrderField("showInfo", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyShowImg(boolean isAsc)
  {
    this.orderFields.add(new OrderField("showImg", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyShowCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("showCount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyClickCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("clickCount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyUrl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvadvQuery orderbyLockTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("lockTime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("description", "description");
      fieldMap.put("creatDate", "creatDate");
      fieldMap.put("state", "state");
      fieldMap.put("showDate", "showDate");
      fieldMap.put("endDate", "endDate");
      fieldMap.put("uid", "uid");
      fieldMap.put("sid", "sid");
      fieldMap.put("pos", "pos");
      fieldMap.put("img", "img");
      fieldMap.put("showName", "showName");
      fieldMap.put("showInfo", "showInfo");
      fieldMap.put("showImg", "showImg");
      fieldMap.put("showCount", "showCount");
      fieldMap.put("clickCount", "clickCount");
      fieldMap.put("url", "url");
      fieldMap.put("lockTime", "lockTime");
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
 * Qualified Name:     com.leeson.core.query.AdvadvQuery
 * JD-Core Version:    0.6.2
 */