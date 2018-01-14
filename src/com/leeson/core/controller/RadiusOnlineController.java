package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.RadiusOnlineInfo;
import com.leeson.radius.core.RadiusCOA;
import com.leeson.radius.core.model.RadiusOnlineMap;
import com.leeson.radius.core.utils.DoRecord;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RadiusOnlineController
{
  private static DecimalFormat df = new DecimalFormat(".##");

  @RequestMapping({"ist.action"})
  public String pageOnline(String nasnameQ, String ipQ, String loginNameQ, String macQ, Integer Page, ModelMap model)
    throws ParseException
  {
    int count = 0;
    Iterator it = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (it.hasNext()) {
      Object o = it.next();
      String acctSessionId = o.toString();
      String[] loginInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      String ip = loginInfo[2];
      String mac = loginInfo[3];
      String username = loginInfo[4];
      String nasname = loginInfo[16];
      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(nasnameQ)) && 
        (!nasname.contains(nasnameQ))) {
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

      if ((stringUtils.isNotBlank(macQ)) && 
        (!mac.contains(macQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(nasnameQ)) {
        nasnameQ = null;
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
      if (isIt.booleanValue()) {
        count++;
      }
    }

    List onlineInfos = new ArrayList();
    if ((Page == null) || (Page.intValue() <= 0)) {
      Page = Integer.valueOf(1);
    }
    Integer id = Integer.valueOf(1);
    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      RadiusOnlineInfo onlineInfo = new RadiusOnlineInfo();
      Object o = iterator.next();
      String acctSessionId = o.toString();
      String[] loginInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      String ip = loginInfo[2];
      String mac = loginInfo[3];
      String username = loginInfo[4];
      String nasname = loginInfo[16];
      Boolean isIt = Boolean.valueOf(true);
      if ((stringUtils.isNotBlank(nasnameQ)) && 
        (!nasname.contains(nasnameQ))) {
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

      if ((stringUtils.isNotBlank(macQ)) && 
        (!mac.contains(macQ))) {
        isIt = Boolean.valueOf(false);
      }

      if (stringUtils.isBlank(nasnameQ)) {
        nasnameQ = null;
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
      if (isIt.booleanValue())
      {
        if (id.intValue() > (Page.intValue() - 1) * 10) {
          String inS = "0 M";
          String outS = "0 M";
          String allS = "0 M";
          String octetsS = "0 M";
          try {
            double in = Double.valueOf(loginInfo[11]).doubleValue();
            double out = Double.valueOf(loginInfo[12]).doubleValue();
            double all = Double.valueOf(loginInfo[7]).doubleValue();
            double octets = in + out;
            in /= 1048576.0D;
            out /= 1048576.0D;
            all /= 1048576.0D;
            octets /= 1048576.0D;
            inS = df.format(in);
            outS = df.format(out);
            allS = df.format(all);
            octetsS = df.format(octets);
            if (inS.startsWith(".")) {
              inS = "0" + inS;
            }
            if (outS.startsWith(".")) {
              outS = "0" + outS;
            }
            if (allS.startsWith(".")) {
              allS = "0" + allS;
            }
            if (octetsS.startsWith(".")) {
              octetsS = "0" + octetsS;
            }
            inS = inS + "M";
            outS = outS + "M";
            allS = allS + "M";
            octetsS = octetsS + " M";
          }
          catch (Exception localException) {
          }
          onlineInfo.setNasIP(loginInfo[0]);
          onlineInfo.setIp(loginInfo[1]);
          onlineInfo.setUserIP(ip);
          onlineInfo.setCallingStationId(mac);
          onlineInfo.setName(username);
          onlineInfo.setSharedSecret(loginInfo[5]);
          onlineInfo.setSessionTime(loginInfo[6]);
          onlineInfo.setOctets(allS);
          onlineInfo.setClientType(loginInfo[8]);
          onlineInfo.setStartDate(loginInfo[9]);
          onlineInfo.setCostTime(loginInfo[10]);
          onlineInfo.setInS(inS);
          onlineInfo.setOutS(outS);
          onlineInfo.setCostOctets(octetsS);
          onlineInfo.setAcctSessionId(acctSessionId);
          onlineInfo.setState(loginInfo[15]);
          onlineInfo.setUpdateDate(loginInfo[14]);
          onlineInfo.setNasname(loginInfo[16]);

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
    model.addAttribute("nasnameQ", nasnameQ);
    return "radiusOnline/list";
  }

  @RequestMapping({"ick.action"})
  public String kick(@RequestParam String acctSessionId)
  {
    RadiusCOA.req_COA(
      (String[])RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().get(acctSessionId), "Radius Admin Kick COA");

    return "redirect:ist.action";
  }

  @RequestMapping(value={"icks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String kicks(@RequestParam String[] acctSessionIds)
  {
    for (String acctSessionId : acctSessionIds) {
      RadiusCOA.req_COA(
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId), "Radius Admin Kick COA");
    }

    return "redirect:ist.action";
  }

  @RequestMapping(value={"icks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String kicks()
  {
    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String acctSessionId = o.toString();
      RadiusCOA.req_COA(
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId), "Radius Admin Kick COA");
    }

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eleteOnline.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deleteOnline()
  {
    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String acctSessionId = o.toString();
      DoRecord.coreMethod(acctSessionId, "Radius Admin Delete");
    }
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiusOnlineController
 * JD-Core Version:    0.6.2
 */