package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalap;
import com.leeson.core.query.PortalapQuery;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.PortalapService;
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
@RequestMapping("/portalapController")
public class PortalapController
{

  @Autowired
  private PortalapService portalapService;

  @Autowired
  private PortalwebService portalwebService;

  @RequestMapping({"ist.action"})
  public String page(PortalapQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setIpLike(true);
    query.setMacLike(true);
    query.setAddressLike(true);
    query.setNameLike(true);
    if (stringUtils.isBlank(query.getIp())) {
      query.setIp(null);
    }
    if (stringUtils.isBlank(query.getMac())) {
      query.setMac(null);
    }
    if (stringUtils.isBlank(query.getAddress())) {
      query.setAddress(null);
    }
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    Pagination pagination = this.portalapService.getPortalapListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalap/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalap/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalap e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getMac())) {
      model.addAttribute("msg", "MAC不能为空！！");
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalap/save";
    }
    PortalapQuery apq = new PortalapQuery();
    apq.setMac(e.getMac());
    if (this.portalapService.getPortalapList(apq).size() > 0) {
      model.addAttribute("msg", "该MAC的AP已经存在！");
      e.setMac(null);
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalap/save";
    }
    this.portalapService.addPortalap(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portalap e = this.portalapService.getPortalapByKey(id);
    model.addAttribute("entity", e);
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalap/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalap e, ModelMap model)
  {
    if (stringUtils.isBlank(e.getMac())) {
      model.addAttribute("msg", "MAC不能为空！！");
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalap/save";
    }
    PortalapQuery apq = new PortalapQuery();
    apq.setMac(e.getMac());
    List aps = this.portalapService.getPortalapList(apq);
    if ((aps.size() > 0) && 
      (((Portalap)aps.get(0)).getId() != e.getId())) {
      model.addAttribute("msg", "该MAC的AP已经存在！");
      e.setMac(null);
      model.addAttribute("entity", e);
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalap/save";
    }

    this.portalapService.updatePortalapByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portalapService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portalapService.deleteByKeys(list);
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalapController
 * JD-Core Version:    0.6.2
 */