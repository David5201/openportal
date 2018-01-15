package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.query.PortalaccountgroupQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountgroupService;
import com.leeson.core.service.PortalspeedService;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portalaccountgroupController")
public class PortalaccountgroupController
{

  @Autowired
  private PortalaccountgroupService portalaccountgroupService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalspeedService portalspeedService;

  @Autowired
  private PortalaccountService portalaccountService;
  private static SimpleDateFormat format = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");

  @RequestMapping({"ist.action"})
  public String page(PortalaccountgroupQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setNameLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }

    Pagination pagination = this.portalaccountgroupService
      .getPortalaccountgroupListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    return "portalaccountgroup/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);

    return "portalaccountgroup/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalaccountgroup e, String dateS, ModelMap model)
  {
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);

    if (stringUtils.isBlank(e.getName())) {
      model.addAttribute("msg", "名称不能为空！");
      model.addAttribute("entity", e);
      return "portalaccountgroup/save";
    }

    PortalaccountgroupQuery aq = new PortalaccountgroupQuery();
    aq.setName(e.getName());
    aq.setNameLike(false);
    if (this.portalaccountgroupService.getPortalaccountgroupList(aq).size() > 0) {
      model.addAttribute("msg", "名称已经存在！");
      model.addAttribute("entity", e);
      return "portalaccountgroup/save";
    }

    if (stringUtils.isBlank(e.getState())) {
      e.setState("0");
    }

    if (e.getMaclimitcount() == null) {
      e.setMaclimitcount(Integer.valueOf(1));
    }

    if (e.getTime() == null)
      e.setTime(Long.valueOf(0L));
    else {
      e.setTime(Long.valueOf(e.getTime().longValue() * 1000L * 60L));
    }
    if (e.getOctets() == null)
      e.setOctets(Long.valueOf(0L));
    else {
      e.setOctets(Long.valueOf(e.getOctets().longValue() * 1024L * 1024L));
    }
    if (stringUtils.isBlank(dateS)) {
      e.setDate(new Date());
    } else {
      Date date = new Date();
      try {
        date = format.parse(dateS + " 23:59:59");
      } catch (Exception ex) {
        date = new Date();
      }
      e.setDate(date);
    }

    if (e.getOctetsLimit() == null)
      e.setOctetsLimit(Long.valueOf(0L));
    else {
      e.setOctetsLimit(Long.valueOf(e.getOctetsLimit().longValue() * 1024L * 1024L));
    }

    if (e.getTimeLimit() == null)
      e.setTimeLimit(Long.valueOf(0L));
    else {
      e.setTimeLimit(Long.valueOf(e.getTimeLimit().longValue() * 60L * 1000L));
    }

    this.portalaccountgroupService.addPortalaccountgroup(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portalaccountgroup e = this.portalaccountgroupService
      .getPortalaccountgroupByKey(id);
    model.addAttribute("entity", e);

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);

    return "portalaccountgroup/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalaccountgroup e, String dateS, ModelMap model)
  {
    if (e.getTime() == null)
      e.setTime(Long.valueOf(0L));
    else {
      e.setTime(Long.valueOf(e.getTime().longValue() * 1000L * 60L));
    }
    if (e.getOctets() == null)
      e.setOctets(Long.valueOf(0L));
    else {
      e.setOctets(Long.valueOf(e.getOctets().longValue() * 1024L * 1024L));
    }
    if (stringUtils.isBlank(dateS)) {
      e.setDate(new Date());
    } else {
      Date date = new Date();
      try {
        date = format.parse(dateS + " 23:59:59");
      } catch (Exception ex) {
        date = new Date();
      }
      e.setDate(date);
    }

    if (e.getOctetsLimit() == null)
      e.setOctetsLimit(Long.valueOf(0L));
    else {
      e.setOctetsLimit(Long.valueOf(e.getOctetsLimit().longValue() * 1024L * 1024L));
    }

    if (e.getTimeLimit() == null)
      e.setTimeLimit(Long.valueOf(0L));
    else {
      e.setTimeLimit(Long.valueOf(e.getTimeLimit().longValue() * 60L * 1000L));
    }
    this.portalaccountgroupService.updatePortalaccountgroupByKey(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String editState(@RequestParam Long id, @RequestParam String to)
  {
    Portalaccountgroup account = this.portalaccountgroupService
      .getPortalaccountgroupByKey(id);
    if (to.equals("state")) {
      int state = Integer.valueOf(account.getState()).intValue() + 1;
      if (state >= 5) {
        state = 0;
      }
      account.setState(String.valueOf(state));
    }

    if (to.equals("maclimit")) {
      if (account.getMaclimit().intValue() == 0)
        account.setMaclimit(Integer.valueOf(1));
      else {
        account.setMaclimit(Integer.valueOf(0));
      }

    }

    if (to.equals("auto")) {
      if (account.getAutologin().intValue() == 0)
        account.setAutologin(Integer.valueOf(1));
      else {
        account.setAutologin(Integer.valueOf(0));
      }

    }

    if (to.equals("autoKick")) {
      if (account.getAutoKick().intValue() == 0)
        account.setAutoKick(Integer.valueOf(1));
      else {
        account.setAutoKick(Integer.valueOf(0));
      }

    }

    if (to.equals("clearHaveAll")) {
      if (account.getClearHaveAll().intValue() == 0)
        account.setClearHaveAll(Integer.valueOf(1));
      else {
        account.setClearHaveAll(Integer.valueOf(0));
      }

    }

    if (to.equals("clearHaveLimit")) {
      if (account.getClearHaveLimit().intValue() == 0)
        account.setClearHaveLimit(Integer.valueOf(1));
      else {
        account.setClearHaveLimit(Integer.valueOf(0));
      }

    }

    this.portalaccountgroupService.updatePortalaccountgroupByKey(account);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    if (id.longValue() != 1L) {
      this.portalaccountgroupService.deleteByKey(id);
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
    this.portalaccountgroupService.deleteByKeys(list);

    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalaccountgroupController
 * JD-Core Version:    0.6.2
 */