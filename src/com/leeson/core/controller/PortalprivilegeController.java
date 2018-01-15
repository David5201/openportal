package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalprivilege;
import com.leeson.core.query.PortalprivilegeQuery;
import com.leeson.core.service.PortalprivilegeService;
import com.leeson.core.utils.InitListener;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portalprivilegeController")
public class PortalprivilegeController
{

  @Autowired
  private PortalprivilegeService portalprivilegeService;

  @RequestMapping({"istTree.action"})
  public String listTree(ModelMap model, HttpServletRequest request)
  {
    return "portalprivilege/listTree";
  }

  @RequestMapping({"ist.action"})
  public String page(PortalprivilegeQuery query, Long parentId, ModelMap model) {
    query.setNameLike(true);
    query.setUrlLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getUrl())) {
      query.setUrl(null);
    }
    if ((parentId != null) && (parentId.longValue() != 0L)) {
      query.setParentId(parentId);
    }

    Pagination pagination = this.portalprivilegeService.getPortalprivilegeListWithPage(query);
    if ((parentId != null) && (parentId.longValue() == 0L)) {
      pagination.setList(this.portalprivilegeService.findTopList());
    }
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalprivilege/list";
  }

  @RequestMapping({"ddV.action"})
  public String addV(Long parentId, ModelMap model)
  {
    if ((parentId != null) && (parentId.longValue() != 0L)) {
      Portalprivilege e = new Portalprivilege();
      e.setParentId(parentId);
      model.addAttribute("entity", e);
    }
    return "portalprivilege/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalprivilege e, HttpServletRequest request)
  {
    PortalprivilegeQuery pq = new PortalprivilegeQuery();
    if (e.getParentId() == null) {
      int a = this.portalprivilegeService.findTopList().size();
      e.setPosition(Integer.valueOf(a + 1));
    } else {
      pq.setParentId(e.getParentId());
      pq.orderbyPosition(false);
      List tt = this.portalprivilegeService.getPortalprivilegeList(pq);
      if (tt.size() == 0)
        e.setPosition(Integer.valueOf(e.getParentId().intValue() * 10 + 1));
      else {
        e.setPosition(Integer.valueOf(((Portalprivilege)tt.get(0)).getPosition().intValue() + 1));
      }
    }
    this.portalprivilegeService.addPortalprivilege(e);

    ServletContext servletContext = request.getServletContext();
    InitListener.initData(servletContext, this.portalprivilegeService);
    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portalprivilege e = this.portalprivilegeService.getPortalprivilegeByKey(id);
    model.addAttribute("entity", e);
    return "portalprivilege/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalprivilege e, HttpServletRequest request)
  {
    Portalprivilege et = this.portalprivilegeService.getPortalprivilegeByKey(e.getId());
    e.setPosition(et.getPosition());
    this.portalprivilegeService.updatePortalprivilegeByKeyAll(e);
    ServletContext servletContext = request.getServletContext();
    InitListener.initData(servletContext, this.portalprivilegeService);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id, HttpServletRequest request)
  {
    this.portalprivilegeService.deleteByKey(id);

    ServletContext servletContext = request.getServletContext();
    InitListener.initData(servletContext, this.portalprivilegeService);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
  {
    List list = Arrays.asList(ids);
    this.portalprivilegeService.deleteByKeys(list);

    ServletContext servletContext = request.getServletContext();
    InitListener.initData(servletContext, this.portalprivilegeService);
    return "redirect:ist.action";
  }

  @RequestMapping({"oveUp.action"})
  public String moveUp(@RequestParam Long id, HttpServletRequest request)
  {
    this.portalprivilegeService.editPosUP(id);
    ServletContext servletContext = request.getServletContext();
    InitListener.initData(servletContext, this.portalprivilegeService);
    return "portalprivilege/listTree";
  }

  @RequestMapping({"oveDown.action"})
  public String moveDown(@RequestParam Long id, HttpServletRequest request) {
    this.portalprivilegeService.editPosDown(id);
    ServletContext servletContext = request.getServletContext();
    InitListener.initData(servletContext, this.portalprivilegeService);
    return "portalprivilege/listTree";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalprivilegeController
 * JD-Core Version:    0.6.2
 */