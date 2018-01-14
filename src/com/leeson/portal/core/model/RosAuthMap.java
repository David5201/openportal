package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RosAuthMap
  implements Serializable
{
  private static final long serialVersionUID = 6693407912961755301L;
  private Map<String, String[]> rosAuthMap = new ConcurrentHashMap();

  private static RosAuthMap instance = new RosAuthMap();

  public static RosAuthMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getRosAuthMap() {
    return this.rosAuthMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.RosAuthMap
 * JD-Core Version:    0.6.2
 */