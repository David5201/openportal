package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.bean.Portaluserrole;
import com.leeson.core.query.PortaldepartmentQuery;
import com.leeson.core.query.PortalroleQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.query.PortaluserroleQuery;
import com.leeson.core.service.PortaldepartmentService;
import com.leeson.core.service.PortalroleService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.service.PortaluserroleService;
import com.leeson.core.utils.DepartmentUtils;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortaluserController
{
  private static Logger logger = Logger.getLogger(PortaluserController.class);

  @Autowired
  private PortaluserService portaluserService;

  @Autowired
  private PortaldepartmentService portaldepartmentService;

  @Autowired
  private PortaluserroleService portaluserroleService;

  @Autowired
  private PortalroleService portalroleService;

  @RequestMapping({"ist.action"})
  public String page(PortaluserQuery query, Long roleId, ModelMap model) { query.setNameLike(true);
    query.setLoginNameLike(true);

    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getLoginName())) {
      query.setLoginName(null);
    }

    boolean dNull = false;
    if ((query.getDepartmentId() != null) && (query.getDepartmentId().longValue() == 0L)) {
      query.setDepartmentId(null);
      dNull = true;
    }

    Pagination pagination = this.portaluserService
      .getPortaluserListWithPage(query);
    Long uid;
    if ((roleId != null) && (roleId.longValue() != 0L)) {
      PortaluserroleQuery urq = new PortaluserroleQuery();
      urq.setRoleId(roleId);
      List<Portaluserrole> urs = this.portaluserroleService.getPortaluserroleList(urq);
      List ut = new ArrayList();
      List us = pagination.getList();
      Collection uids = new HashSet();
      for (Portaluserrole ur : urs)
        uids.add(ur.getUserId());
      Iterator localIterator2;
      for (localIterator2 = uids.iterator(); localIterator2.hasNext(); 
        localIterator2.hasNext())
      {
        uid = (Long)localIterator2.next();
        localIterator2 = us.iterator(); 
				  Portaluser u = (Portaluser)localIterator2.next();
        if (u.getId() == uid) {
          ut.add(u);
        }
				  continue; 
      }

      pagination.setList(ut);
    }
    Collection uids;
    if ((roleId != null) && (roleId.longValue() == 0L)) {
      List<Portaluserrole> urs = this.portaluserroleService.getPortaluserroleList(new PortaluserroleQuery());
      List ut = new ArrayList();
      List<Portaluser> us = (List<Portaluser>) pagination.getList();
      uids = new HashSet();
      for (Portaluserrole ur : urs) {
        uids.add(ur.getUserId());
      }
      for (Portaluser u : us) {
        if (!uids.contains(u.getId())) {
          ut.add(u);
        }
      }
      pagination.setList(ut);
    }

    if (dNull) {
      List ut = new ArrayList();
      List<Portaluser> us = (List<Portaluser>) pagination.getList();
      for (Portaluser u : us) {
        if (u.getDepartmentId() == null) {
          ut.add(u);
        }
      }
      pagination.setList(ut);
      query.setDepartmentId(Long.valueOf(0L));
    }

    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());

    PortaluserroleQuery portaluserroleQuery = new PortaluserroleQuery();
    List userroles = this.portaluserroleService
      .getPortaluserroleList(portaluserroleQuery);
    model.addAttribute("userroles", userroles);

    List roleList = this.portalroleService
      .getPortalroleList(new PortalroleQuery());
    model.addAttribute("roleList", roleList);

    model.addAttribute("query", query);
    model.addAttribute("roleId", roleId);
    model.addAttribute("departmentList", departmentList);
    model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
    model.addAttribute("pagination", pagination);
    return "portaluser/list";
  }

  @RequestMapping({"ddV"})
  public String addV(ModelMap model)
  {
    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());
    List roleList = this.portalroleService
      .getPortalroleList(new PortalroleQuery());
    model.addAttribute("roleList", roleList);
    model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
    return "portaluser/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portaluser e, Long roleId, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getLoginName()) | 
      stringUtils.isBlank(e.getPassword()))) {
      model.addAttribute("msg", "用户名和密码不能为空！");
      model.addAttribute("entity", e);
      return "portaluser/save";
    }
    PortaluserQuery uq = new PortaluserQuery();
    uq.setLoginName(e.getLoginName());
    uq.setLoginNameLike(false);
    if (this.portaluserService.getPortaluserList(uq).size() > 0) {
      model.addAttribute("msg", "登录名已经存在！");
      model.addAttribute("entity", e);
      return "portaluser/save";
    }
    if (stringUtils.isBlank(e.getPassword()))
      e.setPassword(null);
    else {
      e.setPassword(DigestUtils.md5Hex(e.getPassword()));
    }
    this.portaluserService.addPortaluser(e);
    if (roleId != null) {
      Portaluserrole portaluserrole = new Portaluserrole();
      portaluserrole.setRoleId(roleId);
      portaluserrole.setUserId(((Portaluser)this.portaluserService.getPortaluserList(uq).get(0)).getId());
      this.portaluserroleService.addPortaluserrole(portaluserrole);
    }
    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String editPassword(@RequestParam Long id, ModelMap model)
  {
    Portaluser portaluser = new Portaluser();
    portaluser.setId(id);
    portaluser.setPassword(DigestUtils.md5Hex("1234"));
    this.portaluserService.updatePortaluserByKey(portaluser);
    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portaluser e = this.portaluserService.getPortaluserByKey(id);
    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());
    PortaluserroleQuery portaluserroleQuery = new PortaluserroleQuery();
    portaluserroleQuery.setUserId(id);
    List userroles = this.portaluserroleService
      .getPortaluserroleList(portaluserroleQuery);
    if (userroles.size() > 0) {
      Long roleId = ((Portaluserrole)userroles.get(0)).getRoleId();
      model.addAttribute("roleId", roleId);
    }

    List roleList = this.portalroleService
      .getPortalroleList(new PortalroleQuery());
    model.addAttribute("roleList", roleList);

    model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
    model.addAttribute("entity", e);
    return "portaluser/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portaluser e, Long roleId)
  {
    Portaluser u = this.portaluserService.getPortaluserByKey(e.getId());
    if (stringUtils.isBlank(e.getPassword()))
      e.setPassword(u.getPassword());
    else {
      e.setPassword(DigestUtils.md5Hex(e.getPassword()));
    }
    e.setLoginName(u.getLoginName());

    PortaluserroleQuery pu = new PortaluserroleQuery();
    pu.setUserId(e.getId());
    this.portaluserroleService.deleteByQuery(pu);

    if (roleId != null) {
      Portaluserrole ar = new Portaluserrole();
      ar.setUserId(e.getId());
      ar.setRoleId(roleId);
      this.portaluserroleService.addPortaluserrole(ar);
    }
    this.portaluserService.updatePortaluserByKeyAll(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    PortaluserroleQuery pu = new PortaluserroleQuery();
    pu.setUserId(id);
    this.portaluserroleService.deleteByQuery(pu);
    this.portaluserService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      PortaluserroleQuery pu = new PortaluserroleQuery();
      pu.setUserId(id);
      this.portaluserroleService.deleteByQuery(pu);
    }
    this.portaluserService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"ogin.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String loginUI(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    model.addAttribute("msg", "");
    return "portaluser/login";
  }

  @RequestMapping(value={"ogin.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String login(PortaluserQuery e, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getLoginName()) | 
      stringUtils.isBlank(e.getPassword()))) {
      model.addAttribute("msg", "用户名和密码不能为空！");
      return "portaluser/login";
    }
    String inputPassword = e.getPassword();
    String md5PW = DigestUtils.md5Hex(e.getPassword());
    e.setPassword(md5PW);

    String result = "portaluser/login";
    UsernamePasswordToken token = new UsernamePasswordToken(e.getLoginName(), e.getPassword());
    Subject currentUser = SecurityUtils.getSubject();

    logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Try Login System !!!");
    try {
      currentUser.login(token);
      token.setRememberMe(true);
      Portaluser user = (Portaluser)this.portaluserService.getPortaluserList(e).get(0);
      request.getSession().setAttribute("user", user);
      result = "redirect:ndex.action";
      logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Use Username: [" + e.getLoginName() + "] Success to Login System !!!");
    }
    catch (Exception loginE)
    {
      model.addAttribute("msg", "用户名或密码错误！");
      result = "portaluser/login";
      logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Use Username: [" + e.getLoginName() + "] Password: [" + inputPassword + "] False to Login System !!!");
    }
    return result;
  }

  @RequestMapping({"oginOut.action"})
  public String loginOut(HttpServletRequest request, HttpServletResponse response)
  {
    Subject currentUser = SecurityUtils.getSubject();
    currentUser.logout();
    request.getSession().removeAttribute("user");
    return "portaluser/logout";
  }

  @RequestMapping({"ditmyself.action"})
  public String editmyself(HttpServletRequest request, ModelMap model)
  {
    HttpSession session = request.getSession();
    Portaluser u = (Portaluser)session.getAttribute("user");
    Portaluser e = null;
    if (u != null) {
      e = this.portaluserService.getPortaluserByKey(u.getId());
    }
    if (e != null) {
      model.addAttribute("entity", e);
      return "portaluser/edit";
    }
    model.addAttribute("msg", "请重新登录！");
    return "portaluser/login";
  }

  @RequestMapping(value={"ditmyself.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String editmyself(HttpServletRequest request, Portaluser e, ModelMap model)
  {
    HttpSession session = request.getSession();
    Portaluser u = (Portaluser)session.getAttribute("user");
    if (u == null) {
      model.addAttribute("msg", "请重新登录！");
      return "portaluser/login";
    }

    if (stringUtils.isBlank(e.getPassword()))
      e.setPassword(u.getPassword());
    else {
      e.setPassword(DigestUtils.md5Hex(e.getPassword()));
    }
    e.setLoginName(u.getLoginName());
    e.setId(u.getId());
    this.portaluserService.updatePortaluserByKey(e);
    return "redirect:ight.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaluserController
 * JD-Core Version:    0.6.2
 */