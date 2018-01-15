package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.query.PortalsmsapiQuery;
import com.leeson.core.query.PortalsmslogsQuery;
import com.leeson.core.service.PortalsmsapiService;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.core.utils.HttpsUtils;
import com.leeson.portal.core.model.Config;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portalsmslogsController")
public class PortalsmslogsController
{

  @Autowired
  private PortalsmslogsService portalsmslogsService;

  @Autowired
  private PortalsmsapiService portalsmsapiService;
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(PortalsmslogsController.class);

  @RequestMapping({"ist.action"})
  public String page(PortalsmslogsQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setInfoLike(true);
    query.setPhoneLike(true);
    query.setTypeLike(false);
    if (stringUtils.isBlank(query.getInfo())) {
      query.setInfo(null);
    }
    if (stringUtils.isBlank(query.getPhone())) {
      query.setPhone(null);
    }
    if (stringUtils.isBlank(query.getType())) {
      query.setType(null);
    }
    query.orderbyId(false);
    Pagination pagination = this.portalsmslogsService.getPortalsmslogsListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    PortalsmsapiQuery q = new PortalsmsapiQuery();
    q.setState("1");
    List smsList = this.portalsmsapiService.getPortalsmsapiList(q);
    Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
    if (smsList.size() > 0) {
      smsapi = (Portalsmsapi)smsList.get(0);
    }
    if ("10".equals(smsapi.getType())) {
      String url = "https:msJson.aspx";
      String params = "action=overage&userid=&account=" + smsapi.getAppkey() + "&password=" + smsapi.getAppsecret();
      String result = HttpsUtils.httpsRequest(url, "POST", params);
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("SMS Send Result = " + result);
      }
      if (result.contains("Success")) {
        JSONObject jsonObject = null;
        jsonObject = JSONObject.fromObject(result);
        String overage = jsonObject.getString("overage");
        String sendTotal = jsonObject.getString("sendTotal");
        model.addAttribute("overage", overage);
        model.addAttribute("sendTotal", sendTotal);
      }
    }
    return "portalsmslogs/list";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portalsmslogsService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portalsmslogsService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.portalsmslogsService.deleteAll();
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalsmslogsController
 * JD-Core Version:    0.6.2
 */