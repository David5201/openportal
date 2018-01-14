package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MacLimitMap
  implements Serializable
{
  private static final long serialVersionUID = 5366716655819873198L;
  private Map<String, String[]> MacLimitMap = new ConcurrentHashMap();

  private static MacLimitMap instance = new MacLimitMap();

  public static MacLimitMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getMacLimitMap() {
    return this.MacLimitMap;
  }

  public void setMacLimitMap(Map<String, String[]> macLimitMap) {
    this.MacLimitMap = macLimitMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.MacLimitMap
 * JD-Core Version:    0.6.2
 */