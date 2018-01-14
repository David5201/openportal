package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutoLoginMap
  implements Serializable
{
  private static final long serialVersionUID = 4486367212242440408L;
  private Map<String, String[]> autoLoginMap = new ConcurrentHashMap();

  private static AutoLoginMap instance = new AutoLoginMap();

  public static AutoLoginMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getAutoLoginMap() {
    return this.autoLoginMap;
  }

  public void setAutoLoginMap(Map<String, String[]> autoLoginMap) {
    this.autoLoginMap = autoLoginMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AutoLoginMap
 * JD-Core Version:    0.6.2
 */