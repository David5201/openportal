package com.leeson.portal.core.listener;

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
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class CheckOnlineWifidog extends Thread
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(CheckOnlineWifidog.class);

  ApplicationContext ac = SpringContextHelper.getApplicationContext();

  private static WifiDogOnlineMap onlineMap = WifiDogOnlineMap.getInstance();
  private Map<String, String[]> onlineUserMap = onlineMap.getWifiDogOnlineMap();
  private String host;
  private String username;
  String[] loginInfo;
  Long userId;
  Long recordId;
  PortallinkrecordService linkRecordService;
  PortalaccountService accountService;
  Portallinkrecord linkRecord;
  Portalaccount account;
  String state;
  boolean isHave;

  public CheckOnlineWifidog(String host, String username, String[] loginInfo)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    this.isHave = this.onlineUserMap.containsKey(host);
    if (this.isHave) {
      this.host = host;
      this.username = username;
      this.loginInfo = loginInfo;

      if (basConfig.getAuthInterface().contains("1")) {
        String uid = loginInfo[1];
        String rid = loginInfo[2];
        if ((!stringUtils.isBlank(uid)) && (!stringUtils.isBlank(rid)))
        {
          this.userId = Long.valueOf(Long.parseLong(loginInfo[1]));
          this.recordId = Long.valueOf(Long.parseLong(loginInfo[2]));

          this.linkRecordService = 
            ((PortallinkrecordService)this.ac
            .getBean("portallinkrecordServiceImpl"));
          this.linkRecord = this.linkRecordService
            .getPortallinkrecordByKey(this.recordId);
          this.accountService = 
            ((PortalaccountService)this.ac
            .getBean("portalaccountServiceImpl"));
          this.account = this.accountService.getPortalaccountByKey(this.userId);
          this.state = this.account.getState();
        }
      }
    }
  }

  public void run()
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if ((this.isHave) && (basConfig.getAuthInterface().contains("1")) && 
      (this.account != null) && (this.linkRecord != null) && 
      (this.state != null) && (!this.state.equals("")))
      startCheckOverTime();
  }

  private boolean startCheckOverTime()
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      log.info("wifidog开始检测余额   网络地址: " + this.loginInfo[4] + "登录名: " + this.username);
    }
    if ((!this.state.equals("1")) && 
      (!doCheckOverTime())) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("wifidog IP:" + this.loginInfo[4] + "账户" + this.username + 
          "余额不足，强制下线成功！！");
      }
      doLogRecord("IP:" + this.loginInfo[4] + "账户" + this.username + 
        "余额不足，强制下线成功！！");
      return false;
    }

    return true;
  }

  private boolean doCheckOverTime()
  {
    Date now = new Date();
    Long costTime = Long.valueOf(now.getTime() - this.linkRecord.getStartDate().getTime());
    Date toDate = this.account.getDate();
    Long haveTime = this.account.getTime();
    Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
    long in = Long.valueOf(this.loginInfo[8]).longValue();
    long out = Long.valueOf(this.loginInfo[9]).longValue();
    long octets = in + out;

    if ((this.state.equals("3")) && 
      (toDate.getTime() <= now.getTime())) {
      this.onlineUserMap.remove(this.host);
      this.linkRecord.setEndDate(now);
      this.linkRecord.setTime(costTime);
      this.linkRecord.setState(this.state);
      this.linkRecord.setIns(Long.valueOf(out));
      this.linkRecord.setOuts(Long.valueOf(in));
      this.linkRecord.setOctets(Long.valueOf(octets));
      this.linkRecord.setEx1("Portal");
      this.linkRecord.setEx2("买断过期");
      this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
      this.account.setState("2");
      this.accountService.updatePortalaccountByKey(this.account);
      return false;
    }

    if ((this.state.equals("2")) && 
      (newHaveTime.longValue() <= 0L)) {
      this.onlineUserMap.remove(this.host);
      this.account.setTime(newHaveTime);
      this.account.setState("4");
      this.accountService.updatePortalaccountByKey(this.account);
      this.linkRecord.setEndDate(now);
      this.linkRecord.setTime(costTime);
      this.linkRecord.setState(this.state);
      this.linkRecord.setIns(Long.valueOf(out));
      this.linkRecord.setOuts(Long.valueOf(in));
      this.linkRecord.setOctets(Long.valueOf(octets));
      this.linkRecord.setEx1("Portal");
      this.linkRecord.setEx2("时长不足");
      this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
      return false;
    }

    if (((this.state.equals("4")) || (this.state.equals("0"))) && 
      (this.account.getOctets().longValue() <= octets)) {
      try {
        this.account.setState("0");
        this.account.setOctets(Long.valueOf(0L));
        this.accountService.updatePortalaccountByKey(this.account);
        this.linkRecord.setEndDate(now);
        this.linkRecord.setTime(costTime);
        this.linkRecord.setState(this.state);
        this.linkRecord.setIns(Long.valueOf(out));
        this.linkRecord.setOuts(Long.valueOf(in));
        this.linkRecord.setOctets(Long.valueOf(octets));
        this.linkRecord.setEx1("Portal");
        this.linkRecord.setEx2("流量不足");
        this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
      } catch (Exception localException) {
      }
      return false;
    }

    return true;
  }

  private void doLogRecord(String info) {
    Portallogrecord logRecord = new Portallogrecord();
    Date nowDate = new Date();
    logRecord.setInfo(info);
    logRecord.setRecDate(nowDate);
    PortallogrecordService logRecordService = (PortallogrecordService)this.ac
      .getBean("portallogrecordServiceImpl");
    logRecordService.addPortallogrecord(logRecord);
  }

  public boolean doPing()
  {
    boolean a = false;
    try
    {
      String ip = this.loginInfo[4];
      int timeOut = Integer.parseInt(((Portalbas)config.getConfigMap().get("")).getTimeoutSec());
      a = InetAddress.getByName(ip).isReachable(timeOut * 1000);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return a;
    }
    return a;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.CheckOnlineWifidog
 * JD-Core Version:    0.6.2
 */