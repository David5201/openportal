package com.leeson.common.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

public class RequestUtils
{
  private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

  public static String getQueryParam(HttpServletRequest request, String name)
  {
    if (StringUtils.isBlank(name)) {
      return null;
    }
    if (request.getMethod().equalsIgnoreCase("POST")) {
      return request.getParameter(name);
    }
    String s = request.getQueryString();
    if (StringUtils.isBlank(s))
      return null;
    try
    {
      s = URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      log.error("encoding UTF-8 not support?", e);
    }
    String[] values = (String[])parseQueryString(s).get(name);
    if ((values != null) && (values.length > 0)) {
      return values[(values.length - 1)];
    }
    return null;
  }

  public static Map<String, Object> getQueryParams(HttpServletRequest request)
  {
    Map<String, String[]> map = null;
    if (request.getMethod().equalsIgnoreCase("POST")) {
      map = request.getParameterMap();
    } else {
      String s = request.getQueryString();
      if (StringUtils.isBlank(s))
        return new HashMap();
      try
      {
        s = URLDecoder.decode(s, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        log.error("encoding UTF-8 not support?", e);
      }
      map = parseQueryString(s);
    }

    Map params = new HashMap(map.size());

    for (Map.Entry entry : map.entrySet()) {
      int len = ((String[])entry.getValue()).length;
      if (len == 1)
        params.put((String)entry.getKey(), ((String[])entry.getValue())[0]);
      else if (len > 1) {
        params.put((String)entry.getKey(), entry.getValue());
      }
    }
    return params;
  }

  public static Map<String, String[]> parseQueryString(String s)
  {
    String[] valArray = null;
    if (s == null) {
      throw new IllegalArgumentException();
    }
    Map ht = new HashMap();
    StringTokenizer st = new StringTokenizer(s, "&");
    while (st.hasMoreTokens()) {
      String pair = st.nextToken();
      int pos = pair.indexOf('=');
      if (pos != -1)
      {
        String key = pair.substring(0, pos);
        String val = pair.substring(pos + 1, pair.length());
        if (ht.containsKey(key)) {
          String[] oldVals = (String[])ht.get(key);
          valArray = new String[oldVals.length + 1];
          for (int i = 0; i < oldVals.length; i++) {
            valArray[i] = oldVals[i];
          }
          valArray[oldVals.length] = val;
        } else {
          valArray = new String[1];
          valArray[0] = val;
        }
        ht.put(key, valArray);
      }
    }
    return ht;
  }

  public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix)
  {
    return getRequestMap(request, prefix, false);
  }

  public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix)
  {
    return getRequestMap(request, prefix, true);
  }

  private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
  {
    Map map = new HashMap();
    Enumeration names = request.getParameterNames();

    while (names.hasMoreElements()) {
      String name = (String)names.nextElement();
      if (name.startsWith(prefix)) {
        String key = nameWithPrefix ? name : name.substring(prefix.length());
        String value = StringUtils.join(request.getParameterValues(name), ',');
        map.put(key, value);
      }
    }
    return map;
  }

  public static String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("X-Real-IP");
    if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
      return ip;
    }
    ip = request.getHeader("X-Forwarded-For");
    if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip)))
    {
      int index = ip.indexOf(',');
      if (index != -1) {
        return ip.substring(0, index);
      }
      return ip;
    }

    return request.getRemoteAddr();
  }

  public static String getLocation(HttpServletRequest request)
  {
    UrlPathHelper helper = new UrlPathHelper();
    StringBuffer buff = request.getRequestURL();
    String uri = request.getRequestURI();
    String origUri = helper.getOriginatingRequestUri(request);
    buff.replace(buff.length() - uri.length(), buff.length(), origUri);
    String queryString = helper.getOriginatingQueryString(request);
    if (queryString != null) {
      buff.append("?").append(queryString);
    }
    return buff.toString();
  }

  public static String getRequestedSessionId(HttpServletRequest request)
  {
    String sid = request.getRequestedSessionId();
    String ctx = request.getContextPath();

    if ((request.isRequestedSessionIdFromURL()) || (StringUtils.isBlank(ctx))) {
      return sid;
    }

    Cookie cookie = CookieUtils.getCookie(request, 
      "JSESSIONID");
    if (cookie != null) {
      return cookie.getValue();
    }
    return request.getSession().getId();
  }

  public static void main(String[] args)
  {
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.RequestUtils
 * JD-Core Version:    0.6.2
 */