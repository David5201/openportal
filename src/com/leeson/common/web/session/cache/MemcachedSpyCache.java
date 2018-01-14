/*    */ package com.leeson.common.web.session.cache;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.spy.memcached.MemcachedClient;
/*    */ import org.springframework.beans.factory.DisposableBean;
/*    */ import org.springframework.beans.factory.InitializingBean;
/*    */ 
/*    */ public class MemcachedSpyCache
/*    */   implements SessionCache, InitializingBean, DisposableBean
/*    */ {
/*    */   private MemcachedClient client;
/*    */   private String[] servers;
/*    */   private Integer[] weights;
/*    */ 
/*    */   public HashMap<String, Serializable> getSession(String root)
/*    */   {
/* 23 */     return (HashMap)this.client.get(root);
/*    */   }
/*    */ 
/*    */   public void setSession(String root, Map<String, Serializable> session, int exp)
/*    */   {
/* 28 */     this.client.set(root, exp * 60, session);
/*    */   }
/*    */ 
/*    */   public Serializable getAttribute(String root, String name) {
/* 32 */     HashMap session = getSession(root);
/* 33 */     return session != null ? (Serializable)session.get(name) : null;
/*    */   }
/*    */ 
/*    */   public void setAttribute(String root, String name, Serializable value, int exp)
/*    */   {
/* 38 */     HashMap session = getSession(root);
/* 39 */     if (session == null) {
/* 40 */       session = new HashMap();
/*    */     }
/* 42 */     session.put(name, value);
/* 43 */     this.client.set(root, exp * 60, session);
/*    */   }
/*    */ 
/*    */   public void clear(String root) {
/* 47 */     this.client.delete(root);
/*    */   }
/*    */ 
/*    */   public boolean exist(String root) {
/* 51 */     return this.client.get(root) != null;
/*    */   }
/*    */ 
/*    */   public void afterPropertiesSet() throws Exception {
/* 55 */     List addr = new ArrayList(
/* 56 */       this.servers.length);
/*    */ 
/* 58 */     for (String s : this.servers) {
/* 59 */       int index = s.indexOf(":");
/* 60 */       addr.add(new InetSocketAddress(s.substring(0, index), 
/* 61 */         Integer.parseInt(s.substring(index + 1))));
/*    */     }
/* 63 */     this.client = new MemcachedClient(addr);
/*    */   }
/*    */ 
/*    */   public void destroy() throws Exception {
/* 67 */     this.client.shutdown();
/*    */   }
/*    */ 
/*    */   public String[] getServers() {
/* 71 */     return this.servers;
/*    */   }
/*    */ 
/*    */   public void setServers(String[] servers) {
/* 75 */     this.servers = servers;
/*    */   }
/*    */ 
/*    */   public Integer[] getWeights() {
/* 79 */     return this.weights;
/*    */   }
/*    */ 
/*    */   public void setWeights(Integer[] weights) {
/* 83 */     this.weights = weights;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.cache.MemcachedSpyCache
 * JD-Core Version:    0.6.2
 */