package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portaldepartment;
import com.leeson.core.query.PortaldepartmentQuery;
import com.leeson.core.service.PortaldepartmentService;
import com.leeson.core.utils.DepartmentUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortaldepartmentController
{

  @Autowired
  private PortaldepartmentService portaldepartmentService;

  @RequestMapping({"istTree.action"})
  public String listTree(ModelMap model)
  {
    List departmentList = this.portaldepartmentService.getPortaldepartmentList(new PortaldepartmentQuery());
    model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
    model.addAttribute("departmentList", departmentList);
    return "portaldepartment/listTree";
  }

  @RequestMapping({"ist.action"})
  public String page(PortaldepartmentQuery query, Long parentId, ModelMap model)
  {
    query.setNameLike(true);
    query.setDescriptionLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    if ((parentId != null) && (parentId.longValue() != 0L)) {
      query.setParentId(parentId);
    }

    Pagination pagination = this.portaldepartmentService
      .getPortaldepartmentListWithPage(query);
    List<Portaldepartment> departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());

    if ((parentId != null) && (parentId.longValue() == 0L)) {
      List fd = new ArrayList();
      for (Portaldepartment d : departmentList) {
        if (d.getParentId() == null) {
          fd.add(d);
        }
      }
      pagination.setList(fd);
    }

    model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
    model.addAttribute("departmentList", departmentList);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portaldepartment/list";
  }

  @RequestMapping({"ddV.action"})
  public String addV(Long parentId, ModelMap model)
  {
    if ((parentId != null) && (parentId.longValue() != 0L)) {
      Portaldepartment e = new Portaldepartment();
      e.setParentId(parentId);
      model.addAttribute("entity", e);
    }
    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());
    model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
    model.addAttribute("departmentList", departmentList);
    return "portaldepartment/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portaldepartment e)
  {
    this.portaldepartmentService.addPortaldepartment(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portaldepartment e = this.portaldepartmentService
      .getPortaldepartmentByKey(id);
    List departmentList = this.portaldepartmentService
      .getPortaldepartmentList(new PortaldepartmentQuery());
    model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
    model.addAttribute("departmentList", departmentList);
    model.addAttribute("entity", e);
    return "portaldepartment/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portaldepartment e)
  {
    this.portaldepartmentService.updatePortaldepartmentByKeyAll(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id, ModelMap model)
  {
    this.portaldepartmentService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids, ModelMap model)
  {
    List list = Arrays.asList(ids);

    this.portaldepartmentService.deleteByKeys(list);

    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaldepartmentController
 * JD-Core Version:    0.6.2
 */