package com.leeson.portal.core.service.action.unifi;

public class UniFiMethod
{
  public static boolean sendAuthorization(String mac, int minutes, String site, String basip)
  {
    return HttpUtil.login(mac, minutes, site, basip);
  }

  public static boolean sendUnauthorization(String mac, String site, String basip)
  {
    return HttpUtil.loginOut(mac, site, basip);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.unifi.UniFiMethod
 * JD-Core Version:    0.6.2
 */