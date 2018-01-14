/*      */ package com.leeson.core.controller;
/*      */ 
/*      */ import com.leeson.common.page.Pagination;
/*      */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Config;
/*      */ import com.leeson.core.bean.OnlineInfo;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalaccountgroup;
/*      */ import com.leeson.core.bean.Portalcard;
/*      */ import com.leeson.core.bean.Portalorder;
/*      */ import com.leeson.core.bean.Portalsmsapi;
/*      */ import com.leeson.core.bean.RadiusOnlineInfo;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalaccountmacsQuery;
/*      */ import com.leeson.core.query.PortalcardQuery;
/*      */ import com.leeson.core.query.PortallinkrecordQuery;
/*      */ import com.leeson.core.query.PortalorderQuery;
/*      */ import com.leeson.core.query.PortalsmsapiQuery;
/*      */ import com.leeson.core.query.PortalspeedQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalaccountgroupService;
/*      */ import com.leeson.core.service.PortalaccountmacsService;
/*      */ import com.leeson.core.service.PortalcardService;
/*      */ import com.leeson.core.service.PortalconfigService;
/*      */ import com.leeson.core.service.PortallinkrecordService;
/*      */ import com.leeson.core.service.PortalorderService;
/*      */ import com.leeson.core.service.PortalsmsapiService;
/*      */ import com.leeson.core.service.PortalspeedService;
/*      */ import com.leeson.core.utils.Kick;
/*      */ import com.leeson.core.utils.MyUtils;
/*      */ import com.leeson.portal.core.model.OnlineMap;
/*      */ import com.leeson.portal.core.model.PhoneCodeMap;
/*      */ import com.leeson.portal.core.service.utils.PortalUtil;
/*      */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*      */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*      */ import com.leeson.radius.core.utils.COAThread;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Controller;
/*      */ import org.springframework.ui.ModelMap;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.RequestParam;
/*      */ 
/*      */ @Controller
/*      */ public class portalcustomerController
/*      */ {
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountService portalaccountService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountmacsService portalaccountmacsService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalcardService portalcardService;
/*      */ 
/*      */   @Autowired
/*      */   private PortallinkrecordService portallinkrecordService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalconfigService portalconfigService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalorderService portalorderService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalsmsapiService portalsmsapiService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalspeedService portalspeedService;
/*      */ 
/*      */   @Autowired
/*      */   private ConfigService configService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountgroupService portalaccountgroupService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountmacsService macsService;
/*   93 */   private static SimpleDateFormat format = new SimpleDateFormat(
/*   94 */     "yyyy-MM-dd HH:mm:ss");
/*      */ 
/*   95 */   private static DecimalFormat df = new DecimalFormat(".##");
/*      */ 
/*      */   @RequestMapping({"/customerPayRecord.action"})
/*      */   public String PayRecord(PortalcardQuery query, String query_begin_time, String query_end_time, ModelMap model, HttpServletRequest request)
/*      */   {
/*  102 */     HttpSession session = request.getSession();
/*  103 */     String un = (String)session.getAttribute("username");
/*  104 */     String pwd = (String)session.getAttribute("password");
/*  105 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/*  106 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  107 */       return "portalcustomer/login";
/*      */     }
/*  109 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  110 */     aq.setLoginName(un);
/*  111 */     aq.setPassword(pwd);
/*  112 */     List accounts = this.portalaccountService
/*  113 */       .getPortalaccountList(aq);
/*  114 */     if (accounts.size() != 1) {
/*  115 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  116 */       return "portalcustomer/login";
/*      */     }
/*  118 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*      */ 
/*  120 */     query.setAccountId(acc.getId());
/*  121 */     query.setAccountDel(Integer.valueOf(0));
/*  122 */     query.orderbyPayDate(false);
/*  123 */     if (stringUtils.isBlank(query.getCategoryType())) {
/*  124 */       query.setCategoryType(null);
/*      */     }
/*  126 */     if (stringUtils.isBlank(query.getPayType())) {
/*  127 */       query.setPayType(null);
/*      */     }
/*  129 */     Date begin_time = null;
/*  130 */     Date end_time = null;
/*      */     try {
/*  132 */       if (stringUtils.isNotBlank(query_begin_time)) {
/*  133 */         begin_time = format.parse(query_begin_time);
/*  134 */         query.setBegin_time(begin_time);
/*      */       }
/*      */     } catch (Exception e) {
/*  137 */       model.addAttribute("msg", "开始日期格式错误！");
/*  138 */       query_begin_time = null;
/*      */     }
/*      */     try {
/*  141 */       if (stringUtils.isNotBlank(query_end_time)) {
/*  142 */         end_time = format.parse(query_end_time);
/*  143 */         query.setEnd_time(end_time);
/*      */       }
/*      */     } catch (Exception e) {
/*  146 */       model.addAttribute("msg", "结束日期格式错误！");
/*  147 */       query_end_time = null;
/*      */     }
/*      */ 
/*  150 */     Pagination pagination = this.portalcardService
/*  151 */       .getPortalcardListWithPage(query);
/*  152 */     model.addAttribute("pagination", pagination);
/*  153 */     model.addAttribute("query", query);
/*  154 */     model.addAttribute("query_begin_time", query_begin_time);
/*  155 */     model.addAttribute("query_end_time", query_end_time);
/*      */ 
/*  157 */     return "portalcustomer/payRecord";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerLinkRecord.action"})
/*      */   public String LinkRecord(PortallinkrecordQuery query, String query_begin_time, String query_end_time, ModelMap model, HttpServletRequest request)
/*      */   {
/*  166 */     HttpSession session = request.getSession();
/*  167 */     String un = (String)session.getAttribute("username");
/*  168 */     String pwd = (String)session.getAttribute("password");
/*  169 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/*  170 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  171 */       return "portalcustomer/login";
/*      */     }
/*  173 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  174 */     aq.setLoginName(un);
/*  175 */     aq.setPassword(pwd);
/*  176 */     List accounts = this.portalaccountService
/*  177 */       .getPortalaccountList(aq);
/*  178 */     if (accounts.size() != 1) {
/*  179 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  180 */       return "portalcustomer/login";
/*      */     }
/*  182 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*      */ 
/*  184 */     query.setUid(acc.getId());
/*  185 */     query.setAccountDel(Integer.valueOf(0));
/*  186 */     query.orderbyId(false);
/*  187 */     query.setIpLike(true);
/*  188 */     if (stringUtils.isBlank(query.getState())) {
/*  189 */       query.setState(null);
/*      */     }
/*  191 */     if (stringUtils.isBlank(query.getIp())) {
/*  192 */       query.setIp(null);
/*      */     }
/*      */ 
/*  195 */     Date begin_time = null;
/*  196 */     Date end_time = null;
/*      */     try {
/*  198 */       if (stringUtils.isNotBlank(query_begin_time)) {
/*  199 */         begin_time = format.parse(query_begin_time);
/*  200 */         query.setBegin_time(begin_time);
/*      */       }
/*      */     } catch (Exception e) {
/*  203 */       model.addAttribute("msg", "开始日期格式错误！");
/*  204 */       query_begin_time = null;
/*      */     }
/*      */     try {
/*  207 */       if (stringUtils.isNotBlank(query_end_time)) {
/*  208 */         end_time = format.parse(query_end_time);
/*  209 */         query.setEnd_time(end_time);
/*      */       }
/*      */     } catch (Exception e) {
/*  212 */       model.addAttribute("msg", "结束日期格式错误！");
/*  213 */       query_end_time = null;
/*      */     }
/*      */ 
/*  216 */     Pagination pagination = this.portallinkrecordService
/*  217 */       .getPortallinkrecordListWithPage(query);
/*  218 */     model.addAttribute("pagination", pagination);
/*  219 */     model.addAttribute("query", query);
/*  220 */     model.addAttribute("query_begin_time", query_begin_time);
/*  221 */     model.addAttribute("query_end_time", query_end_time);
/*      */ 
/*  223 */     return "portalcustomer/linkRecord";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerAdd.action"})
/*      */   public String addV(ModelMap model)
/*      */   {
/*  229 */     Integer haveAcc = this.portalaccountService
/*  230 */       .getPortalaccountCount(new PortalaccountQuery());
/*  231 */     if ((haveAcc != null) && 
/*  232 */       (haveAcc.intValue() >= Integer.valueOf(
/*  233 */       ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
/*  233 */       .getCoreConfigMap().get("core"))[1]).intValue()))
/*      */     {
/*  234 */       model.addAttribute("msg", "账户超过授权数！");
/*  235 */       return "portalcustomer/login";
/*      */     }
/*      */ 
/*  239 */     if (this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue() == 0) {
/*  240 */       model.addAttribute("msg", "对不起，管理员设置不允许自助创建新账户！！");
/*  241 */       return "portalcustomer/login";
/*  242 */     }if (2 == this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue()) {
/*  243 */       model.addAttribute("msg", "请认真填写账户信息！！");
/*  244 */       return "portalcustomer/addPhone";
/*  245 */     }if (3 == this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue()) {
/*  246 */       model.addAttribute("msg", "请认真填写账户信息！！");
/*  247 */       return "portalcustomer/addSms";
/*      */     }
/*  249 */     model.addAttribute("msg", "请认真填写账户信息！！");
/*  250 */     return "portalcustomer/add";
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/customerAddSms.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*      */   public String addSms(Portalaccount e, String yzm, ModelMap model, HttpServletRequest request)
/*      */   {
/*  257 */     String phone = e.getPhoneNumber();
/*  258 */     if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
/*  259 */       model.addAttribute("msg", "请输入手机号和验证码！！");
/*  260 */       model.addAttribute("entity", e);
/*  261 */       return "portalcustomer/addSms";
/*      */     }
/*  263 */     if (phone.length() != 11) {
/*  264 */       model.addAttribute("msg", "手机号不正确！！");
/*  265 */       model.addAttribute("entity", e);
/*  266 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  267 */       return "portalcustomer/addSms";
/*      */     }
/*      */     try {
/*  270 */       Long.parseLong(phone);
/*      */     } catch (Exception ex) {
/*  272 */       model.addAttribute("msg", "手机号不正确！！");
/*  273 */       model.addAttribute("entity", e);
/*  274 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  275 */       return "portalcustomer/addSms";
/*      */     }
/*  277 */     String code = "";
/*  278 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/*  279 */     query.setState("1");
/*  280 */     List smsList = this.portalsmsapiService
/*  281 */       .getPortalsmsapiList(query);
/*      */     Portalsmsapi smsapi;
/*  283 */     if (smsList.size() > 0)
/*  284 */       smsapi = (Portalsmsapi)smsList.get(0);
/*      */     else {
/*  286 */       smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*      */     }
/*  288 */     Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
/*  289 */     Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
/*      */     try {
/*  291 */       Date saveDate = (Date)objs[1];
/*  292 */       Long nowTime = Long.valueOf(new Date().getTime());
/*  293 */       if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
/*  294 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  295 */         model.addAttribute("msg", "验证码已过期，请重新获取验证码！");
/*  296 */         model.addAttribute("entity", e);
/*  297 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  298 */         return "portalcustomer/addSms";
/*      */       }
/*  300 */       code = (String)objs[0];
/*      */     } catch (Exception ex) {
/*  302 */       model.addAttribute("msg", "手机号或验证码不正确！");
/*  303 */       model.addAttribute("entity", e);
/*  304 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  305 */       return "portalcustomer/addSms";
/*      */     }
/*      */ 
/*  308 */     if (!yzm.equals(code)) {
/*  309 */       model.addAttribute("msg", "验证码不正确！");
/*  310 */       model.addAttribute("entity", e);
/*  311 */       return "portalcustomer/addSms";
/*      */     }
/*      */ 
/*  314 */     if ((stringUtils.isBlank(e.getLoginName())) || 
/*  315 */       (stringUtils.isBlank(e.getPassword()))) {
/*  316 */       model.addAttribute("msg", "登录名和密码不能为空！");
/*  317 */       model.addAttribute("entity", e);
/*  318 */       model.addAttribute("yzm", yzm);
/*  319 */       return "portalcustomer/addSms";
/*      */     }
/*  321 */     if (!MyUtils.checkUserName(e.getLoginName())) {
/*  322 */       model.addAttribute("msg", "登录名不符合规范!!");
/*  323 */       e.setLoginName(null);
/*  324 */       model.addAttribute("entity", e);
/*  325 */       model.addAttribute("yzm", yzm);
/*  326 */       return "portalcustomer/addSms";
/*      */     }
/*  328 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  329 */     aq.setLoginName(e.getLoginName());
/*  330 */     aq.setLoginNameLike(false);
/*  331 */     if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
/*  332 */       model.addAttribute("msg", "登录名已经存在！");
/*  333 */       model.addAttribute("entity", e);
/*  334 */       model.addAttribute("yzm", yzm);
/*  335 */       return "portalcustomer/addSms";
/*      */     }
/*      */ 
/*  338 */     if (stringUtils.isBlank(e.getGender())) {
/*  339 */       e.setGender(null);
/*      */     }
/*      */ 
/*  342 */     Portalaccountgroup ag = this.portalaccountgroupService
/*  343 */       .getPortalaccountgroupByKey(Long.valueOf(1L));
/*  344 */     if (stringUtils.isBlank(e.getState())) {
/*  345 */       e.setState(ag.getState());
/*      */     }
/*  347 */     if (e.getMaclimitcount() == null) {
/*  348 */       e.setMaclimitcount(ag.getMaclimitcount());
/*      */     }
/*  350 */     if (e.getMaclimit() == null) {
/*  351 */       e.setMaclimit(ag.getMaclimit());
/*      */     }
/*  353 */     if (e.getAutologin() == null) {
/*  354 */       e.setAutologin(ag.getAutologin());
/*      */     }
/*  356 */     if (e.getSpeed() == null) {
/*  357 */       e.setSpeed(ag.getSpeed());
/*      */     }
/*  359 */     e.setDate(ag.getDate());
/*  360 */     e.setTime(ag.getTime());
/*  361 */     e.setOctets(ag.getOctets());
/*      */ 
/*  363 */     this.portalaccountService.addPortalaccount(e);
/*      */ 
/*  365 */     HttpSession session = request.getSession();
/*  366 */     session.setAttribute("username", e.getLoginName());
/*  367 */     session.setAttribute("password", e.getPassword());
/*      */ 
/*  369 */     PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*      */ 
/*  371 */     model.addAttribute("msg", "恭喜您，账户创建成功！！");
/*  372 */     return "redirect:/customerIndex.action";
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/customerAddPhone.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*      */   public String addPhone(Portalaccount e, String yzm, ModelMap model, HttpServletRequest request)
/*      */   {
/*  379 */     String phone = e.getLoginName();
/*  380 */     if ((stringUtils.isBlank(phone)) || (stringUtils.isBlank(yzm))) {
/*  381 */       model.addAttribute("msg", "请输入手机号和验证码！！");
/*  382 */       model.addAttribute("entity", e);
/*  383 */       return "portalcustomer/addPhone";
/*      */     }
/*  385 */     if (phone.length() != 11) {
/*  386 */       model.addAttribute("msg", "手机号不正确！！");
/*  387 */       model.addAttribute("entity", e);
/*  388 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  389 */       return "portalcustomer/addPhone";
/*      */     }
/*      */     try {
/*  392 */       Long.parseLong(phone);
/*      */     } catch (Exception ex) {
/*  394 */       model.addAttribute("msg", "手机号不正确！！");
/*  395 */       model.addAttribute("entity", e);
/*  396 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  397 */       return "portalcustomer/addPhone";
/*      */     }
/*  399 */     String code = "";
/*  400 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/*  401 */     query.setState("1");
/*  402 */     List smsList = this.portalsmsapiService
/*  403 */       .getPortalsmsapiList(query);
/*      */     Portalsmsapi smsapi;
/*  405 */     if (smsList.size() > 0)
/*  406 */       smsapi = (Portalsmsapi)smsList.get(0);
/*      */     else {
/*  408 */       smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*      */     }
/*  410 */     Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
/*  411 */     Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
/*      */     try {
/*  413 */       Date saveDate = (Date)objs[1];
/*  414 */       Long nowTime = Long.valueOf(new Date().getTime());
/*  415 */       if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
/*  416 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  417 */         model.addAttribute("msg", "验证码已过期，请重新获取验证码！");
/*  418 */         model.addAttribute("entity", e);
/*  419 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  420 */         return "portalcustomer/addPhone";
/*      */       }
/*  422 */       code = (String)objs[0];
/*      */     } catch (Exception ex) {
/*  424 */       model.addAttribute("msg", "手机号或验证码不正确！");
/*  425 */       model.addAttribute("entity", e);
/*  426 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  427 */       return "portalcustomer/addPhone";
/*      */     }
/*      */ 
/*  430 */     if (!yzm.equals(code)) {
/*  431 */       model.addAttribute("msg", "验证码不正确！");
/*  432 */       model.addAttribute("entity", e);
/*  433 */       return "portalcustomer/addPhone";
/*      */     }
/*      */ 
/*  436 */     if ((stringUtils.isBlank(e.getLoginName())) || 
/*  437 */       (stringUtils.isBlank(e.getPassword()))) {
/*  438 */       model.addAttribute("msg", "登录名和密码不能为空！");
/*  439 */       model.addAttribute("entity", e);
/*  440 */       model.addAttribute("yzm", yzm);
/*  441 */       return "portalcustomer/addPhone";
/*      */     }
/*  443 */     if (!MyUtils.checkUserName(e.getLoginName())) {
/*  444 */       model.addAttribute("msg", "登录名不符合规范!!");
/*  445 */       e.setLoginName(null);
/*  446 */       model.addAttribute("entity", e);
/*  447 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  448 */       return "portalcustomer/addPhone";
/*      */     }
/*  450 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  451 */     aq.setLoginName(e.getLoginName());
/*  452 */     aq.setLoginNameLike(false);
/*  453 */     if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
/*  454 */       model.addAttribute("msg", "登录名已经存在！");
/*  455 */       model.addAttribute("entity", e);
/*  456 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*  457 */       return "portalcustomer/addPhone";
/*      */     }
/*      */ 
/*  460 */     if (stringUtils.isBlank(e.getGender())) {
/*  461 */       e.setGender(null);
/*      */     }
/*      */ 
/*  464 */     Portalaccountgroup ag = this.portalaccountgroupService
/*  465 */       .getPortalaccountgroupByKey(Long.valueOf(1L));
/*  466 */     if (stringUtils.isBlank(e.getState())) {
/*  467 */       e.setState(ag.getState());
/*      */     }
/*  469 */     if (e.getMaclimitcount() == null) {
/*  470 */       e.setMaclimitcount(ag.getMaclimitcount());
/*      */     }
/*  472 */     if (e.getMaclimit() == null) {
/*  473 */       e.setMaclimit(ag.getMaclimit());
/*      */     }
/*  475 */     if (e.getAutologin() == null) {
/*  476 */       e.setAutologin(ag.getAutologin());
/*      */     }
/*  478 */     if (e.getSpeed() == null) {
/*  479 */       e.setSpeed(ag.getSpeed());
/*      */     }
/*  481 */     e.setDate(ag.getDate());
/*  482 */     e.setTime(ag.getTime());
/*  483 */     e.setOctets(ag.getOctets());
/*  484 */     e.setPhoneNumber(phone);
/*  485 */     this.portalaccountService.addPortalaccount(e);
/*      */ 
/*  487 */     HttpSession session = request.getSession();
/*  488 */     session.setAttribute("username", e.getLoginName());
/*  489 */     session.setAttribute("password", e.getPassword());
/*      */ 
/*  491 */     PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/*      */ 
/*  493 */     model.addAttribute("msg", "恭喜您，账户创建成功！！");
/*  494 */     return "redirect:/customerIndex.action";
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/customerAdd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*      */   public String add(Portalaccount e, ModelMap model, HttpServletRequest request)
/*      */   {
/*  501 */     if ((stringUtils.isBlank(e.getLoginName())) || 
/*  502 */       (stringUtils.isBlank(e.getPassword()))) {
/*  503 */       model.addAttribute("msg", "登录名和密码不能为空！");
/*  504 */       model.addAttribute("entity", e);
/*  505 */       return "portalcustomer/add";
/*      */     }
/*  507 */     if (!MyUtils.checkUserName(e.getLoginName())) {
/*  508 */       model.addAttribute("msg", "登录名不符合规范!!");
/*  509 */       e.setLoginName(null);
/*  510 */       model.addAttribute("entity", e);
/*  511 */       return "portalcustomer/add";
/*      */     }
/*  513 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  514 */     aq.setLoginName(e.getLoginName());
/*  515 */     aq.setLoginNameLike(false);
/*  516 */     if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
/*  517 */       model.addAttribute("msg", "登录名已经存在！");
/*  518 */       model.addAttribute("entity", e);
/*  519 */       return "portalcustomer/add";
/*      */     }
/*      */ 
/*  522 */     if (stringUtils.isBlank(e.getGender())) {
/*  523 */       e.setGender(null);
/*      */     }
/*      */ 
/*  526 */     Portalaccountgroup ag = this.portalaccountgroupService
/*  527 */       .getPortalaccountgroupByKey(Long.valueOf(1L));
/*  528 */     if (stringUtils.isBlank(e.getState())) {
/*  529 */       e.setState(ag.getState());
/*      */     }
/*  531 */     if (e.getMaclimitcount() == null) {
/*  532 */       e.setMaclimitcount(ag.getMaclimitcount());
/*      */     }
/*  534 */     if (e.getMaclimit() == null) {
/*  535 */       e.setMaclimit(ag.getMaclimit());
/*      */     }
/*  537 */     if (e.getAutologin() == null) {
/*  538 */       e.setAutologin(ag.getAutologin());
/*      */     }
/*  540 */     if (e.getSpeed() == null) {
/*  541 */       e.setSpeed(ag.getSpeed());
/*      */     }
/*  543 */     e.setDate(ag.getDate());
/*  544 */     e.setTime(ag.getTime());
/*  545 */     e.setOctets(ag.getOctets());
/*      */ 
/*  547 */     this.portalaccountService.addPortalaccount(e);
/*      */ 
/*  549 */     HttpSession session = request.getSession();
/*  550 */     session.setAttribute("username", e.getLoginName());
/*  551 */     session.setAttribute("password", e.getPassword());
/*      */ 
/*  553 */     model.addAttribute("msg", "恭喜您，账户创建成功！！");
/*  554 */     return "redirect:/customerIndex.action";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerEdit.action"})
/*      */   public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
/*      */   {
/*  562 */     HttpSession session = request.getSession();
/*  563 */     String un = (String)session.getAttribute("username");
/*  564 */     String pwd = (String)session.getAttribute("password");
/*  565 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/*  566 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  567 */       return "portalcustomer/login";
/*      */     }
/*  569 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  570 */     aq.setLoginName(un);
/*  571 */     aq.setPassword(pwd);
/*  572 */     List accounts = this.portalaccountService
/*  573 */       .getPortalaccountList(aq);
/*  574 */     if (accounts.size() != 1) {
/*  575 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  576 */       return "portalcustomer/login";
/*      */     }
/*  578 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*  579 */     model.addAttribute("entity", acc);
/*  580 */     model.addAttribute("msg", "修改账户信息，如果不修改密码，请留空！！");
/*  581 */     return "portalcustomer/save";
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/customerEdit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*      */   public String edit(Portalaccount e, String oldpassword, ModelMap model, HttpServletRequest request)
/*      */   {
/*  588 */     HttpSession session = request.getSession();
/*  589 */     String un = (String)session.getAttribute("username");
/*  590 */     String pwd = (String)session.getAttribute("password");
/*  591 */     String basip = (String)session.getAttribute("basip");
/*  592 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/*  593 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  594 */       return "portalcustomer/login";
/*      */     }
/*  596 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  597 */     aq.setLoginName(un);
/*  598 */     aq.setPassword(pwd);
/*  599 */     List accounts = this.portalaccountService
/*  600 */       .getPortalaccountList(aq);
/*  601 */     if (accounts.size() != 1) {
/*  602 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  603 */       return "portalcustomer/login";
/*      */     }
/*  605 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*  606 */     long accid = acc.getId().longValue();
/*  607 */     long eid = e.getId().longValue();
/*  608 */     if (accid != eid) {
/*  609 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  610 */       return "portalcustomer/login";
/*      */     }
/*      */ 
/*  613 */     if (!acc.getPassword().equals(oldpassword)) {
/*  614 */       e.setId(acc.getId());
/*  615 */       e.setLoginName(acc.getLoginName());
/*  616 */       model.addAttribute("entity", e);
/*  617 */       model.addAttribute("msg", "原密码不正确！！");
/*  618 */       return "portalcustomer/save";
/*      */     }
/*      */ 
/*  621 */     if (stringUtils.isNotBlank(e.getPassword())) {
/*  622 */       acc.setPassword(e.getPassword());
/*      */     }
/*      */ 
/*  625 */     if (stringUtils.isBlank(e.getName()))
/*  626 */       acc.setName(null);
/*      */     else {
/*  628 */       acc.setName(e.getName());
/*      */     }
/*      */ 
/*  631 */     if (stringUtils.isBlank(e.getGender()))
/*  632 */       acc.setGender(null);
/*      */     else {
/*  634 */       acc.setGender(e.getGender());
/*      */     }
/*      */ 
/*  637 */     if (stringUtils.isBlank(e.getPhoneNumber()))
/*  638 */       acc.setPhoneNumber(null);
/*      */     else {
/*  640 */       acc.setPhoneNumber(e.getPhoneNumber());
/*      */     }
/*      */ 
/*  643 */     if (stringUtils.isBlank(e.getEmail()))
/*  644 */       acc.setEmail(null);
/*      */     else {
/*  646 */       acc.setEmail(e.getEmail());
/*      */     }
/*      */ 
/*  649 */     if (stringUtils.isBlank(e.getDescription()))
/*  650 */       acc.setDescription(null);
/*      */     else {
/*  652 */       acc.setDescription(e.getDescription());
/*      */     }
/*      */ 
/*  655 */     if (stringUtils.isBlank(e.getIdnumber()))
/*  656 */       acc.setIdnumber(null);
/*      */     else {
/*  658 */       acc.setIdnumber(e.getIdnumber());
/*      */     }
/*      */ 
/*  661 */     if (stringUtils.isBlank(e.getAddress()))
/*  662 */       acc.setAddress(null);
/*      */     else {
/*  664 */       acc.setAddress(e.getAddress());
/*      */     }
/*      */ 
/*  667 */     if (stringUtils.isBlank(e.getEx1()))
/*  668 */       acc.setEx1(null);
/*      */     else {
/*  670 */       acc.setEx1(e.getEx1());
/*      */     }
/*  672 */     if (stringUtils.isBlank(e.getEx2()))
/*  673 */       acc.setEx2(null);
/*      */     else {
/*  675 */       acc.setEx2(e.getEx2());
/*      */     }
/*      */ 
/*  678 */     this.portalaccountService.updatePortalaccountByKeyAll(acc);
/*      */     String isOnline;
/*  685 */     if (OnlineMap.getInstance()
/*  683 */       .getOnlineUserMap()
/*  684 */       .containsKey(
/*  685 */       GetNgnixRemotIP.getRemoteAddrIp(request) + ":" + basip))
/*  686 */       isOnline = "在线";
/*      */     else {
/*  688 */       isOnline = "离线";
/*      */     }
/*  690 */     List speeds = this.portalspeedService
/*  691 */       .getPortalspeedList(new PortalspeedQuery());
/*  692 */     model.addAttribute("speeds", speeds);
/*  693 */     model.addAttribute("entity", acc);
/*  694 */     model.addAttribute("isOnline", isOnline);
/*  695 */     model.addAttribute("msg", "恭喜您，账户信息修改成功！");
/*  696 */     return "portalcustomer/index";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerLogin.action"})
/*      */   public String Login(ModelMap model, HttpServletRequest request)
/*      */   {
/*  702 */     HttpSession session = request.getSession();
/*  703 */     String un = (String)session.getAttribute("username");
/*  704 */     String pwd = (String)session.getAttribute("password");
/*  705 */     if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
/*  706 */       return "redirect:/customerIndex.action";
/*      */     }
/*  708 */     model.addAttribute("msg", "欢迎使用 ！！");
/*  709 */     return "portalcustomer/login";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerLoginOut.action"})
/*      */   public String LoginOut(ModelMap model, HttpServletRequest request)
/*      */   {
/*  715 */     HttpSession session = request.getSession();
/*      */ 
/*  717 */     session.removeAttribute("password");
/*  718 */     model.addAttribute("msg", "注销成功！");
/*  719 */     return "portalcustomer/login";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerIndex.action"})
/*      */   public String index(String username, String password, ModelMap model, HttpServletRequest request)
/*      */   {
/*  726 */     HttpSession session = request.getSession();
/*      */ 
/*  729 */     Cookie[] cookies = request.getCookies();
/*  730 */     String cip = "";
/*  731 */     String cbasip = "";
/*  732 */     if (cookies != null)
/*  733 */       for (int i = 0; i < cookies.length; i++) {
/*  734 */         if (cookies[i].getName().equals("ip"))
/*  735 */           cip = cookies[i].getValue();
/*  736 */         if (cookies[i].getName().equals("basip"))
/*  737 */           cbasip = cookies[i].getValue();
/*      */       }
/*      */     String isOnline;
/*  743 */     if (OnlineMap.getInstance().getOnlineUserMap()
/*  743 */       .containsKey(cip + ":" + cbasip))
/*  744 */       isOnline = "在线";
/*      */     else {
/*  746 */       isOnline = "离线";
/*      */     }
/*      */ 
/*  749 */     List speeds = this.portalspeedService
/*  750 */       .getPortalspeedList(new PortalspeedQuery());
/*  751 */     model.addAttribute("speeds", speeds);
/*      */ 
/*  753 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  754 */     aq.setLoginNameLike(false);
/*  755 */     aq.setPasswordLike(false);
/*      */ 
/*  757 */     if ((stringUtils.isNotBlank(username)) && 
/*  758 */       (stringUtils.isNotBlank(password))) {
/*  759 */       aq.setLoginName(username);
/*  760 */       aq.setPassword(password);
/*  761 */       List accounts = this.portalaccountService.getPortalaccountList(aq);
/*  762 */       if (accounts.size() == 1) {
/*  763 */         Portalaccount account = (Portalaccount)accounts.get(0);
/*  764 */         if (password.equals(account.getPassword())) {
/*  765 */           model.addAttribute("entity", account);
/*  766 */           model.addAttribute("isOnline", isOnline);
/*  767 */           model.addAttribute("msg", "登录成功！");
/*  768 */           session.setAttribute("username", username);
/*  769 */           session.setAttribute("password", password);
/*  770 */           return "portalcustomer/index";
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  775 */     String un = (String)session.getAttribute("username");
/*  776 */     String pwd = (String)session.getAttribute("password");
/*  777 */     if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
/*  778 */       aq.setLoginName(un);
/*  779 */       aq.setPassword(pwd);
/*  780 */       List accounts = this.portalaccountService.getPortalaccountList(aq);
/*  781 */       if (accounts.size() == 1) {
/*  782 */         Portalaccount account = (Portalaccount)accounts.get(0);
/*  783 */         if (pwd.equals(account.getPassword())) {
/*  784 */           model.addAttribute("entity", account);
/*  785 */           model.addAttribute("isOnline", isOnline);
/*  786 */           model.addAttribute("msg", "登录成功！");
/*  787 */           return "portalcustomer/index";
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  793 */     model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  794 */     return "portalcustomer/login";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerPay.action"})
/*      */   public String pay(ModelMap model, HttpServletRequest request)
/*      */   {
/*  801 */     HttpSession session = request.getSession();
/*  802 */     String un = (String)session.getAttribute("username");
/*  803 */     String pwd = (String)session.getAttribute("password");
/*  804 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/*  805 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  806 */       return "portalcustomer/login";
/*      */     }
/*  808 */     PortalaccountQuery aq = new PortalaccountQuery();
/*  809 */     aq.setLoginName(un);
/*  810 */     aq.setPassword(pwd);
/*  811 */     List accounts = this.portalaccountService
/*  812 */       .getPortalaccountList(aq);
/*  813 */     if (accounts.size() != 1) {
/*  814 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  815 */       return "portalcustomer/login";
/*      */     }
/*      */ 
/*  818 */     Portalaccount e = (Portalaccount)accounts.get(0);
/*  819 */     if (e == null) {
/*  820 */       model.addAttribute("msg", "用户信息丢失，请先登录！！");
/*  821 */       return "portalcustomer/login";
/*      */     }
/*  823 */     model.addAttribute("entity", e);
/*  824 */     model.addAttribute("msg", "请输入充值卡序列号进行充值！！");
/*  825 */     return "portalcustomer/pay";
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/customerPay.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*      */   public String pay(@RequestParam Long id, @RequestParam String cardKey, ModelMap model, HttpServletRequest request)
/*      */   {
/*  832 */     HttpSession session = request.getSession();
/*  833 */     String basip = (String)session.getAttribute("basip");
/*  834 */     if ((id == null) || (id.longValue() == 0L)) {
/*  835 */       model.addAttribute("msg", "用户信息丢失，请先登录！！");
/*  836 */       return "portalcustomer/login";
/*      */     }
/*  838 */     Portalaccount e = this.portalaccountService.getPortalaccountByKey(id);
/*  839 */     if (e == null) {
/*  840 */       model.addAttribute("msg", "用户信息丢失，请先登录！！");
/*  841 */       return "portalcustomer/login";
/*      */     }
/*  843 */     if (stringUtils.isNotBlank(cardKey)) {
/*  844 */       Boolean boo = Boolean.valueOf(false);
/*  845 */       PortalcardQuery cq = new PortalcardQuery();
/*  846 */       cq.setCdKey(cardKey);
/*  847 */       cq.setCdKeyLike(false);
/*  848 */       cq.setStateLike(false);
/*  849 */       cq.setState("0");
/*  850 */       List cards = this.portalcardService.getPortalcardList(cq);
/*  851 */       if ((cards != null) && (cards.size() == 1)) {
/*  852 */         boo = Boolean.valueOf(true);
/*      */       }
/*  854 */       if (!boo.booleanValue()) {
/*  855 */         cq.setState("1");
/*  856 */         cards = this.portalcardService.getPortalcardList(cq);
/*      */       }
/*  858 */       if ((cards != null) && (cards.size() == 1)) {
/*  859 */         boo = Boolean.valueOf(true);
/*      */       }
/*  861 */       if (!boo.booleanValue()) {
/*  862 */         model.addAttribute("entity", e);
/*  863 */         model.addAttribute("msg", "充值卡CD-KEY错误或已经使用,请仔细核对！！");
/*  864 */         return "portalcustomer/pay";
/*      */       }
/*  866 */       Portalcard card = (Portalcard)cards.get(0);
/*  867 */       String payType = card.getPayType();
/*  868 */       Long payTime = card.getPayTime();
/*      */ 
/*  870 */       String state = e.getState();
/*  871 */       Long oldDate = Long.valueOf(e.getDate().getTime());
/*  872 */       Long oldTime = e.getTime();
/*  873 */       Long oldOctets = e.getOctets();
/*      */ 
/*  875 */       if (oldOctets == null) {
/*  876 */         oldOctets = Long.valueOf(0L);
/*      */       }
/*  878 */       if (oldTime == null) {
/*  879 */         oldTime = Long.valueOf(0L);
/*      */       }
/*      */ 
/*  882 */       Long now = Long.valueOf(new Date().getTime());
/*      */ 
/*  886 */       if (payType.equals("3"))
/*      */       {
/*      */         Long newDate;
/*  888 */         if (oldDate.longValue() < new Date().getTime())
/*  889 */           newDate = Long.valueOf(now.longValue() + payTime.longValue());
/*      */         else {
/*  891 */           newDate = Long.valueOf(oldDate.longValue() + payTime.longValue());
/*      */         }
/*  893 */         e.setDate(new Date(newDate.longValue()));
/*  894 */         e.setState(payType);
/*      */       }
/*      */ 
/*  897 */       if (payType.equals("2")) {
/*  898 */         if (oldTime.longValue() < 0L)
/*  899 */           e.setTime(payTime);
/*      */         else {
/*  901 */           e.setTime(Long.valueOf(oldTime.longValue() + payTime.longValue()));
/*      */         }
/*  903 */         e.setState(payType);
/*      */       }
/*      */ 
/*  906 */       if (payType.equals("4")) {
/*  907 */         if (oldOctets.longValue() < 0L)
/*  908 */           e.setOctets(payTime);
/*      */         else {
/*  910 */           e.setOctets(Long.valueOf(oldOctets.longValue() + payTime.longValue()));
/*      */         }
/*  912 */         e.setState(payType);
/*      */       }
/*      */ 
/*  915 */       if (state.equals("1")) {
/*  916 */         e.setState(state);
/*      */       }
/*      */ 
/*  919 */       e.setMaclimit(card.getMaclimit());
/*  920 */       e.setMaclimitcount(card.getMaclimitcount());
/*  921 */       e.setAutologin(card.getAutologin());
/*  922 */       e.setSpeed(card.getSpeed());
/*      */ 
/*  924 */       this.portalaccountService.updatePortalaccountByKey(e);
/*  925 */       card.setAccountId(id);
/*  926 */       card.setAccountName(e.getLoginName());
/*  927 */       card.setState("2");
/*  928 */       Date date = new Date();
/*  929 */       if (card.getBuyDate() == null) {
/*  930 */         card.setBuyDate(date);
/*      */       }
/*  932 */       card.setPayDate(date);
/*  933 */       this.portalcardService.updatePortalcardByKey(card);
/*      */ 
/*  936 */       PortalorderQuery oq = new PortalorderQuery();
/*  937 */       oq.setAccountName(card.getAccountName());
/*  938 */       oq.setAccountNameLike(false);
/*  939 */       oq.setCdKey(card.getCdKey());
/*  940 */       oq.setCdKeyLike(false);
/*  941 */       List orders = this.portalorderService
/*  942 */         .getPortalorderList(oq);
/*  943 */       if (orders.size() > 0) {
/*  944 */         Portalorder order = (Portalorder)orders.get(0);
/*  945 */         order.setAccountDel(card.getAccountDel());
/*  946 */         order.setAccountId(card.getAccountId());
/*  947 */         order.setAccountName(card.getAccountName());
/*      */ 
/*  949 */         order.setBuyer(card.getAccountName());
/*  950 */         order.setCategoryType(card.getCategoryType());
/*  951 */         order.setCdKey(card.getCdKey());
/*  952 */         order.setDescription(card.getDescription());
/*  953 */         order.setMoney(card.getMoney());
/*  954 */         order.setName(card.getName());
/*  955 */         order.setPayby(Integer.valueOf(0));
/*      */ 
/*  957 */         order.setPayTime(card.getPayTime());
/*  958 */         order.setPayType(card.getPayType());
/*  959 */         order.setSeller("系统");
/*  960 */         order.setState("1");
/*  961 */         order.setTradeno(card.getCdKey());
/*  962 */         order.setUserDel(card.getUserDel());
/*  963 */         this.portalorderService.updatePortalorderByKey(order);
/*      */       } else {
/*  965 */         Portalorder order = new Portalorder();
/*  966 */         order.setAccountDel(card.getAccountDel());
/*  967 */         order.setAccountId(card.getAccountId());
/*  968 */         order.setAccountName(card.getAccountName());
/*  969 */         order.setBuyDate(card.getBuyDate());
/*  970 */         order.setBuyer(card.getAccountName());
/*  971 */         order.setCategoryType(card.getCategoryType());
/*  972 */         order.setCdKey(card.getCdKey());
/*  973 */         order.setDescription(card.getDescription());
/*  974 */         order.setMoney(card.getMoney());
/*  975 */         order.setName(card.getName());
/*  976 */         order.setPayby(Integer.valueOf(0));
/*  977 */         order.setPayDate(card.getPayDate());
/*  978 */         order.setPayTime(card.getPayTime());
/*  979 */         order.setPayType(card.getPayType());
/*  980 */         order.setSeller("系统");
/*  981 */         order.setState("1");
/*  982 */         order.setTradeno(card.getCdKey());
/*  983 */         order.setUserDel(card.getUserDel());
/*  984 */         this.portalorderService.addPortalorder(order);
/*      */       }
/*      */       String isOnline;
/*  993 */       if (OnlineMap.getInstance()
/*  990 */         .getOnlineUserMap()
/*  991 */         .containsKey(
/*  992 */         GetNgnixRemotIP.getRemoteAddrIp(request) + ":" + 
/*  993 */         basip))
/*  994 */         isOnline = "在线";
/*      */       else {
/*  996 */         isOnline = "离线";
/*      */       }
/*  998 */       List speeds = this.portalspeedService
/*  999 */         .getPortalspeedList(new PortalspeedQuery());
/* 1000 */       model.addAttribute("speeds", speeds);
/* 1001 */       model.addAttribute("entity", e);
/* 1002 */       model.addAttribute("isOnline", isOnline);
/* 1003 */       model.addAttribute("msg", "恭喜您，充值成功！");
/* 1004 */       return "portalcustomer/index";
/*      */     }
/* 1006 */     model.addAttribute("entity", e);
/* 1007 */     model.addAttribute("msg", "请仔细核对并输入充值卡CD-KEY！！");
/* 1008 */     return "portalcustomer/pay";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerUnlockMac.action"})
/*      */   public String customerUnlockMac(ModelMap model, HttpServletRequest request)
/*      */   {
/* 1015 */     HttpSession session = request.getSession();
/* 1016 */     String un = (String)session.getAttribute("username");
/* 1017 */     String pwd = (String)session.getAttribute("password");
/* 1018 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/* 1019 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1020 */       return "portalcustomer/login";
/*      */     }
/* 1022 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 1023 */     aq.setLoginName(un);
/* 1024 */     aq.setPassword(pwd);
/* 1025 */     List accounts = this.portalaccountService
/* 1026 */       .getPortalaccountList(aq);
/* 1027 */     if (accounts.size() != 1) {
/* 1028 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1029 */       return "portalcustomer/login";
/*      */     }
/* 1031 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*      */ 
/* 1034 */     Cookie[] cookies = request.getCookies();
/* 1035 */     String cip = "";
/* 1036 */     String cbasip = "";
/* 1037 */     if (cookies != null)
/* 1038 */       for (int i = 0; i < cookies.length; i++) {
/* 1039 */         if (cookies[i].getName().equals("ip"))
/* 1040 */           cip = cookies[i].getValue();
/* 1041 */         if (cookies[i].getName().equals("basip"))
/* 1042 */           cbasip = cookies[i].getValue();
/*      */       }
/*      */     String isOnline;
/* 1048 */     if (OnlineMap.getInstance().getOnlineUserMap()
/* 1048 */       .containsKey(cip + ":" + cbasip))
/* 1049 */       isOnline = "在线";
/*      */     else {
/* 1051 */       isOnline = "离线";
/*      */     }
/*      */ 
/* 1054 */     List speeds = this.portalspeedService
/* 1055 */       .getPortalspeedList(new PortalspeedQuery());
/* 1056 */     model.addAttribute("speeds", speeds);
/* 1057 */     model.addAttribute("entity", acc);
/* 1058 */     model.addAttribute("isOnline", isOnline);
/*      */ 
/* 1060 */     Integer count = this.portalaccountgroupService
/* 1061 */       .getPortalaccountgroupByKey(Long.valueOf(1L)).getUnlockMac();
/* 1062 */     if ((count != null) && (count.intValue() > 0)) {
/* 1063 */       String dateS = acc.getEx4();
/* 1064 */       String costS = acc.getEx3();
/* 1065 */       Date now = new Date();
/* 1066 */       String nowS = String.valueOf(now.getMonth());
/*      */ 
/* 1068 */       if (stringUtils.isBlank(dateS)) {
/* 1069 */         PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
/* 1070 */         macq.setAccountId(acc.getId());
/* 1071 */         List macs = this.portalaccountmacsService
/* 1072 */           .getPortalaccountmacsList(macq);
/* 1073 */         if ((macs != null) && (macs.size() > 0)) {
/* 1074 */           this.portalaccountmacsService.deleteByQuery(macq);
/* 1075 */           acc.setEx3("1");
/* 1076 */           acc.setEx4(nowS);
/* 1077 */           this.portalaccountService.updatePortalaccountByKey(acc);
/* 1078 */           model.addAttribute("msg", "自助解绑成功！");
/*      */         } else {
/* 1080 */           model.addAttribute("msg", "该帐号还没有绑定MAC！");
/*      */         }
/*      */       }
/* 1083 */       else if (stringUtils.isBlank(costS)) {
/* 1084 */         PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
/* 1085 */         macq.setAccountId(acc.getId());
/* 1086 */         List macs = this.portalaccountmacsService
/* 1087 */           .getPortalaccountmacsList(macq);
/* 1088 */         if ((macs != null) && (macs.size() > 0)) {
/* 1089 */           this.portalaccountmacsService.deleteByQuery(macq);
/* 1090 */           acc.setEx3("1");
/* 1091 */           acc.setEx4(nowS);
/* 1092 */           this.portalaccountService.updatePortalaccountByKey(acc);
/* 1093 */           model.addAttribute("msg", "自助解绑成功！");
/*      */         } else {
/* 1095 */           model.addAttribute("msg", "该帐号还没有绑定MAC！");
/*      */         }
/*      */       }
/* 1098 */       else if (dateS.equals(nowS)) {
/* 1099 */         if (Integer.valueOf(costS).intValue() < count.intValue()) {
/* 1100 */           PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
/* 1101 */           macq.setAccountId(acc.getId());
/* 1102 */           List macs = this.portalaccountmacsService
/* 1103 */             .getPortalaccountmacsList(macq);
/* 1104 */           if ((macs != null) && (macs.size() > 0)) {
/* 1105 */             this.portalaccountmacsService.deleteByQuery(macq);
/* 1106 */             acc.setEx3(String.valueOf(
/* 1107 */               Integer.valueOf(costS).intValue() + 1));
/* 1108 */             acc.setEx4(nowS);
/* 1109 */             this.portalaccountService
/* 1110 */               .updatePortalaccountByKey(acc);
/* 1111 */             model.addAttribute("msg", "自助解绑成功！");
/*      */           } else {
/* 1113 */             model.addAttribute("msg", "该帐号还没有绑定MAC！");
/*      */           }
/*      */         } else {
/* 1116 */           model.addAttribute("msg", "本月自助解绑次数已用完，请联系管理员！");
/*      */         }
/*      */       } else {
/* 1119 */         PortalaccountmacsQuery macq = new PortalaccountmacsQuery();
/* 1120 */         macq.setAccountId(acc.getId());
/* 1121 */         List macs = this.portalaccountmacsService
/* 1122 */           .getPortalaccountmacsList(macq);
/* 1123 */         if ((macs != null) && (macs.size() > 0)) {
/* 1124 */           this.portalaccountmacsService.deleteByQuery(macq);
/* 1125 */           acc.setEx3("1");
/* 1126 */           acc.setEx4(nowS);
/* 1127 */           this.portalaccountService.updatePortalaccountByKey(acc);
/* 1128 */           model.addAttribute("msg", "自助解绑成功！");
/*      */         } else {
/* 1130 */           model.addAttribute("msg", "该帐号还没有绑定MAC！");
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1137 */       model.addAttribute("msg", "系统不允许自助解绑，请联系管理员！");
/*      */     }
/* 1139 */     return "portalcustomer/index";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerFindMethod.action"})
/*      */   public String customerFindMethod(ModelMap model, HttpServletRequest request)
/*      */   {
/* 1145 */     model.addAttribute("msg", "请选择重置密码方式！！");
/* 1146 */     return "portalcustomer/find";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerSmsFind.action"})
/*      */   public String customerSmsForget(ModelMap model, HttpServletRequest request)
/*      */   {
/* 1154 */     model.addAttribute("msg", "请填写要重置密码的手机号！！");
/* 1155 */     return "portalcustomer/Smsforget";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerSmsForget.action"})
/*      */   public String customerSmsForget(PortalaccountQuery aq, String yzm, ModelMap model, HttpServletRequest request)
/*      */   {
/* 1162 */     if ((stringUtils.isBlank(aq.getLoginName())) || 
/* 1163 */       (stringUtils.isBlank(aq.getPhoneNumber()))) {
/* 1164 */       model.addAttribute("msg", "请填写要重置密码的登录名和手机号！！");
/* 1165 */       return "portalcustomer/Smsforget";
/*      */     }
/* 1167 */     if (stringUtils.isBlank(yzm)) {
/* 1168 */       model.addAttribute("msg", "请填写验证码！！");
/* 1169 */       return "portalcustomer/Smsforget";
/*      */     }
/*      */ 
/* 1172 */     String phone = aq.getPhoneNumber();
/* 1173 */     if (phone.length() != 11) {
/* 1174 */       model.addAttribute("msg", "手机号不正确！！");
/* 1175 */       model.addAttribute("entity", aq);
/* 1176 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/* 1177 */       return "portalcustomer/Smsforget";
/*      */     }
/*      */     try {
/* 1180 */       Long.parseLong(phone);
/*      */     } catch (Exception ex) {
/* 1182 */       model.addAttribute("msg", "手机号不正确！！");
/* 1183 */       model.addAttribute("entity", aq);
/* 1184 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/* 1185 */       return "portalcustomer/Smsforget";
/*      */     }
/* 1187 */     String code = "";
/* 1188 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/* 1189 */     query.setState("1");
/* 1190 */     List smsList = this.portalsmsapiService
/* 1191 */       .getPortalsmsapiList(query);
/*      */     Portalsmsapi smsapi;
/* 1193 */     if (smsList.size() > 0)
/* 1194 */       smsapi = (Portalsmsapi)smsList.get(0);
/*      */     else {
/* 1196 */       smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/*      */     }
/* 1198 */     Long time = Long.valueOf(smsapi.getTime().intValue() * 60000);
/* 1199 */     Object[] objs = (Object[])PhoneCodeMap.getInstance().getPhoneCodeMap().get(phone);
/*      */     try {
/* 1201 */       Date saveDate = (Date)objs[1];
/* 1202 */       Long nowTime = Long.valueOf(new Date().getTime());
/* 1203 */       if (nowTime.longValue() - saveDate.getTime() > time.longValue()) {
/* 1204 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/* 1205 */         model.addAttribute("msg", "验证码已过期，请重新获取验证码！");
/* 1206 */         model.addAttribute("entity", aq);
/* 1207 */         PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/* 1208 */         return "portalcustomer/Smsforget";
/*      */       }
/* 1210 */       code = (String)objs[0];
/*      */     } catch (Exception ex) {
/* 1212 */       model.addAttribute("msg", "手机号或验证码不正确！");
/* 1213 */       model.addAttribute("entity", aq);
/* 1214 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/* 1215 */       return "portalcustomer/Smsforget";
/*      */     }
/*      */ 
/* 1218 */     if (!yzm.equals(code)) {
/* 1219 */       model.addAttribute("msg", "验证码不正确！");
/* 1220 */       model.addAttribute("entity", aq);
/* 1221 */       return "portalcustomer/Smsforget";
/*      */     }
/*      */ 
/* 1224 */     HttpSession session = request.getSession();
/* 1225 */     aq.setLoginNameLike(false);
/* 1226 */     aq.setPhoneNumberLike(false);
/* 1227 */     List accs = this.portalaccountService
/* 1228 */       .getPortalaccountList(aq);
/* 1229 */     if ((accs != null) && (accs.size() == 1)) {
/* 1230 */       PhoneCodeMap.getInstance().getPhoneCodeMap().remove(phone);
/* 1231 */       Portalaccount acc = (Portalaccount)accs.get(0);
/* 1232 */       session.setAttribute("account", acc);
/* 1233 */       model.addAttribute("entity", acc);
/* 1234 */       model.addAttribute("msg", "验证通过，请重置密码！！");
/* 1235 */       return "portalcustomer/SmsforgetSave";
/*      */     }
/* 1237 */     model.addAttribute("msg", "该用户和手机号不匹配！！");
/* 1238 */     return "portalcustomer/Smsforget";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerSmsForgetSave.action"})
/*      */   public String customerSmsForgetSave(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
/*      */   {
/* 1246 */     HttpSession session = request.getSession();
/* 1247 */     Portalaccount acc = (Portalaccount)session.getAttribute("account");
/* 1248 */     if (acc == null) {
/* 1249 */       model.addAttribute("msg", "用户信息丢失，请重试！！");
/* 1250 */       return "portalcustomer/Smsforget";
/*      */     }
/* 1252 */     if (stringUtils.isBlank(aq.getPassword())) {
/* 1253 */       model.addAttribute("entity", acc);
/* 1254 */       model.addAttribute("msg", "密码格式错误！！");
/* 1255 */       return "portalcustomer/SmsforgetSave";
/*      */     }
/* 1257 */     acc.setPassword(aq.getPassword());
/* 1258 */     this.portalaccountService.updatePortalaccountByKey(acc);
/* 1259 */     session.removeAttribute("account");
/*      */ 
/* 1262 */     Cookie[] cookies = request.getCookies();
/* 1263 */     String cip = "";
/* 1264 */     String cbasip = "";
/* 1265 */     if (cookies != null)
/* 1266 */       for (int i = 0; i < cookies.length; i++) {
/* 1267 */         if (cookies[i].getName().equals("ip"))
/* 1268 */           cip = cookies[i].getValue();
/* 1269 */         if (cookies[i].getName().equals("basip"))
/* 1270 */           cbasip = cookies[i].getValue();
/*      */       }
/*      */     String isOnline;
/* 1276 */     if (OnlineMap.getInstance().getOnlineUserMap()
/* 1276 */       .containsKey(cip + ":" + cbasip))
/* 1277 */       isOnline = "在线";
/*      */     else {
/* 1279 */       isOnline = "离线";
/*      */     }
/*      */ 
/* 1282 */     List speeds = this.portalspeedService
/* 1283 */       .getPortalspeedList(new PortalspeedQuery());
/* 1284 */     model.addAttribute("speeds", speeds);
/* 1285 */     model.addAttribute("entity", acc);
/* 1286 */     model.addAttribute("isOnline", isOnline);
/* 1287 */     model.addAttribute("msg", "密码重置成功！");
/* 1288 */     session.setAttribute("username", acc.getLoginName());
/* 1289 */     session.setAttribute("password", acc.getPassword());
/* 1290 */     return "portalcustomer/index";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerFind.action"})
/*      */   public String customerForget(ModelMap model, HttpServletRequest request)
/*      */   {
/* 1298 */     model.addAttribute("msg", "请填写要找回密码的用户名！！");
/* 1299 */     return "portalcustomer/forget";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerForget.action"})
/*      */   public String customerForget(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
/*      */   {
/* 1306 */     if (stringUtils.isBlank(aq.getLoginName())) {
/* 1307 */       model.addAttribute("msg", "请正确填写用户名！！");
/* 1308 */       return "portalcustomer/forget";
/*      */     }
/* 1310 */     HttpSession session = request.getSession();
/* 1311 */     List accs = this.portalaccountService
/* 1312 */       .getPortalaccountList(aq);
/* 1313 */     if ((accs != null) && (accs.size() == 1)) {
/* 1314 */       Portalaccount acc = (Portalaccount)accs.get(0);
/* 1315 */       String ans = acc.getEx1();
/* 1316 */       if (stringUtils.isBlank(ans)) {
/* 1317 */         model.addAttribute("msg", "该用户没有设置找回密码问题，请联系管理员！！");
/* 1318 */         return "portalcustomer/forget";
/*      */       }
/* 1320 */       ans = acc.getEx2();
/* 1321 */       if (stringUtils.isBlank(ans)) {
/* 1322 */         model.addAttribute("msg", "该用户没有设置找回密码问题，请联系管理员！！");
/* 1323 */         return "portalcustomer/forget";
/*      */       }
/* 1325 */       session.setAttribute("account", acc);
/* 1326 */       model.addAttribute("msg", "请输入找回密码问题答案！！");
/* 1327 */       model.addAttribute("entity", acc);
/* 1328 */       return "portalcustomer/forgetDo";
/*      */     }
/* 1330 */     model.addAttribute("msg", "请正确填写用户名！！");
/* 1331 */     return "portalcustomer/forget";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerForgetDo.action"})
/*      */   public String customerForgetDo(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
/*      */   {
/* 1339 */     HttpSession session = request.getSession();
/* 1340 */     Portalaccount acc = (Portalaccount)session.getAttribute("account");
/* 1341 */     if (acc == null) {
/* 1342 */       model.addAttribute("msg", "用户信息丢失，请重试！！");
/* 1343 */       return "portalcustomer/forget";
/*      */     }
/* 1345 */     String ans = acc.getEx2();
/* 1346 */     if (stringUtils.isBlank(ans)) {
/* 1347 */       model.addAttribute("msg", "该用户没有设置找回密码问题，请联系管理员！！");
/* 1348 */       return "portalcustomer/forget";
/*      */     }
/* 1350 */     if (stringUtils.isBlank(aq.getEx2())) {
/* 1351 */       model.addAttribute("msg", "回答不正确！！");
/* 1352 */       return "portalcustomer/forget";
/*      */     }
/* 1354 */     if (ans.equals(aq.getEx2())) {
/* 1355 */       model.addAttribute("entity", acc);
/* 1356 */       model.addAttribute("msg", "验证通过，请重置密码！！");
/* 1357 */       return "portalcustomer/forgetSave";
/*      */     }
/* 1359 */     model.addAttribute("msg", "回答不正确！！");
/* 1360 */     return "portalcustomer/forget";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerForgetSave.action"})
/*      */   public String customerForgetSave(PortalaccountQuery aq, ModelMap model, HttpServletRequest request)
/*      */   {
/* 1368 */     HttpSession session = request.getSession();
/* 1369 */     Portalaccount acc = (Portalaccount)session.getAttribute("account");
/* 1370 */     if (acc == null) {
/* 1371 */       model.addAttribute("msg", "用户信息丢失，请重试！！");
/* 1372 */       return "portalcustomer/forget";
/*      */     }
/* 1374 */     if (stringUtils.isBlank(aq.getPassword())) {
/* 1375 */       model.addAttribute("entity", acc);
/* 1376 */       model.addAttribute("msg", "密码格式错误！！");
/* 1377 */       return "portalcustomer/forgetSave";
/*      */     }
/* 1379 */     acc.setPassword(aq.getPassword());
/* 1380 */     this.portalaccountService.updatePortalaccountByKey(acc);
/* 1381 */     session.removeAttribute("account");
/*      */ 
/* 1384 */     Cookie[] cookies = request.getCookies();
/* 1385 */     String cip = "";
/* 1386 */     String cbasip = "";
/* 1387 */     if (cookies != null)
/* 1388 */       for (int i = 0; i < cookies.length; i++) {
/* 1389 */         if (cookies[i].getName().equals("ip"))
/* 1390 */           cip = cookies[i].getValue();
/* 1391 */         if (cookies[i].getName().equals("basip"))
/* 1392 */           cbasip = cookies[i].getValue();
/*      */       }
/*      */     String isOnline;
/* 1398 */     if (OnlineMap.getInstance().getOnlineUserMap()
/* 1398 */       .containsKey(cip + ":" + cbasip))
/* 1399 */       isOnline = "在线";
/*      */     else {
/* 1401 */       isOnline = "离线";
/*      */     }
/*      */ 
/* 1404 */     List speeds = this.portalspeedService
/* 1405 */       .getPortalspeedList(new PortalspeedQuery());
/* 1406 */     model.addAttribute("speeds", speeds);
/* 1407 */     model.addAttribute("entity", acc);
/* 1408 */     model.addAttribute("isOnline", isOnline);
/* 1409 */     model.addAttribute("msg", "密码重置成功！");
/* 1410 */     session.setAttribute("username", acc.getLoginName());
/* 1411 */     session.setAttribute("password", acc.getPassword());
/* 1412 */     return "portalcustomer/index";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerOnline.action"})
/*      */   public String Online(Integer Page, ModelMap model, HttpServletRequest request)
/*      */     throws ParseException
/*      */   {
/* 1419 */     HttpSession session = request.getSession();
/* 1420 */     String un = (String)session.getAttribute("username");
/* 1421 */     String pwd = (String)session.getAttribute("password");
/* 1422 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/* 1423 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1424 */       return "portalcustomer/login";
/*      */     }
/* 1426 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 1427 */     aq.setLoginName(un);
/* 1428 */     aq.setPassword(pwd);
/* 1429 */     List accounts = this.portalaccountService
/* 1430 */       .getPortalaccountList(aq);
/* 1431 */     if (accounts.size() != 1) {
/* 1432 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1433 */       return "portalcustomer/login";
/*      */     }
/* 1435 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*      */ 
/* 1437 */     int count = 0;
/* 1438 */     Iterator it = OnlineMap.getInstance().getOnlineUserMap()
/* 1439 */       .keySet().iterator();
/* 1440 */     while (it.hasNext()) {
/* 1441 */       String[] loginInfo = 
/* 1442 */         (String[])OnlineMap.getInstance().getOnlineUserMap()
/* 1442 */         .get(it.next());
/* 1443 */       Boolean isIt = Boolean.valueOf(true);
/* 1444 */       if (!acc.getLoginName().equals(loginInfo[0])) {
/* 1445 */         isIt = Boolean.valueOf(false);
/*      */       }
/* 1447 */       if (isIt.booleanValue()) {
/* 1448 */         count++;
/*      */       }
/*      */     }
/*      */ 
/* 1452 */     List onlineInfos = new ArrayList();
/* 1453 */     if ((Page == null) || (Page.intValue() <= 0)) {
/* 1454 */       Page = Integer.valueOf(1);
/*      */     }
/* 1456 */     Integer id = Integer.valueOf(1);
/* 1457 */     Iterator iterator = OnlineMap.getInstance().getOnlineUserMap()
/* 1458 */       .keySet().iterator();
/* 1459 */     while (iterator.hasNext()) {
/* 1460 */       OnlineInfo onlineInfo = new OnlineInfo();
/* 1461 */       String host = (String)iterator.next();
/* 1462 */       String[] loginInfo = 
/* 1463 */         (String[])OnlineMap.getInstance().getOnlineUserMap()
/* 1463 */         .get(host);
/* 1464 */       String username = loginInfo[0];
/* 1465 */       String time = loginInfo[3];
/* 1466 */       String mac = loginInfo[4];
/*      */ 
/* 1468 */       String inS = "0 M";
/* 1469 */       String outS = "0 M";
/* 1470 */       String octetsS = "0 M";
/*      */       try {
/* 1472 */         double in = Double.valueOf(loginInfo[7]).doubleValue();
/* 1473 */         double out = Double.valueOf(loginInfo[8]).doubleValue();
/* 1474 */         double octets = in + out;
/* 1475 */         in /= 1048576.0D;
/* 1476 */         out /= 1048576.0D;
/* 1477 */         octets /= 1048576.0D;
/* 1478 */         inS = df.format(in);
/* 1479 */         outS = df.format(out);
/* 1480 */         octetsS = df.format(octets);
/* 1481 */         if (inS.startsWith(".")) {
/* 1482 */           inS = "0" + inS;
/*      */         }
/* 1484 */         if (outS.startsWith(".")) {
/* 1485 */           outS = "0" + outS;
/*      */         }
/* 1487 */         if (octetsS.startsWith(".")) {
/* 1488 */           octetsS = "0" + octetsS;
/*      */         }
/* 1490 */         inS = inS + " M";
/* 1491 */         outS = outS + " M";
/* 1492 */         octetsS = octetsS + " M";
/*      */       }
/*      */       catch (Exception localException) {
/*      */       }
/* 1496 */       String type = loginInfo[6];
/* 1497 */       String basname = loginInfo[11];
/* 1498 */       String ssid = loginInfo[12];
/* 1499 */       String apmac = loginInfo[13];
/* 1500 */       String auto = loginInfo[14];
/* 1501 */       String agent = loginInfo[15];
/* 1502 */       if (stringUtils.isBlank(basname)) {
/* 1503 */         basname = "base";
/*      */       }
/* 1505 */       if (stringUtils.isBlank(ssid)) {
/* 1506 */         ssid = "unknow";
/*      */       }
/* 1508 */       if (stringUtils.isBlank(apmac)) {
/* 1509 */         apmac = "unknow";
/*      */       }
/* 1511 */       if (stringUtils.isBlank(agent)) {
/* 1512 */         agent = "unknow";
/*      */       }
/*      */ 
/* 1515 */       Date loginTime = ThreadLocalDateUtil.parse(time);
/* 1516 */       String nowString = ThreadLocalDateUtil.format(new Date());
/* 1517 */       Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 1518 */       Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/* 1519 */       costTime = Long.valueOf(costTime.longValue() / 60000L);
/* 1520 */       Boolean isIt = Boolean.valueOf(true);
/* 1521 */       if (!acc.getLoginName().equals(loginInfo[0])) {
/* 1522 */         isIt = Boolean.valueOf(false);
/*      */       }
/* 1524 */       if (isIt.booleanValue()) {
/* 1525 */         if (id.intValue() > (Page.intValue() - 1) * 10) {
/* 1526 */           onlineInfo.setIp(host);
/* 1527 */           onlineInfo.setLoginName(username);
/* 1528 */           onlineInfo.setStartDate(loginTime);
/* 1529 */           onlineInfo.setTime(costTime);
/* 1530 */           onlineInfo.setMac(mac);
/* 1531 */           onlineInfo.setInS(inS);
/* 1532 */           onlineInfo.setOutS(outS);
/* 1533 */           onlineInfo.setOctetsS(octetsS);
/*      */ 
/* 1535 */           if ("0".equals(type))
/* 1536 */             onlineInfo.setType("一键认证");
/* 1537 */           else if ("1".equals(type))
/* 1538 */             onlineInfo.setType("本地用户");
/* 1539 */           else if ("2".equals(type))
/* 1540 */             onlineInfo.setType("Radius");
/* 1541 */           else if ("3".equals(type))
/* 1542 */             onlineInfo.setType("APP认证");
/* 1543 */           else if ("4".equals(type))
/* 1544 */             onlineInfo.setType("短信认证");
/* 1545 */           else if ("5".equals(type))
/* 1546 */             onlineInfo.setType("微信认证");
/* 1547 */           else if ("6".equals(type))
/* 1548 */             onlineInfo.setType("公众号认证");
/* 1549 */           else if ("7".equals(type))
/* 1550 */             onlineInfo.setType("访客认证");
/* 1551 */           else if ("8".equals(type)) {
/* 1552 */             onlineInfo.setType("延迟认证");
/*      */           }
/* 1554 */           onlineInfo.setBasname(basname);
/* 1555 */           onlineInfo.setSsid(ssid);
/* 1556 */           onlineInfo.setApmac(apmac);
/* 1557 */           onlineInfo.setAuto(auto);
/* 1558 */           onlineInfo.setAgent(agent);
/* 1559 */           onlineInfo.setState(acc.getState());
/* 1560 */           onlineInfo.setId(id);
/* 1561 */           onlineInfos.add(onlineInfo);
/*      */         }
/*      */ 
/* 1564 */         id = Integer.valueOf(id.intValue() + 1);
/* 1565 */         if (id.intValue() > Page.intValue() * 10)
/*      */         {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1571 */     Pagination pagination = new Pagination(Page.intValue(), 10, count, onlineInfos);
/* 1572 */     model.addAttribute("pagination", pagination);
/* 1573 */     return "portalcustomer/online";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerkick.action"})
/*      */   public String kick(@RequestParam String ip, ModelMap model, HttpServletRequest request)
/*      */   {
/* 1580 */     HttpSession session = request.getSession();
/* 1581 */     String un = (String)session.getAttribute("username");
/* 1582 */     String pwd = (String)session.getAttribute("password");
/* 1583 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/* 1584 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1585 */       return "portalcustomer/login";
/*      */     }
/* 1587 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 1588 */     aq.setLoginName(un);
/* 1589 */     aq.setPassword(pwd);
/* 1590 */     List accounts = this.portalaccountService
/* 1591 */       .getPortalaccountList(aq);
/* 1592 */     if (accounts.size() != 1) {
/* 1593 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1594 */       return "portalcustomer/login";
/*      */     }
/* 1596 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/* 1597 */     String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(ip);
/* 1598 */     if ((loginInfo != null) && 
/* 1599 */       (acc.getLoginName().equals(loginInfo[0]))) {
/* 1600 */       Kick.kickUserByCustomer(ip);
/* 1601 */       if (stringUtils.isNotBlank(loginInfo[4])) {
/* 1602 */         PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1603 */         macsq.setAccountId(acc.getId());
/* 1604 */         macsq.setMac(PortalUtil.MacFormat(loginInfo[4]));
/* 1605 */         macsq.setMacLike(false);
/* 1606 */         this.macsService.deleteByQuery(macsq);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1611 */     Iterator iterator = RadiusOnlineMap.getInstance()
/* 1612 */       .getRadiusOnlineMap().keySet().iterator();
/* 1613 */     while (iterator.hasNext()) {
/* 1614 */       String acctSessionId = (String)iterator.next();
/* 1615 */       String[] radiusOnlineInfo = 
/* 1616 */         (String[])RadiusOnlineMap.getInstance()
/* 1616 */         .getRadiusOnlineMap().get(acctSessionId);
/* 1617 */       if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 1618 */         (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
/* 1619 */         if (stringUtils.isNotBlank(loginInfo[4])) {
/* 1620 */           if (loginInfo[4].equals(radiusOnlineInfo[3])) {
/* 1621 */             COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 1622 */               "By Customer COA");
/* 1623 */             if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
/* 1624 */               PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1625 */               macsq.setAccountId(acc.getId());
/* 1626 */               macsq.setMac(
/* 1627 */                 PortalUtil.MacFormat(radiusOnlineInfo[3]));
/* 1628 */               macsq.setMacLike(false);
/* 1629 */               this.macsService.deleteByQuery(macsq);
/*      */             }
/*      */           }
/*      */         } else {
/* 1633 */           int i = ip.lastIndexOf(":");
/* 1634 */           String uip = ip.substring(0, i);
/* 1635 */           if (uip.equals(radiusOnlineInfo[2])) {
/* 1636 */             COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 1637 */               "By Customer COA");
/* 1638 */             if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
/* 1639 */               PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1640 */               macsq.setAccountId(acc.getId());
/* 1641 */               macsq.setMac(
/* 1642 */                 PortalUtil.MacFormat(radiusOnlineInfo[3]));
/* 1643 */               macsq.setMacLike(false);
/* 1644 */               this.macsService.deleteByQuery(macsq);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1652 */     return "redirect:/customerOnline.action";
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/customerkicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*      */   public String kicks(ModelMap model, HttpServletRequest request)
/*      */   {
/* 1658 */     HttpSession session = request.getSession();
/* 1659 */     String un = (String)session.getAttribute("username");
/* 1660 */     String pwd = (String)session.getAttribute("password");
/* 1661 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/* 1662 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1663 */       return "portalcustomer/login";
/*      */     }
/* 1665 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 1666 */     aq.setLoginName(un);
/* 1667 */     aq.setPassword(pwd);
/* 1668 */     List accounts = this.portalaccountService
/* 1669 */       .getPortalaccountList(aq);
/* 1670 */     if (accounts.size() != 1) {
/* 1671 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1672 */       return "portalcustomer/login";
/*      */     }
/* 1674 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/* 1675 */     Iterator it = OnlineMap.getInstance().getOnlineUserMap()
/* 1676 */       .keySet().iterator();
/* 1677 */     while (it.hasNext()) {
/* 1678 */       String host = (String)it.next();
/* 1679 */       String[] loginInfo = 
/* 1680 */         (String[])OnlineMap.getInstance().getOnlineUserMap()
/* 1680 */         .get(host);
/* 1681 */       if ((loginInfo != null) && 
/* 1682 */         (acc.getLoginName().equals(loginInfo[0]))) {
/* 1683 */         Kick.kickUserByCustomer(host);
/* 1684 */         if (stringUtils.isNotBlank(loginInfo[4])) {
/* 1685 */           PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1686 */           macsq.setAccountId(acc.getId());
/* 1687 */           macsq.setMac(PortalUtil.MacFormat(loginInfo[4]));
/* 1688 */           macsq.setMacLike(false);
/* 1689 */           this.macsService.deleteByQuery(macsq);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1695 */     Iterator iterator = RadiusOnlineMap.getInstance()
/* 1696 */       .getRadiusOnlineMap().keySet().iterator();
/* 1697 */     while (iterator.hasNext()) {
/* 1698 */       String acctSessionId = (String)iterator.next();
/* 1699 */       String[] radiusOnlineInfo = 
/* 1700 */         (String[])RadiusOnlineMap.getInstance()
/* 1700 */         .getRadiusOnlineMap().get(acctSessionId);
/* 1701 */       if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 1702 */         (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
/* 1703 */         COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 1704 */           "By Customer COA");
/* 1705 */         if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
/* 1706 */           PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1707 */           macsq.setAccountId(acc.getId());
/* 1708 */           macsq.setMac(PortalUtil.MacFormat(radiusOnlineInfo[3]));
/* 1709 */           macsq.setMacLike(false);
/* 1710 */           this.macsService.deleteByQuery(macsq);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1716 */     return "redirect:/customerOnline.action";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerRadiusOnline.action"})
/*      */   public String RadiusOnline(Integer Page, ModelMap model, HttpServletRequest request)
/*      */     throws ParseException
/*      */   {
/* 1723 */     HttpSession session = request.getSession();
/* 1724 */     String un = (String)session.getAttribute("username");
/* 1725 */     String pwd = (String)session.getAttribute("password");
/* 1726 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/* 1727 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1728 */       return "portalcustomer/login";
/*      */     }
/* 1730 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 1731 */     aq.setLoginName(un);
/* 1732 */     aq.setPassword(pwd);
/* 1733 */     List accounts = this.portalaccountService
/* 1734 */       .getPortalaccountList(aq);
/* 1735 */     if (accounts.size() != 1) {
/* 1736 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1737 */       return "portalcustomer/login";
/*      */     }
/* 1739 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*      */ 
/* 1741 */     int count = 0;
/* 1742 */     Iterator it = RadiusOnlineMap.getInstance()
/* 1743 */       .getRadiusOnlineMap().keySet().iterator();
/* 1744 */     while (it.hasNext()) {
/* 1745 */       String acctSessionId = (String)it.next();
/* 1746 */       String[] loginInfo = 
/* 1747 */         (String[])RadiusOnlineMap.getInstance()
/* 1747 */         .getRadiusOnlineMap().get(acctSessionId);
/* 1748 */       String username = loginInfo[4];
/* 1749 */       Boolean isIt = Boolean.valueOf(true);
/*      */ 
/* 1751 */       if (!acc.getLoginName().equals(username)) {
/* 1752 */         isIt = Boolean.valueOf(false);
/*      */       }
/*      */ 
/* 1755 */       if (isIt.booleanValue()) {
/* 1756 */         count++;
/*      */       }
/*      */     }
/*      */ 
/* 1760 */     List onlineInfos = new ArrayList();
/* 1761 */     if ((Page == null) || (Page.intValue() <= 0)) {
/* 1762 */       Page = Integer.valueOf(1);
/*      */     }
/* 1764 */     Integer id = Integer.valueOf(1);
/* 1765 */     Iterator iterator = RadiusOnlineMap.getInstance()
/* 1766 */       .getRadiusOnlineMap().keySet().iterator();
/* 1767 */     while (iterator.hasNext()) {
/* 1768 */       RadiusOnlineInfo onlineInfo = new RadiusOnlineInfo();
/* 1769 */       String acctSessionId = (String)iterator.next();
/* 1770 */       String[] loginInfo = 
/* 1771 */         (String[])RadiusOnlineMap.getInstance()
/* 1771 */         .getRadiusOnlineMap().get(acctSessionId);
/* 1772 */       String ip = loginInfo[2];
/* 1773 */       String mac = loginInfo[3];
/* 1774 */       String username = loginInfo[4];
/* 1775 */       Boolean isIt = Boolean.valueOf(true);
/*      */ 
/* 1777 */       if (!acc.getLoginName().equals(username)) {
/* 1778 */         isIt = Boolean.valueOf(false);
/*      */       }
/*      */ 
/* 1781 */       if (isIt.booleanValue())
/*      */       {
/* 1783 */         if (id.intValue() > (Page.intValue() - 1) * 10) {
/* 1784 */           String inS = "0 M";
/* 1785 */           String outS = "0 M";
/* 1786 */           String allS = "0 M";
/* 1787 */           String octetsS = "0 M";
/*      */           try {
/* 1789 */             double in = Double.valueOf(loginInfo[11]).doubleValue();
/* 1790 */             double out = Double.valueOf(loginInfo[12]).doubleValue();
/* 1791 */             double all = Double.valueOf(loginInfo[7]).doubleValue();
/* 1792 */             double octets = in + out;
/* 1793 */             in /= 1048576.0D;
/* 1794 */             out /= 1048576.0D;
/* 1795 */             all /= 1048576.0D;
/* 1796 */             octets /= 1048576.0D;
/* 1797 */             inS = df.format(in);
/* 1798 */             outS = df.format(out);
/* 1799 */             allS = df.format(all);
/* 1800 */             octetsS = df.format(octets);
/* 1801 */             if (inS.startsWith(".")) {
/* 1802 */               inS = "0" + inS;
/*      */             }
/* 1804 */             if (outS.startsWith(".")) {
/* 1805 */               outS = "0" + outS;
/*      */             }
/* 1807 */             if (allS.startsWith(".")) {
/* 1808 */               allS = "0" + allS;
/*      */             }
/* 1810 */             if (octetsS.startsWith(".")) {
/* 1811 */               octetsS = "0" + octetsS;
/*      */             }
/* 1813 */             inS = inS + "M";
/* 1814 */             outS = outS + "M";
/* 1815 */             allS = allS + "M";
/* 1816 */             octetsS = octetsS + " M";
/*      */           }
/*      */           catch (Exception localException) {
/*      */           }
/* 1820 */           onlineInfo.setNasIP(loginInfo[0]);
/* 1821 */           onlineInfo.setIp(loginInfo[1]);
/* 1822 */           onlineInfo.setUserIP(ip);
/* 1823 */           onlineInfo.setCallingStationId(mac);
/* 1824 */           onlineInfo.setName(username);
/* 1825 */           onlineInfo.setSharedSecret(loginInfo[5]);
/* 1826 */           onlineInfo.setSessionTime(loginInfo[6]);
/* 1827 */           onlineInfo.setOctets(allS);
/* 1828 */           onlineInfo.setClientType(loginInfo[8]);
/* 1829 */           onlineInfo.setStartDate(loginInfo[9]);
/* 1830 */           onlineInfo.setCostTime(loginInfo[10]);
/* 1831 */           onlineInfo.setInS(inS);
/* 1832 */           onlineInfo.setOutS(outS);
/* 1833 */           onlineInfo.setCostOctets(octetsS);
/* 1834 */           onlineInfo.setAcctSessionId(acctSessionId);
/* 1835 */           onlineInfo.setState(loginInfo[15]);
/* 1836 */           onlineInfo.setUpdateDate(loginInfo[14]);
/* 1837 */           onlineInfo.setNasname(loginInfo[16]);
/*      */ 
/* 1839 */           onlineInfo.setId(id);
/* 1840 */           onlineInfos.add(onlineInfo);
/*      */         }
/*      */ 
/* 1843 */         id = Integer.valueOf(id.intValue() + 1);
/* 1844 */         if (id.intValue() > Page.intValue() * 10)
/*      */         {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1850 */     Pagination pagination = new Pagination(Page.intValue(), 10, count, onlineInfos);
/* 1851 */     model.addAttribute("pagination", pagination);
/* 1852 */     return "portalcustomer/onlineR";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/customerRadiuskick.action"})
/*      */   public String Radiuskick(@RequestParam String acctSessionId, ModelMap model, HttpServletRequest request)
/*      */   {
/* 1859 */     HttpSession session = request.getSession();
/* 1860 */     String un = (String)session.getAttribute("username");
/* 1861 */     String pwd = (String)session.getAttribute("password");
/* 1862 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/* 1863 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1864 */       return "portalcustomer/login";
/*      */     }
/* 1866 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 1867 */     aq.setLoginName(un);
/* 1868 */     aq.setPassword(pwd);
/* 1869 */     List accounts = this.portalaccountService
/* 1870 */       .getPortalaccountList(aq);
/* 1871 */     if (accounts.size() != 1) {
/* 1872 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1873 */       return "portalcustomer/login";
/*      */     }
/* 1875 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*      */ 
/* 1877 */     String[] radiusOnlineInfo = 
/* 1878 */       (String[])RadiusOnlineMap.getInstance()
/* 1878 */       .getRadiusOnlineMap().get(acctSessionId);
/* 1879 */     if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 1880 */       (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
/* 1881 */       COAThread.COA_Account_Cost(radiusOnlineInfo, "By Customer COA");
/* 1882 */       if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
/* 1883 */         PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1884 */         macsq.setAccountId(acc.getId());
/* 1885 */         macsq.setMac(PortalUtil.MacFormat(radiusOnlineInfo[3]));
/* 1886 */         macsq.setMacLike(false);
/* 1887 */         this.macsService.deleteByQuery(macsq);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1892 */     return "redirect:/customerRadiusOnline.action";
/*      */   }
/*      */ 
/*      */   @RequestMapping(value={"/customerRadiuskicks.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*      */   public String Radiuskicks(ModelMap model, HttpServletRequest request)
/*      */   {
/* 1898 */     HttpSession session = request.getSession();
/* 1899 */     String un = (String)session.getAttribute("username");
/* 1900 */     String pwd = (String)session.getAttribute("password");
/* 1901 */     if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/* 1902 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1903 */       return "portalcustomer/login";
/*      */     }
/* 1905 */     PortalaccountQuery aq = new PortalaccountQuery();
/* 1906 */     aq.setLoginName(un);
/* 1907 */     aq.setPassword(pwd);
/* 1908 */     List accounts = this.portalaccountService
/* 1909 */       .getPortalaccountList(aq);
/* 1910 */     if (accounts.size() != 1) {
/* 1911 */       model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1912 */       return "portalcustomer/login";
/*      */     }
/* 1914 */     Portalaccount acc = (Portalaccount)accounts.get(0);
/*      */ 
/* 1916 */     Iterator iterator = RadiusOnlineMap.getInstance()
/* 1917 */       .getRadiusOnlineMap().keySet().iterator();
/* 1918 */     while (iterator.hasNext()) {
/* 1919 */       String acctSessionId = (String)iterator.next();
/* 1920 */       String[] radiusOnlineInfo = 
/* 1921 */         (String[])RadiusOnlineMap.getInstance()
/* 1921 */         .getRadiusOnlineMap().get(acctSessionId);
/* 1922 */       if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0) && 
/* 1923 */         (acc.getLoginName().equals(radiusOnlineInfo[4]))) {
/* 1924 */         COAThread.COA_Account_Cost(radiusOnlineInfo, 
/* 1925 */           "By Customer COA");
/* 1926 */         if (stringUtils.isNotBlank(radiusOnlineInfo[3])) {
/* 1927 */           PortalaccountmacsQuery macsq = new PortalaccountmacsQuery();
/* 1928 */           macsq.setAccountId(acc.getId());
/* 1929 */           macsq.setMac(PortalUtil.MacFormat(radiusOnlineInfo[3]));
/* 1930 */           macsq.setMacLike(false);
/* 1931 */           this.macsService.deleteByQuery(macsq);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1937 */     return "redirect:/customerRadiusOnline.action";
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.portalcustomerController
 * JD-Core Version:    0.6.2
 */