package com.leeson.radius.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.radius.core.model.RadiusOnlineMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OnlineLimit
{
  public static boolean macLimit(String username, String userMac, int limitCount)
  {
    if ((stringUtils.isNotBlank(userMac)) && 
      (limitCount > 0)) {
      int count = 0;
      Iterator iterator = RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().keySet().iterator();
      while (iterator.hasNext()) {
        Object o = iterator.next();
        String t = o.toString();
        String[] loginInfo = 
          (String[])RadiusOnlineMap.getInstance()
          .getRadiusOnlineMap().get(t);
        String haveUsername = loginInfo[4];
        if (username.equals(haveUsername)) {
          count++;
        }
      }
      if (count >= limitCount) {
        return false;
      }
    }

    return true;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.OnlineLimit
 * JD-Core Version:    0.6.2
 */