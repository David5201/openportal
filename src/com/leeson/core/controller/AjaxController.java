package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortallogrecordQuery;
import com.leeson.core.query.PortalmessageQuery;
import com.leeson.core.query.PortalphonesQuery;
import com.leeson.core.query.PortalsmsapiQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.service.PortalmessageService;
import com.leeson.core.service.PortalphonesService;
import com.leeson.core.service.PortalsmsapiService;
import com.leeson.core.utils.SMSAliRequest;
import com.leeson.core.utils.SMSCMCCMASRequest;
import com.leeson.core.utils.SMSEmppRequest;
import com.leeson.core.utils.SMSIPYYRequest;
import com.leeson.core.utils.SMSKaiLiRequest;
import com.leeson.core.utils.SMSOpenMasRequest;
import com.leeson.core.utils.SMSProRequest;
import com.leeson.core.utils.SMSRequest;
import com.leeson.core.utils.SMSSMGPAPIRequest;
import com.leeson.core.utils.SMSSUBMAILRequest;
import com.leeson.core.utils.SMSUMSRequest;
import com.leeson.core.utils.SMSVirtualRequest;
import com.leeson.core.utils.SMSWXHYRequest;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.model.WifiDogOnlineMap;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ajaxController")
public class AjaxController
{

  @Autowired
  private PortalmessageService portalmessageService;

  @Autowired
  private PortallogrecordService portallogrecordService;

  @Autowired
  private PortalaccountService portalaccountService;

  @Autowired
  private PortalsmsapiService portalsmsapiService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalphonesService portalphonesService;

  @ResponseBody
  @RequestMapping({"/ajax_smsAPI_find"})
  public Map<String, String> find(String phone, HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();
    if ((stringUtils.isBlank(phone)) || (phone.length() != 11)) {
      map.put("ret", "1");
      map.put("msg", "手机号码不正确！");
      return map;
    }
    try {
      Long.parseLong(phone);
    } catch (Exception e) {
      map.put("ret", "1");
      map.put("msg", "手机号码不正确！");
      return map;
    }

    PortalsmsapiQuery query = new PortalsmsapiQuery();
    query.setState("1");
    List smsList = this.portalsmsapiService
      .getPortalsmsapiList(query);
    Long count = Long.valueOf(0L);
    String url = "";
    Long id = Long.valueOf(1L);
    String type = "0";
    String text = "";
    int time = 1;

    String key = "";
    String secret = "";
    String sign = "";
    String template = "";
    String company = "";

    if (smsList.size() > 0) {
      Portalsmsapi smsapi = (Portalsmsapi)smsList.get(0);
      count = smsapi.getCount();
      url = smsapi.getUrl();
      id = smsapi.getId();
      type = smsapi.getType();
      text = smsapi.getText();
      time = smsapi.getTime().intValue();

      key = smsapi.getAppkey();
      secret = smsapi.getAppsecret();
      sign = smsapi.getSmssign();
      template = smsapi.getSmstemplate();
      company = smsapi.getCompany();
    } else {
      Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
      count = smsapi.getCount();
      url = smsapi.getUrl();
      type = smsapi.getType();
      text = smsapi.getText();
      time = smsapi.getTime().intValue();

      key = smsapi.getAppkey();
      secret = smsapi.getAppsecret();
      sign = smsapi.getSmssign();
      template = smsapi.getSmstemplate();
      company = smsapi.getCompany();
    }

    if (stringUtils.isNotBlank(url)) {
      Boolean state = Boolean.valueOf(false);
      if ("0".equals(type)) {
        state = Boolean.valueOf(SMSRequest.send(url, phone, key, id));
      } else if ("2".equals(type)) {
        state = Boolean.valueOf(SMSVirtualRequest.send(phone));
        try {
          Object[] objs = 
            (Object[])PhoneCodeMap.getInstance()
            .getPhoneCodeMap().get(phone);
          String yzm = (String)objs[0];
          if (stringUtils.isNotBlank(text)) {
            if ("[yzm]".equals(text)) {
              text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + time + 
                "分钟! 【" + company + "】提供!";
            }
            else if (text.contains("[yzm]"))
              text = text.replace("[yzm]", yzm);
            else {
              text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + 
                time + "分钟! 【" + company + "】提供!";
            }
          }
          else {
            text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + time + 
              "分钟! 【" + company + "】提供!";
          }
          map.put("msg", text);
        } catch (Exception e) {
          map.put("ret", "1");
          map.put("msg", "获取验证码失败，请使用其他方式或重试！");
        }
      } else if ("3".equals(type)) {
        state = Boolean.valueOf(SMSAliRequest.send(url, phone, key, secret, sign, 
          template, company, id));
      } else if ("4".equals(type)) {
        state = Boolean.valueOf(SMSEmppRequest.send(1, url, key, secret, template, 
          text, sign, company, phone, time, id));
      } else if ("5".equals(type)) {
        state = Boolean.valueOf(SMSUMSRequest.send(url, key, secret, template, text, 
          phone, id));
      } else if ("6".equals(type)) {
        state = Boolean.valueOf(SMSOpenMasRequest.send(1, url, key, secret, template, 
          text, sign, company, phone, time, id));
      } else if ("7".equals(type)) {
        state = Boolean.valueOf(SMSSUBMAILRequest.send(key, secret, sign, template, 
          phone, id));
      } else if ("8".equals(type)) {
        state = Boolean.valueOf(SMSKaiLiRequest.send(1, url, key, secret, text, sign, 
          company, phone, time, id));
      } else if ("9".equals(type)) {
        state = Boolean.valueOf(SMSSMGPAPIRequest.send(url, key, secret, sign, 
          template, text, phone, id));
      } else if ("10".equals(type)) {
        state = Boolean.valueOf(SMSIPYYRequest.send(url, key, secret, text, phone, id));
      } else if ("11".equals(type)) {
        state = Boolean.valueOf(SMSWXHYRequest.send(url, key, secret, text, phone, id));
      } else if ("12".equals(type)) {
        state = Boolean.valueOf(SMSCMCCMASRequest.send(1, url, key, secret, company, template, text, sign, phone, time, id));
      } else {
        HttpSession session = request.getSession();
        String mac = (String)session.getAttribute("ikmac");
        state = Boolean.valueOf(SMSProRequest.send(url, phone, mac, text, request, id));
      }

      if (state.booleanValue()) {
        count = Long.valueOf(count.longValue() + 1L);
        Portalsmsapi smsapi = new Portalsmsapi();
        smsapi.setId(id);
        smsapi.setCount(count);
        this.portalsmsapiService.updatePortalsmsapiByKey(smsapi);
        map.put("ret", "0");
      } else {
        map.put("ret", "1");
        map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
      }
    } else {
      map.put("ret", "1");
      map.put("msg", "短信网关配置出错，请联系管理员！");
    }
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_smsAPI_regist"})
  public Map<String, String> regist(String phone, HttpServletRequest request, HttpServletResponse response) {
    Map map = new HashMap();
    if ((stringUtils.isBlank(phone)) || (phone.length() != 11)) {
      map.put("ret", "1");
      map.put("msg", "手机号码不正确！");
      return map;
    }
    try {
      Long.parseLong(phone);
    } catch (Exception e) {
      map.put("ret", "1");
      map.put("msg", "手机号码不正确！");
      return map;
    }

    PortalsmsapiQuery query = new PortalsmsapiQuery();
    query.setState("1");
    List smsList = this.portalsmsapiService
      .getPortalsmsapiList(query);
    Long count = Long.valueOf(0L);
    String url = "";
    Long id = Long.valueOf(1L);
    String type = "0";
    String text = "";
    int time = 1;

    String key = "";
    String secret = "";
    String sign = "";
    String template = "";
    String company = "";

    if (smsList.size() > 0) {
      Portalsmsapi smsapi = (Portalsmsapi)smsList.get(0);
      count = smsapi.getCount();
      url = smsapi.getUrl();
      id = smsapi.getId();
      type = smsapi.getType();
      text = smsapi.getText();
      time = smsapi.getTime().intValue();

      key = smsapi.getAppkey();
      secret = smsapi.getAppsecret();
      sign = smsapi.getSmssign();
      template = smsapi.getSmstemplate();
      company = smsapi.getCompany();
    } else {
      Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
      count = smsapi.getCount();
      url = smsapi.getUrl();
      type = smsapi.getType();
      text = smsapi.getText();
      time = smsapi.getTime().intValue();

      key = smsapi.getAppkey();
      secret = smsapi.getAppsecret();
      sign = smsapi.getSmssign();
      template = smsapi.getSmstemplate();
      company = smsapi.getCompany();
    }

    if (2 == this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue()) {
      PortalaccountQuery aq = new PortalaccountQuery();
      aq.setLoginName(phone);
      aq.setLoginNameLike(false);
      if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
        map.put("ret", "1");
        map.put("msg", "当前手机号已注册！");
        return map;
      }
    }

    if (stringUtils.isNotBlank(url)) {
      Boolean state = Boolean.valueOf(false);
      if ("0".equals(type)) {
        state = Boolean.valueOf(SMSRequest.send(url, phone, key, id));
      } else if ("2".equals(type)) {
        state = Boolean.valueOf(SMSVirtualRequest.send(phone));
        try {
          Object[] objs = 
            (Object[])PhoneCodeMap.getInstance()
            .getPhoneCodeMap().get(phone);
          String yzm = (String)objs[0];
          if (stringUtils.isNotBlank(text)) {
            if ("[yzm]".equals(text)) {
              text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
                "分钟! 【" + company + "】提供!";
            }
            else if (text.contains("[yzm]"))
              text = text.replace("[yzm]", yzm);
            else {
              text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + 
                time + "分钟! 【" + company + "】提供!";
            }
          }
          else {
            text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
              "分钟! 【" + company + "】提供!";
          }
          map.put("msg", text);
        } catch (Exception e) {
          map.put("ret", "1");
          map.put("msg", "获取验证码失败，请使用其他方式或重试！");
        }
      } else if ("3".equals(type)) {
        state = Boolean.valueOf(SMSAliRequest.send(url, phone, key, secret, sign, 
          template, company, id));
      } else if ("4".equals(type)) {
        state = Boolean.valueOf(SMSEmppRequest.send(1, url, key, secret, template, 
          text, sign, company, phone, time, id));
      } else if ("5".equals(type)) {
        state = Boolean.valueOf(SMSUMSRequest.send(url, key, secret, template, text, 
          phone, id));
      } else if ("6".equals(type)) {
        state = Boolean.valueOf(SMSOpenMasRequest.send(1, url, key, secret, template, 
          text, sign, company, phone, time, id));
      } else if ("7".equals(type)) {
        state = Boolean.valueOf(SMSSUBMAILRequest.send(key, secret, sign, template, 
          phone, id));
      } else if ("8".equals(type)) {
        state = Boolean.valueOf(SMSKaiLiRequest.send(1, url, key, secret, text, sign, 
          company, phone, time, id));
      } else if ("9".equals(type)) {
        state = Boolean.valueOf(SMSSMGPAPIRequest.send(url, key, secret, sign, 
          template, text, phone, id));
      } else if ("10".equals(type)) {
        state = Boolean.valueOf(SMSIPYYRequest.send(url, key, secret, text, phone, id));
      } else if ("11".equals(type)) {
        state = Boolean.valueOf(SMSWXHYRequest.send(url, key, secret, text, phone, id));
      } else if ("12".equals(type)) {
        state = Boolean.valueOf(SMSCMCCMASRequest.send(1, url, key, secret, company, template, text, sign, phone, time, id));
      } else {
        HttpSession session = request.getSession();
        String mac = (String)session.getAttribute("ikmac");
        state = Boolean.valueOf(SMSProRequest.send(url, phone, mac, text, request, id));
      }

      if (state.booleanValue()) {
        count = Long.valueOf(count.longValue() + 1L);
        Portalsmsapi smsapi = new Portalsmsapi();
        smsapi.setId(id);
        smsapi.setCount(count);
        this.portalsmsapiService.updatePortalsmsapiByKey(smsapi);
        map.put("ret", "0");
      } else {
        map.put("ret", "1");
        map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
      }
    } else {
      map.put("ret", "1");
      map.put("msg", "短信网关配置出错，请联系管理员！");
    }
    return map;
  }

  @ResponseBody
  @RequestMapping({"/ajax_smsAPI"})
  public Map<String, String> weixinsms(String phone, HttpServletRequest request, HttpServletResponse response) {
    Map map = new HashMap();
    if ((stringUtils.isBlank(phone)) || (phone.length() != 11)) {
      map.put("ret", "1");
      map.put("msg", "手机号码不正确！");
      return map;
    }
    try {
      Long.parseLong(phone);
    } catch (Exception e) {
      map.put("ret", "1");
      map.put("msg", "手机号码不正确！");
      return map;
    }

    if (1 == this.configService.getConfigByKey(Long.valueOf(1L)).getSmsAuthList().intValue()) {
      PortalphonesQuery phonesQuery = new PortalphonesQuery();
      phonesQuery.setPhone(phone);
      phonesQuery.setPhoneLike(false);
      int isCanAuth = this.portalphonesService.getPortalphonesCount(phonesQuery).intValue();
      if (isCanAuth <= 0) {
        map.put("ret", "1");
        map.put("msg", "当前手机号未授权，请联系管理员！");
        return map;
      }
    }

    PortalsmsapiQuery query = new PortalsmsapiQuery();
    query.setState("1");
    List smsList = this.portalsmsapiService
      .getPortalsmsapiList(query);
    Long count = Long.valueOf(0L);
    String url = "";
    Long id = Long.valueOf(1L);
    String type = "0";
    String more = "0";
    String text = "";
    int time = 1;

    String key = "";
    String secret = "";
    String sign = "";
    String template = "";
    String company = "";

    if (smsList.size() > 0) {
      Portalsmsapi smsapi = (Portalsmsapi)smsList.get(0);
      count = smsapi.getCount();
      url = smsapi.getUrl();
      id = smsapi.getId();
      type = smsapi.getType();
      more = smsapi.getMore();
      text = smsapi.getText();
      time = smsapi.getTime().intValue();

      key = smsapi.getAppkey();
      secret = smsapi.getAppsecret();
      sign = smsapi.getSmssign();
      template = smsapi.getSmstemplate();
      company = smsapi.getCompany();
    } else {
      Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
      count = smsapi.getCount();
      url = smsapi.getUrl();
      type = smsapi.getType();
      more = smsapi.getMore();
      text = smsapi.getText();
      time = smsapi.getTime().intValue();

      key = smsapi.getAppkey();
      secret = smsapi.getAppsecret();
      sign = smsapi.getSmssign();
      template = smsapi.getSmstemplate();
      company = smsapi.getCompany();
    }

    if ("0".equals(more)) {
      Boolean isOnline = Boolean.valueOf(false);
      Iterator it = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
      while (it.hasNext()) {
        Object o = it.next();
        String host = o.toString();
        String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(host);
        String username = loginInfo[0];
        if (phone.equals(username)) {
          isOnline = Boolean.valueOf(true);
          break;
        }
      }
      if (isOnline.booleanValue()) {
        map.put("ret", "1");
        map.put("msg", "当前手机号已在线！");
        return map;
      }
    }

    if (stringUtils.isNotBlank(url)) {
      Boolean state = Boolean.valueOf(false);
      if ("0".equals(type)) {
        state = Boolean.valueOf(SMSRequest.send(url, phone, key, id));
      } else if ("2".equals(type)) {
        state = Boolean.valueOf(SMSVirtualRequest.send(phone));
        try {
          Object[] objs = 
            (Object[])PhoneCodeMap.getInstance()
            .getPhoneCodeMap().get(phone);
          String yzm = (String)objs[0];
          if (stringUtils.isNotBlank(text)) {
            if ("[yzm]".equals(text)) {
              text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
                "分钟! 【" + company + "】提供!";
            }
            else if (text.contains("[yzm]"))
              text = text.replace("[yzm]", yzm);
            else {
              text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + 
                time + "分钟! 【" + company + "】提供!";
            }
          }
          else {
            text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
              "分钟! 【" + company + "】提供!";
          }
          map.put("msg", text);
        } catch (Exception e) {
          map.put("ret", "1");
          map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
        }
      } else if ("3".equals(type)) {
        state = Boolean.valueOf(SMSAliRequest.send(url, phone, key, secret, sign, 
          template, company, id));
      } else if ("4".equals(type)) {
        state = Boolean.valueOf(SMSEmppRequest.send(0, url, key, secret, template, 
          text, sign, company, phone, time, id));
      } else if ("5".equals(type)) {
        state = Boolean.valueOf(SMSUMSRequest.send(url, key, secret, template, text, 
          phone, id));
      } else if ("6".equals(type)) {
        state = Boolean.valueOf(SMSOpenMasRequest.send(0, url, key, secret, template, 
          text, sign, company, phone, time, id));
      } else if ("7".equals(type)) {
        state = Boolean.valueOf(SMSSUBMAILRequest.send(key, secret, sign, template, 
          phone, id));
      } else if ("8".equals(type)) {
        state = Boolean.valueOf(SMSKaiLiRequest.send(0, url, key, secret, text, sign, 
          company, phone, time, id));
      } else if ("9".equals(type)) {
        state = Boolean.valueOf(SMSSMGPAPIRequest.send(url, key, secret, sign, 
          template, text, phone, id));
      } else if ("10".equals(type)) {
        state = Boolean.valueOf(SMSIPYYRequest.send(url, key, secret, text, phone, id));
      } else if ("11".equals(type)) {
        state = Boolean.valueOf(SMSWXHYRequest.send(url, key, secret, text, phone, id));
      } else if ("12".equals(type)) {
        state = Boolean.valueOf(SMSCMCCMASRequest.send(0, url, key, secret, company, template, text, sign, phone, time, id));
      } else {
        HttpSession session = request.getSession();
        String mac = (String)session.getAttribute("ikmac");
        state = Boolean.valueOf(SMSProRequest.send(url, phone, mac, text, request, id));
      }

      if (state.booleanValue()) {
        count = Long.valueOf(count.longValue() + 1L);
        Portalsmsapi smsapi = new Portalsmsapi();
        smsapi.setId(id);
        smsapi.setCount(count);
        this.portalsmsapiService.updatePortalsmsapiByKey(smsapi);
        map.put("ret", "0");
      } else {
        map.put("ret", "1");
        map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
      }
    } else {
      map.put("ret", "1");
      map.put("msg", "短信网关配置出错，请联系管理员！");
    }
    return map;
  }

  @ResponseBody
  @RequestMapping({"/get_ajax_onlineCount"})
  public Map<String, Object> onlineCount(HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();

    int online_count = OnlineMap.getInstance().getOnlineUserMap().size() + 
      WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().size();
    map.put("online_count", Integer.valueOf(online_count));

    return map;
  }

  @ResponseBody
  @RequestMapping({"/get_ajax_fenleiCount"})
  public Map<String, Object> fenleiCount()
  {
    Map map = new HashMap();

    PortalaccountQuery pq = new PortalaccountQuery();
    pq.setState("0");

    int lock_count = this.portalaccountService.getPortalaccountCount(pq).intValue();
    int acc_count = this.portalaccountService
      .getPortalaccountCount(new PortalaccountQuery()).intValue();

    int online_count = OnlineMap.getInstance().getOnlineUserMap().size() + 
      WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().size();

    map.put("online_count", Integer.valueOf(online_count));
    map.put("acc_count", Integer.valueOf(acc_count));
    map.put("lock_count", Integer.valueOf(lock_count));
    map.put("true_count", Integer.valueOf(acc_count - lock_count));

    int outline_count = acc_count - online_count;
    if (outline_count < 0) {
      outline_count = 0;
    }
    map.put("outline_count", Integer.valueOf(outline_count));

    return map;
  }

  @ResponseBody
  @RequestMapping({"/get_ajax_msgCount"})
  public Map<String, Object> msgCount(HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();
    Portaluser user = (Portaluser)request.getSession()
      .getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      map.put("msgCount", Integer.valueOf(0));
      return map;
    }
    String toid = user.getId().toString();
    PortalmessageQuery message = new PortalmessageQuery();
    message.setToid(toid);
    message.setState("0");
    message.setDelin("0");
    message.setToPos("0");
    map.put("msgCount", this.portalmessageService.getPortalmessageCount(message));
    return map;
  }

  @ResponseBody
  @RequestMapping({"/get_ajax_log"})
  public Map<String, String> log(HttpServletRequest request, HttpServletResponse response)
  {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Map map = new LinkedHashMap();
    PortallogrecordQuery logrecordQuery = new PortallogrecordQuery();
    logrecordQuery.setPageSize(7);
    logrecordQuery.setPage(1);
    logrecordQuery.orderbyId(false);

    List<Portallogrecord> operationRecords = (List<Portallogrecord>) this.portallogrecordService.getPortallogrecordListWithPage(logrecordQuery).getList();
    int i = 7;
    for (Portallogrecord log : operationRecords) {
      String t = format.format(log.getRecDate());
      t = t + "." + i--;
      map.put(t, log.getInfo());
    }
    return map;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxController
 * JD-Core Version:    0.6.2
 */