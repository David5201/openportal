package com.leeson.portal.core.service;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalbasService;
import com.leeson.portal.core.service.action.ReqInfo;
import com.leeson.portal.core.service.action.iKuai.iKuaiAuth;
import com.leeson.portal.core.service.action.v1.chap.Chap_Auth_V1;
import com.leeson.portal.core.service.action.v1.chap.Chap_Challenge_V1;
import com.leeson.portal.core.service.action.v1.chap.Chap_Quit_V1;
import com.leeson.portal.core.service.action.v1.pap.PAP_Auth_V1;
import com.leeson.portal.core.service.action.v1.pap.PAP_Quit_V1;
import com.leeson.portal.core.service.action.v2.chap.Chap_Auth_V2;
import com.leeson.portal.core.service.action.v2.chap.Chap_Challenge_V2;
import com.leeson.portal.core.service.action.v2.chap.Chap_Quit_V2;
import com.leeson.portal.core.service.action.v2.pap.PAP_Auth_V2;
import com.leeson.portal.core.service.action.v2.pap.PAP_Quit_V2;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class InterfaceControl
{
  private static Logger log = Logger.getLogger(InterfaceControl.class);

  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

  private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
  private static PortalbasService basService = (PortalbasService)ac.getBean("portalbasServiceImpl");
  private static ConfigService configService = (ConfigService)ac.getBean("configServiceImpl");

  public static Boolean Method(String Action, String userName, String passWord, String ip, String bip, String mac)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String basIp = basConfig.getBasIp();
    String bas = basConfig.getBas();
    int basPort = Integer.parseInt(basConfig.getBasPort());
    String sharedSecret = basConfig.getSharedSecret();
    String authType = basConfig.getAuthType();
    int timeoutSec = Integer.parseInt(basConfig.getTimeoutSec());
    int portalVer = Integer.parseInt(basConfig.getPortalVer());

    if (stringUtils.isNotBlank(bip)) {
      PortalbasQuery bq = new PortalbasQuery();
      bq.setBasIp(bip);
      bq.setBasIpLike(false);
      List basList = basService.getPortalbasList(bq);
      if (basList.size() > 0) {
        Portalbas basconfig = (Portalbas)basList.get(0);

        bas = basconfig.getBas();
        basPort = Integer.parseInt(basconfig.getBasPort());
        sharedSecret = basconfig.getSharedSecret();
        authType = basconfig.getAuthType();
        timeoutSec = Integer.parseInt(basconfig.getTimeoutSec());
        portalVer = Integer.parseInt(basconfig.getPortalVer());
        basIp = bip;
      }
    }

    String basipT = DomainToIP(basIp);
    if (stringUtils.isBlank(basipT)) {
      return Boolean.valueOf(false);
    }
    basIp = basipT;

    short SerialNo_Short = (short)(int)(1.0D + Math.random() * 32767.0D);

    byte[] SerialNo = PortalUtil.SerialNo(SerialNo_Short);
    byte[] UserIP = new byte[4];
    String[] ips = ip.split("[.]");

    for (int i = 0; i < 4; i++) {
      int m = NumberUtils.toInt(ips[i]);
      byte b = (byte)m;
      UserIP[i] = b;
    }

    if ((bas.equals("2")) || (bas.equals("4"))) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("【iKuai对接   准备portal协议报文发送】  ip: " + ip + " mac: " + mac + " user: " + userName + " basip: " + basIp);
      }

      if (Action.equals("PORTAL_LOGIN")) {
        if (stringUtils.isBlank(mac)) {
          if (basConfig.getIsdebug().equals("1")) {
            log.info("【iKuai对接   获取MAC地址参数 错误!!!】");
          }
          return Boolean.valueOf(false);
        }
        return Boolean.valueOf(iKuaiAuth.auth(basIp, basPort, timeoutSec, userName, passWord, SerialNo, UserIP, mac));
      }

      return Boolean.valueOf(PAP_Quit_V1.quit(0, basIp, basPort, timeoutSec, 
        SerialNo, UserIP));
    }

    if (basConfig.getIsdebug().equals("1")) {
      log.info("【准备portal协议报文发送】  ip: " + ip + " user: " + userName + " basip: " + basIp);
    }

    if ((authType.equals("0")) && (portalVer == 1)) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("使用Portal V1协议，PAP认证方式！！");
      }

      if (Action.equals("PORTAL_LOGIN")) {
        if (bas.equals("1")) {
          if (basConfig.getIsdebug().equals("1")) {
            log.info("H3C设备！！");
          }

          byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 1);

          if (Ack_info.length == 1) {
            return Boolean.valueOf(false);
          }
        }
        return Boolean.valueOf(PAP_Auth_V1.auth(basIp, basPort, timeoutSec, userName, 
          passWord, SerialNo, UserIP));
      }

      return Boolean.valueOf(PAP_Quit_V1.quit(0, basIp, basPort, timeoutSec, 
        SerialNo, UserIP));
    }
    if ((authType.equals("0")) && (portalVer == 2))
    {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("使用Portal V2协议，PAP认证方式！！");
      }

      if (Action.equals("PORTAL_LOGIN")) {
        if (bas.equals("1")) {
          if (basConfig.getIsdebug().equals("1")) {
            log.info("H3C设备！！");
          }

          byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 2);

          if (Ack_info.length == 1) {
            return Boolean.valueOf(false);
          }
        }
        return Boolean.valueOf(PAP_Auth_V2.auth(basIp, basPort, timeoutSec, userName, 
          passWord, SerialNo, UserIP, sharedSecret));
      }

      return Boolean.valueOf(PAP_Quit_V2.quit(0, basIp, basPort, timeoutSec, 
        SerialNo, UserIP, sharedSecret));
    }

    if ((authType.equals("1")) && (portalVer == 2))
    {
      return Boolean.valueOf(Portal_V2(Action, userName, passWord, basIp, basPort, 
        timeoutSec, SerialNo, UserIP, sharedSecret, SerialNo_Short, ip, bas));
    }

    if ((authType.equals("1")) && (portalVer == 1))
    {
      return Boolean.valueOf(Portal_V1(Action, userName, passWord, basIp, basPort, 
        timeoutSec, SerialNo, UserIP, ip, bas, sharedSecret));
    }

    if (basConfig.getIsdebug().equals("1")) {
      log.info("参数错误,认证方式或版本号错误!!!");
    }

    return Boolean.valueOf(false);
  }

  private static boolean Portal_V2(String Action, String userName, String passWord, String basIp, int basPort, int timeoutSec, byte[] SerialNo, byte[] UserIP, String sharedSecret, short SerialNo_Short, String ip, String bas)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      log.info("使用Portal V2协议，Chap认证方式！！");
    }

    byte[] ReqID = new byte[2];
    if (Action.equals("PORTAL_LOGIN"))
    {
      if (bas.equals("1")) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("H3C设备！！");
        }

        byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 2);

        if (Ack_info.length == 1) {
          return false;
        }

      }

      byte[] Challenge = new byte[16];

      byte[] Ack_Challenge_V2 = Chap_Challenge_V2.challenge(basIp, 
        basPort, timeoutSec, SerialNo, UserIP, sharedSecret);

      if (Ack_Challenge_V2.length == 2) {
        ReqID = Ack_Challenge_V2;
        Chap_Quit_V2.quit(1, basIp, basPort, timeoutSec, SerialNo, 
          UserIP, ReqID, sharedSecret);
        return false;
      }
      ReqID[0] = Ack_Challenge_V2[6];
      ReqID[1] = Ack_Challenge_V2[7];
      for (int i = 0; i < 16; i++) {
        Challenge[i] = Ack_Challenge_V2[(34 + i)];
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("获得Challenge：" + 
          PortalUtil.Getbyte2HexString(Challenge));
      }

      byte[] Ack_Auth_V2 = Chap_Auth_V2.auth(basIp, basPort, 
        timeoutSec, userName, passWord, SerialNo, UserIP, 
        ReqID, Challenge, sharedSecret);
      if (((Ack_Auth_V2[0] & 0xFF) != 20) && 
        ((Ack_Auth_V2[0] & 0xFF) != 22)) {
        Chap_Quit_V2.quit(2, basIp, basPort, timeoutSec, SerialNo, 
          UserIP, ReqID, sharedSecret);
        return false;
      }
      return true;
    }

    return Chap_Quit_V2.quit(0, basIp, basPort, timeoutSec, SerialNo, 
      UserIP, ReqID, sharedSecret);
  }

  private static boolean Portal_V1(String Action, String userName, String passWord, String basIp, int basPort, int timeoutSec, byte[] SerialNo, byte[] UserIP, String ip, String bas, String sharedSecret)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      log.info("使用Portal V1协议，Chap认证方式！！");
    }

    byte[] ReqID = new byte[2];
    if (Action.equals("PORTAL_LOGIN"))
    {
      if (bas.equals("1")) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("H3C设备！！");
        }

        byte[] Ack_info = ReqInfo.reqInfo(basIp, basPort, timeoutSec, SerialNo, UserIP, sharedSecret, 1);

        if (Ack_info.length == 1) {
          return false;
        }
      }

      byte[] Challenge = new byte[16];

      byte[] Ack_Challenge_V1 = Chap_Challenge_V1.Action(basIp, basPort, 
        timeoutSec, SerialNo, UserIP);

      if (Ack_Challenge_V1.length == 2) {
        ReqID = Ack_Challenge_V1;
        Chap_Quit_V1.quit(1, basIp, basPort, timeoutSec, SerialNo, 
          UserIP, ReqID);
        return false;
      }
      ReqID[0] = Ack_Challenge_V1[6];
      ReqID[1] = Ack_Challenge_V1[7];
      for (int i = 0; i < 16; i++) {
        Challenge[i] = Ack_Challenge_V1[(18 + i)];
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("获得Challenge：" + 
          PortalUtil.Getbyte2HexString(Challenge));
      }

      byte[] Ack_Auth_V1 = Chap_Auth_V1.auth(basIp, basPort, 
        timeoutSec, userName, passWord, SerialNo, UserIP, 
        ReqID, Challenge);
      if (((Ack_Auth_V1[0] & 0xFF) != 20) && 
        ((Ack_Auth_V1[0] & 0xFF) != 22)) {
        Chap_Quit_V1.quit(2, basIp, basPort, timeoutSec, SerialNo, 
          UserIP, ReqID);
        return false;
      }
      return true;
    }

    return Chap_Quit_V1.quit(0, basIp, basPort, timeoutSec, SerialNo, 
      UserIP, ReqID);
  }

  private static String DomainToIP(String domain)
  {
    if (configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
      return domain;
    }
    String ip = "";
    try {
      ip = java.net.InetAddress.getByName(domain).toString().split("/")[1];
    } catch (UnknownHostException e) {
      log.error("DomainToIP ERROR!!");
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
    }
    System.out.println("Domain:" + domain + " IP:" + ip);
    return ip;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.InterfaceControl
 * JD-Core Version:    0.6.2
 */