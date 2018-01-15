package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.bean.Portalmessage;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalmessageQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalcardService;
import com.leeson.core.service.PortalmessageService;
import com.leeson.core.service.PortalorderService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.utils.MyUtils;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/portalmessageController")
public class PortalmessageController
{

  @Autowired
  private PortalmessageService portalmessageService;

  @Autowired
  private PortaluserService portaluserService;

  @Autowired
  private PortalaccountService portalaccountService;

  @Autowired
  private PortalcardService portalcardService;

  @Autowired
  private PortalorderService portalorderService;

  @RequestMapping({"istIn.action"})
  public String pageIn(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
  {
    Portaluser user = (Portaluser)request.getSession()
      .getAttribute("user");
    query.setToid(user.getId().toString());
    query.setToPos("0");
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
    return "portalmessage/listIn";
  }

  @RequestMapping({"howIn.action"})
  public String showIn(@RequestParam Long id, ModelMap model)
  {
    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    message.setState("1");
    this.portalmessageService.updatePortalmessageByKey(message);
    model.addAttribute("entity", message);
    return "portalmessage/showIn";
  }

  @RequestMapping({"istOut.action"})
  public String pageOut(PortalmessageQuery query, HttpServletRequest request, ModelMap model)
  {
    Portaluser user = (Portaluser)request.getSession()
      .getAttribute("user");
    query.setFromid(user.getId().toString());
    query.setFromPos("0");
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
    return "portalmessage/listOut";
  }

  @RequestMapping({"howTo.action"})
  public String showTo(@RequestParam Long id, ModelMap model)
  {
    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    model.addAttribute("entity", message);
    return "portalmessage/showTo";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id)
  {
    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    if (message.getState().equals("1"))
      message.setState("0");
    else {
      message.setState("1");
    }
    this.portalmessageService.updatePortalmessageByKey(message);
    return "redirect:istIn.action";
  }

  @RequestMapping({"ndelete.action"})
  public String Indelete(@RequestParam Long id)
  {
    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    message.setDelin("1");
    this.portalmessageService.updatePortalmessageByKey(message);
    return "redirect:istIn.action";
  }

  @RequestMapping(value={"ndeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String Indeletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    List<Portalmessage> messages = this.portalmessageService
      .getPortalmessageByKeys(list);
    for (Portalmessage message : messages) {
      message.setDelin("1");
      this.portalmessageService.updatePortalmessageByKey(message);
    }
    return "redirect:istIn.action";
  }

  @RequestMapping({"utdelete.action"})
  public String Outdelete(@RequestParam Long id)
  {
    Portalmessage message = this.portalmessageService.getPortalmessageByKey(id);
    message.setDelout("1");
    this.portalmessageService.updatePortalmessageByKey(message);
    return "redirect:istOut.action";
  }

  @RequestMapping(value={"utdeletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String Outdeletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    List<Portalmessage> messages = this.portalmessageService
      .getPortalmessageByKeys(list);
    for (Portalmessage message : messages) {
      message.setDelout("1");
      this.portalmessageService.updatePortalmessageByKey(message);
    }
    return "redirect:istOut.action";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String add(Long toid, String toPos, Long cardId, ModelMap model)
  {
    List users = this.portaluserService
      .getPortaluserList(new PortaluserQuery());
    List accounts = this.portalaccountService
      .getPortalaccountList(new PortalaccountQuery());
    Portalmessage e = new Portalmessage();
    if (stringUtils.isNotBlank(toPos)) {
      e.setToPos(toPos);
    }
    if ((toid != null) && (toid.longValue() != 0L)) {
      e.setToid(toid.toString());
    }

    if ((stringUtils.isNotBlank(toPos)) && (toPos.equals("0"))) {
      model.addAttribute("users", users);
    }
    if ((stringUtils.isNotBlank(toPos)) && (toPos.equals("1"))) {
      model.addAttribute("users", accounts);
    }

    if ((cardId != null) && (cardId.longValue() != 0L)) {
      Portalcard card = this.portalcardService.getPortalcardByKey(cardId);
      e.setTitle("充值卡CD-KEY");
      String payType = card.getPayType();
      String type = "";
      if ("2".equals(payType)) {
        type = "计时";
      }
      if ("3".equals(payType)) {
        type = "买断";
      }
      e.setDescription("充值卡CD-KEY:【" + card.getCdKey() + "】  名称:【" + card.getName() + "】  类型:【" + type + "】  详细信息:【" + card.getDescription() + "】");
      model.addAttribute("cardId", cardId);
    }

    model.addAttribute("entity", e);
    return "portalmessage/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalmessage e, Long cardId, HttpServletRequest request, ModelMap model)
  {
    if (stringUtils.isBlank(e.getToPos())) {
      model.addAttribute("msg", "请先选择用户分类和用户！！");
      e.setToPos(null);
      e.setToid(null);
      model.addAttribute("entity", e);
      return "portalmessage/save";
    }
    if (stringUtils.isBlank(e.getToid())) {
      model.addAttribute("msg", "请先选择用户！！");
      List users = this.portaluserService
        .getPortaluserList(new PortaluserQuery());
      List accounts = this.portalaccountService
        .getPortalaccountList(new PortalaccountQuery());
      if ((e.getToPos() != null) && (e.getToPos().equals("0"))) {
        model.addAttribute("users", users);
      }
      if ((e.getToPos() != null) && (e.getToPos().equals("1"))) {
        model.addAttribute("users", accounts);
      }
      e.setToid(null);
      model.addAttribute("entity", e);
      return "portalmessage/save";
    }

    if ((stringUtils.isBlank(e.getTitle())) || 
      (stringUtils.isBlank(e.getDescription()))) {
      model.addAttribute("msg", "消息标题和内容不能为空！");
      List users = this.portaluserService
        .getPortaluserList(new PortaluserQuery());
      List accounts = this.portalaccountService
        .getPortalaccountList(new PortalaccountQuery());
      if ((e.getToPos() != null) && (e.getToPos().equals("0"))) {
        model.addAttribute("users", users);
      }
      if ((e.getToPos() != null) && (e.getToPos().equals("1"))) {
        model.addAttribute("users", accounts);
      }
      model.addAttribute("entity", e);
      return "portalmessage/save";
    }

    Portaluser user = (Portaluser)request.getSession()
      .getAttribute("user");
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
    e.setFromPos("0");
    e.setIp(GetNgnixRemotIP.getRemoteAddrIp(request));
    e.setState("0");
    e.setToname(toUserName);

    if ((cardId != null) && (cardId.longValue() != 0L)) {
      Portalcard card = this.portalcardService.getPortalcardByKey(cardId);
      if (!card.getState().equals("0")) {
        model.addAttribute("msg", "该充值卡已经被售出！");
        e.setTitle("该充值卡已经被售出！");
        e.setDescription("该充值卡已经被售出,请点击返回！！！");
        model.addAttribute("entity", e);
        model.addAttribute("cardId", cardId);
        return "portalmessage/save";
      }
      card.setState("1");
      card.setBuyDate(new Date());
      card.setAccountName(toUserName);
      this.portalcardService.updatePortalcardByKey(card);

      Portalorder order = new Portalorder();
      order.setAccountDel(card.getAccountDel());
      order.setAccountId(card.getAccountId());
      order.setAccountName(card.getAccountName());
      order.setBuyDate(card.getBuyDate());
      order.setBuyer(card.getAccountName());
      order.setCategoryType(card.getCategoryType());
      order.setCdKey(card.getCdKey());
      order.setDescription(card.getDescription());
      order.setMoney(card.getMoney());
      order.setName(card.getName());
      order.setPayby(Integer.valueOf(0));
      order.setPayDate(card.getBuyDate());
      order.setPayTime(card.getPayTime());
      order.setPayType(card.getPayType());
      order.setSeller("系统");
      order.setState("1");
      order.setTradeno(card.getCdKey());
      order.setUserDel(card.getUserDel());
      this.portalorderService.addPortalorder(order);
    }

    this.portalmessageService.addPortalmessage(e);

    return "redirect:istOut.action";
  }

  @ResponseBody
  @RequestMapping({"/ajax_ChooseSend.action"})
  public Map<Long, String> ChooseUsers(@RequestParam String toPos, ModelMap model) {
    if (stringUtils.isNotBlank(toPos)) {
      Map map = new HashMap();
      if (toPos.equals("0")) {
        List<Portaluser> users = this.portaluserService
          .getPortaluserList(new PortaluserQuery());
        for (Portaluser user : users) {
          map.put(user.getId(), user.getLoginName());
        }
        return map;
      }
      if (toPos.equals("1")) {
        List<Portalaccount> users = this.portalaccountService
          .getPortalaccountList(new PortalaccountQuery());
        for (Portalaccount account : users) {
          map.put(account.getId(), account.getLoginName());
        }
        return map;
      }
    }

    return null;
  }

  @RequestMapping({"/message_sendUI.action"})
  public String sendUI(ModelMap model)
  {
    return "portalmessage/sendUI";
  }

  @RequestMapping(value={"/message_send.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String send(Portalmessage e, HttpServletRequest request, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getTitle())) || 
      (stringUtils.isBlank(e.getDescription()))) {
      model.addAttribute("msg", "消息标题和内容不能为空！");
      return "portalmessage/sendUI";
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
        return "portalmessage/sendUI";
      }
    } catch (Exception ex) {
      model.addAttribute("msg", "消息标题不符合规范！");
      return "portalmessage/sendUI";
    }
    try {
      if (!MyUtils.checkInput(e.getDescription())) {
        model.addAttribute("msg", "消息内容不符合规范！");
        return "portalmessage/sendUI";
      }
    } catch (Exception ex) {
      model.addAttribute("msg", "消息内容不符合规范！");
      return "portalmessage/sendUI";
    }

    if (stringUtils.isBlank(e.getFromname())) {
      e.setFromname("游客");
    }
    try
    {
      if (!MyUtils.checkInput(e.getFromname())) {
        model.addAttribute("msg", "用户名不符合规范！");
        return "portalmessage/sendUI";
      }
    } catch (Exception ex) {
      model.addAttribute("msg", "用户名不符合规范！");
      return "portalmessage/sendUI";
    }

    HttpSession session = request.getSession();
    String ikmac = (String)session.getAttribute("ikmac");
    Cookie[] cookies = request.getCookies();
    String cmac = "";
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("mac"))
          cmac = cookies[i].getValue();
      }
    }
    if (stringUtils.isBlank(ikmac)) {
      ikmac = cmac;
    }
    if (stringUtils.isNotBlank(ikmac)) {
      e.setDescription("mac=" + ikmac + " " + e.getDescription());
    }
    e.setFromid("0");
    e.setFromPos("1");
    e.setToname("admin");
    e.setToid("1");
    e.setToPos("0");
    e.setIp(GetNgnixRemotIP.getRemoteAddrIp(request));
    e.setState("0");
    e.setDate(new Date());
    e.setDelin("0");
    e.setDelout("0");
    this.portalmessageService.addPortalmessage(e);

    return "portalmessage/sendOK";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalmessageController
 * JD-Core Version:    0.6.2
 */