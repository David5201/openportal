package com.leeson.common.web.session;

import com.leeson.common.web.RequestUtils;
import com.leeson.common.web.session.cache.SessionCache;
import com.leeson.common.web.session.id.SessionIdGenerator;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class CacheSessionProvider
  implements SessionProvider, InitializingBean
{
  private SessionCache sessionCache;
  private SessionIdGenerator sessionIdGenerator;
  private int sessionTimeout = 30;

  public Serializable getAttribute(HttpServletRequest request, String name)
  {
    Map session = null;

    String root = RequestUtils.getRequestedSessionId(request);
    if (StringUtils.isBlank(root)) {
      return null;
    }
    session = this.sessionCache.getSession(root);
    if (session != null) {
      return (Serializable)session.get(name);
    }
    return null;
  }

  public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value)
  {
    Map session = new HashMap();
    String root = RequestUtils.getRequestedSessionId(request);

    session.put(name, value);
    this.sessionCache.setSession(root, session, this.sessionTimeout);
  }

  public String getSessionId(HttpServletRequest request, HttpServletResponse response)
  {
    String root = RequestUtils.getRequestedSessionId(request);
    if ((root == null) || (root.length() != 32) || (!this.sessionCache.exist(root))) {
      do
        root = this.sessionIdGenerator.get();
      while (this.sessionCache.exist(root));
      this.sessionCache.setSession(root, new HashMap(), 
        this.sessionTimeout);
      response.addCookie(createCookie(request, root));
    }
    return root;
  }

  public void logout(HttpServletRequest request, HttpServletResponse response) {
    String root = RequestUtils.getRequestedSessionId(request);
    if (!StringUtils.isBlank(root)) {
      this.sessionCache.clear(root);
      Cookie cookie = createCookie(request, null);
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }
  }

  private Cookie createCookie(HttpServletRequest request, String value) {
    Cookie cookie = new Cookie("JSESSIONID", value);
    String ctx = request.getContextPath();
    cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
    return cookie;
  }

  public void afterPropertiesSet() throws Exception {
    Assert.notNull(this.sessionCache);
    Assert.notNull(this.sessionIdGenerator);
  }

  public void setSessionCache(SessionCache sessionCache)
  {
    this.sessionCache = sessionCache;
  }

  public void setSessionTimeout(int sessionTimeout)
  {
    Assert.isTrue(sessionTimeout > 0);
    this.sessionTimeout = sessionTimeout;
  }

  public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
    this.sessionIdGenerator = sessionIdGenerator;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.CacheSessionProvider
 * JD-Core Version:    0.6.2
 */