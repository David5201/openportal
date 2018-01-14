package com.leeson.portal.core.service.utils;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.apache.log4j.Logger;

public class ACKAuthenticator
{
  private static Logger log = Logger.getLogger(ACKAuthenticator.class);
  private static Config config = Config.getInstance();

  public static byte[] MK_Authen(byte[] Buff, byte[] Attrs, String sharedSecret, byte[] reqAuthen)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    byte[] Secret = sharedSecret.getBytes();
    byte[] Authen = new byte[16];

    byte[] buf = new byte[Buff.length + 16 + Attrs.length + Secret.length];

    for (int i = 0; i < Buff.length; i++) {
      buf[i] = Buff[i];
    }
    for (int i = 0; i < 16; i++) {
      buf[(Buff.length + i)] = reqAuthen[i];
    }
    if (Attrs.length > 0) {
      for (int i = 0; i < Attrs.length; i++) {
        buf[(Buff.length + 16 + i)] = Attrs[i];
      }
      for (int i = 0; i < Secret.length; i++)
        buf[(Buff.length + 16 + Attrs.length + i)] = Secret[i];
    }
    else {
      for (int i = 0; i < Secret.length; i++) {
        buf[(Buff.length + 16 + i)] = Secret[i];
      }

    }

    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(buf);
      Authen = md.digest();
      if (basConfig.getIsdebug().equals("1"))
        log.info("生成Request Authenticator :" + 
          PortalUtil.Getbyte2HexString(Authen));
    }
    catch (NoSuchAlgorithmException e)
    {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("生成Request Authenticator出错！");
      }
    }

    return Authen;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.utils.ACKAuthenticator
 * JD-Core Version:    0.6.2
 */