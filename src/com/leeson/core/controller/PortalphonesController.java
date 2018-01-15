package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalphones;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.PortaldepartmentQuery;
import com.leeson.core.query.PortalphonesQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.PortaldepartmentService;
import com.leeson.core.service.PortalphonesService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.utils.DepartmentUtils;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portalphonesController")
public class PortalphonesController
{

  @Autowired
  private PortalphonesService portalphonesService;

  @Autowired
  private PortaluserService portaluserService;

  @Autowired
  private PortaldepartmentService portaldepartmentService;

  @RequestMapping({"ist.action"})
  public String page(PortalphonesQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List users = this.portaluserService
      .getPortaluserList(new PortaluserQuery());
    model.addAttribute("users", users);
    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());
    model.addAttribute("departmentList", departmentList);

    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    if (!"admin".equals(user.getLoginName())) {
      query.setUid(Long.valueOf(uid));
    }

    query.setNameLike(true);
    query.setDescriptionLike(true);
    query.setPhoneLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    if (stringUtils.isBlank(query.getPhone())) {
      query.setPhone(null);
    }
    Pagination pagination = this.portalphonesService
      .getPortalphonesListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalphones/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }

    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());
    model.addAttribute("chooseList", 
      DepartmentUtils.getAllDepartments(departmentList));
    return "portalphones/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalphones e, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }

    e.setUid(user.getId());
    e.setDid(user.getDepartmentId());
    this.portalphonesService.addPortalphones(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }

    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());
    model.addAttribute("chooseList", 
      DepartmentUtils.getAllDepartments(departmentList));

    Portalphones e = this.portalphonesService.getPortalphonesByKey(id);
    long uid = user.getId().longValue();
    if (!"admin".equals(user.getLoginName())) {
      long suid = e.getUid().longValue();
      if (suid != uid) {
        return "redirect:ist.action";
      }
    }
    model.addAttribute("entity", e);
    return "portalphones/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalphones e, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }

    Portalphones s = this.portalphonesService.getPortalphonesByKey(e.getId());
    if (s != null) {
      long uid = user.getId().longValue();
      if (!"admin".equals(user.getLoginName())) {
        long suid = s.getUid().longValue();
        if (suid != uid) {
          return "redirect:ist.action";
        }
        e.setDid(user.getDepartmentId());
      }
      else if (uid == s.getUid().longValue()) {
        e.setDid(user.getDepartmentId());
      }

      this.portalphonesService.updatePortalphonesByKey(e);
    }
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }

    Portalphones s = this.portalphonesService.getPortalphonesByKey(id);
    if (s != null) {
      long uid = user.getId().longValue();
      if (!"admin".equals(user.getLoginName())) {
        long suid = s.getUid().longValue();
        if (suid == uid)
          this.portalphonesService.deleteByKey(id);
      }
      else {
        this.portalphonesService.deleteByKey(id);
      }
    }
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }

    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      Portalphones s = this.portalphonesService.getPortalphonesByKey(id);
      if (s != null) {
        long uid = user.getId().longValue();
        if (!"admin".equals(user.getLoginName())) {
          long suid = s.getUid().longValue();
          if (suid == uid)
            this.portalphonesService.deleteByKey(id);
        }
        else {
          this.portalphonesService.deleteByKey(id);
        }
      }
    }
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }

    long uid = user.getId().longValue();
    PortalphonesQuery query = new PortalphonesQuery();
    if (!"admin".equals(user.getLoginName())) {
      query.setUid(Long.valueOf(uid));
    }
    this.portalphonesService.deleteByQuery(query);
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalphonesController
 * JD-Core Version:    0.6.2
 */