package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ipMap
  implements Serializable
{
  private static final long serialVersionUID = 5220960469752563771L;
  private Map<String, Integer> ipmap = new ConcurrentHashMap();

  private static ipMap instance = new ipMap();

  public static ipMap getInstance()
  {
    return instance;
  }

  public Map<String, Integer> getIpmap() {
    return this.ipmap;
  }

  public void setIpmap(Map<String, Integer> ipmap) {
    this.ipmap = ipmap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.ipMap
 * JD-Core Version:    0.6.2
 */