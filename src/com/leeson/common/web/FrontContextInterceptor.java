package com.leeson.common.web;

import java.io.PrintStream;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

public class FrontContextInterceptor extends HandlerInterceptorAdapter
{
  public static final String RETURN_URL = "returnUrl";
  private Integer adminId;
  private String[] excludeUrls;
  private String loginUrl;
  private String processUrl;
  private String returnUrl;

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    return true;
  }

  private String getLoginUrl(HttpServletRequest request)
  {
    StringBuilder buff = new StringBuilder();
    if (this.loginUrl.startsWith("/")) {
      String ctx = request.getContextPath();
      if (!StringUtils.isBlank(ctx)) {
        buff.append(ctx);
      }
    }
    buff.append(this.loginUrl);

    System.out.println(buff.toString());
    return buff.toString();
  }

  private String getProcessUrl(HttpServletRequest request) {
    StringBuilder buff = new StringBuilder();
    if (this.loginUrl.startsWith("/")) {
      String ctx = request.getContextPath();
      if (!StringUtils.isBlank(ctx)) {
        buff.append(ctx);
      }
    }
    buff.append(this.processUrl);
    return buff.toString();
  }

  private boolean exclude(String uri) {
    if (this.excludeUrls != null) {
      for (String exc : this.excludeUrls) {
        if (exc.equals(uri)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean permistionPass(String uri, Set<String> perms, boolean viewOnly)
  {
    String u = null;

    for (String perm : perms) {
      if (uri.startsWith(perm))
      {
        if (viewOnly)
        {
          int i = uri.lastIndexOf("/");
          if (i == -1) {
            throw new RuntimeException("uri must start width '/':" + 
              uri);
          }
          u = uri.substring(i + 1);

          if (u.startsWith("o_")) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  private static String getURI(HttpServletRequest request)
    throws IllegalStateException
  {
    UrlPathHelper helper = new UrlPathHelper();
    String uri = helper.getOriginatingRequestUri(request);
    String ctxPath = helper.getOriginatingContextPath(request);
    if (ctxPath != null) {
      uri = uri.substring(ctxPath.length());
    }
    return uri;
  }

  public void setExcludeUrls(String[] excludeUrls)
  {
    this.excludeUrls = excludeUrls;
  }

  public void setAdminId(Integer adminId) {
    this.adminId = adminId;
  }

  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }

  public void setProcessUrl(String processUrl) {
    this.processUrl = processUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.FrontContextInterceptor
 * JD-Core Version:    0.6.2
 */