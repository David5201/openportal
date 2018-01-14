package com.leeson.common.web.session.cache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;

public class MemcachedDangaCache
  implements SessionCache, InitializingBean
{
  private MemCachedClient client;
  private String[] servers = { "192.168.0.1:80" };

  private Integer[] weights = { Integer.valueOf(1) };

  public HashMap<String, Serializable> getSession(String root)
  {
    return (HashMap)this.client.get(root);
  }

  public void setSession(String root, Map<String, Serializable> session, int exp)
  {
    this.client.set(root, session, new Date(System.currentTimeMillis() + exp * 60 * 1000));
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
    Date expDate = new Date(System.currentTimeMillis() + exp * 60 * 1000);
    this.client.set(root, session, expDate);
  }

  public void clear(String root) {
    this.client.delete(root);
  }

  public boolean exist(String root) {
    return this.client.keyExists(root);
  }

  public void afterPropertiesSet() throws Exception {
    this.client = new MemCachedClient();

    SockIOPool pool = SockIOPool.getInstance();

    pool.setServers(this.servers);
    pool.setWeights(this.weights);

    pool.setInitConn(5);
    pool.setMinConn(5);
    pool.setMaxConn(250);
    pool.setMaxIdle(21600000L);

    pool.setMaintSleep(30L);

    pool.setNagle(false);
    pool.setSocketTO(3000);
    pool.setSocketConnectTO(0);

    pool.initialize();

    this.client.setCompressEnable(true);
    this.client.setCompressThreshold(65536L);
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

  @Test
  public void testMemcached()
  {
    MemCachedClient c = new MemCachedClient();
    SockIOPool pool = SockIOPool.getInstance();
    pool.setServers(this.servers);
    pool.setWeights(this.weights);
    pool.setInitConn(5);
    pool.setMinConn(5);
    pool.setMaxConn(250);
    pool.setMaxIdle(21600000L);
    pool.setMaintSleep(30L);
    pool.setNagle(false);
    pool.setSocketTO(3000);
    pool.setSocketConnectTO(0);
    pool.initialize();
    c.setCompressEnable(true);
    c.setCompressThreshold(65536L);
    String key = "t11";
    Object object = c.get(key);
    System.out.println(object);
  }

  @Test
  public void testRedis()
  {
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.cache.MemcachedDangaCache
 * JD-Core Version:    0.6.2
 */