package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.HashMap;

public class RadiusAPIMap
  implements Serializable
{
  private static final long serialVersionUID = 3748694201323103019L;
  private HashMap<String, String> radiusAPIMap = new HashMap();

  private static RadiusAPIMap instance = new RadiusAPIMap();

  private RadiusAPIMap() {
    this.radiusAPIMap.put("state", "0");
    this.radiusAPIMap.put("url", "http:adiusAPI");
  }

  public static RadiusAPIMap getInstance() {
    return instance;
  }

  public HashMap<String, String> getRadiusAPIMap() {
    return this.radiusAPIMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.RadiusAPIMap
 * JD-Core Version:    0.6.2
 */