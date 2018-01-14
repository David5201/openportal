package com.leeson.portal.core.model;

import java.io.Serializable;

public class WeixinSMSMap
  implements Serializable
{
  private static final long serialVersionUID = 3996588398323647432L;
  private String pwd = "OpenPortal";

  private static WeixinSMSMap instance = new WeixinSMSMap();

  public static WeixinSMSMap getInstance()
  {
    return instance;
  }

  public String getPwd() {
    return this.pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.WeixinSMSMap
 * JD-Core Version:    0.6.2
 */