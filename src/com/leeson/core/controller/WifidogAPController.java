package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.portal.core.model.WifiDogAPMap;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wifidogAPController")
public class WifidogAPController
{
  private static WifiDogAPMap onlineMap = WifiDogAPMap.getInstance();

  @RequestMapping({"ist.action"})
  public String pageOnline(String ipQ, String gwidQ, Integer Page, ModelMap model)
    throws ParseException
  {
    HashMap APMap = new HashMap();
    APMap.putAll(onlineMap.getWifiDogAPMap());

    int count = 0;
    Iterator it = APMap.keySet().iterator();
    while (it.hasNext()) {
      Object o = it.next();
      String gwid = o.toString();
      String[] APInfo = (String[])APMap.get(gwid);
      String ip = APInfo[4];

      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(ipQ)) && 
        (!ip.contains(ipQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(gwidQ)) && 
        (!gwid.contains(gwidQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(ipQ)) {
        ipQ = null;
      }
      if (stringUtils.isBlank(gwidQ)) {
        gwidQ = null;
      }
      if (isIt.booleanValue()) {
        count++;
      }
    }

    List ApInfos = new ArrayList();
    if ((Page == null) || (Page.intValue() <= 0)) {
      Page = Integer.valueOf(1);
    }
    Integer id = Integer.valueOf(1);
    Iterator iterator = APMap.keySet().iterator();
    while (iterator.hasNext()) {
      ApInfo apInfo = new ApInfo();
      Object o = iterator.next();
      String gwid = o.toString();
      String[] APInfo = (String[])APMap.get(gwid);
      String ip = APInfo[4];

      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(ipQ)) && 
        (!ip.contains(ipQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(gwidQ)) && 
        (!gwid.contains(gwidQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(ipQ)) {
        ipQ = null;
      }
      if (stringUtils.isBlank(gwidQ)) {
        gwidQ = null;
      }
      if (isIt.booleanValue())
      {
        if (id.intValue() > (Page.intValue() - 1) * 10) {
          apInfo.setId(id);
          apInfo.setGw_id(gwid);
          apInfo.setIp(ip);
          apInfo.setTime(APInfo[5]);
          apInfo.setSys_uptime(APInfo[0]);
          apInfo.setSys_memfree(APInfo[1]);
          apInfo.setSys_load(APInfo[2]);
          apInfo.setWifidog_uptime(APInfo[3]);
          ApInfos.add(apInfo);
        }

        id = Integer.valueOf(id.intValue() + 1);
        if (id.intValue() > Page.intValue() * 10)
        {
          break;
        }
      }
    }
    Pagination pagination = new Pagination(Page.intValue(), 10, count, ApInfos);
    model.addAttribute("pagination", pagination);
    model.addAttribute("ipQ", ipQ);
    model.addAttribute("gwidQ", gwidQ);
    return "wifidog/listAP";
  }

  @RequestMapping({"elete.action"})
  public String kick(@RequestParam String gwid)
  {
    WifiDogAPMap.getInstance().getWifiDogAPMap().remove(gwid);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String kicks(@RequestParam String[] gwids)
  {
    for (String gwid : gwids) {
      WifiDogAPMap.getInstance().getWifiDogAPMap().remove(gwid);
    }
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String kicks()
  {
    WifiDogAPMap.getInstance().getWifiDogAPMap().clear();
    return "redirect:ist.action";
  }
  public class ApInfo {
    Integer id;
    String gw_id;
    String ip;
    String time;
    String sys_uptime;
    String sys_memfree;
    String sys_load;
    String wifidog_uptime;

    public ApInfo() {
    }
    public Integer getId() {
      return this.id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getGw_id() {
      return this.gw_id;
    }

    public void setGw_id(String gw_id) {
      this.gw_id = gw_id;
    }

    public String getIp() {
      return this.ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    public String getTime() {
      return this.time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public String getSys_uptime() {
      return this.sys_uptime;
    }

    public void setSys_uptime(String sys_uptime) {
      this.sys_uptime = sys_uptime;
    }

    public String getSys_memfree() {
      return this.sys_memfree;
    }

    public void setSys_memfree(String sys_memfree) {
      this.sys_memfree = sys_memfree;
    }

    public String getSys_load() {
      return this.sys_load;
    }

    public void setSys_load(String sys_load) {
      this.sys_load = sys_load;
    }

    public String getWifidog_uptime() {
      return this.wifidog_uptime;
    }

    public void setWifidog_uptime(String wifidog_uptime) {
      this.wifidog_uptime = wifidog_uptime;
    }

    public String toString()
    {
      return "ApInfo [id=" + this.id + ", gw_id=" + this.gw_id + ", ip=" + this.ip + 
        ", time=" + this.time + ", sys_uptime=" + this.sys_uptime + 
        ", sys_memfree=" + this.sys_memfree + ", sys_load=" + this.sys_load + 
        ", wifidog_uptime=" + this.wifidog_uptime + "]";
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.WifidogAPController
 * JD-Core Version:    0.6.2
 */