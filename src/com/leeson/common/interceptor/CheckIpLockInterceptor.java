package com.leeson.common.interceptor;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalurlparameter;
import com.leeson.core.service.PortalurlparameterService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.ipMap;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CheckIpLockInterceptor extends HandlerInterceptorAdapter
{
  private static Logger logger = Logger.getLogger(CheckIpLockInterceptor.class);
  private static Config config = Config.getInstance();
  private static ipMap ipMapContext = ipMap.getInstance();

  PortalurlparameterService urlparametersService = (PortalurlparameterService)SpringContextHelper.getBean("portalurlparameterServiceImpl");

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    response.setContentType("text/html;charset=utf-8");
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    HttpSession session = request.getSession();

    String ip = request.getParameter("ip");
    String pip = request.getParameter(this.urlparametersService
      .getPortalurlparameterByKey(Long.valueOf(1L)).getUserip());
    String sip = (String)session.getAttribute("ip");

    if (stringUtils.isBlank(ip)) {
      ip = pip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = sip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }
    if (!ipMapContext.getIpmap().containsKey(ip)) {
      ipMapContext.getIpmap().put(ip, Integer.valueOf(1));
      return true;
    }
    if (basConfig.getIsdebug().equals("1")) {
      logger.error(" IP: " + ip + " to Post is too fast !!!");
    }

    String msgTemp = "你操作太快,请稍后再试！！";
    msgTemp = new String(msgTemp.getBytes("UTF-8"), "UTF-8");

    JSONObject jo = new JSONObject();
    jo.put("ret", Integer.valueOf(1));
    jo.put("msg", msgTemp);
    String respMessage = jo.toString();
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
    return false;
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception excptn)
    throws Exception
  {
    HttpSession session = request.getSession();
    String ip = request.getParameter("ip");
    String pip = request.getParameter(this.urlparametersService
      .getPortalurlparameterByKey(Long.valueOf(1L)).getUserip());
    String sip = (String)session.getAttribute("ip");

    if (stringUtils.isBlank(ip)) {
      ip = pip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = sip;
    }
    if (stringUtils.isBlank(ip)) {
      ip = GetNgnixRemotIP.getRemoteAddrIp(request);
    }
    ipMapContext.getIpmap().remove(ip);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.CheckIpLockInterceptor
 * JD-Core Version:    0.6.2
 */