package com.leeson.portal.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.controller.AjaxInterFaceController;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.UniFiMacSiteMap;
import com.leeson.portal.core.service.InterfaceControl;
import com.leeson.portal.core.service.action.unifi.UniFiMethod;
import java.util.HashMap;
import java.util.Map;

public class KickAll
{
  private static Config configDefault = Config.getInstance();
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  public static void kickUser(String ip)
  {
    int i = ip.lastIndexOf(":");
    String ubip = ip.substring(i + 1);
    String uip = ip.substring(0, i);

    Portalbas config = (Portalbas)configDefault.getConfigMap().get(ubip);
    if (config == null) {
      String[] loginInfo = null;
      loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
      if (loginInfo != null) {
        String username = loginInfo[0];
        onlineMap.getOnlineUserMap().remove(ip);
        AjaxInterFaceController.SangforLogout(uip, username);
      }
      return;
    }
    String[] loginInfo = null;
    loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
    if (loginInfo != null) {
      String username = loginInfo[0];
      String mac = loginInfo[4];
      if (config.getBas().equals("3")) {
        if (stringUtils.isNotBlank(mac)) {
          String site = 
            (String)UniFiMacSiteMap.getInstance().getMacSiteMap()
            .get(mac);
          UniFiMethod.sendUnauthorization(mac, site, ubip);
        }
      } else if ((!config.getBas().equals("5")) && 
        (!config.getBas().equals("6")) && 
        (!config.getBas().equals("7")) && 
        (!config
        .getBas().equals("8"))) {
        InterfaceControl.Method("PORTAL_LOGINOUT", username, 
          "password", uip, ubip, mac);
      }
      onlineMap.getOnlineUserMap().remove(ip);
      AjaxInterFaceController.SangforLogout(uip, username);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.KickAll
 * JD-Core Version:    0.6.2
 */