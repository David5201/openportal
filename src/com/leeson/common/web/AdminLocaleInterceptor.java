package com.leeson.common.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminLocaleInterceptor extends HandlerInterceptorAdapter
{
  public static final String LOCALE = "locale";

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws ServletException
  {
    return true;
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.AdminLocaleInterceptor
 * JD-Core Version:    0.6.2
 */