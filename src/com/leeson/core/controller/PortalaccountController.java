package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.bean.Portalcardcategory;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.query.PortalcardcategoryQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountgroupService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalcardService;
import com.leeson.core.service.PortalcardcategoryService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.service.PortalorderService;
import com.leeson.core.service.PortalspeedService;
import com.leeson.core.utils.ExcelUtils;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.radius.core.model.RadiusOnlineMap;
import com.leeson.radius.core.utils.COAThread;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/portalaccountController")
public class PortalaccountController
{

  @Autowired
  private PortalaccountService portalaccountService;

  @Autowired
  private PortalaccountmacsService macsService;

  @Autowired
  private PortalconfigService portalconfigService;

  @Autowired
  private PortalspeedService portalspeedService;

  @Autowired
  private PortalcardcategoryService portalcardcategoryService;

  @Autowired
  private PortalcardService portalcardService;

  @Autowired
  private PortalorderService portalorderService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalaccountgroupService portalaccountgroupService;
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  @RequestMapping({"ist.action"})
  public String page(PortalaccountQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setLoginNameLike(true);
    query.setNameLike(true);
    if (stringUtils.isBlank(query.getLoginName())) {
      query.setLoginName(null);
    }
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }

    Pagination pagination = this.portalaccountService
      .getPortalaccountListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);

    return "portalaccount/list";
  }

  @RequestMapping({"ddV.action"})
  public String addV(ModelMap model)
  {
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);

    return "portalaccount/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalaccount e, Long speed, ModelMap model)
  {
    Integer haveAcc = this.portalaccountService
      .getPortalaccountCount(new PortalaccountQuery());
    if ((haveAcc != null) && 
      (haveAcc.intValue() >= Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
      .getCoreConfigMap().get("core"))[1]).intValue()))
    {
      model.addAttribute("msg", "账户超过授权数！");
      model.addAttribute("entity", e);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalaccount/save";
    }

    if ((stringUtils.isBlank(e.getLoginName())) || 
      (stringUtils.isBlank(e.getPassword()))) {
      model.addAttribute("msg", "登录名和密码不能为空！");
      model.addAttribute("entity", e);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalaccount/save";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(e.getLoginName());
    aq.setLoginNameLike(false);
    if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
      model.addAttribute("msg", "登录名已经存在！");
      model.addAttribute("entity", e);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalaccount/save";
    }

    if (stringUtils.isBlank(e.getGender())) {
      e.setGender(null);
    }

    Portalaccountgroup ag = this.portalaccountgroupService
      .getPortalaccountgroupByKey(Long.valueOf(1L));
    if (stringUtils.isBlank(e.getState())) {
      e.setState(ag.getState());
    }
    if (e.getMaclimitcount() == null) {
      e.setMaclimitcount(ag.getMaclimitcount());
    }
    if (e.getMaclimit() == null) {
      e.setMaclimit(ag.getMaclimit());
    }
    if (e.getAutologin() == null) {
      e.setAutologin(ag.getAutologin());
    }
    if (e.getSpeed() == null) {
      e.setSpeed(ag.getSpeed());
    }
    e.setDate(ag.getDate());
    e.setTime(ag.getTime());
    e.setOctets(ag.getOctets());
    this.portalaccountService.addPortalaccount(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
    model.addAttribute("entity", e);

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);

    return "portalaccount/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalaccount e, ModelMap model)
  {
    Portalaccount u = this.portalaccountService.getPortalaccountByKey(e.getId());
    if (stringUtils.isBlank(e.getPassword())) {
      e.setPassword(u.getPassword());
    }
    e.setLoginName(u.getLoginName());
    e.setDate(u.getDate());
    e.setTime(u.getTime());
    e.setOctets(u.getOctets());
    if (stringUtils.isBlank(e.getState())) {
      e.setState("0");
    }
    if (stringUtils.isBlank(e.getGender())) {
      e.setGender(null);
    }
    if (e.getMaclimitcount() == null) {
      e.setMaclimitcount(Integer.valueOf(1));
    }
    if (stringUtils.isNotBlank(e.getEx4()))
      try {
        int ex4 = Integer.valueOf(e.getEx4()).intValue();
        if (ex4 != 0) {
          ex4--;
          e.setEx4(String.valueOf(ex4));
        }
      }
      catch (Exception localException) {
      }
    this.portalaccountService.updatePortalaccountByKeyAll(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String editState(@RequestParam Long id, @RequestParam String to)
  {
    Portalaccount account = this.portalaccountService.getPortalaccountByKey(id);
    if (to.equals("state")) {
      int state = Integer.valueOf(account.getState()).intValue() + 1;
      if (state >= 5) {
        state = 0;
      }
      account.setState(String.valueOf(state));
    }
    if (to.equals("password")) {
      account.setPassword("1234");
    }
    if (to.equals("maclimit")) {
      if (account.getMaclimit().intValue() == 0)
        account.setMaclimit(Integer.valueOf(1));
      else {
        account.setMaclimit(Integer.valueOf(0));
      }
    }
    if (to.equals("auto")) {
      if (account.getAutologin().intValue() == 0)
        account.setAutologin(Integer.valueOf(1));
      else {
        account.setAutologin(Integer.valueOf(0));
      }
    }

    this.portalaccountService.updatePortalaccountByKey(account);
    return "redirect:ist.action";
  }

  @RequestMapping({"ay.action"})
  public String pay(@RequestParam Long id, ModelMap model)
  {
    Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
    model.addAttribute("entity", e);
    List list = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    model.addAttribute("list", list);
    return "portalaccount/pay";
  }

  @RequestMapping(value={"ay.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String payDo(@RequestParam Long id, Long cardCategoryId, ModelMap model)
  {
    if (id == null) {
      model.addAttribute("msg", "用户信息丢失，请重新选择！！");
      return "redirect:ist.action";
    }
    Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
    if (e == null) {
      model.addAttribute("msg", "用户不存在，请重新选择！！");
      return "redirect:ist.action";
    }
    if (cardCategoryId == null) {
      model.addAttribute("entity", e);
      List list = this.portalcardcategoryService
        .getPortalcardcategoryList(new PortalcardcategoryQuery());
      model.addAttribute("list", list);
      model.addAttribute("msg", "请正确选择充值卡类型！！");
      return "portalaccount/pay";
    }
    Portalcardcategory card = this.portalcardcategoryService
      .getPortalcardcategoryByKey(cardCategoryId);
    if (card == null) {
      model.addAttribute("entity", e);
      List list = this.portalcardcategoryService
        .getPortalcardcategoryList(new PortalcardcategoryQuery());
      model.addAttribute("list", list);
      model.addAttribute("msg", "该充值卡类型不存在！！");
      return "portalaccount/pay";
    }
    model.addAttribute("entity", e);
    model.addAttribute("card", card);
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    return "portalaccount/payDo";
  }

  @RequestMapping(value={"ays.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String pays(@RequestParam Long id, Long cardCategoryId, ModelMap model, HttpServletRequest request)
  {
    Portaluser user = (Portaluser)request.getSession()
      .getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      model.addAttribute("msg", "管理员登录信息丢失，请重新选择！！");
      return "redirect:ist.action";
    }
    if (id == null) {
      model.addAttribute("msg", "用户信息丢失，请重新选择！！");
      return "redirect:ist.action";
    }
    Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
    if (e == null) {
      model.addAttribute("msg", "用户不存在，请重新选择！！");
      return "redirect:ist.action";
    }
    if (cardCategoryId == null) {
      model.addAttribute("entity", e);
      List list = this.portalcardcategoryService
        .getPortalcardcategoryList(new PortalcardcategoryQuery());
      model.addAttribute("list", list);
      model.addAttribute("msg", "请正确选择充值卡类型！！");
      return "portalaccount/pay";
    }
    Portalcardcategory card = this.portalcardcategoryService
      .getPortalcardcategoryByKey(cardCategoryId);
    if (card == null) {
      model.addAttribute("entity", e);
      List list = this.portalcardcategoryService
        .getPortalcardcategoryList(new PortalcardcategoryQuery());
      model.addAttribute("list", list);
      model.addAttribute("msg", "该充值卡类型不存在！！");
      return "portalaccount/pay";
    }

    try
    {
      String categoryType = card.getState();
      String payType;
      if (categoryType.equals("0")) {
        payType = "2";
      }
      else
      {
        if (categoryType.equals("4"))
          payType = "4";
        else
          payType = "3";
      }
      if (card.getMoney() == null) {
        card.setMoney(Double.valueOf(0.0D));
      }
      Long time = card.getTime();
      Long payTime = Long.valueOf(0L);
      if (categoryType.equals("0"))
        payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L);
      else if (categoryType.equals("1"))
        payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L);
      else if (categoryType.equals("2"))
        payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L);
      else if (categoryType.equals("3"))
        payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L * 12L);
      else if (categoryType.equals("4")) {
        payTime = Long.valueOf(time.longValue() * 1024L * 1024L);
      }
      String cdkey = UUID.randomUUID().toString();
      Portalcard cardOrder = new Portalcard();
      cardOrder.setName(card.getName());
      cardOrder.setDescription(card.getDescription());
      cardOrder.setCategoryType(categoryType);
      cardOrder.setPayType(payType);
      cardOrder.setPayTime(payTime);
      cardOrder.setAccountDel(Integer.valueOf(0));
      cardOrder.setUserDel(Integer.valueOf(0));
      cardOrder.setCdKey(cdkey);
      cardOrder.setMoney(card.getMoney());
      cardOrder.setState("2");
      cardOrder.setBuyDate(new Date());
      cardOrder.setAccountName(e.getLoginName());
      cardOrder.setAccountId(id);
      cardOrder.setMaclimit(card.getMaclimit());
      cardOrder.setMaclimitcount(card.getMaclimitcount());
      cardOrder.setAutologin(card.getAutologin());
      cardOrder.setSpeed(card.getSpeed());
      cardOrder.setPayDate(new Date());

      String state = e.getState();
      Long oldDate = Long.valueOf(e.getDate().getTime());
      Long oldTime = e.getTime();
      Long oldOctets = e.getOctets();
      if (oldOctets == null) {
        oldOctets = Long.valueOf(0L);
      }
      if (oldTime == null) {
        oldTime = Long.valueOf(0L);
      }
      Long now = Long.valueOf(new Date().getTime());

      if (payType.equals("3"))
      {
        Long newDate;
        if (oldDate.longValue() < new Date().getTime())
          newDate = Long.valueOf(now.longValue() + payTime.longValue());
        else {
          newDate = Long.valueOf(oldDate.longValue() + payTime.longValue());
        }
        e.setDate(new Date(newDate.longValue()));
        e.setState(payType);
      }

      if (payType.equals("2")) {
        if (oldTime.longValue() < 0L)
          e.setTime(payTime);
        else {
          e.setTime(Long.valueOf(oldTime.longValue() + payTime.longValue()));
        }
        e.setState(payType);
      }

      if (payType.equals("4")) {
        if (oldOctets.longValue() < 0L)
          e.setOctets(payTime);
        else {
          e.setOctets(Long.valueOf(oldOctets.longValue() + payTime.longValue()));
        }
        e.setState(payType);
      }
      if (state.equals("1")) {
        e.setState(state);
      }
      e.setMaclimit(card.getMaclimit());
      e.setMaclimitcount(card.getMaclimitcount());
      e.setAutologin(card.getAutologin());
      e.setSpeed(card.getSpeed());
      this.portalaccountService.updatePortalaccountByKey(e);

      this.portalcardService.addPortalcard(cardOrder);

      Portalorder order = new Portalorder();
      order.setAccountDel(cardOrder.getAccountDel());
      order.setAccountId(cardOrder.getAccountId());
      order.setAccountName(cardOrder.getAccountName());
      order.setBuyDate(cardOrder.getBuyDate());
      order.setBuyer(cardOrder.getAccountName());
      order.setCategoryType(cardOrder.getCategoryType());
      order.setCdKey(cardOrder.getCdKey());
      order.setDescription(cardOrder.getDescription());
      order.setMoney(cardOrder.getMoney());
      order.setName(cardOrder.getName());
      order.setPayby(Integer.valueOf(0));
      order.setPayDate(cardOrder.getPayDate());
      order.setPayTime(cardOrder.getPayTime());
      order.setPayType(cardOrder.getPayType());
      order.setSeller(user.getLoginName());
      order.setState("1");
      order.setTradeno(cardOrder.getCdKey());
      order.setUserDel(cardOrder.getUserDel());
      this.portalorderService.addPortalorder(order);

      model.addAttribute("msg", "充值成功！！");
      PortalaccountQuery query = new PortalaccountQuery();
      query.setId(id);
      Pagination pagination = this.portalaccountService
        .getPortalaccountListWithPage(query);
      model.addAttribute("pagination", pagination);
      model.addAttribute("query", query);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalaccount/list";
    } catch (Exception ex) {
      model.addAttribute("msg", "发生错误！！");
    }return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    Set<Map.Entry<String,String[]>> entries = onlineMap.getOnlineUserMap()
      .entrySet();
    for (Map.Entry entry : entries) {
      String[] info = (String[])entry.getValue();
      String ip = (String)entry.getKey();
      if ((stringUtils.isNotBlank(info[1])) && 
        (Long.valueOf(info[1]) == id)) {
        Kick.kickUserDeleteUser(ip);
      }

    }

    Portalaccount acc = this.portalaccountService.getPortalaccountByKey(id);
    if (acc != null) {
      String username = acc.getLoginName();
      Iterator iterator = RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().keySet().iterator();
      while (iterator.hasNext()) {
        Object o = iterator.next();
        String acctSessionId = o.toString();
        String[] radiusOnlineInfo = 
          (String[])RadiusOnlineMap.getInstance()
          .getRadiusOnlineMap().get(acctSessionId);
        if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
          (username.equals(radiusOnlineInfo[4]))) {
          COAThread.COA_Account_Cost(radiusOnlineInfo, 
            "Radius Account Delete COA");
        }
      }

    }

    this.portalaccountService.deleteByKey(id);

    PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
    macsq.setAccountId(id);
    this.macsService.deleteByQuery(macsq);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List<Long> list = Arrays.asList(ids);

    Set<Map.Entry <String,String[]>> entries = onlineMap.getOnlineUserMap()
      .entrySet();
    for (Long id : list) {
      for (Map.Entry entry : entries) {
        String[] info = (String[])entry.getValue();
        String ip = (String)entry.getKey();
        if ((stringUtils.isNotBlank(info[1])) && 
          (Long.valueOf(info[1]) == id)) {
          Kick.kickUserDeleteUser(ip);
        }

      }

      Portalaccount acc = this.portalaccountService.getPortalaccountByKey(id);
      if (acc != null) {
        String username = acc.getLoginName();
        Iterator iterator = RadiusOnlineMap.getInstance()
          .getRadiusOnlineMap().keySet().iterator();
        while (iterator.hasNext()) {
          Object o = iterator.next();
          String acctSessionId = o.toString();
          String[] radiusOnlineInfo = 
            (String[])RadiusOnlineMap.getInstance()
            .getRadiusOnlineMap().get(acctSessionId);
          if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
            (username.equals(radiusOnlineInfo[4]))) {
            COAThread.COA_Account_Cost(radiusOnlineInfo, 
              "Radius Account Delete COA");
          }
        }

      }

      PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
      macsq.setAccountId(id);
      this.macsService.deleteByQuery(macsq);
    }
    this.portalaccountService.deleteByKeys(list);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    Set<Map.Entry<String,String[]>> entries = onlineMap.getOnlineUserMap()
      .entrySet();
    for (Map.Entry entry : entries) {
      String ip = (String)entry.getKey();
      Kick.kickUserDeleteUser(ip);
    }

    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String acctSessionId = o.toString();
      String[] radiusOnlineInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0)) {
        COAThread.COA_Account_Cost(radiusOnlineInfo, 
          "Radius Account Delete COA");
      }
    }

    this.portalaccountService.deleteByQuery(new PortalaccountQuery());
    this.macsService.deleteByQuery(new PortalaccountmacsQuery());
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eleteQuery.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deleteQuery(PortalaccountQuery query)
  {
    query.setLoginNameLike(true);
    query.setNameLike(true);
    if (stringUtils.isBlank(query.getLoginName())) {
      query.setLoginName(null);
    }
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    if ((query.getId() == null) && (query.getState() == null) && 
      (query.getName() == null) && (query.getLoginName() == null)) {
      return "redirect:ist.action";
    }

    List<Portalaccount> list = this.portalaccountService
      .getPortalaccountList(query);

    Set<Map.Entry<String, String[]>> entries = onlineMap.getOnlineUserMap()
      .entrySet();
    for (Portalaccount acc : list) {
      for (Map.Entry entry : entries) {
        String[] info = (String[])entry.getValue();
        String ip = (String)entry.getKey();
        if ((stringUtils.isNotBlank(info[1])) && 
          (Long.valueOf(info[1]) == acc.getId())) {
          Kick.kickUserDeleteUser(ip);
        }

      }

      if (acc != null) {
        String username = acc.getLoginName();
        Iterator iterator = RadiusOnlineMap.getInstance()
          .getRadiusOnlineMap().keySet().iterator();
        while (iterator.hasNext()) {
          Object o = iterator.next();
          String acctSessionId = o.toString();
          String[] radiusOnlineInfo = 
            (String[])RadiusOnlineMap.getInstance()
            .getRadiusOnlineMap().get(acctSessionId);
          if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
            (username.equals(radiusOnlineInfo[4]))) {
            COAThread.COA_Account_Cost(radiusOnlineInfo, 
              "Radius Account Delete COA");
          }
        }

      }

      this.portalaccountService.deleteByKey(acc.getId());
      PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
      macsq.setAccountId(acc.getId());
      this.macsService.deleteByQuery(macsq);
    }
    this.portalaccountService.deleteByQuery(query);
    return "redirect:ist.action";
  }

  @RequestMapping({"ut.action"})
  public String out()
  {
    return "portalaccount/out";
  }

  @RequestMapping({"utV.action"})
  public String outV(ModelMap model, HttpServletRequest request)
  {
    List accounts = this.portalaccountService
      .getPortalaccountList(new PortalaccountQuery());
    String cfgPath = request.getServletContext().getRealPath("/");
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    File dir = new File(cfgPath + "ExcelOut/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String fileName = cfgPath + "ExcelOut/" + nowString + ".xls";
    try {
      ExcelUtils.writeAccountToExcel(fileName, accounts);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("msg", "文件创建失败！");
      model.addAttribute("downUrl", null);
      model.addAttribute("err", Integer.valueOf(1));
      return "portalaccount/outResult";
    }
    model.addAttribute("msg", "文件创建成功！");
    model.addAttribute("downUrl", nowString + ".xls");
    model.addAttribute("creatDate", nowString);
    model.addAttribute("err", Integer.valueOf(0));
    return "portalaccount/outResult";
  }

  @RequestMapping({"n.action"})
  public String in(ModelMap model)
  {
    model.addAttribute("msg", "接入账户文件导入!");
    return "portalaccount/in";
  }

  @RequestMapping(value={"n.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String in(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
  {
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    if ((!ext.equals("xls")) && (!ext.equals("xlsx"))) {
      model.addAttribute("msg", "文件格式错误!");
      model.addAttribute("err", Integer.valueOf(0));
      return "portalaccount/in";
    }

    String dir = null;
    String nowString = null;
    List unInAccounts = new ArrayList();
    try {
      InputStream in = file.getInputStream();
      dir = request.getServletContext().getRealPath("/ExcelIn");
      File fileLocation = new File(dir);

      if (!fileLocation.exists()) {
        boolean isCreated = fileLocation.mkdir();
        if (!isCreated)
        {
          model.addAttribute("msg", "权限不足!");
          model.addAttribute("err", Integer.valueOf(0));
          return "portalaccount/in";
        }
      }

      Date now = new Date();
      SimpleDateFormat format = new SimpleDateFormat(
        "yyyy-MM-dd-HH-mm-ss");
      nowString = format.format(now);

      File uploadFile = new File(dir, nowString + ".xls");

      FileUtils.copyInputStreamToFile(in, uploadFile);

      in.close();
    }
    catch (Exception ex) {
      model.addAttribute("msg", "传送文件异常!");
      model.addAttribute("err", Integer.valueOf(0));
      File df = new File(dir + "/" + nowString + ".xls");
      if (df.exists()) {
        df.delete();
      }
      return "portalaccount/in";
    }
    try
    {
      unInAccounts = ExcelUtils.readExcelAccount(dir + "/" + nowString + 
        ".xls");
    } catch (Exception e) {
      model.addAttribute("msg", "文件读取错误!");
      model.addAttribute("err", Integer.valueOf(0));
      File df = new File(dir + "/" + nowString + ".xls");
      if (df.exists()) {
        df.delete();
      }
      return "portalaccount/in";
    }
    File df = new File(dir + "/" + nowString + ".xls");
    if (df.exists()) {
      df.delete();
    }
    model.addAttribute("unInAccounts", unInAccounts);
    return "portalaccount/inResult";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalaccountController
 * JD-Core Version:    0.6.2
 */