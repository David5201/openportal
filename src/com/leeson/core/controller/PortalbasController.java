package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portalconfig;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalbasauthView;
import com.leeson.core.query.PortalconfigQuery;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.service.PortalwebService;
import com.leeson.portal.core.model.WySlot15gasa;
import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalbasController
{

  @Autowired
  private PortalbasService portalbasService;

  @Autowired
  private PortalconfigService portalconfigService;

  @Autowired
  private PortalbasauthService portalbasauthService;

  @Autowired
  private PortalwebService portalwebService;

  @Autowired
  private ConfigService configService;
  private static SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");

  @RequestMapping({"ist.action"})
  public String page(PortalbasQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    if (stringUtils.isBlank(query.getBasname())) {
      query.setBasname(null);
    }
    if (stringUtils.isBlank(query.getBasIp())) {
      query.setBasIp(null);
    }
    query.setBasnameLike(true);
    query.setBasIpLike(true);

    Pagination pagination = this.portalbasService.getPortalbasListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalbas/list";
  }

  @RequestMapping({"dd.action"})
  public String addV(ModelMap model)
  {
    Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
    model.addAttribute("entity", basic);
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalbas/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalbas e, ModelMap model, PortalbasauthView bav)
  {
    if ((stringUtils.isBlank(e.getBasname())) || (stringUtils.isBlank(e.getBasIp()))) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas名称 和 BasIP 不能为空！!");
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    PortalbasQuery bqip = new PortalbasQuery();
    bqip.setBasIp(e.getBasIp());
    bqip.setBasIpLike(false);
    if (this.portalbasService.getPortalbasList(bqip).size() > 0) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      e.setBasIp("");
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas IP 已经存在！!");
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    PortalconfigQuery cq = new PortalconfigQuery();
    cq.setBasIp(e.getBasIp());
    cq.setBasIpLike(false);
    if (this.portalconfigService.getPortalconfigList(cq).size() > 0) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      e.setBasIp("");
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas IP 已经存在！!");
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    PortalbasQuery bq = new PortalbasQuery();
    bq.setBasname(e.getBasname());
    bq.setBasnameLike(false);
    if (this.portalbasService.getPortalbasList(bq).size() > 0) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      e.setBasname("");
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas名称 已经存在！!");
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }
    if ((e.getBas().equals("2")) || (e.getBas().equals("4"))) {
      e.setAuthType("0");
      e.setPortalVer("1");
    }
    if (e.getAuthInterface() == null) {
      e.setAuthInterface("");
    }

    int basCount = 0;
    Date nowDate = new Date();
    String nowString = fs.format(nowDate);
    try {
      nowDate = fs.parse(nowString);
    } catch (ParseException err) {
      nowDate = new Date();
    }
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    Boolean notCan = Boolean.valueOf(false);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
      Date saveDate;
      try {
        saveDate = fs.parse(toDateString);
      }
      catch (ParseException err)
      {
        saveDate = nowDate;
      }
      if (nowDate.getTime() < saveDate.getTime())
        basCount = Integer.valueOf(MxMzIyRDMzMzAy[4]).intValue();
      else
        notCan = Boolean.valueOf(true);
    } else
    {
      notCan = Boolean.valueOf(true);
    }
    Integer count = this.portalbasService.getPortalbasCount(new PortalbasQuery());
    if (notCan.booleanValue()) {
      if ((count != null) && 
        (count.intValue() >= 2)) {
        Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
        model.addAttribute("entity", basic);
        model.addAttribute("bas", e);
        model.addAttribute("msg", "系统未授权，只能添加2个多BAS设置！!");
        List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
        model.addAttribute("webs", webs);
        return "portalbas/save";
      }
    }
    else if ((count != null) && 
      (count.intValue() >= basCount)) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      model.addAttribute("bas", e);
      model.addAttribute("msg", "BAS授权数已经达到上限！!");
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
    if (e.getLateAuthTime() == null) {
      e.setLateAuthTime(Long.valueOf(10L));
    }
    this.portalbasService.addPortalbas(e);
    SetAuth(e, bav);

    com.leeson.portal.core.model.Config.getInstance().getConfigMap().put(e.getBasIp(), e);
    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
    model.addAttribute("entity", basic);

    Portalbas e = this.portalbasService.getPortalbasByKey(id);
    e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
    model.addAttribute("bas", e);
    PortalbasauthQuery baq = new PortalbasauthQuery();
    baq.setBasid(e.getId());
    List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
    for (Portalbasauth ba : basauths) {
      model.addAttribute("username" + ba.getType(), ba.getUsername());
      model.addAttribute("password" + ba.getType(), ba.getPassword());
      model.addAttribute("url" + ba.getType(), ba.getUrl());
      model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
    }
    List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
    model.addAttribute("webs", webs);
    return "portalbas/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalbas e, ModelMap model, PortalbasauthView bav)
  {
    if ((stringUtils.isBlank(e.getBasname())) || (stringUtils.isBlank(e.getBasIp()))) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas名称 和 BasIP 不能为空！!");

      PortalbasauthQuery baq = new PortalbasauthQuery();
      baq.setBasid(e.getId());
      List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
      for (Portalbasauth ba : basauths) {
        model.addAttribute("username" + ba.getType(), ba.getUsername());
        model.addAttribute("password" + ba.getType(), ba.getPassword());
        model.addAttribute("url" + ba.getType(), ba.getUrl());
        model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
      }
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    PortalbasQuery bqip = new PortalbasQuery();
    bqip.setBasIp(e.getBasIp());
    bqip.setBasIpLike(false);
    List bips = this.portalbasService.getPortalbasList(bqip);
    if ((bips.size() > 0) && 
      (((Portalbas)bips.get(0)).getId() != e.getId())) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      e.setBasIp("");
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas IP 已经存在！!");

      PortalbasauthQuery baq = new PortalbasauthQuery();
      baq.setBasid(e.getId());
      List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
      for (Portalbasauth ba : basauths) {
        model.addAttribute("username" + ba.getType(), ba.getUsername());
        model.addAttribute("password" + ba.getType(), ba.getPassword());
        model.addAttribute("url" + ba.getType(), ba.getUrl());
        model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
      }
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    PortalconfigQuery cq = new PortalconfigQuery();
    cq.setBasIp(e.getBasIp());
    cq.setBasIpLike(false);
    if (this.portalconfigService.getPortalconfigList(cq).size() > 0) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      e.setBasIp("");
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas IP 已经存在，基础配置就是该Bas IP ！!");
      PortalbasauthQuery baq = new PortalbasauthQuery();
      baq.setBasid(e.getId());
      List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
      for (Portalbasauth ba : basauths) {
        model.addAttribute("username" + ba.getType(), ba.getUsername());
        model.addAttribute("password" + ba.getType(), ba.getPassword());
        model.addAttribute("url" + ba.getType(), ba.getUrl());
        model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
      }
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    PortalbasQuery bq = new PortalbasQuery();
    bq.setBasname(e.getBasname());
    bq.setBasnameLike(false);
    List bs = this.portalbasService.getPortalbasList(bq);
    if ((bs.size() > 0) && 
      (((Portalbas)bs.get(0)).getId() != e.getId())) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      e.setBasname("");
      model.addAttribute("bas", e);
      model.addAttribute("msg", "Bas名称 已经存在！!");
      PortalbasauthQuery baq = new PortalbasauthQuery();
      baq.setBasid(e.getId());
      List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
      for (Portalbasauth ba : basauths) {
        model.addAttribute("username" + ba.getType(), ba.getUsername());
        model.addAttribute("password" + ba.getType(), ba.getPassword());
        model.addAttribute("url" + ba.getType(), ba.getUrl());
        model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
      }
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    if ((e.getBas().equals("2")) || (e.getBas().equals("4"))) {
      e.setAuthType("0");
      e.setPortalVer("1");
    }
    if (e.getAuthInterface() == null) {
      e.setAuthInterface("");
    }

    int basCount = 0;
    Date nowDate = new Date();
    String nowString = fs.format(nowDate);
    try {
      nowDate = fs.parse(nowString);
    } catch (ParseException err) {
      nowDate = new Date();
    }
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    Boolean notCan = Boolean.valueOf(false);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
      Date saveDate;
      try {
        saveDate = fs.parse(toDateString);
      }
      catch (ParseException err)
      {
        saveDate = nowDate;
      }
      if (nowDate.getTime() < saveDate.getTime())
        basCount = Integer.valueOf(MxMzIyRDMzMzAy[4]).intValue();
      else
        notCan = Boolean.valueOf(true);
    } else
    {
      notCan = Boolean.valueOf(true);
    }
    Integer count = this.portalbasService.getPortalbasCount(new PortalbasQuery());
    if (notCan.booleanValue()) {
      if ((count != null) && 
        (count.intValue() > 2)) {
        Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
        model.addAttribute("entity", basic);
        model.addAttribute("bas", e);
        model.addAttribute("msg", "系统未授权，只能添加2个多BAS设置！!");
        List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
        model.addAttribute("webs", webs);
        return "portalbas/save";
      }
    }
    else if ((count != null) && 
      (count.intValue() > basCount)) {
      Portalconfig basic = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
      model.addAttribute("entity", basic);
      model.addAttribute("bas", e);
      model.addAttribute("msg", "BAS授权数已经达到上限！!");
      List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
      model.addAttribute("webs", webs);
      return "portalbas/save";
    }

    Portalbas oldbas = this.portalbasService.getPortalbasByKey(e.getId());
    com.leeson.portal.core.model.Config.getInstance().getConfigMap().remove(oldbas.getBasIp());
    e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
    if (e.getLateAuthTime() == null) {
      e.setLateAuthTime(Long.valueOf(10L));
    }
    this.portalbasService.updatePortalbasByKeyAll(e);
    SetAuth(e, bav);

    com.leeson.portal.core.model.Config.getInstance().getConfigMap().put(e.getBasIp(), e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    Portalbas oldbas = this.portalbasService.getPortalbasByKey(id);
    com.leeson.portal.core.model.Config.getInstance().getConfigMap().remove(oldbas.getBasIp());
    this.portalbasService.deleteByKey(id);
    DelAuth(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      Portalbas oldbas = this.portalbasService.getPortalbasByKey(id);
      com.leeson.portal.core.model.Config.getInstance().getConfigMap().remove(oldbas.getBasIp());
      DelAuth(id);
    }
    this.portalbasService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  private void DelAuth(Long basId)
  {
    PortalbasauthQuery bas = new PortalbasauthQuery();
    bas.setBasid(basId);
    this.portalbasauthService.deleteByQuery(bas);
  }

  private void SetAuth(Portalbas e, PortalbasauthView bav) {
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
    baq.setBasid(e.getId());

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
      ba.setBasid(e.getId());
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
      ba.setBasid(e.getId());
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
      ba.setBasid(e.getId());
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
      ba.setBasid(e.getId());
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
      ba.setBasid(e.getId());
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
      ba.setBasid(e.getId());
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
      ba.setBasid(e.getId());
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
      ba.setBasid(e.getId());
      ba.setBasip(e.getBasIp());
      ba.setUsername(bav.getUsername7());
      ba.setPassword(bav.getPassword7());
      ba.setUrl(bav.getUrl7());
      ba.setType(Integer.valueOf(7));
      ba.setSessiontime(Long.valueOf(bav.getTime7().longValue() * 60000L));
      this.portalbasauthService.addPortalbasauth(ba);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalbasController
 * JD-Core Version:    0.6.2
 */