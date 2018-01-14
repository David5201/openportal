package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.OnlineInfo;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortallogrecordQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.utils.Kick;
import com.leeson.core.utils.kickAllThread;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.ipMap;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PortallogrecordController
{

  @Autowired
  private PortallogrecordService portallogrecordService;

  @Autowired
  private PortalaccountService portalaccountService;
  private static Config config = Config.getInstance();
  private static OnlineMap onlineMap = OnlineMap.getInstance();
  private static SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static DecimalFormat df = new DecimalFormat(".##");

  @RequestMapping({"ist.action"})
  public String page(PortallogrecordQuery query, ModelMap model)
  {
    query.setInfoLike(true);
    if (stringUtils.isBlank(query.getInfo())) {
      query.setInfo(null);
    }
    query.orderbyId(false);
    Pagination pagination = this.portallogrecordService
      .getPortallogrecordListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portallogrecord/list";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portallogrecordService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portallogrecordService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.portallogrecordService.deleteAll();
    return "redirect:ist.action";
  }

  @RequestMapping({"ist.action"})
  public String pageOnline(String ipQ, String loginNameQ, String macQ, String typeQ, String apmacQ, String ssidQ, String agentQ, String basnameQ, String autoQ, String begintime, String endtime, Integer Page, ModelMap model)
    throws ParseException
  {
    int count = 0;
    Iterator it = onlineMap.getOnlineUserMap().keySet().iterator();
    while (it.hasNext()) {
      Object o = it.next();
      String host = o.toString();
      String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
      String username = loginInfo[0];
      String mac = loginInfo[4];

      String type = loginInfo[6];
      String basname = loginInfo[11];
      String ssid = loginInfo[12];
      String apmac = loginInfo[13];
      String auto = loginInfo[14];
      String agent = loginInfo[15];
      if (stringUtils.isBlank(basname)) {
        basname = "base";
      }
      if (stringUtils.isBlank(ssid)) {
        ssid = "unknow";
      }
      if (stringUtils.isBlank(apmac)) {
        apmac = "unknow";
      }
      if (stringUtils.isBlank(agent)) {
        agent = "unknow";
      }

      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(ipQ)) && 
        (!host.contains(ipQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(loginNameQ)) && 
        (!username.contains(loginNameQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(macQ)) && 
        (!mac.contains(macQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(ipQ)) {
        ipQ = null;
      }
      if (stringUtils.isBlank(loginNameQ)) {
        loginNameQ = null;
      }
      if (stringUtils.isBlank(macQ)) {
        macQ = null;
      }

      if ((stringUtils.isNotBlank(typeQ)) && 
        (!type.contains(typeQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(typeQ)) {
        typeQ = null;
      }

      if ((stringUtils.isNotBlank(basnameQ)) && 
        (!basname.contains(basnameQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(basnameQ)) {
        basnameQ = null;
      }

      if ((stringUtils.isNotBlank(ssidQ)) && 
        (!ssid.contains(ssidQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(ssidQ)) {
        ssidQ = null;
      }

      if ((stringUtils.isNotBlank(apmacQ)) && 
        (!apmac.contains(apmacQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(apmacQ)) {
        apmacQ = null;
      }

      if ((stringUtils.isNotBlank(autoQ)) && 
        (!auto.contains(autoQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(autoQ)) {
        autoQ = null;
      }

      if ((stringUtils.isNotBlank(agentQ)) && 
        (!agent.contains(agentQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(agentQ)) {
        agentQ = null;
      }

      String time = loginInfo[3];
      Date loginTime = ThreadLocalDateUtil.parse(time);
      String begintimeQS = null;
      String endtimeQS = null;
      Date begin_time = null;
      Date end_time = null;
      if (stringUtils.isNotBlank(begintime)) {
        begintimeQS = begintime + " 00:00:00";
        try {
          begin_time = inputformat.parse(begintimeQS);
          if (loginTime.getTime() < begin_time.getTime())
            isIt = Boolean.valueOf(false);
        }
        catch (ParseException localParseException) {
        }
      }
      if (stringUtils.isNotBlank(endtime)) {
        endtimeQS = endtime + " 23:59:59";
        try {
          end_time = inputformat.parse(endtimeQS);
          if (loginTime.getTime() > end_time.getTime())
            isIt = Boolean.valueOf(false);
        }
        catch (ParseException localParseException1) {
        }
      }
      if (stringUtils.isBlank(begintime)) {
        begintime = null;
      }
      if (stringUtils.isBlank(endtime)) {
        endtime = null;
      }

      if (isIt.booleanValue()) {
        count++;
      }
    }

    List onlineInfos = new ArrayList();
    if ((Page == null) || (Page.intValue() <= 0)) {
      Page = Integer.valueOf(1);
    }
    Integer id = Integer.valueOf(1);
    Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
    while (iterator.hasNext()) {
      OnlineInfo onlineInfo = new OnlineInfo();
      Object o = iterator.next();
      String host = o.toString();
      String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
      String username = loginInfo[0];
      String time = loginInfo[3];
      String mac = loginInfo[4];

      String inS = "0 M";
      String outS = "0 M";
      String octetsS = "0 M";
      try {
        double in = Double.valueOf(loginInfo[7]).doubleValue();
        double out = Double.valueOf(loginInfo[8]).doubleValue();
        double octets = in + out;
        in /= 1048576.0D;
        out /= 1048576.0D;
        octets /= 1048576.0D;
        inS = df.format(in);
        outS = df.format(out);
        octetsS = df.format(octets);
        if (inS.startsWith(".")) {
          inS = "0" + inS;
        }
        if (outS.startsWith(".")) {
          outS = "0" + outS;
        }
        if (octetsS.startsWith(".")) {
          octetsS = "0" + octetsS;
        }
        inS = inS + " M";
        outS = outS + " M";
        octetsS = octetsS + " M";
      }
      catch (Exception localException) {
      }
      String type = loginInfo[6];
      String basname = loginInfo[11];
      String ssid = loginInfo[12];
      String apmac = loginInfo[13];
      String auto = loginInfo[14];
      String agent = loginInfo[15];
      if (stringUtils.isBlank(basname)) {
        basname = "base";
      }
      if (stringUtils.isBlank(ssid)) {
        ssid = "unknow";
      }
      if (stringUtils.isBlank(apmac)) {
        apmac = "unknow";
      }
      if (stringUtils.isBlank(agent)) {
        agent = "unknow";
      }

      Date loginTime = ThreadLocalDateUtil.parse(time);
      String nowString = ThreadLocalDateUtil.format(new Date());
      Date nowTime = ThreadLocalDateUtil.parse(nowString);
      Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
      costTime = Long.valueOf(costTime.longValue() / 60000L);
      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(ipQ)) && 
        (!host.contains(ipQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(loginNameQ)) && 
        (!username.contains(loginNameQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(macQ)) && 
        (!mac.contains(macQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(ipQ)) {
        ipQ = null;
      }
      if (stringUtils.isBlank(loginNameQ)) {
        loginNameQ = null;
      }
      if (stringUtils.isBlank(macQ)) {
        macQ = null;
      }

      if ((stringUtils.isNotBlank(typeQ)) && 
        (!type.contains(typeQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(typeQ)) {
        typeQ = null;
      }

      if ((stringUtils.isNotBlank(basnameQ)) && 
        (!basname.contains(basnameQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(basnameQ)) {
        basnameQ = null;
      }

      if ((stringUtils.isNotBlank(ssidQ)) && 
        (!ssid.contains(ssidQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(ssidQ)) {
        ssidQ = null;
      }

      if ((stringUtils.isNotBlank(apmacQ)) && 
        (!apmac.contains(apmacQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(apmacQ)) {
        apmacQ = null;
      }

      if ((stringUtils.isNotBlank(autoQ)) && 
        (!auto.contains(autoQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(autoQ)) {
        autoQ = null;
      }

      if ((stringUtils.isNotBlank(agentQ)) && 
        (!agent.contains(agentQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(agentQ)) {
        agentQ = null;
      }

      String begintimeQS = null;
      String endtimeQS = null;
      Date begin_time = null;
      Date end_time = null;
      if (stringUtils.isNotBlank(begintime)) {
        begintimeQS = begintime + " 00:00:00";
        try {
          begin_time = inputformat.parse(begintimeQS);
          if (loginTime.getTime() < begin_time.getTime())
            isIt = Boolean.valueOf(false);
        }
        catch (ParseException localParseException2) {
        }
      }
      if (stringUtils.isNotBlank(endtime)) {
        endtimeQS = endtime + " 23:59:59";
        try {
          end_time = inputformat.parse(endtimeQS);
          if (loginTime.getTime() > end_time.getTime())
            isIt = Boolean.valueOf(false);
        }
        catch (ParseException localParseException3) {
        }
      }
      if (stringUtils.isBlank(begintime)) {
        begintime = null;
      }
      if (stringUtils.isBlank(endtime)) {
        endtime = null;
      }

      if (isIt.booleanValue())
      {
        if (id.intValue() > (Page.intValue() - 1) * 10) {
          onlineInfo.setIp(host);
          onlineInfo.setLoginName(username);
          onlineInfo.setStartDate(loginTime);
          onlineInfo.setTime(costTime);
          onlineInfo.setMac(mac);
          onlineInfo.setInS(inS);
          onlineInfo.setOutS(outS);
          onlineInfo.setOctetsS(octetsS);

          if ("0".equals(type))
            onlineInfo.setType("一键认证");
          else if ("1".equals(type))
            onlineInfo.setType("本地用户");
          else if ("2".equals(type))
            onlineInfo.setType("Radius");
          else if ("3".equals(type))
            onlineInfo.setType("APP认证");
          else if ("4".equals(type))
            onlineInfo.setType("短信认证");
          else if ("5".equals(type))
            onlineInfo.setType("微信认证");
          else if ("6".equals(type))
            onlineInfo.setType("公众号认证");
          else if ("7".equals(type))
            onlineInfo.setType("访客认证");
          else if ("8".equals(type)) {
            onlineInfo.setType("延迟认证");
          }
          onlineInfo.setBasname(basname);
          onlineInfo.setSsid(ssid);
          onlineInfo.setApmac(apmac);
          onlineInfo.setAuto(auto);
          onlineInfo.setAgent(agent);

          if ("1".equals(type)) {
            PortalaccountQuery aq = new PortalaccountQuery();
            aq.setLoginName(username);
            List a = this.portalaccountService
              .getPortalaccountList(aq);
            if ((a != null) && (a.size() == 1)) {
              onlineInfo.setState(((Portalaccount)a.get(0)).getState());
            }
          }
          onlineInfo.setId(id);
          onlineInfos.add(onlineInfo);
        }

        id = Integer.valueOf(id.intValue() + 1);
        if (id.intValue() > Page.intValue() * 10)
        {
          break;
        }
      }
    }
    Pagination pagination = new Pagination(Page.intValue(), 10, count, onlineInfos);
    model.addAttribute("pagination", pagination);
    model.addAttribute("ipQ", ipQ);
    model.addAttribute("loginNameQ", loginNameQ);
    model.addAttribute("macQ", macQ);

    model.addAttribute("typeQ", typeQ);
    model.addAttribute("basnameQ", basnameQ);
    model.addAttribute("ssidQ", ssidQ);
    model.addAttribute("apmacQ", apmacQ);
    model.addAttribute("autoQ", autoQ);
    model.addAttribute("agentQ", agentQ);
    model.addAttribute("begintime", begintime);
    model.addAttribute("endtime", endtime);
    return "portallogrecord/listOnline";
  }

  @RequestMapping({"ick.action"})
  public String kick(@RequestParam String ip)
  {
    Kick.kickUserByAdmin(ip);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"icks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String kicks(@RequestParam String[] ips)
  {
    for (String ip : ips) {
      Kick.kickUserByAdmin(ip);
    }
    return "redirect:ist.action";
  }

  @RequestMapping(value={"icks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String kicks()
  {
    Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String host = o.toString();

      kickAllThread.kickAll(host);
    }
    return "redirect:ist.action";
  }

  @ResponseBody
  @RequestMapping({"/kickAll.action"})
  public Map<Integer, String> kickAll() {
    AutoLoginMacMap.getInstance().getAutoLoginMacMap().clear();
    AutoLoginMap.getInstance().getAutoLoginMap().clear();
    int count = 1;
    HashMap kickMap = new HashMap();
    Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String host = o.toString();
      kickMap.put(Integer.valueOf(count), host);
      Kick.kickUserAllAPI(host);
      count++;
    }
    return kickMap;
  }
  @ResponseBody
  @RequestMapping({"/kickIP.action"})
  public Map<String, Integer> kickIP(String ip) {
    HashMap kickMap = new HashMap();
    Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
    while (iterator.hasNext())
      try {
        Object o = iterator.next();
        String host = o.toString();
        String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
        String mac = loginInfo[4];
        String[] ips = host.split(":");
        String uip = ips[0];
        if (uip.equals(ip)) {
          Kick.kickUserIPAPI(host);
          kickMap.put(uip, Integer.valueOf(1));
          AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(mac);
          AutoLoginMap.getInstance().getAutoLoginMap().remove(mac);
        }
      }
      catch (Exception localException) {
      }
    return kickMap;
  }

  @RequestMapping(value={"eleteOnline.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deleteOnline()
  {
    Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String host = o.toString();
      Kick.deleteOnline(host);
    }
    return "redirect:ist.action";
  }

  @RequestMapping({"nLock.action"})
  public String unLock(ModelMap model) throws ParseException
  {
    ipMap.getInstance().getIpmap().clear();
    model.addAttribute("msg", "已经解锁所有IP地址！！！");

    String authInterface = ((Portalbas)config.getConfigMap().get("")).getAuthInterface();
    List onlineInfos = new ArrayList();
    Integer id = Integer.valueOf(1);
    Iterator iterator = onlineMap.getOnlineUserMap().keySet().iterator();
    while (iterator.hasNext()) {
      OnlineInfo onlineInfo = new OnlineInfo();
      Object o = iterator.next();
      String host = o.toString();
      String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(host);
      String username = loginInfo[0];
      String time = loginInfo[3];
      Date loginTime = ThreadLocalDateUtil.parse(time);
      String nowString = ThreadLocalDateUtil.format(new Date());
      Date nowTime = ThreadLocalDateUtil.parse(nowString);
      Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
      costTime = Long.valueOf(costTime.longValue() / 60000L);
      Boolean isIt = Boolean.valueOf(true);

      if (isIt.booleanValue()) {
        onlineInfo.setIp(host);
        onlineInfo.setLoginName(username);
        onlineInfo.setStartDate(loginTime);
        onlineInfo.setTime(costTime);
        if (authInterface.contains("1")) {
          PortalaccountQuery aq = new PortalaccountQuery();
          aq.setLoginName(username);
          List a = this.portalaccountService
            .getPortalaccountList(aq);
          if ((a != null) && (a.size() == 1)) {
            onlineInfo.setState(((Portalaccount)a.get(0)).getState());
          }
        }
        onlineInfo.setId(id);
        onlineInfos.add(onlineInfo);
        id = Integer.valueOf(id.intValue() + 1);
        if (id.intValue() > 10)
        {
          break;
        }
      }
    }
    Pagination pagination = new Pagination(1, 10, onlineInfos.size(), 
      onlineInfos);
    model.addAttribute("pagination", pagination);

    return "portallogrecord/listOnline";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallogrecordController
 * JD-Core Version:    0.6.2
 */