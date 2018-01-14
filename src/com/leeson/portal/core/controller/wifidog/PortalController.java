package com.leeson.portal.core.controller.wifidog;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class PortalController extends HttpServlet
{
  private static final long serialVersionUID = -8206480691084456762L;
  private static Config config = Config.getInstance();

  private static Logger logger = Logger.getLogger(PortalController.class);

  protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = request.getSession();
    String url = (String)session.getAttribute("url");
    String token = (String)session.getAttribute("token");
    String webMod = (String)session.getAttribute("web");
    if (stringUtils.isBlank(webMod)) {
      webMod = "";
    }

    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      logger.info("WifiDog Portal : " + 
        request.getRequestURL().toString() + "?" + 
        request.getQueryString());
    }

    if (stringUtils.isNotBlank(token)) {
      String[] info = (String[])WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().get(token);
      if ((info.length == 11) && 
        ("weixin".equals(info[10])))
      {
        String weburl = "/wifidogWx.jsp";
        request.getRequestDispatcher("/" + webMod + weburl).forward(request, response);
      }

    }

    if (stringUtils.isNotBlank(url))
      request.getRequestDispatcher("ifidogOk.jsp?l=" + url).forward(request, response);
    else
      request.getRequestDispatcher("ifidogOk.jsp").forward(request, response);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.PortalController
 * JD-Core Version:    0.6.2
 */