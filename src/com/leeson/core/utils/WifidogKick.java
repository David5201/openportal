package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

public class WifidogKick
{
  private static Logger log = Logger.getLogger(WifidogKick.class);
  private static Config configDefault = Config.getInstance();
  private static WifiDogOnlineMap onlineMap = WifiDogOnlineMap.getInstance();

  public static void kickUser(String token)
  {
    Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
    String[] loginInfo = null;
    loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
    if (loginInfo != null) {
      String username = loginInfo[0];
      String ip = loginInfo[4];

      if (config.getAuthInterface().contains("1")) {
        doLinkRecord(loginInfo);
      }
      doLogRecord("IP:" + ip + ",用户:" + username + ",被Portal服务器踢下线！");

      if (config.getIsdebug().equals("1")) {
        log.info("用户IP: " + ip + " 用户名: " + username + 
          " 被Portal服务器踢下线！");
      }

      onlineMap.getWifiDogOnlineMap().remove(token);
    }
  }

  public static void ApKickUser(String token)
  {
    Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
    String[] loginInfo = null;
    loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
    if (loginInfo != null) {
      String username = loginInfo[0];
      String ip = loginInfo[4];

      if (config.getAuthInterface().contains("1")) {
        doLinkRecord(loginInfo);
      }
      doLogRecord("IP:" + ip + ",用户:" + username + ",被AP踢下线！");

      if (config.getIsdebug().equals("1")) {
        log.info("用户IP: " + ip + " 用户名: " + username + 
          " 被AP踢下线！");
      }

      onlineMap.getWifiDogOnlineMap().remove(token);
    }
  }

  public static void offLine(String token)
  {
    Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
    String[] loginInfo = null;
    loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
    if (loginInfo != null) {
      String username = loginInfo[0];
      String ip = loginInfo[4];
      if (config.getAuthInterface().contains("1")) {
        doLinkRecord(loginInfo);
      }
      doLogRecord("IP:" + ip + ",用户:" + username + ",主动下线成功!");

      if (config.getIsdebug().equals("1")) {
        log.info("用户IP: " + ip + " 用户名: " + username + ",主动下线成功!");
      }

      onlineMap.getWifiDogOnlineMap().remove(token);
    }
  }

  public static void WeixinOffLine(String token)
  {
    Portalbas config = (Portalbas)configDefault.getConfigMap().get("");
    String[] loginInfo = null;
    loginInfo = (String[])onlineMap.getWifiDogOnlineMap().get(token);
    if (loginInfo != null) {
      String username = loginInfo[0];
      String ip = loginInfo[4];

      doLogRecord("IP:" + ip + ",用户:" + username + ",未通过微信认证下线成功!");

      if (config.getIsdebug().equals("1")) {
        log.info("用户IP: " + ip + " 用户名: " + username + ",未通过微信认证下线成功!");
      }
      onlineMap.getWifiDogOnlineMap().remove(token);
    }
  }

  private static void doLinkRecord(String[] loginInfo)
  {
    PortallinkrecordService portallinkrecordService = (PortallinkrecordService)
      SpringContextHelper.getBean("portallinkrecordServiceImpl");
    PortalaccountService portalaccountService = (PortalaccountService)
      SpringContextHelper.getBean("portalaccountServiceImpl");

    String uid = loginInfo[1];
    String rid = loginInfo[2];
    if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
      return;
    }
    Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
    Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
    if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || (recordId.longValue() == 0L)) {
      return;
    }
    Portallinkrecord linkRecord = portallinkrecordService
      .getPortallinkrecordByKey(recordId);
    Portalaccount account = portalaccountService
      .getPortalaccountByKey(userId);
    String state = account.getState();

    long in = Long.valueOf(loginInfo[8]).longValue();
    long out = Long.valueOf(loginInfo[9]).longValue();
    long octets = in + out;
    linkRecord.setIns(Long.valueOf(out));
    linkRecord.setOuts(Long.valueOf(in));
    linkRecord.setOctets(Long.valueOf(octets));
    Date now = new Date();
    linkRecord.setEndDate(now);
    Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
    linkRecord.setTime(costTime);
    linkRecord.setState(state);
    linkRecord.setEx1("Portal");
    linkRecord.setEx2("用户下线");
    portallinkrecordService.updatePortallinkrecordByKey(linkRecord);

    if (!state.equals("1")) {
      Long haveTime = account.getTime();
      Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
      if ((state.equals("3")) && 
        (account.getDate().getTime() <= now.getTime())) {
        account.setState("2");
        portalaccountService.updatePortalaccountByKey(account);
      }

      if (state.equals("2")) {
        if (newHaveTime.longValue() <= 0L) {
          account.setState("4");
        }
        account.setTime(newHaveTime);
        portalaccountService.updatePortalaccountByKey(account);
      }
      if ((state.equals("4")) || (state.equals("0"))) {
        long haveOctets = account.getOctets().longValue() - octets;
        if (haveOctets <= 0L) {
          account.setState("0");
        }
        account.setOctets(Long.valueOf(haveOctets));
        portalaccountService.updatePortalaccountByKey(account);
      }
    }
  }

  private static void doLogRecord(String info)
  {
    PortallogrecordService portallogrecordService = (PortallogrecordService)
      SpringContextHelper.getBean("portallogrecordServiceImpl");
    Portallogrecord logRecord = new Portallogrecord();
    Date nowDate = new Date();
    logRecord.setInfo(info);
    logRecord.setRecDate(nowDate);
    portallogrecordService.addPortallogrecord(logRecord);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.WifidogKick
 * JD-Core Version:    0.6.2
 */