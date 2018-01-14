package com.leeson.common.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XssFilter
  implements Filter
{
  private String filterChar;
  private String replaceChar;
  private String splitChar;
  FilterConfig filterConfig = null;

  public void init(FilterConfig filterConfig) throws ServletException { this.filterChar = filterConfig.getInitParameter("FilterChar");
    this.replaceChar = filterConfig.getInitParameter("ReplaceChar");
    this.splitChar = filterConfig.getInitParameter("SplitChar");
    this.filterConfig = filterConfig; }

  public void destroy()
  {
    this.filterConfig = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)request, this.filterChar, this.replaceChar, this.splitChar), response);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.XssFilter
 * JD-Core Version:    0.6.2
 */