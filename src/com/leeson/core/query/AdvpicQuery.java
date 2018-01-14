package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvpicQuery extends BaseQuery
{
  private Long id;
  private String name;
  private boolean nameLike;
  private Long uid;
  private Long sid;
  private Long aid;
  private Long pos;
  private String img;
  private boolean imgLike;
  private String url;
  private boolean urlLike;
  private Long showCount;
  private Long clickCount;
  private String imgW;
  private boolean imgWLike;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public AdvpicQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }
  public AdvpicQuery setName(String name) {
    this.name = name;
    return this;
  }

  public AdvpicQuery setNameLike(boolean isLike) {
    this.nameLike = isLike;
    return this;
  }

  public Long getUid() {
    return this.uid;
  }
  public AdvpicQuery setUid(Long uid) {
    this.uid = uid;
    return this;
  }

  public Long getSid() {
    return this.sid;
  }
  public AdvpicQuery setSid(Long sid) {
    this.sid = sid;
    return this;
  }

  public Long getAid() {
    return this.aid;
  }
  public AdvpicQuery setAid(Long aid) {
    this.aid = aid;
    return this;
  }

  public Long getPos() {
    return this.pos;
  }
  public AdvpicQuery setPos(Long pos) {
    this.pos = pos;
    return this;
  }

  public String getImg() {
    return this.img;
  }
  public AdvpicQuery setImg(String img) {
    this.img = img;
    return this;
  }

  public AdvpicQuery setImgLike(boolean isLike) {
    this.imgLike = isLike;
    return this;
  }

  public String getUrl() {
    return this.url;
  }
  public AdvpicQuery setUrl(String url) {
    this.url = url;
    return this;
  }

  public AdvpicQuery setUrlLike(boolean isLike) {
    this.urlLike = isLike;
    return this;
  }

  public Long getShowCount() {
    return this.showCount;
  }
  public AdvpicQuery setShowCount(Long showCount) {
    this.showCount = showCount;
    return this;
  }

  public Long getClickCount() {
    return this.clickCount;
  }
  public AdvpicQuery setClickCount(Long clickCount) {
    this.clickCount = clickCount;
    return this;
  }

  public String getImgW() {
    return this.imgW;
  }
  public AdvpicQuery setImgW(String imgW) {
    this.imgW = imgW;
    return this;
  }

  public AdvpicQuery setImgWLike(boolean isLike) {
    this.imgWLike = isLike;
    return this;
  }

  public AdvpicQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyName(boolean isAsc)
  {
    this.orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyUid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("uid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbySid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("sid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyAid(boolean isAsc)
  {
    this.orderFields.add(new OrderField("aid", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyPos(boolean isAsc)
  {
    this.orderFields.add(new OrderField("pos", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyImg(boolean isAsc)
  {
    this.orderFields.add(new OrderField("img", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyUrl(boolean isAsc)
  {
    this.orderFields.add(new OrderField("url", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyShowCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("showCount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyClickCount(boolean isAsc)
  {
    this.orderFields.add(new OrderField("clickCount", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public AdvpicQuery orderbyImgW(boolean isAsc)
  {
    this.orderFields.add(new OrderField("imgW", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("name", "name");
      fieldMap.put("uid", "uid");
      fieldMap.put("sid", "sid");
      fieldMap.put("aid", "aid");
      fieldMap.put("pos", "pos");
      fieldMap.put("img", "img");
      fieldMap.put("url", "url");
      fieldMap.put("showCount", "showCount");
      fieldMap.put("clickCount", "clickCount");
      fieldMap.put("imgW", "imgW");
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
 * Qualified Name:     com.leeson.core.query.AdvpicQuery
 * JD-Core Version:    0.6.2
 */