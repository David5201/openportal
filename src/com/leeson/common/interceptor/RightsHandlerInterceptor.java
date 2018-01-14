package com.leeson.common.interceptor;

import com.leeson.common.util.RightsHelper;
import com.leeson.common.util.ServiceHelper;
import com.leeson.common.util.Tools;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.service.PortalprivilegeService;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RightsHandlerInterceptor extends HandlerInterceptorAdapter
{
  private static Logger logger = Logger.getLogger(RightsHandlerInterceptor.class);

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    String path = request.getServletPath();
    if (path.matches(".*/((login)|(logout)|(code)).*"))
      return true;
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("sessionUser");
    String menuId = null;
    List<String> subList = (List)ServiceHelper.getPrivilegeService().getAllPrivilegeUrls();
    for (String m : subList) {
      String menuUrl = m;
      if (Tools.notEmpty(menuUrl)) {
        if (path.contains(menuUrl)) {
          menuId = m;
          break;
        }
        String[] arr = menuUrl.split("\\.");
        String regex = "";
        if (arr.length == 2) {
          regex = "*)?." + arr[1];
        }
        else {
          regex = "*)?.html";
        }
        if (path.matches(regex)) {
          menuId = m;
          break;
        }
      }

    }

    if (menuId != null)
    {
      String userRights = (String)session.getAttribute("sessionUserRights");
      String roleRights = (String)session.getAttribute("sessionRoleRights");
      if ((RightsHelper.testRights(userRights, menuId)) || (RightsHelper.testRights(roleRights, menuId))) {
        return true;
      }
      logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Login Username: [" + user.getLoginName() + "] request URL: " + path + " Not Have Privileges !!!");
      ModelAndView mv = new ModelAndView();
      mv.setViewName("no_rights");
      throw new ModelAndViewDefiningException(mv);
    }

    return super.preHandle(request, response, handler);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.RightsHandlerInterceptor
 * JD-Core Version:    0.6.2
 */