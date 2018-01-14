package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portaltimeweb;
import com.leeson.core.query.PortaltimewebQuery;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.PortaltimewebService;
import com.leeson.core.service.PortalwebService;
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
public class PortaltimewebController
{

  @Autowired
  private PortaltimewebService portaltimewebService;

  @Autowired
  private PortalwebService portalwebService;
  private static SimpleDateFormat format = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");

  @RequestMapping({"ist.action"})
  public String page(PortaltimewebQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setNameLike(true);
    query.setDescriptionLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    query.orderbyPos(true);
    query.orderbyId(true);
    Pagination pagination = this.portaltimewebService.getPortaltimewebListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portaltimeweb/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portaltimeweb/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portaltimeweb e, String queryviewdate, ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);

    if ((queryviewdate != null) && (!"".equals(queryviewdate))) {
      try {
        Date viewDate = format.parse(queryviewdate + " 00:00:00");
        e.setViewdate(viewDate);
      } catch (Exception ex) {
        model.addAttribute("msg", "日期格式不正确！");
        queryviewdate = null;
        return "portaltimeweb/save";
      }
    }
    if (e.getViewdateday() != null) {
      if ((e.getViewdateday().intValue() < 1) || (e.getViewdateday().intValue() > 31)) {
        model.addAttribute("msg", "日格式不正确！");
        model.addAttribute("entity", e);
        return "portaltimeweb/save";
      }
    }
    else e.setViewdateday(Integer.valueOf(0));

    if (e.getViewmonth() != null) {
      if ((e.getViewmonth().intValue() < 1) || (e.getViewmonth().intValue() > 12)) {
        model.addAttribute("msg", "月格式不正确！");
        model.addAttribute("entity", e);
        return "portaltimeweb/save";
      }
    }
    else e.setViewmonth(Integer.valueOf(0));

    if (e.getViewweekday() != null) {
      if ((e.getViewweekday().intValue() < 1) || (e.getViewweekday().intValue() > 7)) {
        model.addAttribute("msg", "星期格式不正确！");
        model.addAttribute("entity", e);
        return "portaltimeweb/save";
      }
    }
    else e.setViewweekday(Integer.valueOf(0));

    if (e.getPos() == null) {
      e.setPos(Long.valueOf(0L));
    }
    if (e.getCount() == null) {
      e.setCount(Long.valueOf(0L));
    }

    PortaltimewebQuery q = new PortaltimewebQuery();
    q.setName(e.getName());
    q.setNameLike(false);
    if (this.portaltimewebService.getPortaltimewebList(q).size() > 0) {
      model.addAttribute("msg", "该名称已经存在！");
      model.addAttribute("entity", e);
      return "portaltimeweb/save";
    }

    this.portaltimewebService.addPortaltimeweb(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);

    Portaltimeweb e = this.portaltimewebService.getPortaltimewebByKey(id);
    model.addAttribute("entity", e);
    return "portaltimeweb/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portaltimeweb e, String queryviewdate, ModelMap model)
  {
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);

    if ((queryviewdate != null) && (!"".equals(queryviewdate))) {
      try {
        Date viewDate = format.parse(queryviewdate + " 00:00:00");
        e.setViewdate(viewDate);
      } catch (Exception ex) {
        model.addAttribute("msg", "日期格式不正确！");
        queryviewdate = null;
        return "portaltimeweb/save";
      }
    }
    if (e.getViewdateday() != null) {
      if ((e.getViewdateday().intValue() < 1) || (e.getViewdateday().intValue() > 31)) {
        model.addAttribute("msg", "日格式不正确！");
        model.addAttribute("entity", e);
        return "portaltimeweb/save";
      }
    }
    else e.setViewdateday(Integer.valueOf(0));

    if (e.getViewmonth() != null) {
      if ((e.getViewmonth().intValue() < 1) || (e.getViewmonth().intValue() > 12)) {
        model.addAttribute("msg", "月格式不正确！");
        model.addAttribute("entity", e);
        return "portaltimeweb/save";
      }
    }
    else e.setViewmonth(Integer.valueOf(0));

    if (e.getViewweekday() != null) {
      if ((e.getViewweekday().intValue() < 1) || (e.getViewweekday().intValue() > 7)) {
        model.addAttribute("msg", "星期格式不正确！");
        model.addAttribute("entity", e);
        return "portaltimeweb/save";
      }
    }
    else e.setViewweekday(Integer.valueOf(0));

    if (e.getPos() == null) {
      e.setPos(Long.valueOf(0L));
    }
    if (e.getCount() == null) {
      e.setCount(Long.valueOf(0L));
    }

    PortaltimewebQuery q = new PortaltimewebQuery();
    q.setName(e.getName());
    q.setNameLike(false);
    List timewebs = this.portaltimewebService.getPortaltimewebList(q);
    if ((timewebs != null) && (timewebs.size() > 0) && 
      (((Portaltimeweb)timewebs.get(0)).getId() != e.getId())) {
      model.addAttribute("msg", "该名称已经存在！");
      model.addAttribute("entity", e);
      return "portaltimeweb/save";
    }

    this.portaltimewebService.updatePortaltimewebByKeyAll(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portaltimewebService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portaltimewebService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.portaltimewebService.deleteAll();
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaltimewebController
 * JD-Core Version:    0.6.2
 */