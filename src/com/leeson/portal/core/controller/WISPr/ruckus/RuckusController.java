package com.leeson.portal.core.controller.WISPr.ruckus;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Advadv;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.service.AdvadvService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.controller.WISPr.utils.Tools;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.ipMap;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringEscapeUtils;

public class RuckusController extends HttpServlet
{
  private static final long serialVersionUID = 4629097038325367725L;
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  private static Config config = Config.getInstance();

  private static AdvadvService advadvService = (AdvadvService)
    SpringContextHelper.getBean("advadvServiceImpl");

  private static PortalwebService webService = (PortalwebService)
    SpringContextHelper.getBean("portalwebServiceImpl");

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=utf-8");
    request.setCharacterEncoding("utf-8");

    HttpSession session = request.getSession();

    String apmac = (String)session.getAttribute("apmac");
    String ikmac = (String)session.getAttribute("ikmac");
    String ssid = "";
    String ikweb = "";

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
        if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }

    String ip = (String)session.getAttribute("ip");
    String basip = (String)session.getAttribute("basip");

    String pip = request.getParameter("uip");
    String pmac = request.getParameter("client_mac");
    String purl = request.getParameter("url");
    String pbasip = request.getParameter("sip");
    String pssid = request.getParameter("ssid");
    String papmac = request.getParameter("mac");
    String proxy = request.getParameter("proxy");
    if (stringUtils.isNotBlank(purl)) {
      purl = new String(purl.getBytes("iso-8859-1"), "utf-8");
    }
    if (stringUtils.isNotBlank(pssid)) {
      pssid = new String(pssid.getBytes("iso-8859-1"), "utf-8");
    }

    if (stringUtils.isNotBlank(papmac)) {
      apmac = papmac;
    }

    System.out.println("Ruckus basip=" + pbasip + "----ip=" + pip + 
      "----mac=" + pmac + "----url=" + purl + "----ssid=" + pssid + 
      "----apMac=" + papmac);

    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    if (stringUtils.isNotBlank(pbasip)) {
      basip = pbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = cbasip;
    }
    if (stringUtils.isBlank(basip)) {
      basip = basConfig.getBasIp();
    }

    Portalbas basConfigFind = (Portalbas)config.getConfigMap().get(basip);
    if ((basConfigFind != null) && 
      (stringUtils.isNotBlank(basConfigFind.getBasIp()))) {
      basConfig = basConfigFind;
    }

    basip = basConfig.getBasIp();

    if (!basConfig.getBas().equals("6")) {
      request.getRequestDispatcher("/error.html").forward(request, 
        response);
      return;
    }

    if (stringUtils.isNotBlank(pip)) {
      ip = pip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = cip;
    }
    if (stringUtils.isBlank(ip)) {
      if ("0".equals(basConfig.getIsOut())) {
        ip = GetNgnixRemotIP.getRemoteAddrIp(request);
      } else {
        request.getRequestDispatcher("/error.html").forward(request, 
          response);
        return;
      }
    }

    if (stringUtils.isNotBlank(pmac)) {
      ikmac = pmac;
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isNotBlank(purl)) {
      ikweb = purl;
    }

    if (stringUtils.isNotBlank(pssid)) {
      ssid = pssid;
    }

    if (stringUtils.isBlank(ssid)) {
      ssid = (String)session.getAttribute("ssid");
    }

    if (stringUtils.isNotBlank(ikmac))
    {
      Cookie cookieMac = new Cookie("mac", ikmac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", ikmac);
    }

    if (stringUtils.isNotBlank(apmac)) {
      session.setAttribute("apmac", apmac);
    }

    if (stringUtils.isNotBlank(ssid)) {
      ssid = URLDecoder.decode(ssid);
      ssid = StringEscapeUtils.unescapeHtml(ssid);
      session.setAttribute("ssid", ssid);
    }
    if (stringUtils.isNotBlank(ikweb)) {
      ikweb = URLDecoder.decode(ikweb);
      ikweb = StringEscapeUtils.unescapeHtml(ikweb);
      if (!ikweb.startsWith("http")) {
        ikweb = "http://" + ikweb;
      }
    }

    String webMod = Tools.getWebMod(ssid, apmac, ip, basConfig.getWeb());
    session.setAttribute("web", webMod);

    String userAgent = request.getHeader("user-agent");
    boolean isComputer = false;
    String agent = "";
    if (stringUtils.isNotBlank(userAgent)) {
      if (userAgent.contains("Windows")) {
        isComputer = true;
        agent = "Windows";
      } else if (userAgent.contains("Macintosh")) {
        isComputer = true;
        agent = "MacOS";
      }
      else if (userAgent.contains("Linux")) {
        isComputer = false;
        agent = "Android";
      } else if (userAgent.contains("Android")) {
        isComputer = false;
        agent = "Android";
      } else if (userAgent.contains("iPhone")) {
        isComputer = false;
        agent = "IOS";
      } else if (userAgent.contains("iPad")) {
        isComputer = false;
        agent = "IOS";
      } else if (userAgent.contains("iPod")) {
        isComputer = false;
        agent = "IOS";
      }
    }

    session.setAttribute("agent", agent);
    if (!"1".equals(basConfig.getIsComputer()))
    {
      if (isComputer) {
        session.setAttribute("web", "");
        request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
        request.getRequestDispatcher("L.jsp").forward(request, 
          response);
        return;
      }
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      Kick.kickUserWISPrSyn(ip + ":" + basip, ikmac, "");
    }

    if (onlineMap.getOnlineUserMap().size() >= Integer.valueOf(((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
      request.setAttribute("msg", "已超过允许最大用户数限制！！");
      request.getRequestDispatcher("L.jsp").forward(request, response);
      return;
    }

    session.setAttribute("ip", ip);
    session.setAttribute("basip", basip);
    session.setAttribute("ikweb", ikweb);
    session.setAttribute("ssid", ssid);

    String auth = basConfig.getAuthInterface();
    auth = auth.replace("3", "");
    session.setAttribute("auth", auth);

    if (stringUtils.isNotBlank(proxy)) {
      session.setAttribute("proxy", proxy);

      String loginUrl = "http:ubscriberPortal/hotspotlogin";
      session.setAttribute("loginUrl", loginUrl);
      String logoutUrl = "http:SubscriberPortal/hotspotlogout";
      session.setAttribute("logoutUrl", logoutUrl);
    }
    else
    {
      String loginUrl = "http:ogin";
      session.setAttribute("loginUrl", loginUrl);
      String logoutUrl = "http:ogout";
      session.setAttribute("logoutUrl", logoutUrl);
    }

    if (Tools.autoLogin(basip, ip, ikmac, basConfig, session)) {
      request.getRequestDispatcher("/APIGoAuthRuckus.jsp").forward(
        request, response);
      return;
    }

    Cookie cookieIP = new Cookie("ip", null);
    cookieIP.setMaxAge(-1);
    response.addCookie(cookieIP);
    Cookie cookieBasIp = new Cookie("basip", null);
    cookieBasIp.setMaxAge(-1);
    response.addCookie(cookieBasIp);
    Cookie cookiePwd = new Cookie("password", null);
    cookiePwd.setMaxAge(-1);
    response.addCookie(cookiePwd);

    ipMap.getInstance().getIpmap().remove(ip);

    session.setAttribute("api", "ruckus");

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
                  request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=1").forward(
                    request, response);
                }
              }
            }
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
    request.getRequestDispatcher("/" + webMod + "APIauth.jsp").forward(
      request, response);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.WISPr.ruckus.RuckusController
 * JD-Core Version:    0.6.2
 */