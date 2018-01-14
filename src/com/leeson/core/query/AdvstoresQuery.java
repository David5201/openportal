package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvstoresQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private String description;
  private boolean descriptionLike;
  private Date creatDate;
  private Long uid;
  private String address;
  private boolean addressLike;
  private String phone;
  private boolean phoneLike;
  private String img;
  private boolean imgLike;
  private Integer showInfo;
  private String x;
  private boolean xLike;
  private String y;
  private boolean yLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public AdvstoresQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public AdvstoresQuery setName(String name) {
    this.name = name;
    return this;
  }

  public AdvstoresQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public AdvstoresQuery setDescription(String description) {
    this.description = description;
    return this;
  }

  public AdvstoresQuery setDescriptionLike(boolean isLike) {
    this.descriptionLike = isLike;
    return this;
  }

  public Date getCreatDate() {
    return this.creatDate;
  }
  public AdvstoresQuery setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
    return this;
  }

  public Long getUid() {
    return this.uid;
  }
  public AdvstoresQuery setUid(Long uid) {
    this.uid = uid;
    return this;
  }

  public String getAddress() {
    return this.address;
  }
  public AdvstoresQuery setAddress(String address) {
    this.address = address;
    return this;
  }

  public AdvstoresQuery setAddressLike(boolean isLike) {
    this.addressLike = isLike;
    return this;
  }

  public String getPhone() {
    return this.phone;
  }
  public AdvstoresQuery setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public AdvstoresQuery setPhoneLike(boolean isLike) {
    this.phoneLike = isLike;
    return this;
  }

  public String getImg() {
    return this.img;
  }
  public AdvstoresQuery setImg(String img) {
    this.img = img;
    return this;
  }

  public AdvstoresQuery setImgLike(boolean isLike) {
    this.imgLike = isLike;
    return this;
  }

  public Integer getShowInfo() {
    return this.showInfo;
  }
  public AdvstoresQuery setShowInfo(Integer showInfo) {
    this.showInfo = showInfo;
    return this;
  }

  public String getX() {
    return this.x;
  }
  public AdvstoresQuery setX(String x) {
    this.x = x;
    return this;
  }

  public AdvstoresQuery setXLike(boolean isLike) {
    this.xLike = isLike;
    return this;
  }

  public String getY() {
    return this.y;
  }
  public AdvstoresQuery setY(String y) {
    this.y = y;
    return this;
  }

  public AdvstoresQuery setYLike(boolean isLike) {
    this.yLike = isLike;
    return this;
  }

  public AdvstoresQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyDescription(boolean isAsc)
  {
    this.orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyCreatDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("creatDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyUid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyAddress(boolean isAsc)
  {
    this.orderFields.add(new OrderField("address", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyPhone(boolean isAsc)
  {
    this.orderFields.add(new OrderField("phone", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyImg(boolean isAsc)
  {
    this.orderFields.add(new OrderField("img", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyShowInfo(boolean isAsc)
  {
    this.orderFields.add(new OrderField("showInfo", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyX(boolean isAsc)
  {
    this.orderFields.add(new OrderField("x", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvstoresQuery orderbyY(boolean isAsc)
  {
    this.orderFields.add(new OrderField("y", isAsc ? "ASC" : "DESC"));
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
      fieldMap.put("uid", "uid");
      fieldMap.put("address", "address");
      fieldMap.put("phone", "phone");
      fieldMap.put("img", "img");
      fieldMap.put("showInfo", "showInfo");
      fieldMap.put("x", "x");
      fieldMap.put("y", "y");
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
 * Qualified Name:     com.leeson.core.query.AdvstoresQuery
 * JD-Core Version:    0.6.2
 */