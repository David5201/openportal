/*     */ package com.leeson.common.web.session.cache;
/*     */ 
/*     */ import com.danga.MemCached.MemCachedClient;
/*     */ import com.danga.MemCached.SockIOPool;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.junit.Test;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ 
/*     */ public class MemcachedDangaCache
/*     */   implements SessionCache, InitializingBean
/*     */ {
/*     */   private MemCachedClient client;
/*  22 */   private String[] servers = { "192.168.0.1:80" };
/*     */ 
/*  24 */   private Integer[] weights = { Integer.valueOf(1) };
/*     */ 
/*     */   public HashMap<String, Serializable> getSession(String root)
/*     */   {
/*  28 */     return (HashMap)this.client.get(root);
/*     */   }
/*     */ 
/*     */   public void setSession(String root, Map<String, Serializable> session, int exp)
/*     */   {
/*  33 */     this.client.set(root, session, new Date(System.currentTimeMillis() + exp * 60 * 1000));
/*     */   }
/*     */ 
/*     */   public Serializable getAttribute(String root, String name) {
/*  37 */     HashMap session = getSession(root);
/*  38 */     return session != null ? (Serializable)session.get(name) : null;
/*     */   }
/*     */ 
/*     */   public void setAttribute(String root, String name, Serializable value, int exp)
/*     */   {
/*  43 */     HashMap session = getSession(root);
/*  44 */     if (session == null) {
/*  45 */       session = new HashMap();
/*     */     }
/*  47 */     session.put(name, value);
/*  48 */     Date expDate = new Date(System.currentTimeMillis() + exp * 60 * 1000);
/*  49 */     this.client.set(root, session, expDate);
/*     */   }
/*     */ 
/*     */   public void clear(String root) {
/*  53 */     this.client.delete(root);
/*     */   }
/*     */ 
/*     */   public boolean exist(String root) {
/*  57 */     return this.client.keyExists(root);
/*     */   }
/*     */ 
/*     */   public void afterPropertiesSet() throws Exception {
/*  61 */     this.client = new MemCachedClient();
/*     */ 
/*  63 */     SockIOPool pool = SockIOPool.getInstance();
/*     */ 
/*  66 */     pool.setServers(this.servers);
/*  67 */     pool.setWeights(this.weights);
/*     */ 
/*  73 */     pool.setInitConn(5);
/*  74 */     pool.setMinConn(5);
/*  75 */     pool.setMaxConn(250);
/*  76 */     pool.setMaxIdle(21600000L);
/*     */ 
/*  81 */     pool.setMaintSleep(30L);
/*     */ 
/*  87 */     pool.setNagle(false);
/*  88 */     pool.setSocketTO(3000);
/*  89 */     pool.setSocketConnectTO(0);
/*     */ 
/*  92 */     pool.initialize();
/*     */ 
/*  96 */     this.client.setCompressEnable(true);
/*  97 */     this.client.setCompressThreshold(65536L);
/*     */   }
/*     */ 
/*     */   public String[] getServers() {
/* 101 */     return this.servers;
/*     */   }
/*     */ 
/*     */   public void setServers(String[] servers) {
/* 105 */     this.servers = servers;
/*     */   }
/*     */ 
/*     */   public Integer[] getWeights() {
/* 109 */     return this.weights;
/*     */   }
/*     */ 
/*     */   public void setWeights(Integer[] weights) {
/* 113 */     this.weights = weights;
/*     */   }
/*     */ 
/*     */   @Test
/*     */   public void testMemcached()
/*     */   {
/* 119 */     MemCachedClient c = new MemCachedClient();
/* 120 */     SockIOPool pool = SockIOPool.getInstance();
/* 121 */     pool.setServers(this.servers);
/* 122 */     pool.setWeights(this.weights);
/* 123 */     pool.setInitConn(5);
/* 124 */     pool.setMinConn(5);
/* 125 */     pool.setMaxConn(250);
/* 126 */     pool.setMaxIdle(21600000L);
/* 127 */     pool.setMaintSleep(30L);
/* 128 */     pool.setNagle(false);
/* 129 */     pool.setSocketTO(3000);
/* 130 */     pool.setSocketConnectTO(0);
/* 131 */     pool.initialize();
/* 132 */     c.setCompressEnable(true);
/* 133 */     c.setCompressThreshold(65536L);
/* 134 */     String key = "t11";
/* 135 */     Object object = c.get(key);
/* 136 */     System.out.println(object);
/*     */   }
/*     */ 
/*     */   @Test
/*     */   public void testRedis()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.cache.MemcachedDangaCache
 * JD-Core Version:    0.6.2
 */