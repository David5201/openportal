package com.leeson.portal.core.listener;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Historyonline;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.query.HistoryonlineQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.HistoryonlineService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.service.PortalweixinwifiService;
import com.leeson.core.utils.CheckAutoLoginUtils;
import com.leeson.core.utils.CheckTimeUtils;
import com.leeson.core.utils.Kick;
import com.leeson.core.utils.WifidogKick;
import com.leeson.portal.core.model.LateAuthMap;
import com.leeson.portal.core.model.MagicMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.OpenIdMap;
import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
import com.leeson.portal.core.model.WeixinMap;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import com.leeson.portal.core.model.iKuaiMacIpMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.utils.HttpRequest;
import com.leeson.portal.core.utils.KickAll;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.leeson.radius.core.RadiusCOA;
import com.leeson.radius.core.model.RadiusOnlineMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class CheckOnlineService
  implements ServletContextListener
{
  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

  private static Logger log = Logger.getLogger(CheckOnlineService.class);
  private PortallinkrecordService linkRecordService;
  private PortalaccountService accountService;
  private PortallogrecordService logRecordService;
  private PortalweixinwifiService weixinwifiService;
  private PortalbasauthService basauthService;
  private HistoryonlineService historyonlineService;
  private Long weixinTime = Long.valueOf(10L);
  private Long checkTime = Long.valueOf(10L);
  private String path;
  private static ConfigService configService;
  private static Timer timerRecordOnlineCount = new Timer();
  private static Timer timerweixinCheck = new Timer();
  private static Timer timerstartCheck = new Timer();

  public void contextDestroyed(ServletContextEvent servletContextEvent)
  {
    timerRecordOnlineCount.cancel();
    timerweixinCheck.cancel();
    timerstartCheck.cancel();

    configService = (ConfigService)
      SpringContextHelper.getBean("configServiceImpl");
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue()) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("CheckOnlineService Will Stop , Ready Offline All User...");
      }
      this.linkRecordService = ((PortallinkrecordService)
        SpringContextHelper.getBean("portallinkrecordServiceImpl"));
      this.accountService = ((PortalaccountService)
        SpringContextHelper.getBean("portalaccountServiceImpl"));
      this.logRecordService = ((PortallogrecordService)
        SpringContextHelper.getBean("portallogrecordServiceImpl"));

      Iterator iterator = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
      while (iterator.hasNext()) {
        Object o = iterator.next();
        String t = o.toString();

        int i = t.lastIndexOf(":");
        String ubip = t.substring(i + 1);

        String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
        Kick.doLinkAll(loginInfo, "服务器关闭");
        String username = loginInfo[0];
        String mac = loginInfo[4];
        String type = loginInfo[6];
        Portalbas basconfig = (Portalbas)config.getConfigMap().get(ubip);
        if ((basconfig != null) && 
          ("1".equals(type)) && 
          ("1".equals(basconfig.getIsPortalCheck()))) {
          doLinkRecord(loginInfo, "服务器关闭");
        }
        try
        {
          String time = loginInfo[3];
          Date loginTime = ThreadLocalDateUtil.parse(time);
          String nowString = ThreadLocalDateUtil.format(new Date());
          Date nowTime = ThreadLocalDateUtil.parse(nowString);
          Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
          costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
          try
          {
            doLogRecord("IP: " + t + " mac: " + mac + " 用户: " + 
              username + " 上线时间: " + time + " 在线时长: " + 
              costTime + "分钟,服务器关闭！");
          }
          catch (Exception localException)
          {
          }
          if (basConfig.getIsdebug().equals("1")) {
            log.info("IP: " + t + " mac: " + mac + " user: " + 
              username + 
              " , Portal Kick By Server Shutdown!");
          }
        }
        catch (Exception localException1)
        {
        }
        CheckTimeUtils.recordTime(loginInfo);
        CheckAutoLoginUtils.recordTime(loginInfo);

        KickAll.kickUser(t);
        if (stringUtils.isNotBlank(mac))
          LateAuthMap.getInstance().getLateAuthMap().remove(mac);
      }
    }
    else {
      WeixinMapToDisk(servletContextEvent);
    }
    OnlineUserMapToDisk(servletContextEvent);

    ikuaiMacIpMapToDisk(servletContextEvent);

    if (basConfig.getIsdebug().equals("1")) {
      log.info("CheckOnlineService Stoped!!");
    }
    com.leeson.core.utils.kickAllThread.offThread();
    com.leeson.portal.core.utils.kickAllThread.offThread();
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    configService = (ConfigService)
      SpringContextHelper.getBean("configServiceImpl");
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    this.linkRecordService = ((PortallinkrecordService)
      SpringContextHelper.getBean("portallinkrecordServiceImpl"));
    this.accountService = ((PortalaccountService)
      SpringContextHelper.getBean("portalaccountServiceImpl"));
    this.logRecordService = ((PortallogrecordService)
      SpringContextHelper.getBean("portallogrecordServiceImpl"));
    this.weixinwifiService = ((PortalweixinwifiService)
      SpringContextHelper.getBean("portalweixinwifiServiceImpl"));
    this.weixinTime = this.weixinwifiService.getPortalweixinwifiByKey(Long.valueOf(1L))
      .getOutTime();
    this.basauthService = ((PortalbasauthService)
      SpringContextHelper.getBean("portalbasauthServiceImpl"));
    this.historyonlineService = ((HistoryonlineService)
      SpringContextHelper.getBean("historyonlineServiceImpl"));
    this.checkTime = configService.getConfigByKey(Long.valueOf(1L)).getCheckTime();

    if (1 != configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue()) {
      OnlineUserMapFromDisk(servletContextEvent);
      WeixinMapFromDisk(servletContextEvent);
    }

    ikuaiMacIpMapFromDisk(servletContextEvent);

    new Thread()
    {
      public void run()
      {
        try {
          CheckOnlineService.this.startCheck();
        }
        catch (Exception e) {
          CheckOnlineService.log.error("CheckOnlineService Start ERROR!!");
          CheckOnlineService.log.error("==============ERROR Start=============");
          CheckOnlineService.log.error(e);
          CheckOnlineService.log.error("ERROR INFO ", e);
          CheckOnlineService.log.error("==============ERROR End=============");
        }
      }
    }
    .start();

    if (basConfig.getIsdebug().equals("1")) {
      log.info("CheckOnlineService Start Success!! 20s Later Start , Every " + 
        this.checkTime + "s Check Once !!");
    }

    new Thread()
    {
      public void run()
      {
        try {
          CheckOnlineService.this.weixinCheck();
        }
        catch (Exception e) {
          CheckOnlineService.log.error("CheckWeiXinAuthTempService Start ERROR!!");
          CheckOnlineService.log.error("==============ERROR Start=============");
          CheckOnlineService.log.error(e);
          CheckOnlineService.log.error("ERROR INFO ", e);
          CheckOnlineService.log.error("==============ERROR End=============");
        }
      }
    }
    .start();

    if (basConfig.getIsdebug().equals("1"))
    {
      log.info("CheckWeiXinAuthTempService Start Success!! 20s Later Start , Every " + 
        this.weixinTime + "s Check Once !!");
    }

    new Thread()
    {
      public void run()
      {
        try {
          CheckOnlineService.this.RecordOnlineCount();
          CheckOnlineService.log.info("Start Record Online Count Service !!");
        } catch (Exception e) {
          CheckOnlineService.log.error("Do Record Online Count Service Start ERROR!!");
          CheckOnlineService.log.error("==============ERROR Start=============");
          CheckOnlineService.log.error(e);
          CheckOnlineService.log.error("ERROR INFO ", e);
          CheckOnlineService.log.error("==============ERROR End=============");
        }
      }
    }
    .start();

    new Thread()
    {
      public void run()
      {
        try {
          HttpRequest.send();
        }
        catch (Exception e) {
          CheckOnlineService.log.error("==============ERROR Start=============");
          CheckOnlineService.log.error(e);
          CheckOnlineService.log.error("ERROR INFO ", e);
          CheckOnlineService.log.error("==============ERROR End=============");
        }
      }
    }
    .start();

    if (basConfig.getIsdebug().equals("1")) {
      log.info("Check Server Info !!");
    }

    this.path = servletContextEvent.getServletContext().getRealPath("/");
    new Thread()
    {
      public void run()
      {
        try {
          CheckOnlineService.this.deleteFiles(CheckOnlineService.this.path);
        }
        catch (Exception e) {
          CheckOnlineService.log.error("==============ERROR Start=============");
          CheckOnlineService.log.error(e);
          CheckOnlineService.log.error("ERROR INFO ", e);
          CheckOnlineService.log.error("==============ERROR End=============");
        }
      }
    }
    .start();
  }

  private void startCheck()
  {
    TimerTask task = new TimerTask()
    {
      public void run() {
        Portalbas basConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get("");
        if (basConfig.getIsdebug().equals("1")) {
          CheckOnlineService.log.info("Start Check Portal Online User List , Check Have Time !!");
        }

        Iterator iteratorWifidog = WifiDogOnlineMap.getInstance()
          .getWifiDogOnlineMap()
          .keySet().iterator();
        while (iteratorWifidog.hasNext()) {
          Object o = iteratorWifidog.next();
          String t = o.toString();
          String[] loginInfo = 
            (String[])WifiDogOnlineMap.getInstance()
            .getWifiDogOnlineMap().get(t);
          String username = loginInfo[0];
          if (basConfig.getIsdebug().equals("1")) {
            CheckOnlineService.log.info("Check Online User , host: " + loginInfo[4] + 
              " user: " + username);
          }
          new CheckOnlineWifidog(t, username, loginInfo).start();
        }

        if (CheckOnlineService.Do()) {
          try
          {
            Iterator iteratorOnlineUserSessionTimeCheck = 
              OnlineMap.getInstance().getOnlineUserMap()
              .keySet().iterator();
            while (iteratorOnlineUserSessionTimeCheck.hasNext()) {
              Object oOnlineUserSessionTimeCheck = iteratorOnlineUserSessionTimeCheck
                .next();
              String tOnlineUserSessionTimeCheck = oOnlineUserSessionTimeCheck
                .toString();

              int i = tOnlineUserSessionTimeCheck.lastIndexOf(":");
              String basip = tOnlineUserSessionTimeCheck.substring(i + 1);

              String[] loginInfo = 
                (String[])OnlineMap.getInstance().getOnlineUserMap()
                .get(tOnlineUserSessionTimeCheck);
              if ((loginInfo != null) && (loginInfo.length > 0)) {
                Portalbas bas = 
                  (Portalbas)CheckOnlineService.config.getConfigMap()
                  .get(basip);
                if (bas != null) {
                  String type = loginInfo[6];
                  PortalbasauthQuery q = new PortalbasauthQuery();
                  q.setBasip(basip);
                  q.setBasipLike(false);
                  q.setType(Integer.valueOf(type));
                  List basauths = CheckOnlineService.this.basauthService
                    .getPortalbasauthList(q);
                  if ((basauths != null) && (basauths.size() > 0)) {
                    String username = loginInfo[0];
                    if (basConfig.getIsdebug().equals("1")) {
                      CheckOnlineService.log.info("SessionTime Check  host: " + 
                        tOnlineUserSessionTimeCheck + 
                        " LoginName: " + username);
                    }
                    Portalbasauth basauth = (Portalbasauth)basauths.get(0);
                    Long sessionTime = basauth
                      .getSessiontime();
                    if ((sessionTime != null) && 
                      (sessionTime.longValue() > 0L)) {
                      String time = loginInfo[3];
                      Date loginTime = ThreadLocalDateUtil.parse(time);
                      String nowString = 
                        ThreadLocalDateUtil.format(new Date());
                      Date nowTime = 
                        ThreadLocalDateUtil.parse(nowString);
                      Long costTime = Long.valueOf(nowTime.getTime() - 
                        loginTime.getTime());
                      if (costTime.longValue() >= sessionTime.longValue()) {
                        Kick.kickUserSessionTimeOut(tOnlineUserSessionTimeCheck);
                      }

                    }

                  }

                }

              }

            }

          }
          catch (Exception e)
          {
            CheckOnlineService.log.error("==============SessionTimeCheck ERROR Start=============");
            CheckOnlineService.log.error(e);
            CheckOnlineService.log.error("ERROR INFO ", e);
            CheckOnlineService.log.error("==============SessionTimeCheck ERROR End=============");
          }

        }

        Iterator iterator = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
        while (iterator.hasNext()) {
          Object o = iterator.next();
          String t = o.toString();
          String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
          String username = loginInfo[0];

          int i = t.lastIndexOf(":");
          String basip = t.substring(i + 1);

          Portalbas userConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get(basip);
          if ((userConfig != null) && ("1".equals(loginInfo[6])) && ("1".equals(userConfig.getIsPortalCheck()))) {
            if (basConfig.getIsdebug().equals("1")) {
              CheckOnlineService.log.info("Check Online User , host: " + t + 
                " user: " + username);
            }
            new CheckOnline(t, username, loginInfo).start();
          }
        }
      }
    };
    long delay = 20000L;
    long intevalPeriod = this.checkTime.longValue() * 1000L;

    timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
  }

  private void weixinCheck() {
    TimerTask taskweixin = new TimerTask()
    {
      public void run() {
        Portalbas basConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get("");
        if (basConfig.getIsdebug().equals("1")) {
          CheckOnlineService.log.info("Start Check WeiXin Auth Temp List!!");
        }

        Iterator it = WeixinMap.getInstance().getWeixinipmap().keySet().iterator();
        Long nowTime = Long.valueOf(new Date().getTime());
        while (it.hasNext()) {
          Object o = it.next();
          String host = o.toString();
          if (basConfig.getIsdebug().equals("1")) {
            CheckOnlineService.log.info("Check WeiXin Auth Temp , host: " + host);
          }
          Long startTime = Long.valueOf(((Date)WeixinMap.getInstance().getWeixinipmap().get(host)).getTime());

          Long outTime = Long.valueOf(CheckOnlineService.this.weixinTime.longValue() * 1000L);
          if (nowTime.longValue() - startTime.longValue() >= outTime.longValue()) {
            if (host.startsWith("wifidog")) {
              WeixinMap.getInstance().getWeixinipmap()
                .remove(host);
              host = host.replace("wifidog", "");
              WifidogKick.WeixinOffLine(host);
            } else {
              Kick.WeixinOffLine(host);
              WeixinMap.getInstance().getWeixinipmap()
                .remove(host);
              MagicMap.getInstance().getMagicMap().remove(host);
              try
              {
                Iterator itWisprMap = WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().keySet()
                  .iterator();
                while (itWisprMap.hasNext()) {
                  Object oWispr = itWisprMap.next();
                  String hostWispr = oWispr.toString();
                  if (host.equals(hostWispr)) {
                    WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(hostWispr);

                    int i = host.lastIndexOf(":");
                    String uip = host.substring(0, i);

                    Iterator iterator = RadiusOnlineMap.getInstance()
                      .getRadiusOnlineMap().keySet().iterator();
                    while (iterator.hasNext()) {
                      Object oRadius = iterator.next();
                      String tRadius = oRadius.toString();
                      String[] radiusOnlineInfo = 
                        (String[])RadiusOnlineMap.getInstance()
                        .getRadiusOnlineMap().get(tRadius);
                      if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
                        (uip.equals(radiusOnlineInfo[2]))) {
                        RadiusCOA.req_COA(radiusOnlineInfo, "Radius Weixin Kick COA");
                      }
                    }
                  }
                }

              }
              catch (Exception localException)
              {
              }

              Iterator itopenidMap = OpenIdMap.getInstance()
                .getOpenIdMap().keySet()
                .iterator();
              while (itopenidMap.hasNext()) {
                Object oopenidMap = itopenidMap.next();
                String openid = oopenidMap.toString();
                String[] hosts = 
                  (String[])OpenIdMap.getInstance()
                  .getOpenIdMap().get(openid);
                if ((hosts != null) && (hosts.length > 0)) {
                  String hostopenid = hosts[0];
                  if (host.equals(hostopenid)) {
                    OpenIdMap.getInstance().getOpenIdMap()
                      .remove(openid);
                  }
                }
              }
            }
            if (basConfig.getIsdebug().equals("1"))
              CheckOnlineService.log.info(host + " Can Not Finish WinXin Auth!!");
          }
        }
      }
    };
    long delay = 20000L;
    long intevalPeriod = this.weixinTime.longValue() * 500L;

    timerweixinCheck.scheduleAtFixedRate(taskweixin, delay, intevalPeriod);
  }

  private void doLinkRecord(String[] loginInfo, String info)
  {
    try
    {
      String uid = loginInfo[1];
      String rid = loginInfo[2];
      if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
        return;
      }
      Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
      Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
      if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || 
        (recordId.longValue() == 0L)) {
        return;
      }
      Portallinkrecord linkRecord = this.linkRecordService
        .getPortallinkrecordByKey(recordId);
      Portalaccount account = this.accountService
        .getPortalaccountByKey(userId);
      String state = account.getState();

      long in = Long.valueOf(loginInfo[7]).longValue();
      long out = Long.valueOf(loginInfo[8]).longValue();
      long octets = in + out;
      linkRecord.setIns(Long.valueOf(in));
      linkRecord.setOuts(Long.valueOf(out));
      linkRecord.setOctets(Long.valueOf(octets));
      Date now = new Date();
      linkRecord.setEndDate(now);
      Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
      linkRecord.setTime(costTime);
      linkRecord.setState(state);
      linkRecord.setEx1("Portal");
      linkRecord.setEx2(info);
      this.linkRecordService.updatePortallinkrecordByKey(linkRecord);

      if (!state.equals("1")) {
        Long haveTime = account.getTime();
        Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
        if ((state.equals("3")) && 
          (account.getDate().getTime() <= now.getTime())) {
          account.setState("2");
          this.accountService.updatePortalaccountByKey(account);
        }

        if (state.equals("2")) {
          if (newHaveTime.longValue() <= 0L) {
            account.setState("4");
          }
          account.setTime(newHaveTime);
          this.accountService.updatePortalaccountByKey(account);
        }
        if ((state.equals("4")) || (state.equals("0"))) {
          long haveOctets = account.getOctets().longValue() - octets;
          if (haveOctets <= 0L) {
            account.setState("0");
          }
          account.setOctets(Long.valueOf(haveOctets));
          this.accountService.updatePortalaccountByKey(account);
        }
      }
    }
    catch (Exception localException)
    {
    }
  }

  private void doLogRecord(String info) {
    try {
      Portallogrecord logRecord = new Portallogrecord();
      Date nowDate = new Date();
      logRecord.setInfo(info);
      logRecord.setRecDate(nowDate);
      this.logRecordService.addPortallogrecord(logRecord);
    }
    catch (Exception localException)
    {
    }
  }

  private void ikuaiMacIpMapToDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(new FileOutputStream(
        servletContextEvent.getServletContext().getRealPath("/") + 
        "/ikuaiMacIpMap.dat"));

      os.writeObject(iKuaiMacIpMap.getInstance().getMacIpMap());

      if (basConfig.getIsdebug().equals("1"))
        log.info("ikuaiMacIpMapToDisk!!");
    }
    catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e1) {
        log.error("==============ERROR Start=============");
        log.error(e1);
        log.error("ERROR INFO ", e1);
        log.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  private void ikuaiMacIpMapFromDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectInputStream is = null;
    File parent = new File(servletContextEvent.getServletContext()
    		 .getRealPath("/") + "/ikuaiMacIpMap.dat");

    label331: 
    try { if (parent.exists()) {
        is = new ObjectInputStream(new FileInputStream(
          servletContextEvent.getServletContext()
          .getRealPath("/") + "/ikuaiMacIpMap.dat"));
        HashMap ikuaiMacIpMap = (HashMap)is
          .readObject();

        iKuaiMacIpMap.getInstance().setMacIpMap(ikuaiMacIpMap);

        if (basConfig.getIsdebug().equals("1")) {
          log.info("ikuaiMacIpMapFromDisk!!");

          break label331;
        } } else if (basConfig.getIsdebug().equals("1")) {
        log.info("ikuaiMacIpMap File Not Exists!!");
      }
    } catch (Exception e)
    {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e1) {
        log.error("==============ERROR Start=============");
        log.error(e1);
        log.error("ERROR INFO ", e1);
        log.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  private void OnlineUserMapToDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(new FileOutputStream(
        servletContextEvent.getServletContext().getRealPath("/") + 
        "/OnlineUserMap.dat"));
      if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue())
        os.writeObject(new ConcurrentHashMap());
      else {
        os.writeObject(OnlineMap.getInstance().getOnlineUserMap());
      }
      if (basConfig.getIsdebug().equals("1"))
        log.info("OnlineUserMapToDisk!!");
    }
    catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e1) {
        log.error("==============ERROR Start=============");
        log.error(e1);
        log.error("ERROR INFO ", e1);
        log.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  private void OnlineUserMapFromDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectInputStream is = null;
    File parent = new File(servletContextEvent.getServletContext()
    		.getRealPath("/") + "/OnlineUserMap.dat");

    label331: 
    try { if (parent.exists()) {
        is = new ObjectInputStream(new FileInputStream(
          servletContextEvent.getServletContext()
          .getRealPath("/") + "/OnlineUserMap.dat"));
        Map OnlineUserMap = (ConcurrentHashMap)is
          .readObject();
        OnlineMap.getInstance().setOnlineUserMap(OnlineUserMap);
        if (basConfig.getIsdebug().equals("1")) {
          log.info("OnlineUserMapFromDisk!!");

          break label331;
        } } else if (basConfig.getIsdebug().equals("1")) {
        log.info("OnlineUserMap File Not Exists!!");
      }
    } catch (Exception e)
    {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e1) {
        log.error("==============ERROR Start=============");
        log.error(e1);
        log.error("ERROR INFO ", e1);
        log.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  private void WeixinMapToDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(new FileOutputStream(
        servletContextEvent.getServletContext().getRealPath("/") + 
        "/WeixinMap.dat"));
      if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue())
        os.writeObject(new ConcurrentHashMap());
      else {
        os.writeObject(WeixinMap.getInstance().getWeixinipmap());
      }
      if (basConfig.getIsdebug().equals("1"))
        log.info("WeixinMapToDisk!!");
    }
    catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e1) {
        log.error("==============ERROR Start=============");
        log.error(e1);
        log.error("ERROR INFO ", e1);
        log.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  private void WeixinMapFromDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectInputStream is = null;
    File parent = new File(servletContextEvent.getServletContext()
    		 .getRealPath("/") + "/WeixinMap.dat");

    label331: 
    try { if (parent.exists()) {
        is = new ObjectInputStream(new FileInputStream(
          servletContextEvent.getServletContext()
          .getRealPath("/") + "/WeixinMap.dat"));
        Map weixinipmap = (ConcurrentHashMap)is
          .readObject();
        WeixinMap.getInstance().setWeixinipmap(weixinipmap);
        if (basConfig.getIsdebug().equals("1")) {
          log.info("WeixinMapFromDisk!!");

          break label331;
        } } else if (basConfig.getIsdebug().equals("1")) {
        log.info("WeixinMap File Not Exists!!");
      }
    } catch (Exception e)
    {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e1) {
        log.error("==============ERROR Start=============");
        log.error(e1);
        log.error("ERROR INFO ", e1);
        log.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  private void deleteFiles(String path) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    File file = new File(path + "/OnlineUserMap.dat");
    if (file.exists()) {
      file.delete();
      if (basConfig.getIsdebug().equals("1")) {
        log.info("Server Start Format OnlineUserMap File!!");
      }
    }
    file = null;
    file = new File(path + "/WeixinMap.dat");
    if (file.exists()) {
      file.delete();
      if (basConfig.getIsdebug().equals("1")) {
        log.info("Server Start Format WeixinMap File!!");
      }
    }
    file = null;
  }

  public static boolean Do() {
    Long isThis = Long.valueOf(new Date().getTime());
    boolean Do = false;
    if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
      Do = true;
    }
    return Do;
  }

  private void RecordOnlineCount() {
    TimerTask task = new TimerTask()
    {
      public void run() {
        try {
          Portalbas basConfig = (Portalbas)CheckOnlineService.config.getConfigMap().get("");
          if (basConfig.getIsdebug().equals("1")) {
            CheckOnlineService.log.info("Do Record Online Count Service !!");
          }
          long counts = OnlineMap.getInstance().getOnlineUserMap().size();
          Date now = new Date();
          int year = now.getYear() + 1900;
          int month = now.getMonth() + 1;
          int day = now.getDate();
          int week = now.getDay();
          if (week == 0) {
            week = 7;
          }
          int hour = now.getHours();

          if (basConfig.getIsdebug().equals("1")) {
            CheckOnlineService.log.info("Online Count=" + counts + " " + year + "-" + month + "-" + day + " week=" + week + " HH=" + hour);
          }

          HistoryonlineQuery hq = new HistoryonlineQuery();
          hq.setRecDay(Integer.valueOf(day));
          hq.setRecMonth(Integer.valueOf(month));
          hq.setRecWeek(Integer.valueOf(week));
          hq.setRecYear(Integer.valueOf(year));
          hq.setRecTime(Integer.valueOf(hour));
          List hs = CheckOnlineService.this.historyonlineService.getHistoryonlineList(hq);
          if (hs.size() > 0) {
            Historyonline oh = (Historyonline)hs.get(0);
            long oC = oh.getCounts().longValue();
            if (counts > oC) {
              oh.setCounts(Long.valueOf(counts));
              oh.setRecDate(now);
              CheckOnlineService.this.historyonlineService.updateHistoryonlineByKey(oh);
            }
          } else {
            Historyonline r = new Historyonline();
            r.setCounts(Long.valueOf(counts));
            r.setRecDate(now);
            r.setRecDay(Integer.valueOf(day));
            r.setRecMonth(Integer.valueOf(month));
            r.setRecWeek(Integer.valueOf(week));
            r.setRecYear(Integer.valueOf(year));
            r.setRecTime(Integer.valueOf(hour));
            CheckOnlineService.this.historyonlineService.addHistoryonline(r);
          }
        } catch (Exception e) {
          CheckOnlineService.log.error("==============ERROR Start=============");
          CheckOnlineService.log.error(e);
          CheckOnlineService.log.error("ERROR INFO ", e);
          CheckOnlineService.log.error("==============ERROR End=============");
        }
      }
    };
    long delay = 60000L;
    long intevalPeriod = 60000L;

    timerRecordOnlineCount.scheduleAtFixedRate(task, delay, intevalPeriod);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.CheckOnlineService
 * JD-Core Version:    0.6.2
 */