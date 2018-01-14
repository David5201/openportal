package com.leeson.portal.core.controller.WISPr;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalap;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.query.PortalapQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalssidQuery;
import com.leeson.core.service.PortalapService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.service.PortalssidService;
import com.leeson.portal.core.controller.WISPr.utils.Tools;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.MagicMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.RosAuthMap;
import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
import com.leeson.portal.core.model.WeixinMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class WISPrAuthController extends HttpServlet
{
  private static final long serialVersionUID = -1966047929923869408L;
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  private static Config config = Config.getInstance();

  PortallogrecordService logrecordService = (PortallogrecordService)SpringContextHelper.getBean("portallogrecordServiceImpl");

  PortalbasauthService basauthService = (PortalbasauthService)SpringContextHelper.getBean("portalbasauthServiceImpl");

  PortalapService apService = (PortalapService)SpringContextHelper.getBean("portalapServiceImpl");

  PortalssidService ssidService = (PortalssidService)SpringContextHelper.getBean("portalssidServiceImpl");

  private static Logger logger = Logger.getLogger(WISPrAuthController.class);

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = request.getSession();

    String ip = (String)session.getAttribute("ip");
    String basip = (String)session.getAttribute("basip");
    String ikweb = (String)session.getAttribute("ikweb");
    String ikmac = (String)session.getAttribute("ikmac");
    String ssid = (String)session.getAttribute("ssid");
    String apmac = (String)session.getAttribute("apmac");
    String webMod = (String)session.getAttribute("web");
    String agent = (String)session.getAttribute("agent");
    if (stringUtils.isBlank(webMod)) {
      webMod = "";
    }

    System.out.println("AuthAPI basip=" + basip + "----ip=" + ip + 
      "----mac=" + ikmac + "----url=" + ikweb + "----ssid=" + ssid + 
      "----apmac=" + apmac + "----webMod=" + webMod);

    if ((stringUtils.isBlank(ikmac)) || (stringUtils.isBlank(basip)) || 
      (stringUtils.isBlank(ip))) {
      request.getRequestDispatcher("rror.html").forward(request, 
        response);
      return;
    }
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    if ((!basConfig.getBas().equals("5")) && 
      (!basConfig.getBas().equals("6")) && 
      (!basConfig
      .getBas().equals("7")) && (!basConfig.getBas().equals("8"))) {
      request.getRequestDispatcher("rror.html").forward(request, 
        response);
      return;
    }

    String[] rosInfo = (String[])RosAuthMap.getInstance().getRosAuthMap().get(ikmac);
    RosAuthMap.getInstance().getRosAuthMap().remove(ikmac);
    if (rosInfo.length < 5) {
      String auth = basConfig.getAuthInterface();
      request.setAttribute("auth", auth);
      session.setAttribute("msg", "获取认证信息出错！！");
      request.getRequestDispatcher("L.jsp").forward(request, response);
      return;
    }
    int basauthType = Integer.valueOf(rosInfo[0]).intValue();
    Long id = Long.valueOf(rosInfo[1]);
    String username = rosInfo[2];
    String authUser = rosInfo[3];
    String authPwd = rosInfo[4];

    String authUrl = ikweb;
    PortalbasauthQuery bsq = new PortalbasauthQuery();
    bsq.setBasip(basip);
    List<Portalbasauth> basauths = this.basauthService.getPortalbasauthList(bsq);
    if (basauths.size() > 0) {
      for (Portalbasauth ba : basauths) {
        if (ba.getType().intValue() == basauthType) {
          authUrl = ba.getUrl();
          if (!stringUtils.isBlank(authUrl)) break;
          authUrl = ikweb;

          break;
        }
      }
    }
    if (stringUtils.isBlank(authUrl)) {
      authUrl = ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[0];
    }
    session.setAttribute("ikweb", authUrl);

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
        ip + ":" + basip);
      Tools.GetMacTimeLimitMethod(loginInfo, ikmac, session);
      request.getRequestDispatcher("/" + webMod + "APIok.jsp").forward(
        request, response);
      return;
    }

    String[] loginInfo = new String[16];
    loginInfo[0] = username;
    Date now = new Date();
    loginInfo[3] = ThreadLocalDateUtil.format(now);
    loginInfo[4] = ikmac;
    loginInfo[6] = rosInfo[0];
    loginInfo[7] = "0";
    loginInfo[8] = "0";

    loginInfo[9] = ip;
    loginInfo[10] = basip;
    loginInfo[11] = ((Portalbas)config.getConfigMap().get(basip)).getBasname();
    loginInfo[12] = ssid;
    loginInfo[13] = apmac;
    loginInfo[14] = "no";
    if ((rosInfo.length == 6) && 
      (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
      loginInfo[14] = "yes";
    }

    loginInfo[15] = agent;

    Portallogrecord logRecord = new Portallogrecord();

    if (id.longValue() == 3L) {
      loginInfo[5] = "ok";
      Long userId = Tools.getAccountid(username);
      Long recordId = Tools.doLinkRecord(userId, ip, basip, ikmac);
      loginInfo[1] = String.valueOf(userId);
      loginInfo[2] = String.valueOf(recordId);
      Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), response, 
        username);
    } else if (id.longValue() == 4L) {
      loginInfo[5] = "ok";
      Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), response);
    } else if (id.longValue() == 6L) {
      boolean state = true;
      if ((rosInfo.length == 6) && 
        (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
        state = false;
      }

      if (state) {
        WeixinMap.getInstance().getWeixinipmap().put(ip + ":" + basip, now);
        WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().put(ip + ":" + basip, "yes");
      }
      Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), authUser, 
        authPwd, username.replace("-临时放行", ""), response);
    } else if (id.longValue() == 7L) {
      boolean state = true;
      if ((rosInfo.length == 6) && 
        (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
        state = false;
      }

      if (state) {
        String[] magicInfo = new String[3];
        magicInfo[0] = (ip + ":" + basip);
        magicInfo[1] = "";
        magicInfo[2] = ikmac;
        MagicMap.getInstance().getMagicMap()
          .put(ip + ":" + basip, magicInfo);
        WeixinMap.getInstance().getWeixinipmap().put(ip + ":" + basip, now);
        WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().put(ip + ":" + basip, "yes");
      }
      Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), authUser, 
        authPwd, username.replace("-临时放行", ""), response);
    } else {
      Tools.UpdateMacTimeLimitMethod(ikmac, id, request.getSession(), authUser, 
        authPwd, username, response);
    }

    if ((id.longValue() != 6L) && (id.longValue() != 7L)) {
      if (id.longValue() == 3L) {
        Tools.AutoLoginPutMethod(ikmac, id, authUser, authPwd, username.replace("(无感知)", ""));
      }
      else if (username.contains("无感知"))
        Tools.AutoLoginPutMethod(ikmac, id, authUser, authPwd, username);
      else {
        Tools.AutoLoginPutMethod(ikmac, id, authUser, authPwd, username + "(无感知)");
      }

    }

    session.setAttribute("username", username);
    onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
    logRecord.setRecDate(now);
    logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + 
      " 用户: 【" + username + "】,登录成功!");
    this.logrecordService.addPortallogrecord(logRecord);

    if (stringUtils.isNotBlank(ssid)) {
      try {
        PortalssidQuery apq = new PortalssidQuery();
        apq.setSsid(ssid);
        apq.setSsidLike(false);
        List aps = this.ssidService.getPortalssidList(apq);
        if ((aps != null) && (aps.size() > 0)) {
          Portalssid ap = (Portalssid)aps.get(0);
          ap.setBasip(basip);
          long count = ap.getCount().longValue() + 1L;
          ap.setCount(Long.valueOf(count));
          this.ssidService.updatePortalssidByKey(ap);
        } else {
          Portalssid ap = new Portalssid();
          ap.setSsid(ssid);
          ap.setBasip(basip);
          ap.setCount(Long.valueOf(1L));
          this.ssidService.addPortalssid(ap);
        }
      } catch (Exception e) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
    }
    if (stringUtils.isNotBlank(apmac)) {
      try {
        PortalapQuery apq = new PortalapQuery();
        apq.setMac(apmac);
        apq.setMacLike(false);
        List aps = this.apService.getPortalapList(apq);
        if ((aps != null) && (aps.size() > 0)) {
          Portalap ap = (Portalap)aps.get(0);
          ap.setBasip(basip);
          long count = ap.getCount().longValue() + 1L;
          ap.setCount(Long.valueOf(count));
          this.apService.updatePortalapByKey(ap);
        } else {
          Portalap ap = new Portalap();
          ap.setMac(apmac);
          ap.setBasip(basip);
          ap.setCount(Long.valueOf(1L));
          this.apService.addPortalap(ap);
        }
      } catch (Exception e) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
    }

    if ((basauthType == 5) || (basauthType == 6)) {
      String userAgent = request.getHeader("user-agent");
      System.out.println("user-agent " + userAgent);
      boolean isComputer = false;
      if (stringUtils.isNotBlank(userAgent)) {
        if (userAgent.contains("Windows")) {
          isComputer = true;
        }
        else if ((userAgent.contains("Android")) || 
          (userAgent.contains("iPhone")) || 
          (userAgent.contains("iPod")) || 
          (userAgent.contains("iPad"))) {
          isComputer = false;
        }

      }

      if ((rosInfo.length == 6) && 
        (stringUtils.isNotBlank(rosInfo[5])) && ("auto".equals(rosInfo[5]))) {
        request.getRequestDispatcher("/" + webMod + "APIok.jsp").forward(request, 
          response);
        return;
      }

      if (isComputer) {
        request.getRequestDispatcher("/" + webMod + "APIwxpc.jsp").forward(request, 
          response);
        return;
      }
      request.getRequestDispatcher("/" + webMod + "APIwx.jsp").forward(request, 
        response);
      return;
    }

    request.getRequestDispatcher("/" + webMod + "APIok.jsp").forward(request, 
      response);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.WISPrAuthController
 * JD-Core Version:    0.6.2
 */