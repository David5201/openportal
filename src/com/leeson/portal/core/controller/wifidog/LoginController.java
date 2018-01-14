package com.leeson.portal.core.controller.wifidog;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Advadv;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.service.AdvadvService;
import com.leeson.core.service.PortalwebService;
import com.leeson.portal.core.controller.WISPr.utils.Tools;
import com.leeson.portal.core.controller.utils.BASE64;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet
{
  private static final long serialVersionUID = -2471043149508545912L;
  private static Config config = Config.getInstance();

  private static Logger logger = Logger.getLogger(LoginController.class);

  private static AdvadvService advadvService = (AdvadvService)
    SpringContextHelper.getBean("advadvServiceImpl");

  private static PortalwebService webService = (PortalwebService)
    SpringContextHelper.getBean("portalwebServiceImpl");

  protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    HttpSession session = request.getSession();

    String gw_id = request.getParameter("gw_id");
    String gw_address = request.getParameter("gw_address");
    String gw_port = request.getParameter("gw_port");
    String mac = request.getParameter("mac");
    String url = request.getParameter("url");
    String apmac = request.getParameter("gw_mac");
    String ssid = request.getParameter("ssid");

    if (stringUtils.isNotBlank(gw_id)) {
      try {
        Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
        while (iteratorConfig.hasNext()) {
          Object o = iteratorConfig.next();
          String t = o.toString();
          Portalbas bas = (Portalbas)config.getConfigMap().get(t);
          String basName = bas.getBasname();
          if ((stringUtils.isNotBlank(basName)) && 
            (basName.equals(gw_id))) {
            basConfig = bas;
            break;
          }
        }
      }
      catch (Exception e) {
        basConfig = (Portalbas)config.getConfigMap().get("");
      }

    }

    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      logger.info("WifiDog Login : " + request.getRequestURL().toString() + 
        "?" + request.getQueryString());
    }

    String ip = GetNgnixRemotIP.getRemoteAddrIp(request);

    if (stringUtils.isNotBlank(url)) {
      try {
        url = BASE64.decryptBASE64(url);
      } catch (Exception localException1) {
      }
      url = URLDecoder.decode(url);
      url = StringEscapeUtils.unescapeHtml(url);
      session.setAttribute("url", url);
    }
    if (stringUtils.isNotBlank(ssid)) {
      try {
        ssid = BASE64.decryptBASE64(ssid);
      } catch (Exception localException2) {
      }
      ssid = URLDecoder.decode(ssid);
      ssid = StringEscapeUtils.unescapeHtml(ssid);
      session.setAttribute("ssid", ssid);
    }

    String webMod = Tools.getWebMod(ssid, apmac, ip, basConfig.getWeb());
    session.setAttribute("web", webMod);
    mac = PortalUtil.MacFormat(mac);
    if (OnlineMap.getInstance().getOnlineUserMap().size() >= Integer.valueOf(((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
      session.setAttribute("gw_id", gw_id);
      session.setAttribute("gw_address", gw_address);
      session.setAttribute("gw_port", gw_port);
      session.setAttribute("mac", mac);
      request.setAttribute("msg", "已超过允许最大用户数限制！！");
      session.setAttribute("web", "");
      request.getRequestDispatcher("L.jsp").forward(request, 
        response);
      return;
    }

    if (!"1".equals(basConfig.getIsComputer())) {
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

      System.out.println("isComputer:" + isComputer);
      if (isComputer) {
        session.setAttribute("web", "");
        request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
        request.getRequestDispatcher("L.jsp").forward(request, 
          response);
        return;
      }
    }

    session.setAttribute("gw_id", gw_id);
    session.setAttribute("gw_address", gw_address);
    session.setAttribute("gw_port", gw_port);
    session.setAttribute("mac", mac);
    session.setAttribute("apmac", apmac);

    String auth = basConfig.getAuthInterface();
    session.setAttribute("auth", auth);

    if (Tools.Do()) {
      try {
        if (stringUtils.isNotBlank(webMod)) {
          String ids = webMod.replace("/", "");
          Long id = Long.valueOf(ids);
          Portalweb web = webService.getPortalwebByKey(id);
          Long advID = Long.valueOf(0L);
          if (web != null) {
            advID = web.getAdv();
            Advadv adv = advadvService.getAdvadvByKey(advID);
            if (adv != null) {
              int state = adv.getState().intValue();
              if (state == 1) {
                Date startDate = adv.getShowDate();
                Date endDate = adv.getEndDate();
                Date nowDate = new Date();
                if (((startDate == null) || 
                  (nowDate.getTime() >= startDate.getTime())) && (
                  (endDate == null) || 
                  (endDate.getTime() >= nowDate.getTime()))) {
                  request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=2").forward(
                    request, response);
                }
              }
            }
          }
        }
      }
      catch (Exception localException3)
      {
      }
    }
    request.getRequestDispatcher("ifidogAuth.jsp").forward(request, 
      response);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.LoginController
 * JD-Core Version:    0.6.2
 */