package com.leeson.core.query;

import com.leeson.common.base.BaseQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryonlineQuery extends BaseQuery
{
  private Long id;
  private Long counts;
  private Date recDate;
  private Integer recYear;
  private Integer recMonth;
  private Integer recDay;
  private Integer recWeek;
  private Integer recTime;
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
  public HistoryonlineQuery setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getCounts() {
    return this.counts;
  }
  public HistoryonlineQuery setCounts(Long counts) {
    this.counts = counts;
    return this;
  }

  public Date getRecDate() {
    return this.recDate;
  }
  public HistoryonlineQuery setRecDate(Date recDate) {
    this.recDate = recDate;
    return this;
  }

  public Integer getRecYear() {
    return this.recYear;
  }
  public HistoryonlineQuery setRecYear(Integer recYear) {
    this.recYear = recYear;
    return this;
  }

  public Integer getRecMonth() {
    return this.recMonth;
  }
  public HistoryonlineQuery setRecMonth(Integer recMonth) {
    this.recMonth = recMonth;
    return this;
  }

  public Integer getRecDay() {
    return this.recDay;
  }
  public HistoryonlineQuery setRecDay(Integer recDay) {
    this.recDay = recDay;
    return this;
  }

  public Integer getRecWeek() {
    return this.recWeek;
  }
  public HistoryonlineQuery setRecWeek(Integer recWeek) {
    this.recWeek = recWeek;
    return this;
  }

  public Integer getRecTime() {
    return this.recTime;
  }
  public HistoryonlineQuery setRecTime(Integer recTime) {
    this.recTime = recTime;
    return this;
  }

  public String getEx1() {
    return this.ex1;
  }
  public HistoryonlineQuery setEx1(String ex1) {
    this.ex1 = ex1;
    return this;
  }

  public HistoryonlineQuery setEx1Like(boolean isLike) {
    this.ex1Like = isLike;
    return this;
  }

  public String getEx2() {
    return this.ex2;
  }
  public HistoryonlineQuery setEx2(String ex2) {
    this.ex2 = ex2;
    return this;
  }

  public HistoryonlineQuery setEx2Like(boolean isLike) {
    this.ex2Like = isLike;
    return this;
  }

  public String getEx3() {
    return this.ex3;
  }
  public HistoryonlineQuery setEx3(String ex3) {
    this.ex3 = ex3;
    return this;
  }

  public HistoryonlineQuery setEx3Like(boolean isLike) {
    this.ex3Like = isLike;
    return this;
  }

  public String getEx4() {
    return this.ex4;
  }
  public HistoryonlineQuery setEx4(String ex4) {
    this.ex4 = ex4;
    return this;
  }

  public HistoryonlineQuery setEx4Like(boolean isLike) {
    this.ex4Like = isLike;
    return this;
  }

  public String getEx5() {
    return this.ex5;
  }
  public HistoryonlineQuery setEx5(String ex5) {
    this.ex5 = ex5;
    return this;
  }

  public HistoryonlineQuery setEx5Like(boolean isLike) {
    this.ex5Like = isLike;
    return this;
  }

  public String getEx6() {
    return this.ex6;
  }
  public HistoryonlineQuery setEx6(String ex6) {
    this.ex6 = ex6;
    return this;
  }

  public HistoryonlineQuery setEx6Like(boolean isLike) {
    this.ex6Like = isLike;
    return this;
  }

  public String getEx7() {
    return this.ex7;
  }
  public HistoryonlineQuery setEx7(String ex7) {
    this.ex7 = ex7;
    return this;
  }

  public HistoryonlineQuery setEx7Like(boolean isLike) {
    this.ex7Like = isLike;
    return this;
  }

  public String getEx8() {
    return this.ex8;
  }
  public HistoryonlineQuery setEx8(String ex8) {
    this.ex8 = ex8;
    return this;
  }

  public HistoryonlineQuery setEx8Like(boolean isLike) {
    this.ex8Like = isLike;
    return this;
  }

  public String getEx9() {
    return this.ex9;
  }
  public HistoryonlineQuery setEx9(String ex9) {
    this.ex9 = ex9;
    return this;
  }

  public HistoryonlineQuery setEx9Like(boolean isLike) {
    this.ex9Like = isLike;
    return this;
  }

  public String getEx10() {
    return this.ex10;
  }
  public HistoryonlineQuery setEx10(String ex10) {
    this.ex10 = ex10;
    return this;
  }

  public HistoryonlineQuery setEx10Like(boolean isLike) {
    this.ex10Like = isLike;
    return this;
  }

  public HistoryonlineQuery orderbyId(boolean isAsc)
  {
    this.orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyCounts(boolean isAsc)
  {
    this.orderFields.add(new OrderField("counts", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyRecDate(boolean isAsc)
  {
    this.orderFields.add(new OrderField("recDate", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyRecYear(boolean isAsc)
  {
    this.orderFields.add(new OrderField("recYear", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyRecMonth(boolean isAsc)
  {
    this.orderFields.add(new OrderField("recMonth", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyRecDay(boolean isAsc)
  {
    this.orderFields.add(new OrderField("recDay", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyRecWeek(boolean isAsc)
  {
    this.orderFields.add(new OrderField("recWeek", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyRecTime(boolean isAsc)
  {
    this.orderFields.add(new OrderField("recTime", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx1(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex1", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx2(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex2", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx3(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex3", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx4(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex4", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx5(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex5", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx6(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex6", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx7(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex7", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx8(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex8", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx9(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex9", isAsc ? "ASC" : "DESC"));
    return this;
  }

  public HistoryonlineQuery orderbyEx10(boolean isAsc)
  {
    this.orderFields.add(new OrderField("ex10", isAsc ? "ASC" : "DESC"));
    return this;
  }

  private static Map<String, String> getFieldSet()
  {
    if (fieldMap == null) {
      fieldMap = new HashMap();
      fieldMap.put("id", "id");
      fieldMap.put("counts", "counts");
      fieldMap.put("recDate", "recDate");
      fieldMap.put("recYear", "recYear");
      fieldMap.put("recMonth", "recMonth");
      fieldMap.put("recDay", "recDay");
      fieldMap.put("recWeek", "recWeek");
      fieldMap.put("recTime", "recTime");
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
 * Qualified Name:     com.leeson.core.query.HistoryonlineQuery
 * JD-Core Version:    0.6.2
 */