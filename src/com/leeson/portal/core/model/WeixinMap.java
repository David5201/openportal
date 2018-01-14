package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeixinMap
  implements Serializable
{
  private static final long serialVersionUID = 5220960469752563771L;
  private Map<String, Date> weixinipmap = new ConcurrentHashMap();

  private static WeixinMap instance = new WeixinMap();

  public static WeixinMap getInstance()
  {
    return instance;
  }

  public Map<String, Date> getWeixinipmap() {
    return this.weixinipmap;
  }

  public void setWeixinipmap(Map<String, Date> weixinipmap) {
    this.weixinipmap = weixinipmap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.WeixinMap
 * JD-Core Version:    0.6.2
 */