package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortallogrecordQuery extends BaseQuery
{
  private Long id;
  private String info;
  private boolean infoLike;
  private Date recDate;
  private List<OrderField> orderFields = new ArrayList();
  private String fields;
  private static Map<String, String> fieldMap;

  public Long getId()
  {
    return this.id;
  }
  public PortallogrecordQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public String getInfo() {
    return this.info;
  }
  public PortallogrecordQuery setInfo(String info) {
    this.info = info;
    return this;
  }

  public PortallogrecordQuery setInfoLike(boolean isLike) {
    this.infoLike = isLike;
    return this;
  }

  public Date getRecDate() {
    return this.recDate;
  }
  public PortallogrecordQuery setRecDate(Date recDate) {
    this.recDate = recDate;
    return this;
  }

  public PortallogrecordQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallogrecordQuery orderbyInfo(boolean isAsc)
  {
    this.orderFields.add(new OrderField("info", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public PortallogrecordQuery orderbyRecDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("rec_date", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("info", "info");
      fieldMap.put("recDate", "rec_date");
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
 * Qualified Name:     com.leeson.core.query.PortallogrecordQuery
 * JD-Core Version:    0.6.2
 */