package com.leeson.common.web.session.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class EhcacheSessionCache
  implements SessionCache, InitializingBean
{
  private Ehcache cache;

  public Map<String, Serializable> getSession(String root)
  {
    Element e = this.cache.get(root);
    return e != null ? (HashMap)e.getValue() : null;
  }

  public void setSession(String root, Map<String, Serializable> session, int exp)
  {
    this.cache.put(new Element(root, session));
  }

  public Serializable getAttribute(String root, String name) {
    Map session = getSession(root);
    return session != null ? (Serializable)session.get(name) : null;
  }

  public void setAttribute(String root, String name, Serializable value, int exp)
  {
    Map session = getSession(root);
    if (session == null) {
      session = new HashMap();
    }
    session.put(name, value);
    this.cache.put(new Element(root, session));
  }

  public void clear(String root) {
    this.cache.remove(root);
  }

  public boolean exist(String root) {
    return this.cache.isKeyInCache(root);
  }

  public void afterPropertiesSet() throws Exception {
    Assert.notNull(this.cache);
  }

  public void setCache(Ehcache cache)
  {
    this.cache = cache;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.cache.EhcacheSessionCache
 * JD-Core Version:    0.6.2
 */