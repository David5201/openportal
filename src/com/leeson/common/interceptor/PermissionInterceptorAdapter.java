package com.leeson.common.interceptor;

import com.leeson.core.bean.Portaluser;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PermissionInterceptorAdapter extends HandlerInterceptorAdapter
{
  private static Logger logger = Logger.getLogger(PermissionInterceptorAdapter.class);

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    boolean isPermissioin = false;
    Subject currentUser = SecurityUtils.getSubject();
    Portaluser user = (Portaluser)request.getSession()
      .getAttribute("user");
    if ((user != null) && (user.getLoginName().equals("admin"))) {
      return true;
    }
    String privUrl = request.getServletPath();

    int pos = privUrl.indexOf("?");
    if (pos > -1) {
      privUrl = privUrl.substring(0, pos);
    }

    pos = privUrl.indexOf(".action");
    if (pos > -1) {
      privUrl = privUrl.substring(0, pos);
    }

    if (privUrl.endsWith("UI")) {
      privUrl = privUrl.substring(0, privUrl.length() - 2);
    }

    if (privUrl.endsWith("V")) {
      privUrl = privUrl.substring(0, privUrl.length() - 1);
    }

    if (privUrl.endsWith("deletes")) {
      privUrl = privUrl.substring(0, privUrl.length() - 1);
    }

    if (privUrl.endsWith("kicks")) {
      privUrl = privUrl.substring(0, privUrl.length() - 1);
    }

    Collection allPrivilegeUrls = (Collection)request
      .getServletContext().getAttribute("allPrivilegeUrls");
    if (!allPrivilegeUrls.contains(privUrl)) {
      isPermissioin = true;
    }
    else if (currentUser.isPermitted(privUrl)) {
      isPermissioin = true;
    }

    if (isPermissioin)
    {
      return true;
    }

    logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Login Username: [" + user.getLoginName() + "] request URL: " + privUrl + " Not Have Privileges !!!");
    throw new AuthorizationException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.PermissionInterceptorAdapter
 * JD-Core Version:    0.6.2
 */