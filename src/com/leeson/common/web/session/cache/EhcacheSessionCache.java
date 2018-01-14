/*    */ package com.leeson.common.web.session.cache;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.sf.ehcache.Ehcache;
/*    */ import net.sf.ehcache.Element;
/*    */ import org.springframework.beans.factory.InitializingBean;
/*    */ import org.springframework.util.Assert;
/*    */ 
/*    */ public class EhcacheSessionCache
/*    */   implements SessionCache, InitializingBean
/*    */ {
/*    */   private Ehcache cache;
/*    */ 
/*    */   public Map<String, Serializable> getSession(String root)
/*    */   {
/* 16 */     Element e = this.cache.get(root);
/* 17 */     return e != null ? (HashMap)e.getValue() : null;
/*    */   }
/*    */ 
/*    */   public void setSession(String root, Map<String, Serializable> session, int exp)
/*    */   {
/* 22 */     this.cache.put(new Element(root, session));
/*    */   }
/*    */ 
/*    */   public Serializable getAttribute(String root, String name) {
/* 26 */     Map session = getSession(root);
/* 27 */     return session != null ? (Serializable)session.get(name) : null;
/*    */   }
/*    */ 
/*    */   public void setAttribute(String root, String name, Serializable value, int exp)
/*    */   {
/* 32 */     Map session = getSession(root);
/* 33 */     if (session == null) {
/* 34 */       session = new HashMap();
/*    */     }
/* 36 */     session.put(name, value);
/* 37 */     this.cache.put(new Element(root, session));
/*    */   }
/*    */ 
/*    */   public void clear(String root) {
/* 41 */     this.cache.remove(root);
/*    */   }
/*    */ 
/*    */   public boolean exist(String root) {
/* 45 */     return this.cache.isKeyInCache(root);
/*    */   }
/*    */ 
/*    */   public void afterPropertiesSet() throws Exception {
/* 49 */     Assert.notNull(this.cache);
/*    */   }
/*    */ 
/*    */   public void setCache(Ehcache cache)
/*    */   {
/* 55 */     this.cache = cache;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.cache.EhcacheSessionCache
 * JD-Core Version:    0.6.2
 */