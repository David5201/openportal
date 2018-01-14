package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WifiDogAPMap
  implements Serializable
{
  private static final long serialVersionUID = -3535488446356014904L;
  private Map<String, String[]> wifiDogAPMap = new ConcurrentHashMap();

  private static WifiDogAPMap instance = new WifiDogAPMap();

  public static WifiDogAPMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getWifiDogAPMap() {
    return this.wifiDogAPMap;
  }

  public void setWifiDogAPMap(Map<String, String[]> wifiDogAPMap) {
    this.wifiDogAPMap = wifiDogAPMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.WifiDogAPMap
 * JD-Core Version:    0.6.2
 */