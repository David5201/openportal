package com.leeson.core.realm;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalprivilege;
import com.leeson.core.bean.Portalrole;
import com.leeson.core.bean.Portalroleprivilege;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.bean.Portaluserrole;
import com.leeson.core.query.PortalroleprivilegeQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.query.PortaluserroleQuery;
import com.leeson.core.service.PortalprivilegeService;
import com.leeson.core.service.PortalroleService;
import com.leeson.core.service.PortalroleprivilegeService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.service.PortaluserroleService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroDbRealm extends AuthorizingRealm
{

  @Resource
  PortaluserService userService;

  @Resource
  PortaluserroleService userroleService;

  @Resource
  PortalroleService roleService;

  @Resource
  PortalroleprivilegeService roleprivilegeService;

  @Resource
  PortalprivilegeService privilegeService;

  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
    throws AuthenticationException
  {
    UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
    PortaluserQuery pq = new PortaluserQuery();
    pq.setLoginName(token.getUsername());
    pq.setLoginNameLike(false);
    List users = this.userService.getPortaluserList(pq);
    Portaluser user = null;
    if (users.size() == 1) {
      user = (Portaluser)users.get(0);
    }
    if (user != null) {
      return new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), getName());
    }
    throw new AuthenticationException();
  }

  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
  {
    Set roleNames = new HashSet();
    Set permissions = new HashSet();
    Portaluser user = (Portaluser)SecurityUtils.getSubject().getSession().getAttribute("user");

    List<Portalprivilege> privileges = userToPrivileges(user);

    if (privileges != null) {
      for (Portalprivilege p : privileges) {
        roleNames.add(p.getName());
        if (stringUtils.isNotBlank(p.getUrl())) {
          permissions.add(p.getUrl());
        }
      }
    }
    if ((user != null) && (user.getLoginName().equals("admin"))) {
      roleNames.add("admin");
      permissions.add("admin");
    }
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
    info.setStringPermissions(permissions);
    return info;
  }

  public List<Portalprivilege> userToPrivileges(Portaluser user)
  {
    PortaluserroleQuery urq = new PortaluserroleQuery();
    urq.setUserId(user.getId());
    List<Portaluserrole> userRoleList = null;
    userRoleList = this.userroleService.getPortaluserroleList(urq);
    List roleIds = new ArrayList();
    if (userRoleList.size() > 0) {
      for (Portaluserrole userRole : userRoleList) {
        roleIds.add(userRole.getRoleId());
      }
    }
    List roles = null;
    if (roleIds.size() > 0) {
      roles = this.roleService.getPortalroleByKeys(roleIds);
    }

    List privileges = null;
    List pids = new ArrayList();
    if ((roles != null) && (roles.size() > 0))
    {
      Iterator localIterator3;
      for (Iterator localIterator2 = roles.iterator(); localIterator2.hasNext(); 
        localIterator3.hasNext())
      {
        Portalrole role = (Portalrole)localIterator2.next();
        PortalroleprivilegeQuery rpq = new PortalroleprivilegeQuery();
        rpq.setRoleId(role.getId());
        List rps = null;
        rps = this.roleprivilegeService.getPortalroleprivilegeList(rpq);
        localIterator3 = rps.iterator(); 
				  Portalroleprivilege rp = (Portalroleprivilege)localIterator3.next();
        pids.add(rp.getPrivilegeId());
				  continue;
      }

    }

    if (pids.size() > 0) {
      privileges = this.privilegeService.getPortalprivilegeByKeys(pids);
    }
    return privileges;
  }

  public void clearCachedAuthorizationInfo(String principal)
  {
    SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
    clearCachedAuthorizationInfo(principals);
  }

  public void clearAllCachedAuthorizationInfo()
  {
    Cache cache = getAuthorizationCache();
    if (cache != null)
      for (Iterator localIterator = cache.keys().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
        cache.remove(key);
      }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.realm.ShiroDbRealm
 * JD-Core Version:    0.6.2
 */