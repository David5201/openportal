package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserLimitMap
  implements Serializable
{
  private static final long serialVersionUID = 2852091659414270091L;
  private Map<String, String[]> UserLimitMap = new ConcurrentHashMap();

  private static UserLimitMap instance = new UserLimitMap();

  public static UserLimitMap getInstance()
  {
    return instance;
  }

  public Map<String, String[]> getUserLimitMap() {
    return this.UserLimitMap;
  }

  public void setUserLimitMap(Map<String, String[]> userLimitMap) {
    this.UserLimitMap = userLimitMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.UserLimitMap
 * JD-Core Version:    0.6.2
 */