package com.leeson.core.controller;

import com.leeson.core.bean.Zsqhdapi;
import com.leeson.core.service.ZsqhdapiService;
import com.leeson.portal.core.model.AccountAPIMap;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ZsqhdapiController
{

  @Autowired
  private ZsqhdapiService zsqhdapiService;

  @RequestMapping({"onfig.action"})
  public String edit(ModelMap model)
  {
    Zsqhdapi e = this.zsqhdapiService.getZsqhdapiByKey(Long.valueOf(1L));
    model.addAttribute("entity", e);
    return "zsqhdAPI/config";
  }

  @RequestMapping(value={"ave.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Zsqhdapi e, ModelMap model)
  {
    e.setId(Long.valueOf(1L));
    this.zsqhdapiService.updateZsqhdapiByKey(e);
    Zsqhdapi api = this.zsqhdapiService.getZsqhdapiByKey(Long.valueOf(1L));
    model.addAttribute("entity", api);

    String url = "";
    String state = "";
    String publicurl = "";
    String publicstate = "";
    String autourl = "";
    String autostate = "";

    if (api != null) {
      url = api.getUrl();
      state = String.valueOf(api.getState());
      publicurl = api.getPublicurl();
      publicstate = String.valueOf(api.getPublicstate());
      autourl = api.getAutourl();
      autostate = String.valueOf(api.getAutostate());
    }

    AccountAPIMap.getInstance().getAccountAPIMap().put("url", url);
    AccountAPIMap.getInstance().getAccountAPIMap().put("state", state);
    AccountAPIMap.getInstance().getAccountAPIMap().put("publicurl", publicurl);
    AccountAPIMap.getInstance().getAccountAPIMap().put("publicstate", publicstate);
    AccountAPIMap.getInstance().getAccountAPIMap().put("autourl", autourl);
    AccountAPIMap.getInstance().getAccountAPIMap().put("autostate", autostate);

    model.addAttribute("msg", "保存成功！！");
    return "zsqhdAPI/config";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.ZsqhdapiController
 * JD-Core Version:    0.6.2
 */