package com.leeson.core.controller;

import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.query.PortalonlinelimitQuery;
import com.leeson.core.service.PortalonlinelimitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalonlinelimitController
{

  @Autowired
  private PortalonlinelimitService portalonlinelimitService;

  @RequestMapping({"how.action"})
  public String show(ModelMap model)
  {
    List es = this.portalonlinelimitService.getPortalonlinelimitList(new PortalonlinelimitQuery());
    model.addAttribute("pagination", es);
    return "portalonlinelimit/list";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portalonlinelimit e = this.portalonlinelimitService.getPortalonlinelimitByKey(id);
    if (e.getState().intValue() == 0)
      e.setState(Integer.valueOf(1));
    else {
      e.setState(Integer.valueOf(0));
    }
    this.portalonlinelimitService.updatePortalonlinelimitByKey(e);
    return "redirect:how.action";
  }

  @RequestMapping({"ave.action"})
  public String save(@RequestParam Long id, ModelMap model)
  {
    Portalonlinelimit e = this.portalonlinelimitService.getPortalonlinelimitByKey(id);
    int time = (int)(e.getTime().longValue() / 60000L);
    model.addAttribute("entity", e);
    model.addAttribute("time", Integer.valueOf(time));
    return "portalonlinelimit/config";
  }

  @RequestMapping(value={"ave.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String save(Portalonlinelimit e, ModelMap model)
  {
    e.setTime(Long.valueOf(e.getTime().longValue() * 60000L));
    this.portalonlinelimitService.updatePortalonlinelimitByKey(e);

    List es = this.portalonlinelimitService.getPortalonlinelimitList(new PortalonlinelimitQuery());
    model.addAttribute("pagination", es);
    model.addAttribute("msg", "保存成功！！");
    return "portalonlinelimit/list";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalonlinelimitController
 * JD-Core Version:    0.6.2
 */