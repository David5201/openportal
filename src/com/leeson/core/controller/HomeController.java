package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.common.utils.sysinfo.MonitorInfoBean;
import com.leeson.common.utils.sysinfo.MonitorService;
import com.leeson.core.bean.Portalap;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalapQuery;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.query.PortallinkrecordallQuery;
import com.leeson.core.query.PortallogrecordQuery;
import com.leeson.core.query.PortalmessageQuery;
import com.leeson.core.query.PortalorderQuery;
import com.leeson.core.query.PortalssidQuery;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.query.PortalweixinwifiQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalapService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortallinkrecordallService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.service.PortalmessageService;
import com.leeson.core.service.PortalorderService;
import com.leeson.core.service.PortalssidService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.service.PortalweixinwifiService;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import com.leeson.portal.core.model.WySlot15gasa;
import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
import com.leeson.portal.core.utils.AaHpl8Ha9bNPen9OLddV;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController
{

  @Autowired
  private PortaluserService portaluserService;

  @Autowired
  private PortalmessageService portalmessageService;

  @Autowired
  private PortallogrecordService portallogrecordService;

  @Autowired
  private PortalaccountService portalaccountService;

  @Autowired
  private PortalweixinwifiService portalweixinwifiService;

  @Autowired
  private PortalbasService portalbasService;

  @Autowired
  private PortallinkrecordallService portallinkrecordallService;

  @Autowired
  private PortalorderService portalorderService;

  @Autowired
  private PortalssidService portalssidService;

  @Autowired
  private PortalapService portalapService;

  @Autowired
  private PortalwebService portalwebService;

  @Autowired
  private ConfigService configService;
  private static DecimalFormat df = new DecimalFormat(".##");

  @RequestMapping({"howCount.action"})
  public String showCount(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    linkCount(model);
    moneyCount(model);
    authCount(model);
    ssidCount(model);
    apCount(model);
    webCount(model);

    return "homeAction/showCount";
  }
  @ResponseBody
  @RequestMapping({"/get_ajax_allCount.action"})
  public Map<String, Object> get_ajax_allCount(HttpServletRequest request, HttpServletResponse response) {
    Map map = new HashMap();
    linkCount(map);
    moneyCount(map);
    authCount(map);

    ssidCount(map);
    apCount(map);
    webCount(map);
    return map;
  }

  private void ssidCount(Map<String, Object> resultMap) {
    PortalssidQuery q = new PortalssidQuery();
    q.setFields("ssid,count");
    List<Portalssid> ssids = this.portalssidService
      .getPortalssidList(q);
    Map map = new HashMap();
    for (Portalssid ssid : ssids) {
      map.put(ssid.getSsid(), ssid.getCount());
    }
    resultMap.put("ssids", map);
  }

  private void apCount(Map<String, Object> resultMap) {
    PortalapQuery q = new PortalapQuery();
    q.setFields("mac,count");
    List<Portalap> aps = this.portalapService
      .getPortalapList(q);
    Map map = new HashMap();
    for (Portalap ap : aps) {
      map.put(ap.getMac(), ap.getCount());
    }
    resultMap.put("aps", map);
  }

  private void webCount(Map<String, Object> resultMap) {
    com.leeson.core.bean.Config baseWeb = this.configService.getConfigByKey(Long.valueOf(1L));
    resultMap.put("baseShow", baseWeb.getCountShow());
    resultMap.put("baseAuth", baseWeb.getCountAuth());
    PortalwebQuery q = new PortalwebQuery();
    q.setFields("name,countShow,countAuth");
    List<Portalweb> webs = this.portalwebService
      .getPortalwebList(q);
    Map map = new HashMap();
    for (Portalweb web : webs) {
      Long[] counts = new Long[2];
      counts[0] = web.getCountShow();
      counts[1] = web.getCountAuth();
      map.put(web.getName(), counts);
    }
    resultMap.put("webs", map);
  }

  private void linkCount(Map<String, Object> resultMap)
  {
    int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();

    int linkAll = this.portallinkrecordallService
      .getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue() + 
      onlineCount;
    int linkAllUp = linkAll / 7;
    resultMap.put("linkAll", Integer.valueOf(linkAll));
    resultMap.put("linkAllUp", Integer.valueOf(linkAllUp));

    PortallinkrecordallQuery linkTodayQuery = new PortallinkrecordallQuery();
    Date now = new Date();
    Date today = new Date(now.getYear(), now.getMonth(), now.getDate());
    linkTodayQuery.setBegin_time(today);
    int linkToday = this.portallinkrecordallService
      .getPortallinkrecordallCount(linkTodayQuery).intValue();
    Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
    while (iteratorOnline.hasNext()) {
      Object o = iteratorOnline.next();
      String t = o.toString();
      String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
      try {
        String time = loginInfo[3];
        Date loginTime = ThreadLocalDateUtil.parse(time);
        if (loginTime.getTime() >= today.getTime())
          linkToday++;
      }
      catch (Exception localException) {
      }
    }
    int linkTodayUp = linkToday / 7;
    resultMap.put("linkToday", Integer.valueOf(linkToday));
    resultMap.put("linkTodayUp", Integer.valueOf(linkTodayUp));

    PortallinkrecordallQuery linkWeekQuery = new PortallinkrecordallQuery();
    linkWeekQuery.setBegin_time(new Date(new Date().getTime() - 604800000L));

    int linkWeek = this.portallinkrecordallService
      .getPortallinkrecordallCount(linkWeekQuery).intValue() + onlineCount;
    int linkWeekUp = linkWeek / 7;
    resultMap.put("linkWeek", Integer.valueOf(linkWeek));
    resultMap.put("linkWeekUp", Integer.valueOf(linkWeekUp));

    PortallinkrecordallQuery linkMonthQuery = new PortallinkrecordallQuery();
    linkMonthQuery.setBegin_time(new Date(new Date().getTime() - 2592000000L));

    int linkMonth = this.portallinkrecordallService
      .getPortallinkrecordallCount(linkMonthQuery).intValue() + onlineCount;
    int linkMonthUp = linkMonth / 7;
    resultMap.put("linkMonth", Integer.valueOf(linkMonth));
    resultMap.put("linkMonthUp", Integer.valueOf(linkMonthUp));
  }

  private void moneyCount(Map<String, Object> resultMap) {
    Double money = Double.valueOf(0.0D);
    String moneyS = "0.00";

    PortalorderQuery q = new PortalorderQuery();
    q.setFields("money");
    List<Portalorder> orders = this.portalorderService
      .getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    resultMap.put("moneyAll", moneyS);

    money = Double.valueOf(0.0D);
    moneyS = "0.00";
    Date now = new Date();
    q.setBegin_time1(new Date(now.getYear(), now.getMonth(), now.getDate()));
    orders = this.portalorderService.getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    resultMap.put("moneyToday", moneyS);

    money = Double.valueOf(0.0D);
    moneyS = "0.00";
    q.setBegin_time1(new Date(now.getTime() - 604800000L));

    orders = this.portalorderService.getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    resultMap.put("moneyWeek", moneyS);

    money = Double.valueOf(0.0D);
    moneyS = "0.00";
    q.setBegin_time1(new Date(now.getTime() - 2592000000L));

    orders = this.portalorderService.getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    resultMap.put("moneyMonth", moneyS);
  }

  private void authCount(Map<String, Object> resultMap) {
    int onekey = 0;
    int portaluser = 0;
    int radius = 0;
    int sms = 0;
    int app = 0;
    int weixin = 0;
    int gzh = 0;
    int guest = 0;
    int count = 0;

    int ios = 0;
    int windows = 0;
    int android = 0;
    int macos = 0;

    int onekeyB = 0;
    int portaluserB = 0;
    int radiusB = 0;
    int smsB = 0;
    int appB = 0;
    int weixinB = 0;
    int gzhB = 0;
    int guestB = 0;

    int iosB = 0;
    int windowsB = 0;
    int androidB = 0;
    int macosB = 0;

    Map map = new HashMap();
    Iterator iterator = com.leeson.portal.core.model.Config.getInstance().getConfigMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String t = o.toString();
      if (stringUtils.isNotBlank(t)) {
        map.put(t, Integer.valueOf(0));
      }
    }

    int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();
    count += onlineCount;

    Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
    while (iteratorOnline.hasNext()) {
      Object o = iteratorOnline.next();
      String t = o.toString();
      int i = t.lastIndexOf(":");
      String basip = t.substring(i + 1);
      if (map.containsKey(basip)) {
        int basCount = ((Integer)map.get(basip)).intValue();
        basCount++;
        map.put(basip, Integer.valueOf(basCount));
      } else {
        map.put(basip, Integer.valueOf(1));
      }

      String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);

      String type = loginInfo[6];
      if ("0".equals(type))
        onekey++;
      else if ("1".equals(type))
        portaluser++;
      else if ("2".equals(type))
        radius++;
      else if ("3".equals(type))
        app++;
      else if ("4".equals(type))
        sms++;
      else if ("5".equals(type))
        weixin++;
      else if ("6".equals(type))
        gzh++;
      else if ("7".equals(type)) {
        guest++;
      }

      String client = loginInfo[15];
      if ("Windows".equals(client)) {
        windows++;
      }
      if ("MacOS".equals(client)) {
        macos++;
      }
      if ("Android".equals(client)) {
        android++;
      }
      if ("IOS".equals(client)) {
        ios++;
      }

    }

    PortallinkrecordallQuery basQ = new PortallinkrecordallQuery();
    basQ.setBasipLike(false);
    Iterator iteratorBas = map.keySet().iterator();
    while (iteratorBas.hasNext()) {
      String basip = (String)iteratorBas.next();
      int basCount = ((Integer)map.get(basip)).intValue();
      basQ.setBasip(basip);
      basCount += this.portallinkrecordallService.getPortallinkrecordallCount(basQ).intValue();
      map.put(basip, Integer.valueOf(basCount));
    }

    PortallinkrecordallQuery authQ = new PortallinkrecordallQuery();
    authQ.setMethodtypeLike(false);
    authQ.setMethodtype("一键认证");
    onekey += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("本地用户");
    portaluser += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("Radius");
    radius += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("APP认证");
    app += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("短信认证");
    sms += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("微信认证");
    weixin += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("公众号认证");
    gzh += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("访客认证");
    guest += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();

    PortallinkrecordallQuery clientQ = new PortallinkrecordallQuery();
    clientQ.setAgentLike(false);
    clientQ.setAgent("Windows");
    windows += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
    clientQ.setAgent("MacOS");
    macos += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
    clientQ.setAgent("Android");
    android += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
    clientQ.setAgent("IOS");
    ios += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();

    count += this.portallinkrecordallService.getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue();

    resultMap.put("onekey", Integer.valueOf(onekey));
    resultMap.put("portaluser", Integer.valueOf(portaluser));
    resultMap.put("radius", Integer.valueOf(radius));
    resultMap.put("sms", Integer.valueOf(sms));
    resultMap.put("weixin", Integer.valueOf(weixin));
    resultMap.put("gzh", Integer.valueOf(gzh));
    resultMap.put("app", Integer.valueOf(app));
    resultMap.put("guest", Integer.valueOf(guest));

    resultMap.put("windows", Integer.valueOf(windows));
    resultMap.put("macos", Integer.valueOf(macos));
    resultMap.put("android", Integer.valueOf(android));
    resultMap.put("ios", Integer.valueOf(ios));

    if (count != 0) {
      onekeyB = (int)Math.rint(onekey / count * 100.0D);
      portaluserB = (int)Math.rint(portaluser / count * 100.0D);
      radiusB = (int)Math.rint(radius / count * 100.0D);
      smsB = (int)Math.rint(sms / count * 100.0D);
      weixinB = (int)Math.rint(weixin / count * 100.0D);
      gzhB = (int)Math.rint(gzh / count * 100.0D);
      appB = (int)Math.rint(app / count * 100.0D);
      guestB = (int)Math.rint(guest / count * 100.0D);

      windowsB = (int)Math.rint(windows / count * 100.0D);
      macosB = (int)Math.rint(macos / count * 100.0D);
      androidB = (int)Math.rint(android / count * 100.0D);
      iosB = (int)Math.rint(ios / count * 100.0D);
    }
    resultMap.put("onekeyB", Integer.valueOf(onekeyB));
    resultMap.put("portaluserB", Integer.valueOf(portaluserB));
    resultMap.put("radiusB", Integer.valueOf(radiusB));
    resultMap.put("smsB", Integer.valueOf(smsB));
    resultMap.put("weixinB", Integer.valueOf(weixinB));
    resultMap.put("gzhB", Integer.valueOf(gzhB));
    resultMap.put("appB", Integer.valueOf(appB));
    resultMap.put("guestB", Integer.valueOf(guestB));

    resultMap.put("windowsB", Integer.valueOf(windowsB));
    resultMap.put("macosB", Integer.valueOf(macosB));
    resultMap.put("androidB", Integer.valueOf(androidB));
    resultMap.put("iosB", Integer.valueOf(iosB));

    resultMap.put("count", Integer.valueOf(count));

    resultMap.put("basCount", map);
  }

  private void linkCount(ModelMap model)
  {
    int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();

    int linkAll = this.portallinkrecordallService
      .getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue() + 
      onlineCount;
    int linkAllUp = linkAll / 7;
    model.put("linkAll", Integer.valueOf(linkAll));
    model.put("linkAllUp", Integer.valueOf(linkAllUp));

    PortallinkrecordallQuery linkTodayQuery = new PortallinkrecordallQuery();
    Date now = new Date();
    Date today = new Date(now.getYear(), now.getMonth(), now.getDate());
    linkTodayQuery.setBegin_time(today);
    int linkToday = this.portallinkrecordallService
      .getPortallinkrecordallCount(linkTodayQuery).intValue();
    Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
    while (iteratorOnline.hasNext()) {
      Object o = iteratorOnline.next();
      String t = o.toString();
      String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);
      try {
        String time = loginInfo[3];
        Date loginTime = ThreadLocalDateUtil.parse(time);
        if (loginTime.getTime() >= today.getTime())
          linkToday++;
      }
      catch (Exception localException) {
      }
    }
    int linkTodayUp = linkToday / 7;
    model.put("linkToday", Integer.valueOf(linkToday));
    model.put("linkTodayUp", Integer.valueOf(linkTodayUp));

    PortallinkrecordallQuery linkWeekQuery = new PortallinkrecordallQuery();
    linkWeekQuery.setBegin_time(new Date(new Date().getTime() - 604800000L));

    int linkWeek = this.portallinkrecordallService
      .getPortallinkrecordallCount(linkWeekQuery).intValue() + onlineCount;
    int linkWeekUp = linkWeek / 7;
    model.put("linkWeek", Integer.valueOf(linkWeek));
    model.put("linkWeekUp", Integer.valueOf(linkWeekUp));

    PortallinkrecordallQuery linkMonthQuery = new PortallinkrecordallQuery();
    linkMonthQuery.setBegin_time(new Date(new Date().getTime() - 2592000000L));

    int linkMonth = this.portallinkrecordallService
      .getPortallinkrecordallCount(linkMonthQuery).intValue() + onlineCount;
    int linkMonthUp = linkMonth / 7;
    model.put("linkMonth", Integer.valueOf(linkMonth));
    model.put("linkMonthUp", Integer.valueOf(linkMonthUp));
  }

  private void moneyCount(ModelMap model) {
    Double money = Double.valueOf(0.0D);
    String moneyS = "0.00";

    PortalorderQuery q = new PortalorderQuery();
    q.setFields("money");
    List<Portalorder> orders = this.portalorderService
      .getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    model.put("moneyAll", moneyS);

    money = Double.valueOf(0.0D);
    moneyS = "0.00";
    Date now = new Date();
    q.setBegin_time1(new Date(now.getYear(), now.getMonth(), now.getDate()));
    orders = this.portalorderService.getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    model.put("moneyToday", moneyS);

    money = Double.valueOf(0.0D);
    moneyS = "0.00";
    q.setBegin_time1(new Date(now.getTime() - 604800000L));

    orders = this.portalorderService.getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    model.put("moneyWeek", moneyS);

    money = Double.valueOf(0.0D);
    moneyS = "0.00";
    q.setBegin_time1(new Date(now.getTime() - 2592000000L));

    orders = this.portalorderService.getPortalorderList(q);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    model.put("moneyMonth", moneyS);
  }

  private void authCount(ModelMap model) {
    int onekey = 0;
    int portaluser = 0;
    int radius = 0;
    int sms = 0;
    int app = 0;
    int weixin = 0;
    int gzh = 0;
    int guest = 0;
    int count = 0;

    int ios = 0;
    int windows = 0;
    int android = 0;
    int macos = 0;

    int onekeyB = 0;
    int portaluserB = 0;
    int radiusB = 0;
    int smsB = 0;
    int appB = 0;
    int weixinB = 0;
    int gzhB = 0;
    int guestB = 0;

    int iosB = 0;
    int windowsB = 0;
    int androidB = 0;
    int macosB = 0;

    Map map = new HashMap();
    Iterator iterator = com.leeson.portal.core.model.Config.getInstance().getConfigMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String t = o.toString();
      if (stringUtils.isNotBlank(t)) {
        map.put(t, Integer.valueOf(0));
      }
    }

    int onlineCount = OnlineMap.getInstance().getOnlineUserMap().size();
    count += onlineCount;

    Iterator iteratorOnline = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
    while (iteratorOnline.hasNext()) {
      Object o = iteratorOnline.next();
      String t = o.toString();
      int i = t.lastIndexOf(":");
      String basip = t.substring(i + 1);
      if (map.containsKey(basip)) {
        int basCount = ((Integer)map.get(basip)).intValue();
        basCount++;
        map.put(basip, Integer.valueOf(basCount));
      } else {
        map.put(basip, Integer.valueOf(1));
      }

      String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(t);

      String type = loginInfo[6];
      if ("0".equals(type))
        onekey++;
      else if ("1".equals(type))
        portaluser++;
      else if ("2".equals(type))
        radius++;
      else if ("3".equals(type))
        app++;
      else if ("4".equals(type))
        sms++;
      else if ("5".equals(type))
        weixin++;
      else if ("6".equals(type))
        gzh++;
      else if ("7".equals(type)) {
        guest++;
      }

      String client = loginInfo[15];
      if ("Windows".equals(client)) {
        windows++;
      }
      if ("MacOS".equals(client)) {
        macos++;
      }
      if ("Android".equals(client)) {
        android++;
      }
      if ("IOS".equals(client)) {
        ios++;
      }

    }

    PortallinkrecordallQuery basQ = new PortallinkrecordallQuery();
    basQ.setBasipLike(false);
    Iterator iteratorBas = map.keySet().iterator();
    while (iteratorBas.hasNext()) {
      String basip = (String)iteratorBas.next();
      int basCount = ((Integer)map.get(basip)).intValue();
      basQ.setBasip(basip);
      basCount += this.portallinkrecordallService.getPortallinkrecordallCount(basQ).intValue();
      map.put(basip, Integer.valueOf(basCount));
    }

    PortallinkrecordallQuery authQ = new PortallinkrecordallQuery();
    authQ.setMethodtypeLike(false);
    authQ.setMethodtype("一键认证");
    onekey += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("本地用户");
    portaluser += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("Radius");
    radius += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("APP认证");
    app += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("短信认证");
    sms += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("微信认证");
    weixin += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("公众号认证");
    gzh += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();
    authQ.setMethodtype("访客认证");
    guest += this.portallinkrecordallService.getPortallinkrecordallCount(authQ).intValue();

    PortallinkrecordallQuery clientQ = new PortallinkrecordallQuery();
    clientQ.setAgentLike(false);
    clientQ.setAgent("Windows");
    windows += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
    clientQ.setAgent("MacOS");
    macos += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
    clientQ.setAgent("Android");
    android += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();
    clientQ.setAgent("IOS");
    ios += this.portallinkrecordallService.getPortallinkrecordallCount(clientQ).intValue();

    count += this.portallinkrecordallService.getPortallinkrecordallCount(new PortallinkrecordallQuery()).intValue();

    model.put("onekey", Integer.valueOf(onekey));
    model.put("portaluser", Integer.valueOf(portaluser));
    model.put("radius", Integer.valueOf(radius));
    model.put("sms", Integer.valueOf(sms));
    model.put("weixin", Integer.valueOf(weixin));
    model.put("gzh", Integer.valueOf(gzh));
    model.put("app", Integer.valueOf(app));
    model.put("guest", Integer.valueOf(guest));

    model.put("windows", Integer.valueOf(windows));
    model.put("macos", Integer.valueOf(macos));
    model.put("android", Integer.valueOf(android));
    model.put("ios", Integer.valueOf(ios));

    if (count != 0) {
      onekeyB = (int)Math.rint(onekey / count * 100.0D);
      portaluserB = (int)Math.rint(portaluser / count * 100.0D);
      radiusB = (int)Math.rint(radius / count * 100.0D);
      smsB = (int)Math.rint(sms / count * 100.0D);
      weixinB = (int)Math.rint(weixin / count * 100.0D);
      gzhB = (int)Math.rint(gzh / count * 100.0D);
      appB = (int)Math.rint(app / count * 100.0D);
      guestB = (int)Math.rint(guest / count * 100.0D);

      windowsB = (int)Math.rint(windows / count * 100.0D);
      macosB = (int)Math.rint(macos / count * 100.0D);
      androidB = (int)Math.rint(android / count * 100.0D);
      iosB = (int)Math.rint(ios / count * 100.0D);
    }

    model.put("onekeyB", Integer.valueOf(onekeyB));
    model.put("portaluserB", Integer.valueOf(portaluserB));
    model.put("radiusB", Integer.valueOf(radiusB));
    model.put("smsB", Integer.valueOf(smsB));
    model.put("weixinB", Integer.valueOf(weixinB));
    model.put("gzhB", Integer.valueOf(gzhB));
    model.put("appB", Integer.valueOf(appB));
    model.put("guestB", Integer.valueOf(guestB));

    model.put("windowsB", Integer.valueOf(windowsB));
    model.put("macosB", Integer.valueOf(macosB));
    model.put("androidB", Integer.valueOf(androidB));
    model.put("iosB", Integer.valueOf(iosB));

    model.put("count", Integer.valueOf(count));

    model.put("basCount", map);
  }

  private void ssidCount(ModelMap model) {
    PortalssidQuery q = new PortalssidQuery();
    q.setFields("ssid,count");
    List ssids = this.portalssidService
      .getPortalssidList(q);
    model.put("ssids", ssids);
  }

  private void apCount(ModelMap model) {
    PortalapQuery q = new PortalapQuery();
    q.setFields("mac,count");
    List aps = this.portalapService
      .getPortalapList(q);
    model.put("aps", aps);
  }

  private void webCount(ModelMap model) {
    com.leeson.core.bean.Config baseWeb = this.configService.getConfigByKey(Long.valueOf(1L));
    model.put("baseShow", baseWeb.getCountShow());
    model.put("baseAuth", baseWeb.getCountAuth());
    PortalwebQuery q = new PortalwebQuery();
    q.setFields("name,countShow,countAuth");
    List webs = this.portalwebService
      .getPortalwebList(q);
    model.put("webs", webs);
  }

  @RequestMapping({"ndex.action"})
  public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
    Portaluser user = (Portaluser)request.getSession()
      .getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      model.put("msgCount", Integer.valueOf(0));
      return "homeAction/index";
    }
    String toid = user.getId().toString();
    PortalmessageQuery message = new PortalmessageQuery();
    message.setToid(toid);
    message.setToPos("0");
    message.setState("0");
    message.setDelin("0");
    model.addAttribute("msgCount", 
      this.portalmessageService.getPortalmessageCount(message));

    int license = 0;
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
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
      String apCount = MxMzIyRDMzMzAy[1];
      String toDateString = MxMzIyRDMzMzAy[2];
      String basCount = MxMzIyRDMzMzAy[4];
      String macCount = MxMzIyRDMzMzAy[5];
      Date saveDate;
      try { saveDate = fs.parse(toDateString); }
      catch (ParseException e)
      {
        saveDate = new Date();
      }Date now = new Date();
      String nowString = fs.format(now);
      Date nowDate;
      try { nowDate = fs.parse(nowString); }
      catch (ParseException e)
      {
        nowDate = saveDate;
      }

      long to = saveDate.getTime();
      long from = nowDate.getTime();

      int doMac = OnlineMap.getInstance().getOnlineUserMap().size();
      int doAp = this.portalweixinwifiService
        .getPortalweixinwifiCount(new PortalweixinwifiQuery()).intValue();
      int doBas = this.portalbasService
        .getPortalbasCount(new PortalbasQuery()).intValue();
      int apB = 100;
      int basB = 100;
      int macB = 100;
      try {
        if (Integer.valueOf(apCount).intValue() > 0)
          apB = (int)Math.rint(doAp / 
            Integer.valueOf(apCount).intValue() * 100.0D);
      }
      catch (Exception e) {
        apB = 100;
      }
      try {
        if (Integer.valueOf(basCount).intValue() > 0)
          basB = (int)Math.rint(doBas / 
            Integer.valueOf(basCount).intValue() * 100.0D);
      }
      catch (Exception e) {
        basB = 100;
      }
      try {
        if (Integer.valueOf(macCount).intValue() > 0)
          macB = (int)Math.rint(doMac / 
            Integer.valueOf(macCount).intValue() * 100.0D);
      }
      catch (Exception e) {
        macB = 100;
      }

      int haveDay = (int)((to - from) / 86400000L);

      if (nowDate.getTime() < saveDate.getTime()) {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString);
        model.addAttribute("nowdate", nowString);
        model.addAttribute("doAp", Integer.valueOf(doAp));
        model.addAttribute("doBas", Integer.valueOf(doBas));
        model.addAttribute("doMac", Integer.valueOf(doMac));
        model.addAttribute("apB", Integer.valueOf(apB));
        model.addAttribute("basB", Integer.valueOf(basB));
        model.addAttribute("macB", Integer.valueOf(macB));
        model.addAttribute("haveDay", Integer.valueOf(haveDay));
        license = 1;
      } else {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString);
        model.addAttribute("nowdate", nowString);
        model.addAttribute("doAp", Integer.valueOf(doAp));
        model.addAttribute("doBas", Integer.valueOf(doBas));
        model.addAttribute("doMac", Integer.valueOf(doMac));
        model.addAttribute("apB", Integer.valueOf(apB));
        model.addAttribute("basB", Integer.valueOf(basB));
        model.addAttribute("macB", Integer.valueOf(macB));
        model.addAttribute("haveDay", Integer.valueOf(haveDay));
        license = 2;
      }
    }
    model.addAttribute("license", Integer.valueOf(license));

    return "homeAction/index";
  }

  @RequestMapping({"ight.action"})
  public String right(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    PortallogrecordQuery logrecordQuery = new PortallogrecordQuery();
    logrecordQuery.setPageSize(7);
    logrecordQuery.setPage(1);
    logrecordQuery.orderbyId(false);
    List operationRecords = this.portallogrecordService
      .getPortallogrecordListWithPage(logrecordQuery).getList();

    PortalaccountQuery portalaccountQuery = new PortalaccountQuery();
    portalaccountQuery.setState("0");

    int lock_count = this.portalaccountService
      .getPortalaccountCount(portalaccountQuery).intValue();
    int acc_count = this.portalaccountService
      .getPortalaccountCount(new PortalaccountQuery()).intValue();

    int online_count = OnlineMap.getInstance().getOnlineUserMap().size() + 
      WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().size();

    model.addAttribute("config", 
      ((Portalbas)com.leeson.portal.core.model.Config.getInstance().getConfigMap()
      .get("")).getAuthInterface());

    model.addAttribute("operationRecords", operationRecords);
    int outline_count = acc_count - online_count;
    if (outline_count < 0) {
      outline_count = 0;
    }
    model.addAttribute("outline_count", Integer.valueOf(outline_count));
    model.addAttribute("online_count", Integer.valueOf(online_count));
    model.addAttribute("acc_count", Integer.valueOf(acc_count));
    model.addAttribute("lock_count", Integer.valueOf(lock_count));
    model.addAttribute("true_count", Integer.valueOf(acc_count - lock_count));

    MonitorInfoBean sysInfo = MonitorService.getMonitorInfoBean();
    model.addAttribute("System", sysInfo.getOsName());
    model.addAttribute("CpuRatio", Double.valueOf(sysInfo.getCpuRatio()));
    model.addAttribute("TotalThread", Integer.valueOf(sysInfo.getTotalThread()));
    model.addAttribute("Memory", sysInfo.getFreePhysicalMemorySize() / 1024L + "MB / " + sysInfo.getTotalMemorySize() / 1024L + "MB");
    model.addAttribute("Disk", sysInfo.getDiskFreeSpace() + "GB / " + sysInfo.getDiskTotalSpace() + "GB");

    return "homeAction/right";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.HomeController
 * JD-Core Version:    0.6.2
 */