package com.leeson.radius.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.util.List;

public class MacLimit
{
  private static PortalaccountmacsService macsService = (PortalaccountmacsService)
    SpringContextHelper.getBean("portalaccountmacsServiceImpl");

  private static ConfigService configService = (ConfigService)
    SpringContextHelper.getBean("configServiceImpl");

  public static boolean macLimit(Long id, String username, String userMac, int limitMac, int limitCount)
  {
    if ((stringUtils.isNotBlank(userMac)) && 
      (1 == configService.getConfigByKey(Long.valueOf(1L)).getRadiusOn().intValue()) && 
      (limitMac == 1)) {
      PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
      mq.setAccountId(id);
      List<Portalaccountmacs> accountmacs = macsService
        .getPortalaccountmacsList(mq);
      if ((accountmacs.size() < 1) || (limitCount == 0) || 
        (accountmacs.size() < limitCount)) {
        Boolean isHave = Boolean.valueOf(false);
        for (Portalaccountmacs amacs : accountmacs) {
          if (amacs.getMac().toLowerCase().equals(userMac)) {
            isHave = Boolean.valueOf(true);
            break;
          }
        }
        if (!isHave.booleanValue()) {
          Portalaccountmacs mac = new Portalaccountmacs();
          mac.setAccountId(id);
          mac.setMac(userMac);
          macsService.addPortalaccountmacs(mac);
        }
      } else {
        Boolean isHave = Boolean.valueOf(false);
        for (Portalaccountmacs amacs : accountmacs) {
          if (amacs.getMac().toLowerCase().equals(userMac)) {
            isHave = Boolean.valueOf(true);
            break;
          }
        }
        if (!isHave.booleanValue()) {
          return false;
        }
      }

    }

    return true;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.MacLimit
 * JD-Core Version:    0.6.2
 */