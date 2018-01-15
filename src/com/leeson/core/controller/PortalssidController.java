package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.query.PortalssidQuery;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.PortalssidService;
import com.leeson.core.service.PortalwebService;
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
@RequestMapping("/portalssidController")
public class PortalssidController
{

  @Autowired
  private PortalssidService portalssidService;

  @Autowired
  private PortalwebService portalwebService;

  @RequestMapping({"ist.action"})
  public String page(PortalssidQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setSsidLike(true);
    query.setAddressLike(true);
    query.setNameLike(true);
    if (stringUtils.isBlank(query.getSsid())) {
      query.setSsid(null);
    }
    if (stringUtils.isBlank(query.getAddress())) {
      query.setAddress(null);
    }
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    Pagination pagination = this.portalssidService.getPortalssidListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalssid/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalssid/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalssid e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getSsid())) {
      model.addAttribute("msg", "SSID不能为空！！");
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalssid/save";
    }
    PortalssidQuery apq = new PortalssidQuery();
    apq.setSsid(e.getSsid());
    if (this.portalssidService.getPortalssidList(apq).size() > 0) {
      model.addAttribute("msg", "该SSID已经存在！");
      e.setSsid(null);
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalssid/save";
    }
    this.portalssidService.addPortalssid(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portalssid e = this.portalssidService.getPortalssidByKey(id);
    model.addAttribute("entity", e);
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalssid/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalssid e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getSsid())) {
      model.addAttribute("msg", "SSID不能为空！！");
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalssid/save";
    }
    PortalssidQuery apq = new PortalssidQuery();
    apq.setSsid(e.getSsid());
    List aps = this.portalssidService.getPortalssidList(apq);
    if ((aps.size() > 0) && 
      (((Portalssid)aps.get(0)).getId() != e.getId())) {
      model.addAttribute("msg", "该SSID已经存在！");
      e.setSsid(null);
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalssid/save";
    }

    this.portalssidService.updatePortalssidByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portalssidService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portalssidService.deleteByKeys(list);
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalssidController
 * JD-Core Version:    0.6.2
 */