package com.wxpay.bean;

import java.io.Serializable;

public class WxPayResult
  implements Serializable
{
  private String appid;
  private String bankType;
  private String cashFee;
  private String feeType;
  private String isSubscribe;
  private String mchId;
  private String nonceStr;
  private String openid;
  private String outTradeNo;
  private String resultCode;
  private String returnCode;
  private String sign;
  private String timeEnd;
  private String totalFee;
  private String tradeType;
  private String transactionId;
  private static final long serialVersionUID = -1227026039888867970L;

  public String getAppid()
  {
    return this.appid;
  }

  public void setAppid(String appid)
  {
    this.appid = appid;
  }

  public String getBankType()
  {
    return this.bankType;
  }

  public void setBankType(String bankType)
  {
    this.bankType = bankType;
  }

  public String getCashFee()
  {
    return this.cashFee;
  }

  public void setCashFee(String cashFee)
  {
    this.cashFee = cashFee;
  }

  public String getFeeType()
  {
    return this.feeType;
  }

  public void setFeeType(String feeType)
  {
    this.feeType = feeType;
  }

  public String getIsSubscribe()
  {
    return this.isSubscribe;
  }

  public void setIsSubscribe(String isSubscribe)
  {
    this.isSubscribe = isSubscribe;
  }

  public String getMchId()
  {
    return this.mchId;
  }

  public void setMchId(String mchId)
  {
    this.mchId = mchId;
  }

  public String getNonceStr()
  {
    return this.nonceStr;
  }

  public void setNonceStr(String nonceStr)
  {
    this.nonceStr = nonceStr;
  }

  public String getOpenid()
  {
    return this.openid;
  }

  public void setOpenid(String openid)
  {
    this.openid = openid;
  }

  public String getOutTradeNo()
  {
    return this.outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo)
  {
    this.outTradeNo = outTradeNo;
  }

  public String getResultCode()
  {
    return this.resultCode;
  }

  public void setResultCode(String resultCode)
  {
    this.resultCode = resultCode;
  }

  public String getReturnCode()
  {
    return this.returnCode;
  }

  public void setReturnCode(String returnCode)
  {
    this.returnCode = returnCode;
  }

  public String getSign()
  {
    return this.sign;
  }

  public void setSign(String sign)
  {
    this.sign = sign;
  }

  public String getTimeEnd()
  {
    return this.timeEnd;
  }

  public void setTimeEnd(String timeEnd)
  {
    this.timeEnd = timeEnd;
  }

  public String getTotalFee()
  {
    return this.totalFee;
  }

  public void setTotalFee(String totalFee)
  {
    this.totalFee = totalFee;
  }

  public String getTradeType()
  {
    return this.tradeType;
  }

  public void setTradeType(String tradeType)
  {
    this.tradeType = tradeType;
  }

  public String getTransactionId()
  {
    return this.transactionId;
  }

  public void setTransactionId(String transactionId)
  {
    this.transactionId = transactionId;
  }

  public String toString() {
    return "WxPayResult [appid=" + this.appid + ", bankType=" + this.bankType + 
      ", cashFee=" + this.cashFee + ", feeType=" + this.feeType + 
      ", isSubscribe=" + this.isSubscribe + ", mchId=" + this.mchId + 
      ", nonceStr=" + this.nonceStr + ", openid=" + this.openid + 
      ", outTradeNo=" + this.outTradeNo + ", resultCode=" + this.resultCode + 
      ", returnCode=" + this.returnCode + ", sign=" + this.sign + 
      ", timeEnd=" + this.timeEnd + ", totalFee=" + this.totalFee + 
      ", tradeType=" + this.tradeType + ", transactionId=" + 
      this.transactionId + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.bean.WxPayResult
 * JD-Core Version:    0.6.2
 */