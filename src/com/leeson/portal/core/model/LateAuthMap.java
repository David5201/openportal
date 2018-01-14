package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LateAuthMap
  implements Serializable
{
  private static final long serialVersionUID = 8678626130014354741L;
  private Map<String, Date> lateAuthMap = new ConcurrentHashMap();

  private static LateAuthMap instance = new LateAuthMap();

  public static LateAuthMap getInstance()
  {
    return instance;
  }

  public Map<String, Date> getLateAuthMap() {
    return this.lateAuthMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.LateAuthMap
 * JD-Core Version:    0.6.2
 */