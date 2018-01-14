package com.leeson.common.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper
{
  private String[] filterChars;
  private String[] replaceChars;

  public XssHttpServletRequestWrapper(HttpServletRequest request, String filterChar, String replaceChar, String splitChar)
  {
    super(request);
    if ((filterChar != null) && (filterChar.length() > 0)) {
      this.filterChars = filterChar.split(splitChar);
    }
    if ((replaceChar != null) && (replaceChar.length() > 0))
      this.replaceChars = replaceChar.split(splitChar);
  }

  public String getQueryString() {
    String value = super.getQueryString();
    if (value != null) {
      value = xssEncode(value);
    }
    return value;
  }

  public String getParameter(String name)
  {
    String value = super.getParameter(xssEncode(name));
    if (value != null) {
      value = xssEncode(value);
    }
    return value;
  }

  public String[] getParameterValues(String name) {
    String[] parameters = super.getParameterValues(name);
    if ((parameters == null) || (parameters.length == 0)) {
      return null;
    }
    for (int i = 0; i < parameters.length; i++) {
      parameters[i] = xssEncode(parameters[i]);
    }
    return parameters;
  }

  public String getHeader(String name)
  {
    String value = super.getHeader(xssEncode(name));
    if (value != null) {
      value = xssEncode(value);
    }
    return value;
  }

  private String xssEncode(String s)
  {
    if ((s == null) || (s.equals("")))
      return s;
    try
    {
      s = URLDecoder.decode(s, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < this.filterChars.length; i++) {
      if (s.contains(this.filterChars[i])) {
        s = s.replace(this.filterChars[i], this.replaceChars[i]);
      }
    }
    return s;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.XssHttpServletRequestWrapper
 * JD-Core Version:    0.6.2
 */