package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.utils.WifidogKick;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

@Controller
@RequestMapping("/wifidogOnlineController")
public class WifidogOnlineController
{

  @Autowired
  private PortalaccountService portalaccountService;
  private static Config config = Config.getInstance();
  private static WifiDogOnlineMap onlineMap = WifiDogOnlineMap.getInstance();

  @RequestMapping({"ist.action"})
  public String pageOnline(String ipQ, String gwidQ, String loginNameQ, Integer Page, ModelMap model)
    throws ParseException
  {
    HashMap onlineUserMap = new HashMap();
    onlineUserMap.putAll(onlineMap.getWifiDogOnlineMap());
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    int count = 0;
    Iterator it = onlineUserMap.keySet().iterator();
    while (it.hasNext()) {
      Object o = it.next();
      String token = o.toString();
      String[] loginInfo = (String[])onlineUserMap.get(token);
      String username = loginInfo[0];
      String ip = loginInfo[4];
      String gwid = loginInfo[6];
      if (gwid == null) {
        gwid = "";
      }

      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(gwidQ)) && 
        (!gwid.contains(gwidQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(ipQ)) && 
        (!ip.contains(ipQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(loginNameQ)) && 
        (!username.contains(loginNameQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(gwidQ)) {
        gwidQ = null;
      }
      if (stringUtils.isBlank(ipQ)) {
        ipQ = null;
      }
      if (stringUtils.isBlank(loginNameQ)) {
        loginNameQ = null;
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
    Iterator iterator = onlineUserMap.keySet().iterator();
    while (iterator.hasNext()) {
      OnlineInfo onlineInfo = new OnlineInfo();
      Object o = iterator.next();
      String token = o.toString();
      String[] loginInfo = (String[])onlineUserMap.get(token);
      String username = loginInfo[0];
      String time = loginInfo[3];
      String ip = loginInfo[4];
      String gwid = loginInfo[6];
      if (gwid == null) {
        gwid = "";
      }

      Date loginTime = format.parse(time);
      String nowString = format.format(new Date());
      Date nowTime = format.parse(nowString);
      Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
      costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(gwidQ)) && 
        (!gwid.contains(gwidQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(ipQ)) && 
        (!ip.contains(ipQ))) {
        isIt = Boolean.valueOf(false);
      }

      if ((stringUtils.isNotBlank(loginNameQ)) && 
        (!username.contains(loginNameQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(gwidQ)) {
        gwidQ = null;
      }
      if (stringUtils.isBlank(ipQ)) {
        ipQ = null;
      }
      if (stringUtils.isBlank(loginNameQ)) {
        loginNameQ = null;
      }
      if (isIt.booleanValue())
      {
        if (id.intValue() > (Page.intValue() - 1) * 10) {
          onlineInfo.setId(id);
          onlineInfo.setGwid(gwid);
          onlineInfo.setToken(token);
          onlineInfo.setIp(ip);
          onlineInfo.setLoginName(username);
          onlineInfo.setStartDate(loginTime);
          onlineInfo.setTime(costTime);
          onlineInfo.setMac(loginInfo[5]);
          onlineInfo.setIncoming(loginInfo[8]);
          onlineInfo.setOutgoing(loginInfo[9]);
          String authInterface = ((Portalbas)config.getConfigMap().get("")).getAuthInterface();
          if (authInterface.contains("1")) {
            PortalaccountQuery aq = new PortalaccountQuery();
            aq.setLoginName(username);
            List a = this.portalaccountService
              .getPortalaccountList(aq);
            if ((a != null) && (a.size() == 1)) {
              onlineInfo.setState(((Portalaccount)a.get(0)).getState());
            }
          }
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
    model.addAttribute("gwidQ", gwidQ);
    return "wifidog/listOnline";
  }

  @RequestMapping({"ick.action"})
  public String kick(@RequestParam String token)
  {
    WifidogKick.kickUser(token);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"icks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String kicks(@RequestParam String[] tokens)
  {
    for (String token : tokens) {
      WifidogKick.kickUser(token);
    }
    return "redirect:ist.action";
  }

  @RequestMapping(value={"icks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String kicks()
  {
    HashMap OnlineUserMap = new HashMap();
    OnlineUserMap.putAll(onlineMap.getWifiDogOnlineMap());
    Iterator iterator = OnlineUserMap.keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String token = o.toString();
      WifidogKick.kickUser(token);
    }
    return "redirect:ist.action";
  }

  public class OnlineInfo
  {
    Integer id;
    String token;
    String mac;
    String gwid;
    String incoming;
    String outgoing;
    String loginName;
    Date startDate;
    String ip;
    Long time;
    String state;

    public OnlineInfo()
    {
    }

    public Integer getId()
    {
      return this.id;
    }
    public void setId(Integer id) {
      this.id = id;
    }
    public String getToken() {
      return this.token;
    }
    public void setToken(String token) {
      this.token = token;
    }
    public String getMac() {
      return this.mac;
    }
    public void setMac(String mac) {
      this.mac = mac;
    }
    public String getGwid() {
      return this.gwid;
    }
    public void setGwid(String gwid) {
      this.gwid = gwid;
    }
    public String getIncoming() {
      return this.incoming;
    }
    public void setIncoming(String incoming) {
      this.incoming = incoming;
    }
    public String getOutgoing() {
      return this.outgoing;
    }
    public void setOutgoing(String outgoing) {
      this.outgoing = outgoing;
    }
    public String getLoginName() {
      return this.loginName;
    }
    public void setLoginName(String loginName) {
      this.loginName = loginName;
    }
    public Date getStartDate() {
      return this.startDate;
    }
    public void setStartDate(Date startDate) {
      this.startDate = startDate;
    }
    public String getIp() {
      return this.ip;
    }
    public void setIp(String ip) {
      this.ip = ip;
    }
    public Long getTime() {
      return this.time;
    }
    public void setTime(Long time) {
      this.time = time;
    }
    public String getState() {
      return this.state;
    }
    public void setState(String state) {
      this.state = state;
    }

    public String toString() {
      return "OnlineInfo [id=" + this.id + ", token=" + this.token + ", mac=" + this.mac + 
        ", gwid=" + this.gwid + ", incoming=" + this.incoming + 
        ", outgoing=" + this.outgoing + ", loginName=" + this.loginName + 
        ", startDate=" + this.startDate + ", ip=" + this.ip + ", time=" + 
        this.time + ", state=" + this.state + "]";
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.WifidogOnlineController
 * JD-Core Version:    0.6.2
 */