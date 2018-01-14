package com.leeson.portal.core.service.action;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.ipMacMap;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetMac
{
  private static Logger log = Logger.getLogger(GetMac.class);
  private static Config config = Config.getInstance();

  public static void getMac(byte[] data, String basIp, byte[] userIpBuf) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String userIp = "";
    for (int i = 0; i < 4; i++) {
      if (i < 3)
        userIp = userIp + (userIpBuf[i] & 0xFF) + ".";
      else {
        userIp = userIp + (userIpBuf[i] & 0xFF);
      }
    }

    String mac = "";
    Boolean state = Boolean.valueOf(false);
    if ((data[15] & 0xFF) != 0) {
      int pos = 0;
      if ((data[0] & 0xFF) == 1) {
        pos = 16;
      }
      if ((data[0] & 0xFF) == 2) {
        pos = 32;
      }
      int AN = data[15] & 0xFF;

      int num = 1;
      while (num <= AN) {
        if (pos >= data.length)
        {
          break;
        }
        int type = data[pos] & 0xFF;
        pos++;

        if (pos >= data.length)
        {
          break;
        }
        int len = (data[pos] & 0xFF) - 2;
        pos++;

        byte[] buf = new byte[len];
        for (int l = 0; l < buf.length; l++) {
          if (pos + l >= data.length) {
            break;
          }
          buf[l] = data[(pos + l)];
        }
        pos += len;
        if (type == 11) {
          mac = PortalUtil.Getbyte2MacString(buf);
          state = Boolean.valueOf(true);
          break;
        }
        num++;
      }
    }

    if ((state.booleanValue()) && (!mac.equals("")))
      ipMacMap.getInstance().getIpMacMap().put(userIp + ":" + basIp, mac.trim().toLowerCase());
    else {
      ipMacMap.getInstance().getIpMacMap().put(userIp + ":" + basIp, "error");
    }
    if (basConfig.getIsdebug().equals("1"))
      log.info("用户IP: " + userIp + " BasIp: " + basIp + "Mac地址是：" + mac.trim());
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.GetMac
 * JD-Core Version:    0.6.2
 */