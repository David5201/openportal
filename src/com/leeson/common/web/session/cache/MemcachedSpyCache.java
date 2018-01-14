package com.leeson.common.web.session.cache;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class MemcachedSpyCache
  implements SessionCache, InitializingBean, DisposableBean
{
  private MemcachedClient client;
  private String[] servers;
  private Integer[] weights;

  public HashMap<String, Serializable> getSession(String root)
  {
    return (HashMap)this.client.get(root);
  }

  public void setSession(String root, Map<String, Serializable> session, int exp)
  {
    this.client.set(root, exp * 60, session);
  }

  public Serializable getAttribute(String root, String name) {
    HashMap session = getSession(root);
    return session != null ? (Serializable)session.get(name) : null;
  }

  public void setAttribute(String root, String name, Serializable value, int exp)
  {
    HashMap session = getSession(root);
    if (session == null) {
      session = new HashMap();
    }
    session.put(name, value);
    this.client.set(root, exp * 60, session);
  }

  public void clear(String root) {
    this.client.delete(root);
  }

  public boolean exist(String root) {
    return this.client.get(root) != null;
  }

  public void afterPropertiesSet() throws Exception {
    List addr = new ArrayList(
      this.servers.length);

    for (String s : this.servers) {
      int index = s.indexOf(":");
      addr.add(new InetSocketAddress(s.substring(0, index), 
        Integer.parseInt(s.substring(index + 1))));
    }
    this.client = new MemcachedClient(addr);
  }

  public void destroy() throws Exception {
    this.client.shutdown();
  }

  public String[] getServers() {
    return this.servers;
  }

  public void setServers(String[] servers) {
    this.servers = servers;
  }

  public Integer[] getWeights() {
    return this.weights;
  }

  public void setWeights(Integer[] weights) {
    this.weights = weights;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.cache.MemcachedSpyCache
 * JD-Core Version:    0.6.2
 */