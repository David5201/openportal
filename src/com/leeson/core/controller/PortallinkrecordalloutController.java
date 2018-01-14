package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portallinkrecordallout;
import com.leeson.core.query.PortallinkrecordalloutQuery;
import com.leeson.core.service.PortallinkrecordalloutService;
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
public class PortallinkrecordalloutController
{

  @Autowired
  private PortallinkrecordalloutService portallinkrecordalloutService;

  @RequestMapping({"ist.action"})
  public String page(PortallinkrecordalloutQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    Pagination pagination = this.portallinkrecordalloutService.getPortallinkrecordalloutListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portallinkrecordallout/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    return "portallinkrecordallout/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portallinkrecordallout e)
  {
    this.portallinkrecordalloutService.addPortallinkrecordallout(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portallinkrecordallout e = this.portallinkrecordalloutService.getPortallinkrecordalloutByKey(id);
    model.addAttribute("entity", e);
    return "portallinkrecordallout/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portallinkrecordallout e)
  {
    this.portallinkrecordalloutService.updatePortallinkrecordalloutByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portallinkrecordalloutService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portallinkrecordalloutService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.portallinkrecordalloutService.deleteAll();
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallinkrecordalloutController
 * JD-Core Version:    0.6.2
 */