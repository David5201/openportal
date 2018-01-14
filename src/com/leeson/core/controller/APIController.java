package com.leeson.core.controller;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.bean.Portalspeed;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountgroupService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.service.PortalspeedService;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.radius.core.model.RadiusOnlineMap;
import com.leeson.radius.core.utils.COAThread;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.misc.BASE64Decoder;

@Controller
public class APIController
{

  @Autowired
  private PortalaccountService portalaccountService;

  @Autowired
  private PortalaccountmacsService macsService;

  @Autowired
  private PortalconfigService portalconfigService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalspeedService portalspeedService;

  @Autowired
  private PortalaccountgroupService portalaccountgroupService;
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  private static Logger log = Logger.getLogger(APIController.class);

  @RequestMapping(value={"/CheckIn.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void checkIn(Portalaccount e, Integer macLimit, Integer count, HttpServletResponse response, HttpServletRequest request)
  {
    try
    {
      if ((stringUtils.isBlank(e.getLoginName())) || 
        (stringUtils.isBlank(e.getPassword()))) {
        String respMessage = "error";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }

      int accountState = Integer.valueOf(e.getState()).intValue();
      if ((accountState < 0) || (accountState > 3)) {
        String respMessage = "error";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }

      Long userTime = this.portalaccountgroupService
        .getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
      if ((accountState == 2) && 
        (e.getTime() == null)) {
        e.setTime(userTime);
      }

      if ((accountState == 3) && 
        (e.getDate() == null)) {
        String respMessage = "error";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }

      if (e.getDate() == null) {
        e.setDate(new Date());
      }

      if (count == null) {
        count = Integer.valueOf(1);
      }
      if (macLimit == null) {
        macLimit = Integer.valueOf(0);
      }
      if (stringUtils.isBlank(e.getGender())) {
        e.setGender(null);
      }

      PortalaccountQuery aq = new PortalaccountQuery();
      aq.setLoginName(e.getLoginName());
      aq.setLoginNameLike(false);
      List accountList = this.portalaccountService
        .getPortalaccountList(aq);
      if (accountList.size() > 0)
      {
        e.setId(((Portalaccount)accountList.get(0)).getId());
        this.portalaccountService.updatePortalaccountByKeyAll(e);

        PortalaccountmacsQuery amsq = new PortalaccountmacsQuery();
        amsq.setAccountId(e.getId());
        this.macsService.deleteByQuery(amsq);

        String respMessage = "ok";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }

      e.setAutologin(Integer.valueOf(0));
      e.setMaclimit(macLimit);
      e.setMaclimitcount(count);
      this.portalaccountService.addPortalaccount(e);
      String respMessage = "ok";
      PrintWriter out = response.getWriter();
      out.print(respMessage);
      out.close();
      return;
    }
    catch (Exception ex)
    {
      try {
        String respMessage = "error";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }
      catch (Exception localException1)
      {
      }
    }
  }

  @RequestMapping(value={"/CheckOut.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void checkOut(String loginName, HttpServletResponse response, HttpServletRequest request)
  {
    try {
      if (stringUtils.isBlank(loginName)) {
        String respMessage = "error";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }

      PortalaccountQuery aq = new PortalaccountQuery();
      aq.setLoginName(loginName);
      aq.setLoginNameLike(false);
      List accountList = this.portalaccountService
        .getPortalaccountList(aq);
      if (accountList.size() > 0) {
        Portalaccount acc = (Portalaccount)accountList.get(0);
        Long id = acc.getId();
        Set<Map.Entry<String, String[]>> entries = onlineMap
          .getOnlineUserMap().entrySet();
        for (Map.Entry entry : entries) {
          String[] info = (String[])entry.getValue();
          String ip = (String)entry.getKey();
          if ((stringUtils.isNotBlank(info[1])) && 
            (Long.valueOf(info[1]) == id)) {
            Kick.kickUserDeleteUser(ip);
          }
        }

        int accountState = Integer.valueOf(acc.getState()).intValue();
        acc = this.portalaccountService.getPortalaccountByKey(id);
        acc.setState("0");
        this.portalaccountService.updatePortalaccountByKey(acc);

        Long costTime = Long.valueOf(0L);
        if (accountState == 2) {
          Long userTime = this.portalaccountgroupService
            .getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
          costTime = Long.valueOf(userTime.longValue() - acc.getTime().longValue());
        }
        String respMessage = String.valueOf(costTime);
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }
      String respMessage = "error";
      PrintWriter out = response.getWriter();
      out.print(respMessage);
      out.close();
      return;
    }
    catch (Exception e) {
      try {
        String respMessage = "error";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        return;
      }
      catch (Exception localException1)
      {
      }
    }
  }

  @RequestMapping({"/AccAPI.action"})
  public void AccAPI(HttpServletResponse response, HttpServletRequest request, @RequestParam("business") String business)
    throws IOException
  {
    String respMessage = "E99";
    Integer haveAcc = this.portalaccountService
      .getPortalaccountCount(new PortalaccountQuery());
    if ((haveAcc != null) && 
      (haveAcc.intValue() >= Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
      .getCoreConfigMap().get("core"))[1]).intValue()))
    {
      respMessage = "E83";
      PrintWriter out = response.getWriter();
      out.print(respMessage);
      out.close();
      return;
    }

    if (stringUtils.isNotBlank(business)) {
      String key = decodeBase64(business);
      if (stringUtils.isNotBlank(key)) {
        try {
          String[] keys = key.split("\\t");

          if (keys[0].equals("001")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              if (stringUtils.isNotEmpty(keys[2])) {
                PortalaccountQuery aq = new PortalaccountQuery();
                aq.setLoginName(keys[1]);
                aq.setLoginNameLike(false);
                List accs = this.portalaccountService
                  .getPortalaccountList(aq);
                if (accs.size() < 1) {
                  Portalaccount acc = new Portalaccount();
                  acc.setLoginName(keys[1]);
                  acc.setPassword(keys[2]);
                  acc.setName(keys[4]);

                  boolean haveSpeed = false;
                  if (keys[3].equals("1")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(1L)) != null) {
                      acc.setSpeed(Long.valueOf(1L));
                      haveSpeed = true;
                    } else {
                      respMessage = "E30";
                    }
                  } else if (keys[3].equals("2")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(2L)) != null) {
                      acc.setSpeed(Long.valueOf(2L));
                      haveSpeed = true;
                    } else {
                      respMessage = "E30";
                    }
                  } else if (keys[3].equals("3")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(3L)) != null) {
                      acc.setSpeed(Long.valueOf(3L));
                      haveSpeed = true;
                    } else {
                      respMessage = "E30";
                    }
                  } else if (keys[3].equals("4")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(4L)) != null) {
                      acc.setSpeed(Long.valueOf(4L));
                      haveSpeed = true;
                    } else {
                      respMessage = "E30";
                    }
                  }
                  else respMessage = "E30";

                  if (haveSpeed)
                  {
                    Portalaccountgroup ag = this.portalaccountgroupService
                      .getPortalaccountgroupByKey(Long.valueOf(1L));
                    if (stringUtils.isBlank(acc.getState())) {
                      acc.setState(ag.getState());
                    }
                    if (acc.getMaclimitcount() == null) {
                      acc.setMaclimitcount(ag
                        .getMaclimitcount());
                    }
                    if (acc.getMaclimit() == null) {
                      acc.setMaclimit(ag.getMaclimit());
                    }
                    if (acc.getAutologin() == null) {
                      acc.setAutologin(ag.getAutologin());
                    }
                    if (acc.getSpeed() == null) {
                      acc.setSpeed(ag.getSpeed());
                    }
                    acc.setDate(ag.getDate());
                    acc.setTime(ag.getTime());
                    acc.setOctets(ag.getOctets());

                    this.portalaccountService
                      .addPortalaccount(acc);
                    respMessage = "E00";
                  }
                } else {
                  respMessage = "E13";
                }
              } else {
                respMessage = "E20";
              }
            }
            else respMessage = "E10";

          }

          if (keys[0].equals("002")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              if (stringUtils.isNotEmpty(keys[2])) {
                PortalaccountQuery aq = new PortalaccountQuery();
                aq.setLoginName(keys[1]);
                aq.setLoginNameLike(false);
                List accs = this.portalaccountService
                  .getPortalaccountList(aq);
                if (accs.size() == 1) {
                  Portalaccount acc = (Portalaccount)accs.get(0);
                  acc.setPassword(keys[2]);
                  this.portalaccountService
                    .updatePortalaccountByKey(acc);
                  respMessage = "E00";
                } else {
                  respMessage = "E14";
                }
              } else {
                respMessage = "E20";
              }
            }
            else respMessage = "E14";

          }

          if (keys[0].equals("004")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                acc.setState("0");
                this.portalaccountService
                  .updatePortalaccountByKey(acc);
                respMessage = "E00";
              } else {
                respMessage = "E14";
              }
            } else {
              respMessage = "E14";
            }

          }

          if (keys[0].equals("005")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                acc.setState("1");
                this.portalaccountService
                  .updatePortalaccountByKey(acc);
                respMessage = "E00";
              } else {
                respMessage = "E14";
              }
            } else {
              respMessage = "E14";
            }

          }

          if (keys[0].equals("006")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              if (stringUtils.isNotEmpty(keys[2])) {
                PortalaccountQuery aq = new PortalaccountQuery();
                aq.setLoginName(keys[1]);
                aq.setLoginNameLike(false);
                List accs = this.portalaccountService
                  .getPortalaccountList(aq);
                if (accs.size() == 1) {
                  Portalaccount acc = (Portalaccount)accs.get(0);
                  if (keys[2].equals("1")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(1L)) != null) {
                      kickAcc(acc.getId(), 
                        acc.getLoginName());
                      acc.setSpeed(Long.valueOf(1L));
                      this.portalaccountService
                        .updatePortalaccountByKey(acc);
                      respMessage = "E00";
                    } else {
                      respMessage = "E30";
                    }
                  } else if (keys[2].equals("2")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(2L)) != null) {
                      kickAcc(acc.getId(), 
                        acc.getLoginName());
                      acc.setSpeed(Long.valueOf(2L));
                      this.portalaccountService
                        .updatePortalaccountByKey(acc);
                      respMessage = "E00";
                    } else {
                      respMessage = "E30";
                    }
                  } else if (keys[2].equals("3")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(3L)) != null) {
                      kickAcc(acc.getId(), 
                        acc.getLoginName());
                      acc.setSpeed(Long.valueOf(3L));
                      this.portalaccountService
                        .updatePortalaccountByKey(acc);
                      respMessage = "E00";
                    } else {
                      respMessage = "E30";
                    }
                  } else if (keys[2].equals("4")) {
                    if (this.portalspeedService
                      .getPortalspeedByKey(Long.valueOf(4L)) != null) {
                      kickAcc(acc.getId(), 
                        acc.getLoginName());
                      acc.setSpeed(Long.valueOf(4L));
                      this.portalaccountService
                        .updatePortalaccountByKey(acc);
                      respMessage = "E00";
                    } else {
                      respMessage = "E30";
                    }
                  }
                  else respMessage = "E30"; 
                }
                else
                {
                  respMessage = "E14";
                }
              } else {
                respMessage = "E30";
              }
            }
            else respMessage = "E14";

          }

          if (keys[0].equals("007")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
                macsq.setAccountId(acc.getId());
                this.macsService.deleteByQuery(macsq);
                this.portalaccountService.deleteByKey(acc.getId());
                respMessage = "E00";
              } else {
                respMessage = "E14";
              }
            } else {
              respMessage = "E14";
            }

          }

          if (keys[0].equals("014")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                respMessage = "E00";
              } else {
                respMessage = "E14";
              }
            } else {
              respMessage = "E14";
            }

          }

          if (keys[0].equals("091"))
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                respMessage = "E00\\t" + acc.getLoginName() + 
                  "\\t" + acc.getSpeed() + "\\t" + 
                  acc.getState();
              } else {
                respMessage = "E14";
              }
            } else {
              respMessage = "E14";
            }
        }
        catch (Exception e) {
          log.error("Account API Error ！");
          log.error("==============ERROR Start=============");
          log.error(e);
          log.error("ERROR INFO ", e);
          log.error("==============ERROR End=============");
        }
      }
    }

    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
  }

  private void kickAcc(Long id, String username)
  {
    Set<Map.Entry<String, String[]>> entries = onlineMap.getOnlineUserMap()
      .entrySet();
    for (Map.Entry entry : entries) {
      String[] info = (String[])entry.getValue();
      String ip = (String)entry.getKey();
      if ((stringUtils.isNotBlank(info[1])) && 
        (Long.valueOf(info[1]) == id)) {
        Kick.kickUserDeleteUser(ip);
      }

    }

    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String acctSessionId = o.toString();
      String[] radiusOnlineInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
        (username.equals(radiusOnlineInfo[4])))
        COAThread.COA_Account_Cost(radiusOnlineInfo, 
          "Radius Account API COA");
    }
  }

  public static String decodeBase64(String input)
  {
    String key = "";
    try {
      input = input.trim();
      BASE64Decoder decoder = new BASE64Decoder();

      byte[] b = decoder.decodeBuffer(input);
      key = new String(b, "GBK");
    } catch (Exception e) {
      key = "";
    }
    return key;
  }

  public static String decodeBase64Utf8(String input) {
    String key = "";
    try {
      input = input.trim();
      BASE64Decoder decoder = new BASE64Decoder();

      byte[] b = decoder.decodeBuffer(input);
      key = new String(b, "UTF-8");
    } catch (Exception e) {
      key = "";
    }
    return key;
  }

  @RequestMapping({"/AccountAPI.action"})
  public void AccountAPI(HttpServletResponse response, HttpServletRequest request, @RequestParam("business") String business)
    throws IOException
  {
    String respMessage = "E99";
    Integer haveAcc = this.portalaccountService
      .getPortalaccountCount(new PortalaccountQuery());
    if ((haveAcc != null) && 
      (haveAcc.intValue() >= Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
      .getCoreConfigMap().get("core"))[1]).intValue()))
    {
      respMessage = "E90";
      PrintWriter out = response.getWriter();
      out.print(respMessage);
      out.close();
      return;
    }

    if (stringUtils.isNotBlank(business)) {
      String key = decodeBase64Utf8(business);
      System.out.println(key);
      if (stringUtils.isNotBlank(key)) {
        try {
          String[] keys = key.split("\\t");

          if (keys[0].equals("001")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              if (stringUtils.isNotEmpty(keys[2])) {
                if (stringUtils.isNotEmpty(keys[3])) {
                  if (stringUtils.isNotEmpty(keys[4])) {
                    if (stringUtils.isNotEmpty(keys[5])) {
                      if (stringUtils.isNotEmpty(keys[6]))
                      {
                        if (stringUtils.isNotEmpty(keys[7]))
                        {
                          if (stringUtils.isNotEmpty(keys[8]))
                          {
                            if (stringUtils.isNotEmpty(keys[9]))
                            {
                              if (stringUtils.isNotEmpty(keys[10]))
                              {
                                PortalaccountQuery aq = new PortalaccountQuery();
                                aq.setLoginName(keys[1]);
                                aq.setLoginNameLike(false);
                                List accs = this.portalaccountService
                                  .getPortalaccountList(aq);
                                if (accs.size() < 1) {
                                  Portalaccount acc = new Portalaccount();
                                  acc.setLoginName(keys[1]);
                                  acc.setPassword(keys[2]);
                                  acc.setName(keys[11]);
                                  acc.setPhoneNumber(keys[12]);

                                  boolean haveSpeed = false;
                                  PortalspeedQuery sq = new PortalspeedQuery();
                                  sq.setNameLike(false);
                                  sq.setName(keys[7]);
                                  List speeds = this.portalspeedService
                                    .getPortalspeedList(sq);
                                  if (speeds
                                    .size() == 1) {
                                    acc.setSpeed(
                                      ((Portalspeed)speeds
                                      .get(0))
                                      .getId());
                                    haveSpeed = true;
                                  } else {
                                    respMessage = "E07";
                                  }
                                  if (haveSpeed) {
                                    acc.setState(keys[3]);
                                    acc.setTime(
                                      Long.valueOf(keys[4]));
                                    SimpleDateFormat format = new SimpleDateFormat(
                                      "yyyy-MM-dd HH:mm:ss");
                                    Date date = format
                                      .parse(keys[5] + 
                                      " 23:59:59");
                                    acc.setDate(date);
                                    acc.setOctets(
                                      Long.valueOf(keys[6]));
                                    acc.setMaclimitcount(
                                      Integer.valueOf(keys[8]));
                                    acc.setMaclimit(
                                      Integer.valueOf(keys[9]));
                                    acc.setAutologin(
                                      Integer.valueOf(keys[10]));
                                    this.portalaccountService
                                      .addPortalaccount(acc);
                                    respMessage = "E00";
                                  }
                                } else {
                                  respMessage = "E20";
                                }
                              }
                              else {
                                respMessage = "E10";
                              }
                            }
                            else respMessage = "E09";
                          }
                          else
                            respMessage = "E08";
                        }
                        else
                          respMessage = "E07";
                      }
                      else
                        respMessage = "E06";
                    }
                    else
                      respMessage = "E05";
                  }
                  else
                    respMessage = "E04";
                }
                else
                  respMessage = "E03";
              }
              else
                respMessage = "E02";
            }
            else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("002")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              if (stringUtils.isNotEmpty(keys[2])) {
                if (stringUtils.isNotEmpty(keys[3])) {
                  if (stringUtils.isNotEmpty(keys[4])) {
                    if (stringUtils.isNotEmpty(keys[5])) {
                      if (stringUtils.isNotEmpty(keys[6]))
                      {
                        if (stringUtils.isNotEmpty(keys[7]))
                        {
                          if (stringUtils.isNotEmpty(keys[8]))
                          {
                            if (stringUtils.isNotEmpty(keys[9]))
                            {
                              if (stringUtils.isNotEmpty(keys[10]))
                              {
                                PortalaccountQuery aq = new PortalaccountQuery();
                                aq.setLoginName(keys[1]);
                                aq.setLoginNameLike(false);
                                List accs = this.portalaccountService
                                  .getPortalaccountList(aq);
                                if (accs.size() == 1) {
                                  Portalaccount acc = (Portalaccount)accs.get(0);
                                  kickAcc(acc.getId(), acc.getLoginName());

                                  acc.setPassword(keys[2]);
                                  acc.setName(keys[11]);
                                  acc.setPhoneNumber(keys[12]);

                                  boolean haveSpeed = false;
                                  PortalspeedQuery sq = new PortalspeedQuery();
                                  sq.setNameLike(false);
                                  sq.setName(keys[7]);
                                  List speeds = this.portalspeedService
                                    .getPortalspeedList(sq);
                                  if (speeds
                                    .size() == 1) {
                                    acc.setSpeed(
                                      ((Portalspeed)speeds
                                      .get(0))
                                      .getId());
                                    haveSpeed = true;
                                  } else {
                                    respMessage = "E07";
                                  }
                                  if (haveSpeed) {
                                    if (!"none".equals(keys[4])) {
                                      acc.setTime(
                                        Long.valueOf(keys[4]));
                                    }
                                    if (!"none".equals(keys[5])) {
                                      SimpleDateFormat format = new SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm:ss");
                                      Date date = format
                                        .parse(keys[5] + 
                                        " 23:59:59");
                                      acc.setDate(date);
                                    }
                                    if (!"none".equals(keys[5])) {
                                      acc.setOctets(
                                        Long.valueOf(keys[6]));
                                    }
                                    acc.setState(keys[3]);
                                    acc.setMaclimitcount(
                                      Integer.valueOf(keys[8]));
                                    acc.setMaclimit(
                                      Integer.valueOf(keys[9]));
                                    acc.setAutologin(
                                      Integer.valueOf(keys[10]));
                                    this.portalaccountService
                                      .updatePortalaccountByKey(acc);
                                    respMessage = "E00";
                                  }
                                } else {
                                  respMessage = "E30";
                                }
                              }
                              else {
                                respMessage = "E10";
                              }
                            }
                            else respMessage = "E09";
                          }
                          else
                            respMessage = "E08";
                        }
                        else
                          respMessage = "E07";
                      }
                      else
                        respMessage = "E06";
                    }
                    else
                      respMessage = "E05";
                  }
                  else
                    respMessage = "E04";
                }
                else
                  respMessage = "E03";
              }
              else
                respMessage = "E02";
            }
            else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("003")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
                macsq.setAccountId(acc.getId());
                this.macsService.deleteByQuery(macsq);
                this.portalaccountService.deleteByKey(acc.getId());
                respMessage = "E00";
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("004")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
                macsq.setAccountId(acc.getId());
                this.macsService.deleteByQuery(macsq);
                respMessage = "E00";
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("005")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                respMessage = "E00";
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("006")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                respMessage = "E00\\t" + acc.toString();
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }
          }
        }
        catch (Exception localException)
        {
        }
      }
    }
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
  }

  @RequestMapping({"/AccountAPIGBK.action"})
  public void AccountAPIGBK(HttpServletResponse response, HttpServletRequest request, @RequestParam("business") String business)
    throws IOException
  {
    String respMessage = "E99";
    Integer haveAcc = this.portalaccountService
      .getPortalaccountCount(new PortalaccountQuery());
    if ((haveAcc != null) && 
      (haveAcc.intValue() >= Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
      .getCoreConfigMap().get("core"))[1]).intValue()))
    {
      respMessage = "E90";
      PrintWriter out = response.getWriter();
      out.print(respMessage);
      out.close();
      return;
    }

    if (stringUtils.isNotBlank(business)) {
      String key = decodeBase64(business);
      System.out.println(key);
      if (stringUtils.isNotBlank(key)) {
        try {
          String[] keys = key.split("\\t");

          if (keys[0].equals("001")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              if (stringUtils.isNotEmpty(keys[2])) {
                if (stringUtils.isNotEmpty(keys[3])) {
                  if (stringUtils.isNotEmpty(keys[4])) {
                    if (stringUtils.isNotEmpty(keys[5])) {
                      if (stringUtils.isNotEmpty(keys[6]))
                      {
                        if (stringUtils.isNotEmpty(keys[7]))
                        {
                          if (stringUtils.isNotEmpty(keys[8]))
                          {
                            if (stringUtils.isNotEmpty(keys[9]))
                            {
                              if (stringUtils.isNotEmpty(keys[10]))
                              {
                                PortalaccountQuery aq = new PortalaccountQuery();
                                aq.setLoginName(keys[1]);
                                aq.setLoginNameLike(false);
                                List accs = this.portalaccountService
                                  .getPortalaccountList(aq);
                                if (accs.size() < 1) {
                                  Portalaccount acc = new Portalaccount();
                                  acc.setLoginName(keys[1]);
                                  acc.setPassword(keys[2]);
                                  acc.setName(keys[11]);
                                  acc.setPhoneNumber(keys[12]);

                                  boolean haveSpeed = false;
                                  PortalspeedQuery sq = new PortalspeedQuery();
                                  sq.setNameLike(false);
                                  sq.setName(keys[7]);
                                  List speeds = this.portalspeedService
                                    .getPortalspeedList(sq);
                                  if (speeds
                                    .size() == 1) {
                                    acc.setSpeed(
                                      ((Portalspeed)speeds
                                      .get(0))
                                      .getId());
                                    haveSpeed = true;
                                  } else {
                                    respMessage = "E07";
                                  }
                                  if (haveSpeed) {
                                    acc.setState(keys[3]);
                                    acc.setTime(
                                      Long.valueOf(keys[4]));
                                    SimpleDateFormat format = new SimpleDateFormat(
                                      "yyyy-MM-dd HH:mm:ss");
                                    Date date = format
                                      .parse(keys[5] + 
                                      " 23:59:59");
                                    acc.setDate(date);
                                    acc.setOctets(
                                      Long.valueOf(keys[6]));
                                    acc.setMaclimitcount(
                                      Integer.valueOf(keys[8]));
                                    acc.setMaclimit(
                                      Integer.valueOf(keys[9]));
                                    acc.setAutologin(
                                      Integer.valueOf(keys[10]));
                                    this.portalaccountService
                                      .addPortalaccount(acc);
                                    respMessage = "E00";
                                  }
                                } else {
                                  respMessage = "E20";
                                }
                              }
                              else {
                                respMessage = "E10";
                              }
                            }
                            else respMessage = "E09";
                          }
                          else
                            respMessage = "E08";
                        }
                        else
                          respMessage = "E07";
                      }
                      else
                        respMessage = "E06";
                    }
                    else
                      respMessage = "E05";
                  }
                  else
                    respMessage = "E04";
                }
                else
                  respMessage = "E03";
              }
              else
                respMessage = "E02";
            }
            else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("002")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              if (stringUtils.isNotEmpty(keys[2])) {
                if (stringUtils.isNotEmpty(keys[3])) {
                  if (stringUtils.isNotEmpty(keys[4])) {
                    if (stringUtils.isNotEmpty(keys[5])) {
                      if (stringUtils.isNotEmpty(keys[6]))
                      {
                        if (stringUtils.isNotEmpty(keys[7]))
                        {
                          if (stringUtils.isNotEmpty(keys[8]))
                          {
                            if (stringUtils.isNotEmpty(keys[9]))
                            {
                              if (stringUtils.isNotEmpty(keys[10]))
                              {
                                PortalaccountQuery aq = new PortalaccountQuery();
                                aq.setLoginName(keys[1]);
                                aq.setLoginNameLike(false);
                                List accs = this.portalaccountService
                                  .getPortalaccountList(aq);
                                if (accs.size() == 1) {
                                  Portalaccount acc = (Portalaccount)accs.get(0);
                                  kickAcc(acc.getId(), acc.getLoginName());

                                  acc.setPassword(keys[2]);
                                  acc.setName(keys[11]);
                                  acc.setPhoneNumber(keys[12]);

                                  boolean haveSpeed = false;
                                  PortalspeedQuery sq = new PortalspeedQuery();
                                  sq.setNameLike(false);
                                  sq.setName(keys[7]);
                                  List speeds = this.portalspeedService
                                    .getPortalspeedList(sq);
                                  if (speeds
                                    .size() == 1) {
                                    acc.setSpeed(
                                      ((Portalspeed)speeds
                                      .get(0))
                                      .getId());
                                    haveSpeed = true;
                                  } else {
                                    respMessage = "E07";
                                  }
                                  if (haveSpeed) {
                                    if (!"none".equals(keys[4])) {
                                      acc.setTime(
                                        Long.valueOf(keys[4]));
                                    }
                                    if (!"none".equals(keys[5])) {
                                      SimpleDateFormat format = new SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm:ss");
                                      Date date = format
                                        .parse(keys[5] + 
                                        " 23:59:59");
                                      acc.setDate(date);
                                    }
                                    if (!"none".equals(keys[5])) {
                                      acc.setOctets(
                                        Long.valueOf(keys[6]));
                                    }
                                    acc.setState(keys[3]);
                                    acc.setMaclimitcount(
                                      Integer.valueOf(keys[8]));
                                    acc.setMaclimit(
                                      Integer.valueOf(keys[9]));
                                    acc.setAutologin(
                                      Integer.valueOf(keys[10]));
                                    this.portalaccountService
                                      .updatePortalaccountByKey(acc);
                                    respMessage = "E00";
                                  }
                                } else {
                                  respMessage = "E30";
                                }
                              }
                              else {
                                respMessage = "E10";
                              }
                            }
                            else respMessage = "E09";
                          }
                          else
                            respMessage = "E08";
                        }
                        else
                          respMessage = "E07";
                      }
                      else
                        respMessage = "E06";
                    }
                    else
                      respMessage = "E05";
                  }
                  else
                    respMessage = "E04";
                }
                else
                  respMessage = "E03";
              }
              else
                respMessage = "E02";
            }
            else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("003")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
                macsq.setAccountId(acc.getId());
                this.macsService.deleteByQuery(macsq);
                this.portalaccountService.deleteByKey(acc.getId());
                respMessage = "E00";
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("004")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
                macsq.setAccountId(acc.getId());
                this.macsService.deleteByQuery(macsq);
                respMessage = "E00";
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("005")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                kickAcc(acc.getId(), acc.getLoginName());
                respMessage = "E00";
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }

          }

          if (keys[0].equals("006")) {
            if (stringUtils.isNotEmpty(keys[1])) {
              PortalaccountQuery aq = new PortalaccountQuery();
              aq.setLoginName(keys[1]);
              aq.setLoginNameLike(false);
              List accs = this.portalaccountService
                .getPortalaccountList(aq);
              if (accs.size() == 1) {
                Portalaccount acc = (Portalaccount)accs.get(0);
                respMessage = "E00\\t" + acc.toString();
              } else {
                respMessage = "E30";
              }
            } else {
              respMessage = "E01";
            }
          }
        }
        catch (Exception localException)
        {
        }
      }
    }
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.APIController
 * JD-Core Version:    0.6.2
 */