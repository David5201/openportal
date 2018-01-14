package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.OnlineInfo;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.bean.Portalsmsapi;
import com.leeson.core.bean.RadiusOnlineInfo;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.query.PortalcardQuery;
import com.leeson.core.query.PortallinkrecordQuery;
import com.leeson.core.query.PortalorderQuery;
import com.leeson.core.query.PortalsmsapiQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountgroupService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalcardService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortalorderService;
import com.leeson.core.service.PortalsmsapiService;
import com.leeson.core.service.PortalspeedService;
import com.leeson.core.utils.Kick;
import com.leeson.core.utils.MyUtils;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import com.leeson.radius.core.model.RadiusOnlineMap;
import com.leeson.radius.core.utils.COAThread;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class portalcustomerController
{

  @Autowired
  private PortalaccountService portalaccountService;

  @Autowired
  private PortalaccountmacsService portalaccountmacsService;

  @Autowired
  private PortalcardService portalcardService;

  @Autowired
  private PortallinkrecordService portallinkrecordService;

  @Autowired
  private PortalconfigService portalconfigService;

  @Autowired
  private PortalorderService portalorderService;

  @Autowired
  private PortalsmsapiService portalsmsapiService;

  @Autowired
  private PortalspeedService portalspeedService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalaccountgroupService portalaccountgroupService;

  @Autowired
  private PortalaccountmacsService macsService;
  private static SimpleDateFormat format = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");

  private static DecimalFormat df = new DecimalFormat(".##");

  @RequestMapping({"/customerPayRecord.action"})
  public String PayRecord(PortalcardQuery query, String query_begin_time, String query_end_time, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);

    query.setAccountId(acc.getId());
    query.setAccountDel(Integer.valueOf(0));
    query.orderbyPayDate(false);
    if (stringUtils.isBlank(query.getCategoryType())) {
      query.setCategoryType(null);
    }
    if (stringUtils.isBlank(query.getPayType())) {
      query.setPayType(null);
    }
    Date begin_time = null;
    Date end_time = null;
    try {
      if (stringUtils.isNotBlank(query_begin_time)) {
        begin_time = format.parse(query_begin_time);
        query.setBegin_time(begin_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "开始日期格式错误！");
      query_begin_time = null;
    }
    try {
      if (stringUtils.isNotBlank(query_end_time)) {
        end_time = format.parse(query_end_time);
        query.setEnd_time(end_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "结束日期格式错误！");
      query_end_time = null;
    }

    Pagination pagination = this.portalcardService
      .getPortalcardListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    model.addAttribute("query_begin_time", query_begin_time);
    model.addAttribute("query_end_time", query_end_time);

    return "portalcustomer/payRecord";
  }

  @RequestMapping({"/customerLinkRecord.action"})
  public String LinkRecord(PortallinkrecordQuery query, String query_begin_time, String query_end_time, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);

    query.setUid(acc.getId());
    query.setAccountDel(Integer.valueOf(0));
    query.orderbyId(false);
    query.setIpLike(true);
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    if (stringUtils.isBlank(query.getIp())) {
      query.setIp(null);
    }

    Date begin_time = null;
    Date end_time = null;
    try {
      if (stringUtils.isNotBlank(query_begin_time)) {
        begin_time = format.parse(query_begin_time);
        query.setBegin_time(begin_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "开始日期格式错误！");
      query_begin_time = null;
    }
    try {
      if (stringUtils.isNotBlank(query_end_time)) {
        end_time = format.parse(query_end_time);
        query.setEnd_time(end_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "结束日期格式错误！");
      query_end_time = null;
    }

    Pagination pagination = this.portallinkrecordService
      .getPortallinkrecordListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    model.addAttribute("query_begin_time", query_begin_time);
    model.addAttribute("query_end_time", query_end_time);

    return "portalcustomer/linkRecord";
  }

  @RequestMapping({"/customerAdd.action"})
  public String addV(ModelMap model)
  {
    Integer haveAcc = this.portalaccountService
      .getPortalaccountCount(new PortalaccountQuery());
    if ((haveAcc != null) && 
      (haveAcc.intValue() >= Integer.valueOf(
      ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
      .getCoreConfigMap().get("core"))[1]).intValue()))
    {
      model.addAttribute("msg", "账户超过授权数！");
      return "portalcustomer/login";
    }

    if (this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue() == 0) {
      model.addAttribute("msg", "对不起，管理员设置不允许自助创建新账户！！");
      return "portalcustomer/login";
    }if (2 == this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue()) {
      model.addAttribute("msg", "请认真填写账户信息！！");
      return "portalcustomer/addPhone";
    }if (3 == this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue()) {
      model.addAttribute("msg", "请认真填写账户信息！！");
      return "portalcustomer/addSms";
    }
    model.addAttribute("msg", "请认真填写账户信息！！");
    return "portalcustomer/add";
  }

  @RequestMapping(value={"/customerAddSms.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String addSms(Portalaccount e, String yzm, ModelMap model, HttpServletRequest request)
  {
    String phone = e.getPhoneNumber();
    if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
      model.addAttribute("msg", "请输入手机号和验证码！！");
      model.addAttribute("entity", e);
      return "portalcustomer/addSms";
    }
    if (phone.length() != 11) {
      model.addAttribute("msg", "手机号不正确！！");
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addSms";
    }
    try {
      Long.parseLong(phone);
    } catch (Exception ex) {
      model.addAttribute("msg", "手机号不正确！！");
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addSms";
    }
    String code = "";
    PortalsmsapiQuery query = new PortalsmsapiQuery();
    query.setState("1");
    List smsList = this.portalsmsapiService
      .getPortalsmsapiList(query);
    Portalsmsapi smsapi;
    if (smsList.size() > 0)
      smsapi = (Portalsmsapi)smsList.get(0);
    else {
      smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
    }
    Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
    Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
    try {
      Date saveDate = (Date)objs[1];
      Long nowTime = Long.valueOf(new Date().getTime());
      if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        model.addAttribute("msg", "验证码已过期，请重新获取验证码！");
        model.addAttribute("entity", e);
        PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        return "portalcustomer/addSms";
      }
      code = (String)objs[0];
    } catch (Exception ex) {
      model.addAttribute("msg", "手机号或验证码不正确！");
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addSms";
    }

    if (!yzm.equals(code)) {
      model.addAttribute("msg", "验证码不正确！");
      model.addAttribute("entity", e);
      return "portalcustomer/addSms";
    }

    if ((stringUtils.isBlank(e.getLoginName())) || 
      (stringUtils.isBlank(e.getPassword()))) {
      model.addAttribute("msg", "登录名和密码不能为空！");
      model.addAttribute("entity", e);
      model.addAttribute("yzm", yzm);
      return "portalcustomer/addSms";
    }
    if (!MyUtils.checkUserName(e.getLoginName())) {
      model.addAttribute("msg", "登录名不符合规范!!");
      e.setLoginName(null);
      model.addAttribute("entity", e);
      model.addAttribute("yzm", yzm);
      return "portalcustomer/addSms";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(e.getLoginName());
    aq.setLoginNameLike(false);
    if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
      model.addAttribute("msg", "登录名已经存在！");
      model.addAttribute("entity", e);
      model.addAttribute("yzm", yzm);
      return "portalcustomer/addSms";
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

    HttpSession session = request.getSession();
    session.setAttribute("username", e.getLoginName());
    session.setAttribute("password", e.getPassword());

    PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);

    model.addAttribute("msg", "恭喜您，账户创建成功！！");
    return "redirect:/customerIndex.action";
  }

  @RequestMapping(value={"/customerAddPhone.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String addPhone(Portalaccount e, String yzm, ModelMap model, HttpServletRequest request)
  {
    String phone = e.getLoginName();
    if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
      model.addAttribute("msg", "请输入手机号和验证码！！");
      model.addAttribute("entity", e);
      return "portalcustomer/addPhone";
    }
    if (phone.length() != 11) {
      model.addAttribute("msg", "手机号不正确！！");
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addPhone";
    }
    try {
      Long.parseLong(phone);
    } catch (Exception ex) {
      model.addAttribute("msg", "手机号不正确！！");
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addPhone";
    }
    String code = "";
    PortalsmsapiQuery query = new PortalsmsapiQuery();
    query.setState("1");
    List smsList = this.portalsmsapiService
      .getPortalsmsapiList(query);
    Portalsmsapi smsapi;
    if (smsList.size() > 0)
      smsapi = (Portalsmsapi)smsList.get(0);
    else {
      smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
    }
    Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
    Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
    try {
      Date saveDate = (Date)objs[1];
      Long nowTime = Long.valueOf(new Date().getTime());
      if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        model.addAttribute("msg", "验证码已过期，请重新获取验证码！");
        model.addAttribute("entity", e);
        PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        return "portalcustomer/addPhone";
      }
      code = (String)objs[0];
    } catch (Exception ex) {
      model.addAttribute("msg", "手机号或验证码不正确！");
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addPhone";
    }

    if (!yzm.equals(code)) {
      model.addAttribute("msg", "验证码不正确！");
      model.addAttribute("entity", e);
      return "portalcustomer/addPhone";
    }

    if ((stringUtils.isBlank(e.getLoginName())) || 
      (stringUtils.isBlank(e.getPassword()))) {
      model.addAttribute("msg", "登录名和密码不能为空！");
      model.addAttribute("entity", e);
      model.addAttribute("yzm", yzm);
      return "portalcustomer/addPhone";
    }
    if (!MyUtils.checkUserName(e.getLoginName())) {
      model.addAttribute("msg", "登录名不符合规范!!");
      e.setLoginName(null);
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addPhone";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(e.getLoginName());
    aq.setLoginNameLike(false);
    if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
      model.addAttribute("msg", "登录名已经存在！");
      model.addAttribute("entity", e);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/addPhone";
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
    e.setPhoneNumber(phone);
    this.portalaccountService.addPortalaccount(e);

    HttpSession session = request.getSession();
    session.setAttribute("username", e.getLoginName());
    session.setAttribute("password", e.getPassword());

    PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);

    model.addAttribute("msg", "恭喜您，账户创建成功！！");
    return "redirect:/customerIndex.action";
  }

  @RequestMapping(value={"/customerAdd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalaccount e, ModelMap model, HttpServletRequest request)
  {
    if ((stringUtils.isBlank(e.getLoginName())) || 
      (stringUtils.isBlank(e.getPassword()))) {
      model.addAttribute("msg", "登录名和密码不能为空！");
      model.addAttribute("entity", e);
      return "portalcustomer/add";
    }
    if (!MyUtils.checkUserName(e.getLoginName())) {
      model.addAttribute("msg", "登录名不符合规范!!");
      e.setLoginName(null);
      model.addAttribute("entity", e);
      return "portalcustomer/add";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(e.getLoginName());
    aq.setLoginNameLike(false);
    if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
      model.addAttribute("msg", "登录名已经存在！");
      model.addAttribute("entity", e);
      return "portalcustomer/add";
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

    HttpSession session = request.getSession();
    session.setAttribute("username", e.getLoginName());
    session.setAttribute("password", e.getPassword());

    model.addAttribute("msg", "恭喜您，账户创建成功！！");
    return "redirect:/customerIndex.action";
  }

  @RequestMapping({"/customerEdit.action"})
  public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);
    model.addAttribute("entity", acc);
    model.addAttribute("msg", "修改账户信息，如果不修改密码，请留空！！");
    return "portalcustomer/save";
  }

  @RequestMapping(value={"/customerEdit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalaccount e, String oldpassword, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    String basip = (String)session.getAttribute("basip");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);
    long accid = acc.getId().longValue();
    long eid = e.getId().longValue();
    if (accid != eid) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }

    if (!acc.getPassword().equals(oldpassword)) {
      e.setId(acc.getId());
      e.setLoginName(acc.getLoginName());
      model.addAttribute("entity", e);
      model.addAttribute("msg", "原密码不正确！！");
      return "portalcustomer/save";
    }

    if (stringUtils.isNotBlank(e.getPassword())) {
      acc.setPassword(e.getPassword());
    }

    if (stringUtils.isBlank(e.getName()))
      acc.setName(null);
    else {
      acc.setName(e.getName());
    }

    if (stringUtils.isBlank(e.getGender()))
      acc.setGender(null);
    else {
      acc.setGender(e.getGender());
    }

    if (stringUtils.isBlank(e.getPhoneNumber()))
      acc.setPhoneNumber(null);
    else {
      acc.setPhoneNumber(e.getPhoneNumber());
    }

    if (stringUtils.isBlank(e.getEmail()))
      acc.setEmail(null);
    else {
      acc.setEmail(e.getEmail());
    }

    if (stringUtils.isBlank(e.getDescription()))
      acc.setDescription(null);
    else {
      acc.setDescription(e.getDescription());
    }

    if (stringUtils.isBlank(e.getIdnumber()))
      acc.setIdnumber(null);
    else {
      acc.setIdnumber(e.getIdnumber());
    }

    if (stringUtils.isBlank(e.getAddress()))
      acc.setAddress(null);
    else {
      acc.setAddress(e.getAddress());
    }

    if (stringUtils.isBlank(e.getEx1()))
      acc.setEx1(null);
    else {
      acc.setEx1(e.getEx1());
    }
    if (stringUtils.isBlank(e.getEx2()))
      acc.setEx2(null);
    else {
      acc.setEx2(e.getEx2());
    }

    this.portalaccountService.updatePortalaccountByKeyAll(acc);
    String isOnline;
    if (OnlineMap.getInstance()
      .getOnlineUserMap()
      .containsKey(
      GetNgnixRemotIP.getRemoteAddrIp(request) + ":" + basip))
      isOnline = "在线";
    else {
      isOnline = "离线";
    }
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    model.addAttribute("entity", acc);
    model.addAttribute("isOnline", isOnline);
    model.addAttribute("msg", "恭喜您，账户信息修改成功！");
    return "portalcustomer/index";
  }

  @RequestMapping({"/customerLogin.action"})
  public String Login(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
      return "redirect:/customerIndex.action";
    }
    model.addAttribute("msg", "欢迎使用 ！！");
    return "portalcustomer/login";
  }

  @RequestMapping({"/customerLoginOut.action"})
  public String LoginOut(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    session.removeAttribute("password");
    model.addAttribute("msg", "注销成功！");
    return "portalcustomer/login";
  }

  @RequestMapping({"/customerIndex.action"})
  public String index(String username, String password, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    if (cookies != null)
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
      }
    String isOnline;
    if (OnlineMap.getInstance().getOnlineUserMap()
      .containsKey(cip + ":" + cbasip))
      isOnline = "在线";
    else {
      isOnline = "离线";
    }

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);

    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginNameLike(false);
    aq.setPasswordLike(false);

    if ((stringUtils.isNotBlank(username)) && 
      (stringUtils.isNotBlank(password))) {
      aq.setLoginName(username);
      aq.setPassword(password);
      List accounts = this.portalaccountService.getPortalaccountList(aq);
      if (accounts.size() == 1) {
        Portalaccount account = (Portalaccount)accounts.get(0);
        if (password.equals(account.getPassword())) {
          model.addAttribute("entity", account);
          model.addAttribute("isOnline", isOnline);
          model.addAttribute("msg", "登录成功！");
          session.setAttribute("username", username);
          session.setAttribute("password", password);
          return "portalcustomer/index";
        }
      }
    }

    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
      aq.setLoginName(un);
      aq.setPassword(pwd);
      List accounts = this.portalaccountService.getPortalaccountList(aq);
      if (accounts.size() == 1) {
        Portalaccount account = (Portalaccount)accounts.get(0);
        if (pwd.equals(account.getPassword())) {
          model.addAttribute("entity", account);
          model.addAttribute("isOnline", isOnline);
          model.addAttribute("msg", "登录成功！");
          return "portalcustomer/index";
        }
      }

    }

    model.addAttribute("msg", "用户验证失败，请先登录！！");
    return "portalcustomer/login";
  }

  @RequestMapping({"/customerPay.action"})
  public String pay(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }

    Portalaccount e = (Portalaccount)accounts.get(0);
    if (e == null) {
      model.addAttribute("msg", "用户信息丢失，请先登录！！");
      return "portalcustomer/login";
    }
    model.addAttribute("entity", e);
    model.addAttribute("msg", "请输入充值卡序列号进行充值！！");
    return "portalcustomer/pay";
  }

  @RequestMapping(value={"/customerPay.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String pay(@RequestParam Long id, @RequestParam String cardKey, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String basip = (String)session.getAttribute("basip");
    if ((id == null) || (id.longValue() == 0L)) {
      model.addAttribute("msg", "用户信息丢失，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
    if (e == null) {
      model.addAttribute("msg", "用户信息丢失，请先登录！！");
      return "portalcustomer/login";
    }
    if (stringUtils.isNotBlank(cardKey)) {
      Boolean boo = Boolean.valueOf(false);
      PortalcardQuery cq = new PortalcardQuery();
      cq.setCdKey(cardKey);
      cq.setCdKeyLike(false);
      cq.setStateLike(false);
      cq.setState("0");
      List cards = this.portalcardService.getPortalcardList(cq);
      if ((cards != null) && (cards.size() == 1)) {
        boo = Boolean.valueOf(true);
      }
      if (!boo.booleanValue()) {
        cq.setState("1");
        cards = this.portalcardService.getPortalcardList(cq);
      }
      if ((cards != null) && (cards.size() == 1)) {
        boo = Boolean.valueOf(true);
      }
      if (!boo.booleanValue()) {
        model.addAttribute("entity", e);
        model.addAttribute("msg", "充值卡CD-KEY错误或已经使用,请仔细核对！！");
        return "portalcustomer/pay";
      }
      Portalcard card = (Portalcard)cards.get(0);
      String payType = card.getPayType();
      Long payTime = card.getPayTime();

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
      card.setAccountId(id);
      card.setAccountName(e.getLoginName());
      card.setState("2");
      Date date = new Date();
      if (card.getBuyDate() == null) {
        card.setBuyDate(date);
      }
      card.setPayDate(date);
      this.portalcardService.updatePortalcardByKey(card);

      PortalorderQuery oq = new PortalorderQuery();
      oq.setAccountName(card.getAccountName());
      oq.setAccountNameLike(false);
      oq.setCdKey(card.getCdKey());
      oq.setCdKeyLike(false);
      List orders = this.portalorderService
        .getPortalorderList(oq);
      if (orders.size() > 0) {
        Portalorder order = (Portalorder)orders.get(0);
        order.setAccountDel(card.getAccountDel());
        order.setAccountId(card.getAccountId());
        order.setAccountName(card.getAccountName());

        order.setBuyer(card.getAccountName());
        order.setCategoryType(card.getCategoryType());
        order.setCdKey(card.getCdKey());
        order.setDescription(card.getDescription());
        order.setMoney(card.getMoney());
        order.setName(card.getName());
        order.setPayby(Integer.valueOf(0));

        order.setPayTime(card.getPayTime());
        order.setPayType(card.getPayType());
        order.setSeller("系统");
        order.setState("1");
        order.setTradeno(card.getCdKey());
        order.setUserDel(card.getUserDel());
        this.portalorderService.updatePortalorderByKey(order);
      } else {
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
        order.setPayDate(card.getPayDate());
        order.setPayTime(card.getPayTime());
        order.setPayType(card.getPayType());
        order.setSeller("系统");
        order.setState("1");
        order.setTradeno(card.getCdKey());
        order.setUserDel(card.getUserDel());
        this.portalorderService.addPortalorder(order);
      }
      String isOnline;
      if (OnlineMap.getInstance()
        .getOnlineUserMap()
        .containsKey(
        GetNgnixRemotIP.getRemoteAddrIp(request) + ":" + 
        basip))
        isOnline = "在线";
      else {
        isOnline = "离线";
      }
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      model.addAttribute("entity", e);
      model.addAttribute("isOnline", isOnline);
      model.addAttribute("msg", "恭喜您，充值成功！");
      return "portalcustomer/index";
    }
    model.addAttribute("entity", e);
    model.addAttribute("msg", "请仔细核对并输入充值卡CD-KEY！！");
    return "portalcustomer/pay";
  }

  @RequestMapping({"/customerUnlockMac.action"})
  public String customerUnlockMac(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    if (cookies != null)
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
      }
    String isOnline;
    if (OnlineMap.getInstance().getOnlineUserMap()
      .containsKey(cip + ":" + cbasip))
      isOnline = "在线";
    else {
      isOnline = "离线";
    }

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    model.addAttribute("entity", acc);
    model.addAttribute("isOnline", isOnline);

    Integer count = this.portalaccountgroupService
      .getPortalaccountgroupByKey(Long.valueOf(1L)).getUnlockMac();
    if ((count != null) && (count.intValue() > 0)) {
      String dateS = acc.getEx4();
      String costS = acc.getEx3();
      Date now = new Date();
      String nowS = String.valueOf(now.getMonth());

      if (stringUtils.isBlank(dateS)) {
        PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
        macq.setAccountId(acc.getId());
        List macs = this.portalaccountmacsService
          .getPortalaccountmacsList(macq);
        if ((macs != null) && (macs.size() > 0)) {
          this.portalaccountmacsService.deleteByQuery(macq);
          acc.setEx3("1");
          acc.setEx4(nowS);
          this.portalaccountService.updatePortalaccountByKey(acc);
          model.addAttribute("msg", "自助解绑成功！");
        } else {
          model.addAttribute("msg", "该帐号还没有绑定MAC！");
        }
      }
      else if (stringUtils.isBlank(costS)) {
        PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
        macq.setAccountId(acc.getId());
        List macs = this.portalaccountmacsService
          .getPortalaccountmacsList(macq);
        if ((macs != null) && (macs.size() > 0)) {
          this.portalaccountmacsService.deleteByQuery(macq);
          acc.setEx3("1");
          acc.setEx4(nowS);
          this.portalaccountService.updatePortalaccountByKey(acc);
          model.addAttribute("msg", "自助解绑成功！");
        } else {
          model.addAttribute("msg", "该帐号还没有绑定MAC！");
        }
      }
      else if (dateS.equals(nowS)) {
        if (Integer.valueOf(costS).intValue() < count.intValue()) {
          PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
          macq.setAccountId(acc.getId());
          List macs = this.portalaccountmacsService
            .getPortalaccountmacsList(macq);
          if ((macs != null) && (macs.size() > 0)) {
            this.portalaccountmacsService.deleteByQuery(macq);
            acc.setEx3(String.valueOf(
              Integer.valueOf(costS).intValue() + 1));
            acc.setEx4(nowS);
            this.portalaccountService
              .updatePortalaccountByKey(acc);
            model.addAttribute("msg", "自助解绑成功！");
          } else {
            model.addAttribute("msg", "该帐号还没有绑定MAC！");
          }
        } else {
          model.addAttribute("msg", "本月自助解绑次数已用完，请联系管理员！");
        }
      } else {
        PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
        macq.setAccountId(acc.getId());
        List macs = this.portalaccountmacsService
          .getPortalaccountmacsList(macq);
        if ((macs != null) && (macs.size() > 0)) {
          this.portalaccountmacsService.deleteByQuery(macq);
          acc.setEx3("1");
          acc.setEx4(nowS);
          this.portalaccountService.updatePortalaccountByKey(acc);
          model.addAttribute("msg", "自助解绑成功！");
        } else {
          model.addAttribute("msg", "该帐号还没有绑定MAC！");
        }
      }

    }
    else
    {
      model.addAttribute("msg", "系统不允许自助解绑，请联系管理员！");
    }
    return "portalcustomer/index";
  }

  @RequestMapping({"/customerFindMethod.action"})
  public String customerFindMethod(ModelMap model, HttpServletRequest request)
  {
    model.addAttribute("msg", "请选择重置密码方式！！");
    return "portalcustomer/find";
  }

  @RequestMapping({"/customerSmsFind.action"})
  public String customerSmsForget(ModelMap model, HttpServletRequest request)
  {
    model.addAttribute("msg", "请填写要重置密码的手机号！！");
    return "portalcustomer/Smsforget";
  }

  @RequestMapping({"/customerSmsForget.action"})
  public String customerSmsForget(PortalaccountQuery aq, String yzm, ModelMap model, HttpServletRequest request)
  {
    if ((stringUtils.isBlank(aq.getLoginName())) || 
      (stringUtils.isBlank(aq.getPhoneNumber()))) {
      model.addAttribute("msg", "请填写要重置密码的登录名和手机号！！");
      return "portalcustomer/Smsforget";
    }
    if (stringUtils.isBlank(yzm)) {
      model.addAttribute("msg", "请填写验证码！！");
      return "portalcustomer/Smsforget";
    }

    String phone = aq.getPhoneNumber();
    if (phone.length() != 11) {
      model.addAttribute("msg", "手机号不正确！！");
      model.addAttribute("entity", aq);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/Smsforget";
    }
    try {
      Long.parseLong(phone);
    } catch (Exception ex) {
      model.addAttribute("msg", "手机号不正确！！");
      model.addAttribute("entity", aq);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/Smsforget";
    }
    String code = "";
    PortalsmsapiQuery query = new PortalsmsapiQuery();
    query.setState("1");
    List smsList = this.portalsmsapiService
      .getPortalsmsapiList(query);
    Portalsmsapi smsapi;
    if (smsList.size() > 0)
      smsapi = (Portalsmsapi)smsList.get(0);
    else {
      smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
    }
    Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
    Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
    try {
      Date saveDate = (Date)objs[1];
      Long nowTime = Long.valueOf(new Date().getTime());
      if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        model.addAttribute("msg", "验证码已过期，请重新获取验证码！");
        model.addAttribute("entity", aq);
        PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
        return "portalcustomer/Smsforget";
      }
      code = (String)objs[0];
    } catch (Exception ex) {
      model.addAttribute("msg", "手机号或验证码不正确！");
      model.addAttribute("entity", aq);
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      return "portalcustomer/Smsforget";
    }

    if (!yzm.equals(code)) {
      model.addAttribute("msg", "验证码不正确！");
      model.addAttribute("entity", aq);
      return "portalcustomer/Smsforget";
    }

    HttpSession session = request.getSession();
    aq.setLoginNameLike(false);
    aq.setPhoneNumberLike(false);
    List accs = this.portalaccountService
      .getPortalaccountList(aq);
    if ((accs != null) && (accs.size() == 1)) {
      PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
      Portalaccount acc = (Portalaccount)accs.get(0);
      session.setAttribute("account", acc);
      model.addAttribute("entity", acc);
      model.addAttribute("msg", "验证通过，请重置密码！！");
      return "portalcustomer/SmsforgetSave";
    }
    model.addAttribute("msg", "该用户和手机号不匹配！！");
    return "portalcustomer/Smsforget";
  }

  @RequestMapping({"/customerSmsForgetSave.action"})
  public String customerSmsForgetSave(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portalaccount acc = (Portalaccount)session.getAttribute("account");
    if (acc == null) {
      model.addAttribute("msg", "用户信息丢失，请重试！！");
      return "portalcustomer/Smsforget";
    }
    if (stringUtils.isBlank(aq.getPassword())) {
      model.addAttribute("entity", acc);
      model.addAttribute("msg", "密码格式错误！！");
      return "portalcustomer/SmsforgetSave";
    }
    acc.setPassword(aq.getPassword());
    this.portalaccountService.updatePortalaccountByKey(acc);
    session.removeAttribute("account");

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    if (cookies != null)
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
      }
    String isOnline;
    if (OnlineMap.getInstance().getOnlineUserMap()
      .containsKey(cip + ":" + cbasip))
      isOnline = "在线";
    else {
      isOnline = "离线";
    }

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    model.addAttribute("entity", acc);
    model.addAttribute("isOnline", isOnline);
    model.addAttribute("msg", "密码重置成功！");
    session.setAttribute("username", acc.getLoginName());
    session.setAttribute("password", acc.getPassword());
    return "portalcustomer/index";
  }

  @RequestMapping({"/customerFind.action"})
  public String customerForget(ModelMap model, HttpServletRequest request)
  {
    model.addAttribute("msg", "请填写要找回密码的用户名！！");
    return "portalcustomer/forget";
  }

  @RequestMapping({"/customerForget.action"})
  public String customerForget(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
  {
    if (stringUtils.isBlank(aq.getLoginName())) {
      model.addAttribute("msg", "请正确填写用户名！！");
      return "portalcustomer/forget";
    }
    HttpSession session = request.getSession();
    List accs = this.portalaccountService
      .getPortalaccountList(aq);
    if ((accs != null) && (accs.size() == 1)) {
      Portalaccount acc = (Portalaccount)accs.get(0);
      String ans = acc.getEx1();
      if (stringUtils.isBlank(ans)) {
        model.addAttribute("msg", "该用户没有设置找回密码问题，请联系管理员！！");
        return "portalcustomer/forget";
      }
      ans = acc.getEx2();
      if (stringUtils.isBlank(ans)) {
        model.addAttribute("msg", "该用户没有设置找回密码问题，请联系管理员！！");
        return "portalcustomer/forget";
      }
      session.setAttribute("account", acc);
      model.addAttribute("msg", "请输入找回密码问题答案！！");
      model.addAttribute("entity", acc);
      return "portalcustomer/forgetDo";
    }
    model.addAttribute("msg", "请正确填写用户名！！");
    return "portalcustomer/forget";
  }

  @RequestMapping({"/customerForgetDo.action"})
  public String customerForgetDo(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portalaccount acc = (Portalaccount)session.getAttribute("account");
    if (acc == null) {
      model.addAttribute("msg", "用户信息丢失，请重试！！");
      return "portalcustomer/forget";
    }
    String ans = acc.getEx2();
    if (stringUtils.isBlank(ans)) {
      model.addAttribute("msg", "该用户没有设置找回密码问题，请联系管理员！！");
      return "portalcustomer/forget";
    }
    if (stringUtils.isBlank(aq.getEx2())) {
      model.addAttribute("msg", "回答不正确！！");
      return "portalcustomer/forget";
    }
    if (ans.equals(aq.getEx2())) {
      model.addAttribute("entity", acc);
      model.addAttribute("msg", "验证通过，请重置密码！！");
      return "portalcustomer/forgetSave";
    }
    model.addAttribute("msg", "回答不正确！！");
    return "portalcustomer/forget";
  }

  @RequestMapping({"/customerForgetSave.action"})
  public String customerForgetSave(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portalaccount acc = (Portalaccount)session.getAttribute("account");
    if (acc == null) {
      model.addAttribute("msg", "用户信息丢失，请重试！！");
      return "portalcustomer/forget";
    }
    if (stringUtils.isBlank(aq.getPassword())) {
      model.addAttribute("entity", acc);
      model.addAttribute("msg", "密码格式错误！！");
      return "portalcustomer/forgetSave";
    }
    acc.setPassword(aq.getPassword());
    this.portalaccountService.updatePortalaccountByKey(acc);
    session.removeAttribute("account");

    Cookie[] cookies = request.getCookies();
    String cip = "";
    String cbasip = "";
    if (cookies != null)
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("ip"))
          cip = cookies[i].getValue();
        if (cookies[i].getName().equals("basip"))
          cbasip = cookies[i].getValue();
      }
    String isOnline;
    if (OnlineMap.getInstance().getOnlineUserMap()
      .containsKey(cip + ":" + cbasip))
      isOnline = "在线";
    else {
      isOnline = "离线";
    }

    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    model.addAttribute("entity", acc);
    model.addAttribute("isOnline", isOnline);
    model.addAttribute("msg", "密码重置成功！");
    session.setAttribute("username", acc.getLoginName());
    session.setAttribute("password", acc.getPassword());
    return "portalcustomer/index";
  }

  @RequestMapping({"/customerOnline.action"})
  public String Online(Integer Page, ModelMap model, HttpServletRequest request)
    throws ParseException
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);

    int count = 0;
    Iterator it = OnlineMap.getInstance().getOnlineUserMap()
      .keySet().iterator();
    while (it.hasNext()) {
      String[] loginInfo = 
        (String[])OnlineMap.getInstance().getOnlineUserMap()
        .get(it.next());
      Boolean isIt = Boolean.valueOf(true);
      if (!acc.getLoginName().equals(loginInfo[0])) {
        isIt = Boolean.valueOf(false);
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
    Iterator iterator = OnlineMap.getInstance().getOnlineUserMap()
      .keySet().iterator();
    while (iterator.hasNext()) {
      OnlineInfo onlineInfo = new OnlineInfo();
      String host = (String)iterator.next();
      String[] loginInfo = 
        (String[])OnlineMap.getInstance().getOnlineUserMap()
        .get(host);
      String username = loginInfo[0];
      String time = loginInfo[3];
      String mac = loginInfo[4];

      String inS = "0 M";
      String outS = "0 M";
      String octetsS = "0 M";
      try {
        double in = Double.valueOf(loginInfo[7]).doubleValue();
        double out = Double.valueOf(loginInfo[8]).doubleValue();
        double octets = in + out;
        in /= 1048576.0D;
        out /= 1048576.0D;
        octets /= 1048576.0D;
        inS = df.format(in);
        outS = df.format(out);
        octetsS = df.format(octets);
        if (inS.startsWith(".")) {
          inS = "0" + inS;
        }
        if (outS.startsWith(".")) {
          outS = "0" + outS;
        }
        if (octetsS.startsWith(".")) {
          octetsS = "0" + octetsS;
        }
        inS = inS + " M";
        outS = outS + " M";
        octetsS = octetsS + " M";
      }
      catch (Exception localException) {
      }
      String type = loginInfo[6];
      String basname = loginInfo[11];
      String ssid = loginInfo[12];
      String apmac = loginInfo[13];
      String auto = loginInfo[14];
      String agent = loginInfo[15];
      if (stringUtils.isBlank(basname)) {
        basname = "base";
      }
      if (stringUtils.isBlank(ssid)) {
        ssid = "unknow";
      }
      if (stringUtils.isBlank(apmac)) {
        apmac = "unknow";
      }
      if (stringUtils.isBlank(agent)) {
        agent = "unknow";
      }

      Date loginTime = ThreadLocalDateUtil.parse(time);
      String nowString = ThreadLocalDateUtil.format(new Date());
      Date nowTime = ThreadLocalDateUtil.parse(nowString);
      Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
      costTime = Long.valueOf(costTime.longValue() / 60000L);
      Boolean isIt = Boolean.valueOf(true);
      if (!acc.getLoginName().equals(loginInfo[0])) {
        isIt = Boolean.valueOf(false);
      }
      if (isIt.booleanValue()) {
        if (id.intValue() > (Page.intValue() - 1) * 10) {
          onlineInfo.setIp(host);
          onlineInfo.setLoginName(username);
          onlineInfo.setStartDate(loginTime);
          onlineInfo.setTime(costTime);
          onlineInfo.setMac(mac);
          onlineInfo.setInS(inS);
          onlineInfo.setOutS(outS);
          onlineInfo.setOctetsS(octetsS);

          if ("0".equals(type))
            onlineInfo.setType("一键认证");
          else if ("1".equals(type))
            onlineInfo.setType("本地用户");
          else if ("2".equals(type))
            onlineInfo.setType("Radius");
          else if ("3".equals(type))
            onlineInfo.setType("APP认证");
          else if ("4".equals(type))
            onlineInfo.setType("短信认证");
          else if ("5".equals(type))
            onlineInfo.setType("微信认证");
          else if ("6".equals(type))
            onlineInfo.setType("公众号认证");
          else if ("7".equals(type))
            onlineInfo.setType("访客认证");
          else if ("8".equals(type)) {
            onlineInfo.setType("延迟认证");
          }
          onlineInfo.setBasname(basname);
          onlineInfo.setSsid(ssid);
          onlineInfo.setApmac(apmac);
          onlineInfo.setAuto(auto);
          onlineInfo.setAgent(agent);
          onlineInfo.setState(acc.getState());
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
    return "portalcustomer/online";
  }

  @RequestMapping({"/customerkick.action"})
  public String kick(@RequestParam String ip, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);
    String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(ip);
    if ((loginInfo != null) && 
      (acc.getLoginName().equals(loginInfo[0]))) {
      Kick.kickUserByCustomer(ip);
      if (stringUtils.isNotBlank(loginInfo[4])) {
        PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
        macsq.setAccountId(acc.getId());
        macsq.setMac(PortalUtil.MacFormat(loginInfo[4]));
        macsq.setMacLike(false);
        this.macsService.deleteByQuery(macsq);
      }

    }

    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      String acctSessionId = (String)iterator.next();
      String[] radiusOnlineInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
        (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
        if (stringUtils.isNotBlank(loginInfo[4])) {
          if (loginInfo[4].equals(radiusOnlineInfo[3])) {
            COAThread.COA_Account_Cost(radiusOnlineInfo, 
              "By Customer COA");
            if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
              PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
              macsq.setAccountId(acc.getId());
              macsq.setMac(
                PortalUtil.MacFormat(radiusOnlineInfo[3]));
              macsq.setMacLike(false);
              this.macsService.deleteByQuery(macsq);
            }
          }
        } else {
          int i = ip.lastIndexOf(":");
          String uip = ip.substring(0, i);
          if (uip.equals(radiusOnlineInfo[2])) {
            COAThread.COA_Account_Cost(radiusOnlineInfo, 
              "By Customer COA");
            if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
              PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
              macsq.setAccountId(acc.getId());
              macsq.setMac(
                PortalUtil.MacFormat(radiusOnlineInfo[3]));
              macsq.setMacLike(false);
              this.macsService.deleteByQuery(macsq);
            }
          }
        }
      }

    }

    return "redirect:/customerOnline.action";
  }

  @RequestMapping(value={"/customerkicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String kicks(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);
    Iterator it = OnlineMap.getInstance().getOnlineUserMap()
      .keySet().iterator();
    while (it.hasNext()) {
      String host = (String)it.next();
      String[] loginInfo = 
        (String[])OnlineMap.getInstance().getOnlineUserMap()
        .get(host);
      if ((loginInfo != null) && 
        (acc.getLoginName().equals(loginInfo[0]))) {
        Kick.kickUserByCustomer(host);
        if (stringUtils.isNotBlank(loginInfo[4])) {
          PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
          macsq.setAccountId(acc.getId());
          macsq.setMac(PortalUtil.MacFormat(loginInfo[4]));
          macsq.setMacLike(false);
          this.macsService.deleteByQuery(macsq);
        }
      }

    }

    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      String acctSessionId = (String)iterator.next();
      String[] radiusOnlineInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
        (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
        COAThread.COA_Account_Cost(radiusOnlineInfo, 
          "By Customer COA");
        if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
          PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
          macsq.setAccountId(acc.getId());
          macsq.setMac(PortalUtil.MacFormat(radiusOnlineInfo[3]));
          macsq.setMacLike(false);
          this.macsService.deleteByQuery(macsq);
        }
      }

    }

    return "redirect:/customerOnline.action";
  }

  @RequestMapping({"/customerRadiusOnline.action"})
  public String RadiusOnline(Integer Page, ModelMap model, HttpServletRequest request)
    throws ParseException
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);

    int count = 0;
    Iterator it = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (it.hasNext()) {
      String acctSessionId = (String)it.next();
      String[] loginInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      String username = loginInfo[4];
      Boolean isIt = Boolean.valueOf(true);

      if (!acc.getLoginName().equals(username)) {
        isIt = Boolean.valueOf(false);
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
      String acctSessionId = (String)iterator.next();
      String[] loginInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      String ip = loginInfo[2];
      String mac = loginInfo[3];
      String username = loginInfo[4];
      Boolean isIt = Boolean.valueOf(true);

      if (!acc.getLoginName().equals(username)) {
        isIt = Boolean.valueOf(false);
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
    return "portalcustomer/onlineR";
  }

  @RequestMapping({"/customerRadiuskick.action"})
  public String Radiuskick(@RequestParam String acctSessionId, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);

    String[] radiusOnlineInfo = 
      (String[])RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().get(acctSessionId);
    if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
      (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
      COAThread.COA_Account_Cost(radiusOnlineInfo, "By Customer COA");
      if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
        PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
        macsq.setAccountId(acc.getId());
        macsq.setMac(PortalUtil.MacFormat(radiusOnlineInfo[3]));
        macsq.setMacLike(false);
        this.macsService.deleteByQuery(macsq);
      }

    }

    return "redirect:/customerRadiusOnline.action";
  }

  @RequestMapping(value={"/customerRadiuskicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String Radiuskicks(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");
    if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    PortalaccountQuery aq = new PortalaccountQuery();
    aq.setLoginName(un);
    aq.setPassword(pwd);
    List accounts = this.portalaccountService
      .getPortalaccountList(aq);
    if (accounts.size() != 1) {
      model.addAttribute("msg", "用户验证失败，请先登录！！");
      return "portalcustomer/login";
    }
    Portalaccount acc = (Portalaccount)accounts.get(0);

    Iterator iterator = RadiusOnlineMap.getInstance()
      .getRadiusOnlineMap().keySet().iterator();
    while (iterator.hasNext()) {
      String acctSessionId = (String)iterator.next();
      String[] radiusOnlineInfo = 
        (String[])RadiusOnlineMap.getInstance()
        .getRadiusOnlineMap().get(acctSessionId);
      if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
        (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
        COAThread.COA_Account_Cost(radiusOnlineInfo, 
          "By Customer COA");
        if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
          PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
          macsq.setAccountId(acc.getId());
          macsq.setMac(PortalUtil.MacFormat(radiusOnlineInfo[3]));
          macsq.setMacLike(false);
          this.macsService.deleteByQuery(macsq);
        }
      }

    }

    return "redirect:/customerRadiusOnline.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.portalcustomerController
 * JD-Core Version:    0.6.2
 */