package com.leeson.portal.core.controller.wifidog;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.WifiDogAPMap;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class PingController extends HttpServlet
{
  private static final long serialVersionUID = -1966047929923869408L;
  private static Config config = Config.getInstance();

  private static Logger logger = Logger.getLogger(PingController.class);

  protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String now = ThreadLocalDateUtil.format(new Date());

    String gw_id = request.getParameter("gw_id");
    String sys_uptime = request.getParameter("sys_uptime");
    String sys_memfree = request.getParameter("sys_memfree");
    String sys_load = request.getParameter("sys_load");
    String wifidog_uptime = request.getParameter("wifidog_uptime");
    String[] apInfo = new String[6];
    apInfo[0] = sys_uptime;
    apInfo[1] = sys_memfree;
    apInfo[2] = sys_load;
    apInfo[3] = wifidog_uptime;

    apInfo[4] = GetNgnixRemotIP.getRemoteAddrIp(request);
    apInfo[5] = now;

    String query = request.getQueryString();

    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      logger.info("WifiDog Ping : " + request.getRequestURL().toString() + 
        "?" + query);
    }
    if (stringUtils.isBlank(gw_id)) {
      System.out.println("WifiDog Ping Error !");
      response.getOutputStream().write("{'error':'2'}".getBytes());
      return;
    }
    WifiDogAPMap.getInstance().getWifiDogAPMap().put(gw_id, apInfo);
    System.out.println("WifiDog Ping Success !");
    response.getOutputStream().write("Pong".getBytes());
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.PingController
 * JD-Core Version:    0.6.2
 */