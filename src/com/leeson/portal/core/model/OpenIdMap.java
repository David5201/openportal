package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OpenIdMap
  implements Serializable
{
  private static final long serialVersionUID = 4318035969875254558L;
  private Map<String, String[]> openIdMap = new ConcurrentHashMap();

  private static OpenIdMap instance = new OpenIdMap();

  public static OpenIdMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getOpenIdMap() {
    return this.openIdMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.OpenIdMap
 * JD-Core Version:    0.6.2
 */