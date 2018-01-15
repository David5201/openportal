package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalrole;
import com.leeson.core.bean.Portalroleprivilege;
import com.leeson.core.bean.Portaluserrole;
import com.leeson.core.query.PortalroleQuery;
import com.leeson.core.query.PortalroleprivilegeQuery;
import com.leeson.core.query.PortaluserroleQuery;
import com.leeson.core.service.PortalprivilegeService;
import com.leeson.core.service.PortalroleService;
import com.leeson.core.service.PortalroleprivilegeService;
import com.leeson.core.service.PortaluserroleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portalroleController")
public class PortalroleController
{

  @Autowired
  private PortalroleService portalroleService;

  @Autowired
  private PortaluserroleService portaluserroleService;

  @Autowired
  private PortalroleprivilegeService portalroleprivilegeService;

  @Autowired
  private PortalprivilegeService portalprivilegeService;

  @RequestMapping({"ist.action"})
  public String page(PortalroleQuery query, ModelMap model)
  {
    query.setNameLike(true);
    query.setDescriptionLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }

    Pagination pagination = this.portalroleService
      .getPortalroleListWithPage(query);
    model.addAttribute("query", query);
    model.addAttribute("pagination", pagination);
    return "portalrole/list";
  }

  @RequestMapping({"ddV.action"})
  public String addV(ModelMap model)
  {
    return "portalrole/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalrole e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getName())) {
      model.addAttribute("msg", "角色名不能为空！");
      model.addAttribute("entity", e);
      return "portalrole/save";
    }
    this.portalroleService.addPortalrole(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"ditPrivilegeUI.action"})
  public String editPrivilegeUI(@RequestParam Long id, ModelMap model)
  {
    Portalrole portalrole = this.portalroleService.getPortalroleByKey(id);

    PortalroleprivilegeQuery rpq = new PortalroleprivilegeQuery();
    rpq.setRoleId(id);
    rpq.setFields("privilegeId");
    List<Portalroleprivilege> rps = this.portalroleprivilegeService
      .getPortalroleprivilegeList(rpq);
    Long[] privilegeIds = new Long[rps.size()];
    int index = 0;
    for (Portalroleprivilege rp : rps) {
      privilegeIds[(index++)] = rp.getPrivilegeId();
    }
    model.addAttribute("privilegeIds", privilegeIds);
    model.addAttribute("portalrole", portalrole);
    return "portalrole/setPrivilegeUI";
  }

  @RequestMapping({"ditPrivilege.action"})
  public String editPrivilege(@RequestParam Long[] privilegeIds, @RequestParam Long id)
  {
    PortalroleprivilegeQuery rpq = new PortalroleprivilegeQuery();
    rpq.setRoleId(id);
    Portalroleprivilege rp = new Portalroleprivilege();
    rp.setRoleId(id);
    this.portalroleprivilegeService.deleteByQuery(rpq);
    for (Long pid : privilegeIds) {
      rp.setPrivilegeId(pid);
      this.portalroleprivilegeService.addPortalroleprivilege(rp);
    }

    PortaluserroleQuery pu = new PortaluserroleQuery();
    pu.setRoleId(id);
    List<Portaluserrole> urs = this.portaluserroleService
      .getPortaluserroleList(pu);
    List<Long> uids = new ArrayList();
    for (Portaluserrole ur : urs) {
      ((List)uids).add(ur.getUserId());
    }
    this.portaluserroleService.deleteByQuery(pu);

    for (Long uid : uids) {
      Portaluserrole ar = new Portaluserrole();
      ar.setUserId(uid);
      ar.setRoleId(id);
      this.portaluserroleService.addPortaluserrole(ar);
    }

    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portalrole e = this.portalroleService.getPortalroleByKey(id);
    model.addAttribute("entity", e);
    return "portalrole/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalrole e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getName())) {
      model.addAttribute("msg", "角色名不能为空！");
      model.addAttribute("entity", e);
      return "portalrole/save";
    }
    this.portalroleService.updatePortalroleByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id, ModelMap model)
  {
    PortaluserroleQuery urq = new PortaluserroleQuery();
    urq.setRoleId(id);
    this.portaluserroleService.deleteByQuery(urq);

    PortalroleprivilegeQuery rp = new PortalroleprivilegeQuery();
    rp.setRoleId(id);
    this.portalroleprivilegeService.deleteByQuery(rp);
    this.portalroleService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids, ModelMap model)
  {
    PortaluserroleQuery urq = new PortaluserroleQuery();
    for (Long id : ids) {
      urq.setRoleId(id);
      this.portaluserroleService.deleteByQuery(urq);
    }

    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      PortalroleprivilegeQuery rp = new PortalroleprivilegeQuery();
      rp.setRoleId(id);
      this.portalroleprivilegeService.deleteByQuery(rp);
    }
    this.portalroleService.deleteByKeys(list);

    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalroleController
 * JD-Core Version:    0.6.2
 */