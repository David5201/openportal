package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalspeed;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.PortalspeedService;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portalspeedController")
public class PortalspeedController
{

  @Autowired
  private PortalspeedService portalspeedService;

  @RequestMapping({"ist.action"})
  public String page(PortalspeedQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    Pagination pagination = this.portalspeedService.getPortalspeedListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalspeed/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    return "portalspeed/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalspeed e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getName())) {
      model.addAttribute("msg", "请填写名称！");
      model.addAttribute("entity", e);
      return "portalspeed/save";
    }
    this.portalspeedService.addPortalspeed(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portalspeed e = this.portalspeedService.getPortalspeedByKey(id);
    model.addAttribute("entity", e);
    return "portalspeed/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalspeed e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getName())) {
      model.addAttribute("msg", "请填写名称！");
      model.addAttribute("entity", e);
      return "portalspeed/save";
    }
    this.portalspeedService.updatePortalspeedByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    if (id.longValue() != 1L) {
      this.portalspeedService.deleteByKey(id);
    }

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    if (list.contains(Long.valueOf(1L))) {
      list.remove(Long.valueOf(1L));
    }
    this.portalspeedService.deleteByKeys(list);

    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalspeedController
 * JD-Core Version:    0.6.2
 */