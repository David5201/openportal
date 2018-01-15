package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountmacsService;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/portalaccountmacsController")
public class PortalaccountmacsController
{

  @Autowired
  private PortalaccountmacsService portalaccountmacsService;

  @Autowired
  private PortalaccountService portalaccountService;

  @RequestMapping({"aclist.action"})
  public String page(PortalaccountmacsQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    Portalaccount account = this.portalaccountService.getPortalaccountByKey(query.getAccountId());
    if (stringUtils.isBlank(query.getMac()))
      query.setMac(null);
    else {
      query.setMacLike(true);
    }
    Pagination pagination = this.portalaccountmacsService.getPortalaccountmacsListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    model.addAttribute("account", account);
    return "portalaccount/maclist";
  }

  @RequestMapping({"acadd.action"})
  public String add(Portalaccountmacs e)
  {
    if (stringUtils.isNotBlank(e.getMac())) {
      this.portalaccountmacsService.addPortalaccountmacs(e);
    }
    return "redirect:aclist.action?accountId=" + e.getAccountId();
  }

  @ResponseBody
  @RequestMapping({"acedit.action"})
  public Map<String, String> edit(Portalaccountmacs e) {
    if (stringUtils.isBlank(e.getMac())) {
      e.setMac(null);
    }
    this.portalaccountmacsService.updatePortalaccountmacsByKey(e);
    Map map = new HashMap();
    map.put("ret", "0");
    return map;
  }

  @RequestMapping({"acdelete.action"})
  public String delete(@RequestParam Long id)
  {
    Long accountId = this.portalaccountmacsService.getPortalaccountmacsByKey(id).getAccountId();
    this.portalaccountmacsService.deleteByKey(id);
    return "redirect:aclist.action?accountId=" + accountId;
  }

  @RequestMapping(value={"acdeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    Long id = ids[0];
    Long accountId = this.portalaccountmacsService.getPortalaccountmacsByKey(id).getAccountId();
    List list = Arrays.asList(ids);
    this.portalaccountmacsService.deleteByKeys(list);

    return "redirect:aclist.action?accountId=" + accountId;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalaccountmacsController
 * JD-Core Version:    0.6.2
 */