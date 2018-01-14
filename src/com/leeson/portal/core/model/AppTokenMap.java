package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.HashMap;

public class AppTokenMap
  implements Serializable
{
  private static final long serialVersionUID = -8038288385166224254L;
  private HashMap<String, String> TokenMap = new HashMap();

  private static AppTokenMap instance = new AppTokenMap();

  public static AppTokenMap getInstance()
  {
    return instance;
  }

  public static void setInstance(AppTokenMap instance) {
    instance = instance;
  }

  public HashMap<String, String> getTokenMap() {
    return this.TokenMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AppTokenMap
 * JD-Core Version:    0.6.2
 */