package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineMap
  implements Serializable
{
  private static final long serialVersionUID = 5220960469752563771L;
  private Map<String, String[]> OnlineUserMap = new ConcurrentHashMap();

  private static OnlineMap instance = new OnlineMap();

  public static OnlineMap getInstance()
  {
    return instance;
  }

  public static void setInstance(OnlineMap instance) {
    instance = instance;
  }

  public Map<String, String[]> getOnlineUserMap() {
    return this.OnlineUserMap;
  }

  public void setOnlineUserMap(Map<String, String[]> onlineUserMap) {
    this.OnlineUserMap = onlineUserMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.OnlineMap
 * JD-Core Version:    0.6.2
 */