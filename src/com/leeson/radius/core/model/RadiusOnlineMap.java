package com.leeson.radius.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RadiusOnlineMap
  implements Serializable
{
  private static final long serialVersionUID = -8746602081460381251L;
  private Map<String, String[]> radiusOnlineMap = new ConcurrentHashMap();

  private static RadiusOnlineMap instance = new RadiusOnlineMap();

  public static RadiusOnlineMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getRadiusOnlineMap() {
    return this.radiusOnlineMap;
  }

  public void setRadiusOnlineMap(Map<String, String[]> radiusOnlineMap) {
    this.radiusOnlineMap = radiusOnlineMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.model.RadiusOnlineMap
 * JD-Core Version:    0.6.2
 */