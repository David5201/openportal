package com.leeson.common.interceptor;

import com.leeson.core.bean.Portaluser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter
{
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    String path = request.getServletPath();
    if (path.matches(".*/((login)|(logout)|(code)).*")) {
      return true;
    }
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("sessionUser");
    if (user != null) {
      return true;
    }
    response.sendRedirect(request.getContextPath() + "/login.html");
    return false;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.LoginHandlerInterceptor
 * JD-Core Version:    0.6.2
 */