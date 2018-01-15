package com.leeson.core.controller;

import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalconfig;
import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.query.PortalweixinwifiQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.service.PortalweixinwifiService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/configController")
public class ConfigController
{

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalbasService portalbasService;

  @Autowired
  private PortalconfigService portalconfigService;

  @Autowired
  private PortalweixinwifiService portalweixinwifiService;

  @RequestMapping({"how.action"})
  public String edit(ModelMap model)
  {
    com.leeson.core.bean.Config e = this.configService.getConfigByKey(Long.valueOf(1L));
    model.addAttribute("entity", e);
    return "config/config";
  }

  @RequestMapping(value={"ave.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(com.leeson.core.bean.Config e, ModelMap model)
  {
    e.setId(Long.valueOf(1L));
    String domain = e.getDomain().trim();
    if (!domain.startsWith("http://")) {
      domain = "http://" + domain;
    }
    while (domain.endsWith("/")) {
      domain = domain.substring(0, domain.length() - 1);
    }
    e.setDomain(domain);
    this.configService.updateConfigByKey(e);
    e = this.configService.getConfigByKey(Long.valueOf(1L));
    model.addAttribute("entity", e);
    model.addAttribute("msg", "全局设置保存成功！！");

    Map configMap = new ConcurrentHashMap();
    configMap.putAll(com.leeson.portal.core.model.Config.getInstance().getConfigMap());
    Iterator iterator = configMap.keySet().iterator();
    Portalbas bas;
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String t = o.toString();
      bas = (Portalbas)com.leeson.portal.core.model.Config.getInstance().getConfigMap().get(t);
      bas.setIsdebug(String.valueOf(e.getIsDebug()));
      com.leeson.portal.core.model.Config.getInstance().getConfigMap().put(t, bas);
    }
    List<Portalbas> bass = this.portalbasService.getPortalbasList(new PortalbasQuery());
    for (Portalbas bas1 : bass) {
      bas1.setIsdebug(String.valueOf(e.getIsDebug()));
      this.portalbasService.updatePortalbasByKey(bas1);
    }
    Portalconfig base = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
    base.setIsdebug(String.valueOf(e.getIsDebug()));
    this.portalconfigService.updatePortalconfigByKey(base);

    List<Portalweixinwifi> weixins = this.portalweixinwifiService.getPortalweixinwifiList(new PortalweixinwifiQuery());
    for (Portalweixinwifi weixin : weixins) {
      weixin.setDomain(e.getDomain());
      this.portalweixinwifiService.updatePortalweixinwifiByKey(weixin);
    }
    return "config/config";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.ConfigController
 * JD-Core Version:    0.6.2
 */