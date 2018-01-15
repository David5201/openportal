package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalmessage;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalmessageQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalmessageService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.utils.MyUtils;
import java.util.Arrays;
import java.util.Date;
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
@RequestMapping("/portalcustomerMsgController")
public class PortalcustomerMsgController
{

  @Autowired
  private PortalmessageService portalmessageService;

  @Autowired
  private PortaluserService portaluserService;

  @Autowired
  private PortalaccountService portalaccountService;

  @RequestMapping({"/customerMsgListIn.action"})
  public String pageIn(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    query.setToid(user.getId().toString());
    query.setToPos("1");
    query.setDelin("0");
    query.orderbyState(true);
    query.orderbyDate(false);
    query.setDescriptionLike(true);
    query.setTitleLike(true);
    if (stringUtils.isBlank(query.getTitle())) {
      query.setTitle(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    Pagination pagination = this.portalmessageService
      .getPortalmessageListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalcustomerMsg/listIn";
  }

  @RequestMapping({"/customerMsgShowIn.action"})
  public String showIn(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    if ((!message.getToid().equals(String.valueOf(user.getId()))) || 
      (!message
      .getToPos().equals("1"))) {
      model.addAttribute("msg", "非法请求！！");
      return "portalcustomerMsg/showIn";
    }
    message.setState("1");
    this.portalmessageService.updatePortalmessageByKey(message);
    model.addAttribute("entity", message);
    return "portalcustomerMsg/showIn";
  }

  @RequestMapping({"/customerMsgListOut.action"})
  public String pageOut(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    query.setFromid(user.getId().toString());
    query.setFromPos("1");
    query.setDelout("0");
    query.orderbyState(true);
    query.orderbyDate(false);
    query.setDescriptionLike(true);
    query.setTitleLike(true);
    if (stringUtils.isBlank(query.getTitle())) {
      query.setTitle(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    Pagination pagination = this.portalmessageService
      .getPortalmessageListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalcustomerMsg/listOut";
  }

  @RequestMapping({"/customerMsgShowTo.action"})
  public String showTo(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    if ((!message.getFromid().equals(String.valueOf(user.getId()))) || 
      (!message
      .getFromPos().equals("1"))) {
      model.addAttribute("msg", "非法请求！！");
      return "portalcustomerMsg/showTo";
    }
    model.addAttribute("entity", message);
    return "portalcustomerMsg/showTo";
  }

  @RequestMapping({"/customerMsgEdit.action"})
  public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    if ((message.getToid().equals(String.valueOf(user.getId()))) && 
      (message.getToPos().equals("1"))) {
      if (message.getState().equals("1"))
        message.setState("0");
      else {
        message.setState("1");
      }
      this.portalmessageService.updatePortalmessageByKey(message);
    }

    return "redirect:/customerMsgListIn.action";
  }

  @RequestMapping({"/customerMsgIndelete.action"})
  public String Indelete(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    if ((message.getToid().equals(String.valueOf(user.getId()))) && 
      (message.getToPos().equals("1"))) {
      message.setDelin("1");
      this.portalmessageService.updatePortalmessageByKey(message);
    }

    return "redirect:/customerMsgListIn.action";
  }

  @RequestMapping(value={"/customerMsgIndeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String Indeletes(@RequestParam Long[] ids, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    List list = Arrays.asList(ids);
    List<Portalmessage> messages = this.portalmessageService
      .getPortalmessageByKeys(list);
    for (Portalmessage message : messages) {
      if ((message.getToid().equals(String.valueOf(user.getId()))) && 
        (message.getToPos().equals("1"))) {
        message.setDelin("1");
        this.portalmessageService.updatePortalmessageByKey(message);
      }
    }

    return "redirect:/customerMsgListIn.action";
  }

  @RequestMapping({"/customerMsgOutdelete.action"})
  public String Outdelete(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    if ((message.getFromid().equals(String.valueOf(user.getId()))) && 
      (message.getFromPos().equals("1"))) {
      message.setDelout("1");
      this.portalmessageService.updatePortalmessageByKey(message);
    }

    return "redirect:/customerMsgListOut.action";
  }

  @RequestMapping(value={"/customerMsgOutdeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String Outdeletes(@RequestParam Long[] ids, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    List list = Arrays.asList(ids);
    List<Portalmessage> messages = this.portalmessageService
      .getPortalmessageByKeys(list);
    for (Portalmessage message : messages) {
      if ((message.getFromid().equals(String.valueOf(user.getId()))) && 
        (message.getFromPos().equals("1"))) {
        message.setDelout("1");
        this.portalmessageService.updatePortalmessageByKey(message);
      }
    }

    return "redirect:/customerMsgListOut.action";
  }

  @RequestMapping(value={"/customerMsgAdd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String add(Long toid, String toPos, ModelMap model)
  {
    List users = this.portaluserService
      .getPortaluserList(new PortaluserQuery());

    Portalmessage e = new Portalmessage();
    if (stringUtils.isNotBlank(toPos)) {
      e.setToPos("0");
      e.setToid(toid.toString());
    }

    model.addAttribute("users", users);
    model.addAttribute("entity", e);
    return "portalcustomerMsg/save";
  }

  @RequestMapping(value={"/customerMsgAdd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalmessage e, HttpServletRequest request, ModelMap model)
  {
    e.setToPos("0");
    if (stringUtils.isBlank(e.getToid())) {
      model.addAttribute("msg", "请先选择用户！！");
      List users = this.portaluserService
        .getPortaluserList(new PortaluserQuery());
      model.addAttribute("users", users);
      e.setToid(null);
      model.addAttribute("entity", e);
      return "portalcustomerMsg/save";
    }

    if ((stringUtils.isBlank(e.getTitle())) || 
      (stringUtils.isBlank(e.getDescription()))) {
      model.addAttribute("msg", "消息标题和内容不能为空！");
      List users = this.portaluserService
        .getPortaluserList(new PortaluserQuery());
      model.addAttribute("users", users);
      model.addAttribute("entity", e);
      return "portalcustomerMsg/save";
    }

    e.setTitle(e.getTitle().replace(",", " "));
    e.setTitle(e.getTitle().replace(".", " "));
    e.setTitle(e.getTitle().replace("!", " "));
    e.setTitle(e.getTitle().replace("?", " "));
    e.setTitle(e.getTitle().replace(";", " "));
    e.setTitle(e.getTitle().replace("，", " "));
    e.setTitle(e.getTitle().replace("。", " "));
    e.setTitle(e.getTitle().replace("！", " "));
    e.setTitle(e.getTitle().replace("？", " "));
    e.setTitle(e.getTitle().replace("；", " "));

    e.setDescription(e.getDescription().replace(",", " "));
    e.setDescription(e.getDescription().replace(".", " "));
    e.setDescription(e.getDescription().replace("!", " "));
    e.setDescription(e.getDescription().replace("?", " "));
    e.setDescription(e.getDescription().replace(";", " "));
    e.setDescription(e.getDescription().replace("，", " "));
    e.setDescription(e.getDescription().replace("。", " "));
    e.setDescription(e.getDescription().replace("！", " "));
    e.setDescription(e.getDescription().replace("？", " "));
    e.setDescription(e.getDescription().replace("；", " "));
    try
    {
      if (!MyUtils.checkInput(e.getTitle())) {
        model.addAttribute("msg", "消息标题不符合规范！");
        List users = this.portaluserService
          .getPortaluserList(new PortaluserQuery());
        model.addAttribute("users", users);
        model.addAttribute("entity", e);
        return "portalmessage/sendUI";
      }
    } catch (Exception ex) {
      model.addAttribute("msg", "消息标题不符合规范！");
      List users = this.portaluserService
        .getPortaluserList(new PortaluserQuery());
      model.addAttribute("users", users);
      model.addAttribute("entity", e);
      return "portalmessage/sendUI";
    }
    try {
      if (!MyUtils.checkInput(e.getDescription())) {
        model.addAttribute("msg", "消息内容不符合规范！");
        List users = this.portaluserService
          .getPortaluserList(new PortaluserQuery());
        model.addAttribute("users", users);
        model.addAttribute("entity", e);
        return "portalmessage/sendUI";
      }
    } catch (Exception ex) {
      model.addAttribute("msg", "消息内容不符合规范！");
      List users = this.portaluserService
        .getPortaluserList(new PortaluserQuery());
      model.addAttribute("users", users);
      model.addAttribute("entity", e);
      return "portalmessage/sendUI";
    }

    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    String toUserName = null;
    if (e.getToPos().equals("0")) {
      toUserName = this.portaluserService.getPortaluserByKey(
        Long.valueOf(e.getToid())).getLoginName();
    }
    if (e.getToPos().equals("1")) {
      toUserName = this.portalaccountService.getPortalaccountByKey(
        Long.valueOf(e.getToid())).getLoginName();
    }
    e.setDate(new Date());
    e.setDelin("0");
    e.setDelout("0");
    e.setFromid(user.getId().toString());
    e.setFromname(user.getLoginName());
    e.setFromPos("1");
    e.setIp(request.getRemoteHost());
    e.setState("0");
    e.setToname(toUserName);

    this.portalmessageService.addPortalmessage(e);

    return "redirect:/customerMsgListOut.action";
  }

  @ResponseBody
  @RequestMapping({"/newMsg"})
  public Map<String, Object> msgCount(HttpServletRequest request, HttpServletResponse response) {
    Map map = new HashMap();

    HttpSession session = request.getSession();

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || 
      (stringUtils.isBlank(pwd))) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      map.put("msg", "登录信息丢失，请重新登录！！");
      return map;
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      session.removeAttribute("username");
      session.removeAttribute("password");
      session.removeAttribute("ip");
      map.put("msg", "登录信息丢失，请重新登录！！");
      return map;
    }
    Portalaccount user = (Portalaccount)accounts.get(0);

    if ((user == null) || (user.getId() == null)) {
      map.put("msg", "登录信息丢失，请重新登录！！");
      return map;
    }
    String toid = user.getId().toString();
    PortalmessageQuery message = new PortalmessageQuery();
    message.setToid(toid);
    message.setState("0");
    message.setDelin("0");
    message.setToPos("1");
    int count = this.portalmessageService.getPortalmessageCount(message).intValue();
    if (count > 0) {
      map.put("msg", "你有" + count + "条未读消息！！！");
      map.put("newMsg", " 【" + count + "条未读】");
    } else {
      map.put("msg", Integer.valueOf(0));
    }
    return map;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalcustomerMsgController
 * JD-Core Version:    0.6.2
 */