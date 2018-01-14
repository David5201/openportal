package com.leeson.portal.core.listener;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.controller.AjaxInterFaceController;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.utils.CheckAutoLoginUtils;
import com.leeson.core.utils.CheckTimeUtils;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.UniFiMacSiteMap;
import com.leeson.portal.core.service.InterfaceControl;
import com.leeson.portal.core.service.action.unifi.UniFiMethod;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class CheckOnline extends Thread
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(CheckOnline.class);
  ApplicationContext ac = SpringContextHelper.getApplicationContext();

  private static OnlineMap onlineMap = OnlineMap.getInstance();
  private Map<String, String[]> onlineUserMap = onlineMap.getOnlineUserMap();
  private String host;
  private String username;
  private String ip;
  private String basip;
  private String mac;
  private Portalbas basConfig;
  String[] loginInfo;
  Long userId;
  Long recordId;
  PortallinkrecordService linkRecordService;
  PortalaccountService accountService;
  Portallinkrecord linkRecord;
  Portalaccount account;
  String state;
  boolean isHave;

  public CheckOnline(String host, String username, String[] loginInfo)
  {
    int i = host.lastIndexOf(":");
    String basip = host.substring(i + 1);
    String ip = host.substring(0, i);

    Portalbas basConfig = (Portalbas)config.getConfigMap().get(basip);
    this.basConfig = basConfig;
    this.basip = basip;
    this.ip = ip;
    this.isHave = ((this.onlineUserMap.containsKey(host)) && (basConfig != null));
    if (this.isHave) {
      this.host = host;
      this.username = username;
      this.loginInfo = loginInfo;
      this.mac = loginInfo[4];

      if ("1".equals(loginInfo[6])) {
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
    String type = this.loginInfo[6];
    if ((this.isHave) && ("1".equals(type)) && ("1".equals(this.basConfig.getIsPortalCheck())) && 
      (this.account != null) && (this.linkRecord != null) && 
      (this.state != null) && (!this.state.equals("")))
      startCheckOverTime();
  }

  private void startCheckOverTime()
  {
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      log.info("Start Check isOnline , host: " + this.host + " mac: " + this.mac + 
        " user: " + this.username);
    }
    if ((!this.state.equals("1")) && 
      (!doCheckOverTime())) {
      if (this.basConfig.getBas().equals("3")) {
        if (stringUtils.isNotBlank(this.mac)) {
          String site = 
            (String)UniFiMacSiteMap.getInstance()
            .getMacSiteMap().get(this.mac);
          UniFiMethod.sendUnauthorization(this.mac, site, this.basip);
        }
      } else if ((!this.basConfig.getBas().equals("5")) && 
        (!this.basConfig.getBas().equals("6")) && 
        (!this.basConfig
        .getBas().equals("7")) && (!this.basConfig.getBas().equals("8"))) {
        InterfaceControl.Method("PORTAL_LOGINOUT", this.username, 
          "password", this.ip, this.basip, "");
      }
      CheckTimeUtils.recordTime(this.loginInfo);
      CheckAutoLoginUtils.recordTime(this.loginInfo);
      this.onlineUserMap.remove(this.host);
      AjaxInterFaceController.SangforLogout(this.ip, this.username);
    }
  }

  private boolean doCheckOverTime()
  {
    Date now = new Date();
    Long costTime = Long.valueOf(now.getTime() - this.linkRecord.getStartDate().getTime());
    Date toDate = this.account.getDate();
    Long haveTime = this.account.getTime();
    Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
    long in = Long.valueOf(this.loginInfo[7]).longValue();
    long out = Long.valueOf(this.loginInfo[8]).longValue();
    long octets = in + out;
    if ((this.state.equals("3")) && 
      (toDate.getTime() <= now.getTime())) {
      try {
        Kick.doLinkAll(this.loginInfo, "买断过期");
        this.linkRecord.setEndDate(now);
        this.linkRecord.setTime(costTime);
        this.linkRecord.setState(this.state);
        this.linkRecord.setIns(Long.valueOf(in));
        this.linkRecord.setOuts(Long.valueOf(out));
        this.linkRecord.setOctets(Long.valueOf(octets));
        this.linkRecord.setEx1("Portal");
        this.linkRecord.setEx2("买断过期");
        this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
        this.account.setState("2");
        this.accountService.updatePortalaccountByKey(this.account);

        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          log.info("IP: " + this.ip + " mac: " + this.mac + " user: " + 
            this.username + 
            " , Portal Kick By User Over Date!");
        }
        doLogRecord("IP: " + this.host + " mac: " + this.mac + " 用户: " + 
          this.username + " 上线时间: " + this.loginInfo[3] + " 在线时长: " + 
          costTime + "分钟,买断过期！");
      } catch (Exception localException) {
      }
      return false;
    }

    if ((this.state.equals("2")) && 
      (newHaveTime.longValue() <= 0L)) {
      try {
        Kick.doLinkAll(this.loginInfo, "时长不足");
        this.account.setTime(newHaveTime);
        this.accountService.updatePortalaccountByKey(this.account);
        this.linkRecord.setEndDate(now);
        this.linkRecord.setTime(costTime);
        this.linkRecord.setState(this.state);
        this.linkRecord.setIns(Long.valueOf(in));
        this.linkRecord.setOuts(Long.valueOf(out));
        this.linkRecord.setOctets(Long.valueOf(octets));
        this.linkRecord.setEx1("Portal");
        this.linkRecord.setEx2("时长不足");
        this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
        this.account.setState("4");
        this.accountService.updatePortalaccountByKey(this.account);

        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          log.info("IP: " + this.ip + " mac: " + this.mac + " user: " + 
            this.username + 
            " , Portal Kick By User Have Not Time!");
        }
        doLogRecord("IP: " + this.host + " mac: " + this.mac + " 用户: " + 
          this.username + " 上线时间: " + this.loginInfo[3] + " 在线时长: " + 
          costTime + "分钟,时长不足！");
      } catch (Exception localException1) {
      }
      return false;
    }

    if (((this.state.equals("4")) || (this.state.equals("0"))) && 
      (this.account.getOctets().longValue() <= octets)) {
      try {
        Kick.doLinkAll(this.loginInfo, "流量不足");
        this.account.setOctets(Long.valueOf(0L));
        this.accountService.updatePortalaccountByKey(this.account);
        this.linkRecord.setEndDate(now);
        this.linkRecord.setTime(costTime);
        this.linkRecord.setState(this.state);
        this.linkRecord.setIns(Long.valueOf(in));
        this.linkRecord.setOuts(Long.valueOf(out));
        this.linkRecord.setOctets(Long.valueOf(octets));
        this.linkRecord.setEx1("Portal");
        this.linkRecord.setEx2("流量不足");
        this.linkRecordService.updatePortallinkrecordByKey(this.linkRecord);
        this.account.setState("0");
        this.accountService.updatePortalaccountByKey(this.account);

        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          log.info("IP: " + this.ip + " mac: " + this.mac + " user: " + 
            this.username + 
            " , Portal Kick By User Have Not Octets!");
        }
        doLogRecord("IP: " + this.host + " mac: " + this.mac + " 用户: " + 
          this.username + " 上线时间: " + this.loginInfo[3] + " 在线时长: " + 
          costTime + "分钟,流量不足！");
      } catch (Exception localException2) {
      }
      return false;
    }

    return true;
  }

  private void doLogRecord(String info) {
    try {
      Portallogrecord logRecord = new Portallogrecord();
      Date nowDate = new Date();
      logRecord.setInfo(info);
      logRecord.setRecDate(nowDate);
      PortallogrecordService logRecordService = (PortallogrecordService)this.ac
        .getBean("portallogrecordServiceImpl");
      logRecordService.addPortallogrecord(logRecord);
    }
    catch (Exception localException)
    {
    }
  }

  public boolean doPing() {
    boolean a = false;
    try {
      int timeOut = 3;
      a = InetAddress.getByName(this.ip).isReachable(timeOut * 1000);
    } catch (Exception e) {
      return a;
    }
    return a;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.CheckOnline
 * JD-Core Version:    0.6.2
 */