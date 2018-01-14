package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MagicMap
  implements Serializable
{
  private static final long serialVersionUID = 4318035969875254558L;
  private Map<String, String[]> magicMap = new ConcurrentHashMap();

  private static MagicMap instance = new MagicMap();

  public static MagicMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getMagicMap() {
    return this.magicMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.MagicMap
 * JD-Core Version:    0.6.2
 */