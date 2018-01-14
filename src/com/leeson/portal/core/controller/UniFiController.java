package com.leeson.portal.core.controller;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.UniFiMacSiteMap;
import com.leeson.portal.core.model.iKuaiMacIpMap;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UniFiController extends HttpServlet
{
  private static final long serialVersionUID = -1966047929923869408L;
  private static Config config = Config.getInstance();

  private static OnlineMap onlineMap = OnlineMap.getInstance();

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=utf-8");
    request.setCharacterEncoding("utf-8");

    String id = request.getParameter("id");
    String ssid = request.getParameter("ssid");
    if (stringUtils.isNotBlank(ssid)) {
      ssid = new String(ssid.getBytes("iso-8859-1"), "utf-8");
    }
    String url = request.getParameter("url");
    if (stringUtils.isNotBlank(url)) {
      url = new String(url.getBytes("iso-8859-1"), "utf-8");
    }
    String apmac = request.getParameter("ap");
    String site = request.getRequestURI();
    String[] ss = site.split("s/");
    int pos = ss[1].length();
    site = ss[1].substring(0, pos - 1);

    HttpSession session = request.getSession();
    Cookie[] cookies = request.getCookies();
    String cpassword = "";
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("password"))
          cpassword = cookies[i].getValue();
        if (cookies[i].getName().equals("mac")) {
          cmac = cookies[i].getValue();
        }
      }
    }
    String ikweb = url;
    String ikmac = id;

    if (stringUtils.isBlank(ikweb)) {
      ikweb = (String)session.getAttribute("ikweb");
    }

    if (stringUtils.isBlank(apmac)) {
      apmac = (String)session.getAttribute("apmac");
    }

    if (stringUtils.isBlank(ikmac)) {
      ikmac = (String)session.getAttribute("ikmac");
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }

    if (stringUtils.isBlank(site)) {
      site = (String)session.getAttribute("site");
    }
    if (stringUtils.isBlank(site)) {
      site = "default";
    }
    session.setAttribute("site", site);
    Cookie cookieSite = new Cookie("site", site);
    cookieSite.setMaxAge(86400);
    response.addCookie(cookieSite);

    String basip = ((Portalbas)config.getConfigMap().get("")).getBasIp();

    String ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    ip = ikmac;

    if (stringUtils.isNotBlank(ssid)) {
      ssid = URLDecoder.decode(ssid, "utf-8");
    }
    if (stringUtils.isBlank(ssid)) {
      ssid = (String)session.getAttribute("ssid");
    }

    if (stringUtils.isNotBlank(ikmac)) {
      ikmac = PortalUtil.MacFormat(ikmac);
      iKuaiMacIpMap.getInstance().getMacIpMap().put(ikmac, ip);
      UniFiMacSiteMap.getInstance().getMacSiteMap().put(ikmac, site);
      Cookie cookieMac = new Cookie("mac", ikmac);
      cookieMac.setMaxAge(86400);
      response.addCookie(cookieMac);
      session.setAttribute("ikmac", ikmac);
    }

    boolean isLogin = onlineMap.getOnlineUserMap().containsKey(
      ip + ":" + basip);
    if (isLogin) {
      String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
        ip + ":" + basip);
      String username = loginInfo[0];
      session.setAttribute("username", username);
      session.setAttribute("password", cpassword);
      session.setAttribute("ip", ip);
      session.setAttribute("basip", basip);
      session.setAttribute("ssid", ssid);
      session.setAttribute("apmac", apmac);

      request.getRequestDispatcher("/ok.jsp").forward(request, response);

      return;
    }

    session.setAttribute("ikweb", ikweb);

    session.setAttribute("ssid", ssid);

    session.setAttribute("apmac", apmac);

    request.getRequestDispatcher("/index_choose").forward(request, response);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.UniFiController
 * JD-Core Version:    0.6.2
 */