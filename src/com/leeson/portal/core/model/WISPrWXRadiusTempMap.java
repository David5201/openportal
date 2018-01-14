package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WISPrWXRadiusTempMap
  implements Serializable
{
  private static final long serialVersionUID = 849360672903806770L;
  private Map<String, String> wisprWXRadiusTempMap = new ConcurrentHashMap();

  private static WISPrWXRadiusTempMap instance = new WISPrWXRadiusTempMap();

  public static WISPrWXRadiusTempMap getInstance()
  {
    return instance;
  }

  public Map<String, String> getWisprWXRadiusTempMap() {
    return this.wisprWXRadiusTempMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.WISPrWXRadiusTempMap
 * JD-Core Version:    0.6.2
 */