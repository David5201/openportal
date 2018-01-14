/*     */ package com.leeson.core.realm;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalprivilege;
/*     */ import com.leeson.core.bean.Portalrole;
/*     */ import com.leeson.core.bean.Portalroleprivilege;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.bean.Portaluserrole;
/*     */ import com.leeson.core.query.PortalroleprivilegeQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.query.PortaluserroleQuery;
/*     */ import com.leeson.core.service.PortalprivilegeService;
/*     */ import com.leeson.core.service.PortalroleService;
/*     */ import com.leeson.core.service.PortalroleprivilegeService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import com.leeson.core.service.PortaluserroleService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.shiro.SecurityUtils;
/*     */ import org.apache.shiro.authc.AuthenticationException;
/*     */ import org.apache.shiro.authc.AuthenticationInfo;
/*     */ import org.apache.shiro.authc.AuthenticationToken;
/*     */ import org.apache.shiro.authc.SimpleAuthenticationInfo;
/*     */ import org.apache.shiro.authc.UsernamePasswordToken;
/*     */ import org.apache.shiro.authz.AuthorizationInfo;
/*     */ import org.apache.shiro.authz.SimpleAuthorizationInfo;
/*     */ import org.apache.shiro.cache.Cache;
/*     */ import org.apache.shiro.realm.AuthorizingRealm;
/*     */ import org.apache.shiro.session.Session;
/*     */ import org.apache.shiro.subject.PrincipalCollection;
/*     */ import org.apache.shiro.subject.SimplePrincipalCollection;
/*     */ import org.apache.shiro.subject.Subject;
/*     */ 
/*     */ public class ShiroDbRealm extends AuthorizingRealm
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   PortaluserService userService;
/*     */ 
/*     */   @Resource
/*     */   PortaluserroleService userroleService;
/*     */ 
/*     */   @Resource
/*     */   PortalroleService roleService;
/*     */ 
/*     */   @Resource
/*     */   PortalroleprivilegeService roleprivilegeService;
/*     */ 
/*     */   @Resource
/*     */   PortalprivilegeService privilegeService;
/*     */ 
/*     */   protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
/*     */     throws AuthenticationException
/*     */   {
/*  65 */     UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
/*  66 */     PortaluserQuery pq = new PortaluserQuery();
/*  67 */     pq.setLoginName(token.getUsername());
/*  68 */     pq.setLoginNameLike(false);
/*  69 */     List users = this.userService.getPortaluserList(pq);
/*  70 */     Portaluser user = null;
/*  71 */     if (users.size() == 1) {
/*  72 */       user = (Portaluser)users.get(0);
/*     */     }
/*  74 */     if (user != null) {
/*  75 */       return new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), getName());
/*     */     }
/*  77 */     throw new AuthenticationException();
/*     */   }
/*     */ 
/*     */   protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
/*     */   {
/*  87 */     Set roleNames = new HashSet();
/*  88 */     Set permissions = new HashSet();
/*  89 */     Portaluser user = (Portaluser)SecurityUtils.getSubject().getSession().getAttribute("user");
/*     */ 
/*  91 */     List<Portalprivilege> privileges = userToPrivileges(user);
/*     */ 
/*  93 */     if (privileges != null) {
/*  94 */       for (Portalprivilege p : privileges) {
/*  95 */         roleNames.add(p.getName());
/*  96 */         if (stringUtils.isNotBlank(p.getUrl())) {
/*  97 */           permissions.add(p.getUrl());
/*     */         }
/*     */       }
/*     */     }
/* 101 */     if ((user != null) && (user.getLoginName().equals("admin"))) {
/* 102 */       roleNames.add("admin");
/* 103 */       permissions.add("admin");
/*     */     }
/* 105 */     SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
/* 106 */     info.setStringPermissions(permissions);
/* 107 */     return info;
/*     */   }
/*     */ 
/*     */   public List<Portalprivilege> userToPrivileges(Portaluser user)
/*     */   {
/* 112 */     PortaluserroleQuery urq = new PortaluserroleQuery();
/* 113 */     urq.setUserId(user.getId());
/* 114 */     List<Portaluserrole> userRoleList = null;
/* 115 */     userRoleList = this.userroleService.getPortaluserroleList(urq);
/* 116 */     List roleIds = new ArrayList();
/* 117 */     if (userRoleList.size() > 0) {
/* 118 */       for (Portaluserrole userRole : userRoleList) {
/* 119 */         roleIds.add(userRole.getRoleId());
/*     */       }
/*     */     }
/* 122 */     List roles = null;
/* 123 */     if (roleIds.size() > 0) {
/* 124 */       roles = this.roleService.getPortalroleByKeys(roleIds);
/*     */     }
/*     */ 
/* 127 */     List privileges = null;
/* 128 */     List pids = new ArrayList();
/* 129 */     if ((roles != null) && (roles.size() > 0))
/*     */     {
/*     */       Iterator localIterator3;
/* 130 */       for (Iterator localIterator2 = roles.iterator(); localIterator2.hasNext(); 
/* 135 */         localIterator3.hasNext())
/*     */       {
/* 130 */         Portalrole role = (Portalrole)localIterator2.next();
/* 131 */         PortalroleprivilegeQuery rpq = new PortalroleprivilegeQuery();
/* 132 */         rpq.setRoleId(role.getId());
/* 133 */         List rps = null;
/* 134 */         rps = this.roleprivilegeService.getPortalroleprivilegeList(rpq);
/* 135 */         localIterator3 = rps.iterator(); 
				  Portalroleprivilege rp = (Portalroleprivilege)localIterator3.next();
/* 136 */         pids.add(rp.getPrivilegeId());
				  continue;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 141 */     if (pids.size() > 0) {
/* 142 */       privileges = this.privilegeService.getPortalprivilegeByKeys(pids);
/*     */     }
/* 144 */     return privileges;
/*     */   }
/*     */ 
/*     */   public void clearCachedAuthorizationInfo(String principal)
/*     */   {
/* 151 */     SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
/* 152 */     clearCachedAuthorizationInfo(principals);
/*     */   }
/*     */ 
/*     */   public void clearAllCachedAuthorizationInfo()
/*     */   {
/* 159 */     Cache cache = getAuthorizationCache();
/* 160 */     if (cache != null)
/* 161 */       for (Iterator localIterator = cache.keys().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 162 */         cache.remove(key);
/*     */       }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.realm.ShiroDbRealm
 * JD-Core Version:    0.6.2
 */