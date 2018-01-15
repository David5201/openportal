package com.leeson.core.controller;

import com.leeson.core.bean.Payapi;
import com.leeson.core.service.PayapiService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payController")
public class PayController
{

  @Autowired
  private PayapiService payapiService;

  @RequestMapping({"onfig"})
  public String show(ModelMap model)
  {
    Payapi e = this.payapiService.getPayapiByKey(Long.valueOf(1L));
    model.addAttribute("entity", e);
    return "portalpay/config";
  }

  @RequestMapping(value={"ave"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String save(Payapi e, ModelMap model, HttpServletRequest request)
  {
    e.setId(Long.valueOf(1L));
    this.payapiService.updatePayapiByKeyAll(e);
    model.addAttribute("msg", "保存成功！!");
    model.addAttribute("entity", e);
    return "portalpay/config";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PayController
 * JD-Core Version:    0.6.2
 */