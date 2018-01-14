package com.leeson.common.util;

import com.leeson.core.service.PortalprivilegeService;
import com.leeson.core.service.PortaluserService;
import org.springframework.context.ApplicationContext;

public final class ServiceHelper
{
  public static Object getService(String serviceName)
  {
    return Const.WEB_APP_CONTEXT.getBean(serviceName);
  }

  public static PortaluserService getUserService() {
    return (PortaluserService)getService("portaluserServiceImpl");
  }
  public static PortalprivilegeService getPrivilegeService() {
    return (PortalprivilegeService)getService("portalprivilegeServiceImpl");
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.ServiceHelper
 * JD-Core Version:    0.6.2
 */