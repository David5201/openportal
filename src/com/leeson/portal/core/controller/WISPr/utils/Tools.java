package com.leeson.portal.core.controller.WISPr.utils;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.bean.Portalap;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portalip;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.bean.Portaltimeweb;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.query.PortalapQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalipQuery;
import com.leeson.core.query.PortalssidQuery;
import com.leeson.core.query.PortaltimewebQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalapService;
import com.leeson.core.service.PortalautologinService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortalipService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortalonlinelimitService;
import com.leeson.core.service.PortalssidService;
import com.leeson.core.service.PortaltimewebService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.utils.IPv4Util;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.RosAuthMap;
import com.leeson.portal.core.model.UserLimitMap;
import com.leeson.portal.core.model.ipMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class Tools
{
  private static Logger logger = Logger.getLogger(Tools.class);

  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

  private static PortalaccountService accountService = (PortalaccountService)
    SpringContextHelper.getBean("portalaccountServiceImpl");

  private static PortalautologinService autologinService = (PortalautologinService)
    SpringContextHelper.getBean("portalautologinServiceImpl");

  private static PortalapService apService = (PortalapService)
    SpringContextHelper.getBean("portalapServiceImpl");

  private static PortalbasauthService basauthService = (PortalbasauthService)
    SpringContextHelper.getBean("portalbasauthServiceImpl");

  private static PortalonlinelimitService onlinelimitService = (PortalonlinelimitService)
    SpringContextHelper.getBean("portalonlinelimitServiceImpl");

  private static PortalaccountmacsService macsService = (PortalaccountmacsService)
    SpringContextHelper.getBean("portalaccountmacsServiceImpl");

  private static PortalssidService ssidService = (PortalssidService)
    SpringContextHelper.getBean("portalssidServiceImpl");

  private static PortalwebService webService = (PortalwebService)
    SpringContextHelper.getBean("portalwebServiceImpl");

  private static PortallinkrecordService linkRecordService = (PortallinkrecordService)
    SpringContextHelper.getBean("portallinkrecordServiceImpl");

  private static PortalipService ipService = (PortalipService)
    SpringContextHelper.getBean("portalipServiceImpl");

  private static ConfigService configService = (ConfigService)
    SpringContextHelper.getBean("configServiceImpl");

  private static PortaltimewebService portaltimewebService = (PortaltimewebService)
    SpringContextHelper.getBean("portaltimewebServiceImpl");

  public static int CheckMacTimeLimitMethod(String param, Long id)
  {
    Portalonlinelimit onlinelimit = onlinelimitService
      .getPortalonlinelimitByKey(id);
    if (onlinelimit.getState().intValue() == 1) {
      if (onlinelimit.getType().intValue() == 0) {
        if ((stringUtils.isNotBlank(param)) && (!"error".equals(param))) {
          String[] TimeInfo = 
            (String[])MacLimitMap.getInstance()
            .getMacLimitMap().get(param);
          if (TimeInfo != null) {
            Long timepermit = onlinelimit.getTime();
            if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
              return 1;
          }
        }
        else {
          return 2;
        }
      }
      else if (stringUtils.isNotBlank(param)) {
        String[] TimeInfo = 
          (String[])UserLimitMap.getInstance()
          .getUserLimitMap().get(param);
        if (TimeInfo != null) {
          Long timepermit = onlinelimit.getTime();
          if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
            return 1;
        }
      }
      else {
        return 2;
      }
    }

    return 0;
  }

  public static boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets)
  {
    Portalaccount account = accountService.getPortalaccountByKey(userId);

    if (userState.equals("0")) {
      return false;
    }

    if (userState.equals("1")) {
      return true;
    }

    if (userState.equals("3")) {
      Date now = new Date();
      if (userDate.getTime() > now.getTime()) {
        return true;
      }
      account.setState("2");
      accountService.updatePortalaccountByKey(account);
      userState = "2";
    }

    if (userState.equals("2")) {
      if (userTime.longValue() > 0L) {
        return true;
      }
      account.setState("4");
      accountService.updatePortalaccountByKey(account);
      userState = "4";
    }

    if (userState.equals("4")) {
      if (octets.longValue() > 0L) {
        return true;
      }
      account.setState("0");
      accountService.updatePortalaccountByKey(account);
      return false;
    }

    return false;
  }

  public static boolean Do() {
    Long isThis = Long.valueOf(new Date().getTime());
    boolean Do = false;
    if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
      Do = true;
    }
    return Do;
  }

  public static boolean autoLogin(String basip, String ip, String ikmac, Portalbas basConfig, HttpSession session)
  {
    try {
      if (Do())
      {
        String authUser;
        Long userId;
        if (stringUtils.isNotBlank(ikmac)) {
          String[] macTimeInfo = 
            (String[])AutoLoginMacMap.getInstance()
            .getAutoLoginMacMap().get(ikmac);
          if (macTimeInfo != null) {
            String[] userinfo = 
              (String[])AutoLoginMap.getInstance()
              .getAutoLoginMap().get(ikmac);
            try {
              Long id = Long.valueOf(macTimeInfo[2]);
              if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
                authUser = userinfo[0];
                String authPwd = userinfo[1];
                String username = userinfo[2];
                Portalautologin autologin = autologinService
                  .getPortalautologinByKey(id);
                if ((autologin != null) && 
                  (autologin.getState().intValue() == 1)) {
                  Long timepermit = autologin.getTime();
                  String loginTimeString = macTimeInfo[0];
                  Long oldcostTime = 
                    Long.valueOf(macTimeInfo[1]);
                  Long costTime = oldcostTime;

                  if (stringUtils.isNotBlank(loginTimeString)) {
                    Date loginTime = 
                      ThreadLocalDateUtil.parse(loginTimeString);
                    String nowString = 
                      ThreadLocalDateUtil.format(new Date());
                    Date nowTime = 
                      ThreadLocalDateUtil.parse(nowString);
                    costTime = Long.valueOf(nowTime.getTime() - 
                      loginTime.getTime() + 
                      oldcostTime.longValue());
                  }
                  if ((costTime.longValue() < timepermit.longValue()) || 
                    (timepermit.longValue() <= 0L)) {
                    userId = null;
                    String userState = null;
                    String password = authPwd;
                    boolean CheckAccount = false;
                    if (3L == id.longValue())
                    {
                      PortalaccountQuery accq = new PortalaccountQuery();
                      accq.setLoginNameLike(false);
                      accq.setLoginName(username);

                      if (!"1".equals(basConfig
                        .getIsPortalCheck())) {
                        accq.setPasswordLike(false);
                        accq.setPassword(password);
                      }
                      List accs = accountService
                        .getPortalaccountList(accq);
                      if ((accs != null) && 
                        (accs.size() == 1)) {
                        Portalaccount acc = 
                          (Portalaccount)accs
                          .get(0);
                        if (acc != null) {
                          userId = acc.getId();
                          Date userDate = acc
                            .getDate();
                          Long userTime = acc
                            .getTime();
                          Long octets = acc
                            .getOctets();
                          if (octets == null) {
                            octets = Long.valueOf(0L);
                          }
                          userState = acc
                            .getState();
                          password = acc
                            .getPassword();

                          if (checkTimeOut(
                            userState, 
                            userId, 
                            userDate, 
                            userTime, 
                            octets)) {
                            CheckAccount = true;
                          }

                          if (!"1"
                            .equals(basConfig
                            .getIsPortalCheck()))
                          {
                            if (!password
                              .equals(authPwd)) {
                              CheckAccount = false;
                            }

                          }

                          if (CheckAccount)
                          {
                            if ("1".equals(basConfig
                              .getIsPortalCheck())) {
                              Integer limitCount = acc
                                .getMaclimitcount();
                              if ((limitCount != null) && 
                                (limitCount.intValue() > 0)) {
                                int count = 0;
                                Iterator iterator = 
                                  OnlineMap.getInstance()
                                  .getOnlineUserMap()
                                  .keySet()
                                  .iterator();

                                while (iterator
                                  .hasNext()) {
                                  Object o = iterator
                                    .next();
                                  String t = o
                                    .toString();
                                  String[] loginInfo = 
                                    (String[])OnlineMap.getInstance()
                                    .getOnlineUserMap()
                                    .get(t);
                                  String haveUsername = loginInfo[0];

                                  if (username
                                    .equals(haveUsername)) {
                                    count++;
                                  }
                                }
                                if (count >= limitCount.intValue())
                                  CheckAccount = false;
                              }
                            }
                          }
                        }
                      }
                    }
                    else
                    {
                      CheckAccount = true;
                    }

                    if (CheckAccount) {
                      String tid = "0";
                      if (1L == id.longValue())
                        tid = "4";
                      else if (2L == id.longValue())
                        tid = "0";
                      else if (3L == id.longValue())
                        tid = "1";
                      else if (4L == id.longValue())
                        tid = "2";
                      else if (5L == id.longValue())
                        tid = "3";
                      else if (6L == id.longValue())
                        tid = "5";
                      else if (7L == id.longValue())
                        tid = "6";
                      else if (8L == id.longValue()) {
                        tid = "7";
                      }

                      String[] rosInfo = new String[6];
                      rosInfo[0] = tid;
                      rosInfo[1] = macTimeInfo[2];
                      rosInfo[2] = username;
                      rosInfo[3] = authUser;
                      rosInfo[4] = authPwd;
                      rosInfo[5] = "auto";
                      RosAuthMap.getInstance()
                        .getRosAuthMap()
                        .put(ikmac, rosInfo);

                      session.setAttribute(
                        "username", username);
                      session.setAttribute(
                        "password", password);
                      session.setAttribute(
                        "authUser", authUser);
                      session.setAttribute("authPwd", 
                        authPwd);
                      session.setAttribute("ip", ip);
                      session.setAttribute("basip", 
                        basip);
                      return true;
                    }
                    AutoLoginMacMap.getInstance()
                      .getAutoLoginMacMap()
                      .remove(ikmac);
                    AutoLoginMap.getInstance()
                      .getAutoLoginMap()
                      .remove(ikmac);
                  }
                  else
                  {
                    AutoLoginMacMap.getInstance()
                      .getAutoLoginMacMap()
                      .remove(ikmac);
                    AutoLoginMap.getInstance()
                      .getAutoLoginMap()
                      .remove(ikmac);
                  }
                }
              }
            }
            catch (Exception localException1)
            {
            }
          }
        }
        if ((1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L))) && 
          (1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L))))
        {
          if (!ipMap.getInstance().getIpmap().containsKey(ip)) {
            ipMap.getInstance().getIpmap().put(ip, Integer.valueOf(1));

            String userMac = ikmac;

            if (stringUtils.isNotBlank(userMac))
            {
              List<Portalaccountmacs> macs = macsService
                .getPortalaccountmacsList(new PortalaccountmacsQuery());
              for (Portalaccountmacs mac : macs) {
                if (mac.getMac().equals(userMac)) {
                  Portalaccount acc = accountService
                    .getPortalaccountByKey(mac
                    .getAccountId());
                  int state = acc.getAutologin().intValue();
                  if (state != 1) {
                    break;
                  }
                  if (basConfig.getAuthInterface()
                    .contains("1"))
                  {
                    PortalbasauthQuery bsq = new PortalbasauthQuery();
                    bsq.setBasip(basip);
                    authUser = basConfig
                      .getBasUser();
                    String authPwd = basConfig
                      .getBasPwd();
                    List<Portalbasauth> basauths = basauthService
                      .getPortalbasauthList(bsq);
                    if (basauths.size() > 0) {
                      for (Portalbasauth ba : basauths) {
                        if (ba.getType().intValue() == 1) {
                          authUser = ba
                            .getUsername();
                          authPwd = ba
                            .getPassword();

                          if ((!stringUtils.isBlank(authUser)) && 
                            (!stringUtils.isBlank(authPwd))) break;
                          authUser = basConfig
                            .getBasUser();
                          authPwd = basConfig
                            .getBasPwd();

                          break;
                        }
                      }
                    }
                    if (acc == null) break;
                    userId = acc.getId();
                    Date userDate = acc.getDate();
                    Long userTime = acc.getTime();
                    Long octets = acc.getOctets();
                    if (octets == null) {
                      octets = Long.valueOf(0L);
                    }
                    String username = acc
                      .getLoginName();
                    String userState = acc
                      .getState();
                    String password = acc
                      .getPassword();

                    if (!"1".equals(basConfig
                      .getIsPortalCheck())) {
                      authUser = username;
                      authPwd = password;
                    }

                    if (!checkTimeOut(userState, 
                      userId, userDate, 
                      userTime, octets)) {
                      break;
                    }
                    boolean onlimitCan = true;

                    if ("1".equals(basConfig
                      .getIsPortalCheck())) {
                      Integer limitCount = acc
                        .getMaclimitcount();
                      if ((limitCount != null) && 
                        (limitCount.intValue() > 0)) {
                        int count = 0;
                        Iterator iterator = 
                          OnlineMap.getInstance()
                          .getOnlineUserMap()
                          .keySet()
                          .iterator();

                        while (iterator
                          .hasNext()) {
                          Object o = iterator
                            .next();
                          String t = o
                            .toString();
                          String[] loginInfo = 
                            (String[])OnlineMap.getInstance()
                            .getOnlineUserMap()
                            .get(t);
                          String haveUsername = loginInfo[0];

                          if (username
                            .equals(haveUsername)) {
                            count++;
                          }
                        }
                        if (count >= limitCount.intValue()) {
                          onlimitCan = false;
                        }

                      }

                    }

                    if (!onlimitCan) break;
                    String[] rosInfo = new String[6];
                    rosInfo[0] = "1";
                    rosInfo[1] = "3";
                    rosInfo[2] = username;
                    rosInfo[3] = authUser;
                    rosInfo[4] = authPwd;
                    rosInfo[5] = "auto";

                    RosAuthMap.getInstance()
                      .getRosAuthMap()
                      .put(ikmac, 
                      rosInfo);

                    session.setAttribute(
                      "username", 
                      username);
                    session.setAttribute(
                      "password", 
                      password);
                    session.setAttribute(
                      "authUser", 
                      authUser);
                    session.setAttribute(
                      "authPwd", 
                      authPwd);
                    session.setAttribute(
                      "ip", ip);
                    session.setAttribute(
                      "basip", basip);

                    ipMap.getInstance()
                      .getIpmap()
                      .remove(ip);
                    return true;
                  }

                  String[] userinfo = null;
                  userinfo = 
                    (String[])AutoLoginMap.getInstance()
                    .getAutoLoginMap()
                    .get(userMac);
                  String username = "";
                  String password = "";
                  String phone = "";
                  if ((userinfo != null) && 
                    (userinfo.length == 2)) {
                    username = userinfo[0];
                    password = userinfo[1];
                  }
                  if ((userinfo != null) && 
                    (userinfo.length == 3)) {
                    username = userinfo[0];
                    password = userinfo[1];
                    phone = userinfo[2];
                  }

                  String[] rosInfo = new String[6];
                  rosInfo[0] = "2";
                  rosInfo[1] = "4";
                  rosInfo[2] = phone;
                  rosInfo[3] = username;
                  rosInfo[4] = password;
                  rosInfo[5] = "auto";
                  RosAuthMap.getInstance()
                    .getRosAuthMap()
                    .put(ikmac, rosInfo);

                  session.setAttribute(
                    "username", phone);
                  session.setAttribute(
                    "password", password);
                  session.setAttribute(
                    "authUser", username);
                  session.setAttribute("authPwd", 
                    password);
                  session.setAttribute("ip", ip);
                  session.setAttribute("basip", 
                    basip);

                  ipMap.getInstance().getIpmap()
                    .remove(ip);
                  return true;
                }

              }

            }

          }

        }

      }

    }
    catch (Exception e)
    {
      logger.error("==============ERROR Start=============");
      logger.error(e);
      logger.error("ERROR INFO ", e);
      logger.error("==============ERROR End=============");
    }
    ipMap.getInstance().getIpmap().remove(ip);
    return false;
  }

  public static String getWebMod(String ssid, String apmac, String ip, Long basConfigWeb)
  {
    String webMod = "";
    Long webID = Long.valueOf(0L);
    if (Do()) {
      try {
        PortaltimewebQuery timewebq;
        try {
          Date date = new Date();
          int weekDay = date.getDay();
          if (weekDay == 0) {
            weekDay = 7;
          }
          int dateDay = date.getDate();
          int monthDay = date.getMonth() + 1;

          timewebq = new PortaltimewebQuery();
          timewebq.orderbyPos(true);
          timewebq.orderbyId(true);
          List<Portaltimeweb> timewebs = portaltimewebService
            .getPortaltimewebList(timewebq);
          if ((timewebs != null) && (timewebs.size() > 0))
            for (Portaltimeweb timeweb : timewebs) {
              Date setday = timeweb.getViewdate();
              int weekday = timeweb.getViewweekday().intValue();
              int dateday = timeweb.getViewdateday().intValue();
              int monthday = timeweb.getViewmonth().intValue();
              if ((setday != null) || (weekday != 0) || (dateday != 0) || 
                (monthday != 0))
              {
                if (setday != null) {
                  long time = date.getTime();
                  long endTime = setday.getTime() + 86400000L;
                  if ((setday.getTime() <= time) && (time <= endTime)) {
                    Portalweb web = webService
                      .getPortalwebByKey(timeweb.getWeb());
                    if (web != null) {
                      webMod = String.valueOf(web.getId()) + 
                        "/";
                      webID = web.getId();
                      break;
                    }
                  }
                } else {
                  boolean weekState = false;
                  boolean dayState = false;
                  boolean monthState = false;
                  if (weekday == 0) {
                    weekState = true;
                  }
                  else if (weekDay == weekday) {
                    weekState = true;
                  }

                  if (dateday == 0) {
                    dayState = true;
                  }
                  else if (dateDay == dateday) {
                    dayState = true;
                  }

                  if (monthday == 0) {
                    monthState = true;
                  }
                  else if (monthDay == monthday) {
                    monthState = true;
                  }

                  if ((weekState) && (dayState) && (monthState)) {
                    Portalweb web = webService
                      .getPortalwebByKey(timeweb.getWeb());
                    if (web != null) {
                      webMod = String.valueOf(web.getId()) + 
                        "/";
                      webID = web.getId();
                      break;
                    }
                  }
                }
              }
            }
        }
        catch (Exception localException1) {
        }
        if ((stringUtils.isBlank(webMod)) && 
          (stringUtils.isNotBlank(ssid))) {
          PortalssidQuery ssidq = new PortalssidQuery();
          ssidq.setSsid(ssid);
          ssidq.setSsidLike(false);
          List ssids = ssidService
            .getPortalssidList(ssidq);
          if ((ssids != null) && (ssids.size() > 0)) {
            Portalssid sside = (Portalssid)ssids.get(0);
            Portalweb web = webService.getPortalwebByKey(sside
              .getWeb());
            if (web != null) {
              webMod = String.valueOf(web.getId()) + "/";
              webID = web.getId();
            }
          }
        }

        if ((stringUtils.isBlank(webMod)) && 
          (stringUtils.isNotBlank(apmac))) {
          PortalapQuery apq = new PortalapQuery();
          apq.setMac(apmac);
          apq.setMacLike(false);
          List aps = apService.getPortalapList(apq);
          if ((aps != null) && (aps.size() > 0)) {
            Portalap ap = (Portalap)aps.get(0);
            Portalweb web = webService.getPortalwebByKey(ap
              .getWeb());
            if (web != null) {
              webMod = String.valueOf(web.getId()) + "/";
              webID = web.getId();
            }
          }
        }

        if (stringUtils.isBlank(webMod)) {
          Portalweb web = webService.getPortalwebByKey(basConfigWeb);
          if (web != null) {
            webMod = String.valueOf(web.getId()) + "/";
            webID = web.getId();
          }
        }
        if (stringUtils.isBlank(webMod)) {
          long ipL = IPv4Util.ipToLong(ip);
          List<Portalip> iplList = ipService
            .getPortalipList(new PortalipQuery());
          for (Portalip portalip : iplList) {
            long startH = IPv4Util.ipToLong(portalip.getStart());
            long endH = IPv4Util.ipToLong(portalip.getEnd());
            if ((ipL >= startH) && (ipL <= endH)) {
              Portalweb web = webService
                .getPortalwebByKey(portalip.getWeb());
              if (web == null) break;
              webMod = String.valueOf(web.getId()) + "/";
              webID = web.getId();

              break;
            }
          }
        }
      } catch (Exception e) {
        webMod = "";
        webID = Long.valueOf(0L);
      }
    }
    try
    {
      if (webID.longValue() != 0L) {
        Portalweb portalweb = webService.getPortalwebByKey(webID);
        if (portalweb != null) {
          portalweb.setCountShow(Long.valueOf(portalweb.getCountShow().longValue() + 1L));
          webService.updatePortalwebByKey(portalweb);
        }
      } else {
        com.leeson.core.bean.Config config = configService
          .getConfigByKey(Long.valueOf(1L));
        if (config != null) {
          config.setCountShow(Long.valueOf(config.getCountShow().longValue() + 1L));
          configService.updateConfigByKey(config);
        }
      }
    }
    catch (Exception localException2) {
    }
    return webMod;
  }

  public static Long getAccountid(String username)
  {
    PortalaccountQuery q = new PortalaccountQuery();
    q.setLoginName(username);
    q.setLoginNameLike(false);
    List accs = accountService.getPortalaccountList(q);
    Portalaccount acc = null;
    if (accs.size() > 0) {
      acc = (Portalaccount)accs.get(0);
    }
    if (acc != null) {
      return acc.getId();
    }
    return null;
  }

  public static Long doLinkRecord(Long userId, String ip, String basip, String mac)
  {
    Portalbas basconfig = (Portalbas)config.getConfigMap().get(basip);
    if ((basconfig != null) && 
      ("1".equals(basconfig.getIsPortalCheck()))) {
      Portalaccount acc = accountService
        .getPortalaccountByKey(userId);
      if (acc != null) {
        String username = acc.getLoginName();
        String userState = acc.getState();
        Portallinkrecord linkRecord = new Portallinkrecord();
        linkRecord.setStartDate(new Date());
        linkRecord.setIp(ip);
        linkRecord.setBasip(basip);
        linkRecord.setMac(mac);
        linkRecord.setIns(Long.valueOf(0L));
        linkRecord.setOuts(Long.valueOf(0L));
        linkRecord.setOctets(Long.valueOf(0L));
        linkRecord.setLoginName(username);
        linkRecord.setState(userState);
        linkRecord.setUid(userId);
        linkRecordService.addPortallinkrecord(linkRecord);
        return linkRecord.getId();
      }
    }

    return null;
  }

  public static void GetMacTimeLimitMethod(String[] loginInfo, String mac, HttpSession session)
  {
    String username = loginInfo[0];
    String uid = loginInfo[1];
    String rid = loginInfo[2];
    String loginTimeString = loginInfo[3];
    if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
      mac = loginInfo[4];
    }
    Date nowTime = new Date();
    Date loginTime = nowTime;
    try {
      loginTime = ThreadLocalDateUtil.parse(loginTimeString);
    }
    catch (Exception localException)
    {
    }

    int costTime = (int)((nowTime.getTime() - loginTime.getTime()) / 60000L);

    int haveTime = getHaveTime();
    if ((!stringUtils.isBlank(uid)) && (!stringUtils.isBlank(rid))) {
      haveTime = (int)(getTime(username).longValue() / 60000L);
    }
    int toHaveTime = haveTime;

    Long oldcostTime = Long.valueOf(0L);
    Boolean notLimit = Boolean.valueOf(true);
    if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])MacLimitMap.getInstance().getMacLimitMap()
        .get(mac);
      if (macTimeInfo != null) {
        Long id = Long.valueOf(macTimeInfo[2]);
        oldcostTime = Long.valueOf(macTimeInfo[1]);
        Portalonlinelimit onlinelimit = onlinelimitService
          .getPortalonlinelimitByKey(id);
        if (onlinelimit != null) {
          Long timepermit = onlinelimit.getTime();
          loginTimeString = macTimeInfo[0];
          if (onlinelimit.getState().intValue() == 1)
          {
            if (stringUtils.isNotBlank(loginTimeString))
              try {
                loginTime = 
                  ThreadLocalDateUtil.parse(loginTimeString);
              }
              catch (Exception localException1)
              {
              }
            costTime = (int)((nowTime.getTime() - 
              loginTime.getTime() + oldcostTime.longValue()) / 60000L);
            haveTime = (int)(timepermit.longValue() / 60000L);
            notLimit = Boolean.valueOf(false);
          }
        }
      }
    }

    int overTime = costTime;
    if (!notLimit.booleanValue()) {
      haveTime -= overTime;
    }
    if (haveTime > toHaveTime) {
      haveTime = toHaveTime;
    }
    if (haveTime < 0) {
      haveTime = 0;
    }
    if (notLimit.booleanValue()) {
      overTime = costTime + (int)(oldcostTime.longValue() / 60000L);
    }

    String haveTimeString = String.valueOf(haveTime);
    String overTimeString = String.valueOf(overTime);
    session.setAttribute("haveTime", haveTimeString);
    session.setAttribute("overTime", overTimeString);
  }

  public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, String authUser, String authPwd, String phone, HttpServletResponse response)
  {
    Portalonlinelimit onlinelimit = onlinelimitService
      .getPortalonlinelimitByKey(id);
    Long timepermit = onlinelimit.getTime();
    Long costTime = Long.valueOf(0L);
    int haveTime = getHaveTime();
    int toHaveTime = haveTime;
    Long oldcostTime = Long.valueOf(0L);
    Boolean notLimit = Boolean.valueOf(true);
    if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])MacLimitMap.getInstance().getMacLimitMap()
        .get(mac);
      if (macTimeInfo != null) {
        oldcostTime = Long.valueOf(macTimeInfo[1]);
      }
      if (onlinelimit.getState().intValue() == 1) {
        Date now = new Date();
        String nowString = ThreadLocalDateUtil.format(now);
        if (macTimeInfo == null) {
          macTimeInfo = new String[3];
          macTimeInfo[1] = "0";
        }
        macTimeInfo[0] = nowString;
        macTimeInfo[2] = String.valueOf(id);
        MacLimitMap.getInstance().getMacLimitMap()
          .put(mac, macTimeInfo);
        costTime = oldcostTime;
        haveTime = (int)(timepermit.longValue() / 60000L);
        notLimit = Boolean.valueOf(false);

        if (id.longValue() == 1L) {
          String[] TimeInfo = 
            (String[])UserLimitMap.getInstance()
            .getUserLimitMap().get(phone);
          if (TimeInfo == null) {
            TimeInfo = new String[3];
            TimeInfo[1] = "0";
          }
          TimeInfo[0] = nowString;
          TimeInfo[2] = String.valueOf(id);
          UserLimitMap.getInstance().getUserLimitMap()
            .put(phone, TimeInfo);
          if (onlinelimit.getType().intValue() == 1) {
            costTime = Long.valueOf(TimeInfo[1]);
            haveTime = (int)(timepermit.longValue() / 60000L);
          }
        }
      }

      Cookie cookieMac = new Cookie("mac", mac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", mac);
    }
    int overTime = (int)(costTime.longValue() / 60000L);
    haveTime -= overTime;
    if (haveTime > toHaveTime) {
      haveTime = toHaveTime;
    }
    if (haveTime < 0) {
      haveTime = 0;
    }
    if (notLimit.booleanValue()) {
      overTime += (int)(oldcostTime.longValue() / 60000L);
    }
    String haveTimeString = String.valueOf(haveTime);
    String overTimeString = String.valueOf(overTime);
    session.setAttribute("haveTime", haveTimeString);
    session.setAttribute("overTime", overTimeString);

    Boolean isAuto = Boolean.valueOf(false);
    if (isAuto.booleanValue())
    {
      if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
        String[] userinfo = new String[3];
        userinfo[0] = authUser;
        userinfo[1] = authPwd;
        userinfo[2] = phone;
        AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
      }
    }
  }

  public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response)
  {
    Portalonlinelimit onlinelimit = onlinelimitService
      .getPortalonlinelimitByKey(id);
    Long timepermit = onlinelimit.getTime();
    Long costTime = Long.valueOf(0L);
    int haveTime = getHaveTime();
    int toHaveTime = haveTime;
    Long oldcostTime = Long.valueOf(0L);
    Boolean notLimit = Boolean.valueOf(true);
    if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])MacLimitMap.getInstance().getMacLimitMap()
        .get(mac);
      if (macTimeInfo != null) {
        oldcostTime = Long.valueOf(macTimeInfo[1]);
      }
      if ((onlinelimit.getState().intValue() == 1) && 
        (onlinelimit.getType().intValue() == 0)) {
        Date now = new Date();
        String nowString = ThreadLocalDateUtil.format(now);
        if (macTimeInfo == null) {
          macTimeInfo = new String[3];
          macTimeInfo[1] = "0";
        }
        macTimeInfo[0] = nowString;
        macTimeInfo[2] = String.valueOf(id);
        MacLimitMap.getInstance().getMacLimitMap()
          .put(mac, macTimeInfo);
        costTime = oldcostTime;
        haveTime = (int)(timepermit.longValue() / 60000L);
        notLimit = Boolean.valueOf(false);
      }

      Cookie cookieMac = new Cookie("mac", mac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", mac);
    }
    int overTime = (int)(costTime.longValue() / 60000L);
    haveTime -= overTime;
    if (haveTime > toHaveTime) {
      haveTime = toHaveTime;
    }
    if (haveTime < 0) {
      haveTime = 0;
    }
    if (notLimit.booleanValue()) {
      overTime += (int)(oldcostTime.longValue() / 60000L);
    }
    String haveTimeString = String.valueOf(haveTime);
    String overTimeString = String.valueOf(overTime);
    session.setAttribute("haveTime", haveTimeString);
    session.setAttribute("overTime", overTimeString);
  }

  public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, HttpServletResponse response, String username)
  {
    Portalonlinelimit onlinelimit = onlinelimitService
      .getPortalonlinelimitByKey(id);
    Long timepermit = onlinelimit.getTime();
    Long costTime = Long.valueOf(0L);
    int haveTime = (int)(getTime(username).longValue() / 60000L);
    int toHaveTime = haveTime;
    Long oldcostTime = Long.valueOf(0L);
    Boolean notLimit = Boolean.valueOf(true);
    if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])MacLimitMap.getInstance().getMacLimitMap()
        .get(mac);
      if (macTimeInfo != null) {
        oldcostTime = Long.valueOf(macTimeInfo[1]);
      }
      if ((onlinelimit.getState().intValue() == 1) && 
        (onlinelimit.getType().intValue() == 0)) {
        Date now = new Date();
        String nowString = ThreadLocalDateUtil.format(now);
        if (macTimeInfo == null) {
          macTimeInfo = new String[3];
          macTimeInfo[1] = "0";
        }
        macTimeInfo[0] = nowString;
        macTimeInfo[2] = String.valueOf(id);
        MacLimitMap.getInstance().getMacLimitMap()
          .put(mac, macTimeInfo);
        costTime = oldcostTime;
        haveTime = (int)(timepermit.longValue() / 60000L);
        notLimit = Boolean.valueOf(false);
      }

      Cookie cookieMac = new Cookie("mac", mac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", mac);
    }
    int overTime = (int)(costTime.longValue() / 60000L);
    haveTime -= overTime;
    if (haveTime > toHaveTime) {
      haveTime = toHaveTime;
    }
    if (haveTime < 0) {
      haveTime = 0;
    }
    if (notLimit.booleanValue()) {
      overTime += (int)(oldcostTime.longValue() / 60000L);
    }
    String haveTimeString = String.valueOf(haveTime);
    String overTimeString = String.valueOf(overTime);
    session.setAttribute("haveTime", haveTimeString);
    session.setAttribute("overTime", overTimeString);
  }

  public static Long getTime(String username)
  {
    PortalaccountQuery query = new PortalaccountQuery();
    query.setLoginName(username);
    query.setLoginNameLike(false);
    Portalaccount a = (Portalaccount)accountService.getPortalaccountList(query).get(0);
    String userState = a.getState();
    Date userDate = a.getDate();
    Long userTime = a.getTime();

    if (userState.equals(String.valueOf(0))) {
      return Long.valueOf(0L);
    }

    if (userState.equals(String.valueOf(1))) {
      return Long.valueOf(86400000L);
    }

    if (userState.equals(String.valueOf(3))) {
      Date now = new Date();
      if (userDate.getTime() > now.getTime()) {
        return Long.valueOf(userDate.getTime() - now.getTime());
      }
      return Long.valueOf(0L);
    }

    if (userState.equals("2")) {
      if (userTime.longValue() > 0L) {
        return userTime;
      }
      return Long.valueOf(0L);
    }

    return Long.valueOf(0L);
  }

  public static int getHaveTime()
  {
    try
    {
      Date nowdate = new Date();
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(nowdate);
      calendar.add(5, 1);
      Date tdate = calendar.getTime();
      SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
      String tsString = fs.format(tdate);
      Date t = fs.parse(tsString);
      return (int)((t.getTime() - nowdate.getTime()) / 60000L); } catch (Exception e) {
    }
    return 1440;
  }

  public static void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
  {
    Portalautologin autologin = autologinService
      .getPortalautologinByKey(id);
    if ((autologin != null) && (autologin.getState().intValue() == 1) && 
      (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = 
        (String[])AutoLoginMacMap.getInstance()
        .getAutoLoginMacMap().get(mac);
      Date now = new Date();
      String nowString = ThreadLocalDateUtil.format(now);
      if (macTimeInfo == null) {
        macTimeInfo = new String[3];
        macTimeInfo[1] = "0";
      }
      macTimeInfo[0] = nowString;
      macTimeInfo[2] = String.valueOf(id);
      AutoLoginMacMap.getInstance().getAutoLoginMacMap()
        .put(mac, macTimeInfo);

      String[] userinfo = new String[3];
      userinfo[0] = authUser;
      userinfo[1] = authPwd;
      userinfo[2] = username;
      AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.utils.Tools
 * JD-Core Version:    0.6.2
 */