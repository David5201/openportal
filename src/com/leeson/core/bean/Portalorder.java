package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Portalorder
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private Long payTime;
  private String payType;
  private String state;
  private String cdKey;
  private String categoryType;
  private String accountName;
  private Long accountId;
  private Date payDate;
  private Integer userDel;
  private Integer accountDel;
  private Double money;
  private Date buyDate;
  private Integer payby;
  private String tradeno;
  private String buyer;
  private String seller;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Long getPayTime() {
    return this.payTime;
  }
  public void setPayTime(Long payTime) {
    this.payTime = payTime;
  }
  public String getPayType() {
    return this.payType;
  }
  public void setPayType(String payType) {
    this.payType = payType;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getCdKey() {
    return this.cdKey;
  }
  public void setCdKey(String cdKey) {
    this.cdKey = cdKey;
  }
  public String getCategoryType() {
    return this.categoryType;
  }
  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }
  public String getAccountName() {
    return this.accountName;
  }
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
  public Long getAccountId() {
    return this.accountId;
  }
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
  public Date getPayDate() {
    return this.payDate;
  }
  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }
  public Integer getUserDel() {
    return this.userDel;
  }
  public void setUserDel(Integer userDel) {
    this.userDel = userDel;
  }
  public Integer getAccountDel() {
    return this.accountDel;
  }
  public void setAccountDel(Integer accountDel) {
    this.accountDel = accountDel;
  }
  public Double getMoney() {
    return this.money;
  }
  public void setMoney(Double money) {
    this.money = money;
  }
  public Date getBuyDate() {
    return this.buyDate;
  }
  public void setBuyDate(Date buyDate) {
    this.buyDate = buyDate;
  }
  public Integer getPayby() {
    return this.payby;
  }
  public void setPayby(Integer payby) {
    this.payby = payby;
  }
  public String getTradeno() {
    return this.tradeno;
  }
  public void setTradeno(String tradeno) {
    this.tradeno = tradeno;
  }
  public String getBuyer() {
    return this.buyer;
  }
  public void setBuyer(String buyer) {
    this.buyer = buyer;
  }
  public String getSeller() {
    return this.seller;
  }
  public void setSeller(String seller) {
    this.seller = seller;
  }
  public String toString() {
    return "Portalorder [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",payTime=" + this.payTime + ",payType=" + this.payType + ",state=" + this.state + ",cdKey=" + this.cdKey + ",categoryType=" + this.categoryType + ",accountName=" + this.accountName + ",accountId=" + this.accountId + ",payDate=" + this.payDate + ",userDel=" + this.userDel + ",accountDel=" + this.accountDel + ",money=" + this.money + ",buyDate=" + this.buyDate + ",payby=" + this.payby + ",tradeno=" + this.tradeno + ",buyer=" + this.buyer + ",seller=" + this.seller + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalorder
 * JD-Core Version:    0.6.2
 */