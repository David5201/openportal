package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.Portalweixinwifi;
import com.leeson.core.query.PortalweixinwifiQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalweixinwifiService;
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
public class PortalweixinwifiController
{
  private static SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  private PortalweixinwifiService portalweixinwifiService;

  @Autowired
  private ConfigService configService;

  @RequestMapping({"dit"})
  public String show(@RequestParam Long id, ModelMap model) { Portalweixinwifi e = this.portalweixinwifiService
      .getPortalweixinwifiByKey(id);
    Portalweixinwifi temp = this.portalweixinwifiService
      .getPortalweixinwifiByKey(Long.valueOf(1L));
    String domain = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
    Long outTime = temp.getOutTime();
    model.addAttribute("domain", domain);
    model.addAttribute("outTime", outTime);
    model.addAttribute("entity", e);
    if (id.longValue() == 1L) {
      model.addAttribute("isTemp", Integer.valueOf(1));
    }
    return "portalweixinwifi/save";
  }

  @RequestMapping(value={"dit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalweixinwifi e, ModelMap model)
  {
    Portalweixinwifi temp = this.portalweixinwifiService
      .getPortalweixinwifiByKey(Long.valueOf(1L));
    String domainT = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
    Long outTimeT = temp.getOutTime();
    model.addAttribute("domain", domainT);
    model.addAttribute("outTime", outTimeT);

    e.setAppId(e.getAppId().trim());
    e.setBasip(e.getBasip().trim());
    e.setSecretKey(e.getSecretKey().trim());
    e.setShopId(e.getShopId().trim());
    e.setSsid(e.getSsid().trim());

    Integer count = this.portalweixinwifiService.getPortalweixinwifiCount(new PortalweixinwifiQuery());
    if ((count != null) && 
      (count.intValue() > getAP())) {
      model.addAttribute("msg", "系统未授权，只能添加1个设置！!");
      model.addAttribute("entity", e);
      return "portalweixinwifi/save";
    }

    if ((stringUtils.isBlank(e.getBasip())) || (stringUtils.isBlank(e.getAppId())) || (stringUtils.isBlank(e.getDomain())) || (stringUtils.isBlank(e.getSecretKey())) || (stringUtils.isBlank(e.getShopId())) || (stringUtils.isBlank(e.getSsid()))) {
      model.addAttribute("msg", "所有信息不能为空！！");
      model.addAttribute("entity", e);
      return "portalweixinwifi/save";
    }
    PortalweixinwifiQuery q = new PortalweixinwifiQuery();
    q.setSsid(e.getSsid());
    q.setSsidLike(false);
    q.setBasip(e.getBasip());
    q.setBasipLike(false);
    List wxwfs = this.portalweixinwifiService.getPortalweixinwifiList(q);
    if ((wxwfs.size() > 0) && 
      (((Portalweixinwifi)wxwfs.get(0)).getId() != e.getId())) {
      model.addAttribute("msg", "该BasIP下此SSID已经存在！");
      model.addAttribute("entity", e);
      return "portalweixinwifi/save";
    }

    e.setDomain(domainT);
    if ((e.getOutTime() == null) || (e.getOutTime().longValue() < 10L)) {
      e.setOutTime(Long.valueOf(10L));
    }

    this.portalweixinwifiService.updatePortalweixinwifiByKey(e);

    Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
    model.addAttribute("pagination", pagination);
    return "portalweixinwifi/list";
  }

  @RequestMapping({"ist.action"})
  public String page(PortalweixinwifiQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setSsidLike(true);
    query.setAppIdLike(true);
    query.setShopIdLike(true);
    query.setBasipLike(true);
    Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalweixinwifi/list";
  }

  @RequestMapping({"dd.action"})
  public String addV(ModelMap model)
  {
    Portalweixinwifi temp = this.portalweixinwifiService
      .getPortalweixinwifiByKey(Long.valueOf(1L));
    String domain = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
    Long outTime = temp.getOutTime();
    model.addAttribute("domain", domain);
    model.addAttribute("outTime", outTime);
    return "portalweixinwifi/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalweixinwifi e, ModelMap model)
  {
    Portalweixinwifi temp = this.portalweixinwifiService
      .getPortalweixinwifiByKey(Long.valueOf(1L));
    String domainT = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain();
    Long outTimeT = temp.getOutTime();
    model.addAttribute("domain", domainT);
    model.addAttribute("outTime", outTimeT);

    e.setAppId(e.getAppId().trim());
    e.setBasip(e.getBasip().trim());
    e.setSecretKey(e.getSecretKey().trim());
    e.setShopId(e.getShopId().trim());
    e.setSsid(e.getSsid().trim());

    Integer count = this.portalweixinwifiService.getPortalweixinwifiCount(new PortalweixinwifiQuery());
    if ((count != null) && 
      (count.intValue() >= getAP())) {
      model.addAttribute("msg", "系统未授权，只能添加1个设置！!");
      model.addAttribute("entity", e);
      return "portalweixinwifi/save";
    }

    if ((stringUtils.isBlank(e.getBasip())) || (stringUtils.isBlank(e.getAppId())) || (stringUtils.isBlank(e.getDomain())) || (stringUtils.isBlank(e.getSecretKey())) || (stringUtils.isBlank(e.getShopId())) || (stringUtils.isBlank(e.getSsid()))) {
      model.addAttribute("msg", "所有信息不能为空！！");
      model.addAttribute("entity", e);
      return "portalweixinwifi/save";
    }
    PortalweixinwifiQuery q = new PortalweixinwifiQuery();
    q.setSsid(e.getSsid());
    q.setSsidLike(false);
    q.setBasip(e.getBasip());
    q.setBasipLike(false);
    if (this.portalweixinwifiService.getPortalweixinwifiList(q).size() > 0) {
      model.addAttribute("msg", "该BasIP下此SSID已经存在！");
      model.addAttribute("entity", e);
      return "portalweixinwifi/save";
    }

    e.setDomain(domainT);
    if ((e.getOutTime() == null) || (e.getOutTime().longValue() < 10L)) {
      e.setOutTime(Long.valueOf(10L));
    }
    this.portalweixinwifiService.addPortalweixinwifi(e);

    Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
    model.addAttribute("pagination", pagination);
    return "portalweixinwifi/list";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id, ModelMap model)
  {
    if (id.longValue() != 1L) {
      this.portalweixinwifiService.deleteByKey(id);
    }
    Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
    model.addAttribute("pagination", pagination);
    return "portalweixinwifi/list";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids, ModelMap model)
  {
    List list = Arrays.asList(ids);
    if (list.contains(Long.valueOf(1L))) {
      list.remove(Long.valueOf(1L));
    }
    this.portalweixinwifiService.deleteByKeys(list);

    Pagination pagination = this.portalweixinwifiService.getPortalweixinwifiListWithPage(new PortalweixinwifiQuery());
    model.addAttribute("pagination", pagination);
    return "portalweixinwifi/list";
  }

  public static int getAP()
  {
    int apCount = 1;
    Date nowDate = new Date();
    String nowString = fs.format(nowDate);
    try {
      nowDate = fs.parse(nowString);
    } catch (ParseException e) {
      nowDate = new Date();
    }
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = 
      (String)Wz3ofg0r225avuerr.getInstance()
      .getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = 
      (String[])WySlot15gasa.getInstance()
      .getAmkbYQX3eQjuwtnxpbjYgQGZOr()
      .get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
      Date saveDate;
      try {
        saveDate = fs.parse(toDateString);
      }
      catch (ParseException e)
      {
        saveDate = nowDate;
      }
      if (nowDate.getTime() < saveDate.getTime()) {
        apCount = Integer.valueOf(MxMzIyRDMzMzAy[1]).intValue();
      }
    }
    return apCount;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalweixinwifiController
 * JD-Core Version:    0.6.2
 */