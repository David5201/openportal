package com.leeson.common.web.session;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpSessionProvider
  implements SessionProvider
{
  public Serializable getAttribute(HttpServletRequest request, String name)
  {
    HttpSession session = request.getSession(false);
    if (session != null) {
      return (Serializable)session.getAttribute(name);
    }
    return null;
  }

  public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value)
  {
    HttpSession session = request.getSession();
    session.setAttribute(name, value);
  }

  public String getSessionId(HttpServletRequest request, HttpServletResponse response)
  {
    return request.getSession().getId();
  }

  public void logout(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false);
    if (session != null)
      session.invalidate();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.HttpSessionProvider
 * JD-Core Version:    0.6.2
 */