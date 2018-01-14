package com.leeson.core.controller;

import com.leeson.core.bean.Portalurlparameter;
import com.leeson.core.service.PortalurlparameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalurlparameterController
{

  @Autowired
  private PortalurlparameterService portalurlparameterService;

  @RequestMapping({"how.action"})
  public String editV(ModelMap model)
  {
    Portalurlparameter e = this.portalurlparameterService.getPortalurlparameterByKey(Long.valueOf(1L));
    model.addAttribute("entity", e);
    return "portalurlparameter/config";
  }

  @RequestMapping(value={"ave.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalurlparameter e, ModelMap model)
  {
    e.setId(Long.valueOf(1L));
    this.portalurlparameterService.updatePortalurlparameterByKey(e);
    model.addAttribute("entity", e);
    model.addAttribute("msg", "设置更新成功！！");
    return "portalurlparameter/config";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalurlparameterController
 * JD-Core Version:    0.6.2
 */