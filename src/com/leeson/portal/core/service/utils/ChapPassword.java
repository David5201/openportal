package com.leeson.portal.core.service.utils;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.apache.log4j.Logger;

public class ChapPassword
{
  private static Logger log = Logger.getLogger(ChapPassword.class);
  private static Config config = Config.getInstance();

  public static byte[] MK_ChapPwd(byte[] ReqID, byte[] Challenge, byte[] usp) throws UnsupportedEncodingException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    byte[] ChapPwd = new byte[16];

    byte[] buf = new byte[1 + usp.length + Challenge.length];

    buf[0] = ReqID[1];
    for (int i = 0; i < usp.length; i++) {
      buf[(1 + i)] = usp[i];
    }
    for (int i = 0; i < Challenge.length; i++) {
      buf[(1 + usp.length + i)] = Challenge[i];
    }

    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(buf);
      ChapPwd = md.digest();
      if (basConfig.getIsdebug().equals("1"))
        log.info("生成Chap-Password" + PortalUtil.Getbyte2HexString(ChapPwd));
    }
    catch (NoSuchAlgorithmException e)
    {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("生成Chap-Password出错！");
      }
    }

    return ChapPwd;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.utils.ChapPassword
 * JD-Core Version:    0.6.2
 */