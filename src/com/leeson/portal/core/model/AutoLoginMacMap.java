package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutoLoginMacMap
  implements Serializable
{
  private static final long serialVersionUID = 5366716655819873198L;
  private Map<String, String[]> AutoLoginMacMap = new ConcurrentHashMap();

  private static AutoLoginMacMap instance = new AutoLoginMacMap();

  public static AutoLoginMacMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getAutoLoginMacMap() {
    return this.AutoLoginMacMap;
  }

  public void setAutoLoginMacMap(Map<String, String[]> autoLoginMacMap) {
    this.AutoLoginMacMap = autoLoginMacMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AutoLoginMacMap
 * JD-Core Version:    0.6.2
 */