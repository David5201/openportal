package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.HashMap;

public class ipMacMap
  implements Serializable
{
  private static final long serialVersionUID = 5220960469752563771L;
  private HashMap<String, String> ipMacMap = new HashMap();

  private static ipMacMap instance = new ipMacMap();

  public static ipMacMap getInstance()
  {
    return instance;
  }

  public HashMap<String, String> getIpMacMap() {
    return this.ipMacMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.ipMacMap
 * JD-Core Version:    0.6.2
 */