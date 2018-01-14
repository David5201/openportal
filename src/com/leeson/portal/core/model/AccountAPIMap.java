package com.leeson.portal.core.model;

import java.io.Serializable;
import java.util.HashMap;

public class AccountAPIMap
  implements Serializable
{
  private static final long serialVersionUID = -7966240873304008863L;
  private HashMap<String, String> accountAPIMap = new HashMap();

  private static AccountAPIMap instance = new AccountAPIMap();

  private AccountAPIMap() {
    this.accountAPIMap.put("url", "");
    this.accountAPIMap.put("state", "");

    this.accountAPIMap.put("publicurl", "");
    this.accountAPIMap.put("publicstate", "");

    this.accountAPIMap.put("autourl", "");
    this.accountAPIMap.put("autostate", "");
  }

  public static AccountAPIMap getInstance() {
    return instance;
  }

  public HashMap<String, String> getAccountAPIMap() {
    return this.accountAPIMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.model.AccountAPIMap
 * JD-Core Version:    0.6.2
 */