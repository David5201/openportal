package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.query.PortalsmsapiQuery;
import com.leeson.core.service.PortalsmsapiService;
import com.leeson.core.utils.SMSAliRequest;
import com.leeson.core.utils.SMSEmppRequest;
import com.leeson.core.utils.SMSKaiLiRequest;
import com.leeson.core.utils.SMSOpenMasRequest;
import com.leeson.core.utils.SMSProRequest;
import com.leeson.core.utils.SMSRequest;
import com.leeson.core.utils.SMSSMGPAPIRequest;
import com.leeson.core.utils.SMSSUBMAILRequest;
import com.leeson.core.utils.SMSUMSRequest;
import com.leeson.core.utils.SMSVirtualRequest;
import com.leeson.portal.core.model.PhoneCodeMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/portalsmsapiController")
public class PortalsmsapiController
{

  @Autowired
  private PortalsmsapiService portalsmsapiService;

  @RequestMapping({"ist.action"})
  public String page(PortalsmsapiQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setNameLike(true);
    query.setStateLike(false);
    query.setUrlLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getUrl())) {
      query.setUrl(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    Pagination pagination = this.portalsmsapiService
      .getPortalsmsapiListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalsmsapi/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model)
  {
    return "portalsmsapi/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalsmsapi e)
  {
    int state = Integer.valueOf(e.getState()).intValue();
    if (state == 1) {
      List<Portalsmsapi> es = this.portalsmsapiService
        .getPortalsmsapiList(new PortalsmsapiQuery());
      for (Portalsmsapi ec : es) {
        ec.setState("0");
        this.portalsmsapiService.updatePortalsmsapiByKey(ec);
      }
    }

    if ("0".equals(e.getType())) {
      e.setUrl("http:pi2/sms/send.php");
    }
    if ("2".equals(e.getType())) {
      e.setUrl("http://127.0.0.1");
    }
    if ("3".equals(e.getType())) {
      e.setUrl("http:outer/rest");
    }
    if (("4".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("127.0.0.1:9981");
    }

    if ("5".equals(e.getType())) {
      e.setUrl("http:ms/Api/Send.do");
    }
    if (("6".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("http:penMasService");
    }

    if ("7".equals(e.getType())) {
      e.setUrl("http://127.0.0.1");
    }
    if (("8".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("http:msSend.do");
    }

    if (("9".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("127.0.0.1:8890");
    }

    if (("10".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("https:msJson.aspx");
    }

    if ("11".equals(e.getType())) {
      e.setUrl("http:gws/OrderServlet");
    }
    if (("12".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("http:pp/sdk/login");
    }

    this.portalsmsapiService.addPortalsmsapi(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portalsmsapi e = this.portalsmsapiService.getPortalsmsapiByKey(id);
    model.addAttribute("entity", e);
    return "portalsmsapi/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalsmsapi e)
  {
    int state = Integer.valueOf(e.getState()).intValue();
    if (state == 1) {
      List<Portalsmsapi> es = this.portalsmsapiService
        .getPortalsmsapiList(new PortalsmsapiQuery());
      for (Portalsmsapi ec : es) {
        ec.setState("0");
        this.portalsmsapiService.updatePortalsmsapiByKey(ec);
      }
    }

    if ("0".equals(e.getType())) {
      e.setUrl("http:pi2/sms/send.php");
    }
    if ("2".equals(e.getType())) {
      e.setUrl("http://127.0.0.1");
    }
    if ("3".equals(e.getType())) {
      e.setUrl("http:outer/rest");
    }
    if (("4".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("127.0.0.1:9981");
    }

    if ("5".equals(e.getType())) {
      e.setUrl("http:ms/Api/Send.do");
    }
    if (("6".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("http:penMasService");
    }

    if ("7".equals(e.getType())) {
      e.setUrl("http://127.0.0.1");
    }
    if (("8".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("http:msSend.do");
    }

    if (("9".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("127.0.0.1:8890");
    }

    if (("10".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("https:msJson.aspx");
    }

    if ("11".equals(e.getType())) {
      e.setUrl("http:gws/OrderServlet");
    }
    if (("12".equals(e.getType())) && 
      (stringUtils.isBlank(e.getUrl()))) {
      e.setUrl("http:pp/sdk/login");
    }

    this.portalsmsapiService.updatePortalsmsapiByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String editState(@RequestParam Long id, @RequestParam String to)
  {
    if (to.equals("state")) {
      Portalsmsapi e = this.portalsmsapiService.getPortalsmsapiByKey(id);
      int state = Integer.valueOf(e.getState()).intValue();
      if (state == 1) {
        Portalsmsapi ec = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
        ec.setState("1");
        this.portalsmsapiService.updatePortalsmsapiByKey(ec);
        if (e.getId().longValue() != 1L)
          e.setState("0");
      }
      else
      {
        List<Portalsmsapi> es = this.portalsmsapiService
          .getPortalsmsapiList(new PortalsmsapiQuery());
        for (Portalsmsapi ec : es) {
          ec.setState("0");
          this.portalsmsapiService.updatePortalsmsapiByKey(ec);
        }
        e.setState("1");
      }
      this.portalsmsapiService.updatePortalsmsapiByKey(e);
    }

    if (to.equals("more")) {
      Portalsmsapi e = this.portalsmsapiService.getPortalsmsapiByKey(id);
      int more = Integer.valueOf(e.getMore()).intValue();
      if (more == 1)
        e.setMore("0");
      else {
        e.setMore("1");
      }
      this.portalsmsapiService.updatePortalsmsapiByKey(e);
    }

    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    if (id.longValue() != 1L) {
      this.portalsmsapiService.deleteByKey(id);
    }
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    if (list.contains(Long.valueOf(1L))) {
      list.remove(Long.valueOf(1L));
    }
    this.portalsmsapiService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @ResponseBody
  @RequestMapping({"/sms/test.action"})
  public Map<String, String> test(String phone, HttpServletRequest request, HttpServletResponse response)
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
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalsmsapiController
 * JD-Core Version:    0.6.2
 */