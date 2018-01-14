package com.wxpay.bean;

import java.io.Serializable;

public class WxPayDto
  implements Serializable
{
  private static final long serialVersionUID = -4641374184740720071L;
  private String orderId;
  private String totalFee;
  private String spbillCreateIp;
  private String notifyUrl;
  private String body;
  private String openId;

  public String getOrderId()
  {
    return this.orderId;
  }

  public void setOrderId(String orderId)
  {
    this.orderId = orderId;
  }

  public String getTotalFee()
  {
    return this.totalFee;
  }

  public void setTotalFee(String totalFee)
  {
    this.totalFee = totalFee;
  }

  public String getSpbillCreateIp()
  {
    return this.spbillCreateIp;
  }

  public void setSpbillCreateIp(String spbillCreateIp)
  {
    this.spbillCreateIp = spbillCreateIp;
  }

  public String getNotifyUrl()
  {
    return this.notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl)
  {
    this.notifyUrl = notifyUrl;
  }

  public String getBody()
  {
    return this.body;
  }

  public void setBody(String body)
  {
    this.body = body;
  }

  public String getOpenId()
  {
    return this.openId;
  }

  public void setOpenId(String openId)
  {
    this.openId = openId;
  }

  public String toString() {
    return "WxPayDto [orderId=" + this.orderId + ", totalFee=" + this.totalFee + 
      ", spbillCreateIp=" + this.spbillCreateIp + ", notifyUrl=" + 
      this.notifyUrl + ", body=" + this.body + ", openId=" + this.openId + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.bean.WxPayDto
 * JD-Core Version:    0.6.2
 */