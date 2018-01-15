package com.leeson.core.controller;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.Payapi;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.bean.Portalcardcategory;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalcardQuery;
import com.leeson.core.query.PortalcardcategoryQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PayapiService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalcardService;
import com.leeson.core.service.PortalcardcategoryService;
import com.leeson.core.service.PortalorderService;
import com.leeson.core.service.PortalspeedService;
import com.leeson.core.utils.HttpsUtils;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import com.wxpay.Core;
import com.wxpay.Notify;
import com.wxpay.bean.WxPayDto;
import com.wxpay.bean.WxPayResult;
import com.wxpay.utils.RequestHandler;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/buyController")
public class BuyController
{

  @Autowired
  private PortalcardService portalcardService;

  @Autowired
  private PortalcardcategoryService portalcardcategoryService;

  @Autowired
  private PortalaccountService portalaccountService;

  @Autowired
  private PortalorderService portalorderService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private PortalspeedService portalspeedService;

  @Autowired
  private PayapiService payapiService;
  private static Logger logger = Logger.getLogger(BuyController.class);

  @RequestMapping({"/buy.action"})
  public String buy(ModelMap model)
  {
    List list = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    model.addAttribute("list", list);
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    return "buy/buy";
  }

  @RequestMapping({"/goBuy.action"})
  public String goBuy(Long id, ModelMap model, HttpServletRequest request)
  {
    if (Do()) {
      if (id == null) {
        List list = this.portalcardcategoryService
          .getPortalcardcategoryList(new PortalcardcategoryQuery());
        model.addAttribute("list", list);
        model.addAttribute("msg", "该充值卡不存在！！");
        return "buy/buy";
      }

      Portalcardcategory card = this.portalcardcategoryService
        .getPortalcardcategoryByKey(id);
      if (card == null) {
        List list = this.portalcardcategoryService
          .getPortalcardcategoryList(new PortalcardcategoryQuery());
        model.addAttribute("list", list);
        model.addAttribute("msg", "该充值卡不存在！！");
        return "buy/buy";
      }

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
      Long userId = acc.getId();
      Long cardId = card.getId();

      session.setAttribute("userId", userId);
      session.setAttribute("cardId", cardId);

      model.addAttribute("user", acc);
      model.addAttribute("card", card);

      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);

      Payapi payapi = this.payapiService.getPayapiByKey(Long.valueOf(1L));
      int alipay = payapi.getAlipay().intValue();
      int wxpay = payapi.getWeixin().intValue();
      model.addAttribute("alipay", Integer.valueOf(alipay));
      model.addAttribute("wxpay", Integer.valueOf(wxpay));

      return "buy/goBuy";
    }
    List list = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    model.addAttribute("list", list);
    model.addAttribute("msg", "系统未授权或已过期！！");
    return "buy/buy";
  }

  @RequestMapping({"/goAlipay.action"})
  public String goAlipay(ModelMap model, HttpServletRequest request)
  {
    if (Do()) {
      HttpSession session = request.getSession();
      try
      {
        Long userID = Long.valueOf(((Long)session.getAttribute("userId")).longValue());
        Long cardID = Long.valueOf(((Long)session.getAttribute("cardId")).longValue());

        if (cardID == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        Portalcardcategory card = this.portalcardcategoryService
          .getPortalcardcategoryByKey(cardID);
        if (card == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        Portalaccount account = this.portalaccountService
          .getPortalaccountByKey(userID);
        if (account == null) {
          model.addAttribute("msg", "用户验证失败，请先登录！！");
          return "portalcustomer/login";
        }

        String categoryType = card.getState();
        String payType;
        if (categoryType.equals("0")) {
          payType = "2";
        }
        else
        {
          if (categoryType.equals("4"))
            payType = "4";
          else {
            payType = "3";
          }
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

        cardOrder.setState("-1");
        cardOrder.setBuyDate(new Date());
        cardOrder.setAccountName(account.getLoginName());
        cardOrder.setAccountId(userID);

        cardOrder.setMaclimit(card.getMaclimit());
        cardOrder.setMaclimitcount(card.getMaclimitcount());
        cardOrder.setAutologin(card.getAutologin());
        cardOrder.setSpeed(card.getSpeed());

        this.portalcardService.addPortalcard(cardOrder);

        String out_trade_no = cdkey;

        String subject = card.getName();

        String total_fee = String.valueOf(card.getMoney());

        String body = card.getDescription();

        String return_url = request.getScheme() + "://" + 
          request.getServerName() + ":" + 
          request.getServerPort() + request.getContextPath() + 
          "/returnAlipay.action";

        String notify_url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + 
          "/returnAlipayNotify.action";

        Payapi payapi = this.payapiService.getPayapiByKey(Long.valueOf(1L));

        Map sParaTemp = new HashMap();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", payapi.getAlipayPartner());
        sParaTemp.put("seller_id", payapi.getAlipayPartner());
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("anti_phishing_key", 
          AlipayConfig.anti_phishing_key);

        sParaTemp.put("exter_invoke_ip", 
          GetNgnixRemotIP.getRemoteAddrIp(request));
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);

        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", 
          "确认");
        model.addAttribute("web", sHtmlText);
        model.addAttribute("cid", cardOrder.getId());

        if (isPortalWXAuthInner(request)) {
          model.addAttribute("weixin", Integer.valueOf(1));
          model.addAttribute("ones", Integer.valueOf(1));
        } else {
          model.addAttribute("weixin", Integer.valueOf(0));
        }
        return "buy/goAlipay";
      } catch (Exception e) {
        List list = this.portalcardcategoryService
          .getPortalcardcategoryList(new PortalcardcategoryQuery());
        model.addAttribute("list", list);
        model.addAttribute("msg", "发生错误！！");
        return "buy/buy";
      }
    }
    List list = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    model.addAttribute("list", list);
    model.addAttribute("msg", "系统未授权或已过期！！");
    return "buy/buy";
  }

  @RequestMapping({"/goAlipayUrl.action"})
  public String goAlipay(Long id, ModelMap model, HttpServletRequest request)
  {
    if (Do()) {
      try {
        if (id == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        Portalcard card = this.portalcardService.getPortalcardByKey(id);

        if (card == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        if (!"-1".equals(card.getState())) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该订单已支付！！");
          return "buy/buy";
        }

        String out_trade_no = card.getCdKey();

        String subject = card.getName();

        String total_fee = String.valueOf(card.getMoney());

        String body = card.getDescription();

        String return_url = request.getScheme() + "://" + 
          request.getServerName() + ":" + 
          request.getServerPort() + request.getContextPath() + 
          "/returnAlipay.action";

        String notify_url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + 
          "/returnAlipayNotify.action";

        Payapi payapi = this.payapiService.getPayapiByKey(Long.valueOf(1L));

        Map sParaTemp = new HashMap();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", payapi.getAlipayPartner());
        sParaTemp.put("seller_id", payapi.getAlipayPartner());
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("anti_phishing_key", 
          AlipayConfig.anti_phishing_key);

        sParaTemp.put("exter_invoke_ip", 
          GetNgnixRemotIP.getRemoteAddrIp(request));
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);

        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", 
          "确认");
        model.addAttribute("web", sHtmlText);

        model.addAttribute("cid", id);
        if (isPortalWXAuthInner(request)) {
          model.addAttribute("weixin", Integer.valueOf(1));
          model.addAttribute("ones", Integer.valueOf(0));
        } else {
          model.addAttribute("weixin", Integer.valueOf(0));
        }
        return "buy/goAlipay";
      } catch (Exception e) {
        List list = this.portalcardcategoryService
          .getPortalcardcategoryList(new PortalcardcategoryQuery());
        model.addAttribute("list", list);
        model.addAttribute("msg", "发生错误！！");
        return "buy/buy";
      }
    }
    List list = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    model.addAttribute("list", list);
    model.addAttribute("msg", "系统未授权或已过期！！");
    return "buy/buy";
  }

  @RequestMapping({"/returnAlipayNotify.action"})
  public void returnAlipayNotify(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if (Do())
      try
      {
        Map params = new HashMap();
        Map requestParams = request.getParameterMap();
        Iterator iter = requestParams.keySet().iterator();
        while (iter
          .hasNext()) {
          String name = (String)iter.next();
          String[] values = (String[])requestParams.get(name);
          String valueStr = "";
          for (int i = 0; i < values.length; i++) {
            valueStr = 
              valueStr + values[i] + ",";
          }

          params.put(name, valueStr);
          logger.info("returnAlipayNotify Info : " + name + "=" + 
            valueStr);
        }

        String out_trade_no = request.getParameter("out_trade_no");
        String cardKey = out_trade_no;

        String trade_no = request.getParameter("trade_no");
        String buyer = request.getParameter("buyer_email");
        String seller = request.getParameter("seller_email");

        String trade_status = request.getParameter("trade_status");

        if (AlipayNotify.verify(params))
        {
          if (trade_status.equals("TRADE_FINISHED"))
          {
            if (stringUtils.isNotBlank(cardKey)) {
              Boolean boo = Boolean.valueOf(false);
              PortalcardQuery cq = new PortalcardQuery();
              cq.setCdKey(cardKey);
              cq.setCdKeyLike(false);
              cq.setStateLike(false);
              cq.setState("-1");
              List cards = this.portalcardService
                .getPortalcardList(cq);
              if ((cards != null) && (cards.size() == 1)) {
                boo = Boolean.valueOf(true);
              }
              if (boo.booleanValue()) {
                Portalcard card = (Portalcard)cards.get(0);
                Long aid = card.getAccountId();
                if (aid != null) {
                  Portalaccount e = this.portalaccountService
                    .getPortalaccountByKey(aid);
                  if (e != null) {
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
                    e.setMaclimitcount(card
                      .getMaclimitcount());
                    e.setAutologin(card.getAutologin());
                    e.setSpeed(card.getSpeed());

                    this.portalaccountService
                      .updatePortalaccountByKey(e);
                    card.setAccountId(aid);
                    card.setAccountName(e.getLoginName());
                    card.setState("2");
                    card.setPayDate(new Date());
                    this.portalcardService
                      .updatePortalcardByKey(card);

                    Portalorder order = new Portalorder();
                    order.setAccountDel(card
                      .getAccountDel());
                    order.setAccountId(card.getAccountId());
                    order.setAccountName(card
                      .getAccountName());
                    order.setBuyDate(card.getBuyDate());
                    order.setBuyer(buyer);
                    order.setCategoryType(card
                      .getCategoryType());
                    order.setCdKey(card.getCdKey());
                    order.setDescription(card
                      .getDescription());
                    order.setMoney(card.getMoney());
                    order.setName(card.getName());
                    order.setPayby(Integer.valueOf(1));
                    order.setPayDate(card.getPayDate());
                    order.setPayTime(card.getPayTime());
                    order.setPayType(card.getPayType());
                    order.setSeller(seller);
                    order.setState("1");
                    order.setTradeno(trade_no);
                    order.setUserDel(card.getUserDel());
                    this.portalorderService
                      .addPortalorder(order);
                  }
                }
              }

            }

          }
          else if (trade_status.equals("TRADE_SUCCESS"))
          {
            if (stringUtils.isNotBlank(cardKey)) {
              Boolean boo = Boolean.valueOf(false);
              PortalcardQuery cq = new PortalcardQuery();
              cq.setCdKey(cardKey);
              cq.setCdKeyLike(false);
              cq.setStateLike(false);
              cq.setState("-1");
              List cards = this.portalcardService
                .getPortalcardList(cq);
              if ((cards != null) && (cards.size() == 1)) {
                boo = Boolean.valueOf(true);
              }
              if (boo.booleanValue()) {
                Portalcard card = (Portalcard)cards.get(0);
                Long aid = card.getAccountId();
                if (aid != null) {
                  Portalaccount e = this.portalaccountService
                    .getPortalaccountByKey(aid);
                  if (e != null) {
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
                    e.setMaclimitcount(card
                      .getMaclimitcount());
                    e.setAutologin(card.getAutologin());
                    e.setSpeed(card.getSpeed());

                    this.portalaccountService
                      .updatePortalaccountByKey(e);
                    card.setAccountId(aid);
                    card.setAccountName(e.getLoginName());
                    card.setState("2");
                    card.setPayDate(new Date());
                    this.portalcardService
                      .updatePortalcardByKey(card);

                    Portalorder order = new Portalorder();
                    order.setAccountDel(card
                      .getAccountDel());
                    order.setAccountId(card.getAccountId());
                    order.setAccountName(card
                      .getAccountName());
                    order.setBuyDate(card.getBuyDate());
                    order.setBuyer(buyer);
                    order.setCategoryType(card
                      .getCategoryType());
                    order.setCdKey(card.getCdKey());
                    order.setDescription(card
                      .getDescription());
                    order.setMoney(card.getMoney());
                    order.setName(card.getName());
                    order.setPayby(Integer.valueOf(1));
                    order.setPayDate(card.getPayDate());
                    order.setPayTime(card.getPayTime());
                    order.setPayType(card.getPayType());
                    order.setSeller(seller);
                    order.setState("1");
                    order.setTradeno(trade_no);
                    order.setUserDel(card.getUserDel());
                    this.portalorderService
                      .addPortalorder(order);
                  }

                }

              }

            }

          }

          String respMessage = "success";
          PrintWriter out = response.getWriter();
          out.print(respMessage);
          out.close();
        }
        else
        {
          String respMessage = "fail";
          PrintWriter out = response.getWriter();
          out.print(respMessage);
          out.close();
        }
      } catch (Exception e) {
        try {
          String respMessage = "fail";
          PrintWriter out = response.getWriter();
          out.print(respMessage);
          out.close();
        }
        catch (Exception localException1) {
        }
      }
    else try {
        String respMessage = "fail";
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
      }
      catch (Exception localException2)
      {
      }
  }

  @RequestMapping({"/returnAlipay.action"})
  public String returnAlipay(ModelMap model, HttpServletRequest request)
  {
    if (Do())
    {
      Map params = new HashMap();
      Map requestParams = request.getParameterMap();
      Iterator iter = requestParams.keySet().iterator();
      while (iter
        .hasNext()) {
        String name = (String)iter.next();
        String[] values = (String[])requestParams.get(name);
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
          valueStr = 
            valueStr + values[i] + ",";
        }
        try
        {
          valueStr = new String(valueStr.getBytes("ISO-8859-1"), 
            "utf-8");
        }
        catch (UnsupportedEncodingException e) {
          logger.info("returnAlipay Code ISO-8859-1 to UTF-8 Error : " + 
            e);
        }
        params.put(name, valueStr);
        logger.info("returnAlipay Info : " + name + "=" + valueStr);
      }

      String trade_status = request.getParameter("trade_status");

      if (AlipayNotify.verify(params))
      {
        if (!trade_status.equals("TRADE_FINISHED"))
        {
          trade_status.equals("TRADE_SUCCESS");
        }

        HttpSession session = request.getSession();
        Portalaccount e = new Portalaccount();
        String un = (String)session.getAttribute("username");
        String pwd = (String)session.getAttribute("password");

        if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
          PortalaccountQuery aq = new PortalaccountQuery();
          aq.setLoginNameLike(false);
          aq.setPasswordLike(false);

          aq.setLoginName(un);
          aq.setPassword(pwd);
          List accounts = this.portalaccountService.getPortalaccountList(aq);
          if (accounts.size() == 1) {
            e = (Portalaccount)accounts.get(0);
          }
        }
        String isOnline = "在线";
        model.addAttribute("entity", e);
        model.addAttribute("isOnline", isOnline);
        model.addAttribute("msg", "恭喜您，充值成功！");
        return "portalcustomer/index";
      }

      HttpSession session = request.getSession();
      Portalaccount e = new Portalaccount();
      String un = (String)session.getAttribute("username");
      String pwd = (String)session.getAttribute("password");

      if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginNameLike(false);
        aq.setPasswordLike(false);

        aq.setLoginName(un);
        aq.setPassword(pwd);
        List accounts = this.portalaccountService.getPortalaccountList(aq);
        if (accounts.size() == 1) {
          e = (Portalaccount)accounts.get(0);
        }
      }
      String isOnline = "在线";
      model.addAttribute("entity", e);
      model.addAttribute("isOnline", isOnline);
      model.addAttribute("msg", "充值失败！");
      return "portalcustomer/index";
    }

    HttpSession session = request.getSession();
    Portalaccount e = new Portalaccount();
    String un = (String)session.getAttribute("username");
    String pwd = (String)session.getAttribute("password");

    if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
      PortalaccountQuery aq = new PortalaccountQuery();
      aq.setLoginNameLike(false);
      aq.setPasswordLike(false);

      aq.setLoginName(un);
      aq.setPassword(pwd);
      List accounts = this.portalaccountService.getPortalaccountList(aq);
      if (accounts.size() == 1) {
        e = (Portalaccount)accounts.get(0);
      }
    }
    String isOnline = "在线";
    model.addAttribute("entity", e);
    model.addAttribute("isOnline", isOnline);
    model.addAttribute("msg", "系统未授权或已过期！！");
    return "portalcustomer/index";
  }

  @RequestMapping({"/goWXpay.action"})
  public String goWXpay(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if (Do()) {
      HttpSession session = request.getSession();
      try
      {
        Long userID = Long.valueOf(((Long)session.getAttribute("userId")).longValue());
        Long cardID = Long.valueOf(((Long)session.getAttribute("cardId")).longValue());

        if (cardID == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        Portalcardcategory card = this.portalcardcategoryService
          .getPortalcardcategoryByKey(cardID);
        if (card == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        Portalaccount account = this.portalaccountService
          .getPortalaccountByKey(userID);
        if (account == null) {
          model.addAttribute("msg", "用户验证失败，请先登录！！");
          return "portalcustomer/login";
        }

        if (isPortalWXAuthInner(request)) {
          Payapi payAPI = this.payapiService.getPayapiByKey(Long.valueOf(1L));
          String weixinAppId = payAPI.getWeixinAppId();
          String url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + "/doWXpay.action";
          String authUrl = "https:onnectuthorize";
          model.addAttribute("url", url);
          model.addAttribute("authUrl", authUrl);
          model.addAttribute("appid", weixinAppId);
          return "buy/oauth";
        }
        return "redirect:/doWXpay.action";
      }
      catch (Exception e) {
        List list = this.portalcardcategoryService
          .getPortalcardcategoryList(new PortalcardcategoryQuery());
        model.addAttribute("list", list);
        model.addAttribute("msg", "发生错误！！");
        return "buy/buy";
      }
    }
    List list = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    model.addAttribute("list", list);
    model.addAttribute("msg", "系统未授权或已过期！！");
    return "buy/buy";
  }

  @RequestMapping({"/doWXpay.action"})
  public String doWXpay(ModelMap model, HttpServletRequest request)
  {
    if (Do()) {
      HttpSession session = request.getSession();
      try
      {
        Long userID = Long.valueOf(((Long)session.getAttribute("userId")).longValue());
        Long cardID = Long.valueOf(((Long)session.getAttribute("cardId")).longValue());

        if (cardID == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        Portalcardcategory card = this.portalcardcategoryService
          .getPortalcardcategoryByKey(cardID);
        if (card == null) {
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "该充值卡不存在！！");
          return "buy/buy";
        }

        Portalaccount account = this.portalaccountService
          .getPortalaccountByKey(userID);
        if (account == null) {
          model.addAttribute("msg", "用户验证失败，请先登录！！");
          return "portalcustomer/login";
        }

        String categoryType = card.getState();
        String payType;
        if (categoryType.equals("0")) {
          payType = "2";
        }
        else
        {
          if (categoryType.equals("4"))
            payType = "4";
          else {
            payType = "3";
          }
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

        String cdkey = UUID.randomUUID().toString().replace("-", "");
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

        cardOrder.setState("-1");
        cardOrder.setBuyDate(new Date());
        cardOrder.setAccountName(account.getLoginName());
        cardOrder.setAccountId(userID);

        cardOrder.setMaclimit(card.getMaclimit());
        cardOrder.setMaclimitcount(card.getMaclimitcount());
        cardOrder.setAutologin(card.getAutologin());
        cardOrder.setSpeed(card.getSpeed());

        this.portalcardService.addPortalcard(cardOrder);

        String notify_url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + 
          "/returnWXpayNotify.action";

        Payapi payAPI = this.payapiService.getPayapiByKey(Long.valueOf(1L));
        String weixinAppId = payAPI.getWeixinAppId();
        String weixinAppSecret = payAPI.getWeixinAppSecret();
        String weixinPartner = payAPI.getWeixinPartner();
        String weixinKey = payAPI.getWeixinKey();

        String code = request.getParameter("code");
        String openId = "";
        if (stringUtils.isNotBlank(code)) {
          String oauth2_url = "https:nsccess_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
          String oauth_url = oauth2_url.replace("APPID", weixinAppId)
            .replace("SECRET", weixinAppSecret)
            .replace("CODE", code);
          JSONObject jsonObject = httpsRequestToJsonObject(oauth_url, 
            "POST", null);
          Object errorCode = jsonObject.get("errcode");
          if (errorCode != null) {
            System.out.println("code error");
          } else {
            openId = jsonObject.getString("openid");
            System.out.println("openId:" + openId);
          }

        }

        WxPayDto tpWxPay = new WxPayDto();
        tpWxPay.setOpenId(openId);
        tpWxPay.setBody(card.getName());
        tpWxPay.setOrderId(cdkey);
        tpWxPay.setSpbillCreateIp(
          GetNgnixRemotIP.getRemoteAddrIp(request));
        tpWxPay.setTotalFee(String.valueOf(card.getMoney()));

        model.addAttribute("user", account);
        model.addAttribute("card", card);

        if (isPortalWXAuthInner(request)) {
          model.addAttribute("weixin", Integer.valueOf(1));
          String codeData = Core.getPackage(tpWxPay, weixinAppId, 
            weixinAppSecret, weixinPartner, weixinKey, notify_url);
          if (stringUtils.isNotBlank(codeData)) {
            model.addAttribute("codeData", codeData);
            return "buy/goWXpay";
          }
          List list = this.portalcardcategoryService
            .getPortalcardcategoryList(new PortalcardcategoryQuery());
          model.addAttribute("list", list);
          model.addAttribute("msg", "发生错误！！");
          return "buy/buy";
        }

        model.addAttribute("weixin", Integer.valueOf(0));
        String codeUrl = Core.getCodeurl(tpWxPay, weixinAppId, 
          weixinAppSecret, weixinPartner, weixinKey, notify_url);
        if (stringUtils.isNotBlank(codeUrl)) {
          model.addAttribute("codeUrl", codeUrl);
          return "buy/goWXpay";
        }
        List list = this.portalcardcategoryService
          .getPortalcardcategoryList(new PortalcardcategoryQuery());
        model.addAttribute("list", list);
        model.addAttribute("msg", "发生错误！！");
        return "buy/buy";
      }
      catch (Exception e)
      {
        List list = this.portalcardcategoryService
          .getPortalcardcategoryList(new PortalcardcategoryQuery());
        model.addAttribute("list", list);
        model.addAttribute("msg", "发生错误！！");
        return "buy/buy";
      }
    }
    List list = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    model.addAttribute("list", list);
    model.addAttribute("msg", "系统未授权或已过期！！");
    return "buy/buy";
  }

  @RequestMapping({"/returnWXpayNotify.action"})
  public void returnWXpayNotify(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if (Do())
    {
      try
      {
        String notityXml = "";
        String resXml = "";
        String inputLine;
        while ((inputLine = request.getReader().readLine()) != null)
        {
          notityXml = notityXml + inputLine;
        }
        request.getReader().close();

        logger.info("Recive Weixin Server Pay Notify Result : " + 
          notityXml);

        Map m = Notify.parseXmlToList2(notityXml);
        String TrueSign = "";
        try {
          SortedMap packageParams = new TreeMap();
          Map params = new HashMap();
          Iterator iter = m.keySet().iterator();
          while (iter
            .hasNext()) {
            String name = (String)iter.next();
            String value = (String)m.get(name);

            params.put(name, value);
            packageParams.put(name, value);
            logger.info("returnWXpayNotify Info : " + name + "=" + 
              value);
          }
          packageParams.remove("sign");

          Payapi payAPI = this.payapiService.getPayapiByKey(Long.valueOf(1L));
          String weixinAppId = payAPI.getWeixinAppId();
          String weixinAppSecret = payAPI.getWeixinAppSecret();
          String weixinKey = payAPI.getWeixinKey();

          RequestHandler reqHandler = new RequestHandler(null, null);
          reqHandler.init(weixinAppId, weixinAppSecret, weixinKey);
          TrueSign = reqHandler.createSign(packageParams);
          logger.info("returnWXpayNotify doSign : " + TrueSign);
        }
        catch (Exception localException1) {
        }
        String cardKey = ((String)m.get("out_trade_no")).toString();
        String buyer = ((String)m.get("openid")).toString();
        String seller = ((String)m.get("appid")).toString();
        String trade_no = ((String)m.get("transaction_id")).toString();
        String sign = ((String)m.get("sign")).toString();

        WxPayResult wpr = new WxPayResult();
        wpr.setAppid(seller);
        wpr.setBankType(((String)m.get("bank_type")).toString());
        wpr.setCashFee(((String)m.get("cash_fee")).toString());
        wpr.setFeeType(((String)m.get("fee_type")).toString());
        wpr.setIsSubscribe(((String)m.get("is_subscribe")).toString());
        wpr.setMchId(((String)m.get("mch_id")).toString());
        wpr.setNonceStr(((String)m.get("nonce_str")).toString());
        wpr.setOpenid(buyer);
        wpr.setOutTradeNo(cardKey);
        wpr.setResultCode(((String)m.get("result_code")).toString());
        wpr.setReturnCode(((String)m.get("return_code")).toString());
        wpr.setSign(sign);
        wpr.setTimeEnd(((String)m.get("time_end")).toString());
        wpr.setTotalFee(((String)m.get("total_fee")).toString());
        wpr.setTradeType(((String)m.get("trade_type")).toString());
        wpr.setTransactionId(trade_no);

        if (("SUCCESS".equals(wpr.getResultCode())) && 
          (TrueSign.equals(sign)))
        {
          if (stringUtils.isNotBlank(cardKey)) {
            Boolean boo = Boolean.valueOf(false);
            PortalcardQuery cq = new PortalcardQuery();
            cq.setCdKey(cardKey);
            cq.setCdKeyLike(false);
            cq.setStateLike(false);
            cq.setState("-1");
            List cards = this.portalcardService
              .getPortalcardList(cq);
            if ((cards != null) && (cards.size() == 1)) {
              boo = Boolean.valueOf(true);
            }
            if (boo.booleanValue()) {
              Portalcard card = (Portalcard)cards.get(0);
              Long aid = card.getAccountId();
              if (aid != null) {
                Portalaccount e = this.portalaccountService
                  .getPortalaccountByKey(aid);
                if (e != null) {
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

                  this.portalaccountService
                    .updatePortalaccountByKey(e);
                  card.setAccountId(aid);
                  card.setAccountName(e.getLoginName());
                  card.setState("2");
                  card.setPayDate(new Date());
                  this.portalcardService
                    .updatePortalcardByKey(card);

                  Portalorder order = new Portalorder();
                  order.setAccountDel(card.getAccountDel());
                  order.setAccountId(card.getAccountId());
                  order.setAccountName(card.getAccountName());
                  order.setBuyDate(card.getBuyDate());
                  order.setBuyer(buyer);
                  order.setCategoryType(card
                    .getCategoryType());
                  order.setCdKey(card.getCdKey());
                  order.setDescription(card.getDescription());
                  order.setMoney(card.getMoney());
                  order.setName(card.getName());
                  order.setPayby(Integer.valueOf(2));
                  order.setPayDate(card.getPayDate());
                  order.setPayTime(card.getPayTime());
                  order.setPayType(card.getPayType());
                  order.setSeller(seller);
                  order.setState("1");
                  order.setTradeno(trade_no);
                  order.setUserDel(card.getUserDel());
                  this.portalorderService.addPortalorder(order);
                }
              }
            }
          }
          resXml = "<xml><return_code><![CDATA[SUCCESS]]><eturn_msg></xml> ";
        }
        else
        {
          resXml = "<xml><return_code><![CDATA[FAIL]]><eturn_msg></xml> ";
        }

        BufferedOutputStream out = new BufferedOutputStream(
          response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
      } catch (Exception e) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
        try {
          String resXml = "<xml><return_code><![CDATA[FAIL]]><eturn_msg></xml> ";

          BufferedOutputStream out = new BufferedOutputStream(
            response.getOutputStream());
          out.write(resXml.getBytes());
          out.flush();
          out.close();
        } catch (Exception localException2) {
        }
      }
    }
    else try {
        String resXml = "<xml><return_code><![CDATA[FAIL]]><eturn_msg></xml> ";

        BufferedOutputStream out = new BufferedOutputStream(
          response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
      }
      catch (Exception localException3)
      {
      } 
  }

  public static boolean Do()
  {
    Long isThis = Long.valueOf(new Date().getTime());
    boolean Do = false;
    if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
      Do = true;
    }
    return Do;
  }

  public static boolean isPortalWXAuthInner(HttpServletRequest request)
  {
    String WEI_XIN_BROWSER = "MicroMessenger/";

    String userAgent = request.getHeader("user-agent");
    boolean isPortalWXAuthInner = false;
    if (stringUtils.isNotBlank(userAgent))
    {
      if (userAgent.contains(WEI_XIN_BROWSER)) {
        isPortalWXAuthInner = true;
      }
    }
    return isPortalWXAuthInner;
  }

  public static JSONObject httpsRequestToJsonObject(String requestUrl, String requestMethod, String outputStr)
  {
    JSONObject jsonObject = null;
    try {
      String buffer = HttpsUtils.httpsRequest(requestUrl, requestMethod, outputStr);
      jsonObject = JSONObject.fromObject(buffer);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return jsonObject;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.BuyController
 * JD-Core Version:    0.6.2
 */