package com.leeson.portal.core.controller.wifidog;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.utils.WifidogKick;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class AuthController extends HttpServlet
{
  private static final long serialVersionUID = -8404975286682591703L;
  private static Config config = Config.getInstance();

  private static Logger logger = Logger.getLogger(AuthController.class);

  protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      logger.info("WifiDog Auth : " + request.getRequestURL().toString() + "?" + request.getQueryString());
    }

    String stage = request.getParameter("stage");
    String token = request.getParameter("token");

    if ("logout".equals(stage)) {
      WifidogKick.ApKickUser(token.trim());
      System.out.println("=============================logout");
      response.getOutputStream().write("Auth: 1".getBytes());
    } else {
      String ip = request.getParameter("ip");
      String mac = request.getParameter("mac");
      mac = PortalUtil.MacFormat(mac);
      String incoming = request.getParameter("incoming");
      String outgoing = request.getParameter("outgoing");

      String[] loginInfo = null;
      loginInfo = 
        (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
        .get(token);
      if (loginInfo != null) {
        loginInfo[4] = ip;
        loginInfo[5] = mac;
        loginInfo[8] = incoming;
        loginInfo[9] = outgoing;

        if ("login".equals(stage)) {
          String username = loginInfo[0];
          String time = loginInfo[3];
          String userIDStr = loginInfo[1];
          String basip = loginInfo[7];
          Date loginTime = null;
          try {
            loginTime = ThreadLocalDateUtil.parse(time);
          } catch (ParseException e) {
            loginTime = new Date();
            logger.error("WifiDog Auth Login DataParse Error : ", e);
          }

          if (stringUtils.isNotBlank(userIDStr)) {
            Long userID = Long.valueOf(userIDStr);
            PortalaccountService accountService = (PortalaccountService)SpringContextHelper.getBean("portalaccountServiceImpl");
            String userState = accountService.getPortalaccountByKey(userID).getState();
            Long linkRecordID = doLinkRecord(username, ip, basip, mac, userState, userID, loginTime);
            loginInfo[2] = String.valueOf(linkRecordID);
          }

          PortallogrecordService logRecordService = (PortallogrecordService)SpringContextHelper.getBean("portallogrecordServiceImpl");
          Portallogrecord logRecord = new Portallogrecord();

          logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + mac + 
            " 用户: " + username + ",wifidog登录成功!");
          logRecord.setRecDate(loginTime);
          logRecordService.addPortallogrecord(logRecord);
        }

        WifiDogOnlineMap.getInstance().getWifiDogOnlineMap()
          .put(token, loginInfo);
        System.out.println("=============================auth: 1");
        response.getOutputStream().write("Auth: 1".getBytes());
      } else {
        System.out.println("=============================auth: 0");
        response.getOutputStream().write("Auth: 0".getBytes());
      }
    }
  }

  private Long doLinkRecord(String username, String ip, String basip, String mac, String userState, Long userID, Date loginTime)
  {
    Portallinkrecord linkRecord = new Portallinkrecord();
    linkRecord.setStartDate(loginTime);
    linkRecord.setIp(ip);
    linkRecord.setBasip(basip);
    linkRecord.setMac(mac);
    linkRecord.setIns(Long.valueOf(0L));
    linkRecord.setOuts(Long.valueOf(0L));
    linkRecord.setOctets(Long.valueOf(0L));
    linkRecord.setLoginName(username);
    linkRecord.setState(userState);
    linkRecord.setUid(userID);
    PortallinkrecordService linkRecordService = (PortallinkrecordService)SpringContextHelper.getBean("portallinkrecordServiceImpl");
    linkRecordService.addPortallinkrecord(linkRecord);
    return linkRecord.getId();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.AuthController
 * JD-Core Version:    0.6.2
 */