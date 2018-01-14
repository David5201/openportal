package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalip;
import com.leeson.core.query.PortalipQuery;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.PortalipService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.utils.IPv4Util;
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
public class PortalipController
{

  @Autowired
  private PortalipService portalipService;

  @Autowired
  private PortalwebService portalwebService;

  @RequestMapping({"ist.action"})
  public String page(PortalipQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setNameLike(true);
    query.setDescriptionLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    Pagination pagination = this.portalipService.getPortalipListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalip/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalip/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalip e, ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);

    PortalipQuery q = new PortalipQuery();
    q.setName(e.getName());
    q.setNameLike(false);
    if (this.portalipService.getPortalipList(q).size() > 0) {
      model.addAttribute("msg", "该名称已经存在！");
      model.addAttribute("entity", e);
      return "portalip/save";
    }

    if (!IPv4Util.isIP(e.getStart())) {
      model.addAttribute("msg", "开始IP非法!");
      model.addAttribute("entity", e);
      return "portalip/save";
    }
    if (!IPv4Util.isIP(e.getEnd())) {
      model.addAttribute("msg", "结束IP非法!");
      model.addAttribute("entity", e);
      return "portalip/save";
    }
    try {
      long startL = IPv4Util.ipToLong(e.getStart());
      long endL = IPv4Util.ipToLong(e.getEnd());
      if (startL > endL) {
        model.addAttribute("msg", "IP范围错误!");
        model.addAttribute("entity", e);
        return "portalip/save";
      }

      List<Portalip> ipList = this.portalipService.getPortalipList(new PortalipQuery());
      for (Portalip portalip : ipList) {
        long startH = IPv4Util.ipToLong(portalip.getStart());
        long endH = IPv4Util.ipToLong(portalip.getEnd());
        if ((startL >= startH) && (startL <= endH)) {
          model.addAttribute("msg", "IP范围已存在!");
          model.addAttribute("entity", e);
          return "portalip/save";
        }
        if ((endL >= startH) && (endL <= endH)) {
          model.addAttribute("msg", "IP范围已存在!");
          model.addAttribute("entity", e);
          return "portalip/save";
        }
      }
    } catch (Exception ex) {
      model.addAttribute("msg", "IP地址错误!");
      model.addAttribute("entity", e);
      return "portalip/save";
    }

    this.portalipService.addPortalip(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portalip e = this.portalipService.getPortalipByKey(id);
    model.addAttribute("entity", e);
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalip/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalip e, ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);

    PortalipQuery q = new PortalipQuery();
    q.setName(e.getName());
    q.setNameLike(false);
    List ips = this.portalipService.getPortalipList(q);
    if ((ips != null) && (ips.size() > 0) && 
      (((Portalip)ips.get(0)).getId() != e.getId())) {
      model.addAttribute("msg", "该名称已经存在！");
      model.addAttribute("entity", e);
      return "portalip/save";
    }

    if (!IPv4Util.isIP(e.getStart())) {
      model.addAttribute("msg", "开始IP非法!");
      model.addAttribute("entity", e);
      return "portalip/save";
    }
    if (!IPv4Util.isIP(e.getEnd())) {
      model.addAttribute("msg", "结束IP非法!");
      model.addAttribute("entity", e);
      return "portalip/save";
    }
    try {
      long startL = IPv4Util.ipToLong(e.getStart());
      long endL = IPv4Util.ipToLong(e.getEnd());
      if (startL > endL) {
        model.addAttribute("msg", "IP范围错误!");
        model.addAttribute("entity", e);
        return "portalip/save";
      }

      List<Portalip> iplList = this.portalipService.getPortalipList(new PortalipQuery());
      for (Portalip portalip : iplList)
        if (portalip.getId() != e.getId()) {
          long startH = IPv4Util.ipToLong(portalip.getStart());
          long endH = IPv4Util.ipToLong(portalip.getEnd());
          if ((startL >= startH) && (startL <= endH)) {
            model.addAttribute("msg", "IP范围已存在!");
            model.addAttribute("entity", e);
            return "portalip/save";
          }
          if ((endL >= startH) && (endL <= endH)) {
            model.addAttribute("msg", "IP范围已存在!");
            model.addAttribute("entity", e);
            return "portalip/save";
          }
        }
    }
    catch (Exception ex) {
      model.addAttribute("msg", "IP地址错误!");
      model.addAttribute("entity", e);
      return "portalip/save";
    }
    this.portalipService.updatePortalipByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portalipService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portalipService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.portalipService.deleteAll();
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalipController
 * JD-Core Version:    0.6.2
 */