package com.leeson.portal.core.controller;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portalip;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.controller.AjaxInterFaceController;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalipQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalautologinService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortalipService;
import com.leeson.core.service.PortalonlinelimitService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.utils.IPv4Util;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
import com.leeson.portal.core.model.WeixinMap;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class LinkAuthAction extends HttpServlet
{
  private static final long serialVersionUID = 2052385989876611487L;
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  private static Config config = Config.getInstance();

  private static Logger logger = Logger.getLogger(LinkAuthAction.class);
  public static final String WEI_XIN_BROWSER = "MicroMessenger/";
  private static PortalwebService webService = (PortalwebService)
    SpringContextHelper.getBean("portalwebServiceImpl");

  private static PortalbasauthService basauthService = (PortalbasauthService)
    SpringContextHelper.getBean("portalbasauthServiceImpl");

  private static PortalonlinelimitService onlinelimitService = (PortalonlinelimitService)
    SpringContextHelper.getBean("portalonlinelimitServiceImpl");

  private static PortalaccountService accountService = (PortalaccountService)
    SpringContextHelper.getBean("portalaccountServiceImpl");

  private static PortalautologinService autologinService = (PortalautologinService)
    SpringContextHelper.getBean("portalautologinServiceImpl");

  private static PortalipService ipService = (PortalipService)
    SpringContextHelper.getBean("portalipServiceImpl");

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    HttpSession session = request.getSession();
    String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    if (basConfig.getIsdebug().equals("1")) {
      logger.info("linkAuth RemoteAddrIp= " + ip);
    }

    String webMod = "";
    try {
      long ipL = IPv4Util.ipToLong(ip);
      List<Portalip> iplList = ipService
        .getPortalipList(new PortalipQuery());
      for (Portalip portalip : iplList) {
        long startH = IPv4Util.ipToLong(portalip.getStart());
        long endH = IPv4Util.ipToLong(portalip.getEnd());
        if ((ipL >= startH) && (ipL <= endH)) {
          Portalweb web = webService.getPortalwebByKey(portalip.getWeb());
          if (web == null) break;
          webMod = String.valueOf(web.getId()) + "/";

          break;
        }
      }
    } catch (Exception e) {
      webMod = "";
    }
    session.setAttribute("web", webMod);

    if (!isPortalWXAuthInner(request)) {
      request.getRequestDispatcher("/" + webMod + "info.jsp").forward(request, response);
      return;
    }

    String host = "";
    Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String t = o.toString();
      if (t.contains(ip + ":")) {
        host = t;
        if (WeixinMap.getInstance().getWeixinipmap().get(t) != null) {
          WeixinMap.getInstance().getWeixinipmap().remove(t);
          WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(t);
          if (basConfig.getIsdebug().equals("1")) {
            logger.info("IP:" + t + "Link Auth Success , Remove WeixinTempMap !!");
          }
        }

      }

    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(host);
    if (isLogin) {
      String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
      String[] ts = host.split(":");
      String basip = ts[1];
      String ikmac = loginInfo[4];
      String username = loginInfo[0].replace("-临时放行", "");

      loginInfo[0] = username;
      onlineMap.getOnlineUserMap().put(host, loginInfo);
      GetMacTimeLimitMethod(loginInfo, ikmac, session);
      session.setAttribute("username", username);
      session.setAttribute("ip", ip);
      session.setAttribute("basip", basip);

      String authUrlWeb = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
      PortalbasauthQuery bsq = new PortalbasauthQuery();
      bsq.setBasip(basip);
      bsq.setBasipLike(false);
      bsq.setType(Integer.valueOf(5));
      String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
      String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
      List<Portalbasauth> basauths = basauthService
        .getPortalbasauthList(bsq);
      if (basauths.size() > 0) {
        for (Portalbasauth ba : basauths) {
          if (ba.getType().intValue() == 5) {
            authUser = ba.getUsername();
            authPwd = ba.getPassword();
            authUrlWeb = ba.getUrl();
            if ((stringUtils.isBlank(authUser)) || 
              (stringUtils.isBlank(authPwd))) {
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();
            }
            if (!stringUtils.isBlank(authUrlWeb)) break;
            authUrlWeb = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];

            break;
          }
        }
      }
      if (stringUtils.isBlank(authUrlWeb)) {
        authUrlWeb = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
      }

      AutoLoginPutMethod(ikmac, Long.valueOf(7L), authUser, authPwd, username);

      AjaxInterFaceController.SangforLogin(ip, username, ikmac, "", basip);

      session.setAttribute("ikweb", authUrlWeb);

      response.sendRedirect("/" + webMod + "ok.jsp?u=公众号认证&i=" + ip + "&m=认证成功&l=" + authUrlWeb);
      return;
    }
    request.getRequestDispatcher("/" + webMod + "out.jsp").forward(request, response);
  }

  private static void GetMacTimeLimitMethod(String[] loginInfo, String mac, HttpSession session)
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
                loginTime = ThreadLocalDateUtil.parse(loginTimeString);
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

  private static Long getTime(String username)
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

  private static int getHaveTime()
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

  public static boolean isPortalWXAuthInner(HttpServletRequest request)
  {
    String userAgent = request.getHeader("user-agent");
    boolean isPortalWXAuthInner = false;
    if (stringUtils.isNotBlank(userAgent))
    {
      if (userAgent.contains("MicroMessenger/")) {
        isPortalWXAuthInner = true;
      }
    }
    return isPortalWXAuthInner;
  }

  private static void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
  {
    Portalautologin autologin = autologinService
      .getPortalautologinByKey(id);
    if ((autologin != null) && (autologin.getState().intValue() == 1) && 
      (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = (String[])AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(mac);
      Date now = new Date();
      String nowString = ThreadLocalDateUtil.format(now);
      if (macTimeInfo == null) {
        macTimeInfo = new String[3];
        macTimeInfo[1] = "0";
      }
      macTimeInfo[0] = nowString;
      macTimeInfo[2] = String.valueOf(id);
      AutoLoginMacMap.getInstance().getAutoLoginMacMap().put(mac, macTimeInfo);

      String[] userinfo = new String[3];
      userinfo[0] = authUser;
      userinfo[1] = authPwd;
      userinfo[2] = username;
      AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.LinkAuthAction
 * JD-Core Version:    0.6.2
 */