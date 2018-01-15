package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Radiuslinkrecordallout;
import com.leeson.core.query.RadiuslinkrecordalloutQuery;
import com.leeson.core.service.RadiuslinkrecordalloutService;
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
@RequestMapping("/radiuslinkrecordalloutController")
public class RadiuslinkrecordalloutController
{

  @Autowired
  private RadiuslinkrecordalloutService radiuslinkrecordalloutService;

  @RequestMapping({"ist.action"})
  public String page(RadiuslinkrecordalloutQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    Pagination pagination = this.radiuslinkrecordalloutService.getRadiuslinkrecordalloutListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "radiuslinkrecordallout/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    return "radiuslinkrecordallout/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Radiuslinkrecordallout e)
  {
    this.radiuslinkrecordalloutService.addRadiuslinkrecordallout(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Radiuslinkrecordallout e = this.radiuslinkrecordalloutService.getRadiuslinkrecordalloutByKey(id);
    model.addAttribute("entity", e);
    return "radiuslinkrecordallout/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Radiuslinkrecordallout e)
  {
    this.radiuslinkrecordalloutService.updateRadiuslinkrecordalloutByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.radiuslinkrecordalloutService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.radiuslinkrecordalloutService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.radiuslinkrecordalloutService.deleteAll();
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiuslinkrecordalloutController
 * JD-Core Version:    0.6.2
 */