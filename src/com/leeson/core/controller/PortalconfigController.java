package com.leeson.core.controller;

import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portalconfig;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalbasauthView;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.service.PortalwebService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalconfigController
{
  private static com.leeson.portal.core.model.Config base_cfg = com.leeson.portal.core.model.Config.getInstance();

  @Autowired
  private PortalconfigService portalconfigService;

  @Autowired
  private PortalbasauthService portalbasauthService;

  @Autowired
  private PortalbasService portalbasService;

  @Autowired
  private PortalwebService portalwebService;

  @Autowired
  private ConfigService configService;

  @RequestMapping({"how"})
  public String show(ModelMap model) { Portalconfig e = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
    e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
    model.addAttribute("entity", e);
    PortalbasauthQuery baq = new PortalbasauthQuery();
    baq.setBasid(Long.valueOf(0L));
    List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
    for (Portalbasauth ba : basauths) {
      model.addAttribute("username" + ba.getType(), ba.getUsername());
      model.addAttribute("password" + ba.getType(), ba.getPassword());
      model.addAttribute("url" + ba.getType(), ba.getUrl());
      model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
    }

    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalconfig/config";
  }

  @RequestMapping(value={"ave"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalconfig e, ModelMap model, PortalbasauthView bav)
  {
    e.setId(Long.valueOf(1L));
    if ((e.getBas().equals("2")) || (e.getBas().equals("4"))) {
      e.setAuthType("0");
      e.setPortalVer("1");
    }

    PortalbasQuery bq = new PortalbasQuery();
    bq.setBasIp(e.getBasIp());
    bq.setBasIpLike(false);
    if (this.portalbasService.getPortalbasList(bq).size() > 0) {
      model.addAttribute("entity", e);
      model.addAttribute("msg", "Bas IP 已经存在！!");
      PortalbasauthQuery baq = new PortalbasauthQuery();
      baq.setBasid(Long.valueOf(0L));
      List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
      for (Portalbasauth ba : basauths) {
        model.addAttribute("username" + ba.getType(), ba.getUsername());
        model.addAttribute("password" + ba.getType(), ba.getPassword());
        model.addAttribute("url" + ba.getType(), ba.getUrl());
        model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
      }
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalconfig/config";
    }

    if (e.getAuthInterface() == null) {
      e.setAuthInterface("");
    }
    Portalconfig oldConfig = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
    base_cfg.getConfigMap().remove(oldConfig.getBasIp());
    e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
    if (e.getLateAuthTime() == null) {
      e.setLateAuthTime(Long.valueOf(10L));
    }
    this.portalconfigService.updatePortalconfigByKeyAll(e);

    SetAuth(e, bav);

    e = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
    InitConfig(e);
    model.addAttribute("entity", e);
    model.addAttribute("msg", "设备对接设置信息更新成功！！");
    PortalbasauthQuery baq = new PortalbasauthQuery();
    baq.setBasid(Long.valueOf(0L));
    List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
    for (Portalbasauth ba : basauths) {
      model.addAttribute("username" + ba.getType(), ba.getUsername());
      model.addAttribute("password" + ba.getType(), ba.getPassword());
      model.addAttribute("url" + ba.getType(), ba.getUrl());
      model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
    }
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalconfig/config";
  }

  private void SetAuth(Portalconfig e, PortalbasauthView bav) {
    if (bav.getTime0() == null) {
      bav.setTime0(Long.valueOf(0L));
    }
    if (bav.getTime1() == null) {
      bav.setTime1(Long.valueOf(0L));
    }
    if (bav.getTime2() == null) {
      bav.setTime2(Long.valueOf(0L));
    }
    if (bav.getTime3() == null) {
      bav.setTime3(Long.valueOf(0L));
    }
    if (bav.getTime4() == null) {
      bav.setTime4(Long.valueOf(0L));
    }
    if (bav.getTime5() == null) {
      bav.setTime5(Long.valueOf(0L));
    }
    if (bav.getTime6() == null) {
      bav.setTime6(Long.valueOf(0L));
    }
    if (bav.getTime7() == null) {
      bav.setTime7(Long.valueOf(0L));
    }

    PortalbasauthQuery baq = new PortalbasauthQuery();
    baq.setBasid(Long.valueOf(0L));

    baq.setType(Integer.valueOf(0));
    List basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername0());
      ba.setPassword(bav.getPassword0());
      ba.setUrl(bav.getUrl0());
      ba.setType(Integer.valueOf(0));
      ba.setSessiontime(Long.valueOf(bav.getTime0().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername0());
      ba.setPassword(bav.getPassword0());
      ba.setUrl(bav.getUrl0());
      ba.setType(Integer.valueOf(0));
      ba.setSessiontime(Long.valueOf(bav.getTime0().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }

    baq.setType(Integer.valueOf(1));
    basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername1());
      ba.setPassword(bav.getPassword1());
      ba.setUrl(bav.getUrl1());
      ba.setSessiontime(Long.valueOf(bav.getTime1().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername1());
      ba.setPassword(bav.getPassword1());
      ba.setUrl(bav.getUrl1());
      ba.setType(Integer.valueOf(1));
      ba.setSessiontime(Long.valueOf(bav.getTime1().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }

    baq.setType(Integer.valueOf(2));
    basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername2());
      ba.setPassword(bav.getPassword2());
      ba.setUrl(bav.getUrl2());
      ba.setSessiontime(Long.valueOf(bav.getTime2().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername2());
      ba.setPassword(bav.getPassword2());
      ba.setUrl(bav.getUrl2());
      ba.setType(Integer.valueOf(2));
      ba.setSessiontime(Long.valueOf(bav.getTime2().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }

    baq.setType(Integer.valueOf(3));
    basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername3());
      ba.setPassword(bav.getPassword3());
      ba.setUrl(bav.getUrl3());
      ba.setSessiontime(Long.valueOf(bav.getTime3().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername3());
      ba.setPassword(bav.getPassword3());
      ba.setUrl(bav.getUrl3());
      ba.setType(Integer.valueOf(3));
      ba.setSessiontime(Long.valueOf(bav.getTime3().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }

    baq.setType(Integer.valueOf(4));
    basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername4());
      ba.setPassword(bav.getPassword4());
      ba.setUrl(bav.getUrl4());
      ba.setSessiontime(Long.valueOf(bav.getTime4().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername4());
      ba.setPassword(bav.getPassword4());
      ba.setUrl(bav.getUrl4());
      ba.setType(Integer.valueOf(4));
      ba.setSessiontime(Long.valueOf(bav.getTime4().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }

    baq.setType(Integer.valueOf(5));
    basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername5());
      ba.setPassword(bav.getPassword5());
      ba.setUrl(bav.getUrl5());
      ba.setSessiontime(Long.valueOf(bav.getTime5().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername5());
      ba.setPassword(bav.getPassword5());
      ba.setUrl(bav.getUrl5());
      ba.setType(Integer.valueOf(5));
      ba.setSessiontime(Long.valueOf(bav.getTime5().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }

    baq.setType(Integer.valueOf(6));
    basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername6());
      ba.setPassword(bav.getPassword6());
      ba.setUrl(bav.getUrl6());
      ba.setSessiontime(Long.valueOf(bav.getTime6().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername6());
      ba.setPassword(bav.getPassword6());
      ba.setUrl(bav.getUrl6());
      ba.setType(Integer.valueOf(6));
      ba.setSessiontime(Long.valueOf(bav.getTime6().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }

    baq.setType(Integer.valueOf(7));
    basauths = this.portalbasauthService.getPortalbasauthList(baq);
    if (basauths.size() > 0) {
      Portalbasauth ba = (Portalbasauth)basauths.get(0);
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername7());
      ba.setPassword(bav.getPassword7());
      ba.setUrl(bav.getUrl7());
      ba.setSessiontime(Long.valueOf(bav.getTime7().longValue() * 60000L));
      this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
    } else {
      Portalbasauth ba = new Portalbasauth();
      ba.setBasid(Long.valueOf(0L));
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername7());
      ba.setPassword(bav.getPassword7());
      ba.setUrl(bav.getUrl7());
      ba.setType(Integer.valueOf(7));
      ba.setSessiontime(Long.valueOf(bav.getTime7().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }
  }

  private void InitConfig(Portalconfig config)
  {
    Portalbas cfg = new Portalbas();

    cfg.setBasIp(config.getBasIp());
    cfg.setBasPort(config.getBasPort());
    cfg.setSharedSecret(config.getSharedSecret());
    cfg.setAuthType(config.getAuthType());
    cfg.setTimeoutSec(config.getTimeoutSec());
    cfg.setPortalVer(config.getPortalVer());
    cfg.setBas(config.getBas());
    cfg.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
    cfg.setIsOut(config.getIsOut());
    cfg.setIsPortalCheck(config.getIsPortalCheck());
    cfg.setBasUser(config.getBasUser());
    cfg.setBasPwd(config.getBasPwd());
    cfg.setAuthInterface(config.getAuthInterface());
    cfg.setWeb(config.getWeb());
    cfg.setBasname(config.getBasname());
    cfg.setIsComputer(config.getIsComputer());
    cfg.setLateAuth(config.getLateAuth());
    cfg.setLateAuthTime(config.getLateAuthTime());
    cfg.setIsNtf(config.getIsNtf());

    base_cfg.getConfigMap().put(config.getBasIp(), cfg);
    base_cfg.getConfigMap().put("", cfg);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalconfigController
 * JD-Core Version:    0.6.2
 */